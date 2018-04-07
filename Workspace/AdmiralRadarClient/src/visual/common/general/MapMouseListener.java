package visual.common.general;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import pref.GamePreferences;

public class MapMouseListener implements MouseListener, MouseMotionListener {

	private boolean listening;
	private MapBasedElement root;

	public MapMouseListener(MapBasedElement mapBasedElement) {
		listening = true;
		root = mapBasedElement;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Does Nothing

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (listening) {
			root.setMouseOver( getMousePositionInGame( e ) );
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (listening) {
			root.clickGridDot( getMousePositionInGame( e ).x , getMousePositionInGame( e ).y );
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Does Nothing

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Does Nothing

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Does Nothing

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Does Nothing

	}

	private Point getMousePositionInGame(MouseEvent e) {

		int x_w, y_w, sMax, s, x0, y0, sp, x, y, i, j;

		x_w = root.getSize().width;
		y_w = root.getSize().height;
		sMax = (int) ( 0.9 * Math.min( x_w , y_w ) ) - 2 * MapBasedElement.MARGIN;
		s = sMax - ( sMax % ( GamePreferences.SEG - 1 ) );
		x0 = ( x_w - s ) / 2;
		y0 = ( y_w - s ) / 2;
		sp = root.sp;

		x = e.getX() + sp / 2;
		y = e.getY() + sp / 2;

		if (sp > 0) {
			i = ( ( x - x0 ) - ( ( x - x0 ) % sp ) ) / sp;
			j = ( ( y - y0 ) - ( ( y - y0 ) % sp ) ) / sp;
		} else {
			i = -1;
			j = -1;
		}

		return new Point( i , j );

	}

	public boolean isListening() {
		return listening;
	}

	public void enabled(boolean b) {
		listening = b;
	}

}
