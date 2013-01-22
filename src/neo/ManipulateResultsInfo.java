/**
 * 
 */
package neo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import neo.enums.SubRef;
import neo.wrappers.NumberNode;
import neo.wrappers.RaceNode;
import neo.wrappers.racetype.HandicapClassSubreferenceNode;
import neo.wrappers.racetype.RaceTypeSubreferenceNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseUtil;

/**
 * @author Miriam Martin
 *
 */
public class ManipulateResultsInfo {

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
		Index<Node> numbersIndex = graphDb.index().forNodes("numbers");



		Node node = null;

		// START GET HANDICAP SUBREFERENCE NODE

		/*Transaction tx = graphDb.beginTx();

		HandicapClassSubreferenceNode hcapNode = null;

		try {
			hcapNode = RaceTypeSubreferenceNode.getHandicapReferenceNode(util).getHandicapNode();

			tx.success();
		} finally {
			tx.finish();
		}*/

		

		// END GET HANDICAP SUBREFERENCE NODE

		// START ITERATE RACE NODES
		
		Node refNode = graphDb.getReferenceNode();
		Iterable<Relationship> rels2 = refNode.getRelationships(SubRef.RACE);
		Iterator<Relationship> iters = rels2.iterator();

		Node raceRefNode = iters.next().getOtherNode(refNode);

		Iterable<Relationship> raceRels = raceRefNode.getRelationships(SubRef.RACE);
		Iterator<Relationship> raceIter = raceRels.iterator();

		int races = 0;
		int dups = 0;

		Set<String> set = new HashSet<>();

		while (raceIter.hasNext()){

			Node raceNode = raceIter.next().getOtherNode(raceRefNode);

			RaceNode raceWrapper = null;



			try {
				raceWrapper = new RaceNode(raceNode);

				//String raceName = raceWrapper.getName().toLowerCase();

				//boolean isHandicap = raceName.indexOf("handicap") > 0;

				//boolean notBumper = raceName.indexOf("national hunt flat") < 0;

				//boolean notHurdle = raceName.indexOf("chase") < 0;

				///boolean notChase = raceName.indexOf("hurdle") < 0;

				boolean isSet = raceNode.hasProperty("cadge");

				if ( isSet ){
					races++;
					String cadge = (String) raceNode.getProperty("cadge");

					if (!set.add(raceWrapper.getRaceId())){
						dups++;
					}

					System.out.println(cadge );

					int start = 1;
					int end = cadge.indexOf(")");
					String raceClass = cadge.substring(start, end );
					//String[] tokens = raceClass.split("\\s+");


					try {
						
						
						
						/**System.out.println(raceClass  + " " + Integer.parseInt(tokens[1]));
						int theClass = Integer.parseInt(tokens[1]);
						if (hcapNode != null){
							tx = graphDb.beginTx();
							
							try {
								hcapNode.addRace(raceWrapper, theClass);
								System.out.println("adding: " + raceClass  + " " + Integer.parseInt(tokens[1]));
								tx.success();
							} finally {
								tx.finish();
							}
							
						}*/
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


					start = cadge.indexOf("(", end) + 1;
					end = cadge.indexOf(")", start);
					String raceQualifier = cadge.substring( start, end ).trim();

					String remaining = cadge.substring(end + 1).trim();

					if (remaining.startsWith("(")){
						start = remaining.indexOf(")") + 1;
						remaining = remaining.substring(start).trim();
					}
					
					String raceDistance = remaining.split("\\s+")[0];
					
					Transaction tx = graphDb.beginTx();
					
					try {
						 int distanceInFurlongs = convertDistanceStringToInteger(raceDistance);
						 IndexHits<Node> n = numbersIndex.get("numberValue", distanceInFurlongs);
							NumberNode distanceNumberNode = new NumberNode(n.getSingle());
							raceWrapper.addDistance(distanceNumberNode);
							
						tx.success();
						 
					} catch (Exception e){
						System.out.println("error " + raceQualifier );
						System.out.println("err " +raceDistance + "\n" );
						System.exit(0);
					} finally {
						tx.finish();
					}

					System.out.println(raceQualifier );
					System.out.println(raceDistance + "\n" );
				}



			} catch (Exception e){

			}

			// END ITERATE RACE NODES


		}
		System.out.println("races " + races + " dups" + dups);
	}
	
	/**
	 * also a method
	 * @param s
	 * @return
	 */
	private static Integer convertDistanceStringToInteger(String s) {
		
		if (s.equals("2m½f")){
			return 17;
		}
		
		if (s.equals("1m½f")){
			return 9;
		}
		
		if (s.charAt(s.length()-2) == '½') {
			System.out.println("replace--" + s);
			String newString = s.replaceAll("½", "");
			System.out.println("replace--" + newString);
			
			 
			return convertDistanceStringToInteger(newString) + 1;
		}
		Integer i;
		
		
		
		if(s.length() == 2){
			if (s.endsWith("f")){
				i = Integer.parseInt(s.substring(0, 1));
			} else {
				i = Integer.parseInt(s.substring(0, 1)) * Integer.valueOf(8);
			}
		} else {
			int sum = 0;
			sum += 8 * Integer.parseInt(s.substring(0, 1));
			sum += Integer.parseInt(s.substring(2, 3));
			i = sum;
		}
		
		System.out.println("rf.cdsti--" + s + " gives " + i );
		
		return i;
	}

}
