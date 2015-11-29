package org.xodia.dependent.games;

public class Bullet extends GameObject{

	public Bullet(float x, float y) {
		super(x, y, 5, 5);
		
		setVelY(-2.5f);
		setSpeed(0.9999f);
		setAlive(true);
	}

}
