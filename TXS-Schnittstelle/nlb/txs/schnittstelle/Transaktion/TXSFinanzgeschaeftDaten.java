/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQ;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQBlock;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;
import nlb.txs.schnittstelle.Wertpapier.Gattungsdaten.Gattung;

/**
 * @author tepperc
 *
 */
public class TXSFinanzgeschaeftDaten implements TXSTransaktion
{
    /**
     * Ausgezahlter Betrag
     */
    private String ivAbetrag;
    
    /**
     * Abweichende Faelligkeit
     */
    private String ivAbwfaell;
    
    /**
     * Abzahlungsbeginn (verzoegerter)
     */
    private String ivAbzbeg;
    
    /**
     * Aktiv/Passiv
     */
    private String ivAktivpassiv;
    
    /**
     * Auszahlungskurs
     */
    private String ivAkurs;
    
    /**
     * Ausgleichsforderung - noch nicht verwendet
     */
    //private String ivAusglford;
    
    /**
     * Auszahlungsstatus
     */
    private String ivAusstat;
    
    /**
     * Auszahlungsdatum
     */
    private String ivAuszdat;
     
    /**
     * Auszahlungsverpflichtung
     */
    private String ivAverpfl;
    
    /**
     * Aktenzeichen
     */
    private String ivAz;
    
    /**
     * Bankname
     */
    private String ivBank;
    
    /**
     * Kennzeichen Bankgebaeudekredit
     */
    private String ivBankgeb;
    
    /**
     * Kontobetreuer
     */
    private String ivBetreuer;
    
    /**
     * BIC
     */
    private String ivBic;
    
    /**
     * BLZ
     */
    private String ivBlz;
    
    /**
     * Boersennotiert
     */
    private String ivBoersnot;
    
    /**
     * Besondere Schuldform
     */
    private String ivBsform;
    
    /**
     * DSCR - nicht verwendet
     */
    //private String ivDscr;
    
    /**
     * Durchlaufender Kredit
     */
    private String ivDurchl;
    
    /**
     * Effektive Stuecke
     */
    private String ivEffstk;
    
    /**
     * Einzelwertberichtigung
     */
    private String ivEwb;
    
    /**
     * Extern verbrieft
     */
    private String ivExtx;
    
    /**
     * Externer Key fuer externe Verbriefung
     */
    private String ivExtxkey;
    
    /**
     * Finanzierungsart
     */
    private String ivFinart;
       
    /**
     * Foerderkredit
     */
    private String ivFoerdkredit;
    
    /**
     * Freie Spitze bei Cut Off externe Verbriefung
     */
    private String ivFreicutex;
    
    /**
     * Freie Spitze
     */
    private String ivFspitz;
    
    /**
     * Feiertage mischen
     */
    private String ivFtagmisch;
    
    /**
     * Gedeckte Schuldverschreibung - nicht verwendet
     */
    //private String ivGedschv;
    
    /**
     * GS1-Privilegierung
     */
    private String ivGs1priv;
    
    /**
     * Haircut Level
     */
    private String ivHcut;
    
    /**
     * IBAN
     */
    private String ivIban;
    
    /**
     * ICR - nicht verwendet
     */
    //private String ivIcr;
    
    /**
     * ISIN
     */
    private String ivIsin;
    
    /**
     * Kapitalaustasch
     */
    private String ivKaptausch;
    
    /**
     * Kapitalaustausch zur Laufzeit
     */
    private String ivKaptauschlfz;
    
    /**
     * Geschaeftskategorie
     */
    private String ivKat;
    
    /**
     * Kaufdatum
     */
    private String ivKaufdat;
    
    /**
     * Konkursvorrecht gesichert - nicht verwendet
     */
    //private String ivKonvorges;
    
    /**
     * Betrag Konsortialfinanzierung
     */
    private String ivKonsfin;
  
    /**
     * Name des Konsortialfuehrers
     */
    private String ivKonsfuehrer;
    
    /**
     * Kennzeichen Konsortialkredit
     */
    private String ivKonskredit;
    

    /**
     * Konsortial Anteilsart
     */
    private String ivKonsantart;
    
    /**
     * Konsortialquote
     */
    private String ivKonsquo;
    
    /**
     * Kontonummer
     */
    private String ivKonto;
    
    /**
     * Kuendigungsfrist
     */
    private String ivKuendfrist;
    
    /**
     * Kuendigungsrecht
     */
    private String ivKuendr;
    
    /**
     * Liquiditaetskategorie
     */
    private String ivLiqkat;
    
    /**
     * Aktueller LTV
     */
    private String ivLtv;
    
    /**
     * Max LTV
     */
    private String ivMaxltv;
    
    /**
     * Aktueller LTV Moodys - nicht verwendet
     */
    //private String ivMltv;
    
    /**
     * Nennbetrag
     */
    private String ivNbetrag;
    
    /**
     * Pfandbriefrelevanz
     */
    private String ivPfbrfrel;
    
    /**
     * Privatplatzierung
     */
    //private String ivPplatz;
    
    /**
     * Produktart
     */
    private String ivProdart;
    
    /**
     * Rahmenvertrag Abschlussdatum
     */
    private String ivRahmenvdat;
    
    /**
     * Rahmenvertragsnummer
     */
    private String ivRahmenvnr;
    
    /**
     * Rahmenvertrag Counterpart
     */
    private String ivRahmenvpart;
    
    /**
     * Rating Fitch langfristig
     */
    private String ivRatfitch;
    
    /**
     * Rating Fitch kurzfristig
     */
    private String ivRatfitchk;
    
    /**
     * Internes Rating
     */
    private String ivRatint;
    
    /**
     * Rating Moodys langfristig
     */
    private String ivRatmoody;
    
    /**
     * Rating Moodys kurzfristig
     */
    private String ivRatmoodyk;
    
    /**
     * Rating S_P langfristig
     */
    private String ivRatsp;
    
    /**
     * Rating S_P kurzfristig
     */
    private String ivRatspk;
    
    /**
     * Kreditreferat
     */
    private String ivReferat;
    
    /**
     * Darlehen als Refinanzierungsmittel fuer andere Banken
     */
    private String ivRefikred;
    
    /**
     * Related Loans
     */
    private String ivRelloan;
    
    /**
     * Repo-Flag
     */
    private String ivRepo;
    
    /**
     * Risiokogewicht
     */
    private String ivRgew;
    
    /**
     * Risikogruppe
     */
    private String ivRgruppe;
    
    /**
     * Risikoabschlag
     */
    private String ivRisikoab;
    
    /**
     * Restkapital bei Cut Off externe Verbriefung
     */
    private String ivRkapcutex;
    
    /**
     * Restkapital (Ist)
     */
    private String ivRkapi;
    
    /**
     * Restkapital (Soll)
     */
    private String ivRkaps;
    
    /**
     * Servicer
     */
    //private String ivSvcr;
    
    /**
     * Simulationsgeschaeft
     */
    private String ivSimu;
    
    /**
     * Kommentar
     */
    private String ivText;
    
    /**
     * Tilgungsmodell
     */
    private String ivTilgmod;
    
    /**
     * Tageskurs
     */
    private String ivTkurs;
    
    /**
     * Kredittyp
     */
    private String ivTyp;
    
    /**
     * Ursprungslaufzeit in Monaten
     */
    private String ivUrlfzmon;
    
    /**
     * Urspruenglicher LTV bei Auszahlung
     */
    private String ivUrltv;
    
    /**
     * Anfaenglicher Tilgungssatz
     */
    private String ivUrtilgsatz;
     
    /**
     * Vermittelt durch
     */
    private String ivVermd;
    
    /**
     * Kreditversicherung
     */
    private String ivVers;
    
    /**
     * Verwaesserungsabschlag
     */
    private String ivVerwab;
    
    /**
     * Verwahrnummer
     */
    private String ivVerwnr;
    
    /**
     * Vorauszahlungen
     */
    private String ivVorausz;
    
    /**
     * Vertragswaehrung
     */
    private String ivVwhrg;
    
    /**
     * Betragswaehrung
     */
    private String ivWhrg;
     
    /**
     * Weitere strukturierte Elemente - nicht verwendet
     */
    //private String ivWselem;
    
    /**
     * Zusagedatum
     */
    private String ivZusdat;
    
    /**
     * Zusaetzliche Sicherheiten vorhanden - nicht verwendet
     */
    //private String ivZusshvorh;
    
    /**
     * Zweck des Kredites
     */
    private String ivZweck;
    
    // Neu hinzugefuegte Felder - 29.09.2011
    /**
     * Deckungsschlüssel
     */
    private String ivDeckschl;

    // Neu hinzugefuegte Feld - 01.10.2014
    /**
     * Ausplatzierungsmerkmal
     */
    private String ivAuspl;
    
    /**
     * Produktschlüssel
     */
    private String ivProdschl;
    // Neu hinzugefuegte Felder - 29.09.2011
        
    /**
     * Kennzeichen auf Rollover-Darlehen 
     */
    private String ivRoll;
    
    // Hinzugefuegte Felder - 15.11.2012
    /**
     * Konto-Einrichtung
     */
    private String ivKein;
    
    /**
     * 
     */
    private String ivBoe;
    
    /**
     * 
     */
    private String ivVoe;
    
    /**
     * 
     */
    private String ivPoe;
    // Hinzugefuegte Felder - 15.11.2012
    
    // Hinzugefuegte Felder fuer Wertpapiere
    /**
     * 
     */
    private String ivDepotnummer;
    
    /**
     * 
     */
    private String ivMAVPROD;
    
    /**
     * 
     */
    private String ivGd160;
    
    /**
     * 
     */
    private String ivGd260;
    
    /**
     * 
     */
    private String ivGd776;
    
    /**
     * 
     */
    private String ivGd776B;
    
    /**
     * 
     */
    private String ivGibnot;
    
    /**
     * 
     */
    private String ivPfandbriefoepg;
    // Hinzugefuegte Felder fuer Wertpapiere
    
    /**
     * Konstruktor
     */
    public TXSFinanzgeschaeftDaten()
    {
        initTXSFinanzgeschaeftDaten();
    }
    
    /**
     * Initialisierung der Felder mit leeren Strings
     */
    public void initTXSFinanzgeschaeftDaten()
    {
        this.ivAbetrag = new String();
        this.ivAbwfaell = new String();
        this.ivAbzbeg = new String();
        this.ivAktivpassiv = new String();
        this.ivAkurs = new String();
        this.ivAusstat = new String();
        this.ivAuszdat = new String();
        this.ivAverpfl = new String();
        this.ivAz = new String();
        this.ivBank = new String();
        this.ivBankgeb = new String();
        this.ivBetreuer = new String();
        this.ivBic = new String();
        this.ivBlz = new String();
        this.ivBoersnot = new String();
        this.ivBsform = new String();
        this.ivDurchl = new String();
        this.ivEffstk = new String();
        this.ivEwb = new String();
        this.ivExtx = new String();
        this.ivExtxkey = new String();
        this.ivFinart = new String();
        this.ivFoerdkredit = new String();
        this.ivFreicutex = new String();
        this.ivFspitz = new String();
        this.ivFtagmisch = new String();
        this.ivGs1priv = new String();
        this.ivHcut = new String();
        this.ivIban = new String();
        this.ivIsin = new String();
        this.ivKaptausch = new String();
        this.ivKaptauschlfz = new String();
        this.ivKat = new String();
        this.ivKaufdat = new String();
        this.ivKonsfuehrer = new String();
        this.ivKonskredit = new String();
        this.ivKonsfin = new String();
        this.ivKonsantart = new String();
        this.ivKonsquo = new String();
        this.ivKonto = new String();
        this.ivKuendfrist = new String();
        this.ivKuendr = new String();
        this.ivLiqkat = new String();
        this.ivLtv = new String();
        this.ivMaxltv = new String();
        this.ivNbetrag = new String();
        this.ivPfbrfrel = new String();
        this.ivProdart = new String();
        this.ivRahmenvdat = new String();
        this.ivRahmenvnr = new String();
        this.ivRahmenvpart = new String();
        this.ivRatfitch = new String();
        this.ivRatfitchk = new String();
        this.ivRatint = new String();
        this.ivRatmoody = new String();
        this.ivRatmoodyk = new String();
        this.ivRatsp = new String();
        this.ivRatspk = new String();
        this.ivReferat = new String();
        this.ivRefikred = new String();
        this.ivRelloan = new String();
        this.ivRepo = new String();
        this.ivRgew = new String();
        this.ivRgruppe = new String();
        this.ivRisikoab = new String();
        this.ivRkapcutex = new String();
        this.ivRkapi = new String();
        this.ivRkaps = new String();
        this.ivSimu = new String();
        this.ivText = new String();
        this.ivTilgmod = new String();
        this.ivTkurs = new String();
        this.ivTyp = new String();
        this.ivUrlfzmon = new String();
        this.ivUrltv = new String();
        this.ivUrtilgsatz = new String();
        this.ivVermd = new String();
        this.ivVers = new String();
        this.ivVerwab = new String();
        this.ivVerwnr = new String();
        this.ivVorausz = new String();
        this.ivVwhrg = new String();
        this.ivWhrg = new String();
        this.ivZusdat = new String();
        this.ivZweck = new String();
        // Neu hinzugefuegte Felder - 29.09.2011
        this.ivDeckschl = new String();
        this.ivAuspl = new String();
        this.ivProdschl = new String();
        // Neu hinzugefuegte Felder - 29.09.2011
        this.ivRoll = new String();
        // Hinzugefuegte Felder - 15.11.2012
        this.ivKein = new String();
        this.ivBoe = new String();
        this.ivVoe = new String();
        this.ivPoe = new String();
        this.ivDepotnummer = new String();
        this.ivMAVPROD = new String();
        this.ivGd160 = new String();
        this.ivGd260 = new String();
        this.ivGd776 = new String();
        this.ivGd776B = new String();
        this.ivGibnot = new String();
        this.ivPfandbriefoepg = new String();
    }

