/*******************************************************************************
 * Copyright (c) 2017 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Kunde;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

public class KundeRequestVorlaufsatz 
{
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Systemname
     */
    private String ivSystemname;
    
    /**
     * Systembenutzer
     */
    private String ivSystembenutzer;
    
    /**
     * Aktuelles Datum/Erstellungsdatum
     */
    private String ivDatum;

    /**
     * Aktuelle Zeit/Erstellungszeit
     */
    private String ivZeit;
    
    /**
     * Zielsystem
     */
    private String ivZielsystem;
        
    /**
     * log4j-Logger
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor 
     */
    public KundeRequestVorlaufsatz(Logger pvLogger)
    {
    	this.ivLogger = pvLogger;
    	ivInstitutsnummer = new String();
    	ivSystemname = new String();
    	ivSystembenutzer = new String();
    	ivDatum = new String();
    	ivZeit = new String();
    	ivZielsystem = new String();
    }
    
    /**
     * Konstruktor - mit Parametern
     * @param pvInstitutsnummer
     * @param pvZielsystem
     */
    public KundeRequestVorlaufsatz(String pvInstitutsnummer, String pvZielsystem)
    {
    	this.ivInstitutsnummer = pvInstitutsnummer;
    	if (pvInstitutsnummer.equals("NLB"))
    	{
    		this.ivInstitutsnummer = "009";
    	}
    	if (pvInstitutsnummer.equals("BLB"))
    	{
    		this.ivInstitutsnummer = "004";
    	}
    	
        this.ivSystemname = "TXS_PROD"; // Konstant 'TXS_PROD'
        this.ivSystembenutzer = System.getProperty("user.name");
        DateFormat lvDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date lvDate = new Date();
        this.ivDatum = lvDateFormat.format(lvDate);
        lvDateFormat = new SimpleDateFormat("HH:mm:ss");
        this.ivZeit = lvDateFormat.format(lvDate);
        this.ivZielsystem = pvZielsystem;
    }

    /**
     * Liefert die Institutsnummer
     * @return the institutsnummer
     */
    public String getInstitutsnummer() {
        return this.ivInstitutsnummer;
    }

    /**
     * Setzt die Institutsnummer
     * @param pvInstitutsnummer the institutsnummer to set
     */
    public void setInstitutsnummer(String pvInstitutsnummer) {
        this.ivInstitutsnummer = pvInstitutsnummer;
    }

     
    /**
     * Liefert den Systemnamen
	 * @return the ivSystemname
	 */
	public String getSystemname() 
	{
		return ivSystemname;
	}

	/**
	 * Setzt den Systemnamen
	 * @param pvSystemname the ivSystemname to set
	 */
	public void setSystemname(String pvSystemname) 
	{
		this.ivSystemname = pvSystemname;
	}

	/**
	 * Liefert den Systembenutzer
	 * @return the ivSystembenutzer
	 */
	public String getSystembenutzer() 
	{
		return ivSystembenutzer;
	}

	/**
	 * Setzt den Systembenutzer
	 * @param pvSystembenutzer the ivSystembenutzer to set
	 */
	public void setSystembenutzer(String pvSystembenutzer) 
	{
		this.ivSystembenutzer = pvSystembenutzer;
	}

	/**
	 * Liefert das (Erstellungs)Datum
	 * @return the ivDatum
	 */
	public String getDatum() 
	{
		return ivDatum;
	}

	/**
	 * Setzt das (Erstellungs)Datum
	 * @param pvDatum the ivDatum to set
	 */
	public void setDatum(String pvDatum) 
	{
		this.ivDatum = pvDatum;
	}

	/**
	 * Liefert die (Erstellungs)Zeit
	 * @return the ivZeit
	 */
	public String getZeit() 
	{
		return ivZeit;
	}

	/**
	 * Setzt die (Erstellungs)Zeit
	 * @param pvZeit the ivZeit to set
	 */
	public void setZeit(String pvZeit) 
	{
		this.ivZeit = pvZeit;
	}

	/**
	 * Liefert das Zuelsystem
	 * @return the ivZielsystem
	 */
	public String getZielsystem() 
	{
		return ivZielsystem;
	}

	/**
	 * Setzt das Zielsystem
	 * @param pvZielsystem the ivZielsystem to set
	 */
	public void setZielsystem(String pvZielsystem) 
	{
		this.ivZielsystem = pvZielsystem;
	}

	/**
     * Zerlegt die Zeichenkette in einzelne Felder
     * @param pvZeile 
     * @return 
     */
   public boolean parseVorlaufsatz(String pvZeile)
   {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
     int    lvZzStr=0;              // pointer fuer satzbereich
     
     // steuerung/iteration eingabesatz
     for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn semikolon erkannt
       if (pvZeile.charAt(lvZzStr) == ';')
       {
         this.setVorlaufsatz(lvLfd, lvTemp);
       
         lvLfd++;                  // naechste Feldnummer
       
         // loeschen Zwischenbuffer
         lvTemp = new String();

       }
       else
       {
           // uebernehmen byte aus eingabesatzposition
           lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
       }
     } // ende for
     
     return true;
   }
    
    /**
     * Setzt einen Wert des Vorlaufsatzes
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setVorlaufsatz(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
          case 0:
              this.setInstitutsnummer(pvWert);
              break;
          case 1:
              this.setSystemname(pvWert);
              break;
          case 2:
              this.setSystembenutzer(pvWert);
              break;
          case 3:
              this.setDatum(pvWert);
              break;
          case 4:
        	  this.setZeit(pvWert);
        	  break;
          case 5:
        	  this.setZielsystem(pvWert);
        	  break;
          default:
              ivLogger.error("KundeRequestVorlaufsatz: undefiniert");
        }
    }
    
    /**
     * Liefert einen String mit den Inhalten der Instanz zurueck
     * @return 
     */
    public String toString()
    {
    	StringBuilder lvHelpString = new StringBuilder();
    	
    	lvHelpString.append(ivInstitutsnummer + ";");
    	lvHelpString.append(ivSystemname + ";");
    	lvHelpString.append(ivSystembenutzer + ";");
    	lvHelpString.append(ivDatum + ";");
    	lvHelpString.append(ivZeit + ";");
    	lvHelpString.append(ivZielsystem + ";" + StringKonverter.lineSeparator);
    	
    	return lvHelpString.toString();
    }
    
}
