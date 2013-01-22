/**
 * 
 */
package newraceday;

import horse.BasicHorse;
import horse.BasicHorseId;
import horse.BasicHorseName;
import horse.Horse;
import horse.HorseId;
import horse.HorseName;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.joda.time.LocalDate;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import race.BasicRaceId;
import race.Race;
import race.RaceId;
import race.RaceTime;
import racemeeting.RaceMeeting;
import scala.util.control.Exception.Finally;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import course.Course;

/**
 * @author Miriam Martin
 *
 */
public class FromRunnersIndex {

	private HtmlPage runnersIndexPage;
	private GraphDatabaseService oldGraphDb;
	private GraphDatabaseService currentGraphDb;
	private LocalDate date;
	private Map<Integer,Course> courseMap;
	private Map<Course,RaceMeeting> raceMeetingMap;
	
	public enum FooRels implements RelationshipType{DATE, COURSE}

	/**
	 * @param page 
	 * @param graphDb2012 
	 * @param oldGraphDb 
	 * @param string 
	 * 
	 */
	public FromRunnersIndex(HtmlPage page, GraphDatabaseService oldGraphDb, GraphDatabaseService graphDb2012, String date) {
		this.runnersIndexPage = page;
		this.oldGraphDb = oldGraphDb;
		this.currentGraphDb = graphDb2012;
		this.date = new LocalDate(date);
		
		courseMap = new HashMap<>();
		raceMeetingMap = new HashMap<>();
		
		//check for date
		Node currentDate = checkForDate();
		
		System.out.println(currentDate.getProperty("date"));
		
		
		
		
		printTableData();
	}
	

	private Node createNewDate(Index<Node> dateIndex) {
		Transaction tx = currentGraphDb.beginTx();
		Node currentDate = null;
		
		try {
			//create node
			currentDate = currentGraphDb.createNode();
			currentDate.setProperty("date", date.toString());
			
			//add link to subreference node
			Node subreferenceNode = getSubreferenceNode(FooRels.DATE);
			subreferenceNode.createRelationshipTo(currentDate, FooRels.DATE);
			
			//index node
			dateIndex.add(currentDate, "local date", date);
			tx.success();
		} finally {
			tx.finish();
		}
		return currentDate;
	}


	private Node checkForDate() {
		Index<Node> dateIndex = currentGraphDb.index().forNodes("dates");
		
		Node currentDate = dateIndex.get("local date", date).getSingle();
		
		if (currentDate == null){
			currentDate = createNewDate(dateIndex);
		}
		
		return currentDate;
		
	}
	
	private Node getSubreferenceNode(FooRels foo){
		Node referenceNode = currentGraphDb.getReferenceNode();
		
		Iterable<Relationship> subreferenceNodesCollection = referenceNode.getRelationships(Direction.OUTGOING, foo);
		
		Iterator<Relationship> iter = subreferenceNodesCollection.iterator();
		
		if (!iter.hasNext()){
			
			Transaction tx = currentGraphDb.beginTx();
			
			Node newNode = currentGraphDb.createNode();
			
			try{
				referenceNode.createRelationshipTo(newNode, foo);
				tx.success();
			} finally {
				tx.finish();
			}
			
			return newNode;
			
		} else {
			return iter.next().getEndNode();
		}
	}


	private void printTableData() {
		DomNodeList<DomNode> table = runnersIndexPage.querySelectorAll("tr");
		
		
		
		for (DomNode dm : table){
			//System.out.println(date);
			String dmXml = dm.asXml();
			
			
			
			// find horseId
			int firstIndex = dmXml.indexOf("horse_id=", 88) + 9;
			
			if (firstIndex > 8) {
				
				addHorse(dmXml, firstIndex);
				
				
				
				
			}
			
			
			
			
			
		}
		
	}


