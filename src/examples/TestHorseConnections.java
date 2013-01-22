/**
 * 
 */
package examples;

import java.util.Collection;
import java.util.Iterator;

import neo.wrappers.HorseNode;
import neo.wrappers.IndividualResultNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * @author Miriam Martin
 *
 */
public class TestHorseConnections {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);
		
		findMunsarim(graphDb);
		
		//find all IRN's get Horse
		//allHorseRelsConnected(graphDb);
		allMunsarimRels(graphDb);

	}
	
	private static void allHorseRelsConnected(GraphDatabaseService graphDb) {
		Iterable<Node> nodes = graphDb.getAllNodes();
		Iterator<Node> iter = nodes.iterator();
		int number = 1;
		
		while(iter.hasNext()){
			Node n = iter.next();
			IndividualResultNode irn = null;
			HorseNode h = null;
			
			if (n.hasProperty("comment")){
				irn = new IndividualResultNode(n);
				
				
					h = irn.getHorse();
					
					if (h == null){
						System.out.println(number + "Can't find horse " + irn.getHorseName());
						number++;
					}
				}
			}
		
	}
	
	private static void allMunsarimRels(GraphDatabaseService graphDb) {
		Iterable<Node> nodes = graphDb.getAllNodes();
		Iterator<Node> iter = nodes.iterator();
		int number = 1;
		
		IndividualResultNode first = null;
		
		while(iter.hasNext()){
			Node n = iter.next();
			IndividualResultNode irn = null;
			HorseNode h = null;
			
			if (n.hasProperty("horseName")){
				String s = (String)n.getProperty("horseName");
				if (s.startsWith("Munsarim")){
					irn = new IndividualResultNode(n);
					if (first == null) first = irn;
					h = irn.getHorse();
					System.out.println(s + " " + irn + " " + irn.getRace().compareTo(first.getRace()) );
				}
				
				
				
					
					
				}
			}
		
	}

	private static void findMunsarim(GraphDatabaseService graphDb) {
		Index<Node> index = graphDb.index().forNodes("horses");
		
		IndexHits<Node> indexHits = index.get("horseId", "723216");
		
		Iterator<Node> iter = indexHits.iterator();
		
		int number = 1;
		
		while (iter.hasNext()){
			HorseNode h = new HorseNode(iter.next());
			
			System.out.println(number + ": " + h);
			
			Collection<IndividualResultNode> results = h.getResults();
			
			for (IndividualResultNode irn : results){
				System.out.println(irn.getHorseName());
			}
			
			number++;
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
