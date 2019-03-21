package testing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class FrameManager extends JPanel implements ActionListener {

	private ArrayList<Actor> actors;// List of all actors for easier repainting
	private ArrayList<Block> blocks;// list of blocks for collision detection looping
	private Paddle paddleLeft, paddleRight;
	private boolean play,gameOver;

	private final KeyManager kManager;

	private Ball ball;

	private Dimension screenSize;

	private Timer timer;

	public FrameManager(Dimension screenSize) {

		this.screenSize = screenSize;
		

		timer = new Timer(45, this);
		kManager = new KeyManager();
		play = false;
		gameOver = false;
		

		addKeyListener(kManager);

		timer.start();

	}
	
	private void rotatePaddles() {
		paddleLeft.rotate(kManager.isLeft());
		paddleRight.rotate(kManager.isRight());
	}
	
	private void checkCollision() {
		ArrayList<Thread> tList = new ArrayList<Thread>();
		//Makes and start collision checking thread for each block
		for (Block b : blocks) {
			CollisionRunnable cr = new CollisionRunnable(b, ball);
			Thread t = new Thread(cr);
			tList.add(t);
			t.start();
		}

		//wait for all threads to finish
		for (Thread t : tList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void gameOver(Graphics g){
		this.setBackground(Color.BLACK);
		g.setFont(new Font(g.getFont().getFontName(),Font.BOLD,32));
		g.setColor(Color.RED);
		g.drawString("The ball fell. That's Game Over!", screenSize.width/2-250, screenSize.height/2-100);
		g.setColor(Color.WHITE);
		g.setFont(new Font(g.getFont().getFontName(),Font.ITALIC,24));
		g.drawString("Your Score: " + Integer.toString(ball.getScore()), screenSize.width/2-100, screenSize.height/2);
		g.drawString("Press \"Space\" to try again!", screenSize.width/2-150, screenSize.height/2+100);
		if(kManager.isStart()){
			populateLists();
			play = true;
			gameOver = false;
		}
		g.setColor(Color.BLACK);
	} 
	
	private void mainMenu(Graphics g){
		this.setBackground(Color.WHITE);
		g.setFont(new Font(g.getFont().getFontName(),Font.BOLD,32));
		g.drawString("Welcome to PinBall", screenSize.width/2-175, screenSize.height/2-100);
		g.setFont(new Font(g.getFont().getFontName(),Font.ITALIC,24));
		g.drawString("Press \"Space\" to let the ball drop", screenSize.width/2-200, screenSize.height/2);
		if(kManager.isStart()){
			populateLists();
			play = true;
		}
		
	}
	
	private void playGame(Graphics g){
		this.setBackground(Color.LIGHT_GRAY);
		for (Actor a : actors) {
			a.draw(g);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font(g.getFont().getFontName(),Font.BOLD,32));
		g.drawString("Score: " + Integer.toString(ball.getScore()), 10, 50);
		g.setColor(Color.BLACK);
		if(ball.getY() > screenSize.getHeight()-50){
			gameOver = true;
			play = false;
		}
	}
	
	@Override
	protected void paintComponent(Graphics arg0) {

		super.paintComponent(arg0);
		if(play)
			playGame(arg0);
		else{
			if(gameOver){
				gameOver(arg0);
			}
			else{
				mainMenu(arg0);
			}
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(play){
			checkCollision();
			rotatePaddles();
		}
		repaint();

	}

//	Function: populateList()
//	Purpose: Makes all the actors for the pinball board. Useful for resetting the situation
	private void populateLists() {
		blocks = new ArrayList<Block>();
		actors = new ArrayList<Actor>();
		Point p;
		double middleScreen = screenSize.getWidth() / 2;

		Random rand = new Random();
		double randX = rand.nextDouble() < .5 ? middleScreen + (rand.nextDouble() * 30 + 50) :middleScreen - (rand.nextDouble()*30+50);
		ball = new Ball(randX, 220 + rand.nextDouble() * 20, Color.RED.darker(), 10, rand.nextDouble()*20-10, rand.nextDouble()*20-10);

		//Block( xPos, yPos, color, width, height, angle)
		Block ceiling = new Block(0, 0, Color.BLACK, 1100, 140, 0,1);
		Block wallLeft = new Block(0, 0, new Color(0, 127, 0), 150, 1100, 0,1);
		Block wallRight = new Block(screenSize.getWidth() - 150, 0, new Color(0, 127, 0), 150, 1100, 0,1);

		Block inclineLeft = new Block(125, 400, new Color(0, 127, 0), 250, 60, 40,.9);
		p = inclineLeft.getCorner(true, true);
		Block cliffLeft = new Block(p.getX() - 60, p.getY(), new Color(0, 127, 0), 60, 300, 0,.9);
		paddleLeft = new Paddle(p.getX() - 10, p.getY(), new Color(127, 127, 0), 80, 30, 20, 1.5, -40, 20);

		p = new Point((int) (2 * middleScreen - p.getX()), (int) p.getY());
		Block inclineRight = new Block(p.getX(), p.getY(), new Color(0, 127, 0), 250, 60, -40,.9);
		Block cliffRight = new Block(p.getX(), p.getY(), new Color(0, 127, 0), 60, 300, 0,.9);
		paddleRight = new Paddle(p.getX() + 10, p.getY(), new Color(127, 127, 0), 30, 80, 70,1.5, 70, 130);

		Block b1 = new Block((int) middleScreen - 90, 250, Color.RED, 60, 60, 20,.9);
		Block b2 = new Block((int) 2*middleScreen - b1.getCorner(true, false).getX(), 250, Color.RED, 60, 60, -20,1.9);
		Block b3 = new Block((int) middleScreen, 350, Color.RED, 60, 60, 45,.9);

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
		blocks.add(b3);

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
		actors.add(b3);
		actors.add(ball);

	}

}
