/*test*/
//test2
package blackbelt.model;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@Column(nullable = false)
	private String subject;
	
	@Column(nullable = false)
	private String content;
	
	private boolean immediate; // true => Non groupable
	// int priority; // 0 = high priority, 1 = normal, 2 = low (as newsletter)
	private Date creationDate;

	public Mail() {}
	
	public Mail(User user, String subject, String content, boolean immediate) {
		////this.id = -1;
		this.user = user;
		this.subject = subject;
		this.content = content;
		this.immediate = immediate;
		this.creationDate = new Date();
	}

	public Mail(User user, String subject, String text, boolean immediate, Date date) {
		////this.id = id;
		this.user = user;
		this.subject = subject;
		this.content = text;
		this.immediate = immediate;
		this.creationDate = date;
	}

	public Long getId() {
		return this.id;
	}

	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getText() {
		return this.content;
	}

	public String getFormatedText() {

		String content = new String(this.content);
		String result = new String("");

		while (content.length() > 0) {
			result += content.substring(0, content.indexOf(".") + 1) + "<br>";
			content = content.substring(content.substring(0, content.indexOf(".") + 1).length(), content.length());
		}

		return result;
	}

	public boolean getImmediate() {
		return immediate;
	}

	public Date getDate() {
		return this.creationDate;
	}

	public String getDateMessage() {
		GregorianCalendar gDate = new GregorianCalendar();
		gDate.setGregorianChange(this.creationDate);
		String date = gDate.get(GregorianCalendar.YEAR) + "/"
					+ gDate.get(GregorianCalendar.MONTH) + "/"
					+ gDate.get(GregorianCalendar.DAY_OF_MONTH) + " ("
					+ gDate.get(GregorianCalendar.HOUR_OF_DAY) + ":"
					+ gDate.get(GregorianCalendar.MINUTE) + ":"
					+ gDate.get(GregorianCalendar.SECOND) + ")";
		return date;
	}
}
