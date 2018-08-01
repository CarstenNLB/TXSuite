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
     * Konstruktor
     */
    public Grundstueck() 
    {
        super();
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
     * @return the grundbuchblattId
     */
    public String getGrundbuchblattId() {
        return this.ivGrundbuchblattId;
    }

    /**
     * @param pvGrundbuchblattId the grundbuchblattId to set
     */
    public void setGrundbuchblattId(String pvGrundbuchblattId) {
        this.ivGrundbuchblattId = pvGrundbuchblattId;
    }

    /**
     * @return the laufendeNummer
     */
    public String getLaufendeNummer() {
        return this.ivLaufendeNummer;
    }

    /**
     * @param pvLaufendeNummer the laufendeNummer to set
     */
    public void setLaufendeNummer(String pvLaufendeNummer) {
        this.ivLaufendeNummer = pvLaufendeNummer;
    }

    /**
     * @return the gemarkung
     */
    public String getGemarkung() {
        return this.ivGemarkung;
    }

    /**
     * @param pvGemarkung the gemarkung to set
     */
    public void setGemarkung(String pvGemarkung) {
        this.ivGemarkung = pvGemarkung;
    }

    /**
     * @return the groesse
     */
    public String getGroesse() {
        return this.ivGroesse;
    }

    /**
     * @param pvGroesse the groesse to set
     */
    public void setGroesse(String pvGroesse) {
        this.ivGroesse = pvGroesse;
    }

    /**
     * @return the masseinheit
     */
    public String getMasseinheit() {
        return this.ivMasseinheit;
    }

    /**
     * @param pvMasseinheit the masseinheit to set
     */
    public void setMasseinheit(String pvMasseinheit) {
        this.ivMasseinheit = pvMasseinheit;
    }

    /**
     * @return the flur
     */
    public String getFlur() {
        return this.ivFlur;
    }

    /**
     * @param pvFlur the flur to set
     */
    public void setFlur(String pvFlur) {
        this.ivFlur = pvFlur;
    }

    /**
     * @return the flurstueck
     */
    public String getFlurstueck() {
        return this.ivFlurstueck;
    }

    /**
     * @param pvFlurstueck the flurstueck to set
     */
    public void setFlurstueck(String pvFlurstueck) {
        this.ivFlurstueck = pvFlurstueck;
    }
    
    /**
     * @param pvZeile 
     * @return 
     * 
     */
    public boolean parseGrundstueck(String pvZeile)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
     int    lvZzStr=0;              // pointer fuer satzbereich
     //int    lvZzFld=0;              // bytezaehler je ermitteltes feld
     
     // steuerung/iteration eingabesatz
     for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setGrundstueck(lvLfd, lvTemp);
       
         lvLfd++;                  // naechste Feldnummer
         //lvZzFld = 0;              // Bytezaehler
       
         // loeschen Zwischenbuffer
         lvTemp = new String();

       }
       else
       {
           // uebernehmen byte aus eingabesatzposition
           lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
           //lvZzFld++;
       }
     } // ende for
     
     // Letzte Feld auch noch setzen
     this.setGrundstueck(lvLfd, lvTemp);     
     
     return true;
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
              System.out.println("Grundstueck: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Grundstuecks in einen String
     * @return
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
