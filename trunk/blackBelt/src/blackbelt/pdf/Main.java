package blackbelt.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import blackbelt.dao.DaoServices;

import com.itextpdf.text.DocumentException;

public class Main {
	private static final String DOCUMENT_RESULT = ("S:\\DocumentsPourPDF\\HtmlPage.pdf");
	private static final String DOCUMENT_RESULT2 = ("S:\\DocumentsPourPDF\\HtmlPage2.pdf");

	public static void main(String[] args) throws DocumentException, IOException {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		DaoServices been = (DaoServices)applicationContext.getBean("daoServices");
		
		
		//been.saveDataTest();
		
		been.run(17L);
		
		

		PdfGenerator pdfGen = new PdfGenerator();
		pdfGen.generatePdf(been.run(17L), new FileOutputStream(DOCUMENT_RESULT));

	}

}
