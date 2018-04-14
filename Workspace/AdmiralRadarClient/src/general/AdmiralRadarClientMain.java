package general;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pref.GamePreferences;

public class AdmiralRadarClientMain {

	public AdmiralRadarClientMain() throws MalformedURLException, IOException {
		initComponents();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new AdmiralRadarClientMain();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	private void initComponents() throws MalformedURLException, IOException {
		JFrame frame = new JFrame("Frame with JPanel and background");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		System.out.println( GamePreferences.RESOURCES_PATH + "space.png" );
		
		final Image background = ImageUtils.scaleImage(300, 300, ImageIO.read(new URL("file:///Users/sbuck/Dropbox/College Work/12_S2018/CS 307/Git/AdmiralRadar/Workspace/Resources/doggo.gif")));
		
		final Dimension jpanelDimensions = new Dimension(new ImageIcon(background).getIconWidth(), new ImageIcon(background).getIconHeight());

		frame.add(new JPanel() {
			@Override
			protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				grphcs.drawImage(background, 0, 0, null);
				grphcs.setColor( Color.BLUE );
				grphcs.fillRect( 2 , 2 , 100 , 100 );
			}

			@Override
			public Dimension getPreferredSize() {
				return jpanelDimensions;
			}
		});

		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}

class ImageUtils {

	public static BufferedImage scaleImage(int width, int height, String filename) {
		BufferedImage bi;
		try {
			ImageIcon ii = new ImageIcon(filename);
			bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			g2d.drawImage(ii.getImage(), 0, 0, width, height, null);
		} catch (Exception e) {
			return null;
		}
		return bi;
	}

	static Image scaleImage(int width, int height, BufferedImage filename) {
		BufferedImage bi;
		try {
			bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			g2d.drawImage(filename, 0, 0, width, height, null);
		} catch (Exception e) {
			return null;
		}
		return bi;
	}

}
