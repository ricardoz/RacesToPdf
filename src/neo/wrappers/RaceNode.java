/**
 * 
 */
package neo.wrappers;



import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import neo.enums.RaceClassNode;
import neo.enums.SubRef;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.util.NodeWrapperImpl;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

/**
 * @author Miriam Martin
 *
 */
public class RaceNode extends NodeWrapperImpl implements Comparable<RaceNode> {

	private String name;
	private String id;
	
	enum  RaceEnum implements RelationshipType{FIELD_SIZE, RUNNER, DISTANCE};

	public RaceNode(Node node) {
		super(node);
		
		name = (String) node.getProperty("raceName");
		id = (String)node.getProperty("raceId");
		// TODO Auto-generated constructor stub
	}
	
	public RaceNode(Node node, String mode){
		super(node);
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		//Chris c = new Chris();
		//HtmlPage p = c.getPageFromWebClient("http://www.racingpost.com/horses/result_home.sd?race_id=533000&r_date=2011-06-15&popup=yes#results_top_tabs=re_&results_bottom_tabs=NOTEBOOK");
		
		//System.out.println(p.asXml());
		//System.out.println(p.asText());
		
		

	}
	
	public Node copyNode(GraphDatabaseService db) {
		Node n = null;
		Index<Node> index = db.index().forNodes("raceId");
		Transaction tx = db.beginTx();
		try {
			// create node
			n = db.createNode();
			
			// link to ref node
			//n.createRelationshipTo(db.getReferenceNode(), SubRef.TEST);
			
			// edit properties
			n.setProperty("id", id);
			n.setProperty("name", name);
			n.setProperty("course", getCourseName());
			System.out.println("In race node copyNode " + name);
			
			// index node
			index.add(n,"raceId", getRaceId());
			
			tx.success();
		} finally {
			tx.finish();
		}
		
		return n;
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		RaceNode other = (RaceNode) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Collection<IndividualResultNode> getRunners(){
		Set<IndividualResultNode> set = new TreeSet<>();
		Iterable<Relationship> rels = getUnderlyingNode().getRelationships(RaceEnum.RUNNER);
		Iterator<Relationship> iter = rels.iterator();
		
		while (iter.hasNext()){
			Node runner = iter.next().getOtherNode(getUnderlyingNode());
			
			
				set.add(new IndividualResultNode(runner));
			
		}
		
		return set;
	}

	public void setRaceIdProperty(GraphDatabaseService graphDb, String raceId) {
		
		
		Transaction tx = graphDb.beginTx();
		try {
			
			getUnderlyingNode().setProperty("raceId", raceId);
			
			System.out.println("added: " + raceId);

			tx.success();
		} catch (Exception e){
			System.out.println("failed: " + raceId );
			e.printStackTrace();
		} finally {
			tx.finish();
		}
	}
	
	public void setRaceNameProperty(GraphDatabaseService graphDb, String raceName) {
		
		this.name = raceName;
		
		Transaction tx = graphDb.beginTx();
		try {
			
			getUnderlyingNode().setProperty("raceName", raceName);
			
			System.out.println("added: " + raceName);

			tx.success();
		} catch (Exception e){
			System.out.println("failed: " + raceName );
			e.printStackTrace();
		} finally {
			tx.finish();
		}
	}
	
	public void setRaceDateProperty(GraphDatabaseService graphDb, String raceDate) {
		
		
		Transaction tx = graphDb.beginTx();
		try {
			
			getUnderlyingNode().setProperty("raceDate", raceDate);
			
			System.out.println("added: " + raceDate);

			tx.success();
		} catch (Exception e){
			System.out.println("failed: " + raceDate );
			e.printStackTrace();
		} finally {
			tx.finish();
		}
	}
	
	public void setRaceTimeProperty(GraphDatabaseService graphDb, String raceTime) {
		
		
		Transaction tx = graphDb.beginTx();
		try {
			
			getUnderlyingNode().setProperty("raceTime", raceTime);
			
			System.out.println("added: " + raceTime);

			tx.success();
		} catch (Exception e){
			System.out.println("failed: " + raceTime );
			e.printStackTrace();
		} finally {
			tx.finish();
		}
	}
	
	@Override
	public String toString(){
		return "Race name: " + name + " Race id: " + id;
	}

	public boolean isGroup() {
		return name.indexOf("(Group ") > 0;
	}
	
	public boolean isGroup1() {
		return name.indexOf("(Group 1") > 0;
	}
	
	public boolean isGroup2() {
		return name.indexOf("(Group 2") > 0;
	}
	
	public boolean isGroup3() {
		return name.indexOf("(Group 3") > 0;
	}
	
	

	public boolean isHandicap() {
		return name.indexOf("Handicap") > 0;
	}
	
	public boolean isMaiden() {
		return name.indexOf("Maiden") > 0;
	}

	public boolean isConditionsRace() {
		return name.indexOf("Conditions") > 0;
	}

	public void addFieldSize(NumberNode numberNode) {
		getUnderlyingNode().createRelationshipTo(numberNode.getUnderlyingNode(), RaceEnum.FIELD_SIZE);
		
	}

	public void addCadge(String cadge) throws Exception {
		if (getUnderlyingNode().hasProperty("cadge")){
			throw new Exception ("failed adding cadge " + cadge + "\n" + this);
		} else {
			getUnderlyingNode().setProperty("cadge", cadge);
		}
		
	}

	public void addAnalysis(String analysis) throws Exception {
		if (getUnderlyingNode().hasProperty("analysis")){
			throw new Exception ("failed adding analysis " + analysis + "\n" + this);
		} else {
			getUnderlyingNode().setProperty("analysis", analysis);
		}
		
	}

	public void addIndividualResult(IndividualResultNode irWrapper) {
		getUnderlyingNode().createRelationshipTo(irWrapper.getUnderlyingNode(), RaceEnum.RUNNER);
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public Node getReferenceNode() {
		// TODO Auto-generated method stub
	
			Iterable<Relationship> rels = getUnderlyingNode().getRelationships(SubRef.RACE);
			Iterator<Relationship> iter = rels.iterator();
			
			Node n = iter.next().getOtherNode(getUnderlyingNode());
			
			
					
					
			
			
			return n;
	}
	
	public String getRaceId(){
		return id;
	}

	public void addDistance(NumberNode distanceNumberNode) {

		if (!getUnderlyingNode().getRelationships(RaceEnum.DISTANCE).iterator().hasNext()){
			getUnderlyingNode().createRelationshipTo(distanceNumberNode.getUnderlyingNode(), RaceEnum.DISTANCE);
		}
		
	}

	public int getDistance() {
		
		return (int)getUnderlyingNode().getSingleRelationship(RaceEnum.DISTANCE, Direction.OUTGOING).getEndNode().getProperty("numberValue");
	}

	public String getCadge() {
		// TODO Auto-generated method stub
		return (String)getUnderlyingNode().getProperty("cadge");
	}
	
	public String getRaceDate() {
		// TODO Auto-generated method stub
		return (String)getUnderlyingNode().getProperty("raceDate");
	}
	
	public String getAnalysis() {
		if (getUnderlyingNode().hasProperty("analysis")) {
		
			return (String) getUnderlyingNode().getProperty("analysis");
		}
		
		return "";
	}

	public void fixAnalysis() {
		String analysis = getAnalysis();
		
		int index = analysis.indexOf("MY RACE NOTES");
		
		if (index > 0){
			analysis = analysis.substring(index);
			analysis = analysis.replace("MY RACE NOTES", "").trim();
			String[] split = analysis.split("\\.");
			StringBuffer sb = new StringBuffer();
			
			for (int j = 0; j < split.length; j++) {
				sb.append(split[j].replaceAll("\\r|\\n", " ").trim());
				//System.out.println("split: " + split[j]);
				sb.append(". ");
			}
			
			Transaction tx = getUnderlyingNode().getGraphDatabase().beginTx();
			
			try {
				getUnderlyingNode().setProperty("analysis", sb.toString());
				tx.success();
			} finally {
				tx.finish();
			}
			
			//System.out.println("old: " + analysis);
			//System.out.println("new: " + sb.toString());
		}
		
	}
	
	public int getDayOfYear(){
		String thisDate = getRaceDate();
		
		
		String[] thisTokens = thisDate.split("\\s+");
		
		
		int thisDayOfMonth = Integer.parseInt(thisTokens[0].trim());
		
		
		int thisStartOfMonth = 0;
		
		
		try {
			thisStartOfMonth = examples.Utilities.getMonthStartAsDayOfYear(thisDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(thisDate);
			System.exit(1);
		}
		
		return thisDayOfMonth + thisStartOfMonth;
	}

	@Override
	public int compareTo(RaceNode rn) {
		return getDayOfYear() - rn.getDayOfYear();
		
	}

	public RaceClassNode getRaceClass() {
		Iterator<Relationship> iter = getUnderlyingNode().getRelationships(Direction.BOTH).iterator();
		
		
		while (iter.hasNext()){
			Node n = iter.next().getOtherNode(getUnderlyingNode());
			if (n.hasProperty("handicapClass")){
				System.out.println(n.getProperty("handicapClass"));
				return new RaceClassNode(n);
			}
		}
		
		return null;
	}

	public int getFieldSize() {
		
		return (int)getUnderlyingNode().getSingleRelationship(RaceEnum.FIELD_SIZE, Direction.OUTGOING)
				.getEndNode().getProperty("numberValue");
	}

	public String getCourseName() {
		// TODO Auto-generated method stub
		return getCourseNode().getCourseName();
	}

	private CourseNode getCourseNode() {
		Iterator<Relationship> iter = getUnderlyingNode().getRelationships(Direction.INCOMING).iterator();
		
		while (iter.hasNext()){
			Node n = iter.next().getStartNode();
			if (n.hasProperty("courseId")){
				return new CourseNode(n);
			}
		}
		return null;
	}

	public IndividualResultNode getWinner() {
		Collection<IndividualResultNode> horses = getRunners();
		Iterator<IndividualResultNode> iter = horses.iterator();
		
		for (IndividualResultNode irn : horses) {
			if (irn.getPosition().equals("1")) return irn;
		}
		return null;
	}

}
