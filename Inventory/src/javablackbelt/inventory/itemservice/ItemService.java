package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.List;

import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ItemService {

	/**
	 * METHODES dropRandomItem
	 * 
	 * @param user
	 * @param level
	 * @param cause
	 */

	public static void dropRandomItem(User user, int level, String cause) {

		/**
		 * VARIABLES
		 */

		int itemCount = 0;

		/**
		 * LISTES TEMPORAIRES
		 */

		List<ItemType> tempItemList = new ArrayList<ItemType>();
		List<Item> userList = new ArrayList<Item>();

		userList = user.getListOfItems();

		/**
		 * PARCOURS DE L'ENUM ET DE LA LISTE DE L'USER
		 */

		for (ItemType enumItems : ItemType.values()) {
			for (Item userListItems : userList) {

				/**
				 * SI ENUMITEMS (TYPE ITEM) EST EGAL A USERLISTITEMS.GETUSERTYPE
				 * (TYPE DE L'ITEM DANS LA LISTE DE L'USER)
				 */

				if (!(enumItems.equals(userListItems.getItemType()))) {

					/**
					 * 
					 * AJOUT DANS LA LISTE TEMPORAIRE QUI VA RECUP LES ELEMENTS
					 * NN PRESENTS DANS LA LISTE DE L'USER MAIS PRESENTS DANS
					 * L'ENUM
					 * 
					 */

					tempItemList.add(enumItems);
				}
			}
		}

		/**
		 * GENERATION VALEUR ALEATOIRE COMPRISE ENTRE 0 ET LA TAILLE DE LA LISTE
		 */

		int random = (int) ((Math.random() * (tempItemList.size() - 0)) + 0);

		/**
		 * PARCOURS DE LA LISTE TEMPORAIRE
		 */

		for (ItemType item : tempItemList) {

			/**
			 * SI LE COMPTEUR EST EGAL A LA VALEUR ALEATOIRE
			 */

			if ((itemCount == random) && (level == item.getItemLevel())) {

				/**
				 * CREATION D'UN OBJET ITEM ET ENVOI DANS LA LISTE (SAC) DE L'USER
				 */

				Item userItem = new Item(user, item, user);
				userItem.setCause(cause);
				user.addItem(userItem);

				/**
				 * AVERTIT L'UTILISATEUR QU'IL A OBTENU UN ITEM
				 */

				System.out.println("L'utilisateur : " + user + "a reçu : "
						+ item.getItemName());
			}

			/**
			 * SINON INCREMENTATION DU COMPTEUR
			 */

			else {
				itemCount++;
			}
		}
	}

	public static void dropRandomItem(User user, int level, int percent,
			String cause) {

	}

	public static void dropItem(User user, ItemType itemType, String cause) {

		/**
		 * INSTANCIATION D'UN OBJET ITEM + AJOUT A LA LISTE (SAC) DE L'USER
		 */

		Item userItem = new Item(user, itemType, user);
		userItem.setCause(cause);
		user.addItem(userItem);
	}
}