	private void addHorse(String dmXml, int firstIndex) {
		int lastIndex = dmXml.indexOf("\"", firstIndex);
		String horseIdAsString = dmXml.substring(firstIndex, lastIndex);
		System.out.println(firstIndex + "  " + lastIndex + "   -" + horseIdAsString + "-");
		HorseId horseId = new BasicHorseId(horseIdAsString);
		
		//last index = the double quote after horseId
		//change first index to next > + 1
		//change last index to next <
		HorseName horseName;
		
		firstIndex = dmXml.indexOf(">", lastIndex) + 1;
		lastIndex = dmXml.indexOf("<", firstIndex);
		horseName = new BasicHorseName(dmXml.substring(firstIndex, lastIndex).trim());
		
		/*System.out.println(firstIndex + "  " + lastIndex + "   "
				+ horseIdAsString + "  -" + horseName + "-");*/
		
		// find course Id
		// change first index to crs_id= + 7
		// change last index to next \"
		String courseIdAsString;
		
		firstIndex = dmXml.indexOf("crs_id=", lastIndex) + 7;
		lastIndex = dmXml.indexOf("\"", firstIndex);
		courseIdAsString = dmXml.substring(firstIndex, lastIndex); 
		
		/*System.out.println(firstIndex + "  " + lastIndex + "   "
				+ horseIdAsString + "  -" + horseName + "-" + " " + courseIdAsString);*/
		
		// find race Id
		// change first index to race_id= + 8
		// change last index to &
		
		int raceIdFirstIndex = dmXml.indexOf("race_id=", lastIndex) + 8;
		int raceIdLastIndex = dmXml.indexOf("&", raceIdFirstIndex);
		String raceIdAsString = dmXml.substring(raceIdFirstIndex, raceIdLastIndex);
		
		RaceId raceId = new BasicRaceId(raceIdAsString);
		
		// find race Time
		// firstIndex is next > + 1
		// lastIndex is next <
		RaceTime raceTime;
		
		int raceTimeFirstIndex = dmXml.indexOf(">", raceIdLastIndex) + 1;
		int raceTimeLastIndex = dmXml.indexOf("<", raceTimeFirstIndex);
		raceTime = new BasicRaceTime(dmXml.substring(raceTimeFirstIndex, raceTimeLastIndex).trim());
		
		/**
		 * Race Meeting Map: Course -> Race Meeting
		 * Give Race Meeting Time -> Race
		 * Add Horse to Race 
		 */
		
		Course course = getCourseFromCourseMap(courseIdAsString);
		RaceMeeting rm = getRaceMeetingFromRaceMeetingMap(course);
		Race race = rm.getRace(raceId);
		
		if (race == null){
			race = new BasicRace(raceId, raceTime);
			rm.addRace(race);
		}
		
		Horse horse = new BasicHorse(horseId, horseName);
		race.addHorse(horse);
		
		firstIndex = dmXml.indexOf("horse_id=", raceTimeLastIndex) + 9;
		
		if (firstIndex > raceTimeLastIndex){
			addHorse(dmXml,firstIndex);
		}
	}


	private RaceMeeting getRaceMeetingFromRaceMeetingMap(Course course) {
		RaceMeeting rm = raceMeetingMap.get(course);
		
		if (rm == null){
			rm = new BasicRaceMeeting ( course , date );
			raceMeetingMap.put(course, rm);
		}
		
		
		return rm;
	}


	private Course getCourseFromCourseMap(String courseIdAsString) {
		Integer courseId = Integer.parseInt(courseIdAsString);
		Course course = courseMap.get(courseId);
		
		if (course == null){
			// create course
			course = new Course(courseId);
			courseMap.put(courseId, course);
		}
		
		return course;
	}


	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setJavaScriptEnabled(true);
		webClient.setJavaScriptErrorListener(null);
		webClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("page " + page + "\nurl " + url + " " + arg);
			}

		});
		
		GraphDatabaseService oldGraphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		GraphDatabaseService graphDb2012 =   new EmbeddedGraphDatabase( "2012/races2012" ) ;
		registerShutdownHook(oldGraphDb);
		registerShutdownHook(graphDb2012);
		
		HtmlPage page = webClient.getPage("http://www.racingpost.com/horses2/cards/home.sd?r_date=2012-04-10&cardHomeTabs=runner_index");
		
		LocalDate date = LocalDate.now();
		date = date.plusDays(1);
		
		
		FromRunnersIndex fri = new FromRunnersIndex(page, oldGraphDb, graphDb2012, date.toString());
		fri.showData();
		
		/**
		 * Search index for course node
		 */

	}
	
	private void showData() {
		for (Course c : courseMap.values()){
			System.out.println(c + "\n");
			
			RaceMeeting rm = raceMeetingMap.get(c);
			Collection<Race> races = rm.getRaces();
			
			for (Race r : races){
				System.out.println(r + "\n");
				
				Collection<Horse> horses = r.getHorses();
				for (Horse h : horses){
					System.out.println(h);
				}
			}
		}
		
	}
	
	private boolean addToDatabase(){
		for (Course c : courseMap.values()){
			System.out.println(c + "\n");
			
			RaceMeeting rm = raceMeetingMap.get(c);
			Collection<Race> races = rm.getRaces();
			
			for (Race r : races){
				System.out.println(r + "\n");
				
				Collection<Horse> horses = r.getHorses();
				for (Horse h : horses){
					System.out.println(h);
				}
			}
		}
		
		return false;
		
	}


	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running example before it's completed)
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			@Override
			public void run()
			{
				graphDb.shutdown();
			}
		} );

	}

}
