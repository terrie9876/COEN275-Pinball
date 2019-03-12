package testing;

import java.awt.event.*;

public class keyManager extends KeyAdapter {

	boolean setlock = false;
	boolean rPaddle, lPaddle;
	double rAngle = 0, lAngle = 0;
	double paddleAngle = 130;
	double paddleLeftUp, paddleRightUp, paddleLeftDown, paddleRightDown;
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			lPaddle = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rPaddle = true;
		}
	}
	
	public void keyReleased(KeyEvent e){
	
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			lPaddle = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rPaddle = false;
		}
	}
	
	public void changePaddle() {
		if (rPaddle) {
			rAngle = Math.max(-0.5, rAngle - 0.1);
		}else{
			rAngle = Math.min(0.5, rAngle + 0.1);
		}
		if(lPaddle){
			lAngle = Math.max(-0.5, lAngle - 0.1);
		}else{
			lAngle = Math.min(0.5, lAngle + 0.1);
		}
		
		paddleLeftUp = paddleLeftUp + (int) (Math.cos(lAngle) * paddleAngle);
		paddleLeftDown = paddleLeftDown + (int) (Math.sin(lAngle) * paddleAngle);
		
		paddleRightUp = paddleRightUp + (int) (-Math.cos(rAngle) * paddleAngle);
		paddleRightDown = paddleRightDown + (int) (Math.sin(rAngle) * paddleAngle);
		
		
	}
}
