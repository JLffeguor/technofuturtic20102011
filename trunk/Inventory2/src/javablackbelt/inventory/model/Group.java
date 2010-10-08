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
	   
	/////////////////////////
	// VARIABLE DECLARATION
	
	private String groupName;
	private List<User> listOfUsers = new ArrayList<User>();
	//private List<Item> inventoryItems = new ArrayList<Item>();
	
	///////////////////////////
	// CONSTRUCTOR AND METHODS
	
	/**
	 * Constructor.
	 * @param groupName the name of the group
	 */
	public Group(String groupName) {
		this.groupName = groupName;
	}
	
	
	
	///////////////////////
	// GETTERS AND SETTERS
	
	/**
	 * To get the name of the group.
	 * @return the group name
	 */
	public String getGroupName() {
		return groupName;
	}
	

}
