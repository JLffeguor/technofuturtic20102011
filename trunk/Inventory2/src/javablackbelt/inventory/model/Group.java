package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Class Group.
 * A group is composed of different users.
 */
@Entity
@Table(name="Group_")
public class Group {

	@Id
	@GeneratedValue
	private Long groupId;
	
	//private static long countGroup = 0 ;

	private String groupName;
	private List<User> listOfUsers = new ArrayList<User>();

	public Group(String groupName) {
		this.groupName = groupName;
		//this.groupId = ++Group.countGroup;	
	}
	
	public void addUserToGroup(User user) {
		listOfUsers.add(user);
	}
	
	public String getGroupName() {
		return groupName;
	}

	public Long getGroupId() {
		return groupId;
	}
	
	public void displayListOfUser() {
		for(User u : listOfUsers) {
			System.out.println(u.getNickName() + ", " + u.getUserId());
		}
	}
	
	public List<User> getListOfUser() {
		return this.listOfUsers;
	}
}
