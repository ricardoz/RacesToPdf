/**
 * 
 */
package onetime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import neo.enums.SubRef;
import neo.wrappers.RaceNode;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * @author Miriam Martin
 *
 */
public class EditIndividualResultNode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);
		
		
		// get race subref node
		Node refNode = graphDb.getReferenceNode();
		Iterable<Relationship> rels2 = refNode.getRelationships(SubRef.RACE);
		Iterator<Relationship> iters = rels2.iterator();		
		Node raceRefNode = iters.next().getOtherNode(refNode);
		
		Iterable<Relationship> raceRels = raceRefNode.getRelationships(Direction.OUTGOING, SubRef.RACE);
		Iterator<Relationship> raceIter = raceRels.iterator();
		
		ArrayList<Node> list = new ArrayList<>();
		
		while (raceIter.hasNext()){
			list.add(raceIter.next().getEndNode());
		}
		
		//System.out.println(races.next());
		
		Collection<String> analysis = getAnalysis(list.iterator());
		
		for (String s : analysis){
			System.out.println(s);
		}
		
		
		//	
		

	}
	
	private static Collection<String> getAnalysis(Iterator<Node> iter) {
		
		
		Node n = iter.next();
		
		System.out.println(iter.hasNext());
		
		while (!n.hasProperty("cadge")){
			n = iter.next();
			System.out.println(n.hasProperty("cadge"));
		}
		
		RaceNode r = new RaceNode(n);
		
		List<String> analysis = Arrays.asList(r.getAnalysis().split("\\."));
		
		return analysis;
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
