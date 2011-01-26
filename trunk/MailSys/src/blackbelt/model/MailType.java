package blackbelt.model;

/**
 * There are three kind of mails :
 * IMMEDIATE : the mails are send immediatly
 * GROUPABLE : some mails can be grouped. The user define if he want to recieve mails every day or week
 * SLOW_NOT_GROUPABLE : It's the mail who are not important but they can enter in the GROUPABLE category like the newsletters
 * */
public enum MailType {
	IMMEDIATE,
	GROUPABLE,
	SLOW_NOT_GROUPABLE;
}
