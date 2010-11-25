package blackbelt.template;

import java.util.List;

import org.springframework.stereotype.Service;

import blackbelt.model.Mail;
import blackbelt.model.User;
/**
 * 
 */
@Service
public final class MainTemplate {
	
	public MainTemplate() {
		
	}
	
	private String templateHeader(User user) {
		return "<div align=\"center\"><font face=\"Impact,Verdana\" size=\"7\"><font color=\"#000000\">Black</font><font color=\"#bababa\">Belt</font><br>Factory</font></div><hr>"
			+  "<div align=\"center\"> to : " + user.getPseudo()
			+  "  -  <a href=\"C:/testing/users/" + user.getPseudo() //To review: the Link
			+  ".html\">your account here</a></div><br>";
	}
	
	private String templateBody(Mail mail) {
		return "<div style=\"margin:10;width:70%\"><div style=\"background:#000000;padding:10\"><font face=\"Comic_Sans,Verdana\" color=\"#FFFFFF\" size=\"5\">"
			+  mail.getSubject()
			+  "</font><br><font color=\"#FFFFFF\" size=\"2\">"
			+  mail.getDateMessage()
			+  "</font></div><div style=\"background:#bababa;padding:15px\">"
			+  mail.getFormatedText() + "</div></div>";
	}
	
	private String templateFooter() {
		return "<br><hr><br><div align=\"center\"><a href=\"http://www.blackbeltfactory.com/ui#!\"><img border=\"0\" src=\"http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png\"><br>www.blackbeltfactory.com</a></div>";
	}
	
	public final String TemplateMail(List<Mail> mails) throws NullPointerException, Exception{
		String content;
		User user;
		if (mails == null){
			throw new NullPointerException("No list defined for 'mails'.");
		} else if (mails.size() == 0){
			throw new Exception("No mail define for template.");
		} else if (mails.get(0).getUser() == null){
			throw new NullPointerException("No user defined (the user is obtained by the first mail in the list).");
		}
		
		user = mails.get(0).getUser();
		content = this.templateHeader(user);
		
		for (Mail mail : mails) {
			content += this.templateBody(mail);
		}
		
		content += this.templateFooter();
		
		return content;
	}
}
