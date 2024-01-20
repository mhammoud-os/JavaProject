import hsa2.GraphicsConsole;
import java.awt.*;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Arena implements ActionListener {
	GraphicsConsole gc = new GraphicsConsole(1280, 720);
	
	Random rand = new Random();
	Player player = new Player(10, 10, 10, 40, 40);
	Player player2 = new Player(1250, 10, 10, 40, 40);
	Timer timer = new Timer(10, this);
	Timer timer2 = new Timer(10, this);
	double time;
	double time2;
	Platform mainPlatform = new Platform(0, 720 - 100, 1280, 100);
	Platform leftSide = new Platform(-20, 0, 10, 720);
	Platform rightSide = new Platform(1290, 0, 10, 720);
	static int[][] blocks;
	
	//Window size
	public static int winX = 1280;
	public static int winY = 720;
		
	//Graphics Consoles
	static GraphicsConsole gcIntro = new GraphicsConsole(winX, winY, "Intro Screen");
	
	//Start button variables
	static int startX = winX/2 - 100;
	static int startY = winY/2 + 225;
	static int startW = 175;
	static int startH = 75;
		
	static Rectangle startButton = new Rectangle(startX, startY, startW, startH);
		
	static int mouseX, mouseY;
	
	static BufferedImage logo;

	public static void main(String[] args) {
		new Arena();
	}

	Arena() {
		
		setupIntro();
		
		while (!checkStart()) {
			gcIntro.sleep(500);
		}
		
		switchScreen();
		
		setup();
		while (true) {
			detectKeys();
			drawGraphics();
			Thread.yield();
			player.timer = time;
			player2.timer = time2;
			gc.sleep(30);
		}
	}
	
	void setupIntro() {
		//Set the visible screens
		gcIntro.setVisible(true);
		gc.setVisible(false);
		
		//Set up other stuff
		gcIntro.setAntiAlias(true);
		gcIntro.setLocationRelativeTo(null);
		//gcIntro.setBackgroundColor(new Color(222, 213, 42));
		gcIntro.setBackgroundColor(new Color(108, 219, 230));
		gcIntro.clear();
		
		//Fonts and colours
		Font titleFont = new Font("Comic Sans MS", Font.PLAIN, 50);
		Font bodyFont = new Font("Comic Sans MS", Font.PLAIN, 30);
		Color blue1 = new Color(42, 81, 222);
		Color green1 = new Color(21, 150, 23);
		
		//Welcome
		gcIntro.setColor(blue1);
		gcIntro.setFont(titleFont);
		gcIntro.drawString("Welcome to LeapDuel Arena!", winX/2 - 325, winY/2 - 225);
		logo = loadImage("leapDuelArenaLogo.png");
		gcIntro.drawImage(logo, winX/2 - 250, winY/2 - 150);
		
		//Show rules
		gcIntro.setFont(bodyFont);
		gcIntro.drawString("Rules: ", winX/2 - 60, winY/2 - 135);
		gcIntro.drawString("Move left, right, and jump with wasd or arrow keys.", winX/2 - 350, winY/2 - 75);
		gcIntro.drawString("The goal of the game is to jump on the other player.", winX/2 -  350, winY/2 - 25);
		gcIntro.drawString("You have 3 lives, and you lose one if you get jumped on.", winX/2 - 375, winY/2 + 25);
		gcIntro.drawString("Last one standing wins!", winX/2 - 175, winY/2 + 75);
		
		gcIntro.drawString("Click the button to start the game!", winX/2 - 250, winY/2 + 175);
		
		//Draw the start button
		gcIntro.setColor(green1);
		gcIntro.fillRect(startX, startY, startW, startH);
		
		gcIntro.setColor(blue1);
		gcIntro.drawString("START!", startX + 25, startY + 50);
		
		//Enable the mouse functions
		gcIntro.enableMouse();
		gcIntro.enableMouseMotion();
		gcIntro.getMouseClick();
		
		//gcIntro.clear();
		//gc.clear();
	}
	
	/**
	 * Check if the start button has been clicked
	 */
	static boolean checkStart() {
		mouseX = gcIntro.getMouseX();
		mouseY = gcIntro.getMouseY();
		
		if (gcIntro.getMouseClick() != 0) {
			if (startButton.contains(mouseX, mouseY)) {
				return true;
			} else return false;
		} else return false;
		
	}
	
	/**
	 * Switches to the main graphics console
	 */
	void switchScreen() {
		gcIntro.setVisible(false);
		gc.setVisible(true);
		
		gcIntro.clear();
		gc.clear();
		
		gc.setLocationRelativeTo(null);
		gc.setBackgroundColor(Color.BLUE);
	}
	
	static BufferedImage loadImage(String fileName) {
		  BufferedImage img = null;
		  try {
		    img = ImageIO.read(new File(fileName).getAbsoluteFile());
		  } catch (IOException e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(null, "An image failed to load", "ERROR", JOptionPane.ERROR_MESSAGE);
		  }
		  return img;
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
				if (i % 2 == 1) {
					blocks[i][j] = rand.nextInt(0, 6);
				}
			}
		}
	}

	void drawBlocks() {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				if (blocks[i][j] == 1) {
					gc.setColor(Color.BLACK);

					int rectX = 80 + (31 * (j));
					int rectY = 75 + (48 * (i));
					int rectWidth = 30;
					int rectHeight = 10;

					gc.setStroke(6);
					gc.setColor(Color.BLACK);
					gc.fillRect(rectX, rectY, rectWidth + 30, rectHeight);
					gc.drawRect(rectX, rectY, rectWidth + 30, rectHeight);
					Platform platform = new Platform(rectX, rectY, rectWidth + 30, rectHeight);
					if (player.fall) {
						player.DetectPlatform(platform);
					}
					if (player2.fall) {
						player2.DetectPlatform(platform);
					}
				}
			}
		}
	}

	void drawGraphics() {
		synchronized (gc) {
			gc.clear();
			player.DetectPlatform(leftSide);
			if (player.fall) {
				player.DetectPlatform(rightSide);
			}
			if (player.fall) {
				player.DetectPlatform(mainPlatform);
			}

			player2.DetectPlatform(leftSide);
			if (player2.fall) {
				player2.DetectPlatform(rightSide);
			}
			if (player2.fall) {
				player2.DetectPlatform(mainPlatform);
			}

			gc.setColor(Color.GREEN);
			gc.fillRect(mainPlatform.x, mainPlatform.y, mainPlatform.width, mainPlatform.height);

			drawBlocks();
			gc.setColor(new Color(100, 100, 100));
			gc.fillRect(player.x, player.y, player.width, player.height);
			gc.fillRect(player2.x, player2.y, player2.width, player2.height);
			gc.fillRect(player2.getTop().x, player2.getTop().y, player2.getTop().width, player2.getTop().height);

			player.getPlayerColide(player2);
			player2.getPlayerColide(player);
			player.fall();
			player2.fall();
		}
	}

	void detectKeys() {
		if (gc.isKeyDown(37)) player.moveLeft();
		if (gc.isKeyDown(39)) player.moveRight();
		if (gc.isKeyDown(38) && !timer.isRunning() && !player.fall) {
			timer.start();
			player.jumping = true;
		}
		if (gc.isKeyDown(40)) player.jumpdown();

		if (gc.isKeyDown(65)) player2.moveLeft();
		if (gc.isKeyDown(68)) player2.moveRight();
		if (gc.isKeyDown(87) && !timer2.isRunning() && !player2.fall) {
			timer2.start();
			player2.jumping = true;
		}
		if (gc.isKeyDown(83)) player2.jumpdown();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer2) {
			time2 += 0.1;
		}
		if (time2 >= 3) player2.jumping = false;
		if (time2 >= 6) {
			time2 = 0;
			timer2.stop();
		}
		if (ev.getSource() == timer) {
			time += 0.1;
		}
		if (time >= 3) player.jumping = false;
		if (time >= 6) {
			time = 0;
			timer.stop();
		}
	}
}
