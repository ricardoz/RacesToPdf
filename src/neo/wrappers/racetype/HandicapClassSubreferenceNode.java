/**
 * 
 */
package neo.wrappers.racetype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import neo.wrappers.RaceNode;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseUtil;
import org.neo4j.util.NodeWrapperImpl;

import neo.enums.*;

/**
 * @author Miriam Martin
 *
 */
public class HandicapClassSubreferenceNode extends NodeWrapperImpl {
	
	enum HandicapClass implements RelationshipType{CLASS1, CLASS2, CLASS3, CLASS4, CLASS5, CLASS6};

	protected HandicapClassSubreferenceNode(Node node) {
		super(node);
	}
	
	public void addRace(RaceNode rn, int i){
		HandicapClass relType = lookUpRelationshipType(i);
		Relationship rel = getUnderlyingNode().getSingleRelationship(relType, Direction.OUTGOING);
		Node hcapClass = rel.getOtherNode(getUnderlyingNode());
		
		boolean notAlreadyExist = rn.getUnderlyingNode().getSingleRelationship(relType, Direction.OUTGOING) == null;
		if (notAlreadyExist ) {
			rn.getUnderlyingNode().createRelationshipTo(hcapClass, relType);
		}
	}
	
	public Set<RaceNode> getRaces(int raceClass, int distance){
		HandicapClass hc = lookUpRelationshipType(raceClass);
		
		Set<RaceNode> set = new TreeSet<>();
		
		// get intermediary node
		Node inter = getUnderlyingNode().getSingleRelationship(hc, Direction.OUTGOING).getEndNode();
		
		// get all nodes from there
		Iterator<Relationship> iterator = inter.getRelationships().iterator();
		
		while (iterator.hasNext()){
			Node possibleNode = iterator.next().getStartNode();
			if (possibleNode != inter ){
				try {
					RaceNode rn = new RaceNode(possibleNode);
					if (rn.getDistance() == distance) set.add(rn);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//System.out.println("error " + possibleNode.toString());
				}
			}
		}
		
		return set;
		
	}
	
	public Set<RaceNode> getRacesAtDistanceAndCourse(int distance, String courseName){
		//HandicapClass hc = lookUpRelationshipType(raceClass);
		
		Set<RaceNode> set = new TreeSet<>();
		
		List<Node> intermediates = new ArrayList<>();
		
		for (RaceClass hc : RaceClass.values()) {
			System.out.println(hc);
			intermediates.add(getUnderlyingNode().getSingleRelationship(hc, Direction.OUTGOING).getEndNode());
		}
		
		System.out.println(intermediates.size());
		
		// get intermediary node
		//Node inter = getUnderlyingNode().getSingleRelationship(hc, Direction.OUTGOING).getEndNode();
		
		for (Node inter: intermediates) {
			// get all nodes from there
			Iterator<Relationship> iterator = inter.getRelationships()
					.iterator();
			while (iterator.hasNext()) {
				Node possibleNode = iterator.next().getStartNode();
				if (possibleNode != inter) {
					try {
						RaceNode rn = new RaceNode(possibleNode);
						if(rn.getCourseName().startsWith("Catt")) {
							System.out.println(rn.getCourseName());
							System.out.println(courseName);
							System.out.println(rn.getCadge());
							System.out.println(rn.getCourseName() == courseName);
							System.out.println("");
						}
						
						if (rn.getDistance() == distance && rn.getCourseName().equals( courseName))
							set.add(rn);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//System.out.println("error " + possibleNode.toString());
					}
				}
			}
		}
		return set;
		
	}
	
	private HandicapClass lookUpRelationshipType(int i) {
		switch(i){
		case 1: return HandicapClass.CLASS1;
		case 2: return HandicapClass.CLASS2;
		case 3: return HandicapClass.CLASS3;
		case 4: return HandicapClass.CLASS4;
		case 5: return HandicapClass.CLASS5;
		case 6: return HandicapClass.CLASS6;
		}
		return null;
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
		GraphDatabaseUtil util = new GraphDatabaseUtil(graphDb);



		Node node = null;

		// START GET HANDICAP SUBREFERENCE NODE

		Transaction tx = graphDb.beginTx();

		HandicapClassSubreferenceNode hcapNode = null;

		try {
			hcapNode = RaceTypeSubreferenceNode.getHandicapReferenceNode(util).getHandicapNode();
			
			node = hcapNode.getUnderlyingNode();
			
			for (HandicapClass hc : HandicapClass.values()){
				if (node.getRelationships(hc).iterator().hasNext() == false) {
					Node newNode = graphDb.createNode();
					newNode.setProperty("handicapClass", hc.toString());
					node.createRelationshipTo(newNode, hc);
				}
			}

			tx.success();
		} finally {
			tx.finish();
		}
	}

}
