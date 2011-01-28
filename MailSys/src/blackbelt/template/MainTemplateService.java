package blackbelt.template;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.dao.MailDao;
import blackbelt.model.Mail;
import blackbelt.model.MailCategory;
import blackbelt.model.MailType;
import blackbelt.model.MailingDelayType;
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
    
    /**
     * Determine the footer for a given mail. If the mail is for a blackbelt user, we insert a link to this account
     * and if the mail's type is groupable, the footer will have options for the user. 
     * For any other mails the footer will simply contain a url link of blackbletfactory website. 
     * @param user
     * @param isGrouped
     * @return the footer string with the user option if there is one or just the web site url.
     */
    private String templateFooter(User user, boolean isGrouped) {

        String mailDelayOption = "";
        String secondDelayOption = "";
        String thirdDelayOption = "";
        String footer="";
        if (user !=null){
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
          //FIXME: link to the user account
            footer = "<br><hr><br><div align=\"justify\"> your account : <a href=\"http://www.blackbeltfactory.com/ui#!User/d&d\"> http://www.blackbeltfactory.com/ui#!</a>";
            if (isGrouped) {
                footer += "<br>group option is "
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
        }else{
            footer = "<br><hr><br><div align=\"justify\"> <a href=\"http://www.blackbeltfactory.com\"> http://www.blackbeltfactory.com/ui#!</a>"
                   + "<br><br><img border=\"0\" src=\"http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png\"><br>";
        }
        
        
        return footer;
    }

    public class MailSubjectAndContent{

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

    /** Builds the complete content and subject of the one mail to be sent.
     * If the mails parameter contains more than a mail, they are all groupables. Individual mails are always passed individually to this method.
     */
    public MailSubjectAndContent templateMail(List<Mail> mails) {
        // Params check
        if (mails == null){
            throw new IllegalArgumentException("bug: No list defined for 'mails'.");
        } else if (mails.size() == 0){
            throw new IllegalArgumentException("bug: No mail defined for template.");
        } else if ((mails.get(0).getUser() == null && mails.get(0).getEmailTarget() == null) || (mails.get(0).getUser() != null && mails.get(0).getEmailTarget() != null)){
            throw new IllegalArgumentException("bug: No user or address mail defined (the user is obtained by the first mail in the list).");
        }

        MailSubjectAndContent msc = new MailSubjectAndContent();
        User recipient = mails.get(0).getUser();
        //content = this.templateHeader(user);

        // Building of content
        String content = "";
        for (Mail mail : mails) {
            content += (mail.getUseTemplate()) ?
                    this.templateBody(mail) // Case of most mails.
                    :
                        mail.getText();  // This would be the case of a newsletter: we don't add a template around.
        }
        content  += this.templateFooter(recipient,
                (mails.size()>1)  // We only require the special grouping footer part if have multiple grouped mails. 
        );
        msc.setContent(content);

        // Building of subject
        if (mails.get(0).getMailType() == MailType.GROUPABLE) {  // We have a list of groupable mails.
            msc.setSubject( this.mainSubjectOfGroupedMails(mails) );
        } else {
            msc.setSubject( mails.get(0).getSubject() );
        }

        return msc;
    }

    /** From a list of grouped mail, makes a single subject string that represents every mail
     *
     *  **See Example in MailCategory**   
     * 
     * */ 
    private String mainSubjectOfGroupedMails(List<Mail> mails) {

        if(mails.size()==1) {  // One mail => we just take the subject of the mail.
            return mails.get(0).getSubject();

        } else {  // Multiple mail => we have to build a generic subject.
            String subjectOfGroupedMails = "";
            MailCategory[] mailCategoryValues = MailCategory.values();
            int numerOfCategories = mailCategoryValues.length;
            
            for (MailCategory mailCategory : mailCategoryValues) {
                numerOfCategories--;
                // Count the mails of the current subject.
                int amountOfMailsForTheCurrentSubject = 0;
                for (Mail mail : mails) {
                    if (mail.getMailCategory() == mailCategory) {
                        amountOfMailsForTheCurrentSubject++;
                    }
                }

                if (amountOfMailsForTheCurrentSubject != 0){
                    subjectOfGroupedMails += (Integer.toString(amountOfMailsForTheCurrentSubject) + " - " + mailCategory.getText());
                    
                    // if it is the last categorie to be added to the subject don't add a comma at the end of the subject
                    if(numerOfCategories<1){
                        subjectOfGroupedMails += ", ";
                    }
                }
                
            }
            return subjectOfGroupedMails;
        }

    }
}
