package visual.common;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import util.Preferences;

public class MapMouseListener implements MouseListener, MouseMotionListener {

	private boolean listening;
	private MapBasedElement root;


	public MapMouseListener(MapBasedElement mapBasedElement) {
		listening = true;
		root = mapBasedElement;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//Does Nothing

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (listening){
			root.setMouseOver(getMousePositionInGame(e));
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (listening){
			root.clickGridDot(getMousePositionInGame(e).x , getMousePositionInGame(e).y);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Does Nothing

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Does Nothing

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Does Nothing

	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Does Nothing

	}


	private Point getMousePositionInGame(MouseEvent e){


		int x_w = root.getSize().width;
		int y_w = root.getSize().height;
		int sMax = (int) (0.9 * Math.min( x_w , y_w )) - 2*MapBasedElement.MARGIN;
		int s = sMax - ( sMax % (Preferences.SEG - 1) );
		int x0 = (x_w-s) / 2;
		int y0 = (y_w-s) / 2;
		int sp = root.sp;

		int x = e.getX() + sp / 2;
		int y = e.getY() + sp / 2;

		int i = ((x - x0) - ((x - x0) % sp)) / sp;
		int j = ((y - y0) - ((y - y0) % sp)) / sp;

		return new Point(i, j);


	}

	public boolean isListening() {
		return listening;
	}


	public void enabled(boolean b) {
		listening = b;
	}

}
