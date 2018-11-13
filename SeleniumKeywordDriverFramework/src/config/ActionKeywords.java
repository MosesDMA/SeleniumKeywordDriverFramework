package config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import executionEngine.DriverScript;
import utility.Log;

import static executionEngine.DriverScript.OR;

public class ActionKeywords {

	public static WebDriver driver;
	
	/**
	 * 
	 */
	public static void openBrowser(String object, String data) {
		try {
			Log.info("启动chrome浏览器");
//			System.setProperty("webdriver.chrome.driver", ".\\libs\\chromedriver.exe");
//			System.setProperty("webdriver.chrome.driver", "C://driver//chromedriver.exe");
			//如果环境变量中设置有对应的驱动，就不需要配置
			driver = new ChromeDriver();
		} catch (Exception e) {
			Log.info("无法启动浏览器" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void openUrl(String object, String data) {
		try {
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Log.info("打开测试服务器地址");
			driver.get(Constants.URL);
		} catch (Exception e) {
			Log.info("无法打开测试环境地址" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void click_Login_link(String object, String data) {
		driver.findElement(By.xpath(".//*[@id='u1']/a[text()='登录']")).click();
		driver.findElement(By.id("TANGRAM__PSP_10__footerULoginBtn")).click();
	}
	
	public static void input_Username(String object, String data) {
		Cookie c1 = new Cookie("BAIDUID",Constants.BAIDUID);
		driver.manage().addCookie(c1);
	}
	
	public static void input_Password(String object, String data) {
		Cookie c2 = new Cookie("BDUSS",Constants.BDUSS);
		driver.manage().addCookie(c2);
	}
	
	public static void click_Submit(String object, String data) {
		driver.navigate().refresh();
	}
	
	public static void closeBrowser(String object, String data) {
		try {
			Thread.sleep(5000);
			Log.info("等待5秒后关闭并退出浏览器");
			driver.quit();
		} catch (Exception e) {
			Log.error("无法关闭浏览器" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void click(String object, String data) {
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Log.info("点击元素" + object);
		} catch (Exception e) {
			Log.error("无法点击元素" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void inputUsername(String object, String data) {
		try {
			Log.info("在用户名输入框输入...");
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.UserName);
		} catch (Exception e) {
			Log.error("无法输入用户名" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void inputPassword(String object, String data) {
		try {
			Log.info("密码框输入...");
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.Password);
		} catch (Exception e) {
			Log.error("密码框无法输入" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void input(String object, String data) {
		try {
			Log.info("开始在"+object+"输入文本");
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		} catch (Exception e) {
			Log.error("无法输入文本" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void waitFor(String object, String data) throws InterruptedException {
		try {
			Log.info("等待三秒");
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("无法等待" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
}
