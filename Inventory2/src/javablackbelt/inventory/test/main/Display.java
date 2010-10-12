package javablackbelt.inventory.test.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javablackbelt.inventory.itemservice.ActiveItems;
import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.User;

public class Display {

	/** display list of active items by user */
	public static void displayActiveItemsByList(Map<Long, List<Item>> inMap,
			Long mapKey) {

		for (Item i : inMap.get(mapKey)) {
			System.out.println("");
			System.out.print(i.getItemType().getItemName() + " "
					+ i.getItemType().getItemDescription());
			System.out.println("");
		}
	}

	/** display the globalList of active items */
	public static void displayGlobalItems() {

		List<Item> globalItems = new ArrayList<Item>();
		globalItems = ActiveItems.getInstance().getGlobalItemsList();

		for (Item i : globalItems) {
			System.out.println("");
			System.out.print(i.getItemType().getItemName() + " "
					+ i.getItemType().getItemDescription());
			System.out.println("");
		}
	}

	/** display the user list */
	public static void displayUserItemsList(User user) {
		System.out.println("	------- " + user.getNickName() + "'s inventory -------");
		if(user.getInventoryItems().isEmpty()) {
			System.out.println("xxxxx NO ITEM xxxxx");
		}
		else{
			for (Item i : user.getInventoryItems()) {
				System.out.println("--> Item : " + i.getItemType().getItemName());
				System.out.println("--> Description : "
						+ i.getItemType().getItemDescription() + "\n--> Activaton date : "
						+ i.getActivationDate() + "\n--> Removal date : "
						+ i.getRemovalDate() + "\n");
			}
		}
		System.out.println("");
	}
	
	public static void displayGroupItemsList(Group group) {
		System.out.println("	------- " + group.getGroupName() + "'s inventory -------");
		if(ActiveItems.getInstance().getActiveItems(group) == null) {
			System.out.println("xxxxx NO ITEM xxxxx");
		}
		else{
			for (Item i : ActiveItems.getInstance().getActiveItems(group)) {
				System.out.println("--> Item : " + i.getItemType().getItemName());
				System.out.println("--> Description : "
						+ i.getItemType().getItemDescription() + "\n--> Activaton date : "
						+ i.getActivationDate() + "\n--> Removal date : "
						+ i.getRemovalDate() + "\n");
			}
		}
		System.out.println("");
	}
}
