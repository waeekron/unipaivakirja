package unipaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author walte
 * @version 27.7.2021
+--------------------------------------+--------------------------------------+
| Luokan nimi:TagNimi                  | Avustajat:                           |
+--------------------------------------+--------------------------------------+
| Vastuualueet:                        |                                      |
|                                      |                                      |
| - Tietää tagien id:t ja nimet        |                                      |
|                                      |                                      |
| - Osaa muuttaa itsensä merkkijonoksi |                                      |
|                                      |                                      |
|                                      |                                      |
+--------------------------------------+--------------------------------------+
 */
public class TagNimi {
	
	private String nimi = "";
	private int tagID;
	private static int seuraavaNro = 1;
	
	
	/**
	 * Tyhjä konstruktori
	 */
	public TagNimi() {
		//
	}
	
	
	/**
	 * Konstruktori
	 * @param ni nimi
	 */
	public TagNimi(String ni) {
		this.nimi = ni;
	}
	
	
	/**
	 * Konstruktori kaikille TagNimen kentille 
	 * @param id tagID
	 * @param ni nimi
	 */
	public TagNimi(int id, String ni) {
		this.tagID = id;
		this.nimi = ni;
	}
	
	
	/**
	 * Asettaa tagille seuraavan id numeron
	 * @return tagin uusi tagID
	 * @example
	 * <pre name="test">
	 * TagNimi tag = new TagNimi();
	 * tag.getTagID() === 0;
	 * tag.rekisteroi();
	 * TagNimi tag2 = new TagNimi();
	 * tag2.rekisteroi();
	 * 
	 * int n1 = tag.getTagID();
	 * int n2 = tag2.getTagID();
	 * n1 === n2 - 1;
	 * </pre>
	 */
	public void rekisteroi(){
		this.tagID = seuraavaNro;
		seuraavaNro++;
	}
	
	
	/**
	 * Asettaa id:n ja varmistaa, että seuraava numero on aina suurempi
	 * @param nro asetettava id nro
	 */
	private void setTagID(int nro) {
		tagID = nro;
		if (tagID >= seuraavaNro) seuraavaNro = tagID + 1;
	}
	
	
	/**
	 * @return tagID
	 */
	public int getTagID() {
		return this.tagID;
	}
	
	
	/**
	 * @return nimi
	 */
	public String getNimi() {
		return this.nimi;
	}
	
	
	/**
	 * Asettaa nimen 
	 * @param ni asetettava nimi
	 */
	public void setNimi(String ni) {
		this.nimi = ni;
	}
	
	
	/**
	 * Täyttää tägin testi tiedoilla
	 */
	public void taytaTiedoilla() {
		Random rnd = new Random();
		this.nimi = "#HashTag" + rnd.nextInt();
	}
	
	
    /**
     * Tulostetaan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(tagID + " " + nimi );
    }


    /**
     * Tulostetaan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Selvittää TagNimen tiedot csv tiedostosta ja pitää huolen
     * että seuraavaNro on suurempi kuin tuleva tagID
     * @param rivi csv rivi
     * @example
     * <pre name="test">
     * TagNimi t = new TagNimi();
     * t.parse("2|valveuni");
     * t.getTagID() === 2;
     * </pre>
     */
    public void parse(String rivi) {
    	var sb = new StringBuilder(rivi);
    	setTagID(Mjonot.erota(sb, '|', getTagID()));
    	this.nimi = Mjonot.erota(sb, '|', nimi);
    	
    }
    
    
    /**
     * Palauttaa tagin tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return TagNimi csv muodossa
     * @example
     * <pre name="test">
     * TagNimi t = new TagNimi();
     * t.parse("2|valveuni");
     * t.toString().equals("2|valveuni") === true;
     * </pre>
     */
	@Override
	public String toString() {
		return this.tagID + "|" + this.nimi;
	}

	
	/**
	 * 
	 * @param args Ei käytössä
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
