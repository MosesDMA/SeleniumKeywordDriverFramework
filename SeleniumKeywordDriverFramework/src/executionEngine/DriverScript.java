package executionEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtil;
import utility.Log;

public class DriverScript {

	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	//反射
	public static Method method[];
	
	public static Properties OR;
	public static String sPageObject;
	
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static boolean bResult;
	
	public static String sData;
	
	public DriverScript() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
	}
	/**
	 * 测试
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void main(String[] args) throws Exception {

		//加载log4j.xml配置文件
		DOMConfigurator.configure("log4j.xml");
		ExcelUtil.setExcelFile(System.getProperty("user.dir") + Constants.Path_TestData);
		//创建一个文件输入流对象
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + Constants.OR_Path);
		//创建一个Properties对象
		OR = new Properties(System.getProperties());
		//加载全部对象仓库文件
		OR.load(fs);
		//执行用例
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
		
//		WebDriver driver = new ChromeDriver();
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
//		driver.get("https://www.baidu.com");
//		System.out.println(driver.manage().getCookies());
		//通过抓包工具fiddler，找到Host为passport.baidu.com的RUL,在右侧窗口查看该请求的Cookie
		//也可以通过浏览器中查找cookie,不能实时
		//添加cookie
//		Cookie c1 = new Cookie("BAIDUID","BAIDUID=9A3F2636C4FCE5ADF0CF5F19FE9502AE:FG=1");
//		Cookie c2 = new Cookie("BDUSS","V2Vi1JRjZvTXNmVzVQSHhkWVRTUzFTeVJ-U1BkTGREM0hmYy1VdkV4ajFHc2hiQVFBQUFBJCQAAAAAAAAAAAEAAAA-M4sKeWluZmFuZ3d1YwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPWNoFv1jaBbTT");
//		driver.manage().addCookie(c1);
//		driver.manage().addCookie(c2);
//		driver.navigate().refresh();
//		Thread.sleep(2000);
		
//		driver.findElement(By.xpath(".//*[@id='u1']/a[text()='登录']")).click();
//		driver.findElement(By.id("TANGRAM__PSP_10__footerULoginBtn")).click();
//		driver.findElement(By.id("TANGRAM__PSP_10__userName")).sendKeys("");
//		driver.findElement(By.id("TANGRAM__PSP_10__password")).sendKeys("");
		//获取验证码
//		WebElement verifyCodeImg = driver.findElement(By.id("TANGRAM__PSP_10__verifyCodeImg"));
//		System.out.println(verifyCodeImg.getAttribute("src"));
//		driver.findElement(By.id("TANGRAM__PSP_10__verifyCode")).sendKeys(verifyCodeImg.getAttribute("src"));
//		driver.findElement(By.id("TANGRAM__PSP_10__submit")).click();
		
//		String username = driver.findElement(By.className("user-name")).getText();
//		System.out.println("username = " + username);
//		System.out.println(driver.manage().getCookies());
//		Thread.sleep(3000);
//		driver.quit();
	}
	
	private void execute_TestCase() throws Exception {
		//获取测试用例数量
		int iTotalTestCases = ExcelUtil.getRowCount(Constants.Sheet_TestCases);
		//外层for循环，有多少个测试用例就执行多少次循环
		for(int iTestCase=1;iTestCase<iTotalTestCases;iTestCase++) {
			//从Test Case表获取测试ID
			bResult = true;
			sTestCaseID = ExcelUtil.getCellData(iTestCase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
			//获取当前测试用例的Run Mode值
			sRunMode = ExcelUtil.getCellData(iTestCase, Constants.Col_RunMode, Constants.Sheet_TestCases);
			//Run Mode的值控制用例是否被执行
			if(sRunMode.equals("Yes")) {
				//只有当Run Mode的单元格数据是Yes,下面代码才被执行
				iTestStep = ExcelUtil.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtil.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				
				bResult = true;
				//这个for循环的次数就等于测试用例的步骤数
				for(;iTestStep<iTestLastStep;iTestStep++) {
					sActionKeyword = ExcelUtil.getCellData(iTestStep, Constants.Col_ActionKeyword, Constants.Sheet_TestSteps);
					sPageObject = ExcelUtil.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
					sData = ExcelUtil.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
					execute_Actions();
					if(bResult == false) {
						ExcelUtil.setCellData(Constants.KEYWORD_FAIL, iTestCase, Constants.Col_Result, Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
						break;
					}
				}
				if(bResult == true) {
					ExcelUtil.setCellData(Constants.KEYWORD_PASS, iTestCase, Constants.Col_Result, Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);
				}
			}
			
		}
	}
	
	private static void execute_Actions() throws Exception {
		int i;
		for(i = 0; i< method.length; i++) {
			if(method[i].getName().equals(sActionKeyword)) {
				//一旦匹配到关键字，并传递页面对象参数和动作关键字参数
				method[i].invoke(actionKeywords, sPageObject, sData);
				if(bResult == true) {
					ExcelUtil.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					break;
				}else {
					ExcelUtil.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					ActionKeywords.closeBrowser("", "");
					break;
				}
			}
		}
		if(i>=method.length) {
			bResult = false;
			ExcelUtil.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
		}
	}
}
