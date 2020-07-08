/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPKonto;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPSicherheit;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;


/**
 * @author tepperc
 *
 */
public class TXSKreditSicherheit implements TXSTransaktion
{
    /**
     * Hauptsicherheit
     */
    private String ivHauptsh;
    
    /**
     * Key
     */
    private String ivKey;
    
    /**
     * Originator
     */
    private String ivOrg;
    
    /**
     * Quellsystem
     */
    private String ivQuelle;
    
    /**
     * Waehrung des Zuweisungsbetrag
     */
    private String ivWhrg;
    
    /**
     * Zuweisungsbetrag
     */
    private String ivZuwbetrag;

    /**
     * Konstruktor
     */
    public TXSKreditSicherheit() 
    {
        initTXSKreditSicherheit();
    }

    /**
     *  Initialisiert die Variablen mit leeren Strings
     */
    public void initTXSKreditSicherheit()
    {
        this.ivHauptsh = new String();
        this.ivKey = new String();
        this.ivOrg = new String();
        this.ivQuelle = new String();
        this.ivWhrg = new String();
        this.ivZuwbetrag = new String();
    }

    /**
     * @return the hauptsh
     */
    public String getHauptsh() {
        return this.ivHauptsh;
    }

    /**
     * @param pvHauptsh the hauptsh to set
     */
    public void setHauptsh(String pvHauptsh) {
        this.ivHauptsh = pvHauptsh;
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
     * @return the zuwbetrag
     */
    public String getZuwbetrag() {
        return this.ivZuwbetrag;
    }

    /**
     * @param pvZuwbetrag the zuwbetrag to set
     */
    public void setZuwbetrag(String pvZuwbetrag) {
        this.ivZuwbetrag = pvZuwbetrag;
    }

    /**
     * TXSKreditSicherheitStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:kredsh ");
    }
    
    /**
     * TXSKreditSicherheit in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("key=\"" + this.ivKey
                        + "\" org=\"" + this.ivOrg 
                        + "\" quelle=\"" + this.ivQuelle + "\"");
        if (ivWhrg.length() > 0)
        {
          lvHelpXML.append(" whrg=\"" + this.ivWhrg + "\"");
        }
        if (ivZuwbetrag.length() > 0)
        {
          lvHelpXML.append(" zuwbetrag=\"" + this.ivZuwbetrag + "\"");
        }
        if (ivHauptsh.length() > 0)
        {
            lvHelpXML.append(" hauptsh=\"" + ivHauptsh + "\"");
        }
        
        lvHelpXML.append(">\n");
        return lvHelpXML;
    }
    
    /**
     * TXSKreditSicherheitEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("    </txsi:kredsh>\n");
    }

    /**
     * Importiert die Darlehensinformationen 
     * @param pvModus 
     * @param pvDarlehen 
     * @param pvSicherheit 
     * @param pvLogger 
     * @return 
     */
    public boolean importDarlehen(int pvModus, nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen, Sicherheit pvSicherheit, Logger pvLogger)
    {
        BigDecimal lvWork = new BigDecimal("0.0");
        BigDecimal lvBuerge_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenUKAP_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenRKAP_Fakt = new BigDecimal("1.0");
        
        if (pvSicherheit == null)
        {
            pvLogger.error("Keine Sicherheit vorhanden --> null");
            return false;
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

        /* dKonEigenUKAP_Fakt wird vom dKonEigenRKAP_Fakt gesetzt ! */
        lvKonEigenUKAP_Fakt = lvKonEigenRKAP_Fakt;
        
        BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvDarlehen.getZuweisungsbetrag()).multiply(lvKonEigenUKAP_Fakt);
        BigDecimal lvSollBetrag = StringKonverter.convertString2BigDecimal(pvDarlehen.getSolldeckung()).multiply(lvKonEigenUKAP_Fakt);
        
        //BigDecimal lvZuwBetragBu = new BigDecimal("0.0");
        BigDecimal lvZuwBetragRe = new BigDecimal("0.0");
        if (pvDarlehen.getKredittyp().equals("1"))
        { /* ohne B�rge .. alles */
            lvZuwBetragRe  = lvZuwBetrag;
        } /* ohne B�rge .. alles */

        this.ivKey = "SH_DARL" + pvSicherheit.getObjektnummer(); //+ "_" + sicherheit.getKontonummer();
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
                System.out.println("TXSKreditSicherheit: Unbekannter Modus");
        }        

        this.ivWhrg = pvDarlehen.getSatzwaehrung();
        if (pvDarlehen.getKredittyp().equals("3"))
        {
            this.ivZuwbetrag = lvSollBetrag.toString();
        }
        if (pvDarlehen.getKredittyp().equals("1"))
        {
            this.ivZuwbetrag = lvZuwBetragRe.toString();
            if (lvZuwBetragRe.doubleValue() == 0.0)
            {
                pvLogger.info("Darlehen - Konto " + pvSicherheit.getKontonummer() + " Zuweisungsbetrag == 0.0");
                return false;
            }
        }
        
        return true;
    }

