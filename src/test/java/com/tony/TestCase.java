package com.tony;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

import com.PublicSupportTest.Assertion;
import com.PublicSupportTest.PublicOperation;
import com.PublicSupportTest.Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestCase {
	
	
	/**
	 * 彩贝登录  case# 67239	
	 * @param wd WebDriver对象
	 * @param account 用户名/密码
	 */
	public static void caiBai(WebDriver wd,String[] account,String testproduct,String[] site,String[] address) throws Exception {   //67239
		
		/*彩贝网*/
		wd.get("http://fanli.qq.com/"); // 登录彩贝

		WebElement search = wd.findElement(By.id("mall_searchtext"));
//		search.sendKeys("飞牛网");
		Actions key = new Actions(wd);
		key.click(search).build().perform();
		key.sendKeys("feiniu").build().perform();
		Thread.sleep(1000);
		wd.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/ul/li[2]/div/form/div/div[2]/div[1]/div/ul/li/a/img")).click();
		
		String title1 = "飞牛网返利详情 | QQ彩贝返利商城";
		Tools.changeWindowtitle(wd, title1);
		Tools.xWindow(wd);
		wd.findElement(By.xpath("//a[@class='busi_buy']")).click();
		
		String title2 = "登录QQ彩贝";
		Tools.changeWindowtitle(wd, title2);
		Tools.xWindow(wd);
		Thread.sleep(1000);
		WebElement frame = wd.findElement(By.xpath("/html/body/div/iframe"));
		wd.switchTo().frame(frame);
		WebElement u = wd.findElement(By.xpath("//*[@id='u']"));
		WebElement p = wd.findElement(By.xpath("//*[@id='p']"));
		WebElement s = wd.findElement(By.xpath("//*[@id='login_button']"));
		u.sendKeys(account[0]);
		p.sendKeys(account[1]);
		s.submit();
		wd.switchTo().defaultContent();
		
		/*飞牛网开始*/
		String feiniu = "飞牛网-大润发网上商城";    
		Tools.changeWindowtitle(wd, feiniu);
		Thread.sleep(1000);
		if(wd.findElement(By.xpath("//div[@class='u-location-cont oh']")).isDisplayed()){   //第一次登录时，需要选择地址
			wd.findElement(By.id("popDist_CPG1_CS000016")).click();
			Tools.waitForElementInvisible(wd, By.xpath("//div[@class='u-location-cont oh']"), 10);
		}
		PublicOperation.addProduct(wd,testproduct); //将测试产品加入购物车
		
		PublicOperation po = new PublicOperation();
		String ordernumber = po.payOrder(wd,site,address[1]);  //订单支付
		
		PublicOperation.cancelOrder(wd,site,ordernumber);
		
		PublicOperation.logOut(wd, site);
		
	}
	
	/**
	 * 登录  case# 67238
	 * @param wd WebDriver对象
	 * @param account 用户名/密码"testwy01", "123456wy"
	 */
	public static void logIn(WebDriver wd,String[] site,String[] account) throws Exception {  //67238/67237/67236 
		
		wd.get(site[0]);
		
		if(wd.findElement(By.xpath("//div[@class='u-location-cont oh']")).isDisplayed()){   //检查地区选择框是否出现
//			wd.findElement(By.xpath("//div[@class='m-g-location J_select_dist']//div[@class='u-location-cont oh']//a[@id='popDist_CPG1_CS000016']")).click();
			wd.findElement(By.xpath("//i[@class='J_close_distpop']")).click();
			Tools.waitForElementInvisible(wd, By.xpath("//div[@class='u-location-cont oh']"), 10);
		}
		
		wd.findElement(By.xpath("//div[@class='dt J_login_status']/a[@class='login J_do_login J_chgurl']")).click();  //点击登录
//		wd.findElement(By.xpath("//div[@class='dt J_login_status']/a[@class='login J_do_login J_chgurl z-c-red']")).click();
		
		if(PublicOperation.logIn(wd, account)){   //testcase 67238结束
			Tools.log("testcase 67238 success");
		}else {
			Tools.log("testcase 67238 failed");
		}

/*		
		wd.findElement(By.xpath("//*[@id='hd-logo']")).click();  //点击logo
		Thread.sleep(1000);
		wd.get(site[0]);
		By by = By.linkText("testwy01");  
		if(Tools.isExist(wd, by)){//testcase 67237结束
			Tools.log("testcase 67237 success");
		}else Tools.log("testcase 67237 failed");
		
		wd.findElement(By.xpath("//a[@class='z-h-c']/span")).click();
		String title = "飞牛商城-正品低价、购物首选,大润发官方网上商城";
		Tools.changeWindowcontain(wd, title);
		Tools.xWindow(wd);
		wd.get(url[0]);
		if(Tools.isExist(wd, by)){//testcase 67236结束
			Tools.log("testcase 67236 success");
		}else Tools.log("testcase 67236 failed");
*/		
		
	}
	
	
	/**
	 * 不同页面查看登录情况  case# /67237/67236,"67235","67234","67233","67232","67231","67230","67229","67228","67227",
	 * @param wd WebDriver对象
	 * @param url 测试地址
	 * @param testcase 测试案例ID
	 */
	public static void checkAccount(WebDriver wd,String[][] urltest)throws Exception{
		
		List<String> errortime = new ArrayList<String>();
		List<String> errortime1 = new ArrayList<String>();
		
		for(int i=0;i<2;i++){
			wd.get(urltest[i][0]);
			Tools.waitPageLoad(wd);
			By by1 = By.linkText("testwy01");
			if(Tools.isExist(wd, by1)){
				Tools.log("testcase "+urltest[i][1]+" success");
			}else {
				Tools.log("BUG# "+urltest[i][1]+" No Account LogIn！");
			}
		}
		for(int i=2;i<urltest.length;i++){
			try{
				wd.get(urltest[i][0]);
				Tools.waitPageLoad(wd);
				Tools tls = new Tools(); //判断有没有出现活动结束时的对话框
				if(tls.isAlertPresent(wd)){
					Tools.log("BUG# "+urltest[i][1]+" There was a problem running,Maybe activity end");
					errortime.add(urltest[i][0]);
					errortime.add(urltest[i][1]);
					wd.switchTo().alert().accept();
					continue;
				}
				Tools.waitPageLoad(wd);
				String name = wd.findElement(By.id("wellcome_wording")).getText();
				if(!"testwy01".equals(name)){
					Tools.log("BUG# "+urltest[i][1]+" No Account LogIn！");
					errortime1.add(urltest[i][0]);
					errortime1.add(urltest[i][1]);
				}
//				Assertion.verifyEquals("testwy01", name, "BUG# "+urltest[i][1]+" 页面头部没有显示用户登录！");
			}catch(NoSuchElementException e){
				e.printStackTrace();
			}
		}
		if(!errortime.isEmpty()){
			wd.get(errortime.get(0));
			Tools.waitPageLoad(wd);
			wd.findElement(By.id("wellcome_wording")).getText();
		}
		if(!errortime1.isEmpty()){
			wd.get(errortime1.get(0));
			Tools.waitPageLoad(wd);
			wd.findElement(By.id("test_error")).getText();
		}
	}
	
	/**
	 * 产品页加入购物车  case# 67226
	 * @param wd WebDriver对象
	 * @param site 商详页
	 */
	public static void proAddCart(WebDriver wd,String[] site)throws Exception{
		wd.get(site[1]);
		Thread.sleep(1000);
		
		int numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		WebElement addbutton = wd.findElement(By.xpath("//a[@id='btnAddCart']"));
		addbutton.click();
		int numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		assertEquals(numb1+1,numb2);
	}
	

	/**
	 * 搜索页加入购物车  case# 67225
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param linkid 加入购物车ID
	 */
	public static void searchAddCart(WebDriver wd,String[] site,String[] linkid)throws Exception{
		wd.get(site[2]);
		Thread.sleep(1000);
		
		int numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		WebElement addbutton = wd.findElement(By.xpath("//*[@id="+"'"+"linkOrder_"+linkid[0]+"'"+"]"));
		addbutton.click();
		int numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		assertEquals(numb1+1,numb2);
	}
	
	/**
	 * 搜索页点击产看产品详情后，查看产品页面  case# 67224
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param linkid 加入购物车ID
	 * @param title 商详页title
	 */
	public static void searchLookInfo(WebDriver wd,String[] site,String[] linkid,String[] title)throws Exception{
		wd.get(site[3]);
		Thread.sleep(1000);
		
		WebElement lookinfo = wd.findElement(By.xpath("//*[@id="+"'"+"linkOrder_"+linkid[1]+"'"+"]"));
		lookinfo.click();
		new WebDriverWait(wd, 10).until(ExpectedConditions.titleIs(title[0]));
		assertEquals(title[0],wd.getTitle().toString());
	}
	
	/**
	 * 馆页点击购入购物车按钮  case# 67223
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param linkid 加入购物车ID
	 */
	public static void guanAddCart(WebDriver wd,String[] site,String[] linkid)throws Exception{
		wd.get(site[4]);
		Thread.sleep(1000);
		
		int numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		WebElement addbutton = wd.findElement(By.xpath("//*[@id="+"'"+"linkOrder_"+linkid[0]+"'"+"]"));
		addbutton.click();
		int numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		Assertion.verifyEquals(numb1+1,numb2);
	}
	
	
	/**
	 * 馆页点击产看产品详情后，查看产品页面  case# 67222
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param linkid 加入购物车ID
	 * @param title 商详页title
	 */
	public static void guanLookInfo(WebDriver wd,String[] site,String[] linkid,String[] title)throws Exception{
		wd.get(site[5]);
		Thread.sleep(1000);
		
		WebElement lookinfo = wd.findElement(By.xpath("//*[@id="+"'"+"linkOrder_"+linkid[1]+"'"+"]"));
		lookinfo.click();
		new WebDriverWait(wd, 10).until(ExpectedConditions.titleIs(title[0]));
		assertEquals(title[0],wd.getTitle().toString());
	}
	
	/**
	 * 聚合页加入购物车，查看活动购物车  case# 67221/67220
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param linkid 加入购物车ID
	 */
	public static void juheAddCart(WebDriver wd,String[] site,String[] linkid)throws Exception{
		wd.get(site[6]);
		Thread.sleep(1000);
		
		int numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		WebElement addbutton = wd.findElement(By.xpath("//*[@id="+"'"+"linkOrder_"+linkid[2]+"'"+"]"));
//		WebElement quantity = wd.findElement(By.xpath("//span[@id="+"'"+"itemLink_"+linkid[2]+"'"+"]/input"));
//		quantity.clear();
//		quantity.sendKeys("10");
		for(int i=0;i<2;i++){
			addbutton.click();
			Thread.sleep(1000);
		}
		int numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		Assertion.verifyEquals(numb1+2,numb2,"BUG#67221:聚合页加入购物车失败");  //67221
		
		String cartinfo = wd.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[2]/div[2]/p[1]")).getText().substring(2, 10);
		Tools.log(cartinfo);
		assertEquals("已满足活动条件！",cartinfo); //67220
	}
	
	

	/**
	 * 购物车结算页面地址选择上海和新疆  case# 67219,67218,67217
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 */
	public static void cartCheckAddress(WebDriver wd,String[] site)throws Exception{
		wd.get(site[0]);
		Thread.sleep(1000);
		
		int numb1 = Integer.valueOf(wd.findElement(By.xpath("//span[@class='count cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		if(0<numb1){
			PublicOperation.deleteAllProducts(wd);
			Thread.sleep(1000);
		}
		
		wd.get(site[7]);
		Thread.sleep(1000);
		WebElement addbutton = wd.findElement(By.xpath("//a[@id='btnAddCart']"));   //添加生鲜产品
		addbutton.click();
		
		wd.get("http://buy.feiniu.com/cart/index");//进入购物车界面
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
		Thread.sleep(1000);

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
		Thread.sleep(1000);

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
        String mon1 = wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div/div/div[3]/div[2]/ul/li[5]/dl/dd[1]/span")).getText();
        if("0".equals(mon1.substring(mon1.length()-2, mon1.length()-1))){   //判断价钱是否有小数点
        	money = Tools.catchnumber(mon1).substring(0,mon1.length()-3);   //处理取出的金额后面的00删除，还有取出小数点
        }else{
        	money = mon1;
        }
        
        wd.findElement(By.className("clearing-total2")).click();  //购物车中点击结算
        
        WebElement pay = wd.findElement(By.xpath("//span[@class='pay-price']"));
        String mon2 = pay.getText();
        assertEquals(money,mon2);  //验证结算页面的订单金额   67217验证测试是否成功
	}
	
	
	/**
	 * 订单提交，查看完成页面，退货取消订单，查看积分+优惠券的显示  case# 67216,67215,67214,67213,65938
	 * @param wd WebDriver对象
	 * @param site 搜索页面
	 * @param testproduct 线上测试产品
	 * @param address 用户地址ID
	 */
	public static void orderAndAccount(WebDriver wd,String[] site,String testproduct,String[] address)throws Exception{
		
		wd.get(site[0]);
		Thread.sleep(1000);
		
		int numb1 = Integer.valueOf(wd.findElement(By.xpath("//span[@class='count cart_quantity']")).getText()).intValue();   //获取购物车产品数量
		if(0<numb1){
			PublicOperation.deleteAllProducts(wd);
			Thread.sleep(1000);
		}
		
		/**************记录付款前的优惠券数量和积分**************/
		PublicOperation po1 = new PublicOperation();
		List<Integer> num1 = po1.getJifen(wd);
		
		int diyongquan1 = num1.get(0);
//		int gouwujin1 = num1.get(0);
		int jifen1 = num1.get(1);
		Tools.log("Init："+diyongquan1+"|"+jifen1);
		
		PublicOperation.addProduct(wd,testproduct);    //将测试产品加入购物车
		
		PublicOperation po = new PublicOperation();
		String ordernumber = po.payOrderCoupon(wd,site,address[0]);  //订单支付
		
		/**************记录付款后的优惠券数量和积分**************/
		PublicOperation po2 = new PublicOperation();
		List<Integer> num2 = po2.getJifen(wd);
		
		int diyongquan2 = num2.get(0);
//		int gouwujin2 = num2.get(0);
		int jifen2 = num2.get(1);
		Tools.log("Pay:"+diyongquan2+"|"+jifen2);
		Assertion.verifyEquals(diyongquan1-1,diyongquan2,"BUG:Compare the vouchers error before than pay the order!");
		Assertion.verifyEquals(jifen1-1,jifen2,"BUG:Compare the integral error before than pay the order!");
		
		PublicOperation.cancelOrder(wd,site,ordernumber);
		
		Tools.log("Please wait for 30 minutes！");
		Thread.sleep(2000000); //等待三十分钟
		/**************记录取消订单后的优惠券数量和积分**************/
		PublicOperation po3 = new PublicOperation();
		List<Integer> num3 = po3.getJifen(wd);
		
		int diyongquan3 = num3.get(0);
//		int gouwujin3 = num3.get(0);
		int jifen3 = num3.get(1);
		Tools.log("Cancel:"+diyongquan3+"|"+jifen3);
		Assertion.verifyEquals(diyongquan1,diyongquan3,"BUG:Compare the vouchers error before than cancel the order!");
//		Assertion.verifyEquals(jifen1,jifen3,"BUG:Compare the integral error before than cancel the order!");
		assertEquals(jifen1,jifen3,"BUG:Compare the integral error before than cancel the order!");
	}
	
	
	/**
	 * 不同页面查看登录情况  case# /67237/67236,"67235","67234","67233","67232","67231","67230","67229","67228","67227",
	 * @param wd WebDriver对象
	 * @param url 测试地址
	 * @param testcase 测试案例ID
	 */
	public static void checkAccountMaster(WebDriver wd,String[] urltest,String account)throws Exception{
		
		List<String> errortime = new ArrayList<String>();
		
		wd.get(urltest[0]);
		
		Tools.log(account);
		Tools.waitPageLoad(wd);
		By by = By.linkText(account);
//		WebElement name = wd.findElement(by);
//		Tools.log(name.getText());
		if(Tools.isExist(wd, by)){
			Tools.log("testcase "+urltest[1]+" success");
		}else {
			errortime.add(urltest[0]);
			Tools.log("BUG# "+urltest[1]+" No Account LogIn");
		}
			
		if(!errortime.isEmpty()){
			wd.get(errortime.get(0));
			Tools.waitPageLoad(wd);
			WebElement name = wd.findElement(by);
			Tools.log(name.getText());
		}
	}
	
	public static void checkAccountNode(WebDriver wd,String[] urltest,String account)throws Exception{
		
		wd.get(urltest[0]);
		
		Tools.log(account);
		Tools.waitPageLoad(wd);
		
//		Tools tls = new Tools(); //判断有没有出现活动结束时的对话框
//		if(tls.isAlertPresent(wd)){
//			Tools.log("error case# "+urltest[1]+" 运行时出现问题,可能活动结束！");
//			wd.switchTo().alert().accept();
//		}
		
		String name = wd.findElement(By.id("wellcome_wording")).getText();
		
		assertEquals(account, name, "BUG# "+urltest[1]+" No Account LogIn");
//		if(!"testwy01".equals(name)){
//			Tools.log("BUG# "+urltest[1]+" 页面头部没有显示用户登录！");
//			errortime1.add(urltest[0]);
//		}
//		Assertion.verifyEquals("testwy01", name, "BUG# "+urltest[i][1]+" 页面头部没有显示用户登录！");	
	}
	
}
