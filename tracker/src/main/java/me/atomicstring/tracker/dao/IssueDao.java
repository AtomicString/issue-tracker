package me.atomicstring.tracker.dao;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import me.atomicstring.tracker.dao.data.Issue;

public interface IssueDao {
	
	@SqlQuery("SELECT * FROM issues ORDER BY created_at DESC, id DESC LIMIT :limit")
	@RegisterBeanMapper(Issue.class)
	public List<Issue> getLatestPage(@Bind("limit") int limit);
	
	@SqlQuery("SELECT * FROM issues WHERE (created_at, id) < (:beforeTime, :beforeId) ORDER BY created_at DESC, id DESC LIMIT :limit")
	@RegisterBeanMapper(Issue.class)
	public List<Issue> getPage(@Bind("beforeTime") Instant beforeTime, @Bind("beforeId") UUID beforeId, @Bind("limit") int limit);
	
	
	@SqlUpdate("INSERT INTO issues VALUES (:id, :title, :body, :status, :authorId, :createdAt)")
	public void insertIssue(@BindBean Issue issue);
	
}
