package blackbelt.pdf;

public class TestDataGenerator {

	private Section root;
	
	public void generate() {
		Section section1 = new Section(
				"1. Exceptions",
				"</p>Exceptions and Errors are special Java objects families used to describe problems that occurs in applications. They can be split into 3 sub families having a common ancestor (Throwable): Errors, Runtime Exceptions, Checked Exceptions.</p>");
		Section section11 = new Section(
				"1.1. Runtime Exceptions",
				"<p>Runtime Exceptions are problems that happen in your application because you've written bad code. Try this example in Eclipse:</p>[code]    public static void main(String[] args){         BankAccount ba = null;         printAccountDetails(ba);     }      public static void printAccountDetails(BankAccount ba){         System.out.println(ba.getAccountNumber() + \" - \" + ba.getBalance());     }[/code]<p>The above example throws a NullPointerException because the getAccountNumber() method is called using a null reference.</p>");
		Section section12 = new Section(
				"1.2. Checked Exceptions","[image src = \" C:\\Eclipse\\eclipse-jee-galileo-SR2-win32\\workspace\\BlackBeltPdf\\images.gif \"] image test ");
		Section section112 = new Section("1.1.2. Throwing Runtime Exceptions", "If some conditions aren't met in your program you may want to throw an Exception. The syntax is easy, simply use the throw keyword and a new instance of a RuntimeException.See the below example:[code]public class BankAccount {     private String accountNumber;     private double balance;      // Constructors, getters & setters ...      public void debitAccount(double amount) {         if(balance < amount){             throw new RuntimeException(\"Balance too low to execute debit. \" + accountNumber);         }         balance -= amount;     } }[/code]");
		Section section2 = new Section(
				"2. Classloading",
				"The JVM includes a classloading mechanism that is necessary "
						+ "to understand when weird deployment runtime problems happen.\n");
		Section section21 = new Section(
				"2.1. Files",
				"As you know, source code is stored in .java files. The compiler "
						+ "produces .class files with bytecode.In the following diagram, you see these elements, with the ClassLoader "
						+ "responsible of finding/loading the .class files at runtime.\n");
		Section section111 = new Section(
				"1.1.1. Exercice",
				"<p>Execute the following steps</p></br><ul><p>    </p><li>Run a program that throws a NullPointerException (as on the example above).</li><p>    </p><li>Build a new class that iterates through an array but goes too far</li><p>    </p><li>Check the Java SE JavaDoc, locate Throwable, Exception and browse through the subclasses of RuntimeException. Note that the notion of subclass will be seen in a later chapter.</li><p></p></ul>");

		root = section1;
		
		section1.getSubSections().add(section11);
		section1.getSubSections().add(section12);
		section11.getSubSections().add(section112);
		section11.getSubSections().add(section111);// create a sub-subSection
		section2.getSubSections().add(section21);
	}

	public Section getRoot() {
		return root;
	}
	
	

}
