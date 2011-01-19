package services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.domainModel.User;
import dao.services.DaoServices;



public class Main {

	private static final String DOCUMENT_RESULT = ("C:\\testing\\PDF\\pdf1.pdf");
	private static final String DOCUMENT_RESULT2 = ("C:\\testing\\PDF\\pdf2.pdf");
	private static final String DOCUMENT_RESULT3 = ("C:\\testing\\PDF\\pdf3.pdf");
	private static final String DOCUMENT_RESULT4 = ("C:\\testing\\PDF\\pdf4.pdf");
	public static void main(String[] args) throws InvalidParameterException, FileNotFoundException, IOException {
		//USER
		User yo = new User("Gusbin","Johan",6,null);
		User ju = new User("Van","Julien",2,"http://medlem.spray.se/iso/pikachu.jpg");
		//User ju = new User("Van","Julien",2,"http://www.aero-club-neuchatel.ch/spip/IMG/bmp_2008-3-3_13-10-33-703.bmp");
		//define application context for db load
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		DaoServices been = (DaoServices)applicationContext.getBean("daoServices");
		//load section by id
		CoursePdfGenerator service = new CoursePdfGenerator(been.load(1L),yo,true);
		service.generatePdf(new FileOutputStream(DOCUMENT_RESULT));
		
//		CoursePdfGenerator service2 = new CoursePdfGenerator(been.load(1L),ju,false);
//		service2.generatePdf(new FileOutputStream(DOCUMENT_RESULT2));
//		
//		CoursePdfGenerator service3 = new CoursePdfGenerator(been.load(1L),yo,true,"http://medlem.spray.se/iso/pikachu.jpg");
//		service3.generatePdf(new FileOutputStream(DOCUMENT_RESULT3));
//		
//		CoursePdfGenerator service4 = new CoursePdfGenerator(been.load(1L),ju,true,"http://artdec.ca/boutique/images/terre_pourrie.jpg");
//		service4.generatePdf(new FileOutputStream(DOCUMENT_RESULT4));
		
	}

}
