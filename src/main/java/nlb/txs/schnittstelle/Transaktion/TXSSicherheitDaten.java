/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPSicherheit;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
import nlb.txs.schnittstelle.Utilities.MappingDPP;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class TXSSicherheitDaten implements TXSTransaktion
{
    /**
     * Art der Sicherheit
     */
    private String ivArt;
    
    /**
     * Bemerkungszusatz
     */
    private String ivBemzus;
    
    /**
     * Bezeichnung
     */
    private String ivBez;
    
    /**
     * Eigene Gleichlasten
     */
    private String ivEgleichl;
    
    /**
     * Eigene Vorlasten
     */
    private String ivEvorl;
    
    /**
     * Fremde Gleichlasten
     */
    private String ivFgleichl;
    
    /**
     * Fremde Vorlasten
     */
    private String ivFvorl;
    
    /**
     * Geprueft
     */
    private String ivGepr;
    
    /**
     * Info Konsortialgesch�ft
     */
    private String ivKoninfo;
    
    /**
     * Nennbetrag der Sicherheit
     */
    private String ivNbetrag;
    
    /**
     * Prozentualer Anteil einer Sicherheitenabtretung von ihrer Originalsicherheit
     */
    private String ivOrigsichant;
    
    /**
     * Buergschaft-Prozent
     */
    private String ivPbsatz;
    
    /**
     * Refinanzierungsregister Info
     */
    private String ivReginfo;
    
    /**
     * Rechtlicher Grund
     */
    private String ivRgrund;
    
    /**
     * Rechtlicher Grund vom
     */
    private String ivRgrunddat;
    
    /**
     * Tilgungsersatzleistungen
     */
    private String ivTilgers;
    
    /**
     * Verfuegungsbetrag
     */
    private String ivVbetrag;
    
    /**
     * Verwahrnummer
     */
    private String ivVerwnr;
    
    /**
     * Betragswaehrung
     */
    private String ivWhrg;

    /**
     * Benutzerdefiniertes Feld Buergschaft-Prozent
     */
    private String ivProbsatz;
        
    /**
     * Refi-Register: Datum einer separaten Sicherungszweckerklaerung
     */
    private String ivDsepsichzerkl;
    
    /**
     * Konstruktor
     */
    public TXSSicherheitDaten() 
    {
        initTXSSicherheitDaten();
    }

    /**
     * Alle Variablen werden mit einem leeren String initialisiert.
     */
    public void initTXSSicherheitDaten()
    {
        this.ivArt = new String();
        this.ivBemzus = new String();
        this.ivBez = new String();
        this.ivEgleichl = new String();
        this.ivEvorl = new String();
        this.ivFgleichl = new String();
        this.ivFvorl = new String();
        this.ivGepr = new String();
        this.ivKoninfo = new String();
        this.ivNbetrag = new String();
        this.ivOrigsichant = new String();
        this.ivPbsatz = new String();
        this.ivReginfo = new String();
        this.ivRgrund = new String();
        this.ivRgrunddat = new String();
        this.ivTilgers = new String();
        this.ivVbetrag = new String();
        this.ivVerwnr = new String();
        this.ivWhrg = new String();
        this.ivProbsatz = new String();
        this.ivDsepsichzerkl = new String();
    }

    /**
     * @return the art
     */
    public String getArt() 
    {
        return this.ivArt;
    }

    /**
     * @param pvArt the art to set
     */
    public void setArt(String pvArt) 
    {
        this.ivArt = pvArt;
    }

    /**
     * @return the bemzus
     */
    public String getBemzus() {
        return this.ivBemzus;
    }

    /**
     * @param pvBemzus the bemzus to set
     */
    public void setBemzus(String pvBemzus) {
        this.ivBemzus = pvBemzus;
    }

    /**
     * @return the bez
     */
    public String getBez() 
    {
        return this.ivBez;
    }

    /**
     * @param pvBez the bez to set
     */
    public void setBez(String pvBez) 
    {
        this.ivBez = pvBez;
    }

    /**
     * @return the egleichl
     */
    public String getEgleichl() 
    {
        return this.ivEgleichl;
    }

    /**
     * @param pvEgleichl the egleichl to set
     */
    public void setEgleichl(String pvEgleichl) 
    {
        this.ivEgleichl = pvEgleichl;
    }

    /**
     * @return the evorl
     */
    public String getEvorl() 
    {
        return this.ivEvorl;
    }

    /**
     * @param pvEvorl the evorl to set
     */
    public void setEvorl(String pvEvorl) 
    {
        this.ivEvorl = pvEvorl;
    }

    /**
     * @return the fgleichl
     */
    public String getFgleichl() 
    {
        return this.ivFgleichl;
    }

    /**
     * @param pvFgleichl the fgleichl to set
     */
    public void setFgleichl(String pvFgleichl) 
    {
        this.ivFgleichl = pvFgleichl;
    }

    /**
     * @return the fvorl
     */
    public String getFvorl() 
    {
        return this.ivFvorl;
    }

    /**
     * @param pvFvorl the fvorl to set
     */
    public void setFvorl(String pvFvorl) 
    {
        this.ivFvorl = pvFvorl;
    }

    /**
     * @return the gepr
     */
    public String getGepr() 
    {
        return this.ivGepr;
    }

    /**
     * @param pvGepr the gepr to set
     */
    public void setGepr(String pvGepr) 
    {
        this.ivGepr = pvGepr;
    }

    /**
     * @return the koninfo
     */
    public String getKoninfo() 
    {
        return this.ivKoninfo;
    }

    /**
     * @param pvKoninfo the koninfo to set
     */
    public void setKoninfo(String pvKoninfo) 
    {
        this.ivKoninfo = pvKoninfo;
    }

    /**
     * @return the nbetrag
     */
    public String getNbetrag() 
    {
        return this.ivNbetrag;
    }

    /**
     * @param pvNbetrag the nbetrag to set
     */
    public void setNbetrag(String pvNbetrag) 
    {
        this.ivNbetrag = pvNbetrag;
    }

    /**
     * @return the origsichant
     */
    public String getOrigsichant() {
        return this.ivOrigsichant;
    }

    /**
     * @param pvOrigsichant the origsichant to set
     */
    public void setOrigsichant(String pvOrigsichant) {
        this.ivOrigsichant = pvOrigsichant;
    }

    /**
     * @return the pbsatz
     */
    public String getPbsatz() 
    {
        return this.ivPbsatz;
    }

    /**
     * @param pvPbsatz the pbsatz to set
     */
    public void setPbsatz(String pvPbsatz) 
    {
        this.ivPbsatz = pvPbsatz;
    }

    /**
     * @return the reginfo
     */
    public String getReginfo() 
    {
        return this.ivReginfo;
    }

    /**
     * @param pvReginfo the reginfo to set
     */
    public void setReginfo(String pvReginfo) 
    {
        this.ivReginfo = pvReginfo;
    }

    /**
     * @return the rgrund
     */
    public String getRgrund() {
        return this.ivRgrund;
    }

    /**
     * @param pvRgrund the rgrund to set
     */
    public void setRgrund(String pvRgrund) {
        this.ivRgrund = pvRgrund;
    }

    /**
     * @return the rgrunddat
     */
    public String getRgrunddat() {
        return this.ivRgrunddat;
    }

    /**
     * @param pvRgrunddat the rgrunddat to set
     */
    public void setRgrunddat(String pvRgrunddat) {
        this.ivRgrunddat = pvRgrunddat;
    }

    /**
     * @return the tilgers
     */
    public String getTilgers() 
    {
        return this.ivTilgers;
    }

    /**
     * @param pvTilgers the tilgers to set
     */
    public void setTilgers(String pvTilgers) 
    {
        this.ivTilgers = pvTilgers;
    }

    /**
     * @return the vbetrag
     */
    public String getVbetrag() 
    {
        return this.ivVbetrag;
    }

    /**
     * @param pvVbetrag the vbetrag to set
     */
    public void setVbetrag(String pvVbetrag) 
    {
        this.ivVbetrag = pvVbetrag;
    }

    /**
     * @return the verwnr
     */
    public String getVerwnr() 
    {
        return this.ivVerwnr;
    }

    /**
     * @param pvVerwnr the verwnr to set
     */
    public void setVerwnr(String pvVerwnr) 
    {
        this.ivVerwnr = pvVerwnr;
    }

    /**
     * @return the whrg
     */
    public String getWhrg() 
    {
        return this.ivWhrg;
    }

    /**
     * @param pvWhrg the whrg to set
     */
    public void setWhrg(String pvWhrg) 
    {
        this.ivWhrg = pvWhrg;
    }

    /**
     * @return the probsatz
     */
    public String getProbsatz() 
    {
        return this.ivProbsatz;
    }

    /**
     * @param pvProbsatz the probsatz to set
     */
    public void setProbsatz(String pvProbsatz) 
    {
        this.ivProbsatz = pvProbsatz;
    }

    /**
     * @return the dsepsichzerkl
     */
    public String getDsepsichzerkl() {
        return this.ivDsepsichzerkl;
    }

    /**
     * @param pvDsepsichzerkl the dsepsichzerkl to set
     */
    public void setDsepsichzerkl(String pvDsepsichzerkl) {
        this.ivDsepsichzerkl = pvDsepsichzerkl;
    }

    /**
     * TXSSicherheitDatenStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:shdaten ");
    }
    
    /**
     * TXSSicherheitDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        if (this.ivArt.length() > 0)
        {
            lvHelpXML.append("art=\"" + this.ivArt + "\" ");           
        }
        if (this.ivBemzus.length() > 0)
        {
            lvHelpXML.append("bemzus=\"" + this.ivBemzus + "\" ");
        }
        if (this.ivBez.length() > 0)
        {
            lvHelpXML.append("bez=\"" + this.ivBez + "\" ");
        }
        if (this.ivEgleichl.length() > 0)
        {
            lvHelpXML.append("egleichl=\"" + this.ivEgleichl + "\" ");            
        }
        if (this.ivEvorl.length() > 0)
        {
            lvHelpXML.append("evorl=\"" + this.ivEvorl + "\" ");
        }
        if (this.ivFgleichl.length() > 0)
        {
            lvHelpXML.append("fgleichl=\"" + this.ivFgleichl + "\" ");            
        }
        if (this.ivFvorl.length() > 0)
        {
            lvHelpXML.append("fvorl=\"" + this.ivFvorl + "\" ");            
        }
        if (this.ivGepr.length() > 0)
        {
            lvHelpXML.append("gepr=\"" + this.ivGepr + "\" ");
        }
        if (this.ivKoninfo.length() > 0)
        {
            lvHelpXML.append("koninfo=\"" + this.ivKoninfo + "\" ");            
        }
        if (this.ivNbetrag.length() > 0)
        {
            lvHelpXML.append("nbetrag=\"" + this.ivNbetrag + "\" ");
        }
        if (this.ivOrigsichant.length() > 0)
        {
            lvHelpXML.append("origsichant=\"" + this.ivOrigsichant + "\" ");            
        }
        if (this.ivPbsatz.length() > 0)
        {
            lvHelpXML.append("pbsatz=\"" + this.ivPbsatz + "\" ");
        }
        if (this.ivReginfo.length() > 0)
        {
            lvHelpXML.append("reginfo=\"" + this.ivReginfo + "\" ");            
        }
        if (this.ivRgrund.length() > 0)
        {
            lvHelpXML.append("rgrund=\"" + this.ivRgrund + "\" ");            
        }
        if (this.ivRgrunddat.length() > 0)
        {
            lvHelpXML.append("rgrunddat=\"" + this.ivRgrunddat + "\" ");            
        }
        if (this.ivTilgers.length() > 0)
        {
            lvHelpXML.append("tilgers=\"" + this.ivTilgers + "\" ");            
        }
        if (this.ivVbetrag.length() > 0)
        {
            lvHelpXML.append("vbetrag=\"" + this.ivVbetrag + "\" ");
        }
        if (this.ivVerwnr.length() > 0)
        {
            lvHelpXML.append("verwnr=\"" + this.ivVerwnr + "\" ");            
        }
        if (this.ivVbetrag.length() > 0 || this.ivNbetrag.length() > 0)
        {
            lvHelpXML.append("whrg=\"" + this.ivWhrg + "\" ");
        }
        if (this.ivProbsatz.length() > 0)
        {
            lvHelpXML.append("probsatz=\"" + this.ivProbsatz + "\" ");
        }
        if (this.ivDsepsichzerkl.length() > 0)
        {
            lvHelpXML.append("dsepsichzerkl=\"" + this.ivDsepsichzerkl + "\" ");
        }
        lvHelpXML.append(">");
        return lvHelpXML;
    }
    
    /**
     * TXSSicherheitDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
      return new StringBuffer("</txsi:shdaten>\n");
    }
    
    /**
     * Importiert die Darlehensinformationen 
     * @param pvDarlehen 
     * @param pvSicherheit 
     * @param pvObj 
     * @return 
     */
    public boolean importDarlehen(nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen, Sicherheit pvSicherheit, Sicherungsobjekt pvObj)
    {
        BigDecimal dBtrDivHd = new BigDecimal("0.01");
        /* Sicherheitenschl�ssel auf zwei Stellen ... */
        String lvSiSchl = new String();
        //System.out.println("SiSchl: " + darlehen.getSicherheitenSchluessel());
        lvSiSchl = pvDarlehen.getSicherheitenSchluessel().substring(1,3);
   
        //int iTypGS;
        /* Jetzt noch TXX408 .... */
        String lvSiArt = new String();
        /* Erste Definition geht �ber den Sicherheitenschl�ssel */
        if (lvSiSchl.equals("01") ||
            lvSiSchl.equals("03") ||
            lvSiSchl.equals("04") ||
            lvSiSchl.equals("05") ||
            lvSiSchl.equals("06") ||
            lvSiSchl.equals("31"))
        { /* 1.-te grobe Definition */
         String lvBuffer1 = new String();
         lvBuffer1 = pvObj.getBeleihungsgebiet();
         if (lvBuffer1.startsWith("91") ||
             lvBuffer1.startsWith("99"))
         { /* Nicht Deutschland */
          lvSiArt = "19";
         } /* Nicht Deutschland */
         else
         { /* Deutschland */
          //iTypGS = 0; /* Grundschuld */
          lvSiArt = "12";
         } /* Deutschland */
        } /* 1.-te grobe Definition */
        if (lvSiArt.isEmpty())
        { /* Noch undefiniert */
         //iTypGS = 0; /* Grundschuld */
         lvSiArt = "12";
        } /* Noch undefiniert */
        /* Redinition diverser Mappings auf Basis der ISIN */
        String lvWISIN = pvDarlehen.getIsin().toUpperCase();
        
        String lvKISINPR1     =  "ELK";
        String lvKISINPR2     =  "EBK";
        
        if (lvWISIN.startsWith(lvKISINPR1) ||
            lvWISIN.startsWith(lvKISINPR2))
        { /* ISIN hat Redefinition */
         /* SicherheitenArt - Stelle 7-10=> Pos 6-9 im Feld - 4-stellig */
         /* Generisch */
         if (lvWISIN.charAt(6) == 'E')
         { /* Einzel */
          if (lvWISIN.charAt(7) == 'F')
          { /* Brief */
           if (lvWISIN.substring(8,10).equals("GS"))
           { /* Grundschuld */
             lvSiArt = "11";
           } /* Grundschuld */
           if (lvWISIN.substring(8,10).equals("HY"))
           { /* Hypothek */
            lvSiArt = "13";
           } /* Hypothek */
          } /* Brief */
          if (lvWISIN.charAt(7) == 'H')
          { /* Buch  */
           if (lvWISIN.substring(8,10).equals("GS"))
           { /* Grundschuld */
            lvSiArt = "12";
           } /* Grundschuld */
           if (lvWISIN.substring(8,10).equals("HY"))
           { /* Hypothek */
            lvSiArt = "14";
           } /* Hypothek */
          } /* Buch  */
         } /* Einzel */
         if (lvWISIN.charAt(6) == 'G')
         { /* Gesamt */
          if (lvWISIN.charAt(7) == 'F')
          { /* Brief */
           if (lvWISIN.substring(8,10).equals("GS"))
           { /* Grundschuld */
            lvSiArt = "15";
           } /* Grundschuld */
           if (lvWISIN.substring(8,10).equals("HY"))
           { /* Hypothek */
            lvSiArt = "17";
           } /* Hypothek */
          } /* Brief */
          if (lvWISIN.charAt(7) == 'H')
          { /* Buch  */
           if (lvWISIN.substring(8,10).equals("GS"))
           { /* Grundschuld */
            lvSiArt = "16";
           } /* Grundschuld */
           if (lvWISIN.substring(8,10).equals("HY"))
           { /* Hypothek */
            lvSiArt = "18";
           } /* Hypothek */
          } /* Buch  */
          if (lvWISIN.substring(6,10).equals("GPAU"))
          { /* Ausl- Grundpf.recht */
           lvSiArt = "19";
          } /* Ausl- Grundpf.recht */
         } /* Gesamt */
         if (lvWISIN.substring(6,10).equals("KOBS"))
         { /* Kommunal - B�rgsch. */
          lvSiArt = "51";
         } /* Kommunal - B�rgsch. */
         if (lvWISIN.substring(6,10).equals("KOSS"))
         { /* Kommunal - Schuldsch. */
          lvSiArt = "50";
         } /* Kommunal - Schuldsch. */
         // Sicherheitenart Derivat gibt es nicht - CT 05.07.2012
         //if (sWISIN.substring(6,10).equals("DERI"))
         //{ /* Derivate  */
         // sSiArt = "Derivat";
         //} /* Derivate  */
        } /* ISIN hat Redefinition .. */
        /* 407/408 wird w/Betragsberechnung nach hinten verlegt */
        
        //System.out.println("BuergschaftProzent: " + darlehen.getBuergschaftProzent());
        
        BigDecimal lvWork = new BigDecimal("0.0");
        BigDecimal lvBuerge_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenUKAP_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenRKAP_Fakt = new BigDecimal("1.0");
        if (pvDarlehen.getKredittyp().equals("4"))
        { /* mit B�rge .. anteilig */
         if (StringKonverter.convertString2Double(pvDarlehen.getBuergschaftProzent()) != 0.0)
         { /* nichts da */
          lvBuerge_Fakt = dBtrDivHd.multiply(StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftProzent()));
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
        
        //BigDecimal dZuwBetrag = StringKonverter.convertString2BigDecimal(sicherheit.getZuweisungsbetrag()).multiply(dKonEigenUKAP_Fakt);
        BigDecimal dVerfBetrag = StringKonverter.convertString2BigDecimal(pvSicherheit.getVerfuegungsbetrag()).multiply(lvKonEigenUKAP_Fakt);
        //System.out.println("Sicherheit - Original: " + sicherheit.getVerfuegungsbetrag());    
        //System.out.println("Sicherheit - Verfuegungsbetrag: " + dVerfBetrag);
        //System.out.println("TXSSicherheitDaten: dZuwBetrag: " + dZuwBetrag + " dVerfBetrag:" + dVerfBetrag);
        
        //sicherheit.setVerfuegungsbetrag((new Double(dVerfBetrag)).toString());
        //sicherheit.setZuweisungsbetrag((new Double(dZuwBetrag)).toString());
        
        BigDecimal lvVerfBetragBu = new BigDecimal("0.0");
        BigDecimal lvVerfBetragRe = new BigDecimal("0.0");
        //BigDecimal dZuwBetragBu = new BigDecimal("0.0");
        //BigDecimal dZuwBetragRe = new BigDecimal("0.0");
        
        if (pvDarlehen.getKredittyp().equals("1"))
        { /* ohne B�rge .. alles */
         lvVerfBetragRe = dVerfBetrag;
         //dZuwBetragRe  = dZuwBetrag;
        } /* ohne B�rge .. alles */
        if (pvDarlehen.getKredittyp().equals("2"))
        { /* mit B�rge .. anteilig */
         if (StringKonverter.convertString2Double(pvDarlehen.getBuergschaftProzent()) == 0.0)
         { /* nichts da */
          lvVerfBetragRe = dVerfBetrag;
          //dZuwBetragRe  = dZuwBetrag;
         } /* nichts da */
         else
         { /* etwas da */
            lvVerfBetragBu = dVerfBetrag.multiply(dBtrDivHd).multiply(
                            StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftProzent()));
            lvVerfBetragRe = dVerfBetrag.subtract(lvVerfBetragBu);
            //dZuwBetragBu  = dZuwBetrag.multiply(dBtrDivHd).multiply(
            //                StringKonverter.convertString2BigDecimal(darlehen.getBuergschaftProzent()));
            //dZuwBetragRe  = dZuwBetrag.subtract(dZuwBetragBu);
         } /* etwas da */
        } /* mit B�rge .. anteilig */
        //System.out.println("Sicherheit - Nominalwert: " + sicherheit.getNominalwert());
        
        this.ivArt = lvSiArt;
        this.ivNbetrag = pvSicherheit.getNominalwert();
        
        this.ivPbsatz = "0.0";
        this.ivProbsatz = pvDarlehen.getBuergschaftProzent();
        
        this.ivVbetrag = lvVerfBetragRe.toString(); 
        this.ivWhrg = pvDarlehen.getSatzwaehrung();
 
        return true;
    }

    /**
     * Importiert die Darlehensinformationen 
     * @param pvDarlehen 
     * @return 
     */
    public boolean importDarlehen(nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen)
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

        /* Grunddefinition */
        //BigDecimal dZuwBetrag = StringKonverter.convertString2BigDecimal(darlehen.getZuweisungsbetrag()).multiply(dKonEigenUKAP_Fakt);
        BigDecimal lvVerfBetrag = StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital()).multiply(lvKonEigenUKAP_Fakt);        
        
        //System.out.println("lvVerfBetrag: " + lvVerfBetrag.toString());
        //System.out.println("BeleihungProzent: " + pvDarlehen.getBeleihungProzent());
        
        // Nur bei Kommunal - CT 09.01.2012
        if (pvDarlehen.getKredittyp().equals("4"))
        {
          /* Wenn vorhanden .. anteilige Beleihprozente */
          if (StringKonverter.convertString2Double(pvDarlehen.getBeleihungProzent()) != 0.0)
          {
           lvVerfBetrag = (lvVerfBetrag.multiply(
                          StringKonverter.convertString2BigDecimal(pvDarlehen.getBeleihungProzent()))).multiply(lvBtrDivHd);
          }
        }
        //System.out.println("lvVerfBetrag: " + lvVerfBetrag.toString());
                
        this.ivArt = "51";
                
        BigDecimal lvUrsprungskapital = StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital()).multiply(lvKonEigenUKAP_Fakt);
        this.ivNbetrag = lvUrsprungskapital.toString();

        this.ivPbsatz = "0.0";
        this.ivProbsatz = pvDarlehen.getBuergschaftProzent();
        
        this.ivVbetrag = lvVerfBetrag.toString(); 
        this.ivWhrg = pvDarlehen.getSatzwaehrung();
        
        //System.out.println("TXSSicherheitDaten - Ursprungskapital: " + lvUrsprungskapital.toString() + " lvVerfBetrag:" + lvVerfBetrag.toString());
        
        return true;
    }

    /**
     * Importiert die Darlehensinformationen 
     * @param pvDarlehen 
     * @return 
     */
    public boolean importDarlehen(Darlehen pvDarlehen, Logger pvLogger)
    {
         
        this.ivArt = "51";
        
        // Buergschaftprozent
        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
    	BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);

   	    if (lvBuergschaftprozent.doubleValue() > 0.0)
    	{
   	        this.ivNbetrag = (StringKonverter.convertString2BigDecimal(pvDarlehen.getNennbetrag())).multiply(lvBuergschaftprozent).toString();
    	}
   	    else
   	    {
   	    	this.ivNbetrag = pvDarlehen.getNennbetrag();
   	    }

        this.ivPbsatz = "0.0";
        this.ivProbsatz = pvDarlehen.getBuergschaftprozent();
        
        //System.out.println(pvDarlehen.getKontonummer() + " Nennbetrag: " + pvDarlehen.getNennbetrag());
        //System.out.println(pvDarlehen.getKontonummer() + " Buergschaftprozent: " + pvDarlehen.getBuergschaftprozent());
   	    if (lvBuergschaftprozent.doubleValue() > 0.0)
    	{
    		this.ivVbetrag = (StringKonverter.convertString2BigDecimal(pvDarlehen.getNennbetrag())).multiply(lvBuergschaftprozent).toString();
    	}
    	else
    	{
    		this.ivVbetrag = pvDarlehen.getNennbetrag(); 
    	}

        //if (pvDarlehen.getKontonummer().contains("4250195350") || pvDarlehen.getKontonummer().contains("4250189380"))
        //{
        //    pvLogger.info("Kontonummer: " + pvDarlehen.getKontonummer());
        //    pvLogger.info("Nennbetrag LoanIQ: " + pvDarlehen.getNennbetrag());
        //    pvLogger.info("Buergschaftprozent LoanIQ: " + lvBuergschaftprozent);
        //    pvLogger.info("Verfuegungsbetrag an TXS: " + this.ivVbetrag);
        //}

        this.ivWhrg = pvDarlehen.getBetragwaehrung();
        
        return true;
    }    

    /**
     * Importiert die Darlehensinformationen 
     * @param pvDarlehen 
     * @return 
     */
    public boolean importMIDAS(Darlehen pvDarlehen)
    {
         
        this.ivArt = "51";
        
        this.ivNbetrag = pvDarlehen.getNennbetrag();
        this.ivPbsatz = "0.0";
        this.ivProbsatz = pvDarlehen.getBuergschaftprozent();
        
        this.ivVbetrag = "0.0"; 
        this.ivWhrg = pvDarlehen.getBetragwaehrung();
        
        return true;
    }    

    
    /**
     * @param pvZielDarlehen
     * @param pvSicherheit
     * @return 
     */
    @Deprecated
    public boolean importDarlehen(
        nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvZielDarlehen, OSPSicherheit pvSicherheit)
    {
        /* Sicherheitenschl�ssel auf zwei Stellen ... */
        String lvSiSchl = new String();

        String lvSiArt = new String();
        /* Erste Definition geht �ber den Sicherheitenschl�ssel */
        if (lvSiSchl.equals("01") ||
            lvSiSchl.equals("03") ||
            lvSiSchl.equals("04") ||
            lvSiSchl.equals("05") ||
            lvSiSchl.equals("06") ||
            lvSiSchl.equals("31"))
        { /* 1.-te grobe Definition */
         String lvBuffer1 = new String();
         //sBuffer1 = sObj.getBeleihungsgebiet();
         if (lvBuffer1.startsWith("91") ||
             lvBuffer1.startsWith("99"))
         { /* Nicht Deutschland */
          lvSiArt = "19";
         } /* Nicht Deutschland */
         else
         { /* Deutschland */
          //iTypGS = 0; /* Grundschuld */
          lvSiArt = "12";
         } /* Deutschland */
        } /* 1.-te grobe Definition */
        if (lvSiArt.isEmpty())
        { /* Noch undefiniert */
         //iTypGS = 0; /* Grundschuld */
         lvSiArt = "12";
        } /* Noch undefiniert */
        /* Redinition diverser Mappings auf Basis der ISIN */
        String lvWISIN = "";//darlehen.getIsin().toUpperCase();
        
        String lvKISINPR1     =  "ELK";
        String lvKISINPR2     =  "EBK";
        
        if (lvWISIN.startsWith(lvKISINPR1) ||
            lvWISIN.startsWith(lvKISINPR2))
        { /* ISIN hat Redefinition */
         /* SicherheitenArt - Stelle 7-10=> Pos 6-9 im Feld - 4-stellig */
         /* Generisch */
         if (lvWISIN.charAt(6) == 'E')
         { /* Einzel */
          if (lvWISIN.charAt(7) == 'F')
          { /* Brief */
           if (lvWISIN.substring(8,10).equals("GS"))
           { /* Grundschuld */
             lvSiArt = "11";
           } /* Grundschuld */
           if (lvWISIN.substring(8,10).equals("HY"))
           { /* Hypothek */
            lvSiArt = "13";
           } /* Hypothek */
          } /* Brief */
          if (lvWISIN.charAt(7) == 'H')
          { /* Buch  */
           if (lvWISIN.substring(8,10).equals("GS"))
           { /* Grundschuld */
            lvSiArt = "12";
           } /* Grundschuld */
           if (lvWISIN.substring(8,10).equals("HY"))
           { /* Hypothek */
            lvSiArt = "14";
           } /* Hypothek */
          } /* Buch  */
         } /* Einzel */
         if (lvWISIN.charAt(6) == 'G')
         { /* Gesamt */
          if (lvWISIN.charAt(7) == 'F')
          { /* Brief */
           if (lvWISIN.substring(8,10).equals("GS"))
           { /* Grundschuld */
            lvSiArt = "15";
           } /* Grundschuld */
           if (lvWISIN.substring(8,10).equals("HY"))
           { /* Hypothek */
            lvSiArt = "17";
           } /* Hypothek */
          } /* Brief */
          if (lvWISIN.charAt(7) == 'H')
          { /* Buch  */
           if (lvWISIN.substring(8,10).equals("GS"))
           { /* Grundschuld */
            lvSiArt = "16";
           } /* Grundschuld */
           if (lvWISIN.substring(8,10).equals("HY"))
           { /* Hypothek */
            lvSiArt = "18";
           } /* Hypothek */
          } /* Buch  */
          if (lvWISIN.substring(6,10).equals("GPAU"))
          { /* Ausl- Grundpf.recht */
           lvSiArt = "19";
          } /* Ausl- Grundpf.recht */
         } /* Gesamt */
         if (lvWISIN.substring(6,10).equals("KOBS"))
         { /* Kommunal - B�rgsch. */
          lvSiArt = "51";
         } /* Kommunal - B�rgsch. */
         if (lvWISIN.substring(6,10).equals("KOSS"))
         { /* Kommunal - Schuldsch. */
          lvSiArt = "50";
         } /* Kommunal - Schuldsch. */
         // Sicherheitenart Derivat gibt es nicht - CT 05.07.2012
         //if (sWISIN.substring(6,10).equals("DERI"))
         //{ /* Derivate  */
         // sSiArt = "Derivat";
         //} /* Derivate  */
        } /* ISIN hat Redefinition .. */
        /* 407/408 wird w/Betragsberechnung nach hinten verlegt */
        
        //System.out.println("CT - pvSicherheit: " + pvSicherheit.getNr());                       
        this.ivArt = MappingDPP.changeSicherheitart(pvSicherheit.getSicherheitenartElementar());
        this.ivNbetrag = pvSicherheit.getNominalwert();
        
        this.ivPbsatz = "0.0";
        this.ivProbsatz = pvZielDarlehen.getBuergschaftProzent();
        
        this.ivVbetrag = pvSicherheit.getVerfuegungsbetrag(); 
        this.ivWhrg = pvSicherheit.getWaehrung();
 
        return true;     
    }
}
