/**
 * 
 */
package neo.wrappers.racetype;

import neo.enums.SubRef;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseUtil;
import org.neo4j.util.NodeWrapperImpl;

/**
 * @author Miriam Martin
 *
 */
public class RaceTypeSubreferenceNode extends NodeWrapperImpl {
	
	public enum raceType implements RelationshipType{HANDICAP,CLAIMER,SELLER,CONDITIONS,MAIDEN,GROUP,STAKES};

	protected RaceTypeSubreferenceNode(Node node) {
		super(node);
		// TODO Auto-generated constructor stub
	}
	
	public HandicapClassSubreferenceNode getHandicapNode(){
		Relationship rel = getUnderlyingNode().getSingleRelationship(raceType.HANDICAP, Direction.OUTGOING);
		Node handicapNode = rel.getEndNode();
		
		return new HandicapClassSubreferenceNode(handicapNode);
		
	}
	
	


	public static RaceTypeSubreferenceNode getHandicapReferenceNode(
			GraphDatabaseUtil util) {
		Node raceTypeSubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.RACE_TYPE);
		return new RaceTypeSubreferenceNode(raceTypeSubreferenceNode);
	}
	
	public static void main(String[] args) {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);

		

		GraphDatabaseUtil util = new GraphDatabaseUtil(graphDb);
		
		
		Transaction tx = graphDb.beginTx();

		try {
			
			
			
			// START GET HANDICAP CLASS NODES
			
			Node raceTypeSubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.RACE_TYPE);
			
			for (raceType rt : raceType.values()){
				if (raceTypeSubreferenceNode.getRelationships(rt).iterator().hasNext() == false) {
					Node newNode = graphDb.createNode();
					newNode.setProperty("raceType", rt.toString());
					raceTypeSubreferenceNode.createRelationshipTo(newNode, rt);
				}
			}
			
			// END GET HANDICAP CLASS NODES
				
				tx.success();
		} finally {
			tx.finish();
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
