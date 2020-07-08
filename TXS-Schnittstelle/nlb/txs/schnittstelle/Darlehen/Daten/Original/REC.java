/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten.Original;

/**
 * @author tepperc
 *
 */
public class REC 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;

    /**
     * Kennzeichen Ultimo
     */
    private String ivNkzult;
    
    /**
     * Verzinsendes Kapital/Tag 
     */
    private String ivNverzkt;
    
    /**
     * Durchschnittskapital lfd.Monat
     */
    private String ivNdukamo;
    
    /**
     * Offene Leistung rückständig Tilg.
     */
    private String ivNolrt;
    
    /**
     * Offene Leistung rückständig Zins
     */
    private String ivNolrz;   
    
    /**
     * Offene Leistung rückständig Gebü.
     */
    private String ivNolrg;
    
    /**
     * Offene Leist.noch nicht fällig Tilg. 
     */
    private String ivNolnt;
    
    /**
     * Offene Leist.noch nicht fällig Zins.
     */                                        
    private String ivNolnz;
    
    /**
     * Offene Leist.noch nicht fällig Gebü. 
     */
    private String ivNolng;
    
    /**
     * Abgrenzung einbehalten Agio 
     */
    private String ivNabea;
    
    /**
     * Abgrenzung einbehalten Disagio
     */
    private String ivNabed;
    
    /**
     * Abgrenzung einbehalten Gebühren
     */
    private String ivNabeg;
    
    /**
     * noch abzugrenzen Agio 
     */
    private String ivNabna;
    
    /**
     * noch abzugrenzen Disagio
     */
    private String ivNabnd;   
    
    /**
     * noch abzugrenzen Gebühren
     */
    private String ivNabng;
    
    /**
     * noch abzugrenzen Agio per 31.12.
     */
    private String ivNab31A;
    
    /**
     * noch abzugrenz.Disagio per 31.12.
     */
    private String ivNab31D;
    
    /**
     * noch abzugrenz.Gebühr.per 31.12.
     */
    private String ivNab31G;
    
    /**
     * Kennzeichen fest/variabel
     */
    private String ivNkzfeva;
    
    /**
     * Kennzeichen aktiv/passiv  
     */
    private String ivNkzakpa;
    
    /**
     * Girokontonummer Zinsen
     */
    private String ivNgknrz;
    
    /**
     * Girokontonummer Tilgung
     */
    private String ivNgknrt;
    
    /**
     * Girokontonummer Gebühren
     */
    private String ivNgknrg;
    
    /**
     * Bankleitzahl Zinsen
     */
    private String ivNblzz;
    
    /**
     * Bankleitzahl Tilgung
     */
    private String ivNblzt;
    
    /**
     * Bankleitzahl Gebühren
     */
    private String ivNblzg;
    
    /**
     * Summe rückständige Leistung
     */
    private String ivSurule;
    
    /**
     * Summe noch nicht fäll. Tilgung 
     */
    private String ivSunnft;
    
    /**
     * Saldo Stundungsanteil
     */
    private String ivSalstd;
    
    /**
     * Summe Vorauszahlungen
     */
    private String ivSuvorz;
    
    /**
     * Konsortialanteil SURULE
     */
    private String ivKsurule;
    
    /**
     * Konsortialanteil SUNNFT
     */
    private String ivKsunnft;
    
    /**
     *  Konsortialanteil SALSTD
     */
    private String ivKsalstd;
    
    /**
     * Konsortialanteil SUVORZ
     */
    private String ivKsuvorz;
    
    /**
     * Konsortialanteil Restkapital 
     */
    private String ivKdrk;
    
    /**
     * Gesamtforderung (sh.Anm.)
     */
    private String ivGesfor;
    
    /**
     * Konsotialanteil Gesamtforderung
     */
    private String ivKgesfor;
    
    /**
     * Anteilige ZINSEN
     */
    private String ivNanzi;
    
    /**
     * Kundengruppe
     */
    private String ivNkungr;
    
    /**
     * Kreditart  
     */
    private String ivNkreart;
    
    /**
     * Konsortialanteil Ursprungskapital
     */
    private String ivNkukap;
    
    /**
     * Gebuchte Zinsen
     */
    private String ivNgebz;
    
    /**
     * Gebuchte weitere Erfolge
     */
    private String ivNgebw;
    
    /**
     * Anteilige Zinsen (IFRS)
     */
    private String ivNantz;
    
    /**
     * Anteilige weitere Erfolge 
     */
    private String ivNantw;
    
    /**
     * Anteilige Zinsen / Vorjahresende
     */
    private String ivN31vz;
    
    /**
     * Anteilige weitere Erfolge /VJ-Ende
     */
    private String ivN31vw;
    
    /**
     * Gebuchte,sofort vereinnahmte Zinserfolge
     */
    private String ivNgebs;
    
    /**
     * Gebuchte,sofort vereinnahmte Nebenleistungen
     */
    private String ivNgebn;
    
    /**
     * Gebuchte,sofort verauslagte Provision
     */
    private String ivNgebp;
    
    /**
     * Disagiodifferenz 
     */
    private String ivNdifd;
    
    /**
     * Kapitallaufzeitende 
     */
    private String ivNkape;
    
    /**
     * Gebuchte Abgrenzung Agio
     */
    private String ivNgaba;
    
    /**
     * Gebuchte Abgrenzung Disagio
     */
    private String ivNgabd;
    
    /**
     * Gebuchte Abgrenzung Gebühren
     */
    private String ivNgabg;
    
    /**
     * in NGEBZ enthaltene Bereitstellungzinsen
     */
    private String ivNgebb;
    
    /**
     * in NANTZ enthaltene Bereitstellungzinsen
     */
    private String ivNantb;
    
    /**
     * in N31VZ enthaltene Bereitstellungzinsen
     */
    private String ivN31vb;
    
    /**
     * gebuchte, nicht zugeordnete Bereitstellgs.zs.
     */
    private String ivNbznz;
    
    /**
     * im lfd Jahr als Geschvorfall ang.BeZs 
     */
    private String ivNbzgv;
    
    /**
     * im lfd Jahr sofort vereinnahmte BerZs 
     */
    private String ivNbzsv;
    
    /**
     * Termin nächste Tilgungsleistung
     */
    private String ivNtilt;
    
    /**
     * Betrag nächste Tilgungsleistung
     */
    private String ivNtilb;
    
    /**
     * Termin nächste Zinsleistung
     */
    private String ivNzint;
    
    /**
     * Betrag nächste Zinsleistung 
     */
    private String ivNzinb;
    
    /**
     * Betrag nächste Nebenleistung
     */
    private String ivNnebb;                

    /**
     * Umsatz12
     */
    private String ivUmsatz12;
    
    /**
     * Umsatz19
     */
    private String ivUmsatz19;
    
    /**
     * Konstruktor
     */
    public REC() 
    {
        this.ivKopf = new DWHKOPF();
        this.ivNkzult = new String();
        this.ivNverzkt = new String();
        this.ivNdukamo = new String();
        this.ivNolrt = new String();
        this.ivNolrz = new String();
        this.ivNolrg = new String();
        this.ivNolnt = new String();
        this.ivNolnz = new String();
        this.ivNolng = new String();
        this.ivNabea = new String();
        this.ivNabed = new String();
        this.ivNabeg = new String();
        this.ivNabna = new String();
        this.ivNabnd = new String();
        this.ivNabng = new String();
        this.ivNab31A = new String();
        this.ivNab31D = new String();
        this.ivNab31G = new String();
        this.ivNkzfeva = new String();
        this.ivNkzakpa = new String();
        this.ivNgknrz = new String();
        this.ivNgknrt = new String();
        this.ivNgknrg = new String();
        this.ivNblzz = new String();
        this.ivNblzt = new String();
        this.ivNblzg = new String();
        this.ivSurule = new String();
        this.ivSunnft = new String();
        this.ivSalstd = new String();
        this.ivSuvorz = new String();
        this.ivKsurule = new String();
        this.ivKsunnft = new String();
        this.ivKsalstd = new String();
        this.ivKsuvorz = new String();
        this.ivKdrk = new String();
        this.ivGesfor = new String();
        this.ivKgesfor = new String();
        this.ivNanzi = new String();
        this.ivNkungr = new String();
        this.ivNkreart = new String();
        this.ivNkukap = new String();
        this.ivNgebz = new String();
        this.ivNgebw = new String();
        this.ivNantz = new String();
        this.ivNantw = new String();
        this.ivN31vz = new String();
        this.ivN31vw = new String();
        this.ivNgebs = new String();
        this.ivNgebn = new String();
        this.ivNgebp = new String();
        this.ivNdifd = new String();
        this.ivNkape = new String();
        this.ivNgaba = new String();
        this.ivNgabd = new String();
        this.ivNgabg = new String();
        this.ivNgebb = new String();
        this.ivNantb = new String();
        this.ivN31vb = new String();
        this.ivNbznz = new String();
        this.ivNbzgv = new String();
        this.ivNbzsv = new String();
        this.ivNtilt = new String();
        this.ivNtilb = new String();
        this.ivNzint = new String();
        this.ivNzinb = new String();
        this.ivNnebb = new String();
        this.ivUmsatz12 = new String();
        this.ivUmsatz19 = new String();
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
     * @return the sNkzult
     */
    public String getsNkzult() {
        return this.ivNkzult;
    }

    /**
     * @param pvSNkzult the sNkzult to set
     */
    public void setsNkzult(String pvSNkzult) {
        this.ivNkzult = pvSNkzult;
    }

    /**
     * @return the sNverzkt
     */
    public String getsNverzkt() {
        return this.ivNverzkt;
    }

    /**
     * @param pvSNverzkt the sNverzkt to set
     */
    public void setsNverzkt(String pvSNverzkt) {
        this.ivNverzkt = pvSNverzkt;
    }

    /**
     * @return the sNdukamo
     */
    public String getsNdukamo() {
        return this.ivNdukamo;
    }

    /**
     * @param pvSNdukamo the sNdukamo to set
     */
    public void setsNdukamo(String pvSNdukamo) {
        this.ivNdukamo = pvSNdukamo;
    }

    /**
     * @return the sNolrt
     */
    public String getsNolrt() {
        return this.ivNolrt;
    }

    /**
     * @param pvSNolrt the sNolrt to set
     */
    public void setsNolrt(String pvSNolrt) {
        this.ivNolrt = pvSNolrt;
    }

    /**
     * @return the sNolrz
     */
    public String getsNolrz() {
        return this.ivNolrz;
    }

    /**
     * @param pvSNolrz the sNolrz to set
     */
    public void setsNolrz(String pvSNolrz) {
        this.ivNolrz = pvSNolrz;
    }

    /**
     * @return the sNolrg
     */
    public String getsNolrg() {
        return this.ivNolrg;
    }

    /**
     * @param pvSNolrg the sNolrg to set
     */
    public void setsNolrg(String pvSNolrg) {
        this.ivNolrg = pvSNolrg;
    }

    /**
     * @return the sNolnt
     */
    public String getsNolnt() {
        return this.ivNolnt;
    }

    /**
     * @param pvSNolnt the sNolnt to set
     */
    public void setsNolnt(String pvSNolnt) {
        this.ivNolnt = pvSNolnt;
    }

    /**
     * @return the sNolnz
     */
    public String getsNolnz() {
        return this.ivNolnz;
    }

    /**
     * @param pvSNolnz the sNolnz to set
     */
    public void setsNolnz(String pvSNolnz) {
        this.ivNolnz = pvSNolnz;
    }

    /**
     * @return the sNolng
     */
    public String getsNolng() {
        return this.ivNolng;
    }

    /**
     * @param pvSNolng the sNolng to set
     */
    public void setsNolng(String pvSNolng) {
        this.ivNolng = pvSNolng;
    }

    /**
     * @return the sNabea
     */
    public String getsNabea() {
        return this.ivNabea;
    }

    /**
     * @param pvSNabea the sNabea to set
     */
    public void setsNabea(String pvSNabea) {
        this.ivNabea = pvSNabea;
    }

    /**
     * @return the sNabed
     */
    public String getsNabed() {
        return this.ivNabed;
    }

    /**
     * @param pvSNabed the sNabed to set
     */
    public void setsNabed(String pvSNabed) {
        this.ivNabed = pvSNabed;
    }

    /**
     * @return the sNabeg
     */
    public String getsNabeg() {
        return this.ivNabeg;
    }

    /**
     * @param pvSNabeg the sNabeg to set
     */
    public void setsNabeg(String pvSNabeg) {
        this.ivNabeg = pvSNabeg;
    }

    /**
     * @return the sNabna
     */
    public String getsNabna() {
        return this.ivNabna;
    }

    /**
     * @param pvSNabna the sNabna to set
     */
    public void setsNabna(String pvSNabna) {
        this.ivNabna = pvSNabna;
    }

    /**
     * @return the sNabnd
     */
    public String getsNabnd() {
        return this.ivNabnd;
    }

    /**
     * @param pvSNabnd the sNabnd to set
     */
    public void setsNabnd(String pvSNabnd) {
        this.ivNabnd = pvSNabnd;
    }

    /**
     * @return the sNabng
     */
    public String getsNabng() {
        return this.ivNabng;
    }

    /**
     * @param pvSNabng the sNabng to set
     */
    public void setsNabng(String pvSNabng) {
        this.ivNabng = pvSNabng;
    }

    /**
     * @return the sNab31A
     */
    public String getsNab31A() {
        return this.ivNab31A;
    }

    /**
     * @param pvSNab31A the sNab31A to set
     */
    public void setsNab31A(String pvSNab31A) {
        this.ivNab31A = pvSNab31A;
    }

    /**
     * @return the sNab31D
     */
    public String getsNab31D() {
        return this.ivNab31D;
    }

    /**
     * @param pvSNab31D the sNab31D to set
     */
    public void setsNab31D(String pvSNab31D) {
        this.ivNab31D = pvSNab31D;
    }

    /**
     * @return the sNab31G
     */
    public String getsNab31G() {
        return this.ivNab31G;
    }

    /**
     * @param pvSNab31G the sNab31G to set
     */
    public void setsNab31G(String pvSNab31G) {
        this.ivNab31G = pvSNab31G;
    }

    /**
     * @return the sNkzfeva
     */
    public String getsNkzfeva() {
        return this.ivNkzfeva;
    }

    /**
     * @param pvSNkzfeva the sNkzfeva to set
     */
    public void setsNkzfeva(String pvSNkzfeva) {
        this.ivNkzfeva = pvSNkzfeva;
    }

    /**
     * @return the sNkzakpa
     */
    public String getsNkzakpa() {
        return this.ivNkzakpa;
    }

    /**
     * @param pvSNkzakpa the sNkzakpa to set
     */
    public void setsNkzakpa(String pvSNkzakpa) {
        this.ivNkzakpa = pvSNkzakpa;
    }

    /**
     * @return the sNgknrz
     */
    public String getsNgknrz() {
        return this.ivNgknrz;
    }

    /**
     * @param pvSNgknrz the sNgknrz to set
     */
    public void setsNgknrz(String pvSNgknrz) {
        this.ivNgknrz = pvSNgknrz;
    }

    /**
     * @return the sNgknrt
     */
    public String getsNgknrt() {
        return this.ivNgknrt;
    }

    /**
     * @param pvSNgknrt the sNgknrt to set
     */
    public void setsNgknrt(String pvSNgknrt) {
        this.ivNgknrt = pvSNgknrt;
    }

    /**
     * @return the sNgknrg
     */
    public String getsNgknrg() {
        return this.ivNgknrg;
    }

    /**
     * @param pvSNgknrg the sNgknrg to set
     */
    public void setsNgknrg(String pvSNgknrg) {
        this.ivNgknrg = pvSNgknrg;
    }

    /**
     * @return the sNblzz
     */
    public String getsNblzz() {
        return this.ivNblzz;
    }

    /**
     * @param pvSNblzz the sNblzz to set
     */
    public void setsNblzz(String pvSNblzz) {
        this.ivNblzz = pvSNblzz;
    }

    /**
     * @return the sNblzt
     */
    public String getsNblzt() {
        return this.ivNblzt;
    }

    /**
     * @param pvSNblzt the sNblzt to set
     */
    public void setsNblzt(String pvSNblzt) {
        this.ivNblzt = pvSNblzt;
    }

    /**
     * @return the sNblzg
     */
    public String getsNblzg() {
        return this.ivNblzg;
    }

    /**
     * @param pvSNblzg the sNblzg to set
     */
    public void setsNblzg(String pvSNblzg) {
        this.ivNblzg = pvSNblzg;
    }

    /**
     * @return the sSurule
     */
    public String getsSurule() {
        return this.ivSurule;
    }

    /**
     * @param pvSSurule the sSurule to set
     */
    public void setsSurule(String pvSSurule) {
        this.ivSurule = pvSSurule;
    }

    /**
     * @return the sSunnft
     */
    public String getsSunnft() {
        return this.ivSunnft;
    }

    /**
     * @param pvSSunnft the sSunnft to set
     */
    public void setsSunnft(String pvSSunnft) {
        this.ivSunnft = pvSSunnft;
    }

    /**
     * @return the sSalstd
     */
    public String getsSalstd() {
        return this.ivSalstd;
    }

    /**
     * @param pvSSalstd the sSalstd to set
     */
    public void setsSalstd(String pvSSalstd) {
        this.ivSalstd = pvSSalstd;
    }

    /**
     * @return the sSuvorz
     */
    public String getsSuvorz() {
        return this.ivSuvorz;
    }

    /**
     * @param pvSSuvorz the sSuvorz to set
     */
    public void setsSuvorz(String pvSSuvorz) {
        this.ivSuvorz = pvSSuvorz;
    }

    /**
     * @return the sKsurule
     */
    public String getsKsurule() {
        return this.ivKsurule;
    }

    /**
     * @param pvSKsurule the sKsurule to set
     */
    public void setsKsurule(String pvSKsurule) {
        this.ivKsurule = pvSKsurule;
    }

    /**
     * @return the sKsunnft
     */
    public String getsKsunnft() {
        return this.ivKsunnft;
    }

    /**
     * @param pvSKsunnft the sKsunnft to set
     */
    public void setsKsunnft(String pvSKsunnft) {
        this.ivKsunnft = pvSKsunnft;
    }

    /**
     * @return the sKsalstd
     */
    public String getsKsalstd() {
        return this.ivKsalstd;
    }

    /**
     * @param pvSKsalstd the sKsalstd to set
     */
    public void setsKsalstd(String pvSKsalstd) {
        this.ivKsalstd = pvSKsalstd;
    }

    /**
     * @return the sKsuvorz
     */
    public String getsKsuvorz() {
        return this.ivKsuvorz;
    }

    /**
     * @param pvSKsuvorz the sKsuvorz to set
     */
    public void setsKsuvorz(String pvSKsuvorz) {
        this.ivKsuvorz = pvSKsuvorz;
    }

    /**
     * @return the sKdrk
     */
    public String getsKdrk() {
        return this.ivKdrk;
    }

    /**
     * @param pvSKdrk the sKdrk to set
     */
    public void setsKdrk(String pvSKdrk) {
        this.ivKdrk = pvSKdrk;
    }

    /**
     * @return the sGesfor
     */
    public String getsGesfor() {
        return this.ivGesfor;
    }

    /**
     * @param pvSGesfor the sGesfor to set
     */
    public void setsGesfor(String pvSGesfor) {
        this.ivGesfor = pvSGesfor;
    }

    /**
     * @return the sKgesfor
     */
    public String getsKgesfor() {
        return this.ivKgesfor;
    }

    /**
     * @param pvSKgesfor the sKgesfor to set
     */
    public void setsKgesfor(String pvSKgesfor) {
        this.ivKgesfor = pvSKgesfor;
    }

    /**
     * @return the sNanzi
     */
    public String getsNanzi() {
        return this.ivNanzi;
    }

    /**
     * @param pvSNanzi the sNanzi to set
     */
    public void setsNanzi(String pvSNanzi) {
        this.ivNanzi = pvSNanzi;
    }

    /**
     * @return the sNkungr
     */
    public String getsNkungr() {
        return this.ivNkungr;
    }

    /**
     * @param pvSNkungr the sNkungr to set
     */
    public void setsNkungr(String pvSNkungr) {
        this.ivNkungr = pvSNkungr;
    }

    /**
     * @return the sNkreart
     */
    public String getsNkreart() {
        return this.ivNkreart;
    }

    /**
     * @param pvSNkreart the sNkreart to set
     */
    public void setsNkreart(String pvSNkreart) {
        this.ivNkreart = pvSNkreart;
    }

    /**
     * @return the sNkukap
     */
    public String getsNkukap() {
        return this.ivNkukap;
    }

    /**
     * @param pvSNkukap the sNkukap to set
     */
    public void setsNkukap(String pvSNkukap) {
        this.ivNkukap = pvSNkukap;
    }

    /**
     * @return the sNgebz
     */
    public String getsNgebz() {
        return this.ivNgebz;
    }

    /**
     * @param pvSNgebz the sNgebz to set
     */
    public void setsNgebz(String pvSNgebz) {
        this.ivNgebz = pvSNgebz;
    }

    /**
     * @return the sNgebw
     */
    public String getsNgebw() {
        return this.ivNgebw;
    }

    /**
     * @param pvSNgebw the sNgebw to set
     */
    public void setsNgebw(String pvSNgebw) {
        this.ivNgebw = pvSNgebw;
    }

    /**
     * @return the sNantz
     */
    public String getsNantz() {
        return this.ivNantz;
    }

    /**
     * @param pvSNantz the sNantz to set
     */
    public void setsNantz(String pvSNantz) {
        this.ivNantz = pvSNantz;
    }

    /**
     * @return the sNantw
     */
    public String getsNantw() {
        return this.ivNantw;
    }

    /**
     * @param pvSNantw the sNantw to set
     */
    public void setsNantw(String pvSNantw) {
        this.ivNantw = pvSNantw;
    }

    /**
     * @return the sN31vz
     */
    public String getsN31vz() {
        return this.ivN31vz;
    }

    /**
     * @param pvSN31vz the sN31vz to set
     */
    public void setsN31vz(String pvSN31vz) {
        this.ivN31vz = pvSN31vz;
    }

    /**
     * @return the sN31vw
     */
    public String getsN31vw() {
        return this.ivN31vw;
    }

    /**
     * @param pvSN31vw the sN31vw to set
     */
    public void setsN31vw(String pvSN31vw) {
        this.ivN31vw = pvSN31vw;
    }

    /**
     * @return the sNgebs
     */
    public String getsNgebs() {
        return this.ivNgebs;
    }

    /**
     * @param pvSNgebs the sNgebs to set
     */
    public void setsNgebs(String pvSNgebs) {
        this.ivNgebs = pvSNgebs;
    }

    /**
     * @return the sNgebn
     */
    public String getsNgebn() {
        return this.ivNgebn;
    }

    /**
     * @param pvSNgebn the sNgebn to set
     */
    public void setsNgebn(String pvSNgebn) {
        this.ivNgebn = pvSNgebn;
    }

    /**
     * @return the sNgebp
     */
    public String getsNgebp() {
        return this.ivNgebp;
    }

    /**
     * @param pvSNgebp the sNgebp to set
     */
    public void setsNgebp(String pvSNgebp) {
        this.ivNgebp = pvSNgebp;
    }

    /**
     * @return the sNdifd
     */
    public String getsNdifd() {
        return this.ivNdifd;
    }

    /**
     * @param pvSNdifd the sNdifd to set
     */
    public void setsNdifd(String pvSNdifd) {
        this.ivNdifd = pvSNdifd;
    }

    /**
     * @return the sNkape
     */
    public String getsNkape() {
        return this.ivNkape;
    }

    /**
     * @param pvSNkape the sNkape to set
     */
    public void setsNkape(String pvSNkape) {
        this.ivNkape = pvSNkape;
    }

    /**
     * @return the sNgaba
     */
    public String getsNgaba() {
        return this.ivNgaba;
    }

    /**
     * @param pvSNgaba the sNgaba to set
     */
    public void setsNgaba(String pvSNgaba) {
        this.ivNgaba = pvSNgaba;
    }

    /**
     * @return the sNgabd
     */
    public String getsNgabd() {
        return this.ivNgabd;
    }

    /**
     * @param pvSNgabd the sNgabd to set
     */
    public void setsNgabd(String pvSNgabd) {
        this.ivNgabd = pvSNgabd;
    }

    /**
     * @return the sNgabg
     */
    public String getsNgabg() {
        return this.ivNgabg;
    }

    /**
     * @param pvSNgabg the sNgabg to set
     */
    public void setsNgabg(String pvSNgabg) {
        this.ivNgabg = pvSNgabg;
    }

    /**
     * @return the sNgebb
     */
    public String getsNgebb() {
        return this.ivNgebb;
    }

    /**
     * @param pvSNgebb the sNgebb to set
     */
    public void setsNgebb(String pvSNgebb) {
        this.ivNgebb = pvSNgebb;
    }

    /**
     * @return the sNantb
     */
    public String getsNantb() {
        return this.ivNantb;
    }

    /**
     * @param pvSNantb the sNantb to set
     */
    public void setsNantb(String pvSNantb) {
        this.ivNantb = pvSNantb;
    }

    /**
     * @return the sN31vb
     */
    public String getsN31vb() {
        return this.ivN31vb;
    }

    /**
     * @param pvSN31vb the sN31vb to set
     */
    public void setsN31vb(String pvSN31vb) {
        this.ivN31vb = pvSN31vb;
    }

    /**
     * @return the sNbznz
     */
    public String getsNbznz() {
        return this.ivNbznz;
    }

    /**
     * @param pvSNbznz the sNbznz to set
     */
    public void setsNbznz(String pvSNbznz) {
        this.ivNbznz = pvSNbznz;
    }

    /**
     * @return the sNbzgv
     */
    public String getsNbzgv() {
        return this.ivNbzgv;
    }

    /**
     * @param pvSNbzgv the sNbzgv to set
     */
    public void setsNbzgv(String pvSNbzgv) {
        this.ivNbzgv = pvSNbzgv;
    }

    /**
     * @return the sNbzsv
     */
    public String getsNbzsv() {
        return this.ivNbzsv;
    }

    /**
     * @param pvSNbzsv the sNbzsv to set
     */
    public void setsNbzsv(String pvSNbzsv) {
        this.ivNbzsv = pvSNbzsv;
    }

    /**
     * @return the sNtilt
     */
    public String getsNtilt() {
        return this.ivNtilt;
    }

    /**
     * @param pvSNtilt the sNtilt to set
     */
    public void setsNtilt(String pvSNtilt) {
        this.ivNtilt = pvSNtilt;
    }

    /**
     * @return the sNtilb
     */
    public String getsNtilb() {
        return this.ivNtilb;
    }

    /**
     * @param pvSNtilb the sNtilb to set
     */
    public void setsNtilb(String pvSNtilb) {
        this.ivNtilb = pvSNtilb;
    }

    /**
     * @return the sNzint
     */
    public String getsNzint() {
        return this.ivNzint;
    }

    /**
     * @param pvSNzint the sNzint to set
     */
    public void setsNzint(String pvSNzint) {
        this.ivNzint = pvSNzint;
    }

    /**
     * @return the sNzinb
     */
    public String getsNzinb() {
        return this.ivNzinb;
    }

    /**
     * @param pvSNzinb the sNzinb to set
     */
    public void setsNzinb(String pvSNzinb) {
        this.ivNzinb = pvSNzinb;
    }

    /**
     * @return the sNnebb
     */
    public String getsNnebb() {
        return this.ivNnebb;
    }

    /**
     * @param pvSNnebb the sNnebb to set
     */
    public void setsNnebb(String pvSNnebb) {
        this.ivNnebb = pvSNnebb;
    }
    
    /**
     * @return 
     */
    public String getUmsatz12()
    {
        return this.ivUmsatz12;
    }
    
    /**
     * @param pvUmsatz12 
     */
    public void setUmsatz12(String pvUmsatz12)
    {
        this.ivUmsatz12 = pvUmsatz12;
    }
    
    /**
     * @return 
     */
    public String getUmsatz19()
    {
        return this.ivUmsatz19;
    }
    
    /**
     * @param pvUmsatz19 
     */
    public void setUmsatz19(String pvUmsatz19)
    {
        this.ivUmsatz19 = pvUmsatz19;
    } 

    /**
     * Setzt einen Wert des REC-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setREC(int pvPos, String pvWert)
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
                this.setsNkzult(pvWert);
               break;
          case 12:
                this.setsNverzkt(pvWert);
              break;
          case 13:
                this.setsNdukamo(pvWert);
              break;
          case 14:
                this.setsNolrt(pvWert);
              break;
          case 15:
                this.setsNolrz(pvWert);
              break;
          case 16:
                this.setsNolrg(pvWert);
              break;
          case 17:
                this.setsNolnt(pvWert);
              break;
          case 18:
                this.setsNolnz(pvWert);
              break;
          case 19:
                this.setsNolng(pvWert);
              break;
          case 20:
                this.setsNabea(pvWert);
              break;
          case 21:
                this.setsNabed(pvWert);
              break;
          case 22:
                this.setsNabeg(pvWert);
              break;
          case 23:
                this.setsNabna(pvWert);
              break;
          case 24:
                this.setsNabnd(pvWert);
              break;
          case 25:
                this.setsNabng(pvWert);
              break;
          case 26:
                this.setsNab31A(pvWert);
              break;
          case 27:
                this.setsNab31D(pvWert);
              break;
          case 28:
                this.setsNab31G(pvWert);
              break;
          case 29:
                this.setsNkzfeva(pvWert);
              break;
          case 30:
                this.setsNkzakpa(pvWert);
              break;
          case 31:
                this.setsNgknrz(pvWert);
              break;
          case 32:
                this.setsNgknrt(pvWert);
              break;
          case 33:
                this.setsNgknrg(pvWert);
              break;
          case 34:
                this.setsNblzz(pvWert);
              break;
          case 35:
                this.setsNblzt(pvWert);
              break;
          case 36:
                this.setsNblzg(pvWert);
              break;
          case 37:
                this.setsSurule(pvWert);
              break;
          case 38:
                this.setsSunnft(pvWert);
              break;
          case 39:
                this.setsSalstd(pvWert);
              break;
          case 40:
                this.setsSuvorz(pvWert);
              break;
          case 41:
                this.setsKsurule(pvWert);
              break;
          case 42:
                this.setsKsunnft(pvWert);
              break;
          case 43:
                this.setsKsalstd(pvWert);
              break;
          case 44:
                this.setsKsuvorz(pvWert);
              break;
          case 45:
                this.setsKdrk(pvWert);
              break;
          case 46:
                this.setsGesfor(pvWert);
              break;
          case 47:
                this.setsKgesfor(pvWert);
              break;
          case 48:
                this.setsNanzi(pvWert);
              break;
          case 49:
                this.setsNkungr(pvWert);
              break;
          case 50:
                this.setsNkreart(pvWert);
              break;
          case 51:
                this.setsNkukap(pvWert);
              break;
          case 52:
                this.setsNgebz(pvWert);
              break;
          case 53:
                this.setsNgebw(pvWert);
              break;
          case 54:
                this.setsNantz(pvWert);
              break;
          case 55:
                this.setsNantw(pvWert);
              break;
          case 56:
                this.setsN31vz(pvWert);
              break;
          case 57:
                this.setsN31vw(pvWert);
              break;
          case 58:
                this.setsNgebs(pvWert);
              break;
          case 59:
                this.setsNgebn(pvWert);
              break;
          case 60:
                this.setsNgebp(pvWert);
              break;
          case 61:
                this.setsNdifd(pvWert);
              break;
          case 62:
                this.setsNkape(pvWert);
              break;
          case 63:
                this.setsNgaba(pvWert);
              break;
          case 64:
                this.setsNgabd(pvWert);
              break;
          case 65:
                this.setsNgabg(pvWert);
              break;
          case 66:
                this.setsNgebb(pvWert);
              break;
          case 67:
                this.setsNantb(pvWert);
              break;
          case 68:
                this.setsN31vb(pvWert);
              break;
          case 69:
                this.setsNbznz(pvWert);
              break;
          case 70:
                this.setsNbzgv(pvWert);
              break;
          case 71:
                this.setsNbzsv(pvWert);
              break;
          case 72:
                this.setsNtilt(pvWert);
              break;
          case 73:
                this.setsNtilb(pvWert);
              break;
          case 74:
                this.setsNzint(pvWert);
              break;
          case 75:
                this.setsNzinb(pvWert);
              break;
          case 76:
                this.setsNnebb(pvWert);
              break;
          case 77:
                this.setUmsatz12(pvWert);
                break;
          case 78:
                this.setUmsatz19(pvWert);
                break;
           default:
                System.out.println("REC: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 
}
