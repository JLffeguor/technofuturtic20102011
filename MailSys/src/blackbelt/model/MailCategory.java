package blackbelt.model;
/**
 * 
 * @author Alia Faton
 * Each grouped mail has a category, we go through the categories of grouped mails to be send and we build the subject. 
 * Example of a common subject for grouped mails: XXXXXXXXXXXXXXXXXXXXXXXXx 
 */
public enum MailCategory {
    CONTRIBUTION("Contribution(s)"),
    QUESTION("Question update(s)"),
    BELT("Belt"),
    COACH("Coach"),
    COURSE("Course access"),
    INVENTORY("Inventory");
    

    private String text;
    
    private MailCategory(String text){
        this.text=text;
    }
    
    public String getText(){
        return this.text;
    }

}


 