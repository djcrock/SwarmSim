package com.djcrocker.swarmsim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class SwarmComponent extends JComponent implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 5819895776939238051L;

	private int sizeX;
	private int sizeY;
	private int targetX;
	private int targetY;
	private Vector2D targetVel = new Vector2D(0,0);
	private List<Entity> entities;
	private boolean pressed = false;
	private Entity e1, e2;

	public SwarmComponent(int x, int y) {
		super();
		addMouseMotionListener(this);
		addMouseListener(this);
		sizeX = x;
		sizeY = y;
		setPreferredSize(new Dimension(x,y));
		entities = new ArrayList<Entity>();
		e1 = new Entity(2,100,100,1,1,Color.RED);
		e2 = new Entity(2,500,200,3,5,1,1,Color.BLUE);
		entities.add(e1);
		entities.add(e2);

	}

	public boolean addEntity(Entity e) {
		for(Entity col : entities) {
			if(e.isTouching(col)) {
				return false;
			}
		}
		entities.add(e);
		return true;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		int currentPass = 0;
		int passesToComplete = 1;
		for(Entity c : entities) {
			if(c.renderPass < currentPass) {
				currentPass = c.renderPass;
			}
			else if(c.renderPass > passesToComplete) {
				passesToComplete = c.renderPass;
			}
		}
		while(currentPass <= passesToComplete) {
			for(Entity e : entities) {
				if(currentPass == e.renderPass) {
					drawCircle(g2d,e.posX,e.posY,e.radius,e.color);
				}
			}
			currentPass++;
		}
	}

	public void tick() {
		/*
		Quadtree tree = new Quadtree(0, new Rectangle(sizeX,sizeY));
		tree.addAll(entities);
		for(Entity e : entities) {
			if(e.color != Color.RED){ 
				Vector2D gravVect = new Vector2D(0,0);
				for(Entity e2 : entities) {
					if(e2 != e) {
						Vector2D gComp = new Vector2D(e.posX, e.posY, e2.posX, e2.posY).setMagnitude(e.getGravForce(e2));
						gravVect = gravVect.add(gComp);
					}
				}
				e.addVelVect(gravVect);
				e.moveTick();
			}
		}
		for(Entity e : entities) {
			ArrayList<Entity> candidates = tree.retrieveFast(e);
			for (Entity e2 : candidates) {
				if(e.isTouching(e2)) {
					e.setVelocity(new Vector2D(0,0));
					e.color = Color.RED;
					e2.setVelocity(new Vector2D(0,0));
					e2.color = Color.RED;
				}
			}
			if(e.posX-e.radius <= 0) {
				e.setSpeedX(Math.abs(e.getSpeedX()));
			}
			if(e.posY-e.radius <= 0) {
				e.setSpeedY(Math.abs(e.getSpeedY()));
			}
			if(e.posX+e.radius >= sizeX) {
				e.setSpeedX(-Math.abs(e.getSpeedX()));
			}
			if(e.posY+e.radius >= sizeY) {
				e.setSpeedY(-Math.abs(e.getSpeedY()));
			}

		}
		 */
		for(Entity e : entities) {
			if(pressed) {
				//double distSqToPointer = (targetX - e.posX)*(targetX - e.posX) + (targetY - e.posY)*(targetY - e.posY);
				//if(distSqToPointer < 100*100) {
					e.accelToward(targetX, targetY);
				//}
				//e.intercept(targetX,targetY,targetVel);
			}
			e.moveTick();
		}
			/*
			if(e.posX-e.radius <= 0) {
				e.setSpeedX(Math.abs(e.getSpeedX()));
			}
			if(e.posY-e.radius <= 0) {
				e.setSpeedY(Math.abs(e.getSpeedY()));
			}
			if(e.posX+e.radius >= sizeX) {
				e.setSpeedX(-Math.abs(e.getSpeedX()));
			}
			if(e.posY+e.radius >= sizeY) {
				e.setSpeedY(-Math.abs(e.getSpeedY()));
			}
			 */
			//}
			//e1.intercept(e2);
			//e1.moveTick();
			//e2.moveTick();
		}

		private void drawCircle(Graphics2D g, double posX, double posY, double radius, Color color) {
			int x = (int)Math.round(posX-radius);
			int y = (int)Math.round(posY-radius);
			int diameter = (int)Math.round(radius*2);
			g.setColor(color);
			g.fillOval(x, y, diameter, diameter);
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			targetVel = new Vector2D(arg0.getX()-targetX,arg0.getY()-targetY);
			targetX = arg0.getX();
			targetY = arg0.getY();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			targetVel = new Vector2D(arg0.getX()-targetX,arg0.getY()-targetY);
			targetX = arg0.getX();
			targetY = arg0.getY();
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			pressed = true;

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			pressed = false;

		}
	}
