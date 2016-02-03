import java.awt.*;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;

/**
 * NavigationPanel.java
 * 
 * creates a navigation panel to display the tickers, start date, end date and
 * retrieve button.
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class NavigationPanel extends JPanel implements ActionListener{

	private TickerPanel tickerPanel;	  // creating a TickerPanel object
	private DatesPanel  startDatesPanel;  // creating a DatesPanel object for start date
	private DatesPanel  endDatesPanel;    // creating a DatesPanel object for end date
	private JButton     retrieveBtn;      // creating a button (retrieve button)
	private JLabel      applicationLbl;   // creating a label that shows the application name
	private JLabel      instructionLbl;   // creating a label that shows the instructions

	
	/**
	 * The NavigationPanel constructor that adds the tickers, start date, end date,
	 * retrieve button, and the two labels into the panel. The layout of this panel
	 * is a GroupLayout that aligns the components into vertical and horizontal groups.
	 */
	public NavigationPanel(){
		super();

		tickerPanel = new TickerPanel("Ticker:", "MSFT");
		this.add(tickerPanel);

		startDatesPanel = new DatesPanel("Start date:", LocalDate.of(2015, Month.JANUARY, 1));
		this.add(startDatesPanel);

		endDatesPanel = new DatesPanel("End date:", LocalDate.of(2015, Month.NOVEMBER, 30));
		this.add(endDatesPanel);

		retrieveBtn = new JButton("Retrieve");
		retrieveBtn.addActionListener(this);
		this.add(retrieveBtn);

		applicationLbl = new JLabel();
		applicationLbl.setFont(new Font("Felix Titling", 1, 24));
		applicationLbl.setText("Stock Market Viewer");
		this.add(applicationLbl);

		instructionLbl = new JLabel();
		instructionLbl.setFont(new Font("Times New Roman", 0, 18));
		instructionLbl.setText("Choose the ticker, start date and end date");
		this.add(instructionLbl);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup()
										.addGap(21, 21, 21)
										.addComponent(tickerPanel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(startDatesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
								.addGroup(layout.createSequentialGroup()
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(endDatesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
						.addGap(31, 31, 31))
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(applicationLbl)
						.addGap(72, 72, 72))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGap(101, 101, 101)
										.addComponent(instructionLbl))
								.addGroup(layout.createSequentialGroup()
										.addGap(142, 142, 142)
										.addComponent(retrieveBtn, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(81, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(19, 19, 19)
						.addComponent(applicationLbl)
						.addGap(18, 18, 18)
						.addComponent(instructionLbl)
						.addGap(64, 64, 64)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(tickerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startDatesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(9, 9, 9)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(endDatesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
						.addComponent(retrieveBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addGap(20, 20, 20))
				);
	}
	
	/**
	 * <code>actionPerformed</code> method is invoked when the retrieve button is pressed to create
	 * a new stocks configuration and a window that shows the stocks chart
	 */
	public void actionPerformed(ActionEvent e) {
		StocksConfiguration config = null;
		try {
			config = new StocksConfiguration(this.tickerPanel.getSelectedTicker(),
					this.startDatesPanel.getSelectedDate(), this.endDatesPanel.getSelectedDate());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, "No data was returned due to: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			// spawn new window contained plot tabs
			new StocksWindow(config);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, "No data was returned due to: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
