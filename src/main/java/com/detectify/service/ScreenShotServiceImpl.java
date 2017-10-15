package com.detectify.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.detectify.dao.ScreenShotDao;


public class ScreenShotServiceImpl implements ScreenShotService {
	
	private static final String screenShotTargetDir = "C:/Users/Moupiya/Documents/Habijabi/";
	SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMdd_hhmmss.SSS");
	
	private ScreenShotDao screenShotDao;

	public void setScreenShotDao(ScreenShotDao screenShotDao) {
		this.screenShotDao = screenShotDao;
	}

	public List<File> takeScreenShots(String urls) throws IOException {
		String[] urlList = StringUtils.split(urls, ";");
		List<File> screenShots = new ArrayList<File>();
	    final WebDriver driver = new FirefoxDriver();
	    
    	for(String url: urlList)
	    {
    		try{
    			screenShots.add(takeScreenshot(driver,url));
    		}
    		catch(Exception e){
    			driver.close();
    			e.printStackTrace();
    		}
	    }

		return screenShots;
	}
	
	private File takeScreenshot(WebDriver driver,String url) throws IOException
	{
		final File screenShot = createUniqueFile();
		System.out.println("Opening page: "+ url);
		driver.get(url);
		System.out.println("Waiting for page to load: "+ url);
  	    try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Taking Screenshot");
		final byte[] outputData = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		try {
			FileUtils.writeByteArrayToFile(screenShot, outputData);
			screenShotDao.storeScreenShot(url, screenShot.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Screenshot saved: "+ url);
		
		return screenShot;
	}

	private File createUniqueFile() throws IOException {
		
		Date date = new Date();
		Random random = new Random();
		String fileName = screenShotTargetDir + fileNameFormat.format(date) + random.nextInt()+".jpg";
		File file = new File(fileName);
		while(!file.createNewFile())
		{
			createUniqueFile();
		}
		
		return file;
	}

	public List<File> takeScreenShots(File fileWithUrls) {
		try(FileReader fis = new FileReader(fileWithUrls);
			BufferedReader br = new BufferedReader(fis)) {
			
			final WebDriver driver = new FirefoxDriver();
			List<File> screenShots = new ArrayList<File>();
			
			String url = null;
			while((url = br.readLine()) != null)
			{
				screenShots.add(takeScreenshot(driver, url));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public List<File> searchScreenShots(String url) {
		
		List<String> screenShotPaths = screenShotDao.getScreenShotPaths(url);
		
	}

	public List<File> searchScreenShots(File fileWithUrls) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String args[])
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ScreenShotService screenShotService = (ScreenShotService)context.getBean("screenShotService");
		try
		{
			if ("takeScreenShotByUrl".equalsIgnoreCase(args[0]))
			{
				screenShotService.takeScreenShots(args[1]);
			}
			else if("takeScreenShotByFile".equalsIgnoreCase(args[0]))
			{
				screenShotService.takeScreenShots(new File(args[1]));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
