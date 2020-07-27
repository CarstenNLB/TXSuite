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
public class UMS 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;
    
    /**
     * Buchungstag
     */
    private String ivUbu; 
    
    /**
     * Wertstellungsdatum
     */
    private String ivUwert;          
    
    /**
     * Buchungsschluessel
     */
    private String ivUb;            
    
    /**
     * Betrag (Waehrung sh. UWISO)
     */
    private String ivUdmb;  
    
    /**
     * Grundbuchnummer
     */
    private String ivUgbnr;  
    
    /**
     * Konstruktor
     */
    public UMS() 
    {
        this.ivKopf = new DWHKOPF();
        this.ivUbu = new String();
        this.ivUwert = new String();
        this.ivUb = new String();
        this.ivUdmb = new String();
        this.ivUgbnr = new String();
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
     * @return the sUbu
     */
    public String getsUbu() {
        return this.ivUbu;
    }

    /**
     * @param pvSUbu the sUbu to set
     */
    public void setsUbu(String pvSUbu) {
        this.ivUbu = pvSUbu;
    }

    /**
     * @return the sUwert
     */
    public String getsUwert() {
        return this.ivUwert;
    }

    /**
     * @param pvSUwert the sUwert to set
     */
    public void setsUwert(String pvSUwert) {
        this.ivUwert = pvSUwert;
    }

    /**
     * @return the sUb
     */
    public String getsUb() {
        return this.ivUb;
    }

    /**
     * @param pvSUb the sUb to set
     */
    public void setsUb(String pvSUb) {
        this.ivUb = pvSUb;
    }

    /**
     * @return the sUdmb
     */
    public String getsUdmb() {
        return this.ivUdmb;
    }

    /**
     * @param pvSUdmb the sUdmb to set
     */
    public void setsUdmb(String pvSUdmb) {
        this.ivUdmb = pvSUdmb;
    }

    /**
     * @return the sUgbnr
     */
    public String getsUgbnr() {
        return this.ivUgbnr;
    }

    /**
     * @param pvSUgbnr the sUgbnr to set
     */
    public void setsUgbnr(String pvSUgbnr) {
        this.ivUgbnr = pvSUgbnr;
    }
    
    /**
     * Setzt einen Wert des UMS-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setUMS(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
          case 4:
              this.getKopf().setsDwhtdb(pvWert);
              break;
          case 5:
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
              this.setsUbu(pvWert);
              break;
          case 12:
              this.setsUwert(pvWert);
              break;
          case 13:
              this.setsUb(pvWert);
              break;
          case 14:
              this.setsUdmb(pvWert);
              break;
          case 15:
              this.setsUgbnr(pvWert);
              break;
           default:
              System.out.println("UMS: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 
    
    /**
     * Liefert die Anzahl der Tage zwischen dem 01.01.1900 
     * und dem Wertstellungsdatum
     * @return 
     */
    public int getAnzahlDatum()
    {
        return DatumUtilities.berechneAnzahlTage(this.ivUwert);
    }

}
