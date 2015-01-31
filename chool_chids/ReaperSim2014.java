package chool_chids;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import background.ScrollingBackdrop;
import background.TitleBackdrop;
import menus.Credits;
import characters.Reaper;
import utils.UpdatesAndFrames;
import utils.SimpleButton;

/**
 * TODO
 * 
 * prevent player from speeding up after transition
 */

public class ReaperSim2014 extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	
	//Dimensions of frame
	private static int HEIGHT = 700;
	private static int WIDTH = 950;
	
	//keeps track of ups and fps
	private UpdatesAndFrames uaf = new UpdatesAndFrames();
	private int updateSleep = 20, updateTarget = 1000/updateSleep;//40sleep = 25fps target
	
	//the player!
	private Reaper player = new Reaper(500, 340);
	
	//number of backdrops, includes title
	private int gameLength = 5;
	
	//resources
	private ScrollingBackdrop[] backdrops;
	private static BufferedImage frameIcon;
	private static BufferedImage jacks;
	
	//main menu buttons
	private SimpleButton creditsButton = new SimpleButton(25, 555, 200, 50);
	private SimpleButton instructionsButton = new SimpleButton(250, 555, 200, 50);
	
	//boundaries for screen scrolling
	private int titleBound = 800;
	private int moveBound = 600;
	
	//screens to be drawn
	private int[] screens = {0, 0};
	
	//used for dramatic scrolling
	private boolean lockMovement = false;
	
	//gamestate
	private static String gamestate = "title";
	
	//Graphics object
	private Graphics2D g;
	
	//menus
	private  Credits creditsMenu = new Credits(WIDTH / 2, 100, 400, 300, creditsButton);
	
	//how far the graphics moves
	private int translateBy = 0;
	
	Thread gl;
			
	private void gameUpdate() {
		if (!lockMovement) 
			player.move();
		if (player.getX() > 850) {
			say("check");
//			try {
//				InputStream is = ReaperSim2014.class.getResourceAsStream("/resources/test.mp4");
//				Desktop.getDesktop().open(new File(""));
//			} catch (IOException e) {
				
				say("Error");
				
				if (gl == null){

					gl = new Thread(new Runnable() {
						public void run() {
//							new DecodeAndPlayAudioAndVideo();
						}
					});
	
					gl.start();
				}
				
//				e.printStackTrace();
//			}
//			System.exit(0);
		}
			
		if (gamestate.equals("draw")) {
			if (player.getX() + player.getWidth() > moveBound && screens[0] < gameLength - 1) {
				shift(player.getSpeed());
				for (JackOLantern j : backdrops[screens[0]].getJacks())
					j.setContactX(j.getContactX() - player.getSpeed());
			}
			
//			System.out.println(player.getX() + player.getWidth() + ":" + backdrops[screens[0]].getJacks()[0].getContactX() + ":" + screens[0]);
			
			
			for (int i = screens[0]; i <= screens[1] && i < gameLength; i++) {
				for (JackOLantern j : backdrops[i].getJacks()) {
					if (player.getBounds().intersects(j.getBounds()) && j.isVisible()) {
						j.setVisible(false);
						player.changeScoreBy(1);
					}
				}
			}
			
			backdrops[screens[0]].moveKids();
			if (screens[1] < gameLength)
				backdrops[screens[1]].moveKids();
				
			
						
		} else if (gamestate.equals("title")) {
			screens[0] = 0;
			screens[1] = 1;
			if (player.getX() > titleBound && !creditsMenu.getOpen()){
				transition(WIDTH);
			}
		}		
	}
	
	String positionsPrev = "";
		
	public void paintComponent(Graphics g1) {		
		super.paintComponent(g1);
		g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					
		g.translate(-1 * translateBy, 0);
					
		backdrops[screens[0]].draw(g);
		if (screens[1] < gameLength)
			backdrops[screens[1]].draw(g);
		
		if (gamestate.equals("title")) {				
			creditsButton.draw(g);
			instructionsButton.draw(g);	
			creditsMenu.draw(g);
		}
		
		//for things that do not move backwards
		g.translate(translateBy, 0); 
		player.draw(g);
		if (!gamestate.equals("title")) {
			g.setFont(new Font("hobo std", Font.PLAIN, 14));
			g.drawString(String.valueOf(player.getScore()), 45, 23);
			g.drawImage(jacks, 10, 0, 30, 30, null);
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("hobo std", Font.PLAIN, 12));
		g.drawString("FPS: " + uaf.getFPS(), 45, 40);
		g.drawString("UPS: " + uaf.getUPS() + "", 45, 60);
						
	}
	
	public void shift(int shift) {
		int originalTranslation = translateBy;
		int screenNum;
		for (int i = 0; i <= shift; i += 5) {
			translateBy = originalTranslation + i;
			player.setX(moveBound - player.getWidth());
			screenNum = translateBy/WIDTH;
			screens[0] = screenNum;
			screens[1] = screenNum + 1 > 10 ? 10 : screenNum + 1;
		}		
	}
	
	//private final byte maxScreens = 10;
	
	public void transition(int shift) {
		lockMovement = true;
		for (int i = 0; i <= shift; i += 5) {
			translateBy = i;
			//say("transitioning");
			sleep(10);

			if (i > WIDTH - titleBound)
				player.setX(player.getX() - 5);
		}
		lockMovement = false;
		gamestate = "draw";
	}
	
	public static void main(String[] args) {	
		JFrame loading = new JFrame();
		loading.setSize(200, 100);
		loading.setLocationRelativeTo(null);
		loading.setTitle("Loading Reaper Sim 2014");
		try {
			loading.add(new JLabel(new ImageIcon(ImageIO.read(ReaperSim2014.class.getResourceAsStream("/resources/loading.png")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loading.setUndecorated(true);
		loading.setVisible(true);
		
		ReaperSim2014 dontFear = new ReaperSim2014();
		
		JFrame frame = new JFrame();
		frame.setTitle("Reaper Sim 2014");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(dontFear);
		frame.setIconImage(frameIcon);	
	
		frame.addKeyListener(dontFear);
		
		frame.setVisible(true);
		loading.dispose();
	}
	
	public ReaperSim2014() {		
		try {
			backdrops = new ScrollingBackdrop[gameLength];
			backdrops[0] = new TitleBackdrop();
			for (int i = 1; i < backdrops.length; i++) {
				backdrops[i] = new ScrollingBackdrop(i);
			}
			//frameIcon = ImageIO.read(ReaperSim2014.class.getResourceAsStream("/resources/icon.png"));
			frameIcon = ImageIO.read(ReaperSim2014.class.getResourceAsStream("/resources/ReapSimEmblem.png"));
			jacks = ImageIO.read(ReaperSim2014.class.getResourceAsStream("/resources/jackOLantern.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		creditsButton.setText("Credits").setFont(new Font("Hobo Std", Font.PLAIN, 30)).setColor(new Color(255, 175, 85))
		.setBorderColor(Color.black).setHoverColor(new Color(220, 140, 50)).setFontColor(Color.black)
		.setPressColor(new Color(160, 70, 240)).setToggleable(true);
		
		instructionsButton.setText("Story").setFont(new Font("Hobo Std", Font.PLAIN, 30)).setColor(new Color(255, 175, 85))
		.setBorderColor(Color.black).setHoverColor(new Color(220, 140, 50)).setFontColor(Color.black)
		.setPressColor(new Color(160, 70, 240)).setToggleable(true);
		
		addMouseListener(this);
		addMouseMotionListener(this);
						
		Thread t = new Thread(this);
		t.start();
		
		
	}
		
	/**
	 * Listeners
	 */
		
	@Override
	public void mouseMoved(MouseEvent e) {	
		creditsButton.onMouseHover(e);
		instructionsButton.onMouseHover(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		creditsButton.onMouseHover(e);
		instructionsButton.onMouseHover(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		creditsButton.onMousePress(e);
		instructionsButton.onMousePress(e);
		
		
		
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {	
		
		if (gamestate.equals("title")){
			if (creditsButton.onMouseRelease(e)){
				creditsMenu.animate();
			}
			if (instructionsButton.onMouseRelease(e)){
				
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			player.setRightHeld(true);
		}
		else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			player.setLeftHeld(true);
		}
		else if (key == KeyEvent.VK_SPACE){
			player.setJumpHeld(true);
		}
		else if (key == KeyEvent.VK_ADD || key == KeyEvent.VK_PLUS){
			player.setspeed(player.getSpeed() + 5);
		}
		else if (key == KeyEvent.VK_SUBTRACT || key == KeyEvent.VK_MINUS){
			player.setspeed(player.getSpeed() - 5);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent k) {
		int key = k.getKeyCode();
		
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			player.setRightHeld(false);
		}
		else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			player.setLeftHeld(false);
		}
		else if (key == KeyEvent.VK_SPACE){
			player.setJumpHeld(false);
		}
	}
	
	private void gameTimer(){
		  final double GAME_HERTZ = updateTarget;
	      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	      double lastUpdateTime = System.nanoTime();
	      
	      while (true)
	      {
	         double now = System.nanoTime();
	            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
	            {
	            	gameUpdate();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					uaf.addGameUpdate();
					uaf.update();
					sleep(updateSleep/2);
	               //sleep(Math.min(updateSleep, 20 /*Make this a variable later*/));
	            }
	            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
	            {
	               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
	            }
	      }
	}

	@Override
	public void run() {		
		Thread paintIt = new Thread(new Runnable() {
			public void run() {
				while (true){
					repaint();
					uaf.addFrameUpdate(); //1.5 Million FPS
					sleep(updateSleep/2);
				}
			}
		});

		Thread gameLoop = new Thread(new Runnable() {
			public void run() {
				gameTimer();
			}
		});

		paintIt.start();
		gameLoop.start();
	}
	
	/**
	 * Finished worker methods
	 */		

	private void sleep(int i){
		try {
			Thread.sleep(i);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}	

	/**
	 * Worthless crap
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			player.attack();
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void keyTyped(KeyEvent k) {}



}
