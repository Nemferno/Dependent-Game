package org.xodia.dependent;

import mdes.slick.sui.Button;
import mdes.slick.sui.Container;
import mdes.slick.sui.Display;
import mdes.slick.sui.TextField;
import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.layout.RowLayout;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.xodia.dependent.data.Resource;

public class GameOverState extends BasicGameState{

	private int id;
	
	private Display display;
	private Button submitScore;
	
	public GameOverState(int id){
		this.id = id;
	}
	
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		submitScore.setEnabled(true);
	}
	
	public void init(final GameContainer gc, final StateBasedGame sg)
			throws SlickException {
		display = new Display(gc);
		
		Container highscoreInput = new Container();
		highscoreInput.setSize(400, 25);
		highscoreInput.setLocation((gc.getWidth() / 2) - highscoreInput.getWidth() / 2,
				gc.getHeight() / 2 - highscoreInput.getHeight() / 2);
		highscoreInput.setOpaque(false);
		
		RowLayout layout = new RowLayout(true, RowLayout.CENTER, RowLayout.TOP);
		highscoreInput.setLayout(layout);
		
		final TextField userField = new TextField();
		userField.setSize(300, 25);
		
		submitScore = new Button("Submit");
		submitScore.setImage(Resource.BUTTON_NORMAL);
		submitScore.setRolloverImage(Resource.BUTTON_HOVERED);
		submitScore.setSize(100, 25);
		submitScore.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String user = userField.getText();
				int score = Resource.score;
				
				try{
					// We have to remove all spaces before sending score
					String updatedUser = user.replace(" ", "");
					
					if(!(updatedUser.length() <= 25))
						return;
					
					Resource.highscore.inputScore(updatedUser, score);
				}catch(Exception e2){
					System.err.println("Could not connect to the internet");
				}
				
				submitScore.setEnabled(false);
			}
		});
		
		highscoreInput.add(submitScore);
		highscoreInput.add(userField);
		
		Container buttonContent = new Container();
		buttonContent.setSize(250, 25);
		buttonContent.setLocation((gc.getWidth() / 2) - buttonContent.getWidth() / 2,
				(gc.getHeight() / 2) - buttonContent.getHeight() / 2 + 200);
		buttonContent.setOpaque(false);
		
		RowLayout layout2 = new RowLayout(true, RowLayout.CENTER, RowLayout.LEFT);
		buttonContent.setLayout(layout2);
		
		Button restart = new Button("Restart");
		restart.setImage(Resource.BUTTON_NORMAL);
		restart.setRolloverImage(Resource.BUTTON_HOVERED);
		restart.setSize(100, 25);
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				sg.enterState(Application.GAMESTATE, new EmptyTransition(), new BlobbyTransition(Color.black));
			}
		});
		
		Button leave = new Button("Leave");
		leave.setImage(Resource.BUTTON_NORMAL);
		leave.setRolloverImage(Resource.BUTTON_HOVERED);
		leave.setSize(100, 25);
		leave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				sg.enterState(Application.MENUSTATE);
			}
		});
		
		buttonContent.add(leave);
		buttonContent.add(restart);
		
		display.add(highscoreInput);
		display.add(buttonContent);
	}

	public void render(GameContainer gc, StateBasedGame sg, Graphics g)
			throws SlickException {
		Resource.LABEL_TEXT_FONT.drawString(gc.getWidth() / 2 - Resource.LABEL_TEXT_FONT.getWidth(Resource.deathNote) / 2,
				gc.getHeight() / 2 - Resource.LABEL_TEXT_FONT.getHeight(Resource.deathNote) / 2 - 50, Resource.deathNote);
		Resource.LABEL_TEXT_FONT.drawString(gc.getWidth() / 2 - Resource.LABEL_TEXT_FONT.getWidth("GAME OVER : Points - " + Resource.score) / 2,
				gc.getHeight() / 2 - Resource.LABEL_TEXT_FONT.getHeight("GAME OVER : Points - " + Resource.score) - 100, 
				"GAME OVER : Points - " + Resource.score);

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
