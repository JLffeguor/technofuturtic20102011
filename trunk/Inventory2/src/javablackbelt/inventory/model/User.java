package javablackbelt.inventory.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User class.
 * A user has a nick name and a set of items and is linked to a group 
 */
@Entity
@Table(name="User_")
public class User  {

	@Id
	@GeneratedValue
	private Long userId;
	
	@OneToMany(mappedBy="owner")
	private Set<Item> inventoryItems = new HashSet<Item>();
	
	@ManyToMany
	@JoinTable(name="USER_GROUP",
			joinColumns=@JoinColumn(name="USER_ID"),
			inverseJoinColumns=@JoinColumn(name="GROUP_ID"))
	Set<Group> groupSet = new HashSet<Group>();
	
	private String nickName;
	private Date birthDate;
	
	public User(String nickName, Date birthDate) {
		this.nickName = nickName;
		this.birthDate = birthDate;
	}
	
	public User(){}
	
	public void addGroupToUser(Group group){
		groupSet.add(group);
	}

	public void addItem(Item itemToAdd) {
		inventoryItems.add(itemToAdd);
		itemToAdd.setOwner(this); /** change of object owner */
	}

	public void removeItem(Item itemToRemove) {
		inventoryItems.remove(itemToRemove);  /** delete an object from user inventory */
	}
	
	public Set<Item> getInventoryItems() {
		return this.inventoryItems;
	}
	
	//////////////////////////////////////////////////////////////////
	///////////////////// Generated Code /////////////////////////////
	//////////////////////////////////////////////////////////////////

	public String getNickName() {
		return nickName;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}	
}