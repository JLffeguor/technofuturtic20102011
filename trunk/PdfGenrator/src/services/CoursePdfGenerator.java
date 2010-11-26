package services;


import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.security.InvalidParameterException;
import java.util.GregorianCalendar;


import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import dao.domainModel.*;

/** Generates an html String with course sections, and use PD4ML to transform the html into a pdf document */ 
public class CoursePdfGenerator {
	
	private Section startSection;
	private final String ASIAN_WOMAN_IMG_URL="http://www.blackbeltfactory.com/VAADIN/themes/blackbelt/image/bgphoto/asianWomanSword.jpg";
	private final String BBF_LOGO="http://www.blackbeltfactory.com/imgs/logos/BlackBeltFactory-logo-950x602.png";
	private PD4ML pd4ml;
	private final String FOOTER_TEXT = "If you don't have access to the course in blackbeltfactory, using this pdf is forbiden. <br />" +
									"If you are witness of an illegal use, please report it to blackbelt website";
	private String urlLogoPdf;
	private User user;
	
	private boolean doTheUserWantACoverPage;;
	
	private final String CSS = "h1,h2,h3,h4 {" +
	"	color: #AA0000; /* #AA0000 = blackbelt_dark_red  */" +
	"	font-weight: bold;" +
	"}"+
	"h1 { "+
		"font-size: 30px;"+
	"}"+
	"h2 { "+
		"font-size: 24px; "+
	"}"+
	"h3 {"+
		"font-size: 18px;"+
	"}"+
	"h4 {"+
		"font-size: 15px;"+
	"}"+
	"#gardeTitle{"+
		"font-size:32;"+
	"}"+
	"body{"+
		"font-family:arial, helvetica, verdana, sans-serif;"+
		"font-size:12px;"+
	"}"+
	"p{"+
		"line-height:17px;"+
	"}"+
	"a {"+
		"text-decoration: none;"+
		"color: #AA0000;"+
	"}"+

	".titre{"+
		"font-size:26px;"+
		"font-family:helvetica,Times_New_Roman;"+
	"}"+
	".logo{"+
		"font-size:32;"+
		"font-family:helvetica,Aharoni,Impact;"+
	"}"+
	".grey{"+
		"color:#787878;"+
	"}"+
	".contentQuote {"+
		"background-color: #EEE;"+
		"margin-left: 25px;"+
		"padding: 14px;"+
		"font-family: Times;"+
		"font-style: italic;"+
	"}"+
	"pre.contentProgramListing {"+
		"background-color: #EEE;"+
		"margin-bottom: 14px;"+
		"margin-left: 25px;"+
		"padding: 14px;"+
		"font-size: 12px; /* smaller to have longer lines */"+
		"overflow: auto; /* scroll bar if too wide */"+
	"}"+
	".licence{"+
		"font-size:8px;"+
		"color: #9E9E9E;"+
		"vertical-align: bottom;"+
		"padding-left: 15px;"+
	"}"+
	".valignBottom{"+
		"vertical-align: bottom;"+
	"}"+
	".valignMiddle{"+
		"vertical-align:middle;"+
	"}"+
	".small{"+
		"font-size:10px;"+
	"}";
	
	public CoursePdfGenerator(Section startSection, User user, boolean coverPageOrNot){
		this.startSection = startSection;
		this.pd4ml = new PD4ML();
		this.user = user;
		this.doTheUserWantACoverPage = coverPageOrNot;
		
		if(this.user.useCustomLogo()){
			this.urlLogoPdf=this.user.getLogoUrl();
		}
		else{
			urlLogoPdf= ASIAN_WOMAN_IMG_URL;
		}
	}
	
	//If the user want a specific logo (not those in his profile)
	public CoursePdfGenerator(Section startSection, User user, boolean coverPageOrNot, String logoUrl){
		this.startSection = startSection;
		this.pd4ml = new PD4ML();
		this.user = user;
		this.doTheUserWantACoverPage = coverPageOrNot;
		
		this.urlLogoPdf = logoUrl;
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
		String html = generateHtmlForDocument();
		System.out.println(html); // FIXME: remove at integration

		// PDF document setting
		pd4ml.addStyle( CSS, true);
		createHeader();
		createFooter();
		pd4ml.enableImgSplit(false); //Do not split an image
	
		// PDF rendering
		pd4ml.render(new StringReader(html), outputStream); //Start creating PDF
	}
	
