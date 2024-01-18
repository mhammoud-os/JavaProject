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
	int count = 0;
	
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
		if(fall) {
			this.y+=gravity;
			this.count ++;
		}
		System.out.println(count);
	}
	
	void jump() {	
		this.jumping = true;
		if(this.jumping) {
			this.y-=gravity+10;
		}
	}
	void jumpdown() {
		this.jumping = false;
		//this.y -= gravity; 
		
	}
	
	void DetectPlatform(Platform platform) {
		//if (this.y > rect.y && this.y < rect.y+rect.height && this.x > rect.x && this.x < rect.x+rect.width) {
		
		if (this.intersects(platform.getTop())) {
			this.fall=false;
			this.y = platform.getTop().y-this.height;
		}else this.fall = true;

		if (this.intersects(platform.getBottom())) {
			this.fall=true;
			this.jumping = false;
			this.y = platform.getBottom().y+(platform.height);
			System.out.println("HI");
		}
		
	}
	
}
