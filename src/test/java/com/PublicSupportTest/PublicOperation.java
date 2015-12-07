package com.PublicSupportTest;

import static org.testng.Assert.assertEquals;










//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
//import java.util.Set;











import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PublicOperation {
	
//	private static SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static String[] ziyingpdts = {
            "http://www.beta1.fn/sh/item/201509CM090000002",//寄销单品
            "http://www.beta1.fn/sh/item/201509CM010000028",//寄销规格品
            "http://www.beta1.fn/sh/item/201509CM080001016",//自营转单含赠品+活动
            "http://www.beta1.fn/sh/item/201509CM080001024",//虚拟组合
            "http://www.beta1.fn/sh/item/201508CM280000075",//生鲜
            "http://www.beta1.fn/sh/item/201509CM280000017"//自营转单规格品
    };

    static String[] shangcheng = {
            "http://item.beta1.fn/100134655"//商城单品+活动
    };
    
    /**
	 * 商品页面增加单个产品
	 * @param wd WebDriver对象
	 */
    public static void addProduct(WebDriver wd,String url) throws Exception {  
    	 wd.get(url);
         Thread.sleep(1000);

         By byele = By.xpath("//*[@id='btnAddCart']");
         WebElement button1 = Tools.WaitUtilFind(wd, byele);
         Thread.sleep(2000);//等待加入购物车按钮可用
         button1.click();
         WebElement div = wd.findElement(By.xpath("//div[@id='addCartBox']"));
         div.isDisplayed();   //加入购物车成功
         WebElement suc = wd.findElement(By.xpath("//div[@id='addCartBox']/div[@class='colect-top']/div[@class='conect-title']/h3"));
         String sucess = suc.getText();

         assertEquals("添加成功", sucess);  //验证是否进入购物车
    }
    
    
	/**
	 * 商品页面循环增加产品
	 * @param wd WebDriver对象
	 */
	public static void addAllProducts(WebDriver wd) throws Exception {  
        

        for (int i = 0; i < ziyingpdts.length; i++) {
            wd.get(ziyingpdts[i]);
            Thread.sleep(1000);

            By byele = By.xpath("//*[@id=\"btnAddCart\"]");
            WebElement button1 = Tools.WaitUtilFind(wd, byele);
            Thread.sleep(2000);//等待加入购物车按钮可用
            button1.click();
            WebElement div = wd.findElement(By.xpath("//div[@id='addCartBox']"));
            div.isDisplayed();   //加入购物车成功
            WebElement suc = wd.findElement(By.xpath("//div[@id='addCartBox']/div[@class='colect-top']/div[@class='conect-title']/h3"));
            String sucess = suc.getText();

            assertEquals("添加成功", sucess);  //验证是否进入购物车
        }
		/*商城*/
        for (int i = 0; i < shangcheng.length; i++) {
            wd.get(shangcheng[i]);
            Thread.sleep(1000);

            By byele1 = By.xpath("//*[@id=\"buyProduct\"]");
            WebElement button2 = Tools.WaitUtilFind(wd, byele1);
            Thread.sleep(2000);//等待加入购物车按钮可用
            button2.click();
            WebElement div = wd.findElement(By.cssSelector("html body div.dp-dialog"));
            div.isDisplayed();   //加入购物车成功
            WebElement suc = wd.findElement(By.cssSelector("html body div.dp-dialog div.dp-dialog-content div.dialog-notice.dp-dialog-mb18"));
            String sucess = suc.getText();
            assertEquals("添加成功！", sucess);  //验证是否进入购物车
        }
    }
	
	
	/**
	 * 删除购物车内所有产品
	 * @param wd WebDriver对象
	 */
	public static void deleteAllProducts(WebDriver wd)throws Exception{
		wd.get("http://buy.feiniu.com/cart/index");//清空购物车产品

        if(!wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div/div/div[2]/div/div[2]/div/p[1]")).isDisplayed()){
        	
        	By loading = By.xpath("//div[@class='c-package']//div[@class='clearing-loading']/div[@class='fn-fl']");
        	Tools.waitForElementInvisible(wd, loading,10);
            WebElement clear = wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div/div/div[3]/div[2]/ul/li[3]/a"));
            clear.click();
            wd.findElement(By.xpath("/html/body/div[5]/div/div[10]/button[1]")).click();
        }
	}
	public static void deleteAllProductsBeta(WebDriver wd)throws Exception{
		wd.get("http://buy.beta1.fn/cart/index");//清空购物车产品

        if(!wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div/div/div[2]/div/div[2]/div/p[1]")).isDisplayed()){
        	
        	By loading = By.xpath("//div[@class='c-package']//div[@class='clearing-loading']/div[@class='fn-fl']");
        	Tools.waitForElementInvisible(wd, loading,10);
            WebElement clear = wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div/div/div/div[3]/div[2]/ul/li[3]/a"));
            clear.click();
            wd.findElement(By.xpath("/html/body/div[5]/div/div[10]/button[1]")).click();
        }
	}
	
	
	/**
	 * 登录
	 * @param wd WebDriver对象
	 * @param na 用户名
	 * @param pd 密码
	 */
	public static boolean logIn(WebDriver wd,String[] account)throws Exception{

        if(Tools.waitPageLoad(wd)) {
            Tools.changeWindowtitle(wd, "登录飞牛网");
            Thread.sleep(1000);
            WebElement name = wd.findElement(By.xpath("//input[@class='username']"));
            WebElement password = wd.findElement(By.xpath("//input[@class='password']"));
            WebElement submit = wd.findElement(By.xpath("//input[@class='sbtbutton']"));
            name.sendKeys(account[0]);
            password.sendKeys(account[1]);
            submit.click();
            Thread.sleep(1000);
            Tools.xWindow(wd);
            return true;
        } else return false;
    }

	/**
	 * 登出
	 * @param wd WebDriver对象
	 * @param site[] 首页
	 */
    public static void logOut(WebDriver wd,String[] site)throws Exception{

        wd.get(site[0]);   //首页退出
        Tools.waitPageLoad(wd);
        WebElement logot = wd.findElement(By.xpath("//div[@class='m-g-topbar']//a[@class='ml5']"));
        logot.click();

    }   

    
    public String payOrder(WebDriver wd,String[] site,String address)throws Exception{

        wd.get(site[0]);

        Thread.sleep(1000);
        Integer numb = Integer.valueOf(wd.findElement(By.xpath("//span[@class='count cart_quantity']")).getText()).intValue();   //获取购物车产品数量

        if(numb > 0){//查看购物车有没有产品
            wd.findElement(By.xpath("//div[@id='hd-my-cart']/p[@class='c-n fl']")).click();  //点击购物车
        }else{
            Tools.log("Shopping Cart Empty");
        }

        Thread.sleep(5000);

        WebElement but = wd.findElement(By.xpath("//input[@id='qx-1']"));
        if(!but.isSelected()){
            but.click();  //全选购物车中产品
            Tools.WaitUtilSelected(wd,By.xpath("//*[@id=\"qx-1\"]"));  //等待全选按钮生效
        }

        wd.findElement(By.className("clearing-total2")).click();  //购物车中点击结算

        WebElement addisselect = wd.findElement(By.xpath("//*[@id='"+address+"']"));			//查看地址有没有被选中
        if(!addisselect.isSelected()){
            addisselect.click();
        }

        WebElement payisselect = wd.findElement(By.xpath("//*[@id=\"payment-online\"]"));			//查看支付方式有没有选中在线支付
        if(!payisselect.isSelected()){
            payisselect.click();
        }

        Thread.sleep(500);
        
        WebElement sub = wd.findElement(By.id("btn_submit"));    //点击提交按钮
        sub.click();     //提交
        Thread.sleep(3000);

        wd.findElement(By.id("showDetail")).click();  //展开订单详情
        String ordernumber = wd.findElement(By.xpath("//span[@class='order-num']")).getText(); //获取订单号
        Tools.log(ordernumber);
        return ordernumber;
        
/*这里开始是测试环境的支付流程，线上测试不需要
        String current = wd.getWindowHandle();

        wd.findElement(By.id("paySubmit")).click();

        Tools.closeAlertAndGetItsText(wd);

        Set<String> all = wd.getWindowHandles();
        Iterator<String> iterator = all.iterator();
        while (iterator.hasNext()) {
            String handle = iterator.next();
            if(handle.equals(current)){
                continue;
            }
            else{
                wd.switchTo().window(handle);
            }
        }


        wd.findElement(By.xpath("/html/body/form/div[1]/input")).sendKeys(ordernumber);  //输入订单号
        wd.findElement(By.xpath("/html/body/form/div[4]/input")).click();
        wd.close();

        wd.switchTo().window(current);

        wd.findElement(By.xpath("//div[@id='pay_notice']//button[@class='btn btn-red']")).click();

        String sucess = wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[3]/a[1]")).getText();

        assertEquals("查看订单详情",sucess);
*/
        
        
    }    
    
    
    /**
	 * 支付功能，首页查看购物车有没有产品，然后支付，使用抵用券/购物金，积分
	 * @param wd WebDriver对象
	 * @param site[] 首页
	 */
    public String payOrderCoupon(WebDriver wd,String[] site,String address)throws Exception{

        wd.get(site[0]);

        Thread.sleep(1000);
        Integer numb = Integer.valueOf(wd.findElement(By.xpath("//span[@class='count cart_quantity']")).getText()).intValue();   //获取购物车产品数量

        if(numb > 0){//查看购物车有没有产品
            wd.findElement(By.xpath("//div[@id='hd-my-cart']/p[@class='c-n fl']")).click();  //点击购物车
        }else{
            Tools.log("Shopping Cart Empty");
        }

        Thread.sleep(5000);

        WebElement but = wd.findElement(By.xpath("//input[@id='qx-1']"));
        if(!but.isSelected()){
            but.click();  //全选购物车中产品
            Tools.WaitUtilSelected(wd,By.xpath("//*[@id=\"qx-1\"]"));  //等待全选按钮生效
        }

        wd.findElement(By.className("clearing-total2")).click();  //购物车中点击结算

        WebElement addisselect = wd.findElement(By.xpath("//*[@id='"+address+"']"));			//查看地址有没有被选中
        if(!addisselect.isSelected()){
            addisselect.click();
        }

        WebElement payisselect = wd.findElement(By.xpath("//*[@id=\"payment-online\"]"));			//查看支付方式有没有选中在线支付
        if(!payisselect.isSelected()){
            payisselect.click();
        }

        Thread.sleep(1000);
        JavascriptExecutor jse = (JavascriptExecutor)wd;  
        jse.executeScript("window.document.getElementById('抵用券0').click()");
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"),10);
//        WebElement gouwujin = wd.findElement(By.xpath("//*[@id='购物金0']")); //购物金
//        gouwujin.click();
//        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"),10);
        WebElement jifen = wd.findElement(By.id("integral"));   //点击积分
        jifen.click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"),10);
        wd.findElement(By.id("cardIntegral")).clear();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);
        wd.findElement(By.id("cardIntegral")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);
        wd.findElement(By.id("cardIntegral")).sendKeys("1");   //添加1积分
        wd.findElement(By.xpath("//*[@id=\"minus\"]")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);

