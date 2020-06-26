/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPGrundbuch;
import nlb.txs.schnittstelle.Utilities.String2XML;


/**
 * @author tepperc
 *
 */
public class TXSVerzeichnisblattDaten implements TXSTransaktion
{
   /**
    * 
    */
   private String ivBand;
   
   /**
    * 
    */
   private String ivBlatt;
    
   /**
    * 
    */ 
   private String ivGbvon;
   
   /**
    * 
    */
   private String ivGericht;
   
   /**
    * 
    */
   private String ivKat;
   
  /**
   * Konstruktor
   */
   public TXSVerzeichnisblattDaten() 
   {
     initTXSVerzeichnisblattDaten();
   }

  /**
   * Initialisierung der Instanzvariablen mit leeren Strings
   */
  public void initTXSVerzeichnisblattDaten()
  {
     this.ivBand = new String();
     this.ivBlatt = new String();
     this.ivGbvon = new String();
     this.ivGericht = new String();
     this.ivKat = new String();
   }

   /**
    * @return the band
    */
   public String getBand() 
   {
       return this.ivBand;
   }

  /**
   * @param pvBand the band to set
   */
  public void setBand(String pvBand) 
  {
    this.ivBand = pvBand;
  }

  /**
   * @return the blatt
   */
  public String getBlatt() 
  {
    return this.ivBlatt;
  }

  /**
   * @param pvBlatt the blatt to set
   */
  public void setBlatt(String pvBlatt) 
  {
    this.ivBlatt = pvBlatt;
  }

  /**
   * @return the gbvon
   */
  public String getGbvon() 
  {
    return this.ivGbvon;
  }

  /**
   * @param pvGbvon the gbvon to set
   */
  public void setGbvon(String pvGbvon) 
  {
    this.ivGbvon = pvGbvon;
  }

  /**
   * @return the gericht
   */
  public String getGericht() 
  {
    return this.ivGericht;
  }

  /**
   * @param pvGericht the gericht to set
   */
  public void setGericht(String pvGericht) 
  {
    this.ivGericht = pvGericht;
  }

  /**
   * @return the kat
   */
  public String getKat() 
  {
    return this.ivKat;
  }

  /**
   * @param pvKat the kat to set
   */
  public void setKat(String pvKat) 
  {
    this.ivKat = pvKat;
  }
  
  /**
   * TXSVerzeichnisblattDatenStart in die XML-Datei schreiben
   * @return 
   */
  public StringBuffer printTXSTransaktionStart()
  {
      return new StringBuffer("        <txsi:vbdaten ");
  }
  
  /**
   * TXSVerzeichnisblattDaten in die XML-Datei schreiben
   * @return 
   */
  public StringBuffer printTXSTransaktionDaten()
  {
      StringBuffer lvHelpXML = new StringBuffer();
      
      if (this.ivBand.length() > 0)
      {
        lvHelpXML.append("band=\"" + String2XML.change2XML(this.ivBand) + "\" ");
      }
      if (this.ivKat.length() > 0)
      {
          lvHelpXML.append("kat=\"" + this.ivKat + "\" ");
      }
      if (this.ivGericht.length() > 0)
      {
          lvHelpXML.append("gericht=\"" + String2XML.change2XML(this.ivGericht) + "\" ");
      }
      lvHelpXML.append("blatt=\"" + String2XML.change2XML(this.ivBlatt)
                      + "\" gbvon=\"" + String2XML.change2XML(this.ivGbvon)
                      + "\">");
      return lvHelpXML;
  }
  
  /**
   * TXSVerzeichnisblattDatenEnde in die XML-Datei schreiben
   * @return 
   */
  public StringBuffer printTXSTransaktionEnde()
  {
      return new StringBuffer("</txsi:vbdaten>\n");
  }

  /**
   * @param pvObj 
   * 
   */
  public void importDarlehen(Sicherungsobjekt pvObj) 
  {
      this.ivBand = pvObj.getBand(); //.trim());
      this.ivBlatt = pvObj.getBlatt(); //.trim());
      this.ivGbvon = pvObj.getGrundbuch(); //.trim());
      this.ivKat= "1";
  }

  /**
   * @param pvGb
   */
  public void importDarlehen(OSPGrundbuch pvGb) 
  {
      this.ivBand = pvGb.getBand(); //.trim());
      this.ivBlatt = pvGb.getBlatt(); //.trim());
      this.ivGbvon = pvGb.getBezirk(); //.trim());
      this.ivKat= "1";
  }

   
}
