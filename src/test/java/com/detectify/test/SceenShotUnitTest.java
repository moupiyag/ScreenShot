package com.detectify.test;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.detectify.service.ScreenShotService;

/**
 * @author Moupiya
 *
 */
public class SceenShotUnitTest {
	
	private ScreenShotService screenShotService = null;
	
	@BeforeClass
	public void setupInitialContext() throws Exception
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		screenShotService = (ScreenShotService)context.getBean("screenShotService");
	}
	
	@Before
    public void setupInitialDataBeforeEachTest() throws Exception {
    }
	
	@Test
    public void accountBalancesUpdatedAfterTransfer() {
    }

}
