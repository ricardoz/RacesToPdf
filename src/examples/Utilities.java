/**
 * 
 */
package examples;

/**
 * @author Miriam Martin
 *
 */
public class Utilities {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static int getMonthStartAsDayOfYear(String month) throws Exception{
		month = month.toLowerCase();
		if (month.indexOf("jan") > 0) return 0;
		if (month.indexOf("feb") > 0) return 31;
		if (month.indexOf("mar") > 0) return 59;
		if (month.indexOf("apr") > 0) return 90;
		if (month.indexOf("may") > 0) return 120;
		if (month.indexOf("jun") > 0) return 151;
		if (month.indexOf("jul") > 0) return 181;
		if (month.indexOf("aug") > 0) return 212;
		if (month.indexOf("sep") > 0) return 243;
		if (month.indexOf("oct") > 0) return 273;
		if (month.indexOf("nov") > 0) return 304;
		if (month.indexOf("dec") > 0) return 334;
		
		
		//return 400;
		throw new Exception();
	}

}
