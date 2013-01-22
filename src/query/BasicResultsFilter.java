package query;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import examples.Chris;

public class BasicResultsFilter extends ResultsFilter {
	
	private DistanceFilter distance;
	private Object ground;

	public static class Builder {
		
		private DistanceFilter distance;
		
		public Builder(){
			
		}
		
		public Builder distance(DistanceFilter df){
			this.distance = df;
			return this;
		}

		public BasicResultsFilter build() {
			// TODO Auto-generated method stub
			return new BasicResultsFilter(this);
		}

	}

	public BasicResultsFilter(Builder builder) {
		distance = builder.distance;
	}

	@Override
	public HtmlPage call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSuitableResult(HtmlPage page) {
		String groundandDistanceString;
		Integer distanceAsInteger;
		if (distance != null || ground!= null) {
			groundandDistanceString = getDistanceAndGround(page);
			String[] groundandDistanceArray = groundandDistanceString.split("\\s+");
			if (distance != null) {
				System.out.println("Checking distance");
				distanceAsInteger = convertDistanceStringToInteger(groundandDistanceArray[0]);
				return distance.evaluate(distanceAsInteger);
			}
		}
		return false;
	}
	
	

	public static void main(String[] args) throws IOException{
		//DistanceFilter edf = new EqualDistanceFilter(Integer.valueOf(8));
		//BasicResultsFilter brf = new BasicResultsFilter.Builder().distance(edf).build();
		
		LessThanOrEqualToDistanceFilter ltdf = new LessThanOrEqualToDistanceFilter(Integer.valueOf(9));
		BasicResultsFilter brf2 = new BasicResultsFilter.Builder().distance(ltdf).build();
		
		MoreThanOrEqualToDistanceFilter mtdf = new MoreThanOrEqualToDistanceFilter(Integer.valueOf(7));
		BasicResultsFilter brf = new BasicResultsFilter.Builder().distance(mtdf).build();
		
		DistanceFilter idf = new IntervalDistanceFilter(ltdf,mtdf);
		BasicResultsFilter brf3 = new BasicResultsFilter.Builder().distance(idf).build();
		
		Chris c = new Chris();
		HtmlPage page = c.getPageFromWebClient("http://www.racingpost.com/horses/result_home.sd?race_id=547667&r_date=2012-02-29&popup=yes");
		System.out.println(brf.isSuitableResult(page));
		System.out.println(brf2.isSuitableResult(page));
		System.out.println(brf3.isSuitableResult(page));
	}

}
