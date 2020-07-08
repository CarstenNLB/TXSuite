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
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Beleihungssatz(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivId = new String();
        this.ivObjektteilId = new String();
        this.ivTeilId = new String();
        this.ivImmobilieId = new String();
        this.ivGrundstueckId = new String();
    }

    /**
     * Liefert die ID des Beleihungssatzes
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * Setzt die ID des Beleihungssatzes
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * Liefert die ID des Objektteils
     * @return the objektteilId
     */
    public String getObjektteilId() {
        return this.ivObjektteilId;
    }

    /**
     * Setzt die ID des Objektteils
     * @param pvObjektteilId the objektteilId to set
     */
    public void setObjektteilId(String pvObjektteilId) {
        this.ivObjektteilId = pvObjektteilId;
    }

    /**
     * Liefert die Teil-ID
     * @return the teilId
     */
    public String getTeilId() {
        return this.ivTeilId;
    }

    /**
     * Setzt die Teil-ID
     * @param pvTeilId the teilId to set
     */
    public void setTeilId(String pvTeilId) {
        this.ivTeilId = pvTeilId;
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
     * Liefert die ID des Grundstuecks
     * @return the grundstueckId
     */
    public String getGrundstueckId() {
        return this.ivGrundstueckId;
    }

    /**
     * Setzt die ID des Grundstuecks
     * @param pvGrundstueckId the grundstueckId to set
     */
    public void setGrundstueckId(String pvGrundstueckId) {
        this.ivGrundstueckId = pvGrundstueckId;
    }
    
    /**
     * Zerlegt eine Zeile in die unterschiedlichen Beleihungssatz-Daten
     * @param pvZeile die zu zerlegende Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseBeleihungssatz(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setBeleihungssatz(lvLfd, lvTemp);
       
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
     this.setBeleihungssatz(lvLfd, lvTemp);
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
              ivLogger.info("Beleihungssatz: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Beleihungssatzes in einen String
     * @return String mit Inhalt des Beleihungssatzes
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
        lvOut.append("ObjektteilId: " + ivObjektteilId + StringKonverter.lineSeparator);
        lvOut.append("TeilId: " + ivTeilId + StringKonverter.lineSeparator);
        lvOut.append("ImmobilieId: " + ivImmobilieId + StringKonverter.lineSeparator);
        lvOut.append("GrundstueckId: " + ivGrundstueckId + StringKonverter.lineSeparator);

        return lvOut.toString();
    }
        
}
