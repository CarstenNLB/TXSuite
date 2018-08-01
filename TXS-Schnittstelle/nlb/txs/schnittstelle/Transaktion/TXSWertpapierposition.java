/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;

/**
 * @author tepperc
 *
 */
public class TXSWertpapierposition implements TXSTransaktion
{
    /**
     * Abweichende Fälligkeit (Sonderfälligkeit)
     * Optional
     */
    private String ivAbwfall;
    
    /**
     * Laufende Nummer
     * Optional
     */
    private String ivLfdnr;
    
    /**
     * Nennbetrag
     * Optional
     */
    private String ivNbetrag;
    
    /**
     * Kennzeichnet die Wertpapierposition als Restant.
     * Optional
     */
    private String ivRest;
    
    /**
     * "Saldo. Ein Originator liefert normalerweise durchgängig 
     * Restkapitalien (Soll) oder Restkapitalien (Ist).
     * z.B. ""100000.00"""
     * Optional
     */
    private String ivRkapi;
    
    /**
     * "Saldo. Ein Originator liefert normalerweise durchgängig 
     * Restkapitalien (Soll) oder Restkapitalien (Ist)
     * z.B. ""100000.00"""
     * Optional
     */
    private String ivRkaps;
    
    /**
     * "Währung der gemeldeten Beträge dieser Transaktion.
     * (Definition gemäß ISO-Standard)"
     * Pflicht nur bei Angabe von Betragswerten
     */
    private String ivWhrg;

    /**
     * "Eindeutiger Schlüssel (in allen Systemen verwendet).
     * Jede WP-Position muss einen eigenen, eindeutigen Schlüssel besitzen. Es darf hier nicht der Schlüssel des zugehörigen Wertpapiers angegeben werden.
     * z.B.: 1234567890 "
     * Pflicht
     */
    private String wpposkey;
    
    /**
     * Konstruktor
     */
    public TXSWertpapierposition()
    {
    	initTXSWertpapierposition();
    }
    
    /**
     * Initialisierung der Felder mit leeren Strings
     */
    public void initTXSWertpapierposition()
    {
        ivAbwfall = new String();
        ivLfdnr = new String();
        ivNbetrag = new String();
        ivRest = new String();
        ivRkapi = new String();
        ivRkaps = new String();
        ivWhrg = new String();
        wpposkey = new String();    	
    }

    /**
     * Liefert die abweichende Faelligkeit
     * @return the abwfall
     */
    public String getAbwfall() 
    {
        return this.ivAbwfall;
    }

    /**
     * Setzt die abweichende Faelligkeit
     * @param pvAbwfall the abwfall to set
     */
    public void setAbwfall(String pvAbwfall) 
    {
        this.ivAbwfall = pvAbwfall;
    }

    /**
     * Liefert die laufende Nummer
     * @return the lfdnr
     */
    public String getLfdnr() 
    {
        return this.ivLfdnr;
    }

    /**
     * Setzt die laufende Nummer
     * @param pvLfdnr the lfdnr to set
     */
    public void setLfdnr(String pvLfdnr) 
    {
        this.ivLfdnr = pvLfdnr;
    }

    /**
     * Liefert den Nennbetrag
     * @return the nbetrag
     */
    public String getNbetrag() 
    {
        return this.ivNbetrag;
    }

    /**
     * Setzt den Nennbetrag
     * @param pvNbetrag the nbetrag to set
     */
    public void setNbetrag(String pvNbetrag) 
    {
        this.ivNbetrag = pvNbetrag;
    }

    /**
     * Liefert das Kennzeichen Restant
     * @return the rest
     */
    public String getRest() 
    {
        return this.ivRest;
    }

    /**
     * Setzt das Kennzeichen Restant
     * @param pvRest the rest to set
     */
    public void setRest(String pvRest) 
    {
        this.ivRest = pvRest;
    }

    /**
     * Liefert das Restkapital (Ist)
     * @return the rkapi
     */
    public String getRkapi() 
    {
        return this.ivRkapi;
    }

    /**
     * Setzt das Restkapital (Ist)
     * @param pvRkapi the rkapi to set
     */
    public void setRkapi(String pvRkapi) 
    {
        this.ivRkapi = pvRkapi;
    }

    /**
     * Liefert das Restkapial (Soll)
     * @return the rkaps
     */
    public String getRkaps() 
    {
        return this.ivRkaps;
    }

    /**
     * Setzt das Restkapital (Soll)
     * @param pvRkaps the rkaps to set
     */
    public void setRkaps(String pvRkaps) 
    {
        this.ivRkaps = pvRkaps;
    }

