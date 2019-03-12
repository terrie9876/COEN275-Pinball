package testing;

import java.awt.Color;
import java.awt.Point;

public class Paddle extends Block {
	double minAngle, maxAngle;
	boolean isLeft;
	double angleInterval;

	public Paddle(double x, double y, Color color, int width, int height, double angle, double minAngle, double maxAngle, boolean isLeft) {
		super(x, y, color, width, height, angle);
		
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		
		this.isLeft = isLeft;
		this.angleInterval = 10;
		
	}

	public void rotatePaddle(boolean way){
		int negative = way? 1:-1;
		
		angle += negative * angleInterval;
		
		if(way && angle > maxAngle)
			angle = maxAngle;
		else if (!way && angle < minAngle)
			angle = minAngle;
		
		this.setCorners();
		
	}
	

	
	

}
