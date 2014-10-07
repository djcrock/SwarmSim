package com.djcrocker.swarmsim;

import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;

public class SwarmFrame extends JFrame {

	private static final long serialVersionUID = -4226835481663011378L;

	private SwarmComponent sc;

	public SwarmFrame(SwarmComponent newSC) {
		sc = newSC;
		setTitle("SwarmSim 2013");
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		int x = sc.getPreferredSize().width;
		int y = sc.getPreferredSize().height;
		Random r = new Random();
		
		//sc.addEntity(new Entity(15,400,400,0,-1.937,0,0,Color.BLUE));
		//sc.addEntity(new Entity(15,200,400,0,1.937,0,0,Color.BLUE));
		
		//sc.addEntity(new Entity(2,300,400,0,0,0,0,Color.BLUE));
		
		for(int i = 0; i<10000; i++) {
			sc.addEntity(new Entity(2,r.nextInt(x),r.nextInt(y),6.0-(r.nextDouble()+0.1)*10,6.0-(r.nextDouble()+0.1)*10,r.nextInt(5)+5,0,Color.BLUE));	
		}


		add(sc);

		pack();
		setVisible(true);
	}
}
