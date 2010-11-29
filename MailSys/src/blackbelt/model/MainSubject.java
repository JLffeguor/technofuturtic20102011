package blackbelt.model;

public enum MainSubject {
	ANSWER_IN_FORUM,
	ANSWER_FROM_COACH,
	REMINDER,
	CONGRATULATION;

	private int mailsHavingSameSubject=0;

	public void setMailsHavingSameSubject(int mailsHavingSameSubject){
		this.mailsHavingSameSubject=mailsHavingSameSubject;
	}
	   
	public int getMailsHavingSameSubject() {
	    return mailsHavingSameSubject;
	}

}


 