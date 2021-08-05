package unipaivakirja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


/**
 * @author walte
 * @version 25.07.2021
 * 
+--------------------------------------+--------------------------------------+
| Luokan nimi:Tagit                     | Avustajat:                           |
+--------------------------------------+--------------------------------------+
| Vastuualueet:                        |                                      |
|                                      |                                      |
| - Osaa järjestää tageja              | - tag                                |
| - Osaa lisätä ja poistaa tageja      |                                      |
| - Osaa tarkistaa onko tagia olemassa |                                      |
| - Lukee ja kirjoittaa tagit          |                                      |
| tiedostoon                           |                                      |
+--------------------------------------+--------------------------------------+
 */
public class Tagit implements Iterable<Tag> { 
	
	private String tiedostonNimi = "resources";
	
	private final Collection<Tag> alkiot = new ArrayList<Tag>();
	
	
	/**
	 * Tagien alustus
	 */
	public Tagit() {
		// Vielä tyhjä
	}
	
	
	/*
	 * Lisää tagin tietorakenteeseen.
	 * @param ta lisättävä tagi.
	 */
	public void lisaa(Tag ta) {
		alkiot.add(ta);
	}
	
	
	/**
	 * Poistaa tagit joiden tunnusnro on parametrina tuotu nro
	 * @param tunnusnro nro
	 */
	public void poistaTagit(int tunnusnro) {
		List<Tag> poistettavat = new ArrayList<>();
		for(Tag tag : alkiot) {
			if (tag.getDreamID() == tunnusnro) poistettavat.add(tag);
		}
		
		alkiot.removeAll(poistettavat);
		
	}
	
	
	/**
	 * Palauttaa tagien lukumäärän
	 * @return tagien lukumäärä
	 */
	public int getLkm() {
		return alkiot.size();
	}
		
	
	/**
	 * Haetaan kaikki unen tagit
	 * @param tunnusnro unen tunnusnumero jolle tageja haetaan
	 * @return lista jossa viitteet löydettyihin tageihin
	 * @example
	 * <pre name="test">
	 * #import java.util.*
	 * 
	 * Tagit<Tag> tagit = new Tagit();
	 * Tag tag1 = new Tag(1); tagit.lisaa(tag1);
	 * Tag tag2 = new Tag(12); tagit.lisaa(tag2);
	 * Tag tag3 = new Tag(1); tagit.lisaa(tag3);
	 * Tag tag4 = new Tag(14); tagit.lisaa(tag4);
	 * 
	 * List<Tag> loytyneet;
	 * loytyneet = tagit.annaTagit(2);
	 * loytyneet.size() === 0;
	 * loytyneet = tagit.annaTagit(1);
	 * loytyneet.size() === 2;
	 * </pre>
	 */
	public List<Tag> annaTagit(int tunnusnro) {
		List<Tag> loydetyt = new ArrayList<Tag>();
		for (Tag ta : alkiot) 
			if (ta.getDreamID() == tunnusnro) loydetyt.add(ta);
		return loydetyt;
		
	}


	/**
	 * Iteraattori tagien läpikäymiseen
	 * @return tagiteraattori
	 * @example
	 * <pre name="test">
	 * #PACKAGEIMPORT
	 * #import java.util.*;
	 * 
	 * Tagit tagit = new Tagit();
	 * Tag tag1 = new Tag(1); tagit.lisaa(tag1);
	 * Tag tag2 = new Tag(2); tagit.lisaa(tag2);
	 * Tag tag3 = new Tag(3); tagit.lisaa(tag3);
	 * Tag tag4 = new Tag(4); tagit.lisaa(tag4);
	 * 
	 * Iterator<Tag> i2=tagit.iterator();
	 * i2.next() === tag1;
	 * i2.next() === tag2;
	 * i2.next() === tag3;
	 * i2.next() === tag4;
	 * i2.next() === tag1; #THROWS NoSuchElementException
	 * </pre>
	 */
	@Override
	public Iterator<Tag> iterator() {
		// TODO Auto-generated method stub
		return alkiot.iterator();
	}
	
	
	/**
	 * Tallentaa tiedot 
	 * @param tiedostonNimi tiedosto johon tallennetaan
	 * @throws SailoException
	 */
	public void tallenna(String tiedostonNimi) throws SailoException {
		File ftied = new File(tiedostonNimi + "/dream_tags.csv");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (Tag tag : alkiot) {
				fo.println(tag);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea.");
		}
	}
	
	
	/**
	 * Tallentaa tiedot 
	 * @param tiedostonNimi tiedosto johon tallennetaan
	 * @throws SailoException
	 */
	public void tallenna() throws SailoException {
		File ftied = new File(tiedostonNimi + "/dream_tags.csv");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
			for (Tag tag : alkiot) {
				fo.println(tag);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea.");
		}
	}
	
	
	/**
	 * Lukee tagit tiedostosta ja lisää ne tietorakenteeseen
	 * @throws SailoException Jos ei voida lukea
	 */
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/dream_tags.csv";
		File ftied = new File(tiedostonNimi);
	
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
			while ( fi.hasNext() ) {
				String s = "";
				s = fi.nextLine();
				Tag tag = new Tag();
				tag.parse(s);
				lisaa(tag);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Ei saa luettua " + tiedostonNimi);
		} 
	}
	
	
	/**
	 * Lukee tagit tiedostosta ja lisää ne tietorakenteeseen
	 * @throws SailoException Jos ei voida lukea
	 */
	public void lueTiedostosta() throws SailoException {
		tiedostonNimi = tiedostonNimi + "/dream_tags.csv";
		File ftied = new File(tiedostonNimi);
	
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
			while ( fi.hasNext() ) {
				String s = "";
				s = fi.nextLine();
				Tag tag = new Tag();
				tag.parse(s);
				lisaa(tag);
			}
		} catch (FileNotFoundException e) {
			throw new SailoException("Ei saa luettua " + tiedostonNimi);
		} 
	}
	
	
	 /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
	public static void main(String[] args) {
		Tagit tagit = new Tagit();
		
		try {
			tagit.lueTiedostosta("resources");
		} catch (SailoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Tag tag1 = new Tag();
		tag1.taytaTiedoilla(4);
		tag1.rekisteroi();
		
		Tag tag2 = new Tag();
		tag2.taytaTiedoilla(3);
		tag2.rekisteroi();
		
		Tag tag3 = new Tag();
		tag3.taytaTiedoilla(2);
		tag3.rekisteroi();
		
		
		tagit.lisaa(tag1);
		tagit.lisaa(tag2);
		tagit.lisaa(tag3);
		
		System.out.println("=========== Tagit testi ============");
		
		List<Tag> tagit2 = tagit.annaTagit(3);
		
		for (Tag ta : tagit2) {
			System.out.print(ta.getDreamID() + " ");
			ta.tulosta(System.out);
			
		}
		
		
		try {
			tagit.tallenna("resources");
		} catch (SailoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
