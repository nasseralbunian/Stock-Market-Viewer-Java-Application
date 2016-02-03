import javax.swing.*;

/**
 * MarketGUI.java
 * 
 * creates a stock markets viewer to display the main window to the user
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class MarketGUI {
	
	/**
	 * The main method creates a MarketViewer object to display the
	 * main window that contains tickers, start date, and end date
	 */
	public static void main(String[] args) {
		MarketViewer marketViewer = new MarketViewer();
		marketViewer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		marketViewer.setVisible(true);
	}
}