	public void createHeader(){
		PD4PageMark head = new PD4PageMark();
		head.setPagesToSkip(1); //Skip the first page
		head.setHtmlTemplate("<img height='50' width='50' align='right' src='"+user.getLogoUrl()+"'>"); //Add the logo of the user company
		head.setAreaHeight(45); //Adjust the height
		pd4ml.setPageHeader(head); //Add header
	}
	
	public void createFooter(){
		//footerPages
		PD4PageMark foot = new PD4PageMark();
		foot.setPagesToSkip(1); //Skip the first page
		foot.setInitialPageNumber(1);
		//Add a table with 3 cells : 1. the blackbelt logo, 2. the licence, 3. the page number
		foot.setHtmlTemplate("<table width='100%'><tr><td><img height='30' width='50' align='left' src='"+BBF_LOGO+"'></td><td class='licence'>"+FOOTER_TEXT+"</td><td align='right' class='valignBottom'>${page}<span class='grey'> /${total}</span></td></tr></table>");
		foot.setAreaHeight(45); //Adjust the height
		pd4ml.setPageFooter(foot); //Add footer
	}
		
	public String generateHtmlForDocument() {
		// Html header
		String finalResult = "<html><head><title>"+this.startSection.getTitle()+"</title></head>" +
		// Html body start
				"<body>";
		//Cover page
		if (doTheUserWantACoverPage){
			finalResult+=createCoverHtml();
		}

		// Main content (all sub sections of startSection)
		finalResult += generateHtmlForSection(this.startSection);
		
		// Html close
		finalResult+="</body></html>";
		return finalResult;
	}
	
	/** Recursive generation of html for given section and all sub-sections */
	public String generateHtmlForSection(Section section){
		// Title
		String finalResult = createSectionTitle(startSection);
		
		// Body
		CourseTextFormatter ctf = new CourseTextFormatter("", this.startSection.getBody());
		finalResult += ctf.format();
		
		// Sub-sections
		for(Section subSection : this.startSection.getSubSections()){
			//This line is important to avoid making a static method.
			CoursePdfGenerator test = new CoursePdfGenerator(subSection, this.user, this.doTheUserWantACoverPage);
			finalResult += test.generateHtmlForSection(subSection);
		}
		
		return finalResult;
	}
	
	public String createCoverHtml(){
		String result = new String("");
		result +=
				// BBF Logo
			    "<div align='center' class='logo'><br/><br/>" +
				"  <img width='200' height='127' src='"+BBF_LOGO+"'>" +
				"</div><br/><br/>" +
				
				// Titles
				"<h1 align='center'>"+this.startSection.getCategoryTitle()+"</h1><br/>" +
				"<h2 align='center'>"+this.startSection.getTitle()+"</h2><br/><br/><br/>" +
				
				// The logo is in a table to not deforme the logo
				"<table align='center'><tr><td>" +
				"  <img width='280' height='320' align='middle' src='"+this.urlLogoPdf+"'>" +
				"</td></tr></table><br/><br/>";
		
		// User who printed.
		result+="<table width='100%'><tr><td width='45px'><img width='45px' height='35' align='left' src='"+this.user.getLevelUrl()+"'></td><td class='valignMiddle'><span class='small'>Download by :</span><br/><i>"+this.user.getFirstName()+" "+this.user.getName()+"</i><br/><span class='small'>"+this.getCurentDate()+"</span></td></tr></table><pd4ml:page.break>";
		return result;
	}
	
	public String createSectionTitle(Section section){
		int size = getTitleSize(section); //Check the title importance
		String title = null;

		int level = Math.min(size+1, 4);  // TODO: when integrating with BBF, see how this has already been developped and reuse.
		title = "<br/><h"+level+">"+section.getTitle()+"</h"+level+">";

		return title;
	}
	
	// TODO: when integrate with BBF code, probably reuse existing method (see how it's done for generating titles on html pages).
	public int getTitleSize(Section section){
		int result = 0;
		
		//See how much parent have a section. With this number we can see the importance of a title and defined its size.
		while(section.getParent() != null){
			section = section.getParent();
			result++;
		}
		
		return result;
	}
	
	// TODO: when integrate with BBF code, reuse a method of DateUtil (see John).
	public String getCurentDate(){
		String sDate="";
		GregorianCalendar gc= new GregorianCalendar();
		sDate=gc.get(GregorianCalendar.MONTH)+"/"+gc.get(GregorianCalendar.DAY_OF_MONTH)+"/"+gc.get(GregorianCalendar.YEAR);
		return sDate;
	}

}
