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
public class KTOZB 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;
    
    /**
     * gueltig ab ........
     */
    private String ivZbg;
    
    /**
     * Einrichtungsdatum
     */                                         
    private String ivZbein;
    
    /**
     * Änderungsdatum
     */                                         
    private String ivZbaen;
    
    /**
     * Datum Konditionierung vom
     */                                          
    private String ivZbkv;             
    
    /**
     * Datum Konditionsannahme
     */
    private String ivZbda;
    
    /**
     * Datum Zinsanpassung
     */
    private String ivZbza;
    
    /**
     *  Laufzeit Zinsanpassung
     */
    private String ivZblza;      
    
    /**
     * %-Auszahlungskurs
     */                                         
    private String ivZbak;
    
    /**
     * Schl. Zinsvereinbarung (Fest/Variabel)
     */                                           
    private String ivZbzv;
    
    /**
     * %-Satz:Abweichung Berechnungsgrundlage
     */                                       
    private String ivZbabg;
    
    /**
     * Berechnungsgrundlage (z. B. LIBOR)
     */
    private String ivZbbg;
    
    /**
     * %-Effektivzinssatz                                       
     */
    private String ivZbez;
    
    /**
     * %-Einstandssatz
     */
    private String ivZbes;
    
    /**
     * %-Marge 
     */                                           
    private String ivZbma;
    
    /**
     * %-Barwert
     */                                         
    private String ivZbbarw;
    
    /**
     * Margenbarwert (Betrag)
     */                                        
    private String ivZbmbw;
    
    /**
     * %-Satz Risikoaufschlag                                          
     */
    private String ivZbras;
    
    /**
     * %-Satz Bereichsaufschlag
     */                                       
    private String ivZbbas;            
    
    /**
     * Konstruktor
     */
    public KTOZB() 
    {
        this.ivKopf = new DWHKOPF();
        this.ivZbg = new String();
        this.ivZbein = new String();
        this.ivZbaen = new String();
        this.ivZbkv = new String();
        this.ivZbda = new String();
        this.ivZbza = new String();
        this.ivZblza = new String();
        this.ivZbak = new String();
        this.ivZbzv = new String();
        this.ivZbabg = new String();
        this.ivZbbg = new String();
        this.ivZbez = new String();
        this.ivZbes = new String();
        this.ivZbma = new String();
        this.ivZbbarw = new String();
        this.ivZbmbw = new String();
        this.ivZbras = new String();
        this.ivZbbas = new String();
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
     * @return the sZbg
     */
    public String getsZbg() {
        return this.ivZbg;
    }

    /**
     * @param pvSZbg the sZbg to set
     */
    public void setsZbg(String pvSZbg) {
        this.ivZbg = pvSZbg;
    }

    /**
     * @return the sZbein
     */
    public String getsZbein() {
        return this.ivZbein;
    }

    /**
     * @param pvSZbein the sZbein to set
     */
    public void setsZbein(String pvSZbein) {
        this.ivZbein = pvSZbein;
    }

    /**
     * @return the sZbaen
     */
    public String getsZbaen() {
        return this.ivZbaen;
    }

    /**
     * @param pvSZbaen the sZbaen to set
     */
    public void setsZbaen(String pvSZbaen) {
        this.ivZbaen = pvSZbaen;
    }

    /**
     * @return the sZbkv
     */
    public String getsZbkv() {
        return this.ivZbkv;
    }

    /**
     * @param pvSZbkv the sZbkv to set
     */
    public void setsZbkv(String pvSZbkv) {
        this.ivZbkv = pvSZbkv;
    }

    /**
     * @return the sZbda
     */
    public String getsZbda() {
        return this.ivZbda;
    }

    /**
     * @param pvSZbda the sZbda to set
     */
    public void setsZbda(String pvSZbda) {
        this.ivZbda = pvSZbda;
    }

    /**
     * @return the sZbza
     */
    public String getsZbza() {
        return this.ivZbza;
    }

    /**
     * @param pvSZbza the sZbza to set
     */
    public void setsZbza(String pvSZbza) {
        this.ivZbza = pvSZbza;
    }

    /**
     * @return the sZblza
     */
    public String getsZblza() {
        return this.ivZblza;
    }

    /**
     * @param pvSZblza the sZblza to set
     */
    public void setsZblza(String pvSZblza) {
        this.ivZblza = pvSZblza;
    }

    /**
     * @return the sZbak
     */
    public String getsZbak() {
        return this.ivZbak;
    }

    /**
     * @param pvSZbak the sZbak to set
     */
    public void setsZbak(String pvSZbak) {
        this.ivZbak = pvSZbak;
    }

    /**
     * @return the sZbzv
     */
    public String getsZbzv() {
        return this.ivZbzv;
    }

    /**
     * @param pvSZbzv the sZbzv to set
     */
    public void setsZbzv(String pvSZbzv) {
        this.ivZbzv = pvSZbzv;
    }

    /**
     * @return the sZbabg
     */
    public String getsZbabg() {
        return this.ivZbabg;
    }

    /**
     * @param pvSZbabg the sZbabg to set
     */
    public void setsZbabg(String pvSZbabg) {
        this.ivZbabg = pvSZbabg;
    }

    /**
     * @return the sZbbg
     */
    public String getsZbbg() {
        return this.ivZbbg;
    }

    /**
     * @param pvSZbbg the sZbbg to set
     */
    public void setsZbbg(String pvSZbbg) {
        this.ivZbbg = pvSZbbg;
    }

    /**
     * @return the sZbez
     */
    public String getsZbez() {
        return this.ivZbez;
    }

    /**
     * @param pvSZbez the sZbez to set
     */
    public void setsZbez(String pvSZbez) {
        this.ivZbez = pvSZbez;
    }

    /**
     * @return the sZbes
     */
    public String getsZbes() {
        return this.ivZbes;
    }

    /**
     * @param pvSZbes the sZbes to set
     */
    public void setsZbes(String pvSZbes) {
        this.ivZbes = pvSZbes;
    }

    /**
     * @return the sZbma
     */
    public String getsZbma() {
        return this.ivZbma;
    }

    /**
     * @param pvSZbma the sZbma to set
     */
    public void setsZbma(String pvSZbma) {
        this.ivZbma = pvSZbma;
    }

    /**
     * @return the sZbbarw
     */
    public String getsZbbarw() {
        return this.ivZbbarw;
    }

    /**
     * @param pvSZbbarw the sZbbarw to set
     */
    public void setsZbbarw(String pvSZbbarw) {
        this.ivZbbarw = pvSZbbarw;
    }

    /**
     * @return the sZbmbw
     */
    public String getsZbmbw() {
        return this.ivZbmbw;
    }

    /**
     * @param pvSZbmbw the sZbmbw to set
     */
    public void setsZbmbw(String pvSZbmbw) {
        this.ivZbmbw = pvSZbmbw;
    }

    /**
     * @return the sZbras
     */
    public String getsZbras() {
        return this.ivZbras;
    }

    /**
     * @param pvSZbras the sZbras to set
     */
    public void setsZbras(String pvSZbras) {
        this.ivZbras = pvSZbras;
    }

    /**
     * @return the sZbbas
     */
    public String getsZbbas() {
        return this.ivZbbas;
    }

    /**
     * @param pvSZbbas the sZbbas to set
     */
    public void setsZbbas(String pvSZbbas) {
        this.ivZbbas = pvSZbbas;
    }

    /**
     * Setzt einen Wert des KTOZB-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setKTOZB(int pvPos, String pvWert)
    {
        //System.out.println("Pos: " + pos + " Wert: " + wert);
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
              this.setsZbg(pvWert);
             break;
          case 12:
              this.setsZbein(pvWert);
              break;
          case 13:
              this.setsZbaen(pvWert);
              break;
          case 14:
              this.setsZbkv(pvWert);
              break;
          case 15:
              this.setsZbda(pvWert);
              break;
          case 16:
              this.setsZbza(pvWert);
              break;
          case 17:
              this.setsZblza(pvWert);
              break;
          case 18:
              this.setsZbak(pvWert);
              break;
          case 19:
              this.setsZbzv(pvWert);
              break;
          case 20:
              this.setsZbabg(pvWert);
              break;
          case 21:
              this.setsZbbg(pvWert);
              break;
          case 22:
              this.setsZbez(pvWert);
              break;
          case 23:
              this.setsZbes(pvWert);
              break;
          case 24:
              this.setsZbma(pvWert);
              break;
          case 25:
              this.setsZbbarw(pvWert);
              break;
          case 26:
              this.setsZbmbw(pvWert);
              break;
          case 27:
              this.setsZbras(pvWert);
              break;
          case 28:
              this.setsZbbas(pvWert);
              break;
          default:
              System.out.println("KTOZB: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 

    /**
     * Liefert die Anzahl der Tage zwischen dem 01.01.1900 
     * und dem Datum 'gueltig ab'
     * @return 
     */
    public int getAnzahlDatum()
    {
        if (this.ivZbg.trim().isEmpty())
        {
            System.out.println("'gueltig ab' nicht gefuellt...");
        }
        return DatumUtilities.berechneAnzahlTage(this.ivZbg);
    }
    
    /**
     * Schreibt das KON-Segment in einen String
     * @return 
     */
    public String toString()
    {
        String lvHelpString = new String();
        lvHelpString = lvHelpString + "KTOZB-Segment:" + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Gueltigkeitsdatum: " + ivZbg + StringKonverter.lineSeparator;
        
        return lvHelpString;
        
    }

}
