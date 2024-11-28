//CLIENT INSTANCE

package froggerGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class carSprite extends sprite {
	
	private Boolean isMoving;

	private frogSprite frog;
	private JLabel carLabel, frogLabel;
	private int stepSpeed, stepDirection;

	public carSprite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public carSprite(int x, int y, int height, int width, String image, Boolean isMoving) {
		super(x, y, height, width, image);
		this.isMoving = isMoving;
		// TODO Auto-generated constructor stub
	}

	public Boolean getIsMoving() {
		return isMoving;
	}

	public void setIsMoving(Boolean isMoving) {
		this.isMoving = isMoving;
	}

	public frogSprite getFrog() {
		return frog;
	}

	public void setFrog(frogSprite frog) {
		this.frog = frog;
	}

	public JLabel getCarLabel() {
		return carLabel;
	}

	public void setCarLabel(JLabel carLabel) {
		this.carLabel = carLabel;
	}

	public JLabel getFrogLabel() {
		return frogLabel;
	}

	public void setFrogLabel(JLabel frogLabel) {
		this.frogLabel = frogLabel;
	}

	public int getStepSpeed() {
		return stepSpeed;
	}

	public void setStepSpeed(int stepSpeed) {
		this.stepSpeed = stepSpeed;
	}

	public int getStepDirection() {
		return stepDirection;
	}

	public void setStepDirection(int stepDirection) {
		this.stepDirection = stepDirection;
	}

	/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (this.getIsMoving() == true) {
			int temp = this.getX();
			
			//System.out.println("car tick");
			
			//direction
			if ( getStepDirection() == 1 ) {
				 temp += stepSpeed;
			} else if (getStepDirection() == 2 ) {
				 temp -= stepSpeed;
			}
			
			//check if car goes offscreen and wrap to other side
			if ( temp >= gameProperties.SCREEN_WIDTH && stepDirection == 1 ) {
				temp = this.getWidth() * -1;
			} else if ( temp + this.getWidth() <= 0 && stepDirection == 2 ) {
				temp = gameProperties.SCREEN_WIDTH;
			}
			
			//update location of car and label
			this.setX(temp);
			carLabel.setLocation( this.getX(), this.getY() );
			
			//collision text
			this.detectCollision();
			
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	//method to check if there's collision with the frog
	
	private void detectCollision() {
		if (this.hitbox.intersects( frog.getHitbox() ) ) {
			
			System.out.println("Collision");
			
			this.setIsMoving(false);
			this.stopThread();
		}
	}
	
	
	//method to start thread
	public void runThread() {
		
		//if thread isn't running, start thread
		if ( this.getIsMoving() == false ) {
			this.setIsMoving(true);
			thread = new Thread(this, "car thread");
			thread.start();
		}
		
	}
	
	public void stopThread() {
		this.setIsMoving(false);
	}
	
	*/
	
}
