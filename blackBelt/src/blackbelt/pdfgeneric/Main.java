package blackbelt.pdfgeneric;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itextpdf.text.DocumentException;

import blackbelt.dao.DaoServices;

public class Main {

	public static void main(String arg[]) throws DocumentException, IOException{
		//It use a DAO to read in the database.
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		DaoServices been = (DaoServices)applicationContext.getBean("daoServices");
		
		CoursePdfGenerator cpg = new CoursePdfGenerator();
		
		/* The method run() read the section in the database. It takes the id of the section in argument.
		 * It's important to call this method first to initialise the sections
		 * */ 
		cpg.setSectionList(been.run(1L));
		cpg.createPdf();
	}

}
