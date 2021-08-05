package application;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import unipaivakirja.TagNimi;

public class addTagController implements ModalControllerInterface<TagNimi>, Initializable{

	@FXML private TextField editTag;
	@FXML private Label virhe;
	
	@FXML private void handleLisaa() {
		if ("".equals(tagKohdalla.getNimi())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Huomautus");
			alert.setHeaderText(null);
			alert.setContentText("Tagin sisältö ei saa olla tyhjä!");
			alert.showAndWait();
			return;
		}
		ModalController.closeStage(editTag);
	}
	
	
	@FXML private void handlePeruuta() {
		tagKohdalla = null;
		ModalController.closeStage(editTag);
	}
	
	
	//============================================================================
	
	private TagNimi tagKohdalla;
	
	
	@Override
	public TagNimi getResult() {
		// TODO Auto-generated method stub
		return tagKohdalla;
	}

	@Override
	public void handleShown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefault(TagNimi oletus) {
		// TODO Auto-generated method stub
		tagKohdalla = oletus;
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		alusta();
		
	}
	
	
	/**
	 * Alustaa tekstikentän kuuntelijan. 
	 */
	private void alusta() {
		editTag.setOnKeyReleased(e -> kasitteleMuutos(editTag.getText()));
	}
	
	
	/**
	 * Tarkistaa, että kentän sisältö on oikeassa muodossa.
	 * @param sisalto
	 */
	private void kasitteleMuutos(String sisalto) {
		if (!sisalto.startsWith("#")) {
			virhe.setText("Tagin tulee alkaa #-merkillä!");
		}
		tagKohdalla.setNimi(sisalto);
	}
	
	
	/**
	 *  Luodaan tagin lisäys dialogi ja palautetaan lisättävä TagNimi olio tai null
	 * @param modalityStage mille ollaan modaalisia, null = sovellukselle
	 * @param oletus Uusi tyhjä tag olio, jonka kentät kysytään käyttäjältä
	 * @return null jos hylätään, muuten täytetty TagNimi olio
	 */
	public static TagNimi kysyTag(Stage modalityStage, TagNimi oletus) {
		return ModalController.showModal(addTagController.class.getResource("addTagView.fxml"), "Tag", modalityStage, oletus, null);
	}

}
