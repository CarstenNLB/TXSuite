/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Filtern;

/**
 * @author tepperc
 *
 */
@Deprecated
public class Konto 
{
  /**
   *  Kontonummer
   */
  private String ivKontonummer;
  
  /**
   * Kennzeichen 'Gefunden'
   */
  private boolean ivGefunden;
  
  /**
   * Konstruktor
   * @param pvNr Kontonummer
   */
  public Konto(String pvNr)
  {
      this.ivKontonummer = pvNr;
      this.ivGefunden = false;
  }
  
  /**
   * Liefert die Kontonummer
   * @return Kontonummer
   */
  public String getKontonummer()
  {
      return this.ivKontonummer;
  }
  
  /**
   * Setzt die Kontonummer
   * @param pvNr Kontonummer
   */
  public void setKontonummer(String pvNr)
  {
      this.ivKontonummer = pvNr;
  }
  
  /**
   * Wurde die Kontonummer gefunden? (Abfrage Kennzeichen 'Gefunden')
   * @return true -> gefunden; false -> nicht gefunden
   */
  public boolean isGefunden()
  {
      return this.ivGefunden;
  }
  
  /**
   * Setzt das Kennzeichen 'Gefunden'
   * @param pvFlag true -> gefunden; false -> nicht gefunden
   */
  public void setGefunden(boolean pvFlag)
  {
      this.ivGefunden = pvFlag;
  }
  
}
