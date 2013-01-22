/**
 * 
 */
package itext.factories;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;

import neo.enums.RaceClassNode;
import neo.wrappers.IndividualResultNode;
import neo.wrappers.RaceNode;

/**
 * @author Miriam Martin
 *
 */
public class HorseChunkFactory {
	
	private static Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 5);
	private static Font boldFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 5);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static Phrase getIndividualResultSummary(IndividualResultNode subsequentResult, IndividualResultNode currentResult) {
		
		// add cadge 
		String cadge = subsequentResult.getRace().getCadge();
		Chunk cadgeChunk = null;
		
		RaceNode raceSub = subsequentResult.getRace();
		RaceNode raceCur = currentResult.getRace();
		
		RaceClassNode classSub = raceSub.getRaceClass();
		RaceClassNode classCur = raceCur.getRaceClass();
		
		
		try {
			if (classSub.compareTo(classCur) > 0){
				if (subsequentResult.isWinner()){
					Font newFont =  new Font(boldFont);
					newFont.setColor(BaseColor.RED);
					cadgeChunk = new Chunk(cadge, newFont);
					
				} else {
					cadgeChunk = new Chunk(cadge, boldFont);
				}
				
			} else {
				if (subsequentResult.isWinner()){
					Font newFont =  new Font(font);
					newFont.setColor(BaseColor.RED);
					cadgeChunk = new Chunk(cadge, newFont);
				} else {
				cadgeChunk = new Chunk(cadge, font);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			cadgeChunk = new Chunk(cadge, font);
		}
		
		Phrase p = new Phrase(cadgeChunk);
		
		
		String position = " " + subsequentResult.getPosition();
		String fieldSize = "" + subsequentResult.getRace().getFieldSize();
		String positionFieldSize = position + "/" + fieldSize;
		
		p.add(new Chunk ( positionFieldSize, font));
		
		p.add(getSpaceChunk(font));
		
		p.add(new Chunk ( subsequentResult.getDistanceBeatenAsString() , font));
		
		p.add(getSpaceChunk(font));
		
		// START ADD OFFICIAL RATING
		
		int or = subsequentResult.getOfficialRating();
		
		
		
		Chunk orChunk;
		
		if (or > currentResult.getOfficialRating()){
			orChunk = new Chunk("" + or, boldFont);
		} else {
			orChunk = new Chunk("" + or, font);
		}
		
		p.add(orChunk);
		
		// END ADD OFFICIAL RATING
		
		// START ADD TOPSPEED
		
		int ts = subsequentResult.getTopspeed();
		
		p.add(getSeparatorChunk(font));
		
		Chunk tsChunk;
		
		if (ts > currentResult.getTopspeed()){
			tsChunk = new Chunk("" + ts, boldFont);
		} else {
			tsChunk = new Chunk("" + ts, font);
		}
		
		p.add(tsChunk);
		
		// END ADD TOPSPEED
		
		// START ADD RPR
		
		int rpr = subsequentResult.getRacingPostRating();
		
		p.add(getSeparatorChunk(font));
		
		Chunk rprChunk;
		
		if (rpr > currentResult.getRacingPostRating()){
			rprChunk = new Chunk("" + rpr, boldFont);
		} else {
			rprChunk = new Chunk("" + rpr, font);
		}
		
		p.add(rprChunk);
		
		// END ADD RPR
		
		p.add(getNewLineChunk(font));
		
		return p;
	}

	private static Chunk getNewLineChunk(Font font2) {
		return new Chunk ("\n", font2);
	}

	private static Chunk getSpaceChunk(Font font2) {
		// TODO Auto-generated method stub
		return new Chunk (" ", font2);
	}
	
	private static Chunk getSeparatorChunk(Font font2) {
		// TODO Auto-generated method stub
		return new Chunk ("/", font2);
	}

}
