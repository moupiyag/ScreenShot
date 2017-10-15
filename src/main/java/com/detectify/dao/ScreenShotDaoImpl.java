package com.detectify.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


/**
 * Provides implementation of interface AccountDao 
 * for data access operations on database table account
 * 
 * @author Moupiya
 *
 */
public class ScreenShotDaoImpl extends JdbcDaoSupport implements ScreenShotDao {

	@Override
	public void storeScreenShot(final String url, final String screenShotPath) {
		String sql = "INSERT INTO screenshot_request (url, screenshot_path, create_date) VALUES (?, ?, NOW())";
        getJdbcTemplate().update(sql, url, screenShotPath);
	}
	
	@Override
	public List<String> getScreenShotPaths(final String url) {
		String sql = "SELECT screenshot_path FROM screenshot_request WHERE url = ?";
		List<String> screenShotPaths = getJdbcTemplate().query(sql,  new PathRowMapper(), url);
		
		return screenShotPaths;
	}
	
	@Override
	public List<String> getScreenShotPaths(final Date date) {
		String sql = "SELECT screenshot_path FROM screenshot_request WHERE create_date = ?";
		List<String> screenShotPaths = getJdbcTemplate().query(sql,  new PathRowMapper(), date);
		
		return screenShotPaths;
	}
	
	@Override
	public List<String> getScreenShotPaths(final String url, final Date date) {
		String sql = "SELECT screenshot_path FROM screenshot_request WHERE url = ? AND create_date = ?";
		List<String> screenShotPaths = getJdbcTemplate().query(sql, new PathRowMapper(), url, date);
		
		return screenShotPaths;
	}
	
	@Override
	public List<String> getScreenShotPaths(final Date startDate, final Date endDate) {
		String sql = "SELECT screenshot_path FROM screenshot_request WHERE create_date BETWEEN ? AND ?";
		List<String> screenShotPaths = getJdbcTemplate().query(sql, new PathRowMapper(), startDate, endDate);
		
		return screenShotPaths;
	}

	
	class PathRowMapper implements RowMapper<String>
	{

		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString(1);
		}
		
	}

}
