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
		
		//////////////////
		// ITEMS CREATION
		
/*		/// User 1
		Item i1 = new Item(u1, ItemType.BEER_BARREL);
		Item i2 = new Item(u1, ItemType.BEER_PINT);
		
		u1.addItem(i1);
		u1.addItem(i2);
		
		/// User 2
		Item i3 = new Item(u2, ItemType.BAKG_BIKINI);
		Item i4 = new Item(u2, ItemType.BAKG_CHAIN);
		Item i5 = new Item(u2, ItemType.FLOWERS);
		
		u2.addItem(i3);
		u2.addItem(i4);
		u2.addItem(i5);
		
		/// User 3
		Item i6 = new Item(u3, ItemType.BAKG_TEA);
		Item i7 = new Item(u3, ItemType.HONOR);
		Item i8 = new Item(u3, ItemType.KARATEKA);
		Item i9 = new Item(u3, ItemType.KARATEKA);
		
		u3.addItem(i6);
		u3.addItem(i7);
		u3.addItem(i8);
		u3.addItem(i9);
		
		/// User 4
		Item i10 = new Item(u4, ItemType.BAKG_IAI_DO);
		Item i11 = new Item(u4, ItemType.HONOR);
		Item i12 = new Item(u4, ItemType.BAKG_SILK);
		Item i13 = new Item(u4, ItemType.BAKG_NAKED);
		
		u4.addItem(i10);
		u4.addItem(i11);
		u4.addItem(i12);
		u4.addItem(i13);*/
	
	}
}
