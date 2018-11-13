package utility;

import org.apache.log4j.Logger;

public class Log {

	//初始化log4j log
	private static Logger log = Logger.getLogger(Log.class.getName());
	//运行测试用例之前的日志输出
	public static void startTestCase(String sTestCaseName) {
		log.info("----------------------");
		log.info(sTestCaseName + "开始");
		log.info("----------------------");
	}
	//用例执行结束后日志输出
	public static void endTestCase(String sTestCaseName) {
		log.info("----------------------");
		log.info(sTestCaseName + "结束");
		log.info("----------------------");
	}
	
	//不同日志级别的方法
	public static void info(String message) {
		log.info(message);
	}
	
	public static void warn(String message) {
		log.warn(message);
	}
	
	public static void error(String message) {
		log.error(message);
	}
	
	public static void fatal(String message) {
		log.fatal(message);
	}
	
	public static void debug(String message) {
		log.debug(message);
	}
}
