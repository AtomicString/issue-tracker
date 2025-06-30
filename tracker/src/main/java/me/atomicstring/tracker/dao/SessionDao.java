package me.atomicstring.tracker.dao;

import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import me.atomicstring.tracker.dao.data.Session;


public interface SessionDao {
	
	@SqlUpdate("INSERT INTO sessions (id, user_id, created_at, expires_at) VALUES (:id, :userId, :createdAt, :expiresAt)")
	@RegisterBeanMapper(Session.class)
	void createSession(@BindBean Session session);
	
	
	@SqlQuery("SELECT * FROM sessions WHERE id = :id")
	@RegisterBeanMapper(Session.class)
	Session getById(@Bind("id") UUID id);
	
	@SqlUpdate("DELETE FROM sessions WHERE id = :id")
	void delete(@Bind("id") UUID id);
	
}
