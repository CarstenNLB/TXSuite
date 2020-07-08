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
public class Grundstueck 
{
    /**
     * GUID fuer Tabelle CMS_LR_DE_B (Grundstueck)
     */
    private String ivId;
    
    /**
     * GUID fuer Tabelle CMS_LR_DE_A (Grundbuchblatt)
     */
    private String ivGrundbuchblattId;
    
    /**
     * Laufende Nummer des Grundstuecks
     */
    private String ivLaufendeNummer;
    
    /**
     * Lage/Gemarkung
     */
    private String ivGemarkung;
    
    /**
     * Groesse
     */
    private String ivGroesse;
    
    /**
     * Masseinheit
     */
    private String ivMasseinheit;
    
    /**
     * Flur
     */
    private String ivFlur;
    
    /**
     * Flurstueck
     */
    private String ivFlurstueck;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Grundstueck(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivId = new String();
        this.ivGrundbuchblattId = new String();
        this.ivLaufendeNummer = new String();
        this.ivGemarkung = new String();
        this.ivGroesse = new String();
        this.ivMasseinheit = new String();
        this.ivFlur = new String();
        this.ivFlurstueck = new String();
    }

    /**
     * Liefert die ID des Grundstuecks
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * Setzt die ID des Grundstuecks
     * @param pvId the id to set
     */
    private void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * Liefert die ID des Grundbuchblatts
     * @return the grundbuchblattId
     */
    public String getGrundbuchblattId() {
        return this.ivGrundbuchblattId;
    }

    /**
     * Setzt die ID des Grundbuchblatts
     * @param pvGrundbuchblattId the grundbuchblattId to set
     */
    private void setGrundbuchblattId(String pvGrundbuchblattId) {
        this.ivGrundbuchblattId = pvGrundbuchblattId;
    }

    /**
     * Liefert die Laufende Nummer
     * @return the laufendeNummer
     */
    public String getLaufendeNummer() {
        return this.ivLaufendeNummer;
    }

    /**
     * Setzt die Laufende Nummer
     * @param pvLaufendeNummer the laufendeNummer to set
     */
    private void setLaufendeNummer(String pvLaufendeNummer) {
        this.ivLaufendeNummer = pvLaufendeNummer;
    }

    /**
     * Liefert die Gemarkung
     * @return the gemarkung
     */
    public String getGemarkung() {
        return this.ivGemarkung;
    }

    /**
     * Setzt die Gemarkung
     * @param pvGemarkung the gemarkung to set
     */
    private void setGemarkung(String pvGemarkung) {
        this.ivGemarkung = pvGemarkung;
    }

    /**
     * Liefert die Groesse des Grundstuecks
     * @return the groesse
     */
    public String getGroesse() {
        return this.ivGroesse;
    }

    /**
     * Setzt die Groesse des Grundstuecks
     * @param pvGroesse the groesse to set
     */
    private void setGroesse(String pvGroesse) {
        this.ivGroesse = pvGroesse;
    }

    /**
     * Liefert die Masseinheit
     * @return the masseinheit
     */
    public String getMasseinheit() {
        return this.ivMasseinheit;
    }

    /**
     * Setzt die Masseinheit
     * @param pvMasseinheit the masseinheit to set
     */
    private void setMasseinheit(String pvMasseinheit) {
        this.ivMasseinheit = pvMasseinheit;
    }

    /**
     * Liefert den Flur
     * @return the flur
     */
    public String getFlur() {
        return this.ivFlur;
    }

    /**
     * Setzt den Flur
     * @param pvFlur the flur to set
     */
    private void setFlur(String pvFlur) {
        this.ivFlur = pvFlur;
    }

    /**
     * Liefert das Flurstueck
     * @return the flurstueck
     */
    public String getFlurstueck() {
        return this.ivFlurstueck;
    }

    /**
     * Setzt das Flurstueck
     * @param pvFlurstueck the flurstueck to set
     */
    private void setFlurstueck(String pvFlurstueck) {
        this.ivFlurstueck = pvFlurstueck;
    }
    
    /**
     * Zerlegt die Zeile in die unterschiedlichen Grundstueck-Daten
     * @param pvZeile die zu zerlegende Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseGrundstueck(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setGrundstueck(lvLfd, lvTemp);
       
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
     this.setGrundstueck(lvLfd, lvTemp);
   }
    
    /**
     * Setzt einen Wert des Grundstuecks
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setGrundstueck(int pvPos, String pvWert)
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
              this.setGrundbuchblattId(pvWert);
              break;
          case 3:
              this.setLaufendeNummer(pvWert);
              break;
          case 4:
              this.setGemarkung(pvWert);
              break;
          case 5:
              this.setGroesse(pvWert);
              break;
          case 6:
              this.setMasseinheit(pvWert);
              break;
          case 7:
              this.setFlur(pvWert);
              break;
          case 8:
              this.setFlurstueck(pvWert);
              break;
          default:
              ivLogger.info("Grundstueck: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Grundstuecks in einen String
     * @return Inhalt des Grundstuecks
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Grundstueck:" + StringKonverter.lineSeparator);
        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
        lvOut.append("GrundbuchblattID: " + ivGrundbuchblattId + StringKonverter.lineSeparator);
        lvOut.append("Laufende Nummer: " + ivLaufendeNummer + StringKonverter.lineSeparator);
        lvOut.append("Gemarkung: " + ivGemarkung + StringKonverter.lineSeparator);
        lvOut.append("Groesse: " + ivGroesse + StringKonverter.lineSeparator);
        lvOut.append("Masseinheit: " + ivMasseinheit + StringKonverter.lineSeparator);
        lvOut.append("Flur: " + ivFlur + StringKonverter.lineSeparator);
        lvOut.append("Flurstueck: " + ivFlurstueck + StringKonverter.lineSeparator);

        return lvOut.toString();
    }

}
