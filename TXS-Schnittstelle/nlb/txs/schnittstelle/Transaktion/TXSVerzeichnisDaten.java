/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPGrundbuch;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPKonto;
import nlb.txs.schnittstelle.Utilities.MappingDPP;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class TXSVerzeichnisDaten implements TXSTransaktion
{   
    /**
     * 
     */
    private String ivAbt;
    
    /**
     * 
     */
    private String ivArt;
    
    /**
     * 
     */
    private String ivAsichr;
    
    /**
     * 
     */
    private String ivBetrag;
    
    /**
     * 
     */
    private String ivEgleichl;
    
    /**
     * 
     */
    private String ivEindat;
    
    /**
     * 
     */
    private String ivEvorl;
    
    /**
     * 
     */
    private String ivFgleichl;
    
    /**
     * 
     */
    private String ivFvorl;
    
    /**
     * 
     */
    private String ivGbart;

    /**
     * 
     */
    private String ivGrdverh;
    
    /**
     * 
     */
    private String ivKat;
    
    
    /**
     * 
     */
    private String ivLoeschdat;
    
    /**
     * 
     */
    private String ivNrabt;
    
    /**
     * 
     */
    private String ivRang;
    
    /**
     * 
     */
    private String ivText;
 
    /**
     * 
     */
    private String ivWhrg;
    
    /**
     * 
     */
    private String ivZins;

    /**
     * Konstruktor
     */
    public TXSVerzeichnisDaten() 
    {
      initTXSVerzeichnisDaten();
    }

   /**
    * Initialisierung der Instanzvariablen mit leeren Strings
    */
    public void initTXSVerzeichnisDaten()
    {
        this.ivAbt = new String();
        this.ivArt = new String();
        this.ivAsichr = new String();
        this.ivBetrag = new String();
        this.ivEgleichl = new String();
        this.ivEindat = new String();
        this.ivEvorl = new String();
        this.ivFgleichl = new String();
        this.ivFvorl = new String();
        this.ivGbart = new String();
        this.ivGrdverh = new String();
        this.ivKat = new String();
        this.ivLoeschdat = new String();
        this.ivNrabt = new String();
        this.ivRang = new String();
        this.ivText = new String();
        this.ivWhrg = new String();
        this.ivZins = new String();
    }

    /**
     * @return the abt
     */
    public String getAbt() {
        return this.ivAbt;
    }

    /**
     * @param pvAbt the abt to set
     */
    public void setAbt(String pvAbt) {
        this.ivAbt = pvAbt;
    }

    /**
     * @return the art
     */
    public String getArt() {
        return this.ivArt;
    }

    /**
     * @param pvArt the art to set
     */
    public void setArt(String pvArt) {
        this.ivArt = pvArt;
    }

    /**
     * @return the asichr
     */
    public String getAsichr() {
        return this.ivAsichr;
    }

    /**
     * @param pvAsichr the asichr to set
     */
    public void setAsichr(String pvAsichr) {
        this.ivAsichr = pvAsichr;
    }

    /**
     * @return the betrag
     */
    public String getBetrag() {
        return this.ivBetrag;
    }

    /**
     * @param pvBetrag the betrag to set
     */
    public void setBetrag(String pvBetrag) {
        this.ivBetrag = pvBetrag;
    }

    /**
     * @return the egleichl
     */
    public String getEgleichl() {
        return this.ivEgleichl;
    }

    /**
     * @param pvEgleichl the egleichl to set
     */
    public void setEgleichl(String pvEgleichl) {
        this.ivEgleichl = pvEgleichl;
    }

    /**
     * @return the eindat
     */
    public String getEindat() {
        return this.ivEindat;
    }

    /**
     * @param pvEindat the eindat to set
     */
    public void setEindat(String pvEindat) {
        this.ivEindat = pvEindat;
    }

    /**
     * @return the evorl
     */
    public String getEvorl() {
        return this.ivEvorl;
    }

    /**
     * @param pvEvorl the evorl to set
     */
    public void setEvorl(String pvEvorl) {
        this.ivEvorl = pvEvorl;
    }

    /**
     * @return the fgleichl
     */
    public String getFgleichl() {
        return this.ivFgleichl;
    }

    /**
     * @param pvFgleichl the fgleichl to set
     */
    public void setFgleichl(String pvFgleichl) {
        this.ivFgleichl = pvFgleichl;
    }

    /**
     * @return the fvorl
     */
    public String getFvorl() {
        return this.ivFvorl;
    }

    /**
     * @param pvFvorl the fvorl to set
     */
    public void setFvorl(String pvFvorl) {
        this.ivFvorl = pvFvorl;
    }

    /**
     * @return the gbart
     */
    public String getGbart() {
        return this.ivGbart;
    }

    /**
     * @param pvGbart the gbart to set
     */
    public void setGbart(String pvGbart) {
        this.ivGbart = pvGbart;
    }

    /**
     * @return the grdverh
     */
    public String getGrdverh() {
        return this.ivGrdverh;
    }

    /**
     * @param pvGrdverh the grdverh to set
     */
    public void setGrdverh(String pvGrdverh) {
        this.ivGrdverh = pvGrdverh;
    }

    /**
     * @return the kat
     */
    public String getKat() {
        return this.ivKat;
    }

    /**
     * @param pvKat the kat to set
     */
    public void setKat(String pvKat) {
        this.ivKat = pvKat;
    }

    /**
     * @return the loeschdat
     */
    public String getLoeschdat() {
        return this.ivLoeschdat;
    }

    /**
     * @param pvLoeschdat the loeschdat to set
     */
    public void setLoeschdat(String pvLoeschdat) {
        this.ivLoeschdat = pvLoeschdat;
    }

    /**
     * @return the nrabt
     */
    public String getNrabt() {
        return this.ivNrabt;
    }

    /**
     * @param pvNrabt the nrabt to set
     */
    public void setNrabt(String pvNrabt) {
        this.ivNrabt = pvNrabt;
    }

    /**
     * @return the rang
     */
    public String getRang() {
        return this.ivRang;
    }

    /**
     * @param pvRang the rang to set
     */
    public void setRang(String pvRang) {
        this.ivRang = pvRang;
    }

    /**
     * @return the text
     */
    public String getText() {
        return this.ivText;
    }

    /**
     * @param pvText the text to set
     */
    public void setText(String pvText) {
        this.ivText = pvText;
    }

    /**
     * @return the whrg
     */
    public String getWhrg() {
        return this.ivWhrg;
    }

    /**
     * @param pvWhrg the whrg to set
     */
    public void setWhrg(String pvWhrg) {
        this.ivWhrg = pvWhrg;
    }

    /**
     * @return the zins
     */
    public String getZins() {
        return this.ivZins;
    }

    /**
     * @param pvZins the zins to set
     */
    public void setZins(String pvZins) {
        this.ivZins = pvZins;
    }

    /**
     * TXSVerzeichnisDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("      <txsi:vedaten ");
    }
    
    /**
     * TXSVerzeichnisDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        if (this.ivAbt.length() > 0)
        {
            lvHelpXML.append("abt=\"" + this.ivAbt + "\" ");
        }
        if (this.ivArt.length() > 0)
        {
          lvHelpXML.append("art=\"" + this.ivArt + "\" ");    
        }     
        if (this.ivAsichr.length() > 0)
        {
            lvHelpXML.append("asichr=\"" + String2XML.change2XML(this.ivAsichr) + "\" ");
        }
        if (this.ivGbart.length() > 0)
        {
            lvHelpXML.append("gbart=\"" + this.ivGbart +  "\" ");
        }
        if (this.ivKat.length() > 0)
        {
            lvHelpXML.append("kat=\"" + this.ivKat + "\" ");
        }
        if (this.ivNrabt.length() > 0)
        {
          lvHelpXML.append("nrabt=\"" + this.ivNrabt + "\" ");
        }
        if (this.ivRang.length() > 0)
        {
            lvHelpXML.append("rang=\"" + this.ivRang + "\" ");            
        }
        if (this.ivBetrag.length() > 0)
        {
          lvHelpXML.append("betrag=\"" + this.ivBetrag
                           + "\" whrg=\"" + this.ivWhrg + "\" ");
        }
        lvHelpXML.append(">");
        return lvHelpXML;
    }
    
    /**
     * TXSVerzeichnisDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
      return new StringBuffer("</txsi:vedaten>\n");
    }

    /**
     * @param pvDarlehen 
     * @param pvSicherheit 
     * @param pvObj 
     * @return 
     * 
     */
    public boolean importDarlehen(Darlehen pvDarlehen, Sicherheit pvSicherheit, Sicherungsobjekt pvObj)
    {
        BigDecimal lvBtrDivHd = new BigDecimal("0.01");
        BigDecimal lvWork = new BigDecimal("0.0");
        BigDecimal lvBuerge_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenUKAP_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenRKAP_Fakt = new BigDecimal("1.0");
        
        if (pvDarlehen.getKredittyp().equals("4"))
        { /* mit B�rge .. anteilig */
         if (StringKonverter.convertString2Double(pvDarlehen.getBuergschaftProzent()) != 0.0)
         { /* nichts da */
          lvBuerge_Fakt = lvBtrDivHd.multiply(StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftProzent()));
         } /* etwas da */
        } /* mit B�rge .. anteilig */
        /* Konsortiale ..... Summe der anderen, nur bei korrektem Schl.... */
        if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) > 0 &&
                StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) < 20)
        { /* Bedeutet Kons.*/
         if (StringKonverter.convertString2Double(pvDarlehen.getUrsprungsKapital()) != 0 )
         { /* Anteil rechnen */
          lvWork = StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital()).subtract(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeUKAP()));
          lvKonEigenUKAP_Fakt = lvBuerge_Fakt.multiply(
                              (lvWork.divide(StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital()), 9, RoundingMode.HALF_UP))); /* Max. 1 */
         } /* Anteil rechnen */
         if (StringKonverter.convertString2Double(pvDarlehen.getRestkapital()) != 0 )
         { /* Anteil rechnen */
          lvWork = StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital()).subtract(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeRKAP()));
          lvKonEigenRKAP_Fakt = lvBuerge_Fakt.multiply(
                              (lvWork.divide(StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital()), 9, RoundingMode.HALF_UP))); /* Max. 1 */
         } /* Anteil rechnen */
        } /* Bedeutet Kons.*/
        else
        { /* Nicht konsortial */
         lvKonEigenUKAP_Fakt = lvBuerge_Fakt;
         lvKonEigenRKAP_Fakt = lvBuerge_Fakt;
        } /* Nicht konsortial */

        /* dKonEigenUKAP_Fakt wird vom dKonEigenRKAP_Fakt gesetzt ! */
        lvKonEigenUKAP_Fakt = lvKonEigenRKAP_Fakt;
        
        //BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvSicherheit.getZuweisungsbetrag()).multiply(lvKonEigenUKAP_Fakt);
        BigDecimal lvVerfBetrag = StringKonverter.convertString2BigDecimal(pvSicherheit.getVerfuegungsbetrag()).multiply(lvKonEigenUKAP_Fakt);
        
        //System.out.println("TXSVerzeichnisDaten: dZuwBetrag: " + dZuwBetrag + " dVerfBetrag:" + dVerfBetrag);
        
        //sicherheit.setVerfuegungsbetrag((new Double(dVerfBetrag)).toString());
        //sicherheit.setZuweisungsbetrag((new Double(dZuwBetrag)).toString());
        
        BigDecimal lvVerfBetragBu = new BigDecimal("0.0");
        BigDecimal lvVerfBetragRe = new BigDecimal("0.0");
        //BigDecimal lvZuwBetragBu = new BigDecimal("0.0");
        //BigDecimal lvZuwBetragRe = new BigDecimal("0.0");
        if (pvDarlehen.getKredittyp().equals("1"))
        { /* ohne B�rge .. alles */
         lvVerfBetragRe = lvVerfBetrag;
         //lvZuwBetragRe  = lvZuwBetrag;
        } /* ohne B�rge .. alles */
        if (pvDarlehen.getKredittyp().equals("2"))
        { /* mit B�rge .. anteilig */
         if (StringKonverter.convertString2Double(pvDarlehen.getBuergschaftProzent()) == 0.0)
         { /* nichts da */
          lvVerfBetragRe = lvVerfBetrag;
          //lvZuwBetragRe  = lvZuwBetrag;
         } /* nichts da */
         else
         { /* etwas da */
            lvVerfBetragBu = lvVerfBetrag.multiply(lvBtrDivHd).multiply(
                            StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftProzent()));
            lvVerfBetragRe = lvVerfBetrag.subtract(lvVerfBetragBu);
            //lvZuwBetragBu  = lvZuwBetrag.multiply(lvBtrDivHd).multiply(
            //                StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftProzent()));
            //lvZuwBetragRe  = lvZuwBetrag.subtract(lvZuwBetragBu);
         } /* etwas da */
        } /* mit B�rge .. anteilig */
        
      this.ivAbt = "3";
      this.ivGbart = "1"; 
      this.ivKat = "1";
      this.ivNrabt = pvObj.getLaufendeNummer();
      this.ivBetrag = lvVerfBetragRe.toString();
      this.ivWhrg = pvDarlehen.getSatzwaehrung();

      return true;
    }

    /**
     * @param pvZielDarlehen
     * @param pvKonto 
     * @param pvGrundbuch 
     * @param pvRang 
     * @return 
     */
    public boolean importDarlehen(Darlehen pvZielDarlehen, OSPKonto pvKonto, OSPGrundbuch pvGrundbuch, String pvRang) 
    {
      //System.out.println("TXSVerzeichnisDaten: dZuwBetrag: " + dZuwBetrag + " dVerfBetrag:" + dVerfBetrag);
             
      if (pvGrundbuch.getAbteilung() != null)
      {
        this.ivAbt = pvGrundbuch.getAbteilung();
      }
      if (pvGrundbuch.getAbteilungNr() != null)
      {
        this.ivNrabt = pvGrundbuch.getAbteilungNr();
      }
      if (pvGrundbuch.getArt() != null)
      {
        this.ivGbart = MappingDPP.changeGrundbuchart(pvGrundbuch.getArt()); 
      }
      this.ivRang = pvRang;
      this.ivKat = "1";
      //this.nrabt = sObj.getLaufendeNummer();
      this.ivBetrag = pvKonto.getZuweisungsbetrag();
      this.ivWhrg = pvKonto.getWaehrung();

      return true;
    }
}
