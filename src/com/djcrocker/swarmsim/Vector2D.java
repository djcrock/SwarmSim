package com.djcrocker.swarmsim;

public class Vector2D {

	private double xComp;
	private double yComp;
	
	public Vector2D() {
		xComp = 0.0;
		yComp = 0.0;
	}
	
	public Vector2D(double x, double y) {
		xComp = x;
		yComp = y;
	}
	
	public Vector2D(double x1, double y1, double x2, double y2) {
		xComp = x2-x1;
		yComp = y2-y1;
	}
	
	public void setMagAndDir(double magnitude, double direction) {
		xComp = magnitude*Math.cos(direction);
		yComp = magnitude*Math.sin(direction);
	}
	
	public double getX() {
		return xComp;
	}
	
	public double getY() {
		return yComp;
	}
	
	public double getMagnitude() {
		return Math.sqrt((xComp*xComp)+(yComp*yComp));
	}
	
	public double getSqMagnitude() {
		return (xComp*xComp)+(yComp*yComp);
	}
	
	public double getDirection() {
		return Math.atan2(yComp,xComp);
	}
	
	public static double getDirection(double x1, double y1, double x2, double y2) {
		return Math.atan2(y2-y1,x2-x1);
	}
	
	public void setX(double x) {
		xComp = x;
	}
	
	public void setY(double y) {
		yComp = y;
	}
	
	public Vector2D add(Vector2D v) {
		return new Vector2D(xComp+v.getX(),yComp+v.getY());
	}
	
	public Vector2D add(double x, double y) {
		return new Vector2D(xComp+x,yComp+y);
	}
	
	public Vector2D subtract(Vector2D v) {
		return new Vector2D(xComp-v.getX(),yComp-v.getY());
	}
	
	public Vector2D subtract(double x, double y) {
		return new Vector2D(xComp-x,yComp-y);
	}
	
	public Vector2D scalarMult(double s) {
		return new Vector2D(xComp*s,yComp*s);
	}
	
	public Vector2D normalize() {
		return scalarMult(1.0/getMagnitude());
	}
	
	public double dot(Vector2D v) {
		return (xComp * v.getX()) + (yComp * v.getY());
	}
	
	public Vector2D setMagnitude(double newMag) {
		return scalarMult(newMag/getMagnitude());
	}
	
	public String toString() {
		return "<" + xComp + "," + yComp + ">";
	}
}
