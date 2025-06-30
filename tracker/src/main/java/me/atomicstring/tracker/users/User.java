package me.atomicstring.tracker.users;

import java.util.UUID;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class User {
    UUID id;
    String username;
    String passwordHash;
    String role;
    byte[] image;

	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	@ColumnName("password_hash")
	public String getPasswordHash() {
		return passwordHash;
	}

	public String getRole() {
		return role;
	}

	public byte[] getImage() {
		return image;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
}
