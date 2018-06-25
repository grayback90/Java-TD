package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;

public class MainMenu {
	
	private Texture background;
	private UI menuUI;
	
	public MainMenu() {
		this.background = quickload("backgrounds/mainmenu");
		this.menuUI = new UI();
		menuUI.addButton("Play", "buttons/playbutton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
		menuUI.addButton("Editor", "buttons/editorbutton", WIDTH / 2 - 128, (int) (HEIGHT * 0.55f));
		menuUI.addButton("Quit", "buttons/quitbutton", WIDTH / 2 - 128, (int) (HEIGHT * 0.65f));
	}
	
	private void updateButtons() {
		if(Mouse.isButtonDown(0)) {
			if(menuUI.isButtonClicked("Play")) {
				StateManager.setState(GameState.GAME);
			}
		}
	}
	
	public void update() {
		drawQuadTex(background, 0, 0, 2048, 1024);
		menuUI.draw();
		updateButtons();
	}

}
