/**
 * 
 */
package examples;

import individual.IndividualResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jfree.ui.RefineryUtilities;

import query.BasicResultsFilter;
import query.ResultQueryBuilder;
import query.ResultsFilter;
import query.ResultsQuerySearch;
import query.SuitableResults;
import chart.DrawAnalyzerChart;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

import draw.DrawAnalyzerMap;

/**
 * @author Miriam Martin
 *
 */
public class Chris implements ResultQueryBuilder{

	final private String RESULTS_CARD_ADDRESS = "http://www.racingpost.com/horses2/results/home.sd?r_date=2012-02-26";
	private WebClient webClient;
	private HtmlPage mainWindowPage;
	List<String> resultLinks;
	private ResultsQuerySearch query;
	private ResultsFilter filter;

	public Chris() throws IOException{
		initializeWebClient();
		logInToRacingPost();

		this.mainWindowPage = getPageFromWebClient(RESULTS_CARD_ADDRESS);



	}

	/**
	 * Should be called after the page has been queried.
	 * What do we return if no races have been found?
	 * Do we need to keep the links as a field since it must be re-established
	 * @return a list with the links to races as a String
	 */
	public List<String> findLinks(ResultsQuerySearch query) {
		
		System.out.println("The Results Query Search is " + null);
		
		if (query.equals(this.query)){
			return resultLinks;
		}

		/**
		 * We could add the old query to query history linked list
		 */
		this.query = query;

		resultLinks = new ArrayList<>();

		//get form
		HtmlForm searchForm = mainWindowPage.getFormByName("resultsSearchForm");
		//searchForm.setActionAttribute("http://www.racingpost.com/horses2/results/home.sd?r_date=2012-02-26");

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

		// Enter start date
		searchForm.getInputByName("start_date").setValueAttribute("2011-09-03");

		//Enter course
		HtmlSelect select = (HtmlSelect) mainWindowPage.getElementById("panelResultsSearchCourse");
		HtmlOption option = select.getOptionByValue("104");
		select.setSelectedAttribute(option, true);

		// END PARAMETER ADJUSTMENT

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

		DomNode resultsDiv = mainWindowPage.querySelector("div.rsResults");
		//System.out.println(resultsDiv.asXml()); 
		//System.out.println(currentPage.getUrl()); 

		Iterable<HtmlElement> resultsList = resultsDiv.getHtmlElementDescendants();


		for(HtmlElement he : resultsList){
			if (he instanceof HtmlAnchor){
				HtmlAnchor ha = (HtmlAnchor)he;
				String badLink = ha.getHrefAttribute();
				int start = badLink.indexOf("/");
				int end = badLink.indexOf("&", start);

				resultLinks.add(badLink.substring(start,end));

			}
		}

		return resultLinks;

	}

