package com.tony;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.TestBase;
import com.PublicSupportTest.Tools;
import com.PublicSupportTest.Listener.PublicBasicTestListener;

@Listeners({PublicBasicTestListener.class})
public class PublicBasicTest extends TestBase{
	
		String testproduct = Tools.xls2Array1(onlinefile,3,1,1)[0];
		String[] address = Tools.xls2Array1(onlinefile,2,2,1);
	 	String[] site = Tools.xls2Array1(onlinefile,1,8,1);
	 	String[] linkid = Tools.xls2Array1(onlinefile, 4, 3, 1);
	 	String[] title = Tools.xls2Array1(onlinefile, 5, 1, 1);
	 	String[][] account = Tools.xls2Array(onlinefile, 0, 2, 2);
	 	String[][] urltest = Tools.xls2Array(onlinefile, 6, 11, 2);
	 	
	 	String jifen = null;
	 
//	    @Parameters({ "test" })
	    @Test(enabled = false,priority=0)//67239
	    public void caiBai()throws Exception{
	        TestCase.caiBai(driver,account[1],testproduct,site,address);
	    }
	    
	    @Test(priority=1) //67238
	    public void logIn67238()throws Exception{
	        TestCase.logIn(driver,site,account[0]);
	    }

	  //67237/67236/67235/67234/67233/67232/67231/67230/67229/67228/67227
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67237()throws Exception{		//67237
	    	TestCase.checkAccountMaster(driver, urltest[0],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void CheckAccount67236()throws Exception{		//67236
	    	TestCase.checkAccountMaster(driver, urltest[1],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67235()throws Exception{		//67235
	    	TestCase.checkAccountNode(driver, urltest[2],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67234()throws Exception{		//67234
	    	TestCase.checkAccountNode(driver, urltest[3],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67233()throws Exception{		//67233
	    	TestCase.checkAccountNode(driver, urltest[4],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67232()throws Exception{		//67232
	    	TestCase.checkAccountNode(driver, urltest[5],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67231()throws Exception{		//67231
	    	TestCase.checkAccountNode(driver, urltest[6],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67230()throws Exception{		//67230
	    	TestCase.checkAccountNode(driver, urltest[7],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2) 
	    public void checkAccount67229()throws Exception{		//67229
	    	TestCase.checkAccountNode(driver, urltest[8],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67228()throws Exception{		//67228
	    	TestCase.checkAccountNode(driver, urltest[9],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount67227()throws Exception{		//67227
	    	TestCase.checkAccountNode(driver, urltest[10],account[0][0]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=3) //67226
	    public void proAddCart67226()throws Exception{
	    	TestCase.proAddCart(driver, site);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=4) //67225
	    public void searchAddCart67225()throws Exception{
	    	TestCase.searchAddCart(driver, site,linkid);
	    }
	    
	    
		@Test(dependsOnMethods = { "logIn67238" },priority=5)  //67224   
		public void searchLookInfo67224()throws Exception{
			TestCase.searchLookInfo(driver, site, linkid, title);
		}
		
		@Test(dependsOnMethods = { "logIn67238" },priority=6)  //67223
		public void guanAddCart67223()throws Exception{
			TestCase.guanAddCart(driver, site, linkid);
		}
		
		@Test(dependsOnMethods = { "logIn67238" },priority=7)  //67222
		public void guanLookInfo67222()throws Exception{
			TestCase.guanLookInfo(driver, site, linkid, title);
		}
		
		@Test(dependsOnMethods = { "logIn67238" },priority=8)  //67221,67220
		public void juheAddCart()throws Exception{
			TestCase.juheAddCart(driver, site, linkid);
		}
		
		@Test(dependsOnMethods = { "logIn67238" },priority=9)  //67219,67218,67217
		public void cartCheckAddress()throws Exception{
			TestCase.cartCheckAddress(driver, site);
		}
		
		@Test(dependsOnMethods = { "logIn67238" },priority=10)  //67216,67215,67214,67213,65938
		public void orderAndAccount()throws Exception{
			TestCase.orderAndAccount(driver, site, testproduct, address);
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
}
