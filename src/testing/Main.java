package project.last.testing;

import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	
	public static void main(String args[]){
		JFrame frame = new JFrame("Pinball Tester");
		Dimension size = new Dimension(800,800);
		FrameManager fm = new FrameManager(size);
		
		frame.setBounds(0,0,(int)size.getWidth(),(int)size.getHeight());
		frame.setSize(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(fm);
		frame.setVisible(true); // display frame
	}

}
