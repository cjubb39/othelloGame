/**
 * A really dumb player. *Any* decent player should win ALL games against this
 * player
 */
public class DumbPlayer implements Player {

	private int horizontal, vertical,identifier;

	public DumbPlayer(int ident) {
		horizontal = 0;
		vertical = 0;
		this.identifier = ident;
	}

	@Override
	/**
	 * Uses a 'straight-forward' way for placing tiles:
	 * starts in the top left, and sequentially tries every possibility until one works.
	 */
	public Location placeTile(boolean retry) {
		MyLocation tile;
		if(!retry)
				tile = new MyLocation(horizontal, vertical);
		else{
			if(horizontal < Game.SIZE-1){
				horizontal++;
				tile = new MyLocation(horizontal, vertical);
			}
			else{
				horizontal = 0;
				vertical++;
				tile = new MyLocation(horizontal, vertical);
			}
		}
		
		return tile;
	}

	@Override
	/**
	 * Doesn't use the state of the board at all - (again, not a good idea!)
	 * resets the placement sequence on every move, ie. it will restart trying 
	 * to place tiles at the top left at every move.
	 */
	public void setResult(Location move) {
		horizontal = 0;
		vertical = 0;
	}
	
	public int getIdentifier(){
		return this.identifier;
	}

}
