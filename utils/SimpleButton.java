package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class SimpleButton {
	
	/**
	 * Variables used in construction
	 * can be set and get
	 */
	
	String text;
	
	Font font;
	
	int x, y;
	
	int width, height;
	
	Color color, hoverColor, pressColor, borderColor, fontColor;
	
	boolean toggleable;
	
	boolean toggle;
	
	//internal vars
	
	Color currentColor;
	
	Rectangle bounds;
	
	boolean pressedInside;
		
	public SimpleButton (int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics2D g) {		
		g.setColor(currentColor);
		g.fill(bounds);
		
		g.setColor(fontColor);
		g.setFont(font);
		String[] brokenLines = text.split("\n");
		for (int i = 0; i < brokenLines.length; i++) {
			g.drawString(brokenLines[i], x + width / 2 - g.getFontMetrics().stringWidth(brokenLines[i]) / 2, 
					y 																	//for each line, initial height
					+ (height / 2)														//plus half the size of the button
					+ (g.getFontMetrics().getHeight() / 2)								//plus one fourth of the height of one line
					+ (g.getFontMetrics().getHeight() * i)								//plus the height of a line times the line number
					- g.getFontMetrics().getHeight() * (brokenLines.length - 1) / 2);	//minus the half the height of the total number of lines
			
		}
				
		g.setColor(borderColor);
		g.draw(bounds);
	}
	
	public void onMouseHover(MouseEvent e) {		
		if (bounds.contains(e.getPoint())){
			if (toggleable && toggle)
				; //do nothing
			else
				currentColor = hoverColor;
		}
		else
			if (toggleable && toggle)
				; // do nothing
			else
				currentColor = color;
	}
	
	private boolean locked = false;
	
	public void onMousePress(MouseEvent e) {
		if (!locked){
			if (bounds.contains(e.getPoint())) {
				pressedInside = true;
				if (toggleable && toggle)
					; //do nothing
				else
					currentColor = pressColor;
			} else
				if (toggleable && toggle)
					; //do nothing
				else
					currentColor = color;
		}
		else{
			say("This button is locked, so stop pressing it you impatient dick.");
		}
	}
	
	public boolean onMouseRelease(MouseEvent e) {
		
		if (pressedInside && bounds.contains(e.getPoint())) {
			pressedInside = false;
			if (toggleable) {
				toggle = !toggle;
				if (!toggle) 
					currentColor = hoverColor;
			} else
				currentColor = hoverColor;
			return true;
		} else {
			if (toggleable)
				; //do nothing
			else
				currentColor = color;
			return false;
		}
	}
		
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 * Setters
	 */
	
	public SimpleButton setToggle(Boolean toggle) {
		this.toggle = toggle;
		return this;
	}
	
	public SimpleButton setToggleable(Boolean toggleable) {
		this.toggleable = toggleable;
		return this;
	}

	public SimpleButton setText(String text) {
		this.text = text;
		return this;
	}

	public SimpleButton setFont(Font font) {
		this.font = font;
		return this;
	}
	
	public SimpleButton setFontColor(Color fontColor) {
		this.fontColor = fontColor;
		return this;
	}

	public SimpleButton setColor(Color color) {
		this.color = color;
		currentColor = color;
		return this;
	}

	public SimpleButton setHoverColor(Color hoverColor) {
		this.hoverColor = hoverColor;
		return this;
	}

	public SimpleButton setPressColor(Color pressColor) {
		this.pressColor = pressColor;
		return this;
	}

	public SimpleButton setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
		return this;
	}
	
	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

}
