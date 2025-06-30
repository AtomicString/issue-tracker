package me.atomicstring.tracker.dao.data;

import java.time.Instant;
import java.util.UUID;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Session {
    UUID id;
    UUID userId;
    Instant createdAt;
    Instant expiresAt;
    
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@ColumnName("user_id")
	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	@ColumnName("created_at")
	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	@ColumnName("expires_at")
	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public boolean isExpired() {
		return expiresAt.isBefore(Instant.now());
	}
	
}
