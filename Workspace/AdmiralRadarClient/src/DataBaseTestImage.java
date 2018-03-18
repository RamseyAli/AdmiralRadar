import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DataBaseTestImage {

	
	public static void main(String[] args) throws Exception{
		
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		JPanel q = new JPanel();
		
		p.setLayout(new BorderLayout());
		q.setLayout(new BorderLayout());
		
		f.setContentPane(p);
		p.add(q, BorderLayout.CENTER);
		
		URL u = new URL("http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg");
	
		BufferedImage img = ImageIO.read(u);
		
		ImageIcon ia = new ImageIcon(img);
		
		JLabel l = new JLabel(ia);
		p.add(l, BorderLayout.CENTER);
		
		f.setSize(500,500);
		
		f.setVisible(true);
		
		
		
	}
}
