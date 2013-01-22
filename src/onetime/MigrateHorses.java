package onetime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import neo.enums.SubRef;
import neo.wrappers.HorseNode;
import neo.wrappers.IndividualResultNode;
import neo.wrappers.RaceNode;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class MigrateHorses {
	
	private static enum RelTypes implements RelationshipType
	{
	    RACED_AGAINST
	}
public static void main(String[] args) {
		
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "C:\\Coursera\\Quant\\test117" ) ;
		registerShutdownHook(graphDb);
		
		GraphDatabaseService h =   new EmbeddedGraphDatabase( "C:\\Coursera\\Quant\\horses4" ) ;
		registerShutdownHook(h);
		Index<Node> nodeIndex = h.index().forNodes( "horses" );
		System.out.println("index " + nodeIndex);
		
		Node raceRefNode = graphDb.getReferenceNode().getSingleRelationship(SubRef.RACE, Direction.OUTGOING).getEndNode();

		
		//getTextForRaceComment(raceRefNode);
		
		//fixTextForRaceAnalysis(raceRefNode);
		
		//testRaceComparable(raceRefNode);
		
		// get all nodes
		
		Iterable<Relationship> races = raceRefNode.getRelationships(Direction.OUTGOING, SubRef.RACE);
		
		Iterator<Relationship> iter = races.iterator();
		
		while (iter.hasNext()){
			try {
				RaceNode rn = new RaceNode(iter.next().getEndNode());
				//System.out.println("date: " + rn.getRaceDate() + "  " + rn.getDayOfYear() );
				
				// get all runners
				Collection<IndividualResultNode> runners = rn.getRunners();
				
				Collection<HorseNode> horseList = new ArrayList<>();
				
				Map<HorseNode, Node> nodes = new TreeMap<>();
				
				for (IndividualResultNode irn : runners) {
					HorseNode horse = irn.getHorse();
					horseList.add(horse);
					//System.out.println(horse.getHorseName() + ": " + irn.getPosition() + " " + irn.getDistanceBeatenAsString());
					//System.out.println();
					
				}
				
				for (HorseNode hn : horseList) {
					// check if it is in h
					//System.out.println("trying " + hn.getHorseName());
					String horseName = hn.getHorseName();
					Node n = nodeIndex.get("name", horseName).getSingle();
					
				
					/*System.out.println("trying " + hn.getHorseName());
					System.out.println("trying " + n);
					System.out.println("found " + n.getProperty("name"));*/
					
					if ( n== null) {
						Transaction tx = h.beginTx();
						
						try {
							n = createAndIndexHorse(hn.getHorseName(), h);
							tx.success();
						} finally  {
							tx.finish();
						}
						
					}
					
					if (n == null) System.exit(0);
					nodes.put(hn,n);
					
				}
				
				for (IndividualResultNode irn1: runners) {
					
					HorseNode h1 = irn1.getHorse();
					Node n1 = nodes.get(h1);
					for (IndividualResultNode irn2: runners) {
						//System.out.println("runners " + runners.size());
						HorseNode h2 = irn2.getHorse();
						Node n2 = nodes.get(h2);
						//System.out.println(n1 + "relss " + n2);
						if (n1 != n2) {
							Transaction tx = h.beginTx();
							//System.out.println(n1 + "rels " + n2);
							
							double d2 = Double.parseDouble(irn2.getDistanceBeatenAsString());
							double d1 = (Double.parseDouble(irn1.getDistanceBeatenAsString()));
							
							/*System.out.println("trying1 " + d1);
							System.out.println("trying2 " + d2);*/
							
							double distance = d2 - d1;
							try {
								Relationship r = n1.createRelationshipTo(n2, RelTypes.RACED_AGAINST);
								//System.out.println(n1 + "rel " + n2);
								r.setProperty("distance", distance);
								r.setProperty("info", irn1.getRace().toString());
								tx.success();
							} finally  {
								tx.finish();
							}
						}
					}
				}
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	

private static Node createAndIndexHorse( final String username, final GraphDatabaseService graphDb )
{
    
    
	
		Node node = graphDb.createNode();
		//System.out.println("node " + node);
		Node ref = graphDb.getReferenceNode();
		Index<Node> nodeIndex = graphDb.index().forNodes( "horses" );
		node.setProperty( "name" , username );
		nodeIndex.add( node, "name", username );
		
		return node;
	
	
	
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
