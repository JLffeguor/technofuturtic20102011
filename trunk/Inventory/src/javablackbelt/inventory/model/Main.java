package javablackbelt.inventory.model;

import javablackbelt.inventory.itemservice.ItemService;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Item test = new Item(ItemType.BEER_BARREL);
				
		test.displayItemInfos();
	
		ItemService.countItemsType();
	
	}
}
