package com.myapp.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myapp.beans.User;

@Repository
public class UserDaoImpl implements UserDao{

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
		System.out.println("Dao Validate User");
		if(checkPasswordMatch(user))
		{
			return true;
		}
		return false;
	}

	private boolean checkPasswordMatch(User user) {
		String sql = "SELECT email FROM user WHERE email = ':email' AND password =':password'";
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("email", user.getEmail());
		parameters.put("password", user.getPassword());
		try
		{
			return jdbcTemplate.queryForObject(sql, parameters, String.class) != null;
		}
		catch(DataAccessException e){
			System.out.println(user.getEmail()+"user invalid");
			return false;
		}
	}

	@Override
	public int insertUser(User user) {
		String sql = "INSERT INTO "
				+ "user "
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
		
		return jdbcTemplate.update(sql,parameters);
	}


	
}