    /**
     * @return the abetrag
     */
    public String getAbetrag() {
        return this.ivAbetrag;
    }

    /**
     * @param pvAbetrag the abetrag to set
     */
    public void setAbetrag(String pvAbetrag) {
        this.ivAbetrag = pvAbetrag;
    }

    /**
     * @return the abwfaell
     */
    public String getAbwfaell() {
        return this.ivAbwfaell;
    }

    /**
     * @param pvAbwfaell the abwfaell to set
     */
    public void setAbwfaell(String pvAbwfaell) {
        this.ivAbwfaell = pvAbwfaell;
    }

    /**
     * @return the abzbeg
     */
    public String getAbzbeg() {
        return this.ivAbzbeg;
    }

    /**
     * @param pvAbzbeg the abzbeg to set
     */
    public void setAbzbeg(String pvAbzbeg) {
        this.ivAbzbeg = pvAbzbeg;
    }

    /**
     * @return the aktivpassiv
     */
    public String getAktivpassiv() {
        return this.ivAktivpassiv;
    }

    /**
     * @param pvAktivpassiv the aktivpassiv to set
     */
    public void setAktivpassiv(String pvAktivpassiv) {
        this.ivAktivpassiv = pvAktivpassiv;
    }

    /**
     * @return the akurs
     */
    public String getAkurs() {
        return this.ivAkurs;
    }

    /**
     * @param pvAkurs the akurs to set
     */
    public void setAkurs(String pvAkurs) {
        this.ivAkurs = pvAkurs;
    }

    /**
     * @return the ausstat
     */
    public String getAusstat() {
        return this.ivAusstat;
    }

    /**
     * @param pvAusstat the ausstat to set
     */
    public void setAusstat(String pvAusstat) {
        this.ivAusstat = pvAusstat;
    }

    /**
     * @return the auszdat
     */
    public String getAuszdat() {
        return this.ivAuszdat;
    }

    /**
     * @param pvAuszdat the auszdat to set
     */
    public void setAuszdat(String pvAuszdat) {
        this.ivAuszdat = pvAuszdat;
    }

    /**
     * @return the averpfl
     */
    public String getAverpfl() {
        return this.ivAverpfl;
    }

    /**
     * @param pvAverpfl the averpfl to set
     */
    public void setAverpfl(String pvAverpfl) {
        this.ivAverpfl = pvAverpfl;
    }

    /**
     * @return the az
     */
    public String getAz() {
        return this.ivAz;
    }

    /**
     * @param pvAz the az to set
     */
    public void setAz(String pvAz) {
        this.ivAz = pvAz;
    }

    /**
     * @return the bank
     */
    public String getBank() {
        return this.ivBank;
    }

    /**
     * @param pvBank the bank to set
     */
    public void setBank(String pvBank) {
        this.ivBank = pvBank;
    }

    /**
     * @return the bankgeb
     */
    public String getBankgeb() {
        return this.ivBankgeb;
    }

    /**
     * @param pvBankgeb the bankgeb to set
     */
    public void setBankgeb(String pvBankgeb) {
        this.ivBankgeb = pvBankgeb;
    }

    /**
     * @return the betreuer
     */
    public String getBetreuer() {
        return this.ivBetreuer;
    }

    /**
     * @param pvBetreuer the betreuer to set
     */
    public void setBetreuer(String pvBetreuer) {
        this.ivBetreuer = pvBetreuer;
    }

    /**
     * @return the bic
     */
    public String getBic() {
        return this.ivBic;
    }

    /**
     * @param pvBic the bic to set
     */
    public void setBic(String pvBic) {
        this.ivBic = pvBic;
    }

    /**
     * @return the blz
     */
    public String getBlz() {
        return this.ivBlz;
    }

    /**
     * @param pvBlz the blz to set
     */
    public void setBlz(String pvBlz) {
        this.ivBlz = pvBlz;
    }

    /**
     * @return the boersnot
     */
    public String getBoersnot() {
        return this.ivBoersnot;
    }

    /**
     * @param pvBoersnot the boersnot to set
     */
    public void setBoersnot(String pvBoersnot) {
        this.ivBoersnot = pvBoersnot;
    }

    /**
     * @return the bsform
     */
    public String getBsform() {
        return this.ivBsform;
    }

    /**
     * @param pvBsform the bsform to set
     */
    public void setBsform(String pvBsform) {
        this.ivBsform = pvBsform;
    }

    /**
     * @return the durchl
     */
    public String getDurchl() {
        return this.ivDurchl;
    }

    /**
     * @param pvDurchl the durchl to set
     */
    public void setDurchl(String pvDurchl) {
        this.ivDurchl = pvDurchl;
    }

    /**
     * @return the effstk
     */
    public String getEffstk() {
        return this.ivEffstk;
    }

    /**
     * @param pvEffstk the effstk to set
     */
    public void setEffstk(String pvEffstk) {
        this.ivEffstk = pvEffstk;
    }

    /**
     * @return the ewb
     */
    public String getEwb() {
        return this.ivEwb;
    }

    /**
     * @param pvEwb the ewb to set
     */
    public void setEwb(String pvEwb) {
        this.ivEwb = pvEwb;
    }

    /**
     * @return the extx
     */
    public String getExtx() {
        return this.ivExtx;
    }

    /**
     * @param pvExtx the extx to set
     */
    public void setExtx(String pvExtx) {
        this.ivExtx = pvExtx;
    }

    /**
     * @return the extxkey
     */
    public String getExtxkey() {
        return this.ivExtxkey;
    }

    /**
     * @param pvExtxkey the extxkey to set
     */
    public void setExtxkey(String pvExtxkey) {
        this.ivExtxkey = pvExtxkey;
    }

    /**
     * @return the finart
     */
    public String getFinart() {
        return this.ivFinart;
    }

    /**
     * @param pvFinart the finart to set
     */
    public void setFinart(String pvFinart) {
        this.ivFinart = pvFinart;
    }

    /**
     * @return the foerdkredit
     */
    public String getFoerdkredit() {
        return this.ivFoerdkredit;
    }

    /**
     * @param pvFoerdkredit the foerdkredit to set
     */
    public void setFoerdkredit(String pvFoerdkredit) {
        this.ivFoerdkredit = pvFoerdkredit;
    }

    /**
     * @return the freicutex
     */
    public String getFreicutex() {
        return this.ivFreicutex;
    }

    /**
     * @param pvFreicutex the freicutex to set
     */
    public void setFreicutex(String pvFreicutex) {
        this.ivFreicutex = pvFreicutex;
    }

    /**
     * @return the fspitz
     */
    public String getFspitz() {
        return this.ivFspitz;
    }

    /**
     * @param pvFspitz the fspitz to set
     */
    public void setFspitz(String pvFspitz) {
        this.ivFspitz = pvFspitz;
    }

    /**
     * @return the ftagmisch
     */
    public String getFtagmisch() {
        return this.ivFtagmisch;
    }

    /**
     * @param pvFtagmisch the ftagmisch to set
     */
    public void setFtagmisch(String pvFtagmisch) {
        this.ivFtagmisch = pvFtagmisch;
    }

    /**
     * @return the gs1priv
     */
    public String getGs1priv() {
        return this.ivGs1priv;
    }

    /**
     * @param pvGs1priv the gs1priv to set
     */
    public void setGs1priv(String pvGs1priv) {
        this.ivGs1priv = pvGs1priv;
    }

    /**
     * @return the hcut
     */
    public String getHcut() {
        return this.ivHcut;
    }

    /**
     * @param pvHcut the hcut to set
     */
    public void setHcut(String pvHcut) {
        this.ivHcut = pvHcut;
    }

    /**
     * @return the iban
     */
    public String getIban() {
        return this.ivIban;
    }

    /**
     * @param pvIban the iban to set
     */
    public void setIban(String pvIban) {
        this.ivIban = pvIban;
    }

    /**
     * @return the isin
     */
    public String getIsin() {
        return this.ivIsin;
    }

    /**
     * @param pvIsin the isin to set
     */
    public void setIsin(String pvIsin) {
        this.ivIsin = pvIsin;
    }

    /**
     * @return the kaptausch
     */
    public String getKaptausch() {
        return this.ivKaptausch;
    }

    /**
     * @param pvKaptausch the kaptausch to set
     */
    public void setKaptausch(String pvKaptausch) {
        this.ivKaptausch = pvKaptausch;
    }

    /**
     * @return the kaptauschlfz
     */
    public String getKaptauschlfz() {
        return this.ivKaptauschlfz;
    }

    /**
     * @param pvKaptauschlfz the kaptauschlfz to set
     */
    public void setKaptauschlfz(String pvKaptauschlfz) {
        this.ivKaptauschlfz = pvKaptauschlfz;
    }

    /**
     * @return the kat
     */
    public String getKat() {
        return this.ivKat;
    }

    /**
     * @param pvKat the kat to set
     */
    public void setKat(String pvKat) {
        this.ivKat = pvKat;
    }

    /**
     * @return the kaufdat
     */
    public String getKaufdat() {
        return this.ivKaufdat;
    }

    /**
     * @param pvKaufdat the kaufdat to set
     */
    public void setKaufdat(String pvKaufdat) {
        this.ivKaufdat = pvKaufdat;
    }

    /**
     * @return the konsfuehrer
     */
    public String getKonsfuehrer() {
        return this.ivKonsfuehrer;
    }

    /**
     * @param pvKonsfuehrer the konsfuehrer to set
     */
    public void setKonsfuehrer(String pvKonsfuehrer) {
        this.ivKonsfuehrer = pvKonsfuehrer;
    }

    /**
     * @return the konskredit
     */
    public String getKonskredit() {
        return this.ivKonskredit;
    }

    /**
     * @param pvKonskredit the konskredit to set
     */
    public void setKonskredit(String pvKonskredit) {
        this.ivKonskredit = pvKonskredit;
    }
    
    /**
     * @return 
     * 
     */
    public String getKonsfin()
    {
        return this.ivKonsfin;
    }

    /**
     * @param pvKonsfin 
     */
    public void setKonsfin(String pvKonsfin)
    {
       this.ivKonsfin = pvKonsfin; 
    }
    
    /**
     * @return the konsantart
     */
    public String getKonsantart() {
        return this.ivKonsantart;
    }

    /**
     * @param pvKonsantart the konsantart to set
     */
    public void setKonsantart(String pvKonsantart) {
        this.ivKonsantart = pvKonsantart;
    }

    /**
     * @return the konsquo
     */
    public String getKonsquo() {
        return this.ivKonsquo;
    }

    /**
     * @param pvKonsquo the konsquo to set
     */
    public void setKonsquo(String pvKonsquo) {
        this.ivKonsquo = pvKonsquo;
    }

    /**
     * @return the konto
     */
    public String getKonto() {
        return this.ivKonto;
    }

    /**
     * @param pvKonto the konto to set
     */
    public void setKonto(String pvKonto) {
        this.ivKonto = pvKonto;
    }

    /**
     * @return the kuendfrist
     */
    public String getKuendfrist() {
        return this.ivKuendfrist;
    }

