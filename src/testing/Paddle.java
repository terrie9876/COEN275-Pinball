package testing;

import java.awt.Color;

public class Paddle extends Block {
	protected double minAngle, maxAngle;
	protected double angleInterval;

	public Paddle(double x, double y, Color color, int width, int height, double angle, double bounceFactor, double minAngle, double maxAngle) {
		super(x, y, color, width, height, angle, bounceFactor);
		
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		
		this.angleInterval = 5;
		
	}

	public void rotate(boolean way){
		int negative = way? 1:-1;
		
		angle += negative * angleInterval;
		
		if(way && angle > maxAngle)
			angle = maxAngle;
		else if (!way && angle < minAngle)
			angle = minAngle;
		
		this.setCorners();
		
	}
	

	
	

}
