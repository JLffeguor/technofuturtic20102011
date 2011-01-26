package blackbelt.model;

public enum MailCategory {
    CONTRIBUTION("Contribution(s)"),
    QUESTION("Question update(s)"),
    BELT("Belt"),
    COACH("Coach"),
    COURSE("Course access"),
    INVENTORY("Inventory");
    //*****Code Forum*****

    private String text;
    
    private MailCategory(String text){
        this.text=text;
    }
    
    public String getText(){
        return this.text;
    }

}


 