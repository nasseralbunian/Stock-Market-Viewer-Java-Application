import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.*;
import java.util.*;

/**
 * DatesPanel.java
 * 
 * This class creates a panel for dates that will be invoked to create 
 * a start and end dates 
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class DatesPanel extends JPanel{
	
	// create three comboboxes for year, month, and day
	private JComboBox<String> yearComboBox, monthComboBox, dayComboBox;

	/**
	 * The constructor defines the items of each combo box and 
	 * add it to the panel
	 * @param title of the group border (either start date or end date)
	 * @param defaultDate of either start date or enda date
	 */
	public DatesPanel(String title, LocalDate defaultDate) {
		super();
		this.setBorder(BorderFactory.createTitledBorder(title));
		
		this.yearComboBox = new JComboBox<>(YEARS);
		this.yearComboBox.setSelectedItem(Integer.toString(defaultDate.getYear()));
		this.add(yearComboBox);

		this.monthComboBox = new JComboBox<>(MONTHS);
		this.monthComboBox.setSelectedItem(defaultDate.getMonth().toString());
		this.add(monthComboBox);

		this.dayComboBox = new JComboBox<>(DAYS);
		this.dayComboBox.setSelectedItem(Integer.toString(defaultDate.getDayOfMonth()));
		this.add(dayComboBox);
	}

	/**
	 * return the values of year, month, and day combo boxes as a selected date
	 * @return date as year, month, and day
	 */
	public LocalDate getSelectedDate() {
		int year = Integer.parseInt((String) this.yearComboBox.getSelectedItem());
		Month month = Month.valueOf((String) this.monthComboBox.getSelectedItem());
		int day = Integer.parseInt((String) this.dayComboBox.getSelectedItem());
		return LocalDate.of(year, month, day);
	}
	
	/**
	 * generate the years from 2000 to 2017 and store them as constants in a <code>YEARS array</code> 
	 * @return years as array
	 */
	private static String[] years(){
		ArrayList<String> years = new ArrayList<>();
		for (int i = 2000; i <= 2017; i++) {
			years.add(Integer.toString(i));
		}
		// Object[] can't be casted to String[] directly.
		return years.toArray(new String[years.size()]);
	}
	
	/**
	 * generate the 12 months from <code>Month enum<code> and store them as constants in a <code>MONTHS array</code> 
	 * @return months as array  
	 */
	private static String[] months(){
		ArrayList<String> months = new ArrayList<>();
		for (Month month: Month.values()) {
			months.add(month.toString());
		}
		return months.toArray(new String[months.size()]);
	}

	/**
	 * generate the 31 days and store them as constants in a <code>DAYS array</code> 
	 * @return days as array  
	 */
	private static String[] days() {
		ArrayList<String> days = new ArrayList<>();
		for (int i = 1; i <= 31; i++) {
			days.add(Integer.toString(i));
		}
		// Object[] can't be casted to String[] directly.
		return days.toArray(new String[days.size()]);
	}
	
	public static final String[] YEARS = years();	 // storing years as constants String array
	public static final String[] MONTHS = months();	 // storing months as constants String array
	public static final String[] DAYS = days();		 // storing days as constants String array
}
