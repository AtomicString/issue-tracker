package me.atomicstring.tracker.middleware;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import me.atomicstring.tracker.dao.SessionDao;
import me.atomicstring.tracker.dao.UserDao;
import me.atomicstring.tracker.dao.data.Session;
import me.atomicstring.tracker.users.User;

public class SessionService {
    private final SessionDao sessionDao;
    private final UserDao userDao;

    public SessionService(SessionDao sessionDao, UserDao userDao) {
        this.sessionDao = sessionDao;
        this.userDao = userDao;
    }

    public Optional<User> getUserForSession(UUID sessionId) {
        Session session = sessionDao.getById(sessionId);
        if (session == null) {
            return Optional.empty();
        } else if (session.isExpired()) {
        	deleteSession(sessionId);
        	return Optional.empty();
        }
        return Optional.of(userDao.getUserById(session.getUserId()));
    }

    public UUID createSessionForUser(User user) {
        UUID sessionId = UUID.randomUUID();
        Instant now = Instant.now();
        Session session = new Session();
        session.setId(sessionId);
        session.setUserId(user.getId());
        session.setCreatedAt(now);
        session.setExpiresAt(now.plus(Duration.ofDays(1)));
        sessionDao.createSession(session);
        return sessionId;
    }

    public void deleteSession(UUID sessionId) {
        sessionDao.delete(sessionId);
    }
}
