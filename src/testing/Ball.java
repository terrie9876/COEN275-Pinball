package testing;

import java.awt.*;
import java.util.ArrayList;

public class Ball extends Actor {
	private int gravity = 1;
	protected int radius;
	Vector2d spd;
	Dimension screen;//used to determine screen boundaries
	public Ball(double x, double y,Color color,int r, int sx, int sy, Dimension size){
		super(x,y,color);
		
		radius = r;
		spd = new Vector2d(sx,sy);
		screen = size;
		
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(color);
		
		checkBoundary();
		
		pos = pos.add(spd);
		
		//Limiting the speed of the ball
		if(spd.getLength() < 25 || spd.getY()<0)
			spd = spd.add(0,gravity);
		
		g.fillOval((int)pos.getX() - radius, (int)pos.getY() - radius, radius * 2, radius * 2);

	}
	
	//Function: getPointBySlope(Vector2d,howMany)
	//Purpose: Returns an ArrayList of Points that are on the edge of the ball. The first point will always be in the direction of slope
	//Note: I used slope so that the first point in the list will always be the one closest to the block that calls this function
	public ArrayList<Point> getPointBySlope(Vector2d slope, int howMany){
		ArrayList<Point> ans = new ArrayList<Point>();
		double angle = 360./(double)howMany;
		Vector2d direction = slope.rotate(angle),sum;
		
		for(int x = 0; x < howMany; x++){
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
	
	//Function: alterSpeed(Vector2d)
	//Purpose: To change the speed of the ball based on the tangent of the surface that it has collided with
	public void alterSpeed(Vector2d tangent){
		tangent.normalize();//only care about the direction of reflection
		for(int x = 0; x < 5 ; x++)
			pos = pos.add(tangent);//we want to push the ball away from the block in hopes of not getting stuck in the block
		
		double spdValue = spd.getLength();
		if(spdValue > 25) //Limiting the max speed of the ball
			spdValue = 25;
		spd.normalize();
		
		double scaleFactor = 2 * spd.dot(tangent);
		tangent.scale(scaleFactor);
		Vector2d newSpd = (tangent.subtract(spd)).inverse();
		newSpd.normalize();
		//System.out.println("Old Spd: "+spd.toString() + " New Spd: "+newSpd.toString() + " Tangent: " + tangent.toString());
		
		newSpd.scale(spdValue);
		spd = newSpd;
		
		
	}
	
	public void checkBoundary(){
		if ((int)pos.getX() < radius)
			spd.setX(Math.abs(spd.getX()));
		if ((int)pos.getX() + radius > screen.getWidth() )
			spd.setX(-Math.abs(spd.getX()));
		if ((int)pos.getY() < radius)
			spd.setY(Math.abs(spd.getY()));
		if ((int)pos.getY() + radius > screen.getHeight())
			spd.setY(-Math.abs(spd.getY())*.95);
	}

}
