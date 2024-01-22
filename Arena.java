package graphics;
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
import javax.swing.*;
import javax.swing.Timer;
import javax.sound.sampled.*;

public class Arena implements ActionListener {
	//Window size
	public static int winX = 1280;
	public static int winY = 720;
		
	//Graphics Consoles
	GraphicsConsole gc = new GraphicsConsole(winX, winY);
	static GraphicsConsole gcIntro = new GraphicsConsole(winX, winY, "Intro Screen");
	static GraphicsConsole gcRules = new GraphicsConsole(winX, winY, "Rules");
	
	Random rand = new Random();
	Player player = new Player(10, 10, 10, 40, 40);
	Player player2 = new Player(1250, 10, 10, 40, 40);
	
	//Timers
	Timer timer = new Timer(10, this);
	Timer timer2 = new Timer(10, this);
	
	static BufferedImage BackgroundImg, BlockImg, PlatformImg;
	static BufferedImage[] pImagesRight = new BufferedImage[12];
	static BufferedImage[] pImagesLeft = new BufferedImage[12];
	static BufferedImage[] bImagesRight = new BufferedImage[12];
	static BufferedImage[] bImagesLeft = new BufferedImage[12];
	static BufferedImage HeartImg;
	int currentFrame = 0;
	double time;
	double time2;
	
	//Platforms
	Platform mainPlatform = new Platform(0, 720 - 100, 1280, 100);
	Platform leftSide = new Platform(-20, 0, 10, 720);
	Platform rightSide = new Platform(1290, 0, 10, 720);
	static int[][] blocks;
	
	//Fonts and colours
	Font titleFont = new Font("Comic Sans MS", Font.PLAIN, 50);
	Font bodyFont = new Font("Comic Sans MS", Font.PLAIN, 28);
	Color blue1 = new Color(42, 81, 222);
	Color green1 = new Color(21, 150, 23);
	
	//Start button variables
	static int startX = winX/2 - 120;
	static int startY = winY/2 - 100;
	static int startW = 240;
	static int startH = 80;
		
	//Rectangles
	static Rectangle startButton = new Rectangle(startX, startY, startW, startH);
	static Rectangle levelButton = new Rectangle(winX/2 - 90, winY/2 + 50, 180, 75);
	static Rectangle questionMark = new Rectangle(winX - 100, winY - 100, 60, 60);
	static Rectangle pvpButton = new Rectangle(winX/2 - 110, winY/2 + 160, 220, 75);
	static Rectangle backToIntro = new Rectangle(winX/2 - 90, winY - 180, 180, 75);
	
	static int levelNum = 0;
		
	static int mouseX, mouseY;
	
	static BufferedImage logo;
	
	static Level levelB = new Level(levelNum);
	static boolean levelClicked = false;
	static boolean rulesClicked = false;
	static boolean pvpClicked = false;
	static boolean Ai = false;

	private Clip backgroundMusic;
	private Clip jumpSound;
	//static boolean toIntro = false;

	public static void main(String[] args) {
		new Arena();
	}

	Arena() {
		//Intro screen
		setupIntro();
		setupRules();
		loadAudio();
		playBackgroundMusic();

		
		while (!checkStart()) {
			drawIntro();
			showLevel();
			drawRuleButton();
			checkPvp();
			checkRules();
			
			if (checkToIntro()) {
				switchScreen(gcIntro);
			}
			
			//if (checkLevel()) levelNum ++;
			if (levelClicked) { 
				levelNum ++;
				levelNum %= 3;
			
			}
			if (pvpClicked) Ai = !Ai;
			levelClicked = false;
			rulesClicked = false;
			pvpClicked = false;
			//toIntro = false;
			gcIntro.sleep(30);
		}
		switchScreen(gc);
		setup();
		boolean running = true;
		while (running) {
			detectKeys();
			drawGraphics();
			Thread.yield();
			if(player.lifeCount ==0 || player2.lifeCount == 0) {
				break;
			}

			player.timer = time;
			player2.timer = time2;
			gc.sleep(30);
		}
			
			gc.clear();
			detectKeys();
			drawGraphics();

			gc.sleep(1000);
			gc.dispose();
			System. exit(0);
	}
	
