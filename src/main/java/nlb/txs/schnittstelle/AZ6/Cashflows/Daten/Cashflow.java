/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.AZ6.Cashflows.Daten;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;

/**
 * @author tepperc
 *
 */
public class Cashflow implements Comparable<Cashflow>
{
    /**
     * Quellsystem
     */
    private String ivQuellsystem;
    
    /**
     * Externer Key/Kontonummer
     */
    private String ivKontonummer;

    /**
     * ISIN 
     */
    private String ivISIN;
    
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Ablaufart-Nummer
     */
    private String ivArtNummer;
    
    /**
     * Wertstellungsdatum (DATWE)
     */
    private String ivWertstellungsdatum;
    
    /**
     * Buchungsdatum (DATBU)
     */
    private String ivBuchungsdatum;
    
    /**
     * Waehrung
     */
    private String ivWaehrung;
    
    /**
     * Wert/Betrag
     */
    private String ivWert;
    
    /**
     * Kennzeichen BruttoNetto
     */
    private String ivBruttoNetto;

    /**
     * Konstruktor - Initialisiert alle Variablen mit einem leeren String
     */
    public Cashflow() 
    {
        super();
        this.ivQuellsystem = new String();
        this.ivKontonummer = new String();
        this.ivISIN = new String();
        this.ivInstitutsnummer = new String();
        this.ivArtNummer = new String();
        this.ivWertstellungsdatum = new String();
        this.ivBuchungsdatum = new String();
        this.ivWaehrung = new String();
        this.ivWert = new String();
        this.ivBruttoNetto = new String();
    }
    
    /**
     * Zerlegt eine Cashflow-Zeile in die einzelnen Felder
     * @param pvZeile Cashflow-Zeile
     * @return Immer 'true'
     */
   public boolean parseCashflow(String pvZeile)
   {                 
      String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
      int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
      int    lvZzStr=0;              // pointer fuer satzbereich
            
      // Steuerung/Iteration Eingabesatz
      for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
      {

        // wenn Semikolon erkannt
        if (pvZeile.charAt(lvZzStr) == ';')
        {
            //System.out.println(lvTemp);
            this.setCashflow(lvLfd, lvTemp);
            
            lvLfd++;                  // naechste Feldnummer
            // loeschen Zwischenbuffer
            lvTemp = new String();
        }
        else
        {
            // uebernehmen Byte aus Eingabesatzposition
            lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
        }
    } // ende for  
     
    return true; 
   }

    /**
     * Setzt einen Wert des Cashflows
     * @param pvPos Feldposition
     * @param pvWert Wert

     */
    public void setCashflow(int pvPos, String pvWert) 
    {
        switch (pvPos)
        {
          case 0:
              this.ivQuellsystem = pvWert;
              break;
          case 1:
              this.ivKontonummer = pvWert;
              break;
          case 2:
              this.ivISIN = pvWert;
              break;
          case 3:
              this.ivInstitutsnummer = pvWert;
              break;
          case 4:
              this.ivArtNummer = pvWert;
              break;
          case 5:
              this.ivWertstellungsdatum = pvWert;
              break;
          case 6:
              this.ivBuchungsdatum = pvWert;
              break;
          case 7:
              this.ivWaehrung = pvWert;
              break;
          case 8:
        	  if (pvWert.startsWith("-"))
        	  {
        		  pvWert = pvWert.substring(1);
        	  }
              this.ivWert = pvWert.replace(",", ".");
              break;
          case 9:
              this.ivBruttoNetto = pvWert;
              break;
          default:
              System.out.println("Cashflow: Feld " + pvPos + " undefiniert");
        }
    }
    
    /**
     * Liefert das Quellsystem
     * @return the quellsystem
     */
    public String getQuellsystem() {
        return this.ivQuellsystem;
    }

    /**
     * Setzt das Quellsystem
     * @param pvQuellsystem das zu setzende Quellsystem
     */
    public void setQuellsystem(String pvQuellsystem) {
        this.ivQuellsystem = pvQuellsystem;
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
     * @param pvKontonummer die zu setzende Kontonummer
     */
    public void setKontonummer(String pvKontonummer) {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * Liefert die ISIN
     * @return the iSIN
     */
    public String getISIN() {
        return this.ivISIN;
    }

    /**
     * Setzt die ISIN
     * @param pvISIN the iSIN to set
     */
    public void setISIN(String pvISIN) {
        this.ivISIN = pvISIN;
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
     * Liefert die Ablaufart-Nummer
     * @return the artNummer
     */
    public String getArtNummer() {
        return this.ivArtNummer;
    }

    /**
     * Setzt die Ablaufart-Nummer
     * @param pvArtNummer the artNummer to set
     */
    public void setArtNummer(String pvArtNummer) {
        this.ivArtNummer = pvArtNummer;
    }

    /**
     * Liefert das Wertstellungsdatum
     * @return the wertstellungsdatum
     */
    public String getWertstellungsdatum() {
        return this.ivWertstellungsdatum;
    }

    /**
     * Setzt das Wertstellungsdatum
     * @param pvWertstellungsdatum the wertstellungsdatum to set
     */
    public void setWertstellungsdatum(String pvWertstellungsdatum) {
        this.ivWertstellungsdatum = pvWertstellungsdatum;
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
     * Liefert die Waehrung
     * @return the waehrung
     */
    public String getWaehrung() {
        return this.ivWaehrung;
    }

    /**
     * Setzt die Waehrung
     * @param pvWaehrung the waehrung to set
     */
    public void setWaehrung(String pvWaehrung) {
        this.ivWaehrung = pvWaehrung;
    }

    /**
     * Liefert den Wert
     * @return the wert
     */
    public String getWert() {
        return this.ivWert;
    }

    /**
     * Setzt den Wert
     * @param pvWert the wert to set
     */
    public void setWert(String pvWert) {
        this.ivWert = pvWert;
    }

    /**
     * Liefert das BruttoNetto-Kennzeichen
     * @return the bruttoNetto
     */
    public String getBruttoNetto() {
        return this.ivBruttoNetto;
    }

    /**
     * Setzt das BruttoNetto-Kennzeichen
     * @param pvBruttoNetto the bruttoNetto to set
     */
    public void setBruttoNetto(String pvBruttoNetto) {
        this.ivBruttoNetto = pvBruttoNetto;
    }

    /**
     * Vergleicht das Buchungsdatum auf die Reihenfolge
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Cashflow pvCashflow)
    {
        return DatumUtilities.changeDate(this.getBuchungsdatum()).replace("-","").compareTo(DatumUtilities.changeDate(pvCashflow.getBuchungsdatum()).replace("-",""));
    }
}
