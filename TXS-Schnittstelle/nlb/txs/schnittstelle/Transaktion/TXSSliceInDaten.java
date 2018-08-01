/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQ;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQBlock;
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Utilities.WPSuffix;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;

/**
 * @author tepperc
 *
 */
public class TXSSliceInDaten implements TXSTransaktion
{
    /**
     * Gibt an, ob es sich um ein Geschaeft der Aktiv- oder Passiv-Seite handelt.
     */
    private String ivAktivpassiv;
    
    /**
     * Gibt an, ob das Slice einer Transaktion zugeordnet ist.
     */
    private String ivAssigned;
    
    /**
     * Bis-Betrag des Slices.
     */
    private String ivBis;
    
    /**
     * Gibt an, ob das Slice zur Blacklist gehoert.
     */
    private String ivBlack;
    
    /**
     * Art der Pfandbriefdeckung (z.B. ordentliche Deckung, sichernde Ueberdeckung).
     */
    private String ivDecktyp;
    
    /**
     * Eindeutiger, externer Schluessel des Slices. Dieser Schluessel wird bei meheren Slices zu einem Kredit zur eindeutigen Identifikation des Slices benoetigt.
     */
    private String ivKey;
    
    /**
     * Nominalbetrag des Slices.
     */
    private String ivNbetrag;
    
    /**
     * Freiwillige Ueberdeckung zur Liquiditaetssicherung
     */
    private String ivNliqui;
    
    /**
     * Gibt den Namen des Pools innerhalb von TXSuite an.
     */
    private String ivPool;
    
    /**
     * Gibt den Namen des Projektes innerhalb von TXSuite an.
     */
    private String ivPrj;
    
    /**
     * Gibt an, ob es sich um ein simuliertes Geschaeft handelt.
     */
    private String ivSimu;
    
    /**
     * Storno-Kennzeichen
     */
    private String ivStorno;
    
    /**
     * Wird gefuellt, wenn für ein Finanzgeschaeft ein spezielles Tilgungsmodell gelten soll. 
     * Tilgungsmodelle werden sonst ueber spezielle Regeln innerhalb von TXSuite gesetzt.
     */
    private String ivTilgmod;
    
    /**
     * Gibt den Namen der Transaktion innerhalb von TXSuite an.
     */
    private String ivTx;
    
    /**
     * Von-Betrag des Slices.
     */
    private String ivVon;
    
    /**
     * Waehrung der gemeldeten Betraege dieser Transaktion.
     * Definition gemaeß ISO-Standard. z.B. "EUR"
     * Haben die verschiedenen betrachteten Konditionspositionen unterschiedliche Waehrungen, 
     * so werden die gemeldeten Betraege in eine gemeinsame Waehrung gemaeß angegebener Priorisierung stichtagsbezogen umgerechnet.
     */
    private String ivWhrg;

    /**
     * Konstruktor
     */
    public TXSSliceInDaten()
    {
        initTXSSliceInDaten();
    }
    
    /**
     * Initialisierung
     */
    public void initTXSSliceInDaten() 
    {
        this.ivAktivpassiv = new String();
        this.ivAssigned = new String();
        this.ivBis = new String();
        this.ivBlack = new String();
        this.ivDecktyp = new String();
        this.ivKey = new String();
        this.ivNbetrag = new String();
        this.ivNliqui = new String();
        this.ivPool = new String();
        this.ivPrj = new String();
        this.ivSimu = new String();
        this.ivStorno = new String();
        this.ivTilgmod = new String();
        this.ivTx = new String();
        this.ivVon = new String();
        this.ivWhrg = new String();
    }

    /**
     * @return the aktivpassiv
     */
    public String getAktivpassiv() {
        return this.ivAktivpassiv;
    }

    /**
     * @param pvAktivpassiv the aktivpassiv to set
     */
    public void setAktivpassiv(String pvAktivpassiv) {
        this.ivAktivpassiv = pvAktivpassiv;
    }

    /**
     * @return the assigned
     */
    public String getAssigned() {
        return this.ivAssigned;
    }

    /**
     * @param pvAssigned the assigned to set
     */
    public void setAssigned(String pvAssigned) {
        this.ivAssigned = pvAssigned;
    }

    /**
     * @return the bis
     */
    public String getBis() {
        return this.ivBis;
    }

    /**
     * @param pvBis the bis to set
     */
    public void setBis(String pvBis) {
        this.ivBis = pvBis;
    }

    /**
     * @return the black
     */
    public String getBlack() {
        return this.ivBlack;
    }

