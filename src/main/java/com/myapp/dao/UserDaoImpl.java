package com.myapp.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myapp.beans.User;

@Repository
public class UserDaoImpl implements UserDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
    
	@Override
	public boolean validateUser(User user) {
		System.out.println("Dao Validate User");
		return false;
	}

	@Override
	public int insertUser(User user) {
		String sql = "INSERT INTO "
				+ "user "
				+ "(email,username,first_name,last_name,phone,password)"
				+ "VALUES"
				+ "('nikhi@ds.com','niask','sas','spa','2134124232','psswed')";
		jdbcTemplate.update(sql);
		return 0;
	}


	
}
