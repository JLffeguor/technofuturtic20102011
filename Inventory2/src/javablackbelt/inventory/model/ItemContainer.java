package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemContainer {
	
	private List<Item> inventoryItems = new ArrayList<Item>();
	
	/**
	 * To add an item in the list
	 * @param itemToAdd
	 */
	public void addItem(Item itemToAdd) {
		inventoryItems.add(itemToAdd);
		itemToAdd.setOwner(this); // changement du proprietaire de l'objet
	}
	
	
	/**
	 * To remove an item in the list
	 * @param itemToRemove
	 */
	public void removeItem(Item itemToRemove) {
		inventoryItems.remove(itemToRemove);  // supprime un objet de l'inventaire d'un user
	}
	
	
	
}
