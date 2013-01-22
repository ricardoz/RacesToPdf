package examples;

import individual.IndividualResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import draw.DrawAnalyzerMap;

public class PricePoundResult implements Callable<List<IndividualResult>> {

	private HtmlPage page;
	private double distanceOfRace;
	private DrawAnalyzerMap map;
	

	public PricePoundResult(HtmlPage p, int distance, DrawAnalyzerMap daList) {
		this.page =p;
		this.distanceOfRace = distance;
		map = daList;
		
		
	}
	
	/**
	 * For now just the number of runners
	 */
	public int findRaceInfo(){
		/**
		 * could be number of runners
		 */
		DomNode domNode = page.querySelector("div.raceInfo");
		String s = domNode.asText();
		//System.out.println(s);
		String[] tokens = s.split("\\s+");
		//System.out.println("runners: " + tokens[0]);
		return Integer.parseInt(tokens[0]);
	}
	
	
	
	/**
	 * race class
	 * age restriction
	 * raceDistance
	 */
	public String findRaceClassAgeString(){
		List<DomNode> listElementList = page.querySelectorAll("li");
		Iterator<DomNode> listElementListIterator = listElementList.iterator();
		String cadge = null;
		
		while (listElementListIterator.hasNext()){
			cadge = listElementListIterator.next().asText();
			if (cadge.startsWith("(")) {
				return cadge;
				//System.out.println("ca: " + classAge);
			}
			
		}
		
		return cadge;
	}

	private List<IndividualResult> findTableData() {
		
		int findRaceInfo = findRaceInfo();
		List<IndividualResult> results = new ArrayList<>(findRaceInfo);
		
		
		List<DomNode> distanceList = page.querySelectorAll("td.dstDesc");
		Iterator<DomNode> distanceListIterator = distanceList.iterator();
		
		
		HtmlTable node = (HtmlTable)page.querySelector("table");
		List<HtmlTableRow> list = node.getRows();
		double totalDistance = 0;
		for (int i=2; i <list.size();i+=5){
			HtmlTableRow row = list.get(i);
			List<HtmlTableCell> data = row.getCells();
			//System.out.println("\nNew Row ");
			/**String[] stringArray = row.asText().split("\\s+");
			for(int j = 0; j < stringArray.length; j++){
				System.out.print("--" + j+":" + stringArray[j]);
				
				
			}
			int position = Integer.valueOf(stringArray[1]);
			int draw = Integer.valueOf(stringArray[2]);
			System.out.println(" ");	*/
			
			for (HtmlTableCell cell : data){
				System.out.print("-;-"  + cell.asText());
			}
			
			//int position = Integer.valueOf(data.get(1).asText());
			//int draw = Integer.valueOf(data.get(2).asText());
			String pd = data.get(1).asText();
			
			String[] positionDraw = pd.split("\\s+");
			String draw = positionDraw[1];
			String position = positionDraw[0];
			String distance = distanceListIterator.next().asText();
			double dis = convertDistanceStringToDouble(distance);
			
			totalDistance += dis;
			
			double poundsBeaten =  Math.round(convertLengthsBeatenToPounds(totalDistance));
			
			
			String oddsHorse = data.get(3).asText();
			String[] oddsHorseArray = oddsHorse.split("\\s+");
			String odds = oddsHorseArray[oddsHorseArray.length - 1];
			double oddsAsDouble = convertOddsStringToDouble(odds);
			
			
			//System.out.println("pd " + positionDraw + " position: " + position + " draw: " + draw );
			//System.out.println("oH: " + oddsHorse + " odds: " + odds + " oad: " + oddsAsDouble);
			//System.out.println("sd: " + distance + " distance: " + dis + " total: " + totalDistance + " lbs: " + poundsBeaten);
			
			results.add(new IndividualResult.Builder(position, draw, map).odds(odds).oddsAsDouble(oddsAsDouble)
					.totalDistance(totalDistance).build());
		}
		
		return results;
	}

	private double convertLengthsBeatenToPounds(double dis) {
		
		return (dis * 16 / distanceOfRace) ;
	}

	private double convertOddsStringToDouble(String odds) {
		
		//System.out.println("trying:--" + odds);
		
		odds = odds.replace("J", "");
		
		if (Character.isDigit(odds.charAt(0))){
			String[] frac = odds.split("/");
			return Double.parseDouble(frac[0]) / Double.parseDouble(frac[1]) + 1.0;
	
		} else {
			return 2.0;
		}
	}

