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
     * Konstruktor
     */
    public Geschaeftspartner() 
    {
        super();
        this.ivId = new String();
        this.ivSicherheitenvereinbarungId = new String();
        this.ivImmobilieId = new String();
        this.ivKundennummer = new String();
        this.ivGeschaeftspartnerfunktion = new String();
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
     * @return the sicherheitenvereinbarungId
     */
    public String getSicherheitenvereinbarungId() {
        return this.ivSicherheitenvereinbarungId;
    }

    /**
     * @param pvSicherheitenvereinbarungId the sicherheitenvereinbarungId to set
     */
    public void setSicherheitenvereinbarungId(String pvSicherheitenvereinbarungId) {
        this.ivSicherheitenvereinbarungId = pvSicherheitenvereinbarungId;
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
     * @return the kundennummer
     */
    public String getKundennummer() {
        return this.ivKundennummer;
    }

    /**
     * @param pvKundennummer the kundennummer to set
     */
    public void setKundennummer(String pvKundennummer) {
        this.ivKundennummer = pvKundennummer;
    }

    /**
     * @return the geschaeftspartnerfunktion
     */
    public String getGeschaeftspartnerfunktion() {
        return this.ivGeschaeftspartnerfunktion;
    }

    /**
     * @param pvGeschaeftspartnerfunktion the geschaeftspartnerfunktion to set
     */
    public void setGeschaeftspartnerfunktion(String pvGeschaeftspartnerfunktion) {
        this.ivGeschaeftspartnerfunktion = pvGeschaeftspartnerfunktion;
    }
    
    /**
     * @param pvZeile 
     * @return 
     * 
     */
   public boolean parseGeschaeftspartner(String pvZeile)
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
         this.setGeschaeftspartner(lvLfd, lvTemp);
       
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
     this.setGeschaeftspartner(lvLfd, lvTemp);     
     
     return true;
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
              System.out.println("Geschaeftspartner: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
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
