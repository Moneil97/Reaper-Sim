package background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Tree implements IDecoration{
	
	private int x, y;
	private int width = 200, height = 25;
	
	private int TRUNK_UNIT_MAX = 4; //not final
	private int trunkUnits;
	
	private BufferedImage house, base, platform, trunk;
	
	public Tree() {
		trunkUnits = new Random().nextInt(TRUNK_UNIT_MAX);
		
		try {
			base = ImageIO.read(Tree.class.getResourceAsStream("/resources/trunkBase.png"));
			house = ImageIO.read(Tree.class.getResourceAsStream("/resources/trunkTop.png"));
			trunk = ImageIO.read(Tree.class.getResourceAsStream("/resources/trunkSegment.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(base, x, 500 - height, width, height, null);
		
		for (int i = 0; i < trunkUnits; i++) {
			g.drawImage(trunk, x, 500 - height - (height + height * i), width, height, null);
		}
		g.drawImage(house, x, 500 - height - height * trunkUnits - width, width, width, null); //because its a square.
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setTrunkUnits(int trunkUnits) {
		this.trunkUnits = trunkUnits;
	}

}
