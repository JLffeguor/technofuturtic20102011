package blackbelt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.dao.MailDao;
import blackbelt.model.MailCategory;
import blackbelt.model.MailType;
/**
 *It' simulates a sending of mails
 */
@Service
public class DataTest extends Thread{
	
	@Autowired
	MailService mailService;
	@Autowired
	MailDao mailDao;
	@Override
	public void run(){
	    
	    
	    
		//init date
		mailService.sendMail("fatonalia@hotmail.com", "subject ",MailCategory.CONTRIBUTION,"Ce message ne sert a rien Go te chercher un caf�",MailType.GROUPABLE);
        try {
            //delai entre 2 send
            System.out.println("////////////////////////");
            sleep(5000);
            System.out.println("////////////////////////");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
		mailService.sendMail("laurent@hotmail.com"," subject ",MailCategory.CONTRIBUTION,"Ce message ne sert a rien.Go te chercher un caf�.",MailType.GROUPABLE);
		mailService.sendMail(mailDao.getUser(2L)," subject ","Ce message ne sert a rien.Go te chercher un caf�.",MailType.GROUPABLE,MailCategory.CONTRIBUTION);
		mailService.sendMail(mailDao.getUser(2L)," subject2 ","Ce message ne sert a rien.Go te chercher un caf�.",MailType.GROUPABLE,MailCategory.CONTRIBUTION);
        mailService.sendMail("fatonalia@hotmail.com"," subject ",MailCategory.CONTRIBUTION,"Ce message ne sert a rien.Go te chercher un caf�.",MailType.GROUPABLE);
		mailService.sendMail("email@coldletter.com"," subject ",MailCategory.QUESTION,"Ce message ne sert a rien.Go te chercher un caf�.",MailType.GROUPABLE);
		
		
		mailService.sendMail("fatonalia@hotmail.com"," subject ",MailCategory.QUESTION, "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.",MailType.GROUPABLE);
		mailService.sendMail("email@coldletter.com"," subject ",null, "contenu de. la mort qui tue, again.",MailType.IMMEDIATE);
		mailService.sendMail(mailDao.getUser(3L)," subject ","contenu de. la mort qui tue, again.",MailType.IMMEDIATE,null);
        //mailService.sendMail(" subject",MailCategory.QUESTION, "deuxieme contenu de la mort qui tue 1, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.sendMail("email@coldletter.com"," subject",MailCategory.QUESTION, "deuxieme contenu de la mort qui tue 2, plusieur fois.",MailType.GROUPABLE);
		//mailService.sendMail(" subject",MailCategory.CONTRIBUTION, "deuxieme contenu de la mort qui tue 3, plusieur fois.", 1L,MailType.GROUPABLE, true);
		//mailService.sendMail(" subject",MailCategory.CONTRIBUTION, "deuxieme contenu de la mort qui tue 4, plusieur fois.", 1L,MailType.GROUPABLE, true);
//		mailService.sendMail(" subject",null, "deuxieme contenu de la mort qui tue 4, plusieur fois.", 1L,MailType.SLOW_NOT_GROUPABLE, true);
//		
//		mailService.sendMail(" subject ",MailCategory.COACH, "deuxieme contenu de la mort qui tue.encore.", 2L,MailType.GROUPABLE, true);
//		mailService.sendMail(" subject ",null, "contenu de la mort qui tue.", 2L,MailType.IMMEDIATE, true);
//		mailService.sendMail(" subject ",MailCategory.COACH, "blablabla. blablabla blabla.", 2L,MailType.GROUPABLE, true);
//		
//		mailService.sendMail(" subject ",MailCategory.COACH,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.GROUPABLE, true);
//		mailService.sendMail(" subject ",null,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.IMMEDIATE, true);
//		mailService.sendMail(" subject ",MailCategory.COACH,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.GROUPABLE, true);
//		mailService.sendMail(" subject ",null,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.SLOW_NOT_GROUPABLE, false);
//		mailService.sendMail(" subject ",null,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.SLOW_NOT_GROUPABLE, true);
	}
}
