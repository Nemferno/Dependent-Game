package org.xodia.dependent;

import mdes.slick.sui.Button;
import mdes.slick.sui.Container;
import mdes.slick.sui.Display;
import mdes.slick.sui.Slider;
import mdes.slick.sui.ToggleButton;
import mdes.slick.sui.event.ActionEvent;
import mdes.slick.sui.event.ActionListener;
import mdes.slick.sui.event.ChangeEvent;
import mdes.slick.sui.event.ChangeListener;
import mdes.slick.sui.layout.RowLayout;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.xodia.dependent.data.Resource;

public class OptionState extends BasicGameState{

	private int id;
	
	// Replace the buttons with sliders!
	private Display display;
	
	private Slider musicSlider, soundSlider;
	
	public OptionState(int id){
		this.id = id;
	}
	
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		musicSlider.setValue(container.getMusicVolume());
		soundSlider.setValue(container.getSoundVolume());
		
	}
	
	public void init(final GameContainer gc, final StateBasedGame sg)
			throws SlickException {
		
		display = new Display(gc);
		
		Container musicContent = new Container();
		musicContent.setLocation(250, 100);
		musicContent.setSize(225, 25);
		musicContent.setOpaque(false);
		
		RowLayout layout = new RowLayout(true);
		layout.setHorizontalAlignment(RowLayout.CENTER);
		layout.setVerticalAlignment(RowLayout.CENTER);
		
		musicContent.setLayout(layout);
		
		musicSlider = new Slider(Slider.HORIZONTAL);
		musicSlider.setBounds(300, 100, 175, 25);
		musicSlider.setValue(1f);
		musicSlider.setThumbSize(0.1f);
		musicSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
					
				gc.setMusicVolume(((Slider) e.getSource()).getValue());
			}
		});
		musicContent.add(musicSlider);
		
		// Add butons for it
		Button leftMusicButton = new Button("->");
		leftMusicButton.setSize(25, 25);
		leftMusicButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				musicSlider.setValue(musicSlider.getValue() + 0.10f);
			}
		});
		musicContent.add(leftMusicButton);
		
		Button rightMusicButton = new Button("<-");
		rightMusicButton.setSize(25, 25);
		rightMusicButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				musicSlider.setValue(musicSlider.getValue() - 0.10f);
			}
		});
		musicContent.add(rightMusicButton);
		
		Container soundContent = new Container();
		soundContent.setLocation(250, 200);
		soundContent.setSize(225, 25);
		soundContent.setOpaque(false);
		soundContent.setLayout(layout);
		
		soundSlider = new Slider(Slider.HORIZONTAL);
		soundSlider.setBounds(300, 100, 175, 25);
		soundSlider.setValue(1f);
		soundSlider.setThumbSize(0.1f);
		soundSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());

				gc.setSoundVolume(((Slider) e.getSource()).getValue());
			}
		});
		soundContent.add(soundSlider);
		
		// Add butons for it
		Button leftSoundButton = new Button("->");
		leftSoundButton.setSize(25, 25);
		leftSoundButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				
				soundSlider.setValue(soundSlider.getValue() + 0.10f);
			}
		});
		soundContent.add(leftSoundButton);
		
		Button rightSoundButton = new Button("<-");
		rightSoundButton.setSize(25, 25);
		rightSoundButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				
				soundSlider.setValue(soundSlider.getValue() - 0.10f);
			}
		});
		soundContent.add(rightSoundButton);
		
		// Full Screen Toggle Button
		final ToggleButton fullScreen = new ToggleButton(Resource.X_BUTTON_UNPRESSED);
		fullScreen.setSize(25, 25);
		fullScreen.setLocation(250, 300);
		fullScreen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				if(gc.isFullscreen())
					try {
						fullScreen.setImage(Resource.X_BUTTON_UNPRESSED);
						
						gc.setFullscreen(false);
					} catch (SlickException e1) {
						e1.printStackTrace();
					}
				else
					try {
						fullScreen.setImage(Resource.X_BUTTON_PRESSED);
						
						gc.setFullscreen(true);
					} catch (SlickException e1) {
						e1.printStackTrace();
					}
			}
		});
		
		// Back Button
		Button backButton = new Button("Back");
		backButton.setImage(Resource.BUTTON_NORMAL);
		backButton.setRolloverImage(Resource.BUTTON_HOVERED);
		backButton.setSize(100, 25);
		backButton.setLocation(270, 400);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Resource.BUTTON_CLICK_SOUND.play(1f, gc.getSoundVolume());
				sg.enterState(Application.MENUSTATE);
			}
		});
		
		display.add(musicContent);
		display.add(soundContent);
		display.add(fullScreen);
		display.add(backButton);
	}

	public void render(GameContainer gc, StateBasedGame sg, Graphics g)
			throws SlickException {
		Resource.TITLE_TEXT_FONT.drawString((gc.getWidth() / 2) - Resource.TITLE_TEXT_FONT.getWidth("Option Menu"), 15, "Option Menu");
		
		Resource.LABEL_TEXT_FONT.drawString(10, 100, "Music Volume: ");
		Resource.LABEL_TEXT_FONT.drawString(10, 200, "Sound Volume: ");
		Resource.LABEL_TEXT_FONT.drawString(10, 300, "Full Screen Mode: ");
		
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
