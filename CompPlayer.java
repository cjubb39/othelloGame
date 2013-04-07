import java.util.ArrayList;

/**
 * Computer player, designed to win Othello Game. Impelments Player interface
 * 
 * @author Chae Jubb
 * @version 1.1
 */

public class CompPlayer implements Player {

	private int identifier;
	private Board board, simBoard, simBoard2;

	public CompPlayer(int ident) {
		this.identifier = ident;
		this.board = new Board(Game.SIZE, Game.SIZE);
		this.board.setUpBoard();

		this.simBoard = Board.copy(this.board);
		this.simBoard2 = Board.copy(this.simBoard2);
	}

	/**
	 * Algorithm used to place next piece. Note, this method (hopefully) doesn't
	 * use the retry param as the validity of the move is internally checked
	 * 
	 * @param retry
	 *            Is this a second (or more) attempt for a given turn?
	 * @return Location of algorithmically determined best move
	 */
	@Override
	public Location placeTile(boolean retry) {

		// System.out.println("Comp Board \n" + this.board.toString());
		System.out.println("Thinking...");
		// Opening moves first
		if (this.getBoardCount() == 4) {
			boolean end = false;

			// repeat until valid move
			while (!end) {
				int random = (int) (4 * Math.random() + 2);
				int random2 = (int) (4 * Math.random() + 2);
				Location attempt = new Tile(random, random2);

				if (this.isValid(attempt, this.identifier)) {
					this.executeMove(this.board, attempt, this.identifier, 0);
					return attempt;
				}

			}
		} else if (this.getBoardCount() == 5) {
			// find where only own tile located
			Location onlyTile = null;
			Location attempt;
			for (int i = 0; i < Game.SIZE; i++) {
				for (int j = 0; j < Game.SIZE; j++) {
					if (this.board.getTile(i, j).getColor() == this.identifier) {
						onlyTile = new Tile(i, j);
					}
				}
			}

			// choose one of two particular openings: diagonal or perpendicular
			for (int i = -2; i <= 2; i += 2) {
				for (int j = -2; j <= 2; j += 2) {
					attempt = new Tile(onlyTile.getHorizontal() + i,
							onlyTile.getVertical() + j);
					if (this.isValid(attempt, this.identifier)) {
						if (this.oppAround(attempt) == 2) {
							this.executeMove(this.board, attempt,
									this.identifier, 0);
							return attempt;
						}
					}
				}
			}

			// general play instructions
		} else if ((this.getBoardCount() >= 6) || (this.getBoardCount() <= 62)) {
			Location attempt, tester;
			ArrayList<Location> maxStableMove = new ArrayList<Location>();
			int stableCount = 0;
			int maxStableCount = 0;

			// find moves that give maximum number of stable pieces
			for (int i = 0; i < Game.SIZE; i++) {
				for (int j = 0; j < Game.SIZE; j++) {
					attempt = new Tile(i, j);

					if (this.isValid(attempt, this.identifier)) {
						this.simBoard2 = Board.copy(this.board);
						this.executeMove(this.simBoard2, attempt,
								this.identifier, 0);

						// check if board combination wins the board for the
						// player
						if (this.isWinner(this.simBoard2, this.identifier)) {
							return attempt;
						}
						stableCount = 0;

						for (int k = 0; k < Game.SIZE; k++) {
							for (int l = 0; l < Game.SIZE; l++) {
								tester = new Tile(k, l);

								if (this.isValid(tester, this.identifier)) {
									if (this.isStable(this.simBoard2, tester,
											this.identifier)) {
										stableCount++;
									}
								}
							}
						}
						if (stableCount > maxStableCount) {
							;
							maxStableCount = stableCount;
							maxStableMove.clear();
							maxStableMove.add(attempt);

						} else if (stableCount == maxStableCount) {
							maxStableCount = stableCount;
							maxStableMove.add(attempt);

						}
					}
				}
			}

			Location response = null;
			if (maxStableMove.size() == 1) {
				response = maxStableMove.get(0);

				this.executeMove(this.board, response, this.identifier, 0);
				return response;
			} else {
				// return a corner if exists
				int maxCount = 0;
				int count;
				Location test;

				for (int i = 0; i < maxStableMove.size(); i++) {
					int hor = maxStableMove.get(i).getHorizontal();
					int vert = maxStableMove.get(i).getVertical();
					test = new Tile(hor, vert);

					if ((hor == 0 && vert == 0) || (hor == 0 && vert == 7)
							|| (hor == 7 && vert == 0)
							|| (hor == 7 && vert == 7)) {
						count = this.executeMove(this.board, test,
								this.identifier, 1);

						if (count > maxCount) {
							response = test;
						}
					}

				}

			}

			// continue if no response so far
			if (response == null) {
				// next look for an edge
				int maxCount = 0;
				int count;
				Location test;

				for (int i = 0; i < maxStableMove.size(); i++) {
					int hor = maxStableMove.get(i).getHorizontal();
					int vert = maxStableMove.get(i).getVertical();
					test = new Tile(hor, vert);

					// looks for edge, not directly next to corner
					if ((((hor == 0) || (hor == 7)) && !((vert == 1) || (vert == 6)))
							|| (((vert == 0) || (vert == 7)) && !((hor == 1) || (hor == 6)))) {
						count = this.executeMove(this.board, test,
								this.identifier, 1);

						if (count > maxCount) {
							response = test;
						}
					}

				}
			}

			// pick next "best" moves. Eliminate points diagonal from corners.
			// BAD PLACES
			ArrayList<Location> transferForward = new ArrayList<Location>();
			if (response == null) {
				int count;
				Location test;

				int bestCount;

				if (this.getBoardCount() <= 40) {
					bestCount = 10000;
				} else {
					bestCount = 0;
				}

				for (int i = 0; i < maxStableMove.size(); i++) {
					int hor = maxStableMove.get(i).getHorizontal();
					int vert = maxStableMove.get(i).getVertical();

					if (((hor == 1) && (vert == 1))
							|| ((hor == 6) && (vert == 6))
							|| ((hor == 1) && (vert == 6))
							|| ((hor == 6) && (vert == 1))) {
						transferForward.add(maxStableMove.remove(i));
					} else {

						test = new Tile(hor, vert);

						count = this.executeMove(this.board, test,
								this.identifier, 1);

						if (this.getBoardCount() <= 40) {
							if (count < bestCount) {
								bestCount = count;
								response = test;
							}
						} else {
							if (count > bestCount) {
								bestCount = count;
								response = test;
							}
						}
					}
				}
			}

			// use X-corners as last resort
			if (transferForward.size() != 0) {
				int maxCount = 0;
				int count = 0;
				Location test;

				for (int i = 0; i < transferForward.size(); i++) {
					test = transferForward.get(i);

					count = this.executeMove(this.board, test, this.identifier,
							1);

					if (count > maxCount) {
						response = test;
					}
				}
			}

			if (response != null) {
				this.executeMove(this.board, response, this.identifier, 0);
				return response;
			} else {
				// if something above breaks, return random valid placement
				System.out.println("E-1");
				boolean end = false;
				while (!end) {
					int hor = (int) (8 * Math.random());
					int vert = (int) (8 * Math.random());
					Location test = new Tile(hor, vert);
					if (this.isValid(test, this.identifier)) {
						end = true;
						this.executeMove(this.board, test, this.identifier, 0);
						return test;
					}
				}
			}
		}
		return null; // needed for error checking. Should never reach this
						// point, theoretically
	}