	void setupIntro() {
		//Set the visible screens
		gcIntro.setVisible(true);
		gc.setVisible(false);
		gcRules.setVisible(false);
		
		//Set up other stuff
		gcIntro.setAntiAlias(true);
		gcIntro.setLocationRelativeTo(null);
		//gcIntro.setBackgroundColor(new Color(222, 213, 42));
		gcIntro.setBackgroundColor(new Color(108, 219, 230));
		gcIntro.clear();
		
		//Enable the mouse functions
		gcIntro.enableMouse();
		gcIntro.enableMouseMotion();
		gcIntro.getMouseClick();
	}
	
	void drawIntro() {
			//Welcome
			gcIntro.setColor(blue1);
			gcIntro.setFont(titleFont);
			//gcIntro.drawString("Welcome to ", winX/2 - 325, winY/2 - 275);
			logo = loadImage("src/imgs/leapDuelArenaLogo.png");
			//gcIntro.drawImage(logo, winX/2 - 25, winY/2 - 375, 400, 150);
			gcIntro.drawImage(logo, winX/2 - 275, winY/2 - 350, 550, 200);
					
			//Draw the start button
			gcIntro.setFont(bodyFont);
			gcIntro.setColor(green1);
			gcIntro.fillRect(startX, startY, startW, startH);
			gcIntro.setColor(Color.BLACK);
			gcIntro.drawRect(startX, startY, startW, startH);
					
			gcIntro.setColor(blue1);
			gcIntro.drawString("START!", startX + 60, startY + 50);
	}
	
	void setupRules() {	
		//Set up other stuff
		gcRules.setAntiAlias(true);
		gcRules.setLocationRelativeTo(null);
		
		//gcIntro.setBackgroundColor(new Color(222, 213, 42));
		gcRules.setBackgroundColor(new Color(108, 219, 230));
		gcRules.clear();
				
		//Enable the mouse functions
		gcRules.enableMouse();
		gcRules.enableMouseMotion();
		gcRules.getMouseClick();
	}
	
	void drawRules() {
			gc.clear();
			gcRules.setColor(blue1);
			gcRules.setFont(titleFont);
			gcRules.drawString("Rules: ", winX/2 - 60, 150);
			
			gcRules.setFont(bodyFont);
			gcRules.drawString("Move left, right, and jump with wasd or arrow keys.", winX/2 - 325, winY/2 - 100);
			gcRules.drawString("The goal of the game is to jump on the other player.", winX/2 -  325, winY/2 - 50);
			gcRules.drawString("You have 3 lives, and you lose one if you get jumped on.", winX/2 - 350, winY/2);
			gcRules.drawString("You can play against the ai or another player.", winX/2 - 275, winY/2 + 50);
			gcRules.drawString("Last one standing wins!", winX/2 - 150, winY/2 + 100);
			
			gcRules.setColor(green1);
			gcRules.fillRect(winX/2 - 90, winY - 180, 180, 75);
			gcRules.setColor(Color.BLACK);
			gcRules.drawRect(winX/2 - 90, winY - 180, 180, 75);
			gcRules.setColor(Color.WHITE);
			gcRules.drawString("Back", winX/2 - 45, winY - 135);
	}
	
	/**
	 * Checks if a button has been clicked
	 */
	static boolean checkStart() {
		mouseX = gcIntro.getMouseX();
		mouseY = gcIntro.getMouseY();
		
		if (gcIntro.getMouseClick() != 0) {
			if (startButton.contains(mouseX, mouseY)) {
				return true;
			} else if (levelButton.contains(mouseX, mouseY)) {
				levelClicked = true;
			} else if (pvpButton.contains(mouseX, mouseY)) {
				pvpClicked = true;
			} else if (questionMark.contains(mouseX, mouseY)) {
				rulesClicked = true;
			}
		}
		return false;
	}

	static boolean checkToIntro() {
		mouseX = gcRules.getMouseX();
		mouseY = gcRules.getMouseY();
		
		if (gcRules.getMouseClick() != 0) {
			if (backToIntro.contains(mouseX, mouseY)) {
				return true;
			}
		} 
		return false;
	}
	
	/**
	 * Switches to the main graphics console
	 */
	void switchScreen(GraphicsConsole g) {
		gcIntro.setVisible(false);
		//gc.setVisible(true);
		gc.setVisible(false);
		gcRules.setVisible(false);
		g.setVisible(true);
		
		gcIntro.clear();
		gc.clear();
		
		gc.setLocationRelativeTo(null);
		gc.setBackgroundColor(Color.BLUE);
	}
	
