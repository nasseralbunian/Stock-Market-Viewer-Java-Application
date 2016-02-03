import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Points.java
 * 
 * This class helps in creating points for stock values. It is invoked when there is a need
 * to create a point such as opening prices, etc. This class depends on <code>ApproximatePoint</code>
 * class to determine points coordinates.
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class Points {
	private List<ApproximatePoint> pointList;	// storing points in an Arraylist of ApproximatePoint type
	private List<Double> xValues;				// storing X values in an Arraylist of double type
	private List<Double> yValues;				// storing Y values in an Arraylist of double type

	/**
	 * Initialize the three objects defined above
	 */
	public Points() {
		this.pointList = new ArrayList<>();
		this.xValues = new ArrayList<>();
		this.yValues = new ArrayList<>();
	}

	/**
	 * adding a point coordinates (approximate point) to the <code>pointList</code> and adding its X coordinate
	 * to the <code>XValues</code> and Y coordinate to the <code>YValues</code>
	 * @param point as <code>ApproximatePoint</code>
	 */
	public void add(ApproximatePoint point) {
		this.pointList.add(point);
		this.xValues.add(point.getX());
		this.yValues.add(point.getY());
	}

	/**
	 * This method is necessary to scale the points to the screen based on the screen
	 * height and width.
	 * @param x0 is the point X coordinate
	 * @param y0 is the point Y coordinate
	 * @param screenWidth is the screen width
	 * @param screenHeight is the screen height
	 * @return the points that are needed to draw the graph
	 */
	public Points scaledToScreen(int x0, int y0, int screenWidth, int screenHeight) {
		Points scaledPoints = new Points();
		for (ApproximatePoint point : this.pointList) {
			Double scaledX = this.scaleMinMax(point.getX(), getMinX(), getMaxX());
			Double scaledY = this.scaleMinMax(point.getY(), getMinY(), getMaxY());

			ApproximatePoint scaledPoint;
			if (scaledY == null) {
				scaledPoint = new ApproximatePoint((x0 + scaledX * screenWidth), null);
			} else {
				scaledPoint = new ApproximatePoint((x0 + scaledX * screenWidth), (y0 - (scaledY * screenHeight)));
			}

			scaledPoints.add(scaledPoint);
		}
		return scaledPoints;
	}
	
	/**
	 * return the scaled value for a coordinate
	 * @param value is the coordinate X or Y
	 * @param min is minimum value in values of X or Y
	 * @param max is maximum value in values of X or Y
	 * @return scaled value
	 */
	private Double scaleMinMax(Double value, Double min, Double max) {
		if (value == null) {
			return null;
		}
		return (value - min) / (max - min);
	}

	/**
	 * get the minimum value in <code>xValues</code> list
	 * @return minimum value
	 */
	public Double getMinX() {
		return this.xValues.stream().reduce(Double.MAX_VALUE, (d1, d2) -> {
			if (d2 == null) {
				return d1;
			}
			return Math.min(d1, d2);
		});
	}

	/**
	 * get the maximum value in <code>xValues</code> list
	 * @return maximum value
	 */
	public Double getMaxX() {
		return this.xValues.stream().reduce((-1) * Double.MAX_VALUE, (d1, d2) -> {
			if (d2 == null) {
				return d1;
			}
			return Math.max(d1, d2);
		});
	}
	
	/**
	 * get the minimum value in <code>yValues</code> list
	 * @return minimum value
	 */
	public Double getMinY() {
		return this.yValues.stream().reduce(Double.MAX_VALUE, (d1, d2) -> {
			if (d2 == null) {
				return d1;
			}
			return Math.min(d1, d2);
		});
	}

	/**
	 * get the maximum value in <code>yValues</code> list
	 * @return maximum value
	 */
	public Double getMaxY() {
		return this.yValues.stream().reduce((-1) * Double.MAX_VALUE, (d1, d2) -> {
			if (d2 == null) {
				return d1;
			}
			return Math.max(d1, d2);
		});
	}

	/**
	 * @return points values as a point list
	 */
	public List<ApproximatePoint> asList() {
		return this.pointList;
	}

	/**
	 * get the point whose index is <code>index</code>
	 * @param index
	 * @return point
	 */
	public ApproximatePoint get(int index) {
		return this.pointList.get(index);
	}

	/**
	 * get the size of point list
	 * @return <code>pointList</code> size
	 */
	public int size() {
		return this.pointList.size();
	}

}
