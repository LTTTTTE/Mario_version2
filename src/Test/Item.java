package Test;

abstract public class Item {

	protected double pos_x;
	protected double pos_y;
	protected double vec_x = 0;
	protected double vec_y = 0;

	public Item(double x, double y) {
		this.pos_x = x;
		this.pos_y = y;
	}

	public double getPos_x() {
		return pos_x;
	}

	public double getPos_y() {
		return pos_y;
	}

	public void setPos_x(double pos_x) {
		this.pos_x = pos_x;
	}

	public void setPos_y(double pos_y) {
		this.pos_y = pos_y;
	}

	public double getVec_x() {
		return vec_x;
	}

	public double getVec_y() {
		return vec_y;
	}

	public void setVec_x(double vec_x) {
		this.vec_x = vec_x;
	}

	public void setVec_y(double vec_y) {
		this.vec_y = vec_y;
	}

	public void delete() {
		this.pos_x = -100;
		this.pos_y = -100;
		System.out.println("DELETED");
	}

	abstract public void event();

}

/** 화면크기 조절아이템 */
class Item_1 extends Item {

	public Item_1(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		Game.round++;
		this.delete();
		if (Game.round == 1) { // 창커짐
			Game.MAX_WIDTH = 1314;
			Game.SCENE_SWITCH = true;
			this.setPos_x(100);
			this.setPos_y(50);
			Game.set_check_point();
		}

		if (Game.round == 2) { // 창줄음
			Game.MAX_WIDTH = 300;
			Game.MAX_HEIGHT = 200;
			Game.SCENE_SWITCH = false;
			this.setPos_x(200);
			this.setPos_y(50);
			Game.set_check_point();
		}
		if (Game.round == 3 && Game.map == 1) {// 창커짐 , 창이동
			Game.SCENE_SWITCH = true;
			Game.WINDOW_SWITCH = false;
			Game.frame.setLocation(0, 0);
			Game.MAX_WIDTH = 800;
			Game.MAX_HEIGHT = (int) (Game.MAX_WIDTH * 0.618);
			Game.map++;
			this.setPos_x(700);
			this.setPos_y(130);
			Game.NextMap();

		}
		if (Game.round == 4 && Game.map == 2) {
			Game.SCENE_SWITCH = true;
			Game.MAX_HEIGHT = 800;
			Game.GROUND = Game.MAX_HEIGHT;
			this.setPos_x(125);
			this.setPos_y(640);
			Game.set_check_point();
		}
		if (Game.round == 5 && Game.map == 2) {
			Game.SCENE_SWITCH = true;
			Game.MAX_HEIGHT = 870;
			Game.GROUND = Game.MAX_HEIGHT;
			this.setPos_x(675);
			this.setPos_y(750);
			Game.set_check_point();
		}
		if (Game.round == 6 && Game.map == 2) {
			Game.isMove = true;
			this.setPos_x(580);
			this.setPos_y(270);
			Game.set_check_point();
		}
		if (Game.round == 7 && Game.map == 2) {
			Game.isMove = false;
			Game.set_check_point();
			Game.SCENE_SWITCH = true;
			Game.MAX_WIDTH = 1000;
		}

		System.out.println("event_get_item_1 , round : " + Game.round);
		Game.startMusic("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\music_coin.wav");
	}
}

/** 윈도우창 이동 조절아이템 */
class Item_2 extends Item {

	public Item_2(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		Game.WINDOW_SWITCH = true;
		System.out.println("event_get_item_2 , WindowMove ");
		Game.set_check_point();
		this.delete();
	}

}

/** 먹으면 다시시작하는 아이템 */
class Item_Bad extends Item {

	public Item_Bad(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub
		Game.pos_x = Game.round_x;
		Game.pos_y = Game.round_y;
	}

}

class Bullet extends Item {

	public Bullet(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public void setVec_x(boolean vec) {

		if (vec)
			super.vec_x = 1;
		else
			super.vec_x = -1;
	}

	@Override
	public void event() {
		// TODO Auto-generated method stub

	}

}
