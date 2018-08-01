package nlb.txs.schnittstelle.SAPCMS;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import nlb.txs.schnittstelle.SAPCMS.Entities.Sicherungsumfang;

import org.apache.log4j.Logger;

public class DeepSeaLogging 
{
	/**
	 * 
	 */
	private SAPCMS_Neu ivSAPCMS;
	
	/**
	 * 
	 */
	private Logger ivLogger;
	
	/**
	 * Konstruktor
	 */
	public DeepSeaLogging(SAPCMS_Neu pvSAPCMS, Logger pvLogger)
	{
		this.ivSAPCMS = pvSAPCMS;
		this.ivLogger = pvLogger;
		startLogging();
	}
	
	/**
	 * Start des Loggings
	 */
	private void startLogging()
	{
		ivLogger.info("Start DeepSea Logging");
        
		for (int x = 0; x < ivSAPCMS.getListeSicherungsumfang().size(); x++)
        {
		  Collection<LinkedList<Sicherungsumfang>> lvHelpColListeSicherungsumfang = ivSAPCMS.getListeSicherungsumfang().values();
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
