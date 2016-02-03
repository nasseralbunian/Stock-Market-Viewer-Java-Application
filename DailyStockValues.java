import java.time.LocalDate;

/**
 * DailyStockValues.java
 * 
 * This class helps in retrieving and parsing the daily stock values 
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class DailyStockValues {
	private LocalDate date;		// storing a date
	private Double opening;		// storing the opening price of that day
	private Double closing;		// storing the closing price of that day
	private Double lowest;		// storing the lowest price in that day
	private Double highest;		// storing the highest price in that day
	private Integer volume;		// storing the volume of stocks on that day

	/**
	 * The constructor accepts the stock information of a single day 
	 * @param date
	 * @param opening
	 * @param closing
	 * @param lowest
	 * @param highest
	 * @param volume
	 */
	public DailyStockValues(LocalDate date, Double opening, Double closing, Double lowest, Double highest, Integer volume) {
		this.date = date;
		this.opening = opening;
		this.closing = closing;
		this.lowest = lowest;
		this.highest = highest;
		this.volume = volume;
	}

	/**
	 * This method will be called by other classes to retrieve the daily stock values as a String
	 */
	public String toString() {
		Object[] args = {this.date.toString(), this.opening, this.closing, this.lowest, this.highest, this.volume};
		return String.format("DailyStockValue(date=%s, opening=%f, closing=%f, lowest=%f, highest=%f, volume=%d)",
				args);
	}

	/**
	 * get the date in which stocks values registered in
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * get the opening price of that day
	 * @return opening price
	 */
	public Double getOpening() {
		return opening;
	}

	/**
	 * get the closing price of that day
	 * @return closing price
	 */
	public Double getClosing() {
		return closing;
	}

	/**
	 * get the lowest price recorded in that day
	 * @return lowest price
	 */
	public Double getLowest() {
		return lowest;
	}

	/**
	 * get the highest price recorded in that day
	 * @return highest price
	 */
	public Double getHighest() {
		return highest;
	}

	/**
	 * get the stocks volume on that day
	 * @return stocks volume
	 */
	public Integer getVolume() {
		return volume;
	}
}
