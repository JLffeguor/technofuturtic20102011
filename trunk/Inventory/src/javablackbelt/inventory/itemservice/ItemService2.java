package javablackbelt.inventory.itemservice;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.User;



public class ItemService2 {

	
	public void sendItemTo(User sender,Item item, User receiver){
		//ajouter l'item dans la InventoryItems de la cible
		receiver.addItem(item);
		
		//envoi d'un email au receveur
		System.out.println(receiver.getNickName()+ " vient de recevoir dans son inventaire "
				+item.getItemType().getItemName()+ " de la part de "+sender.getNickName());
		
		//supprimer l'item de la InventoryItems de l'envoyeur
		sender.removeItem(item);
	}
	
	
	
	
	public void activateItemTo(User sender,Item item, User receiver){
		//afficher sur la page l'objet envoyé
		System.out.println(item.getItemType().getItemName()
				+ " vient d'être activé sur la page de "+receiver.getNickName()+" !");
		
		//ajouter l'item dans la InventoryItems de la cible
		receiver.getInventoryItems().add(item);
		
		//initialiser la activationDate et calculer la removalDate
		item.setActivationDate();
		
		//initialiser la variable activationDescription (Example : "Refreshing Pint of Belgian Beer offered by (sender)".
		
		
		//changer l'owner de l'item
		item.setOwner(receiver);
		
		//supprimer l'item de la InventoryItems de l'envoyeur
		sender.getInventoryItems().remove(item);
	}
	
	
	
}
