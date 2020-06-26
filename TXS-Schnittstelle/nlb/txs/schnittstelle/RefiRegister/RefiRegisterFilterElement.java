/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.RefiRegister;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class RefiRegisterFilterElement 
{
   /**
    * Kontonummer
    */
    private String ivKontonummer;
    
    /**
     * Deckungskennzeichen
     */
    private String ivDeckungskennzeichen;
    
    /**
     * Kompensationsschluessel
     */
    private String ivKompensationsschluessel;
    
    /**
     * Produktschluessel 
     */
    private String ivProduktschluessel;
    
    /**
     * Konsortial-Hauptkontonummer
     */
    private String ivKonsortialHauptkontonummer;
    
    /**
     * 
     */
    private String ivRestkapital;
    
    /**
     * PassivKontonummer
     */
    private String ivPassivKontonummer;

    /**
     * Konstruktor mit Initialisierung der Instanzvariablen
     */
    public RefiRegisterFilterElement() 
    {
        super();
        this.ivKontonummer = new String();
        this.ivDeckungskennzeichen = new String();
        this.ivKompensationsschluessel = new String();
        this.ivProduktschluessel = new String();
        this.ivKonsortialHauptkontonummer = new String();
        this.ivPassivKontonummer = new String();
        this.ivRestkapital = new String();
    }

    /**
     * Konstruktor mit Parameteruebergabe
     * @param pvKontonummer Kontonummer
     * @param pvDeckungskennzeichen Deckungskennzeichen
     * @param pvKompensationsschluessel Kompensationsschluessel
     * @param pvProduktschluessel Produktschluessel
     * @param pvKonsortialHauptkontonummer Konsortial-Hauptkontonummer
     * @param pvPassivKontonummer Passivkontonummer
     * @param pvRestkapital Restkapital
     */
    public RefiRegisterFilterElement(String pvKontonummer, String pvDeckungskennzeichen, String pvKompensationsschluessel, String pvProduktschluessel, String pvKonsortialHauptkontonummer, String pvPassivKontonummer, String pvRestkapital) 
    {
        super();
        this.ivKontonummer = pvKontonummer;
        this.ivDeckungskennzeichen = pvDeckungskennzeichen;
        this.ivKompensationsschluessel = pvKompensationsschluessel;
        this.ivProduktschluessel = pvProduktschluessel;
        this.ivKonsortialHauptkontonummer = pvKonsortialHauptkontonummer;
        this.ivPassivKontonummer = pvPassivKontonummer;
        this.ivRestkapital = pvRestkapital;
    }

    /**
     * Liefert die Kontonummer
     * @return the kontonummer
     */
    public String getKontonummer() {
        return this.ivKontonummer;
    }

    /**
     * Setzt die Kontonummer
     * @param pvKontonummer the kontonummer to set
     */
    public void setKontonummer(String pvKontonummer) {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * Liefert das Deckungskennzeichen
     * @return the deckungskennzeichen
     */
    public String getDeckungskennzeichen() {
        return this.ivDeckungskennzeichen;
    }

    /**
     * Setzt das Deckungskennzeichen
     * @param pvDeckungskennzeichen the deckungskennzeichen to set
     */
    public void setDeckungskennzeichen(String pvDeckungskennzeichen) {
        this.ivDeckungskennzeichen = pvDeckungskennzeichen;
    }

    /**
     * Liefert den Kompensationsschluessel
     * @return the kompensationsschluessel
     */
    public String getKompensationsschluessel() {
        return this.ivKompensationsschluessel;
    }

    /**
     * Setzt den Kompensationsschluessel
     * @param pvKompensationsschluessel the kompensationsschluessel to set
     */
    public void setKompensationsschluessel(String pvKompensationsschluessel) {
        this.ivKompensationsschluessel = pvKompensationsschluessel;
    }

    /**
     * Liefert die Passivkontonummer
     * @return the passivKontonummer
     */
    public String getPassivKontonummer() {
        return this.ivPassivKontonummer;
    }

    /**
     * Setzt die Passivkontonummer
     * @param pvPassivKontonummer the passivKontonummer to set
     */
    public void setPassivKontonummer(String pvPassivKontonummer) {
        this.ivPassivKontonummer = pvPassivKontonummer;
    }

    /**
     * Liefert den Produktschluessel
     * @return the produktschluessel
     */
    public String getProduktschluessel() {
        return this.ivProduktschluessel;
    }

    /**
     * Setzt den Produktschluessel
     * @param pvProduktschluessel the produktschluessel to set
     */
    public void setProduktschluessel(String pvProduktschluessel) {
        this.ivProduktschluessel = pvProduktschluessel;
    }

    /**
     * Liefert die Konsortial-Hauptkontonummer
     * @return the konsortialHauptkontonummer
     */
    public String getKonsortialHauptkontonummer() {
        return this.ivKonsortialHauptkontonummer;
    }

    /**
     * Setzt die Konsortial-Hauptkontonummer
     * @param pvKonsortialHauptkontonummer the konsortialHauptkontonummer to set
     */
    public void setKonsortialHauptkontonummer(String pvKonsortialHauptkontonummer) {
        this.ivKonsortialHauptkontonummer = pvKonsortialHauptkontonummer;
    } 
    
    /**
     * Liefert das Restkapital
     * @return the restkapital
     */
    public String getRestkapital() {
        return this.ivRestkapital;
    }

    /**
     * Setzt das Restkapital
     * @param pvRestkapital the restkapital to set
     */
    public void setRestkapital(String pvRestkapital) {
        this.ivRestkapital = pvRestkapital;
    }

    /**
     * Zerlegt eine Zeile in die verschiedenen Daten/Felder
     * @param pvZeile die zu zerlegende Zeile
     */
    public void parseRefiRegisterFilterElement(String pvZeile)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Semikolon erkannt
       if (pvZeile.charAt(lvZzStr) == ';')
       {
         this.setRefiRegisterFilterElement(lvLfd, lvTemp);
       
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
     this.setRefiRegisterFilterElement(lvLfd, lvTemp);
   }
    
    /**
     * Setzt einen Wert des RefiRegisterFilterElements
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setRefiRegisterFilterElement(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
          case 0:
              this.setKontonummer(pvWert);
              break;
          case 1:
              this.setDeckungskennzeichen(pvWert);
              break;
          case 2:
              this.setKompensationsschluessel(pvWert);
              break;
          case 3:
              this.setProduktschluessel(pvWert);
              break;
          case 4:
              this.setKonsortialHauptkontonummer(pvWert);
              break;
          case 5:
              this.setPassivKontonummer(pvWert);
              break;
          case 6:
              this.setRestkapital(pvWert);
              break;
          default:
              System.out.println("RefiRegisterFilterElement: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des RefiRegisterFilterElements in einen String
     * @return
     */
    public String toString()
    {
        String lvOut = new String();

        lvOut = "Kontonummer: " + ivKontonummer + StringKonverter.lineSeparator;
        lvOut = lvOut + "Deckungskennzeichen: " + ivDeckungskennzeichen + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kompensationsschluessel: " + ivKompensationsschluessel + StringKonverter.lineSeparator;
        lvOut = lvOut + "Produktschluessel: " + ivProduktschluessel + StringKonverter.lineSeparator;
        lvOut = lvOut + "Konsortial-Hauptkontonummer: " + ivKonsortialHauptkontonummer + StringKonverter.lineSeparator;
        lvOut = lvOut + "Passiv-Kontonummer: " + ivPassivKontonummer + StringKonverter.lineSeparator;
        lvOut = lvOut + "Restkapital: " + ivRestkapital + StringKonverter.lineSeparator;

        return lvOut;
    }

}
