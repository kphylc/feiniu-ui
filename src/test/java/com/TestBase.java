package com;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.uncommons.reportng.HTMLReporter;
import org.uncommons.reportng.JUnitXMLReporter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.PublicSupportTest.PublicOperation;
import com.PublicSupportTest.Tools;
import com.PublicSupportTest.Listener.BetaTestListener;

@Listeners({BetaTestListener.class,HTMLReporter.class,JUnitXMLReporter.class})
public class TestBase {
	
	protected static WebDriver driver;
//	protected File betafile = new File("D:/Workspaces/urldata_beta_bak.xls");
//	protected File onlinefile = new File("D:/Workspaces/urldata_online.xls");
	protected File betafile = readExcel("beta");
	protected File onlinefile = readExcel("online");
	
    public WebDriver getDriver() {
        return driver;
    }
    
    @BeforeSuite
    public void beforeSuite() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }
    
    @AfterSuite
    public void afterSuite() {
    	driver.close();
        driver.quit();
    }
 
    @BeforeClass
    public void setUp(){
    	
    }
    
    @AfterClass
    public void tearDown(){
    	
    }
    
    @BeforeMethod(enabled = false)
    public void add()throws Exception{
    	PublicOperation.addAllProducts(driver);
     }

    @AfterMethod(enabled = false)
    public void del()throws Exception{
    	PublicOperation.deleteAllProducts(driver);
    }
    
    private void takeScreenshot(String screenPath) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenPath));
			Tools.log(screenPath);
		} catch (IOException e) {
			Tools.log("Screen shot error: " + screenPath);
		}
	}
	
	public void takeScreenshot() {
		String screenName = String.valueOf(new Date().getTime()) + ".jpg";
		File dir = new File("test-output/snapshot");
		if (!dir.exists())
			dir.mkdirs();
		String screenPath = dir.getAbsolutePath() + "/" + screenName;
		this.takeScreenshot(screenPath);
	}
    
	private File readExcel(String st){
		File excel = null;
		if("beta".equals(st)){
			File f = new File("");
			String load = f.getAbsolutePath() + "/urldata_beta.xls";
			excel = new File(load);
		}else if("online".equals(st)){
			String load = System.getProperty("user.dir") + "/urldata_online.xls";
			excel = new File(load);
		}
		return excel;
	}
	  
}

