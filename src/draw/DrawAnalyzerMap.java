package draw;

import individual.IndividualResult;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DrawAnalyzerMap {

	
	Map<Integer,Stall> theStalls; 
	Integer distance;
	
	

	/**
	 * Leave 0 empty
	 */
	public DrawAnalyzerMap(Integer distance) {
		this.distance = distance;
		
		//create stalls map
		theStalls = new TreeMap<>();
		
		//create 1-16
		for (int i = 1; i <=16 ; i++){
			Integer stallNumber = new Integer(i);
			theStalls.put(stallNumber, new Stall(stallNumber));
		}
		
		
		
	}

	public void add(List<IndividualResult> l){
		
		for (IndividualResult ir : l){
			Integer add = ir.getDrawAsInteger();
			Stall stall = theStalls.get(add);
			
			/**
			 * This can't be right - change to a new stall instead of stalll
			 */
			if (stall == null){
				stall = new Stall(add);
				theStalls.put(add, stall);
			}
			try {
				stall.add(ir);
			} catch (InvalidStallException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
			//System.out.println("Adding " + add + " to " + ir.getPosition() + " draw " + ir.getDrawAsInteger()); 
		}
	}

	
	
	public void compute(){
		for (Stall s : theStalls.values()){
			
			if (s.getNumberOfResults() > 0) System.out.println(s);
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Map<Integer,Stall> getMap() {
		// TODO Auto-generated method stub
		return theStalls;
	}

	public double getDistance() {
		// TODO Auto-generated method stub
		return distance;
	}

}
