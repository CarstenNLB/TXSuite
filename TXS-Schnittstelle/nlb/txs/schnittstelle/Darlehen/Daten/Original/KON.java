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
public class KON 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;
    
    /**
     * Gueltigkeitsdatum ab
     */
    private String ivKog;
    
    /**
     * Datum Einrichtung Kondition
     */                                       
    private String ivKokei;
          
    /**
     * % - Bereitstellungszinsen 
     */
    private String ivKobz;
    
    /**
     * % - Darlehenszinsen 
     */                                        
    private String ivKozi;
    
    /**
     * % - Zinsen Stundungsdarlehn                                        
     */
    private String ivKozs;
    
    /**
     * Schlüssel Tilgung
     */
    private String ivKoti;
    
    /**
     * % - Satz Tilgung 
     */
    private String ivKot;
    
    /**
     * Betrag Tilgungsrate
     */
    private String ivKotr;
    
    /**
     * Betrag Berechnungsnominale
     */
    private String ivKobno;
    
    /**
     * % - Annuitätenzinsen
     */
    private String ivKoaz;
    
    /**
     * Betrag Leistung (Annuität)
     */                                      
    private String ivKole;
    
    /**
     *  Schlüssel Nebenleistung 1
     */
    private String ivKosn1;
    
    /**
     * Art der Nebenleistung 1
     */
    private String ivKoan1;
    
    /**
     * % - Satz Nebenleistung 1                                    
     */
    private String ivKonl1;
    
    /**
     * Betrag Nebenleistung 1
     */
    private String ivKone1;   //                   
    
    /**
     * Schlüssel Nebenleistung 2
     */
    private String ivKosn2;
    
    /**
     * Art der Nebenleistung 2             
     */                                      
    private String ivKoan2;
    
    /**
     * % - Satz Nebenleistung 2
     */
    private String ivKonl2;
    
    /**
     * Betrag Nebenleistung 2
     */                                         
    private String ivKone2;               
    
    /**
     * Schl. Zuschuss/Zinsverbilligung
     */
    private String ivKoszv;
    
    /**
     * Betrag Zuschuss(Anlaufzuschuss)                                       
     */
    private String ivKodz;
    
    /**
     *  %-Satz Zinsverbilligung 
     */                                        
    private String ivKozv;
    
    /**
     * KD-AEND
     */                                        
    private String ivKomv;
    
    /**
     * Schlüssel Roll-Over                                       
     */
    private String ivKoro;     
        
    /**
     * Dieses Kennzeichen signalisiert, dass das Einrichtungsdatum '11110101' 
     * ist bzw. war
     */
    private boolean ivDatum11110101;
    
    /**
     * Konstruktor
     */
    public KON() 
    {
        this.ivKopf = new DWHKOPF();
        this.ivKog = new String();
        this.ivKokei = new String();
        this.ivKobz = new String();
        this.ivKozi = new String();
        this.ivKozs = new String();
        this.ivKoti = new String();
        this.ivKot = new String();
        this.ivKotr = new String();
        this.ivKobno = new String();
        this.ivKoaz = new String();
        this.ivKole = new String();
        this.ivKosn1 = new String();
        this.ivKoan1 = new String();
        this.ivKonl1 = new String();
        this.ivKone1 = new String();
        this.ivKosn2 = new String();
        this.ivKoan2 = new String();
        this.ivKonl2 = new String();
        this.ivKone2 = new String();
        this.ivKoszv = new String();
        this.ivKodz = new String();
        this.ivKozv = new String();
        this.ivKomv = new String();
        this.ivKoro = new String();
        this.ivDatum11110101 = false;
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
     * @return the sKog
     */
    public String getsKog() 
    {
        return this.ivKog;
    }

    /**
     * @param pvSKog the sKog to set
     */
    public void setsKog(String pvSKog) 
    {
        this.ivKog = pvSKog;
        if (pvSKog.equals("11110101"))
        {
            //System.out.println("sKog: " + pvSKog);
            ivDatum11110101 = true;
        }
    }

    /**
     * @return the sKokei
     */
    public String getsKokei() {
        return this.ivKokei;
    }

    /**
     * @param pvSKokei the sKokei to set
     */
    public void setsKokei(String pvSKokei) {
        this.ivKokei = pvSKokei;
    }

    /**
     * @return the sKobz
     */
    public String getsKobz() {
        return this.ivKobz;
    }

    /**
     * @param pvSKobz the sKobz to set
     */
    public void setsKobz(String pvSKobz) {
        this.ivKobz = pvSKobz;
    }

    /**
     * @return the sKozi
     */
    public String getsKozi() {
        return this.ivKozi;
    }

    /**
     * @param pvSKozi the sKozi to set
     */
    public void setsKozi(String pvSKozi) {
        this.ivKozi = pvSKozi;
    }

    /**
     * @return the sKozs
     */
    public String getsKozs() {
        return this.ivKozs;
    }

    /**
     * @param pvSKozs the sKozs to set
     */
    public void setsKozs(String pvSKozs) {
        this.ivKozs = pvSKozs;
    }

    /**
     * @return the sKoti
     */
    public String getsKoti() {
        return this.ivKoti;
    }

    /**
     * @param pvSKoti the sKoti to set
     */
    public void setsKoti(String pvSKoti) {
        this.ivKoti = pvSKoti;
    }

    /**
     * @return the sKot
     */
    public String getsKot() {
        return this.ivKot;
    }

    /**
     * @param pvSKot the sKot to set
     */
    public void setsKot(String pvSKot) {
        this.ivKot = pvSKot;
    }

    /**
     * @return the sKotr
     */
    public String getsKotr() {
        return this.ivKotr;
    }

    /**
     * @param pvSKotr the sKotr to set
     */
    public void setsKotr(String pvSKotr) {
        this.ivKotr = pvSKotr;
    }

    /**
     * @return the sKobno
     */
    public String getsKobno() {
        return this.ivKobno;
    }

    /**
     * @param pvSKobno the sKobno to set
     */
    public void setsKobno(String pvSKobno) {
        this.ivKobno = pvSKobno;
    }

    /**
     * @return the sKoaz
     */
    public String getsKoaz() {
        return this.ivKoaz;
    }

    /**
     * @param pvSKoaz the sKoaz to set
     */
    public void setsKoaz(String pvSKoaz) {
        this.ivKoaz = pvSKoaz;
    }

    /**
     * @return the sKole
     */
    public String getsKole() {
        return this.ivKole;
    }

    /**
     * @param pvSKole the sKole to set
     */
    public void setsKole(String pvSKole) {
        this.ivKole = pvSKole;
    }

    /**
     * @return the sKosn1
     */
    public String getsKosn1() {
        return this.ivKosn1;
    }

    /**
     * @param pvSKosn1 the sKosn1 to set
     */
    public void setsKosn1(String pvSKosn1) {
        this.ivKosn1 = pvSKosn1;
    }

    /**
     * @return the sKoan1
     */
    public String getsKoan1() {
        return this.ivKoan1;
    }

    /**
     * @param pvSKoan1 the sKoan1 to set
     */
    public void setsKoan1(String pvSKoan1) {
        this.ivKoan1 = pvSKoan1;
    }

    /**
     * @return the sKonl1
     */
    public String getsKonl1() {
        return this.ivKonl1;
    }

    /**
     * @param pvSKonl1 the sKonl1 to set
     */
    public void setsKonl1(String pvSKonl1) {
        this.ivKonl1 = pvSKonl1;
    }

    /**
     * @return the sKone1
     */
    public String getsKone1() {
        return this.ivKone1;
    }

    /**
     * @param pvSKone1 the sKone1 to set
     */
    public void setsKone1(String pvSKone1) {
        this.ivKone1 = pvSKone1;
    }

    /**
     * @return the sKosn2
     */
    public String getsKosn2() {
        return this.ivKosn2;
    }

    /**
     * @param pvSKosn2 the sKosn2 to set
     */
    public void setsKosn2(String pvSKosn2) {
        this.ivKosn2 = pvSKosn2;
    }

    /**
     * @return the sKoan2
     */
    public String getsKoan2() {
        return this.ivKoan2;
    }

    /**
     * @param pvSKoan2 the sKoan2 to set
     */
    public void setsKoan2(String pvSKoan2) {
        this.ivKoan2 = pvSKoan2;
    }

    /**
     * @return the sKonl2
     */
    public String getsKonl2() {
        return this.ivKonl2;
    }

    /**
     * @param pvSKonl2 the sKonl2 to set
     */
    public void setsKonl2(String pvSKonl2) {
        this.ivKonl2 = pvSKonl2;
    }

    /**
     * @return the sKone2
     */
    public String getsKone2() {
        return this.ivKone2;
    }

    /**
     * @param pvSKone2 the sKone2 to set
     */
    public void setsKone2(String pvSKone2) {
        this.ivKone2 = pvSKone2;
    }

    /**
     * @return the sKoszv
     */
    public String getsKoszv() {
        return this.ivKoszv;
    }

    /**
     * @param pvSKoszv the sKoszv to set
     */
    public void setsKoszv(String pvSKoszv) {
        this.ivKoszv = pvSKoszv;
    }

    /**
     * @return the sKodz
     */
    public String getsKodz() {
        return this.ivKodz;
    }

    /**
     * @param pvSKodz the sKodz to set
     */
    public void setsKodz(String pvSKodz) {
        this.ivKodz = pvSKodz;
    }

    /**
     * @return the sKozv
     */
    public String getsKozv() {
        return this.ivKozv;
    }

    /**
     * @param pvSKozv the sKozv to set
     */
    public void setsKozv(String pvSKozv) {
        this.ivKozv = pvSKozv;
    }

    /**
     * @return the sKomv
     */
    public String getsKomv() {
        return this.ivKomv;
    }

    /**
     * @param pvSKomv the sKomv to set
     */
    public void setsKomv(String pvSKomv) {
        this.ivKomv = pvSKomv;
    }

    /**
     * @return the sKoro
     */
    public String getsKoro() {
        return this.ivKoro;
    }

    /**
     * @param pvSKoro the sKoro to set
     */
    public void setsKoro(String pvSKoro) {
        this.ivKoro = pvSKoro;
    }   
    
    /**
     * Setzt einen Wert des KON-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setKON(int pvPos, String pvWert)
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
              this.setsKog(pvWert);
              break;
          case 12:
              this.setsKokei(pvWert);
              break;
          case 13:
              this.setsKobz(pvWert);
              break;
          case 14:
              this.setsKozi(pvWert);
              break;
          case 15:
              this.setsKozs(pvWert);
              break;
          case 16:
              this.setsKoti(pvWert);
              break;
          case 17:
              this.setsKot(pvWert);
              break;
          case 18:
              this.setsKotr(pvWert);
              break;
          case 19:
              this.setsKobno(pvWert);
              break;
          case 20:
              this.setsKoaz(pvWert);
              break;
          case 21:
              this.setsKole(pvWert);
              break;
          case 22:
              this.setsKosn1(pvWert);
              break;
          case 23:
              this.setsKoan1(pvWert);
              break;
          case 24:
              this.setsKonl1(pvWert);
              break;
          case 25:
              this.setsKone1(pvWert);
              break;
          case 26:
              this.setsKosn2(pvWert);
              break;
          case 27:
              this.setsKoan2(pvWert);
              break;
          case 28:
              this.setsKonl2(pvWert);
              break;
          case 29:
              this.setsKone2(pvWert);
              break;
          case 30:
              this.setsKoszv(pvWert);
              break;
          case 31:
              this.setsKodz(pvWert);
              break;
          case 32:
              this.setsKozv(pvWert);
              break;
          case 33:
              this.setsKomv(pvWert);
              break;
          case 34:
              this.setsKoro(pvWert);
              break;            
          default:
              System.out.println("KON: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 

    /**
     * Liefert die Anzahl der Tage zwischen dem 01.01.1900 
     * und dem Datum 'gueltig ab'
     * @return 
     */
    public int getAnzahlDatum()
    {
        return DatumUtilities.berechneAnzahlTage(this.ivKog);
    }

    /**
     * @return the datum11110101
     */
    public boolean isDatum11110101() 
    {
        return this.ivDatum11110101;
    }

    /**
     * @param pvDatum11110101 the datum11110101 to set
     */
    public void setDatum11110101(boolean pvDatum11110101) 
    {
        this.ivDatum11110101 = pvDatum11110101;
    }

    /**
     * Schreibt das KON-Segment in einen String
     * @return 
     */
    public String toString()
    {
        String lvHelpString = new String();
        lvHelpString = lvHelpString + "KON-Segment:" + StringKonverter.lineSeparator; 
        lvHelpString = lvHelpString + "Gueltigkeitsdatum: " + ivKog + StringKonverter.lineSeparator;
        
        return lvHelpString;
        
    }
}
