#README for Othello Game
Chae Jubb // ecj2122

##To use this software:
./$java OthelloTest

Follow System.out instructions for choosing game type: Human v Human, Human v Computer.  Additionally, one can observe game between computer AIs.
Follow output and choose move according to standard rules, with my clarifications:
  1) Only the placed tile can cause opponent tiles to the flipped.  That is, no chain reactions.
  2) Only opponents pieces between placed tile and closest friendly (to placed) piece are flipped.

Player 1's pieces represented by X; Player 2's by O.
By default the human player and dumb player are player 1, when playing against ``smart" AI.

Note: I need to make p1 and p2 accessible outside the Othello class to fit the apparent purpose of the interface.
    Making these two objects protected allows them to be called in the OthelloTest class.

##Notes on AI class tournament
I would like my CompPlayer.java to be entered into the class tournament.
My player has (at minimum) the following requirements:
  1) Game rules I list above must apply to testing game engine.  My player uses my exact rules in updating its gameboard
  2) My Board class.  In particular, the custom deep copy method and board set-up methods.
      This board.setUp() method places player 1's pieces (X) at 3,3 and 4,4 with Os at 3,4 and 4,3.
      (This method used for Game and Player board setup)
  3) My Tile class.  The tiles must be able to hold an identifier attribute.
  4) When constructed, must contain argument to be identifier.  (1 for Player 1; 2 for Player 2).
      Theoretically, everything necessary (such as opponent identifier) can be derived from this argument.
