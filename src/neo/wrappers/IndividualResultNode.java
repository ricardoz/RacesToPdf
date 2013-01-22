/**
 * 
 */
package neo.wrappers;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.util.NodeWrapperImpl;

/**
 * @author Miriam Martin
 *
 */
public class IndividualResultNode extends NodeWrapperImpl implements Comparable<IndividualResultNode>{
	
	enum IndividualResultRelationships implements RelationshipType{
		OFFICIAL_RATING, TOPSPEED, RPR
		
	}

	public IndividualResultNode(Node individualResultNode) {
		super(individualResultNode);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void addOfficialRating(Node numberNode) {
		getUnderlyingNode().createRelationshipTo(numberNode, IndividualResultRelationships.OFFICIAL_RATING);
	}

	public void addTopspeed(Node numberNode) {
		getUnderlyingNode().createRelationshipTo(numberNode, IndividualResultRelationships.TOPSPEED);
		
	}

	public void addRacingPostRating(Node numberNode) {
		getUnderlyingNode().createRelationshipTo(numberNode, IndividualResultRelationships.RPR);
		
	}

	public String getDraw() {
		// TODO Auto-generated method stub
		return (String)getUnderlyingNode().getProperty("draw");
	}
	
	public String getPosition() {
		// TODO Auto-generated method stub
		return (String)getUnderlyingNode().getProperty("position");
	}
	
	public String getHorseName() {
		// TODO Auto-generated method stub
		return (String)getUnderlyingNode().getProperty("horseName");
	}

	public String getComment() {
		// TODO Auto-generated method stub
		return (String)getUnderlyingNode().getProperty("comment");
	}

	public String getFractionalOdds() {
		// TODO Auto-generated method stub
		return (String)getUnderlyingNode().getProperty("fractionalOdds");
	}
	
	public String getWeight() {
		// TODO Auto-generated method stub
		return (String)getUnderlyingNode().getProperty("weight");
	}
	
	public String getDistanceBeatenAsString() {
		// TODO Auto-generated method stub
		return getUnderlyingNode().getProperty("distanceBeaten").toString();
	}

	public int getOfficialRating() {
		// TODO Auto-generated method stub
		return (int)getUnderlyingNode()
				.getSingleRelationship(IndividualResultRelationships.OFFICIAL_RATING, Direction.OUTGOING)
				.getEndNode().getProperty("numberValue");
	}
	
	public int getTopspeed() {
		// TODO Auto-generated method stub
		return (int)getUnderlyingNode()
				.getSingleRelationship(IndividualResultRelationships.TOPSPEED, Direction.OUTGOING)
				.getEndNode().getProperty("numberValue");
	}
	
	public int getRacingPostRating() {
		// TODO Auto-generated method stub
		return (int)getUnderlyingNode()
				.getSingleRelationship(IndividualResultRelationships.RPR, Direction.OUTGOING)
				.getEndNode().getProperty("numberValue");
	}

	public void fixComment() {
		String comment = getComment();
		int rightArrow = comment.indexOf(">") + 1;
		if (rightArrow > 2){
			String newComment = comment.substring(rightArrow);
			
			Transaction tx = getUnderlyingNode().getGraphDatabase().beginTx();
			
			try {
				getUnderlyingNode().setProperty("comment", newComment.replace("</div>", "").trim());
				tx.success();
			} finally {
				tx.finish();
			}
		}

		
		
	}

	@Override
	public int compareTo(IndividualResultNode o) {
		// TODO Auto-generated method stub
		return getPositionAsInteger() - o.getPositionAsInteger();
	}

	private int getPositionAsInteger() {
		try {
			int i = Integer.parseInt(getPosition());
			return i;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return 40;
		}
		
	}

	public RaceNode getRace() {
		
		Iterator<Relationship> iter = getUnderlyingNode().getRelationships().iterator();
		
		while (iter.hasNext()){
			Node n = iter.next().getOtherNode(getUnderlyingNode());
			if (n.hasProperty("raceId")) return new RaceNode(n);
		}
		
		return null;
	}

	public HorseNode getHorse() {
		Iterator<Relationship> iter = getUnderlyingNode().getRelationships().iterator();
		
		while (iter.hasNext()){
			Node n = iter.next().getOtherNode(getUnderlyingNode());
			if (n.hasProperty("horseId")) return new HorseNode(n);
		}
		return null;
	}

	public boolean isWinner() {
		// TODO Auto-generated method stub
		return getPositionAsInteger() == 1;
	}
	
	@Override
	public String toString(){
		// Date Course Distance Position Field Size
		String date = getRace().getRaceDate();
		return date;
		
	}

	public int getDrawAsInteger() {
		// TODO Auto-generated method stub
		return Integer.parseInt(getDraw());
	}

	public int poundsBeaten() {
		// TODO Auto-generated method stub
		String db = getDistanceBeatenAsString();
		Integer dbAsInt = Integer.parseInt(db);
		
		return dbAsInt * 16 / getRace().getDistance() ;
	}
	

	

}
