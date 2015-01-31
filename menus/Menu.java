package menus;

import java.awt.Color;
import java.awt.Graphics2D;

import utils.SimpleButton;

public class Menu {

	int x, y;
	int width, height;
	
	int tempX, tempWidth = 0;
	
	boolean open = false, animationFinished = false;
	
	SimpleButton buddon;
	
	public Menu(int x, int y, int width, int height, SimpleButton buddon) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buddon = buddon;
		
		tempX = x;
	}
	
	Thread animation;
	
	public void animate(){
		
		animation = new Thread(new Runnable() {
			public void run() {
				final int sleepTime = 2;
				buddon.setLocked(true);
				animationFinished = false;
				if (open) {		
					do{
						tempX ++;
						tempWidth -= 2;				
						sleep(sleepTime);				
					}while(tempWidth > 0);
					if (tempWidth == 0)
						open = false;
				} else { //open = bool statements MUST be placed there so as long as there is a box to draw, it will be so
					open = true;
					do{
						tempX --;
						tempWidth+=2;				
						sleep(sleepTime);
					}while(tempWidth < width);	
				}
				buddon.setLocked(false);
				animationFinished = true;
				animation = null;
			}
		});
		
		animation.start();
	}
	
	private void sleep(int i){
		try {
			Thread.sleep(i);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g){
		if (open) {
			g.setColor(Color.orange);
			g.fillRect(tempX, y, tempWidth, height);
			
			g.setColor(Color.black);
			g.drawRect(tempX, y, tempWidth, height);
		}
	}
	
	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}
	
	public boolean getOpen() {
		return open;
	}

}
