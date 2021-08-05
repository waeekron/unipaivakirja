package application;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Kirjautumis näkymä
 * @author walte
 * @version 25.05.2021
 */
public class loginGUIController implements ModalControllerInterface<String>{
	
	@FXML private Button kirjaudu;
	
	@FXML void handleKirjaudu () {
		ModalController.closeStage(kirjaudu);
	}

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleShown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefault(String arg0) {
		// TODO Auto-generated method stub
		
	}
}
