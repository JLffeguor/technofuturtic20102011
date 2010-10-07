package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Group.
 * A group is composed of different users.
 * @author forma710
 *
 */
public class Group {
	

	private String groupName;
	private List<User> listOfUsers = new ArrayList<User>();
	private List<Item> listOfItems = new ArrayList<Item>();
	
	/**
	 * Constructor.
	 * @param groupName the name of the group
	 */
	public Group(String groupName) {
		this.groupName = groupName;
	}
	
	/**
	 * To get the name of the group.
	 * @return the group name
	 */
	public String getGroupName() {
		return groupName;
	}
	
	/**
	 * To get the list of users.
	 * @return a list of users.
	 */
	public List<User> getListOfUsers() {
		return listOfUsers;
	}
	
	
	public List<Item> getListOfItems() {
		return listOfItems;
	}
}
