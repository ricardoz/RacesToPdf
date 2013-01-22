/**
 * 
 */
package individual;

import draw.DrawAnalyzerMap;

/**
 * @author Miriam Martin
 *
 */
public class IndividualResult {

	private final String position;
	private final String draw;
	private final String odds;
	private final double oddsAsDouble;
	private final double totalDistance;
	private DrawAnalyzerMap map; 




	public static class Builder {
		private final String position;
		private final String draw;
		private String odds ="";
		private double oddsAsDouble = 0.0;
		private double totalDistance = 0.0;
		private DrawAnalyzerMap map;

		public Builder(String pos, String draw, DrawAnalyzerMap map){
			this.position = pos;
			this.draw = draw;
			this.map = map;
		}
		
		public Builder odds(String odd){
			odds = odd;
			return this;
		}
		
		public Builder oddsAsDouble(double oad){
			oddsAsDouble = oad;
			return this;
		}
		
		public Builder totalDistance(double td){
			totalDistance = td;
			return this;
		}

		public IndividualResult build() {
			return new IndividualResult(this);
		}
	}



	private IndividualResult(Builder builder) {
		position = builder.position;
		draw = builder.draw;
		totalDistance = builder.totalDistance;
		odds = builder.odds;
		oddsAsDouble = builder.oddsAsDouble;
		map = builder.map;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



	public Integer getDrawAsInteger() {
		// TODO Auto-generated method stub
		return Integer.parseInt(draw);
	}



	public boolean isWinner() {
		// TODO Auto-generated method stub
		return position.equals("1");
	}



	public String getPosition() {
		// TODO Auto-generated method stub
		return position;
	}



	public double getOddsAsDouble() {
		// TODO Auto-generated method stub
		return oddsAsDouble;
	}



	public String getDraw() {
		// TODO Auto-generated method stub
		return draw;
	}



	public double getPoundsBeaten() {
		//System.out.println("In abp td: " + totalDistance + " rd " + map.getDistance() + " lbs " +  totalDistance * 16 / map.getDistance() );
		return totalDistance * 16 / map.getDistance();
	}
	
	public double oddsMinusPoundsBeaten(){
		return oddsAsDouble - getPoundsBeaten();
	}
	
	@Override
	public String toString(){
		return "pos " + position + " dr " + draw + " od " + oddsAsDouble + " dis " + totalDistance;
		
	}

}
