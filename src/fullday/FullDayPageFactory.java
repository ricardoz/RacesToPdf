package fullday;

import java.net.URL;

import org.joda.time.LocalDate;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FullDayPageFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	public static FullDayPage getFullDayPage(HtmlPage raceDayPage) {
		
		
		URL url = raceDayPage.getUrl();
		WebResponse wr = raceDayPage.getWebResponse();
		WebWindow window = raceDayPage.getEnclosingWindow();
		
		//check if there is a date in the Url
		String address = url.toString();
		
		
		LocalDate now = new LocalDate();
		
		LocalDate date;
		
		//Get final index of '='
		int finalIndexOfEqualsSign = address.lastIndexOf('=');
		
		if (finalIndexOfEqualsSign < 0) {
			return new FullDayMeetingPage(url, wr, window, now);
		} else {
			date = localDateFromRacingPostUrl(address, finalIndexOfEqualsSign );
		}
		
		if (date.compareTo(now) < 0){
			return new FullDayResultsPage(url, wr, window, date);
		} else {
			return new FullDayMeetingPage(url, wr, window, date);
		}
		
		
	}
	
	private static LocalDate localDateFromRacingPostUrl(String address, int finalIndexOfEqualsSign)  {
		
			
		
		String dateString = address.substring(finalIndexOfEqualsSign + 1);
		
		String[] tokens = dateString.split("-");
		
		int year = Integer.valueOf(tokens[0]);
		int month = Integer.valueOf(tokens[1]);
		int day = Integer.valueOf(tokens[2]);
		
		LocalDate date = new LocalDate(year,month,day);
		
		
		
		return date;
	}

	

	

}
