/**
 * The Location interface - this is used to specify how the horizontal and
 * vertical coordinates are represented. The top left tile will be (horizontal
 * 0, vertical 0) and the bottom right tile will be (horizontal 7, vertical 7).
 * This can be used to represent the location of a tile placement.
 */
public interface Location {

	/**
	 * Gets the horizontal coordinate
	 * 
	 * @return the horizontal coordinate
	 */
	public int getHorizontal();

	/**
	 * Gets the vertical coordinate
	 * 
	 * @return the vertical coordinate
	 */
	public int getVertical();

}
