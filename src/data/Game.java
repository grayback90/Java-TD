package data;

import static helpers.Artist.*;

//main Game class that knows all infos of the game
public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	
	//Temp Variables
	public static final int TILE_SIZE = 64;
	private int spawntime = 2;
	Enemy enemyUfo;
	
	public Game(int[][] map) {
		grid = new TileGrid(map);
		enemyUfo = new Enemy(quickload("enemies/ufo64"), grid.getTile(0, 1), grid, TILE_SIZE, TILE_SIZE, 80);
		waveManager = new WaveManager(enemyUfo, spawntime, 3);
		player = new Player(grid, waveManager);
		
	}
	
	public void update() {
		//drawing the grid
		grid.draw();
		
		//updating wave (= enemy.update + enemy.draw)
		waveManager.update();
		
		//updating the player
		player.update();
	}

}
