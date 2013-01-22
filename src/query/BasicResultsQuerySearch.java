/**
 * 
 */
package query;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import course.Country;
import course.Course;

/**
 * @author Miriam Martin
 *
 */
public class BasicResultsQuerySearch implements ResultsQuerySearch {

	private LocalDate start;
	private LocalDate end;
	private Country country;
	private Course course;
	private boolean endIsToday;

	public BasicResultsQuerySearch(Builder builder) {
		this.start = builder.start;
		this.end = builder.end;

		if (end.equals(LocalDate.now())){
			endIsToday = true;
		}
		this.country = builder.country;
		this.course = builder.course;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicResultsQuerySearch b = new BasicResultsQuerySearch.Builder().build();



		System.out.println(b);
		BasicResultsQuerySearch b3 = new BasicResultsQuerySearch.Builder()
		.changeStartDate(new LocalDate(2012,2,1))
		.changeCourse(new Course("Yarmouth")).build();
		System.out.println(b3);
		BasicResultsQuerySearch b2 = new BasicResultsQuerySearch.Builder().changeStartDate(new LocalDate(2005,9,1)).build();
		System.out.println(b2);

	}

	public static class Builder{

		private LocalDate start;
		private LocalDate end;
		private Course course;
		private Country country;

		public Builder(){
			end = new LocalDate();
			start = end.minusWeeks(1);
			course = null;
			country = null;
		}
		
		public Builder(Year y){
			end = new LocalDate(y.getYear(), 12, 31);
			start = new LocalDate(y.getYear(), 1, 1);
			course = null;
			country = null;
		}

		public Builder changeStartDate(LocalDate l){
			start = l;
			return this;
		}

		public Builder changeEndDate(LocalDate l){
			end = l;
			return this;
		}

		public Builder changeCourse(Course c){
			course = c;
			return this;
		}

		public Builder changeCountry(Country c){
			country = c;
			return this;
		}

		public BasicResultsQuerySearch build(){
			if (start.isAfter(end)){
				throw new IllegalArgumentException("Start date is after end date");
			}
			System.out.println(Days.daysBetween(start,end).getDays());
			if (Days.daysBetween(start,end).getDays() > maximumSearchLimit()){
				throw new IllegalArgumentException(maximumSearchLimit() + "This class can only be used for queries of 7 days w/o a course or 365 with one " + course 
						+"\n" + start + " \n " + end);
			}
			return new BasicResultsQuerySearch(this);
		}

		private int maximumSearchLimit() {
			return course == null ? 7 : 365;
		}

	}

	@Override
	public String toString(){
		return "Start: " + start + " End: " + end + " Country: " + country + " Course: " + course + " Today: " + endIsToday;
	}

	@Override
	public void setFormParameters(HtmlPage mainWindowPage) {
		//System.out.println(mainWindowPage.asXml());
		HtmlForm searchForm = mainWindowPage.getFormByName("resultsSearchForm");

		if (!endIsToday){
			// Enter end date
			searchForm.getInputByName("end_date").setValueAttribute(end.toString());
		}

		// Enter start date
		searchForm.getInputByName("start_date").setValueAttribute(start.toString());

		//Adjust Course
		if (course!= null){
			try {
				//Enter course
				HtmlSelect select = (HtmlSelect) mainWindowPage
						.getElementById("panelResultsSearchCourse");
				HtmlOption option = select.getOptionByValue(course.getId()
						.toString());
				select.setSelectedAttribute(option, true);
			} catch (Exception e) {
				System.out.println("Failed adding course to search form");
				System.out.println("tried value " + course.getId()
						.toString() );
				//System.out.println(mainWindowPage.asXml());
			}
		}

	}

}
