package org.xodia.dependent.data;

import java.awt.Font;

import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class Resource {

	public static Image CREDIT_PNG;
	
	public static Image BUTTON_NORMAL;
	public static Image BUTTON_HOVERED;
	
	public static Image LEFT_BUTTON_NORMAL;
	public static Image RIGHT_BUTTON_NORMAL;
	
	public static Image LEFT_BUTTON_HOVERED;
	public static Image RIGHT_BUTTON_HOVERED;
	
	public static Image X_BUTTON_PRESSED;
	public static Image X_BUTTON_UNPRESSED;
	
	public static Image MENU_BACKGROUND;
	public static Image GAME_BACKGROUND;
	
	public static Image SHOOTER;
	public static Image ASTEROID;
	public static Image BULLET;
	
	public static Image PLAYER;
	public static Image ENEMY;
	public static Image GOAL;
	
	public static Image LASERSIGHTPOWERUP;
	
	public static TrueTypeFont TITLE_TEXT_FONT;
	public static TrueTypeFont BUTTON_TEXT_FONT;
	public static TrueTypeFont SCORE_TEXT_FONT;
	public static TrueTypeFont GOAL_TIMER_TEXT_FONT;
	public static TrueTypeFont REGULAR_TEXT_FONT;
	public static TrueTypeFont LABEL_TEXT_FONT;
	
	public static Sound BUTTON_CLICK_SOUND;
	public static Sound GAME_OVER_SOUND;
	public static Sound GOAL_SOUND;
	public static Sound POWER_UP_SOUND;
	public static Sound LASER_SOUND;
	public static Sound ASTEROID_COLLISION;
	public static Sound COLLISION;
	
	public static Music POLHARDCORPS_BGM;
	public static Music POLSKYSANCTUARY_BGM;
	
	public static int score;
	public static String deathNote;
	
	public static GameContainer container;
	
	public static Highscore highscore;
	
	public static void loadResources(GameContainer container) throws SlickException {
		Resource.container = container;
		
		highscore = Highscore.getInstance();
		
		try{
			highscore.updateScores();
		}catch(Exception e){
			System.out.println("Could not connect to the internet.");
		}
		
		
		POLHARDCORPS_BGM = new Music(ResourceLoader.getResourceAsStream("asset/POLHardCorps.ogg"), ".ogg");
		POLSKYSANCTUARY_BGM = new Music(ResourceLoader.getResourceAsStream("asset/POLSkySanctuary.ogg"), ".ogg");
		
		BUTTON_CLICK_SOUND = new Sound(ResourceLoader.getResourceAsStream("asset/ButtonClick.wav"), ".wav");
		GAME_OVER_SOUND = new Sound(ResourceLoader.getResourceAsStream("asset/GameOver.wav"), ".wav");
		GOAL_SOUND = new Sound(ResourceLoader.getResourceAsStream("asset/Goal.wav"), ".wav");
		POWER_UP_SOUND = new Sound(ResourceLoader.getResourceAsStream("asset/PowerUp.wav"), ".wav");
		LASER_SOUND = new Sound(ResourceLoader.getResourceAsStream("asset/LaserShot.wav"), ".wav");
		ASTEROID_COLLISION = new Sound(ResourceLoader.getResourceAsStream("asset/AsteroidCollision.wav"), ".wav");
		COLLISION = new Sound(ResourceLoader.getResourceAsStream("asset/Collision.wav"), ".wav");
		
		SHOOTER = new Image(ResourceLoader.getResourceAsStream("asset/Shooter.png"), "Shooter", false);
		ASTEROID = new Image(ResourceLoader.getResourceAsStream("asset/Asteroid.png"), "Asteroid", false);
		BULLET = new Image(ResourceLoader.getResourceAsStream("asset/Bullet.png"), "Bullet", false);
		
		LASERSIGHTPOWERUP = new Image(ResourceLoader.getResourceAsStream("asset/LaserSightPowerUp.png"), "Laser Sight Power Up", false);
		
		PLAYER = new Image(ResourceLoader.getResourceAsStream("asset/Player.png"), "Player", false);
		ENEMY = new Image(ResourceLoader.getResourceAsStream("asset/Enemy.png"), "Enemy", false);
		GOAL = new Image(ResourceLoader.getResourceAsStream("asset/Goal.png"), "Goal", false);
		
		CREDIT_PNG = new Image(ResourceLoader.getResourceAsStream("asset/Credits.png"), "Credit PNG", false);
		
		BUTTON_NORMAL = new Image(ResourceLoader.getResourceAsStream("asset/GameButton.png"), "Normal Button", false);
		BUTTON_HOVERED = new Image(ResourceLoader.getResourceAsStream("asset/HoverGameButton.png"), "Hovered Button", false);
		
		LEFT_BUTTON_NORMAL = new Image(ResourceLoader.getResourceAsStream("asset/LeftButton.png"), "Left Button", false);
		RIGHT_BUTTON_NORMAL = new Image(ResourceLoader.getResourceAsStream("asset/RightButton.png"), "Right Button", false);
		
		LEFT_BUTTON_HOVERED = new Image(ResourceLoader.getResourceAsStream("asset/HoverLeftButton.png"), "Hover Left Button", false);
		RIGHT_BUTTON_HOVERED = new Image(ResourceLoader.getResourceAsStream("asset/HoverRightButton.png"), "Hover Right Button", false);
		
		X_BUTTON_PRESSED = new Image(ResourceLoader.getResourceAsStream("asset/PressedButton.png"), "X Button Pressed", false);
		X_BUTTON_UNPRESSED = new Image(ResourceLoader.getResourceAsStream("asset/UnpressedButton.png"), "X Button Unpressed", false);
	
		MENU_BACKGROUND = new Image(ResourceLoader.getResourceAsStream("asset/MenuBackground.png"), "Menu Background", false);
		GAME_BACKGROUND = new Image(ResourceLoader.getResourceAsStream("asset/Background.png"), "Game Background", false);
		
		try{
			Font font = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("asset/Invasion2000.ttf"));
			
			REGULAR_TEXT_FONT = new TrueTypeFont(font.deriveFont(12f), false);
			TITLE_TEXT_FONT = new TrueTypeFont(font.deriveFont(Font.BOLD, 32f), false);
			BUTTON_TEXT_FONT = new TrueTypeFont(font.deriveFont(16f), false);
			SCORE_TEXT_FONT = new TrueTypeFont(font.deriveFont(Font.BOLD, 20f), false);
			LABEL_TEXT_FONT = new TrueTypeFont(font.deriveFont(Font.BOLD, 18f), false);
			GOAL_TIMER_TEXT_FONT = new TrueTypeFont(font.deriveFont(Font.BOLD, 14f), false);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Exception in Font Creation");
			e.printStackTrace();
		}
	}
	
}
