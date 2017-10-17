package com.detectify.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.detectify.dao.ScreenShotDao;
import com.detectify.util.ScreenShotUtility;


public class ScreenShotServiceImpl implements ScreenShotService {
	
	private String screenShotTargetDir;
	SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMdd_hhmmss.SSS");
	private ScreenShotDao screenShotDao;
	

	public String getScreenShotTargetDir() {
		return screenShotTargetDir;
	}
	
	public void setScreenShotTargetDir(String screenShotTargetDir) {
		this.screenShotTargetDir = screenShotTargetDir;
	}

	public void setScreenShotDao(ScreenShotDao screenShotDao) {
		this.screenShotDao = screenShotDao;
	}

	@Override
	public List<File> takeScreenShots(List<String> urls) throws IOException {
		List<File> screenShots = new ArrayList<File>();
	    final WebDriver driver = new FirefoxDriver();
	    
	    try
	    {
	    	for(String url: urls)
		    {
	    		if(!StringUtils.isEmpty(url))
	    			screenShots.add(takeScreenshot(driver,url));
		    }
	    }catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			if(driver != null)
				driver.close();
		}
	    
		return screenShots;
	}
	
	@Override
	public List<File> takeScreenShots(File fileWithUrls) {
		List<File> screenShots = null;
		final WebDriver driver = new FirefoxDriver();
		FileReader fis = null;
		BufferedReader br = null;
		
		try {
			fis = new FileReader(fileWithUrls);
			br = new BufferedReader(fis);
					
			screenShots = new ArrayList<File>();
			
			String url = null;
			while((url = br.readLine()) != null && !StringUtils.isEmpty(url))
			{
				screenShots.add(takeScreenshot(driver, url));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally
		{
			try{
				if(fis != null)
					fis.close();
				if(br != null)
					br.close();
				if(driver != null)
					driver.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return screenShots;
	}
	
	private File takeScreenshot(WebDriver driver,String url) throws IOException,WebDriverException
	{
		final File screenShot = createUniqueFile();
		System.out.println("Opening page: "+ url);
		driver.get(url);
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

	@Override
	public List<File> searchScreenShotsByUrl(String url) {
		
		List<String> screenShotPaths = screenShotDao.getScreenShotPaths(url);
		return ScreenShotUtility.getScreenShotsByPaths(screenShotPaths);
		
	}

	@Override
	public List<File> searchScreenShotsByDate(String date, String dateFormat) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date requestDate = format.parse(date);
		List<String> screenShotPaths = screenShotDao.getScreenShotPaths(requestDate);
		return ScreenShotUtility.getScreenShotsByPaths(screenShotPaths);
	}
	
	@Override
	public List<File> searchScreenShotsByUrlAndDate(String url, String date, String dateFormat) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date requestDate = format.parse(date);
		List<String> screenShotPaths = screenShotDao.getScreenShotPaths(url, requestDate);
		return ScreenShotUtility.getScreenShotsByPaths(screenShotPaths);
	}

	@Override
	public List<File> searchScreenShotsByDateRange(String startDate, String endDate, String dateFormat)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date requestStartDate = format.parse(startDate);
		Date requestEndDate = format.parse(endDate);
		List<String> screenShotPaths = screenShotDao.getScreenShotPaths(requestStartDate, requestEndDate);
		return ScreenShotUtility.getScreenShotsByPaths(screenShotPaths);
	}

}
