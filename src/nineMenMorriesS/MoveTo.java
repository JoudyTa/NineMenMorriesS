package nineMenMorriesS;

public class MoveTo {
	int row, col; 
	public MoveTo() {
		// TODO Auto-generated constructor stub
		this.row = 0;
		this.col = 0;
	}
	
	public MoveTo(int x, int y) {
		this.row = x;
		this.col = y;
	}
	
	public boolean is_equal(MoveTo pos) {
		return (this.row == pos.row) && (this.col == pos.col);
	}

}
