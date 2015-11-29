package org.xodia.dependent.games;

import org.newdawn.slick.Graphics;

public abstract class PowerUp extends GameObject{

	// We can trace if the power up has been activated
	// So we can countdown, and deactive the player's
	// power up
	private boolean hasPowerGiven;
	
	public PowerUp(float x, float y) {
		super(x, y, 25, 25);
	}
	
	public void setPowerGiven(boolean given){
		hasPowerGiven = given;
	}
	
	public boolean hasPowerGiven(){
		return hasPowerGiven;
	}

	// Can only administer the power up to a shooter class
	public abstract void update(Shooter shooter, int delta);
	
	public abstract void render(Graphics g);
	
}
