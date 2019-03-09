package project.last.testing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class FrameManager extends JPanel implements ActionListener {

	private ArrayList<Actor> actors;//List of all actors for easier repainting
	private ArrayList<Block> blocks;//list of blocks for collision detection looping
	
	private Ball ball;
	
	private Dimension screenSize;
	
	private Timer timer;
	
	public FrameManager(Dimension screenSize){
		
		this.screenSize = screenSize;
		this.setBackground(Color.LIGHT_GRAY);
		timer = new Timer(50,this);
		
		populateLists();
		
		timer.start();
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		checkCollision();
		repaint();

	}
	
	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
		for(Actor a: actors){
			a.draw(arg0);
		}
		
	}
	
	private void checkCollision(){
		for(Block b: blocks){
			b.collidedWith(ball);
		}
	}
	
	private void populateLists(){
		blocks = new ArrayList<Block>();
		actors = new ArrayList<Actor>();
//		Saving this specifically because of an interesting test case
//		ball = new Ball(300,250,Color.BLACK,10, 7,7,screenSize);
//		
//		Block b1 = new Block(300, 300, Color.RED,100, 200, -45);
//		Block b2 = new Block(200,400,Color.GREEN,80,25,45);
//		Block b3 = new Block(100, 300,Color.BLUE,75,45,60);
		
//		blocks.add(b1);
//		blocks.add(b2);
//		blocks.add(b3);
		
//		actors.add(b1);
//		actors.add(b2);
//		actors.add(b3);
//		actors.add(ball);
		
		ball = new Ball(300,250,Color.BLACK,10, 7,7,screenSize);
		
		double middleScreen = screenSize.getWidth()/2;
		
		Block wallLeft = new Block(0, 0, Color.BLACK,1100, 140, 0);
		Block wallRight = new Block(0,0,new Color(0,127,0),140,1100,0);
		Block ceiling = new Block(screenSize.getWidth() - 140, 0,new Color(0,127,0),140,1100,0);
		
		Block b1 = new Block(middleScreen/2+75, 200,Color.RED, 60, 90,0);
		Block b2 = new Block(middleScreen/2-75, 200,Color.RED, 60, 90, 0);
		
		
		
		blocks.add(wallLeft);
		blocks.add(wallRight);
		blocks.add(ceiling);
		blocks.add(b1);
		blocks.add(b2);
		
		actors.add(wallLeft);
		actors.add(wallRight);
		actors.add(ceiling);
		actors.add(b1);
		actors.add(b2);
		actors.add(ball);
		
	}

}
