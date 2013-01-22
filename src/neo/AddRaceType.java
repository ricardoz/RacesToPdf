/**
 * 
 */
package neo;

import neo.enums.RaceClass;

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
public class AddRaceType {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook( graphDb );
		
		Index<Node> raceTypeIndex = graphDb.index().forNodes("raceType");
		
		RaceClass[] raceTypes = RaceClass.values();
		
		for (int i = 0; i < raceTypes.length; i++) {
			// add to graph and index
			Transaction tx = graphDb.beginTx();
			
			try {
				
				Node n = graphDb.createNode();
				raceTypeIndex.add(n, "raceType", raceTypes[i]);
				
				System.out.println("added: " + raceTypes[i]);
				
				
				/*
				IndexHits<Node> indexHits = raceTypeIndex.get("raceType", raceTypes[0]);
				
				for (Node n : indexHits){
					System.out.println("removing: " + n);
					n.delete();
					raceTypeIndex.remove(n);
				}
				*/
				

				tx.success();
			} catch (Exception e){
				System.out.println("failed: " + raceTypes[i] );
				e.printStackTrace();
			} finally {
				tx.finish();
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
