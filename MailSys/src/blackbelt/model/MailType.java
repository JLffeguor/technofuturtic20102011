package blackbelt.model;

/**
 * There are three kinds of mails :
 *      IMMEDIATE : mails are send immediately.
 *      GROUPABLE : mails can be grouped. The user defines if he wants to receive them immediately, daily or weekly.
 *      SLOW_NOT_GROUPABLE : mails are less important for exemple newsletter.
 * 
 */
public enum MailType {
	IMMEDIATE,
	GROUPABLE,
	SLOW_NOT_GROUPABLE;
}
