/**
 * 
 */
package com.detectify.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Moupiya
 *
 */
public class ScreenShotUtility {
	
	public static List<File> getScreenShotsByPaths(List<String> paths)
	{
		if(paths.size() == 0)
			return null;
		
		List<File> screenShots = new ArrayList<File>();
		for(String path : paths)
		{
			screenShots.add(new File(path));
		}
		return screenShots;
	}
}
