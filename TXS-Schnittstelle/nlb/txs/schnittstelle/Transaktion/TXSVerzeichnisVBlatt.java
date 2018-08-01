/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPGrundbuch;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

/**
 * @author tepperc
 *
 */
public class TXSVerzeichnisVBlatt implements TXSTransaktion
{
    /**
     * Eindeutige Nummer des Verzeichnisblatts.
     * z.B.: 1234567890
     * Pflicht
     */
    private String ivVbnr;
    
    /**
     * Liefernde Bank
     * Pflicht
     */
    private String ivOrg;
    
    /**
     * Bestandsführendes System der Geschäftspartnerdaten.
     * z.B. SAP
     * Pflicht
     */
    private String ivQuelle;
    
    /**
     * Konstruktor
     */
    public TXSVerzeichnisVBlatt()
    {
        ivVbnr = new String();
        ivOrg = new String();
        ivQuelle = new String();
    }

    /**
     * @return the vbnr
     */
    public String getVbnr() {
        return this.ivVbnr;
    }

    /**
     * @param pvVbnr the vbnr to set
     */
    public void setVbnr(String pvVbnr) {
        this.ivVbnr = pvVbnr;
    }

    /**
     * @return the org
     */
    public String getOrg() {
        return this.ivOrg;
    }

    /**
     * @param pvOrg the org to set
     */
    public void setOrg(String pvOrg) {
        this.ivOrg = pvOrg;
    }

    /**
     * @return the quelle
     */
    public String getQuelle() {
        return this.ivQuelle;
    }

    /**
     * @param pvQuelle the quelle to set
     */
    public void setQuelle(String pvQuelle) {
        this.ivQuelle = pvQuelle;
    }

    /**
     * TXSVerzeichnisVBlattStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("      <txsi:vevb ");
    }
  
    /**
     * TXSVerzeichnisVBlatt in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
      StringBuffer lvHelpXML = new StringBuffer();
      
      lvHelpXML.append("vbnr=\"" + String2XML.change2XML(String2XML.rtrim(this.ivVbnr)) + "\" ");
      lvHelpXML.append("org=\"" + this.ivOrg + "\" ");
      lvHelpXML.append("quelle=\"" + this.ivQuelle + "\">\n"); 

      return lvHelpXML;
    }
  
  /**
   * TXSVerzeichnisVBlattEnde in die XML-Datei schreiben
   * @return 
   */
  public StringBuffer printTXSTransaktionEnde()
  {
      return new StringBuffer("      </txsi:vevb>\n");
  }

  /**
   * Importiert die Darlehensinformationen
   * @param pvModus 
   * @param pvDarlehen 
   * @param pvSicherheit  
   */
  public void importDarlehen(int pvModus, Darlehen pvDarlehen, Sicherheit pvSicherheit) 
  {
      this.ivVbnr = pvSicherheit.getObjektnummer();
      this.ivOrg = ValueMapping.changeMandant(pvDarlehen.getInstitutsnummer());
      switch (pvModus)
      {
          case DarlehenVerarbeiten.DARKA:
              this.ivQuelle = "ADARLPFBG";
              break;
          case DarlehenVerarbeiten.FLUGZEUGE:
              this.ivQuelle = "ADARLFLUG";
              break;
          case DarlehenVerarbeiten.SCHIFFE:
              this.ivQuelle = "ADARLSCHF";
              break;
          case DarlehenVerarbeiten.OEPG:
              this.ivQuelle = "ADARLOEPG";
              break;
          case DarlehenVerarbeiten.ALT:
              this.ivQuelle = "ADARLPFBG";
              break;
          case DarlehenVerarbeiten.DPP:
              this.ivQuelle = "DPP";
              break;
          default:
              System.out.println("TXSFinanzgeschaeft: Unbekannter Modus");
      }        
  }

  /**
   * @param pvZielDarlehen
   * @param sicherheit 
   * @param pvGb
   * @param pvInstitut 
   */
  public void importDarlehen(Darlehen pvZielDarlehen, OSPGrundbuch pvGb, String pvInstitut)   
  {
      this.ivVbnr = pvInstitut + "_" + pvGb.getNr();
      if (this.ivVbnr.length() > 40)
      {
          this.ivVbnr = this.ivVbnr.substring(0, 39);
      }
      
      this.ivOrg = ValueMapping.changeMandant(pvZielDarlehen.getInstitutsnummer());
      this.ivQuelle = "DPP";   
  }  
}
