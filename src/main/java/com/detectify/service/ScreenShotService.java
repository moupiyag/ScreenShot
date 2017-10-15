/**
 * 
 */
package com.detectify.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author Moupiya
 *
 */
public interface ScreenShotService {

	List<File> takeScreenShots(String urls) throws IOException;
	
	List<File> takeScreenShots(File fileWithUrls);
	
	List<File> searchScreenShotsByUrl(String url);
	
	List<File> searchScreenShotsByDate(String date, String dateFormat) throws ParseException;
	
	List<File> searchScreenShotsByUrlAndDate(String url, String date, String dateFormat) throws ParseException;
	
	List<File> searchScreenShotsByDateRange(String startDate, String endDate, String dateFormat) throws ParseException;
	
}
