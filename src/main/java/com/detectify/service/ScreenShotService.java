/**
 * 
 */
package com.detectify.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Moupiya
 *
 */
public interface ScreenShotService {

	List<File> takeScreenShots(String urls) throws IOException;
	
	List<File> takeScreenShots(File fileWithUrls);
	
	List<File> searchScreenShots(String url);
	
	List<File> searchScreenShots(File fileWithUrls);
	
}
