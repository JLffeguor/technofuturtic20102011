package blackbelt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.dao.ExtractionMail;

import blackbelt.model.Mail;

/**
 * Service of mail.
 */
@Service
public class MailService {

	@Autowired
	private ExtractionMail dao;
	
	/**
	 * Save a mail.
	 * @param subject subject of mail
	 * @param text text of mail
	 * @param userID user's id to whom the mail will be send to
	 * @param isAImmadiateMessage indicates if the mail is send or not
	 */
	public void send(String subject, String text,Long userID,boolean isAImmadiateMessage){
		Mail mail = new Mail(null,subject,text,isAImmadiateMessage);
		dao.save(mail, userID);
	}
}

