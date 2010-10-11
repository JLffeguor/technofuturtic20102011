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
				"1.2. Checked Exceptions",
				"[image src = \" C:\\Eclipse\\eclipse-jee-galileo-SR2-win32\\workspace\\BlackBeltPdf\\images.gif \"]");
		Section section112 = new Section(
				"1.1.2. Throwing Runtime Exceptions",
				"[code]# void Calcul(int (*t)[9],int (*f))# {# int i=0,j=1,k=0,p=0;# debut:# {# if (k==92)# {# return; //Il n'y a que 92 possibilités (je fais confiance a la litterature et le programme ne va pas plus loin et il faut bien sortir de la boucle)# }# if (j>=9) //Si on sort de l echiquier# {# k=k+1; //Compteur de solution# printf(\"Solution num %d \n\",k);# Affichage (t); //On affiche la solution# j=8; //On reviens a la derniere ligne# i=f[8]; //On reviens a la derniere reine posée# t[i][j]=0; //On efface la reine# p=-1; //On passe le parametre qui efface la reine# Reine(t,i,j,p); //On efface les impossibilités de la reine# i=f[8]+1; //On passe a la position suivante# goto rech; //On va rechercher si la pose d'une reine est possible ( c est sur que non mais bon! )## } [/code]If some conditions aren't met in your program you may want to throw an Exception.[video id  = \"http://www.kikibese.fr\" type = \"youtube\"] The syntax is easy, simply use the throw keyword and a new instance of a RuntimeException.See the below example:[code]public class BankAccount {     private String accountNumber;     private double balance;      // Constructors, getters & setters ...      public void debitAccount(double amount) {         if(balance < amount){             throw new RuntimeException(\"Balance too low to execute debit. \" + accountNumber);         }         balance -= amount;     } }[/code]");
		Section section2 = new Section(
				"2. Classloading",
				"The JVM includes a classloading mechanism that is necessary "
						+ "to understand when weird deployment runtime problems happen.\n");
		Section section21 = new Section(
				"2.1. Files",
				"As you know, source code is stored in .java files. The compiler "
						+ "produces .class files with bytecode.In the following diagram, you see these elements, with the ClassLoader "
						+ "responsible of finding/loading the .class files at runtime.\n");
		Section section113 = new Section(
				"1.1.3. Nos amis les Hamster",
				"Voilà, enfin disponible pour tous, une rubrique qui va certainement faire fureur et avoir des répercussions "
						+ "relativement conséquentes, une rubrique vraiment super qui va amuser les petits comme les tout petits,"
						+ " une rubrique hors du commun qui va transporter dans un univers féérique les hommes les plus terre-à-terre"
						+ ", LA rubrique des photos de famille. Il s'agit des photos prises sur le vif des trois hamsters que nous avons"
						+ " eu avec ma soeur, durant notre jeunesse, à savoir Friskette, Albert et Jimy. Ils reposent actuellement "
						+ "quelque part dans le jardin de notre grand-maman et je suis tout ému d'enfin pourvoir leur adresser une"
						+ " rubrique sur THE net qui fera passer leur mémoire à la postérité... mais trève de bavardage, voilà "
						+ "LA rubrique \n");
		Section section1131 = new Section(
				"1.1.3.1. Et voila ^^",
				"Et ici les toutes dernières nouveautés...:  \"Un hamster qui rote ressemble"
						+ " à s'y méprendre à un hamster qui pête... question de référentiel...\"Les hamsters sont polygames,"
						+ " c'est-à-dire que les mâles et les femelles n'ont pas de partenaire précis. À la saison des amour"
						+ "s les mâles hamsters vont d'un terrier à l'autre à la recherche de femelles réceptives. Un opercule"
						+ " empêche la fécondation des œufs par les mâles suivants et la femelle chasse alors le plus souvent "
						+ "les prétendants de son territoire. La saison de reproduction se situe entre février et novembre."
						+ " Les femelles auront deux à trois portées par an après une courte gestation  de 15 à 22 jours."
						+ " Le nombre de petits par portée est très variable, pouvant aller jusqu'à 13, avec une moyenne de 5 à 7 petits."
						+ " Les petits sont allaités 3 semaines environ et deviennent adultes à 6 ou 8 semaines[2]."
						+ "Le record de longévité connu pour un hamster sauvage est de 10 ans, mais la plupart des hamsters,"
						+ " sauvages ou en captivité, ne dépassent pas 2 ou 3 ans. Dans la nature leurs principaux ennemis "
						+ "sont les prédateurs : rapaces, serpents, mammifères carnivores et même des hérons ou des corbeaux "
						+ "qui capturent les plus jeunes. Ils craignent également les hivers trop froids, les maladies et"
						+ " les machines agricoles qui détruisent leur terrier.Ils creusent en effet des terriers "
						+ "complexes à entrées multiples, avec tout un jeu de chambres, de greniers et de latrines "
						+ "reliés par un réseau de tunnels qui peuvent plonger à plus de 2m sous la surface du sol"
						+ " en hiver. Le terrier s'agrandit au cours de la vie de l'animal, qui y vit en solitaire."
						+ " Le hamster en sortira généralement au crépuscule ou à la nuit tombée, bien que certaines"
						+ " espèces soient également diurnes.");
				Section section1132 = new Section(
						"1.1.3.1.","Hamster de Roborovski à l'entrée de son nid.Certaines"
						+ " espèces sont particulièrement agressives vis-à-vis de leurs congénères, et des règles "
						+ "hiérarchiques strictes règlent ces rencontres. Les femelles sont souvent dominantes."
						+ " Les cricétinés se défendent âprement avec leurs incisives quand ils sont attaqués."
						+ " Ils attaquent aussi quand ils se font capturer malgré leur fourrure propice au camouflage."
						+ " Celle-ci est généralement dans des tons gris, noir, brun et roux, avec souvent des flancs plus clairs "
						+ "ou une rayure dorsale[2].Les hamsters bénéficient d'une bonne vue pour trouver leurs proies,"
						+ " mais leur ouïe et leur odorat sont également bien développés."
						+ " Pour communiquer entre eux les mâles surtout utilisent un marquage olfactif du territoire."
						+ " Plus l'animal est dominant, plus ses glandes sébacées seront développéesLes hamsters consomment"
						+ " des plantes et des graines, et ils sont à leur tour une source de nourriture pour de nombreux"
						+ " animaux carnivores[2].Leur habitude d'emporter les graines dans leur terrier sous terre joue "
						+ "certainement un rôle dans la dispersion des semences[4].Pour les humains les hamsters sont"
						+ " souvent considérés comme nuisibles lorsqu'ils ravagent les cultures de haricots, de maïs "
						+ "ou de lentilles. On les chasse aussi parfois pour leur fourrure. Certaines espèces de hamsters,"
						+ " élevées en captivité, sont appréciées comme animal de compagnie ou dans les laboratoires pour"
						+ " les recherches comportementales ou physiologiques[2].Dans les pays où l'équilibre écologique"
						+ " est fragile, comme par exemple dans l'État du Queensland en Australie, les hamsters sont interdits,"
						+ " même en tant qu'animaux de compagnie[5] afin de préserver la faune et la végétation locale."
						+ "Pourchassés et piégés par les agriculteurs, parfois jusqu'à l'éradication totale sur certains "
						+ "territoires, certaines populations de hamsters bénéficient à l'inverse d'un statut de protection "
						+ "juridique. C'est le cas par exemple du hamster d'Europe en Alsace[6] ou du Hamster doré en Syrie[7].");
		Section section111 = new Section(
				"1.1.1. Exercice",
				"<p>Execute the following steps</p></br><ul><p>    </p><li>Run a program that throws a NullPointerException (as on the example above).</li><p>    </p><li>Build a new class that iterates through an array but goes too far</li><p>    </p><li>Check the Java SE JavaDoc, locate Throwable, Exception and browse through the subclasses of RuntimeException. Note that the notion of subclass will be seen in a later chapter.</li><p></p></ul>");

		root = section1;

		section1.getSubSections().add(section11);
		section1.getSubSections().add(section12);
		section11.getSubSections().add(section111);
		section11.getSubSections().add(section112);
		// create a sub-subSection
		section2.getSubSections().add(section21);
		section11.getSubSections().add(section113);
		section113.getSubSections().add(section1131);
		section113.getSubSections().add(section1132);
	}

	public Section getRoot() {
		return root;
	}

}
