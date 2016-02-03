import java.awt.*;

/**
 * ApproximatePoint.java
 * 
 * The reason of creating this class is that the standard class <code>java.awt.Point</code> 
 * cannot get Double or null values as a coordinates. In this class, coordinates can be represented
 * as a boxed Double values instead of primitive double and handle a null value as a coordinate
 * 
 * @version 1 28 Dec 2015                                                              
 * @author Nasser Albunian	
*/

public class ApproximatePoint {
	private Double x;	// coordinate X of type Double
	private Double y;   // coordinate Y of type Double

	/**
	 * The constructor accepts two numbers (coordinates) in any type and then pass
	 * these numbers to setX and setY
	 * @param x is the X coordinate
	 * @param y is the Y coordinate
	 */
	public ApproximatePoint(Number x, Number y) {
		setX(x);
		setY(y);
	}

	/**
	 * get the two number as a real point of X and Y as integers
	 * @return point of X and Y
	 */
	public Point getPoint() {
		return new Point((int) Math.round(x), (int) Math.round(y));
	}

	/**
	 * set the number X (any type of numbers) as X coordinate. This method handles the main 
	 * purpose of creating this class which is representing coordinate X as a double or null
	 * if no X number is provided 
	 * @param x is the X coordinate
	 */
	private void setX(Number x) {
		if (x != null) {
			this.x = x.doubleValue();
		} else {
			this.x = null;
		}
	}

	/**
	 * set the number Y (any type of numbers) as Y coordinate. This method handles the main 
	 * purpose of creating this class which is representing coordinate Y as a double or null
	 * if no Y number is provided 
	 * @param y is the Y coordinate
	 */
	private void setY(Number y) {
		if (y != null) {
			this.y = y.doubleValue();
		} else {
			this.y = null;
		}
	}

	/**
	 * get the value of X coordinate
	 * @return x value
	 */
	public Double getX() {
		return x;
	}

	/**
	 * get the value of Y coordinate
	 * @return y value
	 */
	public Double getY() {
		return y;
	}
}
