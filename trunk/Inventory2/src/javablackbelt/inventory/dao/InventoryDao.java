package javablackbelt.inventory.dao;

import java.util.Calendar;
import java.util.List;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
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

	
	public List<User> getUsersByBirthDate(){

		Calendar calendar = Calendar.getInstance();
		 
		int monthOfBirth = calendar.get(Calendar.MONTH);
		int dayOfBirth = calendar.get(Calendar.DAY_OF_MONTH);
		
		monthOfBirth = monthOfBirth + 1 ; // for gregorian calendar
		
		List<User> userList = (List<User>)em
		.createQuery("select u from User u where day(u.birthDate) =:dayOfBirth and month(u.birthDate) =:monthOfBirth")
		.setParameter("dayOfBirth", dayOfBirth).setParameter("monthOfBirth", monthOfBirth)
		.getResultList();
		
		return userList;
	}
	
	public List<Item> getUsedItems(){

		Calendar calendar = Calendar.getInstance();
		 
		int monthOfToday = calendar.get(Calendar.MONTH);
		int dayOfToday = calendar.get(Calendar.DAY_OF_MONTH);
		
		monthOfToday = monthOfToday + 1 ; // for gregorian calendar
		
		List<Item> itemList = (List<Item>)em
		.createQuery("select i from Item i where day(i.removalDate) <:dayOfToday and month(u.removalDate) <:monthOfToday")
		.setParameter("dayOfToday", dayOfToday).setParameter("monthOfToday", monthOfToday)
		.getResultList();
		
		return itemList;
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
