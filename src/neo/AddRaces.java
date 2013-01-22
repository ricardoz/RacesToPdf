/**
 * 
 */
package neo;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import neo.wrappers.CourseNode;
import neo.wrappers.RaceNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import query.BasicResultsQuerySearch;
import query.ResultsQuerySearch;
import query.Year;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;

import course.Course;
import examples.BasicChris;

/**
 * @author Miriam Martin
 *
 */
public class AddRaces {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		//String line = bufferedReader.readLine();

		// START GET COURSE NODES
		/**
		 * Get each course node
		 */
		//System.out.println("Course index: 0 ");
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		registerShutdownHook( graphDb );
		//Index<Node> courseIndex = graphDb.index().forNodes( "course" );

		Iterable<Node> nodes = graphDb.getAllNodes();

		// END GET COURSE NODES


		// START CONVERT TO COURSE NODES

		/**
		 * convert to CourseNode
		 */
		ArrayList<CourseNode> courses = new ArrayList<>(62);
		for (Node n : nodes){
			if (n.hasProperty("courseId")){
				courses.add(new CourseNode(n));
			}

		}

		// END CONVERT TO COURSE NODES

		int cour = 0;
		
		for (CourseNode c : courses) {
			cour++;
			System.out.println("Do you want to add races for " + c.getCourse() + "? races = " + cour);
		}
		// START BULID QUERY
		BasicChris chris = new BasicChris();
		for (CourseNode c : courses) {
			// count the races
			int races = 0;
			Course course = c.getCourse();


			System.out.println("Do you want to add races for " + course + "?");
			
			String input = bufferedReader.readLine();
			
			System.out.println(input);

			if (!course.isAllWeather() && input.equals("y")) {


				cour++;

				if (cour >= 0) {

					List<HtmlAnchor> links = chris
							.findAnchors(buildQuery(course));
					chris.resetMainWindowPage();

					for (HtmlAnchor anchor : links) {
						races++;

						String anchorAsText = anchor.asText();

						/**
						 * check if it end in hurdle/chase
						 */
						if (isFlatRace(anchorAsText)) {

							String raceId = getLinkId(anchor);

							/**
							 * remember to trim
							 * 0 - date
							 * 1 - time
							 * 3 - race name
							 */
							String anchorCourseNameReplaced = anchorAsText
									.replace(course.getName(), ",");

							String[] tokens = anchorCourseNameReplaced
									.split(",");

							String raceDate = null;
							String raceTime = null;
							String raceName = null;
							try {
								raceDate = tokens[0].trim();
								raceTime = tokens[1].trim();
								raceName = tokens[2].trim();
							} catch (Exception e) {
								System.out.println("replaced "
										+ anchorCourseNameReplaced);

								for (int i = 0; i < tokens.length; i++) {

									System.out
									.println("token " + tokens[i]);
								}

								e.printStackTrace();

								System.exit(0);
							}

							// START RACE NODE TRANSACTION

							Transaction tx = graphDb.beginTx();
							try {
								/**
								 * Create new RaceNode
								 */
								Node raceNode = graphDb.createNode();
								//raceNode.setProperty("raceId", raceId);

								/*
								 * Convert to wrapper to add properties
								 */
								RaceNode raceWrapper = new RaceNode(
										raceNode);
								raceWrapper.setRaceIdProperty(graphDb, raceId);
								raceWrapper.setRaceTimeProperty(graphDb, raceTime);
								raceWrapper.setRaceNameProperty(graphDb, raceName);
								raceWrapper.setRaceDateProperty(graphDb, raceDate);
								c.addRace(raceWrapper);

								tx.success();
							} finally {
								tx.finish();
							}

							// END RACE NODE TRANSACTION
						} else {

						}

						//all++;
						//System.out.println(races + ": " +course + " s " +s + " .s " + s.substring(s.indexOf('=') + 1));

					}
				}

			}
			System.out.println(cour + "." + races  + ": " +course );

		}

		// END BUILD QUERY
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

	private static boolean isFlatRace(String anchorAsText) {
		// ends with )
		if (anchorAsText.endsWith(")")){
			if (anchorAsText.indexOf("Hurdle") >0){

				System.out.println("error hurdle " + anchorAsText);
				return false;
			}

			if (anchorAsText.indexOf("Chase") >0){
				System.out.println("error chase " + anchorAsText);
				return false;
			}

			if (anchorAsText.indexOf("NH Flat") >0){
				System.out.println("error nhflat " + anchorAsText);
				return false;
			}

			if (anchorAsText.indexOf("National Hunt") >0){
				System.out.println("error nhflat " + anchorAsText);
				return false;
			}
		}

		// if it doesn't end with hurdle or chase 
		// may let a bumber or two slip through
		if (!anchorAsText.endsWith("Hurdle") && !anchorAsText.endsWith("Chase"))return true;



		return false;
	}

	private static String getLinkId(HtmlAnchor anchor) {
		String badLink = anchor.getHrefAttribute();
		int start = badLink.indexOf("/");
		int end = badLink.indexOf("&", start);
		String s = badLink.substring(start,end);

		String raceId = s.substring(s.indexOf('=') + 1);

		return raceId;
	}

	private static ResultsQuerySearch buildQuery(Course course){
		Year y2011 = Year.Y2011;
		ResultsQuerySearch query = new BasicResultsQuerySearch.Builder(
				y2011).changeCourse(course).build();
		return query;

	}

}
