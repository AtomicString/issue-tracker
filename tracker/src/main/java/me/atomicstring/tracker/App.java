package me.atomicstring.tracker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

import org.jdbi.v3.core.ConnectionException;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UploadedFile;
import me.atomicstring.tracker.dao.SessionDao;
import me.atomicstring.tracker.dao.UserDao;
import me.atomicstring.tracker.middleware.SessionService;
import me.atomicstring.tracker.pages.LoginPage;
import me.atomicstring.tracker.pages.MainPage;
import me.atomicstring.tracker.pages.SignupPage;
import me.atomicstring.tracker.users.AnonUser;
import me.atomicstring.tracker.users.User;

/**
 * Hello world!
 *
 */
public class App {

	static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		String username = System.getenv("POSTGRES_USER");
		String dbName = System.getenv("POSTGRES_DB");
		String password = System.getenv("POSTGRES_PASSWORD");

		HikariDataSource ds = DataSourceFactory.createDataSource(username, password, dbName);

		Jdbi jdbi = Jdbi.create(ds);
		jdbi.installPlugin(new SqlObjectPlugin());

		jdbi.useHandle(handle -> {
			try (Scanner scanner = new Scanner(App.class.getResourceAsStream("/schema.sql"),
					StandardCharsets.UTF_8.name())) {
				scanner.useDelimiter("\\A");
				if (!scanner.hasNext())
					return;
				String schemaSql = scanner.next();
				for (String statement : schemaSql.split(";")) {
					String trimmed = statement.trim();
					if (!trimmed.isEmpty()) {
						handle.execute(trimmed);
					}
				}
			}
		});

		jdbi.useHandle(handle -> {
			try (var conn = handle.getConnection();
					var stmt = conn.createStatement();
					var rs = stmt.executeQuery("SELECT * FROM users LIMIT 0")) {
				var meta = rs.getMetaData();
				logger.info("---- ResultSet Metadata ----");
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					logger.info(String.format("[%d] %s (%s)%n", i, meta.getColumnName(i), meta.getColumnLabel(i)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		Javalin app = Javalin.create(config -> {
			config.staticFiles.enableWebjars();
			config.requestLogger.http((ctx, ms) -> {
				logger.info(ctx.path() + " took " + ms);
			});
		});

		handleRoutes(app, jdbi);

		app.events(events -> {
			events.serverStopping(() -> {
				ds.close();
			});
		});

		handleMiddleware(app, jdbi);

		handleExceptions(app);
		app.start(8000);
	}

	static UUID generateUnusedUUID(UserDao userDao) {
		while (true) {
			UUID candidate = UUID.randomUUID();
			boolean exists = userDao.getAllUsers().stream().anyMatch(user -> user.getId().equals(candidate));
			if (!exists) {
				return candidate;
			}
		}
	}

	private static void handleRoutes(Javalin app, Jdbi jdbi) {
		UserDao userDao = jdbi.onDemand(UserDao.class);
		SessionDao sessionDao = jdbi.onDemand(SessionDao.class);

		SessionService sessionService = new SessionService(sessionDao, userDao);

		app.get("/", ctx -> ctx.html("<!DOCTYPE html>" + new MainPage().getPage(ctx).render()));
		app.get("/login", ctx -> {
			if (ctx.attribute("user") instanceof User) {
				ctx.redirect("/");
			}
			ctx.html("<!DOCTYPE html>" + new LoginPage().getPage(ctx).render());
		});

		app.post("/login", ctx -> {
			if (ctx.attribute("user") instanceof User) {
				ctx.redirect("/");
			}

			String username = ctx.formParam("username");
			String passsword = ctx.formParam("password");

			User potentialUser = userDao.getUserByName(username)
					.orElseThrow(() -> new ForbiddenResponse("Invalid username or password"));
			if (BCrypt.checkpw(passsword, potentialUser.getPasswordHash())) {
				UUID sessionId = sessionService.createSessionForUser(potentialUser);
				ctx.cookie("session", sessionId.toString(), 86400);
				ctx.redirect("/");
				return;
			}

			ctx.status(403).result("Invalid username or password");
		});

		app.get("/signup", ctx -> {
			if (ctx.attribute("user") instanceof User) {
				ctx.redirect("/");
			}
			ctx.html("<!DOCTYPE html>" + new SignupPage().getPage(ctx).render());
		});

		app.post("/signup", ctx -> {
			if (ctx.attribute("user") instanceof User) {
				ctx.redirect("/");
			}

			String username = ctx.formParam("username");
			String password = ctx.formParam("password");
			String re_password = ctx.formParam("re_password");
			UploadedFile file = ctx.uploadedFile("image");

			if (!password.equals(re_password)) {
				ctx.status(500).result("Passwords do not match");
				return;
			}

			try (InputStream is = file.content(); ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

				byte[] data = new byte[8192];
				int nRead;
				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				byte[] imageBytes = buffer.toByteArray();

				UUID id = generateUnusedUUID(userDao);
				String password_hash = BCrypt.hashpw(password, BCrypt.gensalt());

				User user = new User();
				
				user.setId(id);
				user.setUsername(username);
				user.setPasswordHash(password_hash);
				user.setRole("user");
				user.setImage(imageBytes);

				userDao.insertUser(user);
				sessionService.createSessionForUser(user);

				ctx.redirect("/");

			} catch (IOException e) {
				ctx.status(500).result("Error reading image");
			}

		});

		app.get("/users/{id}/image", ctx -> {
			UUID userId = UUID.fromString(ctx.pathParam("id"));

			User user = userDao.getUserById(userId).orElseThrow(() -> new NotFoundResponse("User could not be found"));

			byte[] imageData = user.getImage();

			if (imageData != null) {
				ctx.contentType("image/jpeg"); // or detect dynamically
				ctx.result(new ByteArrayInputStream(imageData));
				return;
			}

			ctx.status(404).result("No image found");

		});
	}

	private static void handleMiddleware(Javalin app, Jdbi jdbi) {
		UserDao userDao = jdbi.onDemand(UserDao.class);
		SessionDao sessionDao = jdbi.onDemand(SessionDao.class);

		SessionService sessionService = new SessionService(sessionDao, userDao);
		app.before(ctx -> {
			String sessionId = ctx.cookie("session");
			if (sessionId != null) {
				sessionService.getUserForSession(UUID.fromString(sessionId)).ifPresentOrElse(user -> {
					ctx.attribute("user", user);
					logger.info("Logged in");
				}, () -> {
					ctx.attribute("user", new AnonUser());
					ctx.removeCookie("session");
				});
			} else {
				ctx.attribute("user", new AnonUser());
			}
		});
	}

	private static void handleExceptions(Javalin app) {
		app.exception(ConnectionException.class, (e, ctx) -> {
			ctx.status(500).result("Could not obtain connection");
		});
		app.exception(NotFoundResponse.class, (e, ctx) -> {
			ctx.status(404).result(e.getMessage());
		});

	}

}
