/**
 * A simple location arrangement.
 */
public class MyLocation implements Location {
	
	private int horizontal;
	private int vertical;
	
	public MyLocation(int x, int y){
		this.horizontal = x;
		this.vertical = y;
	}

	@Override
	public int getHorizontal() {
		return horizontal;
	}

	@Override
	public int getVertical() {
		return vertical;
	}
	
	public String toString() {
		return "horizontal: " + horizontal + ", vertical: " + vertical;
	}
}
