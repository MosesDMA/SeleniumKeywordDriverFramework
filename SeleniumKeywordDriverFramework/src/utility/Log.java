package utility;

import org.apache.log4j.Logger;

public class Log {

	//��ʼ��log4j log
	private static Logger log = Logger.getLogger(Log.class.getName());
	//���в�������֮ǰ����־���
	public static void startTestCase(String sTestCaseName) {
		log.info("----------------------");
		log.info(sTestCaseName + "��ʼ");
		log.info("----------------------");
	}
	//����ִ�н�������־���
	public static void endTestCase(String sTestCaseName) {
		log.info("----------------------");
		log.info(sTestCaseName + "����");
		log.info("----------------------");
	}
	
	//��ͬ��־����ķ���
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
