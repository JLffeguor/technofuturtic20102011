package blackbelt.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.model.Mail;
import blackbelt.model.User;

@Repository
public final class ExtractionMail {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<Mail> findNextMail() {

		String sql;
		Date now;
		Date dateForWeek;
		Date dateForDay;
		GregorianCalendar cal;
		List<Mail> mails;
		Query query;
		List<User> users;

		now = new Date();
		cal = new GregorianCalendar();
		cal.setTime(now);
		cal.add(Calendar.SECOND, -10);
		dateForDay = cal.getTime();
		cal.add(Calendar.SECOND, -30);
		dateForWeek = cal.getTime();

		mails = null;

		sql = "SELECT m.user "
				+ "FROM Mails m "
				+ "WHERE ("
				+ "       m.immediate=true"
				+ "      ) "
				+ "      OR"
				+ "      ("
				+ "           m.immediate=false"
				+ "       AND "
				+ "          ("
				+ "               m.user.mailingDelai = 0 "
				+ "           OR  m.user.lastMailSendedDate IS NULL "
				+ "           OR  (     m.user.lastMailSendedDate IS NOT NULL"
				+ "                AND (    (m.user.mailingDelai = 1 AND :dateForDay > u.lastMailSendedDate)"
				+ "                      OR (m.user.mailingDelai = 2 AND :dateForWeek > u.lastMailSendedDate)"
				+ "                    )" + "              )" + "          )"
				+ "     ) "
				+ "ORDER BY m.IsImediateMessage DESC ";

		query = em.createQuery(sql);
		query.setParameter("dateForDay", dateForDay);
		query.setParameter("dateForWeek", dateForWeek);
		users = query.getResultList();

		if (users == null || users.size() == 0) {
			return null;
		}

		sql = "SELECT m " + "FROM Mails m WHERE m.user.id = :GetUserId "
				+ "ORDER BY IsImediateMessage DESC";

		query = em.createQuery(sql).setParameter("GetUserId",
				users.get(0).getId());
		mails = query.getResultList();

		return mails;
	}

	@Transactional //Used for find a mail to delete when this method is not called in a same transaction.
	public void removeMails(List<Mail> mails) {
		
		for (Mail mail : mails) {
			Mail temp = em.find(Mail.class, mail.getId());
			em.remove(temp);
		}
	}
	
	@Transactional
	public void updateLastMailSendedDate(User user){
		
		User userToModify;
		
		userToModify = em.find(User.class, user.getId());
		userToModify.setLastMailSendedDate(new Date());
		em.persist(userToModify);		
	}
}
