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
public class Geschaeftspartner 
{
    /**
     * GUID fuer Tabelle CMS_CAG_BP (Geschaeftspartner) 
     */
    private String ivId;
    
    /**
     * GUID fuer Tabelle CMS_CAG (Sicherheitenvereinbarung)
     */
    private String ivSicherheitenvereinbarungId;
    
    /**
     * GUID fuer Tabelle CMS_AST (Immobilie)
     */
    private String ivImmobilieId;
    
    /**
     * Identifikationsnummer des Geschaeftspartners
     */
    private String ivKundennummer;
    
    /**
     * Geschaeftspartnerfunktion
     */
    private String ivGeschaeftspartnerfunktion;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Geschaeftspartner(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivId = new String();
        this.ivSicherheitenvereinbarungId = new String();
        this.ivImmobilieId = new String();
        this.ivKundennummer = new String();
        this.ivGeschaeftspartnerfunktion = new String();
    }

    /**
     * Liefert die ID des Geschaeftspartners
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * Setzt die ID des Geschaeftspartners
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * Liefert die ID der Sicherheitenvereinbarung
     * @return the sicherheitenvereinbarungId
     */
    public String getSicherheitenvereinbarungId() {
        return this.ivSicherheitenvereinbarungId;
    }

    /**
     * Setzt die ID der Sicherheitenvereinbarung
     * @param pvSicherheitenvereinbarungId the sicherheitenvereinbarungId to set
     */
    public void setSicherheitenvereinbarungId(String pvSicherheitenvereinbarungId) {
        this.ivSicherheitenvereinbarungId = pvSicherheitenvereinbarungId;
    }

    /**
     * Liefert die ID der Immobilie
     * @return the immobilieId
     */
    public String getImmobilieId() {
        return this.ivImmobilieId;
    }

    /**
     * Setzt die ID der Immobilie
     * @param pvImmobilieId the immobilieId to set
     */
    public void setImmobilieId(String pvImmobilieId) {
        this.ivImmobilieId = pvImmobilieId;
    }

    /**
     * Liefert die Kundennummer
     * @return the kundennummer
     */
    public String getKundennummer() {
        return this.ivKundennummer;
    }

    /**
     * Setzt die Kundennummer
     * @param pvKundennummer the kundennummer to set
     */
    public void setKundennummer(String pvKundennummer) {
        this.ivKundennummer = pvKundennummer;
    }

    /**
     * Liefert die Geschaeftspartnerfunktion
     * @return the geschaeftspartnerfunktion
     */
    public String getGeschaeftspartnerfunktion() {
        return this.ivGeschaeftspartnerfunktion;
    }

    /**
     * Setzt die Geschaeftspartnerfunktion
     * @param pvGeschaeftspartnerfunktion the geschaeftspartnerfunktion to set
     */
    public void setGeschaeftspartnerfunktion(String pvGeschaeftspartnerfunktion) {
        this.ivGeschaeftspartnerfunktion = pvGeschaeftspartnerfunktion;
    }
    
    /**
     * Zerlegt eine Zeile in die unterschiedlichen Geschaeftspartner-Daten
     * @param pvZeile die zu zerlegene Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
   public void parseGeschaeftspartner(String pvZeile, int pvQuelle)
   {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setGeschaeftspartner(lvLfd, lvTemp);
       
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
     this.setGeschaeftspartner(lvLfd, lvTemp);
   }
    
    /**
     * Setzt einen Wert des Geschaeftspartners
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setGeschaeftspartner(int pvPos, String pvWert)
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
              this.setSicherheitenvereinbarungId(pvWert);
              break;
          case 3:
              this.setImmobilieId(pvWert);
              break;
          case 4:
              this.setKundennummer(pvWert);
              break;
          case 5:
              this.setGeschaeftspartnerfunktion(pvWert);
              break;
          default:
              ivLogger.info("Geschaeftspartner: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Vorlaufsatz in einen String
     * @return
     */
    public String toString()
    {
        String lvOut = new String();

        lvOut = "Id: " + ivId + StringKonverter.lineSeparator;
        lvOut = lvOut + "SicherheitenvereinbarungID: " + ivSicherheitenvereinbarungId + StringKonverter.lineSeparator;
        lvOut = lvOut + "ImmobilieID: " + ivImmobilieId + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kundennummer: " + ivKundennummer + StringKonverter.lineSeparator;
        lvOut = lvOut + "Geschaeftspartnerfunktion: " + ivGeschaeftspartnerfunktion + StringKonverter.lineSeparator;

        return lvOut;
    }

}
