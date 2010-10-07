/*test*/
//test2
package blackbelt.model;
import java.util.Date;
import java.util.GregorianCalendar;

public class Mail {
	private User user;
	private String subject;
	private String text;
	private boolean immadiate;    // true => Non groupable
	//int priority;   // 0 = high priority,  1 = normal,  2 = low (as newsletter)
	private Date creationDate;	
	public Mail(User user,String subject,String text,boolean immadiate){
		this.user = user;
		this.subject = subject;
		this.text = text;
		this.immadiate = immadiate;
		this.creationDate = new Date();
	}
///////
///////

	
	
		
/////////
/////////
	public User getUser(){
		return this.user;
	}
	public String getSubject(){
		return this.subject;
	}
	public String getText(){
		return this.text;
	}
	public boolean getImmadiate(){
		return immadiate;
	}
	public Date getDate(){
		return this.creationDate;
	}
	public String getDateMessage(){	
		GregorianCalendar gDate = new GregorianCalendar();
		gDate.setGregorianChange(this.creationDate);
		String date = gDate.get(GregorianCalendar.YEAR)+"/"+gDate.get(GregorianCalendar.MONTH)+"/"+gDate.get(GregorianCalendar.DAY_OF_MONTH)+" ("+gDate.get(GregorianCalendar.HOUR_OF_DAY)+":"+gDate.get(GregorianCalendar.MINUTE)+")";
		return date;
	}
}
