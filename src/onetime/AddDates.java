/**
 * 
 */
package onetime;

import org.joda.time.LocalDate;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * @author Miriam Martin
 *
 */
public class AddDates {

	/**
	 * 
	 */
	public AddDates() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "testDates" ) ;
		registerShutdownHook(graphDb);
		
		Transaction tx = graphDb.beginTx();
		
		try {
			Node node = graphDb.createNode();
			node.setProperty("dateObject", new LocalDate());
			
			LocalDate date = (LocalDate)node.getProperty("dateObject");
			
			System.out.println(date);
			
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
