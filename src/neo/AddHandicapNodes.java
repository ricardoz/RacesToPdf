/**
 * 
 */
package neo;

import java.util.Iterator;

import neo.enums.SubRef;
import neo.wrappers.racetype.RaceTypeSubreferenceNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseUtil;

/**
 * @author Miriam Martin
 *
 */
public class AddHandicapNodes {
	
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);

		Node referenceNode = graphDb.getReferenceNode();

		GraphDatabaseUtil util = new GraphDatabaseUtil(graphDb);
		
		
		Transaction tx = graphDb.beginTx();

		try {
			
			
			
			// START GET HANDICAP CLASS NODES
			
				RaceTypeSubreferenceNode hcap = RaceTypeSubreferenceNode.getHandicapReferenceNode(util);
			
			// END GET HANDICAP CLASS NODES
			



			Iterable<Node> allNodes = graphDb.getAllNodes();

			Iterator<Node> iter = allNodes.iterator();

			while (iter.hasNext()){
				Node n = iter.next();
			}
				

			tx.success();

		} finally {
			tx.finish();
		}

	}

}
