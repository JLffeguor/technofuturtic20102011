package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.util.Scanner;

import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import dao.domainModel.Section;


public class Services {
	
	private Section section;
	private final String CSS_URL = ("S:\\DocumentsPourPDF\\stylePDF.css");
	private PD4ML pdf;
	
	public Services(Section section){
		this.section = section;
		this.pdf = new PD4ML();
	}
	
//	public  void printSection(){
//		System.out.println(this.section.getTitle());
//		System.out.println(this.section.getBody());
//		System.out.println("********************************");
//		for(Section s : section.getSubSections()){
//			Section sub = new Section(s);
//			Services serv = new Services(sub);
//			serv.printSection();
//		}
//	}
	
	public void generatePdf(OutputStream outputStream) throws InvalidParameterException, IOException{
		pdf.addStyle(loadStyleCss(), true);
		StringReader pageHtml = new StringReader(format());
		createHeader();
		createFooter();
		pdf.enableImgSplit(false);
		pdf.render(pageHtml, outputStream);
	}
	
	public void createHeader(){
		PD4PageMark head = new PD4PageMark();
		head.setPagesToSkip(1);
		head.setHtmlTemplate("<img height='30' width='45' align='right' src='http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png'>");
		head.setAreaHeight(40);
		pdf.setPageHeader(head);
	}
	
	public void createFooter(){
		//footerPages
		PD4PageMark foot = new PD4PageMark();
		foot.setPagesToSkip(1);
		foot.setInitialPageNumber(1);
		foot.setPageNumberTemplate("page ${page} ");
		foot.setPageNumberAlignment(2);
		pdf.setPageFooter(foot);
	}
		
	public String format(){
		String finalResult = new String("");
		if(this.section.getParent()==null){
			/* header html */
			finalResult+="<html><head><title>"+this.section.getTitle()+"</title></head><body>";
			/*Page de garde*/
			finalResult+=addIntro();
			finalResult+="<br/><h1>"+this.section.getTitle()+"</h1>";
			
		}
		else{
			finalResult +=  createTitle(section);
//			finalResult += "<br/><br/><h2>"+this.section.getTitle()+"</h2>";
		}
		
		CourseTextFormatter ctf = new CourseTextFormatter("", this.section.getBody());
		
		
		finalResult += ctf.format();
		
		
		for(Section s : this.section.getSubSections()){
			Services serv = new Services(s);
			finalResult+=serv.format();
		}
		if(this.section.getParent()==null){
			finalResult+="</body></html>";
		}
		return finalResult;
	}
	
	public String loadStyleCss(){
		String css = new String("");
		File file = new File(CSS_URL);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()){
				css+=scanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException ("erreur lors du chargement de la feuille de style");
		}
		
		return css;
	}
	public String addIntro(){
		String intro = new String("");
		intro +="<div align='center' class='logo'><b>Black<span class='grey'>Belt</span><br/>Factory</b></div><br/><br/><div align='center'><img align='middle' src='http://antisosial.free.fr/projet/asianWomanSword.jpg'></div><pd4ml:page.break>";
		
		return intro;
	}
	
	public String createTitle(Section currentSection){
		int size = checkTheTitleSize(currentSection);
		String title = null;
		
		switch(size){
		case 0:
			title = "<br/><h1>"+currentSection.getTitle()+"</h1>";
			break;
		case 1:
			title = "<br/><h2>"+currentSection.getTitle()+"</h2>";
			break;
		case 2:
			title = "<br/><h3>"+currentSection.getTitle()+"</h3>";
			break;
		default:
			title = "<br/><h4>"+currentSection.getTitle()+"</h4>";
			break;
		}
		
		return title;
	}
	
	public int checkTheTitleSize(Section currentSection){
		int result = 0;
		
		while(currentSection.getParent() != null){
			currentSection = currentSection.getParent();
			result++;
		}
		
		return result;
	}

}
