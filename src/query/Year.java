package query;

import org.joda.time.LocalDate;

import course.Course;

public enum Year {
	Y2012(2012),Y2011(2011),Y2010(2010),Y2009(2009),Y2008(2008),Y2007(2007);
	
	private int code;

	private Year(int code) {
        this.code = code;
   }
	
	public int getYear(){
		return code;
	}
	
	public static void main(String[] args) {
		
		
		System.out.println(Year.Y2011.getYear());

	}
}
