package javablackbelt.inventory.test.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javablackbelt.inventory.itemservice.ActiveItems;
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

		for (Item i : user.getInventoryItems()) {
			System.out.println("Item : " + i.getItemType().getItemName());
			System.out.println("Description : "
					+ i.getItemType().getItemDescription());
		}
	}
}
