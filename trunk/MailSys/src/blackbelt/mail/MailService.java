package blackbelt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.dao.InsertionMail;
import blackbelt.model.Mail;

@Service
public class MailService {

	@Autowired
	private InsertionMail im;
	
//	public static MailService instance = new MailService(); // TODO: replace by being a Spring bean.

	public void send(String subject, String text,Long userID,boolean isAImmadiateMessage){
		Mail mail = new Mail(null,subject,text,isAImmadiateMessage);
		im.save(mail, userID);
	}
}

