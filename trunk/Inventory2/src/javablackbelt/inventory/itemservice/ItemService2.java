package javablackbelt.inventory.itemservice;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemContainer;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.TargetType;
import javablackbelt.inventory.model.User;

public class ItemService2 {

	public void sendItemTo(User sender,Item item, User receiver){

		//vérification de la destination de l'Item
		//System.out.println(item.getItemType().getTargetType());
		if (item.getItemType().getTargetType()==TargetType.GLOBAL){
			throw new RuntimeException("Global items cannot be sent to a user or a group ! ");

		} 
		

			//envoi d'un email au receveur (User)
			if (receiver instanceof User) {
				if(item.getItemType().getTargetType()==TargetType.USER){
					User u=(User)receiver;

					//ajouter l'item dans la InventoryItems de la cible
					receiver.addItem(item);
					System.out.println(u.getNickName()+ " vient de recevoir dans son inventaire "
							+item.getItemType().getItemName()+ " de la part de "+sender.getNickName());
				} else {
					System.out.println("Cet objet ne peut être envoyé qu'à un groupe !");
				}
			} 
			
			if (receiver instanceof Group) {
				if(item.getItemType().getTargetType()==TargetType.GROUP){
					Group g=(Group)receiver;

					//ajouter l'item dans la InventoryItems de la cible
					receiver.addItem(item);
					System.out.println(g.getGroupName()+ " vient de recevoir dans son inventaire "
							+item.getItemType().getItemName()+ " de la part de "+sender.getNickName());


					//supprimer l'item de la InventoryItems de l'envoyeur
					sender.removeItem(item);
				} else {
					System.out.println("Cet objet ne peut être envoyé qu'à un user !");
				}
			}

	}

	public void activateItemGlobally(User sender, Item item) {

	public void activateItemOnGroup(User sender, Item item, Group) {

	public void activateItemOnUser(User sender, Item item, User user) {

		if (item.getItemType().getTargetType() == TargetType.GLOBAL) {
			RuntimeEx
			System.out
			.println("Cet objet ne peut être activé que sur BlackBeltFactory !");
		} else {
			ENGLISH // afficher sur la page l'objet envoyé
			if (receiver instanceof User) {
				User u = (User) receiver;
				System.out.println(item.getItemType().getItemDescription()
						+ " vient d'être activé sur le profil de "
						+ u.getNickName() + " par de " + sender.getNickName());
			}

			// envoi d'un email au receveur (Group)
			if (receiver instanceof Group) {
				Group g = (Group) receiver;
				System.out.println(item.getItemType().getItemName()
						+ " vient d'être activé sur le profil de "
						+ g.getGroupName() + " par de " + sender.getNickName());
			}



			// initialiser la activationDate et calculer la removalDate
			item.setActivationDate();

			// initialiser la variable activationDescription (Example :
			// "Refreshing Pint of Belgian Beer offered by (sender)".

			// supprimer l'item de la InventoryItems de l'envoyeur
			// sender.removeItem(item);
		}
	}

	public void activateItemTo(User sender, Item item) {

		if (item.getItemType().getTargetType() != TargetType.GLOBAL) {
			System.out
			.println("Cet objet ne peut être activé que sur un user ou un groupe !");
		} else {
			// afficher sur la page l'objet envoyé
			System.out
			.println(item.getItemType().getItemName()
					+ " vient d'etre activee sur la page de BlackBeltFactory !");

			// initialiser la activationDate et calculer la removalDate
			item.setActivationDate();

			// initialiser la variable activationDescription (Example :
			// "Refreshing Pint of Belgian Beer offered by (sender)".

			// supprimer l'item de la InventoryItems de l'envoyeur
			// sender.removeItem(item);
		}
	}

}
