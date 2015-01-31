package characters;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.ImageFlip;

public class Reaper {

	private int x, y, width, height, speed = 10;
	private BufferedImage currentimage, WalkingimageR, WalkingimageL,
			stillImageR, stillImageL;
	private boolean leftHeld, rightHeld, jumpHeld, playerFrozen;
	private Rectangle bounds;

	private int groundLevel = 500;

	private int score = 0;

	private String right = "right", left = "left";//, imageDir = right;
	private BufferedImage attackStillImageR1;
	private BufferedImage attackStillImageL1;
	private BufferedImage attackStillImageR2;
	private BufferedImage attackStillImageL2;
	private BufferedImage walkingAttackImageR1;
	private BufferedImage walkingAttackImageL1;
	private BufferedImage walkingAttackImageR2;
	private BufferedImage walkingAttackImageL2;

	private String dir = right;

	private boolean attackPart2 = false;

	public Reaper(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 70;
		this.height = 160;

		try {
			// Currentimage =
			// ImageIO.read(Reaper.class.getResourceAsStream("/resources/reaper.png"));
			WalkingimageR = ImageIO.read(Reaper.class
					.getResourceAsStream("/reaperStills/Walk1Reap.png"));
			WalkingimageL = ImageFlip.getFlippedHor(WalkingimageR);
			stillImageR = ImageIO.read(Reaper.class
					.getResourceAsStream("/reaperStills/StillReap.png"));
			stillImageL = ImageFlip.getFlippedHor(stillImageR);
			walkingAttackImageR1 = ImageIO.read(Reaper.class
					.getResourceAsStream("/reaperStills/WalkAttack1Reap.png"));
			walkingAttackImageL1 = ImageFlip
					.getFlippedHor(walkingAttackImageR1);
			walkingAttackImageR2 = ImageIO.read(Reaper.class
					.getResourceAsStream("/reaperStills/WalkAttack2Reap.png"));
			walkingAttackImageL2 = ImageFlip
					.getFlippedHor(walkingAttackImageR2);
			attackStillImageR1 = ImageIO.read(Reaper.class
					.getResourceAsStream("/reaperStills/StillAttack1Reap.png"));
			attackStillImageL1 = ImageFlip.getFlippedHor(attackStillImageR1);
			attackStillImageR2 = ImageIO.read(Reaper.class
					.getResourceAsStream("/reaperStills/StillAttack2Reap.png"));
			attackStillImageL2 = ImageFlip.getFlippedHor(attackStillImageR2);

			currentimage = stillImageR;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		g.drawImage(currentimage, x, y, width, height, null);
	}

	boolean grounded = true, goingUp;

	public void move() {

		if (!playerFrozen) {
			if (rightHeld) {
				x += speed;

				dir = right;

				if (attacking)

					if (attackPart2)
						currentimage = walkingAttackImageR2;
					else
						currentimage = walkingAttackImageR1;
				else
					currentimage = WalkingimageR;
			} else if (leftHeld) {
				x -= speed;

				dir = left;

				if (attacking)
					if (attackPart2)
						currentimage = walkingAttackImageL2;
					else
						currentimage = walkingAttackImageL1;
				else
					currentimage = WalkingimageL;
			} else {

				if (dir == left)
					if (attacking)
						if (attackPart2)
							currentimage = attackStillImageL2;
						else
							currentimage = attackStillImageL1;
					else
						currentimage = stillImageL;
				else if (attacking)
					if (attackPart2)
						currentimage = attackStillImageR2;
					else
						currentimage = attackStillImageR1;
				else
					currentimage = stillImageR;
			}

			if (x < 0)
				setX(0);
			else if (x + width > 950) {
				setX(950 - width);
			}

			jumpingMechanics();

			attackTimer();

			// updateBounds();
		}
	}

	boolean attacking = false;
	long attackTime = 1000000000 * 1 / 2;
	long startAttack = 0;
	public void attackTimer() {

		if (!attackReady) {
			long now = System.nanoTime();
			if (now - startAttack > attackTime) {
				attackReady = true;
				attacking = false;
			} else if (now - startAttack > attackTime / 2) {
				attackPart2 = true;
			} else {
				attackReady = false;
				attackPart2 = false;
			}
		}
	}

	boolean attackReady = false;

	public void attack() {

		long now = System.nanoTime();

		if (attackReady) {
			attacking = true;
			startAttack = now;
			attackReady = false;
		}
	}

	// private void updateBounds() {
	// bounds = new Rectangle(x,y,width,height);
	//
	// }

	// public void setImageDir(String s){
	//
	// if (s == left){
	// if (!(imageDir == left))
	// {
	// currentimage = ImageFlip.getFlippedHor(currentimage);
	// imageDir = left;
	// }
	// }
	// else{
	// if (!(imageDir == right))
	// {
	// currentimage = ImageFlip.getFlippedHor(currentimage);
	// imageDir = right;
	// }
	// }
	//
	// }

	private final double gravity = 7.5;
	private final int initVel = -80;
	private double time = 0;
	private int initY;

	private void jumpingMechanics() {
		if (grounded) {
			if (jumpHeld) {
				grounded = false;
				time = 0;
				initY = y;
			}
		} else { // In air
			y = (int) (initY + (initVel * time / 2) + (gravity * Math.pow(
					time / 2, 2)));
			time++;
			/** 286 from 500, for a jump height of 214 pixels */
			// //records jump height from 500
			// topFeetPos = getFeetPos() < topFeetPos ? getFeetPos() :
			// topFeetPos;
			// say(topFeetPos);

			if (getFeetPos() > groundLevel) {
				// put feet on the ground (if they went too far)
				setFeetTo(groundLevel);
				grounded = true;
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	private void setFeetTo(int newFeetPos) {
		y = newFeetPos - height;
	}

	private int getFeetPos() {
		return y + height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSpeed() {
		return speed;
	}

	public void setspeed(int xSpeed) {
		this.speed = xSpeed;
	}

	public BufferedImage getImage() {
		return currentimage;
	}

	public void setImage(BufferedImage image) {
		this.currentimage = image;
	}

	public boolean isLeftHeld() {
		return leftHeld;
	}

	public void setLeftHeld(boolean leftHeld) {
		this.leftHeld = leftHeld;
	}

	public boolean isRightHeld() {
		return rightHeld;
	}

	public void setRightHeld(boolean rightHeld) {
		this.rightHeld = rightHeld;
	}

	public boolean isJumpHeld() {
		return jumpHeld;
	}

	public void setJumpHeld(boolean jumpHeld) {
		this.jumpHeld = jumpHeld;
	}

	public void freezePlayer(boolean b) {
		playerFrozen = b;
	}

	public void say(Object s) {
		System.out.println(this.getClass().getName() + ": " + s);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void changeScoreBy(int change) {
		score += change;
	}

}
