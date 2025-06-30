package me.atomicstring.tracker.dao.data;

import java.time.Instant;
import java.util.UUID;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Issue {
    UUID id; 
    String title; 
    String body; 
    String status; 
    UUID authorId; 
    Instant createdAt;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@ColumnName("author_id")
	public UUID getAuthorId() {
		return authorId;
	}
	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}

	@ColumnName("created_at")
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
