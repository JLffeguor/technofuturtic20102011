package blackbelt.dao;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.model.Mail;
import blackbelt.model.User;

@Transactional
@Repository
public class ExtractionMail {
	
	
	@PersistenceContext
	private EntityManager em;
	
	private User userContainingImmediateMails(){
		Query query = em.createQuery(" SELECT user FROM Mail m join m.user user WHERE m.immediate=true")
						.setMaxResults(1);
		try{ return  (User)query.getSingleResult();
		}catch(NoResultException e){
		return null;	
		}
	}
	
	private User userContainingGroupedMails(){
		/************************/
		Date dateForDay;
		Date dateForWeek;		
		Date now= new Date();
		GregorianCalendar cal= new GregorianCalendar();
		cal.setTime(now);
		cal.add(Calendar.SECOND, -10);
		dateForDay = cal.getTime();
		cal.add(Calendar.SECOND, -30);
		dateForWeek = cal.getTime();	
		/************************/
		
		Query query = em.createQuery("SELECT user "
					+ "FROM Mail m join m.user user"
						+ " WHERE ("
						+ "         m.immediate=false"
						+ " 	AND"
						+ "          ("
						+ "               m.user.lastMailSendedDate IS NULL "
						+ "           OR  (     m.user.lastMailSendedDate IS NOT NULL"
						+ "                AND (    (m.user.mailingDelai = 1 AND :dateForDay > m.user.lastMailSendedDate)"
						+ "                      OR (m.user.mailingDelai = 2 AND :dateForWeek > m.user.lastMailSendedDate)"
						+ "                    )"
						+ "              )"
						+ "          )"
						+ "     ) ")
						.setParameter("dateForDay", dateForDay)
						.setParameter("dateForWeek", dateForWeek)
						.setMaxResults(1);
		try{ return  (User)query.getSingleResult();
		}catch(NoResultException e){
		return null;	
		}		
	}	
	
	private List<Mail> getMailsFromUser(boolean isImmediate, User user){
		if(user !=null){
		Query query = em.createQuery("SELECT m FROM Mail m WHERE m.user =:user AND m.immediate =:condition" )
                        .setParameter("user",user)
		                .setParameter("condition",isImmediate); 
		return query.getResultList();
		}else{
			return null;
		}
	}
	
	public void removeMails(List<Mail> mails) {
		if(!mails.isEmpty()){
		Query query = em.createQuery("DELETE FROM Mail m WHERE m IN (:mails)").setParameter("mails",mails);
		query.executeUpdate();
		}
	}
	
	public void updateLastMailSendedDate(User user){	
		em.createQuery("UPDATE User user SET user.lastMailSendedDate =:todayDate WHERE user.id =:userid")
		  .setParameter("todayDate", new Date())
		  .setParameter("userid", user.getId())
		  .executeUpdate();
	}
	
	public void save(Mail mail, Long idUser) {
		User user = (User) em.find(User.class, idUser);
		mail.setUser(user);
		em.persist(mail);
	}	
	public List<Mail> findNextMail(){
		
		User user;
		
		user = userContainingImmediateMails();
		if(user!=null){
			return getMailsFromUser(true, user);
		}
		
		user = userContainingGroupedMails();
		if(user!=null){
			return getMailsFromUser(false, user);
		}
		
		return null;
			
	}
}
