package testing;

import java.awt.Point;

//Helper class for manipulating values in a 2d environment
public class Vector2d {
	private double x,y;
	
	public Vector2d(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2d(Point tail, Point head){
		this.x = head.getX() - tail.getX();
		this.y = head.getY() - tail.getY();
	}

	public double getLength(){
		Double sum = Math.pow(x, 2) + Math.pow(y,2);
		return Math.pow(sum, .5);
	}
	
	public void normalize(){
		Double length = this.getLength();
		scale(1/length);
	}
	
	public void scale(double val){
		x*=val;
		y*=val;
	}
	
	public double dot(Vector2d d){
		return x*d.x + y*d.y;
	}
	

	
	public Vector2d add(Vector2d b){
		return new Vector2d(this.x +b.x,this.y+b.y);
	}
	
	public Vector2d add(double delX, double delY){
		return new Vector2d(this.x + delX, this.y + delY);
	}
	
	public Vector2d subtract(Vector2d b){
		return new Vector2d(this.x - b.x,this.y-b.y);
	}
	
	public Vector2d subtract(double delX, double delY){
		return new Vector2d(this.x - delX, this.y - delY);
	}
	
	//returns vector2d pointing in opposite direction of this vector 2d
	public Vector2d inverse(){
		return new Vector2d(-this.x,-this.y);
	}
	

	//assume angle given in degrees
	public Vector2d rotate(double angle){
		angle = Math.toRadians(angle);
		double sine = Math.sin(angle), cosine = Math.cos(angle);
		return new Vector2d(this.x * cosine - this.y * sine, this.x * sine + this.y * cosine);
	}
	
//	//Need for checking bounds of rectangle based on only having two corners
//	public Vector2d getCCWVec(){
//		// A Vector v will be turned 90degress CCW when v.x = v.y and v.y = -v.x
//		//if upslope is 2,-3, the CCW turn 90 will give -3, -2
//		return new Vector2d(this.y,-this.x);
//	}
//	
//	public Vector2d getCWVec(){
//		//A Vector v will be turned 90degrees clockwise when v.x = -v.y and v.y = v.x
//		return new Vector2d(-this.y,this.x);
//	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Double.toString(x) + " : " + Double.toString(y);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this)
			return true;
		
		if(!(obj instanceof Vector2d))
			return false;
		Vector2d temp = (Vector2d)obj;
		
		//we have to account to the hardware limitations of calulating sin and cosine
		return (Math.abs(this.x - temp.x) < 1e-8) && (Math.abs(this.y - temp.y) < 1e-8);
	}
	
	
}
