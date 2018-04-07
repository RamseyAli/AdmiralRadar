package visual.roles;

import game.Position;
import visual.common.general.MapBasedElement;
import visual.util.operations.GUIController;

public class CaptainPane extends MapBasedElement {

	private Position	start;
	private Object		signal	= new Object();

	public CaptainPane(GUIController cx) {
		super( cx );

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Position getStartLocation() {
		try {
			synchronized (signal) {
				signal.wait();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return start;
	}

	@Override
	public void clickGridDot(int x, int y) {
		start = new Position( x , y );
		if (!start.isValid()) return;

		synchronized (signal) {
			signal.notify();
		}

		control.setStartLocation( start );
		removeMouseListener( ear );
		// removeMouseMotionListener(ear);
		control.threadSafeRepaint( this );

	}

}
