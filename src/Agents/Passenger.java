package Agents;

import java.awt.Color;

import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DTorus;
import uchicago.src.sim.util.Random;

public class Passenger implements Drawable {

	int x, y;
	private Color color;
	private Object2DTorus space;

	public Passenger(int x, int y, Object2DTorus space) {
		this.x = x;
		this.y = y;
		this.space = space;
		this.color = Color.red;
	}
	
	public void jump() {
		space.putObjectAt(this.x, this.y, null);

		do {
			this.x = Random.uniform.nextIntFromTo(0, space.getSizeX() - 1);
			this.y = Random.uniform.nextIntFromTo(0, space.getSizeY() - 1);
		} while (space.getObjectAt(x, y) != null);

		space.putObjectAt(x, y, this);
	}
	

	public void draw(SimGraphics g) {
		g.drawFastCircle(this.color);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public Color getColor() {
		return color;
	}

}
