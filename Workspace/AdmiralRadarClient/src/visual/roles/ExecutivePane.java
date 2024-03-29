package visual.roles;

import java.awt.GridLayout;

import game.Systems;
import visual.roles.elements.SystemStatusGauge;
import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class ExecutivePane extends ShipPanel {

	SystemStatusGauge	mineG	= new SystemStatusGauge( Systems.MINE , ColorPallate.TACTICAL , 3 );
	SystemStatusGauge	missG	= new SystemStatusGauge( Systems.MISSILE , ColorPallate.TACTICAL , 3 );
	SystemStatusGauge	dronG	= new SystemStatusGauge( Systems.DRONE , ColorPallate.SENSORY , 2 );
	SystemStatusGauge	radrG	= new SystemStatusGauge( Systems.RADAR , ColorPallate.SENSORY , 3 );
	SystemStatusGauge	scenG	= new SystemStatusGauge( Systems.SCENARIO, ColorPallate.UTILITY , 3 );
	SystemStatusGauge	boosG	= new SystemStatusGauge( Systems.BOOST , ColorPallate.UTILITY , 5 );

	public ExecutivePane(GUIController cx) {
		super( cx );
		this.setLayout( new GridLayout( 2 , 3 ) );
		mineG.addController(control);
		missG.addController(control);
		dronG.addController(control);
		radrG.addController(control);
		scenG.addController(control);
		boosG.addController(control);
		
		add( mineG );
		add( dronG );
		add( scenG );
		add( missG );
		add( radrG );
		add( boosG );
		setOpaque(false);
		setBackground( ColorPallate.PRIMARY_TRANSPARENT_GRAY );
		
		enableInteraction();

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
	
	public void enableInteraction(){
		mineG.activateGuageInteraction();
		dronG.activateGuageInteraction();
		scenG.activateGuageInteraction();
		missG.activateGuageInteraction();
		radrG.activateGuageInteraction();
	}

}
