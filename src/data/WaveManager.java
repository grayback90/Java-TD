package data;

public class WaveManager {
	
	private float timeSinceLastWave, timeBetweenEnemies;
	private int waveNumber, enemiesPerWave;
	private Enemy enemyType;
	private Wave currentWave;
	
	public WaveManager(Enemy enemyType, float timeBetweenEnemies, int enemiesPerWave) {
		this.timeSinceLastWave = 0;
		this.waveNumber = 0;
		this.enemiesPerWave = enemiesPerWave;
		this.enemyType = enemyType;
		this.timeBetweenEnemies = timeBetweenEnemies;
		
		this.currentWave = null;
		newWave();
	}
	
	public void update() {
		if (!currentWave.isCompleted()) {
			currentWave.update();
		} else {
			newWave();
		}
	}
	
	private void newWave() {
		currentWave = new Wave(enemyType, timeBetweenEnemies, enemiesPerWave);
		waveNumber ++;
		System.out.println("Beginning Wave " + waveNumber);
	}
	
	public Wave getCurrentWave() {
		return currentWave;
	}

}
