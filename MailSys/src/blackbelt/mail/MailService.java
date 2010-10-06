package blackbelt.mail;

import blackbelt.model.Mail;
import blackbelt.model.User;

public class MailService {

	public static MailService instance = new MailService(); // TODO: replace by being a Sping bean.
	
	
	public void send(String subject, String text,User user,boolean isAImmadiateMessage){
		// TODO: templating around the content.
		Mail mail = new Mail(user,subject,text,isAImmadiateMessage);
		MailDao.instance.save(mail);
	}

	
}

