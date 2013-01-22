/**
 * 
 */
package neo;

import neo.wrappers.RaceNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * @author Miriam Martin
 *
 */
public class AddNumbers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);
		Index<Node> numbersIndex = graphDb.index().forNodes("numbers");
		
		
		Transaction tx = graphDb.beginTx();
		try {
			/**
			 * Create new RaceNode
			 */
			
			for(int i = 0; i < 200; i++){
				Node numberNode = graphDb.createNode();
				numberNode.setProperty("numberValue", i);
				numbersIndex.add(numberNode, "numberValue", i);
			}

			tx.success();
		} finally {
			tx.finish();
		}
		
		for(int i = 0; i < 200; i++){
			
			
			IndexHits<Node> n = numbersIndex.get("numberValue", i);
			int value = (int)n.getSingle().getProperty("numberValue");
			System.out.println(i + ": " + (value == i));
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
