/**
 * 
 */
package traversal.race;

import java.util.List;
import java.util.Set;

import neo.wrappers.RaceNode;
import neo.wrappers.racetype.HandicapClassSubreferenceNode;
import neo.wrappers.racetype.RaceTypeSubreferenceNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseUtil;

/**
 * @author Miriam Martin
 *
 */
public class ClassDistance {

	private int theDistance;
	private int theClass;

	public ClassDistance (int theClass, int theDistance){
		this.theClass = theClass;
		this.theDistance = theDistance;
	}
	
	public Set<RaceNode> getRaces(GraphDatabaseService graphDb){
		GraphDatabaseUtil util = new GraphDatabaseUtil(graphDb);
		
		Transaction tx = graphDb.beginTx();

		HandicapClassSubreferenceNode hcapNode = null;
		
		Set<RaceNode> races = null;

		try {
			hcapNode = RaceTypeSubreferenceNode.getHandicapReferenceNode(util).getHandicapNode();
			
			races = hcapNode.getRaces(theClass, theDistance);

			tx.success();
		} finally {
			tx.finish();
		}
		
		return races;
		
	}
	
	public static Set<RaceNode> getRacesAtDistanceAndCourt(GraphDatabaseService graphDb,
			int distance, String courseName){
		GraphDatabaseUtil util = new GraphDatabaseUtil(graphDb);
		
		

		HandicapClassSubreferenceNode hcapNode = null;
		
		Set<RaceNode> races = null;

		
			hcapNode = RaceTypeSubreferenceNode.getHandicapReferenceNode(util).getHandicapNode();
			
			races = hcapNode.getRacesAtDistanceAndCourse(distance, courseName);

		
		
		return races;
		
	}
	
	/**
	 * @param args main
	 */
	public static void main(String[] args) {
		
		
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);
		
		//Index<Node> numbersIndex = graphDb.index().forNodes("numbers");
		ClassDistance cd = new ClassDistance(4, 7);

		Set<RaceNode> races = cd.getRaces(graphDb);
		

		// START GET HANDICAP SUBREFERENCE NODE

		
		
		for (RaceNode rn : races){
			System.out.println(rn);
		}

		

		// END GET HANDICAP SUBREFERENCE NODE

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
