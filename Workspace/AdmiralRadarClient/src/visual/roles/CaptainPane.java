package visual.roles;

import game.Position;
import visual.common.general.MapBasedElement;
import visual.util.operations.GUIController;

public class CaptainPane extends MapBasedElement{

	private Position start;
	private Object signal = new Object();
	
	public CaptainPane(GUIController cx) {
		super(cx);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Position getStartLocation() {
		System.out.println("WAITING");
		try {
			synchronized(signal){ signal.wait(); }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("RETURNING");
		return start;
	}

	@Override
	public void clickGridDot(int x, int y) {
		start = new Position(x, y);
		synchronized(signal){ signal.notify(); }
		System.out.println("CLICKY");
		control.setStartLocation(start);
		
	}

}
