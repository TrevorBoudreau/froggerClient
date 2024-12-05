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

	
}
