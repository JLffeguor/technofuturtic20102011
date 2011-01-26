package blackbelt.model;

public enum MailCategory {
    CONTRIBUTION("Contribution(s)"),
    QUESTION("Question update(s)"),
    BELT("Belt"),
    COACH("Coach"),
    COURSE("Course access"),
    INVENTORY("Inventory");
    //*****Code Forum*****

    private int mailsHavingSameSubject=0;
    private String text;
    
    private MailCategory(String text){
        this.text=text;
    }
    
    public void setMailsHavingSameSubject(int mailsHavingSameSubject){
        this.mailsHavingSameSubject=mailsHavingSameSubject;
    }
       
    public int getMailsHavingSameSubject() {
        return mailsHavingSameSubject;
    }
    public String getText(){
        return this.text;
    }

}


 