/**
 * The Player interface Each player will get to choose where to place their
 * tiles
 */
public interface Player {

	/**
	 * This method will place a tile on the grid. This method should guarantee
	 * correctness of location (no overlaps, must be in a line with another
	 * piece)
	 * 
	 * @param retry
	 *            if an earlier call to this method returned an invalid
	 *            position, this method will be called again with retry set to
	 *            true.
	 * @return The Location of the tile to place
	 */
	public Location placeTile(boolean retry);

	/**
	 * This method will notify the Player of the result of the previous move
	 * 
	 * @param move
	 *            Locations of piece that the other player flipped
	 */
	public void setResult(Location move);

}
