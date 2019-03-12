package testing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class FrameManager extends JPanel implements ActionListener{

	private ArrayList<Actor> actors;//List of all actors for easier repainting
	private ArrayList<Block> blocks;//list of blocks for collision detection looping
	private Paddle paddleLeft,paddleRight;
	private boolean play;
	private int clockers;
	
	private KeyManager kManager;
	
	private Ball ball;
	
	private Dimension screenSize;
	
	private Timer timer;
	
	public FrameManager(Dimension screenSize){
		
		this.screenSize = screenSize;
		this.setBackground(Color.LIGHT_GRAY);
		
		timer = new Timer(50,this);
		clockers = 0;
		kManager = new KeyManager();
		
		addKeyListener(kManager);
		
		populateLists();
		
		timer.start();
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		checkCollision();
		repaint();
		rotatePaddles();
		
	}
	
	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
		for(Actor a: actors){
			a.draw(arg0);
		}
		
	}
	
	private void rotatePaddles(){
		paddleLeft.rotatePaddle(kManager.isLeft());
		paddleRight.rotatePaddle(kManager.isRight());
		clockers++;
		clockers%=32;
	}
	
	private void checkCollision(){
		ArrayList<Thread> tList = new ArrayList<Thread>();
		for(Block b: blocks){
//			maybe we use this to show not multithreaded issues
//			b.collidedWith(ball);
//		}
			CollisionRunnable cr = new CollisionRunnable(b,ball);
			Thread t = new Thread(cr);
			tList.add(t);
			t.start();
		}
		
	     for (Thread t : tList)
	     {
	         try
	         {
	            // Thread call here
	        	 t.join();
	         } catch ( InterruptedException e )
	         {
	             e.printStackTrace();
	         }
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
//		
//		blocks.add(b1);
//		blocks.add(b2);
//		blocks.add(b3);
//		
//		actors.add(b1);
//		actors.add(b2);
//		actors.add(b3);
//		actors.add(ball);
		Point p;
		
		ball = new Ball(300,250,Color.BLACK,10, 7,7,screenSize);
		
		double middleScreen = screenSize.getWidth()/2;
		System.out.println(middleScreen);
		
		Block ceiling = new Block(0, 0, Color.BLACK,1100, 140, 0);
		Block wallLeft = new Block(0,0,new Color(0,127,0),150,1100,0);
		Block wallRight = new Block(screenSize.getWidth() - 150, 0,new Color(0,127,0),150,1100,0);
		
		Block inclineLeft = new Block(125,400,new Color(0,127,0),250,60,40);
		p = inclineLeft.getCorner(true, true);
		Block cliffLeft = new Block(p.getX()-60,p.getY(),new Color(0,127,0),60,300,0);
		paddleLeft = new Paddle(p.getX()-10, p.getY(), new Color(127,127,0), 80, 30, 20, -40, 20, true);
		
		p = new Point((int)(2*middleScreen - p.getX()),(int)p.getY());
		Block inclineRight = new Block(p.getX(),p.getY(),new Color(0,127,0),250,60,-40);
		Block cliffRight = new Block(p.getX(),p.getY(),new Color(0,127,0),60,300,0);
		paddleRight = new Paddle(p.getX()+10,p.getY(),new Color(127,127,0),30,80,70,70,130,false);
		
		
		Block b1 = new Block((int)middleScreen-90, 250,Color.RED, 60, 60,20);
		Block b2 = new Block((int)middleScreen+30, 250,Color.RED, 60, 60, -20);
		
		
		
		blocks.add(wallLeft);
		blocks.add(wallRight);
		blocks.add(ceiling);
		blocks.add(inclineLeft);
		blocks.add(cliffLeft);
		blocks.add(paddleLeft);
		blocks.add(inclineRight);
		blocks.add(cliffRight);
		blocks.add(paddleRight);
		blocks.add(b1);
		blocks.add(b2);
		
		actors.add(wallLeft);
		actors.add(wallRight);
		actors.add(ceiling);
		actors.add(inclineLeft);
		actors.add(inclineRight);
		actors.add(cliffLeft);
		actors.add(cliffRight);
		actors.add(paddleLeft);
		actors.add(paddleRight);
		actors.add(b1);
		actors.add(b2);
		actors.add(ball);
		
	}

}
