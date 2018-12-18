package Test;


public class PlatForm {
	private int point_1_x;
	private int point_1_y;
	private int point_2_x;
	private int point_2_y;
	private int vec = 5;

	public PlatForm(int x1, int y1, int x2, int y2) {
		point_1_x = x1 - 10;
		point_1_y = y1;
		point_2_x = x2 - 10;
		point_2_y = y2;
		vec = 5;
	}

	public int getPoint_1_x() {
		return point_1_x;
	}

	public int getPoint_1_y() {
		return point_1_y;
	}

	public int getPoint_2_x() {
		return point_2_x;
	}

	public int getPoint_2_y() {
		return point_2_y;
	}

	public int getVec() {
		return vec;
	}

	public void setPoint_1_x(int point_1_x) {
		this.point_1_x = point_1_x;
	}

	public void setPoint_1_y(int point_1_y) {
		this.point_1_y = point_1_y;
	}

	public void setPoint_2_x(int point_2_x) {
		this.point_2_x = point_2_x;
	}

	public void setPoint_2_y(int point_2_y) {
		this.point_2_y = point_2_y;
	}

	public void setPoint_1(int point_1_x, int point_1_y) {
		this.point_1_x = point_1_x;
		this.point_1_y = point_1_y;
	}

	public void setPoint_2(int point_2_x, int point_2_y) {
		this.point_2_x = point_2_x;
		this.point_2_y = point_2_y;
	}

	public void setPoint(int point_1_x, int point_1_y) {
		this.setPoint_1(point_1_x, point_1_y);
		this.setPoint_2(point_1_x + 150, point_1_y + 30);
	}

	public void setVec(int vec) {
		this.vec = vec;
	}

}
