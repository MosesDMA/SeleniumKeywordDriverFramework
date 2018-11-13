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
			Log.info("����chrome�����");
//			System.setProperty("webdriver.chrome.driver", ".\\libs\\chromedriver.exe");
//			System.setProperty("webdriver.chrome.driver", "C://driver//chromedriver.exe");
			//������������������ж�Ӧ���������Ͳ���Ҫ����
			driver = new ChromeDriver();
		} catch (Exception e) {
			Log.info("�޷����������" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void openUrl(String object, String data) {
		try {
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Log.info("�򿪲��Է�������ַ");
			driver.get(Constants.URL);
		} catch (Exception e) {
			Log.info("�޷��򿪲��Ի�����ַ" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void click_Login_link(String object, String data) {
		driver.findElement(By.xpath(".//*[@id='u1']/a[text()='��¼']")).click();
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
			Log.info("�ȴ�5���رղ��˳������");
			driver.quit();
		} catch (Exception e) {
			Log.error("�޷��ر������" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void click(String object, String data) {
		try {
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Log.info("���Ԫ��" + object);
		} catch (Exception e) {
			Log.error("�޷����Ԫ��" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void inputUsername(String object, String data) {
		try {
			Log.info("���û������������...");
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.UserName);
		} catch (Exception e) {
			Log.error("�޷������û���" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void inputPassword(String object, String data) {
		try {
			Log.info("���������...");
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.Password);
		} catch (Exception e) {
			Log.error("������޷�����" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void input(String object, String data) {
		try {
			Log.info("��ʼ��"+object+"�����ı�");
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		} catch (Exception e) {
			Log.error("�޷������ı�" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void waitFor(String object, String data) throws InterruptedException {
		try {
			Log.info("�ȴ�����");
			Thread.sleep(3000);
		} catch (Exception e) {
			Log.error("�޷��ȴ�" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
}
