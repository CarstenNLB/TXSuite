/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class Last 
{
    /**
     * GUID fuer Tabelle CMS_CHG (Last)
     */
    private String ivId;
    
    /**
     * GUID fuer Tabelle CMS_AST (Immobilie)
     */
    private String ivImmobilieId;
    
    /**
     * GUID fuer Tabelle CMS_CAG (Sicherheitenvereinbarung)
     */
    private String ivSicherheitenvereinbarungId;
    
    /**
     * Registernummer
     */
    private String ivRegisternummer;
    
    /**
     * Eintragungsdatum der Grundschuld
     */
    private String ivEintragungsdatum;
    
    /**
     * Rangfolge einer Sicherheitenvereinbarung in VermWert
     */
    private String ivRangfolge;
    
    /**
     * Reihenfolge eines Vermoegenswerts in Sicherheitenvereinbarung
     */
    private String ivReihenfolge;
    
    /**
     * Verfuegungsbetrag
     */
    private String ivVerfuegungsbetrag;
    
    /**
     * Waehrung des Verfuegungsbetrags
     */
    private String ivVerfuegungsbetragWaehrung;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Last(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivId = new String();
        this.ivImmobilieId = new String();
        this.ivSicherheitenvereinbarungId = new String();
        this.ivRegisternummer = new String();
        this.ivEintragungsdatum = new String();
        this.ivRangfolge = new String();
        this.ivReihenfolge = new String();
        this.ivVerfuegungsbetrag = new String();
        this.ivVerfuegungsbetragWaehrung = new String();
    }

    /**
     * Liefert die ID der Last
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * Setzt die ID der Last
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
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
     * Liefert die Registernummer
     * @return the registernummer
     */
    public String getRegisternummer() {
        return this.ivRegisternummer;
    }

    /**
     * Setzt die Registernummer
     * @param pvRegisternummer the registernummer to set
     */
    public void setRegisternummer(String pvRegisternummer) {
        this.ivRegisternummer = pvRegisternummer;
    }

    /**
     * Liefert das Eintragungsdatum
     * @return the eintragungsdatum
     */
    public String getEintragungsdatum() {
        return this.ivEintragungsdatum;
    }

    /**
     * Setzt das Eintragungsdatum
     * @param pvEintragungsdatum the eintragungsdatum to set
     */
    public void setEintragungsdatum(String pvEintragungsdatum) {
        this.ivEintragungsdatum = pvEintragungsdatum;
    }

    /**
     * Liefert die Rangfolge
     * @return the rangfolge
     */
    public String getRangfolge() {
        return this.ivRangfolge;
    }

    /**
     * Setzt die Rangfolge
     * @param pvRangfolge the rangfolge to set
     */
    public void setRangfolge(String pvRangfolge) {
        this.ivRangfolge = pvRangfolge;
    }

    /**
     * Liefert die Reihenfolge
     * @return the reihenfolge
     */
    public String getReihenfolge() {
        return this.ivReihenfolge;
    }

    /**
     * Setzt die Reihenfolge
     * @param pvReihenfolge the reihenfolge to set
     */
    public void setReihenfolge(String pvReihenfolge) {
        this.ivReihenfolge = pvReihenfolge;
    }

    /**
     * Liefert den Verfuegungsbetrag
     * @return the verfuegungsbetrag
     */
    public String getVerfuegungsbetrag() {
        return this.ivVerfuegungsbetrag;
    }

    /**
     * Setzt den Verfuegungsbetrag
     * @param pvVerfuegungsbetrag the verfuegungsbetrag to set
     */
    public void setVerfuegungsbetrag(String pvVerfuegungsbetrag) {
        this.ivVerfuegungsbetrag = pvVerfuegungsbetrag;
    }

    /**
     * Liefert die Waehrung des Verfuegungsbetrags
     * @return the verfuegungsbetragWaehrung
     */
    public String getVerfuegungsbetragWaehrung() {
        return this.ivVerfuegungsbetragWaehrung;
    }

    /**
     * Setzt die Waehrung des Verfuegungsbetrags
     * @param pvVerfuegungsbetragWaehrung the verfuegungsbetragWaehrung to set
     */
    public void setVerfuegungsbetragWaehrung(String pvVerfuegungsbetragWaehrung) {
        this.ivVerfuegungsbetragWaehrung = pvVerfuegungsbetragWaehrung;
    }

    /**
     * Zerlegt eine Zeile in die unterschiedlichen Last-Daten
     * @param pvZeile Die zu zerlegende Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseLast(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setLast(lvLfd, lvTemp);
       
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
     this.setLast(lvLfd, lvTemp);
   }
    
    /**
     * Setzt einen Wert der Last
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setLast(int pvPos, String pvWert)
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
              this.setImmobilieId(pvWert);
              break;
          case 3:
              this.setSicherheitenvereinbarungId(pvWert);
              break;
          case 4:
              this.setRegisternummer(pvWert);
              break;
          case 5:
            if (pvWert.length() == 8)
            {
              pvWert = DatumUtilities.changeDatePoints(pvWert);
            }
            this.setEintragungsdatum(pvWert);
              break;
          case 6:
              this.setRangfolge(pvWert);
              break;
          case 7:
              this.setReihenfolge(pvWert);
              break;
          case 8:
              //pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setVerfuegungsbetrag(pvWert.trim());
              break;
          case 9:
              this.setVerfuegungsbetragWaehrung(pvWert);
              break;
          default:
              ivLogger.info("Last: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt der Last in einen String
     * @return
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
        lvOut.append("ImmobilieID: " + ivImmobilieId + StringKonverter.lineSeparator);
        lvOut.append("SicherheitenvereinbarungId: " + ivSicherheitenvereinbarungId + StringKonverter.lineSeparator);
        lvOut.append("Rangfolge: " + ivRangfolge + StringKonverter.lineSeparator);
        lvOut.append("Reihenfolge: " + ivReihenfolge + StringKonverter.lineSeparator);
        lvOut.append("Verfuegungsbetrag: " + ivVerfuegungsbetrag + StringKonverter.lineSeparator);
        lvOut.append("VerfuegungsbetragWaehrung: " + ivVerfuegungsbetragWaehrung + StringKonverter.lineSeparator);

        return lvOut.toString();
    }

}
