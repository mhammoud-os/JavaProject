package graphics;
import java.awt.Color;

import hsa2.GraphicsConsole;
import java.awt.*;
import java.util.*;

public class JavaProject {

	final static int WIDTH = 1280;
	final static int HEIGHT = 720;
	GraphicsConsole gc = new GraphicsConsole(WIDTH,HEIGHT);
	Platform first = new Platform(0,700, 1280,20);
	Platform secound = new Platform(200,400, 120,50);

	public static void main(String[] args) {
		new JavaProject();
	}

	void setup() {
		gc.setTitle("JavaProject");
		gc.setAntiAlias(true);
		gc.setLocationRelativeTo(null);
		gc.setBackgroundColor(new Color(255,255,255));
		gc.clear();
		

		gc.enableMouse();
		gc.enableMouseMotion();
	}
	Player player = new Player(10,40,40);
	void drawGraphics() {
		synchronized(gc) {
			gc.clear();
			gc.setColor(new Color(100,100,100));
			gc.fillRect(first.x,first.y, first.width, first.height);
			gc.fillRect(secound.x,secound.y, secound.width, secound.height);
			gc.setColor(new Color(0,0,0));
			gc.fillRect(player.x, player.y, player.width, player.height);
			gc.fillRect(secound.getBottom().x, secound.getBottom().y, secound.getBottom().width, secound.getBottom().height);
			//player.moveRight();
			player.fall();

			player.DetectPlatform(first);
			player.DetectPlatform(secound);
			

		}
	}
	void detectKeys(){
			if (gc.isKeyDown(37)) {	//isKeyDown uses keyCodes. Left arrow
				player.moveLeft();
			}
			if (gc.isKeyDown(39)) { //right
				player.moveRight();
			}
			if (gc.isKeyDown(38)) {	//up
				player.jump();
			}
			if (gc.isKeyDown(40)) {	//down
				player.jumpdown();
			}
	}
	static int count = 0;
	JavaProject() {
		setup();
		while(true) {
			detectKeys();
			drawGraphics();
			gc.sleep(30);
			count +=1;
		}
	}
}
