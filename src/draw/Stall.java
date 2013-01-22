/**
 * 
 */
package draw;

import java.util.LinkedList;
import java.util.List;

import individual.IndividualResult;

/**
 * @author Miriam Martin
 *
 */
public class Stall {
	
	List<IndividualResult> results;
	final private Integer number;
	private int numberOfResults;
	private double sum;
	private double poundsBeaten;
	private double oddsMinusPoundsBeaten;

	public Stall(Integer stallNumber) {
		this.number = stallNumber;
		results = new LinkedList<>();
		setNumberOfResults(0);
		sum = 0.0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void add(IndividualResult ir) throws InvalidStallException {
		if (ir.getDrawAsInteger().equals(number)){
			results.add(ir);
			
			// if winner
			if (ir.isWinner()){
				sum+= ir.getOddsAsDouble();
			} else {
				sum--;
			}
			
			poundsBeaten -= ir.getPoundsBeaten();
			
			oddsMinusPoundsBeaten += ir.getOddsAsDouble();
			
			oddsMinusPoundsBeaten -= ir.getPoundsBeaten();
			
			setNumberOfResults(getNumberOfResults() + 1);
			
		} else {
			throw new InvalidStallException(number, ir.getDrawAsInteger());
		}
		
	}
	
	@Override
	public String toString(){
		StringBuffer s = new StringBuffer("stall: " + number + " Number of runners: " + getNumberOfResults() + " lsp: " + sum + " mean: " + sum/getNumberOfResults()
				+ "\n ") ;
		
		for (IndividualResult ir : results){
			s.append("\n" + ir );
		}
		
		return s.toString();
		
	}
	
	public String stallToString(){
		StringBuffer s = new StringBuffer("Number " + number + ":--");
		for (IndividualResult ir : results){
			s.append(ir.getDraw());
		}
		
		return s.toString();
	}

	public double levelStakesProfit() {
		// TODO Auto-generated method stub
		return sum;
	}
	
	public double averagePoundsBeaten(){
		//System.out.println(number + "In abp pb: " + poundsBeaten + " nor " + getNumberOfResults() + " ave " +  poundsBeaten/getNumberOfResults() );
		if (getNumberOfResults() > 0){
			return poundsBeaten/getNumberOfResults();
		} else {
			return 0.0;
		}
		
	}
	
	public double averageOddsMinusPoundsBeaten(){
		//System.out.println(number + " In omp pb: " + oddsMinusPoundsBeaten + " nor " + getNumberOfResults() + " ave " + oddsMinusPoundsBeaten/getNumberOfResults() );
		if (getNumberOfResults() > 0){
			return oddsMinusPoundsBeaten/getNumberOfResults();
		} else {
			return 0.0;
		}
		
	}

	public Comparable<String> getName() {
		String s = "" + number;
		return s;
	}

	/**
	 * @return the numberOfResults
	 */
	public int getNumberOfResults() {
		return numberOfResults;
	}

	/**
	 * @param numberOfResults the numberOfResults to set
	 */
	private void setNumberOfResults(int numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

}
