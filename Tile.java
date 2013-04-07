/**
 * Tile object. Each position on gameboard. Has horizontal and vertical
 * coordinates as well as color value
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class Tile implements Location, java.io.Serializable {

	private int horizontal, vertical;
	private int color; // correspond to player number

	/**
	 * Constructor: Creates tile with supplied coordinates and default color 0
	 * 
	 * @param x
	 *            Horizontal coordinate of tile to be created
	 * @param y
	 *            Vertical coordinate fo tile to be created
	 */
	public Tile(int x, int y) {
		this.horizontal = x;
		this.vertical = y;
		this.color = 0;
	}

	/**
	 * Setter for color value of tile
	 * 
	 * @param newColor
	 *            New color value
	 */
	public void setColor(int newColor) {
		this.color = newColor;
	}

	/**
	 * Getter for color value of tile
	 * 
	 * @return current color value of tile
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * Getter for horizontal coordinate of object
	 * 
	 * @return Horizontal coordinate
	 */
	@Override
	public int getHorizontal() {
		// TODO Auto-generated method stub
		return this.horizontal;
	}

	/**
	 * Getter for vertical coordinate of object
	 * 
	 * @return Vertical coordinate
	 */
	@Override
	public int getVertical() {
		// TODO Auto-generated method stub
		return this.vertical;
	}

	public String toString() {
		return Integer.toString(this.horizontal)
				+ Integer.toString(this.vertical)
				+ Integer.toString(this.color);
	}

}
