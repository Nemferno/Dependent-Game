package org.xodia.dependent.games;

import org.newdawn.slick.geom.Polygon;

public class Shooter extends GameObject{

	private final float[] POINTS = { 12, 0, 13, 0, 0, 25, 25, 25 };
	
	private boolean isLaserSightOnline;
	
	public Shooter(float x, float y) {
		super(x, y, 25, 25);

		setSpeed(2.5f);
		setAlive(true);
	}
	
	public void activateLaserSight(){
		isLaserSightOnline = true;
	}
	
	public void deactivateLaserSight(){
		isLaserSightOnline = false;
	}
	
	public boolean isLaserSightOnline(){
		return isLaserSightOnline;
	}
	
	public Polygon getPolygonalBounds(){
		Polygon poly = new Polygon(POINTS);
		poly.setX(getX());
		poly.setY(getY());
		return poly;
	}

}
