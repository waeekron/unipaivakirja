package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import unipaivakirja.Uni;

public class addDreamController implements ModalControllerInterface<Uni>, Initializable {

	@FXML private Label labelVirhe;
	@FXML private TextField editPaivamaara;
	@FXML private TextField editKesto;
	@FXML private TextField editArvio;
	@FXML private TextArea editSisalto;
	
	
	@FXML private void handleLisaa() {
		if ( editSisalto.getText() != null && editSisalto.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Huomautus");
			alert.setHeaderText(null);
			alert.setContentText("Unen sisältö ei saa olla tyhjä!");
			alert.showAndWait();
			return;
		}
		
		if (!Pattern.matches("^\\d{4}-\\d{2}-\\d{2}", editPaivamaara.getText())) {
			Dialogs.showMessageDialog("Päivämäärän tulee olla 2021-01-01 ");
			return;
		}
		
		
		uusiUni();
		ModalController.closeStage(labelVirhe);
	}
	
	
	@FXML private void handlePeruuta() {
		uniKohdalla = null;
		ModalController.closeStage(labelVirhe);
	}
	
	//============================================================================
	
	private Uni uniKohdalla;
	
	private void uusiUni() {
		uniKohdalla.setArvio(Integer.parseInt(editArvio.getText()));
		uniKohdalla.setKesto(Integer.parseInt(editKesto.getText()));
		uniKohdalla.setUnenSisalto(editSisalto.getText());
		uniKohdalla.setPaivamaara(editPaivamaara.getText());
		
	}


	@Override
	public Uni getResult() {
		return uniKohdalla;
	}

	@Override
	public void handleShown() {
		editSisalto.requestFocus();
		
	}

	@Override
	public void setDefault(Uni oletus) {
		uniKohdalla = oletus;
		naytaUni(uniKohdalla);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
		
	
	/**
	 * Näytetään unen tiedot komponentteihin 
	 * @param uni näytettävä uni
	 */
	public void naytaUni(Uni uni) {
		if (uni == null) return;
		editSisalto.setText(uni.getUnenSisalto());
		editArvio.setText(Integer.toString(uni.getArvio()));
		editKesto.setText(Integer.toString(uni.getKesto()));
		editPaivamaara.setText(uni.getPaivamaara());
	}
	
	
	/**
	 * Luodaan unen muokkaus/lisäys dialogi ja palautetaan sama tietue muutettuna tai null
	 * @param modalityStage mille ollaan modaalisia, null = sovellukselle
	 * @param oletus Mitä dataa näytetään oletuksena
	 * @return null jos hylätään, muuten palautetaan täytetty tietue
	 */
	public static Uni kysyUni(Stage modalityStage, Uni oletus) {
		return ModalController.showModal(addDreamController.class.getResource("addDreamView.fxml"), "Uni", modalityStage, oletus, null);
	}

}
