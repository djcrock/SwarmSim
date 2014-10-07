package com.djcrocker.swarmsim;

public class Run extends Thread {
	
	private final int TICK_TIME = 15;
	private SwarmComponent sc;

	public Run() {
		sc = new SwarmComponent(800,800);
		new SwarmFrame(sc);
	}
	public static void main(String[] args) {
		Run r = new Run();
		r.start();
	}

	@Override
	public void run() {
		long start;
		long calcTime;
		while(true) {
			start = System.currentTimeMillis();
			sc.tick();
			sc.repaint();
			calcTime = System.currentTimeMillis() - start;
			if(TICK_TIME - calcTime > 0) {
				try {
					sleep(TICK_TIME - calcTime);
				}
				catch(InterruptedException ie) {}
			}
		}
	}
}
