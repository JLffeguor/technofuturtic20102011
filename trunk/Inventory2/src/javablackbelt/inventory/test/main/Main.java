package javablackbelt.inventory.test.main;

import javablackbelt.inventory.itemservice.ItemService;
import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		User pierre = new User("Pierre");
		User david = new User("David");
		User mat = new User ("Mat");
		
		Group g1=new Group("Bru-JUG");
		
		
		
		
		Item beerGlass = new Item(pierre,ItemType.BEER_PINT);
		Item backSleep = new Item(pierre,ItemType.BAKG_SLEEP);
		
		// à mon avis, pas top. Ce n'est pas à l'utilisateur d'ajouter un objet
		// à sa liste mais à la classe ItemService. Pour les tests, on utilise le user.addItem(item);
		pierre.addItem(beerGlass); 
		pierre.addItem(backSleep);
		
		
		
		ItemService iServ = new ItemService();
		iServ.sendItemTo(pierre,backSleep,david);
	
		//gerer les receveurs : USER, GROUP, GLOBAL
		
		
		
	}

	
}
