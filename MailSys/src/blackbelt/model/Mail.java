/*test*/
//test2
package blackbelt.model;

import java.util.Date;
import java.util.GregorianCalendar;

public class Mail {
	private int id;
	private User user;
	private String subject;
	private String text;
	private boolean immadiate; // true => Non groupable
	// int priority; // 0 = high priority, 1 = normal, 2 = low (as newsletter)
	private Date creationDate;

	public Mail(User user, String subject, String text, boolean immadiate) {
		this.id = -1;
		this.user = user;
		this.subject = subject;
		this.text = text;
		this.immadiate = immadiate;
		this.creationDate = new Date();
	}

	public Mail(int id, User user, String subject, String text, boolean immadiate, Date date) {
		this.id = id;
		this.user = user;
		this.subject = subject;
		this.text = text;
		this.immadiate = immadiate;
		this.creationDate = date;
	}

	public int getId() {
		return this.id;
	}

	public User getUser() {
		return this.user;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getText() {
		return this.text;
	}

	public String getFormatedText() {

		String content = new String(this.text);
		String result = new String("");

		while (content.length() > 0) {
			result += content.substring(0, content.indexOf(".") + 1) + "<br>";
			content = content.substring(content.substring(0, content.indexOf(".") + 1).length(), content.length());
		}

		return result;
	}

	public boolean getImmadiate() {
		return immadiate;
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
