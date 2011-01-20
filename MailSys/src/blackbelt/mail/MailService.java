package blackbelt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.dao.MailDao;
import blackbelt.model.Mail;
import blackbelt.model.MainSubject;
import blackbelt.model.MailType;

/**
 * Service of mail.
 */
@Service
public class MailService {

	@Autowired
	private MailDao dao;
	
	/**
	 * Save a mail.
	 * @param subject subject of mail
	 * @param text text of mail
	 * @param userID user's id to whom the mail will be send to
	 * @param isAImmadiateMessage indicates if the mail is send or not
	 */
	public void createAndSaveMail(String subject,MainSubject mailSubject, String text, Long userID, MailType mailType, boolean useTemplate){
		Mail mail = new Mail(null,subject,mailSubject,text,mailType, useTemplate);
		dao.save(mail, userID);
	}
	
	
	
}

