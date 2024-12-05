//CLIENT INSTANCE

package froggerGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class logSprite extends sprite{
	
	private Boolean isMoving;
	//private Thread thread;
	private JLabel logLabel;
	private frogSprite frog;
	private JLabel frogLabel;
	private int stepSpeed, stepDirection;
	boolean isIntersecting = true;

	public logSprite() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public logSprite(int x, int y, int height, int width, String image, Boolean isMoving) {
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
	
	public JLabel getLogLabel() {
		return logLabel;
	}

	public int getStepDirection() {
		return stepDirection;
	}
	public void setStepDirection(int stepDirection) {
		this.stepDirection = stepDirection;
	}
	public void setLogLabel(JLabel logLabel) {
		this.logLabel = logLabel;
	}
	
	public frogSprite getFrog() {
		return frog;
	}

	public void setFrog(frogSprite frog) {
		this.frog = frog;
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
	public void setIntersecting(boolean isIntersecting) {
		this.isIntersecting = isIntersecting;
	}
	
	
}