    /**
     * @param pvBlack the black to set
     */
    public void setBlack(String pvBlack) {
        this.ivBlack = pvBlack;
    }

    /**
     * @return the decktyp
     */
    public String getDecktyp() {
        return this.ivDecktyp;
    }

    /**
     * @param pvDecktyp the decktyp to set
     */
    public void setDecktyp(String pvDecktyp) {
        this.ivDecktyp = pvDecktyp;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return this.ivKey;
    }

    /**
     * @param pvKey the key to set
     */
    public void setKey(String pvKey) {
        this.ivKey = pvKey;
    }

    /**
     * @return the nbetrag
     */
    public String getNbetrag() {
        return this.ivNbetrag;
    }

    /**
     * @param pvNbetrag the nbetrag to set
     */
    public void setNbetrag(String pvNbetrag) {
        this.ivNbetrag = pvNbetrag;
    }

    /**
     * @return the nliqui
     */
    public String getNliqui() {
        return this.ivNliqui;
    }

    /**
     * @param pvNliqui the nliqui to set
     */
    public void setNliqui(String pvNliqui) {
        this.ivNliqui = pvNliqui;
    }

    /**
     * @return the pool
     */
    public String getPool() {
        return this.ivPool;
    }

    /**
     * @param pvPool the pool to set
     */
    public void setPool(String pvPool) {
        this.ivPool = pvPool;
    }

    /**
     * @return the prj
     */
    public String getPrj() {
        return this.ivPrj;
    }

    /**
     * @param pvPrj the prj to set
     */
    public void setPrj(String pvPrj) {
        this.ivPrj = pvPrj;
    }

    /**
     * @return the simu
     */
    public String getSimu() {
        return this.ivSimu;
    }

    /**
     * @param pvSimu the simu to set
     */
    public void setSimu(String pvSimu) {
        this.ivSimu = pvSimu;
    }

    /**
     * @return the storno
     */
    public String getStorno() {
        return this.ivStorno;
    }

    /**
     * @param pvStorno the storno to set
     */
    public void setStorno(String pvStorno) {
        this.ivStorno = pvStorno;
    }

    /**
     * @return the tilgmod
     */
    public String getTilgmod() {
        return this.ivTilgmod;
    }

    /**
     * @param pvTilgmod the tilgmod to set
     */
    public void setTilgmod(String pvTilgmod) {
        this.ivTilgmod = pvTilgmod;
    }

    /**
     * @return the tx
     */
    public String getTx() {
        return this.ivTx;
    }

    /**
     * @param pvTx the tx to set
     */
    public void setTx(String pvTx) {
        this.ivTx = pvTx;
    }

    /**
     * @return the von
     */
    public String getVon() {
        return this.ivVon;
    }

    /**
     * @param pvVon the von to set
     */
    public void setVon(String pvVon) {
        this.ivVon = pvVon;
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
     * TXSSliceInDatenStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
      return new StringBuffer("    <txsi:slicedaten ");
    }
    
    /**
     * TXSSliceInDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("key=\"" + this.ivKey);
        
        if (this.ivAktivpassiv.length() > 0)
            lvHelpXML.append("\" aktivpassiv=\"" + this.ivAktivpassiv);
          
        if (this.ivDecktyp.length() > 0)
          lvHelpXML.append("\" decktyp=\"" + this.ivDecktyp);
        
        lvHelpXML.append("\" prj=\"" + this.ivPrj);
        
        lvHelpXML.append("\" tx=\"" + this.ivTx
                        + "\" pool=\"" + this.ivPool);
               
        if (this.ivTilgmod.length() > 0)
            lvHelpXML.append("\" tilgmod=\"" + this.ivTilgmod);

        if (this.ivVon.length() > 0)
            lvHelpXML.append("\" von=\"" + this.ivVon);
       
        if (this.ivBis.length() > 0)
          lvHelpXML.append("\" bis=\"" + this.ivBis);
        
        if (this.ivNbetrag.length() > 0)
          lvHelpXML.append("\" nbetrag=\"" + this.ivNbetrag);
        
        if (this.ivNliqui.length() > 0)
            lvHelpXML.append("\" nliqui=\"" + this.ivNliqui);
        
        lvHelpXML.append("\" whrg=\"" + this.ivWhrg);
        
        lvHelpXML.append("\">");
        return lvHelpXML;
    }
    
    /**
     * TXSSliceInDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("</txsi:slicedaten>\n");
    }

    /**
     * Importiert die Darlehensinformationen aus DarKa
     * @param pvModus 
     * @param pvDarlehen
     * @param pvLogger 
     * @return 
     */
    public boolean importDarlehen(int pvModus, Darlehen pvDarlehen, Logger pvLogger)
    {
        this.ivKey = pvDarlehen.getKontonummer();
        if (pvDarlehen.getKennzeichenAktPas().equals("A"))
        {
          this.ivAktivpassiv = "1";
        }
        else
        {
            pvLogger.error("Darlehen " + pvDarlehen.getKontonummer() + "- Passiv --> Ablehnung");
            return false;
        }
        
        // Deckungstyp anhand des Ausplatzierungsmerkmal ermitteln
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> weitere Deckung
        this.ivDecktyp = ValueMapping.ermittleDeckungstyp(pvDarlehen.getAusplatzierungsmerkmal());
        if (this.ivDecktyp.equals("undefiniert"))
        {
          pvLogger.error("Darlehen " + pvDarlehen.getKontonummer() + "- Deckungstyp undefiniert");
          return false;
        }  

        String lvKeySuffix = new String();
        if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
        {
        	lvKeySuffix = "K";
        }
        if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("H"))
        {
        	lvKeySuffix = "P";
        }  
      
