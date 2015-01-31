package background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TitleBackdrop extends ScrollingBackdrop{
	
	BufferedImage backdrop;
	
	public TitleBackdrop() {
		super(0);
		
		try {
			backdrop = ImageIO.read(TitleBackdrop.class.getResourceAsStream("/resources/titleBackdrop.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.drawImage(backdrop, 0, 0, null);
		
		g.setColor(new Color(200, 0, 0));
		g.setFont(new Font("impact", Font.BOLD, 100));
		g.drawString("REAPER SIM 2014", 950 / 2 - g.getFontMetrics().stringWidth("REAPER SIM 2014") / 2, 300);
		
		g.setColor(Color.white);
		g.setFont(new Font("tahoma", Font.PLAIN, 18));
		g.drawString("Jack Dahms", 100, 215);
		g.drawString("Sam Ebe", 460, 215);
		g.drawString("Cameron O'Neil", 730, 215);
	}

}
