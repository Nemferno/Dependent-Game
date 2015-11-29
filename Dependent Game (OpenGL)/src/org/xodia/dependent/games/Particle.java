package org.xodia.dependent.games;

import org.newdawn.slick.geom.Polygon;

public class Particle extends GameObject{

	private final float[] POINTS = { -2, 6, -8, 2, -7, -2, -4, -8, 7, 2};
	
	public Particle(float x, float y) {
		super(x, y, 15, 15);
		
		setVelY(1.65f);
		setSpeed(0.5f);
		setAlive(true);
	}
	
	public Polygon getPolygonalBounds(){
		Polygon polygon = new Polygon(POINTS);
		polygon.setX(getX());
		polygon.setY(getY());
		return polygon;
	}
	
}
