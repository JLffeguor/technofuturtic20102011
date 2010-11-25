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
	private final String licence = "If you don't have access to the course in blackbeltfactory, using this pdf is forbiden. " +
									"If you are witness of an illegal use, please report it to blackbelt website";
	private String urlLogoUtilisateur = "http://medlem.spray.se/iso/pikachu.jpg";
	private String urlLogoPdf = "http://www.blackbeltfactory.com/imgs/logos/BlackBeltFactory-logo-200x127.png";
	private User user;
	
	public Services(Section section, User user){
		this.section = section;
		this.pdf = new PD4ML();
		this.user = user;
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
		String s = format();
		System.out.println(s);
		StringReader pageHtml = new StringReader(format());
		createHeader();
		createFooter();
		pdf.enableImgSplit(false);
		pdf.render(pageHtml, outputStream);
	}
	
	public void createHeader(){
		PD4PageMark head = new PD4PageMark();
		head.setPagesToSkip(1); //Skip the first page
		head.setHtmlTemplate("<img height='50' width='50' align='right' src='"+urlLogoUtilisateur+"'>"); //Add the logo of the user company
		head.setAreaHeight(45); //Adjust the height
		pdf.setPageHeader(head); //Add header
	}
	
	public void createFooter(){
		//footerPages
		PD4PageMark foot = new PD4PageMark();
		foot.setInitialPageNumber(1);
		foot.setPageNumberTemplate("${page} "); //Add the number of the page
		foot.setPageNumberAlignment(2); //Align the page left
		foot.setHtmlTemplate("<img height='50' width='90' align='left' src='"+urlLogoPdf+"'>"); //Add the Blackbelt logo
		foot.setAreaHeight(45); //Adjust the height
		pdf.setPageFooter(foot); //Add footer
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
			Services serv = new Services(s, this.user);
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