	public HtmlPage getPageFromWebClient(String address) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println("retrieving------" + address);
		return (HtmlPage) webClient.getPage(address);
	}

	private void logInToRacingPost() throws IOException {
		// visit Yahoo Mail login page and get the Form object
		HtmlPage page = (HtmlPage) webClient.getPage("https://reg.racingpost.com/modal_dialog/login.sd?protoSecure=0");
		HtmlForm form = page.getFormByName("loginForm");

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
				System.out.println("handleRefresh");
			}

		});

	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException, ExecutionException {

		Chris c = new Chris();
		List<String> l = c.findLinks(null);

		System.out.println("size------" + l.size());

		List<HtmlPage> h = new ArrayList<>();



		HtmlPage page = null;

		// START FILTER
		/**
		 * It looks like filter takes a web page and check some conditions
		 * ground
		 * distance
		 * handicap/group/maidne/claimer/seller/listed/stakes/maiden4or5/auction
		 */

		/**
		 * would like to break this up into callables
		 * 
		 */

		h = c.filter(l);
		for(String s : l){
			boolean isMile = false;
			try {
				page = c.getPageFromWebClient("http://www.racingpost.com" + s);

			} catch (Exception e) {

				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

			/**
			 * go into filter here
			 */

			DomNode node = page.querySelector("h3");

			String name = node.asText();

			DomNodeList<DomNode> nodeList = page.querySelectorAll("li");
			for (DomNode dm : nodeList){
				if (dm.asText().indexOf("1m") > 0) isMile = true; 
			}

			if (name.indexOf("Handi") > 0 && isMile){
				//System.out.println(name + "------" + distance);
				h.add(page);
			} 

		}

		// END FILTER

		System.out.println("size------" + h.size());

		DrawAnalyzerMap daList = new DrawAnalyzerMap(8);

		ExecutorService pool = Executors.newFixedThreadPool(3);
		Set<Future<List<IndividualResult>>> set = new HashSet<>();
		for(HtmlPage p : h){
			System.out.println("final------" + p.getTitleText());
			Callable<List<IndividualResult>> callable = new PricePoundResult(p, 8, daList);
			Future<List<IndividualResult>> future = pool.submit(callable);
			set.add(future);		
		}

		System.out.println("da list final------");
		//special list

		for (Future<List<IndividualResult>> f : set){
			daList.add(f.get());
		}

		daList.compute();

		DrawAnalyzerChart daChart = new DrawAnalyzerChart("Yarmouth", daList);
		daChart.pack();
		RefineryUtilities.centerFrameOnScreen(daChart);
		daChart.setVisible(true);
	}

	private List<HtmlPage> filter(List<String> l) throws InterruptedException, ExecutionException {
		filter = new BasicResultsFilter.Builder().build();

		HtmlPage page = null;

		List<HtmlPage> h = new ArrayList<HtmlPage>();

		ExecutorService pool = Executors.newFixedThreadPool(4);
		Set<Future<HtmlPage>> set = new HashSet<>();


		for(String s : l){
			boolean isMile = false;
			try {
				page = getPageFromWebClient("http://www.racingpost.com" + s);

			} catch (Exception e) {

				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}


			Callable<HtmlPage> c = new SuitableResults(filter, page);
			Future<HtmlPage> future = pool.submit(c);
			set.add(future);

			DomNode node = page.querySelector("h3");

			String name = node.asText();

			DomNodeList<DomNode> nodeList = page.querySelectorAll("li");
			for (DomNode dm : nodeList){
				if (dm.asText().indexOf("1m") > 0) isMile = true; 
			}

			if (name.indexOf("Handi") > 0 && isMile){
				//System.out.println(name + "------" + distance);
				h.add(page);
			} 

		}


		for (Future<HtmlPage> f : set){
			if (f.get() != null) h.add(f.get());
		}
		return h;

	}



	@Override
	public void buildSearch(ResultsQuerySearch query, ResultsFilter filter) {
		this.query = query;
		this.filter = filter;

		List<String> list = findLinks();
		
		query = null;

		List<HtmlPage> h =  filter (list, filter);

		filter = null;

		// START DRAW ANALYZER

			DrawAnalyzerMap daList = new DrawAnalyzerMap(8);
	
			ExecutorService pool = Executors.newFixedThreadPool(3);
			Set<Future<List<IndividualResult>>> set = new HashSet<>();
			for(HtmlPage p : h){
				System.out.println("final------" + p.getTitleText());
				Callable<List<IndividualResult>> callable = new PricePoundResult(p, 8, daList);
				Future<List<IndividualResult>> future = pool.submit(callable);
				set.add(future);		
			}
	
			System.out.println("da list final------");
			//special list
	
			for (Future<List<IndividualResult>> f : set){
				try {
					daList.add(f.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
			daList.compute();

		// END DRAW ANALYZER

		// START DRAW ANALYZER CHART

			DrawAnalyzerChart daChart = new DrawAnalyzerChart("Yarmouth", daList);
			daChart.pack();
			RefineryUtilities.centerFrameOnScreen(daChart);
			daChart.setVisible(true);

		// END DRAW ANALYZER CHART


	}

	private List<HtmlPage> filter(List<String> l, ResultsFilter filter2) {
		List<HtmlPage> h =  new ArrayList<>();

		HtmlPage page = null;

		ExecutorService pool = Executors.newFixedThreadPool(4);
		Set<Future<HtmlPage>> set = new HashSet<>();


		// START GETTING PAGES FROM LINKS

		for(String s : l){
			
			try {
				page = getPageFromWebClient("http://www.racingpost.com" + s);

			} catch (Exception e) {

			}

		// END GETTING PAGES FROM LINKS


		// STARD ADDING CALLABLE/FUTURE TO POOL/SET

			Callable<HtmlPage> c = new SuitableResults(filter, page);
			Future<HtmlPage> future = pool.submit(c);
			set.add(future);

		// END ADDING CALLABLE/FUTURE TO POOL/SET


		}
		
		l = null;

		// START ADDING NON NULL PAGES TO ARRAYLIST

		for (Future<HtmlPage> f : set){
			HtmlPage p = null;
			try {
				p = f.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			f = null;
			if (p != null) h.add(p);
		}
		
		set = null;
		

		// END ADDING NON NULL PAGES TO ARRAYLIST

		return h;
	}

	private List<String> findLinks() {
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
			
			int i = webClient.waitForBackgroundJavaScript(1000);
			
			while (i > 2)
	        {
	            i = webClient.waitForBackgroundJavaScript(1000);

	            if (i == 2)
	            {
	                break;
	            }
	            synchronized (mainWindowPage) 
	            {
	                System.out.println("threads " + i);
	                try {
						mainWindowPage.wait(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }

			resultsDiv = mainWindowPage.querySelector("div.rsResults");
			Iterable<HtmlElement> resultsList = null;
			try {
				resultsList = resultsDiv.getHtmlElementDescendants();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(mainWindowPage.asXml());
				System.exit(i);
			}
	
	
			for(HtmlElement he : resultsList){
				if (he instanceof HtmlAnchor){
					HtmlAnchor ha = (HtmlAnchor)he;
					String badLink = ha.getHrefAttribute();
					int start = badLink.indexOf("/");
					int end = badLink.indexOf("&", start);
	
					resultLinks.add(badLink.substring(start,end));
	
				}
			}

		return resultLinks;


		// END GET LINKS AND ADD TO ARRAYLIST
	}

	public void setMainWindowPage(String string) {
		try {
			mainWindowPage = getPageFromWebClient(string);
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