	/**
	 * Updates own board to reflect opponent's most previous move
	 * 
	 * @param move
	 *            Location of opponent's most previous move
	 */
	@Override
	public void setResult(Location move) {
		if (move != null) {
			this.executeMove(this.board, move, (this.identifier % 2) + 1, 0);
		}
	}

	/**
	 * Checks if attempted move location is valid
	 * 
	 * @param attempt
	 *            Location to be checked for valid move
	 * @return true if location is valid; false otherwise
	 */
	private boolean isValid(Location attempt, int identifier) {
		int hor = attempt.getHorizontal();
		int vert = attempt.getVertical();

		// check if piece already exists
		if (this.board.getTile(hor, vert).getColor() != 0) {
			return false;
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
	 * Executes supplied move by supplied player on this.board
	 * 
	 * @param attempt
	 *            Location of move
	 * @param identifier
	 *            Identifier for player making move
	 * @param testCode
	 *            0 = execute; 1 = return count, DO NOT execute
	 * @return Number of opponents chips that would be flipped. 0 for actual
	 *         execution; non-zero for simulation
	 */
	private int executeMove(Board board, Location attempt, int identifier,
			int testCode) {
		int hor = attempt.getHorizontal();
		int vert = attempt.getVertical();
		int oppID = (identifier % 2) + 1;
		int count = 0;

		int oldColor = board.getTile(hor, vert).getColor();
		board.getTile(hor, vert).setColor(identifier);

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {

				// make sure direction is on board
				if (((hor + i) < Game.SIZE) && ((hor + i) >= 0)
						&& ((vert + j) < Game.SIZE) && ((vert + j) >= 0)) {

					// check if one of paths has other color
					if (board.getTile(hor + i, vert + j).getColor() == oppID) {

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
								if (board.getTile(hor + k * i, vert + k * j)
										.getColor() == identifier) {

									// entering this portion of the loop means
									// pieces are being "flipped"
									for (int l = 1; l < k; l++) {

										if (testCode == 0) {
											board.getTile(hor + l * i,
													vert + l * j).setColor(
													identifier);
										} else if (testCode == 1) {
											count++;
										}
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

		if (testCode == 1) {
			board.getTile(hor, vert).setColor(oldColor);
		}

		return count;
	}

	/**
	 * Simple accessor method for number of pieces placed
	 * 
	 * @return Integer count of number of pieces played on board
	 */
	private int getBoardCount() {
		int count = 0;
		int color = 0;

		for (int i = 0; i < Game.SIZE; i++) {
			for (int j = 0; j < Game.SIZE; j++) {
				color = this.board.getTile(i, j).getColor();
				if ((color == 1) || (color == 2)) {
					count++;
				}
			}
		}

		return count;
	}

	/**
	 * 
	 * @return count of opponent pieces around provided tile
	 */
	private int oppAround(Location move) {
		int count = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (this.board.getTile(move.getHorizontal() + i,
						move.getVertical() + j).getColor() != 0) {
					count++;
				}
			}
		}

		return count;
	}

	/**
	 * Checks if position is stable. move need not already be occupied by
	 * identifier
	 * 
	 * @param move
	 *            Location to piece to check if stable
	 * @param identifier
	 *            Check if stable for player denoted by identifier
	 * @return true if stable for identifier; false otherwise
	 */
	private boolean isStable(Board board, Location move, int identifier) {
		// create copy of board to sim
		// this.simBoard = Board.copy(board);
		int oppID = (identifier % 2) + 1;
		Location attempt;

		// this.simBoard.getTile(move.getHorizontal(),
		// move.getVertical()).setColor(identifier);

		for (int i = 0; i < Game.SIZE; i++) {
			for (int j = 0; j < Game.SIZE; j++) {

				if (this.simBoard.getTile(i, j).getColor() == 0) {
					// this.simBoard.getTile(i, j).setColor(oppID);

					for (int k = 0; k < Game.SIZE; k++) {
						for (int l = 0; l < Game.SIZE; l++) {

							this.simBoard = Board.copy(board);
							this.simBoard.getTile(move.getHorizontal(),
									move.getVertical()).setColor(identifier);
							this.simBoard.getTile(i, j).setColor(oppID);

							attempt = new Tile(k, l);
							if (this.isValid(attempt, oppID)) {
								this.executeMove(simBoard, attempt, oppID, 0);

								if (this.simBoard.getTile(move.getHorizontal(),
										move.getVertical()).getColor() != identifier) {
									return false;
								}
							}
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks if given player has won the board
	 * 
	 * @param board
	 *            Board for winning condition to be checked on
	 * @param identifier
	 *            Identifier for player to check for winning condition
	 * @return true if player specified wins on specified board; false otherwise
	 */
	private boolean isWinner(Board board, int identifier) {
		int oppID = (identifier % 2) + 1;
		for (int i = 0; i < Game.SIZE; i++) {
			for (int j = 0; j < Game.SIZE; j++) {
				if (board.getTile(i, j).getColor() == oppID) {
					return false;
				}
			}
		}

		// only returns true if no opponent piece on the board
		return true;
	}

	/**
	 * Getter method for identifier of player
	 * 
	 * @return Integer identifier
	 */
	public int getIdentifier() {
		return this.identifier;
	}

}