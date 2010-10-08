package javablackbelt.inventory.model;

import java.util.Date;
import java.util.GregorianCalendar;

import javablackbelt.inventory.itemservice.ItemService;


public class Main {

	/**
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		//////////////////
		// USERS CREATION
		
		User u1 = new User("Pedro");
		User u2 = new User("Garcia");
		User u3 = new User("Antoine");
		User u4 = new User("Kumiko");
		
		ItemService.dropRandomItem(u1, 2, 60, "Black belt");
		ItemService.dropRandomItem(u3, 4, "Black Belt");
		ItemService.dropItem(u2, ItemType.BEER_BARREL, "Welcome");
		
		
		
		//////////////////
		// GROUP CREATION
		
		Group g1 = new Group("The Tech No Futur Team");
		
		
	}
}
