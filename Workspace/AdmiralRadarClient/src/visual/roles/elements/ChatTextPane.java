package visual.roles.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import visual.util.ColorPallate;
import visual.util.FontPallate;

import static database.dbQuery.*;

public class ChatTextPane extends JPanel implements ComponentListener {

	private static final long serialVersionUID = 1L;

	private JScrollPane	squirrel;
	private JTextArea	text;

	public ChatTextPane() {

		setLayout( new BorderLayout() );
		text = new JTextArea();
		
		//Listener
		addComponentListener( this );

		text.setLineWrap( true );
	//	text.setFont( FontPallate.getFont( FontPallate.CHAT_FONT , 10 ) );
		text.setEditable(false);
		text.setLayout( null );
		text.setLineWrap(false);
		text.setForeground( Color.BLACK );
		// text.setBackground(ColorPallate.INVISIBLE);
		
		new Thread( () -> refreshChat() ).start();
		
		squirrel = new JScrollPane( text );
		squirrel.setOpaque( true );
		squirrel.setBackground(ColorPallate.ASTEROID_DARK);

		setOpaque( false );
		this.add( squirrel , BorderLayout.CENTER );
		this.setBackground( ColorPallate.INVISIBLE );
		
		
		
		}

	@Override
	public void componentResized(ComponentEvent e) {
		text.setSize( this.getWidth() , this.getHeight() );
		squirrel.setPreferredSize(new Dimension(this.getWidth() , this.getHeight() ));
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
	
	private void refreshChat() {
		List<String> messages = new ArrayList<String>();
		
		messages = getGlobalMessages();
		
		text.removeAll();
		
		for (int i = 0; i < messages.size(); i++) {
			text.append(messages.get(i));
			text.append("\n");
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
