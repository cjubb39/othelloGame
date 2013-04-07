import java.util.Scanner;

/**
 * Othello game controller. Allows for Human v Human or Human v Computer
 * gameplay
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class Othello implements Game {

	// instance variables
	private Scanner input;
	protected Player p1;
	protected Player p2;
	private Board board;

	/**
	 * Constructor: Assigns scanner object to Othello object
	 */
	public Othello() {
		input = new Scanner(System.in);
	}

	/**
	 * Plays game. Initialized game and continues, alternating turns until the
	 * game ends when no more valid moves are available
	 * 
	 * @return Winning player. Null in case of tie.
	 */
	public Player playGame() {

		//this.initialize(this.p1, this.p2);

		Location attempt;
		int currentPlayer = 1;

		// repeat until game ends (method will return)
		while (true) {

			if (this.moveAvailable(currentPlayer)) {

				System.out.println(this.board.toString());
				System.out.println("Player " + currentPlayer + ": Your Turn");

				if (currentPlayer == 1) {
					attempt = p1.placeTile(false);
				} else {
					attempt = p2.placeTile(false);
				}

				// repeat until valid move supplied
				boolean retry = false;
				do {
					if (this.isValid(attempt, currentPlayer)) {
						this.executeMove(attempt, currentPlayer);

						if (currentPlayer == 1) {
							p2.setResult(attempt);
						} else {
							p1.setResult(attempt);
						}

						retry = false;

					} else {

						retry = true;
						if (currentPlayer == 1) {
							attempt = p1.placeTile(true);
						} else {
							attempt = p2.placeTile(true);
						}
					}
				} while (retry);

				// if other player has move available, prompt to make another
				// move
			} else if (this.moveAvailable(currentPlayer % 2 + 1)) {
				System.out.println("Player " + currentPlayer
						+ " has no legal Moves.");

				if (currentPlayer == 1) {
					p2.setResult(null);
				} else {
					p1.setResult(null);
				}

			} else {
				// game is over with no legal moves left
				System.out.println(this.board.toString());
				System.out.println("GAME OVER!");
				return this.scoreGame();
			}
			// switch players and continue
			currentPlayer = (currentPlayer % 2) + 1;
		}
	}

	/**
	 * Checks if attempted move is valid for supplied player identifier
	 * 
	 * @param attempt
	 *            Location of potential move
	 * @param identifier
	 *            Identifier for player making the move
	 * @return true if valid move; false if invalid move
	 */
	private boolean isValid(Location attempt, int identifier) {
		int hor = attempt.getHorizontal();
		int vert = attempt.getVertical();

		// check if piece already exists
		if ((hor < Game.SIZE) && (hor >= 0) && (vert < Game.SIZE)
				&& (vert >= 0)) {
			if (this.board.getTile(hor, vert).getColor() != 0) {
				return false;
			}
		}
		// check that new piece flips old piece of other color
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {

				if (((hor + i) < Game.SIZE) && ((hor + i) >= 0)
						&& ((vert + j) < Game.SIZE) && ((vert + j) >= 0)) {

					// check if one of paths has other color
					if (this.board.getTile(hor + i, vert + j).getColor() == (identifier % 2 + 1)) {

						int k = 2;
						boolean found = false;
						boolean end = false;

						// move in all 8 directions out from attempted piece
						// placement
						while (k < Game.SIZE && !found && !end) {

							// check next position to be checked in on the board
							if (((hor + k * i) < Game.SIZE)
									&& ((hor + k * i) >= 0)
									&& ((vert + k * j) < Game.SIZE)
									&& ((vert + k * j) >= 0)) {
								// check for piece of own color
								if (this.board.getTile(hor + k * i,
										vert + k * j).getColor() == identifier) {
									found = true;

									return true;
								} else if (this.board.getTile(hor + k * i,
										vert + k * j).getColor() == 0) {
									end = true;
								}

							}

							k++;
						}

					}
				}
			}
		}

		// if above conditions not met, return invalid
		return false;
	}

	/**
	 * Places piece at specified location and flips necessary pieces
	 * 
	 * @param attempt
	 *            Location of move
	 * @param identifier
	 *            Identifier for player making move
	 */
	private void executeMove(Location attempt, int identifier) {
		int hor = attempt.getHorizontal();
		int vert = attempt.getVertical();
		int oppID = (identifier % 2) + 1;

		this.board.getTile(hor, vert).setColor(identifier);

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {

				// make sure direction is on board
				if (((hor + i) < Game.SIZE) && ((hor + i) >= 0)
						&& ((vert + j) < Game.SIZE) && ((vert + j) >= 0)) {

					// check if one of paths has other color
					if (this.board.getTile(hor + i, vert + j).getColor() == oppID) {

						int k = 2;
						boolean found = false;
						boolean end = false;

						// move in all 8 directions out from attempted piece
						// placement
						while ((k < Game.SIZE) && (!found) && (!end)) {

							// check next position to be checked in on the board
							if (((hor + k * i) < Game.SIZE)
									&& ((hor + k * i) >= 0)
									&& ((vert + k * j) < Game.SIZE)
									&& ((vert + k * j) >= 0)) {

								// check for piece of own color
								if (this.board.getTile(hor + k * i,
										vert + k * j).getColor() == identifier) {

									// entering this portion of the loop means
									// pieces are being "flipped"
									for (int l = 1; l < k; l++) {
										this.board.getTile(hor + l * i,
												vert + l * j).setColor(
												identifier);
									}

									found = true;

								} else if (this.board.getTile(hor + k * i,
										vert + k * j).getColor() == 0) {
									end = true;
								}

							}

							k++;
						}

					}
				}

			}
		}

	}

	/**
	 * Checks if there exists a legal move on the board
	 * 
	 * @param identifier
	 *            Identifier for player who is attempting to move
	 * @return true if move available; false otherwise
	 */
	private boolean moveAvailable(int identifier) {
		boolean found = false;

		int i = 0;
		int j = 0;
		while (!found && i < Game.SIZE) {
			j = 0;
			while (!found && j < Game.SIZE) {
				found = this.isValid(new Tile(i, j), identifier);
				j++;
				// System.out.println(found);
			}
			i++;
		}

		return found;
	}

	/**
	 * Sets up players and board according to user input from command line
	 * 
	 * @param p1
	 *            First player of game
	 * @param p2
	 *            Second player of game
	 */
	public void initialize(Player p1, Player p2) {

		// setup Players: H v. H or H v. C
		System.out.println("Welcome. Choose Type of Game");
		System.out.println("1. Human v. Human");
		System.out.println("2. Human v. Computer");
		System.out.println("3. Watch Dumb Player v. Computer");
		System.out.println("4. Watch Computer v. Computer");

		int choice;
		boolean valid = false;
		do {
			try {
				choice = input.nextInt();

				if (choice == 1) {
					this.p1 = new HumanPlayer(1);
					this.p2 = new HumanPlayer(2);
					valid = true;
				} else if (choice == 2) {
					this.p1 = new HumanPlayer(1);
					this.p2 = new CompPlayer(2);

					System.out
							.println("Player 1 is Human; Player 2 is Computer");
					valid = true;
				} else if (choice == 3) {
					this.p1 = new DumbPlayer(1);
					this.p2 = new CompPlayer(2);

					valid = true;

				} else if (choice == 4) {
					this.p1 = new CompPlayer(1);
					this.p2 = new CompPlayer(2);

					valid = true;
				} else {
					System.out.println("Bad Number");
					System.out.println("Choose Type of Game");
					System.out.println("1. Human v. Human");
					System.out.println("2. Human v. Computer");
				}
				input.nextLine(); // eat line break
			} catch (Exception e) {
				System.out.println("Not a Number");
				System.out.println("Choose Type of Game");
				System.out.println("1. Human v. Human");
				System.out.println("2. Human v. Computer");
				input.nextLine();
			}
		} while (!valid);

		// setup Board, including initial tiles
		this.board = new Board(Game.SIZE, Game.SIZE);
		this.board.setUpBoard();
	}

	/**
	 * Computes score of game by counting number of pieces on board
	 * 
	 * @return Winning player. Null if tie.
	 */
	public Player scoreGame() {

		int p1Counter = 0;
		int p2Counter = 0;

		// count tiles for each player
		for (int i = 0; i < Game.SIZE; i++) {
			for (int j = 0; j < Game.SIZE; j++) {
				if (this.board.getTile(i, j).getColor() == 1) {
					p1Counter++;
				} else if (this.board.getTile(i, j).getColor() == 2) {
					p2Counter++;
				}
			}
		}

		System.out.println("Player 1 Score: " + p1Counter);
		System.out.println("Player 2 Score: " + p2Counter);

		if (p1Counter > p2Counter) {
			return this.p1;
		} else if (p2Counter > p1Counter) {
			return this.p2;
		} else {
			return null;
		}
	}
}
