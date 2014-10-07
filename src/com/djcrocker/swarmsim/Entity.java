package com.djcrocker.swarmsim;

import java.awt.Color;

public class Entity {

	public double radius;
	public double posX;
	public double posY;
	public Vector2D velocity;
	public double maxAcceleration = 0.1;
	public double topSpeed = 10;
	public double mass;
	public int renderPass;
	public Color color;
	
	public Entity(double rad, double x, double y, double m, int render, Color col) {
		this(rad,x,y,new Vector2D(0.0,0.0),m,render,col);
	}
	
	public Entity(double rad, double x, double y, double sX, double sY, double m, int render, Color col) {
		this(rad,x,y,new Vector2D(sX,sY),m,render,col);
	}
	
	public Entity(double rad, double x, double y, Vector2D vel, double m, int render, Color col) {
		radius = rad;
		posX = x;
		posY = y;
		velocity = vel;
		mass = m;
		renderPass = render;
		color = col;
	}
	
	public void moveTick() {
		posX += velocity.getX();
		posY += velocity.getY();
	}
	
	public void intercept(Entity e) {
		intercept(e.posX,e.posY,e.getVelocity());
	}
	
	public void intercept(double x, double y, Vector2D vel) {
		double a = topSpeed*topSpeed - vel.dot(vel);
		double b = -2*vel.dot(new Vector2D(posX,posY,x,y));
		double c = -(new Vector2D(posX,posY,x,y).dot(new Vector2D(posX,posY,x,y)));
		double largestRoot = b + Math.sqrt(b*b-4*a*c)/(2*a);
		double tarX = x + vel.scalarMult(largestRoot).getX();
		double tarY = y + vel.scalarMult(largestRoot).getY();
		accelToward(tarX,tarY);
	}
	
	public void accelToward(Entity e) {
		accelToward(e.posX,e.posY);
	}
	
	public void accelToward(double x, double y) {
		Vector2D targetVel = new Vector2D(posX,posY,x,y).setMagnitude(topSpeed);
		approachVelocity(targetVel);
	}
	
	public void approachVelocity(Vector2D targetVel) {
		Vector2D accel = targetVel.subtract(velocity);
		if(accel.getSqMagnitude() > maxAcceleration*maxAcceleration) {
			accel = accel.setMagnitude(maxAcceleration);
		}
		velocity = velocity.add(accel);
	}
	
	public boolean isTouching(Entity e) {
		return (radius + e.radius) * (radius + e.radius) >= getSqDist(e) && e != this;
	}
	
	public double getSqDist(Entity e) {
		double diffX = posX - e.posX;
		double diffY = posY - e.posY;
		return (diffX * diffX) + (diffY * diffY);
	}
	
	public double getSpeedX() {
		return velocity.getX();
	}
	
	public double getSpeedY() {
		return velocity.getY();
	}
	
	public void setSpeedX(double s) {
		velocity.setX(s);
	}
	
	public void setSpeedY(double s) {
		velocity.setY(s);
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2D v) {
		velocity = v;
	}
	
	public void addVelVect(Vector2D v) {
		velocity = velocity.add(v);
	}
	
	public double getMass() {
		return 0.02*radius*radius;
	}
	
	public double getGravForce(Entity e) {
		return (50*getMass()*e.getMass())/(getSqDist(e));
	}
}
