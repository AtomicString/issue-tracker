package me.atomicstring.tracker.dao.data;

import java.time.Instant;
import java.util.UUID;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Comment {
    UUID id;
    UUID issueId;
    UUID authorId;
    UUID parentId;
    String content;
    Instant createdAt;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	
	@ColumnName("issue_id")
	public UUID getIssueId() {
		return issueId;
	}
	public void setIssueId(UUID issueId) {
		this.issueId = issueId;
	}

	@ColumnName("author_id")
	public UUID getAuthorId() {
		return authorId;
	}
	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}

	@ColumnName("parent_id")
	public UUID getParentId() {
		return parentId;
	}
	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@ColumnName("created_at")
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
    
}
