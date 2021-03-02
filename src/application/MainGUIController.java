package application;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;

/**
 * 
 * @author waeekron
 * @version 1.3.2021
 *
 */


public class MainGUIController {
	
	
	@FXML private void handleKirjauduUlos() {
		Dialogs.showMessageDialog("Toimintoa Kirjaudu ulos ei vielä ole");
	}
	
	
	@FXML private void handleLopeta() {
		ModalController.showModal(MainGUIController.class.getResource("quitConfirmationView.fxml"), "Lopeta", null,"");
	}
	
	
	@FXML private void handleApua() {
		ModalController.showModal(MainGUIController.class.getResource("infoDialog.fxml"), "Käyttöohje", null,"");
	}
	
	
	@FXML private void handleTietoja() {
		Dialogs.showMessageDialog("Toimintoa Tietoa... ei vielä ole");
	}
	
	
	@FXML private void handleLisaaMerkinta() {
		ModalController.showModal(MainGUIController.class.getResource("addDreamView.fxml"), "Merkinnän hallinta", null,"");
	}
	
	
	
	@FXML private void handleJarjesta() {
		Dialogs.showMessageDialog("Toimintoa Järjesta ei vielä ole");
	}
	
	
	@FXML private void handleHae() {
		Dialogs.showMessageDialog("Toimintoa Hae ei vielä ole");
	}
	
	
	@FXML private void handlePoista() {
		ModalController.showModal(MainGUIController.class.getResource("deleteView.fxml"), "Poistetaanko", null,"");
	}
	
	
}
