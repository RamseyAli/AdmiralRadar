package visual.roles;

import game.Position;
import visual.common.MapBasedElement;
import visual.util.operations.GUIController;

public class CaptainPane extends MapBasedElement{

	Position start;
	
	public CaptainPane(GUIController cx) {
		super(cx);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Position getStartLocation() {
		
		return null;
	}

	@Override
	public void clickGridDot(int x, int y) {
		start = new Position(x, y);
		
		control.setStartLocation(start);
		
	}

}
