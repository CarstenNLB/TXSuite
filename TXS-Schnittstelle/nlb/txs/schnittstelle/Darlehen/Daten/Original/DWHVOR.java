/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten.Original;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;

/**
 * @author tepperc
 *
 */
public class DWHVOR 
{
    /**
     * Institutsnr.
     */ 
    private String ivDwvinst;
    
    /**
     * Anwendung
     */                                            
    private String ivDwvanw; 
    
    /**
     * Satztyp
     */
    private String ivDwvstyp;
    
    /**
     * Buchungsdatum JJJJMMTT                                              
     */
    private String ivDwvbdat;
    
    /**
     * Datum des Berichtsmonats JJJJMM
     */
    private String ivDwvbmon;
    
    /**
     * Datum der Bestandserstellung (Maschinendatum)
     */
    private String ivDwvdat;
    
    /**
     * Uhrzeit der Bestandserstellung (HHMMSSTH)
     */
    private String ivDwvuhr;

    /**
     * Versionsnr. z.B. 01.00
     */
    private String ivDwvver;
    
    /**
     * Typ = D500808 
     */
    private String ivDwvtyp;

    /**
     * Konstruktor
      */
    public DWHVOR() 
    {
        this.ivDwvinst = new String();
        this.ivDwvanw = new String();
        this.ivDwvstyp = new String();
        this.ivDwvbdat = new String();
        this.ivDwvbmon = new String();
        this.ivDwvdat = new String();
        this.ivDwvuhr = new String();
        this.ivDwvver = new String();
        this.ivDwvtyp = new String();
    }

    /**
     * @return the sDwvinst
     */
    public String getsDwvinst() {
        return this.ivDwvinst;
    }

    /**
     * @param pvSDwvinst the sDwvinst to set
     */
    public void setsDwvinst(String pvSDwvinst) {
        this.ivDwvinst = pvSDwvinst;
    }

    /**
     * @return the sDwvanw
     */
    public String getsDwvanw() {
        return this.ivDwvanw;
    }

    /**
     * @param pvSDwvanw the sDwvanw to set
     */
    public void setsDwvanw(String pvSDwvanw) {
        this.ivDwvanw = pvSDwvanw;
    }

    /**
     * @return the sDwvstyp
     */
    public String getsDwvstyp() {
        return this.ivDwvstyp;
    }

    /**
     * @param pvSDwvstyp the sDwvstyp to set
     */
    public void setsDwvstyp(String pvSDwvstyp) {
        this.ivDwvstyp = pvSDwvstyp;
    }

    /**
     * @return the sDwvbdat
     */
    public String getsDwvbdat() {
        return this.ivDwvbdat;
    }

    /**
     * @param pvSDwvbdat the sDwvbdat to set
     */
    public void setsDwvbdat(String pvSDwvbdat) {
        this.ivDwvbdat = pvSDwvbdat;
    }

    /**
     * @return the sDwvbmon
     */
    public String getsDwvbmon() {
        return this.ivDwvbmon;
    }

    /**
     * @param pvSDwvbmon the sDwvbmon to set
     */
    public void setsDwvbmon(String pvSDwvbmon) {
        this.ivDwvbmon = pvSDwvbmon;
    }

    /**
     * @return the sDwvdat
     */
    public String getsDwvdat() {
        return this.ivDwvdat;
    }

    /**
     * @param pvSDwvdat the sDwvdat to set
     */
    public void setsDwvdat(String pvSDwvdat) {
        this.ivDwvdat = pvSDwvdat;
    }

    /**
     * @return the sDwvuhr
     */
    public String getsDwvuhr() {
        return this.ivDwvuhr;
    }

    /**
     * @param pvSDwvuhr the sDwvuhr to set
     */
    public void setsDwvuhr(String pvSDwvuhr) {
        this.ivDwvuhr = pvSDwvuhr;
    }

    /**
     * @return the sDwvver
     */
    public String getsDwvver() {
        return this.ivDwvver;
    }

    /**
     * @param pvSDwvver the sDwvver to set
     */
    public void setsDwvver(String pvSDwvver) {
        this.ivDwvver = pvSDwvver;
    }

    /**
     * @return the sDwvtyp
     */
    public String getsDwvtyp() {
        return this.ivDwvtyp;
    }

    /**
     * @param pvSDwvtyp the sDwvtyp to set
     */
    public void setsDwvtyp(String pvSDwvtyp) {
        this.ivDwvtyp = pvSDwvtyp;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() 
    {
        return "DWHVOR [sDwvanw=" + this.ivDwvanw + ", sDwvbdat=" + this.ivDwvbdat + ", sDwvbmon=" + this.ivDwvbmon + ", sDwvdat=" + this.ivDwvdat
                + ", sDwvinst=" + this.ivDwvinst + ", sDwvstyp=" + this.ivDwvstyp + ", sDwvtyp=" + this.ivDwvtyp + ", sDwvuhr=" + this.ivDwvuhr + ", sDwvver="
                + this.ivDwvver + "]";
    }
    
    /**
     * Liefert die Anzahl der Tage zwischen dem 01.01.1900 
     * und dem Buchungsdatum
     * @return 
     */
    public int getAnzahlDatum()
    {
        //System.out.println("Buchungsdatum: " + this.sDwvbdat);
        return DatumUtilities.berechneAnzahlTage(this.ivDwvbdat);
    }


}
