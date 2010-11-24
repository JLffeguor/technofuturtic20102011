package services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidParameterException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import dao.domainModel.Section;
import dao.services.DaoServices;



public class Main {

	/**
	 * @param args
	 */
	private static final String DOCUMENT_RESULT = ("C:\\testing\\PDF\\OuhPinaise.pdf");
	public static void main(String[] args) throws InvalidParameterException, FileNotFoundException, IOException {
		//define application context for db load
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		DaoServices been = (DaoServices)applicationContext.getBean("daoServices");
		//load section by id
		Section section = been.load(25L);
		
		//make a service for a section ---> TO DO : parse ....  -_-'
		Services service = new Services(section);
		service.printSection();// 4 testing
		
		String css = service.loadStyleCss(); //loading the css file, for inject him in pdf later
		
		StringReader maPageHtmlPourave = new StringReader(service.format()); //service.format make the html source and return him, we 'cast' all that to a stringReader
		
	
//		String test1 = new String("<html><head><title>Exemple de titre</title></head><body><h1>mon gros titre</h1><br/><p>blablabla blabla</p></body></html>");
//		StringReader test= new StringReader(test1);
			
		PD4ML pdf = new PD4ML();

		pdf.addStyle(css, true);
		
		
		//headerPages
		PD4PageMark head = new PD4PageMark();
		head.setHtmlTemplate("<img height='30' width='30' align='right' src='http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png'>");
		head.setAreaHeight(40);
		pdf.setPageHeader(head);
		
		//footerPages
		PD4PageMark foot = new PD4PageMark();
		foot.setInitialPageNumber(1);
		foot.setPageNumberTemplate("page ${page} ");
		foot.setPageNumberAlignment(1);
		pdf.setPageFooter(foot);
		
		
		
		
		pdf.render(maPageHtmlPourave, new FileOutputStream(DOCUMENT_RESULT));
	}

}
