import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.*;

/**
 * StocksWindow.java
 * 
 * creates a stock pane frame to display the stock values charts
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class StocksWindow extends JFrame{

	private StocksConfiguration stocksConfiguration;	// creating a StocksConfiguration object to invoke the configurations
	private StocksValues pane;							// creating a StocksValues object to draw the charts

	/**
	 * The StocksWindow constructor that sets the frame size and adds the 
	 * generated chart to this frame 
	 * @param configuration of stocks that loads and retrieves the stock values
	 */
	public StocksWindow(StocksConfiguration configuration) throws Exception{
		super(configuration.toString());
		this.stocksConfiguration = configuration;
		this.pane = new StocksValues();
		
		pane.generateChart(configuration.getDailyStockValues());
		this.add(pane);

		setTitle("Stock Market Viewer");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(dim.width/2, dim.height/2);
		setLocationRelativeTo(getRootPane());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
