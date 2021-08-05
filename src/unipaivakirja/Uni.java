package unipaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;



/**
 *  @author walter
 *  @version 18/05/2021
 *  
 * +--------------------------------------+--------------------------------------+
| Luokan nimi:Uni                      | Avustajat:                           |
+--------------------------------------+--------------------------------------+
| Vastuualueet:                        |                                      |
|                                      |                                      |
| - Tietää unen kentät: dream_id,      |                                      |
| user_id, location, duration jne.     |                                      |
|                                      |                                      |
| - Osaa verrata itseänsä muihin uniin |                                      |
| - Osaa muutta itsensä merkkijonoksi  |                                      |
+--------------------------------------+--------------------------------------+
 */
public class Uni implements Cloneable{

	
	private int unenTunnusNro;
	private String unenSisalto = "";
	private int arvio;
	private int kesto;
	private String paivamaara = java.time.LocalDate.now().toString(); 
	
	private static int seuraavaNRO = 1;
	
	
	/*
	 * Tyhjä
	 */
	public Uni() {
		//
		
	}
	
	
	/**
	 * Konstruktori kaikille kentille
	 * @param loc unen kuvauksen sijainti
	 * @param rate unen arvio
	 * @param duration unen kesto
	 * @param date unen päivämäärä
	 */
	public Uni(String loc, int rate, int dur, String d, int dID) {
		this.unenSisalto = loc;
		this.arvio = rate;
		this.kesto = dur;
		this.paivamaara = d;
		this.unenTunnusNro = dID;
	}
	
	
	/**
	 * Täyttää unen kentät testiarvoilla
	 */
	public void taytaTiedoilla() {
		Random arpa = new Random();
		
		unenSisalto = "Sisältö...";
		arvio = arpa.nextInt(5) + 1;
		kesto = 8;
		paivamaara = "01.01.2021";
	}
	
	
	/**
	 * rekisteröi unelle tunnusnumeron
	 * @example
	 * <pre name="test">
	 * Uni u1 = new Uni();
	 * u1.getDreamID() === 0;
	 * u1.rekisteroi();
	 * Uni u2 = new Uni();
	 * u2.rekisteroi();
	 * int n1 = u1.getDreamID();
	 * int n2 = u2.getDreamID();
	 * n1 = n2-1;
	 * </pre>	
	 */
	public void rekisteroi() {
		this.unenTunnusNro = seuraavaNRO;
		seuraavaNRO++;
	}
	
	
	/**
	 * Palauttaa unen tunnusnumeron
	 * @return kokonaislukuna dreamID
	 */
	public int getDreamID() {
		return this.unenTunnusNro;
	}
	
	
	/**
	 * Palauttaa päivämäärän
	 * @return
	 */
	public String getDreamDate() {
		return this.paivamaara.toString();
	}
	
	
	/**
	 * @return the unenTunnusNro
	 */
	public int getUnenTunnusNro() {
		return unenTunnusNro;
	}


	/**
	 * @param unenTunnusNro the unenTunnusNro to set
	 */
	public void setUnenTunnusNro(int nro) {
		this.unenTunnusNro = nro;
		if (unenTunnusNro >= seuraavaNRO) seuraavaNRO = unenTunnusNro + 1;
	}


	/**
	 * @return the tiedostonSijainti
	 */
	public String getUnenSisalto() {
		return unenSisalto;
	}


	/**
	 * @param tiedostonSijainti the tiedostonSijainti to set
	 */
	public void setUnenSisalto(String tiedostonSijainti) {
		this.unenSisalto = tiedostonSijainti;
	}


	/**
	 * @return the arvio
	 */
	public int getArvio() {
		return arvio;
	}


	/**
	 * @param arvio the arvio to set
	 */
	public void setArvio(int arvio) {
		this.arvio = arvio;
	}


	/**
	 * @return the kesto
	 */
	public int getKesto() {
		return kesto;
	}


	/**
	 * @param kesto the kesto to set
	 */
	public void setKesto(int kesto) {
		this.kesto = kesto;
	}


	/**
	 * @return the paivamaara
	 */
	public String getPaivamaara() {
		return paivamaara;
	}


	/**
	 * @param paivamaara the paivamaara to set
	 */
	public void setPaivamaara(String paivamaara) {
		this.paivamaara = paivamaara;
	}


	/**
	 * @return the seuraavaNRO
	 */
	public static int getSeuraavaNRO() {
		return seuraavaNRO;
	}


	/**
	 * @param seuraavaNRO the seuraavaNRO to set
	 */
	public static void setSeuraavaNRO(int seuraavaNRO) {
		Uni.seuraavaNRO = seuraavaNRO;
	}
	
	
	/**
	 * Selvittää unen tiedot csv merkkijonosta ja huolta seuraavaNRO:n arvosta
	 * @param s rivi josta unen tiedot otetaan
	 * @example
	 * <pre name="test">
	 * Uni uni = new Uni();
	 * uni.parse("1|/polku|8|01.01.2021|5");
	 * uni.getArvio() === 5;
	 * </pre>
	 */
	public void parse(String s) {
		var sb = new StringBuilder(s);
		setUnenTunnusNro(Mjonot.erota(sb, '|', unenTunnusNro));
		unenSisalto = (Mjonot.erota(sb, '|', unenSisalto));
		kesto = (Mjonot.erota(sb, '|', kesto));
		paivamaara = (Mjonot.erota(sb, '|', paivamaara));
		arvio = (Mjonot.erota(sb, '|', arvio));
	}


	/**
	 * Tulostaa unen tiedot
	 * @param out tietovirta johon tulostetaan
	 **/
	public void tulosta( PrintStream out) {
		out.println(String.format("%d", unenTunnusNro) + " " + paivamaara.toString());
		out.println("Kesto: " + String.format("%d", kesto) + ", arvio: " + arvio );
		out.println("Unen sisältö: " + unenSisalto);
	}
	
	
	/**
	 * Tulostetaan unen tiedot 
	 * @param os tietovirta johon tulostetaan
	 */
	public void tulosta(OutputStream os) {
		tulosta(new PrintStream(os));
	}
	
	
	/**
	 * @return Unen sisältö
	 */
	public String tulostaSisalto() {
		return this.unenSisalto;
	}
	
	
	/**
	 * Palauttaa unen tiedot merkkijonona
	 * @return Uni csv merkkijonona
	 * @example
	 * <pre name="test">
	 * Uni u = new Uni();
	 * Uni u2 = new Uni();
	 * u.parse("   1|uni1.txt|8|13.1.22|2");
	 * u.toString().startsWith("1|uni1.txt") === true;
	 * </pre> 
	 */
	@Override
	public String toString() { 
		return "" +
				this.unenTunnusNro + "|" +
				this.unenSisalto + "|" + 
				this.kesto + "|" +
				this.paivamaara + "|" +
				this.arvio;
				
	}
	
	
	/**
	 * @return Uni olion klooni
	 */
	@Override
	public Uni clone() throws CloneNotSupportedException {
		return (Uni) super.clone();
	}
	
	
	/**
	 * Testiohjelma
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Uni uni = new Uni();
		uni.rekisteroi();
		uni.taytaTiedoilla();
		uni.tulosta(System.out);
		
		System.out.println();
		
		Uni uni2 = new Uni();
		uni2.rekisteroi();
		uni2.taytaTiedoilla();
		uni2.tulosta(System.out);
	}
}
