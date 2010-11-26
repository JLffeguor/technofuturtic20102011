package blackbelt.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_GEN")
	@javax.persistence.SequenceGenerator(name="SEQ_GEN",sequenceName="seq_user_id")
	private Long id;
	
	@Column(nullable = false)
	private String pseudo;
	
	@Column(nullable = false)
	private String email;
	
	private Date lastMailSendedDate;
	
	@Column(nullable = false)
	private int mailingDelai;// 0= none 1= weakly 2= daily

	//Constructors
	public User() {
		
	}
	
	public User(String pseudo, String email, Date lastMailSendedDate, int mailingDelai) {
		this.pseudo = pseudo;
		this.email = email;
		this.lastMailSendedDate = lastMailSendedDate;
		this.mailingDelai = mailingDelai;
	}
	
	//Getters and setters
	public Long getId() {
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
