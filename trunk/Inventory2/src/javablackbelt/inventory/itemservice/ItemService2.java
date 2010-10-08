package javablackbelt.inventory.itemservice;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemContainer;
import javablackbelt.inventory.model.User;



public class ItemService2 {

	
	public void sendItemTo(User sender,Item item, ItemContainer receiver){
		//ajouter l'item dans la InventoryItems de la cible
		receiver.addItem(item);
		
		//envoi d'un email au receveur
		if (receiver instanceof User) {
			User u=(User)receiver;
			System.out.println(u.getNickName()+ " vient de recevoir dans son inventaire "
					+item.getItemType().getItemName()+ " de la part de "+sender.getNickName());
		}
		
		if (receiver instanceof Group) {
			Group g=(Group)receiver;
			System.out.println(g.getGroupName()+ " vient de recevoir dans son inventaire "
					+item.getItemType().getItemName()+ " de la part de "+sender.getNickName());
		}
		
		//supprimer l'item de la InventoryItems de l'envoyeur
		sender.removeItem(item);
	}
	
	
	
	
	public void activateItemTo(User sender,Item item, User receiver){
		//afficher sur la page l'objet envoyé
		System.out.println(item.getItemType().getItemName()
				+ " vient d'�tre activ�e sur la page de "+receiver.getNickName()+" !");
		
		//ajouter l'item dans la InventoryItems de la cible
		receiver.addItem(item);
		
		//initialiser la activationDate et calculer la removalDate
		item.setActivationDate();
		
		//initialiser la variable activationDescription (Example : "Refreshing Pint of Belgian Beer offered by (sender)".
		
		
	
		//supprimer l'item de la InventoryItems de l'envoyeur
		sender.removeItem(item);
	}
	
	
	
	
}
