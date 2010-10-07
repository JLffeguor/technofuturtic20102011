package blackbelt.mail;
import blackbelt.model.User;

public class MainTest {
	static MailDao bdd = new MailDao();
	
	public static void main(String[] args) {
				
		///////// Données de test ///////
		User u1 = new User("yoyo","j_gusbin@hotmail.fr",15);
		User u2 = new User("fafa","fatonalia@hotmail.com",30);
		User u3 = new User("nawak","bidule_machin@hotmail.fr",15);
		
		
		MailSender consumer = new MailSender();
		consumer.start();
		

		MailService.instance.send("blablabla", "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", u2,false);
		MailService.instance.send("reblabla ", "contenu de. la mort qui tue, again.", u2,false);
		MailService.instance.send("rerereblabla ", "deuxieme contenu de la mort qui tue, plusieur fois.", u2,false);
		MailService.instance.send("la reproduction des pinguins en australie", "deuxieme contenu de la mort qui tue.encore.", u2,false);
		MailService.instance.send("sujet de la mort qui tue", "contenu de la mort qui tue.", u1,false);
		MailService.instance.send("encore sujet de la mort qui tue", "blablabla. blablabla blabla.", u2,false);
		MailService.instance.send("woooooot","i'm on the road,AGAIN.",u3,false);

//		sendMeThisMailDude("deuxieme sujet","la reproduction des pinguing e afrique du sud est grandement menacée!!",s2);

		
		// Ugly hack because MailSender is not in a batch job (yet).
		//MailSender.instance.sendNextMails();

		
	}
	
}
