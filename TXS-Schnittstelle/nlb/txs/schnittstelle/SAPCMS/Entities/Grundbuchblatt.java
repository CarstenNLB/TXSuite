/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS.Entities;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

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
     * Konstruktor
     */
    public Grundbuchblatt() 
    {
        super();
        this.ivId = new String();
        this.ivAmtsgericht = new String();
        this.ivGrundbuchVon = new String();
        this.ivBand = new String();
        this.ivBlatt = new String();
        this.ivRegisterart = new String();
        this.ivReferenz = new String();
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * @return the amtsgericht
     */
    public String getAmtsgericht() {
        return this.ivAmtsgericht;
    }

    /**
     * @param pvAmtsgericht the amtsgericht to set
     */
    public void setAmtsgericht(String pvAmtsgericht) {
        this.ivAmtsgericht = pvAmtsgericht;
    }

    /**
     * @return the grundbuchVon
     */
    public String getGrundbuchVon() {
        return this.ivGrundbuchVon;
    }

    /**
     * @param pvGrundbuchVon the grundbuchVon to set
     */
    public void setGrundbuchVon(String pvGrundbuchVon) {
        this.ivGrundbuchVon = pvGrundbuchVon;
    }

    /**
     * @return the band
     */
    public String getBand() {
        return this.ivBand;
    }

    /**
     * @param pvBand the band to set
     */
    public void setBand(String pvBand) {
        this.ivBand = pvBand;
    }

    /**
     * @return the blatt
     */
    public String getBlatt() {
        return this.ivBlatt;
    }

    /**
     * @param pvBlatt the blatt to set
     */
    public void setBlatt(String pvBlatt) {
        this.ivBlatt = pvBlatt;
    }

    /**
     * @return the registerart
     */
    public String getRegisterart() {
        return this.ivRegisterart;
    }

    /**
     * @param pvRegisterart the registerart to set
     */
    public void setRegisterart(String pvRegisterart) {
        this.ivRegisterart = pvRegisterart;
    }

    /**
     * @return the referenz
     */
    public String getReferenz() {
        return this.ivReferenz;
    }

    /**
     * @param pvReferenz the referenz to set
     */
    public void setReferenz(String pvReferenz) {
        this.ivReferenz = pvReferenz;
    }
    
    /**
     * @param pvZeile 
     * @return 
     * 
     */
    public boolean parseGrundbuchblatt(String pvZeile)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
     int    lvZzStr=0;              // pointer fuer satzbereich
     
     // steuerung/iteration eingabesatz
     for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
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
     
     return true;
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
              System.out.println("Grundbuchblatt: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
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
