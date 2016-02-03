import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * StocksConfiguration.java
 * 
 * performs all the required operations to configure the stocks.
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class StocksConfiguration {

	/**
	 * The StocksConfiguration constructor invokes dates validation operation, check
	 * connection, and load the daily stock values
	 * @param ticker the ticker name
	 * @param from the start date of daily prices
	 * @param to the end date of daily prices
	 * @throws Exception in invalid dates, no Internet connection, or data not available
	 */
	public StocksConfiguration(String ticker, LocalDate from, LocalDate to) throws Exception{
		this.ticker = ticker;
		this.fromDate = from;
		this.toDate = to;
		
		stockValueList = new ArrayList<>();

		if (!this.isValidDates()) {
			throw new Exception("issue with dates range");
		}
		
		this.checkConnection();
		this.load();
	}

	/**
	 * check if end date is not prior to start date 
	 * @return TRUE if valid & FALSE if not valid
	 */
	public boolean isValidDates() {
		return this.fromDate.isBefore(this.toDate);
	}

	/**
	 * get the URL that consists of the ticker and the two dates (start, end)
	 * @return Google Finance URL with the ticker and the dates
	 */
	public String getURLString() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		String dateStrings[] = {ticker, fromDate.format(formatter), toDate.format(formatter)};

		return String.format(URL_PATTERN, dateStrings);
	}
	
	/**
	 * check the Internet connection by requesting a HTTP URL connection and checking
	 * if the request is established or not
	 * @throws Exception if there is no Internet connection
	 */
	public void checkConnection() throws Exception{
		try {
			URL url = new URL(this.getURLString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			InputStream stream = connection.getInputStream();
			this.reader = new BufferedReader(new InputStreamReader(stream));
		} catch (Exception e) {
			throw new Exception("issue with Internet connection");
		}
	}

	/**
	 * load the daily stock values from Google Finance and store them in an array list
	 * @return a list of daily stock values
	 * @throws Exception if values not available
	 */
	public List<DailyStockValues> load() throws Exception{
		boolean firstLine = true;
		String line = null;
		while ((line = this.reader.readLine()) != null) {
			if (firstLine) {
				firstLine = false;
				continue;
			}

			String[] parts = line.split(",");

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uu");
			LocalDate date = LocalDate.parse(parts[0], formatter);

			Double opening = this.parseDouble(parts[1]);
			Double closing = this.parseDouble(parts[4]);
			Double lowest = this.parseDouble(parts[3]);
			Double highest = this.parseDouble(parts[2]);
			Integer volume = this.parseInteger(parts[5]);

			DailyStockValues stockValue = new DailyStockValues(date, opening, closing, lowest, highest, volume);
			stockValueList.add(stockValue);
		}

		if (stockValueList.isEmpty()) {
			throw new Exception("Data not avaialbe.");
		}
		return stockValueList;
	}
	
	/**
	 * get the ticker of a company
	 * @return company ticker
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * get the start date of the daily prices
	 * @return from date
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * get the end date of the daily prices
	 * @return to date
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * parse the string to double. This will be used in reading stocks values (opening,..)
	 * @param s the stock value as String (open, close, etc)
	 * @return stock value as double
	 */
	public Double parseDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * parse the string to integer. This will be used in reading stocks volume
	 * @param s the stocks volume as String
	 * @return stocks volume as integer
	 */
	public Integer parseInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * get the daily stock values that are stored as an array list
	 * @return list of daily stock values
	 */
	public List<DailyStockValues> getDailyStockValues(){
		return stockValueList;
	}
	
	/**
	 * adjust the URL format according to the String parameters of <code>String.format</code>
	 */
	public String toString() {
		return String.format("%s: (%s) to (%s)", new String[]{
				ticker,
				fromDate.toString(),
				toDate.toString()
		});
	}
	
	private String ticker;							   // storing the ticker name as String
	private LocalDate fromDate;						   // storing the start date as a LocalDate type
	private LocalDate toDate;						   // storing the end date as a LocalDate type
	private List<DailyStockValues> stockValueList;     // storing the stock values in a list of DailyStockValues type
	private BufferedReader reader;					   // creating a reader object to read from the website
	public static String DATE_PATTERN = "MMM+d+uuuu";  // creating a date pattern to be used in URL
	public static String URL_PATTERN = "http://www.google.com/finance/historical" +
			"?q=%s&histperiod=daily&startdate=%s&enddate=%s&output=csv";
													   // creating a URL pattern to be called
}
