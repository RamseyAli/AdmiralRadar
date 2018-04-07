package visual.util.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pref.GamePreferences;
import visual.common.ChatPane;
import visual.common.HealthPane;
import visual.common.InfoPane;
import visual.common.OrdersPane;
import visual.roles.NetworkPane;
import visual.util.operations.GUIController;

public class GameFrame extends JFrame implements ComponentListener {

	private static final long serialVersionUID = 1L;

	private static final String	IMAGE	= "space.png";
	private Image				img;

	private class MainPane extends JPanel {

		private static final long serialVersionUID = 1L;

		public MainPane() {
			img = new ImageIcon( IMAGE ).getImage();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent( g );
			g.drawImage( img , 0 , 0 , getWidth() , getHeight() , null );
			g.setColor( new Color( 10 , 10 , 10 , 70 ) );
			g.fillRect( 0 , 0 , getWidth() , getHeight() );
		}
	}

	private MainPane mainPane;

	private GUIController control;

	private HealthPane	h;
	private ChatPane	c;
	private OrdersPane	o;
	private InfoPane	i;

	public GameFrame(Point p, GUIController gc) {
		super( "Admiral Radar" );
		control = gc;
		mainPane = new MainPane();
		mainPane.setLayout( new BorderLayout() );
		setContentPane( mainPane );

		h = new HealthPane( control );
		c = new ChatPane( control );
		o = new OrdersPane( control );
		i = new InfoPane( control );

		addComponentListener( this );
		setComponentSizes();

		setDefaultCloseOperation( EXIT_ON_CLOSE );
		if (GamePreferences.isFullscreen()) fullscreen();
		setLocation( p.x , p.y );
	}

	private void fullscreen() {
		GraphicsDevice device = getGraphicsConfiguration().getDevice();
		if (device.isFullScreenSupported()) {
			setUndecorated( true );
			setResizable( true );
			setAlwaysOnTop( true );

			pack();
			device.setFullScreenWindow( this );
			pack();
			repaint();
		}

	}

	public GameFrame(GUIController gc) {
		this( new Point( 100 , 100 ) , gc );

	}

	public void setPanel(ShipPanel p) {
		mainPane.removeAll();
		mainPane.add( p , BorderLayout.CENTER );
		if (!( p instanceof NetworkPane )) {
			o.setup();
			mainPane.add( h , BorderLayout.NORTH );
			mainPane.add( o , BorderLayout.EAST );
			mainPane.add( i , BorderLayout.SOUTH );
			mainPane.add( c , BorderLayout.WEST );

		}

		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				p.repaint();
			}
		} );

	}

	@Override
	public void componentResized(ComponentEvent e) {
		setComponentSizes();
	}

	private void setComponentSizes() {
		int fw = this.getWidth();
		int fh = this.getHeight();

		h.setPreferredSize( new Dimension( 2 * fw / 3 , fh / 10 ) );
		i.setPreferredSize( new Dimension( 2 * fw / 3 , fh / 10 ) );
		o.setPreferredSize( new Dimension( 2 * fw / 9 , 8 * ( fh / 10 ) ) );
		c.setPreferredSize( new Dimension( 2 * fw / 9 , 8 * ( fh / 10 ) ) );

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// Unused

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// Unsued

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// Unused

	}

}
