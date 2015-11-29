package org.xodia.dependent.games;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.xodia.dependent.data.Resource;
import org.xodia.dependent.util.Timer;

public class StarDefender {

	private Rectangle screen;
	
	private Shooter shooter;
	
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
	
	private ArrayList<GameObject> deadObjects = new ArrayList<GameObject>();
	
	private Timer particleSpawnTimer = new Timer(4000);
	private Timer delayBulletTimer = new Timer(500);
	
	private int 		currentBulletsAlive;
	private final int 	maxBulletsAlive = 4;
	
	private boolean isGameOver;
	
	private int difficulty = 1;
	private Timer difficultyTimer = new Timer(30000);
	
	public StarDefender(){
		screen = new Rectangle(320, 0, 320, 480);
		
		shooter = new Shooter(screen.getX() + ((screen.getWidth() / 2) + 25),
				screen.getY() + (480 - 75));
		
		particleSpawnTimer.start();
		delayBulletTimer.start();
		difficultyTimer.start();
	}
	
	public void init() throws SlickException{
		shooter.setX(screen.getX() + ((screen.getWidth() / 2) + 25));
		shooter.setY(screen.getY() + (480 - 75));
		shooter.setVelX(0);
		shooter.setVelY(0);
		shooter.setSpeed(2.5f);
		shooter.setAlive(true);
		shooter.deactivateLaserSight();
		
		difficulty = 1;
		
		currentBulletsAlive = 0;
		
		particles.clear();
		bullets.clear();
		deadObjects.clear();
		powerUps.clear();
		
		particleSpawnTimer.reset();
		delayBulletTimer.reset();
		difficultyTimer.reset();
		
		isGameOver = false;
	}
	
	public void update(GameContainer gc, Input input, int delta){
		checkInput(input);
		checkMovement(delta);
		
		particleSpawnTimer.tick(delta);
		delayBulletTimer.tick(delta);
		difficultyTimer.tick(delta);
		
		checkCollision(gc);
		removeDeadObjects();
		
		spawnParticle();
		checkDifficultyTimer();
	}
	
	private void checkDifficultyTimer(){
		if(difficultyTimer.isTimeElapsed()){
			if(difficulty < 3){
				difficulty++;
				difficultyTimer.reset();
			}
		}
	}
	
	private void spawnLaserSightPowerUp(Particle particle){
		// There will be 5 out of 100 chances that you will actually obtain this!
		Random random = new Random();
		int rate = random.nextInt(100);
		
		if(++rate <= 5){
			// Spawn the laser sight powerup
			LaserSightPowerUp powerUp = new LaserSightPowerUp(particle.getX() - 12.5f, particle.getY() - 12.5f);
			powerUps.add(powerUp);
		}
	}
	
	private void spawnParticle(){
		if(particleSpawnTimer.isTimeElapsed()){
			Random random = new Random();
			Particle p, p2, p3;
			int x = 0;
			int speedType, speedType2, speedType3;
			
			switch(difficulty){
			case 1:
				x = random.nextInt(270);
				p = new Particle(345 + x, 0);
				particles.add(p);
				
				break;
			case 2:
				
				speedType = random.nextInt(3);
				speedType2 = random.nextInt(3);
				
				x = random.nextInt(270);
				p = new Particle(345 + x, 0);
				
				if(speedType == 1){  
					p.setSpeed(0.33f);
				}else if(speedType == 2){
					p.setSpeed(0.55f);
				}
				
				particles.add(p);
				
				x = random.nextInt(270);
				p2 = new Particle(345 + x, 0);
				
				if(speedType2 == 1){
					p2.setSpeed(0.33f);
				}else if(speedType == 2){
					p.setSpeed(0.55f);
				}
				
				particles.add(p2);
				
				break;
			case 3:
				
				speedType = random.nextInt(3);
				speedType2 = random.nextInt(3);
				speedType3 = random.nextInt(3);
				
				x = random.nextInt(270);
				p = new Particle(345 + x, 0);
				
				if(speedType == 1){
					p.setSpeed(0.33f);
				}else if(speedType == 2){
					p.setSpeed(0.8f);
				}else{
					p.setSpeed(0.55f);
				}
				
				particles.add(p);
				
				x = random.nextInt(270);
				p2 = new Particle(345 + x, 0);
				
				if(speedType2 == 1){
					p2.setSpeed(0.33f);
				}else if(speedType2 == 2){
					p2.setSpeed(0.8f);
				}else{
					p.setSpeed(0.55f);
				}
				
				particles.add(p2);
				
				x = random.nextInt(270);
				p3 = new Particle(345 + x, 0);
				
				if(speedType3 == 1){
					p3.setSpeed(0.33f);
				}else if(speedType3 == 2){
					p3.setSpeed(0.8f);
				}else{
					p.setSpeed(0.55f);
				}
				
				particles.add(p3);
				
				break;
			}
				
			particleSpawnTimer.reset();
		}
	}
	
