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
public class KTR 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;

    /**
     * Schl. Manuelle Leistungsrechnung
     */
    private String ivDml;
    
    /**
     * Zusatzangaben, Zeile 1                                          
     */
    private String ivDzus1;
    
    /**
     * Zusatzangaben, Zeile 2
     */
    private String ivDzus2; 
    
    /**
     * Schluessel Verwendungszweck
     */                                          
    private String ivDv;
    
    /**
     * Schl. Wohnungs-/Neubau-Finanz.
     */                                         
    private String ivDwo;
    
    /**
     * %-Satz Auszahlungskurs
     */                                          
    private String ivDak;
    
    /**
     * Naechstes Zinsperiodenende
     */                                         
    private String ivDnz;
    
    /**
     * Naechstes Tilgungsperiodenende
     */
    private String ivDnt;   
    
    /**
     * Naechster Tilgungsabschr.Termin
     */
    private String ivDnta;
    
    /**
     * Anzahl Zinsperioden                                      
     */
    private String ivDzp;
    
    /**
     * Anzahl Tilgungsperioden
     */
    private String ivDtp;   
    
    /**
     * Anzahl Tilgungsabschreibungstermine
     */                                          
    private String ivDta;
    
    /**
     * Betreuer
     */                                        
    private String ivDbtr;                             

    /**
     * Bewilligungsbescheid-Nr.
     */
    private String ivDbba;
    
    /**
     * Ordnungsbegriff Grosskunde
     */
    private String ivDog;
    
    /**
     * Schluessel Deckung
     */                                           
    private String ivDd; 
    
    /**
     * Prozentsatz Deckung
     */                                          
    private String ivDde;
    
    /**
     * Kundennr. des Buergen
     */                                        
    private String ivDkb;
    
    /**
     * %-Satz Buergschaft
     */                                          
    private String ivDbue;
    
    /**
     * Schluessel Wertberichtigung
     */                                           
    private String ivDwb;
    
    /**
     * Laufzeit der Zinsanpassung 
     */
    private String ivDlz;
    
    /**
     * %-Satz Effektivzins                                         
     */
    private String ivDe;
    
    /**
     * Kontonummer Konsortialhauptkonto
     */                                         
    private String ivDuk;
    
    /**
     * Gesamtbetrag Konsortialkredit 
     */                                           
    private String ivDgk;
    
    /**
     * Kunden-Nummer des Konsortialfuehrers/Pege
     */                                         
    private String ivDkkf;
    
    /**
     * Berichtigungsposten Zins.Stundungsdarl.
     */                                         
    private String ivDbszi;

    /**
     * Korrespondenznummer 
     */
    private String ivDkn;
    
    /**
     * Datum der Konditionierung                                      
     */
    private String ivDkv;
    
    /**
     * Schluessel Tilgung
     */                                       
    private String ivDti;
    
    /**
     * Letztes Zinsperiodenende
     */
    private String ivDlze;
    
    /**
     * letztes Tilgungsperiodenende                                       
     */
    private String ivDlte;
    
    /**
     * Schluessel Ultimo Zinsen
     */
    private String ivDufz;
    
    /**
     * Schluessel Ultimo Tilgung
     */
    private String ivDuft;              
    
    /**
     * Datum Kontoschliessung /  DKZ=8
     */
    private String ivDdks;
    
    /**
     * Datum d. unwiderruflichen Zusage
     */
    private String ivDdzb;
    
    /**
     * Kennzeichen Rahmenkredit
     */                                        
    private String ivDkr;
    
    /**
     * Auszahlungsdatum                                        
     */
    private String ivDausdat;
    
    /**
     * Datum letzte Rate
     */                                        
    private String ivDdlrate;
    
    /**
     * Schluessel Tilgungsbesonderheit
     */                                        
    private String ivDtibes;
    
    /**
     * Personalnummer fuer Angestellte
     */                                     
    private String ivDpn; 
    
    /**
     * Datum Vertrag bis
     */                                         
    private String ivDdvb;   
    
    /**
     * DummyFeld als Fueller 22.03.2005
     */                                       
    private String ivFil1;       
    
    /**
     * Niedrigzinssatz
     */
    private String ivDnzi;
    
    /**
     * Hoechstzinssatz
     */                                         
    private String ivDhzi;
    
    /**
     * ZAS-Ausschlusskennzeichen 
     */
    private String ivDzaskz;
    
    /**
     * Datum Zinsbeginn
     */                                        
    private String ivDzib;
    
    /**
     * Beleihungswert
     */                                       
    private String ivDbw;
    
    /**
     * Zinsaufschlag/-abschlag
     */                                       
    private String ivDzia;
    
    /**
     * Grosse der Zinsperiode
     */                                        
    private String ivDzpg;
    
    /**
     * Schluessel revolvierender  Kreditrahmen
     */                                        
    private String ivDsrv;
    
    /**
     * Ursprungslaufzeit
     */                                       
    private String ivDlu;
    
    /**
     * Wertpapierkennummer
     */                                      
    private String ivDwpk; 
    
    /**
     * Urkundennummer
     */
    private String ivDurk;
    
    /**
     * %-Satz ohne Haftung -- neu 28.04.2005
     */
    private String ivDproh;
    
    /**
     * Kz unvollstaendige Sicherheiten ......
     */                                      
    private String ivDsiu;
    
    /**
     * Kz unvollstaendige Sicherheiten ......
     */                                        
    private String ivDvek;
     
    /**
     * Ausplatzierungsmerkmal
     */
    private String ivAusplatzierungsmerkmal;
    
    /**
     * Konstruktor
     */
    public KTR() 
    {
        this.ivKopf = new DWHKOPF();
        this.ivDml = new String();
        this.ivDzus1 = new String();
        this.ivDzus2 = new String();
        this.ivDv = new String();
        this.ivDwo = new String();
        this.ivDak = new String();
        this.ivDnz = new String();
        this.ivDnt = new String();
        this.ivDnta = new String();
        this.ivDzp = new String();
        this.ivDtp = new String();
        this.ivDta = new String();
        this.ivDbtr = new String();
        this.ivDbba = new String();
        this.ivDog = new String();
        this.ivDd = new String();
        this.ivDde = new String();
        this.ivDkb = new String();
        this.ivDbue = new String();
        this.ivDwb = new String();
        this.ivDlz = new String();
        this.ivDe = new String();
        this.ivDuk = new String();
        this.ivDgk = new String();
        this.ivDkkf = new String();
        this.ivDbszi = new String();
        this.ivDkn = new String();
        this.ivDkv = new String();
        this.ivDti = new String();
        this.ivDlze = new String();;
        this.ivDlte = new String();
        this.ivDufz = new String();
        this.ivDuft = new String();
        this.ivDdks = new String();
        this.ivDdzb = new String();
        this.ivDkr = new String();
        this.ivDausdat = new String();
        this.ivDdlrate = new String();
        this.ivDtibes = new String();
        this.ivDpn = new String();
        this.ivDdvb = new String();
        this.ivFil1 = new String();
        this.ivDnzi = new String();
        this.ivDhzi = new String();
        this.ivDzaskz = new String();
        this.ivDzib = new String();
        this.ivDbw = new String();
        this.ivDzia = new String();
        this.ivDzpg = new String();
        this.ivDsrv = new String();
        this.ivDlu = new String();
        this.ivDwpk = new String();
        this.ivDurk = new String();
        this.ivDproh = new String();
        this.ivDsiu = new String();
        this.ivDvek = new String();
        this.ivAusplatzierungsmerkmal = new String();
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
     * @return the sDml
     */
    public String getsDml() {
        return this.ivDml;
    }

    /**
     * @param pvSDml the sDml to set
     */
    public void setsDml(String pvSDml) {
        this.ivDml = pvSDml;
    }

    /**
     * @return the sDzus1
     */
    public String getsDzus1() {
        return this.ivDzus1;
    }

    /**
     * @param pvSDzus1 the sDzus1 to set
     */
    public void setsDzus1(String pvSDzus1) {
        this.ivDzus1 = pvSDzus1;
    }

    /**
     * @return the sDzus2
     */
    public String getsDzus2() {
        return this.ivDzus2;
    }

    /**
     * @param pvSDzus2 the sDzus2 to set
     */
    public void setsDzus2(String pvSDzus2) {
        this.ivDzus2 = pvSDzus2;
    }

    /**
     * @return the sDv
     */
    public String getsDv() {
        return this.ivDv;
    }

    /**
     * @param pvSDv the sDv to set
     */
    public void setsDv(String pvSDv) {
        this.ivDv = pvSDv;
    }

    /**
     * @return the sDwo
     */
    public String getsDwo() {
        return this.ivDwo;
    }

    /**
     * @param pvSDwo the sDwo to set
     */
    public void setsDwo(String pvSDwo) {
        this.ivDwo = pvSDwo;
    }

    /**
     * @return the sDak
     */
    public String getsDak() {
        return this.ivDak;
    }

    /**
     * @param pvSDak the sDak to set
     */
    public void setsDak(String pvSDak) {
        this.ivDak = pvSDak;
    }

    /**
     * @return the sDnz
     */
    public String getsDnz() {
        return this.ivDnz;
    }

    /**
     * @param pvSDnz the sDnz to set
     */
    public void setsDnz(String pvSDnz) {
        this.ivDnz = pvSDnz;
    }

    /**
     * @return the sDnt
     */
    public String getsDnt() {
        return this.ivDnt;
    }

    /**
     * @param pvSDnt the sDnt to set
     */
    public void setsDnt(String pvSDnt) {
        this.ivDnt = pvSDnt;
    }

    /**
     * @return the sDnta
     */
    public String getsDnta() {
        return this.ivDnta;
    }

    /**
     * @param pvSDnta the sDnta to set
     */
    public void setsDnta(String pvSDnta) {
        this.ivDnta = pvSDnta;
    }

    /**
     * @return the sDzp
     */
    public String getsDzp() {
        return this.ivDzp;
    }

    /**
     * @param pvSDzp the sDzp to set
     */
    public void setsDzp(String pvSDzp) {
        this.ivDzp = pvSDzp;
    }

    /**
     * @return the sDtp
     */
    public String getsDtp() {
        return this.ivDtp;
    }

    /**
     * @param pvSDtp the sDtp to set
     */
    public void setsDtp(String pvSDtp) {
        this.ivDtp = pvSDtp;
    }

    /**
     * @return the sDta
     */
    public String getsDta() {
        return this.ivDta;
    }

    /**
     * @param pvSDta the sDta to set
     */
    public void setsDta(String pvSDta) {
        this.ivDta = pvSDta;
    }

    /**
     * @return the sDbtr
     */
    public String getsDbtr() {
        return this.ivDbtr;
    }

    /**
     * @param pvSDbtr the sDbtr to set
     */
    public void setsDbtr(String pvSDbtr) {
        this.ivDbtr = pvSDbtr;
    }

    /**
     * @return the sDbba
     */
    public String getsDbba() {
        return this.ivDbba;
    }

    /**
     * @param pvSDbba the sDbba to set
     */
    public void setsDbba(String pvSDbba) {
        this.ivDbba = pvSDbba;
    }

    /**
     * @return the sDog
     */
    public String getsDog() {
        return this.ivDog;
    }

    /**
     * @param pvSDog the sDog to set
     */
    public void setsDog(String pvSDog) {
        this.ivDog = pvSDog;
    }

    /**
     * @return the sDd
     */
    public String getsDd() {
        return this.ivDd;
    }

    /**
     * @param pvSDd the sDd to set
     */
    public void setsDd(String pvSDd) {
        this.ivDd = pvSDd;
    }

    /**
     * @return the sDde
     */
    public String getsDde() {
        return this.ivDde;
    }

    /**
     * @param pvSDde the sDde to set
     */
    public void setsDde(String pvSDde) {
        this.ivDde = pvSDde;
    }

    /**
     * @return the sDkb
     */
    public String getsDkb() {
        return this.ivDkb;
    }

    /**
     * @param pvSDkb the sDkb to set
     */
    public void setsDkb(String pvSDkb) {
        this.ivDkb = pvSDkb;
    }

    /**
     * @return the sDbue
     */
    public String getsDbue() {
        return this.ivDbue;
    }

    /**
     * @param pvSDbue the sDbue to set
     */
    public void setsDbue(String pvSDbue) {
        this.ivDbue = pvSDbue;
    }

    /**
     * @return the sDwb
     */
    public String getsDwb() {
        return this.ivDwb;
    }

    /**
     * @param pvSDwb the sDwb to set
     */
    public void setsDwb(String pvSDwb) {
        this.ivDwb = pvSDwb;
    }

    /**
     * @return the sDlz
     */
    public String getsDlz() {
        return this.ivDlz;
    }

    /**
     * @param pvSDlz the sDlz to set
     */
    public void setsDlz(String pvSDlz) {
        this.ivDlz = pvSDlz;
    }

    /**
     * @return the sDe
     */
    public String getsDe() {
        return this.ivDe;
    }

    /**
     * @param pvSDe the sDe to set
     */
    public void setsDe(String pvSDe) {
        this.ivDe = pvSDe;
    }

    /**
     * @return the sDuk
     */
    public String getsDuk() {
        return this.ivDuk;
    }

    /**
     * @param pvSDuk the sDuk to set
     */
    public void setsDuk(String pvSDuk) {
        this.ivDuk = pvSDuk;
    }

    /**
     * @return the sDgk
     */
    public String getsDgk() {
        return this.ivDgk;
    }

    /**
     * @param pvSDgk the sDgk to set
     */
    public void setsDgk(String pvSDgk) {
        this.ivDgk = pvSDgk;
    }

    /**
     * @return the sDkkf
     */
    public String getsDkkf() {
        return this.ivDkkf;
    }

    /**
     * @param pvSDkkf the sDkkf to set
     */
    public void setsDkkf(String pvSDkkf) {
        this.ivDkkf = pvSDkkf;
    }

    /**
     * @return the sDbszi
     */
    public String getsDbszi() {
        return this.ivDbszi;
    }

    /**
     * @param pvSDbszi the sDbszi to set
     */
    public void setsDbszi(String pvSDbszi) {
        this.ivDbszi = pvSDbszi;
    }

    /**
     * @return the sDkn
     */
    public String getsDkn() {
        return this.ivDkn;
    }

    /**
     * @param pvSDkn the sDkn to set
     */
    public void setsDkn(String pvSDkn) {
        this.ivDkn = pvSDkn;
    }

    /**
     * @return the sDkv
     */
    public String getsDkv() {
        return this.ivDkv;
    }

    /**
     * @param pvSDkv the sDkv to set
     */
    public void setsDkv(String pvSDkv) {
        this.ivDkv = pvSDkv;
    }

    /**
     * @return the sDti
     */
    public String getsDti() {
        return this.ivDti;
    }

    /**
     * @param pvSDti the sDti to set
     */
    public void setsDti(String pvSDti) {
        this.ivDti = pvSDti;
    }

    /**
     * @return the sDlze
     */
    public String getsDlze() {
        return this.ivDlze;
    }

    /**
     * @param pvSDlze the sDlze to set
     */
    public void setsDlze(String pvSDlze) {
        this.ivDlze = pvSDlze;
    }

    /**
     * @return the sDlte
     */
    public String getsDlte() {
        return this.ivDlte;
    }

    /**
     * @param pvSDlte the sDlte to set
     */
    public void setsDlte(String pvSDlte) {
        this.ivDlte = pvSDlte;
    }

    /**
     * @return the sDufz
     */
    public String getsDufz() {
        return this.ivDufz;
    }

    /**
     * @param pvSDufz the sDufz to set
     */
    public void setsDufz(String pvSDufz) {
        this.ivDufz = pvSDufz;
    }

    /**
     * @return the sDuft
     */
    public String getsDuft() {
        return this.ivDuft;
    }

    /**
     * @param pvSDuft the sDuft to set
     */
    public void setsDuft(String pvSDuft) {
        this.ivDuft = pvSDuft;
    }

    /**
     * @return the sDdks
     */
    public String getsDdks() {
        return this.ivDdks;
    }

    /**
     * @param pvSDdks the sDdks to set
     */
    public void setsDdks(String pvSDdks) {
        this.ivDdks = pvSDdks;
    }

    /**
     * @return the sDdzb
     */
    public String getsDdzb() {
        return this.ivDdzb;
    }

    /**
     * @param pvSDdzb the sDdzb to set
     */
    public void setsDdzb(String pvSDdzb) {
        this.ivDdzb = pvSDdzb;
    }

    /**
     * @return the sDkr
     */
    public String getsDkr() {
        return this.ivDkr;
    }

    /**
     * @param pvSDkr the sDkr to set
     */
    public void setsDkr(String pvSDkr) {
        this.ivDkr = pvSDkr;
    }

    /**
     * @return the sDausdat
     */
    public String getsDausdat() {
        return this.ivDausdat;
    }

    /**
     * @param pvSDausdat the sDausdat to set
     */
    public void setsDausdat(String pvSDausdat) {
        this.ivDausdat = pvSDausdat;
    }

    /**
     * @return the sDdlrate
     */
    public String getsDdlrate() {
        return this.ivDdlrate;
    }

    /**
     * @param pvSDdlrate the sDdlrate to set
     */
    public void setsDdlrate(String pvSDdlrate) {
        this.ivDdlrate = pvSDdlrate;
    }

    /**
     * @return the sDtibes
     */
    public String getsDtibes() {
        return this.ivDtibes;
    }

    /**
     * @param pvSDtibes the sDtibes to set
     */
    public void setsDtibes(String pvSDtibes) {
        this.ivDtibes = pvSDtibes;
    }

    /**
     * @return the sDpn
     */
    public String getsDpn() {
        return this.ivDpn;
    }

    /**
     * @param pvSDpn the sDpn to set
     */
    public void setsDpn(String pvSDpn) {
        this.ivDpn = pvSDpn;
    }

    /**
     * @return the sDdvb
     */
    public String getsDdvb() {
        return this.ivDdvb;
    }

    /**
     * @param pvSDdvb the sDdvb to set
     */
    public void setsDdvb(String pvSDdvb) {
        this.ivDdvb = pvSDdvb;
    }

    /**
     * @return the sFil1
     */
    public String getsFil1() {
        return this.ivFil1;
    }

    /**
     * @param pvSFil1 the sFil1 to set
     */
    public void setsFil1(String pvSFil1) {
        this.ivFil1 = pvSFil1;
    }

    /**
     * @return the sDnzi
     */
    public String getsDnzi() {
        return this.ivDnzi;
    }

    /**
     * @param pvSDnzi the sDnzi to set
     */
    public void setsDnzi(String pvSDnzi) {
        this.ivDnzi = pvSDnzi;
    }

    /**
     * @return the sDhzi
     */
    public String getsDhzi() {
        return this.ivDhzi;
    }

    /**
     * @param pvSDhzi the sDhzi to set
     */
    public void setsDhzi(String pvSDhzi) {
        this.ivDhzi = pvSDhzi;
    }

    /**
     * @return the sDzaskz
     */
    public String getsDzaskz() {
        return this.ivDzaskz;
    }

    /**
     * @param pvSDzaskz the sDzaskz to set
     */
    public void setsDzaskz(String pvSDzaskz) {
        this.ivDzaskz = pvSDzaskz;
    }

    /**
     * @return the sDzib
     */
    public String getsDzib() {
        return this.ivDzib;
    }

    /**
     * @param pvSDzib the sDzib to set
     */
    public void setsDzib(String pvSDzib) {
        this.ivDzib = pvSDzib;
    }

    /**
     * @return the sDbw
     */
    public String getsDbw() {
        return this.ivDbw;
    }

    /**
     * @param pvSDbw the sDbw to set
     */
    public void setsDbw(String pvSDbw) {
        this.ivDbw = pvSDbw;
    }

    /**
     * @return the sDzia
     */
    public String getsDzia() {
        return this.ivDzia;
    }

    /**
     * @param pvSDzia the sDzia to set
     */
    public void setsDzia(String pvSDzia) {
        this.ivDzia = pvSDzia;
    }

    /**
     * @return the sDzpg
     */
    public String getsDzpg() {
        return this.ivDzpg;
    }

    /**
     * @param pvSDzpg the sDzpg to set
     */
    public void setsDzpg(String pvSDzpg) {
        this.ivDzpg = pvSDzpg;
    }

    /**
     * @return the sDsrv
     */
    public String getsDsrv() {
        return this.ivDsrv;
    }

    /**
     * @param pvSDsrv the sDsrv to set
     */
    public void setsDsrv(String pvSDsrv) {
        this.ivDsrv = pvSDsrv;
    }

    /**
     * @return the sDlu
     */
    public String getsDlu() {
        return this.ivDlu;
    }

    /**
     * @param pvSDlu the sDlu to set
     */
    public void setsDlu(String pvSDlu) {
        this.ivDlu = pvSDlu;
    }

    /**
     * @return the sDwpk
     */
    public String getsDwpk() {
        return this.ivDwpk;
    }

    /**
     * @param pvSDwpk the sDwpk to set
     */
    public void setsDwpk(String pvSDwpk) {
        this.ivDwpk = pvSDwpk;
    }

    /**
     * @return the sDurk
     */
    public String getsDurk() {
        return this.ivDurk;
    }

    /**
     * @param pvSDurk the sDurk to set
     */
    public void setsDurk(String pvSDurk) {
        this.ivDurk = pvSDurk;
    }

    /**
     * @return the sDproh
     */
    public String getsDproh() {
        return this.ivDproh;
    }

    /**
     * @param pvSDproh the sDproh to set
     */
    public void setsDproh(String pvSDproh) {
        this.ivDproh = pvSDproh;
    }

    /**
     * @return the sDsiu
     */
    public String getsDsiu() {
        return this.ivDsiu;
    }

    /**
     * @param pvSDsiu the sDsiu to set
     */
    public void setsDsiu(String pvSDsiu) {
        this.ivDsiu = pvSDsiu;
    }

    /**
     * @return the sDvek
     */
    public String getsDvek() {
        return this.ivDvek;
    }

    /**
     * @param pvSDvek the sDvek to set
     */
    public void setsDvek(String pvSDvek) {
        this.ivDvek = pvSDvek;
    }
    
    /**
     * @return the ausplatzierungsmerkmal
     */
    public String getAusplatzierungsmerkmal() {
        return this.ivAusplatzierungsmerkmal;
    }

    /**
     * @param pvAusplatzierungsmerkmal the ausplatzierungsmerkmal to set
     */
    public void setAusplatzierungsmerkmal(String pvAusplatzierungsmerkmal) {
        this.ivAusplatzierungsmerkmal = pvAusplatzierungsmerkmal;
    }

    /**
     * Setzt einen Wert des KTR-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setKTR(int pvPos, String pvWert)
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
              this.setsDml(pvWert);
               break;
          case 12:
              this.setsDzus1(pvWert);
              break;
          case 13:
              this.setsDzus2(pvWert);
              break;
          case 14:
              this.setsDv(pvWert);
              break;
          case 15:
              this.setsDwo(pvWert);
              break;
          case 16:
              this.setsDak(pvWert);
              break;
          case 17:
              this.setsDnz(pvWert);
              break;
          case 18:
              this.setsDnt(pvWert);
              break;
          case 19:
              this.setsDnta(pvWert);
              break;
          case 20:
              this.setsDzp(pvWert);
              break;
          case 21:
              this.setsDtp(pvWert);
              break;
          case 22:
              this.setsDta(pvWert);
              break;
          case 23:
              this.setsDbtr(pvWert);
              break;
          case 24:
              this.setsDbba(pvWert);
              break;
          case 25:
              this.setsDog(pvWert);
              break;
          case 26:
              this.setsDd(pvWert);
              break;
          case 27:
              this.setsDde(pvWert);
              break;
          case 28:
              this.setsDkb(pvWert);
              break;
          case 29:
              this.setsDbue(pvWert);
              break;
          case 30:
              this.setsDwb(pvWert);
              break;
          case 31:
              this.setsDlz(pvWert);
              break;
          case 32:
              this.setsDe(pvWert);
              break;
          case 33:
              this.setsDuk(pvWert);
              break;
          case 34:
              this.setsDgk(pvWert);
              break;
          case 35:
              this.setsDkkf(pvWert);
              break;
          case 36:
              this.setsDbszi(pvWert);
              break;
          case 37:
              this.setsDkn(pvWert);
              break;
          case 38:
              this.setsDkv(pvWert);
              break;
          case 39:
              this.setsDti(pvWert);
              break;
          case 40:
              this.setsDlze(pvWert);
              break;
          case 41:
              this.setsDlte(pvWert);
              break;
          case 42:
              this.setsDufz(pvWert);
              break;
          case 43:
              this.setsDuft(pvWert);
              break;
          case 44:
              this.setsDdks(pvWert);
              break;
          case 45:
              this.setsDdzb(pvWert);
              break;
          case 46:
              this.setsDkr(pvWert);
              break;
          case 47:
              this.setsDausdat(pvWert);
              break;
          case 48:
              this.setsDdlrate(pvWert);
              break;
          case 49:
              this.setsDtibes(pvWert);
              break;
          case 50:
              this.setsDpn(pvWert);
              break;
          case 51:
              this.setsDdvb(pvWert);
              break;
          case 52:
              this.setsFil1(pvWert);
              break;
          case 53:
              this.setsDnzi(pvWert);
              break;
          case 54:
              this.setsDhzi(pvWert);
              break;
          case 55:
              this.setsDzaskz(pvWert);
              break;
          case 56:
              this.setsDzib(pvWert);
              break;
          case 57:
              this.setsDbw(pvWert);
              break;
          case 58:
              this.setsDzia(pvWert);
              break;
          case 59:
              this.setsDzpg(pvWert);
              break;
          case 60:
              this.setsDsrv(pvWert);
              break;
          case 61:
              this.setsDlu(pvWert);
              break;
          case 62:
              this.setsDwpk(pvWert);
              break;
          case 63:
              this.setsDurk(pvWert);
              break;
          case 64:
              this.setsDproh(pvWert);
              break;
          case 65:
              this.setsDsiu(pvWert);
              break;
          case 66:
              this.setsDvek(pvWert);
              break;
          case 67:
              break;
          case 68:
              break;
          case 69:
              break;
          case 70:
              break;
          case 71:
              this.setAusplatzierungsmerkmal(pvWert);
              break;
          case 72:
              break;
          default:
              System.out.println("KTR: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 

}