	void showLevel() {
		levelB = new Level(levelNum);
		
		//gcIntro.setColor(Color.BLUE);
		//gcIntro.drawString("Level:", 75, 50);
		
			gcIntro.setColor(levelB.levelBg);
			gcIntro.fillRect(winX/2 - 90, winY/2 + 50, 180, 75);
			gcIntro.setColor(Color.BLACK);
			gcIntro.drawRect(winX/2 - 90, winY/2 + 50, 180, 75);
			
			gcIntro.setColor(Color.WHITE);
			gcIntro.drawString(levelB.levelName, winX/2 - 50, winY/2 + 100);
	}
	
	void drawRuleButton() {
			gcIntro.setColor(Color.GRAY);
			gcIntro.fillOval(winX - 100, winY - 100, 60, 60);
			gcIntro.setFont(bodyFont);
			gcIntro.setColor(Color.WHITE);
			gcIntro.drawString("?", winX - 75, winY - 60);
	}
	
	void checkRules() {
		if (rulesClicked) {
				switchScreen(gcRules);
				drawRules();
		}
	}
	
	void checkPvp() {
			if (Ai) {
				gcIntro.setColor(blue1.brighter());
				gcIntro.fillRect(winX/2 - 110, winY/2 + 160, 220, 75);
				gcIntro.setColor(Color.BLACK);
				gcIntro.drawRect(winX/2 - 110, winY/2 + 160, 220, 75);
				gcIntro.setColor(Color.WHITE);
				gcIntro.drawString("Player vs Ai", winX/2 - 80, winY/2 + 210);
			} else {
				gcIntro.setColor(blue1.brighter());
				gcIntro.fillRect(winX/2 - 110, winY/2 + 160, 220, 75);
				gcIntro.setColor(Color.BLACK);
				gcIntro.drawRect(winX/2 - 110, winY/2 + 160, 220, 75);
				gcIntro.setColor(Color.WHITE);
				gcIntro.drawString("Player vs Player", winX/2 - 100, winY/2 + 210);
			}
	}
	
