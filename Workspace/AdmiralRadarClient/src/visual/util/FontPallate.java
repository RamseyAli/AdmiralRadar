package visual.util;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;

public class FontPallate {

	
	public static final int DEFAULT_SIZE = 10;
	
	public static final int OPS_BUTTON_FONT = 1;
	public static final int ABORT_BUTTON_FONT = 2;
	public static final int TURN_INSTRUCTIONS_FONT = 3;
	public static final int CHAT_FONT = 4;
	public static final int MAP_LETTER_FONT = 5;
	
//	public static final int B_BUTTON_FONT = 4;
//	public static final int C_BUTTON_FONT = 5;
//	public static final int D_BUTTON_FONT = 6;
	
	public static Font[] fonts = new Font[6];
	
	public static boolean enabled = false;
	
	private static float SCALE;
	private static Graphics2D g;


	public static void setup(Graphics2D gi){
		if (enabled) return;
		enabled = true;
		g = gi;
		
		SCALE =  (g.getFontMetrics( new Font(Font.SANS_SERIF , Font.BOLD , 100) ).getHeight()) / (100.0f);
		
		fonts[OPS_BUTTON_FONT] = new Font(Font.SANS_SERIF , Font.BOLD , DEFAULT_SIZE);
		fonts[ABORT_BUTTON_FONT] = new Font(Font.MONOSPACED , Font.BOLD , DEFAULT_SIZE);
		fonts[TURN_INSTRUCTIONS_FONT] = new Font(Font.SANS_SERIF , Font.PLAIN , DEFAULT_SIZE);
		fonts[CHAT_FONT] = new Font(Font.MONOSPACED , Font.PLAIN , DEFAULT_SIZE);
		fonts[MAP_LETTER_FONT] = new Font(Font.MONOSPACED , Font.PLAIN , DEFAULT_SIZE);

	}
	
	public static Font getFont(int FONT , int HEIGHT){
		
		return fonts[FONT].deriveFont( HEIGHT * SCALE );
	}

	public static Font getFontFromStringWidth(int FONT, String txt, int maxWidth) {
		final float BENCHMARK = 100f;
		float a = g.getFontMetrics(fonts[FONT].deriveFont( BENCHMARK )).stringWidth( txt ); //Width of String at Font Size 100.
		 //(100.0f / a) Ratio of Font Size / String Width
		
		return getFont(FONT , (int) (maxWidth * (BENCHMARK / a)));
	}

}
