/**
 * 
 */
package onetime;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import neo.enums.SubRef;
import neo.wrappers.IndividualResultNode;
import neo.wrappers.RaceNode;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * @author Miriam Martin
 *
 */
public class EditRaceNode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);
		
		Node raceRefNode = graphDb.getReferenceNode().getSingleRelationship(SubRef.RACE, Direction.OUTGOING).getEndNode();

		
		//getTextForRaceComment(raceRefNode);
		
		//fixTextForRaceAnalysis(raceRefNode);
		
		testRaceComparable(raceRefNode);
	}
	
	private static void testRaceComparable(Node node) {
	
	
		
		Iterable<Relationship> races = node.getRelationships(Direction.OUTGOING, SubRef.RACE);
		
		Iterator<Relationship> iter = races.iterator();
		
		while (iter.hasNext()){
			try {
				RaceNode rn = new RaceNode(iter.next().getEndNode());
				System.out.println("date: " + rn.getRaceDate() + "  " + rn.getDayOfYear() );
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
	

	

	private static void fixTextForRaceAnalysis(Node node) {
		
		Iterable<Relationship> races = node.getRelationships(Direction.OUTGOING, SubRef.RACE);
		
		Iterator<Relationship> iter = races.iterator();
		
		while (iter.hasNext()){
			try {
				RaceNode raceNode = new RaceNode(iter.next().getEndNode());
				
				
				raceNode.fixAnalysis();
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}



	private static void fixTextForRaceComment(Node node) {
		int results = 0;
		Iterable<Relationship> races = node.getRelationships(Direction.OUTGOING, SubRef.RACE);
		
		Iterator<Relationship> iter = races.iterator();
		
		while (iter.hasNext()){
			try {
				RaceNode raceNode = new RaceNode(iter.next().getEndNode());
				Collection<IndividualResultNode> individualResults = raceNode.getRunners();
				
				for (IndividualResultNode ir : individualResults){
					results++;
						//ir.fixComment();
					System.out.println(results + ": " + ir.getComment());
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
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
