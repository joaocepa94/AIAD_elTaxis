package Agents;

import uchicago.src.sim.space.Object2DTorus;
import uchicago.src.sim.util.Random;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import java.awt.*;

public class Taxi implements Drawable {

	private int x, y;
	private Color color;
	private Object2DTorus space;

	public Taxi(int x, int y, Object2DTorus space) {
		this.x = x;
		this.y = y;
		this.color = Color.yellow;
		this.space = space;
	}

	public void draw(SimGraphics g) {
		g.drawFastCircle(this.color);
	}

	public void jump() {
		space.putObjectAt(this.x, this.y, null);

		do {
			this.x = Random.uniform.nextIntFromTo(0, space.getSizeX() - 1);
			this.y = Random.uniform.nextIntFromTo(0, space.getSizeY() - 1);
		} while (space.getObjectAt(x, y) != null);

		space.putObjectAt(x, y, this);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}

}
