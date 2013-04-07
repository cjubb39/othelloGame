import java.util.Scanner;

/**
 * Human player, allows user to input move manually Implements Player Interface.
 * 
 * @author Chae Jubb
 * @version 1.0
 */
public class HumanPlayer implements Player {

	private Scanner input;
	private int identifier;

	/**
	 * Constructor for HumanPlayer
	 * 
	 * @param ident
	 *            Integer used for color value in individual tiles
	 */
	public HumanPlayer(int ident) {
		input = new Scanner(System.in);
		this.identifier = ident;
	}

	/**
	 * Attempts to Place tile based on user input information
	 * 
	 * @param retry
	 *            Is this a second (or more) attempt for a given turn?
	 * @return Location input by user. This location is not checked for
	 *         correctness here
	 */
	@Override
	public Location placeTile(boolean retry) {

		if (retry == false) {
			try {
				System.out
						.println("Where would you like to place your game piece? ");
				System.out.print("Enter Column: ");
				int hor = Integer.parseInt(input.next());
				System.out.print("Enter Row: ");
				int vert = Integer.parseInt(input.next());

				return new Tile(hor, vert);

			} catch (Exception e) {
				System.out.println("Enter valid integer please");
				return this.placeTile(false);
			}
		} else {
			System.out.println("Previous move illegal.  Try again");

			return this.placeTile(false);
		}
	}

	/**
	 * Informs player of other player's move.
	 * 
	 * @param move
	 *            Location of last move
	 */
	@Override
	public void setResult(Location move) {
		if (move != null) {
			System.out.println("Other player put piece at: "
					+ move.getHorizontal() + ", " + move.getVertical());
		} else {
			System.out.println("Other player unable to move");
		}
	}

	/**
	 * Getter method for player's identifier
	 * 
	 * @return Identifier used as color value in individual tilesF
	 */
	public int getIdentifier() {
		return this.identifier;
	}

}
