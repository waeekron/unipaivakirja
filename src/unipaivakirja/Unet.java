package unipaivakirja;

import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;


/**
 *  @author walter
 *  @version 26.7.2021
 *  
 +--------------------------------------+--------------------------------------+
| Luokan nimi:Unet                     | Avustajat:                           |
+--------------------------------------+--------------------------------------+
| Vastuualueet:                        |                                      |
|                                      |                                      |
| - Osaa järjestää unia                | - Uni                                |
| - Lisäys/poisto                      |                                      |
|                                      |                                      |
| - Lukee ja kirjoittaa unet           |                                      |
| tiedostoon                           |                                      |
+--------------------------------------+--------------------------------------+
 */
public class Unet {
	
	
	private Collection<Uni> alkiot = new ArrayList<>();
	private String tiedostonNimi = "resources";
	
	
	
	/**
	 * Tyhjä konstruktori
	 */
	public Unet() {
		//
	}
	
	
	/**
	 * Asettaa tiedoston
	 * @param ni nimi
	 */
	public void setTiedostonNimi(String ni) {
		this.tiedostonNimi = ni;
	}
	
	
	/**
	 * Palauttaa uni kokoelman
	 * @return Collection<Uni>
	 */
	public Collection<Uni> getUnet() {
		return this.alkiot;
	}
	
	
	/**
	 * Lisää unen tietorakenteeseen.
	 * @param uni
	 * @throws SailoException
	 * @example
	 * <pre name="test">
	 * Unet unet = new Unet();
	 * unet.getLkm() === 0;
	 * Uni u1 = new Uni();
	 * Uni u2 = new Uni();
	 * Uni u3 = new Uni();
	 * 
	 * unet.lisaa(u1);
	 * unet.lisaa(u2);
	 * unet.lisaa(u3);
	 * 
	 * unet.getLkm() === 3;
	 * </pre>	
	 *
	 */
	public void lisaa(Uni uni) {
		alkiot.add(uni);
	}
	
	
	/**
	 * Poistaa uni viitteen tietorakenteesta
	 * @param uni poistettava
	 */
	public void poista(Uni uni) {
		alkiot.remove(uni);
	}
	
	
	/**
	 * Korvaa uni viitteen tietorakenteessa. Ottaa unen omistukseensa.
	 * Jos korvattavaa ei löydy niin lisätään uni uutena unena. 
	 * @param uni korvattava/lisättävä
	 */
	public void korvaaTaiLisaa(Uni uni) {
		int id = uni.getUnenTunnusNro();
		var uusiLista = new ArrayList<Uni>(alkiot);
		for (int i = 0; i < getLkm(); i++) {
			if (uusiLista.get(i).getUnenTunnusNro() == id) {
				uusiLista.set(i, uni);
				alkiot = uusiLista;
				return;
			}
		}
		lisaa(uni);
	}
	
	
	/**
	 * Palauttaa unien lukumäärän
	 * @return Unien lukumäärä
	 */
	public int getLkm() {
		return alkiot.size();
	}
	
	
	/**
	 * Palauttaa viitteen i:teen uneen.
	 * @param i Monesko viite palautetaan
	 * @return viite uneen, jonka indeksi on i
	 * @throws IndexOutOfBoundsException Jos i ei ole sallitulla alueella.
	 */
	public Uni anna(int i) throws IndexOutOfBoundsException {
		if (i < 0 || alkiot.size() < i) {
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		}
		return ((ArrayList<Uni>) alkiot).get(i);
	}
	
	
	/**
	 * Tallentaa unet tiedostoon
	 * Tiedoston muoto:
	 * dream_id | user_id | location | duration | date | rating
	 * 1, 1, uni1.txt, 8, 13.1.22, 2
	 * 2, 1, uni2.txt, 7, 14.1.22, -1
	 * @param tiedostonNimi tallennettavan tiedoston nimi
	 * @throws SailoException jos talletus epäonnistuu
	 */
	public void tallenna(String tiedostonNimi) throws SailoException {
		File ftied = new File(tiedostonNimi + "/dreams.csv");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (Uni uni : alkiot) {
				fo.println(uni);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea.");
		}
	}
	
	
	/**
	 * Tallentaa unet tiedostoon
	 * Tiedoston muoto:
	 * dream_id | user_id | location | duration | date | rating
	 * 1, 1, uni1.txt, 8, 13.1.22, 2
	 * 2, 1, uni2.txt, 7, 14.1.22, -1
	 * @param tiedostonNimi tallennettavan tiedoston nimi
	 * @throws SailoException jos talletus epäonnistuu
	 */
	public void tallenna() throws SailoException {
		File ftied = new File(tiedostonNimi + "/dreams.csv");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (Uni uni : alkiot) {
				fo.println(uni);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea.");
		}
	}
	
	
	/**
	 * Lukee unet tiedostosta
	 * @throws SailoException Jos ei saada luettua
	 */
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/dreams.csv";
		File ftied = new File(tiedostonNimi);
		
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
			while ( fi.hasNext() ) {
				String s = "";
				s = fi.nextLine();
				Uni uni = new Uni();
				uni.parse(s);
				lisaa(uni);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Ei saa luettua " + tiedostonNimi);
		} 
	}

	
	/**
	 * Lukee unet tiedostosta
	 * @throws SailoException Jos ei saada luettua
	 */
	public void lueTiedostosta() throws SailoException {
		tiedostonNimi = tiedostonNimi + "/dreams.csv";
		File ftied = new File(tiedostonNimi);
		
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
			while ( fi.hasNext() ) {
				String s = "";
				s = fi.nextLine();
				Uni uni = new Uni();
				uni.parse(s);
				lisaa(uni);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Ei saa luettua " + tiedostonNimi);
		} 
	}
	
	
	/**
	 * @return unien päivämäärät nousevaan järjestykseen järjestettynä localdate listana
	 * @example
	 * <pre name="test">
	 * #import java.time.LocalDate;
	 * #import java.util.*;
	 * Unet unet = new Unet();
	 * Uni u1 = new Uni(); u1.setPaivamaara("2021-08-01");
	 * Uni u2 = new Uni(); u2.setPaivamaara("2021-08-03");
	 * Uni u3 = new Uni(); u3.setPaivamaara("2021-08-02");
	 * unet.lisaa(u1);
	 * unet.lisaa(u2);
	 * unet.lisaa(u3);
	 * for (LocalDate ld : unet.annaPaivamaarat() ) {
	 * 		System.out.println(ld.toString());
	 * 	
	 * }
	 * ArrayList<LocalDate> pvmt = unet.annaPaivamaarat();
	 * pvmt.get(2).toString() === "2021-08-03";
	 * </pre>
	 */
	public ArrayList<LocalDate> annaPaivamaarat () {
		 ArrayList<LocalDate> pvmt = new ArrayList<>();
		 for(Uni uni : alkiot) {
			 LocalDate pvm = LocalDate.parse(uni.getPaivamaara());
			 pvmt.add(pvm);
		 }
		 Collections.sort(pvmt);
		 return pvmt;
	}
	
	
	/**
	 * Palauttaa halutun määrän LocalDate olioita listana
	 * @param pituus kuinka monta päivää halutaan mukaan
	 * @return ArrayList<LocalDate> jossa on päivämäärät: viimeisinlisätty päivämäärä, viimeisinlisätty - 1, ...., viimeisinlisätty-pituus 
	 * @example
	 * <pre name="test">
	 * #import java.time.LocalDate;
	 * #import java.util.*;
	 * Uni u1 = new Uni(); u1.setPaivamaara("2021-08-01"); u1.setKesto(8);
	 * Uni u2 = new Uni(); u2.setPaivamaara("2021-08-03"); u2.setKesto(7);
	 * Uni u3 = new Uni(); u3.setPaivamaara("2021-08-02"); u3.setKesto(5);
	 * Unet unet = new Unet();
	 * unet.lisaa(u1);
	 * unet.lisaa(u2);
	 * unet.lisaa(u3);
	 * unet.annaAikajakso(2).size() === 2;
	 * unet.annaAikajakso(2).get(0).toString() === "2021-08-03";
	 * </pre>
	 */
	public ArrayList<LocalDate> annaAikajakso(int pituus) {
		ArrayList<LocalDate> pvmt = annaPaivamaarat();
		if (pituus > pvmt.size() ) return null;
		ArrayList<LocalDate> uusi = new ArrayList<>();
		for (int i = pvmt.size() - 1; i >= pvmt.size() - pituus ; i--) {
			uusi.add(pvmt.get(i));
		}
		return uusi;
	}
	
	
	/**
	 * Laskee keskiarvon parametrina tuoduista päivämääristä
	 * @param aikajakso aikajakso
	 * @return Päivämäärinä nukuttujen tuntien keskiarvo
	 */
	public double laskeKeskiarvo(ArrayList<LocalDate> aikajakso) {
		if (aikajakso == null  || aikajakso.isEmpty()) return 0;
		double ka = 0;
		int summa = 0;
		for (LocalDate ld : aikajakso) {
			summa += annaPaivanUnenKesto(ld.toString());
		}
		ka = summa / aikajakso.size();
		return ka;
	}
	
	
	/**
	 * Hakee tietyn päivän unen keston
	 * @param pvm jolle haetaan
	 * @return päivän unen kesto
	 */
	public int annaPaivanUnenKesto(String pvm) {
		for (Uni uni : alkiot) {
			if (uni.getPaivamaara().equals(pvm)) return uni.getKesto();
		}
		
		return 0;
	}
	
	
	/**
	 * Testiohjelma
	 * @param args ei käytössä
	 */
	public static void main(String args[]) {
		Unet unet = new Unet();
		
		try {
			unet.lueTiedostosta("resources");
		} catch (SailoException e) {
			System.err.println("Ei voi lukea " + e.getMessage());
		}
		
		
		
		Uni uni = new Uni(), uni2 = new Uni();
		uni.rekisteroi();
		uni.taytaTiedoilla();
		uni2.rekisteroi();
		uni2.taytaTiedoilla();
		
		
			unet.lisaa(uni);
			unet.lisaa(uni2);
			
			System.out.println("============== Unet Testi ==============");
			
			for (int i = 0; i < unet.getLkm(); i++) {
				Uni uusiUni = unet.anna(i);
				System.out.println("Uni nro: " + i);
				uusiUni.tulosta(System.out);
			}
		
		
		try {
			unet.tallenna("resources");
		} catch (SailoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public double laskeUntenKeskiArvo() {
		if (alkiot.isEmpty()) return 0;
		double summa = 0;
		for (Uni uni : alkiot) {
		summa += uni.getKesto();
		}
		return summa/alkiot.size();
	}



}
