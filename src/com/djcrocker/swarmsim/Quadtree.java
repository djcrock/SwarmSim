package com.djcrocker.swarmsim;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Quadtree {

	private final int MAX_OBJECTS = 10;
	private final int MAX_LEVELS = 6;

	private int level;
	private ArrayList<Entity> entities;
	private Rectangle bounds;
	private Quadtree[] nodes;

	public Quadtree(int newLevel, Rectangle newBounds) {
		level = newLevel;
		bounds = newBounds;
		entities = new ArrayList<Entity>();
		nodes = new Quadtree[4];
	}
	
	public void insert(Entity e) {
		if(nodes[0] != null) {
			int index = getIndex(e);
			if(index != -1) {
				nodes[index].insert(e);
				return;
			}
		}
		
		entities.add(e);
		
		if(entities.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if(nodes[0] == null) {
				split();
			}
			int i = 0;
			
			while(i < entities.size()) {
				int index = getIndex(entities.get(i));
				
				if(index != -1) {
					nodes[index].insert(entities.remove(i));
				}
				else {
					i++;
				}
			}
		}
	}
	
	public void addAll(ArrayList<Entity> ents) {
		for(Entity e : ents) {
			insert(e);
		}
	}
	
	public ArrayList<Entity> retrieveFast(Entity e) {
		int index = getIndex(e);
		ArrayList<Entity> returns = new ArrayList<Entity>();
		if(index != -1 && nodes[0] != null) {
			returns.addAll(nodes[index].retrieveFast(e));
		}
		
		returns.addAll(entities);
		return returns;
	}
	
	public ArrayList<Entity> retrieveFast(double x, double y, double r) {
		int index = getIndex(x,y,r);
		ArrayList<Entity> returns = new ArrayList<Entity>();
		if(index != -1 && nodes[0] != null) {
			returns.addAll(nodes[index].retrieveFast(x,y,r));
		}
		
		returns.addAll(entities);
		return returns;
	}
	
	public ArrayList<Entity> retrieveAll(Entity e) {
		int index = getIndex(e);
		ArrayList<Entity> returns = new ArrayList<Entity>();
		if(index != -1 && nodes[0] != null) {
			returns.addAll(nodes[index].retrieveAll(e));
		}
		else if(nodes[0] != null) {
			returns.addAll(nodes[0].retrieveAll(e));
			returns.addAll(nodes[1].retrieveAll(e));
			returns.addAll(nodes[2].retrieveAll(e));
			returns.addAll(nodes[3].retrieveAll(e));
		}
		
		returns.addAll(entities);
		return returns;
	}
	
	public ArrayList<Entity> retrieveAll(double x, double y, double r) {
		int index = getIndex(x,y,r);
		ArrayList<Entity> returns = new ArrayList<Entity>();
		if(index != -1 && nodes[0] != null) {
			returns.addAll(nodes[index].retrieveAll(x,y,r));
		}
		else if(nodes[0] != null) {
			returns.addAll(nodes[0].retrieveAll(x,y,r));
			returns.addAll(nodes[1].retrieveAll(x,y,r));
			returns.addAll(nodes[2].retrieveAll(x,y,r));
			returns.addAll(nodes[3].retrieveAll(x,y,r));
		}
		
		returns.addAll(entities);
		return returns;
	}
	
	public void clear() {
		entities.clear();
		for(int i=0; i<nodes.length; i++) {
			if(nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	private void split() {
		int subWidth = (int)(bounds.getWidth()/2);
		int subHeight = (int)(bounds.getHeight()/2);
		int x = (int)bounds.getX();
		int y = (int)bounds.getY();

		nodes[0] = new Quadtree(level+1, new Rectangle(x+subWidth, y, subWidth, subHeight));
		nodes[1] = new Quadtree(level+1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new Quadtree(level+1, new Rectangle(x, y+subHeight, subWidth, subHeight));
		nodes[3] = new Quadtree(level+1, new Rectangle(x+subWidth, y+subHeight, subWidth, subHeight));
	}

	private int getIndex(Entity e) {
		int index = -1;
		double circTop = e.posY - e.radius;
		double circBottom = e.posY + e.radius;
		double circLeft = e.posX - e.radius;
		double circRight = e.posX + e.radius;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth()/2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight()/2);

		boolean topQuadrant = (circTop < horizontalMidpoint && circBottom < horizontalMidpoint);
		boolean bottomQuadrant = (circTop > horizontalMidpoint);

		if(circLeft < verticalMidpoint && circRight < verticalMidpoint) {
			if(topQuadrant) {
				index = 1;
			}
			else if(bottomQuadrant) {
				index = 2;
			}
		}
		else if(circLeft > verticalMidpoint) {
			if(topQuadrant) {
				index = 01;
			}
			else if(bottomQuadrant) {
				index = 3;
			}
		}

		return index;
	}
	
	private int getIndex(double x, double y, double r) {
		int index = -1;
		double circTop = y - r;
		double circBottom = y + r;
		double circLeft = x - r;
		double circRight = x + r;
		double verticalMidpoint = bounds.getX() + (bounds.getWidth()/2);
		double horizontalMidpoint = bounds.getY() + (bounds.getHeight()/2);

		boolean topQuadrant = (circTop < horizontalMidpoint && circBottom < horizontalMidpoint);
		boolean bottomQuadrant = (circTop > horizontalMidpoint);

		if(circLeft < verticalMidpoint && circRight < verticalMidpoint) {
			if(topQuadrant) {
				index = 1;
			}
			else if(bottomQuadrant) {
				index = 2;
			}
		}
		else if(circLeft > verticalMidpoint) {
			if(topQuadrant) {
				index = 01;
			}
			else if(bottomQuadrant) {
				index = 3;
			}
		}

		return index;
	}
}
