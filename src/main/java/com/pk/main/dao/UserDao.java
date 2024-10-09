package com.pk.main.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pk.main.entity.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// query to insert user

	public boolean insertUser(User user) {

		boolean status = false;

		try {

			String insert_sql_query = "INSERT INTO users VALUES (?,?,?,?)";
			int count = jdbcTemplate.update(insert_sql_query, user.getName(), user.getEmail(), user.getGender(),
					user.getCity());

			if (count > 0) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {

			status = false;
			e.printStackTrace();
		}
		return status;
	}

	// update user

	public boolean updateUser(User user) {

		boolean status = false;

		try {

			// updating through email

			String update_sql_query = "UPDATE users SET name = ?, gender = ?, city = ? WHERE email = ?";
			int count = jdbcTemplate.update(update_sql_query, user.getName(), user.getGender(), user.getCity(),
					user.getEmail());

			if (count > 0) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}

	// delete user by email

	public boolean deleteUserByEmail(String email) {

		boolean status = false;

		try {

			// delete through email

			String update_sql_query = "DELETE FROM users WHERE email = ?";
			int count = jdbcTemplate.update(update_sql_query, email);

			if (count > 0) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}

	// Read user by Email

	public User readByEmail(String email) {

		String read_sql_query = "SELECT * FROM users WHERE email = ?";

		// in .query we need to provide list of objects
		// as of new we only have one object so we can use .queryForObject
		// it requires just one object

		return jdbcTemplate.queryForObject(read_sql_query, new UserRowMapper(), email);

	}

	// to get list of Users

	public List<User> getAllUsers() {
		String select_all_sql_query = "SELECT * FROM users";
		return jdbcTemplate.query(select_all_sql_query, new UserRowMapper());
	}

	// RowMapper class is used to get values form the Database

	public static final class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {

			User user = new User();

			// exact attribute names form the database
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setGender(rs.getString("gender"));
			user.setCity(rs.getString("city"));

			return user;
		}

	}

}
