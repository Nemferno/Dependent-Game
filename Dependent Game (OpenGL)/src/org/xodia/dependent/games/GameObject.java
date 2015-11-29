package org.xodia.dependent.games;

import org.newdawn.slick.geom.Rectangle;

public class GameObject {

	private float x, y;
	private float velX, velY;
	private float speed;
	private boolean isAlive;
	private Rectangle bounds;
	
	public GameObject(float x, float y){
		this(x, y, 0, 0);
	}
	
	public GameObject(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setVelX(float velX){
		this.velX = velX;
	}
	
	public void setVelY(float y){
		this.velY = y;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public void setAlive(boolean alive){
		isAlive = alive;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getVelX(){
		return velX;
	}
	
	public float getVelY(){
		return velY;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public Rectangle getBounds(){
		bounds.setLocation(x, y);
		return bounds;
	}
	
}
