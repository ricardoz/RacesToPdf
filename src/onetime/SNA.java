package onetime;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import neo.enums.SubRef;
import neo.wrappers.HorseNode;
import neo.wrappers.IndividualResultNode;
import neo.wrappers.RaceNode;

//import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import com.google.common.collect.Lists;

public class SNA {
	GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "C:\\Coursera\\Python\\test117" ) ;
	GraphDatabaseService output =   new EmbeddedGraphDatabase( "C:\\Coursera\\Python\\_aRaces1" ) ;
	private Index<Node> outputIndex;
	boolean broken = false;
	int attempted;
	
	enum MyRelationshipTypes implements RelationshipType
	 {
	     WINNER_RACED_IN
	 }
	
	
	public SNA() {
		// register hooks
		registerShutdownHook(graphDb);
		registerShutdownHook(output);
		outputIndex = output.index().forNodes("raceId");
		graphDb.getReferenceNode();
	}
	
	/*public void findIndividualResultNode() {
		if (broken) System.out.println("reenter");
		broken = false;
		Node raceRefNode = graphDb.getReferenceNode().getSingleRelationship(SubRef.RACE, Direction.OUTGOING).getEndNode();
		
		Iterable<Relationship> races = raceRefNode.getRelationships(Direction.OUTGOING, SubRef.RACE);
		
		Iterator<Relationship> iter = races.iterator();
		
		iter.next();
		
		int i = 0;
		
		search:
		while (iter.hasNext()) {
			Node first = iter.next().getEndNode();
			
			while (first == null) first = iter.next().getEndNode();
			RaceNode rNode = new RaceNode(first);
			
			while (rNode.getRunners().size() < 1) {
				if (iter.hasNext()) {
					rNode = new RaceNode(iter.next().getEndNode());
				}
			}
			while (rNode.getWinner() == null) {
				if (iter.hasNext()) {
					rNode = new RaceNode(iter.next().getEndNode());
				}
			}
			while (rNode.getWinner().getHorse() == null) {
				if (iter.hasNext()) {
					rNode = new RaceNode(iter.next().getEndNode());
				}
			}
			i++;
			//if (rNode.getCourseName().startsWith("Bri")) { // no qualifier
				//System.out.println(rNode.getRaceId());
				
				// check for race node already
				Node race = outputIndex.get("raceId", rNode.getRaceId()).getSingle();
				
				if (race == null) race = rNode.copyNode(output);
				
				//get winner
				IndividualResultNode winner = rNode.getWinner();
				// calculate sigmoid
				
				
				System.out.println(i + ": " + rNode.getDistance() + " " +  rNode.getCadge());
				
				//System.out.println(rNode.getWinner());
				if (rNode.getWinner() == null) {
					Collection<IndividualResultNode> runners = rNode.getRunners();
					System.out.println("hi" + runners.size());
					
					for(IndividualResultNode irn : runners) {
						System.out.println(irn.getHorseName() + " " + irn.getPosition());
					}
				}
				
				HorseNode winningHorse = winner.getHorse();
				Collection<IndividualResultNode> winnersResults = null;
				Collection<IndividualResultNode> winnersResults = winner.getHorse();
				try {
					winnersResults = winningHorse.getResults();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					broken = true;
					break search;
				}
				
				if (winnersResults == null) {
					broken = true;
					break search;
					
				}
				
				// check winners results
				for (IndividualResultNode  irn : winnersResults) {
					// calculate sigmoid
					double sigmoid =1 - (1/(1 + Math.exp(6.0 - irn.poundsBeaten())));
					System.out.println(irn.getRace().getCourseName() + " " + irn.getDistanceBeatenAsString() + 
							" " + irn.poundsBeaten() + " " + sigmoid);
					
					// get race from other results
					RaceNode otherRace = irn.getRace();
					
					// check that it is a different result
					if (!rNode.equals(otherRace)) {
						// get Node
						Node otherRaceNode = outputIndex.get("raceId", otherRace.getRaceId()).getSingle();
						
						if (otherRaceNode == null) otherRaceNode = otherRace.copyNode(output);
						
						// create relationship
						Transaction tx = output.beginTx();
						
						try {
							Relationship r = race.createRelationshipTo(otherRaceNode, MyRelationshipTypes.WINNER_RACED_IN);
							r.setProperty("Weight", sigmoid);
							tx.success();
						} finally {
							tx.finish();
						}
					}
				}
			}
			//horses = rNode.getRunners();
		
	}*/
	
	public static void main(String[] args) {
		SNA sna = new SNA();
		sna.neo4j();
		sna.shutdown();
	}
	
	private void shutdown() {
		graphDb.shutdown();
		output.shutdown();
		
	}

	private void neo4j() {
		List<Relationship> list = allRaces();
		
		for (int i = 0; i < list.size(); i++) {
			
			try {
				RaceNode rNode = new RaceNode(list.get(i).getEndNode());
				System.out.println("race: " + rNode.getCourseName());
				
				// check for race node already
				Node race = outputIndex.get("raceId", rNode.getRaceId()).getSingle();
				
				if (race == null) race = rNode.copyNode(output);
				
				//get winner
				IndividualResultNode winner = rNode.getWinner();
				
				
				
				Collection<IndividualResultNode> winnersResults = winner.getHorse().getResults();
				
				System.out.println("winner: " + winner + " races: " + winnersResults.size());
				
				int no = 0;
				
				for (IndividualResultNode  irn : winnersResults) {
					// calculate sigmoid
					
					
					
					// get race from other results
					RaceNode otherRace = irn.getRace();
					
					// check that it is a different result
					if (!rNode.equals(otherRace)) {
						// get Node
						Node otherRaceNode = outputIndex.get("raceId", otherRace.getRaceId()).getSingle();
						
						if (otherRaceNode == null) otherRaceNode = otherRace.copyNode(output);
						
						// create relationship
						Transaction tx = output.beginTx();
						
						try {
							Relationship r = race.createRelationshipTo(otherRaceNode, MyRelationshipTypes.WINNER_RACED_IN);
							double sigmoid =1 - (1/(1 + Math.exp(6.0 - irn.poundsBeaten())));
							r.setProperty("Weight", sigmoid);
							no++;
							System.out.println(race + "  race: " + no + "  other " + otherRaceNode);
							tx.success();
						} finally {
							tx.finish();
						}
					}
				}
				
				
			} catch (Exception e) {

			}
		}
		
	}

	private List<Relationship> allRaces() {
		System.out.println("1");
		Iterable<Node> n = graphDb.getAllNodes();
		for (Node nn: n) {
			System.out.println(n.hashCode());
		}
		graphDb.getReferenceNode();
		graphDb.getReferenceNode().getSingleRelationship(SubRef.RACE, Direction.OUTGOING);
		System.out.println("1a " + graphDb.getReferenceNode().getSingleRelationship(SubRef.RACE, Direction.OUTGOING));
		Node raceRefNode = graphDb.getReferenceNode().getSingleRelationship(SubRef.RACE, Direction.OUTGOING).getEndNode();
		System.out.println("2");
		Iterable<Relationship> races = raceRefNode.getRelationships(Direction.OUTGOING, SubRef.RACE);
		System.out.println("3");
		Iterator<Relationship> iter = races.iterator();
		System.out.println("4");
		return Lists.newArrayList(iter);
		
	}

	// find a node
	// find paths of length 0
	
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
