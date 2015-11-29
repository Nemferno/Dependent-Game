package org.xodia.dependent.games;

import org.newdawn.slick.Graphics;
import org.xodia.dependent.data.Resource;
import org.xodia.dependent.util.Timer;

public class LaserSightPowerUp extends PowerUp{

	private Timer onlineTimer;
	private Timer aliveTilDeadTimer;
	
	// Tells the game that the power up is now over!
	private boolean affectDepleted;
	private boolean instantDeath;
	
	public LaserSightPowerUp(float x, float y) {
		super(x, y);
		
		onlineTimer = new Timer(15000);
		aliveTilDeadTimer = new Timer(5000); // At least 5 seconds to live
		
		aliveTilDeadTimer.start();
		
		setAlive(true);
	}

	public void update(Shooter shooter, int delta) {
		if(shooter.isLaserSightOnline()){
			onlineTimer.tick(delta);
			
			if(onlineTimer.isTimeElapsed()){
				shooter.deactivateLaserSight();
				
				affectDepleted = true;
				
				instantDeath = true;
			}	
		}
		
		// Let's say there are two power ups and the player shoots and gets two of them.
		// If one of them finished before them, then the shooter.isLaserSightOnline() method will be FALSE
		// And is not an option. So, if the shooter's laser sight is not offline AND the timer is not
		// finished, then reactivate it.
		if(!shooter.isLaserSightOnline() && onlineTimer.canStart() && !onlineTimer.isTimeElapsed()){
			shooter.activateLaserSight();
		}
		
		if(isAlive()){
			if(hasPowerGiven()){
				setPowerGiven(false);
				setAlive(false);
				
				aliveTilDeadTimer.stop();
				onlineTimer.start();
				
				shooter.activateLaserSight();
			}
			
			aliveTilDeadTimer.tick(delta);
			
			if(aliveTilDeadTimer.isTimeElapsed()){
				setAlive(false);
				
				instantDeath = true;
			}
		}
	}
	
	public boolean hasInstantDeath(){
		return instantDeath;
	}
	
	public boolean isAffectDepleted(){
		return affectDepleted;
	}

	public void render(Graphics g) {
		if(isAlive()){
			g.drawImage(Resource.LASERSIGHTPOWERUP, getX(), getY());
		}
	}

}
