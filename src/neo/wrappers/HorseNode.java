/**
 * 
 */
package neo.wrappers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.util.NodeWrapperImpl;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;

/**
 * @author Miriam Martin
 *
 */
public class HorseNode extends NodeWrapperImpl implements Comparable<HorseNode>{
	
	enum horseRels implements RelationshipType{RUN, WIN, PLACE}

	private String horseId;
	private String horseName;

	/**
	 * @param node
	 */
	public HorseNode(Node node) {
		super(node);
		this.horseId = (String) node.getProperty("horseId");
		this.horseName = (String) node.getProperty("horseyName");
	}
	
	/**
	 * Get all the available results for this horse.
	 * IndividualResultNode is ordered by position
	 * @return a Collection (TreeSet) of IndividualResultNodes
	 */
	public Collection<IndividualResultNode> getResults(){
		Set<IndividualResultNode> results = new TreeSet<>();
		
		Iterator<Relationship> iter = getUnderlyingNode().getRelationships(horseRels.RUN).iterator();
 
		 while (iter.hasNext()){		 
				 results.add(new IndividualResultNode(iter.next().getEndNode())); 
		 }
		 
		return results;
	}
	
	/**
	 * Add all the available results for this horse to the collection provided.
	 * @param collection the collection that the races are to be added to
	 */
	public void getResults(Collection<IndividualResultNode> collection){		
		Iterator<Relationship> iter = getUnderlyingNode().getRelationships(horseRels.RUN).iterator();
		
		CollectionUtils.addAll(collection, iter);
 
	}
	
	/**
	 * Need a method that accepts a predicate
	 * @param irn
	 * @return
	 */
	public Collection<IndividualResultNode> getSubsequentResults(IndividualResultNode irn){
		Set<IndividualResultNode> results = new TreeSet<>();
		
		Iterator<Relationship> iter = getUnderlyingNode().getRelationships(horseRels.RUN).iterator();
		 
		 
		 
		 while (iter.hasNext()){
			 
			 IndividualResultNode possibleNode = new IndividualResultNode(iter.next().getEndNode());
				
			 if (possibleNode.getRace().compareTo(irn.getRace()) > 0)
				 results.add(possibleNode);			 
		 }
		
		
		
		return results;
	}
	
	/**
	 * 
	 * @param c
	 * @param irn
	 */
	public void getFilteredResults(Collection<IndividualResultNode> c, IndividualResultNode irn){
		Function<IndividualResultNode, Integer> getTopspeed
		 = new Function<IndividualResultNode, Integer>() {
			public Integer apply(IndividualResultNode irn){
				return Integer.valueOf(irn.getTopspeed());
			}
		};
		
	
		
		Ordering<IndividualResultNode> orderByTopspeed = Ordering.natural().onResultOf(getTopspeed);
		
		
		
		c.addAll(ImmutableSortedSet.orderedBy(
			    orderByTopspeed).addAll(c).build());
		
		
		
		
		
		
	}
	
	public Collection<IndividualResultNode> getSubsequentResultsByRpr(IndividualResultNode irn){
		Set<IndividualResultNode> results = new TreeSet<>();
		
		Iterator<Relationship> iter = getUnderlyingNode().getRelationships(horseRels.RUN).iterator();
		 
		 
		 
		 while (iter.hasNext()){
			 
			 IndividualResultNode possibleNode = new IndividualResultNode(iter.next().getEndNode());
				
			 if (possibleNode.getRace().compareTo(irn.getRace()) > 0)
				 results.add(possibleNode);			 
		 }
		
		
		
		return results;
	}
	
	public void addRun(IndividualResultNode irn){
		
		if (newResult(irn)){
					
			Transaction tx = getUnderlyingNode().getGraphDatabase().beginTx();
			
			try{
				getUnderlyingNode().createRelationshipTo(irn.getUnderlyingNode(), horseRels.RUN);
				tx.success();
			} finally {
				tx.finish();
			}
		}
	}
	
	private boolean newResult(IndividualResultNode irn) {
		
		
		 Iterator<Relationship> iter = getUnderlyingNode().getRelationships(horseRels.RUN).iterator();
		 
		 if (! iter.hasNext()) return true;
		 
		 while (iter.hasNext()){
			 if (iter.next().getEndNode().equals(irn.getUnderlyingNode())){
				 return false;
			 }
		 }
		
		return true;
	}

	public void addWin(IndividualResultNode irn){
		if (Integer.parseInt(irn.getPosition()) == 1){
			Transaction tx = getUnderlyingNode().getGraphDatabase().beginTx();
			
			try{
				getUnderlyingNode().createRelationshipTo(irn.getUnderlyingNode(), horseRels.WIN);
				tx.success();
			} finally {
				tx.finish();
			}
		}
		
		
	}
	
	public void addPlace(IndividualResultNode irn){
		Transaction tx = getUnderlyingNode().getGraphDatabase().beginTx();
		
		try{
			getUnderlyingNode().createRelationshipTo(irn.getUnderlyingNode(), horseRels.PLACE);
			tx.success();
		} finally {
			tx.finish();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getHorseName() {
		// TODO Auto-generated method stub
		return horseName;
	}
	
	public String toString(){
		return "Horse: " + horseName + " Id: " + horseId;
	}

	@Override
	public int compareTo(HorseNode arg0) {
		// TODO Auto-generated method stub
		return Integer.valueOf(horseId) - Integer.valueOf(arg0.horseId);
	}

}
