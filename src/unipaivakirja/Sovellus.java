package unipaivakirja;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sovellus-luokka, joka huolehtii unista ja välitää tietoa käyttöliittymään.
 * @author walte
 * @version 31.05.2021
 */
public class Sovellus {
	private Unet unet = new Unet();
	private Tagit tagit = new Tagit();
	private TagienNimet tagienNimet = new TagienNimet(); 
	
	
	/**
	 * Tyhjä konstruktori 
	 */
	public Sovellus() {
		//
	}
	
	
	/**
	 * Täyttää tagienNimet olion testitiedolla
	 */
	public void alustaTagienNimet() {
		this.tagienNimet.taytaTiedoilla();
	}
	
	
	/**
	 * Palauttaa käyttäjän unien määrän
	 * @return käyttäjän unien määrä
	 */
	public int getUnia() {
		return unet.getLkm();
	}
	
	
	/**
	 * @return tagien nimien lukumäärä
	 */
	public int getTagienNimia() {
		return tagienNimet.getLkm();
	}
	
	
	/**
	 * @return TagNimi taulukko
	 */
	public TagNimi[] getTagienNimet() {
		return tagienNimet.getTagienNimet();
	}
	
	
	/**
	 * Poistaa parametrina tuodun paikan
	 * @param nro viitenumero, jonka mukaan poistetaan
	 * @return montako jäsentä poistettiin
	 */
	public void poistaUni(Uni uni) {
		unet.poista(uni);
	}
	
	
	/**
	 * Poistaa parametrina tuodun uneen liitetyt tagit
	 * @param uni Uni jonka tagit poistetaan
	 */
	public void poistaTagit(Uni uni) {
		tagit.poistaTagit(uni.getUnenTunnusNro());
	}
	
	
	/**
	 * Poistaa niiden tagien viitteet tietorakenteesta joiden tagID vastaa parametrina tuotua nro 
	 * @param nro jonka perusteella poistetaan
	 */
	public void poistaTagit(int nro) {
		tagit.poistaTagit(nro);
	}
	
	
	/**
	 * Poistaa tagin nimen tietorakenteesta. Poistaa myös vastaavan relaation 
	 */
	public void poistaTaginNimi(TagNimi tag) {
		tagienNimet.poista(tag);
		poistaTagit(tag.getTagID());
	}
	
	
	/**
	 * Lisää uuden unen
	 * @param lisättävä uni
	 * @throws SailoException jos ei voida lisätä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Unet unet = new Unet();
	 * Uni uni1 = new Uni();
	 * unet.lisaa(uni1); unet.getLkm() === 1;
	 * unet.lisaa(uni1);
	 * unet.lisaa(uni1);
	 * unet.lisaa(uni1);
	 * unet.lisaa(uni1); unet.getLkm() === 5;
	 * unet.lisaa(uni1); #THROWS SailoException
	 * </pre>	
	 *
	 */
	public void lisaa(Uni uni) throws SailoException {
		unet.lisaa(uni);
	}
	
	
	/**
	 * Lisätään tagi 
	 * @param ta lisättävä tagi
	 */
	public void lisaa(Tag ta) {
		tagit.lisaa(ta);
	}
	
	
	/**
	 * Lisätään TagNimi 
	 * @param ta lisättä TagNimi
	 */
	public void lisaa(TagNimi ta) {
		tagienNimet.lisaa(ta);
	}
	
	
	/**
	 * Haetaan kaikki unen tagit
	 * @param uni uni jolle tageja haetaan
	 * @return listan jossa viitteet löydettyihin uniin
	 * @example
	 * <pre name="test">
	 * 
	 * </pre>
	 */
	public List<Tag> annaTagit (Uni uni) {
		return tagit.annaTagit(uni.getUnenTunnusNro());
	}
	
	
	/**
	 * @return sovelluksen unet
	 */
	public Collection<Uni> getUnet() {
		return this.unet.getUnet();
	}
	
	
	/**
	 * Korvaa tai lisää unen tietorakenteeseen
	 * @param uni lisättävä/korvattava
	 */
	public void korvaaTaiLisaa(Uni uni) {
		unet.korvaaTaiLisaa(uni);
		
	}
	
	
	/**
	 * Palauttaa indeksin mukaisen tagin nimen
	 * @param i indeksi josta tagin nimi palautetaan
	 * @return viite indeksissä olevaan uneen
	 * @throws IndexOutOfBoundsException 
	 */
	public TagNimi annaTaginNimi(int i) throws IndexOutOfBoundsException {
		return tagienNimet.anna(i);
	}
	
	
	/**
	 * Palauttaa indexin mukaisen unen
	 * @param i indexi josta uni palautetaan
	 * @return viite indexissä olevaan uneen
	 * @throws IndexOutOfBoundsException jos i on liian pieni/iso
	 */
	public Uni annaUni(int i)  {
		return unet.anna(i);
	}
	
	
	/**
	 * Palauttaa kaikkien unien paivamäärät LocalDate listana
	 * @return nousevassa järjestyksessä oleva lista
	 */
	public ArrayList<LocalDate> annaPaivamaarat() {
		return unet.annaPaivamaarat();
	}
	
	
	/**
	 *  Laskee keskiarvon aikajaksoon sisältyvien päivien nukutuista tunneista
	 * @param aikajakso 
	 * @return unten keskiarvo
	 */
	public double laskeKeskiarvo(ArrayList<LocalDate> aikajakso) {
		return unet.laskeKeskiarvo(aikajakso);
	}
	
	
	/**
	 * Palauttaa pituuden mittaisen aikajakson
	 * @param pituus
	 * @return Lista LocalDate olioita
	 */
	public ArrayList<LocalDate> annaAikajakso(int pituus) {
		return unet.annaAikajakso(pituus);
	}
	
	
	/**
	 * Lukee tiedostot 
	 * @throws SailoException
	 */
	public void lueTiedostosta() throws SailoException {
		unet = new Unet();
		unet.lueTiedostosta();
		tagit = new Tagit();
		tagit.lueTiedostosta();
		tagienNimet = new TagienNimet();
		tagienNimet.lueTiedostosta();
	}
	
	
	/**
	 * 
	 */
	public void tallenna() throws SailoException {
		String virhe = "";
		try {
			unet.tallenna("resources");
			
		} catch ( SailoException e) {
			virhe += e.getMessage();
		}
		
		try {
			tagit.tallenna("resources");
		} catch ( SailoException e) {
			virhe += e.getMessage();
		}
		
		try {
			tagienNimet.tallenna();
		} catch ( SailoException e ) {
			virhe += e.getMessage();
		}
		
		if( !"".equals(virhe)) throw new SailoException(virhe);
		
	}
	
	
	/**
	 * Testiohjelma 
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sovellus sovellus = new Sovellus();
		
		
		try {
			
			Uni uni1 = new Uni(), uni2 = new Uni();
			uni1.rekisteroi();
			uni1.taytaTiedoilla();
			uni2.rekisteroi();
			uni2.taytaTiedoilla();
			
			sovellus.lisaa(uni1);
			sovellus.lisaa(uni2);
			
			System.out.println("============= Sovelluksen testi =================");
			
			for(int i = 0; i < sovellus.getUnia(); i++) {
				Uni uni = sovellus.annaUni(i);
				System.out.println("Jäsen paikassa: " + i);
				uni.tulosta(System.out);
			}
		} catch (SailoException ex) {
			System.out.println(ex.getMessage());
		}

	}

	
	/**
	 * Laskee kaikkien päivien unien kestojen keskiarvon 
	 * @return keskiarvo
	 */
	public double laskeUntenKeskiArvo() {
		
		return unet.laskeUntenKeskiArvo();
	}



}
