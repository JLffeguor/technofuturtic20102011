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
	
	/**
	 * returns a user containing immediate mails
	 * @return a user
	 */
	private User userContainingImmediateMails(){
		Query query = em.createQuery(" SELECT user FROM Mail m join m.user user WHERE m.immediate=true")
						.setMaxResults(1);
		try{ 
			return  (User)query.getSingleResult();
		}catch(NoResultException e){
			return null;	
		}
	}
	/**
	 * returns a user containing grouped mails
	 * @return a user
	 */
	private User userContainingGroupedMails(){
		/************************/
		Date dateForDay;
		Date dateForWeek;		
		Date now;
		//This code determines the interval used for the grouping mail (day and week).
		//Actually, for debug, we use : daily = 10 sec and weekly = 40 sec.
		now = new Date();
		GregorianCalendar cal= new GregorianCalendar();
		cal.setTime(now);
		cal.add(Calendar.SECOND, -10);
		dateForDay = cal.getTime();
		cal.add(Calendar.SECOND, -30);
		dateForWeek = cal.getTime();	
		/************************/
		
		//request to get a user containing grouped mails
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
		try{
			return  (User)query.getSingleResult();
		}catch(NoResultException e){
			return null;	
		}		
	}	
	
	/**
	 * Get mails, immediate or groupable  from a given user.
	 * @param isImmediate indicates if we want immediate mails or grouped mails.
	 * @param user the user who we want the mail.
	 * @return a list of mails 
	 */
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
	/**
	 * remove a list of mails
	 * @param mails
	 */
	public void removeMails(List<Mail> mails) {
		if(!mails.isEmpty()){
		Query query = em.createQuery("DELETE FROM Mail m WHERE m IN (:mails)").setParameter("mails",mails);
		query.executeUpdate();
		}
	}
	
	/**
	 * updates the lastMailSendedDate of a user
	 * @param user
	 */
	public void updateLastMailSendedDate(User user){	
		em.createQuery("UPDATE User user SET user.lastMailSendedDate =:todayDate WHERE user.id =:userid")
		  .setParameter("todayDate", new Date())
		  .setParameter("userid", user.getId())
		  .executeUpdate();
	}
	
	/**
	 * saves a mail in the database
	 * @param mail
	 * @param idUser
	 */
	public void save(Mail mail, Long idUser) {
		User user = (User) em.find(User.class, idUser);
		mail.setUser(user);
		em.persist(mail);
	}
	
	/**
	 * returns a list of mails which are either immediate or grouped of a user.
	 * @return a list of mails
	 */
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
