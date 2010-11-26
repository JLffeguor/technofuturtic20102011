package blackbelt.dao;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.model.Mail;
import blackbelt.model.MailType;
import blackbelt.model.MailingDelayType;
import blackbelt.model.User;

@Transactional
@Repository
public class MailDao {
	
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * returns a user containing immediate mails
	 * @return a user
	 */
	public User userHavingImmediateMails(){
		List<User> list = em.createQuery("SELECT m.user FROM Mail m WHERE m.mailType=:MailType")
						.setParameter("MailType", MailType.IMMEDIATE)
						.setMaxResults(1)  // Because we don't need all the users, just one.
				 		.getResultList();

		if (list.size() == 0) {
			return null;	
		} else {
			return list.get(0);  // We just want one user, we'll execute this query again later to get the next user.
		}
	}
	/**
	 * returns a user containing grouped mails
	 * @return a user
	 */
	public User userHavingGroupedMails(){
		/************************/
		Date yesterday;
		Date lastWeek;		
		Date now;
		//This code determines the interval used for the grouping mail (day and week).
		//Actually, for debug, we use : daily = 10 sec and weekly = 40 sec.
		now = new Date();
		GregorianCalendar cal= new GregorianCalendar();
		cal.setTime(now);
		cal.add(Calendar.SECOND, -10);   // FIXME Change that to ....... XXXXXXXXXXXXXXXXXXXXXXXX
		yesterday = cal.getTime();
		cal.add(Calendar.SECOND, -30);   // FIXME Change that to ....... XXXXXXXXXXXXXXXXXXXXXXXX
		lastWeek = cal.getTime();	
		/************************/
		
		//request to get a user containing grouped mails
		List<User> list = em.createQuery("SELECT m.user "
				+ "FROM Mail m "
					+ " WHERE ("
					+ "         m.mailType=:MailType"
					+ " 	AND"
					+ "          ("
					+ "               m.user.lastMailSendedDate IS NULL "   // Not sent yet
					+ "           OR  (     m.user.lastMailSendedDate IS NOT NULL"
					+ "                AND (    (m.user.mailingDelai = :dailytype AND :yesterday > m.user.lastMailSendedDate)"
					+ "                      OR (m.user.mailingDelai = :weeklytype AND :lastWeek > m.user.lastMailSendedDate)"
					+ "                      OR  m.user.mailingDelai = :immediatetype"
					+ "                    )"
					+ "              )"
					+ "          )"
					+ "     ) ")
					.setParameter("MailType", MailType.GOUPABLE)
					.setParameter("yesterday", yesterday)
					.setParameter("lastWeek", lastWeek)
					.setParameter("dailytype", MailingDelayType.DAILY)
					.setParameter("weeklytype", MailingDelayType.WEEKLY)
					.setParameter("immediatetype", MailingDelayType.IMMEDIATELY)
					.setMaxResults(1)
					.getResultList();
		if (list.size() == 0) {
			return null;	
		} else {
			return list.get(0);  // We just want one user, we'll execute this query again later to get the next user.
		}	
	}	
	
	/**
	 * Get mails, immediate or groupable  from a given user.
	 * @param isImmediate indicates if we want immediate mails or grouped mails.
	 * @param user the user who we want the mail.
	 * @return a list of mails 
	 */
	public List<Mail> getMailsFromUser(MailType mailType, User user){
		Query query = em.createQuery("SELECT m FROM Mail m WHERE m.user =:user AND m.mailType =:mailType" )
		.setParameter("user",user)
		.setParameter("mailType",mailType); 
		return query.getResultList();
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
	 * Returns user's group option
	 * @param user
	 * @return a string
	 */
	public String getGroupOtionFromUser(User user){
		List<MailType> list = em.createQuery("SELECT m.mailType FROM Mail m WHERE m.user =:user")
		              	.setParameter("user", user)
		              	.setMaxResults(1)  // Because we need one user.
		              	.getResultList();

		if (list.size() == 0) {
			return null;	
		} else {
			String groupOption = list.get(0).toString();
			return groupOption;  // We just want a user's group option.
		}
	}
	
}
