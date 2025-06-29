package me.atomicstring.tracker.dao;

import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import me.atomicstring.tracker.dao.records.Session;


@RegisterBeanMapper(Session.class)
public interface SessionDao {
	
	@SqlUpdate("INSERT INTO session (id, user_id, created_at, expires_at) VALUES (:id, :user_id, :created_at, :expires_at")
	void createSession(@BindBean Session session);
	
	
	@SqlQuery("SELECT * FROM session WHERE id = :id")
	Session getById(@Bind("id") UUID id);
	
	@SqlUpdate("DELETE FROM session WHERE id = :id")
	void delete(@Bind("id") UUID id);
	
}
