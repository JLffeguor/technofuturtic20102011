package javablackbelt.inventory.test.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javablackbelt.inventory.itemservice.ActiveItems;
import javablackbelt.inventory.model.Item;

public class Display {


	/** display list by user */
	public void displayActiveItemsByList(Map<Long, List<Item>> inMap,
			Long mapKey) {

		for (Item i : inMap.get(mapKey)) {
			System.out.println("");
			System.out.print(i.getItemType().getItemName() + " "
					+ i.getItemType().getItemDescription());
			System.out.println("");
		}
	}

	/** display the globalList */
	public void displayGlobalItems() {
		
		List<Item> globalItems = new ArrayList<Item>();
		globalItems = ActiveItems.getInstance().getGlobalItemsList();
		
		for (Item i : globalItems) {
			System.out.println("");
			System.out.print(i.getItemType().getItemName() + " "
					+ i.getItemType().getItemDescription());
			System.out.println("");
		}
	}
	
}
