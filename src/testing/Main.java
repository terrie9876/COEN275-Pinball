package testing;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	
	public static void main(String args[]){
		JFrame frame = new JFrame("Pinball");
		Dimension size = new Dimension(800,800);
		FrameManager fm = new FrameManager(size);
		
		frame.setBounds(0,0,(int)size.getWidth(),(int)size.getHeight());
		frame.setMinimumSize(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(fm);
		frame.setVisible(true); // display frame
		frame.pack();
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				fm.requestFocusInWindow();
			}
			
		});
	}

}
