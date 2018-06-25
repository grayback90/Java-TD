package data;

import static helpers.Artist.*;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;

public class Boot {
	
	//constructor
	public Boot() {
		
		beginSession();
				
		//while-loop to hold the screen activ
		while(!Display.isCloseRequested()) {
			
			//update clock
			Clock.update();
			
			//statemanager who function as our ui
			StateManager.update();
			
			Display.update();
			Display.sync(60);
		}
		
		//close the screen
		Display.destroy();
	}
	
	//main-class
	public static void main(String[] args) {
		new Boot();
	}
}
