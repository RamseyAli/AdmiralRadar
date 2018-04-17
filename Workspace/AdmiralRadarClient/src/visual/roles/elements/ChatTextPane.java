package visual.roles.elements;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class ChatTextPane extends JPanel implements MouseWheelListener, ComponentListener {

	private static final long serialVersionUID = 1L;

	private JScrollPane	squirrel;
	private JTextArea	text;

	public ChatTextPane() {

		setLayout( new BorderLayout() );
		text = new JTextArea();
		squirrel = new JScrollPane( text );
		squirrel.addComponentListener( this );

		text.setWrapStyleWord( true );
		text.setLineWrap( true );
	//	text.setFont( FontPallate.getFont( FontPallate.CHAT_FONT , 10 ) );
		text.setOpaque( false );
		text.setFocusable( true );
		text.setLayout( null );
		text.setForeground( Color.WHITE );
		// text.setBackground(ColorPallate.INVISIBLE);

		squirrel.setOpaque( false );
		squirrel.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		squirrel.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER );
		// text.setBackground(ColorPallate.INVISIBLE);

		setOpaque( true );
		this.add( text , BorderLayout.CENTER );
		this.setBackground( ColorPallate.INVISIBLE );
		
		new Thread( () -> refreshChat() ).start();
		
		}
	

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		text.setSize( this.getWidth() , this.getHeight() );
		squirrel.setSize( this.getWidth() , this.getHeight() );

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
