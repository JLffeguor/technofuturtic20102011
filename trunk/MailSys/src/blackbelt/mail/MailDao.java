//on the road again
package blackbelt.mail;

import java.util.ArrayList;
import java.util.List;

import blackbelt.model.Mail;
import blackbelt.model.User;

/** Fake Dao to be replace by JPA code */
public class MailDao {

	final static int MAXEMAILFORASEND = 2;
	
	public static MailDao instance = new MailDao(); // TODO: replace by being a
													// Sping bean.

	ArrayList<Mail> db;
	ArrayList<Mail> stendDb;

	public MailDao() {
		db = new ArrayList<Mail>();
	}

	public void save(Mail mail) {
		db.add(mail);
	}

	public ArrayList<Mail> getDb() {
		return this.db;
	}

	public List<Mail> findNextMail() {

		int i, max;
		List<Mail> rc;
		Mail currentMail;
		User firstUser;

		if (this.db.size() == 0) {
			return null;
		}

		firstUser = this.db.get(0).getUser();
		rc = new ArrayList<Mail>();

		for (i = 0, max = this.db.size(); i < max; i++) {
			
			currentMail = this.db.get(i);
			
			if (currentMail.getUser() == firstUser) {
				rc.add(currentMail);
			}
		}

		return rc;
	}
	
	public void removeMails (List<Mail> mails) {
		for (Mail mail : mails) {
			this.db.remove(mail);
		}
	}

}
