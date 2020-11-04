package com.bankingV1.testcase;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.bankingV1.pageobjects.AddCustomer;
import com.bankingV1.pageobjects.Loginpage;

public class TC_AddCustomer_003 extends BaseClass {


	@Test
	public void addCustomer() throws Exception {
		Loginpage lp = new Loginpage(driver);
		lp.setUserName(username);
		lp.setPassword(password);
		lp.ClickSubmit();
		Thread.sleep(3000);


		AddCustomer addcust = new AddCustomer(driver);

		addcust.clickAddNewCustomer();

		logger.info("providing customer details....");


		addcust.custName("Pavan");
		addcust.custgender("male");
		addcust.custdob("10","15","1985");
		Thread.sleep(5000);
		addcust.custaddress("INDIA");
		addcust.custcity("HYD");
		addcust.custstate("AP");
		addcust.custpinno("5000074");
		addcust.custtelephoneno("987890091");

		String email="ranjith@gmail.com"; //randomestring()+
		addcust.custemailid(email);
		addcust.custpassword("abcdef");
		addcust.custsubmit();

		Thread.sleep(3000);
		
		boolean res = driver.getPageSource().contains("Customer Registered Successfully");
		
		if(res == true) {
			Assert.assertTrue(true);
				
		}else {
			captureScreen(driver, "addNewCustomer");
			Assert.assertTrue(false);
			
		}
	}

}