/*
        WebElement gouwuka = wd.findElement(By.id("cash_card"));  //点击购物卡
        gouwuka.click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);
        wd.findElement(By.id("cardMoney")).clear();   //添加1购物金
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);
        wd.findElement(By.id("cardMoney")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);
        wd.findElement(By.id("cardMoney")).sendKeys("1");   //添加1购物金
        wd.findElement(By.xpath("//*[@id=\"minus\"]")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);
        wd.findElement(By.xpath("//*[@id='paypass']//input[@class='paypass']")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);
        wd.findElement(By.xpath("//*[@id='paypass']//input[@class='paypass']")).sendKeys("123456");   //输入购物金密码
        wd.findElement(By.xpath("//*[@id=\"minus\"]")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 10);
*/
        
        WebElement sub = wd.findElement(By.id("btn_submit"));
//        ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView();" ,sub);     //屏幕移动到提交按钮
        sub.click();     //提交
        Thread.sleep(3000);


        wd.findElement(By.id("showDetail")).click();  //展开订单详情
        String ordernumber = wd.findElement(By.xpath("//span[@class='order-num']")).getText(); //获取订单号
        Tools.log("OrderNumber:"+ordernumber);
//        Tools.log("订单号:"+ordernumber);
        return ordernumber;
        
/*这里开始是测试环境的支付流程，线上测试不需要
        String current = wd.getWindowHandle();

        wd.findElement(By.id("paySubmit")).click();

        Tools.closeAlertAndGetItsText(wd);

        Set<String> all = wd.getWindowHandles();
        Iterator<String> iterator = all.iterator();
        while (iterator.hasNext()) {
            String handle = iterator.next();
            if(handle.equals(current)){
                continue;
            }
            else{
                wd.switchTo().window(handle);
            }
        }


        wd.findElement(By.xpath("/html/body/form/div[1]/input")).sendKeys(ordernumber);  //输入订单号
        wd.findElement(By.xpath("/html/body/form/div[4]/input")).click();
        wd.close();

        wd.switchTo().window(current);

        wd.findElement(By.xpath("//div[@id='pay_notice']//button[@class='btn btn-red']")).click();

        String sucess = wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[3]/a[1]")).getText();

        assertEquals("查看订单详情",sucess);
*/
    }    
    
    public String payOrderCouponBeta(WebDriver wd,String[][] site,String address)throws Exception{

        wd.get(site[0][0]);

        Thread.sleep(1000);
        Integer numb = Integer.valueOf(wd.findElement(By.xpath("//span[@class='count cart_quantity']")).getText()).intValue();   //获取购物车产品数量

        if(numb > 0){//查看购物车有没有产品
            wd.findElement(By.xpath("//div[@id='hd-my-cart']/p[@class='c-n fl']")).click();  //点击购物车
        }else{
            Tools.log("Shopping Cart Empty");
        }

        Thread.sleep(5000);

        WebElement but = wd.findElement(By.xpath("//input[@id='qx-1']"));
        if(!but.isSelected()){
            but.click();  //全选购物车中产品
            Tools.WaitUtilSelected(wd,By.xpath("//*[@id=\"qx-1\"]"));  //等待全选按钮生效
        }

        wd.findElement(By.className("clearing-total2")).click();  //购物车中点击结算

        WebElement addisselect = wd.findElement(By.xpath("//*[@id='"+address+"']"));			//查看地址有没有被选中
        if(!addisselect.isSelected()){
            addisselect.click();
        }

        WebElement payisselect = wd.findElement(By.xpath("//*[@id=\"payment-online\"]"));			//查看支付方式有没有选中在线支付
        if(!payisselect.isSelected()){
            payisselect.click();
        }

        Thread.sleep(1000);
        JavascriptExecutor jse = (JavascriptExecutor)wd;  
        jse.executeScript("window.document.getElementById('抵用券0').click()");
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"),50);
       
        
//        WebElement gouwujin = wd.findElement(By.xpath("//*[@id='购物金0']")); //购物金
//        gouwujin.click();
//        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"),10);
        
        WebElement jifen = wd.findElement(By.id("integral"));   //点击积分
        jifen.click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"),50);
        wd.findElement(By.id("cardIntegral")).clear();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);
        wd.findElement(By.id("cardIntegral")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);
        wd.findElement(By.id("cardIntegral")).sendKeys("1");   //添加1积分
        wd.findElement(By.xpath("//*[@id=\"minus\"]")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);


        WebElement gouwuka = wd.findElement(By.id("cash_card"));  //点击购物卡
        gouwuka.click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);
        wd.findElement(By.id("cardMoney")).clear();   //添加1购物金
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);
        wd.findElement(By.id("cardMoney")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);
        wd.findElement(By.id("cardMoney")).sendKeys("1");   //添加1购物金
        wd.findElement(By.xpath("//*[@id=\"minus\"]")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);
        wd.findElement(By.xpath("//*[@id='paypass']//input[@class='paypass']")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);
        wd.findElement(By.xpath("//*[@id='paypass']//input[@class='paypass']")).sendKeys("123456");   //输入购物金密码
        wd.findElement(By.xpath("//*[@id=\"minus\"]")).click();
        Tools.waitForElementInvisible(wd, By.xpath("/html/body/div[21]/div"), 50);

        
        WebElement sub = wd.findElement(By.id("btn_submit"));
