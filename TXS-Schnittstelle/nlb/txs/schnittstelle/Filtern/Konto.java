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
public class Konto 
{
  /**
   *   
   */
  private String ivKontonummer;
  
  /**
   * 
   */
  private boolean ivGefunden;
  
  /**
   * 
   * @param pvNr
   */
  public Konto(String pvNr)
  {
      this.ivKontonummer = pvNr;
      this.ivGefunden = false;
  }
  
  /**
   * Liefert eine Kontonummer
   * @return 
   */
  public String getKontonummer()
  {
      return this.ivKontonummer;
  }
  
  /**
   * Setzt die Kontonummer
   * @param pvNr 
   */
  public void setKontonummer(String pvNr)
  {
      this.ivKontonummer = pvNr;
  }
  
  /**
   * Wurde die Kontonummer gefunden?
   * @return 
   */
  public boolean isGefunden()
  {
      return this.ivGefunden;
  }
  
  /**
   * Setzt das 'Gefunden'-Flag
   * @param pvFlag 
   */
  public void setGefunden(boolean pvFlag)
  {
      this.ivGefunden = pvFlag;
  }
  
}
