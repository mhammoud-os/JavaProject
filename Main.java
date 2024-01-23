import hsa2.GraphicsConsole;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.*;


public class Main implements ActionListener {
	// The Window
	GraphicsConsole gc = new GraphicsConsole(1280, 720);

	// Global Variables
	Random rand = new Random();
	Player player = new Player(10, 10, 10, 40, 40);
	Player player2 = new Player(1250, 10, 4, 40, 40);
	javax.swing.Timer timer = new javax.swing.Timer(10, this);
	javax.swing.Timer timer2 = new javax.swing.Timer(10, this);
	double time, time2;
	Platform mainPlatform = new Platform(0, 720 - 100, 1280, 100);
	Platform leftSide = new Platform(-20, 0, 10, 720);
	Platform rightSide = new Platform(1290, 0, 10, 720);
	static int[][] blocks;
	boolean AutomaticGame = true;
	static BufferedImage BackgroundImg, BlockImg, PlatformImg;
	static BufferedImage[] pImagesRight = new BufferedImage[12];
	static BufferedImage[] pImagesLeft = new BufferedImage[12];
	static BufferedImage[] bImagesRight = new BufferedImage[12];
	static BufferedImage[] bImagesLeft = new BufferedImage[12];
	static BufferedImage HeartImg;
	int currentFrame = 0;
	boolean PlayerMoveRight = true;
	private Clip backgroundMusic;
	private Clip jumpSound;

	public static void main(String[] args) {
		new Main();
	}

	Main() {
		setup();
		// Load audio files, then start the Background Music.
		loadAudio();
		playBackgroundMusic();
		while (true) {
			detectKeys();
			drawGraphics();
			Thread.yield();
			player.timer = time;
			player2.timer = time2;
			gc.sleep(30);
		}
	}

	void setup() {
		createBlocks();
		gc.setAntiAlias(true);
		// Save all the images to global variables.
		BackgroundImg = loadImage("src/imgs/leapDuelArenaBG.png");
		BlockImg = loadImage("src/imgs/blockImage.png");
		PlatformImg = loadImage("src/imgs/groundPlatformImg.png");
		HeartImg = loadImage("src/imgs/heart.png");

		for (int i = 0; i < 12; i++) {
			pImagesLeft[i] = loadImage("src/imgs/player/right/0_Minotaur_Running_0" + String.format("%02d", i) + ".png");
			bImagesRight[i] = loadImage("src/imgs/player2/right/0_Reaper_Man_Run Slashing_0" + String.format("%02d", i) + ".png");
			pImagesRight[i] = loadImage("src/imgs/player/left/0_Minotaur_Running_0" + String.format("%02d", i) + ".png");
			bImagesLeft[i] = loadImage("src/imgs/player2/left/0_Reaper_Man_Run Slashing_0" + String.format("%02d", i) + ".png");
		}

		gc.setLocationRelativeTo(null);
		gc.clear();
		gc.enableMouse();
		gc.enableMouseMotion();
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
		// Display the amount of lives left.
		gc.drawImage(PlatformImg, 0, 590, 1280, 210);
		for (int i = 0; i!=player.lifeCount; i++) {
			gc.drawImage(HeartImg, 50 + (i*50), 650, 50, 50);
		}
		for (int i = 0; i!=player2.lifeCount; i++) {
			gc.drawImage(HeartImg, 1230 - (50 + (i*50)), 650, 50, 50);
		}

		// Set up the platforms.
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
					gc.drawImage(BlockImg, rectX, rectY, rectWidth + 30, rectHeight);
					Platform platform = new Platform(rectX, rectY, rectWidth + 30, rectHeight);
					if (player.fall) player.DetectPlatform(platform);
					if (player2.fall) player2.DetectPlatform(platform);
				}
			}
		}
	}

	void drawGraphics() {
		synchronized (gc) {
			gc.clear();
			gc.drawImage(BackgroundImg, 0, 0, 1280, 720);

			player.DetectPlatform(leftSide);
			if (player.fall) player.DetectPlatform(rightSide);
			if (player.fall) player.DetectPlatform(mainPlatform);

			player2.DetectPlatform(leftSide);
			if (player2.fall) player2.DetectPlatform(rightSide);
			if (player2.fall) player2.DetectPlatform(mainPlatform);

			gc.setColor(Color.GREEN);
			gc.fillRect(mainPlatform.x, mainPlatform.y, mainPlatform.width, mainPlatform.height);

			drawBlocks();

			gc.setColor(new Color(100, 100, 100, 0));
			gc.fillRect(player.x, player.y, player.width, player.height);

			// Change sprite based on the direction the character is moving.
			if (PlayerMoveRight)
				gc.drawImage(pImagesRight[currentFrame], player.x - 40, player.y - 55, player.width + 80, player.height + 80);
			else
				gc.drawImage(pImagesLeft[currentFrame], player.x - 40, player.y - 55, player.width + 80, player.height + 80);

			if (player2.x > player.x)
				gc.drawImage(bImagesLeft[currentFrame], player2.x - 40, player2.y - 55, player2.width + 80, player2.height + 80);
			else
				gc.drawImage(bImagesRight[currentFrame], player2.x - 40, player2.y - 55, player2.width + 80, player2.height + 80);

			currentFrame = (currentFrame + 1) % 12;

			gc.setColor(new Color(100, 100, 100, 0));
			gc.fillRect(player2.x, player2.y, player2.width, player2.height);
			gc.fillRect(player2.getTop().x, player2.getTop().y, player2.getTop().width, player2.getTop().height);

			player.getPlayerColide(player2);
			player2.getPlayerColide(player);
			player.fall();
			player2.fall();
		}
	}

	void updatePlayer2Position() {
		int distanceX = player.x - player2.x;
		int distanceY = player.y - player2.y;

		// Move towards the player, and jump if nearby or below.
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
		if (AutomaticGame)
			updatePlayer2Position();

		if (gc.isKeyDown(37)) {
			player.moveLeft();
			PlayerMoveRight = false;
		}
		if (gc.isKeyDown(39)) {
			player.moveRight();
			PlayerMoveRight = true;
		}
		if (gc.isKeyDown(38) && !timer.isRunning() && !player.fall) {
			timer.start();
			player.jumping = true;
			playJumpSound();
		}
		if (gc.isKeyDown(40))
			player.jumpdown();

		if (gc.isKeyDown(65))
			player2.moveLeft();
		if (gc.isKeyDown(68))
			player2.moveRight();
		if (!timer2.isRunning() && !player2.fall) {
			timer2.start();
			player2.jumping = true;
		}
		if (gc.isKeyDown(83))
			player2.jumpdown();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer2)
			time2 += 0.1;
		if (ev.getSource() == timer)
			time += 0.1;

		if (time2 >= 3)
			player2.jumping = false;
		if (time2 >= 6) {
			time2 = 0;
			timer2.stop();
		}

		if (time >= 3)
			player.jumping = false;
		if (time >= 6) {
			time = 0;
			timer.stop();
		}
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
}