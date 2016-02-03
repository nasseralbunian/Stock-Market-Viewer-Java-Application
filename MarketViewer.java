import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import javax.swing.*;

/**
 * MarketViewer.java
 * 
 * creates a stock markets frame to display the content of the navigation panel
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class MarketViewer extends JFrame{

	// creating a NavigationPanel object
	private NavigationPanel navigationPanel;
	
	/**
	 * MarketViewer constructor that sets the frame's title and set
	 * the frame size. A NavigationPanel object is added into the container
	 */
	public MarketViewer(){
		setTitle("Stock Market Viewer");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(dim.width/2, dim.height/2);
		setLocation(new Point(dim.width/4, dim.height/4));
		
		Container contentPane = this.getContentPane();
		
		navigationPanel = new NavigationPanel();
		contentPane.add(navigationPanel,BorderLayout.CENTER);
		
		this.pack();
		this.setResizable(false);
	}
}
