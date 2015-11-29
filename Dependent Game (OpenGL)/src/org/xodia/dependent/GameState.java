package org.xodia.dependent;

import mdes.slick.sui.Button;
import mdes.slick.sui.Container;
import mdes.slick.sui.Display;
import mdes.slick.sui.Label;
import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.layout.RowLayout;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.xodia.dependent.data.Highscore;
import org.xodia.dependent.data.Resource;
import org.xodia.dependent.games.BoxGame;
import org.xodia.dependent.games.StarDefender;
import org.xodia.dependent.util.Timer;

public class GameState extends BasicGameState{

	private int id;
	
	private BoxGame boxGame = new BoxGame();
	private StarDefender shooterGame = new StarDefender();
	
	private boolean isGameOver;
	
	private boolean isPausing;
	
	private Timer incrementScoreTimer = new Timer(1000);
	
	private Display display;
	
	private boolean isPauseLeaving = false;
	
	public GameState(int id){
		this.id = id;
	}

	public void init(final GameContainer gc, final StateBasedGame sg)
			throws SlickException {
		display = new Display(gc);
		
		Container pauseContent = new Container();
		pauseContent.setLocation(245, 140);
		pauseContent.setSize(150, 200);
		pauseContent.setOpaque(false);
		
		RowLayout layout = new RowLayout(false, RowLayout.CENTER, RowLayout.LEFT);
		pauseContent.setLayout(layout);
		
		Label label = new Label("Game Paused");
		label.setForeground(Color.green);
		label.pack();
		label.setHeight(25);
		
		Button resume = new Button("Resume");
		resume.setImage(Resource.BUTTON_NORMAL);
		resume.setRolloverImage(Resource.BUTTON_HOVERED);
		resume.setSize(100, 25);
		resume.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				isPausing = false;
			}
		});
		
		Button option = new Button("Option");
		option.setImage(Resource.BUTTON_NORMAL);
		option.setRolloverImage(Resource.BUTTON_HOVERED);
		option.setSize(100, 25);
		option.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				sg.enterState(Application.GAMEOPTIONSTATE);
			}
		});
		
		Button leave = new Button("Leave Game");
		leave.setImage(Resource.BUTTON_NORMAL);
		leave.setRolloverImage(Resource.BUTTON_HOVERED);
		leave.setSize(100, 25);
		leave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				isPausing = false;
				isGameOver = true;
				isPauseLeaving = true;
			}
		});
		
		pauseContent.add(option);
		pauseContent.add(resume);
		pauseContent.add(leave);
		pauseContent.add(label);
		
		display.add(pauseContent);
		
		incrementScoreTimer.start();
	}
	
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		if(!isPausing && isGameOver){
			boxGame.init();
			shooterGame.init();
			
			Resource.score = 0;
			isGameOver = false;
			isPausing = false;
			isPauseLeaving = false;
			
			Resource.deathNote = "";
			
			incrementScoreTimer.start();
		}
		
		Resource.POLHARDCORPS_BGM.loop(1f, container.getMusicVolume());
	}
	
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		
	}

	public void render(GameContainer gc, StateBasedGame sg, Graphics g)
			throws SlickException {
		if(!isGameOver){
			boxGame.render(g);
			shooterGame.render(g);
			
			g.setColor(Color.white);
			g.drawLine(320, 0, 320, 480);
		}
		
		if(isPausing){
			g.setColor(new Color(1f, 1f, 1f, 0.25f));
			g.fill(new Rectangle(0, 0, gc.getWidth(), gc.getHeight()));
			
			display.render(gc, g);
		}
		
		Resource.SCORE_TEXT_FONT.drawString(gc.getWidth() / 2 - Resource.SCORE_TEXT_FONT.getWidth("Highscore: " + ((Resource.highscore.getScore(Highscore.FIRST_PLACE) != -1) ? Resource.highscore.getScore(Highscore.FIRST_PLACE) : 0)) / 2,
				Resource.SCORE_TEXT_FONT.getHeight("Highscore: " + ((Resource.highscore.getScore(Highscore.FIRST_PLACE) != -1) ? Resource.highscore.getScore(Highscore.FIRST_PLACE) : 0)) / 2, 
				"Highscore: " + ((Resource.highscore.getScore(Highscore.FIRST_PLACE) != -1) ? Resource.highscore.getScore(Highscore.FIRST_PLACE) : 0), Color.orange);
		Resource.SCORE_TEXT_FONT.drawString(gc.getWidth() / 2 - Resource.SCORE_TEXT_FONT.getWidth("" + Resource.score) / 2, 
				Resource.SCORE_TEXT_FONT.getHeight("" + Resource.score) / 2 + 25, "" + Resource.score, Color.orange);
	}

	public void update(GameContainer gc, StateBasedGame sg, int delta)
			throws SlickException {
		if(boxGame.isGameOver() || shooterGame.isGameOver())
			isGameOver = true;
		
		if(!isGameOver && !isPausing){
			boxGame.update(gc, gc.getInput(), delta);
			shooterGame.update(gc, gc.getInput(), delta);	
			incrementScoreTimer.tick(delta);
		}
		
		if(isPausing){
			if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				isPausing = false;
			}
			
			display.update(gc, delta);
		}else{
			if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				isPausing = true;
			}
		}
		
		if(isGameOver){
			// Transition. It should create an animation and then switch over
			// For now, use the default transitions that slick has
			Resource.POLHARDCORPS_BGM.stop();
			Resource.GAME_OVER_SOUND.play(1f, gc.getSoundVolume());
			
			if(isPauseLeaving){
				sg.enterState(Application.MENUSTATE, new FadeOutTransition(Color.gray, 2500), new HorizontalSplitTransition(Color.lightGray));
			}else{
				sg.enterState(Application.GAMEOVERSTATE, new FadeOutTransition(Color.gray, 2500), new HorizontalSplitTransition(Color.lightGray));
			}
		}
		
		if(incrementScoreTimer.isTimeElapsed()){
			Resource.score += 1;
			incrementScoreTimer.reset();
		}
	}

	public int getID() {
		return id;
	}
	
}
