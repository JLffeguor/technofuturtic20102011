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

public class ActiveItems {

	private Map<Long, List<Item>> userListActiveItemsMap;
	private Map<Long, List<Item>> groupActiveItemsMap;
	private List<Item> globalItems;

	private static ActiveItems instance = new ActiveItems();

	/** Singleton. */

	private ActiveItems() {
		userListActiveItemsMap = new HashMap<Long, List<Item>>();
		groupActiveItemsMap = new HashMap<Long, List<Item>>();
		globalItems = new ArrayList<Item>();
	}

	public static ActiveItems getInstance() {
		return instance;
	}

	/**
	 * returns the active item list for a specific user. Return null if no list
	 * update of the user's list of active items (suppress the non-active items
	 * thanks to the removal date)
	 */
	synchronized public List<Item> getActiveItems(User user) {
		return getActiveItems(userListActiveItemsMap, user.getUserId());
	}

	/**
	 * returns the active item list for a specific group. Return null if no list
	 * update of the group's list of active items (suppress the non-active items
	 * thanks to the removal date)
	 */
	synchronized public List<Item> getActiveItems(Group group) {
		return getActiveItems(groupActiveItemsMap, group.getGroupId());
	}

	synchronized private List<Item> getActiveItems(Map<Long, List<Item>> map, long id) {
		
		List<Item> itemsInMap = map.get(id);
		if (itemsInMap != null) { // Group has active items (maybe too old, we'll see...)
			
			removeOldItems(itemsInMap);

			if (itemsInMap.isEmpty()) { // No more active item => let's remove
										// the empty list from the map.
				// remove the group mapKey from the Map (remove the user)
				groupActiveItemsMap.remove(id);
			}
			
		}
		return itemsInMap;
		
	}
	
	
	/** returns the globally active item. Return null if no list */
	synchronized public List<Item> getGlobalActiveItems(ItemType itemType) {

		removeOldItems(globalItems);

		// Creating a list to get items of a specified itemType
		List<Item> itemsInList = new ArrayList<Item>();
		for (int i = 0; i < globalItems.size(); i++) {
			if (itemType.equals(globalItems.get(i).getItemType())) {
				itemsInList.add(globalItems.get(i)); // put it into the itemsInList
			}
		}

		return itemsInList;
	}

	/** print active items of the itemTypeGroup (Background or image_home) */
	synchronized public List<Item> getActiveItems(ItemType.Group itemTypeGroup) {
		
		removeOldItems(globalItems);

		// Creating a list to get items of a specified itemTypeGroup
		List<Item> itemsInList = new ArrayList<Item>();
		for (int i = 0; i < globalItems.size(); i++) {
			if (itemTypeGroup.equals(globalItems.get(i).getItemType().getItemTypeGroup())) {
				itemsInList.add(globalItems.get(i)); // put it into the itemsInList
			}
		}

		return itemsInList;
	}

	/** Remove old elements (that are not active anymore) from the given list */
	synchronized public void removeOldItems(List<Item> itemsList) {

		for (int i = 0; i < itemsList.size();) {
			if ((new Date().after(itemsList.get(i).getRemovalDate()))) { // Item is too old! (has been activated long ago
				itemsList.remove(i);
			} else { // Ok, Item is not too old.
				i++; // Next one.
			}
		}
	}

	///////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	///////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	///////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	///////// Methods to add an item in a Map (User or Group) or in the globalList ////////

	/** Don't call this directly, go through ItemService.activateItemOn... */
	synchronized public void addItemToUserListActiveItemsMap(Item item,
			User user) {
		
		// If the list for the specified user is null, create it
		if(userListActiveItemsMap.get(user.getUserId()) == null){
		
			List<Item> userItemsList = new ArrayList<Item>();
			userListActiveItemsMap.put(user.getUserId(), userItemsList);	
		}
		
		// in all cases, add an item into the list 		
		userListActiveItemsMap.get(user.getUserId()).add(item);
	}

	/** Don't call this directly, go through ItemService.activateItemOn... */
	synchronized public void addItemToGroupActiveItemsMap(Item item, Group group) {
			
		// If the list for the specified group is null, create it
		if(groupActiveItemsMap.get(group.getGroupId()) == null){
		
			List<Item> groupItemsList = new ArrayList<Item>();
			groupActiveItemsMap.put(group.getGroupId(), groupItemsList);	
		}
		
		// in all cases, add an item into the list 		
		groupActiveItemsMap.get(group.getGroupId()).add(item);
	}

	/** Don't call this directly, go through ItemService.activateItemOn... */
	/** Replace active item by a new active item (background or image_home) */
	synchronized public void addItemToGloballyActiveItems(Item item) {
		
		// If the globalItems list is empty, add the first item into
		if(globalItems.isEmpty()){
			globalItems.add(item);
			
		} else {
			
			// Loop on the globalItems list to see if there's already an item of the same itemTypeGroup of the parameter
			// There can be only one item  of each itemTypeGroup in the globalItems list (HOME_PAGE,BACKGROUND)
			
			for(int i=0;i<globalItems.size();i++){
				
				// if one itemTypeGroup already exists in the list, replace it with incoming item.
				if (item.getItemType().getItemTypeGroup().equals(globalItems.get(i).getItemType().getItemTypeGroup())) {
					globalItems.set(globalItems.indexOf(globalItems.get(i)), item);
				} else {
					globalItems.add(item); // add into the global list
				}
			}
		}
	}
}