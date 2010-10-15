package ctfTesting;


public class Main {

    public static void main(String[] args){
        
//        BlackBeltTagHandler blackBeltTagHandler = null;
        BlackBeltTagHandlerQuestions test = new BlackBeltTagHandlerQuestions("123-a");
        BlackBeltTagParser blackBeltParse = new BlackBeltTagParser(test, " Voici un code [code] \n{         BankAccount ba = null;         printAccountDetails(ba);     }\n  " +
        		"public static void printAccountDetails(BankAccount ba){        " +
        		" System.out.println(ba.getAccountNumber()  \" - \" + ba.getBalance());     }[/code]");
    
        blackBeltParse.parse(); 
      
        
    }
}