/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class Grundbuchverweis 
{
    /**
     * GUID fuer Tabelle CMS_CHG_LR (Grundbuchverweis)
     */
    private String ivId;

    /**
     * GUID fuer Tabelle CMS_CHG (Last)
     */
    private String ivLastId;
    
    /**
     * GUID fuer Tabelle CMS_LR_DE_A (Grundbuchblatt)
     */
    private String ivGrundbuchblattId;
    
    /**
     * Lfd. Nr. Abt. III
     */
    private String ivLaufendeNrAbt3;
    
    /**
     * Loeschkennzeichen
     */
    private String ivLoeschkennzeichen;
    
    /**
     * GUID fuer Tabelle CMS_CAG (Sicherheitenvereinbarung)
     */
    private String ivSicherheitenvereinbarungId;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Grundbuchverweis(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivId = new String();
        this.ivLastId = new String();
        this.ivGrundbuchblattId = new String();
        this.ivLaufendeNrAbt3 = new String();
        this.ivLoeschkennzeichen = new String();
        this.ivSicherheitenvereinbarungId = new String();
    }

    /**
     * Liefert die ID des Grundbuchverweises
     * @return the id
     */
    public String getId() 
    {
        return this.ivId;
    }

    /**
     * Setzt die ID des Grundbuchverweises
     * @param pvId the id to set
     */
    public void setId(String pvId) 
    {
        this.ivId = pvId;
    }

    /**
     * Liefert die ID der Last
     * @return the lastId
     */
    public String getLastId() 
    {
        return this.ivLastId;
    }

    /**
     * Setzt die ID der Last
     * @param pvLastId the lastId to set
     */
    public void setLastId(String pvLastId) 
    {
        this.ivLastId = pvLastId;
    }

    /**
     * Liefert die ID des Grundbuchblattes
     * @return the grundbuchblattId
     */
    public String getGrundbuchblattId() 
    {
        return this.ivGrundbuchblattId;
    }

    /**
     * Setzt die ID des Grundbuchblattes
     * @param pvGrundbuchblattId the grundbuchblattId to set
     */
    public void setGrundbuchblattId(String pvGrundbuchblattId) 
    {
        this.ivGrundbuchblattId = pvGrundbuchblattId;
    }

    /**
     * Liefert die Laufende Nummer der Abteilung 3
     * @return the laufendeNrAbt3
     */
    public String getLaufendeNrAbt3() 
    {
        return this.ivLaufendeNrAbt3;
    }

    /**
     * Setzt die Laufende Nummer der Abteilung 3
     * @param pvLaufendeNrAbt3 the laufendeNrAbt3 to set
     */
    public void setLaufendeNrAbt3(String pvLaufendeNrAbt3) 
    {
        this.ivLaufendeNrAbt3 = pvLaufendeNrAbt3;
    }

    /**
     * Liefert das Loeschkennzeichen
     * @return
     */
    public String getLoeschkennzeichen() 
    {
		return ivLoeschkennzeichen;
	}

    /**
     * Setzt das Loeschkennzeichen
     * @param ivLoeschkennzeichen
     */
	  public void setLoeschkennzeichen(String ivLoeschkennzeichen)
	  {
	 	  this.ivLoeschkennzeichen = ivLoeschkennzeichen;
	  }

	  /**
     * Liefert die ID der Sicherheitenvereinbarung
     * @return the sicherheitenvereinbarungId
     */
    public String getSicherheitenvereinbarungId() 
    {
        return this.ivSicherheitenvereinbarungId;
    }

    /**
     * Setzt die ID der Sicherheitenvereinbarung
     * @param pvSicherheitenvereinbarungId the sicherheitenvereinbarungId to set
     */
    public void setSicherheitenvereinbarungId(String pvSicherheitenvereinbarungId) 
    {
        this.ivSicherheitenvereinbarungId = pvSicherheitenvereinbarungId;
    }
    
    /**
     * Zerlegt eine Zeile in die unterschiedlichen Grundbuchverweis-Daten
     * @param pvZeile die zu zerlegende Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseGrundbuchverweis(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setGrundbuchverweis(lvLfd, lvTemp);
       
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
     
     // Letzte Feld auch noch setzen
     this.setGrundbuchverweis(lvLfd, lvTemp);
   }

  /**
   * Setzt einen Wert des Grundbuchverweises
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setGrundbuchverweis(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        // Satzart
        break;
      case 1:
        this.setId(pvWert);
        break;
      case 2:
        this.setLastId(pvWert);
        break;
      case 3:
        this.setGrundbuchblattId(pvWert);
        break;
      case 4:
        this.setLaufendeNrAbt3(pvWert);
        break;
      case 5:
        this.setLoeschkennzeichen(pvWert);
        break;
      case 6:
        this.setSicherheitenvereinbarungId(pvWert);
        break;
      default:
        ivLogger.info("Grundbuchverweis: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
    }
  }

  /**
     * Setzt einen Wert des Grundbuchverweises - Variante SPOT
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setGrundbuchverweisSPOT(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
          case 0:
              // Satzart
              break;
          case 1:
              this.setId(pvWert);
              break;
          case 2:
              this.setLastId(pvWert);
              break;
          case 3:
              this.setGrundbuchblattId(pvWert);
              break;
          case 4:
              this.setLaufendeNrAbt3(pvWert);
              break;
          //case 5:
        	//  this.setLoeschkennzeichen(pvWert);
        	//  break;
          case 5:
              this.setSicherheitenvereinbarungId(pvWert);
              break;
          default:
              ivLogger.info("Grundbuchverweis: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Grundbuchverweises in einen String
     * @return
     */
    public String toString()
    {
        StringBuffer lvOut = new StringBuffer();

        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
        lvOut.append("LastId: " + this.ivLastId + StringKonverter.lineSeparator);
        lvOut.append("GrundbuchblattId: " + this.ivGrundbuchblattId + StringKonverter.lineSeparator);
        lvOut.append("Lfd. Nr. Abt. III: " + this.ivLaufendeNrAbt3 + StringKonverter.lineSeparator);
        lvOut.append("Loeschkennzeichen: " + this.ivLoeschkennzeichen + StringKonverter.lineSeparator);
        lvOut.append("SicherheitenvereinbarungId: " + this.ivSicherheitenvereinbarungId + StringKonverter.lineSeparator);

        return lvOut.toString();
    }

}
