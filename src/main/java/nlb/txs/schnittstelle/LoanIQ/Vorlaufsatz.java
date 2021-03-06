/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ;

import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class Vorlaufsatz 
{
    /**
     * Bankarea/Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Buchungsdatum
     */
    private String ivBuchungsdatum;
    
    /**
     * Anzahl der Finanzgeschaefte
     */
    private int ivAnzahlFinanzgeschaefte;
    
    /**
     * Zeitstempel der Dateierstellung
     */
    private String ivTimestamp;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
     * Konstruktor - Initialisiert die Instanzvariablen
     */
    public Vorlaufsatz(Logger pvLogger)
    {
      this.ivLogger = pvLogger;
        this.ivInstitutsnummer = new String();
        this.ivBuchungsdatum = new String();
        this.ivAnzahlFinanzgeschaefte = 0;
        this.ivTimestamp = new String();
    }

    /**
     * Liefert die Institutsnummer
     * @return the institutsnummer
     */
    public String getInstitutsnummer() {
        return this.ivInstitutsnummer;
    }

    /**
     * Setzt die Institutsnummer
     * @param pvInstitutsnummer the institutsnummer to set
     */
    public void setInstitutsnummer(String pvInstitutsnummer) {
        this.ivInstitutsnummer = pvInstitutsnummer;
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
     * Liefert die Anzahl der Finanzgeschaefte
     * @return the anzahlFinanzgeschaefte
     */
    public int getAnzahlFinanzgeschaefte() {
        return this.ivAnzahlFinanzgeschaefte;
    }

    /**
     * Setzt die Anzahl der Finanzgeschaefte
     * @param pvAnzahlFinanzgeschaefte the anzahlFinanzgeschaefte to set
     */
    public void setAnzahlFinanzgeschaefte(int pvAnzahlFinanzgeschaefte) {
        this.ivAnzahlFinanzgeschaefte = pvAnzahlFinanzgeschaefte;
    }

    /**
     * Liefert den Zeitstempel
     * @return the timestamp
     */
    public String getTimestamp() {
        return this.ivTimestamp;
    }

    /**
     * Setzt den Zeitstempel
     * @param pvTimestamp the timestamp to set
     */
    public void setTimestamp(String pvTimestamp) {
        this.ivTimestamp = pvTimestamp;
    }
    
    /**
     * Zerlegt die Zeichenkette in einzelne Felder
     * @param pvZeile die zu zerlegende Zeile
     */
   public void parseVorlaufsatz(String pvZeile)
   {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn semikolon erkannt
       if (pvZeile.charAt(lvZzStr) == ';')
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
     
     //if (stemp.equals("E"))
     //    System.out.println("Vorlaufsatz: Endekennzeichen erreicht.");

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
              this.setInstitutsnummer(pvWert);
              break;
          case 1:
              this.setBuchungsdatum(pvWert);
              break;
          case 2:
              this.setAnzahlFinanzgeschaefte(StringKonverter.convertString2Int(pvWert));
              break;
          case 3:
              this.setTimestamp(pvWert);
              break;
           default:
              ivLogger.error("RefiRegister - Vorlaufsatz: undefiniert");
        }
    }
    
    /**
     * Liefert einen String mit den Inhalten der Instanz zurueck
     * @return 
     */
    public String toString()
    {
        StringBuilder lvHelpString = new StringBuilder();
        
        lvHelpString.append("Institutsnummer: ");
        lvHelpString.append(ivInstitutsnummer);
        lvHelpString.append(StringKonverter.lineSeparator);
        lvHelpString.append("Buchungsdatum: ");
        lvHelpString.append(ivBuchungsdatum);
        lvHelpString.append(StringKonverter.lineSeparator);
        lvHelpString.append("Anzahl Finanzgeschaefte: ");
        lvHelpString.append(ivAnzahlFinanzgeschaefte);
        lvHelpString.append(StringKonverter.lineSeparator);
        lvHelpString.append("Timestamp: ");
        lvHelpString.append(ivTimestamp);
        lvHelpString.append(StringKonverter.lineSeparator);
        
        return lvHelpString.toString(); 
    }
}
