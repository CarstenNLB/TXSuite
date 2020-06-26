/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenBlock;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.Termingeld.Daten.Termingeld;
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Utilities.WPSuffix;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;
import org.apache.log4j.Logger;

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
     * Wird gefuellt, wenn f�r ein Finanzgeschaeft ein spezielles Tilgungsmodell gelten soll. 
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
     * Definition gemae� ISO-Standard. z.B. "EUR"
     * Haben die verschiedenen betrachteten Konditionspositionen unterschiedliche Waehrungen, 
     * so werden die gemeldeten Betraege in eine gemeinsame Waehrung gemae� angegebener Priorisierung stichtagsbezogen umgerechnet.
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
     * Initialisierung der Instanzvariablen mit leeren Strings
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
    public boolean importDarlehen(int pvModus, nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen, Logger pvLogger)
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
            case DarlehenVerarbeiten.KEV:
                this.ivPrj ="KEV";
                //this.ivKey = this.ivKey + "KEV";
                this.ivTx = "KEV";
                this.ivPool = "KEV";
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

        if (pvModus == DarlehenVerarbeiten.KEV)
        {
            this.ivNbetrag = pvDarlehen.getUrsprungsKapital();
            this.ivBis = pvDarlehen.getUrsprungsKapital();
        }
        else
        {
          if (lvSolldeckung.doubleValue() > 0.0)
          {
            this.ivNbetrag = lvSolldeckung.toString();
          }
          else
          {
            this.ivNbetrag = "0.0";
          }
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
     * @param pvDarlehenBlock
     * @param pvVorlaufsatz 
     * @param pvLogger
     * @return 
     */
    public boolean importLoanIQ(DarlehenBlock pvDarlehenBlock, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {    	
    	Darlehen lvHelpDarlehen;
    	lvHelpDarlehen = pvDarlehenBlock.getDarlehenNetto();

        pvLogger.info("Darlehen " + lvHelpDarlehen.getKontonummer() + " - Ausplatzierungsmerkmal " + lvHelpDarlehen
            .getAusplatzierungsmerkmal());

        // CT 22.03.2016 - Erst einmal auskommentiert, da das Ausplatzierungsmerkmal noch nicht korrekt befuellt wird 
        //if (!(lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("H") || lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("K") ||
        //	    lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("F") || lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("S") ||
        //      lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("O")))
        //{
        //    pvLogger.error("Darlehen " + lvHelpDarlehen.getKontonummer() + " - Ungueltiges Ausplatzierungsmerkmal " + lvHelpDarlehen.getAusplatzierungsmerkmal());
        //    return false;
        //}
        // CT 22.03.2016
    	
        // 07.04.2014 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
    	// Hypothekenpfandbrief
        if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("H"))
        { 
            this.ivKey = lvHelpDarlehen.getKontonummer() + "P";
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
        if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
        {
            this.ivKey = lvHelpDarlehen.getKontonummer() + "K";
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
        if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("F"))
        { 
            this.ivKey = lvHelpDarlehen.getKontonummer() + "F";
            this.ivTx = "Flugzeugpfandbrief";
            this.ivPool = "Flugzeugpfandbrief";
            this.ivPrj = "Flugzeugpfandbrief";                       
        }
        
        // Schiffspfandbrief
        if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("S"))
        {        
            this.ivKey = lvHelpDarlehen.getKontonummer() + "S";
            this.ivTx = "Schiffspfandbrief";
            this.ivPool = "Schiffspfandbrief";
            this.ivPrj = "Schiffspfandbrief";                       

        }
        
        // OEPG
        if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("O"))
        {
        	this.ivKey = lvHelpDarlehen.getKontonummer() + "O";
        	this.ivTx = "&#214;ffentlicher Pfandbrief";
        	this.ivPool = "&#214;ffentlicher Pfandbrief"; 
        	this.ivPrj = "&#214;PG";
        }

        if (!lvHelpDarlehen.getMerkmalAktivPassiv().equals("A"))
        {
            pvLogger.error("Konto " + lvHelpDarlehen.getKontonummer() + " - MerkmalAktivPassiv => " + lvHelpDarlehen
                .getMerkmalAktivPassiv());
            return false;
        } 
        this.ivAktivpassiv = "1";
        
        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "1";
        if (lvHelpDarlehen.getAusplatzierungsmerkmal().endsWith("2") || lvHelpDarlehen.getAusplatzierungsmerkmal().equals("O4"))
        {
            this.ivDecktyp = "2";
        }
        if (lvHelpDarlehen.getAusplatzierungsmerkmal().endsWith("3") || lvHelpDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
        {
          if (!lvHelpDarlehen.getAusplatzierungsmerkmal().equals("O3") && !lvHelpDarlehen.getAusplatzierungsmerkmal().equals("O4"))
          {
        	this.ivDecktyp = "3";
          }
        }
        
        this.ivTilgmod = "1";
        
        this.ivVon = "0.0";
         
        /////BigDecimal lvHelpFaktor = new BigDecimal("100.0");
        ////BigDecimal lvHelpBuergschaftprozent = (StringKonverter.convertString2BigDecimal(lvHelpDarlehen.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);
        ////BigDecimal lvHelpSolldeckung;

        if (StringKonverter.convertString2Double(lvHelpDarlehen.getSolldeckung()) > 0.0)
        {
            //if (lvHelpDarlehen.getKennzeichenKonsortialkredit().equals("J"))
            //{
            	// Fremde Fuehrung
            //	if (pvDarlehenBlock.isListeDarlehenLoanIQFremdEmpty())
            //	{
            //		lvHelpSolldeckung = StringKonverter.convertString2BigDecimal(pvDarlehenBlock.getDarlehenLoanIQBrutto().getSolldeckung());
            //	}
            //	else // Eigene Fuehrung
            //	{
            //		lvHelpSolldeckung = StringKonverter.convertString2BigDecimal(pvDarlehenBlock.getDarlehenLoanIQNetto().getSolldeckung());
            //	}
            //}
            //else
            //{
         ////   	lvHelpSolldeckung = StringKonverter.convertString2BigDecimal(lvHelpDarlehen.getSolldeckung());
            //}
        
         ////   if (lvHelpBuergschaftprozent.doubleValue() > 0.0 && lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
         ////   {
         ////   	this.ivBis = (lvHelpSolldeckung.multiply(lvHelpBuergschaftprozent)).toPlainString();
         ////   }
         ////   else
         ////   {
         ////   	this.ivBis = lvHelpSolldeckung.toPlainString();
            this.ivBis = pvDarlehenBlock.getDarlehenNetto().getSolldeckung();
         ////   }
        }
        else
        {
            this.ivBis = "0.0";
        }
                        
        if (StringKonverter.convertString2Double(lvHelpDarlehen.getSolldeckung()) > 0.0)
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
        if (lvHelpDarlehen.getDeckungsschluessel().equals("5") || lvHelpDarlehen.getDeckungsschluessel().equals("6") ||
            lvHelpDarlehen.getDeckungsschluessel().equals("7") || lvHelpDarlehen.getDeckungsschluessel().equals("8"))
        {
            this.ivNliqui = "1";
        }
        
        this.ivWhrg = lvHelpDarlehen.getBetragwaehrung();
        
        return true;
        
    }

    /**
     * Importiert die Darlehensinformationen aus MIDAS
     * @param pvDarlehen
     * @param pvVorlaufsatz 
     * @param pvLogger
     * @return 
     */
    public boolean importMIDAS(Darlehen pvDarlehen, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {    	
        pvLogger.info("Darlehen " + pvDarlehen.getKontonummer() + " - Ausplatzierungsmerkmal " + pvDarlehen
            .getAusplatzierungsmerkmal());

        // CT 22.03.2016 - Erst einmal auskommentiert, da das Ausplatzierungsmerkmal noch nicht korrekt befuellt wird 
        //if (!(pvDarlehen.getAusplatzierungsmerkmal().startsWith("H") || pvDarlehen.getAusplatzierungsmerkmal().startsWith("K") ||
        //	  pvDarlehen.getAusplatzierungsmerkmal().startsWith("F") || pvDarlehen.getAusplatzierungsmerkmal().startsWith("S")))
        //{
        //    pvLogger.error("Darlehen " + pvDarlehen.getKontonummer() + " - Ungueltiges Ausplatzierungsmerkmal " + pvDarlehen.getAusplatzierungsmerkmal());
        //    return false;
        //}
        // CT 22.03.2016
    	
        // 07.04.2014 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
    	// Hypothekenpfandbrief
        if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("H"))
        {
            this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getKontonummer()) + "P";

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
        if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
        {
            this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getKontonummer()) + "K";

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
        if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("F"))
        {
            this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getKontonummer()) + "F";

            this.ivTx = "Flugzeugpfandbrief";
            this.ivPool = "Flugzeugpfandbrief";
            this.ivPrj = "Flugzeugpfandbrief";                       
        }
        
        // Schiffspfandbrief
        if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("S"))
        {
            this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getKontonummer()) + "S";
            this.ivTx = "Schiffspfandbrief";
            this.ivPool = "Schiffspfandbrief";
            this.ivPrj = "Schiffspfandbrief";                       

        }

        // Sonderbehandlung MIDAS aber nur, wenn es kein Ausplatzierungsmerkmal gibt. 
        if (pvDarlehen.getAusplatzierungsmerkmal().trim().isEmpty())
        {
        	if (pvDarlehen.getDeckungsschluessel().equals("D") || pvDarlehen.getDeckungsschluessel().equals("F"))
        	{
        	    this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen
                      .getKontonummer()) + "K";

        		this.ivTx = "&#214;ffentlicher Pfandbrief";
        		this.ivPool = "&#214;ffentlicher Pfandbrief";
        		this.ivPrj = "NLB-PfandBG";                       
        	}
        	if (pvDarlehen.getDeckungsschluessel().equals("U"))
        	{
        	    this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen
                      .getKontonummer()) + "F";

        		this.ivTx = "Flugzeugpfandbrief";
        		this.ivPool = "Flugzeugpfandbrief";
        		this.ivPrj = "Flugzeugpfandbrief";                       
        	}
        }
          
        if (!pvDarlehen.getMerkmalAktivPassiv().equals("A"))
        {
            pvLogger.error("Konto " + pvDarlehen.getKontonummer() + " - MerkmalAktivPassiv => " + pvDarlehen
                .getMerkmalAktivPassiv());
            return false;
        } 
        this.ivAktivpassiv = "1";
        
        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "1";
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2"))
        {
            this.ivDecktyp = "2";
        }
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("3"))
        {
        	this.ivDecktyp = "3";
        }
                                        
        this.ivTilgmod = "1";
        
        this.ivVon = "0.0";
                 
        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
        BigDecimal lvHelpRestkapital = StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital());
        pvLogger.info("Buergschaftprozent: " + pvDarlehen.getBuergschaftprozent() + " - Restkapital: " + lvHelpRestkapital.toString());
        BigDecimal lvHelpBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);
        pvLogger.info("Bis-Betrag errechnet: " + (lvHelpRestkapital.multiply(lvHelpBuergschaftprozent)).toString());

        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("0"))
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
            
       	if (pvDarlehen.getAusplatzierungsmerkmal().equals("F1"))
       	{
       		this.ivBis = new String();
       		this.ivNbetrag = new String();
        }

        // CT 20.12.2011 - NurLiq-Kennzeichen setzen
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivNliqui = "0";
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
        {
        	if (!pvDarlehen.getAusplatzierungsmerkmal().equals("O4")) // OEPG bei MIDAS eigentlich nicht moeglich, aber...
        	{
              this.ivNliqui = "1";
        	}
        }
        
        this.ivWhrg = pvDarlehen.getBetragwaehrung();
        
        return true;
        
    }
    
    /**
     * Importiert die Wertpapier-Informationen
     * @param pvBestandsdaten
     * @param pvInstitutsnummer
     * @param pvLogger
     * @return 
     */
    public boolean importWertpapier(Bestandsdaten pvBestandsdaten, String pvInstitutsnummer, Logger pvLogger)
    {
        pvLogger.info("Wertpapier " + pvBestandsdaten.getProdukt() + " - Ausplatzierungsmerkmal " + pvBestandsdaten.getAusplatzierungsmerkmal());

        if (!(pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("H") || pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("K") ||
              pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("F") || pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("S") ||
              pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("O")))
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
        
        // OEPG
        if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("O"))
        {
        	this.ivKey = pvBestandsdaten.getProdukt() + WPSuffix.getSuffix(pvBestandsdaten.getAusplatzierungsmerkmal(), pvLogger);
        	this.ivTx = "&#214;ffentlicher Pfandbrief";
        	this.ivPool = "&#214;ffentlicher Pfandbrief"; 
        	this.ivPrj = "&#214;PG";
        }

        
        this.ivAktivpassiv = "1"; // Slice immer Aktiv
        
        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "1";
        if (pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("2") || pvBestandsdaten.getAusplatzierungsmerkmal().equals("O4"))
        {
            this.ivDecktyp = "2";
        }
        else if (!pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("O") &&
                 (pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("3") || pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("4")))
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

    /**
     * Importiert die Wertpapier-Informationen fuer KEV
     * @param pvBestandsdaten
     * @param pvInstitutsnummer
     * @param pvLogger
     * @return
     */
    public boolean importKEVWertpapier(Bestandsdaten pvBestandsdaten, String pvInstitutsnummer, Logger pvLogger)
    {
        pvLogger.info("Wertpapier " + pvBestandsdaten.getProdukt() + " - Ausplatzierungsmerkmal " + pvBestandsdaten.getAusplatzierungsmerkmal());

        //if (!(pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("A0") || pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("A1") ||
        //      pvBestandsdaten.getDepotNr().startsWith("SS")))
        //{
        //    pvLogger.error("Wertpapier " + pvBestandsdaten.getProdukt() + " - Ungueltiges Ausplatzierungsmerkmal " + pvBestandsdaten.getAusplatzierungsmerkmal());
        //    return false;
        //}

        this.ivKey = pvBestandsdaten.getProdukt(); // + WPSuffix.getSuffix(pvBestandsdaten.getAusplatzierungsmerkmal(), pvLogger);
        this.ivTx = "KEV";
        this.ivPool = "KEV";
        this.ivPrj = "KEV";

        this.ivAktivpassiv = "1"; // Slice immer Aktiv

        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "1";
        //if (pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("2") || pvBestandsdaten.getAusplatzierungsmerkmal().equals("O4"))
        //{
        //    this.ivDecktyp = "2";
        //}
        //else if (!pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("O") &&
        //    (pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("3") || pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("4")))
        //{
        //    this.ivDecktyp = "3";
        //}

        this.ivVon = "0.0";
        this.ivBis = pvBestandsdaten.getNominalbetrag();

        //if (StringKonverter.convertString2Double(ivBis) > 0.0)
        //{
        //    this.ivNbetrag = this.ivBis;
        //}
        //else
        //{
        //    this.ivNbetrag = "0.0";
        //}

        // CT 20.12.2011 - NurLiq-Kennzeichen setzen
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivNliqui = "0";
        //if (pvBestandsdaten.getAusplatzierungsmerkmal().endsWith("4"))
        //{
        //    if (!pvBestandsdaten.getAusplatzierungsmerkmal().equals("O4")) // OEPG - 'O4' nicht
        //    {
        //        this.ivNliqui = "1";
        //    }
        //}
        // Defaultmaessig Wasserfall (1)
        this.ivTilgmod = "1";
        this.ivWhrg = pvBestandsdaten.getNominalbetragWaehrung();

        return true;
    }

    /**
     * Importiert die Darlehensinformationen aus LoanIQ fuer KEV
     * @param pvDarlehen
     * @param pvVorlaufsatz
     * @param pvLogger
     * @return
     */
    public boolean importKEVLoanIQ(Darlehen pvDarlehen, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
        pvLogger.info("Darlehen " + pvDarlehen.getKontonummer() + " - Ausplatzierungsmerkmal " + pvDarlehen
            .getAusplatzierungsmerkmal());

        // 07.04.2014 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
            this.ivKey = pvDarlehen.getKontonummer(); // + "P";
            this.ivTx = "KEV";
            this.ivPool = "KEV";
            this.ivPrj = "KEV";


        if (!pvDarlehen.getMerkmalAktivPassiv().equals("A"))
        {
            pvLogger.error("Konto " + pvDarlehen.getKontonummer() + " - MerkmalAktivPassiv => " + pvDarlehen
                .getMerkmalAktivPassiv());
            return false;
        }
        this.ivAktivpassiv = "1";

        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "1";
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2"))
        {
            this.ivDecktyp = "2";
        }
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("3"))
        {
            this.ivDecktyp = "3";
        }

        this.ivTilgmod = "1";

        this.ivVon = "0.0";

        this.ivBis = pvDarlehen.getRestkapital();
        //if (StringKonverter.convertString2Double(ivBis) > 0.0)
        //{
        //    this.ivNbetrag = this.ivBis;
        //}
        //else
        //{
        //    this.ivNbetrag = "0.0";
        //}

        // CT 20.12.2011 - NurLiq-Kennzeichen setzen
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivNliqui = "0";
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
        {
            if (!pvDarlehen.getAusplatzierungsmerkmal().equals("O4")) // OEPG bei MIDAS eigentlich nicht moeglich, aber...
            {
                this.ivNliqui = "1";
            }
        }

        this.ivWhrg = pvDarlehen.getBetragwaehrung();

        return true;

    }

    /**
     * Importiert die Termingeldinformationen
     * @param pvTermingeld Termingeld
     * @param pvVorlaufsatz Vorlaufsatz
     * @param pvLogger log4j-Logger
     * @return
     */
    public boolean importTermingeld(Termingeld pvTermingeld, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
        pvLogger.info("Darlehen " + pvTermingeld.getKontonummer() + " - Ausplatzierungsmerkmal " + pvTermingeld
            .getAusplatzierungsmerkmal());

        this.ivKey = pvTermingeld.getKontonummer();

        // 07.04.2014 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
        // Hypothekenpfandbrief
        //if (pvTermingeld.getAusplatzierungsmerkmal().startsWith("H"))
        //{
            this.ivTx = "Hypothekenpfandbrief";
            this.ivPool = "Hypothekenpfandbrief";
            this.ivPrj = "NLB-PfandBG";
        //}

        // Oeffentlicher Pfandbrief
        if (pvTermingeld.getAusplatzierungsmerkmal().startsWith("K"))
        {
            this.ivTx = "&#214;ffentlicher Pfandbrief";
            this.ivPool = "&#214;ffentlicher Pfandbrief";
            this.ivPrj = "NLB-PfandBG";
        }

        // Schiffspfandbrief
        if (pvTermingeld.getAusplatzierungsmerkmal().startsWith("S"))
        {
            this.ivTx = "Schiffspfandbrief";
            this.ivPool = "Schiffspfandbrief";
            this.ivPrj = "Schiffspfandbrief";
        }

        // OEPG
        if (pvTermingeld.getAusplatzierungsmerkmal().startsWith("O"))
        {
            this.ivTx = "&#214;ffentlicher Pfandbrief";
            this.ivPool = "&#214;ffentlicher Pfandbrief";
            this.ivPrj = "&#214;PG";
        }

        this.ivAktivpassiv = "1";

        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung
        this.ivDecktyp = "3";
        if (pvTermingeld.getAusplatzierungsmerkmal().endsWith("2"))
        {
            this.ivDecktyp = "2";
        }
        if (pvTermingeld.getAusplatzierungsmerkmal().endsWith("3"))
        {
            this.ivDecktyp = "3";
        }

        this.ivTilgmod = "1";

        this.ivVon = "0.0";

        this.ivBis = pvTermingeld.getSaldo();

        this.ivWhrg = pvTermingeld.getWaehrung();

        return true;
    }

}