	private void checkInput(Input input){
		if(input.isKeyDown(Input.KEY_LEFT)){
			shooter.setVelX(-1);
		}else if(input.isKeyDown(Input.KEY_RIGHT)){
			shooter.setVelX(1);
		}else{
			shooter.setVelX(0);
		}
		
		if(input.isKeyDown(Input.KEY_UP)){
			if(delayBulletTimer.isTimeElapsed()){
				// If the bullet has elapsed, and a new bullet is ready
				// Verify that the bullets alive in the field are
				// less than the max
				if(currentBulletsAlive < maxBulletsAlive){
					Resource.LASER_SOUND.play(1f, Resource.container.getSoundVolume());
					Bullet bullet = new Bullet(shooter.getX() + (12.5f - 2.5f), shooter.getY());
					bullets.add(bullet);
					currentBulletsAlive++;
					delayBulletTimer.reset();
				}
			}
		}
	}
	
	private void checkMovement(int delta){
		shooter.setX(shooter.getX() + shooter.getVelX() * shooter.getSpeed());
		
		for(Bullet b : bullets){
			if(b.isAlive()){
				b.setY(b.getY() + b.getVelY() * b.getSpeed());
			}
		}
		
		for(Particle p : particles){
			if(p.isAlive()){
				p.setY(p.getY() + p.getVelY() * p.getSpeed());
			}
		}
		
		for(PowerUp up : powerUps){
			if(up instanceof LaserSightPowerUp){
				LaserSightPowerUp sight = (LaserSightPowerUp) up;
				
				if(!sight.hasInstantDeath()){
					sight.update(shooter, delta);
				}
			}
		}
	}
	
	public void checkCollision(GameContainer gc){
		if(shooter.getX() <= screen.getX())
			shooter.setX(screen.getX());
		if(shooter.getX() >= screen.getX() + (screen.getWidth() - 25))
			shooter.setX(screen.getX() + (screen.getWidth() - 25));
		
		for(PowerUp up : powerUps){
			if(up instanceof LaserSightPowerUp){
				LaserSightPowerUp sight = (LaserSightPowerUp) up;
				
				for(Bullet b : bullets){
					if(b.getBounds().intersects(up.getBounds())){
						if(up.isAlive() && !sight.hasInstantDeath()){
							// Proceed if this power up is still alive
							Resource.POWER_UP_SOUND.play(1f, gc.getSoundVolume());
							b.setAlive(false);
							sight.setPowerGiven(true);
						}
					}
				}
				
			}
		}
		
		for(Particle p : particles){
			for(Bullet b : bullets){
				if(b.getBounds().intersects(p.getBounds())){
					Resource.ASTEROID_COLLISION.play(1f, Resource.container.getSoundVolume());
					
					b.setAlive(false);
					p.setAlive(false);
					
					spawnLaserSightPowerUp(p);
					
					break;
				}
			}
			
			if(p.getBounds().intersects(shooter.getBounds())){
				Resource.COLLISION.play(1f, Resource.container.getSoundVolume());
				Resource.deathNote = "Asteroid has \"accidently\" destroyed your ship...";
				isGameOver = true;
				break;
			}
			
		}
		
		for(Bullet b : bullets){
			if(b.isAlive()){
				if(b.getY() <= 0)
					b.setAlive(false);
			}
		}
		
		for(Particle p : particles){
			if(p.isAlive()){
				if(p.getY() >= shooter.getY() + 25){
					p.setAlive(false);
					Resource.deathNote = "Woops... I accidently let something destroy Earth";
					isGameOver = true;
				}
			}
		}
	}
	
	private void removeDeadObjects(){
		for(Bullet b : bullets){
			if(!b.isAlive()){
				deadObjects.add(b);
				currentBulletsAlive--;
			}
		}
		
		for(Particle p : particles){
			if(!p.isAlive()){
				deadObjects.add(p);
			}
		}
		
		for(PowerUp up : powerUps){
			if(up instanceof LaserSightPowerUp){
				LaserSightPowerUp sight = (LaserSightPowerUp) up;
				
				if(sight.hasInstantDeath()){
					deadObjects.add(sight);
				}
			}
		}
		
		bullets.removeAll(deadObjects);
		particles.removeAll(deadObjects);
		powerUps.removeAll(deadObjects);
		
		deadObjects.clear();
	}
	
	public void render(Graphics g){
		g.drawImage(Resource.GAME_BACKGROUND, screen.getX(), screen.getY());
		
		g.drawImage(Resource.SHOOTER, shooter.getX(), shooter.getY());
		
		g.setColor(Color.white);
		
		for(Bullet b : bullets)
			g.drawImage(Resource.BULLET, b.getX(), b.getY());
		
		for(Particle p : particles){
			g.drawImage(Resource.ASTEROID, p.getPolygonalBounds().getX(), p.getPolygonalBounds().getY());
		}
		
		for(PowerUp up : powerUps){
			up.render(g);
		}
		
		if(shooter.isLaserSightOnline()){
			g.setColor(Color.red);
			g.drawLine(shooter.getX() + 12.5f, shooter.getY(), shooter.getX() + 12.5f, 0);
		}
	}

	public boolean isGameOver(){
		return isGameOver;
	}
	
}
