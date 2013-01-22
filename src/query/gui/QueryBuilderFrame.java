/**
 * 
 */
package query.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.joda.time.LocalDate;

import query.BasicResultsFilter;
import query.BasicResultsQuerySearch;
import query.ResultQueryBuilder;
import query.ResultsFilter;
import query.ResultsQuerySearch;
import examples.Chris;

/**
 * @author Miriam Martin
 *
 */
public class QueryBuilderFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QueryCoursePanel coursePanel;
	private QueryDistancePanel distancePanel;
	private ResultQueryBuilder rqb;

	/**
	 * @throws HeadlessException
	 */
	public QueryBuilderFrame() throws HeadlessException {
		super("Query Frame");
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		coursePanel = getQueryCoursePanel();
		distancePanel = getQueryDistancePanel();
		Container container = getContentPane();
		container.add( coursePanel , BorderLayout.NORTH );
		container.add( distancePanel , BorderLayout.CENTER );
		
		JPanel panel = new JPanel();
		JButton submit = new JButton("submit");
		submit.addActionListener(this);
		panel.add(submit);
		
		JButton cancel = new JButton("submit");
		//cancel.addActionListener(this);
		panel.add(cancel);
		
		container.add( panel, BorderLayout.SOUTH );
		
		
		
	}
	
	public void addResultsQueryBuilder(ResultQueryBuilder rqb){
		this.rqb = rqb;
	}

	private QueryDistancePanel getQueryDistancePanel() {
		// TODO Auto-generated method stub
		return new QueryDistancePanel(this);
	}
	
	private QueryCoursePanel getQueryCoursePanel(){
		return new QueryCoursePanel(this);
	}

	/**
	 * @param arg0
	 */
	public QueryBuilderFrame(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public QueryBuilderFrame(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public QueryBuilderFrame(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
	        //Create and set up the window.
	        QueryBuilderFrame frame = new QueryBuilderFrame();
	        frame.addResultsQueryBuilder(new Chris());
	        frame.setSize(400, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("qp.ap:--" + e.getActionCommand());
		System.out.println("qp.ap:--" + coursePanel.getSelectedCourse());
		System.out.println("qp.ap:--" + distancePanel.getSelectedCDistance());
		
		/**
		 * Query needs start / end / Course
		 * Work with default year for now
		 */
		ResultsQuerySearch query = new BasicResultsQuerySearch.Builder().
				changeStartDate(new LocalDate().minusDays(364)).
				changeCourse(coursePanel.getSelectedCourse()).build();
		
		/**
		 * Filter needs distance
		 */
		ResultsFilter filter = new BasicResultsFilter.Builder().distance(distancePanel.getSelectedCDistance()).build();
		
		rqb.buildSearch(query, filter);
		
	}

}
