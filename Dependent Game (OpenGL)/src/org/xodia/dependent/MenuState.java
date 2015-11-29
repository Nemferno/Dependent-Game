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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.xodia.dependent.data.Highscore;
import org.xodia.dependent.data.Resource;

public class MenuState extends BasicGameState{

	private int id;
	
	private final String MENU_TITLE = "Dependent Game";
	
	private boolean isEnteringGame;
	
	private Display display;
	private Label firstPlace, secondPlace, thirdPlace;
	
	private boolean hasMusicStarted = false;
	
	public MenuState(int id){
		this.id = id;
	}
	
	public void init(final GameContainer gc, final StateBasedGame sg)
			throws SlickException {
		
		display = new Display(gc);
		
		Container buttonContent = new Container();
		buttonContent.setLocation(480, 270);
		buttonContent.setSize(100, 150);
		buttonContent.setOpaque(false);
		
		RowLayout layout = new RowLayout(false);
		layout.setHorizontalAlignment(RowLayout.CENTER);
		layout.setVerticalAlignment(RowLayout.TOP);
		buttonContent.setLayout(layout);
		
		Button startButton = new Button("Start");
		startButton.setImage(Resource.BUTTON_NORMAL);
		startButton.setRolloverImage(Resource.BUTTON_HOVERED);
		startButton.setSize(100, 25);
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				isEnteringGame = true;
				sg.enterState(Application.GAMESTATE);
			}
		});
		
		Button optionButton = new Button("Option");
		optionButton.setImage(Resource.BUTTON_NORMAL);
		optionButton.setRolloverImage(Resource.BUTTON_HOVERED);
		optionButton.setSize(100, 25);
		optionButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				sg.enterState(Application.OPTIONSTATE);
			}
		});
		
		Button creditButton = new Button("Credit");
		creditButton.setImage(Resource.BUTTON_NORMAL);
		creditButton.setRolloverImage(Resource.BUTTON_HOVERED);
		creditButton.setSize(100, 25);
		creditButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				sg.enterState(Application.CREDITSTATE, 
						new FadeOutTransition(Color.gray, 1500), 
						new FadeInTransition(Color.lightGray, 2000));
			}
		});
		
		Button exitButton = new Button("Exit");
		exitButton.setImage(Resource.BUTTON_NORMAL);
		exitButton.setRolloverImage(Resource.BUTTON_HOVERED);
		exitButton.setSize(100, 25);
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				gc.exit();
			}
		});

		buttonContent.add(creditButton);
		buttonContent.add(optionButton);
		buttonContent.add(exitButton);
		buttonContent.add(startButton);
		
		Container highscoreContent = new Container();
		highscoreContent.setSize(275, 125);
		highscoreContent.setLocation(15, 270);
		highscoreContent.setOpaque(true);
		highscoreContent.setBackground(Color.lightGray);
		
		RowLayout layout2 = new RowLayout(false, RowLayout.CENTER, RowLayout.LEFT);
		highscoreContent.setLayout(layout2);
		
		Label highscoreTitle = new Label("Highscores");
		highscoreTitle.pack();
		
		firstPlace = new Label("1. " + ((Resource.highscore.getName(Highscore.FIRST_PLACE) != null) ? Resource.highscore.getName(Highscore.FIRST_PLACE) + ": " : "N/A: ") 
				+ ((Resource.highscore.getScore(Highscore.FIRST_PLACE) != -1) ? Resource.highscore.getScore(Highscore.FIRST_PLACE) : 0));
		firstPlace.setSize(150, 25);
		
		secondPlace = new Label("2. " + ((Resource.highscore.getName(Highscore.SECOND_PLACE) != null) ? Resource.highscore.getName(Highscore.SECOND_PLACE) + ": " : "N/A: ")
				+ ((Resource.highscore.getScore(Highscore.SECOND_PLACE) != -1) ? Resource.highscore.getScore(Highscore.SECOND_PLACE) : 0));
		secondPlace.setSize(150, 25);
		
		thirdPlace = new Label("3. " + ((Resource.highscore.getName(Highscore.THIRD_PLACE) != null) ? Resource.highscore.getName(Highscore.THIRD_PLACE) + ": ": "N/A: ") 
				+ ((Resource.highscore.getScore(Highscore.THIRD_PLACE) != -1) ? Resource.highscore.getScore(Highscore.THIRD_PLACE) : 0));
		thirdPlace.setSize(150, 25);

		highscoreContent.add(secondPlace);
		highscoreContent.add(firstPlace);
		highscoreContent.add(thirdPlace);
		highscoreContent.add(highscoreTitle);
		
		display.add(buttonContent);
		display.add(highscoreContent);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		try{
			Resource.highscore.updateScores();
		}catch(Exception e){
			System.err.println("Could not connect to the internet.");
		}
		
		firstPlace.setText("1. " + ((Resource.highscore.getName(Highscore.FIRST_PLACE) != null) ? Resource.highscore.getName(Highscore.FIRST_PLACE) + ": " : "N/A: ") 
				+ ((Resource.highscore.getScore(Highscore.FIRST_PLACE) != -1) ? Resource.highscore.getScore(Highscore.FIRST_PLACE) : 0));
		secondPlace.setText("2. " + ((Resource.highscore.getName(Highscore.SECOND_PLACE) != null) ? Resource.highscore.getName(Highscore.SECOND_PLACE) + ": " : "N/A: ")
				+ ((Resource.highscore.getScore(Highscore.SECOND_PLACE) != -1) ? Resource.highscore.getScore(Highscore.SECOND_PLACE) : 0));
		thirdPlace.setText("3. " + ((Resource.highscore.getName(Highscore.THIRD_PLACE) != null) ? Resource.highscore.getName(Highscore.THIRD_PLACE) + ": ": "N/A: ") 
				+ ((Resource.highscore.getScore(Highscore.THIRD_PLACE) != -1) ? Resource.highscore.getScore(Highscore.THIRD_PLACE) : 0));
		
		if(!hasMusicStarted){
			Resource.POLSKYSANCTUARY_BGM.loop(1f, container.getMusicVolume());
			
			hasMusicStarted = true;
		}else{
			Resource.POLSKYSANCTUARY_BGM.resume();
		}
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		if(isEnteringGame){
			Resource.POLSKYSANCTUARY_BGM.stop();
			
			isEnteringGame = false;
			hasMusicStarted = false;
		}
	}

	public void render(GameContainer gc, StateBasedGame sg, Graphics g)
			throws SlickException {
		g.drawImage(Resource.MENU_BACKGROUND, 0, 0);
		
		Resource.TITLE_TEXT_FONT.drawString((gc.getWidth() / 2) - Resource.TITLE_TEXT_FONT.getWidth(MENU_TITLE) / 2, 
				(gc.getHeight() / 2) - 225, MENU_TITLE, Color.orange);
		
		display.render(gc, g);
	}

	public void update(GameContainer gc, StateBasedGame sg, int delta)
			throws SlickException {
		display.update(gc, delta);
	}

	public int getID() {
		return id;
	}

}
