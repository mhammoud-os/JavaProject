package graphics;
import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Color.*;
import java.awt.*;

public class Player extends Rectangle{
	int speed, startX, startY;
	int gravity = 10;
	boolean fall = true; 
	boolean jumping = false;
	double timer;
	int lifeCount = 3;

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

	void moveRight() {
		this.x+=this.speed;
	}

	void moveLeft() {
		this.x-=this.speed;
	}
	void fall() {
		if(this.jumping) {
			this.y-=gravity;
		}
		else if(fall) {
			this.y+=gravity;
		}
	}
	
	void jumpdown() {
		this.jumping = false;
		gravity +=1; 
	}
	
	void DetectPlatform(Platform platform) {
		if (this.intersects(platform.getTop())) {
			this.fall=false;
			this.gravity = 10;
			this.y = platform.getTop().y-this.height;
		}else this.fall = true;

		if (this.intersects(platform.getBottom())) {
			this.fall=true;
			this.jumping = false;
			this.y = platform.getBottom().y+(platform.height);
		}
		
		if(this.intersects(platform.getRight())) {
			this.x += speed;
		}
		if(this.intersects(platform.getLeft())) {
			this.x -= speed;
		}
	}
	Rectangle getTop() {
    	return new Rectangle(this.x, this.y, this.width, (int)this.height/2);
	}
    Rectangle getBottom() {
    	return new Rectangle(this.x, this.y+(this.height/4)*3, this.width, (int)this.height/2);
    }
    void setup() {
    	this.x = this.startX; 
    	this.y = this.startY;
    	this.fall = true;
    }
    boolean getPlayerColide(Player opponent) {
    	if (this.getBottom().intersects(opponent.getTop())) {
    		this.setup();
    		opponent.setup();
    		this.lifeCount -=1;
    		return true;
		}
		return false;
    }
    

	
}
