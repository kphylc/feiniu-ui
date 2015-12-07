package com.PublicSupportTest;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
	 
	/**
	 * WebDriver帮助类
	 */
	public class Tools {
		
		
		/**
	     * 读取xls文件内容
	     * @param file 想要读取的文件对象
	     * @return 返回Array文件内容
	     */
		public static String[][] xls2Array(File file,int sheet,int row,int unit) {	
			String[][] urltest = new String[row][unit];
			try {
	            FileInputStream fis = new FileInputStream(file); 
	            String cell = null;
	            Workbook rwb = Workbook.getWorkbook(fis);  
	            Sheet rs = rwb.getSheet(sheet);
	                for (int j = 0; j < row; j++) {  
	                    Cell[] cells = rs.getRow(j);
	                    for (int k = 0; k < unit; k++){  
	                    	cell = cells[k].getContents();
	                    	urltest[j][k] = cell;
	                    }
	                }
	            fis.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return urltest;
	    }
		
		public static String[] xls2Array1(File file,int sheet,int row,int unit) {	
			String[] urltest = new String[row];
			try {
	            FileInputStream fis = new FileInputStream(file); 
	            String cell = null;
	            Workbook rwb = Workbook.getWorkbook(fis);  
	            Sheet rs = rwb.getSheet(sheet);
	                for (int j = 0; j < row; j++) {  
	                    Cell[] cells = rs.getRow(j);
	                    for (int k = 0; k < unit; k++){  
	                    	cell = cells[k].getContents();
	                    	urltest[j] = cell;
	                    }
	                }
	            fis.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return urltest;
	    }
		
		public static String[][] xls2Array2(File file,int sheet,int column,int unit) {	
			String[][] urltest = new String[column][unit];
			try {
	            FileInputStream fis = new FileInputStream(file); 
	            String cell = null;
	            Workbook rwb = Workbook.getWorkbook(fis);  
	            Sheet rs = rwb.getSheet(sheet);
	                for (int j = 0; j < column; j++) {  
	                    Cell[] cells = rs.getColumn(j);
	                    for (int k = 0; k < unit; k++){  
	                    	cell = cells[k].getContents();
	                    	urltest[j][k] = cell;
	                    }
	                }
	            fis.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return urltest;
	    }
		
		/**
	     * 输出日志
	     * @param message 要返回的信息
	     */
		public static void log(String message){
			System.out.println(message);
			Reporter.log(message);
		}
		
	/**
	 * 判断是否选中
	 * @param wd WebDriver对象
	 * @param xpath 目标节点的xpath
	 * @return
	 */
	public static boolean isChecked(WebDriver wd,String xpath){
		return wd.findElement(By.xpath(xpath)).isSelected();
	}
	/**
	 * 判断是否可用
	 * @param wd WebDriver对象
	 * @param xpath 目标节点的xpath
	 * @return
	 */
	public static boolean isEnabled(WebDriver wd,String xpath){
		return wd.findElement(By.xpath(xpath)).isEnabled();
	}
	/**
	 * 判断是否存在元素
	 * @param wd WebDriver对象
	 * @param by 目标节点的
	 * @return
	 */
	public static boolean isExist(WebDriver wd,By by){
		try{
			wd.findElement(by);
			return true;
		}catch (NoSuchElementException e) { 
	            return false; 
	       	} 
	}
	

	/**
	 * 关闭所有窗口，除了当前窗口
	 * @param wd WebDriver对象
	 */
	public static void xWindow(WebDriver wd){

		String xWindowHandle = null;
		String current = wd.getWindowHandle();
		Set<String> all = wd.getWindowHandles();
		Iterator<String> iterator = all.iterator();
		while (iterator.hasNext()) {
			String handle = iterator.next();
			if(handle.equals(current)){
				continue;
			}
			xWindowHandle = handle;
			wd.switchTo().window(handle).close();
		}
		all.remove(xWindowHandle);
		//切换到主窗口
		wd.switchTo().window(current);

	}


	/**
	 * 切换窗口
	 * @param wd WebDriver对象
	 * @param title 要切换窗口的标题
	 */
	public static void changeWindowtitle(WebDriver wd,String title){
		String current = wd.getWindowHandle();
		Set<String> all = wd.getWindowHandles();
		Iterator<String> iterator = all.iterator();
		while (iterator.hasNext()) {
			String handle = iterator.next();
			if(handle.equals(current)){
				continue;
			}
			else{
				wd.switchTo().window(handle);
				if(wd.getTitle().contains(title)){
					Tools.log("Change Windows success");
					
					break;
				}
				else{
					continue;
					}
				}
			}
	}
	
	/**
	 * 通过内容切换窗口
	 * @param wd WebDriver对象
	 * @param contain 要切换窗口里面内容
	 */
	public static void changeWindowcontain(WebDriver wd,String contain){
		
		//得到所有的窗口
		Set<String> allWindowsId = wd.getWindowHandles();
		//通过查找页面内容得到新的窗口
		for (String windowId : allWindowsId) {
			wd.switchTo().window(windowId);
			if (wd.getPageSource().contains(contain)){
				wd.switchTo().window(windowId);
				break;
			}
		}
		
	}	
		

	/**
	 * 切换Frame
	 * @param wd WebDriver对象
	 * @param frameName 要切换Frame的名字
	 */
	public static void changeFrame(WebDriver wd,String frameName){
		List<WebElement> frames = wd.findElements(By.tagName("frame"));  
		WebElement ControlPanelFrame = null;
		
		for(WebElement frame : frames){
			if(frame.getAttribute("name") == frameName){
				ControlPanelFrame = frame;
				break;
			}
		}
		
		if (ControlPanelFrame != null)
        {
			wd.switchTo().frame(ControlPanelFrame);
        }    
		
	}
	
	/**
	 * 移动鼠标
	 * @param driver WebDriver对象
	 * @param uiObject Css样式
	 * @throws InterruptedException
	 */
	public static void moveKeysWebElementList(WebDriver driver,By uiObject,int index) throws InterruptedException{
		Actions mouse = new Actions(driver);
        List<WebElement> item= driver.findElements(uiObject);
        mouse.moveToElement(item.get(index)).moveByOffset(10, 3).build().perform();
        driver.wait(30);
	}

	/**
	 * 来点击鼠标
	 * @param driver WebDriver对象
	 * @param uiObject2 Css样式
	 */
	public static void clickKeysWebElementListByCss(WebDriver driver,By uiObject2,int index){
        List<WebElement> item= driver.findElements(uiObject2);
        item.get(index).click();
	}
	
	
	/**
	 * 截图方法
	 * @param drivername WebDriver对象
	 * @param filename 要保存的文件名
	 */
	public static void snapshot(TakesScreenshot drivername, String filename, String address)
	  {

		SimpleDateFormat time = new SimpleDateFormat("yyyy-mm-dd HH,mm,ss");
		String datename = time.format(new Date());
		String format = ".jpg";
//		String address = "E:\\WorkSpace\\jiepin";
	    String currentPath = System.getProperty("user.dir"); //get current work folder
	    log(currentPath);
	    File scrFile = drivername.getScreenshotAs(OutputType.FILE);
	        try {
	            FileUtils.copyFile(scrFile, new File(address+"\\"+filename+datename+format));
	            log("save snapshot path is:"+address+"/"+filename+datename+format);
//	            for(int i=0;i<arrfilename.length;i++){}

	        } catch (IOException e) {
	            log("Can't save screenshot");
	            e.printStackTrace();
	        }
	        finally
	        {
	            log("screen shot finished");
	        }
	  }
	
		/**
		 * 等待显示
		 * @param wd WebDriver对象
		 * @param ele 要等待的元素的By值
		 */
		public static void WaitUtilDisplayed(final WebDriver wd, final By ele)
		  {
			try{
				WebDriverWait wait = new WebDriverWait(wd,10); //等待全选生效
				wait.until((new ExpectedCondition<Boolean>(){
					 public Boolean apply(WebDriver d){
						 if(!wd.findElement(ele).isDisplayed())
							 return true;
							 else return false;
					 }
				 }));
			}catch(WebDriverException e){
				Tools.log("Wait WebElement failed");
				e.printStackTrace();
			}
		  }


		/**
		 * 等待选择
		 * @param wd WebDriver对象
		 * @param by 要等待的元素的By值
		 */
		public static void WaitUtilSelected(final WebDriver wd, final By by)
		{
			try{
				WebDriverWait wait = new WebDriverWait(wd,10); //等待全选生效
				wait.until((new ExpectedCondition<Boolean>(){
					public Boolean apply(WebDriver d){
						if(wd.findElement(by).isSelected())
							return true;
						else return false;
					}
				}));
			}catch(WebDriverException e){
				Tools.log("Wait WebElement failed");
				e.printStackTrace();
			}

		}

		/**
		 * 等待可用
		 * @param wd WebDriver对象
		 * @param by 要等待的元素的xpath值
		 */
		public static void WaitUtilEnabled(final WebDriver wd, final By by)
		{
			try{
				WebDriverWait wait = new WebDriverWait(wd,10); //等待全选生效
				wait.until((new ExpectedCondition<Boolean>(){
					public Boolean apply(WebDriver d){
						if(wd.findElement(by).isEnabled())
							return true;
						else return false;
					}
				}));
			}catch(WebDriverException e){
				Tools.log("Wait WebElement failed");
				e.printStackTrace();
			}

		}

		/**
		 * 等待定位到元素
		 * @param wd WebDriver对象
		 * @param ele 要等待的元素的xpath值
		 */
		public static WebElement WaitUtilFind(WebDriver wd, final By ele)
		{
			try{
				WebDriverWait wait = new WebDriverWait(wd,10);
				wait.until((new ExpectedCondition<WebElement>(){
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(ele);
					}
				}));
			}catch(NoSuchElementException e){
				Tools.log("Wait WebElement failed");
				e.printStackTrace();
			}
			return wd.findElement(ele);
		}

		/**
		 * 判断元素是否在指定时间内存在。
		 * 只要元素出现在dom结构中（不管属性是显示还是隐藏） 马上返回true
		 * 在指定时间仍不存在与dom结构则返回false。
		 * 适用于ajax
		 * @param by 元素
		 * @param seconds 指定秒数
		 * @return 出现返回true 否则返回false
		 */
		public static boolean waitForElementPresence(WebDriver wd,final By by, int seconds)
		{
			try {
				new WebDriverWait(wd, seconds).until(ExpectedConditions.presenceOfElementLocated(by));
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		/**
		 * 判断元素在指定时间是否显示
		 * 元素是否在指定时间内显示（存在dom结构且属性为显示）马上返回true
		 * 如果到指定时间仍未显示（不存在与dom结构 或者存在于dom结构但属性为‘隐藏’）则返回false
		 * 适用于ajax
		 * @param by 元素
		 * @param seconds 指定秒数
		 * @return 出现返回true 否则返回false
		 */
		public static boolean waitForElementVisible(WebDriver wd,final By by, int seconds)
		{
			try {
				new WebDriverWait(wd, seconds).until(ExpectedConditions.visibilityOfElementLocated(by));
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		/**
		 * 判断元素是否在指定时间内隐藏或者消失
		 * 如果元素消失(不存在于dom结构 或者属性为 ‘隐藏’)则立刻返回true
		 * 如果指定时间后元素仍然存在（存在于dom结构且属性为‘显示’）则返回false
		 * @param by 元素
		 * @param seconds 秒数
		 * @return
		 */
		public static boolean waitForElementInvisible(WebDriver wd,final By by, int seconds)
		{
			try {
				new WebDriverWait(wd, seconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		/**
		 * 判断页面是否加载完成
		 */
		public static boolean waitPageLoad(WebDriver wd) {
			
			if(((JavascriptExecutor)wd).executeScript("return document.readyState").equals("complete")){
				return true;
			}else
				return false;
		}
		
		/**
		 * 等待提交后新窗口出现
		 */
		public static boolean waitTitle(WebDriver wd, int seconds, String title) {
			
			try {
				new WebDriverWait(wd, seconds).until(ExpectedConditions.titleIs(title));
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		/**
		 * 等待Alert框出现
		 */
		public Boolean waitAlertPresent(WebDriver wd,int seconds) {

			try {
				new WebDriverWait(wd, seconds).until(ExpectedConditions.alertIsPresent());
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * 处理字符串,只留下数字
		 * @param str 字符串
		 */
			public static String catchnumber(String str) {
				str=str.trim();
				String str2="";
				if(str != null && !"".equals(str)){
					for(int i=0;i<str.length();i++){
						if(str.charAt(i)>=48 && str.charAt(i)<=57){
							str2+=str.charAt(i);
						}
					}
	
				}
				return str2;
			}


		/**
		 * 处理alert框体
		 * @param wd webdriver
		 */
		public String closeAlertAndGetItsText(WebDriver wd) throws Exception{
			Boolean acceptNextAlert = true;
			try {
				Thread.sleep(1000);
				Alert alert = wd.switchTo().alert();
				String alertText = alert.getText();
				if (acceptNextAlert) {
					Thread.sleep(1000);
					alert.accept();
				} else {
					Thread.sleep(1000);
					alert.dismiss();
				}
				return alertText;
			}catch(NoSuchElementException e){
				e.printStackTrace();
			}finally {
				acceptNextAlert = true;
			}
			return null;
		}

		/**
		 * 查看alert框体是否存在
		 * @param wd webdriver
		 */
		public boolean isAlertPresent(WebDriver wd){  
	        try  
	        {  
	            wd.switchTo().alert();  
	            return true;  
	        }     
	        catch (NoAlertPresentException Ex)  
	        {  
	            return false;  
	        }
	    }   
		
		/**
		 * 获取alert内容
		 * @param wd webdriver
		 */
		public String getAlert(WebDriver wd){  
	        Alert alert = wd.switchTo().alert();
	        String str = alert.getText();

	        alert.accept();
	        return str;
	    }  
		
		/**
		 * 操作滚动条  (JS/Action)
		 */
		public static void scrollToElement(WebDriver wd,By by) {

			((JavascriptExecutor)wd).executeScript("arguments[0].scrollIntoView();",wd.findElement(by));
			
		}
		
		public boolean dealPotentialAlert(WebDriver driver,boolean option) {
			boolean flag = false;
			try {
				Alert alert = driver.switchTo().alert();
				if (null == alert)
					throw new NoAlertPresentException();
				try {
					if (option) {
						alert.accept();
						Tools.log("Accept the alert: " + alert.getText());
					} else {
						alert.dismiss();
						Tools.log("Dismiss the alert: " + alert.getText());
					}
					flag = true;
				} catch (WebDriverException ex) {
					if (ex.getMessage().startsWith("Could not find"))
						Tools.log("There is no alert appear!");
					else
						throw ex;
				}
			} catch (NoAlertPresentException e) {
				Tools.log("There is no alert appear!");
			}
			return flag;
		}

		
		
		
}
	
	
	
	
	
	
