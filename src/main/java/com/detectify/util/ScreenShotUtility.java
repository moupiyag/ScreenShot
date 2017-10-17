/**
 * 
 */
package com.detectify.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

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
	
	public static List<String> getUrlsFromFile(File file)
	{
		List<String> urls = new ArrayList<String>();
		try {
			FileReader fis = new FileReader(file);
			BufferedReader br = new BufferedReader(fis);
			
			String url = null;
			while((url = br.readLine()) != null && !StringUtils.isEmpty(url)) {
				urls.add(url);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return urls;
	}

}
