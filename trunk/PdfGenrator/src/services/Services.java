package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Scanner;

import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import dao.domainModel.Section;


public class Services {
	
	private Section section;
	private final String CSS_URL = ("S:\\DocumentsPourPDF\\stylePDF.css");
	private PD4ML pdf = new PD4ML();
	
	public Services(Section section){
		this.section = section;
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
	
	public void generatePdf(Section rootSection, OutputStream outputStream){
		format();
	}
	
	public void createHeader(){
		PD4PageMark head = new PD4PageMark();
		head.setHtmlTemplate("<img height='30' width='30' align='right' src='http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png'>");
		head.setAreaHeight(40);
		pdf.setPageHeader(head);
	}
	
	public void createFooter(){
		//footerPages
		PD4PageMark foot = new PD4PageMark();
		foot.setInitialPageNumber(1);
		foot.setPageNumberTemplate("page ${page} ");
		foot.setPageNumberAlignment(1);
		pdf.setPageFooter(foot);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String format(){
		String finalResult = new String("");
		if(this.section.getParent()==null){
			/* header html */
			finalResult+="<html><head><title>"+this.section.getTitle()+"</title></head><body>";
			/*Page de garde*/
			finalResult+=addIntro();
			finalResult+="<br/><br/><h1>"+this.section.getTitle()+"</h1>";
			
		}
		else{
			finalResult+="<br/><br/><h2>"+this.section.getTitle()+"</h2>";
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

}
