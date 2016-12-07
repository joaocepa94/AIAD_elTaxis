package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private char[][] map = {    
			{'P','P','P','P',' ','P','P','P','P',' ','P','P','P','P','P'},
		    {'P','P',' ',' ',' ','P','P','P','P',' ','P','P','P','P','P'},
		    {' ',' ',' ','P',' ','P','P','P','P',' ','P','P','P','P','P'},
		    {'P','P','P','P',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
		    {'P','P','P','P',' ','P','P','P','P',' ','P','P','P','P','P'},
		    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
		    {'P','P','P','P',' ','P','P','P','P',' ','P','P','P','P','P'},
		    {'P',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','P','P','P'},
		    {' ',' ','P','P',' ','P','P','P','P',' ','P',' ',' ',' ',' '},
		    {'P','P','P','P',' ','P','P','P','P',' ','P','P','P','P','P'}};

	

	private BufferedImage taxi;
	private BufferedImage passenger;
	private BufferedImage road;
	private BufferedImage sidewalk;

	public Map() throws IOException {
		loadImages();
	}

	public void loadImages() throws IOException {

		this.taxi = ImageIO.read(new File("images/taxi.jpg"));
		this.passenger = ImageIO.read(new File("images/passenger.jpg"));
		this.road = ImageIO.read(new File("images/road.jpg"));
		this.sidewalk = ImageIO.read(new File("images/sidewalk.jpg"));

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // clean
		Graphics2D graphics = (Graphics2D) g;
		drawMap(graphics);
	}

	public void drawMap(Graphics2D g2d) {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 15; j++) {
				if (map[i][j] == ' ')
					drawElements(g2d, this.road, i, j);
				else if (map[i][j] == 'S')
					drawElements(g2d, this.passenger, i, j);
				else if (map[i][j] == 'T')
					drawElements(g2d, this.taxi, i, j);
				else if (map[i][j] == 'P')
					drawElements(g2d, this.sidewalk, i, j);

			}
		}

	}

	public void drawElements(Graphics2D g, BufferedImage image, int i, int j) {

		int sizeX, sizeY;

		if (getWidth() > getHeight())
			sizeX = sizeY = getHeight() / 10;
		else
			sizeX = sizeY = getWidth() / 10;

		g.drawImage(image, j * sizeX, i * sizeY, sizeX,
				sizeY, null);
	}
}
