/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten.Original;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class INF 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;

    /**
     * Informationsart/-typ für EDR
     */
    private String ivBbart;
    
    /**
     * Wertstellung
     */
    private String ivBwert;
    
    /**
     * Kontonummer                                        
     */
    private String ivBkto;
    
    /**
     * Buchungsschlüssel
     */
    private String ivBb;
    
    /**
     * Buchungstag
     */                                           
    private String ivBbu;
    
    /**
     * Grundbuchnummer
     */                                          
    private String ivBgbnr;
    
    /**
     * Betrag (Währung sh. BWISO)
     */                                         
    private String ivBdmb;
    
    /**
     * Schlüssel Rücklastschrift
     */                                         
    private String ivBrl;
    
    /**
     * Mahnfriststellungsdatum
     */                                          
    private String ivBmfd;    
        
    /**
     * Konstruktor
     */
    public INF() 
    {
        this.ivKopf = new DWHKOPF();
        this.ivBbart = new String();
        this.ivBwert = new String();
        this.ivBkto = new String();
        this.ivBb = new String();
        this.ivBbu = new String();
        this.ivBgbnr = new String();
        this.ivBdmb = new String();
        this.ivBrl = new String();
        this.ivBmfd = new String();
    }

    /**
     * @return the kopf
     */
    public DWHKOPF getKopf() {
        return this.ivKopf;
    }
    
    /**
     * @param pvKopf the kopf to set
     */
    public void setKopf(DWHKOPF pvKopf) {
        this.ivKopf = pvKopf;
    }

    /**
     * @return the sBbart
     */
    public String getsBbart() {
        return this.ivBbart;
    }

    /**
     * @param pvSBbart the sBbart to set
     */
    public void setsBbart(String pvSBbart) {
        this.ivBbart = pvSBbart;
    }

    /**
     * @return the sBwert
     */
    public String getsBwert() {
        return this.ivBwert;
    }

    /**
     * @param pvSBwert the sBwert to set
     */
    public void setsBwert(String pvSBwert) {
        this.ivBwert = pvSBwert;
    }

    /**
     * @return the sBkto
     */
    public String getsBkto() {
        return this.ivBkto;
    }

    /**
     * @param pvSBkto the sBkto to set
     */
    public void setsBkto(String pvSBkto) {
        this.ivBkto = pvSBkto;
    }

    /**
     * @return the sBb
     */
    public String getsBb() {
        return this.ivBb;
    }

    /**
     * @param pvSBb the sBb to set
     */
    public void setsBb(String pvSBb) {
        this.ivBb = pvSBb;
    }

    /**
     * @return the sBbu
     */
    public String getsBbu() {
        return this.ivBbu;
    }

    /**
     * @param pvSBbu the sBbu to set
     */
    public void setsBbu(String pvSBbu) {
        this.ivBbu = pvSBbu;
    }

    /**
     * @return the sBgbnr
     */
    public String getsBgbnr() {
        return this.ivBgbnr;
    }

    /**
     * @param pvSBgbnr the sBgbnr to set
     */
    public void setsBgbnr(String pvSBgbnr) {
        this.ivBgbnr = pvSBgbnr;
    }

    /**
     * @return the sBdmb
     */
    public String getsBdmb() {
        return this.ivBdmb;
    }

    /**
     * @param pvSBdmb the sBdmb to set
     */
    public void setsBdmb(String pvSBdmb) {
        this.ivBdmb = pvSBdmb;
    }

    /**
     * @return the sBrl
     */
    public String getsBrl() {
        return this.ivBrl;
    }

    /**
     * @param pvSBrl the sBrl to set
     */
    public void setsBrl(String pvSBrl) {
        this.ivBrl = pvSBrl;
    }

    /**
     * @return the sBmfd
     */
    public String getsBmfd() {
        return this.ivBmfd;
    }

    /**
     * @param pvSBmfd the sBmfd to set
     */
    public void setsBmfd(String pvSBmfd) {
        this.ivBmfd = pvSBmfd;
    }

    /**
     * Setzt einen Wert des INF-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setINF(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
            case 4:
                this.getKopf().setsDwhtdb(pvWert);
                break;
            case 5:
                //System.out.println("Setze: " + wert);
                this.getKopf().setsDwheart(pvWert);
                break;
            case 6:
                this.getKopf().setsDwhart(pvWert);
                break;
            case 7:
                this.getKopf().setsDwhtyp(pvWert);
                break;
            case 8:
                this.getKopf().setsDwhres10(pvWert);
                break;
            case 9:
                this.getKopf().setsDwhres11(pvWert);
                break;
            case 10:
                this.getKopf().setsDwhres12(pvWert);
                break;
            case 11:
               this.setsBbart(pvWert);
              break;
           case 12:
               this.setsBwert(pvWert);
               break;
           case 13:
               this.setsBkto(pvWert);
              break;
          case 14:
               this.setsBb(pvWert);
             break;
          case 15:
              this.setsBbu(pvWert);
              break;
          case 16:
              this.setsBgbnr(pvWert);
              break;
          case 17:
              this.setsBdmb(pvWert);
               break;
          case 18:
              this.setsBrl(pvWert);
              break;
          case 19:
              this.setsBmfd(pvWert);
              break;
           default:
              System.out.println("INF: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() 
    {
        StringBuffer lvHelpStringBuffer = new StringBuffer("INF-Segment:" + StringKonverter.lineSeparator);
        lvHelpStringBuffer.append(this.ivKopf.toString());
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Informationsart/-typ für EDR: " + this.ivBbart);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Wertstellung: " + this.ivBwert);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Kontonummer: " + this.ivBkto);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Buchungstag: " + this.ivBb);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Buchungsuhrzeit: " + this.ivBbu);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Grundbuchnummer: " + this.ivBgbnr);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Betrag (Währung sh. BWISO): " + this.ivBdmb);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Schlüssel Rücklastschrift: " + this.ivBrl);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        lvHelpStringBuffer.append("Mahnfriststellungsdatum: " + this.ivBmfd);
        lvHelpStringBuffer.append(StringKonverter.lineSeparator);
        
        return lvHelpStringBuffer.toString();
    } 
    
    /**
     * Liefert die Anzahl der Tage zwischen dem 01.01.1900 
     * und der Wertstellung
     * @return 
     */
    public int getAnzahlDatum()
    {
        return DatumUtilities.berechneAnzahlTage(this.ivBwert);
    }

}
