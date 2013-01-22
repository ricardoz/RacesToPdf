/**
 * 
 */
package query;

/**
 * @author Miriam Martin
 *
 */
public class IntervalDistanceFilter extends DistanceFilter {

	

	private MoreThanOrEqualToDistanceFilter moreThan;
	private LessThanOrEqualToDistanceFilter lessThan;

	IntervalDistanceFilter(Integer integer) {
		super(integer);
	}

	public IntervalDistanceFilter(LessThanOrEqualToDistanceFilter ltdf, MoreThanOrEqualToDistanceFilter mtdf) {
		super(null);
		this.moreThan = mtdf;
		this.lessThan = ltdf;
	}

	/* (non-Javadoc)
	 * @see query.DistanceFilter#evaluate(java.lang.Integer)
	 */
	@Override
	public boolean evaluate(Integer i) {
		System.out.println("idf.e-- i " + i + " ev " +  (moreThan.evaluate(i) && lessThan.evaluate(i)) );
		return moreThan.evaluate(i) && lessThan.evaluate(i);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