    /**
     * @param pvKuendfrist the kuendfrist to set
     */
    public void setKuendfrist(String pvKuendfrist) {
        this.ivKuendfrist = pvKuendfrist;
    }

    /**
     * @return the kuendr
     */
    public String getKuendr() {
        return this.ivKuendr;
    }

    /**
     * @param pvKuendr the kuendr to set
     */
    public void setKuendr(String pvKuendr) {
        this.ivKuendr = pvKuendr;
    }

    /**
     * @return the liqkat
     */
    public String getLiqkat() {
        return this.ivLiqkat;
    }

    /**
     * @param pvLiqkat the liqkat to set
     */
    public void setLiqkat(String pvLiqkat) {
        this.ivLiqkat = pvLiqkat;
    }

    /**
     * @return the ltv
     */
    public String getLtv() {
        return this.ivLtv;
    }

    /**
     * @param pvLtv the ltv to set
     */
    public void setLtv(String pvLtv) {
        this.ivLtv = pvLtv;
    }

    /**
     * @return the maxltv
     */
    public String getMaxltv() {
        return this.ivMaxltv;
    }

    /**
     * @param pvMaxltv the maxltv to set
     */
    public void setMaxltv(String pvMaxltv) {
        this.ivMaxltv = pvMaxltv;
    }

    /**
     * @return the nbetrag
     */
    public String getNbetrag() {
        return this.ivNbetrag;
    }

    /**
     * @param pvNbetrag the nbetrag to set
     */
    public void setNbetrag(String pvNbetrag) {
        this.ivNbetrag = pvNbetrag;
    }

    /**
     * @return the pfbrfrel
     */
    public String getPfbrfrel() {
        return this.ivPfbrfrel;
    }

    /**
     * @param pvPfbrfrel the pfbrfrel to set
     */
    public void setPfbrfrel(String pvPfbrfrel) {
        this.ivPfbrfrel = pvPfbrfrel;
    }

    /**
     * @return the prodart
     */
    public String getProdart() {
        return this.ivProdart;
    }

    /**
     * @param pvProdart the prodart to set
     */
    public void setProdart(String pvProdart) {
        this.ivProdart = pvProdart;
    }

    /**
     * @return the rahmenvdat
     */
    public String getRahmenvdat() {
        return this.ivRahmenvdat;
    }

    /**
     * @param pvRahmenvdat the rahmenvdat to set
     */
    public void setRahmenvdat(String pvRahmenvdat) {
        this.ivRahmenvdat = pvRahmenvdat;
    }

    /**
     * @return the rahmenvnr
     */
    public String getRahmenvnr() {
        return this.ivRahmenvnr;
    }

    /**
     * @param pvRahmenvnr the rahmenvnr to set
     */
    public void setRahmenvnr(String pvRahmenvnr) {
        this.ivRahmenvnr = pvRahmenvnr;
    }

    /**
     * @return the rahmenvpart
     */
    public String getRahmenvpart() {
        return this.ivRahmenvpart;
    }

    /**
     * @param pvRahmenvpart the rahmenvpart to set
     */
    public void setRahmenvpart(String pvRahmenvpart) {
        this.ivRahmenvpart = pvRahmenvpart;
    }

    /**
     * @return the ratfitch
     */
    public String getRatfitch() {
        return this.ivRatfitch;
    }

    /**
     * @param pvRatfitch the ratfitch to set
     */
    public void setRatfitch(String pvRatfitch) {
        this.ivRatfitch = pvRatfitch;
    }

    /**
     * @return the ratfitchk
     */
    public String getRatfitchk() {
        return this.ivRatfitchk;
    }

    /**
     * @param pvRatfitchk the ratfitchk to set
     */
    public void setRatfitchk(String pvRatfitchk) {
        this.ivRatfitchk = pvRatfitchk;
    }

    /**
     * @return the ratint
     */
    public String getRatint() {
        return this.ivRatint;
    }

    /**
     * @param pvRatint the ratint to set
     */
    public void setRatint(String pvRatint) {
        this.ivRatint = pvRatint;
    }

    /**
     * @return the ratmoody
     */
    public String getRatmoody() {
        return this.ivRatmoody;
    }

    /**
     * @param pvRatmoody the ratmoody to set
     */
    public void setRatmoody(String pvRatmoody) {
        this.ivRatmoody = pvRatmoody;
    }

    /**
     * @return the ratmoodyk
     */
    public String getRatmoodyk() {
        return this.ivRatmoodyk;
    }

    /**
     * @param pvRatmoodyk the ratmoodyk to set
     */
    public void setRatmoodyk(String pvRatmoodyk) {
        this.ivRatmoodyk = pvRatmoodyk;
    }

    /**
     * @return the ratsp
     */
    public String getRatsp() {
        return this.ivRatsp;
    }

    /**
     * @param pvRatsp the ratsp to set
     */
    public void setRatsp(String pvRatsp) {
        this.ivRatsp = pvRatsp;
    }

    /**
     * @return the ratspk
     */
    public String getRatspk() {
        return this.ivRatspk;
    }

    /**
     * @param pvRatspk the ratspk to set
     */
    public void setRatspk(String pvRatspk) {
        this.ivRatspk = pvRatspk;
    }

    /**
     * @return the referat
     */
    public String getReferat() {
        return this.ivReferat;
    }

    /**
     * @param pvReferat the referat to set
     */
    public void setReferat(String pvReferat) {
        this.ivReferat = pvReferat;
    }

    /**
     * @return the refikred
     */
    public String getRefikred() {
        return this.ivRefikred;
    }

    /**
     * @param pvRefikred the refikred to set
     */
    public void setRefikred(String pvRefikred) {
        this.ivRefikred = pvRefikred;
    }

    /**
     * @return the relloan
     */
    public String getRelloan() {
        return this.ivRelloan;
    }

    /**
     * @param pvRelloan the relloan to set
     */
    public void setRelloan(String pvRelloan) {
        this.ivRelloan = pvRelloan;
    }

    /**
     * @return the repo
     */
    public String getRepo() {
        return this.ivRepo;
    }

    /**
     * @param pvRepo the repo to set
     */
    public void setRepo(String pvRepo) {
        this.ivRepo = pvRepo;
    }

    /**
     * @return the rgew
     */
    public String getRgew() {
        return this.ivRgew;
    }

    /**
     * @param pvRgew the rgew to set
     */
    public void setRgew(String pvRgew) {
        this.ivRgew = pvRgew;
    }

    /**
     * @return the rgruppe
     */
    public String getRgruppe() {
        return this.ivRgruppe;
    }

    /**
     * @param pvRgruppe the rgruppe to set
     */
    public void setRgruppe(String pvRgruppe) {
        this.ivRgruppe = pvRgruppe;
    }

    /**
     * @return the risikoab
     */
    public String getRisikoab() {
        return this.ivRisikoab;
    }

    /**
     * @param pvRisikoab the risikoab to set
     */
    public void setRisikoab(String pvRisikoab) {
        this.ivRisikoab = pvRisikoab;
    }

    /**
     * @return the rkapcutex
     */
    public String getRkapcutex() {
        return this.ivRkapcutex;
    }

    /**
     * @param pvRkapcutex the rkapcutex to set
     */
    public void setRkapcutex(String pvRkapcutex) {
        this.ivRkapcutex = pvRkapcutex;
    }

    /**
     * @return the rkapi
     */
    public String getRkapi() {
        return this.ivRkapi;
    }

    /**
     * @param pvRkapi the rkapi to set
     */
    public void setRkapi(String pvRkapi) {
        this.ivRkapi = pvRkapi;
    }

    /**
     * @return the rkaps
     */
    public String getRkaps() {
        return this.ivRkaps;
    }

    /**
     * @param pvRkaps the rkaps to set
     */
    public void setRkaps(String pvRkaps) {
        this.ivRkaps = pvRkaps;
    }

    /**
     * @return the simu
     */
    public String getSimu() {
        return this.ivSimu;
    }

    /**
     * @param pvSimu the simu to set
     */
    public void setSimu(String pvSimu) {
        this.ivSimu = pvSimu;
    }

    /**
     * @return the text
     */
    public String getText() {
        return this.ivText;
    }

    /**
     * @param pvText the text to set
     */
    public void setText(String pvText) {
        this.ivText = pvText;
    }

    /**
     * @return the tilgmod
     */
    public String getTilgmod() {
        return this.ivTilgmod;
    }

    /**
     * @param pvTilgmod the tilgmod to set
     */
    public void setTilgmod(String pvTilgmod) {
        this.ivTilgmod = pvTilgmod;
    }

    /**
     * @return the tkurs
     */
    public String getTkurs() {
        return this.ivTkurs;
    }

    /**
     * @param pvTkurs the tkurs to set
     */
    public void setTkurs(String pvTkurs) {
        this.ivTkurs = pvTkurs;
    }

    /**
     * @return the typ
     */
    public String getTyp() {
        return this.ivTyp;
    }

    /**
     * @param pvTyp the typ to set
     */
    public void setTyp(String pvTyp) {
        this.ivTyp = pvTyp;
    }

    /**
     * @return the urlfzmon
     */
    public String getUrlfzmon() {
        return this.ivUrlfzmon;
    }

    /**
     * @param pvUrlfzmon the urlfzmon to set
     */
    public void setUrlfzmon(String pvUrlfzmon) {
        this.ivUrlfzmon = pvUrlfzmon;
    }

    /**
     * @return the urltv
     */
    public String getUrltv() {
        return this.ivUrltv;
    }

    /**
     * @param pvUrltv the urltv to set
     */
    public void setUrltv(String pvUrltv) {
        this.ivUrltv = pvUrltv;
    }

    /**
     * @return the urtilgsatz
     */
    public String getUrtilgsatz() {
        return this.ivUrtilgsatz;
    }

    /**
     * @param pvUrtilgsatz the urtilgsatz to set
     */
    public void setUrtilgsatz(String pvUrtilgsatz) {
        this.ivUrtilgsatz = pvUrtilgsatz;
    }

    /**
     * @return the vermd
     */
    public String getVermd() {
        return this.ivVermd;
    }

    /**
     * @param pvVermd the vermd to set
     */
    public void setVermd(String pvVermd) {
        this.ivVermd = pvVermd;
    }

    /**
     * @return the vers
     */
    public String getVers() {
        return this.ivVers;
    }

    /**
     * @param pvVers the vers to set
     */
    public void setVers(String pvVers) {
        this.ivVers = pvVers;
    }

    /**
     * @return the verwab
     */
    public String getVerwab() {
        return this.ivVerwab;
    }

    /**
     * @param pvVerwab the verwab to set
     */
    public void setVerwab(String pvVerwab) {
        this.ivVerwab = pvVerwab;
    }

    /**
     * @return the verwnr
     */
    public String getVerwnr() {
        return this.ivVerwnr;
    }

    /**
     * @param pvVerwnr the verwnr to set
     */
    public void setVerwnr(String pvVerwnr) {
        this.ivVerwnr = pvVerwnr;
    }

    /**
     * @return the vorausz
     */
    public String getVorausz() {
        return this.ivVorausz;
    }

    /**
     * @param pvVorausz the vorausz to set
     */
    public void setVorausz(String pvVorausz) {
        this.ivVorausz = pvVorausz;
    }

    /**
     * @return the vwhrg
     */
    public String getVwhrg() {
        return this.ivVwhrg;
    }

    /**
     * @param pvVwhrg the vwhrg to set
     */
    public void setVwhrg(String pvVwhrg) {
        this.ivVwhrg = pvVwhrg;
    }

    /**
     * @return the whrg
     */
    public String getWhrg() {
        return this.ivWhrg;
    }

    /**
     * @param pvWhrg the whrg to set
     */
    public void setWhrg(String pvWhrg) {
        this.ivWhrg = pvWhrg;
    }

    /**
     * @return the zusdat
     */
    public String getZusdat() {
        return this.ivZusdat;
    }

    /**
     * @param pvZusdat the zusdat to set
     */
    public void setZusdat(String pvZusdat) {
        this.ivZusdat = pvZusdat;
    }

    /**
     * @return the zweck
     */
    public String getZweck() {
        return this.ivZweck;
    }

    /**
     * @param pvZweck the zweck to set
     */
    public void setZweck(String pvZweck) {
        this.ivZweck = pvZweck;
    }

    /**
     * @return the deckschl
     */
    public String getDeckschl() {
        return this.ivDeckschl;
    }

