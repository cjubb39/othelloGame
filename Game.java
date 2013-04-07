/**
 * The game interface - this will control the Othello game. It will keep track
 * of the "board" and will let players take turns. It will announce moves (by
 * calling the appropriate methods in the Player interface/class).
 */
public interface Game {

	int SIZE = 8;

	/**
	 * This method will initialize the game. At the end of this method, the
	 * board has been set up and the game can be started
	 * 
	 * @param p1
	 *            Player 1
	 * @param p2
	 *            Player 2
	 */
	public void initialize(Player p1, Player p2);

	/**
	 * This is the start point of playing the game. The game will alternate
	 * between the players letting them place tiles. Return null is the game is
	 * a tie.
	 * 
	 * @return Player who won, null otherwise
	 */
	public Player playGame();
}
