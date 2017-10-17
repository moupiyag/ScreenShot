/**
 * 
 */
package com.detectify.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.detectify.util.ContextProvider;

/**
 * @author Moupiya
 *
 */
public class ScreenShotServiceProvider {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		ScreenShotService screenShotService = ContextProvider.getBean("screenShotService");
		try
		{
			if ("takeScreenShotByUrl".equalsIgnoreCase(args[0]))
			{
				printFileName(screenShotService.takeScreenShots(Arrays.asList(args[1])));
			}
			else if("takeScreenShotByFile".equalsIgnoreCase(args[0]))
			{
				printFileName(screenShotService.takeScreenShots(new File(args[1])));
			}
			else if ("searchScreenShotByUrl".equalsIgnoreCase(args[0]))
			{
				printFileName(screenShotService.searchScreenShotsByUrl(args[1]));
			}
			else if ("searchScreenShotByDate".equalsIgnoreCase(args[0]))
			{
				printFileName(screenShotService.searchScreenShotsByDate(args[1], args[2]));
			}
			else if ("searchScreenShotByUrlAndDate".equalsIgnoreCase(args[0]))
			{
				printFileName(screenShotService.searchScreenShotsByUrlAndDate(args[1], args[2], args[3]));
			}
			else if ("searchScreenShotsByDateRange".equalsIgnoreCase(args[0]))
			{
				printFileName(screenShotService.searchScreenShotsByDateRange(args[1], args[2], args[3]));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printFileName(List<File> files)
	{
		System.out.println("File names : ");
		for(File file : files)
		{
			System.out.println(file.getAbsolutePath());
		}
	}

}
