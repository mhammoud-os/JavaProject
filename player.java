package graphics;
import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Color.*;
import java.awt.*;


public class Player extends Rectangle{
	int speed;
	int gravity = 10;
	boolean fall = true; 
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
		}
	}
	void jump() {
		this.y-=gravity+10;
	}
	void jumpdown() {
		//this.y -= gravity; 
		
	}
	
	void DetectPlatform(Rectangle rect) {
		//if (this.y > rect.y && this.y < rect.y+rect.height && this.x > rect.x && this.x < rect.x+rect.width) {
		if (this.intersects(rect)) {
			this.fall=false;
			this.y = rect.y-this.width;
		}else this.fall = true;
	}
	void detectKeys() {
	}
}
