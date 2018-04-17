package visual.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;

import pref.VisualPreferences;
import visual.util.ColorPallate;
import visual.util.FontPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class InfoPane extends ShipPanel implements MouseListener, MouseMotionListener {

	public InfoPane(GUIController ctr) {
		super( ctr );
		addMouseListener(this);
		addMouseMotionListener(this);
		instructions = new JLabel("Welcome to Admiral Radar!");
		this.setLayout(new BorderLayout());
		add(instructions, BorderLayout.CENTER);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Rectangle2D r_outer;
	Rectangle2D r_buttn;
	JLabel instructions;

	@Override
	public void draw() {
		int h_m = (int) ( getHeight() * ( 5.0 / 6 ) );
		int w_m = (int) ( getWidth() * ( 5.0 / 6 ) );
		
		int h_b = (int) ( getHeight() * ( 4.0 / 6 ) );
		int w_b = (int) ( getWidth() * ( 1.0 / 6 ) );

		r_outer = new Rectangle2D.Double( ( getWidth() - w_m )/2 ,  ( getHeight() - h_m )/2  , w_m , h_m );
		r_buttn = new Rectangle2D.Double(  getWidth() - w_b - VisualPreferences.GENERAL_BORDER - w_b/2 ,  ( getHeight() - h_m )/2 + (h_m - h_b)/2  , w_b , h_b );

		g.setColor( ColorPallate.INFO_PANEL_BORDER );
		g.fill( r_outer );
		swell( r_outer , -2 * VisualPreferences.GENERAL_BORDER , -2 * VisualPreferences.GENERAL_BORDER );

		g.setColor( ColorPallate.INFO_CHAT_PANEL );
		g.fill( r_outer );
		
		instructions.setForeground( Color.WHITE );
		instructions.setFont( FontPallate.getFont( FontPallate.TURN_INSTRUCTIONS_FONT , (int) (r_outer.getHeight() / 2.0) )  );
		instructions.setLocation( (getWidth() - w_m )/2  + 3*VisualPreferences.GENERAL_BORDER  , ( getHeight() - h_m )/2 + (h_m - h_b)/2 );

		
		g.setColor(ColorPallate.QUIT_TEXT );
		g.setFont( FontPallate.getFont( FontPallate.ABORT_BUTTON_FONT , (int) (r_buttn.getHeight() / 2.0) )  );
		g.drawString( "ABORT" , (int) (r_buttn.getX()*1.04) , (int) (r_buttn.getY() + ((r_buttn.getHeight() / 2)  + (g.getFontMetrics().getHeight() / 2.5)) ));
		g.setColor(ColorPallate.QUIT_BUTTON );
		g.fill( r_buttn );
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (r_buttn.contains(e.getPoint())) {
			control.quit();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	    double mx = e.getX();
	    double my = e.getY();
	    if ( r_buttn.contains(mx, my) ) {
	        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
	        setCursor(cursor);
	    } else {
	        Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR); 
	        setCursor(cursor);
	    }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setInstructionText(String s){
		instructions.setText( s );
	}

}
