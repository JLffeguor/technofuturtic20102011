//on the road again
package blackbelt.mail;

import java.util.ArrayList;

import blackbelt.model.Mail;

/** Fake Dao to be replace by JPA code */	
public class MailDao {

	public static MailDao instance = new MailDao();  // TODO: replace by being a Sping bean. 
	
	ArrayList<Mail> db ;
	ArrayList<Mail> stendDb ;
	public MailDao(){
		 db = new ArrayList<Mail>();
	}
	
	
	public void save(Mail mail){
			db.add(mail);
	}
	public ArrayList<Mail> getDb(){
		return this.db;
	}
	public Mail findNextMail(){
		Mail nextMail = (this.db.size()>0)
					?
				this.db.remove(0):null;
		return nextMail;
	}
	
}
