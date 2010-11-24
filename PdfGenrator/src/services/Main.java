package services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.services.DaoServices;



public class Main {

	private static final String DOCUMENT_RESULT = ("C:\\testing\\PDF\\OuhPinaise.pdf");
	public static void main(String[] args) throws InvalidParameterException, FileNotFoundException, IOException {
		//define application context for db load
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		DaoServices been = (DaoServices)applicationContext.getBean("daoServices");
		//load section by id
		Services service = new Services(been.load(1L));
		service.generatePdf(new FileOutputStream(DOCUMENT_RESULT));
	}

}
