package blackbelt.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.model.Mail;
import blackbelt.model.User;

@Transactional
@Repository
public class InsertionMail {
	
	//public static InsertionMail instance = new InsertionMail(); // Singleton
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void save(Mail mail, Long idUser) {
		User user = (User) em.find(User.class, idUser);
		mail.setUser(user);
		em.persist(mail);
	}
	
}
