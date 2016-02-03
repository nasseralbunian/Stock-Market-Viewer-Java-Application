import javax.swing.*;
import java.awt.*;
import java.lang.invoke.SwitchPoint;

/**
 * TickerPanel.java
 * 
 * This class creates a panel for tickers to show it in the <code>NavigationPanel</code>
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class TickerPanel extends JPanel{
	
	// creating tickers as constants in an array
	public static final String[] SUPPORTED_TICKERS = {"MSFT", "GOOG", "AAPL", "ANCX"};
	private JComboBox<String> tickerComboBox;	// creating a combo box to show tickers

	/**
	 * The constructor defines the items of tickers combo box, set a default value and 
	 * add it to the panel
	 * @param title is the title of the group box border
	 * @param defaultValue is the default value of the tickers
	 */
	public TickerPanel(String title, String defaultValue) {
		super();
		this.setBorder(BorderFactory.createTitledBorder(title));
		
		this.tickerComboBox = new JComboBox<>(SUPPORTED_TICKERS);
		this.tickerComboBox.setSelectedItem(defaultValue);
		
		this.add(tickerComboBox);
	}

	/**
	 * get the selected ticker by a user
	 * @return ticker symbol
	 */
	public String getSelectedTicker() {
		return (String) tickerComboBox.getSelectedItem();
	}
}
