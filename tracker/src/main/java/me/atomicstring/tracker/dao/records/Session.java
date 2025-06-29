package me.atomicstring.tracker.dao.records;

import java.time.Instant;
import java.util.UUID;

public record Session(
    UUID id,
    UUID user_id,
    Instant created_at,
    Instant expires_at
) {
	
	public boolean isExpired() {
		return expires_at.isBefore(Instant.now());
	}
	
}
