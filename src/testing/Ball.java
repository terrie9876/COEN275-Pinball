package testing;

import java.awt.*;
import java.util.ArrayList;

public class Ball extends Actor {
	private double gravity = 1;
	private int maxSpeed = 20;
	private int radius, score;
	private Vector2d spd;
	
	public Ball(double x, double y,Color color,int r, double sx, double sy){
		super(x,y,color);
		
		score = 0;
		radius = r;
		spd = new Vector2d(sx,sy);
		
	}

	//Function: getPointBySlope(Vector2d,int)
	//Purpose: Returns an ArrayList of howMany number of Points on the edge of the Ball, where the middle value of the ArrayList is in the direction of the given slope
	public ArrayList<Point> getPointBySlope(Vector2d slope, int howMany){
		ArrayList<Point> ans = new ArrayList<Point>();
		double angle = 180./(double)howMany;
		Vector2d direction = slope.rotate(-90),sum;
		
		for(int x = 0; x <= howMany; x++){
			direction.normalize();
			direction.scale(radius);
			sum = pos.add(direction);
			ans.add(new Point((int)sum.getX(),(int)sum.getY()));
			direction = direction.rotate(angle);
		}
		
		return ans;
	}
	
	public Vector2d getBallSpd(){
		return new Vector2d(spd.getX(),spd.getY());
	}
	
	public int getScore(){
		return score;
	}
	
	//Function: alterSpeed(Vector2d)
	//Purpose: To change the speed of the ball based on the tangent of the surface that it has collided with
	//Note: We increase score here because this method is only called when collision detection occurs
	public void alterSpeed(Vector2d tangent, double bounceFactor){
		tangent.normalize();//only care about the direction of reflection
		for(int x = 0; x < 5 ; x++)
			pos = pos.add(tangent);//we want to push the ball away from the block in hopes of not getting stuck in the block
		
		double spdValue = spd.getLength() * bounceFactor;
		
		if(spdValue > (double)maxSpeed) //Limiting the max speed of the ball
			spdValue = maxSpeed;
		spd.normalize();
		
		double scaleFactor = 2 * spd.dot(tangent);
		tangent.scale(scaleFactor);
		Vector2d newSpd = (tangent.subtract(spd)).inverse();
		newSpd.normalize();
		
		newSpd.scale(spdValue);
		spd = newSpd;
		
		score++;
		
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(color);
		
		
		pos = pos.add(spd);
		
		//Limiting the speed of the ball
		if(spd.getLength() < (double)maxSpeed || spd.getY()<0)
			spd = spd.add(0,gravity);
		
		g.fillOval((int)pos.getX() - radius, (int)pos.getY() - radius, radius * 2, radius * 2);

	}

}
