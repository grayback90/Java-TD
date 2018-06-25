package data;

public enum TileType {

	//Create actual tile types
	Grass("baseTiles/grass64", true), Dirt("baseTiles/dirt64", false), Water("baseTiles/water64", false), NULL("baseTiles/water64", false);
	
	String textureName;
	boolean buildable;
	
	//constructor
	TileType(String textureName, boolean buildable) {
		this.textureName = textureName;
		this.buildable = buildable;
	}
}