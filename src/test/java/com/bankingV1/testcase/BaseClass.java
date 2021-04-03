package com.bankingV1.testcase;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.bankingV1.utils.ReadConfig;

public class BaseClass {

	ReadConfig readconfig = new ReadConfig();
	public String baseURL = readconfig.getApplicationURL();
	public String username = readconfig.getUsername();
	public String password = readconfig.getPassword();
	public String Title = readconfig.verifyTitle();
	public static WebDriver driver;
	public static Logger logger;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br)
	{
		logger = Logger.getLogger("ebanking");
		PropertyConfigurator.configure("log4j.properties");

		if(br.equals("chrome")) {

			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			driver = new ChromeDriver();
		}else if(br.equals("firefox")){

			System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
			driver = new FirefoxDriver();

		}else if(br.equals("ie")){

			System.setProperty("webdriver.ie.driver", "C:\\Users\\rstephen4\\eclipse-workspace\\BankingV1\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get(baseURL);
	}



	@AfterClass
	public void tearDown() {
		driver.close();
	}

	public void captureScreen(WebDriver driver, String tname) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}
	
public void Emailmethod(String tname) {
	try {
		

		final String username = "rstephen@dxc.com";
		final String Password = "131522@Ran";
		String BccRecipient = "ranjith.stephen@umusic.com";
		String From = "rstephen@dxc.com";
		Properties prop = System.getProperties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.office365.com");
		prop.put("mail.smtp.port", 587);
		
//		StringWriter writer = new StringWriter();
//		IOUtils.copy(new FileInputStream(new File("./ScreenShot/Test-Report.html")), writer);

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,Password);

			}
		});

		
		MimeMessage msg = new MimeMessage (session);
		msg.setFrom(new InternetAddress(From));
		msg.addRecipient(Message.RecipientType.BCC,new InternetAddress(BccRecipient));
		msg.setSubject("** ELS Smoke Test Report ** - Production");
		MimeMultipart multipart = new MimeMultipart("related");
		BodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = "<img src=\"cid:image\">";
		messageBodyPart.setContent(htmlText, "text/html");
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();
		DataSource fds = new FileDataSource(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID", "<image>");
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		Transport.send(msg);
		System.out.println("Email Sending...........");
		System.out.println("Done");
	} catch (MessagingException e) {
		e.printStackTrace();
	}
}
}
//	public String randomestring() {
//
//		String Gen = RandomStringUtils.randomAlphabetic(8);
//		return(Gen);
//	}
//	public String randomeNum() {
//
//		String Gen2 = RandomStringUtils.randomNumeric(4);
//		return(Gen2);
//	}

