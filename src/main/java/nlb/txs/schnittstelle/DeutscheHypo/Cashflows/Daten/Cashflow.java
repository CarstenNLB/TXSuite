/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.DeutscheHypo.Cashflows.Daten;

public class Cashflow implements Comparable<Cashflow> {

  /**
   * Kontonummer
   */
  private String ivKontonummer;

  /**
   * Datum
   */
  private String ivDatum;

  /**
   * Cashflow-Type (Tilgung oder Zinsen)
   */
  private String ivCashflowType;

  /**
   * Waehrung
   */
  private String ivWaehrung;

  /**
   * Betrag (Summe Cashflow)
   */
  private String ivBetrag;

  /**
   * Deckungsstock
   * Wird nicht benoetigt, da die Deckungsstock Zugehoerigkeit aus der Quellsystem-Datei
   * ermittelt wird.
   */
  private String ivDeckungsstock;

  /**
   * Bedingung
   * Wird nicht benoetigt.
   */
  private String ivBedingung;

  /**
   * Recht
   * Wird nicht benoetigt.
   */
  private String ivRecht;

  /**
   * Konstruktor
   */
  public Cashflow()
  {
    this.ivKontonummer = new String();
    this.ivDatum = new String();
    this.ivCashflowType = new String();
    this.ivWaehrung = new String();
    this.ivBetrag = new String();
    this.ivDeckungsstock = new String();
    this.ivBedingung = new String();
    this.ivRecht = new String();
  }

  /**
   * @param pvZeile Die in einzelne Felder aufzuteilende Zeile
   * @return Immer true
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
        this.ivKontonummer = pvWert;
        break;
      case 1:
        this.ivDatum = pvWert;
        break;
      case 2:
        this.ivCashflowType = pvWert;
        break;
      case 3:
        this.ivWaehrung = pvWert;
        break;
      case 4:
        if (pvWert.startsWith("-"))
        {
          pvWert = pvWert.substring(1);
        }
        this.ivBetrag = pvWert.replace("," , ".");
        break;
      case 5:
        this.ivDeckungsstock = pvWert;
        break;
      case 6:
        this.ivBedingung = pvWert;
        break;
      case 7:
        this.ivRecht = pvWert;
        break;
       default:
        System.out.println("Cashflow: Feld " + pvPos + " undefiniert");
    }
  }

  /**
   * Liefert die Kontonummer
   * @return Kontonummer
   */
  public String getKontonummer() {
    return ivKontonummer;
  }

  /**
   * Setzt die Kontonummer
   * @param pvKontonummer die zu setzende Kontonummer
   */
  public void setKontonummer(String pvKontonummer) {
    this.ivKontonummer = pvKontonummer;
  }

  /**
   * Liefert das Datum
   * @return Datum
   */
  public String getDatum() {
    return ivDatum;
  }

  /**
   * Setzt das Datum
   * @param pvDatum das zu setzende Datum
   */
  public void setDatum(String pvDatum) {
    this.ivDatum = pvDatum;
  }

  /**
   * Liefert den Cashflow-Type
   * @return Cashflow-Type
   */
  public String getCashflowType() {
    return ivCashflowType;
  }

  /**
   * Setzt den Cashflow-Type
   * @param pvCashflowType den zu setzenden Cashflow-Type
   */
  public void setCashflowType(String pvCashflowType) {
    this.ivCashflowType = pvCashflowType;
  }

  /**
   * Liefert die Waehrung
   * @return Waehrung
   */
  public String getWaehrung() {
    return ivWaehrung;
  }

  /**
   * Setzt die Waehrung
   * @param pvWaehrung die zu setzende Waehrung
   */
  public void setWaehrung(String pvWaehrung) {
    this.ivWaehrung = pvWaehrung;
  }

  /**
   * Liefert den Betrag
   * @return Betrag
   */
  public String getBetrag() {
    return ivBetrag;
  }

  /**
   * Setzt den Betrag
   * @param pvBetrag der zu setzende Betrag
   */
  public void setBetrag(String pvBetrag) {
    this.ivBetrag = pvBetrag;
  }

  /**
   * Liefert den Deckungsstock
   * @return Deckungsstock
   */
  public String getDeckungsstock() {
    return ivDeckungsstock;
  }

  /**
   * Setzt den Deckungsstock
   * @param pvDeckungsstock den zu setzenden Deckungsstock
   */
  public void setDeckungsstock(String pvDeckungsstock) {
    this.ivDeckungsstock = pvDeckungsstock;
  }

  /**
   * Liefert die Bedingung
   * @return Bedingung
   */
  public String getBedingung() {
    return ivBedingung;
  }

  /**
   * Setzt die Bedingung
   * @param pvBedingung die zu setzende Bedingung
   */
  public void setBedingung(String pvBedingung) {
    this.ivBedingung = pvBedingung;
  }

  /**
   * Liefert das Recht
   * @return Recht
   */
  public String getRecht() {
    return ivRecht;
  }

  /**
   * Setzt das Recht
   * @param pvRecht das zu setzende Recht
   */
  public void setRecht(String pvRecht) {
    this.ivRecht = pvRecht;
  }

  /**
   * Vergleicht dieses Datum mit dem Datum des Paramter Cashflows
   * @see Comparable#compareTo(Object)
   */
  public int compareTo(Cashflow pvCashflow)
  {
    return this.getDatum().compareTo(pvCashflow.getDatum());
  }
}