	private double convertDistanceStringToDouble(String distance) {
		
		if (distance.length() == 0) return 0;
		
		//System.out.println("trying:--" + distance);
		int sumLast = 0 + distance.charAt(distance.length()-1);
		//first the numbers
		if (Character.isDigit(distance.charAt(0))){
			
			//is the last character not a fraction
			
			
			if (sumLast < 187){
				// return as double
				return Double.parseDouble(distance);
			} else {
				
				double whole = Double.parseDouble(distance.substring(0,distance.length()-1));
				
				double fraction = 0.3333333;
				
				if (sumLast == 188 ){
					fraction = 0.25;
				} else if (sumLast == 189 ){
					fraction = 0.5;
				} else if (sumLast == 190 ){
					fraction = 0.75;
				}
				
				return whole + fraction;
				
				
			}
			
		} else if (sumLast > 187){
			double fraction = 0.3333333;
			
			if (sumLast == 188 ){
				fraction = 0.25;
			} else if (sumLast == 189 ){
				fraction = 0.5;
			} else if (sumLast == 190 ){
				fraction = 0.75;
			}
			
			return fraction;
		} else {
			
			char c = distance.charAt(1);
			
			if (c =='s') return 0.05;
			if (c =='h') return 0.1;
			if (c =='d') return 0.15;
			if (c =='k') return 0.2;
		}
		return 0.6666666;
	}

