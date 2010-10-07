package generatorpdf;

public class Main {


	public static void main(String[] args) {
		HtmlToPdf.convertPdf("<p>1. Exceptions</p>Exceptions and Errors are special Java objects families used to describe problems that occurs in applications. They can be split into 3 sub families having a common ancestor (Throwable): Errors, Runtime Exceptions, Checked Exceptions.</p></p>1.1. Runtime Exceptions<p>Runtime Exceptions are problems that happen in your application because you've written bad code. Try this example in Eclipse:</p><pre class='contentProgramListing' xml:space='preserve'>    public static void main(String[] args){         BankAccount ba = null;         printAccountDetails(ba);     }      public static void printAccountDetails(BankAccount ba){         System.out.println(ba.getAccountNumber() + \" - \" + ba.getBalance());     }</pre><p>The above example throws a NullPointerException because the getAccountNumber() method is called using a null reference.</p>1.1.2. Throwing Runtime ExceptionsIf some conditions aren't met in your program you may want to throw an Exception. The syntax is easy, simply use the throw keyword and a new instance of a RuntimeException.See the below example:<pre class='contentProgramListing' xml:space='preserve'>public class BankAccount {     private String accountNumber;     private double balance;      // Constructors, getters & setters ...      public void debitAccount(double amount) {         if(balance < amount){             throw new RuntimeException(\"Balance too low to execute debit. \" + accountNumber);         }         balance -= amount;     } }</pre><p>1.1.1 Exercice<p>Execute the following steps</p></br><ul><p>    </p><li>Run a program that throws a NullPointerException (as on the example above).</li><p>    </p><li>Build a new class that iterates through an array but goes too far</li><p>    </p><li>Check the Java SE JavaDoc, locate Throwable, Exception and browse through the subclasses of RuntimeException. Note that the notion of subclass will be seen in a later chapter.</li><p></p></ul></p><p>1.2. Checked Exceptions</p>");

	}

}
