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
	//����
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
	 * ����
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

		//����log4j.xml�����ļ�
		DOMConfigurator.configure("log4j.xml");
		ExcelUtil.setExcelFile(System.getProperty("user.dir") + Constants.Path_TestData);
		//����һ���ļ�����������
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + Constants.OR_Path);
		//����һ��Properties����
		OR = new Properties(System.getProperties());
		//����ȫ������ֿ��ļ�
		OR.load(fs);
		//ִ������
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
		
//		WebDriver driver = new ChromeDriver();
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
//		driver.get("https://www.baidu.com");
//		System.out.println(driver.manage().getCookies());
		//ͨ��ץ������fiddler���ҵ�HostΪpassport.baidu.com��RUL,���Ҳര�ڲ鿴�������Cookie
		//Ҳ����ͨ��������в���cookie,����ʵʱ
		//���cookie
//		Cookie c1 = new Cookie("BAIDUID","BAIDUID=9A3F2636C4FCE5ADF0CF5F19FE9502AE:FG=1");
//		Cookie c2 = new Cookie("BDUSS","V2Vi1JRjZvTXNmVzVQSHhkWVRTUzFTeVJ-U1BkTGREM0hmYy1VdkV4ajFHc2hiQVFBQUFBJCQAAAAAAAAAAAEAAAA-M4sKeWluZmFuZ3d1YwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPWNoFv1jaBbTT");
//		driver.manage().addCookie(c1);
//		driver.manage().addCookie(c2);
//		driver.navigate().refresh();
//		Thread.sleep(2000);
		
//		driver.findElement(By.xpath(".//*[@id='u1']/a[text()='��¼']")).click();
//		driver.findElement(By.id("TANGRAM__PSP_10__footerULoginBtn")).click();
//		driver.findElement(By.id("TANGRAM__PSP_10__userName")).sendKeys("");
//		driver.findElement(By.id("TANGRAM__PSP_10__password")).sendKeys("");
		//��ȡ��֤��
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
		//��ȡ������������
		int iTotalTestCases = ExcelUtil.getRowCount(Constants.Sheet_TestCases);
		//���forѭ�����ж��ٸ�����������ִ�ж��ٴ�ѭ��
		for(int iTestCase=1;iTestCase<iTotalTestCases;iTestCase++) {
			//��Test Case���ȡ����ID
			bResult = true;
			sTestCaseID = ExcelUtil.getCellData(iTestCase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
			//��ȡ��ǰ����������Run Modeֵ
			sRunMode = ExcelUtil.getCellData(iTestCase, Constants.Col_RunMode, Constants.Sheet_TestCases);
			//Run Mode��ֵ���������Ƿ�ִ��
			if(sRunMode.equals("Yes")) {
				//ֻ�е�Run Mode�ĵ�Ԫ��������Yes,�������ű�ִ��
				iTestStep = ExcelUtil.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtil.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				
				bResult = true;
				//���forѭ���Ĵ����͵��ڲ��������Ĳ�����
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
				//һ��ƥ�䵽�ؼ��֣�������ҳ���������Ͷ����ؼ��ֲ���
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
