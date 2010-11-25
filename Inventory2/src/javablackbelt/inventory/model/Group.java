package javablackbelt.inventory.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Class Group. A group is composed of different users.
 */
@Entity
@Table(name = "Group_")
public class Group {

	@Id
	@GeneratedValue
	private Long groupId;

	@ManyToMany(mappedBy="groupSet")
	private Set<User> setOfUsers = new HashSet<User>();

	private String groupName;

	public Group(){}
	
	public Group(String groupName) {
		this.groupName = groupName;
	}

	public void addUserToGroup(User user) {
		setOfUsers.add(user);
	}
	
	public void displaySetOfUser() {

		System.out.println(setOfUsers.size());
		for (User u : setOfUsers) {
			System.out.println(u.getNickName());
		}
	}
	
	//////////////////////////////////////////////////////////////////
	///////////////////// Generated Code /////////////////////////////
	//////////////////////////////////////////////////////////////////
	
	public String getGroupName() {
		return groupName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public Set<User> getSetOfUser() {
		return setOfUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
		Group other = (Group) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}
}