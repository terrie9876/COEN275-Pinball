package testing;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Actor{
	protected Vector2d pos;//x and y have to be ints for the coordinate system
	protected Color color;
	public Actor(double x,double y, Color color){
		this.pos = new Vector2d(x,y);
		this.color = color;
	}
	
	public double getX(){
		return pos.getX();
	}
	
	public double getY(){
		return pos.getY();
	}
	
	public Vector2d getPos(){
		return new Vector2d(pos.getX(),pos.getY());
	}
	
	public void setX(double x){
		this.pos.setX(x);
	}
	
	public void setY(double y){
		this.pos.setY(y);
	}
	
	public abstract void draw(Graphics g);

}
