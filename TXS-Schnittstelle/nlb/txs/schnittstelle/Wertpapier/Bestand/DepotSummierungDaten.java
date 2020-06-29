/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Wertpapier.Bestand;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

public class DepotSummierungDaten
{
  /**
   * ISIN
   */
  private String ivISIN;

  /**
   * Depotnummer
   */
  private String ivDepotnummer;

  /**
   * Nominalbetrag
   */
  private String ivNominalbetrag;

  /**
   * Konstruktor
   */
  public DepotSummierungDaten()
  {
    ivISIN = new String();
    ivDepotnummer = new String();
    ivNominalbetrag = new String();
  }

  /**
   * Liefert die ISIN
   * @return
   */
  public String getISIN()
  {
    return ivISIN;
  }

  /**
   * Liefert die Depotnummer
   * Die Depotnummer kann aus mehreren einzelnen Nummern bestehen, die durch Unterstriche unterteilt sind.
   * @return
   */
  public String getDepotnummer()
  {
    return ivDepotnummer;
  }

  /**
   * Liefert den Nominalbetrag
   * @return
   */
  public String getNominalbetrag() {
    return ivNominalbetrag;
  }

  /**
   * Setzt die ISIN
   * @param pvISIN
   */
  public void setISIN(String pvISIN)
  {
    this.ivISIN = pvISIN;
  }

  /**
   * Setzt die Depotnummer
   * @param pvDepotnummer
   */
  public void setDepotnummer(String pvDepotnummer)
  {
    this.ivDepotnummer = pvDepotnummer;
  }

  /**
   * Setzt den Nominalbetrag
   * @param pvNominalbetrag
   */
  public void setNominalbetrag(String pvNominalbetrag)
  {
    this.ivNominalbetrag = pvNominalbetrag;
  }

  /**
   * Schreibt den Inhalt in einen String
   * @return
   */
  public String toString()
  {
    StringBuilder lvResultString = new StringBuilder();
    lvResultString.append("ISIN: " + ivISIN + " - Depotnummer: " + ivDepotnummer + ": " + ivNominalbetrag + StringKonverter.lineSeparator);
    return lvResultString.toString();
  }
}
