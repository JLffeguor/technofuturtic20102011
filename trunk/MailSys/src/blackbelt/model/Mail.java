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
	
	//in case we send an email to a person in the database
	@ManyToOne
	@JoinColumn(nullable = true, name = "user_id")
	private User user;
	
	@ManyToOne
    @JoinColumn(nullable = true, name = "replyTo")
	private User replyTo;
    
	//in case we send an email to a person not in the database
	@Column(nullable = true)
    private String emailTarget;

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
	
	//For sending a mail to a BlackBelt user
	public Mail(User user,String subject, MailCategory mailSubject, String content, MailType mailType, boolean useTemplate) {
		this.user = user;
		this.replyTo = null;
		this.emailTarget = null;
		this.mailCategory = mailSubject;
		this.content = content;
		this.mailType=mailType;
		this.creationDate = new Date();
		this.subject=subject;
		this.useTemplate = useTemplate;
	}
	
	//For sending a mail to an outsider(not a BlackBelt user) 
	public Mail(String emailTarget, String subject, MailCategory mailSubject, String content, MailType mailType, boolean useTemplate) {
        this.user = null;
        this.replyTo = null;
        this.emailTarget = emailTarget;
        this.mailCategory = mailSubject;
        this.content = content;
        this.mailType=mailType;
        this.creationDate = new Date();
        this.subject=subject;
        this.useTemplate = useTemplate;
    }
	
	//Send a mail to a blackbelt user and it contains information about the personne that send the mail 
    public Mail(User recipient,User replyTo, String subject, MailCategory mailSubject, String content, MailType mailType, boolean useTemplate) {
        this.user = recipient;
        this.replyTo = replyTo;
        this.emailTarget = null;
        this.mailCategory = mailSubject;
        this.content = content;
        this.mailType=mailType;
        this.creationDate = new Date();
        this.subject=subject;
        this.useTemplate = useTemplate;
    }
	
	
	
	//Getters and setters
	public Long getId() {
		return this.id;
	}

	public User getUser() {
		return this.user;
	}
	
	
	public User getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(User replyTo) {
        this.replyTo = replyTo;
    }

    /**
     *Set user instead of emailTarget
     */
	public void setUser(User user) {
		this.user = user;
		this.emailTarget = null;
	}
	
	public String getEmailTarget() {
        return this.emailTarget;
    }
	
	/**
     * Set emailTarget instead of user
     */
    
	public void setEmailTarget(String emailTarget) {
        this.user = null;
        this.emailTarget = emailTarget;
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
