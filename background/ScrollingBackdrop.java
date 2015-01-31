package background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import characters.Kid;
import chool_chids.JackOLantern;

public class ScrollingBackdrop {
	
	private int screenNumber;
	private JackOLantern[] jacks;
	private BufferedImage road;
	private BufferedImage sky;
	private IDecoration[] choices;
	private IDecoration[] decorations;
	private Kid[] kids;
	
	public ScrollingBackdrop(int i) {
		screenNumber = i;
		try {
			road = ImageIO.read(ScrollingBackdrop.class.getResourceAsStream("/resources/loopingRoad.png"));
			sky = ImageIO.read(ScrollingBackdrop.class.getResourceAsStream("/resources/loopingBackdrop.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		kids = new Kid[2];
		jacks = new JackOLantern[3];
		decorations = new IDecoration[3];
		choices = new IDecoration[5];
		
		choices[0] = new Tree();
		choices[1] = new Tree();
		choices[2] = new Tree();
		choices[3] = new Tree();
		choices[4] = new Tree();
		
		Random rand = new Random();
		
		for (int k = 0; k < decorations.length; k++) {
			decorations[k] = choices[rand.nextInt(5)];
			decorations[k].setX(950 / 3 * k);
		}
		
		for (int m = 0; m < jacks.length; m++) 
			jacks[m] = new JackOLantern(rand.nextInt(950), rand.nextInt(200) + 300);
		
		final int groundLevel = 500, kidHeight = 120;
		for (int p = 0; p < kids.length; p++)
			kids[p] = new Kid(rand.nextInt(950), groundLevel-kidHeight, 70, kidHeight);
		
	}
	
	public void moveKids(){
		for (Kid k : kids)
			k.ai();
	}
	
	public void draw(Graphics2D g) {
		g.translate(950 * screenNumber, 0);
		
		g.drawImage(road, 0, 500, null);
		g.drawImage(sky, 0, 0, null);

		for (IDecoration d : decorations) 
			d.draw(g);
		for (JackOLantern j : jacks)
			j.draw(g);
		for(Kid child : kids)
			child.draw(g);

		g.setColor(Color.black);
//		g.drawString("" + screenNumber, 30, 30);
		
		g.translate(-950 * screenNumber, 0);
		
	}
	
//	public void drawBackdrop(Graphics2D g) {
//		g.translate(950 * screenNumber, 0);
//		
//		g.drawImage(road, 0, 500, null);
//		g.drawImage(sky, 0, 0, null);
//		
//		g.translate(-950 * screenNumber, 0);
//		
//	}
//	
//	public void drawObjects(Graphics2D g) {
//		g.translate(950 * screenNumber, 0);
//
//		for (IDecoration d : decorations) 
//			d.draw(g);
//		for (JackOLantern j : jacks)
//			j.draw(g);
//		for(Kid child : kids)
//			child.draw(g);
//
//		g.setColor(Color.black);
////		g.drawString("" + screenNumber, 30, 30);
//		
//		g.translate(-950 * screenNumber, 0);
//		
//	}
	
	public JackOLantern[] getJacks() {
		return jacks;
	}
}
