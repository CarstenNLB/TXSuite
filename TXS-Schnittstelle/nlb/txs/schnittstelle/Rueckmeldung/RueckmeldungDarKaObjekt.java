package nlb.txs.schnittstelle.Rueckmeldung;

import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

public class RueckmeldungDarKaObjekt 
{
	/**
	 * Trennzeichen
	 */
	private final char ivTrennzeichen = ';';

	/**
	 * Aktenzeichen vom Finanzgeschaeft
	 */
	private String ivAktenzeichen;
	
	/**
	 * ISIN vom Finanzgeschaeft
	 */
	private String ivISIN;
	
	/**
	 * AktivPassiv-Kennzeichen vom Slice
	 */
	private String ivAktivPassiv;
	
	/**
	 * Assigned vom Slice
	 */
	private String ivAssigned;
	
	/**
	 * Ausplatzierungsmerkmal vom Slice
	 */
	private String ivAusplatzierungsmerkmal;
	
	/**
	 * BetragBis vom Slice
	 */
	private String ivBetragBis;
	
	/**
	 * Waehrung vom Slice
	 */
	private String ivWaehrung;
	
	/**
	 * ExternerKey vom Slice
	 */
	private String ivExternerKey;
	
	/**
	 * Quelle vom Finanzgeschaeft
	 */
	private String ivQuelle;
	
	/**
	 * Originator vom Finanzgeschaeft
	 */
	private String ivOriginator;

	/**
	 * log4j-Logger
	 */
	private Logger ivLogger;

	/**
	 * Konstruktor fuer die Initialiserung mit leeren Strings
	 */
	public RueckmeldungDarKaObjekt(Logger pvLogger)
	{
		this.ivLogger = pvLogger;
		this.ivAktenzeichen = new String();
		this.ivISIN = new String();
		this.ivAktivPassiv = new String();
		this.ivAssigned = new String();
		this.ivAusplatzierungsmerkmal = new String();
		this.ivBetragBis = new String();
		this.ivWaehrung = new String();
		this.ivExternerKey = new String();
		this.ivQuelle = new String();
		this.ivOriginator = new String();
	}

	/**
	 * Liefert das Aktenzeichen
	 * @return
	 */
	public String getAktenzeichen() 
	{
		return ivAktenzeichen;
	}

	/**
	 * Setzt das Aktenzeichen
	 * @param pvAktenzeichen
	 */
	public void setAktenzeichen(String pvAktenzeichen) 
	{
		this.ivAktenzeichen = pvAktenzeichen;
	}

	/**
	 * Liefert die ISIN
	 * @return
	 */
	public String getISIN() 
	{
		return ivISIN;
	}

	/**
	 * Setzt die ISIN
	 * @param pvISIN
	 */
	public void setISIN(String pvISIN) 
	{
		this.ivISIN = pvISIN;
	}

	/**
	 * Liefert das AktivPassiv-Kennzeichen
	 * @return
	 */
	public String getAktivPassiv() 
	{
		return ivAktivPassiv;
	}

	/**
	 * Setzt das AktivPassiv-Kennzeichen
	 * @param pvAktivPassiv
	 */
	public void setAktivPassiv(String pvAktivPassiv) 
	{
		this.ivAktivPassiv = pvAktivPassiv;
	}

	/**
	 * Liefert das Assigned-Kennzeichen
	 * @return
	 */
	public String getAssigned() 
	{
		return ivAssigned;
	}

	/**
	 * Setzt das Assigned-Kennzeichen
	 * @param pvAssigned
	 */
	public void setAssigned(String pvAssigned) {
		this.ivAssigned = pvAssigned;
	}

	/**
	 * Liefert das Ausplatzierungsmerkmal
	 * @return
	 */
	public String getAusplatzierungsmerkmal() 
	{
		return ivAusplatzierungsmerkmal;
	}

	/**
	 * Setzt das Ausplatzierungsmerkmal
	 * @param pvAusplatzierungsmerkmal
	 */
	public void setAusplatzierungsmerkmal(String pvAusplatzierungsmerkmal) 
	{
		this.ivAusplatzierungsmerkmal = pvAusplatzierungsmerkmal;
	}

	/**
	 * Liefert den BetragBis
	 * @return
	 */
	public String getBetragBis() 
	{
		return ivBetragBis;
	}

	/**
	 * Setzt den BetragBis
	 * @param pvBetragBis
	 */
	public void setBetragBis(String pvBetragBis) 
	{
		this.ivBetragBis = pvBetragBis;
	}

	/**
	 * Liefert die Waehrung
	 * @return
	 */
	public String getWaehrung() 
	{
		return ivWaehrung;
	}

	/**
	 * Setzt die Waehrung
	 * @param pvWaehrung
	 */
	public void setWaehrung(String pvWaehrung) 
	{
		this.ivWaehrung = pvWaehrung;
	}

	/**
	 * Liefert den ExternerKey
	 * @return
	 */
	public String getExternerKey() 
	{
		return ivExternerKey;
	}

