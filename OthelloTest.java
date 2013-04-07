/**
 * Test Class for Othello Game
 * 
 * @author Chae Jubb
 * @version 1.0
 */
public class OthelloTest {

	/**
	 * Creates game object and plays it.
	 * 
	 * @param args
	 *            Not used
	 */
	public static void main(String[] args) {
		Othello g = new Othello();
		
		g.initialize(g.p1, g.p2);
		
		g.playGame();

	}

}
