/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;


/**
 * @author tepperc
 *
 */
public class TXSGlobalurkunde implements TXSTransaktion
{
    /**
     * 
     */
    private String ivBiszu;
    
    /**
     * 
     */
    private String ivBiszudat;
    
    /**
     * 
     */
    private String ivGukey;
    
    /**
     * 
     */
    private String ivNr;
    
    /**
     * 
     */
    private String ivRegseite;
    
    /**
     * 
     */
    private String ivRegtyp;
    
    /**
     * 
     */
    private String ivTyp;
    
    /**
     * 
     */
    private String ivValuta;
    
    /**
     * 
     */
    private String ivWhrg;

    /**
     * Konstruktor
     */
    public TXSGlobalurkunde() 
    {
        initTXSGlobalurkunde();
    }

    /**
     * Initialisiert die Instanzvariablen mit leeren Strings
     */
    public void initTXSGlobalurkunde()
    {
        this.ivBiszu = new String();
        this.ivBiszudat = new String();
        this.ivGukey = new String();
        this.ivNr = new String();
        this.ivRegseite = new String();
        this.ivRegtyp = new String();
        this.ivTyp = new String();
        this.ivValuta = new String();
        this.ivWhrg = new String();
    }

    /**
     * @return the biszu
     */
    public String getBiszu() {
        return this.ivBiszu;
    }

    /**
     * @param pvBiszu the biszu to set
     */
    public void setBiszu(String pvBiszu) {
        this.ivBiszu = pvBiszu;
    }

    /**
     * @return the biszudat
     */
    public String getBiszudat() {
        return this.ivBiszudat;
    }

    /**
     * @param pvBiszudat the biszudat to set
     */
    public void setBiszudat(String pvBiszudat) {
        this.ivBiszudat = pvBiszudat;
    }

    /**
     * @return the gukey
     */
    public String getGukey() {
        return this.ivGukey;
    }

    /**
     * @param pvGukey the gukey to set
     */
    public void setGukey(String pvGukey) {
        this.ivGukey = pvGukey;
    }

    /**
     * @return the nr
     */
    public String getNr() {
        return this.ivNr;
    }

    /**
     * @param pvNr the nr to set
     */
    public void setNr(String pvNr) {
        this.ivNr = pvNr;
    }

    /**
     * @return the regseite
     */
    public String getRegseite() {
        return this.ivRegseite;
    }

    /**
     * @param pvRegseite the regseite to set
     */
    public void setRegseite(String pvRegseite) {
        this.ivRegseite = pvRegseite;
    }

    /**
     * @return the regtyp
     */
    public String getRegtyp() {
        return this.ivRegtyp;
    }

    /**
     * @param pvRegtyp the regtyp to set
     */
    public void setRegtyp(String pvRegtyp) {
        this.ivRegtyp = pvRegtyp;
    }

    /**
     * @return the typ
     */
    public String getTyp() {
        return this.ivTyp;
    }

    /**
     * @param pvTyp the typ to set
     */
    public void setTyp(String pvTyp) {
        this.ivTyp = pvTyp;
    }

    /**
     * @return the valuta
     */
    public String getValuta() {
        return this.ivValuta;
    }

    /**
     * @param pvValuta the valuta to set
     */
    public void setValuta(String pvValuta) {
        this.ivValuta = pvValuta;
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
     * TXSGlobalurkundeStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:gudaten ");
     }
    
    /**
     * TXSGlobalurkunde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {   
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("regtyp=\"" + this.ivRegtyp);
        lvHelpXML.append("\" gukey=\"" + this.ivGukey);
        
        if (this.ivBiszu.length() > 0)
        {
            lvHelpXML.append("\" biszu=\"" + this.ivBiszu);
        }
        if (this.ivBiszudat.length() > 0)
        {
            lvHelpXML.append("\" biszudat=\"" + this.ivBiszudat);
        }
        if (this.ivNr.length() > 0)
        {
            lvHelpXML.append("\" nr=\"" + this.ivNr);
        }
        if (this.ivValuta.length() > 0)
        {
            lvHelpXML.append("\" valuta=\"" + this.ivValuta);
        }
        if (this.ivWhrg.length() > 0)
        {
            lvHelpXML.append("\" whrg=\"" + this.ivWhrg);
        }
        lvHelpXML.append("\">");
 
        return lvHelpXML;
    }
    
    /**
     * TXSGlobalurkundeEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("    </txsi:gudaten>\n");
    }
    
    /**
     * Importiert die Wertpapier-Informationen
     * @param pvBestandsdaten
     * @param pvGD630A
     * @param pvGD660
     */
    public boolean importWertpapier(Bestandsdaten pvBestandsdaten, String pvGD630A, String pvGD660)
    {
    	this.ivGukey = pvBestandsdaten.getProdukt() + "_1";
    	
        String lvmyregtyp = pvBestandsdaten.getWertpapierart().substring(3,4);
 
        if ("S0".equals(pvBestandsdaten.getAusplatzierungsmerkmal()) || "S1".equals(pvBestandsdaten.getAusplatzierungsmerkmal()))
        {
        	lvmyregtyp = "4";
        }
        else if ("F0".equals(pvBestandsdaten.getAusplatzierungsmerkmal()) || "F1".equals(pvBestandsdaten.getAusplatzierungsmerkmal()))
        {
        	lvmyregtyp = "5";
        } 

    	this.ivRegtyp = lvmyregtyp;
    	
    	//this.ivBiszu = pvBestandsdaten.getNominalbetrag();
    	this.ivBiszu = pvGD630A;
    	
    	this.ivValuta = pvBestandsdaten.getNominalbetrag();
    	this.ivWhrg = pvBestandsdaten.getNominalbetragWaehrung();
    	this.ivNr = "1";
    	this.ivBiszudat = pvGD660;
    	
    	return true;
    }
    /**
     * Importiert die Wertpapier-Informationen fuer KEV
     * @param pvBestandsdaten
     * @param pvGD630A
     * @param pvGD660
     */
    public boolean importKEVWertpapier(Bestandsdaten pvBestandsdaten, String pvGD630A, String pvGD660)
    {
        this.ivGukey = pvBestandsdaten.getProdukt() + "_1";

        String lvmyregtyp = pvBestandsdaten.getWertpapierart().substring(3,4);

        if ("S0".equals(pvBestandsdaten.getAusplatzierungsmerkmal()) || "S1".equals(pvBestandsdaten.getAusplatzierungsmerkmal()))
        {
            lvmyregtyp = "4";
        }
        else if ("F0".equals(pvBestandsdaten.getAusplatzierungsmerkmal()) || "F1".equals(pvBestandsdaten.getAusplatzierungsmerkmal()))
        {
            lvmyregtyp = "5";
        }

        this.ivRegtyp = lvmyregtyp;

        //this.ivBiszu = pvBestandsdaten.getNominalbetrag();
        this.ivBiszu = pvGD630A;

        this.ivValuta = pvBestandsdaten.getNominalbetrag();
        this.ivWhrg = pvBestandsdaten.getNominalbetragWaehrung();
        this.ivNr = "1";
        this.ivBiszudat = pvGD660;

        return true;
    }


}
