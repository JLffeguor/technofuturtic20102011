package blackbelt.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.dao.MailDao;
import blackbelt.model.Mail;
import blackbelt.model.MailType;
import blackbelt.model.MailingDelayType;
import blackbelt.model.MainSubject;
import blackbelt.model.User;
/**
 * Generates a Html string based on a mail
 */
@Service
public final class MainTemplateService {
	
	@Autowired
	MailDao dao;
	
	public MainTemplateService() {}
	
	private String templateBody(Mail mail) {
		
		return "<div style=\"margin:10;width:70%\">"
			+ "<div style=\"background:#000000;padding:10\">"
			+ "<font face=\"Arial\" color=\"#FFFFFF\" size=\"5\">"
			+  mail.getSubject()
			+ "</font><br><font color=\"#FFFFFF\" size=\"1\">"
			+  mail.getDateMessage()
			+ "</font></div>"
			+ "<div style=\"background:#ffffff;padding:2px\">"
			+ mail.getFormatedText()
			+ "</div></div><br>";
	}
	
	private String templateFooter(User user) {
		
		String mailDelayOption = "";
		String secondDelayOption = "";
		String thirdDelayOption = "";
		
		if(user.getMailingDelai() == MailingDelayType.DAILY) {
			mailDelayOption = user.getMailingDelai().toString();
			secondDelayOption = MailingDelayType.IMMEDIATELY.toString();
			thirdDelayOption = MailingDelayType.WEEKLY.toString();
		} else if(user.getMailingDelai() == MailingDelayType.WEEKLY) {
			mailDelayOption = user.getMailingDelai().toString();
			secondDelayOption = MailingDelayType.IMMEDIATELY.toString();
			thirdDelayOption = MailingDelayType.DAILY.toString();
		} else {
			mailDelayOption = user.getMailingDelai().toString();
			secondDelayOption = MailingDelayType.DAILY.toString();
			thirdDelayOption = MailingDelayType.WEEKLY.toString();
		}
		
		return "<br><hr><br><div align=\"justify\"> your account : <a href=\"http://www.blackbeltfactory.com/ui#!User/d&d\"> http://www.blackbeltfactory.com/ui#!</a>"
			+ "<br>group option is "
			+ mailDelayOption
			+ "  change to : "
			+ "<a href=\"http://www.blackbeltfactory.com/ui#!\">"
			+ secondDelayOption
			+ "</a> or "
			+ "<a href=\"http://imstars.aufeminin.com/stars/fan/jessica-alba/jessica-alba-20070817-299693.jpg\">"
			+ thirdDelayOption
			+ "</a><div align=\"center\">"
			+ "<a href=\"http://www.blackbeltfactory.com/ui#!\">"
			+ "<br><br><img border=\"0\" src=\"http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png\"><br>";
	}
	
	public class MailPackage{
		
		private String subject;
		private String content;
		
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
	
	public final MailPackage TemplateMail(List<Mail> mails) throws NullPointerException, Exception{
		MailPackage mp;
		String content = "";
		User user;
		mp=new MailPackage();
		if (mails == null){
			throw new NullPointerException("No list defined for 'mails'.");
		} else if (mails.size() == 0){
			throw new Exception("No mail define for template.");
		} else if (mails.get(0).getUser() == null){
			throw new NullPointerException("No user defined (the user is obtained by the first mail in the list).");
		}
		
		user = mails.get(0).getUser();
		//content = this.templateHeader(user);
		
		for (Mail mail : mails) {
			content += (mail.getUseTemplate()) ? this.templateBody(mail) : mail.getText();
		}
		
		content  += this.templateFooter(user);

		mp.setSubject((mails.get(0).getMailType()==MailType.GROUPABLE)?this.MainSubjectOfGroupedMails(mails):mails.get(0).getSubject());
		mp.setContent(content);
		
		return mp;
	}
	
	private String MainSubjectOfGroupedMails(List<Mail> mails){    
        int i;
        
        for(Mail mail : mails){
            for(MainSubject mailSubject : MainSubject.values()){
                if (mail.getMailSubject() == mailSubject) {
                    i=mailSubject.getMailsHavingSameSubject()+1;
                    mailSubject.setMailsHavingSameSubject(i);
                }
            }
        }
        
        String mainSubjectOfGroupedMails="";
        
        for(MainSubject mailSubject : MainSubject.values()){
            if (mailSubject.getMailsHavingSameSubject()>0){
            mainSubjectOfGroupedMails+=mainSubjectOfGroupedMails.valueOf(mailSubject.getMailsHavingSameSubject()+" "+mailSubject.toString()+" ");
            }
        }
        
        for(MainSubject mailSubject : MainSubject.values()){
        	mailSubject.setMailsHavingSameSubject(0);
        }
                
        return mainSubjectOfGroupedMails;
    }
}