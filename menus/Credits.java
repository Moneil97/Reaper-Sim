package menus;

import java.awt.Font;
import java.awt.Graphics2D;

import utils.SimpleButton;

public class Credits extends Menu{
			
	public Credits(int x, int y, int width, int height, SimpleButton button) {
		super(x, y, width, height, button);
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
		if (animationFinished && open) {
			g.setFont(new Font("hobo std", Font.PLAIN, 30));
			g.drawString("Programming and Design", 
					950 / 2 - g.getFontMetrics().stringWidth("Programming and Design") / 2, 
					150);
			
			g.setFont(new Font("hobo std", Font.PLAIN, 20));
			g.drawString("Jack Dahms",
					950 / 2 - g.getFontMetrics().stringWidth("Jack Dahms") / 2,
					200);
			g.drawString("Cameron O'Neil",
					950 / 2 - g.getFontMetrics().stringWidth("Cameron O'Neil") / 2,
					230);
			
			g.setFont(new Font("hobo std", Font.PLAIN, 30));
			g.drawString("Most Artwork", 
					950 / 2 - g.getFontMetrics().stringWidth("Most Artwork") / 2, 
					280);
			
			g.setFont(new Font("hobo std", Font.PLAIN, 20));
			g.drawString("Sam Ebe",
					950 / 2 - g.getFontMetrics().stringWidth("Sam Ebe") / 2,
					320);
		}
	}
	
}
