/**
 * 
 */
package neo;

import java.util.Iterator;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseUtil;

/**
 * @author Miriam Martin
 *
 */
public class AddSubRefernceNodes {

	static enum SubRef implements RelationshipType{HORSE,JOCKEY,COURSE,TRAINER,NUMBER,TEST, RACE};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);

		Node referenceNode = graphDb.getReferenceNode();

		GraphDatabaseUtil util = new GraphDatabaseUtil(graphDb);

		

		Transaction tx = graphDb.beginTx();

		try {
			
			Node horseSubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.HORSE);
			Node courseSubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.COURSE);
			Node trainerSubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.TRAINER);
			Node jockeySubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.JOCKEY);
			Node numberSubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.NUMBER);
			Node testSubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.TEST);
			Node raceSubreferenceNode = util.getOrCreateSubReferenceNode(SubRef.RACE);



			Iterable<Node> allNodes = graphDb.getAllNodes();

			Iterator<Node> iter = allNodes.iterator();

			while (iter.hasNext()){
				Node n = iter.next();

				boolean b = false;

				do {
					/**
					 * test
					 * horse
					 * course
					 * trainer
					 * jockey
					 * number
					 *
					if (n.hasProperty("cadge")) {
						testSubreferenceNode.createRelationshipTo(n, SubRef.TEST);
						b = true;
					}

					if (n.hasProperty("raceId") && !b) {
						raceSubreferenceNode.createRelationshipTo(n, SubRef.RACE);
						b = true;
					}

					if (n.hasProperty("courseId") && !b) {
						b = true;
					}

					if (n.hasProperty("trainerId") && !b) {
						b = true;
					}

					if (n.hasProperty("jockeyId")&& !b){
						b = true;
					}

					if (n.hasProperty("numberValue") && !b) {
						numberSubreferenceNode.createRelationshipTo(n, SubRef.NUMBER);
						b = true;
					}
					
					if (!b){
						Iterable<String> prop = n.getPropertyKeys();
						Iterator<String> iterator = prop.iterator();
						while(iterator.hasNext()){
							String property = iterator.next();
							System.out.println("Could not find node type");
							System.out.println(property+ ": " + n.getProperty(property));
							
						}
						
						System.out.println("\n");
					}


					b = true;


				} while (!b);
			}

			tx.success();

		} finally {
			tx.finish();
		}


*/
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
