package visual.roles;

import java.awt.GridLayout;

import visual.roles.elements.SystemStatusGauge;
import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class ExecutivePane extends ShipPanel {

	SystemStatusGauge	mineG	= new SystemStatusGauge( 'M' , ColorPallate.TACTICAL , 3 );
	SystemStatusGauge	missG	= new SystemStatusGauge( 'T' , ColorPallate.TACTICAL , 3 );
	SystemStatusGauge	dronG	= new SystemStatusGauge( 'D' , ColorPallate.SENSORY , 2 );
	SystemStatusGauge	radrG	= new SystemStatusGauge( 'R' , ColorPallate.SENSORY , 3 );
	SystemStatusGauge	scenG	= new SystemStatusGauge( 'S' , ColorPallate.UTILITY , 3 );
	SystemStatusGauge	boosG	= new SystemStatusGauge( 'B' , ColorPallate.UTILITY , 5 );

	public ExecutivePane(GUIController cx) {
		super( cx );
		this.setLayout( new GridLayout( 2 , 3 ) );
		add( mineG );
		add( dronG );
		add( scenG );
		add( missG );
		add( radrG );
		add( boosG );
		setOpaque(false);
		setBackground( ColorPallate.PRIMARY_TRANSPARENT_GRAY );

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

}
