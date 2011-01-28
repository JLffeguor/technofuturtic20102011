package blackbelt.mail;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.dao.MailDao;
import blackbelt.model.Mail;
import blackbelt.model.MailCategory;
import blackbelt.model.MailType;
import blackbelt.model.User;

/**
 * Service of mail.
 */
@Service
public class MailService {

    @Autowired	private MailDao mailDao;
    @Autowired	private MailSender mailSender;

 
    /**
     * Send a mail to a blackbelt user or to none blackbelt user
     * This method uses blackbelt template, if you don't want to use it call the method sendMail(Mail mail)
     * The collection contains Users and strings(electronic email address) 
      * 
     */
    public void sendMail(Collection<Object> targets, String subject, String content, MailType mailType, MailCategory mailCategory){

        for(Object object : targets){
            if (object instanceof User){
                this.sendMail(new Mail((User)object, subject, mailCategory, content, mailType, true ));
            }else if (object instanceof String ){
                this.sendMail(new Mail((String)object, subject, mailCategory, content, mailType, true ));
            } else {
                throw new IllegalArgumentException("Bug: we only accept Strings and Users. Current object's class: " + object.getClass());
            }
        }
    }

    /**
     * Send a mail to a blackbelt user
     * this methode uses a blackbelt template, if you don't want to use it call the method sendMail(Mail mail)
     */
    public void sendMail(User recipient, String subject, String content, MailType mailType, MailCategory mailCategory){
        this.sendMail(new Mail(recipient, subject, mailCategory, content, mailType, true ));
    }

    /**
     * Sends a mail to an electronic email
     * this methode uses a blackbelt template, if you don't want to use it call the method sendMail(Mail mail)
     * 
     */
    public void sendMail(String emailTarget, String subject,MailCategory mailCategory, String content, MailType mailType){
        this.sendMail(new Mail(emailTarget, subject, mailCategory, content, mailType, true ));
    }
    
    
    
    public void sendMail(User recipient, User replyTo, String subject, String content, MailType mailType, MailCategory mailCategory){
        this.sendMail(new Mail(recipient, replyTo, subject, mailCategory, content, mailType, true ));
    }


    /**
     * Save a mail to database
     * Use this methode if you want to use a black belt template
     * 
     */
    public void sendMail(Mail mail){
        //FIXME remove body at integration(), check with mathieu how dao function in blackbelt 

        if (mail.getUser()==null){
            mailDao.save(mail, null);
        }else{
            mailDao.save(mail, mail.getUser().getId());
        }

        // wake up the thread each time we send a mail to database because if there isn't any mail to send the thread goes to sleep for a certain time.
        synchronized(mailSender) {
            mailSender.notify();
        }
    }

}