	@Override
	public List<IndividualResult> call() throws Exception {
		
		return findTableData();
	}
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		// Create and initialize WebClient object
	    WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
	    webClient.setThrowExceptionOnScriptError(false);
	    webClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("handleRefresh");
			}

	    });

	    // visit Yahoo Mail login page and get the Form object
	    HtmlPage page = (HtmlPage) webClient.getPage("https://reg.racingpost.com/modal_dialog/login.sd?protoSecure=0");
	    HtmlForm form = page.getFormByName("loginFrm");

	    // Enter login and passwd
	    form.getInputByName("in_un").setValueAttribute("ricardop");
	    form.getInputByName("in_pw").setValueAttribute("randomly");

	    // Click "Sign In" button/link
	    //page = (HtmlPage) form.getInputByName("lb_login").click();
	    HtmlButton submitButton = (HtmlButton)page.createElement("button");
	    submitButton.setAttribute("type", "submit");
	    form.appendChild(submitButton);
	    page = submitButton.click();
	    
	    page = (HtmlPage) webClient.getPage(
	    		"http://www.racingpost.com/horses/result_home.sd?race_id=533000&r_date=2011-06-15&popup=yes#results_top_tabs=re_&results_bottom_tabs=NOTEBOOK"); 
	    
	    PricePoundResult ppr = new PricePoundResult(page, 8, null);
	    ppr.foo();
	    
	    
	    
	    
	    
	    
	    
	    /*String ageClassDistanceGround = ppr.findRaceClassAgeString();
	    
	    System.out.println(ageClassDistanceGround );
	    
	    int start = 1;
		int end = ageClassDistanceGround.indexOf(")");
		String raceClass = ageClassDistanceGround.substring(start, end );
		
		System.out.println(raceClass );
		
		
		start = ageClassDistanceGround.indexOf("(", end) + 1;
		end = ageClassDistanceGround.indexOf(")", start);
		String raceDistance = ageClassDistanceGround.substring( start, end ).trim();
		
		String remaining = ageClassDistanceGround.substring(end + 1).trim();
		
		System.out.println(raceDistance );
		System.out.println(remaining  );*/
	}
	
	public void foo(){
		List<DomNode> distanceList = page.querySelectorAll("td.dstDesc");
		Iterator<DomNode> distanceListIterator = distanceList.iterator();
		
		List<DomNode> commentList = page.querySelectorAll("div.commentText");
		Iterator<DomNode> commentListIterator = commentList.iterator();
		
		/*while(commentListIterator.hasNext()){
			System.out.println("cli" +commentListIterator.next());
		}*/
		
		
		HtmlTable node = (HtmlTable)page.querySelector("table");
		List<HtmlTableRow> list2 = node.getRows();
		
		List<HtmlTableRow> list = new ArrayList<>();
		
		double totalDistance = 0;
		for (int z=2; z <list2.size();z+=5){
			//System.out.println("char " + list2.get(z).asText().charAt(2));
			//System.out.println(z + " text " + list2.get(z).asText());
			
				//System.out.println("size " + list2.get(z).asText());
				list.add(list2.get(z));
			
			//System.out.println("size " + list.size());
		}
			
		for (HtmlTableRow row : list){
			 
			List<HtmlTableCell> data = row.getCells();
			//System.out.println("\nNew Row ");
		
			
			
			
			
			String pd = data.get(1).asText();
			
			String[] positionDraw = pd.split("\\s+");
			String draw = positionDraw[1];
			String position = positionDraw[0];
			
			
			
			String distance = distanceListIterator.next().asText();
			
			double individualDistanceAsDouble = convertDistanceStringToDouble(distance);
			
			totalDistance += individualDistanceAsDouble;
			
			
			
			double poundsBeaten =  Math.round(convertLengthsBeatenToPounds(totalDistance));
			
			String comment = commentListIterator.next().asXml();
			
			
			String oddsHorse = data.get(3).asText();
			String[] oddsHorseArray = oddsHorse.split("\\s+");
			String odds = oddsHorseArray[oddsHorseArray.length - 1];
			StringBuffer horseBuffer = new StringBuffer(oddsHorseArray[0]);
			for (int h = 1; h < oddsHorseArray.length - 1; h++){
				horseBuffer.append(oddsHorseArray[h]);
			}
			String horse = horseBuffer .toString();
			double oddsAsDouble = convertOddsStringToDouble(odds);
			
			int age = Integer.parseInt(data.get(4).asText());
			String weight = data.get(5).asText().trim();


			
			String trainer = data.get(6).asText();
			int or =Integer.parseInt( data.get(7).asText());
			int ts =Integer.parseInt( data.get(8).asText());
			int rpr =Integer.parseInt( data.get(9).asText());
			
			String horseXml = data.get(3).asXml();
					
			String horseId = getIdFromXml(horseXml);
			
			String trainerXml = data.get(6).asXml();
			
			String trainerId = getIdFromXml(trainerXml);
			
			
			
			/**
			 * position and draw data.get(1).asText();
			 * horse and odds and horse url data.get(3).asText();
			 * distance = distanceListIterator.next().asText();
			 * age = Integer.parseInt(data.get(4).asText());
			 * weight = data.get(5).asText().trim();
			 * 
			 * 
			 */
			
			/*System.out.println("pd " + positionDraw + " position: " + position + " draw: " + draw );
			System.out.println("oH: " + horse + " odds: " + odds + " oad: " + oddsAsDouble);
			System.out.println("sd: " + distance + " distance: " + individualDistanceAsDouble + " total: " + totalDistance + " lbs: " + poundsBeaten);
			System.out.println("age: " + age + " weight: " + weight + " trainer: " + trainer);
			System.out.println("or: " + or + " ts: " + ts + " rpr: " + rpr);
			System.out.println("trainer: " + trainerId + " horseId " + horseId);
			System.out.println("comment: " + comment);
			System.out.println("comment: " + comment);*/
			
			
		}
		
		System.out.println("\n\n" + page.getElementById("results_bottom_tabs").asText());
	}

	private String getIdFromXml(String horseXml) {
		
		int questionMark = horseXml.indexOf("?");
		int start = horseXml.indexOf("=", questionMark) + 1;
		int end = horseXml.indexOf("\"", start);
		return horseXml.substring(start,end);
	}
	
	public List<HtmlTableRow> getIndividualResultTabeleRows(){
		
		
		
		HtmlTable node = (HtmlTable)page.querySelector("table");
		List<HtmlTableRow> everyTableRow = node.getRows();
		
		List<HtmlTableRow> individualResultTableRows = new ArrayList<>();
		
		
		for (int z=2; z <everyTableRow.size();z+=5){
			//System.out.println("char " + list2.get(z).asText().charAt(2));
			//System.out.println(z + " text " + list2.get(z).asText());
			
				//System.out.println("size " + list2.get(z).asText());
				individualResultTableRows.add(everyTableRow.get(z));
			
			//System.out.println("size " + list.size());
		}
		
		return individualResultTableRows;
	}
	
public List<HtmlTableRow> getJockeyTabeleRows(){
		
		
		
		HtmlTable node = (HtmlTable)page.querySelector("table");
		List<HtmlTableRow> everyTableRow = node.getRows();
		
		List<HtmlTableRow> jockeyTableRows = new ArrayList<>();
		
		
		for (int z=3; z <everyTableRow.size();z+=5){
			
				jockeyTableRows.add(everyTableRow.get(z));
			
			
		}
		
		return jockeyTableRows;
	}
	
	public Iterator<DomNode> getDistanceIterator(){
		List<DomNode> distanceList = page.querySelectorAll("td.dstDesc");
		return distanceList.iterator();
	}
	
	public Iterator<DomNode> getcommentIterator(){
		List<DomNode> commentList = page.querySelectorAll("div.commentText");
		return commentList.iterator();
	}
	
	public String getAnalysis(){
		return page.getElementById("results_bottom_tabs").asText();
	}

}
