package visual.roles.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ChatTextPane extends JPanel implements MouseWheelListener , ComponentListener{

	
	private static final long serialVersionUID = 1L;
	
	String lorax = "At the far end of town where the Grickle-grass grows and the wind smells slow-and-sour when it blows and no birds ever sing "
			+ "excepting old crows... is the Street of the Lifted Lorax. And deep in the Grickle-grass, some people say, if you look deep enough "
			+ "you can still see, today, where the Lorax once stood just as long as it could before somebody lifted the Lorax away. What was the Lorax? "
			+ "And why was it there? And why was it lifted and taken somewhere from the far end of town where the Grickle-grass grows? The old Once-ler "
			+ "still lives here. Ask him. He knows. You won't see the Once-ler. Don't knock at his door. He stays in his Lerkim on top of his store. He lurks "
			+ "in his Lerkim, cold under the roof, where he makes his own clothes out of miff-muffered moof. And on special dank midnights in August, he peeks"
			+ " out of the shutters and sometimes he speaks and tells how the Lorax was lifted away. He'll tell you, perhaps... if you're willing to pay. On the "
			+ "end of a rope he lets down a tin pail and you have to toss in fifteen cents and a nail and the shell of a great-great-great- grandfather snail."
			+ " Then he pulls up the pail, makes a most careful count to see if you've paid him the proper amount. Then he hides what you paid him away in his Snuvv,"
			+ " his secret strange hole in his gruvvulous glove. Then he grunts, 'I will call you by Whisper-ma-Phone, for the secrets I tell you are for your ears alone."
			+ "'SLUPP! Down slupps the Whisper-ma-Phone to your ear and the old Once-ler's whispers are not very clear, since they have to come down through a snergelly hose,"
			+ " and he sounds as if he had smallish bees up his nose. 'Now I'll tell you,'he says, with his teeth sounding gray, 'how the Lorax got lifted and taken away. It all"
			+ " started way back. such a long, long time back. Way back in the days when the grass was still green and the pond was still wet and the clouds were still clean, and"
			+ " the song of the Swomee-Swans rang out in space... one morning, I came to this glorious place. And I first saw the trees! The Truffula Trees! The bright-colored tufts"
			+ " of the Truffula Trees! Mile after mile in the fresh morning breeze. And, under the trees, I saw Brown Bar-ba-loots frisking about in their Bar-ba-loot"
			+ " suits as they played in the shade and ate Truffula fruits. From the rippulous pond came the comfortable sound of the Humming-Fish humming while splashing around.";

	
	private JScrollPane wrapper;
	private JTextArea text;
	
	public ChatTextPane(){
		
		setLayout(new BorderLayout());
		text = new JTextArea();
		wrapper = new JScrollPane(text);
		wrapper.addComponentListener( this );
		
		text.setText( lorax );		
		text.setWrapStyleWord( true );
		text.setLineWrap( true );
		text.setFont( new Font( Font.SANS_SERIF , Font.PLAIN , 9 ) );
		text.setOpaque( true );
		text.setFocusable( true );
		text.setLayout( null );
//		text.setBackground(ColorPallate.INVISIBLE);
		
		wrapper.setOpaque( false );
//		text.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
//		text.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER );
//		text.getVerticalScrollBar().setVisible( false );
//		text.setBackground(ColorPallate.INVISIBLE);
	
		setOpaque( false );	
		add( wrapper , BorderLayout.CENTER );
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println( text.getBounds() );
		System.out.println( wrapper.getBounds() );
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
