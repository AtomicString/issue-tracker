package me.atomicstring.tracker.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import me.atomicstring.tracker.users.User;

public interface UserDao {
	
	@SqlQuery("SELECT id, username, password_hash, role, image FROM users")
	@RegisterBeanMapper(User.class)
    List<User> getAllUsers();

    @SqlUpdate("INSERT INTO users (id, username, password_hash, role, image) VALUES (:id, :username, :passwordHash, :role, :image)")
	@RegisterBeanMapper(User.class)
    void insertUser(@BindBean User user);
	
	@SqlQuery("SELECT * FROM users WHERE id=:id")
	@RegisterBeanMapper(User.class)
	User getUserById(@Bind("id") UUID id);

	@SqlQuery("SELECT * FROM users WHERE username = :username")
	@RegisterBeanMapper(User.class)
	Optional<User> getUserByName(@Bind("username") String username);
	
	@SqlQuery("SELECT image FROM users WHERE id=:id")
	byte[] getImage(@Bind("id") UUID id);
	
	@SqlUpdate("UPDATE users SET username=:username, image=:image, password_hash=:passwordHash, role=:role WHERE id=:id")
	@RegisterBeanMapper(User.class)
	void updateUser(@BindBean User user);
	
}
