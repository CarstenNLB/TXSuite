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
public class KONTS 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;
    
    /**
     * Konditionsart
     */
    private String ivKsart;
    
    /**
     * gueltig ab 
     */                                    
    private String ivKsg;
    
    /**
     * Einrichtungsdatum
     */
    private String ivKsein;
    
    /**
     * Aenderungsdatum
     */
    private String ivKsaen;
                                         
    /**
     * Datum naechste Faelligkeit
     */
    private String ivKsnf;
    
    /**
     * Datum naechstes Periodenende
     */                                     
    private String ivKsnpe;
                                         
    /**
     * Datum naechster Abschreibungstermin
     */
    private String ivKsnta;
    
    /**
     * Anzahl Abschreibungstermine
     */                                      
    private String ivKsta;
    
    /**
     * Anzahl Perioden
     */
    private String ivKsap;
    
    /**
     * Periodengroesse
     */
    private String ivKspg;
    
    /**
     * Schl. Ultimofaelligkeit
     */
    private String ivKsuf;
    
    /**
     * Schl. Faelligkeitsbesonderheit
     */
    private String ivKsfb;
    
    /**
     * Schl. Besonderheit 
     */
    private String ivKsbes;                
    
    /**
     * Schl. Sollstellung
     */
    private String ivKsso;                
    
    /**
     * Schl. Rechnungsmodus
     */
    private String ivKsrm;              
    
    /**
     * %-Niedrigstzinssatz
     */
    private String ivKsns;              
    
    /**
     * %-Hoechstzinssatz
     */
    private String ivKshs; 
        
    /**
     * Konstruktor 
     */
    public KONTS() 
    {
        this.ivKopf = new DWHKOPF();
        this.ivKsart = new String();
        this.ivKsg = new String();
        this.ivKsein = new String();
        this.ivKsaen = new String();
        this.ivKsnf = new String();
        this.ivKsnpe = new String();
        this.ivKsnta = new String();
        this.ivKsta = new String();
        this.ivKsap = new String();
        this.ivKspg = new String();
        this.ivKsuf = new String();
        this.ivKsfb = new String();
        this.ivKsbes = new String();
        this.ivKsso = new String();
        this.ivKsrm = new String();
        this.ivKsns = new String();
        this.ivKshs = new String();
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
     * @return the sKsart
     */
    public String getsKsart() {
        return this.ivKsart;
    }

    /**
     * @param pvSKsart the sKsart to set
     */
    public void setsKsart(String pvSKsart) {
        this.ivKsart = pvSKsart;
    }

    /**
     * @return the sKsg
     */
    public String getsKsg() {
        return this.ivKsg;
    }

    /**
     * @param pvSKsg the sKsg to set
     */
    public void setsKsg(String pvSKsg) {
        this.ivKsg = pvSKsg;
    }

    /**
     * @return the sKsein
     */
    public String getsKsein() {
        return this.ivKsein;
    }

    /**
     * @param pvSKsein the sKsein to set
     */
    public void setsKsein(String pvSKsein) {
        this.ivKsein = pvSKsein;
    }

    /**
     * @return the sKsaen
     */
    public String getsKsaen() {
        return this.ivKsaen;
    }

    /**
     * @param pvSKsaen the sKsaen to set
     */
    public void setsKsaen(String pvSKsaen) {
        this.ivKsaen = pvSKsaen;
    }

    /**
     * @return the sKsnf
     */
    public String getsKsnf() {
        return this.ivKsnf;
    }

    /**
     * @param pvSKsnf the sKsnf to set
     */
    public void setsKsnf(String pvSKsnf) {
        this.ivKsnf = pvSKsnf;
    }

    /**
     * @return the sKsnpe
     */
    public String getsKsnpe() {
        return this.ivKsnpe;
    }

    /**
     * @param pvSKsnpe the sKsnpe to set
     */
    public void setsKsnpe(String pvSKsnpe) {
        this.ivKsnpe = pvSKsnpe;
    }

    /**
     * @return the sKsnta
     */
    public String getsKsnta() {
        return this.ivKsnta;
    }

    /**
     * @param pvSKsnta the sKsnta to set
     */
    public void setsKsnta(String pvSKsnta) {
        this.ivKsnta = pvSKsnta;
    }

    /**
     * @return the sKsta
     */
    public String getsKsta() {
        return this.ivKsta;
    }

    /**
     * @param pvSKsta the sKsta to set
     */
    public void setsKsta(String pvSKsta) {
        this.ivKsta = pvSKsta;
    }

    /**
     * @return the sKsap
     */
    public String getsKsap() {
        return this.ivKsap;
    }

    /**
     * @param pvSKsap the sKsap to set
     */
    public void setsKsap(String pvSKsap) {
        this.ivKsap = pvSKsap;
    }

    /**
     * @return the sKspg
     */
    public String getsKspg() {
        return this.ivKspg;
    }

    /**
     * @param pvSKspg the sKspg to set
     */
    public void setsKspg(String pvSKspg) {
        this.ivKspg = pvSKspg;
    }

    /**
     * @return the sKsuf
     */
    public String getsKsuf() {
        return this.ivKsuf;
    }

    /**
     * @param pvSKsuf the sKsuf to set
     */
    public void setsKsuf(String pvSKsuf) {
        this.ivKsuf = pvSKsuf;
    }

    /**
     * @return the sKsfb
     */
    public String getsKsfb() {
        return this.ivKsfb;
    }

    /**
     * @param pvSKsfb the sKsfb to set
     */
    public void setsKsfb(String pvSKsfb) {
        this.ivKsfb = pvSKsfb;
    }

    /**
     * @return the sKsbes
     */
    public String getsKsbes() {
        return this.ivKsbes;
    }

    /**
     * @param pvSKsbes the sKsbes to set
     */
    public void setsKsbes(String pvSKsbes) {
        this.ivKsbes = pvSKsbes;
    }

    /**
     * @return the sKsso
     */
    public String getsKsso() {
        return this.ivKsso;
    }

    /**
     * @param pvSKsso the sKsso to set
     */
    public void setsKsso(String pvSKsso) {
        this.ivKsso = pvSKsso;
    }

    /**
     * @return the sKsrm
     */
    public String getsKsrm() {
        return this.ivKsrm;
    }

    /**
     * @param pvSKsrm the sKsrm to set
     */
    public void setsKsrm(String pvSKsrm) {
        this.ivKsrm = pvSKsrm;
    }

    /**
     * @return the sKsns
     */
    public String getsKsns() {
        return this.ivKsns;
    }

    /**
     * @param pvSKsns the sKsns to set
     */
    public void setsKsns(String pvSKsns) {
        this.ivKsns = pvSKsns;
    }

    /**
     * @return the sKshs
     */
    public String getsKshs() {
        return this.ivKshs;
    }

    /**
     * @param pvSKshs the sKshs to set
     */
    public void setsKshs(String pvSKshs) {
        this.ivKshs = pvSKshs;
    }

    /**
     * Setzt einen Wert des KONTS-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setKONTS(int pvPos, String pvWert)
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
               this.setsKsart(pvWert);
              break;
           case 12:
               this.setsKsg(pvWert);
              break;
           case 13:
               this.setsKsein(pvWert);
               break;
           case 14:
               this.setsKsaen(pvWert);
               break;
           case 15:
               this.setsKsnf(pvWert);
               break;
           case 16:
               this.setsKsnpe(pvWert);
               break;
           case 17:
               this.setsKsnta(pvWert);
               break;
           case 18:
               this.setsKsta(pvWert);
               break;
           case 19:
               this.setsKsap(pvWert);
               break;
           case 20:
               this.setsKspg(pvWert);
               break;
           case 21:
               this.setsKsuf(pvWert);
               break;
           case 22:
               this.setsKsfb(pvWert);
               break;
           case 23:
               this.setsKsbes(pvWert);
               break;
           case 24:
               this.setsKsso(pvWert);
               break;
           case 25:
               this.setsKsrm(pvWert);
               break;
           case 26:
               this.setsKsns(pvWert);
               break;
           case 27:
               this.setsKshs(pvWert);
               break;
           default:
              System.out.println("KONTS: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 
    
    /**
     * Liefert die Anzahl der Tage zwischen dem 01.01.1900 
     * und dem Datum 'gueltig ab'
     * @return 
     */
    public int getAnzahlDatum()
    {
        return DatumUtilities.berechneAnzahlTage(this.ivKsg);
    }

}
