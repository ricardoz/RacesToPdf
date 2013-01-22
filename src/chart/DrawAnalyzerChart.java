/**
 * 
 */
package chart;

import draw.DrawAnalyzerMap;
import draw.Stall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;


/**
 * @author Miriam Martin
 *
 */
public class DrawAnalyzerChart extends ApplicationFrame{

	final private Map<Integer,Stall> map;

	public DrawAnalyzerChart(String title) {
		super(title);
		map = null;
		// TODO Auto-generated constructor stub
	}
	
	public DrawAnalyzerChart(String title, DrawAnalyzerMap map) {
		super(title);
		
		this.map = map.getMap();
		
		final CategoryDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(1500,970));
		setContentPane(chartPanel);	
	}
	
	private CategoryDataset createDataset() {
		// row keys...
		final String series1 = "LSP";
		final String series2 = "lbs";
		final String series3 = "omp";
		

		

		//create the dataset
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for (Stall s : map.values()){
			dataset.addValue(s.levelStakesProfit(), series1, s.getName());
			dataset.addValue(s.averagePoundsBeaten(), series2, s.getName());
			dataset.addValue(s.averageOddsMinusPoundsBeaten(), series3, s.getName());
			System.out.println("adding lsp" + s.levelStakesProfit() + " omp:" + s.averageOddsMinusPoundsBeaten() + " lbs" + s.averagePoundsBeaten());
		}

		

		return dataset;

	}
	
	private JFreeChart createChart(final CategoryDataset dataset) {
		// create the chart
		final JFreeChart chart = ChartFactory.createLineChart(
				"Bar Chart Demo",			// chart title
				"Category",					// domain label
				"Value",					// range label
				dataset,					// data
				PlotOrientation.VERTICAL,	// orientation
				true,						// include legend
				true,						// tooltips
				false						// url's				
				);

		// set background
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only
		final NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines
		//final BarRenderer renderer = (BarRenderer)plot.getRenderer();
		//renderer.setDrawBarOutline(false);

		// set up gradient paint for series
		/*final GradientPaint gp0 = new GradientPaint(
				0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, Color.lightGray
				);
		//final GradientPaint gp1 = new GradientPaint(
				0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, Color.lightGray
				);
		final GradientPaint gp2 = new GradientPaint(
				0.0f, 0.0f, Color.red, 
				0.0f, 0.0f, Color.lightGray
				);
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);*/
		
		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/6.0));

		return chart;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

}
