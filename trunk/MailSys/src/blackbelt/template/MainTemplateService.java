package blackbelt.template;

import java.util.List;

import org.springframework.stereotype.Service;

import blackbelt.model.Mail;
import blackbelt.model.MailSubject;
import blackbelt.model.User;
/**
 * Generates a Html string based on a mail
 */
@Service
public final class MainTemplateService {
	
	public MainTemplateService() {
		
	}
	
	private String templateHeader(User user) {
		return "<div align=\"center\"><font face=\"Impact,Verdana\" size=\"7\"><font color=\"#000000\">Black</font><font color=\"#bababa\">Belt</font><br>Factory</font></div><hr>"
			+  "<div align=\"center\"> to : " + user.getPseudo()
			+  "  -  <a href=\"C:/testing/users/user.html\">your account here</a>"
			+  "</div><br>";
	}
	
	private String templateBody(Mail mail) {
		return "<div style=\"margin:10;width:70%\"><div style=\"background:#000000;padding:10\"><font face=\"Comic_Sans,Verdana\" color=\"#FFFFFF\" size=\"5\">"
			+  mail.getMailSubject().toString()
			+  "</font><br><font color=\"#FFFFFF\" size=\"2\">"
			+  mail.getDateMessage()
			+  "</font></div><div style=\"background:#bababa;padding:15px\">"
			+  mail.getFormatedText() + "</div></div>";
	}
	
	private String templateFooter() {
		return "<br><hr><br><div align=\"center\"><a href=\"http://www.blackbeltfactory.com/ui#!\"><img border=\"0\" src=\"http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png\"><br>www.blackbeltfactory.com</a></div>";
	}
	
	/** Produces a mail body from a list of groupable mail (or a list with a single mail).
	 * All the given mails must be sent to the same user.
	 */
	public final String TemplateMail(List<Mail> mails) {
		
		if (mails == null) {
			throw new IllegalArgumentException("Bug: Cannot give a null list.");
		} else if (mails.size() == 0) {
			throw new IllegalArgumentException("Bug: Cannot give an empty list.");
		} else if (mails.get(0).getUser() == null) {
			throw new RuntimeException("Bug: No user defined (the user is obtained by the first mail in the list).");
		}

		User user = mails.get(0).getUser();

		// Build content text.
		String content = this.templateHeader(user);;
		for (Mail mail : mails) {
			content += this.templateBody(mail);
		}
		content += this.templateFooter();
		
		return content;
	}
	
//	public String MainSubjectOfGroupedMails(List<Mail> mails){
//		for(MailSubject mailSubject : MailSubject.values()) {
//			List
//			for(Mail mail : mails){
//				if (mail.getMailSubject() == mailSubject) {
//				    list.add	together....
//				}
//
//				switch(mail.getMailSubject()){
//				case SUBJECT_1 : IncrementVariableMailsOfSameSubject(mail); break;
//				case SUBJECT_2 : IncrementVariableMailsOfSameSubject(mail); break;
//				case SUBJECT_3 : IncrementVariableMailsOfSameSubject(mail); break;
//				case SUBJECT_4 : IncrementVariableMailsOfSameSubject(mail); break;
//				case CONGRATULATION : IncrementVariableMailsOfSameSubject(mail); break;
//				default:break;
//				}
//			}
//		}
//		
//	}
//	
//	public void IncrementVariableMailsOfSameSubject(Mail mail){
//		int i = 0;
//		i=mail.getMailSubject().getMailsOfSameSubject()+1;
//		mail.getMailSubject().setMailsOfSameSubject(i);
//	}
}
