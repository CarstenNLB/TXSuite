package nlb.txs.schnittstelle.Sicherheiten;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import nlb.txs.schnittstelle.Sicherheiten.Entities.Sicherungsumfang;

import org.apache.log4j.Logger;

/**
 * Die Klasse wird nicht mehr benoetigt.
 * Die Klasse wurde erstellt, damit Daten fuer das Projekt DeepSea geloggt/aufgezeichnet werden konnten.
 */
@Deprecated
public class DeepSeaLogging
{
	/**
	 * 
	 */
	private SicherheitenDaten ivSicherheitenDaten;
	
	/**
	 * 
	 */
	private Logger ivLogger;
	
	/**
	 * Konstruktor
	 */
	public DeepSeaLogging(SicherheitenDaten pvSicherheitenDaten, Logger pvLogger)
	{
		this.ivSicherheitenDaten = pvSicherheitenDaten;
		this.ivLogger = pvLogger;
		startLogging();
	}
	
	/**
	 * Start des Loggings
	 */
	private void startLogging()
	{
		ivLogger.info("Start DeepSea Logging");
        
		for (int x = 0; x < ivSicherheitenDaten.getListeSicherungsumfang().size(); x++)
        {
		  Collection<LinkedList<Sicherungsumfang>> lvHelpColListeSicherungsumfang = ivSicherheitenDaten.getListeSicherungsumfang().values();
          Iterator<LinkedList<Sicherungsumfang>> lvHelpIterListeSicherungsumfang = lvHelpColListeSicherungsumfang.iterator();
		  while (lvHelpIterListeSicherungsumfang.hasNext())
		  {
			  LinkedList<Sicherungsumfang> lvHelpListeSicherungsumfang = lvHelpIterListeSicherungsumfang.next();
			  if (lvHelpListeSicherungsumfang != null)
			  {
				  for (int z = 0; z < lvHelpListeSicherungsumfang.size(); z++)
				  {
					  Sicherungsumfang lvShum = lvHelpListeSicherungsumfang.get(z);
					  //System.out.println("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
					  ivLogger.info("Sicherungsumfang: " + lvShum.getSicherheitenvereinbarungId());
				  }
			  }
		  }
        } 
		
		ivLogger.info("Ende DeepSea Logging");
	}
	
	
}
