package com.djcrocker.swarmsim;

import java.awt.Color;
import java.util.ArrayList;

public class Creature extends Entity {
	
	public int creatureID;
	public String name;
	public double accel;
	public double topSpeed;
	private ArrayList<Integer> prey;
	private ArrayList<Integer> predators;
	private ArrayList<Integer> allies;
	
	public Creature(double rad, double x, double y, double m, Color col) {
		super(rad, x, y, m, 1, col);
	}
	
	
}
