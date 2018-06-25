package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

public class Enemy {
	
	private int width, height, health, currentCheckpoint;
	private float speed, x, y;
	private Texture texture;
	private Tile startTile;
	private boolean first = true, alive = true;
	private TileGrid grid;
	
	private ArrayList<Checkpoint> checkpoints;
	private int[] directions;
	
	//constructor
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed) {
		this.texture = texture;
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.grid = grid;
		
		//initalize the checkpoint list
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		//0 = x and 1 = y
		this.directions[0] = 0;
		this.directions[1] = 0;
		directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
	}
	
	//update method
	public void update() {
		if (first) {
			first = false;
		} else {
			if(checkpointReached()) {
				if (currentCheckpoint + 1 == checkpoints.size()) {
					die();
				} else {
					currentCheckpoint++;
				}
			} else {
				x += delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
				y += delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
			}
		}
	}
	
	private boolean checkpointReached() {
		boolean reached = false;
		Tile t = checkpoints.get(currentCheckpoint).getTile();
		
		//check if postition reached tile within variance of 3 (arbitrary)
		if (x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() -3 && y < t.getY() + 3) {
			
			reached = true;
			x = t.getX();
			y = t.getY();
		}
		
		
		return reached;
	}
	
	private void populateCheckpointList() {
		checkpoints.add(findNextC(startTile, directions = findNextD(startTile)));
		
		int counter = 0;
		//cont = continue
		boolean cont = true;
		
		while (cont) {
			int[] currentD = findNextD(checkpoints.get(counter).getTile());
			//TODO: this ends after 20 checkpoints
			if (currentD[0] == 2 || counter == 30) {
				cont = false;
			} else {
				checkpoints.add(findNextC(checkpoints.get(counter).getTile(), directions = findNextD(checkpoints.get(counter).getTile())));
			}
			counter++;
		}
	}
	
	//find the next checkpoint
	private Checkpoint findNextC(Tile s, int[] dir) {
		Tile next = null;
		Checkpoint c = null;
		
		boolean found = false;
		int counter = 1;
		
		while (!found) {
			//search for a tile thats not the same as my starting tile or the end of map 
			if (s.getXPlace() + dir[0] * counter == grid.getTilesWide() || 
					s.getYPlace() + dir[1] * counter == grid.getTilesHigh() || 
					s.getType() != grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter).getType()) {
				found = true;
				counter -= 1;
				//the tile before is our next checkpoint
				next = grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
			}			
			counter++;
		}
		
		//declare the checkpoint
		c = new Checkpoint(next, dir[0], dir[1]);
		
		return c;
		
	}
	
	//find the next direction
	private int[] findNextD(Tile s) {
		int[] dir = new int[2];
		Tile u = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
		Tile d = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
		Tile l = grid.getTile(s.getXPlace() - 1, s.getYPlace());
		Tile r = grid.getTile(s.getXPlace() + 1, s.getYPlace());
		
		if(s.getType() == u.getType() && directions[1] != 1) {
			dir[0] = 0;
			dir[1] = -1;
		} else if (s.getType() == r.getType() && directions[0] != -1) {
			dir[0] = 1;
			dir[1] = 0;
		} else if (s.getType() == d.getType() && directions[1] != -1) {
			dir[0] = 0;
			dir[1] = 1;
		} else if (s.getType() == l.getType() && directions[0] != 1) {
			dir[0] = -1;
			dir[1] = 0;
		} else {
			dir[0] = 2;
			dir[1] = 2;
			//System.out.println("I found the End!");
		}
		
		return dir;
	}
	
	//kill the enemy if killed by player or at the end of the maze
	private void die() {
		alive = false;
	}
	
	
	
	//draw-method
	public void draw() {
		drawQuadTex(texture, x, y, width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
	
	public TileGrid getTileGrid() {
		return grid;
	}
	
	public boolean isAlive() {
		return alive;
	}

}
