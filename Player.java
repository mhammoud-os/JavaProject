/*Player.java
 * By: Malek Hammoud
 * January 22, 2023
 * This is the main Player class. Players are drawn and key input is taken in seperate file*/

import java.awt.*;
public class Player extends Rectangle{
	int speed, startX, startY;
	int gravity = 10;
	boolean fall = true; 
	boolean jumping = false;
	double timer;
	int lifeCount = 3;

	boolean movingRight = false;
	boolean movingLeft= false;
	
	Player(int startX, int startY, int speed, int width, int height){
		this.speed =speed;
		this.width = width; 
		this.height = height;
		this.x = 10;
		this.y = 10;
		this.startX = startX;
		this.startY = startY;
		this.setup();
		this.fall();
	}

	/*Move the player right*/
	void moveRight() {
		this.movingRight = true;
		this.movingLeft = false;
		this.x+=this.speed;
	}

	/*Move the player left */
	void moveLeft() {
		this.movingLeft= true;
		this.movingRight= false;
		this.x-=this.speed;
	}

	/*Move down, called automatically*/
	void fall() {
		if(this.jumping) {
			this.y-=gravity;
		}
		else if(fall) {
			this.y+=gravity;
		}
	}
	
	/*Move player down faster*/
	void jumpdown() {
		this.jumping = false;
		gravity +=1; 
	}
	
	/**
	 * Detects wheather if landed on a platform
	 * @param platform
	 */
	void DetectPlatform(Platform platform) {
		//if the player intersects with the top of the platform, stop falling
		if (this.intersects(platform.getTop())) {
			this.fall=false;
			this.gravity = 10;
			this.y = platform.getTop().y-this.height;
		}else this.fall = true;

		//if the player intersects with the bottom of the platform stop jumping
		if (this.intersects(platform.getBottom())) {
			this.fall=true;
			this.jumping = false;
			this.y = platform.getBottom().y+(platform.height);
		}
		
		//if the player intersects with the side of the a platform then stop moving
		if(this.intersects(platform.getRight())) {
			this.x += speed;
		}
		if(this.intersects(platform.getLeft())) {
			this.x -= speed;
		}
	}
	/**
	 * @return Top Rectangle
	 */
	Rectangle getTop() {
    	return new Rectangle(this.x, this.y, this.width, (int)this.height/2);
	}
	/**
	 * @return Bottom Rectangle
	 */
    Rectangle getBottom() {
    	return new Rectangle(this.x, this.y+(this.height/4)*3, this.width, (int)this.height/2);
    }
    int sidesWidth = 10;
    int heightOffset = 10;
    /**
     * @return left Rectangle
     */
    Rectangle getLeft() {
    	return new Rectangle(this.x-this.sidesWidth, this.y+heightOffset, sidesWidth, this.height-(heightOffset*2));
    }
    /**
     * @return right Rectangle
     */
    Rectangle getRight() {
    	return new Rectangle(this.x+this.width, this.y+heightOffset, sidesWidth, this.height-(heightOffset*2));
    }
    
    /**
     * sets up values
     */
    void setup() {
    	this.x = this.startX; 
    	this.y = this.startY;
    	this.fall = true;
    }
    
    /**
     * Checks if collided with oponent
     * @param opponent object
     */
    void getPlayerColide(Player opponent) {
    	//If the bottom of this player intersects with the top of another player reset everything and remove life from opponent
    	if (this.getBottom().intersects(opponent.getTop()) && !this.jumping) {
    		this.setup();
    		opponent.setup();
    		opponent.lifeCount -=1;
		}
    	//if the player collides with another player then prevent from going through
    	if (this.getLeft().intersects(opponent.getRight()) && this.movingLeft) {
    		this.x = opponent.getRight().x+opponent.sidesWidth+this.sidesWidth-7;
    	}
    	if (this.getRight().intersects(opponent.getLeft()) && this.movingRight) {
    		this.x = opponent.getLeft().x-this.sidesWidth-this.width+7;
    	}
    }
}
