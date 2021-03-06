package Test;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {

	static JFrame frame;
	/** 창길이(변경될수있음) */
	public static int WIDTH = 1200;
	/** 창높이(변경될수있음) */
	public static int HEIGHT = (int) (WIDTH * 0.618);
	/** 게임스피드(default = 0.5) */
	public static final double SPEED = 0.65;
	/** 케릭터사이즈 */
	public static final int SIZE = 35;
	/** 케릭터 점프 Vector(힘) */
	public static final int JUMP_POWER = 50;
	/** 중력가속도 */
	public static final double GRAVITY = 9.8;
	/** 땅바닥 좌표 */
	public static int GROUND = HEIGHT - 100; // 650쯤
	/** 케릭터 현재 X 좌표 */
	public static double pos_x = WIDTH / 2;
	/** 케릭터 현재 Y 좌표 */
	public static double pos_y = HEIGHT - 110;
	/** 라운드 초기 X 좌표 */
	public static double round_x = WIDTH / 2;
	/** 라운드 초기 Y 좌표 */
	public static double round_y = WIDTH / 2;
	/** 케릭터 x축으로 가는힘 */
	public static double vec_x = 0;
	/** 케릭터 y축으로 가는힘 */
	public static double vec_y = 0;
	/** 케릭터 y축으로가는 가속도힘 */
	public static double vec2_y = 0;
	/** 케릭터 점프 변수 */
	public static double vec_jump = 0;
	/** 시간변수(가속도영향) */
	public static double time = 0;
	/** 케릭터 보는방향 (true => 오른쪽 , false => 왼쪽) */
	public static boolean heading = true;
	/** 창크기 변경시 조건이되는 최대 창길이 */
	public static int MAX_WIDTH = WIDTH;
	/** 창크기 변경시 조건이되는 최대 창높이 */
	public static int MAX_HEIGHT = HEIGHT;
	/** MAX값에맞게 : true => 창 커짐 , false => 창 작아짐 */
	public static boolean SCENE_SWITCH = true;
	/** true => 창이동가능, false =>창이동불가 */
	public static boolean WINDOW_SWITCH = false;
	/** 라운드(창이동과 크기변경에 속하는 변수 */
	public static int round = 0;
	/** 맵(실제 라운드 카운팅) */
	public static int map = 1;
	/** 발판 이동 트리거 변수 */
	public static boolean isMove = false;

	/** 더미자료구조 */
	int[] dummy = new int[10];

	/** 충돌위치 저장리스트 */
	static ArrayList<PlatForm> list_plat = new ArrayList<>();
	/** 아이템 위치 저장테이블 */
	static Hashtable<String, Item> table_item = new Hashtable<>();

	/** 배경이미지 */
	private BufferedImage background_image;
	/** 캐릭터 이미지 */
	private BufferedImage[] character_image;
	/** 벽 이미지 */
	private BufferedImage wall_image;
	/** 적 이미지 */
	private BufferedImage[] enemy_image;
	/** black 아이템 이미지 */
	private BufferedImage[] black_item_image;
	/** red 아이템 이미지 */
	private BufferedImage red_item_image;

	/** 생성자 클래스 (한번초기화되는부분) */
	public Game() {
		// image초기화부분
		background_image = init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\background.png");
		character_image = new BufferedImage[] {
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\mario_1_right.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\mario_2_right.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\mario_3_right.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\mario_jump_right.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\mario_1_left.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\mario_2_left.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\mario_3_left.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\mario_jump_left.png"), };
		wall_image = init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\wall_2.png");
		enemy_image = new BufferedImage[] {
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\enemy_1.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\enemy_2.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\enemy_3.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\enemy_4.png") };
		black_item_image = new BufferedImage[] {
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\coin_2.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\coin_3.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\coin_4.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\coin_5.png"),
				init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\coin_6.png") };
		red_item_image = init_image("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\stat.png");

		frame = new JFrame("GAME");
		MyPanel panel = new MyPanel();
		frame.setContentPane(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);

		listen_key listener_key = new listen_key();
		listen_click listener_click = new listen_click();
		frame.addKeyListener(listener_key);
		frame.addMouseListener(listener_click);

		// frame.setLocation(100,500);

		/// 단 한번 초기화 되는부분
		setMap();
		startMusic("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\music_super.wav");

		///
	}

	/** 이미지 초기화 */
	private BufferedImage init_image(String file) {
		try {
			return ImageIO.read(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			return background_image;
		}
	}

	/** 스케줄러(매번초기화됨) */
	public void start() {
		while (true) {
			// 0.016초마다 계속 초기화 되는부분
			time += 0.0166666666667;

			Ground();
			Jump();
			GetItems();
			MoveWindow();
			EnemyMover();

			if (isMove)
				plat_mover();
			// if (SCENE_SWITCH) {
			if (Scene_change(MAX_WIDTH, MAX_HEIGHT)) {
				SCENE_SWITCH = false;
			}
			// }

			pos_x += vec_x * 5 * SPEED;
			pos_y += -vec_jump / 10 + (vec_y * SPEED);

			frame.repaint();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("EXCEPTION OCCURED");
				e.printStackTrace();
			}
		}
	}

	/** 충돌구현 */
	public void Ground() {
		if (pos_y <= GROUND) {
			vec_y = 0.5 * GRAVITY * time * time;

			// System.out.println("GRAVITY~~" + vec_jump);
		} else
			vec_y = 0;

		for (PlatForm plat : list_plat) {
			// 위에서 아래로 충돌
			if (pos_x >= plat.getPoint_1_x() && pos_x <= plat.getPoint_2_x()
					&& pos_y + SIZE * 1.5 <= plat.getPoint_1_y() && pos_y + SIZE * 2.2 >= plat.getPoint_1_y()) {
				vec_y = 0;
				time = 0;
			}
			// 아래에서 위로 충돌
			if (pos_x >= plat.getPoint_1_x() - 5 && pos_x <= plat.getPoint_2_x() + 5
					&& pos_y + 45 >= plat.getPoint_2_y() + 5 && pos_y + 45 <= plat.getPoint_2_y() + 15) {
				vec_jump = 0;
				if (time > 2) {
					time = 0;
				}
			}
			// 왼쪽에서 오른쪽으로 충돌
			if (pos_y + 45 >= plat.getPoint_1_y() && pos_y + 45 <= plat.getPoint_2_y() && pos_x <= plat.getPoint_1_x()
					&& pos_x >= plat.getPoint_1_x() - SIZE / 2.0) {
				pos_x = plat.getPoint_1_x() - SIZE / 2.0;
			}
			// 오른쪽에서 왼쪽으로 충돌
			if (pos_y + 45 >= plat.getPoint_1_y() && pos_y + 45 <= plat.getPoint_2_y() && pos_x >= plat.getPoint_2_x()
					&& pos_x <= plat.getPoint_2_x() + SIZE / 2.5) {
				pos_x = plat.getPoint_2_x() + SIZE / 2.5;
			}
			// 맵충돌또는 초기화
			if (pos_y > MAX_HEIGHT || pos_x < 0 || pos_x > MAX_WIDTH) {
				System.out.println("OUT_OF_MAP");
				reset_pos();
			}
		}
	}

	/** 초기 맵, 초기 아이템생성(생성자안에사용됨) */
	public void setMap() {

		list_plat.add(new PlatForm(100, 610, 250, 640));
		list_plat.add(new PlatForm(900, 610, 1050, 640));
		list_plat.add(new PlatForm(700, 510, 850, 540));
		list_plat.add(new PlatForm(500, 410, 650, 440));
		list_plat.add(new PlatForm(700, 310, 850, 340));
		list_plat.add(new PlatForm(900, 210, 1050, 240));
		list_plat.add(new PlatForm(300, 510, 450, 540));
		list_plat.add(new PlatForm(300, 310, 450, 340));
		list_plat.add(new PlatForm(100, 210, 250, 240));
		list_plat.add(new PlatForm(50, 110, 200, 140));

		table_item.put("item_1", new Item_1(965, 150));
		table_item.put("item_2", new Item_2(1200, 600));
		table_item.put("item_bad_1", new Item_Bad(750, 450));
		table_item.put("item_bad_1_2", new Item_Bad(878 - 15, 579 - 40));
		table_item.put("item_bad_1_3", new Item_Bad(150, 300));
		table_item.put("item_bad_1_4", new Item_Bad(1300, 500));
		table_item.put("item_bad_1_5", new Item_Bad(1300, 500));
		table_item.put("item_bad_1_6", new Item_Bad(500, 900));
		table_item.put("item_bad_1_7", new Item_Bad(100, 100));
		table_item.put("item_bad_1_8", new Item_Bad(500, 500));
	}

	/** 아이템획득시 이벤트관리메소드 */
	public void GetItems() {

		if (table_item.isEmpty())
			return;

		Set<String> keys = table_item.keySet();
		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String str = iterator.next();
			Item item = table_item.get(str);
			if (contains(item)) {
				item.event();
			}

		}
	}

	/** 점프시 가속도,위치관리메소드 */
	public void Jump() {
		if (vec_jump > 0) {
			vec_jump -= GRAVITY * time * 10 / 60;
		} else
			vec_jump = 0;
	}

	/** 아이템 먹었는지 체크하는 메소드 */
	public boolean contains(Item item) {
		double item_x = item.getPos_x();
		double item_y = item.getPos_y();
		if (pos_x - SIZE <= item_x && pos_x + SIZE >= item_x && pos_y - SIZE <= item_y && pos_y + SIZE >= item_y) {

			return true;
		}

		else
			return false;
	}

	/** 화면크기조절 메소드 */
	public boolean Scene_change(int max_width, int max_height) {
		/*
		 * if (round == 1) { Game.MAX_WIDTH = 1600; max_width = 1600;
		 * //table_item.get("item_1").setPos_x(); //table_item.get("item_1").setPos_y();
		 * }
		 */

		if (SCENE_SWITCH) { // 크기 커짐
			if (max_width > WIDTH)
				WIDTH = WIDTH + 5;
			if (max_height > HEIGHT)
				HEIGHT = HEIGHT + 5;

			frame.setSize(WIDTH, HEIGHT);

			if (WIDTH < max_width || HEIGHT < max_height) {
				return false;
			} else {

				return true;
			}

		} else { /// 크기작아짐

			if (max_width < WIDTH)
				WIDTH = WIDTH - 15;
			if (max_height < HEIGHT)
				HEIGHT = HEIGHT - 15;

			frame.setSize(WIDTH, HEIGHT);

			if (WIDTH > max_width || HEIGHT > max_height) {
				return false;
			} else {

				return true;
			}
		}
	}

	/** 창이동 메소드 */
	public boolean MoveWindow() {

		if (!WINDOW_SWITCH)
			return false;
		frame.setLocation((int) (800 - (pos_x * 1.0)), (int) (700 - (pos_y * 1.0)));

		return true;
	}

	/** 다음맵 세팅 메소드 */
	public static boolean NextMap() {

		Iterator<PlatForm> iter_plat = list_plat.iterator();
		while (iter_plat.hasNext()) {
			PlatForm p = iter_plat.next();
			p.setPoint(50, 110);
		}

		if (map == 2) {
			set_check_point();

			list_plat.get(0).setPoint(330, 190);
			list_plat.get(1).setPoint(660, 190);
			list_plat.get(2).setPoint(500, 380);
			list_plat.get(3).setPoint(50, 640);// move
			list_plat.get(4).setPoint(50, 720);
			list_plat.get(5).setPoint(300, 800);
			list_plat.get(6).setPoint(600, 800);
			list_plat.get(7).setPoint(600, 510);// move
			// 맵맵맵맵맵
			return true;
		}

		return false;
	}

	/** 공격 메소드 */
	public boolean Attack() {

		Bullet bul = new Bullet(pos_x, pos_y);
		bul.setVec_x(heading);
		return false;
	}

	/** 적 랜덤이동 메소드 */
	public boolean EnemyMover() {

		Set<String> keys = table_item.keySet();
		Iterator<String> iter_table_item = keys.iterator();
		Random rand = new Random();
		while (iter_table_item.hasNext()) {

			String s = iter_table_item.next();
			if (s.contains("bad")) {
				Item i = table_item.get(s);

				if (rand.nextInt(15) == 7) {
					i.setVec_x(rand.nextInt(5) - rand.nextInt(5));
					i.setVec_y(rand.nextInt(5) - rand.nextInt(5));
				}

				i.setPos_x(i.getPos_x() + i.getVec_x() * SPEED);
				i.setPos_y(i.getPos_y() + i.getVec_y() * SPEED);
			}
		}
		return false;
	}

	/** 위치리셋 메소드 */
	public void reset_pos() {
		// TODO Auto-generated method stub
		pos_x = round_x;
		pos_y = round_y;
	}

	/** 체크포인트 좌표설정 */
	public static void set_check_point() {
		round_x = pos_x;
		round_y = pos_y;
	}

	/** 플렛폼 이동메소드 */
	public void plat_mover() {
		if (map == 2) {
			PlatForm p1 = list_plat.get(3);// 아랫놈
			PlatForm p2 = list_plat.get(7);// 윗놈
			if (dummy[2] == 0) {
				dummy[0] = p1.getPoint_1_x(); // 이동가능한 최저 x좌표
				dummy[1] = p2.getPoint_1_x(); // 이동가능한 최고 x좌표
				dummy[2] = 1;
			}

			if (p1.getPoint_1_x() < dummy[0])
				p1.setVec(5);
			if (p1.getPoint_1_x() > dummy[1])
				p1.setVec(-5);
			if (p2.getPoint_1_x() < dummy[0])
				p2.setVec(5);
			if (p2.getPoint_1_x() > dummy[1])
				p2.setVec(-5);

			p1.setPoint_1(p1.getPoint_1_x() + p1.getVec(), p1.getPoint_1_y());
			p2.setPoint_1(p2.getPoint_1_x() + p2.getVec(), p2.getPoint_1_y());

		}
	}

	/** 소리관리 메소드 */
	public static void startMusic(String file) {
		try {
			Clip o = AudioSystem.getClip();
			o.open(AudioSystem.getAudioInputStream(new File(file)));
			o.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 패널 클래스(화면생성, 스케줄러에서 사용됨) */
	private class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// ** 배경이미지 삽입*/
			g.drawImage(background_image, 0, 0, MAX_WIDTH, MAX_HEIGHT, this);

			// ** 캐릭터이미지 삽입*/

			if (vec_x >= 0) { // 우측
				if (vec_jump > 0)
					g.drawImage(character_image[3], (int) pos_x, (int) pos_y, this);
				else {
					g.drawImage(character_image[dummy[6] % 3], (int) pos_x, (int) pos_y, this);
				}
			}
			if (vec_x < 0) {// 좌측
				if (vec_jump > 0)
					g.drawImage(character_image[7], (int) pos_x, (int) pos_y, this);
				else {
					g.drawImage(character_image[dummy[6] % 3 + 4], (int) pos_x, (int) pos_y, this);
				}
			}
			if (dummy[5]++ > 5) {
				dummy[5] = 0;
				dummy[6]++;
			}
			Iterator<PlatForm> iter_list_plat = list_plat.iterator();
			Set<String> keys = table_item.keySet();
			Iterator<String> iter_table_item = keys.iterator();

			while (iter_list_plat.hasNext()) {
				PlatForm p = iter_list_plat.next();
				// ** 벽이미지 삽입 */
				g.drawImage(wall_image, p.getPoint_1_x() + 20, p.getPoint_1_y() - 20, 150, 30, this);
			}

			// **black 이미지 삽입*/
			g.drawImage(black_item_image[dummy[4] / 10], (int) table_item.get("item_1").getPos_x(),
					(int) table_item.get("item_1").getPos_y(), (int) (SIZE), (int) (SIZE), this);

			// ** red 이미지 삽입 */
			g.drawImage(red_item_image, (int) table_item.get("item_2").getPos_x(),
					(int) table_item.get("item_2").getPos_y(), (int) (SIZE * 1.5), (int) (SIZE * 1.5), this);

			while (iter_table_item.hasNext()) {
				String s = iter_table_item.next();
				if (s.contains("bad")) {
					Item i = table_item.get(s);
					// ** 적 이미지 삽입*/
					g.drawImage(enemy_image[1], (int) i.getPos_x(), (int) i.getPos_y(), (int) (SIZE * 1.2),
							(int) (SIZE * 1.2), this);
				}
			}
			dummy[4] = (dummy[4] + 1) % 25;
			// Graphics2D g2 = (Graphics2D) g;

		}
	}

	/** 키보드입력메소드(스케줄러외의 다른스케줄러에서 사용됨) */
	public class listen_key implements KeyListener {

		@Override
		public void keyPressed(KeyEvent k) {
			// TODO Auto-generated method stub
			if (k.getKeyCode() == k.VK_LEFT) {
				if (vec_x == -1)
					return;
				vec_x--;
				System.out.println("KEY_LEFT_PRESSED : " + vec_x);
			}
			if (k.getKeyCode() == k.VK_RIGHT) {
				if (vec_x == 1)
					return;
				vec_x++;
				System.out.println("KEY_RIGHT_PRESSED : " + vec_x);
			}
			if (k.getKeyCode() == k.VK_UP) {
				System.out.println("KEY_UP_PRESSED");
			}
			if (k.getKeyCode() == k.VK_DOWN) {
				System.out.println("KEY_DOWN_PRESSED");
			}
			if (k.getKeyCode() == k.VK_SPACE) {
				if (vec_y == 0) {
					time = 0;
					vec_jump = JUMP_POWER;
					System.out.println("KEY_SPACE_PRESSED");
					startMusic("C:\\Users\\DB2\\eclipse-workspace\\MARIO_BETA\\src\\Test\\music_jump.wav");
				}
			}
			if (k.getKeyCode() == k.VK_V) {

				System.out.println("ATTACK");
			}
		}

		@Override
		public void keyReleased(KeyEvent k) {
			// TODO Auto-generated method stub
			if (k.getKeyCode() == k.VK_LEFT) {
				if (vec_x == 1)
					return;
				vec_x++;
				System.out.println("KEY_LEFT_RELEASED : " + vec_x);
			}
			if (k.getKeyCode() == k.VK_RIGHT) {
				if (vec_x == -1)
					return;
				vec_x--;
				System.out.println("KEY_RIGHT_RELEASED : " + vec_x);
			}
			if (k.getKeyCode() == k.VK_UP) {
				System.out.println("KEY_UP_RELEASED");
			}
			if (k.getKeyCode() == k.VK_DOWN) {
				System.out.println("KEY_DOWN_RELEASED");
			}
			if (k.getKeyCode() == k.VK_SPACE) {
				System.out.println("KEY_SPACE_RELEASED");
			}
			if (k.getKeyCode() == k.VK_ESCAPE) {
				System.out.println(pos_x + "," + pos_y);
			}
		}

		@Override
		public void keyTyped(KeyEvent k) {
			// TODO Auto-generated method stub

		}

	}

	/** 마우스입력메소드(스케줄러외의 다른스케줄러에서 사용됨) */
	public class listen_click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent k) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent k) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent k) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent k) {
			// TODO Auto-generated method stub
			System.out.println("(" + k.getX() + "," + k.getY() + ") PRESSED");
			table_item.put(String.format("item_bad_1_%d", dummy[7]++ + 9), new Item_Bad(k.getX(), k.getY()));
		}

		@Override
		public void mouseReleased(MouseEvent k) {
			// TODO Auto-generated method stub

		}

	}

}
