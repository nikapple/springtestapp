package com.myapp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myapp.beans.Book;
import com.myapp.beans.User;

@Repository
public class UserDaoImpl implements UserDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean validateUser(User user) {
		if (checkPasswordMatch(user)) {
			return true;
		}
		return false;
	}

	private boolean checkPasswordMatch(User user) {
		String sql = "SELECT email FROM user WHERE email = :email AND password =:password";
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters.put("email", user.getEmail());
			parameters.put("password", user.getPassword());
			String email = jdbcTemplate.queryForObject(sql, parameters,
					String.class);
			return email != null;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	@Override
	public int insertUser(User user) {
		String sql = "INSERT INTO " + "user "
				+ "(email,username,first_name,last_name,phone,password)"
				+ "VALUES"
				+ "(:email,:username,:first_name,:last_name,:phone,:password)";

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("email", user.getEmail());
		parameters.put("username", user.getUsername());
		parameters.put("first_name", user.getFirstName());
		parameters.put("last_name", user.getLastName());
		parameters.put("phone", user.getPhone());
		parameters.put("password", user.getPassword());

		return jdbcTemplate.update(sql, parameters);
	}

	@Override
	public boolean checkUserExists(User user) {
		String sql = "SELECT email FROM user WHERE email = :email";
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters.put("email", user.getEmail());
			String email = jdbcTemplate.queryForObject(sql, parameters,
					String.class);
			return email != null;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	@Override
	public User getUserInfo(User user) {
		String sql = "SELECT email,first_name,last_name,phone,username FROM user WHERE email = :email";
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters.put("email", user.getEmail());
			user = jdbcTemplate.queryForObject(
					sql,
					parameters,
					(rs, rowNum) -> new User(rs.getString("username"), rs
							.getString("email"), rs.getString("first_name"), rs
							.getString("last_name"), rs.getString("phone")));
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Book> getAssignedBooks(User user) {
		String sql = "SELECT t.name as topic, b.name as bookName, b.author as author FROM activity a,book b ,topic t, user u WHERE a.book_id=b.id AND b.topic_id=t.id AND a.user_id=u.id AND u.username=:username";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", user.getUsername());
		List<Book> bookList = new ArrayList<>();

		List<Map<String,Object>> rows = getJdbcTemplate().queryForList(sql, parameters);
		for (Map<String,Object> row : rows) {
			Book book = new Book();
			book.setTopic((String)(row.get("topic")));
			book.setName((String)row.get("bookName"));
			book.setAuthor((String)row.get("author"));
			bookList.add(book);
		}
		return bookList;
	}

}