	/**
	 * Setzt den ExternerKey
	 * @param pvExternerKey
	 */
	public void setExternerKey(String pvExternerKey) 
	{
		this.ivExternerKey = pvExternerKey;
	}

	/**
	 * Liefert das Quellsystem
	 * @return
	 */
	public String getQuelle() 
	{
		return ivQuelle;
	}

	/**
	 * Setzt das Quellsystem
	 * @param pvQuelle
	 */
	public void setQuelle(String pvQuelle) 
	{
		this.ivQuelle = pvQuelle;
	}

	/**
	 * Liefert den Originator
	 * @return
	 */
	public String getOriginator() 
	{
		return ivOriginator;
	}

	/**
	 * Setzt den Originator
	 * @param pvOriginator
	 */
	public void setOriginator(String pvOriginator) 
	{
		this.ivOriginator = pvOriginator;
	}

	/**
     * Zerlegt eine Zeile des RueckmeldungDarKaObjekts 
     * @param pvZeile die zu zerlegende Zeile
     */
	public void parseRueckmeldungDarKaObjekt(String pvZeile)
	{
      StringBuilder lvTemp = new StringBuilder(); // arbeitsbereich/zwischenspeicher feld
      int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
      boolean lvDatenStart = false;
     
      // steuerung/iteration eingabesatz
      for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
      {    	
    	  // wenn Trennzeichen erkannt
    	  if (pvZeile.charAt(lvZzStr) == ivTrennzeichen)
    	  {
    		    this.setRueckmeldungDarKaObjekt(lvLfd, lvTemp.toString());
            lvLfd++;                  // naechste Feldnummer
            // Zwischenbuffer loeschen
            lvTemp = new StringBuilder();
    	  }
    	  else
    	  {
    		
        	if (pvZeile.charAt(lvZzStr) == '"')
        	{
        		lvDatenStart = !lvDatenStart;
         	}
        	else
        	{
        		if (lvDatenStart)
        		{
        			if ((int)pvZeile.charAt(lvZzStr) != 0)
        			{
        				// uebernehmen byte aus eingabesatzposition
        				lvTemp.append(pvZeile.charAt(lvZzStr));
        			}
        		}
        	}
    	  }
      } // ende for	
      
      // Das letzte Feld muss auch noch gesetzt werden
      this.setRueckmeldungDarKaObjekt(lvLfd, lvTemp.toString());
	}
    
    /**
     * Setzt einen Wert des RueckmeldungDarKaObjekts
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setRueckmeldungDarKaObjekt(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
          case 0:
              this.setAktenzeichen(pvWert);
              break;
          case 1:
              this.setISIN(pvWert);
              break;
          case 2:
              this.setAktivPassiv(pvWert);
              break;
          case 3:
              this.setAssigned(pvWert);
              break;
          case 4:
        	  this.setAusplatzierungsmerkmal(pvWert);
        	  break;
          case 5:
        	  this.setBetragBis(pvWert);
        	  break;
          case 6:
        	  this.setWaehrung(pvWert);
        	  break;
          case 7:
        	  this.setExternerKey(pvWert);
        	  break;
          case 8:
        	  this.setQuelle(pvWert);
        	  break;
          case 9:
        	  if (pvWert.contains("NORD/LB"))
        	  {
        		  pvWert = "25050000";
        	  }
        	  if (pvWert.contains("Bremer"))
        	  {
        		 pvWert = "29050000"; 
        	  }
        	  this.setOriginator(pvWert);
        	  break;
          default:
              ivLogger.error("RueckmeldungDarKaObjekts: undefiniert - Position: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Liefert einen String mit den Inhalten der RueckmeldungDarKaObjekte zurueck
     * @return 
     */
    public String toString()
    {
        StringBuilder lvHelpString = new StringBuilder();
        
        lvHelpString.append("Aktenzeichen: " + this.ivAktenzeichen + StringKonverter.lineSeparator);
        lvHelpString.append("ISIN: " + this.ivISIN + StringKonverter.lineSeparator);
        lvHelpString.append("AktivPassiv: " + this.ivAktivPassiv + StringKonverter.lineSeparator);
        lvHelpString.append("Assigned: " + this.ivAssigned + StringKonverter.lineSeparator);
        lvHelpString.append("Ausplatzierungsmerkmal: " + this.ivAusplatzierungsmerkmal + StringKonverter.lineSeparator);
        lvHelpString.append("BetragBis: " + this.ivBetragBis + StringKonverter.lineSeparator);
        lvHelpString.append("Waehrung: " + this.ivWaehrung + StringKonverter.lineSeparator);
        lvHelpString.append("ExternerKey: " + this.ivExternerKey + StringKonverter.lineSeparator);
        lvHelpString.append("Quelle: " + this.ivQuelle + StringKonverter.lineSeparator);
        lvHelpString.append("Originator: " + this.ivOriginator + StringKonverter.lineSeparator);
        
        return lvHelpString.toString(); 
    }

}
