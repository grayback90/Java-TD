package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {
	
	public static final int WIDTH = 1280, HEIGHT = 960;
	
	//method to create the screen
	public static void beginSession() {
		//set title
		Display.setTitle("Java TD by Gray");
		try {
			//DisplaySize
			Display.setDisplayMode(new DisplayMode(WIDTH,  HEIGHT));
			//create the screen
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		//setting OpenGL up
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	//method to draw Quads
	public static void drawQuad(float x, float y, float width, float height) {
		//drawing a quad with OpenGL
		glBegin(GL_QUADS);
		glVertex2f(x, y); //Top left corner
		glVertex2f(x + width, y); //Top right corner
		glVertex2f(x + width, y + height); //Bottom right corner
		glVertex2f(x, y + height); //bottom left corner
		glEnd();
		
	}
	
	//method to draw Quads-Texture
	public static void drawQuadTex(Texture tex, float x, float y, float width, float height) {
		//Bind the texture
		tex.bind();
		//translate (uses local coordinates instead of x+width and so on)
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		//x and y are the new 0, 0
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		//setting the other corners
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
	}
	
	//method to draw Quads-Texture and rotate them
	public static void drawQuadTexRot(Texture tex, float x, float y, float width, float height, float angle) {
		//Bind the texture
		tex.bind();
		//translate (setting coords to the middlepoint of the tile)
		glTranslatef(x + width / 2, y + height / 2, 0);
		//rotate the tile
		glRotatef(angle, 0, 0, 1);
		//translate it back after rotate (so there are only x and y, so get rid of the other stuff)
		glTranslatef(- width / 2, - height / 2, 0);
		glBegin(GL_QUADS);
		//x and y are the new 0, 0
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		//setting the other corners
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
	}
	
	//method to load textures from png
	public static Texture loadTexture(String path, String fileType) {
		Texture tex = null;
		
		//get external files
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return tex;
	}

	//method to quickload and to have less overhead in class definitions
	public static Texture quickload(String name) {
		Texture tex = null;
		
		tex = loadTexture("res/" + name + ".png", "PNG");
		
		return tex;
	}
}
