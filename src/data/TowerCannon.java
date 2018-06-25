package data;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class TowerCannon {
	
	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, damage;
	private Texture tBaseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private Enemy target;
	
	public TowerCannon(Texture tBaseTexture, Tile startTile, int damage, ArrayList<Enemy> enemies) {
		this.tBaseTexture = tBaseTexture;
		this.cannonTexture = quickload("towers/cannonGun64");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.damage = damage;
		this.firingSpeed = 2;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies = enemies;
		this.target = aquireTarget();
		this.angle =  calculateAngle();
	}
	
	private Enemy aquireTarget() {
		return enemies.get(0);
	}
	
	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}
	
	private void shoot() {
		timeSinceLastShot = 0;
		projectiles.add(new Projectile(quickload("towers/cannonBullet64"), target, x + (Game.TILE_SIZE / 2) - (Game.TILE_SIZE / 4), y + (Game.TILE_SIZE / 2) - (Game.TILE_SIZE / 4), 900, 50));
	}
	
	public void update() {
		timeSinceLastShot += delta();
		if (timeSinceLastShot > firingSpeed) {
			shoot();
		}
		
		for (Projectile p: projectiles) {
			p.update();
		}
		
		angle = calculateAngle();
		
		draw();
	}
	
	public void draw() {
		drawQuadTex(tBaseTexture, x, y, width, height);
		drawQuadTexRot(cannonTexture, x, y, width, height, angle);
	}

}
