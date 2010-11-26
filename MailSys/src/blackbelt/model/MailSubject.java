package blackbelt.model;

public enum MailSubject {
	SUBJECT_1,
	SUBJECT_2,
	SUBJECT_3,
	SUBJECT_4,
	CONGRATULATION	;
	
	
	private int mailsOfSameSubject;

	public int getMailsOfSameSubject() {
		return mailsOfSameSubject;
	}

	public void setMailsOfSameSubject(int mailsOfSameSubject) {
		this.mailsOfSameSubject = mailsOfSameSubject;
	}
}
