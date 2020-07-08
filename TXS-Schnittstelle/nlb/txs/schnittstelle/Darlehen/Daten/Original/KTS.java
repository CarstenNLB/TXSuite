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
public class KTS 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;
   
    /**
     * Konto-Einrichtung 
     */
    private String ivDein;
    
    /**
     * Konto-Änderung
     */                                    
    private String ivDaen;
    
    /**
     * Datum der Konto-Eröffnung
     */                                    
    private String ivDer;
    
    /**
     * Datum der Vollauszahlung
     */                                     
    private String ivDdv;
    
    /**
     * Datum der Schließung
     */                                    
    private String ivDds;
    
    /**
     * Nächste Zinsfälligkeit
     */                                     
    private String ivDnf; 
 
    /**
     * Nächste Tilgungsfälligkeit
     */
    private String ivDntf;
    
    /**
     * Tilgungsbeginn
     */                                     
    private String ivDtb;
    
    /**
     * Datum der Annahme
     */                                    
    private String ivDda;
    
    /**
     * Datum der Bewilligung
     */
    private String ivDdb;
    
    /**
     * Datum Zinsanpassung
     */                                    
    private String ivDza;
    
    /**
     * Datum letzte Zinsanpassung
     */                                     
    private String ivDlzip;
    
    /**
     * Datum letzte Statistikmeldung
     */                                    
    private String ivDdstm;  
    
    /**
     * Ursprungskapital 
     */
    private String ivDu;
    
    /**
     * Auszahlungsverpflichtung
     */
    private String ivDav;
    
    /**
     * Restkapital (Saldo)
     */
    private String ivDrk;
    
    /**
     * Saldo Stundungsdarlehen
     */
    private String ivDst;
    
    /**
     * Passivkontonr. / Einheitsdarlehn
     */
    private String ivDpk;
   
    /**
     * Finanzkontor. (Bestandkto.)
     */
    private String ivDfk;
    
    /**
     * Bewilligende Organisationseinh. 
     */                                 
    private String ivDbs;
    
    /**
     * Verwaltende Organisationseinheit
     */
    private String ivDvs;
    
    /**
     * Produktverantwortliche OE (PAS) 
     */
    private String ivDpdv;
    
    /**
     * Produktschlüssel 
     */
    private String ivDpd;
    
    /**
     * Schlüssel Darlehensbesonderh.
     */
    private String ivDbes;
    
    /**
     * Schlüssel Refinanzierung
     */                                     
    private String ivDr;
    
    /**
     * Schlüssel Kompensation/Saldier.
     */
    private String ivDkom;
    
    /**
     * Kontozustand
     */                                   
    private String ivDkz;
    
    /**
     * Schlüssel Sollstellung
     */
    private String ivDso;
    
    /**
     * Schlüssel Zinsrechnung
     */
    private String ivDzr;
    
    /**
     * Schlüssel Fristigkeit
     */
    private String ivDf;
    
    /**
     * Schlüssel Sicherheiten 
     */                                 
    private String ivDsi;
    
    /**
     * Schlüssel Bürgschaft
     */
    private String ivDsb;
    
    /**
     * Währungsschlüssel Darlehnskonto
     */
    private String ivDwiso;
     
    /**
     * Konstruktor
     */
    public KTS() 
    {
        this.ivKopf = new DWHKOPF();
        this.ivDein = new String();
        this.ivDaen = new String();
        this.ivDer = new String();
        this.ivDdv = new String();
        this.ivDds = new String();
        this.ivDnf = new String();
        this.ivDntf = new String();
        this.ivDtb = new String();
        this.ivDda = new String();
        this.ivDdb = new String();
        this.ivDza = new String();
        this.ivDlzip = new String();
        this.ivDdstm = new String();
        this.ivDu = new String();
        this.ivDav = new String();
        this.ivDrk = new String();
        this.ivDst = new String();
        this.ivDpk = new String();
        this.ivDfk = new String();
        this.ivDbs = new String();
        this.ivDvs = new String();
        this.ivDpdv = new String();
        this.ivDpd = new String();
        this.ivDbes = new String();
        this.ivDr = new String();
        this.ivDkom = new String();
        this.ivDkz = new String();
        this.ivDso = new String();
        this.ivDzr = new String();
        this.ivDf = new String();
        this.ivDsi = new String();
        this.ivDsb = new String();
        this.ivDwiso = new String();
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
     * @return the sDein
     */
    public String getsDein() {
        return this.ivDein;
    }

    /**
     * @param pvSDein the sDein to set
     */
    public void setsDein(String pvSDein) {
        this.ivDein = pvSDein;
    }

    /**
     * @return the sDaen
     */
    public String getsDaen() {
        return this.ivDaen;
    }

    /**
     * @param pvSDaen the sDaen to set
     */
    public void setsDaen(String pvSDaen) {
        this.ivDaen = pvSDaen;
    }

    /**
     * @return the sDer
     */
    public String getsDer() {
        return this.ivDer;
    }

    /**
     * @param pvSDer the sDer to set
     */
    public void setsDer(String pvSDer) {
        this.ivDer = pvSDer;
    }

    /**
     * @return the sDdv
     */
    public String getsDdv() {
        return this.ivDdv;
    }

    /**
     * @param pvSDdv the sDdv to set
     */
    public void setsDdv(String pvSDdv) {
        this.ivDdv = pvSDdv;
    }

    /**
     * @return the sDds
     */
    public String getsDds() {
        return this.ivDds;
    }

    /**
     * @param pvSDds the sDds to set
     */
    public void setsDds(String pvSDds) {
        this.ivDds = pvSDds;
    }

    /**
     * @return the sDnf
     */
    public String getsDnf() {
        return this.ivDnf;
    }

    /**
     * @param pvSDnf the sDnf to set
     */
    public void setsDnf(String pvSDnf) {
        this.ivDnf = pvSDnf;
    }

    /**
     * @return the sDntf
     */
    public String getsDntf() {
        return this.ivDntf;
    }

    /**
     * @param pvSDntf the sDntf to set
     */
    public void setsDntf(String pvSDntf) {
        this.ivDntf = pvSDntf;
    }

    /**
     * @return the sDtb
     */
    public String getsDtb() {
        return this.ivDtb;
    }

    /**
     * @param pvSDtb the sDtb to set
     */
    public void setsDtb(String pvSDtb) {
        this.ivDtb = pvSDtb;
    }

    /**
     * @return the sDda
     */
    public String getsDda() {
        return this.ivDda;
    }

    /**
     * @param pvSDda the sDda to set
     */
    public void setsDda(String pvSDda) {
        this.ivDda = pvSDda;
    }

    /**
     * @return the sDdb
     */
    public String getsDdb() {
        return this.ivDdb;
    }

    /**
     * @param pvSDdb the sDdb to set
     */
    public void setsDdb(String pvSDdb) {
        this.ivDdb = pvSDdb;
    }

    /**
     * @return the sDza
     */
    public String getsDza() {
        return this.ivDza;
    }

    /**
     * @param pvSDza the sDza to set
     */
    public void setsDza(String pvSDza) {
        this.ivDza = pvSDza;
    }

    /**
     * @return the sDlzip
     */
    public String getsDlzip() {
        return this.ivDlzip;
    }

    /**
     * @param pvSDlzip the sDlzip to set
     */
    public void setsDlzip(String pvSDlzip) {
        this.ivDlzip = pvSDlzip;
    }

    /**
     * @return the sDdstm
     */
    public String getsDdstm() {
        return this.ivDdstm;
    }

    /**
     * @param pvSDdstm the sDdstm to set
     */
    public void setsDdstm(String pvSDdstm) {
        this.ivDdstm = pvSDdstm;
    }

    /**
     * @return the sDu
     */
    public String getsDu() {
        return this.ivDu;
    }

    /**
     * @param pvSDu the sDu to set
     */
    public void setsDu(String pvSDu) {
        this.ivDu = pvSDu;
    }

    /**
     * @return the sDav
     */
    public String getsDav() {
        return this.ivDav;
    }

    /**
     * @param pvSDav the sDav to set
     */
    public void setsDav(String pvSDav) {
        this.ivDav = pvSDav;
    }

    /**
     * @return the sDrk
     */
    public String getsDrk() {
        return this.ivDrk;
    }

    /**
     * @param pvSDrk the sDrk to set
     */
    public void setsDrk(String pvSDrk) {
        this.ivDrk = pvSDrk;
    }

    /**
     * @return the sDst
     */
    public String getsDst() {
        return this.ivDst;
    }

    /**
     * @param pvSDst the sDst to set
     */
    public void setsDst(String pvSDst) {
        this.ivDst = pvSDst;
    }

    /**
     * @return the sDpk
     */
    public String getsDpk() {
        return this.ivDpk;
    }

    /**
     * @param pvSDpk the sDpk to set
     */
    public void setsDpk(String pvSDpk) {
        this.ivDpk = pvSDpk;
    }

    /**
     * @return the sDfk
     */
    public String getsDfk() {
        return this.ivDfk;
    }

    /**
     * @param pvSDfk the sDfk to set
     */
    public void setsDfk(String pvSDfk) {
        this.ivDfk = pvSDfk;
    }

    /**
     * @return the sDbs
     */
    public String getsDbs() {
        return this.ivDbs;
    }

    /**
     * @param pvSDbs the sDbs to set
     */
    public void setsDbs(String pvSDbs) {
        this.ivDbs = pvSDbs;
    }

    /**
     * @return the sDvs
     */
    public String getsDvs() {
        return this.ivDvs;
    }

    /**
     * @param pvSDvs the sDvs to set
     */
    public void setsDvs(String pvSDvs) {
        this.ivDvs = pvSDvs;
    }

    /**
     * @return the sDpdv
     */
    public String getsDpdv() {
        return this.ivDpdv;
    }

    /**
     * @param pvSDpdv the sDpdv to set
     */
    public void setsDpdv(String pvSDpdv) {
        this.ivDpdv = pvSDpdv;
    }

    /**
     * @return the sDpd
     */
    public String getsDpd() {
        return this.ivDpd;
    }

    /**
     * @param pvSDpd the sDpd to set
     */
    public void setsDpd(String pvSDpd) {
        this.ivDpd = pvSDpd;
    }

    /**
     * @return the sDbes
     */
    public String getsDbes() {
        return this.ivDbes;
    }

    /**
     * @param pvSDbes the sDbes to set
     */
    public void setsDbes(String pvSDbes) {
        this.ivDbes = pvSDbes;
    }

    /**
     * @return the sDr
     */
    public String getsDr() {
        return this.ivDr;
    }

    /**
     * @param pvSDr the sDr to set
     */
    public void setsDr(String pvSDr) {
        this.ivDr = pvSDr;
    }

    /**
     * @return the sDkom
     */
    public String getsDkom() {
        return this.ivDkom;
    }

    /**
     * @param pvSDkom the sDkom to set
     */
    public void setsDkom(String pvSDkom) {
        this.ivDkom = pvSDkom;
    }

    /**
     * @return the sDkz
     */
    public String getsDkz() {
        return this.ivDkz;
    }

    /**
     * @param pvSDkz the sDkz to set
     */
    public void setsDkz(String pvSDkz) {
        this.ivDkz = pvSDkz;
    }

    /**
     * @return the sDso
     */
    public String getsDso() {
        return this.ivDso;
    }

    /**
     * @param pvSDso the sDso to set
     */
    public void setsDso(String pvSDso) {
        this.ivDso = pvSDso;
    }

    /**
     * @return the sDzr
     */
    public String getsDzr() {
        return this.ivDzr;
    }

    /**
     * @param pvSDzr the sDzr to set
     */
    public void setsDzr(String pvSDzr) {
        this.ivDzr = pvSDzr;
    }

    /**
     * @return the sDf
     */
    public String getsDf() {
        return this.ivDf;
    }

    /**
     * @param pvSDf the sDf to set
     */
    public void setsDf(String pvSDf) {
        this.ivDf = pvSDf;
    }

    /**
     * @return the sDsi
     */
    public String getsDsi() {
        return this.ivDsi;
    }

    /**
     * @param pvSDsi the sDsi to set
     */
    public void setsDsi(String pvSDsi) {
        this.ivDsi = pvSDsi;
    }

    /**
     * @return the sDsb
     */
    public String getsDsb() {
        return this.ivDsb;
    }

    /**
     * @param pvSDsb the sDsb to set
     */
    public void setsDsb(String pvSDsb) {
        this.ivDsb = pvSDsb;
    }

    /**
     * @return the sDwiso
     */
    public String getsDwiso() {
        return this.ivDwiso;
    }

    /**
     * @param pvSDwiso the sDwiso to set
     */
    public void setsDwiso(String pvSDwiso) {
        this.ivDwiso = pvSDwiso;
    }

    /**
     * Setzt einen Wert des KTS-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setKTS(int pvPos, String pvWert)
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
              this.setsDein(pvWert);
             break;
          case 12:
              this.setsDaen(pvWert);
              break;
          case 13:
              this.setsDer(pvWert);
              break;
          case 14:
              this.setsDdv(pvWert);
              break;
          case 15:
              this.setsDds(pvWert);
              break;
          case 16:
              this.setsDnf(pvWert);
              break;
          case 17:
              this.setsDntf(pvWert);
              break;
          case 18:
              this.setsDtb(pvWert);
              break;
          case 19:
              this.setsDda(pvWert);
              break;
          case 20:
              this.setsDdb(pvWert);
              break;
          case 21:
              this.setsDza(pvWert);
              break;
          case 22:
              this.setsDlzip(pvWert);
              break;
          case 23:
              this.setsDdstm(pvWert);
              break;
          case 24:
              this.setsDu(pvWert);
              break;
          case 25:
              this.setsDav(pvWert);
              break;
          case 26:
              this.setsDrk(pvWert);
              break;
          case 27:
              this.setsDst(pvWert);
              break;
          case 28:
              this.setsDpk(pvWert);
              break;
          case 29:
              this.setsDfk(pvWert);
              break;
          case 30:
              this.setsDbs(pvWert);
              break;
          case 31:
              this.setsDvs(pvWert);
              break;
          case 32:
              this.setsDpdv(pvWert);
              break;
          case 33:
              this.setsDpd(pvWert);
              break;
          case 34:
              this.setsDbes(pvWert);
              break;
          case 35:
              this.setsDr(pvWert);
              break;
          case 36:
              this.setsDkom(pvWert);
              break;
          case 37:
              this.setsDkz(pvWert);
              break;
          case 38:
              this.setsDso(pvWert);
              break;
          case 39:
              this.setsDzr(pvWert);
              break;
          case 40:
              this.setsDf(pvWert);
              break;
          case 41:
              this.setsDsi(pvWert);
              break;
          case 42:
              this.setsDsb(pvWert);
              break;
          case 43:
              this.setsDwiso(pvWert);
              break;
          default:
              System.out.println("KTS: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 

    /**
     * Liefert die Anzahl der Tage zwischen dem 01.01.1900 
     * und dem Datum 'gueltig ab'
     * @return 
     */
    public int getAnzahlDatum()
    {
        return DatumUtilities.berechneAnzahlTage(this.ivDza);
    }

}
