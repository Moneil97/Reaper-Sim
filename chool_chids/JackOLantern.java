package chool_chids;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JackOLantern {
	
	BufferedImage jack;
	
	int x, y, width, height;
	
	int contactX;
	
	boolean visible = true;
	
	public JackOLantern(int x, int y) {
		try {
			jack = ImageIO.read(JackOLantern.class.getResourceAsStream("/resources/jackOLantern.png"));
		} catch (IOException e) {
			e.printStackTrace();
			
			
		}
		this.x = x;
		this.y = y;
		width = 15;
		height = 15;
		contactX = x;
	}
	
	public void draw(Graphics2D g) {
		if (visible){
			//g.setColor(Color.blue);
			//g.fillRect(x, y, width, height);
			g.drawImage(jack, x, y, width, height, null);
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(contactX, y, width, height);
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setContactX(int contactX) {
		this.contactX = contactX;
	}
	
	public int getContactX() {
		return contactX;
	}

}
