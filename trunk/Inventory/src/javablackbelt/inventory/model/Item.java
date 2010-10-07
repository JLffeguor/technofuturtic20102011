package javablackbelt.inventory.model;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class Item.
 * This class describe an item.
 * @author forma710
 *
 */
public class Item {
	
	private User owner;
	private ItemType itemType;
	
	/** User who dropped that item initially. If initialOwner <> owner,
	that item has been given by initialOwner to somebody else, then to owner */
	private final User initialOwner;
	
	/** when that item has been dropped */
	private final Date creationDate;
	
	/** when that item has been used. Useful for items that last some 
	time (as 24h) when activated. null if not activated yet. */
	private Date activationDate;
	
	/** when this activated target should be deleted (by a batch).
	Not null if activationDate is not null. */
	private Date removalDate;
	
	/** A short description of why this item has been created.
	e.g.: auction, black belt,... Null if activationDate is null.*/
	private String cause; 
	
	/** Non null when activated. e.g.: "Refreshing pint of
	Belgian beer offered by John<link>" */
	String activationDescription;
	
	/** User on which the item has been activated.
	Either group or user, if any. e.g.: a beer is offered to somebody */
	User userTarget;
	Group groupTarget;
	
	/**
	 * Constructor.
	 * 
	 * @param itemType the type of item.
	 * @param initialOwner the initial owner.
	 */
	public Item(User owner, ItemType itemType, User initialOwner) {
		this.owner = owner;
		this.itemType = itemType;
		this.initialOwner = initialOwner;
		this.creationDate = new Date();
	}
	
	/**
	 * When this method is called the current date
	 * is set into activation date.
	 * This method set also the removal date.
	 */
	public void setActivationDate() {
		// set activation date and removal date with the current date.
		this.activationDate = this.removalDate = new Date();  
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(this.removalDate);
		// add the item duration (in hours) to the activation date.
		cal.add(GregorianCalendar.HOUR, this.itemType.getItemDuration());
		this.removalDate = cal.getTime();
	}
	
	/**
	 * To insert a text that explain why this item has
	 * been created.
	 * @param cause
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	/**
	 * The activation description is set when the item
	 * is activated.
	 * @param activationDescription
	 */
	public void setActivationDescription(String activationDescription) {
		this.activationDescription = activationDescription;
	}
	
	/**
	 * Set the user target.
	 * @param userTarget
	 */
	public void setUserTarget(User userTarget) {
		this.userTarget = userTarget;
	}
	
	/**
	 * Set the group target.
	 * @param groupTarget
	 */
	public void setGroupTarget(Group groupTarget) {
		this.groupTarget = groupTarget;
	}

	/**
	 * 
	 * @return owner
	 */
	public User getOwner() {
		return owner;
	}
	
	/**
	 * To get the type of the item
	 * @return item type
	 */
	public ItemType getItemType() {
		return itemType;
	}
	
	/**
	 * To get the initial owner
	 * @return initial owner
	 */
	public User getInitialOwner() {
		return initialOwner;
	}
	
	/**
	 * To get the creation date of the item
	 * @return creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * 
	 * @return activation date
	 */
	public Date getActivationDate() {
		return activationDate;
	}
	
	/**
	 * To get the removal date
	 * @return removal date
	 */
	public Date getRemovalDate() {
		return removalDate;
	}
	
	/**
	 * To get the description of why the item 
	 * has been created. 
	 * @return cause
	 */
	public String getCause() {
		return cause;
	}
	
	/**
	 * Get the description of the activation. 
	 * @return activation description
	 */
	public String getActivationDescription() {
		return activationDescription;
	}

	public User getUserTarget() {
		return userTarget;
	}

	public Group getGroupTarget() {
		return groupTarget;
	}
	
}
