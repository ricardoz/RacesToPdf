/**
 * 
 */
package query;


import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Miriam Martin
 *
 */
public interface ResultsQuerySearch {

	/**
	 * The query will be either a map or a builder
	 * for different queries it will adjust the search form
	 * @param mainWindowPage
	 */
	public void setFormParameters(HtmlPage mainWindowPage);

}
