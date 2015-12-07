package com.Beta1;

import org.testng.annotations.Test;
import com.TestBase;
import com.PublicSupportTest.Tools;

public class BetaTest extends TestBase {
	
	String[][] site = Tools.xls2Array2(betafile,1,8,56);
	String[][] account = Tools.xls2Array(betafile, 0, 1, 3);
	String[] address = Tools.xls2Array1(betafile,2,2,1);
	String[][] area =  Tools.xls2Array(betafile, 6, 17, 8);
	String[][] urltest = Tools.xls2Array(betafile, 3, 11, 2);
	String[] huodong = Tools.xls2Array1(betafile,4,4,1);
	String[] guan = Tools.xls2Array1(betafile,5,4,1);
	 	
	 	String jifen = null;

//	    @Parameters({ "test" })
	    
	    @Test(priority=1) //67238
	    public void logIn67238()throws Exception{
	        TestCase.logIn(driver,site,account[0]);
	    }

	  //67237/67236/67235/67234/67233/67232/67231/67230/67229/67228/67227
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount1()throws Exception{		//67237/67236
	    	TestCase.checkAccount1(driver, urltest,account[0][2]);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=2)  
	    public void checkAccount2()throws Exception{		//67235/67234/67233/67232/67231/67230/67229/67228/67227
	    	TestCase.checkAccount2(driver, urltest,account[0][2]);
	    }
	    

	    @Test(dependsOnMethods = { "logIn67238" },priority=3)  //67219,67218,67217  产看购物车新疆上海地址，需要生鲜
		public void cartCheckAddress()throws Exception{
			TestCase.cartCheckAddress(driver, site, area);
		}
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=4) 	//67226  商详页添加
	    public void proAddCart67226()throws Exception{
	    	TestCase.proAddCart(driver, site, area);
	    }
	    
	    @Test(dependsOnMethods = { "logIn67238" },priority=5) //67225,67224   搜索页添加
	    public void searchAddCart()throws Exception{
	    	TestCase.searchAddCart(driver, site);
	    }
	    
		
		@Test(dependsOnMethods = { "logIn67238" },priority=6)  //67223,67222  馆页添加
		public void guanAddCart()throws Exception{
			TestCase.guanAddCart(driver, site, guan);
		}
		
		
		@Test(dependsOnMethods = { "logIn67238" },priority=8)  //67221,67220  聚合页添加
		public void juheAddCart()throws Exception{
			TestCase.juheAddCart(driver, site, huodong);
		}
		
		
		@Test(dependsOnMethods = { "logIn67238" },priority=10)  //67216,67215,67214,67213,65938  下定和取消订单
		public void orderAndAccount()throws Exception{
			TestCase.orderAndAccount(driver, site, address);
		}
		
}
