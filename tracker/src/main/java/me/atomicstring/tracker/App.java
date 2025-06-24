package me.atomicstring.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

import io.javalin.Javalin;
import me.atomicstring.tracker.pages.MainPage;

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
		
		Javalin app = Javalin.create();
		app.get("/", ctx -> ctx.html(new MainPage().build().render()));
		app.events(events -> {
			events.serverStopping(() -> {
				ds.close();
			});
		});
		app.start(8000);
	}
}
