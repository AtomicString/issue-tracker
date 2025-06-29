package me.atomicstring.tracker.users;

import java.util.UUID;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record User(
    UUID id,
    String username,
    @ColumnName("password_hash") String passwordHash,
    String role,
    byte[] image
) {

	public UUID id() {
		return id;
	}

	public String username() {
		return username;
	}

	public String passwordHash() {
		return passwordHash;
	}

	public String role() {
		return role;
	}

	public byte[] image() {
		return image;
	}}
