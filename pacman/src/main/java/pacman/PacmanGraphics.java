package pacman;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PacmanGraphics extends Canvas {

	private static final long serialVersionUID = 1L;
	private BufferStrategy strategy;
	private BufferedImage image;

	public static final int HEIGHT = 800;
	public static final int WIDTH = 800;

	private BufferedImage getImage(String filename) {
		BufferedImage sourceImage = null;

		try {
			URL url = this.getClass().getClassLoader().getResource(filename);
			if (url == null) {
				throw new RuntimeException("Can't find filename: " + filename);
			}
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load: " + filename);
		}

		return sourceImage;
	}

	public void setup() {
		image = getImage("ghost.png");

		JFrame container = new JFrame("Pacman");

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);

		setBounds(0, 0, WIDTH, HEIGHT);
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();

	}

	public void drawField(int x, int y) {
		// Init the graphics system
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, HEIGHT, WIDTH, HEIGHT);
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Draw the image
		g.drawImage(image, x, y, null);

		// Show the graphics
		g.dispose();
		strategy.show();

	}

	public static void main(String[] args) {
		int x = HEIGHT / 2;
		int y = WIDTH / 2;

		PacmanGraphics graphics = new PacmanGraphics();
		graphics.setup();

		while (true) {
			graphics.drawField(x, y);
			y--;

			// slow down the game so it doesn't move too fast
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
