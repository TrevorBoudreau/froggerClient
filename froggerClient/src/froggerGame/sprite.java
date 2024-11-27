package froggerGame;

import java.awt.Rectangle;

//import java.awt.Rectangle;

/*
 * 
 * 
 * 
 * 
 */

public class sprite {

	protected int x, y;
	protected int height, width;
	protected String image;
	
	//for collision detection with cars and logs
	protected Rectangle hitbox;
	
	//default constructor
	public sprite() {
		super();
		hitbox = new Rectangle(0,0,0,0);
	}

	//secondary constructor
	public sprite(int x, int y, int height, int width, String image) {
		super();
		
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.image = image;
		hitbox = new Rectangle(x,y,width,height);
	}

	//auto-generated getters and setters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		//move hitbox with frog
		this.hitbox.x = this.x;
	}


	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		//move hitbox with frog
		this.hitbox.y = this.y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	//for debugging, likely won't be used
	public int getHitboxX() {
		return this.hitbox.x;
	}
	
	public int getHitboxY() {
		return this.hitbox.y;
	}
	
	
	
}
