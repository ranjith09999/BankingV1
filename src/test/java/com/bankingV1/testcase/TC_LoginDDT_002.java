package com.bankingV1.testcase;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bankingV1.pageobjects.Loginpage;
import com.bankingV1.utils.XLUtils;

public class TC_LoginDDT_002 extends BaseClass {

	@Test(dataProvider="LoginData")
	public void loginDDT(String user,String pwd) {
		
		Loginpage lp = new Loginpage(driver);
		lp.setUserName(user);
		logger.info("user name provided");
		lp.setPassword(pwd);
		logger.info("password provided");
		lp.ClickSubmit();
		logger.info("Login button clicked");
		
		if(isAlertPresent()==true) {
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			Assert.assertTrue(false);
			logger.warn("Login Failed");
			
		}else {
			Assert.assertTrue(true);
			lp.ClickLogout();
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			logger.info("Logout");
		}
	}
	
	public boolean isAlertPresent() { //user defined method created to check alert is present or not
		try {
			
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
		
		
	}
	
	
	
	
	@DataProvider(name="LoginData")
	String [][] getData() throws IOException
	{
		String path = System.getProperty("user.dir")+"/src/test/java/com/bankingV1/testdata/LoginData.xlsx";
		
		int rownum=XLUtils.getRowCount(path, "Sheet1");
		int cocount = XLUtils.getCellCount(path, "Sheet1", 1);
		
		String logindata[][] = new String[rownum][cocount];
		
		for(int i=1;i<rownum;i++) {
			for(int j=0;j<cocount;j++) {
				
				logindata[i-1][j]=XLUtils.getCellData(path, "sheet1", i, j);
				
			}
		}
		return logindata;		
	}
}
