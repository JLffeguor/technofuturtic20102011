package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.util.GregorianCalendar;
import java.util.Scanner;

import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import dao.domainModel.*;


public class Services {
	
	private Section section;
	private final String CSS_URL = ("S:\\DocumentsPourPDF\\stylePDF.css");
	private final String ASIAN_WOMAN_IMG_URL="http://www.blackbeltfactory.com/VAADIN/themes/blackbelt/image/bgphoto/asianWomanSword.jpg";
	private final String BBF_LOGO="http://www.blackbeltfactory.com/imgs/logos/BlackBeltFactory-logo-950x602.png";
	private PD4ML pdf;
	private final String licence = "If you don't have access to the course in blackbeltfactory, using this pdf is forbiden. <br />" +
									"If you are witness of an illegal use, please report it to blackbelt website";
	private String urlLogoPdf;
	private User user;
	
	public Services(Section section, User user){
		this.section = section;
		this.pdf = new PD4ML();
		this.user = user;
		if(this.user.useCustomLogo()){
			this.urlLogoPdf=this.user.getLogoUrl();
		}
		else{
			urlLogoPdf= ASIAN_WOMAN_IMG_URL;
		}
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
		//Load and add the CSS styles
		pdf.addStyle(loadStyleCss(), true);
		String s = format();
		System.out.println(s); //Show generated HTML on console
		StringReader pageHtml = new StringReader(format());
		createHeader();
		createFooter();
		pdf.enableImgSplit(false); //Do not split an image
		pdf.render(pageHtml, outputStream); //Start creating PDF
	}
	
	public void createHeader(){
		PD4PageMark head = new PD4PageMark();
		head.setPagesToSkip(1); //Skip the first page
		head.setHtmlTemplate("<img height='50' width='50' align='right' src='"+user.getLogoUrl()+"'>"); //Add the logo of the user company
		head.setAreaHeight(45); //Adjust the height
		pdf.setPageHeader(head); //Add header
	}
	
	public void createFooter(){
		//footerPages
		PD4PageMark foot = new PD4PageMark();
		foot.setPagesToSkip(1); //Skip the first page
		foot.setInitialPageNumber(1);
		//Add a table with 3 cells : 1. the blackbelt logo, 2. the licence, 3. the page number
		foot.setHtmlTemplate("<table width='100%'><tr><td><img height='30' width='50' align='left' src='"+BBF_LOGO+"'></td><td class='licence'>"+licence+"</td><td align='right' class='valignBottom'>${page}<span class='grey'> /${total}</span></td></tr></table>");
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
		intro +="<div align='center' class='logo'><br/><br/><img width='200' height='127' src='"+BBF_LOGO+"'></div><br/><br/><h1 align='center'>"+this.section.getCategoryTitle()+"</h1><br/><h2 align='center'>"+this.section.getTitle()+"</h2><br/><br/><br/><table align='center'><tr><td><img width='280' height='320' align='middle' src='"+this.urlLogoPdf+"'></td></tr></table><br/><br/>";
		//intro+="<div align='left' class='coverPageLog'><img align='left' src='"+this.user.getLevelUrl()+"'><span>"+this.user.getFirstName()+"</span><br/><span>"+this.user.getName()+"</span></div><pd4ml:page.break>";
		intro+="<table width='100%'><tr><td width='45px'><img width='45px' height='35' align='left' src='"+this.user.getLevelUrl()+"'></td><td class='valignMiddle'><span class='small'>Download by :</span><br/><i>"+this.user.getFirstName()+" "+this.user.getName()+"</i><br/><span class='small'>"+this.getCurentDate()+"</span></td></tr></table><pd4ml:page.break>";
		return intro;
	}
	
	public String createTitle(Section currentSection){
		int size = checkTheTitleSize(currentSection); //Check the title importance
		String title = null;
		
		//And define the title size.
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
		
		//See how much parent have a section. With this number we can see the importance of a title and defined its size.
		while(currentSection.getParent() != null){
			currentSection = currentSection.getParent();
			result++;
		}
		
		return result;
	}
	public String getCurentDate(){
		String sDate="";
		GregorianCalendar gc= new GregorianCalendar();
		sDate=gc.get(GregorianCalendar.MONTH)+"/"+gc.get(GregorianCalendar.DAY_OF_MONTH)+"/"+gc.get(GregorianCalendar.YEAR);
		return sDate;
	}

}
