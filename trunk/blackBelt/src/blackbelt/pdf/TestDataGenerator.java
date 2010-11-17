package blackbelt.pdf;

public class TestDataGenerator {

	private Section root1;
	private Section root2;

	public void generate() {
		/** Create Section for the database*/
		Section section6 = new Section("6. Wow - Speed Test", "This case is quite easy, except that it's designed as a test to be programmed as fast as possible. From the moment you read the statement below, you should measure the amount of time you need to make the program work.", null);
		
		Section section61 = new Section("6.1 Preparation", "Have Eclipse and your DB ready, with all the needed jar files and jdbc driver. Have a persistence.xml ready. If you use Spring, prepare your applicationContext.xml file. <br/>"
										+ "For example, you may import and install <a href=\"http://www.javablackbelt.com/ui#coursePage/uuid=495af82d-cb5c-4e62-bcdd-5b352b636923\">this JPA project and DB</a>. "
										+ "When you are ready, start your timer and read the statement below.", section6);
		
		Section section62 = new Section("6.2 Domain Model", "In the Wow game  (<i>World of Warcraft</i>), players group and kill bosses in dungeons. <br/>"
										+ "A dungeon is a place with rooms and monsters. Imagine it's a building. Once you enter the building, you start killing monsters, for example the receptionist. <br/>"
										+ "If another group of player decides to enter in the same dungeon (the building) they will see their own \"instance\" of the dungeon, where the receptionist is still alive. Each group entering in a dungeon has its own DungeonInstance. <br/>"
										+ "A Dungeon is like a map defining the rooms of the building and the kind of monsters out there.<br/> "
										+ "A DungeonInstance is like a game memory remembering which monster is alive or dead for the playing group. But that notion of alive/dead monster is out of the scope of this project. <br/>"
										+ "Even the notion of monster is not modelized here, except bosses. A boss is a big monster wearing stuff. Once the boss is killed, players may steal stuff from the dead boss. Some bosses wear epic stuff, and this is modelized in our domain model. "
										+ "[image src=\"S:\\DocumentsPourPDF\\images\\wowClassDiagram.jpg\"/]", section6);
		
		Section section63 = new Section("6.3 Data", "In this project, we modelize 2 dungeons: "
										+ "<ul> <li><a href=\"http://www.wowwiki.com/The_Nexus_(instance)\">Nexus</a></li> <li><a href=\"http://www.wowwiki.com/Gundrak\">Gundrak</a></li></ul> "
										+ "This is how the Nexus dungeon looks like from the outside: [image src=\"S:\\DocumentsPourPDF\\images\\The_Nexus.JPG\"/] <br/>"
										+ "Each dungeon has a few bosses, some with epic stuff: "
										+ "<ul> <li>Nexus<ul> <li>Grand Magus Telestra (epic stuff)</li> <li>Anomalus</li> <li>Keristrasza (epic stuff)</li> </ul> </li> <li>Gundrak <ul> <li>Noorabi</li> <li>Sla'dran</li> "
										+ "</ul></li></ul> <br /> "
										+ "A first group of 4 player gathers. Let's take alphabet letters as names to simplify.<ul>    <li>A</li>    <li>B</li>    <li>C</li>    <li>D</li></ul> "
										+ "That group decides to enter into the Nexus dungeon. The system creates a DungeonInstance. <br />"
										+ "[code escape=\"true\"]Nexus:Dungeon <- :DungeonInstance <-> :Group <- A, B, C, D [/code] <br/>"
										+ "Another group of players (E, F and G) decides to play the same dungeon. Another DungeonInstance is created in the DB, also bound to Nexus. <br />"
										+ "A third group of players (H, I, J, K and L) decides to play together, but in the other Dugeon: <i>Gundrak</i>. A corresponding DungeonInstance is created. <br/>"
										+ "[image src=\"S:\\DocumentsPourPDF\\images\\wowObjectDiagram.jpg\"/]", section6);		
		
		Section section64 = new Section("6.4 Queries", "Let's start with the more easy queries: <ul>    <li><i>Select all the dungeons having a boss named \"Noorabi\".</i></li>	    <li><i>Select all the bosses of the Nexus dungeon.</i></li>	</ul> "
										+ "The third query is harder: <ul>  <li><i>Which players are in a dungeonInstance of a dungeon having at least a boss wearing epic stuff?</i></li>	</ul> "
										+ "In our DB, the two bosses wearing epic stuff are Grand Magus Telestra "
										+ "[image src=\"S:\\DocumentsPourPDF\\images\\174px-Grand_Magus_Telestra.jpg\"/] "
										+ "and Keristrasza. [image src=\"S:\\DocumentsPourPDF\\images\\200px-Keristrasza.jpg\"/])", section6);
		
		Section section65 = new Section("6.5 Optional Query", "Stop your timer now, before doing this last optional part: you are finished ;-)<br/> "
										+ "<b>This last query is not part of the test</b> <br />"
										+ "The optional query is: <ul>    <li><i>What is the name of the bosses that player A may meet in his current dungeon?</i></li></ul>", section6);
		
		Section section66 = new Section("6.6 How Long ?", "We have run this test in a controlled classroom with 11 average junior programmers. They had 3 months of Java practice. They completed the <i>JPA Fundamentals</i> course and workshop one month before the test and did not practice JPA much during the month preceding the test. 2 weeks before this wow speed test, they have done the previous test (Car Show). The 3 fastest students (according to the previous test) did not particpate. <br />" 
										+ "The result was: <br/> "
										+ "1st: 1h30 <br /> "
										+ "2nd: 2h30 <br /> "
										+ "others: too long (not finished). After 3h30, 4 students did not reach the queries. The 3 others were programming the queries. <br/> "
										+ "A gifted programmer having experience with JPA should take less than 1h, but certainly more than 20 minutes (because the code to create the data is long, and you need time to understand the statement).", section6);
		
		Section section67 = new Section("6.7 Solution", "Dans cette vidéo, Johan, Julien et Pierre et  présentent une solution: [video id=\"16687877\" type=\"Vimeo\"/]", section6);
		
		Section section7 = new Section("7. Hello world", "Bonjour, je m'appelle Alfred et moi aussi je suis alcoolique", null);

	
		/** Add the section to their childs */
		section6.getSubSections().add(section61);
		section6.getSubSections().add(section62);
		section6.getSubSections().add(section63);
		section6.getSubSections().add(section64);
		section6.getSubSections().add(section65);
		section6.getSubSections().add(section66);
		section6.getSubSections().add(section67);
		
		/** Return the section 6 when getRoot is call*/
		root1 = section6;
		root2 = section7;
	}

	public Section getRoot() {
		return this.root1;
	}
	
	public Section getRootDeux(){
		return this.root2;
	}
}
