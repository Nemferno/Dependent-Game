package org.xodia.dependent;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.xodia.dependent.data.Resource;

public class Application extends StateBasedGame{

	private static final String TITLE = "Dependent Game";
	private static final String	VERSION = "0.1.4";
	
	public static final int MENUSTATE = 1;
	public static final int GAMESTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	public static final int CREDITSTATE = 4;
	public static final int OPTIONSTATE = 5;
	public static final int GAMEOPTIONSTATE = 6;
	
	public Application(){
		super(TITLE + " " + VERSION);
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		Resource.loadResources(gc);
		
		addState(new MenuState(MENUSTATE));
		addState(new CreditState(CREDITSTATE));
		addState(new GameState(GAMESTATE));
		addState(new GameOverState(GAMEOVERSTATE));
		addState(new OptionState(OPTIONSTATE));
		addState(new GameOptionState(GAMEOPTIONSTATE));
		
		enterState(MENUSTATE);
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Application(), 640, 480, false);
		app.setTargetFrameRate(60);
		app.setShowFPS(false);
		app.setIcon("asset/DependentGameIcon.png");
		app.start();
	}
	
}