    /**
     * @param pvDeckschl the deckschl to set
     */
    public void setDeckschl(String pvDeckschl) {
        this.ivDeckschl = pvDeckschl;
    }

    /**
     * @return the auspl
     */
    public String getAuspl() {
        return this.ivAuspl;
    }

    /**
     * @param pvAuspl the auspl to set
     */
    public void setAuspl(String pvAuspl) {
        this.ivAuspl = pvAuspl;
    }

    /**
     * @return the prodschl
     */
    public String getProdschl() {
        return this.ivProdschl;
    }

    /**
     * @param pvProdschl the prodschl to set
     */
    public void setProdschl(String pvProdschl) {
        this.ivProdschl = pvProdschl;
    }

    /**
     * @return the roll
     */
    public String getRoll() {
        return this.ivRoll;
    }

    /**
     * @param pvRoll the roll to set
     */
    public void setRoll(String pvRoll) {
        this.ivRoll = pvRoll;
    }

    /**
     * @return the kein
     */
    public String getKein() 
    {
        return this.ivKein;
    }

    /**
     * @param pvKein 
     */
    public void setKein(String pvKein) 
    {
        this.ivKein = pvKein;
    }

    /**
     * @return the boe
     */
    public String getBoe() 
    {
        return this.ivBoe;
    }

    /**
     * @param pvBoe 
     * @param pvBewilligendeOE the bewilligendeOE to set
     */
    public void setBoe(String pvBoe) 
    {
        this.ivBoe = pvBoe;
    }

    /**
     * @return the voe
     */
    public String getVoe() 
    {
        return this.ivVoe;
    }

    /**
     * @param pvVoe 
     */
    public void setVoe(String pvVoe) {
        this.ivVoe = pvVoe;
    }

    /**
     * @return the poe
     */
    public String getPoe() {
        return this.ivPoe;
    }

    /**
     * @param pvPoe 
     */
    public void setPoe(String pvPoe) 
    {
        this.ivPoe = pvPoe;
    }

