package org.xodia.dependent.games;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.xodia.dependent.data.Resource;
import org.xodia.dependent.util.Timer;

public class BoxGame {

	private Rectangle 	screen;
	
	private Rectangle 	goal;
	
	private boolean 	isGoalReached;
	private Timer 		goalTimer;
	
	private GameObject 	player, enemy;
	private boolean 	isGameOver;
	
	public BoxGame(){
		screen = new Rectangle(0, 0, 320, 480);
		
		player = new GameObject(screen.getX() + (320 / 2) - 12.5f, screen.getY() + (480 / 2) - 12.5f, 25, 25);
		enemy = new GameObject(screen.getX(), screen.getY(), 25, 25);
		
		goal = new Rectangle(0, 0, 25, 25);
		
		goalTimer = new Timer(10000);
		goalTimer.start();
		
		player.setSpeed(0.9f);
		enemy.setSpeed(0.4f);
	}
	
	public void init() throws SlickException{
		player.setX(screen.getX() + (320 / 2) - 12.5f);
		player.setY(screen.getY() + (320 / 2) - 12.5f);
		player.setVelX(0);
		player.setVelY(0);
		player.setSpeed(0.9f);
		
		enemy.setX(0);
		enemy.setY(0);
		enemy.setVelX(0);
		enemy.setVelY(0);
		enemy.setSpeed(0.4f);
		
		goalTimer.reset();
		isGoalReached = true;
		
		isGameOver = false;
	}
	
	public void update(GameContainer container, Input input, int delta){
		updateInput(input);
		updateMovement();
		
		goalTimer.tick(delta);
		
		checkCollision(container);
		spawnGoal();
		checkGoalTimer();
	}
	
	private void spawnGoal(){
		if(isGoalReached){
			Random random = new Random();
			int spawnX = random.nextInt(270);
			int spawnY = random.nextInt(430);
			goal.setX(spawnX + 25);
			goal.setY(spawnY + 25);
			goalTimer.reset();
			isGoalReached = false;
		}
	}
	
	private void checkGoalTimer(){
		if(goalTimer.isTimeElapsed()){
			Resource.deathNote = "LOOK AT THE SCREEN, THE TIME JUST WENT 0!!!";
			isGameOver = true;
		}
	}
	
	private void updateInput(Input input){
		if(input.isKeyDown(Input.KEY_A)){
			player.setVelX(-1);
		}else if(input.isKeyDown(Input.KEY_D)){
			player.setVelX(1);
		}else{
			player.setVelX(0);
		}
		
		if(input.isKeyDown(Input.KEY_S)){
			player.setVelY(1);
		}else if(input.isKeyDown(Input.KEY_W)){
			player.setVelY(-1);
		}else{
			player.setVelY(0);
		}
	}
	
	private void updateMovement(){
		player.setX(player.getX() + player.getVelX() * player.getSpeed());
		player.setY(player.getY() + player.getVelY() * player.getSpeed());
		
		enemy.setX(enemy.getX() + enemy.getVelX() * enemy.getSpeed());
		enemy.setY(enemy.getY() + enemy.getVelY() * enemy.getSpeed());
		
		calculateMovement();
	}
	
	private void checkCollision(GameContainer container){
		if(enemy.getBounds().intersects(player.getBounds())){
			Resource.deathNote = "YOU JUST DIED BY COLLIDING WITH A SQUARE!!!";
			isGameOver = true;
		}
		
		if(player.getBounds().intersects(goal)){
			Resource.GOAL_SOUND.play(1f, container.getSoundVolume());
			isGoalReached = true;
		}
		
		if(player.getX() <= 0)
			player.setX(0);
		if(player.getX() >= screen.getWidth() - 25)
			player.setX(screen.getWidth() - 25);
		if(player.getY() <= 0)
			player.setY(0);
		if(player.getY() >= screen.getHeight() - 25)
			player.setY(screen.getHeight() - 25);
	}
	
	private void calculateMovement(){
		if(enemy.getX() > player.getX()){
			enemy.setVelX(-1);
		}
		
		if(enemy.getX() < player.getX()){
			enemy.setVelX(1);
		}
		
		if(enemy.getY() > player.getY()){
			enemy.setVelY(-1);
		}
		
		if(enemy.getY() < player.getY()){
			enemy.setVelY(1);
		}
	}
	
	public void render(Graphics g){
		g.drawImage(Resource.GOAL, goal.getX(), goal.getY());
		
		Resource.GOAL_TIMER_TEXT_FONT.drawString(goal.getX() + (goal.getWidth() / 2 - Resource.REGULAR_TEXT_FONT.getWidth("" + goalTimer.getTimeInSeconds()) + 4), 
				goal.getY() + (goal.getHeight() / 2 - Resource.REGULAR_TEXT_FONT.getHeight("" + goalTimer.getTimeInSeconds())), "" + goalTimer.getTimeInSeconds(), Color.yellow);
		
		g.drawImage(Resource.ENEMY, enemy.getX(), enemy.getY());
		
		g.drawImage(Resource.PLAYER, player.getX(), player.getY());
	}
	
	public boolean isGameOver(){
		return isGameOver;
	}
	
}
