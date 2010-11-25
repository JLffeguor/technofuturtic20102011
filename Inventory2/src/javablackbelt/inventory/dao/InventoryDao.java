package javablackbelt.inventory.dao;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class InventoryDao {

	@PersistenceContext
	private EntityManager em;

	
	public void getUsersByBirthDate(Date birthDate){
		Date now = new Date() ; 
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(now);
//		
//		int wday  = cal.DAY_OF_MONTH;
//		int wmonth = cal.MONTH;
//		cal.getCalendar.DAY_OF_MONTH;
//		List<User> userList = (List<User>)em.createQuery("select u from User u where u.birthDate <=:birthDate ")
//		.setParameter("birthDate", birthDate)
//		.getResultList();
//	
//		System.out.println(userList.size());
		
//		for(User u : userList){
//			System.out.println("User name : " + u.getNickName() + "  / birthDate : " + u.getBirthDate());
//		}
	}
		
	
	
	
	
	
	
	public void persist(Object obj){
		
		if(obj != null){
			em.persist(obj);
		}
	}
	
	public Object merge(Object obj) {
		
		return em.merge(obj);
	}	
	
	public User findUser(Long id){
		
		User theUser = em.find(User.class, id);
		
		return theUser;
	}
	
	public Group findGroup(Long id){
		
		Group theGroup = em.find(Group.class, id);
		
		return theGroup;
	}
}
