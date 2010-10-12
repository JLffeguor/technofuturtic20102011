package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Group.
 * A group is composed of different users.
 */
public class Group {

	private static long countGroup = 0 ;

	private String groupName;
	private List<User> listOfUsers = new ArrayList<User>();
	private long groupId;

	public Group(String groupName) {
		this.groupName = groupName;
		this.groupId = ++Group.countGroup;	
	}
	
	public void addUserToGroup(User user) {
		listOfUsers.add(user);
	}
	
	public String getGroupName() {
		return groupName;
	}

	public long getGroupId() {
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
