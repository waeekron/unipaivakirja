package kanta;


import java.util.Comparator;
import java.util.Map;

import unipaivakirja.Uni;
/**
 * Vertaa unia parametrina tuodun mapin perusteella
 * @author waeekron 
 * @version 20.7.2021
 *
 */
public class UniPaivamaaraComparator implements Comparator<Uni> {

	private Map<String, Integer> sortOrder;
	
	
	/**
	 * Konstruktori
	 * @param sortOrder Map jossa on päivämääriä merrkijonoina ja niiden indeksit
	 */
	public UniPaivamaaraComparator(Map<String, Integer> sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	/**
	 * Vertaa kahta uni oliota
	 */
	@Override
	public int compare(Uni o1, Uni o2) {
		
		Integer paivamaaraPaikka1 = sortOrder.get(o1.getPaivamaara());
		if (paivamaaraPaikka1 == null) {
			throw new IllegalArgumentException("Vääränlainen päivämäärä " + o1.getPaivamaara());
		}
		Integer paivamaaraPaikka2 = sortOrder.get(o2.getPaivamaara());
		if (paivamaaraPaikka2 == null) {
			throw new IllegalArgumentException("Vääränlainen päivämäärä " + o2.getPaivamaara());
		}
		return paivamaaraPaikka2.compareTo(paivamaaraPaikka1);
	}

}
