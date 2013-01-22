/**
 * 
 */
package onetime;

import java.lang.invoke.FilterGeneric.F0;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import neo.enums.SubRef;
import neo.wrappers.HorseNode;
import neo.wrappers.IndividualResultNode;
import neo.wrappers.RaceNode;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * This is a makeover class
 * First it finds all the horses
 *
 */
public final class WorkWithAllHorseNodes {

	private final Set<HorseNode> allHorses;

	public WorkWithAllHorseNodes(Set<HorseNode> allHorseNodes){
		this.allHorses = allHorseNodes;
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);

		

		//IndexHits<Node> horses = horseIndex.query("horseId", "753369");

		//Set<Node> set = new HashSet<>();



		

		WorkWithAllHorseNodes allHorseNodes = createWorkWithAllHorseNodes(graphDb);
		
		Set<String> names = new HashSet<>();
		
		allHorseNodes.putAllHorseNamesInAGivenCollection(names);

		
		
		Function<HorseNode, String> function = new Function(){
			public String apply(HorseNode hn){
				return "";
			}
		};
		
		allHorseNodes.createMultiMapFromFunction(function);
		
		
		
		
		
		int falses = 0;


		for (Node n : allHorses){
			/*Iterator<String> v = n.getPropertyKeys().iterator();

			while (v.hasNext()){
				System.out.println(v.next());
			}*/

			if (!addHorseToMap(n, map, names)){
				falses ++;
			}
		}


		
		Node raceRefNode = graphDb.getReferenceNode().getSingleRelationship(SubRef.RACE, Direction.OUTGOING).getEndNode();

		Iterable<Relationship> races = raceRefNode.getRelationships(Direction.OUTGOING, SubRef.RACE);

		Iterator<Relationship> iter = races.iterator();

		while (iter.hasNext()){
			try {
				RaceNode rn = new RaceNode(iter.next().getEndNode());
				//System.out.println("date: " + rn.getRaceDate() + "  " + rn.getDayOfYear() );

				Collection<IndividualResultNode> runners = rn.getRunners();

				for (IndividualResultNode irn : runners){
					try {
						map.get(irn.getHorseName()).addRun(irn);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//System.out.println(irn.getHorseName() );
					}
				}




			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		System.out.println( "horses " + names.size() + " dups " + falses);


	}

	private <K> void createMultiMapFromFunction(Function<HorseNode , K> function) {
		Multimap<K, HorseNode> map = HashMultimap.create();
		
		for(HorseNode hn : allHorses){
			map.put(function.apply(hn), hn);
		}
		
	}

	private void putAllHorseNamesInAGivenCollection(Collection<String> names) {
		for (HorseNode hn : allHorses){
			names.add(hn.getHorseName());
		}	
	}

	private static WorkWithAllHorseNodes createWorkWithAllHorseNodes(
			GraphDatabaseService graphDb) {
		Index<Node> horseIndex = graphDb.index().forNodes("horses");
		
		IndexHits<Node> allHorses = horseIndex.query("horseId", "*");
		
		Set<HorseNode> allHorseNodes = new HashSet<>();
		
		return new WorkWithAllHorseNodes(allHorseNodes);
		


	}

	private static boolean addHorseToMap(Node n, Map<String, HorseNode> map, Set<String> names) {
		if (n.hasProperty("horseyName") && names.add((String) n.getProperty("horseyName"))){


			HorseNode hn = new HorseNode(n);

			map.put(hn.getHorseName(), hn);

			System.out.println(hn.getHorseName() );

			return true;
		} else {
			return false;
		}
	}

}
