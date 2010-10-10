package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ActiveItems2 {

	private Map<Long, List<Item>> userListActiveItemsMap;
	private Map<Long, List<Item>> groupActiveItemsMap;
	private List<Item> globalItems;

	
	private static ActiveItems2 instance = new ActiveItems2(); // Singleton.

	private Date actualDate = new Date();

	private ActiveItems2(){
		 Map<Long, List<Item>> userListActiveItemsMap = new HashMap<Long, List<Item>>();
		 Map<Long, List<Item>> groupActiveItemsMap = new HashMap<Long, List<Item>>();
		 List<Item> globalItems = new ArrayList<Item>();
	}
	
	
	
	public void addItemToUserListActiveItemsMap(Item item, User user){
		 Long userId = user.getUserId();
		 List<Item> userItemsList = userListActiveItemsMap.get(userId);
		 if (userItemsList==null){
			 //creation of userId in the Map
			 userItemsList = new ArrayList<Item>();
			 userListActiveItemsMap.put(userId, userItemsList);
		 } 
		userItemsList.add(item);
	}
	
	
	public void addItemToGroupActiveItemsMap(Item item, Group group){
		Long groupId = group.getGroupId();
		 List<Item> groupItemsList = groupActiveItemsMap.get(groupId);
		 if (groupItemsList==null){
			 //creation of userId in the Map
			 groupItemsList = new ArrayList<Item>();
			 groupActiveItemsMap.put(groupId, groupItemsList);
		 } 
		groupItemsList.add(item);
	}
	

	public void addItemToGloballyActiveItems(Item item){
		globalItems.add(item);
	}
	
	
	

	public static ActiveItems2 getInstance() {
		return instance;
	}
}
