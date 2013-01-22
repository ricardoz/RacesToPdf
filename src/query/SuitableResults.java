/**
 * 
 */
package query;

import java.util.concurrent.Callable;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Miriam Martin
 *
 */
public class SuitableResults implements Callable<HtmlPage> {

	private HtmlPage page;
	final private ResultsFilter filter;

	public SuitableResults(ResultsFilter filter, HtmlPage page) {
		this.filter = filter;
		this.page = page;
	}
	
	public SuitableResults(ResultsFilter filter) {
		this.filter = filter;
	}
	
	public void setPage(HtmlPage p){
		this.page = p;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public HtmlPage call() throws Exception {
		return (filter.isSuitableResult(page)) ? page : null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
