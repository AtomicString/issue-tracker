package me.atomicstring.tracker.middleware;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import io.javalin.http.NotFoundResponse;
import me.atomicstring.tracker.dao.SessionDao;
import me.atomicstring.tracker.dao.UserDao;
import me.atomicstring.tracker.dao.records.Session;
import me.atomicstring.tracker.users.User;

public class SessionService {
    private final SessionDao sessionDao;
    private final UserDao userDao;

    public SessionService(SessionDao sessionDao, UserDao userDao) {
        this.sessionDao = sessionDao;
        this.userDao = userDao;
    }

    public User getUserForSession(UUID sessionId) {
        Session session = sessionDao.getById(sessionId);
        if (session == null) {
            return null;
        } else if (session.isExpired()) {
        	deleteSession(sessionId);
        	return null;
        }
        return userDao.getUserById(session.user_id()).orElseThrow(() -> new NotFoundResponse("Session's user could not be found"));
    }

    public UUID createSessionForUser(User user) {
        UUID sessionId = UUID.randomUUID();
        Instant now = Instant.now();
        sessionDao.createSession(new Session(sessionId, user.id(), now, now.plus(Duration.ofDays(1))));
        return sessionId;
    }

    public void deleteSession(UUID sessionId) {
        sessionDao.delete(sessionId);
    }
}
