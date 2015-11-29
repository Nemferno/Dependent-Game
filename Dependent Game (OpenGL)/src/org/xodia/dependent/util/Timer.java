package org.xodia.dependent.util;

public class Timer {
	
	private static final int defTime = 10000;
	
	private int startTime;
	private int time;
	private boolean timeElapsed;
	private boolean canStart;
	
	public Timer(){
		this(defTime);
	}
	
	public Timer(int startTime){
		this.startTime = startTime;
		
		time = startTime;
	}
	
	public void tick(int update){
		if(canStart){
			if(!timeElapsed){
				time -= update;
				
				if(time <= 0)
					timeElapsed = true;
			}
		}
	}
	
	public void start(){
		canStart = true;
	}
	
	public void stop(){
		canStart = false;
	}
	
	public boolean canStart(){
		return canStart;
	}
	
	public void reset(){
		timeElapsed = false;
		time = startTime;
	}
	
	public int getTimeInSeconds(){
		return time / 1000;
	}
	
	public long getTimeInMilliSeconds(){
		return time;
	}
	
	public boolean isTimeElapsed(){
		return timeElapsed;
	}
	
}
