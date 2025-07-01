package me.atomicstring.tracker.dao;

import java.util.List;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import me.atomicstring.tracker.dao.data.Comment;

public interface CommentDao {
	
	@SqlQuery("SELECT * FROM comments WHERE issue_id=:issueId ORDER BY created_at")
	@RegisterBeanMapper(Comment.class)
	public List<Comment> getCommentsForIssue(@Bind("issueId") UUID issueId);
	
	@SqlUpdate("INSERT INTO comments (id, issue_id, author_id, content, created_at) VALUES (:id, :issueId, :authorId, :content, :createdAt)")
	@RegisterBeanMapper(Comment.class)
	public void insertComment(@BindBean Comment comment);
	
}
