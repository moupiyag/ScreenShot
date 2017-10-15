package com.detectify.dao;

import java.util.Date;
import java.util.List;

/**
 * Interface for data access operations on database table screenshot_history
 * 
 * @author Moupiya
 *
 */
public interface ScreenShotDao {

	public void storeScreenShot(String url, String screenShotPath);
	
	public List<String> getScreenShotPaths(String url);
	
	public List<String> getScreenShotPaths(Date date);
	
	public List<String> getScreenShotPaths(String url, Date date);
	
	public List<String> getScreenShotPaths(Date startDate, Date endDate);
	
}