    /**
     * @param pvModus 
     * @param pvDarlehen
     * @param pvLogger 
     * @return
     */
    public boolean importDarlehen(int pvModus, nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen, Logger pvLogger)
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
        // Zuweisungsbetrag - Hypothekar
        BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvDarlehen.getZuweisungsbetrag()).multiply(lvKonEigenUKAP_Fakt);
        pvDarlehen.setZuweisungsbetrag(lvZuwBetrag.toString());
        // Solldeckung - Kommnunal
        BigDecimal lvSollBetrag = StringKonverter.convertString2BigDecimal(pvDarlehen.getSolldeckung()).multiply(lvKonEigenUKAP_Fakt);        

        //BigDecimal lvZuwBetragBu = new BigDecimal("0.0");
        /* Da Fall 'D' schon vorher �ber Faktor verrechnet wurde ....
        hier keine Unterscheidung mehr, daf�r wird dann aber nur
        der B�rge geschrieben ! .................................. */
        BigDecimal lvZuwBetragRe = new BigDecimal("0.0");
        lvZuwBetragRe  = StringKonverter.convertString2BigDecimal(pvDarlehen.getZuweisungsbetrag());
        
        this.ivKey = pvDarlehen.getBuergennummer() + "_" + pvDarlehen.getKontonummer();
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
                //if (pvDarlehen.getInstitutsnummer().equals("004"))
                //{
                //    if (pvDarlehen.getProduktSchluessel().equals("01002") || pvDarlehen.getProduktSchluessel().equals("01003"))
                //    {
                //        this.ivQuelle = "AKFWOEPG";
                //    }
                //}
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
        this.ivWhrg = pvDarlehen.getSatzwaehrung();
        
        if (pvDarlehen.getKredittyp().equals("4"))
        {
            this.ivZuwbetrag = lvSollBetrag.toString();
        }
        if (pvDarlehen.getKredittyp().equals("2"))
        {
          this.ivZuwbetrag = lvZuwBetragRe.toString();
          if (lvZuwBetragRe.doubleValue() == 0.0)
          {
            pvLogger.info("Darlehen - Konto " + pvDarlehen.getKontonummer() + " Zuweisungsbetrag == 0.0");
            return false;
          }
        }
        
        return true;
    }

    /**
     * @param pvDarlehen 
     * @param pvKonto
     * @param pvSicherheit
     * @param pvInstitut 
     * @param pvLogger 
     * @return
     */
    public boolean importDarlehen(nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen, OSPKonto pvKonto, OSPSicherheit pvSicherheit, String pvInstitut, Logger pvLogger)
    {        
        if (pvSicherheit == null)
        {
            System.out.println("Keine Sicherheit vorhanden --> null");
            return false;
        } 
        
        BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvKonto.getZuweisungsbetrag());
 
        this.ivKey = pvInstitut + "_" + pvSicherheit.getNr();
        this.ivOrg = ValueMapping.changeMandant(pvDarlehen.getInstitutsnummer());
        this.ivQuelle = "DPP";
        this.ivWhrg = pvSicherheit.getWaehrung();
        this.ivZuwbetrag = lvZuwBetrag.toString();
        if (lvZuwBetrag.doubleValue() == 0.0)
        {
            pvLogger.info("Darlehen - Konto " + pvKonto.getDarKaNr() + " Zuweisungsbetrag == 0.0");
            return false;
        }
        
        return true;
    }
    
    /**
     * @param pvDarlehen
     * @param pvInstitutsnummer 
     * @param pvLogger 
     * @return
     */
    public boolean importDarlehen(Darlehen pvDarlehen, String pvInstitutsnummer, Logger pvLogger)
    {
        this.ivKey = pvDarlehen.getBuergennummer() + "_" + pvDarlehen.getKontonummer();
        this.ivOrg = ValueMapping.changeMandant(pvInstitutsnummer);

        if (pvDarlehen.getDeckungsschluessel().equals("A"))
        {
          if (pvDarlehen.getQuellsystem().equals("LOANIQ"))
          {
            this.ivQuelle = "ALIQOEPG";
          }
          if (pvDarlehen.getQuellsystem().equals("IWHS"))
          {
              this.ivQuelle = "AAZ6OEPG";
          }
        }
        else
        {
            if (pvDarlehen.getQuellsystem().equals("LOANIQ"))
            {
                this.ivQuelle = "ALIQPFBG";
            }
            if (pvDarlehen.getQuellsystem().equals("IWHS"))
            {
                this.ivQuelle = "AAZ6PFBG";
            }

        }
        // MIDAS-Daten(Key und Quelle) setzen
        if (pvDarlehen.getQuellsystem().startsWith("MID"))
        {
            this.ivKey = pvDarlehen.getBuergennummer() + "_"  + MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getKontonummer());
            this.ivQuelle = "AMIDPFBG";
        }

        this.ivWhrg = pvDarlehen.getBetragwaehrung();
        // Buergschaftprozent
      ////  BigDecimal lvHelpFaktor = new BigDecimal("100.0");
    	////BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);

    	////System.out.println("Solldeckung: " + pvDarlehen.getSolldeckung());
    	////System.out.println("Buergschaftprozent: " + pvDarlehen.getBuergschaftprozent());
    	
    	////if (lvBuergschaftprozent.doubleValue() > 0.0)
    	////{
    	////	this.ivZuwbetrag = (StringKonverter.convertString2BigDecimal(pvDarlehen.getSolldeckung())).multiply(lvBuergschaftprozent).toString();
    	////}
    	////else
    	////{
    		this.ivZuwbetrag = pvDarlehen.getSolldeckung(); 
    	////}
        
        return true;
    }
    
    /**
     * @param pvDarlehen
     * @param pvInstitutsnummer 
     * @param pvLogger 
     * @return
     */
    public boolean importMIDAS(Darlehen pvDarlehen, String pvInstitutsnummer, Logger pvLogger)
    {
        // MIDAS-Daten(Key und Quelle) setzen
        this.ivKey = pvDarlehen.getBuergennummer() + "_"  + MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getKontonummer());
        this.ivQuelle = "AMIDPFBG";
        this.ivOrg = ValueMapping.changeMandant(pvInstitutsnummer);
        this.ivWhrg = pvDarlehen.getBetragwaehrung();
        this.ivZuwbetrag = pvDarlehen.getSolldeckung();
        
        return true;
    }
    
    /**
     * Setzt die Felder der TXSKreditSicherheit
     * @param pvHauptsh
     * @param pvKey
     * @param pvOrg
     * @param pvQuelle
     * @param pvWhrg
     * @param pvZuwbetrag
     */
    public void setTXSKreditSicherheit(String pvHauptsh, String pvKey, String pvOrg, String pvQuelle, String pvWhrg, String pvZuwbetrag)
    {
    	this.ivHauptsh = pvHauptsh;
    	this.ivKey = pvKey;
    	this.ivOrg = pvOrg;
    	this.ivQuelle = pvQuelle;
    	this.ivZuwbetrag = pvZuwbetrag;
    }

}
