package testing;

import java.awt.event.*;

public class KeyManager extends KeyAdapter implements KeyListener{

	private boolean rPaddle, lPaddle, gStart;
	
	public KeyManager(){
		lPaddle = true;
		rPaddle = false;
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			lPaddle = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rPaddle = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			gStart = true;
		}
		
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			lPaddle = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rPaddle = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			gStart = false;
		}
	}
	
	public boolean isLeft(){
		return lPaddle;
	}
	
	public boolean isRight(){
		return rPaddle;
	}
	
	public boolean isStart(){
		return gStart;
	}
	
	
}
