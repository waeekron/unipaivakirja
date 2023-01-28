package kanta;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import unipaivakirja.Uni;
/**
 * Luokka jonka avulla voidaan järjestää unia.
 * @author waeekron 
 * @version 20.7.2021
 *
 */
public class UniComparator implements Comparator<Uni> {

	private Collection<Uni> collection;
	private String orderBy = "Paivamaara";
	
	
	/**
	 * Konstruktori
	 * @param sortOrder Map jossa on päivämääriä merrkijonoina ja niiden indeksit
	 */
	public UniComparator(Collection<Uni> sortOrder) {
		this.collection = sortOrder;
	}
	
	public UniComparator(Collection<Uni> collection, String orderBy) {
		this.collection = collection;
		this.orderBy = orderBy;
	}
	

	/**
	 * Vertaa kahta uni oliota orderBy oliomuuttu
	 */
	@Override
	public int compare(Uni o1, Uni o2) {

		if (this.orderBy.equals("Kesto")) {
			System.out.println("Kesto vertailussa");
			Integer k1 = o1.getKesto();
			Integer k2 = o2.getKesto();
			return k1.compareTo(k2);
			
		}
		
		if (this.orderBy.equals("Laatu")) {
			Integer l1 = o1.getArvio();
			Integer l2  = o2.getArvio();
			return l1.compareTo(l2);
		}
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date paivamaara1= null;
		try {
			paivamaara1 = sdformat.parse(o1.getPaivamaara());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date paivamaara2 = null;
		try {
			paivamaara2= sdformat.parse(o2.getPaivamaara());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paivamaara2.compareTo(paivamaara1);
	}

}
