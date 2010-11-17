package blackbelt.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class User {
	int id;
	private String pseudo;
	private String email;
	private Date lastMailSendedDate;
	private int mailingDelai;// 0= none 1= weakly 2= dayly

	public User(int id, String pseudo, String email, Date lastMailSendedDate, int mailingDelai) {
		
		this.id = id;
		this.pseudo = pseudo;
		this.email = email;
		this.lastMailSendedDate = lastMailSendedDate;
		this.mailingDelai = mailingDelai;

		// simulate users file//
		try {
			
			File rep = new File("C:/testing/users");
			rep.mkdirs();
			PrintWriter make = new PrintWriter("C:/testing/users/" + this.pseudo + ".html");
			make.println("<html>\n<head>\n<title>" + this.pseudo + "</title>\n</head>");
			make.println("<body>\n<p>user : " + this.pseudo + "</p>\n<p>your email : " + this.email + "</p>\n<p>your news letter groupe : ");
			
			if (this.mailingDelai > 2) {
				this.mailingDelai = 0;
			}
			
			if (this.mailingDelai == 0) {
				make.println("your are not grouped</p>");
			} else if (this.mailingDelai == 1) {
				make.println("your are weakly-grouped !</p>");
			} else if (this.mailingDelai == 2) {
				make.println("your are dayly-grouped !</p>");
			}
			
			make.println("</body>\n</html>");
			make.close();
		
		} catch (IOException e) {
		
			throw new RuntimeException(e);
		
		}
	}

	public int getId() {
		return this.id;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setLastMailSendedDate(Date sendDate) {
		this.lastMailSendedDate = sendDate;
	}

	public Date getLastMailSended() {
		return this.lastMailSendedDate;
	}

	public int getMailingDelai() {
		return this.mailingDelai;
	}

	public void setMailingDelai(int newLetterGroupe) {
		this.mailingDelai = newLetterGroupe;
	}

}
