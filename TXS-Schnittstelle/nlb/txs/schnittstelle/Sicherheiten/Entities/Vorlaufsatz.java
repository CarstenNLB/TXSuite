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
public class Vorlaufsatz 
{
    /**
     * Institut
     */
    private String ivInstitut;
    
    /**
     * Systemname
     */
    private String ivSystemname;
    
    /**
     * Systembenutzer
     */
    private String ivSystembenutzer;
    
    /**
     * Buchungsdatum
     */
    private String ivBuchungsdatum;
    
    /**
     * Aktuelles Systemdatum
     */
    private String ivAktuellesDatum;
    
    /**
     * Aktuelle Systemuhrzeit
     */
    private String ivAktuelleUhrzeit;
   
    /**
     * Anzahl der Datensaetze
     */
    private String ivAnzahl;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Vorlaufsatz(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivInstitut = new String();
        this.ivSystemname = new String();
        this.ivSystembenutzer = new String();
        this.ivBuchungsdatum = new String();
        this.ivAktuellesDatum = new String();
        this.ivAktuelleUhrzeit = new String();
        this.ivAnzahl = new String();
    }

    /**
     * Liefert das Institut (009|004 oder NLB|BLB)
     * @return the institut
     */
    public String getInstitut() {
        return this.ivInstitut;
    }

    /**
     * Setzt das Institut
     * @param pvInstitut the institut to set
     */
    public void setInstitut(String pvInstitut) {
        this.ivInstitut = pvInstitut;
    }

    /**
     * Liefert den Systemnamen
     * @return the systemname
     */
    public String getSystemname() {
        return this.ivSystemname;
    }

    /**
     * Setzt den Systemnamen
     * @param pvSystemname the systemname to set
     */
    public void setSystemname(String pvSystemname) {
        this.ivSystemname = pvSystemname;
    }

    /**
     * Liefert den Systembenutzer
     * @return the systembenutzer
     */
    public String getSystembenutzer() {
        return this.ivSystembenutzer;
    }

    /**
     * Setzt den Systembenutzer
     * @param pvSystembenutzer the systembenutzer to set
     */
    public void setSystembenutzer(String pvSystembenutzer) {
        this.ivSystembenutzer = pvSystembenutzer;
    }

    /**
     * Liefert das Buchungsdatum
     * @return the buchungsdatum
     */
    public String getBuchungsdatum() {
        return this.ivBuchungsdatum;
    }

    /**
     * Setzt das Buchungsdatum
     * @param pvBuchungsdatum the buchungsdatum to set
     */
    public void setBuchungsdatum(String pvBuchungsdatum) {
        this.ivBuchungsdatum = pvBuchungsdatum;
    }

    /**
     * Liefert das aktuelle Datum
     * @return the aktuellesDatum
     */
    public String getAktuellesDatum() {
        return this.ivAktuellesDatum;
    }

    /**
     * Setzt das aktuelle Datum
     * @param pvAktuellesDatum the aktuellesDatum to set
     */
    public void setAktuellesDatum(String pvAktuellesDatum) {
        this.ivAktuellesDatum = pvAktuellesDatum;
    }

    /**
     * Liefert die aktuelle Uhrzeit
     * @return the aktuelleUhrzeit
     */
    public String getAktuelleUhrzeit() {
        return this.ivAktuelleUhrzeit;
    }

    /**
     * Setzt die aktuelle Uhrzeit
     * @param pvAktuelleUhrzeit the aktuelleUhrzeit to set
     */
    public void setAktuelleUhrzeit(String pvAktuelleUhrzeit) {
        this.ivAktuelleUhrzeit = pvAktuelleUhrzeit;
    }

    /**
     * Liefert die Anzahl
     * @return the anzahl
     */
    public String getAnzahl() {
        return this.ivAnzahl;
    }

    /**
     * Setzt die Anzahl
     * @param pvAnzahl the anzahl to set
     */
    public void setAnzahl(String pvAnzahl) {
        this.ivAnzahl = pvAnzahl;
    }
    
    /**
     * Zerlegt eine Zeile in die unterschiedlichen Daten/Felder
     * @param pvZeile die zu zerlegende Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
   public void parseVorlaufsatz(String pvZeile, int pvQuelle)
   {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
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

     // Letzte Feld auch noch setzen
     this.setVorlaufsatz(lvLfd, lvTemp);
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
              // Satzart
              break;
          case 1:
              this.setInstitut(pvWert);
              break;
          case 2:
              this.setSystemname(pvWert);
              break;
          case 3:
              this.setSystembenutzer(pvWert);
              break;
          case 4:
              this.setBuchungsdatum(pvWert);
              break;
          case 5:
              this.setAktuellesDatum(pvWert);
              break;
          case 6:
              this.setAktuelleUhrzeit(pvWert);
              break;
          case 7:
              this.setAnzahl(pvWert);
              break;
          default:
              ivLogger.info("Vorlaufsatz: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Vorlaufsatz in einen String
     * @return
     */
    public String toString()
    {
        String lvOut = new String();

        lvOut = "Institut: " + ivInstitut + StringKonverter.lineSeparator;
        lvOut = lvOut + "Systemname: " + ivSystemname + StringKonverter.lineSeparator;
        lvOut = lvOut + "Systembenutzer: " + ivSystembenutzer + StringKonverter.lineSeparator;
        lvOut = lvOut + "Buchungsdatum: " + ivBuchungsdatum + StringKonverter.lineSeparator;
        lvOut = lvOut + "Aktuelles Datum: " + ivAktuellesDatum + StringKonverter.lineSeparator;
        lvOut = lvOut + "Aktuelle Uhrzeit: " + ivAktuelleUhrzeit + StringKonverter.lineSeparator;
        lvOut = lvOut + "Anzahl: " + ivAnzahl + StringKonverter.lineSeparator;

        return lvOut;
    }

}
