package unipaivakirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *  @author walter
 *  @version 28.7.2021
 *  
+--------------------------------------+--------------------------------------+
| Luokan nimi: TagienNimet             | Avustajat:                           |
+--------------------------------------+--------------------------------------+
| Vastuualueet:                        |                                      |
|                                      |                                      |
| - Lukee ja kirjoittaa tiedostoon     |   - TagNimi                          |
| - Syötteen tarkistus                 |                                      |
| - Osaa tarkistaa onko tietyn nimistä |                                      |
| tagia olemassa                       |                                      |
|                                      |                                      |
+--------------------------------------+--------------------------------------+
 */
public class TagienNimet {

	private int lkm = 0;
	private String tiedostonNimi = "resources";
	private TagNimi alkiot[] = new TagNimi[5];
	
	
	/**
	 * Lisää tagin tietorakenteeseen
	 * @param ta tietorakenteeseen lisättävä tag
	 */
    public void lisaa(TagNimi ta) {
        if (lkm >= alkiot.length)
            isompiTaulukko();
        this.alkiot[this.lkm] = ta;
        lkm++;
     
    }
    
    
    /**
     * Poistaa valitun tagin taulukosta
     * @param ta poistettava
     * @example
     * <pre name="test">
     * TagienNimet tagit = new TagienNimet();
     * TagNimi t1 = new TagNimi(1,"tag");
     * tagit.lisaa(t1);
     * tagit.anna(0).toString() === "1|tag";
     * tagit.getLkm() === 1;
     * tagit.poista(t1);
     * tagit.getLkm() === 0;
     * tagit.anna(0) === null;
     * TagNimi t2 = new TagNimi(1,"tag");
     * TagNimi t3 = new TagNimi(2,"ta2");
     * tagit.lisaa(t2);
     * tagit.lisaa(t3);
     * tagit.poista(t2);
     * TagNimi[] lista = tagit.getTagienNimet();
     * for(int i = 0; i < lista.length; i++) { System.out.println(lista[i]); }
     * </pre>
     */
    public void poista(TagNimi ta) {
    	for (int i = 0; i < lkm; i++) {
    		if (alkiot[i].getTagID() == ta.getTagID()) {
    			lkm--;
    			for (int j = i; j < lkm; j++) {
    				int k = j + 1;
    				alkiot[j] = alkiot[k];
    			}
    			
    			alkiot[lkm] = null;
    			
    			return;
    		}
    	}
    }
    
    
    /**
     * Kasvattaa taulukon kokoa tarvittaessa 
     */
    private void isompiTaulukko() {
    	TagNimi[] alkiotUusi = new TagNimi[lkm + 5];
        for (int i = 0; i < lkm; i++) {
            alkiotUusi[i] = alkiot[i];
        }

        alkiot = alkiotUusi;
    }
    
    
    /**
     * @return tagien nimien lukumäärän
     */
    public int getLkm() {
    	return this.lkm;
    }
    
    
    /**
     * Palauttaa taulukon jossa on viitteitä TagNimi olioihin
     * @return TagNimi-taulukko
     */
    public TagNimi[] getTagienNimet() {
    	return this.alkiot;
    }
    
    
    /**
     * Täyttää tietorakenteen testiarvoilla
     */
    public void taytaTiedoilla() {
    	TagNimi t1 = new TagNimi("#Valveuni"); t1.rekisteroi(); lisaa(t1);
    	TagNimi t2 = new TagNimi("#Kissa"); t2.rekisteroi(); lisaa(t2);
    	TagNimi t3 = new TagNimi("#Kuolema"); t3.rekisteroi(); lisaa(t3);
    }
    
	
    /**
     * Palauttaa viitteen i:teen tagiin
     * @param i Monesko viite palautetaan
     * @return viite tagiin, jonka indeksi on i
     */
	public TagNimi anna(int i) throws IndexOutOfBoundsException {
		if (i < 0 || alkiot.length < i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return alkiot[i];
	}
	
	
	/**
	 * Kirjoittaa tietorakenteen tiedostoon
	 * @throws SailoException
	 */
	public void tallenna() throws SailoException {
		File ftied = new File(tiedostonNimi + "/tag_names.csv");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			TagNimi[] nimet = getTagienNimet();
			for(int i = 0; i < nimet.length; i++) {
				if(nimet[i] == null) continue;
				fo.println(nimet[i]);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea.");
		}
	}
	
	
	/**
	 * Lukee tagien nimet tiedostosta ja lisää ne tietorakenteeseen 
	 * @throws SailoException jos ei saada luettua tiedostoa
	 */
	public void lueTiedostosta() throws SailoException {
		File ftied = new File(tiedostonNimi + "/tag_names.csv");
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
			while (fi.hasNext()) {
				String s = "";
				s = fi.nextLine();
				TagNimi tag = new TagNimi();
				tag.parse(s);
				lisaa(tag);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Ei saa luettua " + tiedostonNimi);
		}
	}
	
	
	/**
	 * Testiohjelma tagien nimille
	 * @param args Ei käytössä
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TagienNimet nimet = new TagienNimet();
		
		try {
			nimet.lueTiedostosta();
		} catch (SailoException e) {
			e.printStackTrace();
		}
		
		TagNimi t1 = new TagNimi();
		t1.taytaTiedoilla();
		t1.rekisteroi();
		
		nimet.lisaa(t1);
		
		for (int i=0; i < nimet.getLkm();i++) {
			if(nimet.anna(i) == null) continue;
			System.out.println(nimet.anna(i));
		}
		
		try {
			nimet.tallenna();
		} catch (SailoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
