package com.Beta1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.PublicSupportTest.Assertion;
import com.PublicSupportTest.PublicOperation;
import com.PublicSupportTest.Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TestCase {
	
	
	 static SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 登录  case# 67238
	 * @param wd WebDriver对象
	 * @param account 用户名/密码"testwy01", "123456wy"
	 */
	public static void logIn(WebDriver wd,String[][] site,String[] account) throws Exception {  //67238/67237/67236 
		
		wd.get(site[0][0]);
		
		if(wd.findElement(By.xpath("//div[@class='u-location-cont oh']")).isDisplayed()){   //检查地区选择框是否出现
			wd.findElement(By.xpath("//i[@class='J_close_distpop']")).click();
			Tools.waitForElementInvisible(wd, By.xpath("//div[@class='u-location-cont oh']"), 10);
		}
		
		wd.findElement(By.xpath("//div[@class='dt J_login_status']/a[@class='login J_do_login J_chgurl']")).click();  //点击登录
		
		if(PublicOperation.logIn(wd, account)){   //testcase 67238结束
			Tools.log("testcase 67238 success");
		}else {
			Tools.log("testcase 67238 failed");
		}
	}
	
	
	/**
	 * 不同页面查看登录情况  case# /67237/67236
	 * @param wd WebDriver对象
	 * @param url 测试地址
	 * @param testcase 测试案例ID
	 */
	public static void checkAccount1(WebDriver wd,String[][] urltest,String account)throws Exception{
		
		List<String> errortime = new ArrayList<String>();
		By by = By.linkText(account);
		
		for(int i=0;i<2;i++){
			if("null".equals(urltest[i][0])){
				continue;
			}
			wd.get(urltest[i][0]);
			Tools.waitPageLoad(wd);
			if(Tools.isExist(wd, by)){
				Tools.log("testcase "+urltest[i][1]+" success");
			}else {
				Tools.log("BUG# "+urltest[1]+" No Account LogIn");
				errortime.add(urltest[i][0]);
			}
		}
		if(!errortime.isEmpty()){
			wd.get(errortime.get(0));
			Tools.waitPageLoad(wd);
			WebElement name = wd.findElement(by);
			Tools.log(name.getText());
		}
	}
	
	/**
	 * 不同页面查看登录情况  case# "67235","67234","67233","67232","67231","67230","67229","67228","67227",
	 * @param wd WebDriver对象
	 * @param url 测试地址
	 * @param testcase 测试案例ID
	 */
	public static void checkAccount2(WebDriver wd,String[][] urltest,String account)throws Exception{
		
		List<String> errortime = new ArrayList<String>();
		
		for(int i=2;i<urltest.length;i++){
			if("null".equals(urltest[i][0])){
				continue;
			}
			wd.get(urltest[i][0]);
			Tools.waitPageLoad(wd);
			Tools tls = new Tools(); //判断有没有出现活动结束时的对话框
			if(tls.isAlertPresent(wd)){
				Tools.log("BUG# "+urltest[i][1]+" There was a problem running,Maybe activity end");
				errortime.add(urltest[i][0]);
				wd.switchTo().alert().accept();
				continue;
			}
			Tools.waitPageLoad(wd);
			String name = wd.findElement(By.id("wellcome_wording")).getText();
			Assertion.verifyEquals(name,account,"BUG# "+urltest[1]+" No Account LogIn");	
		}
		if(!errortime.isEmpty()){
			wd.get(errortime.get(0));
			Tools.waitPageLoad(wd);
			wd.findElement(By.id("wellcome_wording")).getText();
		}
	}
	
	/**
	 * 产品页加入购物车  case# 67226
	 * @param wd WebDriver对象
	 * @param site 商详页
	 */
	public static void proAddCart(WebDriver wd,String[][] site ,String[][] area)throws Exception{
		PublicOperation po = new PublicOperation();
		po.proAddCartBeta(wd, site, area);
	}
	

	/**
	 * 搜索页加入购物车  case# 67225,67224
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param linkid 加入购物车ID
	 */
	public static void searchAddCart(WebDriver wd,String[][] site)throws Exception{
		PublicOperation po = new PublicOperation();
		po.searchAddCartBeta(wd,site);
	}
	
	/**
	 * 馆页点击购入购物车按钮  case# 67223,67222
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param linkid 加入购物车ID
	 */
	public static void guanAddCart(WebDriver wd,String[][] site,String[] guan)throws Exception{
		PublicOperation po = new PublicOperation();
		po.guanAddCartBeta(wd, site, guan[0], 0, 15);
		po.guanAddCartBeta(wd, site, guan[1], 15, 29);
		po.guanAddCartBeta(wd, site, guan[2], 29, 42);
		po.guanAddCartBeta(wd, site, guan[3], 42, 56);
	}
	
	
	/**
	 * 聚合页加入购物车，查看活动购物车  case# 67221/67220
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param linkid 加入购物车ID
	 */
	public static void juheAddCart(WebDriver wd,String[][] site,String[] huodong)throws Exception{
		PublicOperation po = new PublicOperation();
		po.juheAddCartBeta(wd, site, huodong[0], 0, 15);
		po.juheAddCartBeta(wd, site, huodong[1], 15, 29);
		po.juheAddCartBeta(wd, site, huodong[2], 29, 42);
		po.juheAddCartBeta(wd, site, huodong[3], 42, 56);
	}
	
	

	/**
	 * 购物车结算页面地址选择上海和新疆  case# 67219,67218,67217
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 */
	public static void cartCheckAddress(WebDriver wd,String[][] site,String[][] area)throws Exception{
		
		int numb1;
		By by = By.xpath("//dl[@class='sevice']/dd/a");	//这一步很重要。。全屏的时候抓不到元素，需要移动屏幕
		PublicOperation po = new PublicOperation();
		WebElement addbutton;
		
		wd.get(site[0][0]);
		Thread.sleep(1000);
		
		numb1 = Integer.valueOf(wd.findElement(By.xpath("//span[@class='count cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		if(0<numb1){
			PublicOperation.deleteAllProductsBeta(wd);
			Thread.sleep(1000);
		}
		
		wd.get(site[0][15]);
		Thread.sleep(1000);
		Tools.scrollToElement(wd, by);
		po.changeAddress(wd,area[0][1],area[0][2],area[0][3],area[0][4],area[0][5],area[0][6]);
		Thread.sleep(1000);
		addbutton = wd.findElement(By.xpath("//a[@id='btnAddCart']"));   //添加生鲜产品
		addbutton.click();
		
		wd.get("http://buy.beta1.fn/cart/index");//进入购物车界面
		Thread.sleep(1000);
		Actions mouse = new Actions(wd);
		WebElement target = wd.findElement(By.xpath("//div[@class='fn-cart-clearing']/div/div[@class='cart-title']/div[@class='ct-bottom']/div/div/span/div"));
		mouse.moveToElement(target).build().perform();  //悬停在地址菜单
		
		wd.findElement(By.xpath("//*[@id=\"selectCar\"]")).click();

		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tab']/a[1]")).click();
		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tabCont clearfix']/div[1]/ul/li[29]/a")).click();  //新疆

		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tab']/a[2]")).click();
		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tabCont clearfix']/div[2]/ul/li[1]/a")).click();  //阿勒泰地区

		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tab']/a[3]")).click();
		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tabCont clearfix']/div[3]/ul/li[1]/a")).click();  //北屯市
		Thread.sleep(5000);

		if(wd.getPageSource().contains("商品不在可销售范围内")){//67219验证测试是否成功
			Tools.log("testcase 67219 success");
		}else Tools.log("testcase 67219 failed");
		
		
		wd.findElement(By.xpath("//*[@id=\"selectCar\"]")).click();

		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tab']/a[1]")).click();
		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tabCont clearfix']/div[1]/ul/li[23]/a")).click();  //上海

		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tab']/a[2]")).click();
		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tabCont clearfix']/div[2]/ul/li/a")).click();  //上海

		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tab']/a[3]")).click();
		wd.findElement(By.xpath("//*[@id=\"selectCar\"]/div[@class='proarea clearfix']/div[@class='_tabCont clearfix']/div[3]/ul/li[6]/a")).click();  //上海
		Thread.sleep(5000);

		if(!wd.getPageSource().contains("商品不在可销售范围内")){//67218验证测试是否成功
			Tools.log("testcase 67218 success");
		}else Tools.log("testcase 67218 failed");
		
		WebElement but = wd.findElement(By.xpath("//input[@id='qx-1']"));
        if(!but.isSelected()){
            but.click();  //全选购物车中产品
            Tools.WaitUtilSelected(wd,By.xpath("//*[@id=\"qx-1\"]"));  //等待全选按钮生效
            Thread.sleep(2000);
        }
        
        String money = null;   
        String mon1 = wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div/div/div[3]/div[2]/ul/li[5]/dl/dd[1]/span")).getText();  //购物车中产品金额
        if("0".equals(mon1.substring(mon1.length()-2, mon1.length()-1))){   //判断价钱是否有小数点
        	money = Tools.catchnumber(mon1).substring(0,mon1.length()-3);   //处理取出的金额后面的00删除，还有取出小数点
        }else{
        	money = mon1;
        }
        
        wd.findElement(By.className("clearing-total2")).click();  //购物车中点击结算
        
        WebElement pay = wd.findElement(By.xpath("//span[@class='pay-price']"));  //获取结算页面的金额
        String mon2 = pay.getText();
        Assertion.verifyEquals(money,mon2);  //验证结算页面的订单金额   67217验证测试是否成功
        
        PublicOperation.deleteAllProductsBeta(wd);  //测试完成后删除数据
	}
	
	
	/**
	 * 订单提交，查看完成页面，退货取消订单，查看积分+优惠券的显示  case# 67216,67215,67214,67213,65938
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param testproduct 线上测试产品
	 * @param address 用户地址ID
	 */
	public static void orderAndAccount(WebDriver wd,String[][] site,String[] address)throws Exception{
		
		PublicOperation po = new PublicOperation();
		wd.get(site[0][0]);
		Thread.sleep(1000);
		
		/**************记录付款前的优惠券数量和积分**************/
		PublicOperation po1 = new PublicOperation();
		List<Integer> num1 = po1.getJifenBeta(wd);
		
		int diyongquan1 = num1.get(0);
//		int gouwujin1 = num1.get(0);
		int jifen1 = num1.get(1);
		Tools.log("Init："+diyongquan1+"|"+jifen1);
		
		String ordernumber = po.payOrderCouponBeta(wd,site,address[0]);  //订单支付
		
		/**************记录付款后的优惠券数量和积分**************/
		PublicOperation po2 = new PublicOperation();
		List<Integer> num2 = po2.getJifenBeta(wd);
		
		int diyongquan2 = num2.get(0);
//		int gouwujin2 = num2.get(0);
		int jifen2 = num2.get(1);
		Tools.log("Pay:"+diyongquan2+"|"+jifen2);
		Assertion.verifyEquals(diyongquan2,diyongquan1-1,"BUG:Compare the vouchers error before than pay the order!");
		Assertion.verifyEquals(jifen2,jifen1-1,"BUG:Compare the integral error before than pay the order!");
		
		PublicOperation.cancelOrderBeta(wd,site,ordernumber);
		
		Tools.log(time.format(new Date()).toString()+"  test complete!");
		
		/*
		
		Tools.log(time.format(new Date()).toString()+"  Please wait for 30 minutes！");
		Thread.sleep(2000000); //等待三十分钟
		*//**************记录取消订单后的优惠券数量和积分**************//*
		PublicOperation po3 = new PublicOperation();
		List<Integer> num3 = po3.getJifenBeta(wd);
		
		int diyongquan3 = num3.get(0);
//		int gouwujin3 = num3.get(0);
		int jifen3 = num3.get(1);
		Tools.log("Cancel:"+diyongquan3+"|"+jifen3);
		Assertion.verifyEquals(diyongquan1,diyongquan3,"BUG:Compare the vouchers error before than cancel the order!");
//		Assertion.verifyEquals(jifen1,jifen3,"BUG:Compare the integral error before than cancel the order!");
		Assertion.verifyEquals(jifen1,jifen3,"BUG:Compare the integral error before than cancel the order!");
		*/
	}
	
}
