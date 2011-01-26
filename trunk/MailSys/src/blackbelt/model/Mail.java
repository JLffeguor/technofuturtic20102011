/*test*/
//test2
package blackbelt.model;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mails")
public class Mail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_GEN")
	@javax.persistence.SequenceGenerator(name="SEQ_GEN",sequenceName="seq_mails_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	@Enumerated(EnumType.STRING)
	private MailCategory mailCategory;
	
	@Column(nullable = false)
	private String subject;
	
	@Column(nullable = false)
	private String content;
	
	@Enumerated(EnumType.STRING)
	private MailType mailType;
	
	private Date creationDate;
	
	private boolean useTemplate;
	
	//Constructors
	public Mail() {}
	
	public Mail(User user,String subject, MailCategory mailSubject, String content, MailType mailType, boolean useTemplate) {
		////this.id = -1;
		this.user = user;
		this.mailCategory = mailSubject;
		this.content = content;
		this.mailType=mailType;
		this.creationDate = new Date();
		this.subject=subject;
		this.useTemplate = useTemplate;
	}

	public Mail(User user, MailCategory mailSubject, String text, boolean immediate, Date date, boolean useTemplate) {
		////this.id = id;
		this.user = user;
		this.mailCategory = mailSubject;
		this.content = text;
		this.creationDate = date;
		this.useTemplate = useTemplate;
	}
	
	//Getters and setters
	public Long getId() {
		return this.id;
	}

	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public MailCategory getMailCategory() {
		return this.mailCategory;
	}

	public String getText() {
		return this.content;
	}

	public String getFormatedText() {

		String content = this.content;
		String result = "";

		while (content.length() > 0) {
			//We make a new paragraph if there is a '.' in the text
			result += content.substring(0, content.indexOf(".") + 1) + "<br /><br />";
			content = content.substring(content.substring(0, content.indexOf(".") + 1).length(), content.length());
			
			//If there is not '.' in the text, we stop the loop
			if (content.indexOf(".") == -1){
				result += content;
				break;
			}
		}

		return result;
	}

	public MailType getMailType() {
		return this.mailType;
	}
	
	public boolean getUseTemplate() {
		return this.useTemplate;
	}

	public Date getDate() {
		return this.creationDate;
	}
	
	public String getSubject(){
		return subject;
	}

	public String getDateMessage() {
		GregorianCalendar gDate = new GregorianCalendar();
		gDate.setGregorianChange(this.creationDate);
		String date = gDate.get(GregorianCalendar.YEAR) + "/"
					+ gDate.get(GregorianCalendar.MONTH)+1 + "/"
					+ gDate.get(GregorianCalendar.DAY_OF_MONTH) + " ("
					+ gDate.get(GregorianCalendar.HOUR_OF_DAY) + ":"
					+ gDate.get(GregorianCalendar.MINUTE) + ":"
					+ gDate.get(GregorianCalendar.SECOND) + ")";
		return date;
	}
}