    /**
     * Liefert die Waehrung
     * @return the whrg
     */
    public String getWhrg() 
    {
        return this.ivWhrg;
    }

    /**
     * Setzt die Waehrung
     * @param pvWhrg the whrg to set
     */
    public void setWhrg(String pvWhrg) 
    {
        this.ivWhrg = pvWhrg;
    }

    /**
     * Liefert den Wertpapierposition-Schluessel
     * @return the wpposkey
     */
    public String getWpposkey() 
    {
        return this.wpposkey;
    }

    /**
     * Setzt den Wertpapierposition-Schluessel
     * @param pvWpposkey the wpposkey to set
     */
    public void setWpposkey(String pvWpposkey) 
    {
        this.wpposkey = pvWpposkey;
    }

    /**
     * Liefert den Start der TXSWertpapierposition als String
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:wpposdaten ");
    }
    
    /**
     * Liefert die TXSWertpapierposition als String
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
       
        lvHelpXML.append("wpposkey=\"" + this.wpposkey);
        
        if (this.ivAbwfall.length() > 0)
          lvHelpXML.append("\" abwfaell=\"" + this.ivAbwfall);
        
        if (this.ivLfdnr.length() > 0)
          lvHelpXML.append("\" lfdnr=\"" + this.ivLfdnr); 
        
        if (this.ivNbetrag.length() > 0)
          lvHelpXML.append("\" nbetrag=\"" + this.ivNbetrag);
        
        if (this.ivRest.length() > 0)
          lvHelpXML.append("\" rest=\"" + this.ivRest);
        
        if (this.ivRkapi.length() > 0)
          lvHelpXML.append("\" rkapi=\"" + this.ivRkapi);
        
        if (this.ivRkaps.length() > 0)
          lvHelpXML.append("\" rkaps=\"" + this.ivRkaps);
        
        if (this.ivWhrg.length() > 0)
          lvHelpXML.append("\" whrg=\"" + this.ivWhrg);
        
        lvHelpXML.append("\">");
        return lvHelpXML;
    }
    
    /**
     * TXSWertpapierpositionEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("    </txsi:wpposdaten>\n");
    }
    
    /**
     * Importiert die PassivDaten aus LoanIQ
     * @param pvPassiv Daten
     * @param pvInstitutsnummer  
     */
    public boolean importLoanIQPassiv(LoanIQPassivDaten pvPassivDaten, String pvInstitutsnummer)
    {       
      String lvKey = pvPassivDaten.getAktenzeichen();
      if (pvInstitutsnummer.startsWith("004")) // BLB
      {
          this.setWpposkey("DE" + pvPassivDaten.getKontonummer() + "_0");      
      }
      else // NLB
      {
          this.setWpposkey(lvKey + "_" + pvPassivDaten.getKontonummer());         	  
      }

      // Als laufende Nummer wird die Kontonummer verwendet 
      this.setLfdnr(pvPassivDaten.getKontonummer());
     
      this.setWhrg(pvPassivDaten.getWaehrung());
      this.setNbetrag(pvPassivDaten.getNennbetrag());
      
      // aufpassen , prolongierte
      // Sonderbehandlung Rollover
      String lvKzro = pvPassivDaten.getRolloverKennzeichen();

      if (("F".equals(lvKzro)) || ("V".equals(lvKzro)))  
      {
          this.setAbwfall(DatumUtilities.changeDate(pvPassivDaten.getTilgungsbeginn()));
      }    
      else
      {    
          if (!("00000000".equals(pvPassivDaten.getNaechsteZinsanpassung())))
          {
              this.setAbwfall(DatumUtilities.changeDate(pvPassivDaten.getNaechsteZinsanpassung()));
          }    
          else
          {
              this.setAbwfall(DatumUtilities.changeDate(pvPassivDaten.getTilgungsbeginn()));
          }
      }
      
      // Bei Zeros wird das Restkapital nicht mehr geliefert. - CT 10.01.2018
      if (!pvPassivDaten.getMerkmalZinssatz().equals("ZERO"))
      {
    	  this.setRkapi(pvPassivDaten.getRestkapital());
      }
      
      return true;
    }
    
    /**
     * Importiert die Wertpapier-Informationen
     * @param pvBestandsdaten
     */
    public boolean importWertpapier(Bestandsdaten pvBestandsdaten)
    {  
    	this.wpposkey = pvBestandsdaten.getProdukt() + "_0";
    	this.ivLfdnr = "0";
    	this.ivWhrg = pvBestandsdaten.getNominalbetragWaehrung();
    	this.ivNbetrag = pvBestandsdaten.getNominalbetrag();
    	this.ivRkapi = pvBestandsdaten.getNominalbetrag();
    	
    	return true;
    }
}
