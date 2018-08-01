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
public class Beleihungssatz 
{
    /**
     * GUID fuer PRT_LR_GUID (Beleihungssatz)
     */
    private String ivId;
    
    /**
     * GUID fuer Objektteil (OBJ_PRT_GUID)
     */
    private String ivObjektteilId;
    
    /**
     * Teil-ID fuer das Objekt (PART_ID)
     */
    private String ivTeilId;
    
    /**
     * GUID fuer Tabelle CMS_AST (Immobilie)
     */
    private String ivImmobilieId;
    
    /**
     * Verknuepfungsschluessel zwischen Immob. und BS-Systemen (LR_ID - Grundstueck)
     */
    private String ivGrundstueckId;

    /**
     * Konstruktor
     */
    public Beleihungssatz() 
    {
        super();
        this.ivId = new String();
        this.ivObjektteilId = new String();
        this.ivTeilId = new String();
        this.ivImmobilieId = new String();
        this.ivGrundstueckId = new String();
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
     * @return the objektteilId
     */
    public String getObjektteilId() {
        return this.ivObjektteilId;
    }

    /**
     * @param pvObjektteilId the objektteilId to set
     */
    public void setObjektteilId(String pvObjektteilId) {
        this.ivObjektteilId = pvObjektteilId;
    }

    /**
     * @return the teilId
     */
    public String getTeilId() {
        return this.ivTeilId;
    }

    /**
     * @param pvTeilId the teilId to set
     */
    public void setTeilId(String pvTeilId) {
        this.ivTeilId = pvTeilId;
    }

    /**
     * @return the immobilieId
     */
    public String getImmobilieId() {
        return this.ivImmobilieId;
    }

    /**
     * @param pvImmobilieId the immobilieId to set
     */
    public void setImmobilieId(String pvImmobilieId) {
        this.ivImmobilieId = pvImmobilieId;
    }

    /**
     * @return the grundstueckId
     */
    public String getGrundstueckId() {
        return this.ivGrundstueckId;
    }

    /**
     * @param pvGrundstueckId the grundstueckId to set
     */
    public void setGrundstueckId(String pvGrundstueckId) {
        this.ivGrundstueckId = pvGrundstueckId;
    }
    
    /**
     * @param pvZeile 
     * @return 
     * 
     */
    public boolean parseBeleihungssatz(String pvZeile)
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
         this.setBeleihungssatz(lvLfd, lvTemp);
       
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
     this.setBeleihungssatz(lvLfd, lvTemp);     
     
     return true;
   }
    
    /**
     * Setzt einen Wert des Beleihungssatzes
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setBeleihungssatz(int pvPos, String pvWert)
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
              this.setObjektteilId(pvWert);
              break;
          case 3:
              this.setTeilId(pvWert);
              break;
          case 4:
              this.setImmobilieId(pvWert);
              break;
          case 5:
              this.setGrundstueckId(pvWert);
              break;
          default:
              System.out.println("Beleihungssatz: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Beleihungssatzes in einen String
     * @return
     */
    public String toString()
    {
        String lvOut = new String();

        lvOut = "Id: " + ivId + StringKonverter.lineSeparator;
        lvOut = lvOut + "ObjektteilId: " + ivObjektteilId + StringKonverter.lineSeparator;
        lvOut = lvOut + "TeilId: " + ivTeilId + StringKonverter.lineSeparator;
        lvOut = lvOut + "ImmobilieId: " + ivImmobilieId + StringKonverter.lineSeparator;
        lvOut = lvOut + "GrundstueckId: " + ivGrundstueckId + StringKonverter.lineSeparator;

        return lvOut;
    }
 
    
}
