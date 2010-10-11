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


//
public class ActiveItems {

	private Map<Long, List<Item>> userListActiveItemsMap;
	private Map<Long, List<Item>> groupActiveItemsMap;
	private List<Item> globalItems;

	private static ActiveItems instance = new ActiveItems();

	/** Singleton. */

	private ActiveItems() {
		Map<Long, List<Item>> userListActiveItemsMap = new HashMap<Long, List<Item>>();
		Map<Long, List<Item>> groupActiveItemsMap = new HashMap<Long, List<Item>>();
		List<Item> globalItems = new ArrayList<Item>();
	}

	
	

	
	/** returns the active item list for a specific user. Return null if no list
	update of the user's list of active items (suppress the non-active items thanks to the removal date)
	*/
	public List<Item> getActiveItems(User user) {

		List<Item> tempItemList = new ArrayList<Item>();
		Long mapKey = user.getUserId();

		// if no list, return null
		if (userListActiveItemsMap.get(mapKey) == null) {
			return null;
		} else {
			List<Item> genericItemList = new ArrayList<Item>();
			genericItemList.addAll(userListActiveItemsMap.get(mapKey));

			tempItemList = itemsStillActivated(genericItemList);

			if (tempItemList != null) {
				userListActiveItemsMap.get(mapKey).clear();
				userListActiveItemsMap.put(mapKey, tempItemList);
			}
			return tempItemList;
		}
	}

	
	/**returns the active item list for a specific group. Return null if no list
	update of the group's list of active items (suppress the non-active items thanks to the removal date)
	*/
	public List<Item> getActiveItems(Group group) {

		List<Item> tempItemList = new ArrayList<Item>();
		Long mapKey = group.getGroupId();

		// if no list, return null
		if (groupActiveItemsMap.get(group.getGroupId()) == null) {
			return null;
		} else {
			List<Item> genericItemList = new ArrayList<Item>();
			genericItemList.addAll(groupActiveItemsMap.get(mapKey));

			tempItemList = itemsStillActivated(genericItemList);

			if (tempItemList != null) {
				groupActiveItemsMap.get(mapKey).clear();
				groupActiveItemsMap.put(group.getGroupId(), tempItemList);
			}
			return tempItemList;
		}
	}

	
	
	/** returns the globally active item. Return null if no list */
	public List<Item> getGlobalActiveItems(ItemType itemType) {

		
		List<Item> tempItemList = new ArrayList<Item>();
		tempItemList = itemsStillActivated(globalItems);
		
		return tempItemList;
	}
	
	
	
	/** print active item on the itemTypeGroup (Background or image_home) */
	public void getActiveItems(ItemType.Group itemTypeGroup) {

		List<Item> tempItemList = new ArrayList<Item>();
		tempItemList = itemsStillActivated(globalItems);

		for (Item i : tempItemList) {
			if (i.getItemType().getItemTypeGroup() == itemTypeGroup) {
				System.out.println(i.getItemType().getItemDescription());
			}
		}
	}
	
	
	/** returns the items still activated */
	public List<Item> itemsStillActivated(List<Item> genericItemList) {
		// Remove old items (that should not be active anymore).
		List<Item> tempItemList = new ArrayList<Item>();

		for (Item i : genericItemList) {
			if (!(new Date().after(i.getRemovalDate()))) {
				tempItemList.add(i);
			}
		}
		genericItemList.clear();
		return tempItemList;
	}

	
	/** Don't call this directly, go through ItemService.activateItemOn... */
	public void addItemToUserListActiveItemsMap(Item item, User user) {
		Long userId = user.getUserId();
		List<Item> userItemsList = groupActiveItemsMap.get(userId);

		// Create list if no list yet.
		if (userItemsList == null) {

			// ... /** creation of userId in the Map */

			userItemsList = new ArrayList<Item>();
			groupActiveItemsMap.put(userId, userItemsList);
		}
		userItemsList.add(item);
	}

	
	/** Don't call this directly, go through ItemService.activateItemOn... */
	public void addItemToGroupActiveItemsMap(Item item, Group group) {
		Long groupId = group.getGroupId();
		List<Item> groupItemsList = groupActiveItemsMap.get(groupId);
		if (groupItemsList == null) {

			/** creation of userId in the Map */

			groupItemsList = new ArrayList<Item>();
			groupActiveItemsMap.put(groupId, groupItemsList);
		}
		groupItemsList.add(item);
	}

	
	/** Don't call this directly, go through ItemService.activateItemOn... */
	public void addItemToGloballyActiveItems(Item item) {
		for (Item i:globalItems){
			if(i.getItemType().getItemTypeGroup()==item.getItemType().getItemTypeGroup()){
				globalItems.set(index, item);
			}
		}
		globalItems.add(item);
	}

	public static ActiveItems getInstance() {
		return instance;
	}
}