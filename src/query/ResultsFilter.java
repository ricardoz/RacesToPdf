package query;

import java.util.concurrent.Callable;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public abstract class ResultsFilter implements Callable<HtmlPage> {

	abstract boolean isSuitableResult(HtmlPage page);
	
	/**
	 * This is a method
	 * @param page
	 * @return
	 */
	String getDistanceAndGround(HtmlPage page) {
		
		DomNode node = page.querySelector("h3");

		String name = node.asText();
		
		System.out.println("name--" + name);

		DomNodeList<DomNode> nodeList = page.querySelectorAll("li");
		for (DomNode dm : nodeList){
			System.out.println("df.gd--" + dm.asText() );
			if (dm.asText().startsWith("(")){
				return dm.asText().substring(dm.asText().lastIndexOf(")") + 1, dm.asText().length()).trim();
			}
		}
		return null;
	}
	
	/**
	 * also a method
	 * @param s
	 * @return
	 */
	Integer convertDistanceStringToInteger(String s) {
		System.out.println("rf.cdsti--" + s );
		Integer i;
		
		if(s.length() == 2){
			if (s.endsWith("f")){
				i = Integer.parseInt(s.substring(0, 1));
			} else {
				i = Integer.parseInt(s.substring(0, 1)) * Integer.valueOf(8);
			}
		} else {
			int sum = 0;
			sum += 8 * Integer.parseInt(s.substring(0, 1));
			sum += Integer.parseInt(s.substring(2, 3));
			i = sum;
		}
		
		System.out.println("rf.cdsti--" + s + " gives " + i );
		
		return i;
	}

}
