package testing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;


/*        		(Top if angle=0)
 *              ______________
 *              |            |  
 *              |            |   Right
 * 	            |            |
 *              --------------
 */  

public class Block extends Actor {
	protected int width, height;
	protected Point corTL,corBR,center;
	protected Point corTR,corBL; // these are just to potentially help with level creation
	protected Vector2d tangentUp,tangentLeft, toTL, toTR;
	protected double angle,bounceFactor;
	public Block(double x, double y,Color color, int width,  int height, double angle, double bounceFactor) {
		super(x, y, color);
		this.width = width;
		this.height = height;
		this.angle = angle;
		this.bounceFactor = bounceFactor;
		
		setCorners();
		
	}

//	Function: getCorner(bool,bool)
//	Purpose: To return a given corner based on the boolean values.
//	Note: This method is mainly used when designing the board, as it's much easier to align blocks together by their corners
	public Point getCorner(boolean isTop, boolean isRight){
		if(isTop){
			if(isRight)
				return corTR;
			else
				return corTL;
		}
		else{
			if(isRight)
				return corBR;
			else
				return corBL;
		}
	}
	
	
//	Function setCorners()
//	Purpose: To set up all of the initial variables including the points representing the 4 corners, and the upward facing tangent
//	Notes: I made this a method because when Paddle extends this class, I need a way to recalculate all of the corners and the tangents as it rotates
	public void setCorners(){
		// Calculating differences for the BR Corner and Center point calculation
		int delX = (int) (width * Math.cos(Math.toRadians(angle)) - height * Math.sin(Math.toRadians(angle)));
		int delY = (int) (width * Math.sin(Math.toRadians(angle)) + height * Math.cos(Math.toRadians(angle)));

		// setting up corners and center
		corTL = new Point((int) pos.getX(), (int) pos.getY());
		corTR = new Point((int) (corTL.getX() + width * Math.cos(Math.toRadians(angle))), (int) (corTL.getY() + width * Math.sin(Math.toRadians(angle))));
		corBL = new Point((int) (corTL.getX() - height * Math.sin(Math.toRadians(angle))), (int) (corTL.getY() + height * Math.cos(Math.toRadians(angle))));
		corBR = new Point((int) pos.getX() + delX, (int) pos.getY() + delY);
		center = new Point((int) pos.getX() + delX / 2, (int) pos.getY() + delY / 2);

		// making Vector2d that contains direction from center to listed corner
		toTL = new Vector2d(center, corTL);
		toTR = new Vector2d(center, corTR);

		// Making tangents
		tangentUp = new Vector2d(Math.cos(Math.toRadians((angle - 90))), Math.sin(Math.toRadians(angle - 90)));
		tangentLeft = tangentUp.rotate(-90);

		// Only care about direction of tangents
		tangentUp.normalize();
		tangentLeft.normalize();
	}
	
//	Function: whichSide(Point)
//	Purpose: To figure out which of the four sides a point is closest to, specifically the center of the ball
	public Vector2d whichSide(Point p){
		boolean isUR = checkBoundary(p, center, toTL.rotate(-90), toTL.getX() < 0, toTL.getY() < 0);
		boolean isUL = checkBoundary(p, center, toTR.rotate(-90), toTR.getX() > 0, toTR.getY() > 0);
		

		int whichOne = 2 * (isUR ? 0 : 1) + ((isUL == isUR) ? 0 : 1);
		/*
		 * Values of whichOne correspond to different sides
		 * 0-Up
		 * 1-Right
		 * 2-Down
		 * 3-Left
		 */
		return tangentUp.rotate(90 * whichOne);
	}
	
//	Function: collidedWith(Ball)
//	Purpose: To see if the given ball has collided with this Block
//	Note: This is the method that gets called from FrameManager to initiate collision detection
	public void collidedWith(Ball ball){
		int numPoints = 36;// number of points that we want to check
		Vector2d currTan = this.whichSide(new Point((int) ball.getPos().getX(), (int) ball.getPos().getY()));//pass the center of the ball as a point
		ArrayList<Point> ptChecks = ball.getPointBySlope(currTan.inverse(), numPoints);
		

		for (Point p : ptChecks) {
			if (isInRectangle(p)) {
				
				ball.alterSpeed(currTan,bounceFactor);
				return;
			}
		}
	}
	
//	Function: isInRectangle(Point)
//	Purpose: Returns true if a given point lies within a Rectangle, false otherwise
	private boolean isInRectangle(Point p){
		Vector2d tangent = tangentUp.rotate(0);
		for (int x = 0; x < 4; x++) {
			if (tangent.equals(tangentUp) || tangent.equals(tangentLeft)) {
				// checking upward or left facing side
				if (!checkBoundary(p, corTL, tangent, tangent.getY() > 0, tangent.getX() < 0))
					return false;
			} 
			else {
				// checking right and down facing side
				if (!checkBoundary(p, corBR, tangent, tangent.getY() > 0, tangent.getX() < 0))
					return false;

			}
			tangent = tangent.rotate(90);// getCWVec();
		}

		return true;
	}
	
//	Function: checkBoundary(Point,Point,Vector2d,bool,bool)
//	Purpose: Returns true depending on the relationship between the two Points relative to the slope and the two bool values
	private boolean checkBoundary(Point check, Point inLine, Vector2d tangent, boolean checkUp, boolean checkRight){
		Vector2d slope = tangent.rotate(90);// slope is perpendicular to the tangent

		// if the side is sufficiently vertical (we don't want to accidentally do division by 0)
		if (Math.abs(slope.getX()) <= 1e-8) {
			return checkRight == (check.getX() > inLine.getX());

		}

		// from point slope formula, y-y1 = m(x-x1) where (x,y) is from check and (x1,y1) are from inLine
		// y_pred = (slope.y/slope.x)(check.x - inLine.x) + inLine.y
		int y_pred = (int) ((slope.getY() / (double) slope.getX()) * (check.getX() - inLine.getX()) + inLine.getY());
		return checkUp == (check.getY() < y_pred);
//		if(isDown)
//			return check.getY() < y_pred;
//		else
//			return check.getY() >= y_pred;
	}
	
	public void draw(Graphics g){
		Graphics2D gr = (Graphics2D) g;
		gr.setColor(color);
		gr.rotate(Math.toRadians(angle),(int)pos.getX(), (int)pos.getY());
		gr.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
		gr.rotate(Math.toRadians(-angle),(int)pos.getX(), (int)pos.getY());
	}
	


}
