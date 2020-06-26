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
public class Grundbuchblatt 
{
    /**
     * GUID fuer Tabelle CMS_LR_DE_A (Grundbuchblatt)
     */
    private String ivId;
    
    /**
     * Amtsgericht
     */
    private String ivAmtsgericht;
    
    /**
     * Grundbuch von
     */
    private String ivGrundbuchVon;
    
    /**
     * Band
     */
    private String ivBand;
    
    /**
     * Blatt/Heft
     */
    private String ivBlatt;
    
    /**
     * Registerart
     */
    private String ivRegisterart;
    
    /**
     * Referenz des alten Grundbuchs, falls Daten migriert wurden
     */
    private String ivReferenz;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Grundbuchblatt(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivId = new String();
        this.ivAmtsgericht = new String();
        this.ivGrundbuchVon = new String();
        this.ivBand = new String();
        this.ivBlatt = new String();
        this.ivRegisterart = new String();
        this.ivReferenz = new String();
    }

    /**
     * Liefert die ID des Grundbuchblatts
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * Setzt die ID des Grundbuchblatts
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * Liefert das Amtsgericht
     * @return the amtsgericht
     */
    public String getAmtsgericht() {
        return this.ivAmtsgericht;
    }

    /**
     * Setzt das Amtsgericht
     * @param pvAmtsgericht the amtsgericht to set
     */
    public void setAmtsgericht(String pvAmtsgericht) {
        this.ivAmtsgericht = pvAmtsgericht;
    }

    /**
     * Liefert das GrundbuchVon
     * @return the grundbuchVon
     */
    public String getGrundbuchVon() {
        return this.ivGrundbuchVon;
    }

    /**
     * Setzt das GrundbuchVon
     * @param pvGrundbuchVon the grundbuchVon to set
     */
    public void setGrundbuchVon(String pvGrundbuchVon) {
        this.ivGrundbuchVon = pvGrundbuchVon;
    }

    /**
     * Liefert das Band
     * @return the band
     */
    public String getBand() {
        return this.ivBand;
    }

    /**
     * Setzt das Band
     * @param pvBand the band to set
     */
    public void setBand(String pvBand) {
        this.ivBand = pvBand;
    }

    /**
     * Liefert das Blatt
     * @return the blatt
     */
    public String getBlatt() {
        return this.ivBlatt;
    }

    /**
     * Setzt das Blatt
     * @param pvBlatt the blatt to set
     */
    public void setBlatt(String pvBlatt) {
        this.ivBlatt = pvBlatt;
    }

    /**
     * Liefert die Registerart
     * @return the registerart
     */
    public String getRegisterart() {
        return this.ivRegisterart;
    }

    /**
     * Setzt die Registerart
     * @param pvRegisterart the registerart to set
     */
    public void setRegisterart(String pvRegisterart) {
        this.ivRegisterart = pvRegisterart;
    }

    /**
     * Liefert die Referenz
     * @return the referenz
     */
    public String getReferenz() {
        return this.ivReferenz;
    }

    /**
     * Setzt die Referenz
     * @param pvReferenz the referenz to set
     */
    public void setReferenz(String pvReferenz) {
        this.ivReferenz = pvReferenz;
    }
    
    /**
     * Zerlegt eine Zeile in die unterschiedlichen Grundbuchblatt-Daten
     * @param pvZeile die zu zerlegene Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseGrundbuchblatt(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setGrundbuchblatt(lvLfd, lvTemp);
       
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
     this.setGrundbuchblatt(lvLfd, lvTemp);
   }
    
    /**
     * Setzt einen Wert des Grundbuchblatts
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setGrundbuchblatt(int pvPos, String pvWert)
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
              this.setAmtsgericht(pvWert);
              break;
          case 3:
              this.setGrundbuchVon(pvWert);
              break;
          case 4:
        	  // Migration BLB -> Entfernen von '$BLB' aus dem Band - CT 04.10.2018
        	  if (pvWert.startsWith("$BLB"))
        	  {
        		  pvWert = pvWert.substring(4);
        	  }
              this.setBand(pvWert);
              break;
          case 5:
              this.setBlatt(pvWert);
              break;
          case 6:
              this.setRegisterart(pvWert);
              break;
          case 7:
              this.setReferenz(pvWert);
              break;
          default:
              ivLogger.info("Grundbuchblatt: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Grundbuchblattes in einen String
     * @return Liefert den Inhalt des Grundbuchblattes als String
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Grundbuchblatt:" + StringKonverter.lineSeparator);
        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
        lvOut.append("Amtsgericht: " + ivAmtsgericht + StringKonverter.lineSeparator);
        lvOut.append("Grundbuch von: " + ivGrundbuchVon + StringKonverter.lineSeparator);
        lvOut.append("Band: " + ivBand + StringKonverter.lineSeparator);
        lvOut.append("Blatt: " + ivBlatt + StringKonverter.lineSeparator);
        lvOut.append("Registerart: " + ivRegisterart + StringKonverter.lineSeparator);
        lvOut.append("Referenz: " + ivReferenz + StringKonverter.lineSeparator);
 
        return lvOut.toString();
    }
    
    /**
     * Vergleicht dieses Grundbuchblatt mit einem anderen Grundbuchblatt auf Gleichheit
     * @param pvGrundbuchblatt Mit diesem Grundbuchblatt soll verglichen werden
     * @return Wenn gleich, dann true und ansonsten false
     */
    public boolean equals(Grundbuchblatt pvGrundbuchblatt)
    {

    	return (this.ivAmtsgericht.equals(pvGrundbuchblatt.getAmtsgericht()) &&
    		    this.ivBand.equals(pvGrundbuchblatt.getBand()) &&	
    		    this.ivBlatt.equals(pvGrundbuchblatt.getBlatt()) &&
    		    this.ivGrundbuchVon.equals(pvGrundbuchblatt.getGrundbuchVon()));
    }

}