    /**
     * Liefert den Start der TXSFinanzgeschaeftDaten als String
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:fgdaten ");
     }
        
    /**
     * TXSFinanzgeschaeftDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        if (this.ivAbetrag.length() > 0)
        	lvHelpXML.append("abetrag=\"" + this.ivAbetrag + "\" ");
        
        if (this.ivAktivpassiv.length() > 0)       
        	lvHelpXML.append("aktivpassiv=\"" + this.ivAktivpassiv + "\" "); 
        
        if (this.ivAuszdat.length() > 0)
        	lvHelpXML.append("auszdat=\"" + this.ivAuszdat + "\" ");
          
        if (this.ivAusstat.length() > 0)
        	lvHelpXML.append("ausstat=\"" + this.ivAusstat + "\" ");
   
        if (this.ivAverpfl.length() > 0)
        	lvHelpXML.append("averpfl=\"" + this.ivAverpfl + "\" ");

        if (this.ivAz.length() > 0)
        	lvHelpXML.append("az=\"" + this.ivAz + "\" ");
        
        if (this.ivDepotnummer.length() > 0)
        	lvHelpXML.append("depotnummer=\"" + this.ivDepotnummer + "\" ");        	
        
        if (this.ivIsin.length() > 0)
        {
        	lvHelpXML.append("isin=\"" + this.ivIsin + "\" ");
        }
        
        if (this.ivKat.length() > 0)
          lvHelpXML.append("kat=\"" + this.ivKat + "\" ");
        
        if (this.ivKonsfin.length() > 0)
        {
           lvHelpXML.append("konsfin=\"" + this.ivKonsfin + "\" ");
        }
        
        if (this.ivKonskredit.length() > 0)
        {
            lvHelpXML.append("konskredit=\"" + this.ivKonskredit + "\" ");
        }

        if (this.ivKonsfuehrer.length() > 0)
        {
            lvHelpXML.append("konsfuehrer=\"" + this.ivKonsfuehrer + "\" ");
        }
        
        if (this.ivKonsantart.length() > 0)
        {
            lvHelpXML.append("konsantart=\"" + this.ivKonsantart + "\" ");
        }
        
        if (this.ivKonsquo.length() > 0)
        {
            lvHelpXML.append("konsquo=\"" + this.ivKonsquo + "\" ");
        }
        
        if (this.ivNbetrag.length() > 0)
        {
          lvHelpXML.append("nbetrag=\"" + this.ivNbetrag + "\" ");
        }
        
        if (this.ivPfbrfrel.length() > 0)
        {
          lvHelpXML.append("pfbrfrel=\"" + this.ivPfbrfrel + "\" ");
        }
        
        if (this.ivRkapi.length() > 0)
        {
          lvHelpXML.append("rkapi=\"" + this.ivRkapi + "\" ");
        }

        if (this.ivRepo.length() > 0)
        {
            lvHelpXML.append("repo=\"" + this.ivRepo + "\" ");
        }
        
        if (this.ivTyp.length() > 0)
        {
          lvHelpXML.append("typ=\"" + this.ivTyp + "\" ");
        }
        
        if (this.ivTkurs.length() > 0)
            lvHelpXML.append("tkurs=\"" + this.ivTkurs + "\" ");
        
        if (this.ivVwhrg.length() > 0)
          lvHelpXML.append("vwhrg=\"" + this.ivVwhrg + "\" ");
        
        if (this.ivWhrg.length() > 0)
          lvHelpXML.append("whrg=\"" + this.ivWhrg + "\" ");
        
        if (this.ivZusdat.length() > 0)
            lvHelpXML.append("zusdat=\"" + this.ivZusdat + "\" ");

        // Neu hinzugefuegte Felder
        if (this.ivDeckschl.length() > 0)
            lvHelpXML.append("deckschl=\"" + this.ivDeckschl + "\" ");

        if (this.ivAuspl.length() > 0)
            lvHelpXML.append("auspl=\"" + this.ivAuspl + "\" ");
        
        if (this.ivProdschl.length() > 0)
            lvHelpXML.append("prodschl=\"" + this.ivProdschl + "\" ");

        if (this.ivRoll.length() > 0)
            lvHelpXML.append("roll=\"" + this.ivRoll + "\" ");
        
        if (this.ivKein.length() > 0)
            lvHelpXML.append("kein=\"" + this.ivKein + "\" ");
        
        if (this.ivBoe.length() > 0)
            lvHelpXML.append("boe=\"" + this.ivBoe + "\" ");
        
        if (this.ivVoe.length() > 0)
            lvHelpXML.append("voe=\"" + this.ivVoe + "\" ");
        
        if (this.ivPoe.length() > 0)
            lvHelpXML.append("poe=\"" + this.ivPoe + "\" ");
        
        if (this.ivText.length() > 0)
        {
            lvHelpXML.append("text=\"" + this.ivText + "\" ");
        }

        if (this.ivFinart.length() > 0)
        {
            lvHelpXML.append("finart=\"" + this.ivFinart + "\" ");            
        }
       
        if (this.ivUrtilgsatz.length() > 0)
            lvHelpXML.append("urtilgsatz=\"" + this.ivUrtilgsatz + "\" ");

        if (this.ivKuendr.length() > 0)
            lvHelpXML.append("kuendr=\"" + this.ivKuendr + "\" ");
        
        if (this.ivMAVPROD.length() > 0)
            lvHelpXML.append("mavprod=\"" + this.ivMAVPROD + "\" ");

        if (this.ivGd160.length() > 0)
            lvHelpXML.append("gd160=\"" + this.ivGd160 + "\" ");

        if (this.ivGd260.length() > 0)
            lvHelpXML.append("gd260=\"" + this.ivGd260 + "\" ");

        if (this.ivGd776.length() > 0)
            lvHelpXML.append("gd776=\"" + this.ivGd776 + "\" ");

        if (this.ivGd776B.length() > 0)
            lvHelpXML.append("gd776b=\"" + this.ivGd776B + "\" ");
        
        if (this.ivGibnot.length() > 0)
            lvHelpXML.append("gibnot=\"" + this.ivGibnot + "\" ");

        if (this.ivPfandbriefoepg.length() > 0)
            lvHelpXML.append("pfandbriefoepg=\"" + this.ivPfandbriefoepg + "\" ");
        
        lvHelpXML.append(">");

        return lvHelpXML;
     }
    
    /**
     * TXSFinanzgeschaeftDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("</txsi:fgdaten>\n");
    }
    
    /**
     * Importiert die Darlehensinformationen aus DarKa
     * Aufruf aus den Klassen 'RefiRegisterAktiv' und 'RefiRegisterPassiv'
     * @param pvModus 
     * @param pvDarlehen 
     * @return 
     */
    public boolean importDarlehen(Darlehen pvDarlehen)
    {     
        /* Definition der Ursprungskapital .......................... */
        BigDecimal lvUrsprungskapital = new BigDecimal(pvDarlehen.getUrsprungsKapital());
        /* Definition der Auszahlungsverpflichtungen ................ */
        BigDecimal lvAuszahlungsVerpf = StringKonverter.convertString2BigDecimal(pvDarlehen.getOffZusage());
        /* Definition der Restkapital ............................... */
        BigDecimal lvRestkapital = new BigDecimal("0.0");
        /* Nach Definition ohne Summe rückständiger Leistung */
        lvRestkapital  = ((StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital())).add(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeNoNiFaeTilgung()))).add(StringKonverter.convertString2BigDecimal(pvDarlehen.getSondertilgung()));

        //System.out.println("dRestkapital: " + dRestkapital.toString());
        // Rechenkonstante
        BigDecimal lvBtrDivHd = new BigDecimal("0.01");
        //System.out.println("dBtrDivHd: " + dBtrDivHd.toString());
        
        /* Wird dann immer als Multiplikator genutzt */
        BigDecimal lvWork = new BigDecimal("0.0");
        BigDecimal lvBuerge_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenUKAP_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenRKAP_Fakt = new BigDecimal("1.0");
            
        if (pvDarlehen.getKredittyp().equals("4"))
        { /* mit Bürge .. anteilig */
         if (StringKonverter.convertString2Double(pvDarlehen.getBuergschaftProzent()) != 0.0)
         { /* nichts da */
          lvBuerge_Fakt = lvBtrDivHd.multiply(StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftProzent()));
         } /* etwas da */
        } /* mit Bürge .. anteilig */

        // BLB - Buergschaftsprozentsatz nicht verwenden, wenn der Deckungsschlüssel 'S' oder 'V' und das Produkt '0802' ist
        if (pvDarlehen.getInstitutsnummer().equals("004"))
        {
            if ((pvDarlehen.getDeckungsschluessel().equals("S") || pvDarlehen.getDeckungsschluessel().equals("V")) && pvDarlehen.getProduktSchluessel().equals("00802"))
            {
                // Buergen-Faktor wieder auf '1' setzen
                lvBuerge_Fakt = new BigDecimal("1.0");
            }
        }
        
        // NLB - Buergschaftsprozentsatz nicht verwenden, wenn der Deckungsschlüssel 'S' oder 'V' ist
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
            if (pvDarlehen.getDeckungsschluessel().equals("S") || pvDarlehen.getDeckungsschluessel().equals("V"))
            {
                // Buergen-Faktor wieder auf '1' setzen
                lvBuerge_Fakt = new BigDecimal("1.0");
            }
        }        

        //System.out.println("dBuerge_Fakt: " + dBuerge_Fakt.toString());
        
        // CT 25.02.2013
        //wenn (Schluessel Kompensation > 0 und < 16), dann "Konsortialkredit" = ja, sonst "Konsortialkredit" = nein
        //wenn (Schluessel Kompensation = 01/10/11), dann "Konsortialfuehrer" = NORD/LB, sonst
        //  wenn (Schluessel Kompensation = 02/04/12/13/14/15), dann "Konsortialfuehrer" = Fremde Fuehrung, sonst "Konsortialfuehrer" bleibt leer
        this.ivKonskredit = "0"; // Standardmaessig erst einmal "0" setzen
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
            if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) > 0 &&
                StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) < 16)
            {
                this.ivKonskredit = "1";
                if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 1 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 10 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 11)
                {
                    this.ivKonsfuehrer = "NORD/LB";
                }
                if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 2 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 4 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 12 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 13 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 14 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 15)
                {
                    this.ivKonsfuehrer = "Fremde F&#252;hrung";
                }
            }
        }
        
        /* Konsortiale ..... Summe der anderen, nur bei korrektem Schl.... */
        if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) > 0 &&
                StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) < 20)
        { /* Bedeutet Kons.*/
         if (StringKonverter.convertString2Double(pvDarlehen.getUrsprungsKapital()) != 0 )
         { /* Anteil rechnen */
          lvWork = (StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital())).subtract(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeUKAP()));
          lvKonEigenUKAP_Fakt = lvBuerge_Fakt.multiply(lvWork.divide(StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital()), 9, RoundingMode.HALF_UP)); /* Max. 1 */
         } /* Anteil rechnen */
         if (StringKonverter.convertString2Double(pvDarlehen.getRestkapital()) != 0 )
         { /* Anteil rechnen */
          lvWork = (StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital())).subtract(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeRKAP()));
          lvKonEigenRKAP_Fakt = lvBuerge_Fakt.multiply(lvWork.divide(StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital()), 9, RoundingMode.HALF_UP)); /* Max. 1 */
         } /* Anteil rechnen */
        } /* Bedeutet Kons.*/
        else
        { /* Nicht konsortial */
         lvKonEigenUKAP_Fakt = lvBuerge_Fakt;
         lvKonEigenRKAP_Fakt = lvBuerge_Fakt;
        } /* Nicht konsortial */

        /* dKonEigenUKAP_Fakt wird vom dKonEigenRKAP_Fakt gesetzt ! */
        lvKonEigenUKAP_Fakt = lvKonEigenRKAP_Fakt;

        //System.out.println("dKonEigenUKAP_Fakt: " + dKonEigenUKAP_Fakt.toString());
        //System.out.println("dKonEigenRKAP_Fakt: " + dKonEigenRKAP_Fakt.toString());        
        
        // CT - 06.06.2012 Ursprungskapital zu 100% nehmen
        //dUrsprungskapital = dUrsprungskapital.multiply(dKonEigenUKAP_Fakt);
        lvRestkapital      = lvRestkapital.multiply(lvKonEigenRKAP_Fakt);
        lvAuszahlungsVerpf = lvAuszahlungsVerpf.multiply(lvKonEigenUKAP_Fakt);
        
        //System.out.println("dUrsprungskaptial: " + dUrsprungskapital);
        //System.out.println("dRestkapital: " + dRestkapital);
        
        this.ivAbetrag = (lvUrsprungskapital.subtract(lvAuszahlungsVerpf)).toString();
        this.ivAktivpassiv = "1"; 
          
        /* Definition der Auszahlungstermin ......................... */
        String lvAusZahl = new String();
        /* Änderung vom 25.04.2005 Kontozustand > '8' statt > '4' */
        /* => Übernahme des Auszahlungstermines von == '4' auf > '3' */
        if (StringKonverter.convertString2Int(pvDarlehen.getKontozustand()) > 3)
        { /* Voll ausgezahlt */
         lvAusZahl = pvDarlehen.getVollvalutierungsdatum();
         this.ivAusstat = "2";
        } /* Voll ausgezahlt */
        if (StringKonverter.convertString2Int(pvDarlehen.getKontozustand()) < 4)
        { /* Noch nicht vollvalutiert */
         this.ivAusstat = "1";   
         if (!(pvDarlehen.getVorNotADat() == null) &&
             !pvDarlehen.getVorNotADat().equals("11110101") && // 01.01.1111
             !pvDarlehen.getVorNotADat().equals("00000000"))   // isEmpty
         { /* Es ist etwas da .... */
           lvAusZahl = pvDarlehen.getVorNotADat();
         } /* Es ist etwas da .... */
         else
         { /* jetzt das Buchungsdatum ! ... */
           lvAusZahl = pvDarlehen.getBuchungsdatum();
         } /* jetzt das Buchungsdatum ! ... */
        } /* Noch nicht vollvalutiert */

        this.ivAuszdat = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvAusZahl));
        this.ivAverpfl = lvAuszahlungsVerpf.toString();                    
        this.ivAz = pvDarlehen.getKontonummer();
                    
        this.ivKat = "1";
        // ISIN Verschluesselung als Wertpapier - CT 11.06.2014
        if (pvDarlehen.getIsin().substring(2,3).equals("W"))
        {
            this.ivKat = "2";
        }
          
        this.ivNbetrag = lvUrsprungskapital.toString();
          
        this.ivPfbrfrel = "3";
        
        // CT - 06.06.2012
        // Restkapital darf nicht groesser als das Nominalkapital sein
        if (lvRestkapital.compareTo(lvUrsprungskapital) == 1)
        {
          this.ivRkapi = lvUrsprungskapital.toString();
        }
        else
        {
          this.ivRkapi = lvRestkapital.toString();
        }
                
        // CT 26.03.2015 - Fest verdrahtet lvKTyp = "1"
        //lvKTyp = "1";
        
        // Vor den Umsetzungen ... Prüfungen, die zur Ablehnung führen
        //if (lvKTyp.isEmpty())
        //{ // Kein Kredittyp moeglich !
        // System.out.println("-->Konto " + pvDarlehen.getKontonummer() + "(" + pvDarlehen.getInstitutsnummer() +
        //         ";" + pvDarlehen.getHerkunftDarlehen() + ";" + pvDarlehen.getHerkunftDaten() +
        //         ";" + pvDarlehen.getKundennummer() + ";" + pvDarlehen.getObjektnummer() +
        //         ")MeKr" + pvDarlehen.getMerkmalKreditart() + ";Si" + pvDarlehen.getSicherheitenSchluessel() + "-Undef.Kredittyp");
         //return false;
        //} // Kein Kredittyp moeglich !

        this.ivTyp = "1";
          
        this.ivVwhrg = pvDarlehen.getSatzwaehrung();
          
        this.ivWhrg = pvDarlehen.getSatzwaehrung();
          
        this.ivZusdat = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvDarlehen.getBewilligungsdatum()));
        
        // Neu hinzugefuegte Felder - 29.09.2011
        // Deckungsschluessel
        this.ivDeckschl = pvDarlehen.getDeckungsschluessel();
        
        // Ausplatzierungsmerkmal
        this.ivAuspl = pvDarlehen.getAusplatzierungsmerkmal();
        
        // Produktionsschluessel
        this.ivProdschl = pvDarlehen.getProduktSchluessel();
        
        // Kennzeichen Rollover
        // Defaultmässig erst einmal auf "0" (false) setzen
        this.ivRoll = "0";
        // Bei Rollover muss das Kennzeichen gesetzt werdem
        if (pvDarlehen.getKennzeichenRollover().equals("F") || pvDarlehen.getKennzeichenRollover().equals("V"))
        {
          this.ivRoll = "1";
        }
        
        /*
        // CT 20.12.2011 - Repo-Flag
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivRepo = "0";
        // Produktschluessel (00)204 --> repoflag = true
        // Sonderbehandlung NLB - CT 01.10.2012
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
          if (pvDarlehen.getProduktSchluessel().equals("00204"))
          {
        	  this.ivRepo = "1";
          }
        }
        // Deckungsschluessel 1,2,3,4,5,6,7,8,0,9 --> repoflag = true
        if (pvDarlehen.getDeckungsschluessel().equals("1") || pvDarlehen.getDeckungsschluessel().equals("2") ||
            pvDarlehen.getDeckungsschluessel().equals("3") || pvDarlehen.getDeckungsschluessel().equals("4") ||
            pvDarlehen.getDeckungsschluessel().equals("5") || pvDarlehen.getDeckungsschluessel().equals("6") ||
            pvDarlehen.getDeckungsschluessel().equals("7") || pvDarlehen.getDeckungsschluessel().equals("8") ||
            pvDarlehen.getDeckungsschluessel().equals("0") || pvDarlehen.getDeckungsschluessel().equals("9"))
        {
            this.ivRepo = "1";
        }*/
        // Sonderbehandlung NLB - CT 01.10.2012
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
          // Produktschluessel (00)204 --> repoflag = true
          if (pvDarlehen.getProduktSchluessel().equals("00204"))
          {
        	  this.ivRepo = "1";
          }
          
          // Produktschluessel == 105 und (Ausplatzierungsmerkmal == K0 oder K1)
          if (pvDarlehen.getProduktSchluessel().equals("00105") && (pvDarlehen.getAusplatzierungsmerkmal().equals("K0") || pvDarlehen.getAusplatzierungsmerkmal().equals("K1")))
          {
        	  this.ivRepo = "1";
          }
        }
        // CT 19.08.2016 - Repo-Flag anhand des Ausplatzierungsmerkmal
        // Kein defaultmaessiges Setzen mehr
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
        {
        	this.ivRepo = "1";
        }
        
        // Hinzugefuegte Felder - 15.11.2012
       this.ivKein = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvDarlehen.getKontoEinrichtung()));
       this.ivBoe = pvDarlehen.getBewilligendeOE();
       this.ivVoe = pvDarlehen.getVerwaltendeOE();
       this.ivPoe = pvDarlehen.getProduktverantwortlicheOE();
        // Hinzugefuegte Felder - 15.11.2012
    
       if (pvDarlehen.getIsin().substring(2,3).equals("W"))
       {
           this.ivTkurs = "100.00";
       }

       this.ivFinart = "7";
       //System.out.println("ivFinart: " + this.ivFinart);
     
       return true;
    }
    
    /**
     * Importiert die Darlehensinformationen aus LoanIQ
     * @param pvDarlehenLoanIQBlock
     * @param pvVorlaufsatz 
     * @param pvLogger
     * @return 
     */
    public boolean importLoanIQ(DarlehenLoanIQBlock pvDarlehenLoanIQBlock, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
    	DarlehenLoanIQ lvHelpDarlehenLoanIQ = pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto();

    	// Konsortial
        if (lvHelpDarlehenLoanIQ.getKennzeichenKonsortialkredit().equals("J"))
        {
          this.ivKonskredit = "1";
          this.ivKonsfuehrer = lvHelpDarlehenLoanIQ.getKonsortialfuehrer();
        }
        else
        {
            this.ivKonskredit = "0";
        }
        
    	BigDecimal lvNennbetrag;
        // Nennbetrag
    	if (lvHelpDarlehenLoanIQ.getKennzeichenKonsortialkredit().equals("J"))
    	{
    		// Fremde Fuehrung
    		if (pvDarlehenLoanIQBlock.isListeDarlehenLoanIQFremdEmpty())
    		{
    			pvLogger.info("Fremde Fuehrung - Darlehen " + pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getKontonummer() + " Betrag: " + pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getNennbetrag());
    			lvNennbetrag = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getNennbetrag());
    		}
    		else // Eigene Fuehrung
    		{
    			pvLogger.info("Eigene Fuehrung - Darlehen " + pvDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getKontonummer() + " Betrag: " + pvDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getNennbetrag());
    			lvNennbetrag = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getNennbetrag());        		
    		}
    	}
    	else
    	{
    		lvNennbetrag = StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getNennbetrag());
    	}
        this.ivNbetrag = lvNennbetrag.toPlainString();
        
        BigDecimal lvAuszahlungsVerpf = StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getAuszahlungsverpflichtung());
        this.ivAbetrag = (lvNennbetrag.subtract(lvAuszahlungsVerpf)).toPlainString();

        // Uebergangsloesung Nennbetrag wegen Kapitalisierung -> andere LoanIQ Logik
        if (lvHelpDarlehenLoanIQ.getKontonummer().equals("4250119430") || lvHelpDarlehenLoanIQ.getKontonummer().equals("4250117110") ||
            lvHelpDarlehenLoanIQ.getKontonummer().equals("4250115800") || lvHelpDarlehenLoanIQ.getKontonummer().equals("4250119410") ||
            lvHelpDarlehenLoanIQ.getKontonummer().equals("4250117130") || lvHelpDarlehenLoanIQ.getKontonummer().equals("4250115780") ||
            lvHelpDarlehenLoanIQ.getKontonummer().equals("4250115100") || lvHelpDarlehenLoanIQ.getKontonummer().equals("4250117030") ||
            lvHelpDarlehenLoanIQ.getKontonummer().equals("4250115680") || lvHelpDarlehenLoanIQ.getKontonummer().equals("4250117050") ||
            lvHelpDarlehenLoanIQ.getKontonummer().equals("4250117040") || lvHelpDarlehenLoanIQ.getKontonummer().equals("4250116850") ||
            lvHelpDarlehenLoanIQ.getKontonummer().equals("4250106460") || lvHelpDarlehenLoanIQ.getKontonummer().equals("4250127650") ||
            lvHelpDarlehenLoanIQ.getKontonummer().equals("4250119000") || lvHelpDarlehenLoanIQ.getKontonummer().equals("4250134150"))
        {
        	this.ivNbetrag = "";
        	this.ivAbetrag = "";
        }
        
        this.ivAktivpassiv = "1"; 
          
        this.ivAusstat = lvHelpDarlehenLoanIQ.getAuszahlungsstatus();

        this.ivAuszdat = DatumUtilities.changeDate(lvHelpDarlehenLoanIQ.getAuszahlungsdatum());

        // Buergschaftprozent
        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
    	BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);

    	if (lvBuergschaftprozent.doubleValue() > 0.0 && lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		this.ivAverpfl = (StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getAuszahlungsverpflichtung())).multiply(lvBuergschaftprozent).toPlainString();
    	}
    	else
    	{
    		this.ivAverpfl = lvHelpDarlehenLoanIQ.getAuszahlungsverpflichtung(); 
    	}
        this.ivAz = lvHelpDarlehenLoanIQ.getKontonummer();
                           
        this.ivPfbrfrel = "3";
 
    	if (lvBuergschaftprozent.doubleValue() > 0.0 && lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		this.ivRkapi = (StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getRestkapital())).multiply(lvBuergschaftprozent).toPlainString();
        }
    	else
    	{
    		this.ivRkapi = lvHelpDarlehenLoanIQ.getRestkapital();
        }
        
        // Definition des Kredittyps
        int lvHelpKredittyp = ValueMapping.ermittleKredittyp(lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal(), lvHelpDarlehenLoanIQ.getBuergschaftprozent());
          
        if (lvHelpKredittyp == DarlehenLoanIQ.UNDEFINIERT)
        {
        	pvLogger.error("Darlehen " + lvHelpDarlehenLoanIQ.getKontonummer() + ": Kredittyp undefiniert...");
        	return false;
        }
                 
        this.ivTyp = (new Integer(lvHelpKredittyp)).toString();
          
        this.ivKat = "1";
        if (lvHelpKredittyp == DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG)
        {
          this.ivKat = "2";
        }

        this.ivVwhrg = lvHelpDarlehenLoanIQ.getBetragwaehrung();
          
        this.ivWhrg = lvHelpDarlehenLoanIQ.getBetragwaehrung();
          
        this.ivZusdat = DatumUtilities.changeDate(lvHelpDarlehenLoanIQ.getZusagedatum());
        
        // Neu hinzugefuegte Felder - 29.09.2011
        // Deckungsschluessel
        this.ivDeckschl = lvHelpDarlehenLoanIQ.getDeckungsschluessel();
        
        // Ausplatzierungsmerkmal - CT 01.10.2014
        this.ivAuspl = lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal();
        
        // Produktionsschluessel
        this.ivProdschl = lvHelpDarlehenLoanIQ.getProduktschluessel();
        
        // Kennzeichen Rollover
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivRoll = "0";
        // Bei Rollover muss das Kennzeichen gesetzt werden
        if (lvHelpDarlehenLoanIQ.getKennzeichenRollover().equals("F") ||
            lvHelpDarlehenLoanIQ.getKennzeichenRollover().equals("V"))
        {
          this.ivRoll = "1";
        }
        
        // Sonderbehandlung NLB - CT 01.10.2012
        if (pvVorlaufsatz.getInstitutsnummer().equals("009"))
        {
        	// Produktschluessel (00)204 --> repoflag = true
        	if (lvHelpDarlehenLoanIQ.getProduktschluessel().endsWith("204"))
        	{
        	  this.ivRepo = "1";
        	}
          
            // Produktschluessel == 105 und (Ausplatzierungsmerkmal == K0 oder K1)
            if (lvHelpDarlehenLoanIQ.getProduktschluessel().endsWith("105") && (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("K0") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("K1")))
            {
          	  this.ivRepo = "1";
            }
        }
        // CT 19.08.2016 - Repo-Flag anhand des Ausplatzierungsmerkmal
        // Kein defaultmaessiges Setzen mehr
        if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("2") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("4"))
        {
        	this.ivRepo = "1";
        }
        
        // Wenn Kredittyp == 'Sonstige Schuldverschreibung' (23), dann Tageskurs (tkurs) = 100.00 
        if (ValueMapping.ermittleKredittyp(lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal(), lvHelpDarlehenLoanIQ.getBuergschaftprozent()) == DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG)
        {
            this.ivTkurs = "100.00";
        }
        
       // Hinzugefuegte Felder - 15.11.2012
       this.ivKein = DatumUtilities.changeDate(lvHelpDarlehenLoanIQ.getKontoeinrichtung());
       this.ivBoe = lvHelpDarlehenLoanIQ.getBewilligendeOE();
       this.ivVoe = lvHelpDarlehenLoanIQ.getVerwaltendeOE();
       this.ivPoe = lvHelpDarlehenLoanIQ.getPOE();
       // Hinzugefuegte Felder - 15.11.2012
           
       return true;
    }
    
    /**
     * Importiert die PassivDaten aus LoanIQ
     * @param pvPassivDaten
     * @param pvInstitutsnummer
     */
    public boolean importLoanIQPassiv(LoanIQPassivDaten pvPassivDaten, String pvInstitutsnummer)
    {       
       String lvTxstyp = "undef";

       this.setAktivpassiv("2");
       
       String lvKey = pvPassivDaten.getAktenzeichen(); 
       // BLB: DE + "Kontonummer"
       if (pvInstitutsnummer.startsWith("004"))
       {
    	   lvKey = "DE" + pvPassivDaten.getKontonummer();
       }
       this.setAz(lvKey);
       this.setIsin(lvKey);       
       this.setKat("7");
       
       this.setNbetrag(pvPassivDaten.getNennbetrag());

       // Kennzeichen Rollover
       // Defaultmaessig auf "0" (false) setzen
       // Bei Rollover muss das Kennzeichen gesetzt werden
       this.setRoll("0");  
       if (pvPassivDaten.getRolloverKennzeichen().equals("F") ||
           pvPassivDaten.getRolloverKennzeichen().equals("V"))
       {
         this.setRoll("1");  
       }
              
       // Produkttyp umsetzen
       // der TXS Produkttyp ist den dortigen Aufzaehlungen zu entnehmen
       String lvProdukt = pvPassivDaten.getKredittyp();
         
       // -> neue Schluessel einpflegen ?
       if (lvProdukt.equals("5011") || lvProdukt.equals("5056"))
       {
    	   lvTxstyp = "020101";                
       }
       if (lvProdukt.equals("5012") || lvProdukt.equals("5057"))
       {
    	   lvTxstyp = "020201";                
       }
       else if (lvProdukt.equals("5013") || lvProdukt.equals("5058"))
       {
    	   lvTxstyp = "020101";                
       }
       else if (lvProdukt.equals("5201") || lvProdukt.equals("5202"))
       {
    	   lvTxstyp = "020201";                
       }

       // Sonderbehandlung fuer die 'anderen' Register (Schiffe und Flugzeuge)
       if (pvPassivDaten.getAusplatzierungsmerkmal().startsWith("S"))
       {
    	   lvTxstyp = "020301";
       }
       else if (pvPassivDaten.getAusplatzierungsmerkmal().startsWith("F"))
       {
    	   lvTxstyp = "020401";   
       }
       this.setTyp(lvTxstyp);
       this.setVwhrg(pvPassivDaten.getWaehrung());       
       this.setWhrg(pvPassivDaten.getWaehrung());
       
       this.setAuspl(pvPassivDaten.getAusplatzierungsmerkmal());
       this.setDeckschl(pvPassivDaten.getDeckungsschluessel());
       
       if (pvPassivDaten.getAusplatzierungsmerkmal().equals("O0") || pvPassivDaten.getAusplatzierungsmerkmal().equals("O1") || pvPassivDaten.getAusplatzierungsmerkmal().equals("O3"))
       {
    	   this.ivPfandbriefoepg = "1";
       }
       
       return true;
    }
    
    /**
     * Importiert die Darlehensinformationen aus LoanIQ fuer das RefiRegister
     * @param pvDarlehenLoanIQ
     * @param pvLogger
     * @return 
     */
    public boolean importLoanIQRefiRegister(DarlehenLoanIQ pvDarlehenLoanIQ, Logger pvLogger)
    {
    	// Konsortial
        if (pvDarlehenLoanIQ.getKennzeichenKonsortialkredit().equals("J"))
        {
          this.ivKonskredit = "1";
          this.ivKonsfuehrer = pvDarlehenLoanIQ.getKonsortialfuehrer();
        }
        else
        {
            this.ivKonskredit = "0";
        }
        
    	////BigDecimal lvNennbetrag = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getNennbetrag());
        // Nennbetrag
    	/* if (pvDarlehenLoanIQ.getKennzeichenKonsortialkredit().equals("J"))
    	{
    		// Fremde Fuehrung
    		if (pvDarlehenLoanIQ.isListeDarlehenLoanIQFremdEmpty())
    		{
    			pvLogger.info("Fremde Fuehrung - Darlehen " + pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getKontonummer() + " Betrag: " + pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getNennbetrag());
    			lvNennbetrag = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getNennbetrag());
    		}
    		else // Eigene Fuehrung
    		{
    			pvLogger.info("Eigene Fuehrung - Darlehen " + pvDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getKontonummer() + " Betrag: " + pvDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getNennbetrag());
    			lvNennbetrag = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getNennbetrag());        		
    		}
    	}
    	else
    	{
    		lvNennbetrag = StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getNennbetrag());
    	} */
        ////this.ivNbetrag = lvNennbetrag.toPlainString();
        
        ////BigDecimal lvAuszahlungsVerpf = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getAuszahlungsverpflichtung());
        ////this.ivAbetrag = (lvNennbetrag.subtract(lvAuszahlungsVerpf)).toPlainString();
        
        this.ivAktivpassiv = "1"; 
          
        this.ivAusstat = pvDarlehenLoanIQ.getAuszahlungsstatus();

        this.ivAuszdat = DatumUtilities.changeDate(pvDarlehenLoanIQ.getAuszahlungsdatum());

        // Buergschaftprozent
        //BigDecimal lvHelpFaktor = new BigDecimal("100.0");
    	//BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);

    	//if (lvBuergschaftprozent.doubleValue() > 0.0 && lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
    	//{
    	//	this.ivAverpfl = (StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getAuszahlungsverpflichtung())).multiply(lvBuergschaftprozent).toPlainString();
    	//}
    	//else
    	//{
    		this.ivAverpfl = pvDarlehenLoanIQ.getAuszahlungsverpflichtung(); 
    	//}
        this.ivAz = pvDarlehenLoanIQ.getKontonummer() + "_" + pvDarlehenLoanIQ.getCustomerID();
                           
        this.ivPfbrfrel = "3";
 
    	//if (lvBuergschaftprozent.doubleValue() > 0.0 && lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
    	//{
    	//	this.ivRkapi = (StringKonverter.convertString2BigDecimal(lvHelpDarlehenLoanIQ.getRestkapital())).multiply(lvBuergschaftprozent).toPlainString();
        //}
    	//else
    	//{
    		this.ivRkapi = pvDarlehenLoanIQ.getRestkapital();
        //}
        
        // Definition des Kredittyps
        //int lvHelpKredittyp = ValueMapping.ermittleKredittyp(pvDarlehenLoanIQ.getAusplatzierungsmerkmal(), lvHelpDarlehenLoanIQ.getBuergschaftprozent());
          
        //if (lvHelpKredittyp == DarlehenLoanIQ.UNDEFINIERT)
        //{
        //	pvLogger.error("Darlehen " + lvHelpDarlehenLoanIQ.getKontonummer() + ": Kredittyp undefiniert...");
        //	return false;
        //}
                 
        this.ivTyp = "1"; //(new Integer(lvHelpKredittyp)).toString();
          
        this.ivKat = "1";
        //if (lvHelpKredittyp == DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG)
        //{
        //  this.ivKat = "2";
        //}

        this.ivVwhrg = pvDarlehenLoanIQ.getBetragwaehrung();
          
        this.ivWhrg = pvDarlehenLoanIQ.getBetragwaehrung();
          
        this.ivZusdat = DatumUtilities.changeDate(pvDarlehenLoanIQ.getZusagedatum());
        
        // Neu hinzugefuegte Felder - 29.09.2011
        // Deckungsschluessel
        this.ivDeckschl = pvDarlehenLoanIQ.getDeckungsschluessel();
        
        // Ausplatzierungsmerkmal - CT 01.10.2014
        this.ivAuspl = pvDarlehenLoanIQ.getAusplatzierungsmerkmal();
        
        // Produktionsschluessel
        this.ivProdschl = pvDarlehenLoanIQ.getProduktschluessel();
        
        // Kennzeichen Rollover
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivRoll = "0";
        // Bei Rollover muss das Kennzeichen gesetzt werden
        if (pvDarlehenLoanIQ.getKennzeichenRollover().equals("F") ||
            pvDarlehenLoanIQ.getKennzeichenRollover().equals("V"))
        {
          this.ivRoll = "1";
        }
        
        // Produktschluessel (00)204 --> repoflag = true
        // Sonderbehandlung NLB - CT 01.10.2012
        //if (pvVorlaufsatz.getInstitutsnummer().equals("009"))
        //{
        //  if (lvHelpDarlehenLoanIQ.getProduktschluessel().endsWith("204"))
        //  {
        //	  this.ivRepo = "1";
        //  }
        //}
        // CT 19.08.2016 - Repo-Flag anhand des Ausplatzierungsmerkmal
        // Kein defaultmaessiges Setzen mehr
        //if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("2") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("4"))
        //{
        	//this.ivRepo = "0";
        //}
        
        // Wenn Kredittyp == 'Sonstige Schuldverschreibung' (23), dann Tageskurs (tkurs) = 100.00 
        //if (ValueMapping.ermittleKredittyp(lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal(), lvHelpDarlehenLoanIQ.getBuergschaftprozent()) == DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG)
        //{
        //    this.ivTkurs = "100.00";
        //}
        
       // Hinzugefuegte Felder - 15.11.2012
       this.ivKein = DatumUtilities.changeDate(pvDarlehenLoanIQ.getKontoeinrichtung());
       this.ivBoe = pvDarlehenLoanIQ.getBewilligendeOE();
       this.ivVoe = pvDarlehenLoanIQ.getVerwaltendeOE();
       this.ivPoe = pvDarlehenLoanIQ.getPOE();
       // Hinzugefuegte Felder - 15.11.2012
           
       return true;
    }
    
    /**
     * Importiert die Darlehensinformationen aus DarKa
     * Aufruf aus den Klassen 'DarlehenVerarbeiten' und 'DarlehenVerarbeitung'
     * @param pvModus 
     * @param pvDarlehen
     * @param pvLogger 
     * @return 
     */
    public boolean importDarlehen(int pvModus, Darlehen pvDarlehen, Logger pvLogger)
    {     
        /* Definition der Ursprungskapital .......................... */
        BigDecimal lvUrsprungskapital = new BigDecimal(pvDarlehen.getUrsprungsKapital());
        /* Definition der Auszahlungsverpflichtungen ................ */
        BigDecimal lvAuszahlungsVerpf = StringKonverter.convertString2BigDecimal(pvDarlehen.getOffZusage());
        /* Definition der Restkapital ............................... */
        BigDecimal lvRestkapital = new BigDecimal("0.0");
        /* Nach Definition ohne Summe rueckstaendiger Leistung */
        lvRestkapital  = ((StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital())).add(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeNoNiFaeTilgung()))).add(StringKonverter.convertString2BigDecimal(pvDarlehen.getSondertilgung()));

        // Rechenkonstante
        BigDecimal lvBtrDivHd = new BigDecimal("0.01");
        //System.out.println("dBtrDivHd: " + dBtrDivHd.toString());
        
        /* Wird dann immer als Multiplikator genutzt */
        BigDecimal lvWork = new BigDecimal("0.0");
        BigDecimal lvBuerge_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenUKAP_Fakt = new BigDecimal("1.0");
        BigDecimal lvKonEigenRKAP_Fakt = new BigDecimal("1.0");
            
        if (pvDarlehen.getKredittyp().equals("4"))
        { /* mit Buerge .. anteilig */
         if (StringKonverter.convertString2Double(pvDarlehen.getBuergschaftProzent()) != 0.0)
         { /* nichts da */
          lvBuerge_Fakt = lvBtrDivHd.multiply(StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftProzent()));
         } /* etwas da */
        } /* mit Buerge .. anteilig */

        // BLB - Buergschaftsprozentsatz nicht verwenden, wenn der Deckungsschluessel 'S' oder 'V' und das Produkt '0802' ist
        if (pvDarlehen.getInstitutsnummer().equals("004"))
        {
            if ((pvDarlehen.getDeckungsschluessel().equals("S") || pvDarlehen.getDeckungsschluessel().equals("V")) && pvDarlehen.getProduktSchluessel().equals("00802"))
            {
                // Buergen-Faktor wieder auf '1' setzen
                lvBuerge_Fakt = new BigDecimal("1.0");
            }
        }
        
        // NLB - Buergschaftsprozentsatz nicht verwenden, wenn der Deckungsschluessel 'S' oder 'V' ist
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
            if (pvDarlehen.getDeckungsschluessel().equals("S") || pvDarlehen.getDeckungsschluessel().equals("V"))
            {
                // Buergen-Faktor wieder auf '1' setzen
                lvBuerge_Fakt = new BigDecimal("1.0");
            }
        }        

        // CT 25.02.2013
        // wenn (Schluessel Kompensation > 0 und < 16), dann "Konsortialkredit" = ja, sonst "Konsortialkredit" = nein
        // wenn (Schluessel Kompensation = 01/10/11), dann "Konsortialführer" = NORD/LB, sonst
        // wenn (Schluessel Kompensation = 02/04/12/13/14/15), dann "Konsortialführer" = Fremde Fuehrung, sonst "Konsortialfuehrer" bleibt leer
        this.ivKonskredit = "0"; // Standardmaessig erst einmal "0" setzen
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
            if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) > 0 &&
                StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) < 16)
            {
                this.ivKonskredit = "1";
                if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 1 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 10 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 11)
                {
                    this.ivKonsfuehrer = "NORD/LB";
                }
                if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 2 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 4 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 12 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 13 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 14 ||
                    StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) == 15)
                {
                    this.ivKonsfuehrer = "Fremde F&#252;hrung";
                }
            }
        }
        
        /* Konsortiale ..... Summe der anderen, nur bei korrektem Schl.... */
        if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) > 0 &&
                StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) < 20)
        { /* Bedeutet Kons.*/
         if (StringKonverter.convertString2Double(pvDarlehen.getUrsprungsKapital()) != 0 )
         { /* Anteil rechnen */
          lvWork = (StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital())).subtract(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeUKAP()));
          lvKonEigenUKAP_Fakt = lvBuerge_Fakt.multiply(lvWork.divide(StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital()), 9, RoundingMode.HALF_UP)); /* Max. 1 */
         } /* Anteil rechnen */
         if (StringKonverter.convertString2Double(pvDarlehen.getRestkapital()) != 0 )
         { /* Anteil rechnen */
          lvWork = (StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital())).subtract(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeRKAP()));
          lvKonEigenRKAP_Fakt = lvBuerge_Fakt.multiply(lvWork.divide(StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital()), 9, RoundingMode.HALF_UP)); /* Max. 1 */
         } /* Anteil rechnen */
        } /* Bedeutet Kons.*/
        else
        { /* Nicht konsortial */
         lvKonEigenUKAP_Fakt = lvBuerge_Fakt;
         lvKonEigenRKAP_Fakt = lvBuerge_Fakt;
        } /* Nicht konsortial */

        /* dKonEigenUKAP_Fakt wird vom dKonEigenRKAP_Fakt gesetzt ! */
        lvKonEigenUKAP_Fakt = lvKonEigenRKAP_Fakt;
        
        // CT - 06.06.2012 Ursprungskapital zu 100% nehmen
        //dUrsprungskapital = dUrsprungskapital.multiply(dKonEigenUKAP_Fakt);
        lvRestkapital      = lvRestkapital.multiply(lvKonEigenRKAP_Fakt);
        lvAuszahlungsVerpf = lvAuszahlungsVerpf.multiply(lvKonEigenUKAP_Fakt);
               
        this.ivAbetrag = (lvUrsprungskapital.subtract(lvAuszahlungsVerpf)).toString();
        this.ivAktivpassiv = "1"; 
          
        /* Definition der Auszahlungstermin ......................... */
        String lvAusZahl = new String();
        /* Änderung vom 25.04.2005 Kontozustand > '8' statt > '4' */
        /* => Übernahme des Auszahlungstermines von == '4' auf > '3' */
        if (StringKonverter.convertString2Int(pvDarlehen.getKontozustand()) > 3)
        { /* Voll ausgezahlt */
         lvAusZahl = pvDarlehen.getVollvalutierungsdatum();
         this.ivAusstat = "2";
        } /* Voll ausgezahlt */
        if (StringKonverter.convertString2Int(pvDarlehen.getKontozustand()) < 4)
        { /* Noch nicht vollvalutiert */
         this.ivAusstat = "1";   
         if (!(pvDarlehen.getVorNotADat() == null) &&
             !pvDarlehen.getVorNotADat().equals("11110101") && // 01.01.1111
             !pvDarlehen.getVorNotADat().equals("00000000"))   // isEmpty
         { /* Es ist etwas da .... */
           lvAusZahl = pvDarlehen.getVorNotADat();
         } /* Es ist etwas da .... */
         else
         { /* jetzt das Buchungsdatum ! ... */
           lvAusZahl = pvDarlehen.getBuchungsdatum();
         } /* jetzt das Buchungsdatum ! ... */
        } /* Noch nicht vollvalutiert */

        this.ivAuszdat = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvAusZahl));
        this.ivAverpfl = lvAuszahlungsVerpf.toString();                    
        this.ivAz = pvDarlehen.getKontonummer();
                    
        this.ivKat = "1";
        // Umschluesselung zum Wertpapier(Aktiv) -> kat="2"
        // H2, S2, O2, F2, H4, S4, O4 und F4 -> Wertpapier(Aktiv)
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
        {
            this.ivKat = "2";
        }
        // H3, S3 und F3 -> Wertpapier(Aktiv)
        // K3 bleibt kat = "1"
        if (pvDarlehen.getAusplatzierungsmerkmal().equals("H3") || pvDarlehen.getAusplatzierungsmerkmal().equals("S3") || pvDarlehen.getAusplatzierungsmerkmal().equals("F3"))
        {
        	this.ivKat = "2";
        }
          
        this.ivNbetrag = lvUrsprungskapital.toString();
          
        this.ivPfbrfrel = "3";
        
        // CT - 06.06.2012
        // Restkapital darf nicht groesser als das Nominalkapital sein
        if (lvRestkapital.compareTo(lvUrsprungskapital) == 1)
        {
          this.ivRkapi = lvUrsprungskapital.toString();
        }
        else
        {
          this.ivRkapi = lvRestkapital.toString();
        }                
        
        String lvKTyp = (new Integer(ValueMapping.ermittleKredittyp(pvDarlehen.getAusplatzierungsmerkmal(), pvDarlehen.getBuergschaftProzent()))).toString();

        // Sonderregel BLB - CT 19.12.2016
        // Ausplatzierungsmerkmal H3 oder S3 und Produktschluessel 0101, 0103 oder 0106 dann Bankkredit
        // ansonsten Schuldverschreibung (aktuelle Regel)
        if (pvDarlehen.getInstitutsnummer().equals("004"))
        {
        	if (pvDarlehen.getAusplatzierungsmerkmal().equals("H3") || pvDarlehen.getAusplatzierungsmerkmal().equals("S3")) // Wenn H3 und S3, dann... 
        	{   // Wenn Produktschluessel 0101, 0103 oder 0106 dann Bankkredit
        		if (pvDarlehen.getProduktSchluessel().equals("00101") || pvDarlehen.getProduktSchluessel().equals("00103") || pvDarlehen.getProduktSchluessel().equals("00106"))
        		{
        			lvKTyp = "64"; // Bankkredit
        		}
        	}
        }
        
        // Besonderheit fuers Deckungspooling - CT 12.11.2014 - Nur NLB
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
          if (pvDarlehen.getProduktSchluessel().equals("00117"))
          {
            lvKTyp = "1";
          }
        }
        
        // CT 10.05.2017 - Einfach nur LettreDeGage-Schrott
        // LettreDeGage einfach "1"
        //if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("G"))
        //{
        //	lvKTyp = "1";
        //}
        
        // Vor den Umsetzungen... Pruefungen, die zur Ablehnung fuehren
        if (lvKTyp.equals("undefiniert"))
        { // Kein Kredittyp moeglich !
          pvLogger.error("-->Konto " + pvDarlehen.getKontonummer() + "(" + pvDarlehen.getInstitutsnummer() +
                   ";" + pvDarlehen.getHerkunftDarlehen() + ";" + pvDarlehen.getHerkunftDaten() +
                   ";" + pvDarlehen.getKundennummer() + ";" + pvDarlehen.getObjektnummer() +
                   ")MeKr" + pvDarlehen.getMerkmalKreditart() + ";Si" + pvDarlehen.getSicherheitenSchluessel() + "-Undef.Kredittyp");
          return false;
        } // Kein Kredittyp moeglich !

        this.ivTyp = lvKTyp;
          
        this.ivVwhrg = pvDarlehen.getSatzwaehrung();
          
        this.ivWhrg = pvDarlehen.getSatzwaehrung();
          
        this.ivZusdat = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvDarlehen.getBewilligungsdatum()));
        
        // Neu hinzugefuegte Felder - 29.09.2011
        // Deckungsschluessel
        this.ivDeckschl = pvDarlehen.getDeckungsschluessel();
        
        // Ausplatzierungsmerkmal
        this.ivAuspl = pvDarlehen.getAusplatzierungsmerkmal();
        
        // Produktionsschluessel
        this.ivProdschl = pvDarlehen.getProduktSchluessel();
        
        // Kennzeichen Rollover
        // Defaultmässig erst einmal auf "0" (false) setzen
        this.ivRoll = "0";
        // Bei Rollover muss das Kennzeichen gesetzt werden
        if (pvDarlehen.getKennzeichenRollover().equals("F") ||
            pvDarlehen.getKennzeichenRollover().equals("V"))
        {
          this.ivRoll = "1";
        }
        
         // Sonderbehandlung NLB - CT 01.10.2012
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
          // Produktschluessel (00)204 --> repoflag = true
          if (pvDarlehen.getProduktSchluessel().equals("00204"))
          {
        	  this.ivRepo = "1";
          }
          
          // Produktschluessel == 105 und (Ausplatzierungsmerkmal == K0 oder K1)
          if (pvDarlehen.getProduktSchluessel().equals("00105") && (pvDarlehen.getAusplatzierungsmerkmal().equals("K0") || pvDarlehen.getAusplatzierungsmerkmal().equals("K1")))
          {
        	  this.ivRepo = "1";
          }
        }
        // CT 19.08.2016 - Repo-Flag anhand des Ausplatzierungsmerkmal
        // Kein defaultmaessiges Setzen mehr
        if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
        {
        	this.ivRepo = "1";
        }
        
       // Hinzugefuegte Felder - 15.11.2012
       this.ivKein = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvDarlehen.getKontoEinrichtung()));
       this.ivBoe = pvDarlehen.getBewilligendeOE();
       this.ivVoe = pvDarlehen.getVerwaltendeOE();
       this.ivPoe = pvDarlehen.getProduktverantwortlicheOE();
       // Hinzugefuegte Felder - 15.11.2012
    
       // Tilgungskurs = "100.00" bei Wertpapier(Aktiv)
       // H2, S2, O2, F2, H4, S4, O4 und F4 -> Wertpapier(Aktiv)
       if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
       {
           this.ivTkurs = "100.00";
       }
       // H3, S3 und F3 -> Wertpapier(Aktiv)
       // K3 bleibt kat = "1"
       if (pvDarlehen.getAusplatzierungsmerkmal().equals("H3") || pvDarlehen.getAusplatzierungsmerkmal().equals("S3") || pvDarlehen.getAusplatzierungsmerkmal().equals("F3"))
       {
    	   this.ivTkurs = "100.00";
       }
       
       return true;
    }

    /**
     * Importiert die Darlehensinformationen aus MIDAS
     * @param pvDarlehenLoanIQ
     * @param pvVorlaufsatz 
     * @param pvLogger
     * @return 
     */
    public boolean importMIDAS(DarlehenLoanIQ pvDarlehenLoanIQ, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
    	// Konsortial
    	this.ivKonskredit = "0";
        if (pvDarlehenLoanIQ.getKennzeichenKonsortialkredit().equals("J"))
        {
          this.ivKonskredit = "1";
          this.ivKonsfuehrer = "Fremde F&#252;hrung"; //pvDarlehenLoanIQ.getKonsortialfuehrer();
        }
        
        // Nennbetrag
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("0"))
        {
        	BigDecimal lvNennbetrag = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getNennbetrag());
        	this.ivNbetrag = lvNennbetrag.toPlainString();
        }
        
        //BigDecimal lvAuszahlungsVerpf = StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getAuszahlungsverpflichtung());
        //this.ivAbetrag = (lvNennbetrag.subtract(lvAuszahlungsVerpf)).toPlainString();
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("0"))
        {
        	this.ivAbetrag = this.ivNbetrag; // CT 30.11.2017
        }
        
        this.ivAktivpassiv = "1"; 
          
        this.ivAusstat = pvDarlehenLoanIQ.getAuszahlungsstatus();

        this.ivAuszdat = DatumUtilities.changeDate(pvDarlehenLoanIQ.getAuszahlungsdatum());
        this.ivAverpfl = pvDarlehenLoanIQ.getAuszahlungsverpflichtung();                    
        this.ivAz = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehenLoanIQ.getQuellsystem(), pvDarlehenLoanIQ.getKontonummer());

        this.ivFinart = "13"; // Tilgungsdarlehen
        
        this.ivKat = "1";
                    
        this.ivPfbrfrel = "3";
                
        // Buergschaftprozent
        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
    	BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);
 
    	if (lvBuergschaftprozent.doubleValue() > 0.0)
    	{
    		this.ivRkapi = (StringKonverter.convertString2BigDecimal(pvDarlehenLoanIQ.getRestkapital())).multiply(lvBuergschaftprozent).toString();
    	}
    	else
    	{
    		this.ivRkapi = pvDarlehenLoanIQ.getRestkapital();
        }
        
        // Definition des Kredittyps
        int lvHelpKredittyp = MappingMIDAS.ermittleKredittyp(pvDarlehenLoanIQ.getAusplatzierungsmerkmal(), pvDarlehenLoanIQ.getBuergschaftprozent());
          
        if (lvHelpKredittyp == 0)
        {
        	pvLogger.error("MIDAS-Darlehen " + pvDarlehenLoanIQ.getKontonummer() + ": Kredittyp undefiniert...");
        	return false;
        }
                              
        this.ivTyp = "" + lvHelpKredittyp;
          
        this.ivVwhrg = pvDarlehenLoanIQ.getBetragwaehrung();
          
        this.ivWhrg = pvDarlehenLoanIQ.getBetragwaehrung();
          
        this.ivZusdat = DatumUtilities.changeDate(pvDarlehenLoanIQ.getZusagedatum());
        
        // Neu hinzugefuegte Felder - 29.09.2011
        // Deckungsschluessel
        this.ivDeckschl = pvDarlehenLoanIQ.getDeckungsschluessel();
        
        // Ausplatzierungsmerkmal - CT 01.10.2014
        this.ivAuspl = pvDarlehenLoanIQ.getAusplatzierungsmerkmal();
        
        // Produktionsschluessel
        this.ivProdschl = pvDarlehenLoanIQ.getProduktschluessel();
        
        // Kennzeichen Rollover
        // Defaultmaessig erst einmal auf "0" (false) setzen
        this.ivRoll = "0";
        // Bei Rollover muss das Kennzeichen gesetzt werden
        if (pvDarlehenLoanIQ.getKennzeichenRollover().equals("F") ||
            pvDarlehenLoanIQ.getKennzeichenRollover().equals("V"))
        {
          this.ivRoll = "1";
        }
        
        // Produktschluessel (00)204 --> repoflag = true
        if (pvDarlehenLoanIQ.getProduktschluessel().equals("00204"))
        {
        	this.ivRepo = "1";
        }
        
        // Produktschluessel == 105 und (Ausplatzierungsmerkmal == K0 oder K1)
        //if (pvDarlehenLoanIQ.getProduktschluessel().equals("00105") && (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("K0") || pvDarlehenLoanIQ.getAusplatzierungsmerkmal().equals("K1")))
        //{
      	//  this.ivRepo = "1";
        //}

        // CT 19.08.2016 - Repo-Flag anhand des Ausplatzierungsmerkmal
        // Kein defaultmaessiges Setzen mehr
        if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehenLoanIQ.getAusplatzierungsmerkmal().endsWith("4"))
        {
        	this.ivRepo = "1";
        }
        
       // Hinzugefuegte Felder - 15.11.2012
       this.ivKein = DatumUtilities.changeDate(pvDarlehenLoanIQ.getKontoeinrichtung());
       this.ivBoe = pvDarlehenLoanIQ.getBewilligendeOE();
       this.ivVoe = pvDarlehenLoanIQ.getVerwaltendeOE();
       this.ivPoe = pvDarlehenLoanIQ.getPOE();
       // Hinzugefuegte Felder - 15.11.2012
           
       return true;
    }

    /**
     * Importiert die Wertpapier-Informationen
     * @param pvBestandsdaten
     * @param pvGattung
     * @param pvLogger
     * @return 
     */
    public boolean importWertpapier(Bestandsdaten pvBestandsdaten, Gattung pvGattung, Logger pvLogger)
    {
    	this.ivAktivpassiv = pvBestandsdaten.getAktivPassiv();
    	this.ivAz = pvBestandsdaten.getProdukt();
    	this.ivIsin = pvBestandsdaten.getProdukt();
    	if (pvBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
    	{
    	  this.ivDepotnummer = pvBestandsdaten.getDepotNr();
    	}
    	if (pvBestandsdaten.getAktivPassiv().equals("1")) // Aktiv - Konstant '2'
    	{
    		this.ivKat = "2";
    	}
    	if (pvBestandsdaten.getAktivPassiv().equals("2")) // Passiv - Konstant '7'
    	{
    		this.ivKat = "7";
    	}
    	this.ivVwhrg = pvGattung.getGD630B();
    	this.ivWhrg = pvGattung.getGD630B();
    	this.ivNbetrag = pvBestandsdaten.getNominalbetrag();
    	if (pvBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
    	{
    		this.ivRkapi = pvBestandsdaten.getNominalbetrag();
    	}
    	
        // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
        // Aktive Wertpapiere aus der Liquiditaetsreserve oder der sichernden Ueberdeckung erhalten repo -> 'Ja'
        // CT 06.08.2015
    	this.ivRepo = "0"; //Default
        if (("K2".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung KO
            ("H2".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung Hypo
            ("S2".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung Schiffe
            ("F2".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung Flugzeuge
            ("K4".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // Liquiditaetsreserve KO
            ("H4".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // Liquiditaetsreserve HPF
            ("S4".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // Liquiditaetsreserve Schiffe 
            ("F4".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // Liquiditaetsreserve Flugzeuge
            ("O4".equals(pvBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung OEPG KO
            ("O2".equals(pvBestandsdaten.getAusplatzierungsmerkmal())))    // sichernde Ueberdeckung OEPG Hypo
        {
            this.ivRepo = "1";
        }
    	
    	this.ivAbetrag = pvBestandsdaten.getNominalbetrag();
    	
    	this.ivUrtilgsatz = pvGattung.getGD861A();
    	this.ivKuendr = pvGattung.getGD943();
    	if (pvBestandsdaten.getAktivPassiv().equals("1")) // Aktiv - Konstant '21'
    	{
    		this.ivTyp = "21";
    	}
    	if (pvBestandsdaten.getAktivPassiv().equals("2")) // Passiv - WP-Art
    	{
    		this.ivTyp = pvBestandsdaten.getWertpapierart();
    	}
    	
        if ("S0".equals(pvBestandsdaten.getAusplatzierungsmerkmal()) || "S1".equals(pvBestandsdaten.getAusplatzierungsmerkmal()))
        {
           this.ivTyp = "010301";
        }
        if ("F0".equals(pvBestandsdaten.getAusplatzierungsmerkmal()) || "F1".equals(pvBestandsdaten.getAusplatzierungsmerkmal()))  
        {
           this.ivTyp = "010401";
        }

    	
    	this.ivTkurs = "100.00";
    	//if (pvBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
    	//{
    	//	this.ivTkurs = pvGattung.getGD669();
    	//}
    	
        this.ivMAVPROD = pvBestandsdaten.getMAVPROD();
    	this.ivGd160 = pvGattung.getGD160();
    	this.ivGd260 = pvGattung.getGD260();
    	this.ivGd776 = pvGattung.getGD776();
    	this.ivGd776B = pvGattung.getGD776B();
    	//this.ivGibnot = pvGattung.getBoersennotiert();
    	
        return true;
    }
}