        switch (pvModus)
        {
            case DarlehenVerarbeiten.ALT:
            case DarlehenVerarbeiten.DARKA:
                if (pvDarlehen.getInstitutsnummer().equals("004"))
                {
                    this.ivPrj = "BLB-PfandBG";
                }
                if (pvDarlehen.getInstitutsnummer().equals("009"))
                {
                    this.ivPrj = "NLB-PfandBG";                       
                }

                // Sicherende Ueberdeckung - CT 31.01.2014
                if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2"))
                {
                  this.ivKey = this.ivKey + "SU";  
                }
                else
                {
                    this.ivKey = this.ivKey + lvKeySuffix;
                }
                if (lvKeySuffix.equals("P"))
                { 
                  this.ivTx = "Hypothekenpfandbrief";
                  this.ivPool = "Hypothekenpfandbrief";
                }
                if (lvKeySuffix.equals("K"))
                {
                  this.ivTx = "&#214;ffentlicher Pfandbrief";
                  this.ivPool = "&#214;ffentlicher Pfandbrief";         
                }
                // Deckungskennzeichen == 1 -> Oeffentlicher Pfandbrief
                if (pvDarlehen.getDeckungsschluessel().equals("1"))
                {
                    this.ivTx = "&#214;ffentlicher Pfandbrief";
                    this.ivPool = "&#214;ffentlicher Pfandbrief";                                 
                }

                // Deckungskennzeichen == 2 -> Hypothekenpfandbrief
                if (pvDarlehen.getDeckungsschluessel().equals("2"))
                {
                    this.ivTx = "Hypothekenpfandbrief";
                    this.ivPool = "Hypothekenpfandbrief";            
                }        
               break;
            case DarlehenVerarbeiten.DPP:
                if (pvDarlehen.getInstitutsnummer().equals("004"))
                {
                    this.ivPrj = "BLB-PfandBG";
                }
                if (pvDarlehen.getInstitutsnummer().equals("009"))
                {
                    this.ivPrj = "NLB-PfandBG";                       
                }

                this.ivKey = this.ivKey + "P"; 
                this.ivTx = "Hypothekenpfandbrief";
                this.ivPool = "Hypothekenpfandbrief";
                break;
            case DarlehenVerarbeiten.FLUGZEUGE:
                this.ivPrj = "Flugzeugpfandbrief";
                this.ivKey = this.ivKey + "F";
                this.ivTx = "Flugzeugpfandbrief";
                this.ivPool = "Flugzeugpfandbrief";
                break;
            case DarlehenVerarbeiten.SCHIFFE:
                this.ivPrj = "Schiffspfandbrief";
                this.ivKey = this.ivKey + "S";
                this.ivTx = "Schiffspfandbrief";
                this.ivPool = "Schiffspfandbrief";
                break;
            case DarlehenVerarbeiten.OEPG:
                this.ivPrj = "&#214;PG";
                this.ivKey = this.ivKey + "K";
                this.ivTx = "&#214;ffentlicher Pfandbrief";
                this.ivPool = "&#214;ffentlicher Pfandbrief";                      
                break;
            default:
                pvLogger.error("TXSSliceInDaten - Unbekannter Modus");       
        }
                      
        this.ivTilgmod = "1";
        
        this.ivVon = "0.0";
        
        // Rechenkonstante
        BigDecimal lvBtrDivHd = new BigDecimal("0.01");
        
