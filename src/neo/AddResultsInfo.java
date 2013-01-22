/**
 * 
 */
package neo;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import neo.enums.SubRef;
import neo.wrappers.IndividualResultNode;
import neo.wrappers.JockeyNode;
import neo.wrappers.NumberNode;
import neo.wrappers.RaceNode;
import neo.wrappers.TrainerNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import examples.BasicChris;
import examples.PricePoundResult;

/**
 * @author Miriam Martin
 *
 */
public class AddResultsInfo {

	
	private GraphDatabaseService graphDb;
	
	private PricePoundResult ppr;

	private RaceNode race;
	
	public AddResultsInfo(final GraphDatabaseService graphDb, final RaceNode race, PricePoundResult ppr){
		this.graphDb = graphDb;
		this.race = race;
		this.ppr = ppr;
	}
	
	/**
	 * 
	 */
	public void addInfo(){
		
		// START GET BUILDING BLOCKS
		Iterator<DomNode> distanceIterator = ppr.getDistanceIterator();
		Iterator<DomNode> commentIterator = ppr.getcommentIterator();
		List<HtmlTableRow> irTableRows = ppr.getIndividualResultTabeleRows();
		List<HtmlTableRow> jockeyTableRows = ppr.getJockeyTabeleRows();
		
		String cadge = ppr.findRaceClassAgeString();
		
		String analysis = ppr.getAnalysis();
		
		// END GET BUILDING BLOCKS
		
		
		// START ADD FIELD SIZE
		
		addFieldSize(race);
		
		// END ADD FIELD SIZE
		
		
		// START ADD CADGE
		
		
		/**
		 * 0 class
		 * 1 age restriction
		 * 2 distance
		 * 3 ground
		 */
		
		
		/*String[] tokens = ageClassDistanceGround.split("\\s+");
		int start = 1;
		int end = cadge.indexOf(")") - 1;
		String raceClass = cadge.substring(start, end );
		
		addRaceClassProperty(raceClass);
		
		String adg = cadge.substring( end );*/
		
			try {
				race.addCadge(cadge);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		// END ADD CADGE
		
		
		// START ADD ANALYSIS
		
			try {
				race.addAnalysis(analysis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		// END ADD ANALYSIS
		
		
		// START ADD INDIVIDUAL RESULTS
			
			/**
			 * for each horse
			 * add properties
			 * add trainer
			 * add jockey
			 */
			
			addIndividualRaces(distanceIterator , commentIterator , irTableRows , jockeyTableRows);
			
		// END ADD INDIVIDUAL RESULTS
		
	
	}
	
	
	private void addIndividualRaces(Iterator<DomNode> distanceIterator,
			Iterator<DomNode> commentIterator, List<HtmlTableRow> irTableRows,
			List<HtmlTableRow> jockeyTableRows) {
		
		int totalDistance = 0;
		int currentRow = 0;
		
		/**
		 * for each horse
		 * add properties
		 * add trainer
		 * add jockey
		 */
		
		for (HtmlTableRow row : irTableRows){
			
			List<HtmlTableCell> data = row.getCells();
			
			
			// START CREATE NODE
			
				
				
					Node individualResultNode = graphDb.createNode();
					
					// START ADD POSITION AND DRAW
						addPositionAndDraw(data.get(1).asText(), individualResultNode);
						System.out.println("draw: " +individualResultNode.getProperty("draw"));
						System.out.println("pos: " +individualResultNode.getProperty("position"));
					// END ADD POSITION AND DRAW
						
					// START ADD DISTANCE
						
						String distance = distanceIterator.next().asText();
						double individualDistanceAsDouble =1.0;
						try {
							individualDistanceAsDouble = convertDistanceStringToDouble(distance);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						totalDistance += individualDistanceAsDouble;
						addDistance(totalDistance, individualResultNode);
						
					// END ADD DISTANCE
						
					// START ADD COMMENT
						
						addComment(commentIterator.next().asXml(), individualResultNode);
						
					// END ADD COMMENT
						
						
					// START ADD ODDS AND HORSE NAME
						
						String oddsHorse = data.get(3).asText();
						
						String[] oddsHorseArray = oddsHorse.split("\\s+");
						String odds = oddsHorseArray[oddsHorseArray.length - 1];
						StringBuffer horseBuffer = new StringBuffer(oddsHorseArray[0]);
						for (int h = 1; h < oddsHorseArray.length - 1; h++){
							horseBuffer.append(oddsHorseArray[h]);
						}
						String horseName = horseBuffer.toString();
						
						individualResultNode.setProperty("horseName", horseName);
						
						addOddsAndHorseName(odds, individualResultNode);
				
					// END ADD ODDS AND HORSE NAME
						
						
					// START ADD WEIGHT
						
						String weight = data.get(5).asText().trim();
						individualResultNode.setProperty("weight", weight);
						
					// END ADD WEIGHT
						
					// START ADD OR, TS, RPR
						
						IndividualResultNode irWrapper = new IndividualResultNode(individualResultNode);
						
						int or = 1;
						int ts = 1;
						int rpr = 1;
						
						
						try {
							or = Integer.parseInt(data.get(7).asText());
							ts = Integer.parseInt(data.get(8).asText());
							rpr = Integer.parseInt(data.get(9).asText());
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						irWrapper.addOfficialRating(getNumberNode(or));
						irWrapper.addTopspeed(getNumberNode(ts));
						irWrapper.addRacingPostRating(getNumberNode(rpr));
						
					// END ADD OR, TS, RPR
						
					// START ADD TRAINER
						
						String trainerXml = data.get(6).asXml();
						
						String trainerId = getIdFromXml(trainerXml);
						
						String trainerName = data.get(6).asText();
						
						Node trainerNode = graphDb.index().forNodes("trainers").get("trainerId", trainerId).getSingle();
						
						if (trainerNode == null){
							trainerNode = graphDb.createNode();
							trainerNode.setProperty("trainerName", trainerName);
							trainerNode.setProperty("trainerId", trainerId);
							 graphDb.index().forNodes("trainers").add(trainerNode,"trainerId", trainerId);
						}
						
						TrainerNode trainerWrapper = new TrainerNode(trainerNode);
						trainerWrapper.addIndividualResult(irWrapper);
						
					// END ADD TRAINER
						
					// START ADD JOCKEY
						
						String jockeyXml = jockeyTableRows.get(currentRow).asXml();
						
						String jockeyId = getIdFromXml(jockeyXml);
						
						String jockeyName = jockeyTableRows.get(currentRow).asText();
						
						Node jockeyNode = graphDb.index().forNodes("jockeys").get("jockeyId", jockeyId).getSingle();
						
						if (jockeyNode == null){
							jockeyNode = graphDb.createNode();
							jockeyNode.setProperty("jockeyName", jockeyName);
							jockeyNode.setProperty("jockeyId", jockeyId);
							graphDb.index().forNodes("jockeys").add(jockeyNode,"jockeyId", jockeyId);
						}
						
						JockeyNode jockeyWrapper = new JockeyNode(jockeyNode);
						jockeyWrapper.addIndividualResult(irWrapper);
						
					// END ADD JOCKEY
						
					// START ADD HORSE
						
						String horseXml = data.get(3).asXml();
						
						String horseId = getIdFromXml(horseXml);
						
						Node horseNode = graphDb.index().forNodes("horses").get("horseId", horseId).getSingle();
						
						if (horseNode == null){
							horseNode = graphDb.createNode();
							horseNode.setProperty("horseyName", horseName);
							horseNode.setProperty("horseId", horseId);
							graphDb.index().forNodes("horses").add(horseNode,"horseId", horseId);
						}
						
					// END ADD HORSE
						
						
					race.addIndividualResult(irWrapper);
				
			
			// END CREATE NODE
				currentRow++;
		}
		
	}

	private Node getNumberNode(int or) {
		return graphDb.index().forNodes("numbers").get("numberValue", or).getSingle();
		
	}

	private void addOddsAndHorseName(String odds, Node individualResultNode) {
		
		double oddsAsDouble =1;
		try {
			oddsAsDouble = convertOddsStringToDouble(odds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		individualResultNode.setProperty("fractionalOdds", odds);
		individualResultNode.setProperty("decimalOdds", oddsAsDouble);
		
	}

	private void addComment(String comment, Node individualResultNode) {
		individualResultNode.setProperty("comment", comment);
		
	}

	private void addDistance(double totalDistance, Node individualResultNode) {
		DecimalFormat twoPlaces = new DecimalFormat("#.##");
		
		
		
		individualResultNode.setProperty("distanceBeaten", Double.valueOf(twoPlaces.format(totalDistance)));
		
	}

	private void addPositionAndDraw(String pd, Node individualResultNode) {
		System.out.println("pd: " + pd);
		String[] positionDraw = pd.split("\\s+");
		String draw = positionDraw[1];
		String position = positionDraw[0];
		
		individualResultNode.setProperty("position", position);
		individualResultNode.setProperty("draw", draw);
		
	}

	

	
		
	

	private void addFieldSize(final RaceNode r) {
		/**
		 * call ppr.findRaceInfo
		 * get the node associated with that number
		 * call race.addFieldSize
		 */
		int numberOfRunners = ppr.findRaceInfo();
		Index<Node> numbersIndex = graphDb.index().forNodes("numbers");
		IndexHits<Node> n = numbersIndex.get("numberValue", numberOfRunners);
		r.addFieldSize(new NumberNode(n.getSingle()));

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);
		
		BasicChris bc = null;
		
		/*try {
			bc = new BasicChris("http://www.racingpost.com/horses/result_home.sd?race_id=548062");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		
		
		Node node = null;
		
		// START GET RACE SUBREFERENCE NODE
		
		Node refNode = graphDb.getReferenceNode();
		Iterable<Relationship> rels2 = refNode.getRelationships(SubRef.RACE);
		Iterator<Relationship> iters = rels2.iterator();
		
		Node raceRefNode = iters.next().getOtherNode(refNode);
		
		// END GET RACE SUBREFERENCE NODE
		
		Iterable<Relationship> raceRels = raceRefNode.getRelationships(SubRef.RACE);
		Iterator<Relationship> raceIter = raceRels.iterator();
		
		int races = 0;
		
		int undef = 0;
		
		while (raceIter.hasNext()){
			races++;
			
			Node raceNode = raceIter.next().getOtherNode(raceRefNode);
			
			RaceNode raceWrapper = null;
			
			
		
			try {
				raceWrapper = new RaceNode(raceNode);
				
				
				
				//if (!raceWrapper.getUnderlyingNode().hasProperty("cadge")){
					//int leaders = 0;
					//System.out.println(races + " race " + raceWrapper + " cadge: " + raceWrapper.getUnderlyingNode().getProperty("cadge"));
					//List<IndividualResultNode> runners = raceWrapper.getRunners();
					
					/**
					 * find leaders
					 */
					/*
					 * for (IndividualResultNode irn : runners){
						if (irn.getComment().indexOf("led")> 0 || irn.getComment().indexOf("made all") > 0 ){
							leaders++;
							
							
						}
					}
					
					if (leaders > 2){
						System.out.println(leaders + " leaders: " + 
								"http://www.racingpost.com/horses/result_home.sd?race_id=" + raceNode.getProperty("raceId"));
					}*/
					
					/**
					 * top x/y held-up
					 */
					/*
						try {
							if (allButOneHeldUp(runners , 3)){
								System.out.println("http://www.racingpost.com/horses/result_home.sd?race_id=" + raceNode.getProperty("raceId"));
							}
							
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					*/
				//}
				String raceName = raceWrapper.getName().toLowerCase();
				
				boolean notSet = !raceWrapper.getUnderlyingNode().hasProperty("cadge");
				
				boolean notBumper = raceName.indexOf("national hunt flat") < 0;
				
				boolean notHurdle = raceName.indexOf("chase") < 0;
				
				boolean notChase = raceName.indexOf("hurdle") < 0;
				
				if ( notSet && notBumper && notHurdle && notChase){
					
					System.out.println("http://www.racingpost.com/horses/result_home.sd?race_id=" + raceNode.getProperty("raceId"));
					
					undef++;
					
					/*
					Transaction tx = graphDb.beginTx();
					
					try {
						String address = "http://www.racingpost.com/horses/result_home.sd?race_id=" + raceNode.getProperty("raceId");
						
						HtmlPage page = bc.getPageFromWebClient(address);
						
						PricePoundResult ppr = new PricePoundResult(page, 8, null);
						
						AddResultsInfo ari = new AddResultsInfo(graphDb, raceWrapper, ppr);
						
						
						
							ari.addInfo();

							tx.success();
						}  catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						tx.finish();
					}*/
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
			/**Transaction tx = graphDb.beginTx();
			
			try {
				String address = "http://www.racingpost.com/horses/result_home.sd?race_id=" + raceNode.getProperty("raceId");
				
				HtmlPage page = bc.getPageFromWebClient(address);
				
				PricePoundResult ppr = new PricePoundResult(page, 8, null);
				
				AddResultsInfo ari = new AddResultsInfo(graphDb, raceWrapper, ppr);
				
				
				
					ari.addInfo();

					tx.success();
				}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				tx.finish();
				*/
				
				
		
		}
		
		
		
		/**Transaction tx = graphDb.beginTx();
		try {
			node = graphDb.createNode();

			tx.success();
		} finally {
			tx.finish();
		}
		
		
		HtmlPage page = bc.getMainWindowPage();
		
		PricePoundResult ppr = new PricePoundResult(page, 8, null);
		
		RaceNode raceNode = new RaceNode(node, "test" );
		AddResultsInfo ari = new AddResultsInfo(graphDb, raceNode, ppr);
		Transaction tx2 = graphDb.beginTx();
		try {
		ari.addInfo();
		
		tx2.success();
	} finally {
		tx2.finish();
	}
		
		Iterable<String> prop = node.getPropertyKeys();
		Iterator<String> iter = prop.iterator();
		while(iter.hasNext()){
			String property = iter.next();
			System.out.println(property+ ": " + node.getProperty(property));
		}
		
		Iterable<Relationship> rels = node.getRelationships();
		System.out.println( "no of rels : " + rels.toString());
		Iterator<Relationship> iter2 = rels.iterator();
		
		while(iter2.hasNext()){
			try {
				Node on = iter2.next().getOtherNode(node);
				Iterable<String> prop3 = on.getPropertyKeys();
				Iterator<String> iter3 = prop3.iterator();
				while(iter3.hasNext()){
					String property = iter3.next();
					try {
						System.out.println(property+ ": " + on.getProperty(property));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		System.out.println(races + " races in all " + undef);

	}
	
	private static boolean allButOneHeldUp(Collection<IndividualResultNode> runners, int i) {
		int heldUp = i+1;
		
		for (IndividualResultNode irn : runners){
			try {
				if (Integer.parseInt(irn.getPosition()) <= i + 1){
					String comment = irn.getComment().toLowerCase();
					if (comment.indexOf("held up") < 0 && comment.indexOf("dwelt") < 0 && comment.indexOf("midfield") < 0 && comment.indexOf("rear") < 0){
						
						heldUp --;
					}		
					
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				
			}
			
		}
		
		return (heldUp >= i);
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
	
private double convertDistanceStringToDouble(String distance) {
		
		if (distance.length() == 0) return 0;
		
		System.out.println("trying:--" + distance);
		int sumLast = 0 + distance.charAt(distance.length()-1);
		//first the numbers
		if (Character.isDigit(distance.charAt(0))){
			
			//is the last character not a fraction
			
			
			if (sumLast < 187){
				// return as double
				return Double.parseDouble(distance);
			} else {
				
				double whole = Double.parseDouble(distance.substring(0,distance.length()-1));
				
				double fraction = 0.3333333;
				
				if (sumLast == 188 ){
					fraction = 0.25;
				} else if (sumLast == 189 ){
					fraction = 0.5;
				} else if (sumLast == 190 ){
					fraction = 0.75;
				}
				
				return whole + fraction;
				
				
			}
			
		} else if (sumLast > 187){
			double fraction = 0.3333333;
			
			if (sumLast == 188 ){
				fraction = 0.25;
			} else if (sumLast == 189 ){
				fraction = 0.5;
			} else if (sumLast == 190 ){
				fraction = 0.75;
			}
			
			return fraction;
		} else {
			
			char c = distance.charAt(1);
			
			if (c =='s') return 0.05;
			if (c =='h') return 0.1;
			if (c =='d') return 0.15;
			if (c =='k') return 0.2;
		}
		return 0.6666666;
	}

private double convertOddsStringToDouble(String odds) {

	System.out.println("trying:--" + odds);

	odds = odds.replace("J", "");

	if (Character.isDigit(odds.charAt(0))){
		String[] frac = odds.split("/");
		return Double.parseDouble(frac[0]) / Double.parseDouble(frac[1]) + 1.0;

	} else {
		return 2.0;
	}
}

private String getIdFromXml(String horseXml) {
	
	int questionMark = horseXml.indexOf("?");
	int start = horseXml.indexOf("=", questionMark) + 1;
	int end = horseXml.indexOf("\"", start);
	return horseXml.substring(start,end);
}

}
