package visual.roles;

import java.awt.Color;
import java.awt.GridLayout;

import visual.roles.elements.SystemStatusGauge;
import visual.util.operations.GUIController;

public class ExecutivePane extends ShipPanel {

	SystemStatusGauge mineG = new SystemStatusGauge('M' , Color.RED, 3);
	SystemStatusGauge missG = new SystemStatusGauge('T' , Color.RED, 3);
	SystemStatusGauge dronG = new SystemStatusGauge('D' , Color.GREEN, 2);
	SystemStatusGauge radrG = new SystemStatusGauge('R' , Color.GREEN, 3);
	SystemStatusGauge scenG = new SystemStatusGauge('S' , Color.YELLOW, 3);
	SystemStatusGauge boosG = new SystemStatusGauge('B' , Color.YELLOW, 5);
	
	
	
	public ExecutivePane(GUIController cx) {
		super(cx);
		this.setLayout(new GridLayout(2,3));
		add(mineG);
		add(dronG);
		add(scenG);
		add(missG);
		add(radrG);
		add(boosG);
		
		
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