	void setup() {
		createBlocks();
		gc.setAntiAlias(true);
		BackgroundImg = loadImage("src/imgs/leapDuelArenaBG.png");
		BlockImg = loadImage("src/imgs/blockImage.png");
		PlatformImg = loadImage("src/imgs/groundPlatformImg.png");
		HeartImg = loadImage("src/imgs/heart.png");
		gc.clear();
		gc.enableMouse();
		gc.enableMouseMotion();
		player.speed = (levelNum+1)*6;
		player2.speed = (levelNum+1)*6;

		for (int i = 0; i < 12; i++) {
			pImagesLeft[i] = loadImage("src/imgs/imgs.player.right/0_Minotaur_Running_0" + String.format("%02d", i) + ".png");
			bImagesRight[i] = loadImage("src/imgs/imgs.player2.right/0_Reaper_Man_Run Slashing_0" + String.format("%02d", i) + ".png");
			pImagesRight[i] = loadImage("src/imgs/imgs.player.left/0_Minotaur_Running_0" + String.format("%02d", i) + ".png");
			bImagesLeft[i] = loadImage("src/imgs/imgs.player2.left/0_Reaper_Man_Run Slashing_0" + String.format("%02d", i) + ".png");
			/*
			pImagesLeft[i] = loadImage("src/imgs/player/right/0_Minotaur_Running_0" + String.format("%02d", i) + ".png");
			bImagesRight[i] = loadImage("src/imgs/player2/right/0_Reaper_Man_Run Slashing_0" + String.format("%02d", i) + ".png");
			pImagesRight[i] = loadImage("src/imgs/player/left/0_Minotaur_Running_0" + String.format("%02d", i) + ".png");
			bImagesLeft[i] = loadImage("src/imgs/player2/left/0_Reaper_Man_Run Slashing_0" + String.format("%02d", i) + ".png");
			*/
		}
	}
	void loadAudio() {
		try {
			// Load background music
			AudioInputStream bgMusicStream = AudioSystem.getAudioInputStream(new File("src/sounds/BGMusic.wav"));
			backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(bgMusicStream);

			// Load jump sound effect
			AudioInputStream jumpSoundStream = AudioSystem.getAudioInputStream(new File("src/sounds/jump.wav"));
			jumpSound = AudioSystem.getClip();
			jumpSound.open(jumpSoundStream);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to load audio files", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	void playBackgroundMusic() {
		if (backgroundMusic != null) {
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	void playJumpSound() {
		if (jumpSound != null) {
			jumpSound.stop(); // Stop any existing instances
			jumpSound.setFramePosition(0); // Rewind to the beginning
			jumpSound.start();
		}
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

		gc.drawImage(PlatformImg, 0, 590, 1280, 210);
		for (int i = 0; i!=player.lifeCount; i++) {
			gc.drawImage(HeartImg, 50 + (i*50), 650, 50, 50);
		}

		for (int i = 0; i!=player2.lifeCount; i++) {
			gc.drawImage(HeartImg, 1230 - (50 + (i*50)), 650, 50, 50);
		}
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
					gc.drawRect(rectX, rectY, rectWidth + 30, rectHeight);
					gc.drawImage(BlockImg, rectX, rectY, rectWidth + 30, rectHeight);
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
			gc.drawImage(BackgroundImg, 0, 0, 1280, 720);
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

			drawBlocks();
			if (player.movingRight)
				gc.drawImage(pImagesRight[currentFrame], player.x - 40, player.y - 55, player.width + 80, player.height + 80);
			else
				gc.drawImage(pImagesLeft[currentFrame], player.x - 40, player.y - 55, player.width + 80, player.height + 80);

			if (Ai) {
				if (player2.x > player.x)
					gc.drawImage(bImagesLeft[currentFrame], player2.x - 40, player2.y - 55, player2.width + 80, player2.height + 80);
				else
					gc.drawImage(bImagesRight[currentFrame], player2.x - 40, player2.y - 55, player2.width + 80, player2.height + 80);
			}else {
				if (player2.movingRight)
					gc.drawImage(bImagesRight[currentFrame], player2.x - 40, player2.y - 55, player2.width + 80, player2.height + 80);
				else gc.drawImage(bImagesLeft[currentFrame], player2.x - 40, player2.y - 55, player2.width + 80, player2.height + 80);
				
			}

			currentFrame = (currentFrame + 1) % 12;

			player.getPlayerColide(player2);
			player2.getPlayerColide(player);
			player.fall();
			player2.fall();
		}
	}
	
	void updatePlayer2Position() {
		int distanceX = player.x - player2.x;
		int distanceY = player.y - player2.y;

		if (Math.abs(distanceX) > Math.abs(distanceY)) {
			if (distanceX > 0)
				player2.moveRight();
			else
				player2.moveLeft();
		} else {
			if (distanceY > 0)
				player2.fall = true;
			else if (!player2.jumping && player.y - player2.y > 30 && Math.abs(distanceX) > 10) {
				player2.jumping = true;
				player2.gravity = -15;
			}
		}

		if (!player2.jumping) {
			if (distanceX > 50)
				player2.moveRight();
			else if (distanceX < -50)
				player2.moveLeft();
		}
	}
	
	
	void detectKeys() {
		if (Ai) { 
			updatePlayer2Position();
			if (!timer2.isRunning() && !player2.fall) {
				playJumpSound();
				timer2.start();
				player2.jumping = true;
			}
		}else {
			if (gc.isKeyDown(65))
				player2.moveLeft();
			if (gc.isKeyDown(68))
				player2.moveRight();
			if (gc.isKeyDown(87) && !timer2.isRunning() && !player2.fall) {
				playJumpSound();
				timer2.start();
				player2.jumping = true;
			}
			if (gc.isKeyDown(83))
				player2.jumpdown();
		}
		if (gc.isKeyDown(37)) {
			player.moveLeft();
		}
		if (gc.isKeyDown(39)) {
			player.moveRight();
		}
		if (gc.isKeyDown(38) && !timer.isRunning() && !player.fall) {
			playJumpSound();
			timer.start();
			player.jumping = true;
		}
		if (gc.isKeyDown(40)) player.jumpdown();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer2) {
			time2 += 0.1;
		}
		if (time2 >= 3) {
			player2.jumping = false;
			time2 = 0;
			timer2.stop();
		}
		if (ev.getSource() == timer) {
			time += 0.1;
		}
		if (time >= 3) {
			player.jumping = false;
			time = 0;
			timer.stop();
		}
	}
	
	/**
	 * 
	 * @param fileName	name of the file
	 * @return the image
	 */
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
}
