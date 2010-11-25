package javablackbelt.inventory.model;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An Item is in the inventory of a user and can be activated.
 */
@Entity
@Table(name="ITEM_")
public class Item {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User owner;
	
	private ItemType itemType;
	
	/** User who dropped that item initially. If initialOwner <> owner,
	that item has been given by initialOwner to somebody else, then to owner */
	@ManyToOne
	@JoinColumn(name="USER_ID_INIT_OWNER")
	private User initialOwner;
	
	/** when that item has been dropped */
	private Date creationDate;
	
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
	private String activationDescription;
	
	/** User on which the item has been activated.
	Either group or user, if any. e.g.: a beer is offered to somebody */
	@ManyToOne
	@JoinColumn(name="USER_ID_TARGET")
	User userTarget;
	
	@ManyToOne
	@JoinColumn(name="TARGET_GROUP_ID")
	Group groupTarget;
	
	public Item(User owner, ItemType itemType) {
		this.owner = this.initialOwner = owner;
		this.itemType = itemType;
		this.creationDate = new Date();
	}
	
	public Item(){}
	
	//////////////////////////////////////////////////////////////////
	///////////////////// Generated Code /////////////////////////////
	//////////////////////////////////////////////////////////////////
	
	/**
	 * When this method is called the current date
	 * is set into activation date.
	 * This method set also the removal date.
	 */
	public void setActivationDate() {
		if(this.removalDate != null) {
			throw new RuntimeException("This item has already been activated");
		}
		this.activationDate = new Date();  
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(GregorianCalendar.HOUR_OF_DAY, this.itemType.getItemDuration());
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
	
	public void setUserTarget(User userTarget) {
		this.userTarget = userTarget;
	}

	public void setGroupTarget(Group groupTarget) {
		this.groupTarget = groupTarget;
	}

	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public ItemType getItemType() {
		return itemType;
	}
	
	public User getInitialOwner() {
		return initialOwner;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public Date getActivationDate() {
		return activationDate;
	}
	
	public Date getRemovalDate() {
		return removalDate;
	}
	
	public String getCause() {
		return cause;
	}
	
	public String getActivationDescription() {
		return activationDescription;
	}
	
	public User getUserTarget() {
		return userTarget;
	}
	
	public Group getGroupTarget() {
		return groupTarget;
	}

	public Long getId() {
		return id;
	}
	public String toString() {
		return ("Item : "+id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}