package application;

import java.io.PrintStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kanta.UniPaivamaaraComparator;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import unipaivakirja.SailoException;
import unipaivakirja.Sovellus;
import unipaivakirja.Tag;
import unipaivakirja.TagNimi;
import unipaivakirja.Uni;
import fi.jyu.mit.fxgui.ListChooser;


/**
 * Käsittelee käyttöliittymän tapahtumia.
 * Ei toimi: 
 * - Lajittelu keston tai arvion perusteella
 * - Tietoja... ei avaa kurssin suunnitelma sivua
 * - Ei tallenna aina sammuttaessa
 * 
 * TODO: Lisättävä syötteen tarkistusta addDreamControlleriin.
 * @author waeekron
 * @version 20.7.2021
 *
 */
public class MainGUIController  implements Initializable{
	
	@FXML private ListChooser<Uni> chooserUnet;
	@FXML private TextArea panelUni;
	@FXML private ListChooser<TagNimi> chooserTagit;
	
	@FXML private Label editPaivamaara;
	@FXML private Label editArvio;
	@FXML private Label editKesto;
	@FXML private TextField editHaku;
	@FXML private Label editKeskiarvo;
	@FXML private Label editKeskiarvoKaikki;
	
	@Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }
	
	
	@FXML private void handleKirjauduUlos() {
		Dialogs.showMessageDialog("Toimintoa Kirjaudu ulos ei vielä ole");
	}
	
	
	@FXML private void handleLopeta() {
		//ModalController.showModal(MainGUIController.class.getResource("quitConfirmationView.fxml"), "Lopeta", null,"");
		tallenna();
		Platform.exit();
	}
	
	
	@FXML private void handleMuokkaa() {
		muokkaa();
	}
	
	
	@FXML private void handleMuokkaaTag() {
		muokkaaTag();
	}
	
	
	@FXML private void handlePoistaTag() {
		poistaTag();
	}
	
	
	@FXML private void handleApua() {
		ModalController.showModal(MainGUIController.class.getResource("infoDialog.fxml"), "Käyttöohje", null,"");
	}
	
	
	@FXML private void handleTietoja() {
		Dialogs.showMessageDialog("Toimintoa Tietoa... ei vielä ole"); // TODO: Tee tämä
	}
	
	
	@FXML private void handleLisaaMerkinta() {
		//ModalController.showModal(MainGUIController.class.getResource("addDreamView.fxml"), "Merkinnän hallinta", null,"");
		uusiUni();
	}
	
	
	@FXML private void handleLisaaTageja() {
		//ModalController.showModal(MainGUIController.class.getResource("addTagView.fxml"), "Merkinnän hallinta", null,"");
		uusiTaginNimi();
	}
	
	
	@FXML private void handleLiitaTag() {
		liitaTag();
	}

	
	@FXML private void handlePoista() {
		poistaUni();
	}


	public String getResult() {
		// TODO Auto-generated method stub
		return null;
	}


	public void handleShown() {
		// TODO Auto-generated method stub
		
	}


	public void setDefault(String oletus) {
		// TODO Auto-generated method stub
		
	}
	
	
	//==================================================================================================
	// Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia

	private Sovellus sovellus;
	private Uni uniKohdalla;
	private TagNimi tagKohdalla;

	
	/**
	 * Tallentaa tiedot
	 * @return null jos onnistuu, virhe viesti jos tuli ongelmia
	 */
	private String tallenna() {
		try {
			sovellus.tallenna();
			return null;
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
			return e.getMessage();
		}
	}
	
	
	/**
	 * Tekee tarvittavat alustukset. Alustetaan listchooserin kuuntelija.  
	 */
	private void alusta() {
		chooserUnet.clear();
		chooserUnet.addSelectionListener(e -> naytaUni());
		chooserTagit.addSelectionListener(e -> naytaTaginNimi());
		editHaku.setOnKeyReleased(e -> handleHaku(editHaku.getText()));
		
	}
	
	
	/**
	 * @param sov Sovellus jota käytetään tässä käyttöliittymässä
	 */
	public void setSovellus(Sovellus sov) {
		this.sovellus = sov;
		naytaUni();
		haeUnet();
		haeTagienNimet();
	}
	
	
	/**
	 * Näyttää tagin nimen komponenttiin
	 */
	protected void naytaTaginNimi() {
		tagKohdalla = chooserTagit.getSelectedObject();
		
		if (tagKohdalla == null) return;
	}
	
	
	/**
	 * Näyttää valitun unen
	 */
	protected void naytaUni() {
		uniKohdalla = chooserUnet.getSelectedObject();
		
		if (uniKohdalla == null) return;
		
		var uniJaTagit = new StringBuilder(uniKohdalla.getUnenSisalto() + "\n");
		List<Tag> tagit = sovellus.annaTagit(uniKohdalla);
		TagNimi[] nimet = sovellus.getTagienNimet();
		
		for (Tag ta : tagit) {
			for (int i = 0; i < nimet.length; i++) {
				if (nimet[i] == null) continue;
				if (ta.getTagID() == nimet[i].getTagID()) uniJaTagit.append(nimet[i].getNimi() + "   ");
			}
		}
		
		panelUni.setText(uniJaTagit.toString());
		editPaivamaara.setText(uniKohdalla.getPaivamaara());
		editArvio.setText(Integer.toString(uniKohdalla.getArvio()));
		editKesto.setText(Integer.toString(uniKohdalla.getKesto()));
		
	}
	
	
	/**
	 * Muokkaa olemassa olevaa unta
	 */
	private void muokkaa() {
		if( uniKohdalla == null ) return;
		
		try {
			Uni uni = addDreamController.kysyUni(null, uniKohdalla.clone());
			if ( uni == null ) return;
			sovellus.korvaaTaiLisaa(uni);
			haeUnet();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Muokkaa olemassa olevaa TagNimeä
	 */
	private void muokkaaTag() {
		if ( tagKohdalla == null ) return;
		
		String uusiNimi = "";
		while( !uusiNimi.startsWith("#")) {
			uusiNimi = Dialogs.showInputDialog("Anna tagille " + tagKohdalla.getNimi() + " uusi nimi",tagKohdalla.getNimi());
			if ( uusiNimi.startsWith("#")) continue;
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Huomautus");
	        alert.setHeaderText(null);
	        alert.setContentText("Tagin tulee alkaa #-merkillä!");
	        alert.showAndWait();
	        
		}
		tagKohdalla.setNimi(uusiNimi);
		haeTagienNimet();
		naytaUni();
	}
	
	
	/**
	 * Poistaa tagin nimen
	 */
	private void poistaTag() {
		if ( tagKohdalla == null ) return;
		boolean vastaus = Dialogs.showQuestionDialog("Poisto", "Haluatko varmasti poistaa tagin: " + tagKohdalla.getNimi() + "?", "Kyllä", "Ei" );
		
		if ( vastaus ) {
			sovellus.poistaTaginNimi(tagKohdalla);
			haeTagienNimet();
			naytaUni();
		}
	}
	
	
	/**
	 * Tulostaa välitetyn unen ja siihen liittyvät tägit
	 * @param os Tulostusvirta
	 * @param uni Valittu uni
	 */
	public void tulosta(PrintStream os, final Uni uni) {
		os.println("-------------------------");
		uni.tulosta(os);
		os.println("-------------------------");
		
		List<Tag> tagit = sovellus.annaTagit(uni);
	
		for (Tag ta : tagit) {
			System.out.println(ta);
			sovellus.annaTaginNimi(ta.getTagID()-1).tulosta(os); /////
		}
		
	}
	
	
	/**
	 * Tulostaa unen tagit
	 */
	public void tulostaUnenTagit(Uni uni) {
		for (Tag ta : sovellus.annaTagit(uni)) {
			System.out.println(ta + " KOHDALLA");
		}
	}
	
	
	/**
	 * Luo Tag olion jolla yhdistetään valittu uni ja valittu tagin nimi
	 */
	public void liitaTag() {
		if( tagKohdalla == null|| uniKohdalla == null) return;
		Tag relaatio = new Tag(tagKohdalla.getTagID(),uniKohdalla.getDreamID() );
		sovellus.lisaa(relaatio);
		naytaUni();
	}
	
	
	/**
	 * Luo uuden unen ja antaa sen sovellukselle
	 */
	protected void uusiUni() {
		Uni uusi = new Uni();
		// Kysy Unen tietoja
		uusi = addDreamController.kysyUni(null, uusi);
		if ( uusi == null ) return;
		uusi.rekisteroi();
		//uusi.taytaTiedoilla();
		try {
			sovellus.lisaa(uusi);
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
		}
		//hae(uusi.getDreamID());
		haeUnet();
		haeTagienNimet();
		laskeSeitsemanEdellisenPaivanUntenKa();
	}
	
	
	/**
	 * Laskee edellisen seitsemän päivän nukutun ajan keskiarvon ja näyttää sen komponenttiin
	 */
	public void laskeSeitsemanEdellisenPaivanUntenKa() {
		if (sovellus.getUnia() < 7) {
			editKeskiarvo.setText("");
			return;
		}
		double arvo = sovellus.laskeKeskiarvo(sovellus.annaAikajakso(7));
		String teksti = String.format("Nukut keskimäärin %.2f" +
				" tuntia viikossa" , arvo);
		editKeskiarvo.setText(teksti);
	}
	
	
	/**
	 * Laskee kaikkien päivien unien kestojen keskiarvon ja näyttää sen komponenttiin
	 */
	public void laskeUntenKa() {
		double arvo = sovellus.laskeUntenKeskiArvo();
		String teksti = String.format("Nukut keskimäärin %.2f tuntia yössä", arvo);
		editKeskiarvoKaikki.setText(teksti);
	}
	
	
	/**
	 * Luo uuden taginNimen ja antaa sen sovellukselle
	 */
	protected void uusiTaginNimi() {
		TagNimi uusi = new TagNimi();
		// Kysy Tagin tietoja
		uusi = addTagController.kysyTag(null,uusi);
		if ( uusi == null ) return;
		uusi.rekisteroi();
		sovellus.lisaa(uusi);
		
		haeTagienNimet();
	}
	
	
	/**
	 * Hakee tagien nimet
	 */
	private void haeTagienNimet() {
		chooserTagit.clear();
		
		int index = 0;
		for (int i = 0; i < sovellus.getTagienNimia(); i++) {
			TagNimi tagNimi = sovellus.annaTaginNimi(i);
			if (tagNimi == null) continue;
			if (tagNimi.getNimi().isEmpty()) continue;
			chooserTagit.add(tagNimi.getNimi(),tagNimi);
		}
		chooserTagit.setSelectedIndex(index);
	}
	
	
	/**
	 * Hakee unien tiedot listaan
	 * @param jnro unen tunnusnumero, joka aktivoidaan haun jälkeen
	 */
	@SuppressWarnings("unused")
	private void hae(int jnro) {
		chooserUnet.clear();
		
		int index = 0;
		for(int i = 0; i < sovellus.getUnia(); i++) {
			Uni uni = sovellus.annaUni(i);
			if (uni.getDreamID() == jnro) index = i;
			chooserUnet.add(uni.getDreamDate(), uni);
		}
		chooserUnet.setSelectedIndex(index);
	}
	
	
	/**
	 * Hakee unet komponenttiin ja varmistaa että ne näytetään oikeassa järjestyksessä
	 */
	private void haeUnet() {
		chooserUnet.clear();
		
		Collection<Uni> unet = sovellus.getUnet();
		
		List<LocalDate> paivamaarat = sovellus.annaPaivamaarat();
		Map<String, Integer> paivamaaraJarjestys = new HashMap<String,Integer>();
		
		for (int i = 0; i < paivamaarat.size(); i++) {
			String p = paivamaarat.get(i).toString();
			paivamaaraJarjestys.put(p, i);
		}
		
		Collections.sort((List<Uni>)unet, new UniPaivamaaraComparator(paivamaaraJarjestys));
		
		for (Uni uni : unet) {
			chooserUnet.add(uni.getPaivamaara(),uni);
		}
		laskeSeitsemanEdellisenPaivanUntenKa();	
		laskeUntenKa();
		chooserUnet.setSelectedIndex(0);
	}

	
	/**
	 * Käy unet ja siihen kuuluvat tagit läpi, jos unen sisältö tai tagin nimi sisältää hakusanan, se näytetään listchooserissa.
	 * @param hakusana sana jota etsitään
	 */
	private void handleHaku(String hakusana) {
		chooserUnet.clear();
		
		hakusana = hakusana.toLowerCase();
		
		for (Uni uni : sovellus.getUnet()) {
			if ( uni.getUnenSisalto().toLowerCase().contains(hakusana) ) {
				chooserUnet.add(uni.getPaivamaara(), uni);
				continue;
			}
			
			List<Tag> tagit = sovellus.annaTagit(uni);
			TagNimi[] nimet = sovellus.getTagienNimet();
			
			for (Tag ta : tagit) {
				for (int i = 0; i < nimet.length; i++) {
					if (nimet[i] == null) continue;
					if (ta.getTagID() == nimet[i].getTagID() && nimet[i].getNimi().toLowerCase().contains(hakusana)) {
						chooserUnet.add(uni.getPaivamaara(),uni);
						
					}
				}
			}		
		}
	}
	
	
	/**
	 * Poistetaan valittu uni
	 */
	public void poistaUni() {
		if ( uniKohdalla == null ) return;
		
		boolean vastaus = Dialogs.showQuestionDialog("Poisto?", "Poistetaanko merkintä?", "Kyllä", "Ei");
		if ( vastaus ) {
			sovellus.poistaUni(uniKohdalla);
			sovellus.poistaTagit(uniKohdalla);
		}
		
		haeUnet();
		if (sovellus.getUnia() == 0) pyyhiKentat();
	}
	
	
	/**
	 * Pyyhkii kentät
	 */
	public void pyyhiKentat() {
		editKesto.setText("");
		editArvio.setText("");
		editPaivamaara.setText("");
		panelUni.clear();
	}
	
	
}
