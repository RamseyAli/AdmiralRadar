package visual.util.operations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController {

	
	LoginController login = new LoginController();
	
	class LoginController implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
			
			}
			
		}
		
	}
	
	public LoginController login(){
		
		return login; 
	}


	
	
	

}
