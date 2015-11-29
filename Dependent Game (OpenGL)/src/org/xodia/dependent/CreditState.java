package org.xodia.dependent;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.xodia.dependent.data.Resource;

public class CreditState extends BasicGameState{

	private int id;
	
	private float cameraX, cameraY;
	private float x, y;

	public CreditState(int id){
		this.id = id;
	}
	
	public void init(GameContainer gc, StateBasedGame sg)
			throws SlickException {

	}
	
	public void enter(GameContainer gc, StateBasedGame sg)
			throws SlickException {
		cameraX = cameraY = 0;
		x = 0;
		y = gc.getHeight() / 2;
	}

	public void render(GameContainer gc, StateBasedGame sg, Graphics g){
		center(x, y);
		
		g.translate(-cameraX, -cameraY);
		
		g.drawImage(Resource.CREDIT_PNG, 0, 0);
		
		g.translate(cameraX, cameraY);
	}

	public void update(GameContainer gc, StateBasedGame sg, int delta)
			throws SlickException {
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
			sg.enterState(Application.MENUSTATE);
		}
		
		if(y + gc.getHeight() / 2 < 600){
			y += 2;
		}
	}
	
	private void center(float x, float y){
		cameraX = x - 640 / 2;
		cameraY = y - 480 / 2;
		
		if(cameraX < 0)
			cameraX = 0;
		if(cameraX + 640 > 640)
			cameraX = 640 - 640;
		
		if(cameraY < 0)
			cameraY = 0;
		if(cameraY + 480 > 600)
			cameraY = 600 - 480;
	}

	public int getID() {
		return id;
	}
	
}
