package characters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.ImageFlip;

public class Kid {

	private int x,y,width,height, speed = 2;
	private BufferedImage image;
	
	public Kid(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		try {
			image = ImageIO.read(Reaper.class.getResourceAsStream("/jack/JackStill.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Kid() {
		// TODO Auto-generated constructor stub
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics2D g) {
		//g.setColor(Color.BLUE);
		//g.fill(getBounds());
		//g.drawImage(image, x, y, width, height, null);
		
		if (direction == left){
			g.drawImage(image, x+width, y, -width, height, null);
		}
		else{
			g.drawImage(image, x, y, width, height, null);
		}
	}
	
	private int sexToNanos = 1000000000;
	
	private String left = "left", right = "right", direction = leftOrRight();
	private long minTimeBetweenDirectionChange = 2 * sexToNanos, lastDirectionChange = System.nanoTime();
	
	public void ai(){
		
		long now = System.nanoTime();
		
		if (now - lastDirectionChange > minTimeBetweenDirectionChange){
			if (percentChance(1.0)){
				switchDirection();
				lastDirectionChange = now;
			}
		}
		
		if (direction == right){
			x += speed;
		}
		else{
			x-= speed;
		}
		
	}
	
	private void switchDirection() {
		if (direction == left)
			direction = right;
		else
			direction = left;
	}

	private String leftOrRight() {
		if (Math.random() > .5)
			return left;
		else
			return right;
	}
	
	private boolean percentChance(double i) {
		
//		double rand = rand(0,100);
//		if(rand <= i){
//			say(rand + " < " + i);
//			return true;
//		}
//		say(rand + " > " + i);	
//		return false;
		
		if(rand(0,100) <= i)
			return true;
		return false;
	}

	private double rand(int min, int max) {
		// [min, max]
		return min + (Math.random() * ((max - min) + 1));
	}
	
	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}
	
//	public static void main(String args[]){
//		Kid k = new Kid();
//		
//		for(int i =0; i <100; i++)
//			k.percentChance(1.5);
//	}

}
