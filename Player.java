package graphics;
import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Color.*;
import java.awt.*;

public class Player extends Rectangle{
	int speed;
	int gravity = 10;
	boolean fall = true; 
	boolean jumping = false;
	double timer;

	Player(int speed, int width, int height){
		this.speed =speed;
		this.width = width; 
		this.height = height;
		this.x = 10;
		this.y = 10;
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
			this.x=platform.getRight().x+platform.getRight().width;
		}
		if(this.intersects(platform.getLeft())) {
			this.x=platform.getLeft().x-this.width;
			System.out.println(platform.getLeft());
			System.out.println("HI");
			//System.out.println(platform.getLeft());
		}
	}
	
}
