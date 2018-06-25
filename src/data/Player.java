package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {
	
	private TileGrid grid;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	private boolean leftMouseButtonDown;
	
	//constructor
	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.index = 0;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<TowerCannon>();
		this.leftMouseButtonDown = false;
	}
	
	//interface with the gameworld
	public void setTile() {
		grid.setTile( (int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() -1) / Game.TILE_SIZE), types[index]);
	}
	
	public void update() {
		
		//update towers
		for (TowerCannon t : towerList) {
			t.update();
		}
		
		//handle mouse input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			towerList.add(new TowerCannon(quickload("towers/cannonBase64"), grid.getTile(Mouse.getX() / Game.TILE_SIZE, (HEIGHT - Mouse.getY() -1) / Game.TILE_SIZE), 50, waveManager.getCurrentWave().getEnemies()));
			//setTile();
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);
		
		//handle keyboard input
		while (Keyboard.next()) {
			//speeds game up with right arrow key
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				System.out.println("right arrow key pressed");
				Clock.changeMultiplier(0.2f);				
			}
			//slows games down with left arrow key
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				System.out.println("left arrow key pressed");
				Clock.changeMultiplier(-0.2f);				
			}
			//pause the game with p
			if (Keyboard.getEventKey() == Keyboard.KEY_P && Keyboard.getEventKeyState()) {
				System.out.println("p key pressed");
				Clock.pause();				
			}
			//add tower to tower list with t
			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				System.out.println("t arrow key pressed");
				towerList.add(new TowerCannon(quickload("towers/cannonBase64"), grid.getTile(1, 2), 50, waveManager.getCurrentWave().getEnemies()));
			}
		}
	}
	
	private void moveIndex() {
		index++;
		if (index > types.length -1) {
			index = 0;
		}
	}

}
