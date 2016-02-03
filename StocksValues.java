import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.Comparator;

/**
 * StocksValues.java
 * 
 * This class is responsible of generating stock charts, handling the stock values points that
 * are required in drawing the plot, and generating a tab for each chart
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class StocksValues extends JTabbedPane{

	/**
	 * The StocksValues constructor
	 */
	public StocksValues() {
		super();
		unitToDateMapping = new TreeMap<>();
	}
	
	/**
	 * generate a chart for each stock values (opening, closing, highest, lowest, and volume)
	 * @param stockValueList that are used to generate stock values points to be drawn in the chart
	 */
	public void generateChart(List<DailyStockValues> stockValueList) {
		this.stockValueList = stockValueList;
		cleanTabs();
		
		// generate chart points for each stock values
		Points openingValues = new Points();
		Points closingValues = new Points();
		Points lowestValues = new Points();
		Points highestValues = new Points();
		Points volumes = new Points();
		
		// data arrive in last to first order, need to reverse order
		this.stockValueList.sort(new Comparator<DailyStockValues>() {
			@Override
			public int compare(DailyStockValues o1, DailyStockValues o2) {
				if (o1.getDate().isBefore(o2.getDate())) {
					return -1;
				} else if (o1.getDate().isEqual(o2.getDate())) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		
		// represent coordinates as a boxed Double values instead of primitive double 
		for (int i = 0; i < this.stockValueList.size(); i++) {
			DailyStockValues stockValue = this.stockValueList.get(i);
			unitToDateMapping.put(i, stockValue.getDate());
			openingValues.add(new ApproximatePoint(i, stockValue.getOpening()));
			closingValues.add(new ApproximatePoint(i, stockValue.getClosing()));
			lowestValues.add(new ApproximatePoint(i, stockValue.getLowest()));
			highestValues.add(new ApproximatePoint(i, stockValue.getHighest()));
			volumes.add(new ApproximatePoint(i, stockValue.getVolume()));
		}
		
		// create an opening plot and add it to its tab
		this.openingPlot = new Plot(openingValues, this.unitToDateMapping);
		this.addTab("Opening", this.openingPlot);

		// create a closing plot and add it to its tab
		this.closingPlot = new Plot(closingValues, this.unitToDateMapping);
		this.addTab("Closing", this.closingPlot);

		// create a lowest price plot and add it to its tab
		this.lowestPricePlot = new Plot(lowestValues, this.unitToDateMapping);
		this.addTab("Lowest", this.lowestPricePlot);

		// create a highest price plot and add it to its tab
		this.highestPricePlot = new Plot(highestValues, this.unitToDateMapping);
		this.addTab("Highest", this.highestPricePlot);

		// create a volume plot and add it to its tab
		this.volumePlot = new Plot(volumes, this.unitToDateMapping);
		this.addTab("Volumes", this.volumePlot);
	}
	
	/**
	 * remove the last drawn chart in each tab. This is needed in case of drawing a new chart
	 */
	private void cleanTabs() {
		this.remove(this.openingPlot);
		this.remove(this.closingPlot);
		this.remove(this.lowestPricePlot);
		this.remove(this.highestPricePlot);
		this.remove(this.volumePlot);
	}
	
	/**
	 * The following instance variables are:
	 * 		Storing the stock values in a list of DailyStockValues type.
	 * 		Creating a plot object for each stock values
	 * 		Creating a tree map object to store an approximate point between a date and its stock value
	 */
	private List<DailyStockValues> stockValueList;
	private Plot openingPlot, closingPlot, lowestPricePlot, highestPricePlot, volumePlot;
	private TreeMap<Integer, LocalDate> unitToDateMapping;
}
