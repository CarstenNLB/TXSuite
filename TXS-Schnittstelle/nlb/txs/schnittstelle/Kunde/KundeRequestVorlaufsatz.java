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
     * Konstruktor - Setzt den Logger und Initialisierung mit leeren Strings
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
     * @param pvInstitutsnummer Institutsnummer
     * @param pvZielsystem Zielsystem
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
     * @return Die Institutsnummer
     */
    public String getInstitutsnummer() {
        return this.ivInstitutsnummer;
    }

    /**
     * Setzt die Institutsnummer
     * @param pvInstitutsnummer Die zu setzende Institutsnummer
     */
    public void setInstitutsnummer(String pvInstitutsnummer) {
        this.ivInstitutsnummer = pvInstitutsnummer;
    }

     
    /**
     * Liefert den Systemnamen
	 * @return Der Systemname
	 */
	public String getSystemname() 
	{
		return ivSystemname;
	}

	/**
	 * Setzt den Systemnamen
	 * @param pvSystemname Der zu setzende Systemname
	 */
	public void setSystemname(String pvSystemname) 
	{
		this.ivSystemname = pvSystemname;
	}

	/**
	 * Liefert den Systembenutzer
	 * @return Der Systembenutzer
	 */
	public String getSystembenutzer() 
	{
		return ivSystembenutzer;
	}

	/**
	 * Setzt den Systembenutzer
	 * @param pvSystembenutzer Der zu setzende Systembenutzer
	 */
	public void setSystembenutzer(String pvSystembenutzer) 
	{
		this.ivSystembenutzer = pvSystembenutzer;
	}

	/**
	 * Liefert das (Erstellungs)Datum
	 * @return Das (Erstellungs)Datum
	 */
	public String getDatum() 
	{
		return ivDatum;
	}

	/**
	 * Setzt das (Erstellungs)Datum
	 * @param pvDatum Das zu setzende (Erstellungs)Datum
	 */
	public void setDatum(String pvDatum) 
	{
		this.ivDatum = pvDatum;
	}

	/**
	 * Liefert die (Erstellungs)Zeit
	 * @return Die (Erstellungs)Zeit
	 */
	public String getZeit() 
	{
		return ivZeit;
	}

	/**
	 * Setzt die (Erstellungs)Zeit
	 * @param pvZeit Die zu setzende (Erstellungs)Zeit
	 */
	public void setZeit(String pvZeit) 
	{
		this.ivZeit = pvZeit;
	}

	/**
	 * Liefert das Zuelsystem
	 * @return Das Zielsystem
	 */
	public String getZielsystem() 
	{
		return ivZielsystem;
	}

	/**
	 * Setzt das Zielsystem
	 * @param pvZielsystem Das zu setzende Zielsystem
	 */
	public void setZielsystem(String pvZielsystem) 
	{
		this.ivZielsystem = pvZielsystem;
	}

	/**
	 * Zerlegt die Zeichenkette in einzelne Felder
	 * @param pvZeile Die zu zerlegende Zeile/Zeichenkette
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
     * Liefert einen String mit den Inhalten aller Instanzvariablen zurueck
     * @return String mit allen Inhalten der Instanzvariablen
     */
    public String toString()
    {
    	StringBuilder lvHelpString = new StringBuilder();
    	
    	lvHelpString.append(ivInstitutsnummer);
    	lvHelpString.append(";");
    	lvHelpString.append(ivSystemname);
    	lvHelpString.append(";");
    	lvHelpString.append(ivSystembenutzer);
    	lvHelpString.append(";");
    	lvHelpString.append(ivDatum);
    	lvHelpString.append(";");
    	lvHelpString.append(ivZeit);
    	lvHelpString.append(";");
    	lvHelpString.append(ivZielsystem);
    	lvHelpString.append(";");
    	lvHelpString.append(StringKonverter.lineSeparator);
    	
    	return lvHelpString.toString();
    }
    
}
