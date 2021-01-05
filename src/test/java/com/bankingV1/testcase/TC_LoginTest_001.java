package com.bankingV1.testcase;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.bankingV1.pageobjects.Loginpage;

public class TC_LoginTest_001 extends BaseClass {

	@Test
	public void loginTest() throws Exception {

		
		logger.info("Url is open");

		Loginpage lp = new Loginpage(driver);
		

		lp.setUserName(username);
		logger.info("Entered Username");
		lp.setPassword(password);
		logger.info("Entered Password");
		lp.ClickSubmit();
		logger.info("Button clicked");

		if(driver.getTitle().equals(Title)){

			logger.info("Testcase passed");
			Assert.assertTrue(true);
			

		} else{
			captureScreen(driver, "loginTest");
			logger.error("Testcase failed");
			Assert.assertTrue(false);
			
		}

	}


}
