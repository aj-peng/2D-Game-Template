package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {

		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - gp.tileSize/2;
		screenY = gp.screenHeight/2 - gp.tileSize/2;
		
		solidArea = new Rectangle();
		solidArea.x = 12;
		solidArea.y = 22;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 24;
		solidArea.height = 26;
		
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {

		worldX = gp.tileSize * 15;
		worldY = gp.tileSize * 9;
		speed = 4;
		direction = "down";
		isIdle = true;
	}

	public void getPlayerImage() {
		try {

			down1 = ImageIO.read(getClass().getResourceAsStream("/player/knight_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/knight_down_2.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/player/knight_down_3.png"));
			down4 = ImageIO.read(getClass().getResourceAsStream("/player/knight_down_4.png"));

			up1 = ImageIO.read(getClass().getResourceAsStream("/player/knight_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/knight_up_2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/player/knight_up_3.png"));
			up4 = ImageIO.read(getClass().getResourceAsStream("/player/knight_up_4.png"));

			left1 = ImageIO.read(getClass().getResourceAsStream("/player/knight_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/knight_left_2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/player/knight_left_3.png"));
			left4 = ImageIO.read(getClass().getResourceAsStream("/player/knight_left_4.png"));

			right1 = ImageIO.read(getClass().getResourceAsStream("/player/knight_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/knight_right_2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/player/knight_right_3.png"));
			right4 = ImageIO.read(getClass().getResourceAsStream("/player/knight_right_4.png"));

			downIdle1 = ImageIO.read(getClass().getResourceAsStream("/player/knight_down_idle_1.png"));
			downIdle2 = ImageIO.read(getClass().getResourceAsStream("/player/knight_down_idle_2.png"));
			
			rightIdle1 = ImageIO.read(getClass().getResourceAsStream("/player/knight_right_idle_1.png"));
			rightIdle2 = ImageIO.read(getClass().getResourceAsStream("/player/knight_right_idle_2.png"));
			
			leftIdle1 = ImageIO.read(getClass().getResourceAsStream("/player/knight_left_idle_1.png"));
			leftIdle2 = ImageIO.read(getClass().getResourceAsStream("/player/knight_left_idle_2.png"));
			
			upIdle1 = ImageIO.read(getClass().getResourceAsStream("/player/knight_up_idle_1.png"));
			upIdle2 = ImageIO.read(getClass().getResourceAsStream("/player/knight_up_idle_2.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void updateSpriteCounter() {
		spriteCounter++;
		if (spriteCounter > 8) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 3;
			} else if (spriteNum == 3) {
				spriteNum = 4;
			} else if (spriteNum == 4) {
				spriteNum = 1;
			} 
			spriteCounter = 0;
		}
	}
	
	public void update() {
		if (idleTime < 5) {
			idleTime++;
		} else {
			isIdle = true;
			updateSpriteCounter();
		}

		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true) {
			if (idleTime > 0) {
				idleTime = 0;
				isIdle = false;
			}
			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cDetector.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cDetector.checkObject(this, true);
			pickUpObject(objIndex);
			
			if(collisionOn == false) {
				if(direction.equals("up")) { worldY -= speed; }
				else if (direction.equals("down")) {	worldY += speed; }
				else if (direction.equals("left")) {	worldX -= speed; } 
				else if (direction.equals("right")) { worldX += speed; }
			}
			
			updateSpriteCounter();
		}
	}
	
	public void pickUpObject(int i) {
		if (i != 999) {
			
			String objectName = gp.obj[i].name;
			if (objectName.equals("Key")) {
				hasKey++;
				gp.obj[i] = null;
			} else if (objectName.equals("Door")) {
				if (hasKey > 0) {
					gp.obj[i] = null;
					hasKey--;
				}
			}
			
		}
	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		if (isIdle == true) {
			if (direction.equals("up")) {
				if (spriteNum == 1 || spriteNum == 2) {
					image = upIdle1;
				} else if (spriteNum == 3 || spriteNum == 4) {
					image = upIdle2;
				}
			} else if (direction.equals("down")) {
				if (spriteNum == 1 || spriteNum == 2) {
					image = downIdle1;
				} else if (spriteNum == 3 || spriteNum == 4) {
					image = downIdle2;
				}
			} else if (direction.equals("right")) {
				if (spriteNum == 1 || spriteNum == 2) {
					image = rightIdle1;
				} else if (spriteNum == 3 || spriteNum == 4) {
					image = rightIdle2;
				}
			} else if (direction.equals("left")) {
				if (spriteNum == 1 || spriteNum == 2) {
					image = leftIdle1;
				} else if (spriteNum == 3 || spriteNum == 4) {
					image = leftIdle2;
				}
			}
		} else {
			if (direction.equals("up")) {
				if (spriteNum == 1) {
					image = up1;
				} else if (spriteNum == 2) {
					image = up2;
				} else if (spriteNum == 3) {
					image = up3;
				} else if (spriteNum == 4) {
					image = up4;
				}
			} else if (direction.equals("down")) {
				if (spriteNum == 1) {
					image = down1;
				} else if (spriteNum == 2) {
					image = down2;
				} else if (spriteNum == 3) {
					image = down3;
				} else if (spriteNum == 4) {
					image = down4;
				}
			} else if (direction.equals("left")) {
				if (spriteNum == 1) {
					image = left1;
				} else if (spriteNum == 2) {
					image = left2;
				} else if (spriteNum == 3) {
					image = left3;
				} else if (spriteNum == 4) {
					image = left4;
				}
			} else if (direction.equals("right")) {
				if (spriteNum == 1) {
					image = right1;
				} else if (spriteNum == 2) {
					image = right2;
				} else if (spriteNum == 3) {
					image = right3;
				} else if (spriteNum == 4) {
					image = right4;
				}
			}
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
