/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.Cashflows.Daten;

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
     * @param pvZeile 
     * @return 
     *       
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
     * @param pvPos Position
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
     * @return the quellsystem
     */
    public String getQuellsystem() {
        return this.ivQuellsystem;
    }

    /**
     * @param pvQuellsystem the quellsystem to set
     */
    public void setQuellsystem(String pvQuellsystem) {
        this.ivQuellsystem = pvQuellsystem;
    }

    /**
     * @return the kontonummer
     */
    public String getKontonummer() {
        return this.ivKontonummer;
    }

    /**
     * @param pvKontonummer the kontonummer to set
     */
    public void setKontonummer(String pvKontonummer) {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * @return the iSIN
     */
    public String getISIN() {
        return this.ivISIN;
    }

    /**
     * @param pvISIN the iSIN to set
     */
    public void setISIN(String pvISIN) {
        this.ivISIN = pvISIN;
    }

    /**
     * @return the institutsnummer
     */
    public String getInstitutsnummer() {
        return this.ivInstitutsnummer;
    }

    /**
     * @param pvInstitutsnummer the institutsnummer to set
     */
    public void setInstitutsnummer(String pvInstitutsnummer) {
        this.ivInstitutsnummer = pvInstitutsnummer;
    }

    /**
     * @return the artNummer
     */
    public String getArtNummer() {
        return this.ivArtNummer;
    }

    /**
     * @param pvArtNummer the artNummer to set
     */
    public void setArtNummer(String pvArtNummer) {
        this.ivArtNummer = pvArtNummer;
    }

    /**
     * @return the wertstellungsdatum
     */
    public String getWertstellungsdatum() {
        return this.ivWertstellungsdatum;
    }

    /**
     * @param pvWertstellungsdatum the wertstellungsdatum to set
     */
    public void setWertstellungsdatum(String pvWertstellungsdatum) {
        this.ivWertstellungsdatum = pvWertstellungsdatum;
    }

    /**
     * @return the buchungsdatum
     */
    public String getBuchungsdatum() {
        return this.ivBuchungsdatum;
    }

    /**
     * @param pvBuchungsdatum the buchungsdatum to set
     */
    public void setBuchungsdatum(String pvBuchungsdatum) {
        this.ivBuchungsdatum = pvBuchungsdatum;
    }

    /**
     * @return the waehrung
     */
    public String getWaehrung() {
        return this.ivWaehrung;
    }

    /**
     * @param pvWaehrung the waehrung to set
     */
    public void setWaehrung(String pvWaehrung) {
        this.ivWaehrung = pvWaehrung;
    }

    /**
     * @return the wert
     */
    public String getWert() {
        return this.ivWert;
    }

    /**
     * @param pvWert the wert to set
     */
    public void setWert(String pvWert) {
        this.ivWert = pvWert;
    }

    /**
     * @return the bruttoNetto
     */
    public String getBruttoNetto() {
        return this.ivBruttoNetto;
    }

    /**
     * @param pvBruttoNetto the bruttoNetto to set
     */
    public void setBruttoNetto(String pvBruttoNetto) {
        this.ivBruttoNetto = pvBruttoNetto;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Cashflow pvCashflow) 
    {
        return DatumUtilities.changeDate(this.getBuchungsdatum()).replace("-","").compareTo(DatumUtilities.changeDate(pvCashflow.getBuchungsdatum()).replace("-",""));
    }
}
