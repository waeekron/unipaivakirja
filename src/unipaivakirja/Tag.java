package unipaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * 
 * @author walte
 * @version 25.07.2021
 * +--------------------------------------+--------------------------------------+
| Luokan nimi:Tag                      | Avustajat:                           |
+--------------------------------------+--------------------------------------+
| Vastuualueet:                        |                                      |
|                                      |                                      |
| - Tietää tag_id:n ja dream_id:n      |                                      |
|                                      |                                      |
|                                      |                                      |
|                                      |                                      |
| - Osaa muutta itsensä merkkijonoksi  |                                      |
+--------------------------------------+--------------------------------------+
 */
public class Tag {
	
	
	
	private int tagTunnusnro;
	private int unenTunnusnro;
	private static int seuraavaNro = 1;
	
	
	
	/*
	 * Tyhjä konstruktori
	 */
	public Tag() {
		//
	}
	
	
	/**
	 * Konstruktori jolla liitetään tietty uni tagiin
	 * @param did
	 */
	public Tag(int did) {
		this.unenTunnusnro = did;
	}
	
	
	/**
	 * Konstruktori kaikille kentille
	 * @param tagID tagin tunnusnumero
	 * @param dreamID unen tunnusnumero
	 */
	public Tag(int tagID, int dreamID) {
		// TODO Auto-generated constructor stub
		this.unenTunnusnro = dreamID;
		this.tagTunnusnro = tagID;
	}


	/**
	 * Rekisteröi tagille tunnusnumeron
	 */
	public void rekisteroi() {
		if(this.tagTunnusnro >= 0) return;
		this.tagTunnusnro  = seuraavaNro++;
	}
	

	/**
	 * Asettaa tunnusnnumeron ja samalla varmistaa että
	 * seuraava numero on aina suurempi kuin tähän mennessä suurin.
	 * @param nro asetettava tunnusnumero
	 */
	public void setTunnusNro(int nro) {
		this.tagTunnusnro = nro;
		if ( tagTunnusnro >= seuraavaNro ) seuraavaNro = tagTunnusnro + 1;
	}
	
	
	/**
	 * 
	 * @param nro
	 */
	public static void setSeuraavaNRO(int nro) {
		seuraavaNro = nro;
	}
	
	
	/**
	 * @returns Olion kentät pilkulla eroteltuna.
	 */
	@Override
	public String toString() {
		return "" + 
				this.unenTunnusnro +  "|" + 
				this.tagTunnusnro  ;
	}
	
	
	/**
	 * Selvittää tagin tiedot csv tiedostosta luetusta merkkijonosta
	 * @param s
	 * 
	 * comt@example
	 * <pre name="test">
	 * Tag tag = new Tag();
	 * tag.parse("1|2");
	 * tag.getTagID() === 2;
	 * tag.getDreamID() === 1;
	 * 
	 * tag.toString() === "1|2";
	 * </pre>
	 */
	public void parse(String s) {
		var sb = new StringBuilder(s);
		unenTunnusnro = Mjonot.erota(sb, '|', unenTunnusnro);
		setTunnusNro(Mjonot.erota(sb, '|', getTagID()));
	}
	
	
	/**
	 * Tulostaa tagin tiedot 
	 * @param out tietovirta johon tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.println("tagTunnusnro: " + this.tagTunnusnro);
		out.println("uniTunnusnro: " + this.unenTunnusnro);
	}
	
	
	/**
	 * Tulostaa tagin tiedot
	 * @param os tietovirta johon tulostetaan
	 */
	public void tulosta(OutputStream os) {
		tulosta(new PrintStream(os));
	}
	
	
	/**
	 * Täyttää tagin kentät testiarvoilla
	 * @param nro viite uneen 
	 */
	public void taytaTiedoilla(int nro) {
		this.unenTunnusnro = nro;
		//Random rand = new Random();
		//this.tagTunnusnro = rand.nextInt(1000);
	}
	
	
	 /**
	 * Getteri unen tunnusnumerolle
	 * @return unen tunnusnumero
	 */
	public int getDreamID() {
		
		return this.unenTunnusnro;
	}
	
	
	/**
	 * 
	 * @return tag nimen tunnusnumero
	 */
	public int getTagID() {
		return this.tagTunnusnro;
	}
	
	
	/**
	 * Testiohjelma
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tag tag = new Tag();
		tag.rekisteroi();
		tag.taytaTiedoilla(3);
		tag.tulosta(System.out);

	}

}
