/**
 * 
 */
package examples;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import query.ResultQueryBuilder;
import query.ResultsFilter;
import query.ResultsQuerySearch;

/**
 * @author Miriam Martin
 *
 */
public class BasicChris implements ResultQueryBuilder, Runnable {
	final private String RESULTS_CARD_ADDRESS = "http://www.racingpost.com/horses2/results/home.sd?r_date=2012-02-26";
	private WebClient webClient;
	private HtmlPage mainWindowPage;
	
	List<String> resultLinks;
	
	public BasicChris() throws IOException{
		initializeWebClient();
		logInToRacingPost();

		this.mainWindowPage = getPageFromWebClient(RESULTS_CARD_ADDRESS);
		 

	}
	
	public BasicChris(String address) throws IOException{
		initializeWebClient();
		logInToRacingPost();

		this.mainWindowPage = getPageFromWebClient(address);
		 

	}
	
	public HtmlPage getPageFromWebClient(String address) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println("retrieving------" + address);
		return (HtmlPage) webClient.getPage(address);
	}

	private void logInToRacingPost() throws IOException {
		// visit Yahoo Mail login page and get the Form object
		HtmlPage page = (HtmlPage) webClient.getPage("https://reg.racingpost.com/modal_dialog/login.sd?protoSecure=0");
		
		//System.out.println(page.asXml());
		
			
		HtmlForm form = page.getFormByName("loginFrm");
			
		
		System.out.println(form.asXml());

		// Enter login and passwd
		form.getInputByName("in_un").setValueAttribute("ricardop");
		form.getInputByName("in_pw").setValueAttribute("randomly");

		// Click "Sign In" button/link
		//page = (HtmlPage) form.getInputByName("lb_login").click();
		HtmlButton submitButton = (HtmlButton)page.createElement("button");
		submitButton.setAttribute("type", "submit");
		form.appendChild(submitButton);
		page = submitButton.click();

	}

	private void initializeWebClient() {
		webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setJavaScriptEnabled(true);
		webClient.setJavaScriptErrorListener(null);
		webClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("page " + page + "\nurl " + url + " " + arg);
			}

		});

	}

	/* (non-Javadoc)
	 * @see query.ResultQueryBuilder#buildSearch(query.ResultsQuerySearch, query.ResultsFilter)
	 */
	@Override
	public void buildSearch(ResultsQuerySearch query, ResultsFilter filter) {
		// TODO Auto-generated method stub

	}
	
	public List<String> findLinks(ResultsQuerySearch query) {
		resultLinks = new ArrayList<>();

		// START PARAMETER ADJUSTMENT

		/**
		 * This is where we ask the query object what we are looking for
		 * - start: a date from before today 
		 * - end: date defaults to today
		 * - interval: must disable start and end
		 * 		4 parts: date(today), from/to*, integer(1), days/weeks/month/year*
		 * - season: for NH disable start/end
		 * - text: at least three-letters probably a different query
		 * - course:
		 * - country:
		 */

		/**
		 * Pass the HtmlForm to the query object
		 */
		query.setFormParameters(mainWindowPage);


		// END PARAMETER ADJUSTMENT

		// START CLICK SUBMIT BUTTON

			List<HtmlButton> buttonList = (List<HtmlButton>) mainWindowPage.getByXPath("//button[@class='btn btnNext btnSearch btnActive btnLight']");
	
			for (HtmlButton button : buttonList){
				try {
					button.click();	
				} catch (Exception e) {
	
					try {
						Thread.sleep(6000);
					} catch(InterruptedException e2) {
						System.out.println("error"); 
					} 
	
				}
			}

		// END CLICK SUBMIT BUTTON

		// START GET LINKS AND ADD TO ARRAYLIST

			DomNode resultsDiv = mainWindowPage.querySelector("div.rsResults");
			//System.out.println(resultsDiv.asXml()); 
			//System.out.println(currentPage.getUrl()); 
	
			Iterable<HtmlElement> resultsList = resultsDiv.getHtmlElementDescendants();
	
	
			for(HtmlElement he : resultsList){
				if (he instanceof HtmlAnchor){
					HtmlAnchor ha = (HtmlAnchor)he;
					System.out.println(ha.asXml()); 
					String badLink = ha.getHrefAttribute();
					int start = badLink.indexOf("/");
					int end = badLink.indexOf("&", start);
	
					resultLinks.add(badLink.substring(start,end));
	
				}
			}

		return resultLinks;


		// END GET LINKS AND ADD TO ARRAYLIST
	}
	
	public ArrayList<HtmlAnchor> findAnchors(ResultsQuerySearch query) {
		ArrayList<HtmlAnchor> resultAnchor = new ArrayList<HtmlAnchor>();

		// START PARAMETER ADJUSTMENT

		/**
		 * This is where we ask the query object what we are looking for
		 * - start: a date from before today 
		 * - end: date defaults to today
		 * - interval: must disable start and end
		 * 		4 parts: date(today), from/to*, integer(1), days/weeks/month/year*
		 * - season: for NH disable start/end
		 * - text: at least three-letters probably a different query
		 * - course:
		 * - country:
		 */

		/**
		 * Pass the HtmlForm to the query object
		 */
		query.setFormParameters(mainWindowPage);


		// END PARAMETER ADJUSTMENT

		// START CLICK SUBMIT BUTTON

			List<HtmlButton> buttonList = (List<HtmlButton>) mainWindowPage.getByXPath("//button[@class='btn btnNext btnSearch btnActive btnLight']");
	
			for (HtmlButton button : buttonList){
				try {
					button.click();	
				} catch (Exception e) {
	
					try {
						Thread.sleep(6000);
					} catch(InterruptedException e2) {
						System.out.println("error"); 
					} 
	
				}
			}

		// END CLICK SUBMIT BUTTON

		// START GET LINKS AND ADD TO ARRAYLIST

			DomNode resultsDiv = mainWindowPage.querySelector("div.rsResults");
			//System.out.println(resultsDiv.asXml()); 
			//System.out.println(currentPage.getUrl()); 
	
			Iterable<HtmlElement> resultsList = resultsDiv.getHtmlElementDescendants();
	
	
			for(HtmlElement he : resultsList){
				if (he instanceof HtmlAnchor){
					HtmlAnchor ha = (HtmlAnchor)he;
					
	
					resultAnchor.add(ha);
	
				}
			}

		return resultAnchor;


		// END GET LINKS AND ADD TO ARRAYLIST
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		
		
	}

	public HtmlPage getMainWindowPage() {
		return mainWindowPage;
	}

	public void resetMainWindowPage() {
		try {
			mainWindowPage = getPageFromWebClient(RESULTS_CARD_ADDRESS);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
