package javablackbelt.inventory.test.main;

import javablackbelt.inventory.itemservice.Tester;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Tester theItemTest = (Tester)applicationContext.getBean("tester");
		
		theItemTest.createData();
		theItemTest.performTests();
	}
}
