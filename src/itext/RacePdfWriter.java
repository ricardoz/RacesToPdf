/**
 * 
 */
package itext;

import itext.factories.HorseChunkFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import neo.wrappers.HorseNode;
import neo.wrappers.IndividualResultNode;
import neo.wrappers.RaceNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import traversal.race.ClassDistance;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.MultiColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Miriam Martin
 *
 */
public class RacePdfWriter  {


	private Collection<RaceNode> races;
	
	

	private Document doc;
	
	
	
	private Font font; 
	private Font boldFont; 
	
	private MultiColumnText mct;

	private static Font blueFont;

	private static Font greenFont;

	private static Font orangeFont;
	
	public RacePdfWriter(Collection<RaceNode> races, Document doc) throws DocumentException, IOException{
		this.races = races;
		
		this.doc = doc;
		
		mct = new MultiColumnText();
		mct.addRegularColumns(doc.left(), doc.right(), 7f, 2);
		
		
		
		
		setUpFonts();
		
		
	}
	
	private void setUpFonts() {
		font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 5);
		boldFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 7);
		
		
		orangeFont = new Font(font);
		orangeFont.setColor(BaseColor.ORANGE);
		
		blueFont = new Font(font);
		blueFont.setColor(BaseColor.BLUE);
		
		greenFont = new Font(font);
		greenFont.setColor(BaseColor.GREEN);
		
	}

	public void addRaces() throws DocumentException{
		
		for (RaceNode rn : races){
			addRace(rn);
			
		}
		
		addTopspeedPerformances(85);
		
		try {
			//mct.addElement(chapter);
			doc.add(mct);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			doc.close();
		}
		
	}

	private void addRace(RaceNode raceNode) throws DocumentException{
		
		
		
		
		//s = s.addSection(new Paragraph(raceNode.getName(), font));
		
		mct.addElement(new Paragraph(raceNode.getName() + "  " + raceNode.getRaceDate() + "  " + raceNode.getCourseName(), boldFont));
		
		//s = s.addSection(new Paragraph(raceNode.getCadge(), font));
		
		
		Paragraph p = new Paragraph(raceNode.getCadge(), font);
		
		Collection<IndividualResultNode> runners = raceNode.getRunners();
		
		int heldUp = 9;
		if (allButOneHeldUp(runners, 4)){
			p.add(new Phrase(" " + raceNode.getFieldSize() + " runners"
					, blueFont));
		} else  {
			heldUp = allButOneLeaders(runners, 3);
			if (heldUp <= 1) {
				p.add(new Phrase(" " + raceNode.getFieldSize() + " runners"
						, orangeFont));
			} else {
				p.add(new Phrase(" " + raceNode.getFieldSize() + " runners"
						, font));
			}
		}
			
		
		mct.addElement(p);
		
		
		//s = s.addSection(new Paragraph("\n", font));
		
		mct.addElement(new Paragraph("\n", font));
		
		
			
		
		PdfPTable table = new PdfPTable(5);
		table.setWidths(new float[]{4,4,60,9,11});
		
		for (IndividualResultNode irn : runners){
			table.addCell(new PdfPCell(new Phrase(irn.getPosition(), font)));
			table.addCell(getDrawPhrase(irn));
			
			table.addCell(new PdfPCell(new Phrase(irn.getHorseName() + " " + irn.getFractionalOdds()  + " "
			+ irn.getHorse().getResults().size() + "/" + irn.getHorse().getSubsequentResults(irn).size(), font)));
			table.addCell(new PdfPCell(new Phrase(irn.getDistanceBeatenAsString() + "L", font)));
			table.addCell(new PdfPCell(
					new Phrase(irn.getOfficialRating() + "/" + irn.getTopspeed() + "/" + irn.getRacingPostRating(), font)));
			
			
			
			
			//add comment
			PdfPCell cell = new PdfPCell(new Phrase(irn.getComment(), font));
			cell.setColspan(5);
			
			
			//add subsequent results
			table.addCell(cell);
			try {
				addSubsequentResultsToTable(table, irn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//s.add(table);
		
		mct.addElement(table);
		
		//s = s.addSection(new Paragraph("\n", font));
		
		mct.addElement(new Paragraph("\n", font));
		
		//s = s.addSection(new Paragraph(raceNode.getAnalysis(), font));
		
		mct.addElement(new Paragraph(raceNode.getAnalysis() + "\n\n", font));
		
		//chapter.add(s);
		
		//s = s.addSection(new Paragraph("\n", font));
		
		
	}
	
	private int allButOneLeaders(Collection<IndividualResultNode> runners,
			int i) {
		int heldUp = 0;
		
		for (IndividualResultNode irn : runners){
			try {
				if (Integer.parseInt(irn.getPosition()) <= i + 1){
					String comment = irn.getComment().toLowerCase();
					if (comment.indexOf("held up") < 0 && comment.indexOf("dwelt") < 0 && comment.indexOf("midfield") < 0 && comment.indexOf("rear") < 0){
						
						heldUp ++;
					}		
					
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				
			}
			
		}
		
		//return (heldUp <= 1);
		return heldUp;
	}
	

	private PdfPCell getDrawPhrase(IndividualResultNode irn) {
		int fieldSize = irn.getRace().getFieldSize();
		PdfPCell cell = new PdfPCell(new Phrase ( irn.getDraw() , font));
		
		if ( fieldSize < 8){
			//return new Phrase( irn.getDraw() , font );
			return cell;
		}
		
		if ( fieldSize < 12){
			if (irn.getDrawAsInteger() <= 6){
				//return new Phrase( irn.getDraw() , orangeFont );
				cell.setBackgroundColor(BaseColor.ORANGE);
				return cell;
			} else {
				//return new Phrase( irn.getDraw() , blueFont );
				cell.setBackgroundColor(BaseColor.CYAN);
				return cell;
			}
		}
		
	
			if (irn.getDrawAsInteger() <= 6){
				//return new Phrase( irn.getDraw() , orangeFont );
				cell.setBackgroundColor(BaseColor.ORANGE);
				return cell;
			} if (irn.getDrawAsInteger() > fieldSize - 6) {
				//return new Phrase( irn.getDraw() , blueFont );
				cell.setBackgroundColor(BaseColor.CYAN);
				return cell;
			} else {
				//return new Phrase( irn.getDraw() , greenFont );
				cell.setBackgroundColor(BaseColor.GREEN);
				return cell;
			}
			
		
	}

	private void addSubsequentResultsToTable(PdfPTable table, IndividualResultNode irn) {
		HorseNode horse = irn.getHorse();
		Collection<IndividualResultNode> subsequentRaces = horse.getSubsequentResults(irn);
		
		//StringBuffer sb = new StringBuffer();
		
		Phrase p = new Phrase("", font);
		
		//com.itextpdf.text.List unorderedList = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
		
		
		for (IndividualResultNode sub : subsequentRaces){
			//unorderedList.add(HorseChunkFactory.getIndividualResultSummary(sub, irn));
			p.add(HorseChunkFactory.getIndividualResultSummary(sub, irn));// = 
		}
		
		PdfPCell cell = new PdfPCell();
		cell.setColspan(table.getNumberOfColumns());
		//cell.setFixedHeight(7);
		//cell.setPaddingTop(0f);
		System.out.println(horse + " " + subsequentRaces.size());
		
		cell.addElement(p);
		
		
		
		
		//cell.setPadding(0f);
		table.addCell(cell);
		
	}

	public void writeChapter(Document d){
		
	}
	
	
	/**
	 * @param args
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws DocumentException, IOException {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook(graphDb);
		
		
				
				
		
		Index<Node> index = graphDb.index().forNodes("races");
		
		System.out.println(index.getName());
		System.out.println(index.query("raceId", "?").size());
		System.out.println(index.get("race Id", "*").size());
		

		//TODO: refactor to use RaceClassEnum
		int raceClass = 5;
		int raceDistanceInFurlongs = 5;

		/*
		 * Should implement race query interface with get races method
		 * pass that object to RacePdfWriter?
		 */
		//ClassDistance cd = new ClassDistance( raceClass, raceDistanceInFurlongs);

		//Collection<RaceNode> races = cd.getRaces(graphDb);
		//Collection<RaceNode> races = ClassDistance.getRacesAtDistanceAndCourt(graphDb, raceDistanceInFurlongs, "Catterick");
		
		
		Iterable<Node> allNodes = graphDb.getAllNodes();
		
		Collection<RaceNode> races = new ArrayList<>();
		
		for (Node n : allNodes) {
			if (n.hasProperty("cadge")) {
				try {
					RaceNode rn = new RaceNode(n);
					if (rn.getDistance() == 5 && rn.getCourseName().startsWith("Catt")) {
						races.add(rn);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(n.getPropertyKeys());
					//e.printStackTrace();
				}
			}
		}
		
		/*for (RaceNode rn : races) {
			System.out.println(rn.getCourseName());
			System.out.println(rn.getCadge());
			System.out.println("");
		}*/
		
		//System.exit(0);
		
		
		Document doc = new Document(PageSize.A4, 7, 7, 7, 7);
		
		
		PdfWriter.getInstance(doc,
				new FileOutputStream("Catterick 5f 2011.pdf"));
		
		doc.open();
		
		
		RacePdfWriter r = new RacePdfWriter(races, doc);
		
		r.addRaces();
		
		
		
		doc.close();
		
		
		
		
	}

		private void addTopspeedPerformances(int minimumTopspeed) throws DocumentException {
		List<IndividualResultNode> individualResults = new ArrayList<>();
		
		for (RaceNode rn : races){
			Collection<IndividualResultNode> results = rn.getRunners();
			
			for (IndividualResultNode irn : results){
				if (irn.getTopspeed() >= minimumTopspeed) {
					individualResults.add(irn);
					//System.out.println( " adding: " + irn.getHorseName());
				}
			}
		}
		
		Comparator<IndividualResultNode> tsComparator = 
                new Comparator<IndividualResultNode>() {
public int compare(IndividualResultNode irn1, IndividualResultNode irn2) {
return irn1.getTopspeed() - irn2.getTopspeed();
}
};
			
			
			Collections.sort(individualResults, tsComparator);
			
			mct.nextColumn();
			
			Paragraph p = new Paragraph();
			
			for (IndividualResultNode irn : individualResults){
				p.add(new Phrase (irn.getHorseName() + " " + irn.getTopspeed(), font));
			}
			
			mct.addElement(p);
			
			
		
	}

		private static void registerShutdownHook(final GraphDatabaseService graphDb) {
			// Registers a shutdown hook for the Neo4j instance so that it
			// shuts down nicely when the VM exits (even if you "Ctrl-C" the
			// running example before it's completed)
			Runtime.getRuntime().addShutdownHook( new Thread()
			{
				@Override
				public void run()
				{
					graphDb.shutdown();
				}
			} );

		}
		
		private static boolean allButOneHeldUp(Collection<IndividualResultNode> runners, int i) {
			int heldUp = i+1;
			
			for (IndividualResultNode irn : runners){
				try {
					if (Integer.parseInt(irn.getPosition()) <= i + 1){
						String comment = irn.getComment().toLowerCase();
						if (comment.indexOf("held up") < 0 && comment.indexOf("dwelt") < 0 && comment.indexOf("midfield") < 0 && comment.indexOf("rear") < 0){
							
							heldUp --;
						}		
						
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					
				}
				
			}
			
			return (heldUp >= i);
		}

	}