        /* Wird dann immer als Multiplikator genutzt */
        BigDecimal lvWork = new BigDecimal("0.0");
        BigDecimal lvBuerge_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenUKAP_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenRKAP_Fakt = new BigDecimal("1.0");
        if (pvDarlehen.getKredittyp().equals("4"))
        { /* mit Buerge .. anteilig */
         if (StringKonverter.convertString2Double(pvDarlehen.getBuergschaftProzent()) != 0.0)
         { /* nichts da */
          lvBuerge_Fakt = lvBtrDivHd.multiply(StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftProzent()));
         } /* etwas da */
        } /* mit Buerge .. anteilig */
        
        // BLB - Buergschaftsprozentsatz nicht verwenden, wenn der Deckungsschluessel 'S' oder 'V' und das Produkt '0802' ist
        if (pvDarlehen.getInstitutsnummer().equals("004"))
        {
            if ((pvDarlehen.getDeckungsschluessel().equals("S") || pvDarlehen.getDeckungsschluessel().equals("V")) && pvDarlehen.getProduktSchluessel().equals("00802"))
            {
                // Buergen-Faktor wieder auf '1' setzen
                lvBuerge_Fakt = new BigDecimal("1.0");
            }
        }

        // NLB - Buergschaftsprozentsatz nicht verwenden, wenn der Deckungsschluessel 'S' oder 'V' ist
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
            if (pvDarlehen.getDeckungsschluessel().equals("S") || pvDarlehen.getDeckungsschluessel().equals("V"))
            {
                // Buergen-Faktor wieder auf '1' setzen
                lvBuerge_Fakt = new BigDecimal("1.0");
            }
        }        

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
 
        ////System.out.println("Restkapital: " + pvDarlehen.getRestkapital());
        ////System.out.println("Ursprungskapital: " + pvDarlehen.getUrsprungsKapital());
        ////System.out.println("SummeRKAP: " + pvDarlehen.getSummeRKAP());
        ////System.out.println("SummeUKAP: " + pvDarlehen.getSummeUKAP());
        ////System.out.println("RKAP_Fakt: " + lvKonEigenRKAP_Fakt);
        ////System.out.println("UKAP_Fakt: " + lvKonEigenUKAP_Fakt);

        /* dKonEigenUKAP_Fakt wird vom dKonEigenRKAP_Fakt gesetzt ! */
        lvKonEigenUKAP_Fakt = lvKonEigenRKAP_Fakt;

        /* Solldeckung *********************************** */
        BigDecimal lvSolldeckung = new BigDecimal("0.0");
        lvSolldeckung = StringKonverter.convertString2BigDecimal(pvDarlehen.getSolldeckung());

        /* Definition der Nominaldeckung ............................ */
        //BigDecimal lvNominaldeckung = new BigDecimal("0.0");
        //lvNominaldeckung = lvSollDeckung; /* Im Normalfall ....*/
        /* Definition der Auszahlungsverpflichtungen ................ */
        //BigDecimal dAuszahlungsVerpf = StringKonverter.convertString2BigDecimal(darlehen.getOffZusage());
        //System.out.println("Auszahlungsverpflichtung: " + dAuszahlungsVerpf);
        
        /* Bei nicht vollvalutierten ................................. */
        //if (StringKonverter.convertString2Int(darlehen.getKontozustand()) < 4)
        //{ /* Noch nicht vollvalutiert */
          // CT 14.02.2012 - AuszahlungsVerpf wird nicht mehr abgezogen
          //dNominaldeckung = (dSollDeckung.subtract(dAuszahlungsVerpf));
        //    dNominaldeckung = dSollDeckung;
        //} /* Noch nicht vollvalutiert */
       
        /* dKonEigenU/RKAP_Fakt - Nutzung */
        lvSolldeckung = lvSolldeckung.multiply(lvKonEigenUKAP_Fakt);
        //lvNominaldeckung = lvNominaldeckung.multiply(lvKonEigenUKAP_Fakt);
                
        if (lvSolldeckung.doubleValue() > 0.0)
        {
          this.ivBis = lvSolldeckung.toString();
        }
        else
        {
            this.ivBis = "0.0";
        }
        
        if (lvSolldeckung.doubleValue() > 0.0)
        {
          this.ivNbetrag = lvSolldeckung.toString();
        }
        else
        {
            this.ivNbetrag = "0.0";
        }
        
        // CT 20.12.2011 - NurLiq-Kennzeichen setzen
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivNliqui = "0";
        //if (pvDarlehen.getDeckungsschluessel().equals("5") || pvDarlehen.getDeckungsschluessel().equals("6") ||
        //    pvDarlehen.getDeckungsschluessel().equals("7") || pvDarlehen.getDeckungsschluessel().equals("8"))
        if (pvDarlehen.getAusplatzierungsmerkmal().equals("K4") || pvDarlehen.getAusplatzierungsmerkmal().equals("H4") ||
            pvDarlehen.getAusplatzierungsmerkmal().equals("S4") || pvDarlehen.getAusplatzierungsmerkmal().equals("F4"))
        {
            this.ivNliqui = "1";
        }
        
        this.ivWhrg = pvDarlehen.getSatzwaehrung();
        
        return true;
        
    }  

    /**
     * Importiert die Darlehensinformationen aus LoanIQ
     * @param pvDarlehenLoanIQBlock 
     * @param pvVorlaufsatz 
     * @param pvLogger
     * @return 
     */
    public boolean importLoanIQ(DarlehenLoanIQBlock pvDarlehenLoanIQBlock, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {    	
    	DarlehenLoanIQ lvHelpDarlehenLoanIQ;
    	lvHelpDarlehenLoanIQ = pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto();

        pvLogger.info("Darlehen " + lvHelpDarlehenLoanIQ.getKontonummer() + " - Ausplatzierungsmerkmal " + lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal());

        // CT 22.03.2016 - Erst einmal auskommentiert, da das Ausplatzierungsmerkmal noch nicht korrekt befuellt wird 
        //if (!(lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K") ||
        //	    lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("S") ||
        //      lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("O")))
        //{
        //    pvLogger.error("Darlehen " + lvHelpDarlehenLoanIQ.getKontonummer() + " - Ungueltiges Ausplatzierungsmerkmal " + lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal());
        //    return false;
        //}
        // CT 22.03.2016
    	
        // 07.04.2014 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
    	// Hypothekenpfandbrief
        if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H"))
        { 
            this.ivKey = lvHelpDarlehenLoanIQ.getKontonummer() + "P";
            this.ivTx = "Hypothekenpfandbrief";
            this.ivPool = "Hypothekenpfandbrief"; 
            //if (pvVorlaufsatz.getInstitutsnummer().equals("004"))
            //{
            //	this.ivPrj = "BLB-PfandBG";
            //}
            //if (pvVorlaufsatz.getInstitutsnummer().equals("009"))
            //{
            	this.ivPrj = "NLB-PfandBG";                       
            //}
        }
        
        // Oeffentlicher Pfandbrief
        if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
        {
            this.ivKey = lvHelpDarlehenLoanIQ.getKontonummer() + "K";
            this.ivTx = "&#214;ffentlicher Pfandbrief";
            this.ivPool = "&#214;ffentlicher Pfandbrief";
            //if (pvVorlaufsatz.getInstitutsnummer().equals("004"))
            //{
            //	this.ivPrj = "BLB-PfandBG";
            //}
            //if (pvVorlaufsatz.getInstitutsnummer().equals("009"))
            //{
            	this.ivPrj = "NLB-PfandBG";                       
            //}
        }
        
        // Flugzeugpfandbrief
        if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F"))
        { 
            this.ivKey = lvHelpDarlehenLoanIQ.getKontonummer() + "F";
            this.ivTx = "Flugzeugpfandbrief";
            this.ivPool = "Flugzeugpfandbrief";
            this.ivPrj = "Flugzeugpfandbrief";                       
        }
        
        // Schiffspfandbrief
        if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("S"))
        {        
            this.ivKey = lvHelpDarlehenLoanIQ.getKontonummer() + "S";
            this.ivTx = "Schiffspfandbrief";
            this.ivPool = "Schiffspfandbrief";
            this.ivPrj = "Schiffspfandbrief";                       

        }
        
        // OEPG
        if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("O"))
        {
        	this.ivKey = lvHelpDarlehenLoanIQ.getKontonummer() + "O";
        	this.ivTx = "&#214;ffentlicher Pfandbrief";
        	this.ivPool = "&#214;ffentlicher Pfandbrief"; 
        	this.ivPrj = "&#214;PG";
        }

        if (!lvHelpDarlehenLoanIQ.getMerkmalAktivPassiv().equals("A"))
        {
            pvLogger.error("Konto " + lvHelpDarlehenLoanIQ.getKontonummer() + " - MerkmalAktivPassiv => " + lvHelpDarlehenLoanIQ.getMerkmalAktivPassiv());
            return false;
        } 
        this.ivAktivpassiv = "1";
        
        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "1";
        if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("2") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("O4"))
        {
            this.ivDecktyp = "2";
        }
        if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("3") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("4"))
        {
          if (!lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("O3") && !lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("O4"))
          {
        	this.ivDecktyp = "3";
          }
        }
        
        this.ivTilgmod = "1";
        
        this.ivVon = "0.0";
         
        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
        BigDecimal lvHelpBuergschaftprozent = (StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);
        BigDecimal lvHelpSolldeckung;

        if (StringKonverter.convertString2Double(lvHelpDarlehenLoanIQ.getSolldeckung()) > 0.0)
        {
            //if (lvHelpDarlehenLoanIQ.getKennzeichenKonsortialkredit().equals("J"))
            //{
            	// Fremde Fuehrung
            //	if (pvDarlehenLoanIQBlock.isListeDarlehenLoanIQFremdEmpty())
            //	{
            //		lvHelpSolldeckung = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getSolldeckung());
            //	}
            //	else // Eigene Fuehrung
            //	{
            //		lvHelpSolldeckung = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getSolldeckung());        		
            //	}
            //}
            //else
            //{
            	lvHelpSolldeckung = StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getSolldeckung());
            //}
        
            if (lvHelpBuergschaftprozent.doubleValue() > 0.0 && lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
            {
            	this.ivBis = (lvHelpSolldeckung.multiply(lvHelpBuergschaftprozent)).toPlainString();
            }
            else
            {
            	this.ivBis = lvHelpSolldeckung.toPlainString();
            }
        }
        else
        {
            this.ivBis = "0.0";
        }
                        
        if (StringKonverter.convertString2Double(lvHelpDarlehenLoanIQ.getSolldeckung()) > 0.0)
        {
        	this.ivNbetrag = this.ivBis;
        }
        else
        {
            this.ivNbetrag = "0.0";
        }
            
        // CT 20.12.2011 - NurLiq-Kennzeichen setzen
        // Defaultmaessig erst einmal auf "0" (false) setzen
        // 07.04.2014 - Spaeter Umstellung auf Ausplatzierungsmerkmal
        this.ivNliqui = "0";
        if (lvHelpDarlehenLoanIQ.getDeckungsschluessel().equals("5") || lvHelpDarlehenLoanIQ.getDeckungsschluessel().equals("6") ||
            lvHelpDarlehenLoanIQ.getDeckungsschluessel().equals("7") || lvHelpDarlehenLoanIQ.getDeckungsschluessel().equals("8"))
        {
            this.ivNliqui = "1";
        }
        
        this.ivWhrg = lvHelpDarlehenLoanIQ.getBetragwaehrung();
        
        return true;
        
    }

    /**
     * Importiert die Darlehensinformationen aus MIDAS
     * @param pvDarlehenLoanIQ 
     * @param pvVorlaufsatz 
     * @param pvLogger
     * @return 
     */
    public boolean importMIDAS(DarlehenLoanIQ pvDarlehenLoanIQ, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {    	
        pvLogger.info("Darlehen " + pvDarlehenLoanIQ.getKontonummer() + " - Ausplatzierungsmerkmal " + pvDarlehenLoanIQ.getAusplatzierungsmerkmal());

        // CT 22.03.2016 - Erst einmal auskommentiert, da das Ausplatzierungsmerkmal noch nicht korrekt befuellt wird 
        //if (!(pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H") || pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K") ||
        //	  pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F") || pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("S")))
        //{
        //    pvLogger.error("Darlehen " + pvDarlehenLoanIQ.getKontonummer() + " - Ungueltiges Ausplatzierungsmerkmal " + pvDarlehenLoanIQ.getAusplatzierungsmerkmal());
        //    return false;
        //}
        // CT 22.03.2016
    	
        // 07.04.2014 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
    	// Hypothekenpfandbrief
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H"))
        { 
            this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehenLoanIQ.getQuellsystem(), pvDarlehenLoanIQ.getKontonummer()) + "P";
            this.ivTx = "Hypothekenpfandbrief";
            this.ivPool = "Hypothekenpfandbrief"; 
            //if (lvHelpDarlehenLoanIQ.getDeckungsschluessel().equals("A"))
            //{
            //  this.ivPrj = "&#214;PG";   
            //}
            //else
            //{
            	this.ivPrj = "NLB-PfandBG";                       
            //}
        }
        
        // Oeffentlicher Pfandbrief
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
        {
        	this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehenLoanIQ.getQuellsystem(), pvDarlehenLoanIQ.getKontonummer()) + "K";                
            this.ivTx = "&#214;ffentlicher Pfandbrief";
            this.ivPool = "&#214;ffentlicher Pfandbrief";
            //if (lvHelpDarlehenLoanIQ.getDeckungsschluessel().equals("A"))
            //{
            //  this.ivPrj = "&#214;PG";   
            //}
            //else
            //{
            	this.ivPrj = "NLB-PfandBG";                       
            //}
        }
        
        // Flugzeugpfandbrief
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F"))
        { 
        	this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehenLoanIQ.getQuellsystem(), pvDarlehenLoanIQ.getKontonummer()) + "F";                
            this.ivTx = "Flugzeugpfandbrief";
            this.ivPool = "Flugzeugpfandbrief";
            this.ivPrj = "Flugzeugpfandbrief";                       
        }
        
        // Schiffspfandbrief
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("S"))
        {        
            this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehenLoanIQ.getQuellsystem(), pvDarlehenLoanIQ.getKontonummer()) + "S";
            this.ivTx = "Schiffspfandbrief";
            this.ivPool = "Schiffspfandbrief";
            this.ivPrj = "Schiffspfandbrief";                       

        }

        // Sonderbehandlung MIDAS aber nur, wenn es kein Ausplatzierungsmerkmal gibt. 
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().trim().isEmpty())
        {
        	if (pvDarlehenLoanIQ.getDeckungsschluessel().equals("D") || pvDarlehenLoanIQ.getDeckungsschluessel().equals("F"))
        	{
        		this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehenLoanIQ.getQuellsystem(), pvDarlehenLoanIQ.getKontonummer()) + "K";                
        		this.ivTx = "&#214;ffentlicher Pfandbrief";
        		this.ivPool = "&#214;ffentlicher Pfandbrief";
        		this.ivPrj = "NLB-PfandBG";                       
        	}
        	if (pvDarlehenLoanIQ.getDeckungsschluessel().equals("U"))
        	{
        		this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehenLoanIQ.getQuellsystem(), pvDarlehenLoanIQ.getKontonummer()) + "F";                
        		this.ivTx = "Flugzeugpfandbrief";
        		this.ivPool = "Flugzeugpfandbrief";
        		this.ivPrj = "Flugzeugpfandbrief";                       
        	}
        }
          
        if (!pvDarlehenLoanIQ.getMerkmalAktivPassiv().equals("A"))
        {
            pvLogger.error("Konto " + pvDarlehenLoanIQ.getKontonummer() + " - MerkmalAktivPassiv => " + pvDarlehenLoanIQ.getMerkmalAktivPassiv());
            return false;
        } 
        this.ivAktivpassiv = "1";
        
        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "1";
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("2"))
        {
            this.ivDecktyp = "2";
        }
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("3"))
        {
        	this.ivDecktyp = "3";
        }
                                        
        this.ivTilgmod = "1";
        
        this.ivVon = "0.0";
                 
        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
        BigDecimal lvHelpRestkapital = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getRestkapital());
        System.out.println("Buergschaftprozent: " + pvDarlehenLoanIQ.getBuergschaftprozent() + " - Restkapital: " + lvHelpRestkapital.toString());
        BigDecimal lvHelpBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);
        System.out.println("Bis-Betrag errechnet: " + (lvHelpRestkapital.multiply(lvHelpBuergschaftprozent)).toString());

        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("0"))
        {
        	if (lvHelpBuergschaftprozent.doubleValue() > 0.0)
        	{
        		this.ivBis = (lvHelpRestkapital.multiply(lvHelpBuergschaftprozent)).toString();
        	}
        	else
        	{
        		this.ivBis = lvHelpRestkapital.toString();
        	}
        }
                
        if (StringKonverter.convertString2Double(ivBis) > 0.0)
        {
        	this.ivNbetrag = this.ivBis;
        }
        else
        {
            this.ivNbetrag = "0.0";
        }
            
       	if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("F1"))
       	{
       		this.ivBis = new String();
       		this.ivNbetrag = new String();
        }

        // CT 20.12.2011 - NurLiq-Kennzeichen setzen
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivNliqui = "0";
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("4"))
        {
        	if (!pvDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("O4")) // OEPG bei MIDAS eigentlich nicht moeglich, aber...
        	{
              this.ivNliqui = "1";
        	}
        }
        
        this.ivWhrg = pvDarlehenLoanIQ.getBetragwaehrung();
        
        return true;
        
    }
    
    /**
     * Importiert die Wertpapier-Informationen
     * @param pvBestandsdaten
     * @param pvGattung
     * @param pvLogger
     * @return 
     */
    public boolean importWertpapier(Bestandsdaten pvBestandsdaten, String pvInstitutsnummer, Logger pvLogger)
    {
        pvLogger.info("Wertpapier " + pvBestandsdaten.getProdukt() + " - Ausplatzierungsmerkmal " + pvBestandsdaten.getAusplatzierungsmerkmal());

        if (!(pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("H") || pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("K") ||
              pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("F") || pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("S")))
        {
            pvLogger.error("Wertpapier " + pvBestandsdaten.getProdukt() + " - Ungueltiges Ausplatzierungsmerkmal " + pvBestandsdaten.getAusplatzierungsmerkmal());
            return false;
        }
    	
        // 18.11.2016 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
    	// Hypothekenpfandbrief
        if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("H"))
        { 
            this.ivKey = pvBestandsdaten.getProdukt() + WPSuffix.getSuffix(pvBestandsdaten.getAusplatzierungsmerkmal(), pvLogger);
            this.ivTx = "Hypothekenpfandbrief";
            this.ivPool = "Hypothekenpfandbrief"; 
            //if (lvHelpDarlehenLoanIQ.getDeckungsschluessel().equals("A"))
            //{
            //  this.ivPrj = "&#214;PG";   
            //}
            //else
            //{
              if (pvInstitutsnummer.equals("004") || pvInstitutsnummer.equals("BLB"))
              {
                  this.ivPrj = "BLB-PfandBG";
              }
              if (pvInstitutsnummer.equals("009") || pvInstitutsnummer.equals("NLB"))
              {
                  this.ivPrj = "NLB-PfandBG";                       
              }
            //}
        }
        
        // Oeffentlicher Pfandbrief
        if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("K"))
        {
            this.ivKey = pvBestandsdaten.getProdukt() + WPSuffix.getSuffix(pvBestandsdaten.getAusplatzierungsmerkmal(), pvLogger);
            this.ivTx = "&#214;ffentlicher Pfandbrief";
            this.ivPool = "&#214;ffentlicher Pfandbrief";
            //if (lvHelpDarlehenLoanIQ.getDeckungsschluessel().equals("A"))
            //{
            //  this.ivPrj = "&#214;PG";   
            //}
            //else
            //{
                if (pvInstitutsnummer.equals("004") || pvInstitutsnummer.equals("BLB"))
                {
                    this.ivPrj = "BLB-PfandBG";
                }
                if (pvInstitutsnummer.equals("009") || pvInstitutsnummer.equals("NLB"))
                {
                    this.ivPrj = "NLB-PfandBG";                       
                }
            //}
        }
        
        // Flugzeugpfandbrief
        if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("F"))
        { 
            this.ivKey = pvBestandsdaten.getProdukt() +  WPSuffix.getSuffix(pvBestandsdaten.getAusplatzierungsmerkmal(), pvLogger);
            this.ivTx = "Flugzeugpfandbrief";
            this.ivPool = "Flugzeugpfandbrief";
            this.ivPrj = "Flugzeugpfandbrief";                       
        }
        
        // Schiffspfandbrief
        if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("S"))
        {        
            this.ivKey = pvBestandsdaten.getProdukt() + WPSuffix.getSuffix(pvBestandsdaten.getAusplatzierungsmerkmal(), pvLogger);
            this.ivTx = "Schiffspfandbrief";
            this.ivPool = "Schiffspfandbrief";
            this.ivPrj = "Schiffspfandbrief";                       

        }
        
        this.ivAktivpassiv = "1"; // Slice immer Aktiv
        
        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "1";
       if (pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("2"))
        {
            this.ivDecktyp = "2";
        }
        if (pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("3"))
        {
        	this.ivDecktyp = "3";
        }
        
        this.ivVon = "0.0";
        this.ivBis = pvBestandsdaten.getNominalbetrag();
       
        if (StringKonverter.convertString2Double(ivBis) > 0.0)
        {
        	this.ivNbetrag = this.ivBis;
        }
        else
        {
            this.ivNbetrag = "0.0";
        }
        
        // CT 20.12.2011 - NurLiq-Kennzeichen setzen
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivNliqui = "0";
        if (pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("4"))
        {
        	if (!pvBestandsdaten.getAusplatzierungsmerkmal().equals("O4")) // OEPG - 'O4' nicht
        	{
              this.ivNliqui = "1";
        	}
        }
        
        this.ivWhrg = pvBestandsdaten.getNominalbetragWaehrung();
        
    	return true;
    }    
}
