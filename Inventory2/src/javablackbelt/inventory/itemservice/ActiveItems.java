package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ActiveItems {

	private Map<Long, List<Item>> userListActiveItemsMap;
	private Map<Long, List<Item>> groupActiveItemsMap;
	private List<Item> globalItems;

	private static ActiveItems instance = new ActiveItems(); // Singleton.
	
	private ActiveItems(){
		 Map<Long, List<Item>> userListActiveItemsMap = new HashMap<Long, List<Item>>();
		 Map<Long, List<Item>> groupActiveItemsMap = new HashMap<Long, List<Item>>();
		 List<Item> globalItems = new ArrayList<Item>();
	}
	
	public void getActiveItems(ItemType.Group itemTypeGroup) {

		for(Item i : globalItems) {
			if(new Date().after(i.getRemovalDate())) {
				globalItems.remove(i);
			} else if(i.getItemType().getItemTypeGroup() == itemTypeGroup) // return i;
				System.out.println(i.getItemType().getItemDescription());
		}
	}

	public void getActiveItems(User user) {

		for (Item i : userListActiveItemsMap.get(user.getUserId())) {
			if(new Date().after(i.getRemovalDate())) {
				userListActiveItemsMap.remove(i);
				user.removeItem(i);
			} else // return i;
				System.out.println(i.getItemType().getItemDescription());
		}
	}

	public void getGlobalActiveItems(ItemType itemType) {

		for (Item i : globalItems) {
			if(new Date().after(i.getRemovalDate())) {
				globalItems.remove(i);
			} else if (itemType.equals(i.getItemType())) // return i;
				System.out.println(i.getItemType().getItemName());
		}
	}

	public ActiveItems getInstance() {
		return instance;
	}
}