package testing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Timer;


/*        (Top if angle=0)
 *              ______________
 *              |            |  
 *              |            |   (Top if angle=90)
 * 	            |            |
 *              --------------
 */  

public class Block extends Actor {
	protected int width, height;
	protected Point corTL,corBR,center,corTR,corBL; // the last two are just for convenience
	protected double boundingW, boundingH;
	public Vector2d tangentUp,tangentLeft, toTL, toTR;
	protected double angle;
	public Block(double x, double y,Color color, int width,  int height, double angle) {
		super(x, y, color);
		this.width = width;
		this.height = height;
		this.angle = angle;
		
		
		setCorners();
		//System.out.println(corTL.toString() + ":" + corTR.toString() + ":" + corBL.toString() + ":" + corBR.toString() + ":" + Double.toString(upSlopeY) + ":" + Double.toString(upSlopeX));
		
	}

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
	
	public void setCorners(){
		corTL = new Point((int)pos.getX(),(int)pos.getY());
		corTR = new Point((int)(corTL.getX() + width * Math.cos(Math.toRadians(angle))),(int)(corTL.getY() + width * Math.sin(Math.toRadians(angle))));
		corBL = new Point((int)(corTL.getX() - height * Math.sin(Math.toRadians(angle))),(int)(corTL.getY() + height * Math.cos(Math.toRadians(angle))));
		
		int delX = (int)(width*Math.cos(Math.toRadians(angle)) - height*Math.sin(Math.toRadians(angle)));
		int delY = (int)(width*Math.sin(Math.toRadians(angle)) + height*Math.cos(Math.toRadians(angle)));
		
		corBR = new Point((int)pos.getX() + delX, (int)pos.getY() + delY);
		center = new Point((int)pos.getX() + delX/2, (int)pos.getY() + delY/2);
		
		toTL = new Vector2d(center,corTL);
		toTR = new Vector2d(center,corTR);
		
		tangentUp = new Vector2d(Math.cos(Math.toRadians((angle - 90))),Math.sin(Math.toRadians(angle - 90)));
		tangentLeft = tangentUp.rotate(-90);//getCCWVec();
		
		tangentUp.normalize();
		tangentLeft.normalize();
	}
	
	public Vector2d whichSide(Point p){
		boolean isUR = checkBoundary(p,center,toTL.rotate(-90),toTL.getX() < 0,toTL.getY()<0);
		boolean isUL = checkBoundary(p,center,toTR.rotate(-90),toTR.getX() > 0,toTR.getY()>0);
		
		int whichOne = 2 * (isUR?0:1) + ((isUL == isUR)?0:1);
		return tangentUp.rotate(90 * whichOne);
	}
	
	public void collidedWith(Ball ball){
		int numPoints = 30;
		ArrayList<Point> ptChecks = ball.getPointBySlope(tangentUp.inverse(),numPoints);
		//Right,Down,Left,Up
		
		//if ball at 40,30 at TL corner is at 35,25
		Vector2d currTan = this.whichSide( new Point((int)ball.getPos().getX(),(int)ball.getPos().getY()));
		
		
		
		for(Point p : ptChecks){
			if(isInRectangle(p,currTan)){
				ball.alterSpeed(currTan);
				break;
			}
		}
		
	}
	
	public boolean isInRectangle(Point p, Vector2d tangent){
		for(int x = 0; x < 4; x++){
			//System.out.println("ISInRectangle: " + tangent.toString());
			//if we are looking at what we've marked as the Up or Left side of the rectangle
			if(tangent.equals(tangentUp)||tangent.equals(tangentLeft)){
				//System.out.println("Checking left or top side");
				if(!checkBoundary(p,corTL,tangent,tangent.getY() > 0,tangent.getX() < 0))
					return false;
			}
			else{
				//System.out.println("Checking right or bottom side");
				//this else statement checks right and down facing side
				if(!checkBoundary(p,corBR,tangent,tangent.getY() > 0,tangent.getX() < 0))
					return false;
				
			}
			tangent = tangent.rotate(90);//getCWVec();
		}
		
		return true;
	}
	
	public boolean checkBoundary(Point check, Point inLine, Vector2d tangent, boolean isDown, boolean isLeft){
		Vector2d slope = tangent.rotate(90);//getCWVec();
		
		//if the side is sufficiently vertical
		if(Math.abs(slope.getX()) <= 1e-8){
			return isLeft == (check.getX() > inLine.getX());
//			ANother way to look at this return statement			
//			if(toLeft)
//				return check.getX() > inLine.getX();
//			else
//				return check.getX() <= inLine.getX();
		}
		
		//from point slope formula, y-y1 = m(x-x1) where (x,y) is from check and (x1,y1) are from inLine
		//y_pred = (slope.y/slope.x)(check.x - inLine.x) + inLine.y
		int y_pred = (int)((slope.getY()/(double)slope.getX())*(check.getX() - inLine.getX()) + inLine.getY());
		//same as the above return statement
		return isDown == (check.getY() < y_pred);
	}
	
	public void draw(Graphics g){
		Graphics2D gr = (Graphics2D) g;
		gr.setColor(color);
		gr.rotate(Math.toRadians(angle),(int)pos.getX(), (int)pos.getY());
		gr.fillRect((int)pos.getX(), (int)pos.getY(), width, height);
		gr.rotate(Math.toRadians(-angle),(int)pos.getX(), (int)pos.getY());
	}
	


}
