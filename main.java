package graphics;
import hsa2.GraphicsConsole;
import java.awt.Color;
import java.util.Random;


import java.awt.*;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class main implements ActionListener{
    GraphicsConsole gc = new GraphicsConsole(1280, 720);
    Random rand = new Random();
	Player player = new Player(10,20,20);
	Timer timer = new Timer(10, this);
	double time;
	Platform mainPlatform = new Platform(0, 720 - 100, 1280, 100);
	Platform leftSide = new Platform(-20, 0, 10, 720);
	Platform rightSide = new Platform(1290, 0, 10, 720);

    static int[][] blocks;

    public static void main(String[] args) {
        new main();
    }
    main() {
        setup();


        while (true) {
			detectKeys();
			drawGraphics();
			Thread.yield();
			player.timer = time;
			gc.sleep(30);
        }
    }
    void setup() {
		createBlocks();
        gc.setAntiAlias(true);
        gc.setBackgroundColor(Color.WHITE);
        gc.setLocationRelativeTo(null);
        gc.clear();
		gc.enableMouse();
		gc.enableMouseMotion();
    }

    void createBlocks() {
        blocks = new int[10][35];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 35; j++) {
            	if (i%2 ==1) {
					blocks[i][j] = rand.nextInt(0, 6);
            	}
            }
        }
    }
    /*
        int[][] blocks1 = {{0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,1,0,0,0,0},
        		{0,0,0,0,1,1,1,0,0},
        		{1,1,0,0,0,0,0,0,1},
        		{1,0,0,0,0,0,0,0,0}};
       */

    void drawBlocks() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (blocks[i][j] == 1) {
                    gc.setColor(Color.BLACK);

                    int rectX = 80 + (31 * (j));
                    int rectY = 75 + (48 * (i));
                    int rectWidth = 30;
                    int rectHeight = 30;

                    gc.setStroke(6);
                    gc.setColor(Color.BLACK);
                    gc.fillRect(rectX, rectY, rectWidth+30, rectHeight);
                    gc.drawRect(rectX, rectY, rectWidth+30, rectHeight);
                    Platform platform = new Platform(rectX, rectY, rectWidth+30, rectHeight);
					if(player.fall) {
						player.DetectPlatform(platform);
					}
                    
                }
            }
        }
    }

	void drawGraphics() {
		synchronized(gc) {
			gc.clear();
			player.DetectPlatform(leftSide);
			if(player.fall) {
				player.DetectPlatform(rightSide);
			}
			if(player.fall) {
				player.DetectPlatform(mainPlatform);
			}

			//Draw grass
			gc.setColor(Color.GREEN);
			gc.fillRect(mainPlatform.x, mainPlatform.y, mainPlatform.width, mainPlatform.height);


			drawBlocks();
			gc.setColor(new Color(100,100,100));
			gc.fillRect(player.x, player.y, player.width, player.height);
			//player.moveRight();
			player.fall();
		}
	}

	void detectKeys(){
			if (gc.isKeyDown(37)) {	//isKeyDown uses keyCodes. Left arrow
				player.moveLeft();
			}
			if (gc.isKeyDown(39)) { //right
				player.moveRight();
			}
			if (gc.isKeyDown(38) && !timer.isRunning() && !player.fall) {	//up
				timer.start();
				player.jumping = true;
				System.out.println("HIf");
			}
			if (gc.isKeyDown(40)) {	//down
				player.jumpdown();
			}
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			time += 0.1;
		}
		System.out.println(time);
		if (time >=3) {
			player.jumping = false;
		};	
		if (time >=6) {
			time = 0;
			timer.stop();

		}
	}
}
