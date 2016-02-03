import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;

/**
 * Plot.java
 * 
 * In this class, a plot will be drawn and added to a panel. Drawing is based on points
 * are generated in StocksValues class.
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class Plot extends JPanel {
	private Points points;								// creating an object of Points class
	private static final int PREF_W = 800;				// a fixed preferred panel width
	private static final int PREF_H = 650;				// a fixed preferred panel height
	private static final int LEFT_BORDER_GAP = 120;		// specifying the size of the left gap between the panel border and the plot
	private static final int TOP_BORDER_GAP = 50;		// specifying the size of the top gap between the panel border and the plot
	private static final int RIGHT_BORDER_GAP = 50;		// specifying the size of the right gap between the panel border and the plot
	private static final int BOTTOM_BORDER_GAP = 50;	// specifying the size of the bottom gap between the panel border and the plot

	private static final Color GRAPH_COLOR = new Color(58, 255, 134);  // specifying the graph color
	private static final Stroke GRAPH_STROKE = new BasicStroke(3f);	   // specifying the graph stroke

	private static final int X_AXIS_MARKS_COUNT = 12;	// number of intervals (gaps) in X axis between dates
	private static final int Y_AXIS_MARKS_COUNT = 10;   // number of intervals (gaps) in Y axis between prices
	

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d"); // format the date that will appear in X axis
	private static final String Y_HATCH_LABEL_PATTERN = "%.2f"; // the price pattern that will appear in Y axis

	private TreeMap<Integer, LocalDate> xUnitToDateLabelMapping;

	/**
	 * A constructor that sets points values
	 * @param points of stocks values
	 */
	public Plot(Points points) {
		this.points = points;
	}

	/**
	 * A constructor that sets points values and mapping value between each date and its price
	 * @param points of stocks values
	 * @param xLabelsMapping is a value of distance between a date and a stock price
	 */
	public Plot(Points points, TreeMap<Integer, LocalDate> xLabelsMapping) {
		this.points = points;
		this.xUnitToDateLabelMapping = xLabelsMapping;
	}

	/**
	 * draw and paint axes and strokes between points
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawWhiteBackground(g2);
		drawAxes(g2);

		g2.setColor(GRAPH_COLOR);
		g2.setStroke(GRAPH_STROKE);

		List<ApproximatePoint> scaledPointList = this.points.scaledToScreen(getOrigin().getX().intValue(),
				getOrigin().getY().intValue(), getPlotWidth(), getPlotHeight()).asList();

		// to have an opportunity to jump over missing points
		int i = 0;
		while (i < scaledPointList.size() - 1) {
			// if we are in the missing point (i = position), or missing point is next, go to the next position
			if (scaledPointList.get(i).getY() == null || scaledPointList.get(i+1) == null) {
				i += 1;
				continue;
			}
			Point startPoint = scaledPointList.get(i).getPoint();
			Point endPoint = scaledPointList.get(i + 1).getPoint();
			g2.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
			i += 1;
		}
	}

	/**
	 * draw a white background for the plot
	 * @param g2 is the object of the graphics which invokes drawing methods
	 */
	public void drawWhiteBackground(Graphics2D g2) {
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(getOrigin().getPoint().x, TOP_BORDER_GAP, getPlotWidth(), getPlotHeight());
	}

	/**
	 * draw the X and Y axes 
	 * @param g2 is the object of the graphics which invokes drawing methods
	 */
	public void drawAxes(Graphics2D g2) {
		g2.setColor(new Color(0, 0, 0));
		
		// Y-axis, (20, height - 20) bottom-left, to (20, 20) top-left
		Point originPoint = getOrigin().getPoint();
		g2.drawLine(originPoint.x, originPoint.y, originPoint.x, TOP_BORDER_GAP);

		// X-axis, (20, height - 20) bottom-left to (width - 20, height - 20) bottom-right
		g2.drawLine(originPoint.x, originPoint.y,
				originPoint.x + getPlotWidth(), originPoint.y);

		drawXHatches(g2);	// draw X axis dates
		drawYHatches(g2);	// draw Y axis prices labels
	}

	/**
	 * get the original point in which the two axes will start from
	 * @return approximate point of starting point of the plot
	 */
	private ApproximatePoint getOrigin() {
		return new ApproximatePoint(LEFT_BORDER_GAP, getHeight() - BOTTOM_BORDER_GAP);
	}

	/**
	 * get the height of the plot
	 * @return plot height
	 */
	private Integer getPlotHeight() {
		return getHeight() - BOTTOM_BORDER_GAP - TOP_BORDER_GAP;
	}

	/**
	 * get the width of the plot
	 * @return plot width
	 */
	private Integer getPlotWidth() {
		return getWidth() - LEFT_BORDER_GAP - RIGHT_BORDER_GAP;
	}

	/**
	 * check if two points can be close to each other in the axis. This will help in arranging the points
	 * in the axis.
	 * @param currentCoordinate current point coordinate
	 * @param nextCoordinate next point coordinate
	 * @param destinationPoint the last point
	 * @return TRUE if current distance higher than or equals to next distance, FALSE otherwise
	 */
	private boolean canComeCloser(Double currentCoordinate, Double nextCoordinate, Double destinationPoint) {
		Double currentDistance = Math.abs(destinationPoint - currentCoordinate);
		Double nextDistance = Math.abs(destinationPoint - nextCoordinate);
		return currentDistance >= nextDistance;
	}

	/**
	 * get the coordinates of X axis hatches. This method specifies the dates will be 
	 * shown in the axis and the gap between each of them
	 * @return coordinate pairs of each hatch
	 */
	public TreeMap<Double, Integer> getXAxisHatchesCoordinates() {
		// X-s have to be discrete values that has even gap between all of them
		TreeMap<Double, Integer> coordinatePairs = new TreeMap<>();
		Double approximatePixelGapWidth = (double) getPlotWidth() / (X_AXIS_MARKS_COUNT - 1);
		Double discretePointGapWidth = (double) getPlotWidth() / (points.size() - 1);
		Integer hatchNum = 0;
		for (int i = 1; i < points.size(); i++) {
			Double xCoordinate = getOrigin().getX() + i * discretePointGapWidth;

			if (!canComeCloser(xCoordinate, xCoordinate + discretePointGapWidth,
					getOrigin().getX() + hatchNum * approximatePixelGapWidth)) {
				coordinatePairs.put(xCoordinate, points.get(i).getX().intValue());
				hatchNum++;
			}
		}
		return coordinatePairs;
	}

	/**
	 * get the coordinates of Y axis hatches. This method allows re-arranging the hatches
	 * if a user resize the screen from the top or the bottom.
	 * @return coordinates of hatches
	 */
	public TreeMap<Double, Double> getYAxisHatchesCoordinates() {
		Double minY = points.getMinY();
		Double maxY = points.getMaxY();
		Double yRange = maxY - minY;
		Double unitsToPixel = yRange / getPlotHeight();

		TreeMap<Double, Double> coordinatePairs = new TreeMap<>();
		Integer pixelGapWidth = getPlotHeight() / (Y_AXIS_MARKS_COUNT - 1);
		for (int i = 0; i < Y_AXIS_MARKS_COUNT; i++) {
			Double yCoordinate = getOrigin().getY() - i * pixelGapWidth;
			Double label = minY + unitsToPixel * (getOrigin().getY() - yCoordinate);
			coordinatePairs.put(yCoordinate, label);
		}
		return coordinatePairs;
	}

	/**
	 * define constant two axes X and Y 
	 */
	private enum HatchAxis {
		X, Y
	}

	/**
	 * draw the X hatches along with the dates labels based on the coordinate pairs that 
	 * are returned from <code>getXAxisHatchesCoordinates</code>
	 * @param g2 is the object of the graphics which invokes drawing methods
	 */
	public void drawXHatches(Graphics2D g2) {
		TreeMap<Double, Integer> xCoordinatePairs = getXAxisHatchesCoordinates();
		for (Double xCoordinate : xCoordinatePairs.keySet()) {
			int x0 = xCoordinate.intValue();
			int y0 = getOrigin().getY().intValue();
			drawHatch(g2, x0, y0, HatchAxis.X);

			Integer tickNum = xCoordinatePairs.get(xCoordinate);
			String label = DATE_FORMATTER.format(xUnitToDateLabelMapping.get(tickNum));
			FontMetrics fontMetrics = g2.getFontMetrics();
			// the label width and height will help in drawing the String in a proper position
			int labelWidth = fontMetrics.stringWidth(label);
			int labelHeight = fontMetrics.getHeight();

			int xLabelPosition = x0 - (labelWidth / 2);
			int yLabelPosition = y0 + labelHeight;
			g2.drawString(label, xLabelPosition, yLabelPosition);
		}
	}

	/**
	 * draw the Y hatches along with the prices labels based on the coordinate pairs that 
	 * are returned from <code>getYAxisHatchesCoordinates</code>
	 * @param g2 is the object of the graphics which invokes drawing methods
	 */
	public void drawYHatches(Graphics2D g2) {
		TreeMap<Double, Double> yCoordinatePairs = getYAxisHatchesCoordinates();
		for (Double yCoordinate : yCoordinatePairs.keySet()) {
			int x0 = getOrigin().getX().intValue();
			int y0 = yCoordinate.intValue();
			drawHatch(g2, x0, y0, HatchAxis.Y);

			String label = String.format(Y_HATCH_LABEL_PATTERN, yCoordinatePairs.get(yCoordinate));
			FontMetrics fontMetrics = g2.getFontMetrics();
			int labelWidth = fontMetrics.stringWidth(label);

			int xLabelPosition = x0 - labelWidth - 5;
			int yLabelPosition = y0 + (fontMetrics.getHeight() / 2) - 3;
			g2.drawString(label, xLabelPosition, yLabelPosition);

		}
	}

	/*
	 * draw the full hatches. This method depends on X and Y of first point in drawing
	 * that is set in <code>drawXHatches</code> and <code>drawYHatches</code>
	 */
	public void drawHatch(Graphics2D g2, int x0, int y0, HatchAxis axis) {
		int MARK_WIDTH = 12;
		if (axis == HatchAxis.X) {
			// hatch for X-axis
			int x1 = x0;
			int y1 = y0 - MARK_WIDTH;
			g2.drawLine(x0, y0, x1, y1);
		} else {
			// hatch for Y-axis
			int x1 = LEFT_BORDER_GAP + MARK_WIDTH;
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);
		}
	}

	/**
	 * return the preferred size of the panel
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}
}