//        ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView();" ,sub);     //屏幕移动到提交按钮
        sub.click();     //提交
        Tools.waitTitle(wd, 50, "支付");


        wd.findElement(By.id("showDetail")).click();  //展开订单详情
        String ordernumber = wd.findElement(By.xpath("//span[@class='order-num']")).getText(); //获取订单号
        Tools.log("OrderNumber:"+ordernumber);
//        Tools.log("订单号:"+ordernumber);
        return ordernumber;
        
/*这里开始是测试环境的支付流程，线上测试不需要
        String current = wd.getWindowHandle();

        wd.findElement(By.id("paySubmit")).click();

        Tools.closeAlertAndGetItsText(wd);

        Set<String> all = wd.getWindowHandles();
        Iterator<String> iterator = all.iterator();
        while (iterator.hasNext()) {
            String handle = iterator.next();
            if(handle.equals(current)){
                continue;
            }
            else{
                wd.switchTo().window(handle);
            }
        }


        wd.findElement(By.xpath("/html/body/form/div[1]/input")).sendKeys(ordernumber);  //输入订单号
        wd.findElement(By.xpath("/html/body/form/div[4]/input")).click();
        wd.close();

        wd.switchTo().window(current);

        wd.findElement(By.xpath("//div[@id='pay_notice']//button[@class='btn btn-red']")).click();

        String sucess = wd.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[3]/a[1]")).getText();

        assertEquals("查看订单详情",sucess);
*/
    }    
    
    
    /**
   	 * 注册功能
   	 * @param wd WebDriver对象
   	 * @param site[] 首页
   	 */
    public static void register(WebDriver wd,String[] site,String na,String pd){
    	//需要手机验证码，还有图形验证码，无法操作
    }
    
    /**
   	 * 取消订单
   	 * @param wd WebDriver对象
   	 * @param site[] 首页
   	 */
    public static void cancelOrder(WebDriver wd,String[] site,String ordernumber)throws Exception{
    	
		wd.get("https://member.feiniu.com/order/orderList");
		
/*		使用鼠标点击的方式进入我的订单
		String current = wd.getWindowHandle();

		WebElement mubiao = wd.findElement(By.xpath("//div[@class='f-header pr']/div[@class='m-g-topbar']/div/ul[@class='fr']/li[@class='tt u-tb-mf dd']/div[@class='dt']/a"));
		Actions mouse = new Actions(wd);
		mouse.moveToElement(mubiao).build().perform();
		wd.findElement(By.linkText("我的订单")).click();  //点击我的订单

//		wd.findElement(By.xpath("/html/body/div[1]/div/ul[2]/li[4]/a")).click(); //点击我的账户
		
		Set<String> all = wd.getWindowHandles();    //关闭窗口
		Iterator<String> iterator = all.iterator();
		while (iterator.hasNext()) {
			String handle = iterator.next();
			if(handle.equals(current)){
				continue;
			}
			else{
				wd.switchTo().window(handle);
				if(wd.getTitle().contains("我的账户-飞牛网")){
					Tools.log("窗口成功跳转");
					
					break;
				}
				else{
					continue;
					}
				}
			}
*/
		
//		if(Tools.isExist(wd, By.linkText("取消订单"))){     //点击取消第一个订单
//			List cancel = wd.findElements(By.linkText("取消订单"));
//			WebElement can = (WebElement)cancel.get(0);
//			can.click();
//		}else Tools.log("下单失败");
		
		for(int i=1;i<100;i++){
			
			Thread.sleep(2000);
			WebElement cancel = wd.findElement(By.xpath("//*[@id='"+"cancel_order_"+ordernumber+"']"));  
			
			cancel.click();
			
			Thread.sleep(3000);
			wd.findElement(By.xpath("//div[@class='aui_state_focus aui_state_lock']//table[@class='aui_dialog']//button[@class='btn_ok']")).click();     //点击确认取消按钮

			Tools tls = new Tools();
			tls.waitAlertPresent(wd, 50);
			String alertText = tls.closeAlertAndGetItsText(wd);
			if("订单已取消".equals(alertText)){  //订单已取消
				Tools.log(alertText+":"+"Cancel "+i+" Success");
				break;
			}Tools.log(alertText+":"+"Cancel Order Failed "+i+" Time");
		}
		
	}
    
    
    public static void cancelOrderBeta(WebDriver wd,String[][] site,String ordernumber)throws Exception{
    	
		wd.get("https://member.beta1.fn/sh/order/orderList");
		
		for(int i=1;i<100;i++){
			
			Thread.sleep(2000);
			WebElement cancel = wd.findElement(By.xpath("//*[@id='"+"cancel_order_"+ordernumber+"']"));  
			
			cancel.click();
			
			Thread.sleep(1000);
			wd.findElement(By.xpath("//div[@class='aui_state_focus aui_state_lock']//table[@class='aui_dialog']//button[@class='btn_ok']")).click();     //点击确认取消按钮

			Tools tls = new Tools();
			tls.waitAlertPresent(wd, 20);
			String alertText = tls.closeAlertAndGetItsText(wd);
			if("订单已取消".equals(alertText)){  //订单已取消
				Tools.log(alertText+":"+"Cancel "+i+" Success");
				break;
			}Tools.log(alertText+":"+"Cancel Order Failed "+i+" Time");
		}
		
	}

    
    
    /**
   	 * 获得积分，抵用券
   	 * @param wd WebDriver对象
   	 */
	public List<Integer> getJifen(WebDriver wd){
    	List<Integer> num = new ArrayList<Integer>();
    	wd.get("https://member.feiniu.com/home/index/");
    	new WebDriverWait(wd, 10).until(ExpectedConditions.titleIs("我的账户-飞牛网"));
    	
    	String num1 = wd.findElement(By.xpath("//div[@class='wrap']//dd[@class='subnav']/ul/li[1]/a/span")).getText();
//    	String num2 = wd.findElement(By.xpath("//div[@class='wrap']//dd[@class='subnav']/ul/li[2]/a/span")).getText();
    	
		int diyongquan = Integer.valueOf(Tools.catchnumber(num1)).intValue();
//		int gouwujin = Integer.valueOf(Tools.catchnumber(num2)).intValue();
		
		wd.get("https://member.feiniu.com/points");
		new WebDriverWait(wd, 10).until(ExpectedConditions.titleIs("我的积分-飞牛网"));
		
		String num3 = wd.findElement(By.xpath("//div[@class='wrap']/div[3]/div/div[2]/ul/li[1]/div/p/span")).getText();
		
		int jifen = Integer.valueOf(Tools.catchnumber(num3)).intValue();
		
		num.add(diyongquan);
//		num.add(gouwujin);
		num.add(jifen);
		return num;
    }
	
	public List<Integer> getJifenBeta(WebDriver wd){
    	List<Integer> num = new ArrayList<Integer>();
    	wd.get("https://member.beta1.fn/sh/home/index/");
    	new WebDriverWait(wd, 10).until(ExpectedConditions.titleIs("我的账户-飞牛网"));
    	
    	String num1 = wd.findElement(By.xpath("//div[@class='wrap']//dd[@class='subnav']/ul/li[1]/a/span")).getText();
//    	String num2 = wd.findElement(By.xpath("//div[@class='wrap']//dd[@class='subnav']/ul/li[2]/a/span")).getText();
    	
		int diyongquan = Integer.valueOf(Tools.catchnumber(num1)).intValue();
//		int gouwujin = Integer.valueOf(Tools.catchnumber(num2)).intValue();
		
		wd.get("https://member.beta1.fn/sh/points");
		new WebDriverWait(wd, 10).until(ExpectedConditions.titleIs("我的积分-飞牛网"));
		
		String num3 = wd.findElement(By.xpath("//div[@class='wrap']/div[3]/div/div[2]/ul/li[1]/div/p/span")).getText();
		
		int jifen = Integer.valueOf(Tools.catchnumber(num3)).intValue();
		
		num.add(diyongquan);
//		num.add(gouwujin);
		num.add(jifen);
		return num;
    }
	
	 /**
   	 * 商详页改地址到长宁区
   	 * @param wd WebDriver对象
   	 */
	public void changeAddress(WebDriver wd,String title,String code,String title1,String code1,String title2,String code2)throws Exception{
		
		By by = By.xpath("//dl[@class='sevice']/dd/a");	//这一步很重要。。全屏的时候抓不到元素，需要移动屏幕
		Tools.scrollToElement(wd, by);
		Thread.sleep(1000);
		
		Actions mouse = new Actions(wd);  
		
		WebElement add = wd.findElement(By.xpath("//span[@id='curSelect']"));
		mouse.moveToElement(add).build().perform();
		
		By by1 = By.xpath("//div[@id='selectDetail']/div/div[1]/a[1]/b");   //等待地址显示
		Tools.waitForElementVisible(wd, by1, 10);
		wd.findElement(by1).click();
		
		By by2 = By.xpath("//a[@title="+"'"+title+"'"+"and @data-code="+"'"+code+"'"+"]");   //等待地址显示
		Tools.waitForElementVisible(wd, by2, 10);
		wd.findElement(by2).click();
		
		By by3 = By.xpath("//div[@id='selectDetail']/div/div[1]/a[2]/b");   //等待地址显示
		Tools.waitForElementVisible(wd, by3, 10);
		wd.findElement(by3).click();
		
		By by4 = By.xpath("//a[@title="+"'"+title1+"'"+"and @data-code="+"'"+code1+"'"+"]");   //等待地址显示
		Tools.waitForElementVisible(wd, by4, 10);
		wd.findElement(by4).click();
		
		By by5 = By.xpath("//div[@id='selectDetail']/div/div[1]/a[3]/b");   //等待地址显示
		Tools.waitForElementVisible(wd, by5, 10);
		wd.findElement(by5).click();
		
		By by6 = By.xpath("//a[@title="+"'"+title2+"'"+"and @data-code="+"'"+code2+"'"+"]");   //等待地址显示
		Tools.waitForElementVisible(wd, by6, 10); 
		wd.findElement(by6).click();
		Thread.sleep(1000);
		
		
		/*
		By by1 = By.xpath("//div[@id='selectDetail']/div/div[1]/a[1]/b");   //等待地址显示
		Tools.waitForElementVisible(wd, by1, 10);
		wd.findElement(by1).click();
		
		By by2 = By.xpath("//a[@title='上海' and @data-code='CS000016']");   //等待地址显示
		Tools.waitForElementVisible(wd, by2, 10);
		wd.findElement(by2).click();
		
		By by3 = By.xpath("//div[@id='selectDetail']/div/div[1]/a[2]/b");   //等待地址显示
		Tools.waitForElementVisible(wd, by3, 10);
		wd.findElement(by3).click();
		
		By by4 = By.xpath("//a[@title='上海市' and @data-code='310100']");   //等待地址显示
		Tools.waitForElementVisible(wd, by4, 10);
		wd.findElement(by4).click();
		
		By by5 = By.xpath("//div[@id='selectDetail']/div/div[1]/a[3]/b");   //等待地址显示
		Tools.waitForElementVisible(wd, by5, 10);
		wd.findElement(by5).click();
		
		By by6 = By.xpath("//a[@title='长宁区' and @data-code='310105']");   //等待地址显示
		Tools.waitForElementVisible(wd, by6, 10); 
		wd.findElement(by6).click();
		Thread.sleep(1000);*/
	}
	
	/**
   	 * 商详页批量加入购物车
   	 * @param wd WebDriver对象
   	 * @param site url地址
   	 */
	public void proAddCartBeta(WebDriver wd,String[][] site, String[][] area)throws Exception{
		
		int numb1;
		int numb2;
		By by = By.xpath("//dl[@class='sevice']/dd/a");	//这一步很重要。。全屏的时候抓不到元素，需要移动屏幕
		By by1 = By.xpath("//a[@class='J_chooseShop']");  //(商品)选择商品规格
		By by2 = By.xpath("//a[@class='J_tie_in']");  //(套餐)选择商品规格
		int numling = 0;   //0元商品，如果是0表示购物车中没有0元商品可以添加，如果是1则表示无法添加
		WebElement addbutton;
		
		for(int i=0;i<site[1].length;i++){
			if(!"商品id".equals(site[1][i])){   //剔除第一行标题
				if("1".equals(site[6][i])){   //1.单品，规格品，赠品，配件
					/**单品，组合，赠品，组合 加入购物车*/  
					wd.get(site[0][i]);
					Tools.log("Produtc page: test product id = "+site[1][i]);
					Thread.sleep(1000);
					numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
					Thread.sleep(500);
					if(wd.getPageSource().contains("抱歉，当前区域不在配送范围内")){
						changeAddress(wd,area[0][1],area[0][2],area[0][3],area[0][4],area[0][5],area[0][6]);  //商详页切换地址
						Thread.sleep(500);
					}
					if(wd.getPageSource().contains("选择商品规格")&&Tools.isExist(wd, by1)){    //选择赠品规格
						List<WebElement> chooseShops = wd.findElements(by1);   //选择规格
						for(WebElement chooseShop : chooseShops){  //循环点击选择商品规格
							chooseShop.click();
							wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[1]/li[1]/span/img")).click();   //点击规格小图标
							wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[2]/li[1]/span")).click();	//选择size
							wd.findElement(By.xpath("//div[@id='J_layer_body']/div[2]/a")).click();   //确定按钮
						}
					}
					Thread.sleep(500);
					addbutton = wd.findElement(By.xpath("//a[@id='btnAddCart']"));
					addbutton.click();
					Thread.sleep(500);
					numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
					if("2".equals(site[7][i])){
						Assertion.verifyEquals(numb2,numb1+2,"(BUG)Produtc page: test product id = "+site[1][i]); //如果等于2，说明是加购品，添加到购物车后，数量需要+2
					}
					Assertion.verifyEquals(numb2,numb1+1,"(BUG)Produtc page: test product id = "+site[1][i]);
				}
				
				if("2".equals(site[6][i])){   //2.加购品，套餐商品
					/**加购品和套餐 加入购物车*/
					wd.get(site[0][i]);
					Tools.log("Produtc page: test product id = "+site[1][i]);
					Thread.sleep(1000);
					numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
					Thread.sleep(500);
					if(wd.getPageSource().contains("选择商品规格")&&Tools.isExist(wd, by1)){    //选择赠品规格
						wd.findElement(by1).click();   //选择规格
						wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[1]/li[1]/span/img")).click();   //点击规格小图标
						wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[2]/li[1]/span")).click();	//选择size
						wd.findElement(By.xpath("//div[@id='J_layer_body']/div[2]/a")).click();   //确定按钮
					}
					Tools.scrollToElement(wd, by);	//这一步很重要。。全屏的时候抓不到元素，需要移动屏幕
					Thread.sleep(1000);
					if("3".equals(site[7][i])){
						if(wd.getPageSource().contains("选择商品规格")&&Tools.isExist(wd, by2)){  
							List<WebElement> J_tie_ins = wd.findElements(by2);   //选择规格
							for(WebElement J_tie_in : J_tie_ins){	//循环点击选择商品规格
								J_tie_in.click();
								wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[1]/li[1]/span/img")).click();   //点击规格小图标
								wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[2]/li[1]/span")).click();	//选择size
								wd.findElement(By.xpath("//div[@id='J_layer_body']/div[2]/a")).click();   //确定按钮
							}
						}
					}else{
						wd.findElement(By.xpath("//input[@class='package-checked']")).click();  //选择套餐规格
						if(wd.getPageSource().contains("选择商品规格")&&Tools.isExist(wd, by2)){  
							List<WebElement> J_tie_ins = wd.findElements(by2);   //选择规格
							for(WebElement J_tie_in : J_tie_ins){	//循环点击选择商品规格
								J_tie_in.click();
								wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[1]/li[1]/span/img")).click();   //点击规格小图标
								wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[2]/li[1]/span")).click();	//选择size
								wd.findElement(By.xpath("//div[@id='J_layer_body']/div[2]/a")).click();   //确定按钮
							}
						}
					}
					Thread.sleep(500);
					addbutton = wd.findElement(By.linkText("购买套装"));
					addbutton.click();
					numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
					if("2".equals(site[7][i])){
						Assertion.verifyEquals(numb2,numb1+2,"(BUG)Produtc page: test product id = "+site[1][i]);	//如果等于2，说明是加购品，添加到购物车后，数量需要+2
					}
					Assertion.verifyEquals(numb2,numb1+1,"(BUG)Produtc page: test product id = "+site[1][i]);
				}
				if("3".equals(site[6][i])){   //3.预购商品
					Tools.log("Produtc page: test product id = "+site[1][i]);	//预购商品不能加入购物车
					continue;
				}
				if("4".equals(site[6][i])){   //4.0元商品
					/**0元商品*/
					wd.get(site[0][i]);
					Tools.log("Produtc page: test product id = "+site[1][i]);
					Thread.sleep(1000);
					numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
					Thread.sleep(500);
					if(wd.getPageSource().contains("抱歉，当前区域不在配送范围内")){
						changeAddress(wd,area[0][1],area[0][2],area[0][3],area[0][4],area[0][5],area[0][6]);	//商详页切换地址
						Thread.sleep(500);
					}
					if(wd.getPageSource().contains("选择商品规格")&&Tools.isExist(wd, by1)){    //选择赠品规格
						List<WebElement> chooseShops = wd.findElements(by1);   //选择规格
						for(WebElement chooseShop : chooseShops){
							chooseShop.click();
							wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[1]/li[1]/span/img")).click();   //点击规格小图标
							wd.findElement(By.xpath("//div[@id='J_layer_body']/ul[2]/li[1]/span")).click();	//选择size
							wd.findElement(By.xpath("//div[@id='J_layer_body']/div[2]/a")).click();   //确定按钮
						}
					}
					Thread.sleep(500);
					addbutton = wd.findElement(By.xpath("//a[@id='btnAddCart']"));
					addbutton.click();
					Thread.sleep(500);
					numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
					if(numling==0){
						Assertion.verifyEquals(numb2,numb1+1,"(BUG)Produtc page: test product id = "+site[1][i]);	//如果等于2，说明是加购品，添加到购物车后，数量需要+2
						numling=1;
					}else if(numling==1){
						String zero = wd.findElement(By.xpath("//div[@id='addCartBox']//div[@class='conect-title']/h3")).getText();
						Assertion.verifyEquals(zero,"0元购商品数量，最多为1件！","(BUG)Produtc page: test product id = "+site[1][i]);	//0远商品在购物车中只能是一件
					}
				}
			}
		}
	}
	
	 /**
   	 * 搜索页批量加入购物车
   	 * @param wd WebDriver对象
   	 * @param site url地址
   	 */
	public void searchAddCartBeta(WebDriver wd,String[][] site)throws Exception{
		int numb1;
		int numb2;
		WebElement addbutton;
		boolean a;
		
		for(int j=0;j<site[1].length;j++){	//循环excel中第二列的ID个数
			if(!"商品id".equals(site[1][j])){   //剔除第一行标题
				for(int k=1;k<5;k++){   //循环2，3，4，5列
					if(!"null".equals(site[k][j])){  //剔除单元格中为null的值
						wd.get("http://search.beta1.fn/?q="+site[k][j]);	//site[2][0]
						Thread.sleep(1000);
						if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartimg']"))){   //加入购物车
							Tools.log("searchAddCart: test product id = "+site[k][j]);
							numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartimg']"));	
							addbutton.click();
							Thread.sleep(2000);
							numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
							Assertion.verifyEquals(numb2,numb1+1,"(BUG)searchAddCart: add to cart error product id = "+site[k][j]);
							Thread.sleep(1000);
							break;
						}else if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='Jdetail']"))){  //查看详情
							Tools.log("searchAddCart: test product id = "+site[k][j]);
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='Jdetail']"));	
							addbutton.click();
							Tools.waitPageLoad(wd);
							a = wd.getPageSource().contains(site[5][j]);	//验证商详页title是否正确
							Assertion.verifyEquals(a,true,"(BUG)searchAddCart: check detail error product id = "+site[k][j]);
							Thread.sleep(1000);
							break;
						}else if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartpre']"))){  //预购产品
							Tools.log("searchAddCart: test product id = "+site[k][j]);
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartpre']"));	
							addbutton.click();
							Tools.waitPageLoad(wd);
							a = wd.getPageSource().contains(site[5][j]);	//验证商详页title是否正确
							Assertion.verifyEquals(a,true,"(BUG)searchAddCart: cartpre error product id = "+site[k][j]);
							Thread.sleep(1000);
							break;
						}else{
							continue;
						}
					}
				}
			}
		}	
	}
	
	/**
   	 * 馆页批量加入购物车/查看详情
   	 * @param wd WebDriver对象
   	 * @param site url地址
   	 */
	public void guanAddCartBeta(WebDriver wd,String[][] site,String guan,int begin,int end)throws Exception{
		int numb1;
		int numb2;
		WebElement addbutton;
		boolean a;
		
		wd.get(guan);
		for(int j=begin;j<end;j++){	//循环excel中转单产品
			if(!"商品id".equals(site[1][j])){   //剔除第一行标题
				for(int k=1;k<5;k++){   //循环2，3，4，5列
					if(!"null".equals(site[k][j])){  //剔除单元格中为null的值
						if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartimg']"))){  //加入购物车
							Tools.log("guanAddCart: test product id = "+site[k][j]);
							numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();	//获取购物车产品数量
							Tools.scrollToElement(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartimg']"));	//页面移动到指定元素
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartimg']"));
							((JavascriptExecutor)wd).executeScript("arguments[0].click();",addbutton);	//点击加入购物车
							Thread.sleep(2000);
							numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
							Assertion.verifyEquals(numb2,numb1+1,"(BUG)guanAddCart: add to cart error product id = "+site[k][j]);
							Thread.sleep(1000);
							break;
						}else if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='Jdetail']"))){  //查看详情
							Tools.log("guanAddCart: test product id = "+site[k][j]);
							Tools.scrollToElement(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='Jdetail']"));	//页面移动到指定元素
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='Jdetail']"));	
							((JavascriptExecutor)wd).executeScript("arguments[0].click();",addbutton);
							Tools.waitPageLoad(wd);
							a = wd.getPageSource().contains(site[5][j]);	//验证商详页title是否正确
							Assertion.verifyEquals(a,true,"(BUG)guanAddCart: check detail error product id = "+site[k][j]);
							wd.get(guan);	//验证结束后返回到馆页
							Thread.sleep(1000);
							break;
						}else if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartpre']"))){  //预购产品
							Tools.log("guanAddCart: test product id = "+site[k][j]);
							Tools.scrollToElement(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartpre']"));	//页面移动到指定元素
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartpre']"));	
							((JavascriptExecutor)wd).executeScript("arguments[0].click();",addbutton);
							Tools.waitPageLoad(wd);
							a = wd.getPageSource().contains(site[5][j]);	//验证商详页title是否正确
							Assertion.verifyEquals(a,true,"(BUG)guanAddCart: cartpre error product id = "+site[k][j]);
							wd.get(guan);	//验证结束后返回到馆页
							Thread.sleep(1000);
							break;
						}else{
							continue;
						}
					}
				}
			}
		}
	}
	
	/**
   	 * 聚合页批量加入购物车/查看详情
   	 * @param wd WebDriver对象
   	 * @param site url地址
   	 */
	public void juheAddCartBeta(WebDriver wd,String[][] site,String huodong,int begin,int end)throws Exception{
		
		int numb1;
		int numb2;
		WebElement addbutton;
		boolean a;
		
		wd.get(huodong);
		for(int j=begin;j<end;j++){  //循环excel中生鲜产品
			if(!"商品id".equals(site[1][j])){   //剔除第一行标题
				for(int k=1;k<5;k++){   //循环2，3，4，5列
					if(!"null".equals(site[k][j])){  //剔除单元格中为null的值
						if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartimg']"))){  //加入购物车
							Tools.log("juheAddCart: test product id = "+site[k][j]);
							numb1 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
							Tools.scrollToElement(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartimg']"));
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartimg']"));
							((JavascriptExecutor)wd).executeScript("arguments[0].click();",addbutton);
							Thread.sleep(2000);
							if(wd.findElement(By.xpath("//div[@class='fn_dialog']")).isDisplayed()){
								wd.findElement(By.xpath("//div[@class='d_content']//div[@class='c_item']/ul[1]/li[1]/img")).click();  //选择颜色
								new WebDriverWait(wd,10).until(ExpectedConditions.visibilityOf(wd.findElement(By.xpath("//div[@class='fn_dialog']//div[@class='c_item']/ul[1]/div"))));
								wd.findElement(By.xpath("//div[@class='d_content']//div[@class='c_item']/ul[2]/li[1]/span")).click();;  //选择规格
								new WebDriverWait(wd,10).until(ExpectedConditions.visibilityOf(wd.findElement(By.xpath("//div[@class='fn_dialog']//div[@class='c_item']/ul[2]/div"))));
								wd.findElement(By.xpath("//div[@class='d_content']//div[@class='c_bottom']/div/span")).click();;  //点击提交
								Thread.sleep(1000);
							}
							numb2 = Integer.valueOf(wd.findElement(By.xpath("//em[@class='cart_quantity']")).getText()).intValue();   //获取购物车产品数量
							Assertion.verifyEquals(numb2,numb1+1,"(BUG)juheAddCart: add to cart error product id = "+site[k][j]);
							Thread.sleep(1000);
							break;
						}else if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='Jdetail']"))){  //查看详情
							Tools.log("juheAddCart: test product id = "+site[k][j]);
							Tools.scrollToElement(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='Jdetail']"));	//页面移动到指定元素
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='Jdetail']"));
							((JavascriptExecutor)wd).executeScript("arguments[0].click();",addbutton);
							Thread.sleep(1000);
							Tools.waitPageLoad(wd);
							a = wd.getPageSource().contains(site[5][j]);	//验证商详页title是否正确
							Assertion.verifyEquals(a,true,"(BUG)juheAddCart: check detail error product id = "+site[k][j]);
							Thread.sleep(1000);
							wd.get(huodong);	//验证结束后返回到聚合页
							Thread.sleep(1000);
							break;
						}else if(Tools.isExist(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartpre']"))){  //预购产品
							Tools.log("juheAddCart: test product id = "+site[k][j]);
							Tools.scrollToElement(wd, By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartpre']"));	//页面移动到指定元素
							addbutton = wd.findElement(By.xpath("//a[@id="+"'"+"linkOrder_"+site[k][j]+"'"+" and @class='cartpre']"));
							((JavascriptExecutor)wd).executeScript("arguments[0].click();",addbutton);
							Tools.waitPageLoad(wd);
							a = wd.getPageSource().contains(site[5][j]);	//验证商详页title是否正确
							Assertion.verifyEquals(a,true,"(BUG)juheAddCart: check detail error product id = "+site[k][j]);
							Thread.sleep(1000);
							wd.get(huodong);	//验证结束后返回到聚合页
							Thread.sleep(1000);
							break;
						}else{
							continue;
						}
					}
				}
			}
		}
//		String cartinfo = wd.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[2]/div[2]/p[1]")).getText().substring(2, 10);
		String cartinfo = wd.findElement(By.xpath("//div[@class='actdone actmid']/p[1]")).getText().substring(2, 10);
		Tools.log(cartinfo);
		Assertion.verifyEquals(cartinfo,"已满足活动条件！"); //67220
	}
    
}
