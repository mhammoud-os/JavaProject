package graphics;
import java.awt.Color;

import hsa2.GraphicsConsole;
import java.awt.*;
import java.util.*;

public class JavaProject {

	final static int WIDTH = 1280;
	final static int HEIGHT = 720;
	GraphicsConsole gc = new GraphicsConsole(WIDTH,HEIGHT);
	Rectangle rect = new Rectangle(0,700, 1280,20);
	Rectangle rect2 = new Rectangle(200,400, 120,20);

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
	Player player = new Player(10,10,10);
	void drawGraphics() {
		synchronized(gc) {
			gc.clear();
			gc.setColor(new Color(100,100,100));
			gc.fillRect(rect.x,rect.y, rect.width, rect.height);
			gc.fillRect(rect2.x,rect2.y, rect2.width, rect2.height);
			gc.setColor(new Color(0,0,0));
			gc.fillRect(player.x, player.y, player.width, player.height);
			//player.moveRight();
			player.fall();
			player.DetectPlatform(rect);
			player.DetectPlatform(rect2);

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
	JavaProject() {
		setup();
		while(true) {
			detectKeys();
			drawGraphics();
			gc.sleep(30);
		}
	}
}
