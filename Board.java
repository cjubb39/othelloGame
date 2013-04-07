import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Aggregates Tiles into playing board.
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class Board implements java.io.Serializable {

	private Tile[][] tiles;
	private int horizontal, vertical;

	/**
	 * Constructor: Creates board of supplied dimensions out of Tile Objects
	 * 
	 * @param horizontal
	 *            Width of Board
	 * @param vertical
	 *            Height of Board
	 */
	public Board(int horizontal, int vertical) {
		this.horizontal = horizontal;
		this.vertical = vertical;

		this.tiles = new Tile[this.horizontal][this.vertical];

		for (int i = 0; i < horizontal; i++) {
			for (int j = 0; j < vertical; j++) {
				this.tiles[i][j] = new Tile(i, j);
			}
		}
	}

	/**
	 * Set up board for initial play, with 4 pieces in appropriate starting
	 * positions on 8 x 8 board
	 */
	public void setUpBoard() {
		this.tiles[3][3].setColor(1);
		this.tiles[4][4].setColor(1);
		this.tiles[3][4].setColor(2);
		this.tiles[4][3].setColor(2);
	}

	/**
	 * Access Tile object by coordinate
	 * 
	 * @param horizontal
	 *            Horizontal coordinate of tile to be accessed
	 * @param vertical
	 *            Vertical coordinate of tile to be accessed
	 * @return Tile object accessed
	 */
	public Tile getTile(int horizontal, int vertical) {
		return this.tiles[horizontal][vertical];
	}

	/**
	 * Setter of individual tile color value
	 * 
	 * @param horizontal
	 *            Horizontal coordinate of tile to be altered
	 * @param vertical
	 *            Vertical coordinate of tile to be altered
	 * @param identifier
	 *            New color value of accessed tile
	 */
	public void setTileColor(int horizontal, int vertical, int identifier) {
		this.tiles[horizontal][vertical].setColor(identifier);
	}

	/**
	 * Represents Board object as a String, with X, O, blanks for board
	 * positions
	 * 
	 * @return Stringified Board object
	 */
	public String toString() {
		String response = "  ";
		int color;

		// header row
		for (int i = 0; i < this.horizontal; i++) {
			response += (i + " ");
		}

		response += "\n";

		for (int i = 0; i < this.vertical; i++) {
			response += (i + " ");

			for (int j = 0; j < this.horizontal; j++) {
				color = this.tiles[j][i].getColor();

				switch (color) {

				case 0:
					response += "  ";
					break;

				case 1:
					response += "X ";
					break;

				case 2:
					response += "O ";
					break;

				default:
					System.out.println("Error in board coloring");
					break;
				}

			}
			response += "\n";
		}

		return response;
	}

	public static Board copy(Board orig) {
		Board obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bos.toByteArray()));
			obj = (Board) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return obj;
	}

}
