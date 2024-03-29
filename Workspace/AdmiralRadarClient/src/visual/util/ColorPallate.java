package visual.util;

import java.awt.Color;

public class ColorPallate {

	public static final Color PRIMARY_TRANSPARENT_GRAY = new Color( 30 , 30 , 30 , 100 );

	public static final Color	HEALTH_BACKG	= new Color( 60 , 60 , 60 , 120 );
	public static final Color	HEALTH_LIGHT	= new Color( 200 , 60 , 60 , 220 );

	public static final Color	ORDER_PANEL_BORDER	= new Color( 0 , 0 , 100 , 180 );
	public static final Color	ORDER_PANEL			= new Color( 60 , 60 , 60 , 150 );

	public static final Color	ORDER_COMPASS			= new Color( 0 , 100 , 100 , 100 );
	public static final Color	ORDER_COMPASS_BUTTON	= new Color( 0 , 100 , 100 , 200 );
	public static final Color	ORDER_SYSTEM_BOX		= new Color( 10 , 10 , 10 , 150 );
	public static final Color	ORDER_SYSTEM_CAUTION	= new Color( 250 , 0 , 0 , 230 );

	public static final Color	UTILITY		= new Color( 213 , 213 , 0 , 200 );
	public static final Color	TACTICAL	= new Color( 250 , 0 , 0 , 200 );
	public static final Color	SENSORY		= new Color( 0 , 250 , 0 , 200 );

	public static final Color MAP_MOUSEOVER = new Color( 90 , 150 , 0 , 100 );

	public static final Color	CHAT_PANEL_BORDER	= new Color( 0 , 0 , 100 , 180 );
	public static final Color	CHAT_PANEL			= new Color( 60 , 60 , 60 , 150 );

	public static final Color CHAT_BOX_COLOR = new Color( 230 , 230 , 230 , 150 );

	public static final Color INVISIBLE = new Color( 0 , 0 , 0 , 0 );

	public static final Color EXEC_SYSTEM_DIAL = new Color (200 , 200 , 200 , 180);

	public static final Color EXEC_SYSTEM_YES = new Color (250 , 250 , 250 , 150);
	public static final Color EXEC_SYSTEM_NO = new Color (10 , 10 , 10 , 150);
	
	public static final Color	ASTEROID_DARK	= new Color( 50 , 50 , 50 , 200);
	public static final Color	ASTEROID_LIGHT	= new Color( 70 , 70 , 70 , 200 );

	public static final Color MAP_BACKGROUND =  new Color( 20 , 20 , 20 , 50 ) ;

	public static final Color MAP_DOTS = new Color( 213 , 213 , 0 , 240 );

	public static final Color INFO_PANEL_BORDER	= new Color( 0 , 0 , 100 , 180 );
	public static final Color INFO_CHAT_PANEL	= new Color( 60 , 60 , 60 , 150 );

	public static final Color QUIT_BUTTON = new Color( 200 , 40 , 40 , 100 );

	public static final Color QUIT_TEXT = new Color( 240 , 240 , 240 , 200 );
	
	public static final Color	ENGINEER_DIRECTION_BOX = new Color( 30 , 30 , 30 , 200 );
	public static final Color	ENGR_ACTIVE_WEAPON	= Color.RED;
	public static final Color	ENGR_BROKEN_WEAPON	= Color.DARK_GRAY;
	public static final Color	ENGR_ACTIVE_SENSOR	= Color.GREEN;
	public static final Color	ENGR_BROKEN_SENSOR	= Color.DARK_GRAY;
	public static final Color	ENGR_ACTIVE_EXTRAS	= Color.YELLOW;
	public static final Color	ENGR_BROKEN_EXTRAS	= Color.DARK_GRAY;
	public static final Color	ENGR_ACTIVE_REACTR	= Color.GRAY;
	public static final Color	ENGR_BROKEN_REACTR	= Color.DARK_GRAY;
	public static final Color	ENGR_CIRCUIT_NNNE	= setTransparency(Color.ORANGE , 120);
	public static final Color	ENGR_CIRCUIT_WWWE	= setTransparency(Color.BLUE , 120);
	public static final Color	ENGR_CIRCUIT_SSSE	= setTransparency(Color.WHITE , 120);

	public static final Color MAP_PATH_COLOR = setTransparency(Color.RED , 250);
	
	private static Color setTransparency(Color c, int alpha){
		return new Color(c.getRed() , c.getGreen() , c.getBlue() , alpha);
	}

}
