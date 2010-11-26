package blackbelt.mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Used for test.
 */
public class MainTest{
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		MailSender consumer = (MailSender)applicationContext.getBean("mailSender");;
		consumer.start();
		
		DataTest dataTest = (DataTest)applicationContext.getBean("dataTest");
		dataTest.run();
		
	}
	
}
