package javablackbelt.inventory.itemservice;

import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ItemService {
	
	// VARIABLE GLOBALE (COMPTEUR D'ITEMS DE L'ENUM)
	
	public static int itemsCount;

	// METHODES
	
	public static void dropRandomItem(User user, int level, String cause){
				
		
		
	}
	
	public static void dropRandomItem(User user, int level, int percent, String cause){
		
	}
	
	public static void dropItem(User user, ItemType itemType, String cause){
		
	}
	
	// COMPTEUR D'ITEMS DE L'ENUM
	
	public static void countItemsType(){
			
		for(ItemType it : ItemType.values()){		
			itemsCount++;
		}
	}
}
