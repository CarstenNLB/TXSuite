/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenBlock;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivDaten;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.Termingelder.Daten.Termingeld;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.MappingLoanIQ;
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.MappingWertpapiere;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;
import nlb.txs.schnittstelle.Wertpapier.Gattungsdaten.Gattung;
import org.apache.log4j.Logger;


/**
 * @author tepperc
 *
 */
public class TXSKonditionenDaten implements TXSTransaktion
{
    /**
     * 
     */
    private String ivAndat;
    
    /**
     * 
     */
    private String ivAtkonv;
    
    /**
     * 
     */
    private String ivAtkonvmod;
    
    /**
     * 
     */
    private String ivAtkonvtag;
    
    /**
     * 
     */
    private String ivBankkal;
    
    /**
     * 
     */
    private String ivBernom;
    
    /**
     * 
     */
    private String ivCap;
    
    /**
     * 
     */
    private String ivDatltztanp;
    
    /**
     * 
     */
    private String ivDatltztfix;
    
    /**
     * 
     */
    private String ivDatltzttilg;
    
    /**
     * 
     */
    private String ivEdat;
    
    /**
     * 
     */
    private String ivEkurs;
    
    /**
     * 
     */
    private String ivEnddat;
    
    /**
     * 
     */
    private String ivFaellig;
    
    /**
     * 
     */
    private String ivFaelligst;
    
    /**
     * 
     */
    private String ivFerienkonv;
    
    /**
     * 
     */
    private String ivFixkalart;
    
    /**
     * 
     */
    private String ivFixkonv;
    
    /**
     * Fixing-Rhythmus
     */
    private String ivFixrhyth;
    
    /**
     * 
     */
    private String ivFixtage;
    
    /**
     * 
     */
    private String ivFixtagedir;
    
    /**
     * 
     */
    private String ivFixtagemod;
    
    /**
     * 
     */
    private String ivFixzins;
    
    /**
     * 
     */
    private String ivFloor;
    
    /**
     * 
     */
    private String ivKalfix;
     
    /**
     * 
     */
    private String ivKalkonv;
    
    /**
     * 
     */
    private String ivKondkey;
    
    /**
     * 
     */
    private String ivKupbas;
    
    /**
     * 
     */
    private String ivKupbasodd;
    
    /**
     * 
     */
    private String ivLfzmon;
    
    /**
     * 
     */
    private String ivLrate;
    
    /**
     * 
     */
    private String ivMantilg;
    
    /**
     * 
     */
    private String ivManzins;
    
    /**
     * 
     */
    private String ivMonendkonv;
    
    /**
     * 
     */
    private String ivNomzins;
     
    /**
     * 
     */
    private String ivRaten;
    
    /**
     * 
     */
    private String ivRefizins;
    
    /**
     * 
     */
    private String ivRefzins;

    /**
     *
     */
    private String ivRefzinstxt;

    /**
     * 
     */
    private String ivRkapfaell;
    
    /**
     * 
     */
    private String ivRkurs;
    
    /**
     * 
     */
    private String ivSondtilg;
    
    /**
     * 
     */
    private String ivSpread;
     
    /**
     * 
     */
    private String ivTilgabw;
    
    /**
     * 
     */
    private String ivTilgbeg;
    
    /**
     * 
     */
    private String ivTilgdat;
    
    /**
     * 
     */
    private String ivTilgperkonv;
    
    /**
     * 
     */
    private String ivTilgryth;
    
    /**
     * 
     */
    private String ivTilgsatz;
    
    /**
     * 
     */
    private String ivTilgver;
    
    /**
     * 
     */
    private String ivVfaellig;
    
    /**
     * 
     */
    private String ivVztilgdat;
    
    /**
     * 
     */
    private String ivVzinsdat;
     
    /**
     * 
     */
    private String ivWhrg;
    
    /**
     * 
     */
    private String ivZahltyp;
    
    /**
     * 
     */
    private String ivZhedge;
    
    /**
     * 
     */
    private String ivZinsabw;
    
    /**
     * 
     */
    private String ivZinsbeg;
    
    /**
     * 
     */
    private String ivZinsdat;
    
    /**
     * 
     */
    private String ivZinsenddat;
    
    /**
     * 
     */
    private String ivZinsfak;
    
    /**
     * 
     */
    private String ivZinsperkonv;
   
    /**
     * 
     */
    private String ivZinsryth;
    
    /**
     * 
     */
    private String ivZinstyp;
    
    /**
     * 
     */
    private String ivZinszahlart;

    // Felder hinzugefuegt - 15.11.2012
    /**
     * 
     */
    private String ivDza;
    
    /**
     * 
     */
    private String ivDlza;
    
    /**
     * 
     */
    private String ivDnz;
    
    /**
     * 
     */
    private String ivDlz;
    
    /**
     * 
     */
    private String ivDkv;
    
    /**
     * 
     */
    private String ivZbkv;
    // Felder hinzugefuegt - 15.11.2012
    
    /**
     * Konstruktor
     */
    public TXSKonditionenDaten()
    {
        initTXSKonditionenDaten();
    }
    
    /**
     * Initialisierung der Felder mit leeren Strings 
     */
    public void initTXSKonditionenDaten()
    {
        this.ivAndat = new String();
        this.ivAtkonv = new String();
        this.ivAtkonvmod = new String();
        this.ivAtkonvtag = new String();
        this.ivBankkal = new String();
        this.ivBernom = new String();
        this.ivCap = new String();
        this.ivDatltztanp = new String();
        this.ivDatltztfix = new String();
        this.ivDatltzttilg = new String();
        this.ivEdat = new String();
        this.ivEkurs = new String();
        this.ivEnddat = new String();
        this.ivFaellig = new String();
        this.ivFaelligst = new String();
        this.ivFerienkonv = new String();
        this.ivFixkalart = new String();
        this.ivFixkonv = new String();
        this.ivFixrhyth = new String();
        this.ivFixtage = new String();
        this.ivFixtagedir = new String();
        this.ivFixtagemod = new String();
        this.ivFixzins = new String();
        this.ivFloor = new String();
        this.ivKalfix = new String();
        this.ivKalkonv = new String();
        this.ivKondkey = new String();
        this.ivKupbas = new String();
        this.ivKupbasodd = new String();
        this.ivLfzmon = new String();
        this.ivLrate = new String();
        this.ivMantilg = new String();
        this.ivManzins = new String();
        this.ivMonendkonv = new String();
        this.ivNomzins = new String();
        this.ivRaten = new String();
        this.ivRefizins = new String();
        this.ivRefzins = new String();
        this.ivRefzinstxt = new String();
        this.ivRkapfaell = new String();
        this.ivRkurs = new String();
        this.ivSondtilg = new String();
        this.ivSpread = new String();
        this.ivTilgabw = new String();
        this.ivTilgbeg = new String();
        this.ivTilgdat = new String();
        this.ivTilgperkonv = new String();
        this.ivTilgryth = new String();
        this.ivTilgsatz = new String();
        this.ivTilgver = new String();
        this.ivVfaellig = new String();
        this.ivVztilgdat = new String();
        this.ivVzinsdat = new String();
        this.ivWhrg = new String();
        this.ivZahltyp = new String();
        this.ivZhedge = new String();
        this.ivZinsabw = new String();
        this.ivZinsbeg = new String();
        this.ivZinsdat = new String();
        this.ivZinsenddat = new String();
        this.ivZinsfak = new String();
        this.ivZinsperkonv = new String();
        this.ivZinsryth = new String();
        this.ivZinstyp = new String();
        this.ivZinszahlart = new String();
        // Felder hinzugefuegt - 15.11.2012
        this.ivDza = new String();
        this.ivDlza = new String();
        this.ivDnz = new String();
        this.ivDlz = new String();
        this.ivDkv = new String();
        this.ivZbkv = new String();
        // Felder hinzugefuegt - 15.11.2012
    }

    /**
     * @return the andat
     */
    public String getAndat() {
        return this.ivAndat;
    }

    /**
     * @param pvAndat the andat to set
     */
    public void setAndat(String pvAndat) {
        this.ivAndat = pvAndat;
    }

    /**
     * @return the atkonv
     */
    public String getAtkonv() {
        return this.ivAtkonv;
    }

    /**
     * @param pvAtkonv the atkonv to set
     */
    public void setAtkonv(String pvAtkonv) {
        this.ivAtkonv = pvAtkonv;
    }

    /**
     * @return the atkonvmod
     */
    public String getAtkonvmod() {
        return this.ivAtkonvmod;
    }

    /**
     * @param pvAtkonvmod the atkonvmod to set
     */
    public void setAtkonvmod(String pvAtkonvmod) {
        this.ivAtkonvmod = pvAtkonvmod;
    }

    /**
     * @return the atkonvtag
     */
    public String getAtkonvtag() {
        return this.ivAtkonvtag;
    }

    /**
     * @param pvAtkonvtag the atkonvtag to set
     */
    public void setAtkonvtag(String pvAtkonvtag) {
        this.ivAtkonvtag = pvAtkonvtag;
    }

    /**
     * @return the bankkal
     */
    public String getBankkal() {
        return this.ivBankkal;
    }

    /**
     * @param pvBankkal the bankkal to set
     */
    public void setBankkal(String pvBankkal) {
        this.ivBankkal = pvBankkal;
    }

    /**
     * @return the bernom
     */
    public String getBernom() {
        return this.ivBernom;
    }

    /**
     * @param pvBernom the bernom to set
     */
    public void setBernom(String pvBernom) {
        this.ivBernom = pvBernom;
    }

    /**
     * @return the cap
     */
    public String getCap() {
        return this.ivCap;
    }

    /**
     * @param pvCap the cap to set
     */
    public void setCap(String pvCap) {
        this.ivCap = pvCap;
    }

    /**
     * @return the datltztanp
     */
    public String getDatltztanp() {
        return this.ivDatltztanp;
    }

    /**
     * @param pvDatltztanp the datltztanp to set
     */
    public void setDatltztanp(String pvDatltztanp) {
        this.ivDatltztanp = pvDatltztanp;
    }

    /**
     * @return the datltztfix
     */
    public String getDatltztfix() {
        return this.ivDatltztfix;
    }

    /**
     * @param pvDatltztfix the datltztfix to set
     */
    public void setDatltztfix(String pvDatltztfix) {
        this.ivDatltztfix = pvDatltztfix;
    }

    /**
     * @return the datltzttilg
     */
    public String getDatltzttilg() {
        return this.ivDatltzttilg;
    }

    /**
     * @param pvDatltzttilg the datltzttilg to set
     */
    public void setDatltzttilg(String pvDatltzttilg) {
        this.ivDatltzttilg = pvDatltzttilg;
    }

    /**
     * @return the edat
     */
    public String getEdat() {
        return this.ivEdat;
    }

    /**
     * @param pvEdat the edat to set
     */
    public void setEdat(String pvEdat) {
        this.ivEdat = pvEdat;
    }

    /**
     * @return the ekurs
     */
    public String getEkurs() {
        return this.ivEkurs;
    }

    /**
     * @param pvEkurs the ekurs to set
     */
    public void setEkurs(String pvEkurs) {
        this.ivEkurs = pvEkurs;
    }

    /**
     * @return the enddat
     */
    public String getEnddat() {
        return this.ivEnddat;
    }

    /**
     * @param pvEnddat the enddat to set
     */
    public void setEnddat(String pvEnddat) {
        this.ivEnddat = pvEnddat;
    }

    /**
     * @return the faellig
     */
    public String getFaellig() {
        return this.ivFaellig;
    }

    /**
     * @param pvFaellig the faellig to set
     */
    public void setFaellig(String pvFaellig) {
        this.ivFaellig = pvFaellig;
    }

    /**
     * @return the faelligst
     */
    public String getFaelligst() {
        return this.ivFaelligst;
    }

    /**
     * @param pvFaelligst the faelligst to set
     */
    public void setFaelligst(String pvFaelligst) {
        this.ivFaelligst = pvFaelligst;
    }

    /**
     * @return the ferienkonv
     */
    public String getFerienkonv() {
        return this.ivFerienkonv;
    }

    /**
     * @param pvFerienkonv the ferienkonv to set
     */
    public void setFerienkonv(String pvFerienkonv) {
        this.ivFerienkonv = pvFerienkonv;
    }

    /**
     * @return the fixkalart
     */
    public String getFixkalart() {
        return this.ivFixkalart;
    }

    /**
     * @param pvFixkalart the fixkalart to set
     */
    public void setFixkalart(String pvFixkalart) {
        this.ivFixkalart = pvFixkalart;
    }

    /**
     * @return the fixkonv
     */
    public String getFixkonv() {
        return this.ivFixkonv;
    }

    /**
     * @param pvFixkonv the fixkonv to set
     */
    public void setFixkonv(String pvFixkonv) {
        this.ivFixkonv = pvFixkonv;
    }

    /**
     * 
     * @return
     */
    public String getFixrhyth() 
    {
		return ivFixrhyth;
	}

    /**
     * 
     * @param ivFixrhyth
     */
	public void setFixrhyth(String ivFixrhyth) 
	{
		this.ivFixrhyth = ivFixrhyth;
	}

	/**
     * @return the fixtage
     */
    public String getFixtage() {
        return this.ivFixtage;
    }

    /**
     * @param pvFixtage the fixtage to set
     */
    public void setFixtage(String pvFixtage) {
        this.ivFixtage = pvFixtage;
    }

    /**
     * @return the fixtagedir
     */
    public String getFixtagedir() {
        return this.ivFixtagedir;
    }

    /**
     * @param pvFixtagedir the fixtagedir to set
     */
    public void setFixtagedir(String pvFixtagedir) {
        this.ivFixtagedir = pvFixtagedir;
    }

    /**
     * @return the fixtagemod
     */
    public String getFixtagemod() {
        return this.ivFixtagemod;
    }

    /**
     * @param pvFixtagemod the fixtagemod to set
     */
    public void setFixtagemod(String pvFixtagemod) {
        this.ivFixtagemod = pvFixtagemod;
    }

    /**
     * @return the fixzins
     */
    public String getFixzins() {
        return this.ivFixzins;
    }

    /**
     * @param pvFixzins the fixzins to set
     */
    public void setFixzins(String pvFixzins) {
        this.ivFixzins = pvFixzins;
    }

    /**
     * @return the floor
     */
    public String getFloor() {
        return this.ivFloor;
    }

    /**
     * @param pvFloor the floor to set
     */
    public void setFloor(String pvFloor) {
        this.ivFloor = pvFloor;
    }

    /**
     * @return the kalfix
     */
    public String getKalfix() {
        return this.ivKalfix;
    }

    /**
     * @param pvKalfix the kalfix to set
     */
    public void setKalfix(String pvKalfix) {
        this.ivKalfix = pvKalfix;
    }

    /**
     * @return the kalkonv
     */
    public String getKalkonv() {
        return this.ivKalkonv;
    }

    /**
     * @param pvKalkonv the kalkonv to set
     */
    public void setKalkonv(String pvKalkonv) {
        this.ivKalkonv = pvKalkonv;
    }

    /**
     * @return the kondkey
     */
    public String getKondkey() {
        return this.ivKondkey;
    }

    /**
     * @param pvKondkey the kondkey to set
     */
    public void setKondkey(String pvKondkey) {
        this.ivKondkey = pvKondkey;
    }

    /**
     * @return the kupbas
     */
    public String getKupbas() {
        return this.ivKupbas;
    }

    /**
     * @param pvKupbas the kupbas to set
     */
    public void setKupbas(String pvKupbas) {
        this.ivKupbas = pvKupbas;
    }

    /**
     * @return the kupbasodd
     */
    public String getKupbasodd() {
        return this.ivKupbasodd;
    }

    /**
     * @param pvKupbasodd the kupbasodd to set
     */
    public void setKupbasodd(String pvKupbasodd) {
        this.ivKupbasodd = pvKupbasodd;
    }

    /**
     * @return the lfzmon
     */
    public String getLfzmon() {
        return this.ivLfzmon;
    }

    /**
     * @param pvLfzmon the lfzmon to set
     */
    public void setLfzmon(String pvLfzmon) {
        this.ivLfzmon = pvLfzmon;
    }

    /**
     * @return the lrate
     */
    public String getLrate() {
        return this.ivLrate;
    }

    /**
     * @param pvLrate the lrate to set
     */
    public void setLrate(String pvLrate) {
        this.ivLrate = pvLrate;
    }

    /**
     * @return the mantilg
     */
    public String getMantilg() {
        return this.ivMantilg;
    }

    /**
     * @param pvMantilg the mantilg to set
     */
    public void setMantilg(String pvMantilg) {
        this.ivMantilg = pvMantilg;
    }

    /**
     * @return the manzins
     */
    public String getManzins() {
        return this.ivManzins;
    }

    /**
     * @param pvManzins the manzins to set
     */
    public void setManzins(String pvManzins) {
        this.ivManzins = pvManzins;
    }

    /**
     * @return the monendkonv
     */
    public String getMonendkonv() {
        return this.ivMonendkonv;
    }

    /**
     * @param pvMonendkonv the monendkonv to set
     */
    public void setMonendkonv(String pvMonendkonv) {
        this.ivMonendkonv = pvMonendkonv;
    }

    /**
     * @return the nomzins
     */
    public String getNomzins() {
        return this.ivNomzins;
    }

    /**
     * @param pvNomzins the nomzins to set
     */
    public void setNomzins(String pvNomzins) {
        this.ivNomzins = pvNomzins;
    }

    /**
     * @return the raten
     */
    public String getRaten() {
        return this.ivRaten;
    }

    /**
     * @param pvRaten the raten to set
     */
    public void setRaten(String pvRaten) {
        this.ivRaten = pvRaten;
    }

    /**
     * @return the refizins
     */
    public String getRefizins() {
        return this.ivRefizins;
    }

    /**
     * @param pvRefizins the refizins to set
     */
    public void setRefizins(String pvRefizins) {
        this.ivRefizins = pvRefizins;
    }

    /**
     * @return the refzins
     */
    public String getRefzins() {
        return this.ivRefzins;
    }

    /**
     * @param pvRefzins the refzins to set
     */
    public void setRefzins(String pvRefzins) {
        this.ivRefzins = pvRefzins;
    }

    /**
     * @return the refzinstxt
     */
    public String getRefzinstxt() {
        return this.ivRefzinstxt;
    }

    /**
     * @param pvRefzinstxt the refzinstxt to set
     */
    public void setRefzinstxt(String pvRefzinstxt) {
        this.ivRefzinstxt = pvRefzinstxt;
    }

    /**
     * @return the rkapfaell
     */
    public String getRkapfaell() {
        return this.ivRkapfaell;
    }

    /**
     * @param pvRkapfaell the rkapfaell to set
     */
    public void setRkapfaell(String pvRkapfaell) {
        this.ivRkapfaell = pvRkapfaell;
    }

    /**
     * @return the rkurs
     */
    public String getRkurs() {
        return this.ivRkurs;
    }

    /**
     * @param pvRkurs the rkurs to set
     */
    public void setRkurs(String pvRkurs) {
        this.ivRkurs = pvRkurs;
    }

    /**
     * @return the sondtilg
     */
    public String getSondtilg() {
        return this.ivSondtilg;
    }

    /**
     * @param pvSondtilg the sondtilg to set
     */
    public void setSondtilg(String pvSondtilg) {
        this.ivSondtilg = pvSondtilg;
    }

    /**
     * @return the spread
     */
    public String getSpread() {
        return this.ivSpread;
    }

    /**
     * @param pvSpread the spread to set
     */
    public void setSpread(String pvSpread) {
        this.ivSpread = pvSpread;
    }

    /**
     * @return the tilgabw
     */
    public String getTilgabw() {
        return this.ivTilgabw;
    }

    /**
     * @param pvTilgabw the tilgabw to set
     */
    public void setTilgabw(String pvTilgabw) {
        this.ivTilgabw = pvTilgabw;
    }

    /**
     * @return the tilgbeg
     */
    public String getTilgbeg() {
        return this.ivTilgbeg;
    }

    /**
     * @param pvTilgbeg the tilgbeg to set
     */
    public void setTilgbeg(String pvTilgbeg) {
        this.ivTilgbeg = pvTilgbeg;
    }

    /**
     * @return the tilgdat
     */
    public String getTilgdat() {
        return this.ivTilgdat;
    }

    /**
     * @param pvTilgdat the tilgdat to set
     */
    public void setTilgdat(String pvTilgdat) {
        this.ivTilgdat = pvTilgdat;
    }

    /**
     * @return the tilgperkonv
     */
    public String getTilgperkonv() {
        return this.ivTilgperkonv;
    }

    /**
     * @param pvTilgperkonv the tilgperkonv to set
     */
    public void setTilgperkonv(String pvTilgperkonv) {
        this.ivTilgperkonv = pvTilgperkonv;
    }

    /**
     * @return the tilgryth
     */
    public String getTilgryth() {
        return this.ivTilgryth;
    }

    /**
     * @param pvTilgryth the tilgryth to set
     */
    public void setTilgryth(String pvTilgryth) {
        this.ivTilgryth = pvTilgryth;
    }

    /**
     * @return the tilgsatz
     */
    public String getTilgsatz() {
        return this.ivTilgsatz;
    }

    /**
     * @param pvTilgsatz the tilgsatz to set
     */
    public void setTilgsatz(String pvTilgsatz) {
        this.ivTilgsatz = pvTilgsatz;
    }

    /**
     * @return the tilgver
     */
    public String getTilgver() {
        return this.ivTilgver;
    }

    /**
     * @param pvTilgver the tilgver to set
     */
    public void setTilgver(String pvTilgver) {
        this.ivTilgver = pvTilgver;
    }

    /**
     * @return the vfaellig
     */
    public String getVfaellig() {
        return this.ivVfaellig;
    }

    /**
     * @param pvVfaellig the vfaellig to set
     */
    public void setVfaellig(String pvVfaellig) {
        this.ivVfaellig = pvVfaellig;
    }

    /**
     * @return the vztilgdat
     */
    public String getVztilgdat() {
        return this.ivVztilgdat;
    }

    /**
     * @param pvVztilgdat the vtilgdat to set
     */
    public void setVztilgdat(String pvVztilgdat) {
        this.ivVztilgdat = pvVztilgdat;
    }

    /**
     * @return the vzinsdat
     */
    public String getVzinsdat() {
        return this.ivVzinsdat;
    }

    /**
     * @param pvVzinsdat the vzinsdat to set
     */
    public void setVzinsdat(String pvVzinsdat) {
        this.ivVzinsdat = pvVzinsdat;
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
     * @return the zahltyp
     */
    public String getZahltyp() {
        return this.ivZahltyp;
    }

    /**
     * @param pvZahltyp the zahltyp to set
     */
    public void setZahltyp(String pvZahltyp) {
        this.ivZahltyp = pvZahltyp;
    }

    /**
     * @return the zhedge
     */
    public String getZhedge() {
        return this.ivZhedge;
    }

    /**
     * @param pvZhedge the zhedge to set
     */
    public void setZhedge(String pvZhedge) {
        this.ivZhedge = pvZhedge;
    }

    /**
     * @return the zinsabw
     */
    public String getZinsabw() {
        return this.ivZinsabw;
    }

    /**
     * @param pvZinsabw the zinsabw to set
     */
    public void setZinsabw(String pvZinsabw) {
        this.ivZinsabw = pvZinsabw;
    }

    /**
     * @return the zinsbeg
     */
    public String getZinsbeg() {
        return this.ivZinsbeg;
    }

    /**
     * @param pvZinsbeg the zinsbeg to set
     */
    public void setZinsbeg(String pvZinsbeg) {
        this.ivZinsbeg = pvZinsbeg;
    }

    /**
     * @return the zinsdat
     */
    public String getZinsdat() {
        return this.ivZinsdat;
    }

    /**
     * @param pvZinsdat the zinsdat to set
     */
    public void setZinsdat(String pvZinsdat) {
        this.ivZinsdat = pvZinsdat;
    }

    /**
     * @return the zinsenddat
     */
    public String getZinsenddat() {
        return this.ivZinsenddat;
    }

    /**
     * @param pvZinsenddat the zinsenddat to set
     */
    public void setZinsenddat(String pvZinsenddat) {
        this.ivZinsenddat = pvZinsenddat;
    }

    /**
     * @return the zinsfak
     */
    public String getZinsfak() {
        return this.ivZinsfak;
    }

    /**
     * @param pvZinsfak the zinsfak to set
     */
    public void setZinsfak(String pvZinsfak) {
        this.ivZinsfak = pvZinsfak;
    }

    /**
     * @return the zinsperkonv
     */
    public String getZinsperkonv() {
        return this.ivZinsperkonv;
    }

    /**
     * @param pvZinsperkonv the zinsperkonv to set
     */
    public void setZinsperkonv(String pvZinsperkonv) {
        this.ivZinsperkonv = pvZinsperkonv;
    }

    /**
     * @return the zinsryth
     */
    public String getZinsryth() {
        return this.ivZinsryth;
    }

    /**
     * @param pvZinsryth the zinsryth to set
     */
    public void setZinsryth(String pvZinsryth) {
        this.ivZinsryth = pvZinsryth;
    }

    /**
     * @return the zinstyp
     */
    public String getZinstyp() {
        return this.ivZinstyp;
    }

    /**
     * @param pvZinstyp the zinstyp to set
     */
    public void setZinstyp(String pvZinstyp) {
        this.ivZinstyp = pvZinstyp;
    }

    /**
     * @return the zinszahlart
     */
    public String getZinszahlart() {
        return this.ivZinszahlart;
    }

    /**
     * @param pvZinszahlart the zinszahlart to set
     */
    public void setZinszahlart(String pvZinszahlart) {
        this.ivZinszahlart = pvZinszahlart;
    }
 
    /**
     * @return the dza
     */
    public String getDza() {
        return this.ivDza;
    }

    /**
     * @param pvDza the dza to set
     */
    public void setDza(String pvDza) {
        this.ivDza = pvDza;
    }

    /**
     * @return the dlza
     */
    public String getDlza() {
        return this.ivDlza;
    }

    /**
     * @param pvDlza the dlza to set
     */
    public void setDlza(String pvDlza) {
        this.ivDlza = pvDlza;
    }

    /**
     * @return the dnz
     */
    public String getDnz() {
        return this.ivDnz;
    }

    /**
     * @param pvDnz the dnz to set
     */
    public void setDnz(String pvDnz) {
        this.ivDnz = pvDnz;
    }

    /**
     * @return the dlz
     */
    public String getDlz() {
        return this.ivDlz;
    }

    /**
     * @param pvDlz the dlz to set
     */
    public void setDlz(String pvDlz) {
        this.ivDlz = pvDlz;
    }

    /**
     * @return the dkv
     */
    public String getDkv() {
        return this.ivDkv;
    }

    /**
     * @param pvDkv the dkv to set
     */
    public void setDkv(String pvDkv) {
        this.ivDkv = pvDkv;
    }

    /**
     * @return the zbkv
     */
    public String getZbkv() {
        return this.ivZbkv;
    }

    /**
     * @param pvZbkv the zbkv to set
     */
    public void setZbkv(String pvZbkv) {
        this.ivZbkv = pvZbkv;
    }

    /**
     * TXSKonditionenDatenStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:konddaten ");
    }
    
    /**
     * TXSKonditionenDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("kondkey=\"" + this.ivKondkey + "\" ");
        
        if (this.ivAndat.length() > 0)
          lvHelpXML.append("andat=\"" + this.ivAndat + "\" ");

        if (this.ivBernom.length() > 0)
          lvHelpXML.append("bernom=\"" + this.ivBernom + "\" ");
        
        if (this.ivDatltztanp.length() > 0)
          lvHelpXML.append("datltztanp=\"" + this.ivDatltztanp + "\" ");
        
        if (this.ivDatltzttilg.length() > 0)
        	lvHelpXML.append("datltzttilg=\"" + this.ivDatltzttilg + "\" ");
         
        if (this.ivEdat.length() > 0)
        {
        	lvHelpXML.append("edat=\"" + this.ivEdat + "\" ");
        } 
        
        if (this.ivEkurs.length() > 0)
        {
        	lvHelpXML.append("ekurs=\"" + this.ivEkurs + "\" ");
        }
        
        if (this.ivRkurs.length() > 0)
            lvHelpXML.append("rkurs=\"" + this.ivRkurs + "\" ");
        
        if (this.ivEnddat.length() > 0)
            lvHelpXML.append("enddat=\"" + this.ivEnddat + "\" ");
        
        if (this.ivKalkonv.length() > 0)
          lvHelpXML.append("kalkonv=\"" + this.ivKalkonv + "\" ");
        
        if (this.ivKalfix.length() > 0)
            lvHelpXML.append("kalfix=\"" + this.ivKalfix + "\" ");
        
        if (this.ivKupbas.length() > 0)
        {
        	lvHelpXML.append("kupbas=\"" + this.ivKupbas + "\" ");
        }
        
        if (this.ivKupbasodd.length() > 0)
        {
        	lvHelpXML.append("kupbasodd=\"" + this.ivKupbasodd + "\" ");
        }
        
        if (this.ivLrate.length() > 0)
          lvHelpXML.append("lrate=\"" + this.ivLrate + "\" ");
        
        if (this.ivMonendkonv.length() > 0)
          lvHelpXML.append("monendkonv=\"" + this.ivMonendkonv + "\" ");
        
        if (this.ivMantilg.length() > 0)
          lvHelpXML.append("mantilg=\"" + this.ivMantilg + "\" ");
        
        if (this.ivManzins.length() > 0)
          lvHelpXML.append("manzins=\"" + this.ivManzins + "\" ");
        
        if (this.ivNomzins.length() > 0)
          lvHelpXML.append("nomzins=\"" + this.ivNomzins + "\" ");
        
        if (this.ivSpread.length() > 0)
          lvHelpXML.append("spread=\"" + this.ivSpread + "\" ");
        
        if (this.ivCap.length() > 0)
            lvHelpXML.append("cap=\"" + this.ivCap + "\" ");
        
        if (this.ivFloor.length() > 0)
            lvHelpXML.append("floor=\"" + this.ivFloor + "\" ");
        
        if (this.ivTilgryth.length() > 0)
          lvHelpXML.append("tilgryth=\"" + this.ivTilgryth + "\" ");
        
        if (this.ivTilgsatz.length() > 0)
          lvHelpXML.append("tilgsatz=\"" + this.ivTilgsatz + "\" ");
        
        if (this.ivVfaellig.length() > 0)
          lvHelpXML.append("vfaellig=\"" + this.ivVfaellig + "\" ");
        
        if (this.ivFaellig.length() > 0)
            lvHelpXML.append("faellig=\"" + this.ivFaellig + "\" ");
        
        if (this.ivZahltyp.length() > 0)
          lvHelpXML.append("zahltyp=\"" + this.ivZahltyp + "\" ");
        
        if (this.ivZinsabw.length() > 0)
        {
        	lvHelpXML.append("zinsabw=\"" + this.ivZinsabw + "\" ");
        }
        
        if (this.ivZinsperkonv.length() > 0)
        {
        	lvHelpXML.append("zinsperkonv=\"" + this.ivZinsperkonv + "\" ");
        }
        
        if (this.ivZinstyp.length() > 0)
          lvHelpXML.append("zinstyp=\"" + this.ivZinstyp + "\" ");
        
        if (this.ivZinsryth.length() > 0)
          lvHelpXML.append("zinsryth=\"" + this.ivZinsryth + "\" ");
        
        if (this.ivZinsbeg.length() > 0)
          lvHelpXML.append("zinsbeg=\"" + this.ivZinsbeg + "\" ");
        
        if (this.ivZinsdat.length() > 0)
          lvHelpXML.append("zinsdat=\"" + this.ivZinsdat + "\" ");
        
        if (this.ivZinsenddat.length() > 0)
        {
        	lvHelpXML.append("zinsenddat=\"" + this.ivZinsenddat + "\" ");
        }
        
        if (this.ivZinszahlart.length() > 0)
        {
        	lvHelpXML.append("zinszahlart=\"" + this.ivZinszahlart + "\" ");
        }
        
        if (this.ivZinsfak.length() > 0)
            lvHelpXML.append("zinsfak=\"" + this.ivZinsfak + "\" ");
        
        if (this.ivBankkal.length() > 0)
          lvHelpXML.append("bankkal=\"" + this.ivBankkal + "\" ");
        
        if (this.ivAtkonv.length() > 0)
          lvHelpXML.append("atkonv=\"" + this.ivAtkonv + "\" ");
        
        if (this.ivAtkonvtag.length() > 0)
            lvHelpXML.append("atkonvtag=\"" + this.ivAtkonvtag + "\" ");
        
        if (this.ivAtkonvmod.length() > 0)
        {
        	lvHelpXML.append("atkonvmod=\"" + this.ivAtkonvmod + "\" ");
        }
        
        if (this.ivFixtagedir.length() > 0)
            lvHelpXML.append("fixtagedir=\"" + this.ivFixtagedir + "\" ");
            
        if (this.ivFixtage.length() > 0)
            lvHelpXML.append("fixtage=\"" + this.ivFixtage + "\" ");
        
        if (this.ivFixtagemod.length() > 0)
            lvHelpXML.append("fixtagemod=\"" + this.ivFixtagemod + "\" ");
        
        if (this.ivFixkalart.length() > 0)
            lvHelpXML.append("fixkalart=\"" + this.ivFixkalart + "\" ");
        
        if (this.ivFixrhyth.length() > 0)
        	lvHelpXML.append("fixrhyth=\"" + this.ivFixrhyth + "\" ");
        
        if (this.ivRefzins.length() > 0)
            lvHelpXML.append("refzins=\"" + this.ivRefzins + "\" ");

        if (this.ivRefzinstxt.length() > 0)
            lvHelpXML.append("refzinstxt=\"" + this.ivRefzinstxt + "\" ");

        if (this.ivTilgabw.length() > 0)
        	lvHelpXML.append("tilgabw=\"" + this.ivTilgabw + "\" ");
        
        if (this.ivTilgdat.length() > 0)
          lvHelpXML.append("tilgdat=\"" + this.ivTilgdat + "\" ");
 
        if (this.ivTilgbeg.length() > 0)
           lvHelpXML.append("tilgbeg=\"" + this.ivTilgbeg + "\" ");
        
        if (this.ivTilgperkonv.length() > 0)
            lvHelpXML.append("tilgperkonv=\"" + this.ivTilgperkonv + "\" ");
        
        if (this.ivTilgver.length() > 0)
            lvHelpXML.append("tilgver=\"" + this.ivTilgver + "\" ");
        
        if (this.ivFixkonv.length() > 0)
          lvHelpXML.append("fixkonv=\"" + this.ivFixkonv + "\" ");

        if (this.ivDatltztfix.length() > 0)
          lvHelpXML.append("datltztfix=\"" + this.ivDatltztfix + "\" ");
        	
        if (this.ivWhrg.length() > 0)
            lvHelpXML.append("whrg=\"" + this.ivWhrg + "\" ");
        
        // Felder hinzugefuegt - 15.11.2012
        if (this.ivDza.length() > 0)
            lvHelpXML.append("dza=\"" + this.ivDza + "\" ");
        
        if (this.ivDlza.length() > 0)
            lvHelpXML.append("dlza=\"" + this.ivDlza + "\" ");
        
        if (this.ivDnz.length() > 0)
            lvHelpXML.append("dnz=\"" + this.ivDnz + "\" ");
        
        if (this.ivDlz.length() > 0)
            lvHelpXML.append("dlz=\"" + this.ivDlz + "\" ");
        
        if (this.ivDkv.length() > 0)
            lvHelpXML.append("dkv=\"" + this.ivDkv + "\" ");
        
        if (this.ivZbkv.length() > 0)
            lvHelpXML.append("zbkv=\"" + this.ivZbkv + "\" ");        
        // Felder hinzugefuegt - 15.11.2012
        
        lvHelpXML.append(">");

        return lvHelpXML;
     }   

    /**
     * TXSKonditionenDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("</txsi:konddaten>" + StringKonverter.lineSeparator);
    }

    /**
     * Importiert die Darlehensinformationen von DarKa
     * @param pvModus
     * @param pvDarlehen 
     * @param pvLogger 
     * @return 
     */
    public boolean importDarlehen(int pvModus, nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen, Logger pvLogger)
    {     
        String lvNULLDat1 = "00000000";

        String lvEUR = "EUR";
        String lvGBP = "GBP";
        String lvIBO = "IBO";
        String lvLIB = "LIB";
        String lvZERO = "ZERO";
        String lvEONIA = "EONIA";
        
        // Zahlungstyp schon hier .. fuer die Rate wichtig ..... 
        String lvZahltyp = "9";
        if (pvDarlehen.getTilgungsschluesselKTR().equals(" ")) { // Leer => KON.KOTI nutzen
          if (pvDarlehen.getTilgungsschluesselKON().equals("2"))
                lvZahltyp = "2";
          else { // nicht gleich 2
           if (pvDarlehen.getTilgungsschluesselKON().equals("1") || pvDarlehen.getTilgungsschluesselKON().equals("7"))
                    lvZahltyp = "1";
           else { // Nicht 1,2,7 
             if (pvDarlehen.getTilgungsschluesselKON().equals("3") || pvDarlehen.getTilgungsschluesselKON().equals("4") ||
                 pvDarlehen.getTilgungsschluesselKON().equals("5") || pvDarlehen.getTilgungsschluesselKON().equals("8") ||
                 pvDarlehen.getTilgungsschluesselKON().equals("A") || pvDarlehen.getTilgungsschluesselKON().equals("B") ||
                 pvDarlehen.getTilgungsschluesselKON().equals("C") || pvDarlehen.getTilgungsschluesselKON().equals("F") ||
                 pvDarlehen.getTilgungsschluesselKON().equals("G") || pvDarlehen.getTilgungsschluesselKON().equals("H") ||
                 pvDarlehen.getTilgungsschluesselKON().equals("K"))
            	 lvZahltyp = "3";
           } // Nicht 1,2,7 
          } // nicht gleich 2
        } // Leer => KON.KOTI nutzen
        else
        { // KTR.DTI ist nicht leer ... 
         if (pvDarlehen.getTilgungsschluesselKTR().equals("2"))
         {
             lvZahltyp = "2";
         }
         else
         { // nicht gleich 2
          if (pvDarlehen.getTilgungsschluesselKTR().equals("1") || pvDarlehen.getTilgungsschluesselKTR().equals("7"))
          {
              lvZahltyp = "1";
          }
          else
          { // Nicht 1,2,7 
           if (pvDarlehen.getTilgungsschluesselKTR().equals("3") || pvDarlehen.getTilgungsschluesselKTR().equals("4") ||
               pvDarlehen.getTilgungsschluesselKTR().equals("5") || pvDarlehen.getTilgungsschluesselKTR().equals("8") ||
               pvDarlehen.getTilgungsschluesselKTR().equals("A") || pvDarlehen.getTilgungsschluesselKTR().equals("B") ||
               pvDarlehen.getTilgungsschluesselKTR().equals("C") || pvDarlehen.getTilgungsschluesselKTR().equals("F") ||
               pvDarlehen.getTilgungsschluesselKTR().equals("G") || pvDarlehen.getTilgungsschluesselKTR().equals("H") ||
               pvDarlehen.getTilgungsschluesselKTR().equals("K"))
           {
               lvZahltyp = "3";
           }
          } // Nicht 1,2,7 
         } // nicht gleich 2 
        } // KTR.DTI ist nicht leer ...
        
        // Redefinition der Rate vom 22.04.2005 ...
        BigDecimal lvRate = new BigDecimal("0.0");
        if (lvZahltyp.equals("3") || lvZahltyp.equals("9")) { // Endfaellig/Sonder
            lvRate = StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital()); // dRestkapital
        } // Endfaellig/Sonder
        if (lvZahltyp.equals("1")) { // Tilgung
            lvRate = StringKonverter.convertString2BigDecimal(pvDarlehen.getTilgungsbetrag()); // naechste Leistung
        } // Tilgung
        if (lvZahltyp.equals("2")) { // Abzahlung
            lvRate = StringKonverter.convertString2BigDecimal(pvDarlehen.getTilgungsbetrag()); // Tilgungsrate
        } // Abzahlung
        
        // Bzgl. Tilgungsrythmus gibt es nur die Perioden 
        String lvAnzTil = new String();
        lvAnzTil = "13";
        if (pvDarlehen.getTilgungJahr().equals("001"))
        { // Jaehrlich
            lvAnzTil = "12";
        }
        if (pvDarlehen.getTilgungJahr().equals("002"))
        { // Halbjaehrlich
            lvAnzTil = "6";
        }
        if (pvDarlehen.getTilgungJahr().equals("004"))
        { // Vierteljaehrlich
            lvAnzTil = "3";
        }
        if (pvDarlehen.getTilgungJahr().equals("012"))
        { // Monatlich
            lvAnzTil = "1";
        }
        
        // CT 24.07.2012 - Aenderungsanforderung von Herrn Lau
        // Endfaellige Kredite in DarKa (Tilgungsschluessel: 3, 5, 8, H, K)
        // erhalten immer den Wert 'bullet' (Schluessel 0)
        if (pvDarlehen.getTilgungsschluesselKTR().equals(" ")) 
        { // Leer => KON.KOTI nutzen 
          if (pvDarlehen.getTilgungsschluesselKON().equals("3") || pvDarlehen.getTilgungsschluesselKON().equals("5") ||
              pvDarlehen.getTilgungsschluesselKON().equals("8") || pvDarlehen.getTilgungsschluesselKON().equals("H") ||
              pvDarlehen.getTilgungsschluesselKON().equals("K"))
              { // endfaellig -> bullet
                lvAnzTil = "0";  
              }            
        }
        else
        {
          if (pvDarlehen.getTilgungsschluesselKTR().equals("3") || pvDarlehen.getTilgungsschluesselKTR().equals("5") ||
              pvDarlehen.getTilgungsschluesselKTR().equals("8") || pvDarlehen.getTilgungsschluesselKTR().equals("H") ||
              pvDarlehen.getTilgungsschluesselKTR().equals("K"))
              { // endfaellig -> bullet
                lvAnzTil = "0";  
              }
        }
        
        /* Definition Zinstermin ................... */
        /* Definition immer KTR.DLZE, alternativ KTR.DNZ */
        String lvZinsTerm = new String();
        if (pvDarlehen.getLetzterZinstermin().equals(lvNULLDat1) ||
            pvDarlehen.getLetzterZinstermin().isEmpty())
        { /*Nichts verwertbares*/
            lvZinsTerm = pvDarlehen.getNaechsterZinstermin();
        } /*Nichts verwertbares*/
        else
        { /* Letztes Zinsperiodenende o.k. */
            lvZinsTerm = pvDarlehen.getLetzterZinstermin();
        } /* Letztes Zinsperiodenende o.k. */
                
        //System.out.println("Zinsrechnung-Schluessel: " + darlehen.getZinsrechnungSchluessel());
        /* ZinsTyp  ............................ */
        // CT 08.06.2012 - Mapping des Zinstyps ueberarbeitet
        String lvZinstyp = new String();
        //sZinstyp = "undefiniert";
        if (pvDarlehen.getZinsrechnungSchluessel().equals("0") || pvDarlehen.getZinsrechnungSchluessel().equals("1") ||
            pvDarlehen.getZinsrechnungSchluessel().equals("2") || pvDarlehen.getZinsrechnungSchluessel().equals("8") ||
            pvDarlehen.getZinsrechnungSchluessel().equals("9") || pvDarlehen.getZinsrechnungSchluessel().equals("F") ||
            pvDarlehen.getZinsrechnungSchluessel().equals("J"))
            // darlehen.getZinsrechnungSchluessel().equals("Z")) --> Z gibt es nicht mehr - CT 08.06.2012
        { /* Definition 1 */
            lvZinstyp = "1";
        } /* Definition 1 */
        else
        { /* nicht Definition 1 fest => variabel - Erweiterung */
         if (pvDarlehen.getZinsrechnungSchluessel().equals("3") || pvDarlehen.getZinsrechnungSchluessel().equals("4") ||
             pvDarlehen.getZinsrechnungSchluessel().equals("5") || pvDarlehen.getZinsrechnungSchluessel().equals("6") ||
             pvDarlehen.getZinsrechnungSchluessel().equals("7") || pvDarlehen.getZinsrechnungSchluessel().equals("A") ||
             pvDarlehen.getZinsrechnungSchluessel().equals("B") || pvDarlehen.getZinsrechnungSchluessel().equals("C") ||
             pvDarlehen.getZinsrechnungSchluessel().equals("D") || pvDarlehen.getZinsrechnungSchluessel().equals("E") ||
             pvDarlehen.getZinsrechnungSchluessel().equals("G") || pvDarlehen.getZinsrechnungSchluessel().equals("K")) //||
             // Die folgenden Auspraegungen gibt es nicht mehr - CT 08.06.2012
             //darlehen.getZinsrechnungSchluessel().equals("L") || darlehen.getZinsrechnungSchluessel().equals("M") ||
             //darlehen.getZinsrechnungSchluessel().equals("N") || darlehen.getZinsrechnungSchluessel().equals("O") ||
             //darlehen.getZinsrechnungSchluessel().equals("P") || darlehen.getZinsrechnungSchluessel().equals("Q") ||
             //darlehen.getZinsrechnungSchluessel().equals("R") || darlehen.getZinsrechnungSchluessel().equals("S") ||
             //darlehen.getZinsrechnungSchluessel().equals("T") || darlehen.getZinsrechnungSchluessel().equals("U") ||
             //darlehen.getZinsrechnungSchluessel().equals("V") || darlehen.getZinsrechnungSchluessel().equals("W") ||
             //darlehen.getZinsrechnungSchluessel().equals("X"))
         { /* Definition 1 */
                lvZinstyp = "2";
         } /* Definition 1 - variabel */
        } /* nicht Definition 1 fest */

        /* ZinsZahlungsrhythmus ............................ */
        String lvAnzZahl = new String();
        lvAnzZahl = "13";
        if (pvDarlehen.getAnzahlZinsperioden().equals("001"))
        { /* Jaehrlich */
            lvAnzZahl = "12";
        }
        if (pvDarlehen.getAnzahlZinsperioden().equals("002"))
        { /* Halbjaehrlich */
            lvAnzZahl = "6";
        }
        if (pvDarlehen.getAnzahlZinsperioden().equals("004"))
        { /* Vierteljaehrlich */
            lvAnzZahl = "3";
        }
        if (pvDarlehen.getAnzahlZinsperioden().equals("012"))
        { /* Monatlich */
            lvAnzZahl = "1";
        }
        if (lvAnzZahl.equals("13"))
        { /* noch undef. */
         /* Jetzt die Groesse */
         if (pvDarlehen.getGrZinsperiode().equals("330"))
         { /* */
                lvAnzZahl = "11";
         }
         if (pvDarlehen.getGrZinsperiode().equals("300")) /* */
         { /* */
                lvAnzZahl = "10";
         }
         if (pvDarlehen.getGrZinsperiode().equals("270")) /* */
         { /* */
                lvAnzZahl = "9";
         }
         if (pvDarlehen.getGrZinsperiode().equals("240")) /* */
         { /* */
                lvAnzZahl = "8";
         }
         if (pvDarlehen.getGrZinsperiode().equals("210")) /* */
         { /* */
                lvAnzZahl = "7";
         }
         if (pvDarlehen.getGrZinsperiode().equals("150")) /* */
         { /* */
                lvAnzZahl = "5";
         }
         if (pvDarlehen.getGrZinsperiode().equals("120")) /* */
         { /* */
                lvAnzZahl = "4";
         }
        } /* noch undef. */
        
        /* Konventionen ...................*/
        String lvKonven1 = new String();
        lvKonven1 = "0";
        if (pvDarlehen.getFaelligkeitBes().equals("4"))
        {
            lvKonven1 = "2";
        }
        else
        { /* Keine 4 */
         if (pvDarlehen.getFaelligkeitBes().equals("1") || pvDarlehen.getFaelligkeitBes().equals("2") ||
             pvDarlehen.getFaelligkeitBes().equals("5"))
         {
                lvKonven1 = "1";
         }
        } /* Keine 4 */
        
        /* Zinsbeginn ................................................. */
        /* Wenn nicht Letztes Zinsperiodenende, dann naechstes + 1 */
        // CT 18.07.2012 - Zinstermin und Zinsbeginn haben
        // dieselbe Berechnungsmethode
        String lvZinsBeginn = new String();
        lvZinsBeginn = DatumUtilities.addTagOhnePruefung(lvZinsTerm);
        
        /* Referenzzinssatz ........................................... */
        String lvRefZinssatz = new String();
        String lvZwipuh = new String();
        String lvDauer = new String();
        String lvBuffer = new String();
        int lvZBBGZahl = 0;

        //System.out.println("Zinsrechnungsschluessel: " + pvDarlehen.getZinsrechnungSchluessel());
        if (pvDarlehen.getZinsrechnungSchluessel().equals("0") || pvDarlehen.getZinsrechnungSchluessel().equals("1") ||
            pvDarlehen.getZinsrechnungSchluessel().equals("8") || pvDarlehen.getZinsrechnungSchluessel().equals("9"))
        { /*1.*/
            lvRefZinssatz = "keine";
        } /*1.*/
        else
        { /* Alle restlichen ausser K */
         if (!pvDarlehen.getZinsrechnungSchluessel().equals("K"))
         { /* alles Sonderfaelle */
                lvRefZinssatz = "Sonderfall";
         } /* alles Sonderfaelle */
         else
         { /* = K */
          /* Teil 1 */
          //System.out.println(pvDarlehen.getBerechnungsgrundlage().toUpperCase());
          lvZwipuh = pvDarlehen.getBerechnungsgrundlage().toUpperCase();
          /* Anpassungen 2.4 ..................................... */
                if (lvZwipuh.equals("LIBOR"))
          { /* Teil 1 ........ */
                    lvRefZinssatz = "LI" + pvDarlehen.getSatzwaehrung();
                    lvZBBGZahl = -1;
          } /* Teil 1 LIBOR... */
          else
          { /* Nicht LIBOR */
            /* etwas aufwendigere Pruefung ist notwendig, weil die Lieferung */
            /* aus Darlehen von der Definition abweicht. Die Tests zeigen    */
            /* M03EURIBO.. statt M003EURIBO ...............................  */
            /* und weil agens unterschiedlich zusammenbaut ................  */
            /* LIEUR, LIGBP, aber USDLIBOR ................................  */
            /* und ein Woche EURIBOR = SWD, die anderen 1WD ...............  */
                    if (lvZwipuh.startsWith("D") || lvZwipuh.startsWith("W") || lvZwipuh.startsWith("M") || lvZwipuh.startsWith("Y"))
            { /* Teil 2 */
                        lvZBBGZahl = -1;
                        lvBuffer = new String();
             for (int i = 1; i < 4; i++)
             { /* Max. 3 Stellen pruefen */
                            if (Character.isDigit(lvZwipuh.charAt(i))) lvBuffer = lvBuffer + lvZwipuh.charAt(i);
             } /* Max. 3 Stellen pruefen */
             //System.out.println("sBuffer: " + sBuffer);
                        if (!lvBuffer.isEmpty())
             { /* eine Zahl */
                            lvZBBGZahl = StringKonverter.convertString2Int(lvBuffer);
                            if ((lvZBBGZahl > 0) && (lvZBBGZahl < 1000))
              {
                                lvDauer = (new Integer(lvZBBGZahl)).toString();
              }
             } /* eine Zahl */
                        lvBuffer = new String();
             int j = 0;
             for (int i = 1; i < 10; i++ )
             { /* Rest pr�fen */
                            if (!Character.isDigit(lvZwipuh.charAt(i)))
              { /* Keine Zahl */
                                lvBuffer = lvBuffer + lvZwipuh.charAt(i);
               j++;
              } /* Keine Zahl */
             } /* Rest pr�fen */
             //System.out.println("sBuffer: " + sBuffer);
             if( j > 5 )
             { /* Es kann etwas werden */
                            if (lvZwipuh.startsWith("D"))
              { /* Tage */
               /* Kann EDR nicht => keine */
                                lvRefZinssatz = "keine";
                                lvZBBGZahl = 0;
              } /* Tage */
                            if (lvZwipuh.startsWith("W"))
              { /* Wochen */
                                if ((lvZBBGZahl > 0) && (lvZBBGZahl < 4))
               { /* o.k. */
                                    if (lvBuffer.substring(3, 6).equals(lvIBO))
                { /* EURIBOR */
                                        lvRefZinssatz = "EURIBOR";
                                        if (lvZBBGZahl == 1) lvRefZinssatz = lvRefZinssatz + "SWD";
                                        if (lvZBBGZahl == 2) lvRefZinssatz = lvRefZinssatz + "1WD";
                                        if (lvZBBGZahl == 3) lvRefZinssatz = lvRefZinssatz + "2WD";
                } /* EURIBOR */
                else
                { /* kein EURIBOR */
                                        if (lvBuffer.substring(3, 6).equals(lvLIB))
                 { /* LIBOR */
                  /* Je nach W�hrung */
                                            if (lvBuffer.startsWith(lvEUR) || lvBuffer.startsWith(lvGBP))
                  { /* Sonder */
                                                String lvBuffer1 = new String();
                                                lvBuffer1 = lvBuffer.substring(1, 4);
                                                lvRefZinssatz = "LI";
                                                lvRefZinssatz = lvRefZinssatz + lvBuffer1;
                                                lvRefZinssatz = lvRefZinssatz + lvDauer;
                                                lvRefZinssatz = lvRefZinssatz + "WKD";
                  } /* Sonder */
                  else
                  { /* der Rest */
                                                lvRefZinssatz = lvRefZinssatz + pvDarlehen.getSatzwaehrung();
                                                lvRefZinssatz = lvRefZinssatz + "LIBOR";
                                                lvRefZinssatz = lvRefZinssatz + lvDauer;
                                                lvRefZinssatz = lvRefZinssatz + "WKD";
                  } /* der Rest */
                 } /* LIBOR */
                 else
                 { /* kein Libor/Euribor */
                                            lvRefZinssatz = "keine";
                                            lvZBBGZahl = 0;
                 } /* kein Libor/Euribor */
                } /* kein EURIBOR */
               } /* o.k. */
               else
               { /* n.o.k. */
                                    lvRefZinssatz = "keine";
                                    lvZBBGZahl = 0;
               } /* n.o.k. */
              } /* Wochen */
                            if (lvZwipuh.startsWith("M"))
              { /* Monate */
                                if ((lvZBBGZahl > 0) && (lvZBBGZahl < 13))
               { /* o.k. */
                //System.out.println("sBuffer: " + sBuffer.substring(3,6));   
                                    if (lvBuffer.substring(3, 6).equals(lvIBO))
                { /* EURIBOR */
                                        if (lvZBBGZahl == 12)
                                            lvRefZinssatz = "EURIBOR1YMD";
                 else
                 { /* Sonderfall �ber Sonderfall */
                                            lvRefZinssatz = "EURIBOR";
                                            lvRefZinssatz = lvRefZinssatz + lvDauer;
                                            lvRefZinssatz = lvRefZinssatz + "MD";
                 } /* Sonderfall �ber Sonderfall */
                } /* EURIBOR */
                else
                { /* kein EURIBOR */
                                        if (lvBuffer.substring(3, 6).equals(lvLIB))
                 { /* LIBOR */
                  /* Je nach W�hrung */
                                            if (lvBuffer.startsWith(lvEUR) || lvBuffer.startsWith(lvGBP))
                  { /* Sonder */
                                                String lvBuffer1 = new String();
                                                lvBuffer1 = lvBuffer.substring(0, 3);
                   //System.out.println("sBuffer1: " + sBuffer1);
                                                lvRefZinssatz = "LI";
                                                lvRefZinssatz = lvRefZinssatz + lvBuffer1;
                                                if (lvZBBGZahl == 12)
                                                    lvRefZinssatz = lvRefZinssatz + "1YD";
                   else
                   { /* Sonderfall */
                                                    lvRefZinssatz = lvRefZinssatz + lvDauer;
                                                    lvRefZinssatz = lvRefZinssatz + "MD";
                   } /* Sonderfall */
                  } /* Sonder */
                  else
                  { /* der Rest */
                                                lvRefZinssatz = lvRefZinssatz + pvDarlehen.getSatzwaehrung();
                                                lvRefZinssatz = lvRefZinssatz + "LIBOR";
                                                lvRefZinssatz = lvRefZinssatz + lvDauer;
                                                lvRefZinssatz = lvRefZinssatz + "MD";
                  } /* der Rest */
                 } /* LIBOR */
                 else { /* kein Libor/Euribor */
                                            lvRefZinssatz = "keine";
                                            lvZBBGZahl = 0;
                 } /* kein Libor/Euribor */
                } /* kein EURIBOR */
               } /* o.k. */
               else
               { /* n.o.k. */
                                    lvRefZinssatz = "keine";
                                    lvZBBGZahl = 0;
               } /* n.o.k. */
              } /* Monate */
                            if (lvZwipuh.startsWith("Y"))
              { /* Jahre */
                                if (lvZBBGZahl > 0)
               { /* o.k. */
                                    if (lvBuffer.substring(3, 6).equals(lvIBO))
                { /* EURIBOR */
                                        if (lvZBBGZahl == 1)
                                            lvRefZinssatz = "EURIBOR1YMD";
                 else
                 { /* Sonderfall �ber Sonderfall */
                                            lvRefZinssatz = "EURIBOR";
                                            lvRefZinssatz = lvRefZinssatz + lvDauer;
                                            lvRefZinssatz = lvRefZinssatz + "YD";
                 } /* Sonderfall �ber Sonderfall */
                } /* EURIBOR */
                else
                { /* kein EURIBOR */
                                        if (lvBuffer.substring(3, 6).equals(lvLIB))
                 { /* LIBOR */
                  /* Je nach W�hrung */
                                            if (lvBuffer.startsWith(lvEUR) || lvBuffer.startsWith(lvGBP))
                  { /* Sonder */
                                                String lvBuffer1 = new String();
                                                lvBuffer1 = lvBuffer.substring(0, 4);
                                                lvRefZinssatz = "LI";
                                                lvRefZinssatz = lvRefZinssatz + lvBuffer1;
                                                lvRefZinssatz = lvRefZinssatz + lvDauer;
                                                lvRefZinssatz = lvRefZinssatz + "YD";
                  } /* Sonder */
                  else
                  { /* der Rest */
                                                lvRefZinssatz = lvRefZinssatz + pvDarlehen.getSatzwaehrung();
                                                lvRefZinssatz = lvRefZinssatz + "LIBOR";
                                                if (lvZBBGZahl == 1)
                                                    lvRefZinssatz = lvRefZinssatz + "12MD";
                   else
                   { /* Normalfall */
                                                    lvRefZinssatz = lvRefZinssatz + lvDauer;
                                                    lvRefZinssatz = lvRefZinssatz + "YD";
                   } /* Normalfall */
                  } /* der Rest */
                 } /* LIBOR */
                 else
                 { /* kein Libor/Euribor */
                                            lvRefZinssatz = "keine";
                                            lvZBBGZahl = 0;
                 } /* kein Libor/Euribor */
                } /* kein EURIBOR */
               } /* o.k. */
               else
               { /* n.o.k. */
                                    lvRefZinssatz = "keine";
                                    lvZBBGZahl = 0;
               } /* n.o.k. */
              } /* Jahre */
             } /* Es kann etwas werden */
             else
             { /* geht nicht */
                            lvRefZinssatz = "keine";
                            lvZBBGZahl = 0;
             } /* geht nicht */
            } /* Teil 2 */
            else
            { /* Rest */
                        lvRefZinssatz = lvZwipuh;
                        lvZBBGZahl = -1;
            } /* Rest */
          } /* Nicht LIBOR */
          /* Teil 2 Laufzeit */
          /* sFITD.cReModSchl geplant, aber Inhalt entspricht DZR ! */
          /* sFITD.lLaufztZsAnp Laufzeit Zinsanpassung aus DLZ .....*/
          /* Es sollen aber Monate sein ............................ */
          // CT 20.12.2011 - Wenn leer, dann 'keine'
          //System.out.println("lvRefZinssatz: " + lvRefZinssatz + "...");
          lvRefZinssatz = lvRefZinssatz.trim();
          if (lvRefZinssatz.isEmpty())
          {
              //System.out.println("Leer...");
              lvZBBGZahl = 0;
              lvRefZinssatz = "keine";
          }
          
          if (lvZBBGZahl == -1)
          { /* Keine Laufzeit bisher definiert */
           if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 2)
           {
               lvRefZinssatz = lvRefZinssatz + "1MD";
           }
           else
           {
            if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 3)
            {
                lvRefZinssatz = lvRefZinssatz + "2MD";
            }
            else
            {
             if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 4)
             {
                 lvRefZinssatz = lvRefZinssatz + "3MD";
             }
             else
             {
              if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 5)
              {
                  lvRefZinssatz = lvRefZinssatz + "4MD";
              }
              else
              {
               if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 6)
               {
                   lvRefZinssatz = lvRefZinssatz + "5MD";
               }
               else
               {
                if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 7)
                {
                    lvRefZinssatz = lvRefZinssatz + "6MD";
                }
                else
                {
                 if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 8)
                 {
                     lvRefZinssatz = lvRefZinssatz + "7MD";
                 }
                 else
                 {
                  if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 9)
                  {
                      lvRefZinssatz = lvRefZinssatz + "8MD";
                  }
                  else
                  {
                   if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 10)
                   {
                       lvRefZinssatz = lvRefZinssatz + "9MD";
                   }
                   else
                   {
                    if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 11)
                    {
                        lvRefZinssatz = lvRefZinssatz + "10MD";
                    }
                    else
                    {
                     if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 12)
                     {
                         lvRefZinssatz = lvRefZinssatz + "11MD";
                     }
                     else
                     {
                      if (StringKonverter.convertString2Double(pvDarlehen.getLaufzeitZinsanpassung()) < 23)
                      {
                          lvRefZinssatz = lvRefZinssatz + "1YD";
                      }
                      else
                      {
                          lvRefZinssatz = "keine";
                      }
                     }
                    }
                   }
                  }
                 }
                }
               }
              }
             }
            }
           }
          } /* Keine Laufzeit bisher definiert */
         } /* = K */
        } /* Alle restlichen ausser K */

        /* Version vom 29.11.2007 Refzins="ZERO...", dann "keine"*/
        if (lvRefZinssatz.startsWith(lvZERO))
        { /* ZERO - kennt EDR nicht */
         System.out.println("ZERO zurueckgesetzt<" + pvDarlehen.getKontonummer() + ">");
            lvRefZinssatz = "keine";
        } /* ZERO - kennt EDR nicht */

        /* Version vom 16.03.2009 Refzins="EONIA...", dann "sEONIA" ohne LFZ */
        if (lvRefZinssatz.startsWith(lvEONIA))
        { /* EONIA */
         System.out.println("EONIA zurueckgesetzt<" + pvDarlehen.getKontonummer() + ">");
            lvRefZinssatz = lvEONIA;
        } /* EONIA - ohne Laufzeiten */

        // Sonderkonvention TXS - Referenzzins - CT 23.10.2013
        // EURLIBOR -> LIEUR
        // GBPLIBOR -> LIGBP
        if (lvRefZinssatz.contains("EURLIBOR"))
        {
          lvRefZinssatz = lvRefZinssatz.replace("EURLIBOR", "LIEUR");
        }
        if (lvRefZinssatz.contains("GBPLIBOR"))
        {
          lvRefZinssatz = lvRefZinssatz.replace("GBPLIBOR", "LIGBP");
        }

        if (pvModus == DarlehenVerarbeiten.KEV)
        {
          ivFixrhyth = lvDauer;
        }

        /* Kalenderkonventionen ....................... */
        String lvKalKonv = new String();
        lvKalKonv = "keine";

        if (pvDarlehen.getMaLeReSchluessel().equals("9"))
        { /* 0. Def. */
            lvKalKonv = "act_act_isma";
        } /* 0. Def. */
        else
        { /* nicht 9 */
         if (pvDarlehen.getMaLeReSchluessel().equals("8"))
         { /*1. Def*/
                lvKalKonv = "act_act";
         } /*1. Def */
         else
         { /* Nicht 8/9 */
          if (pvDarlehen.getMaLeReSchluessel().equals("A") ||
              pvDarlehen.getMaLeReSchluessel().equals("B"))
          { /*2. Def. */
                    lvKalKonv = "act_leap";
          } /*2. Def. */
          else
          { /* Nicht 8/9/A/B */
           if (pvDarlehen.getMaLeReSchluessel().equals(" ") ||
               pvDarlehen.getMaLeReSchluessel().equals("6") ||
               pvDarlehen.getMaLeReSchluessel().equals("7"))
           { /*3. Def. */
                        lvKalKonv = "30_360";
           } /*3. Def. */
           else
           { /* Nicht  /6/7/8/9/A/B */
            if (pvDarlehen.getMaLeReSchluessel().equals("3") ||
                pvDarlehen.getMaLeReSchluessel().equals("5"))
            { /*4. Def. */
                            lvKalKonv = "act_365";
            } /*4. Def. */
            else
            { /* Nicht  /3/5/6/7/8/9/A/B */
             if (pvDarlehen.getMaLeReSchluessel().equals("1") ||
                 pvDarlehen.getMaLeReSchluessel().equals("4"))
             { /*5. Def. */
                                lvKalKonv = "act_360";
             } /*5. Def. */
            } /* Nicht  /3/5/6/7/8/9/A/B */
           } /* Nicht  /6/7/8/9/A/B */
          } /* Nicht 8/9/A/B */
         } /* Nicht 8/9 */
        } /* nicht 9 */
                
        // Muss noch getestet werden - CT 24.10.2012
        // Faelligkeitsdatum ...............................
        // Rollover - Faelligkeit haengt nur vom Tilgungsschluessel ab
        String lvTilg = " ";
        if (pvDarlehen.getTilgungsschluesselKTR().trim().isEmpty())
        { // Leer => KON.KOTI nutzen 
          lvTilg = pvDarlehen.getTilgungsschluesselKON();
        } // Leer => KON.KOTI nutzen 
        else
        { // Nichtleer ..
          lvTilg = pvDarlehen.getTilgungsschluesselKTR();
        } // Nichtleer .. 
        //System.out.println("KON: " + darlehen.getTilgungsschluesselKON());
        //System.out.println("KTR: " + darlehen.getTilgungsschluesselKTR());
        //System.out.println("cTilg: " + cTilg);
        
        //System.out.println("Kennzeichen-Rollover: " + pvDarlehen.getKennzeichenRollover());
        String lvFDATUM = new String();
        if (pvDarlehen.getKennzeichenRollover().equals("F") ||
            pvDarlehen.getKennzeichenRollover().equals("V"))
        { // Rollover und FEST oder VARIABEL
            lvZinstyp = "2"; // Generell aus EDR-Sicht ! 
           if (lvTilg.equals("3") || lvTilg.equals("H") ||
               lvTilg.equals("K"))
           { // Festdarlehen .. und andere
             lvFDATUM = pvDarlehen.getTilgungsbeginn();
           } // Festdarlehen .. und andere 
           else
           { // Annuitaeten .. und andere
             lvFDATUM = pvDarlehen.getVertragBis();
           } // Annuitaeten .. und andere
           pvLogger.info("Konto<" + pvDarlehen.getKontonummer() + "> Rollover => Faelligkeit<" + lvFDATUM + ">");
        } // Rollover und FEST oder VARIABEL 
        else
        {
            // ZinsrechnungSchluessel --> 0,1,2,8,9,F,J,K
            if (pvDarlehen.getZinsrechnungSchluessel().equals("0") || pvDarlehen.getZinsrechnungSchluessel().equals("1") ||
                pvDarlehen.getZinsrechnungSchluessel().equals("2") || pvDarlehen.getZinsrechnungSchluessel().equals("8") ||
                pvDarlehen.getZinsrechnungSchluessel().equals("9") || pvDarlehen.getZinsrechnungSchluessel().equals("F") ||
                pvDarlehen.getZinsrechnungSchluessel().equals("J") || pvDarlehen.getZinsrechnungSchluessel().equals("K")) 
            {
                lvFDATUM = pvDarlehen.getZinsanpassungDatum();
                if (lvFDATUM.equals(lvNULLDat1))
                { // Leer
                    // Tilgungsschluessel --> 3,5,8,H,K
                    if (lvTilg.equals("3") || lvTilg.equals("H") ||
                        lvTilg.equals("K"))
                    {
                        lvFDATUM = pvDarlehen.getTilgungsbeginn();
                    }
                    else
                    {
                        lvFDATUM = pvDarlehen.getVertragBis();
                    }
                } // Leer
            }
            //System.out.println("lvFDATUM: " + lvFDATUM);
            //System.out.println("Tilgungsbeginn: " + pvDarlehen.getTilgungsbeginn());
            //System.out.println("VertragBis: " + pvDarlehen.getVertragBis());
            //System.out.println("Zinsanpassung: " + pvDarlehen.getZinsanpassungDatum());
            
            // Sondervariante Darlehen mit variabler Verzinsung - taegl. faellig
            // ZinsrechnungSchluessel --> 3,4,5,6,7,A,B,C,D,E,G
            if (pvDarlehen.getZinsrechnungSchluessel().equals("3") || pvDarlehen.getZinsrechnungSchluessel().equals("4") ||
                pvDarlehen.getZinsrechnungSchluessel().equals("5") || pvDarlehen.getZinsrechnungSchluessel().equals("6") ||
                pvDarlehen.getZinsrechnungSchluessel().equals("7") || pvDarlehen.getZinsrechnungSchluessel().equals("A") || 
                pvDarlehen.getZinsrechnungSchluessel().equals("B") || pvDarlehen.getZinsrechnungSchluessel().equals("C") || 
                pvDarlehen.getZinsrechnungSchluessel().equals("D") || pvDarlehen.getZinsrechnungSchluessel().equals("E") ||
                pvDarlehen.getZinsrechnungSchluessel().equals("G"))
            { 
                 lvFDATUM = pvDarlehen.getBuchungsdatumPlus2();
            } 
            //System.out.println("lvFDATUM: " + lvFDATUM);
        }
        
        // Tilgungstermin      
        String lvTilgTerm = pvDarlehen.getTilgungstermin();
        if (lvZahltyp.equals("3") || lvZahltyp.equals("9"))
        { // Endfaellig/Sonder
          lvTilgTerm = pvDarlehen.getTilgungsbeginn();
          if (pvDarlehen.getTilgungsbeginn().equals(lvNULLDat1) ||
              pvDarlehen.getTilgungsbeginn().isEmpty())
         { // Nichts verwertbares
           lvTilgTerm = pvDarlehen.getZinsanpassungDatum();
         } // Nichts verwertbares
        } // Endfaellig/Sonder
        
        // Rechenkonstante
        BigDecimal lvBtrDivHd = new BigDecimal("0.01");
        
        /* Definition der Berechnungsnominale Version 003.00......... */
        BigDecimal lvBereNominal = StringKonverter.convertString2BigDecimal(pvDarlehen.getBerechnungsnominale());
        
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
        
        // BLB - Buergschaftsprozentsatz nicht verwenden, wenn der Deckungsschl�ssel 'S' oder 'V' und das Produkt '0802' ist
        if (pvDarlehen.getInstitutsnummer().equals("004"))
        {
            if ((pvDarlehen.getDeckungsschluessel().equals("S") || pvDarlehen.getDeckungsschluessel().equals("V")) && pvDarlehen.getProduktSchluessel().equals("00802"))
            {
                // Buergen-Faktor wieder auf '1' setzen
                lvBuerge_Fakt = new BigDecimal("1.0");
            }
        }
        
        // NLB - Buergschaftsprozentsatz nicht verwenden, wenn der Deckungsschl�ssel 'S' oder 'V' ist
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
            if (pvDarlehen.getDeckungsschluessel().equals("S") || pvDarlehen.getDeckungsschluessel().equals("V"))
            {
                // Buergen-Faktor wieder auf '1' setzen
                lvBuerge_Fakt = new BigDecimal("1.0");
            }
        }        
        
        /* Konsortiale ..... Summe der anderen, nur bei korrektem Schl.... */
        if (StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) > 0 &&
                StringKonverter.convertString2Int(pvDarlehen.getKompensationsschluessel()) < 20)
        { /* Bedeutet Kons.*/
         if (StringKonverter.convertString2Double(pvDarlehen.getUrsprungsKapital()) != 0 )
         { /* Anteil rechnen */
          lvWork = StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital()).subtract(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeUKAP()));
          lvKonEigenUKAP_Fakt = lvBuerge_Fakt.multiply((lvWork.divide(StringKonverter.convertString2BigDecimal(pvDarlehen.getUrsprungsKapital()), 9, RoundingMode.HALF_UP))); /* Max. 1 */
         } /* Anteil rechnen */
         if (StringKonverter.convertString2Double(pvDarlehen.getRestkapital()) != 0 )
         { /* Anteil rechnen */
          lvWork = StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital()).subtract(StringKonverter.convertString2BigDecimal(pvDarlehen.getSummeRKAP()));
          lvKonEigenRKAP_Fakt = lvBuerge_Fakt.multiply((lvWork.divide(StringKonverter.convertString2BigDecimal(pvDarlehen.getRestkapital()), 9, RoundingMode.HALF_UP))); /* Max. 1 */
         } /* Anteil rechnen */
        } /* Bedeutet Kons.*/
        else
        { /* Nicht konsortial */
         lvKonEigenUKAP_Fakt = lvBuerge_Fakt;
         lvKonEigenRKAP_Fakt = lvBuerge_Fakt;
        } /* Nicht konsortial */

        /* dKonEigenUKAP_Fakt wird vom dKonEigenRKAP_Fakt gesetzt ! */
        lvKonEigenUKAP_Fakt = lvKonEigenRKAP_Fakt;

        lvRate = lvRate.multiply(lvKonEigenRKAP_Fakt);
        lvBereNominal = lvBereNominal.multiply(lvKonEigenUKAP_Fakt);

        this.ivKondkey = "1";
                
        //String helpInteger;
        long lvValue = 0;
        String lvHelpDatum = pvDarlehen.getAnpassungstermin();
        //System.out.println("Datum: " + helpDatum);
        lvValue = StringKonverter.convertString2Long(lvHelpDatum);
        
        if (lvValue < StringKonverter.convertString2Long(pvDarlehen.getBuchungsdatum()))
        {
          this.ivDatltztanp = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvDarlehen.getAnpassungstermin()));
          //System.out.println("Datum: " + this.datltztanp);
        }
         
        this.ivKalkonv = lvKalKonv;
        this.ivKalfix = "DE";
        this.ivLrate = lvRate.toString();
        this.ivMonendkonv = "1";
        this.ivMantilg = "1";
        // Bei Rollover muessen die Zinsen berechnet werden
        if (!pvDarlehen.getKennzeichenRollover().equals("F") &&
            !pvDarlehen.getKennzeichenRollover().equals("V"))
        {
          this.ivManzins = "1";
        }
        else
        {
          this.ivManzins = "0";
          // Sonderregel aufgrund negativer Zinsen hinzugefuegt - CT 09.03.2017
          if (pvDarlehen.getKontonummer().equals("2583830359") || pvDarlehen.getKontonummer().equals("2666650163") ||
        	  pvDarlehen.getKontonummer().equals("2666650179") || pvDarlehen.getKontonummer().equals("2583830306") ||
        	  pvDarlehen.getKontonummer().equals("2583830359") || pvDarlehen.getKontonummer().equals("2583830362") ||
        	  pvDarlehen.getKontonummer().equals("2583830321"))
          {
        	  this.ivManzins = "1";
          }
        }
        this.ivNomzins = pvDarlehen.getDarlehenszinssatzProzent();
        this.ivSpread = pvDarlehen.getRisikoaufschlagProzent();
        //this.ivCap = pvDarlehen.getCap();
        //this.ivFloor = pvDarlehen.getFloor();
        this.ivTilgryth = lvAnzTil;
        this.ivTilgsatz = pvDarlehen.getTilgungProzent();
        this.ivVfaellig = pvDarlehen.getZinsVorFTage();
        
        // CT 18.11.2011
        // Wenn Faelligkeit < Buchungstag + 1, dann Buchungstag + 1 verwenden
        //System.out.println("lvFDATUM: " + lvFDATUM);
        lvValue = StringKonverter.convertString2Long(lvFDATUM);
        
        // Altes Mapping - CT 11.10.2012
        /*
        if (value < StringKonverter.convertString2Long(darlehen.getBuchungsdatum()) 
                || sFDATUM.equals("20991231"))
            {
                sFDATUM = darlehen.getVertragBis();
            }
        // Altes Mapping - CT 11.10.2012
        */
        
        // Neues Mapping - CT 11.10.2012
        if (lvValue < StringKonverter.convertString2Long(DatumUtilities.addTagOhnePruefung(pvDarlehen.getBuchungsdatum())) || 
            lvFDATUM.equals("20991231"))
        {
            lvFDATUM = pvDarlehen.getBuchungsdatumPlus2();
            //DatumUtilities..addTagOhnePruefung(darlehen.getBuchungsdatum());
        }
        // Neues Mapping - CT 11.10.2012
        
        this.ivFaellig = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvFDATUM));
        // CT 07.12.2011 - Kann nicht angeliefert werden, da das Datum '00000000' ist 
        //this.datltztanp = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(darlehen.getZinsanpassungDatum()));
        this.ivWhrg = pvDarlehen.getSatzwaehrung();
        this.ivZahltyp = lvZahltyp;
        this.ivZinstyp = lvZinstyp;
        this.ivZinsryth = lvAnzZahl;
        this.ivZinsbeg = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvZinsBeginn));
        this.ivZinsdat = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvZinsTerm));
        this.ivBankkal = "DE";
        this.ivAtkonv = lvKonven1;
        this.ivAtkonvtag = "0";
        this.ivFixtagedir = lvKonven1;
        this.ivFixtage = "2";
        this.ivFixtagemod = "0";
        this.ivFixkalart = "1";
        this.ivRefzins = lvRefZinssatz;
        // CT 27.05.2019 - Duplizierung des Referenzzins in 'ivRefzinstxt'
        this.ivRefzinstxt = this.ivRefzins;
        this.ivTilgdat = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvTilgTerm));
        this.ivTilgbeg = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvDarlehen.getTilgungsbeginn()));
        this.ivTilgver = "0";
        this.ivFixkonv = "1";
        this.ivBernom = lvBereNominal.toString();
        
        // Felder hinzugefuegt - 15.11.2012
        this.ivDza = DatumUtilities.changeDatePoints(pvDarlehen.getZinsanpassungDatum());
        this.ivDlza = DatumUtilities.changeDatePoints(pvDarlehen.getLetzteZinsanpassung());  
        this.ivDnz = DatumUtilities.changeDatePoints(pvDarlehen.getNaechsterZinstermin());
        this.ivDlz = pvDarlehen.getLaufzeitZinsanpassung();
        this.ivDkv = DatumUtilities.changeDatePoints(pvDarlehen.getDatumKonditionierung());
        this.ivZbkv = DatumUtilities.changeDatePoints(pvDarlehen.getKondDatum());
        // Felder hinzugefuegt - 15.11.2012
        
        // CT - Feld 'enddat' hinzugefuegt - 16.04.2013
        long lvFaelligkeitsdatum = StringKonverter.convertString2Long(lvFDATUM);
        long lvVertragBis = StringKonverter.convertString2Long(pvDarlehen.getVertragBis());
        //System.out.println(lvFaelligkeitsdatum + " " + lvVertragBis);
        if (lvFaelligkeitsdatum > lvVertragBis)
        {
        	this.ivEnddat = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvFDATUM));
        }
        else
        {
            this.ivEnddat = DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvDarlehen.getVertragBis()));            
        }
        // CT 10.08.2015 - Datum '31.12.2099' fuer die NLB nicht anliefern
        if (pvDarlehen.getInstitutsnummer().equals("009"))
        {
            if (this.ivEnddat.equals("2099-12-31"))
            {
                this.ivEnddat = new String();
                pvLogger.info("Endedatum 2099-12-31;" + pvDarlehen.getKontonummer());
            }
        }
        return true;
    }
    
    /**
     * Importiert die Darlehensinformationen von LoanIQ
     * @param pvDarlehenBlock
     * @param pvVorlaufsatz
     * @param pvLogger log4j-Logger
     * @return 
     */
    public boolean importLoanIQ(DarlehenBlock pvDarlehenBlock, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
    	Darlehen lvHelpDarlehen = pvDarlehenBlock.getDarlehenNetto();
    	
        // Buergschaftprozent
        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
    	BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(lvHelpDarlehen.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);
    			
        String lvZinsrhythmusLoanIQ = MappingLoanIQ.changeZinsrythmus(lvHelpDarlehen.getZinszahlungsrythmus(), pvLogger);
        this.ivAtkonv = MappingLoanIQ.changeArbeitskonvention(lvHelpDarlehen.getArbeitskonvention(), pvLogger);
        this.ivAtkonvtag = "0";
        this.ivBankkal = "DE";
        
       	if (lvBuergschaftprozent.doubleValue() > 0.0 && lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		this.ivBernom = (StringKonverter.convertString2BigDecimal(lvHelpDarlehen.getBerechnungsnominale())).multiply(lvBuergschaftprozent).toPlainString();
        }
    	else
    	{
    		this.ivBernom = lvHelpDarlehen.getBerechnungsnominale();
        }  
        this.ivDatltztanp = DatumUtilities.changeDate(lvHelpDarlehen.getLetzteZinsanpassung());
        this.ivDkv = lvHelpDarlehen.getDatumKonditionierung();
        if (!lvHelpDarlehen.getLaufzeitZinsanpassung().equals("99Y"))
        {
          this.ivDlz = lvHelpDarlehen.getLaufzeitZinsanpassung();
        }
        this.ivDlza = lvHelpDarlehen.getLetzteZinsanpassung();
        this.ivDnz = lvHelpDarlehen.getZinsperiodenende();
        this.ivDza = lvHelpDarlehen.getNaechsteZinsanpassung();
        this.ivEnddat = DatumUtilities.changeDate(lvHelpDarlehen.getLaufzeitende());
        this.ivDatltzttilg = DatumUtilities.changeDate(lvHelpDarlehen.getLaufzeitende());
        
        String lvFaelligkeit = lvHelpDarlehen.getFaelligkeit();
        
        if (lvFaelligkeit.equals("31.12.2099"))
        {
            lvFaelligkeit = DatumUtilities.changeDatePoints(DatumUtilities.berechneBuchungstagPlus2(pvVorlaufsatz.getBuchungsdatum()));
            this.ivEnddat = DatumUtilities.changeDate(lvFaelligkeit);
            pvLogger.info("Konto " + pvDarlehenBlock.getKontonummer() + ": 31.12.2099 -> " + lvFaelligkeit);
        }
        
        this.ivFaellig = DatumUtilities.changeDate(lvFaelligkeit);
        this.ivFixkalart = "1";
        this.ivFixkonv = "1";
        this.ivFixtage = lvHelpDarlehen.getVorfaelligkeitstage();
        
        this.ivKalfix = "DE";       
        this.ivKalkonv = lvHelpDarlehen.getKalenderkonvention();
        this.ivKondkey = "1";
        
       	if (lvBuergschaftprozent.doubleValue() > 0.0 && lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		this.ivLrate = (StringKonverter.convertString2BigDecimal(lvHelpDarlehen.getRateFaelligkeit())).multiply(lvBuergschaftprozent).toPlainString();
        }
    	else
    	{
    		this.ivLrate = lvHelpDarlehen.getRateFaelligkeit();
        }
 
        this.ivMantilg = "1";
        this.ivManzins = "1";
        // Wenn der Zinssatz == '0.0' ist, dann werden auch keine Zins-Cashflows angeliefert - CT 25.10.2018
        if (StringKonverter.convertString2Double(lvHelpDarlehen.getNominalzinssatz()) == 0.0)
        {
        	this.ivManzins = "0";
        }
        
        this.ivMonendkonv = "1";
        this.ivNomzins = lvHelpDarlehen.getNominalzinssatz();
        this.ivRefzins = MappingLoanIQ.changeReferenzzins(lvHelpDarlehen.getReferenzzins(), lvHelpDarlehen
            .getLaufzeitZinsanpassung(), pvLogger);
        // CT 27.05.2019 - Duplizierung des Referenzzins in 'ivRefzinstxt'
        this.ivRefzinstxt = this.ivRefzins;

        this.ivSpread = lvHelpDarlehen.getSpread();

        this.ivTilgbeg = DatumUtilities.changeDate(lvHelpDarlehen.getTilgungsbeginn());
        this.ivTilgdat = DatumUtilities.changeDate(lvHelpDarlehen.getTilgungstermin());
        //this.ivTilgryth = MappingMIDAS.changeZinsrhythmusTilgungsrhythmus(lvHelpDarlehen.getLaufzeitZinsanpassung());
        this.ivTilgsatz = lvHelpDarlehen.getTilgungsprozentsatz();
        this.ivTilgver = "0";
        this.ivVfaellig = lvHelpDarlehen.getVorfaelligkeitstage();
        this.ivWhrg = lvHelpDarlehen.getBetragwaehrung();
        this.ivZahltyp = lvHelpDarlehen.getTilgungsart();
        if (lvHelpDarlehen.getTilgungsart().equals("7"))
        {
        	this.ivZahltyp = "1";
        }
 
        this.ivZbkv = lvHelpDarlehen.getKonditionierungVon();
        this.ivZinsbeg = DatumUtilities.changeDate(lvHelpDarlehen.getBeginnErsteZinsperiode());
        //System.out.println("Zinstermin: " + lvHelpDarlehen.getZinstermin());
        //System.out.println("Zinsperiodenende: " + lvHelpDarlehen.getZinsperiodenende());
        //System.out.println("Naechste Zinsanpassung: " + lvHelpDarlehen.getNaechsteZinsanpassung());
        this.ivZinsdat = DatumUtilities.changeDate(lvHelpDarlehen.getZinstermin());
        this.ivZinsryth = lvZinsrhythmusLoanIQ;
        this.ivZinstyp = MappingLoanIQ.changeZinstyp(lvHelpDarlehen.getZinstyp(), lvHelpDarlehen.getMerkmalAktivPassiv(), pvLogger);
                
        return true;
    }
    
    /**
     * Importiert die PassivDaten aus LoanIQ
     * @param pvPassivDaten
     * @param pvLogger log4j-Logger
     */
    public boolean importLoanIQPassiv(LoanIQPassivDaten pvPassivDaten, Logger pvLogger)
    {           
      this.setKondkey("1");
      this.setWhrg(pvPassivDaten.getWaehrung());
      this.setAtkonv("0");
      this.setAtkonvmod("0");
      // immer 0
      this.setAtkonvtag("0");
      
      // hier immer DE
      this.setBankkal("DE");

      this.setCap(pvPassivDaten.getCap());
      this.setFloor(pvPassivDaten.getFloor());
            
      this.setEdat(DatumUtilities.changeDate(pvPassivDaten.getEmissionsdatum()));
      this.setEkurs(pvPassivDaten.getEmissionskurs());
      
      // immer Banktage
      this.setFixkalart("1");
      
      // immer anchor
      this.setFixkonv("1");
      this.setFixtage("2");
      this.setFixtagedir("0");
      
      // immer 0
      this.setFixtagemod("0");
      
      // immer target
      this.setKalfix("TARGET");
      
      // Kalenderkonvention 
      this.setKalkonv(pvPassivDaten.getKalenderkonvention());
      
      // kupon ungerade->2, sonst->1
      this.setKupbas(pvPassivDaten.getKuponbasis());
      this.setKupbasodd(pvPassivDaten.getKuponbasisOdd());
      
      // immer gleich
      this.setMonendkonv("1");
      
      this.setNomzins(pvPassivDaten.getNominalzinssatz());
      
      // Referenzzins
      this.setRefzins(MappingLoanIQ.changeReferenzzins(pvPassivDaten.getReferenzzins(), pvPassivDaten.getZinszahlungsrhythmus(), pvLogger));
        // CT 27.05.2019 - Duplizierung des Referenzzins in 'ivRefzinstxt'
        this.ivRefzinstxt = this.ivRefzins;

      this.setSpread(pvPassivDaten.getSpread());
      
      // immer kurz
      this.setTilgabw("1");
            
      // Tilgung defaultmaessig auf 'Ja' (1)
      this.setMantilg("1");

      // aufpassen, prolongierte
      // Sonderbehandlung Rollover
      this.setManzins("1"); 

      ////CT 31.01.2019
      // Fuer Zeros werden keine Zinsen angeliefert - CT 05.11.2018
      ////if (pvPassivDaten.getMerkmalZinssatz().equals("ZERO"))
      ////{
    	////  this.setManzins("0");
      ////}
      ////CT 31.01.2019

      if (("F".equals(pvPassivDaten.getRolloverKennzeichen())) || ("V".equals(pvPassivDaten.getRolloverKennzeichen())))
      {
        this.setManzins("0");
      }
      
      //if (ivVorlaufsatz.getInstitutsnummer().startsWith("009"))
      //{
        // Das Laufzeitende-Datum als Faelligkeit verwenden - CT 23.01.2019
        this.setFaellig(DatumUtilities.changeDate(pvPassivDaten.getLaufzeitende()));
        
        this.setTilgdat(DatumUtilities.changeDate(pvPassivDaten.getTilgungsbeginn()));
        this.setTilgbeg(DatumUtilities.changeDate(pvPassivDaten.getTilgungsbeginn()));
        
        // Das Laufzeitende-Datum als Endedatum verwenden - CT 23.01.2019
        this.setEnddat(DatumUtilities.changeDate(pvPassivDaten.getLaufzeitende()));
      //}
           
      
      // CT 24.09.2012 - Sonderbehandlung BLB
      // Faelligkeit = DZA + 1, wenn Zinsbesonderheit = 4 oder 9
      // Faelligkeit = DZA, sonst
      //if (ivVorlaufsatz.getInstitutsnummer().startsWith("004"))
      //{
        /* if ("9".equals(lvMalere) || "4".equals(lvMalere))
        {
            lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(DatumUtilities.addTagOhnePruefung(pvPassiv.getChildText("ZinsanpassungDatum"))));
            lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(DatumUtilities.addTagOhnePruefung(pvPassiv.getChildText("ZinsanpassungDatum"))));
            lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(DatumUtilities.addTagOhnePruefung(pvPassiv.getChildText("ZinsanpassungDatum"))));
        }
        else
        {
            lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
            lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
            lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));            
        }    */         
      //}
      
      this.setTilgryth(pvPassivDaten.getTilgungsrhythmus());
      // namenspapiere immer bullet
      if (pvPassivDaten.getKredittyp().equals("5011") || pvPassivDaten.getKredittyp().equals("5012") ||
          pvPassivDaten.getKredittyp().equals("5101") || pvPassivDaten.getKredittyp().equals("5103") ||
          pvPassivDaten.getKredittyp().equals("5201") || pvPassivDaten.getKredittyp().equals("5202") ||
          pvPassivDaten.getKredittyp().equals("5013") || pvPassivDaten.getKredittyp().equals("5056") ||
          pvPassivDaten.getKredittyp().equals("5057") || pvPassivDaten.getKredittyp().equals("5058") ||
          pvPassivDaten.getKredittyp().equals("5156") || pvPassivDaten.getKredittyp().equals("5157")   )
      {
          this.setTilgryth("0");
      }
          
      this.setTilgsatz(pvPassivDaten.getTilgungssatz());
      
      // es kommt nur endfaellig in Frage
      this.setZahltyp("3");

      this.setTilgver("0");
      this.setLrate(pvPassivDaten.getLeistungsrate());
              
      // immer kurz  
      this.setZinsabw("1");
      
      this.setZinsbeg(DatumUtilities.changeDate(pvPassivDaten.getEmissionsdatum()));
      
      this.setZinsdat(DatumUtilities.changeDate(pvPassivDaten.getZinsbeginn()));
      
      this.setZinsenddat(DatumUtilities.changeDate(pvPassivDaten.getZinsendedatum()));
     
      // immer anchor
      this.setZinsperkonv("1");
      
      this.setZinsryth(MappingLoanIQ.changeZinsrythmus(pvPassivDaten.getZinszahlungsrhythmus(), pvLogger));
    // Zeros immer Sonderfall (13) -> Aenderung auf 'taeglich' - CT 05.11.2018
    //// CT 23.01.2019
    if (pvPassivDaten.getMerkmalZinssatz().equals("ZERO"))
    {
      this.setZinsryth("13");
    }
      ////if (pvPassivDaten.getMerkmalZinssatz().equals("ZERO"))
      ////{
      ////	  this.setZinsryth("1000");
      ////}
      ////CT 23.01.2019
       
      if (pvPassivDaten.getMerkmalZinssatz().equals("FIXED"))
      {
    	  this.setZinstyp("1");  
      }
      if (pvPassivDaten.getMerkmalZinssatz().equals("FLOAT"))
      {
    	  this.setZinstyp("2");
      }
      if (pvPassivDaten.getMerkmalZinssatz().equals("ZERO"))
      {
    	  this.setZinstyp("4");
      }

      // Zinszahlungsart aus NachsterZinstermin und NaechsteZinsfaelligkeit
      String lvNachsterZinstermin = pvPassivDaten.getNaechsteZinsanpassung();
      
      String lvNaechsteZinsfaelligkeit = pvPassivDaten.getZinstermin(); 
      
      if (lvNachsterZinstermin.compareTo(lvNaechsteZinsfaelligkeit) < 0)
      {
    	  this.setZinszahlart("1");
      }
      else if (lvNachsterZinstermin.compareTo(lvNaechsteZinsfaelligkeit) > 0)
      {
          this.setZinszahlart("2");
      }
      else
      {
          this.setZinszahlart("0");
      }
      
      return true;
    }  

    /**
     * Importiert die Darlehensinformationen von LoanIQ fuer das RefiRegister
     * @param pvDarlehen
     * @param pvLogger log4j-Logger
     * @return 
     */
    public boolean importLoanIQRefiRegister(Darlehen pvDarlehen, Logger pvLogger)
    {    	
        // Buergschaftprozent
        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
    	BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftprozent())).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);
    			
        String lvZinsrhythmusLoanIQ = MappingLoanIQ.changeZinsrythmus(pvDarlehen.getZinszahlungsrythmus(), pvLogger);
        this.ivAtkonv = MappingLoanIQ.changeArbeitskonvention(pvDarlehen.getArbeitskonvention(), pvLogger);
        this.ivAtkonvtag = "0";
        this.ivBankkal = "DE";
        
       	if (lvBuergschaftprozent.doubleValue() > 0.0 && pvDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		this.ivBernom = (StringKonverter.convertString2BigDecimal(pvDarlehen.getBerechnungsnominale())).multiply(lvBuergschaftprozent).toPlainString();
        }
    	else
    	{
    		this.ivBernom = pvDarlehen.getBerechnungsnominale();
        }  
        //this.ivDatltztanp = DatumUtilities.changeDate(pvDarlehen.getLetzteZinsanpassung());
        this.ivDkv = pvDarlehen.getDatumKonditionierung();
        if (!pvDarlehen.getLaufzeitZinsanpassung().equals("99Y"))
        {
          this.ivDlz = pvDarlehen.getLaufzeitZinsanpassung();
        }
        this.ivDlza = pvDarlehen.getLetzteZinsanpassung();
        this.ivDnz = pvDarlehen.getZinsperiodenende();
        this.ivDza = pvDarlehen.getNaechsteZinsanpassung();
        this.ivEnddat = DatumUtilities.changeDate(pvDarlehen.getLaufzeitende());
        //this.ivDatltzttilg = DatumUtilities.changeDate(pvDarlehen.getLaufzeitende());
        this.ivFaellig = DatumUtilities.changeDate(pvDarlehen.getFaelligkeit());
        this.ivFixkalart = "1";
        this.ivFixkonv = "1";
        this.ivFixtage = pvDarlehen.getVorfaelligkeitstage();
        
        this.ivKalfix = "DE";       
        this.ivKalkonv = pvDarlehen.getKalenderkonvention();
        this.ivKondkey = "1";
        
       	if (lvBuergschaftprozent.doubleValue() > 0.0 && pvDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		this.ivLrate = (StringKonverter.convertString2BigDecimal(pvDarlehen.getRateFaelligkeit())).multiply(lvBuergschaftprozent).toPlainString();
        }
    	else
    	{
    		this.ivLrate = pvDarlehen.getRateFaelligkeit();
        }
 
        this.ivMantilg = "1";
        this.ivManzins = "1";
        this.ivMonendkonv = "1";
        this.ivNomzins = pvDarlehen.getNominalzinssatz();
        this.ivRefzins = MappingLoanIQ.changeReferenzzins(pvDarlehen.getReferenzzins(), pvDarlehen.getLaufzeitZinsanpassung(), pvLogger);

        this.ivSpread = pvDarlehen.getSpread();

        this.ivTilgbeg = DatumUtilities.changeDate(pvDarlehen.getTilgungsbeginn());
        this.ivTilgdat = DatumUtilities.changeDate(pvDarlehen.getTilgungstermin());
        //this.ivTilgryth = MappingMIDAS.changeZinsrhythmusTilgungsrhythmus(lvHelpDarlehenLoanIQ.getLaufzeitZinsanpassung());
        this.ivTilgsatz = pvDarlehen.getTilgungsprozentsatz();
        this.ivTilgver = "0";
        this.ivVfaellig = pvDarlehen.getVorfaelligkeitstage();
        this.ivWhrg = pvDarlehen.getBetragwaehrung();
        this.ivZahltyp = pvDarlehen.getTilgungsart();
        if (pvDarlehen.getTilgungsart().equals("7"))
        {
        	this.ivZahltyp = "1";
        }
        
        this.ivZbkv = pvDarlehen.getKonditionierungVon();
        this.ivZinsbeg = DatumUtilities.changeDate(pvDarlehen.getBeginnErsteZinsperiode());
        //System.out.println("Zinstermin: " + lvHelpDarlehenLoanIQ.getZinstermin());
        //System.out.println("Zinsperiodenende: " + lvHelpDarlehenLoanIQ.getZinsperiodenende());
        //System.out.println("Naechste Zinsanpassung: " + lvHelpDarlehenLoanIQ.getNaechsteZinsanpassung());
        this.ivZinsdat = DatumUtilities.changeDate(pvDarlehen.getZinstermin());
        this.ivZinsryth = lvZinsrhythmusLoanIQ;
        this.ivZinstyp = MappingLoanIQ.changeZinstyp(pvDarlehen.getZinstyp(), pvDarlehen.getMerkmalAktivPassiv(), pvLogger);
                
        return true;
    }
    
    /**
     * Importiert die Darlehensinformationen von MIDAS
     * @param pvDarlehen
     * @param pvLogger log4j-Logger
     * @return 
     */
    public boolean importMIDAS(Darlehen pvDarlehen, Logger pvLogger)
    {
    	this.ivAtkonv = "1";
        this.ivAtkonvtag = "0";
 
        // this.ivBankkal = "TARGET"; // Wird nicht beliefert
        
        // Berechnungsnominale wird nicht beliefert
        
        this.ivDatltztanp = DatumUtilities.changeDate(pvDarlehen.getLetzteZinsanpassung());
        this.ivDkv = pvDarlehen.getDatumKonditionierung();
        if (!pvDarlehen.getLaufzeitZinsanpassung().equals("99Y"))
        {
          this.ivDlz = pvDarlehen.getLaufzeitZinsanpassung();
        }
        this.ivDlza = pvDarlehen.getLetzteZinsanpassung();
        this.ivDnz = pvDarlehen.getZinsperiodenende();
        this.ivDza = pvDarlehen.getNaechsteZinsanpassung();
        this.ivEnddat = DatumUtilities.changeDate(pvDarlehen.getLaufzeitende());
        //this.ivFaellig = DatumUtilities.changeDate(pvDarlehen.getLaufzeitende());
        this.ivDatltzttilg = DatumUtilities.changeDate(pvDarlehen.getLaufzeitende());
        
        this.ivFaellig = DatumUtilities.changeDate(pvDarlehen.getFaelligkeit());
        this.ivFixkalart = "1";
        this.ivFixkonv = "1";
        this.ivFixtage = "2";	
        this.ivFixtagemod = "0";
        this.ivFixtagedir = "2";
        // Fixing-Rhythmus
        if (pvDarlehen.getLaufzeitZinsanpassung().equals("1M"))
        {
        	this.ivFixrhyth = "1";
        }
        if (pvDarlehen.getLaufzeitZinsanpassung().equals("3M"))
        {
        	this.ivFixrhyth = "3";
        }
        if (pvDarlehen.getLaufzeitZinsanpassung().equals("6M"))
        {
        	this.ivFixrhyth = "6";
        }
        if (pvDarlehen.getLaufzeitZinsanpassung().equals("1Y"))
        {
        	this.ivFixrhyth = "12";
        }
        
        this.ivKalfix = "TARGET";

        this.ivKalkonv = pvDarlehen.getKalenderkonvention();
        this.ivKondkey = "1";
        
        // Leistungsrate wird nicht angeliefert
        // Buergschaftprozent
    	BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftprozent())).divide(new BigDecimal("100.0"), 9, RoundingMode.HALF_UP);

        if (lvBuergschaftprozent.doubleValue() > 0.0)
        {
        	this.ivLrate = (StringKonverter.convertString2BigDecimal(pvDarlehen.getRateFaelligkeit())).multiply(lvBuergschaftprozent).toPlainString();
        }
        else
        {
        	this.ivLrate = pvDarlehen.getRateFaelligkeit();
        }

        this.ivMantilg = "1";
        this.ivManzins = "0";        	        
        this.ivMonendkonv = "1";
        this.ivNomzins = pvDarlehen.getNominalzinssatz();
        this.ivRefzins = MappingMIDAS.changeReferenzzins(pvDarlehen.getReferenzzins(), pvDarlehen.getBetragwaehrung(), pvDarlehen
            .getLaufzeitZinsanpassung());
        // CT 27.05.2019 - Duplizierung des Referenzzins in 'ivRefzinstxt'
        this.ivRefzinstxt = this.ivRefzins;

        this.ivSpread = pvDarlehen.getSpread();
        //this.ivTilgbeg = DatumUtilities.changeDate(pvDarlehen.getTilgungsbeginn()); -> Wird nicht beliefert
        this.ivTilgdat = DatumUtilities.changeDate(pvDarlehen.getTilgungstermin());
        //this.ivTilgryth = MappingMIDAS.changeZinsrhythmusTilgungsrhythmus(pvDarlehen.getLaufzeitZinsanpassung());
        //this.ivTilgsatz = "";
        this.ivTilgver = "0";
        this.ivWhrg = pvDarlehen.getBetragwaehrung();
        this.ivZahltyp = pvDarlehen.getTilgungsart();
        if (pvDarlehen.getTilgungsart().equals("7"))
        {
        	this.ivZahltyp = "1";
        }
        // MIDAS - erst einmal alle Konstant auf 'Raten'
        if (pvDarlehen.getQuellsystem().startsWith("MID"))
        {
        	this.ivZahltyp = "2";
        }

        this.ivZbkv = pvDarlehen.getKonditionierungVon();
        this.ivZinsbeg = DatumUtilities.changeDate(pvDarlehen.getBeginnErsteZinsperiode());
        this.ivZinsdat = DatumUtilities.changeDate(pvDarlehen.getZinsperiodenende());

        //String lvZinsrhythmusMIDAS = MappingMIDAS.changeZinsrhythmus(pvDarlehen.getZinszahlungsrythmus());
        String lvZinsrhythmusMIDAS = MappingMIDAS.changeZinsrhythmusTilgungsrhythmus(pvDarlehen.getLaufzeitZinsanpassung());
        this.ivZinsryth = lvZinsrhythmusMIDAS;
        this.ivZinstyp = MappingLoanIQ.changeZinstyp(pvDarlehen.getZinstyp(), pvDarlehen.getMerkmalAktivPassiv(), pvLogger);

        // MIDAS: Zinstyp 'fest', dann Reiter 'Zins variabel' nicht beliefern
        //System.out.println("Zinstyp: " + pvDarlehen.getZinstyp());
        if (pvDarlehen.getZinstyp().equals("FIXED"))
        {
        	//System.out.println("Fest...");
        	this.ivFixtage = new String();
        	this.ivFixtagedir = new String();
        	this.ivFixtagemod = new String();
        	this.ivKalfix = new String();
        	this.ivFixrhyth = new String();
        	this.ivRefizins = new String();
        	  
        	this.ivSpread = new String();
        	this.ivFixkalart = new String();
        	this.ivFixkonv = new String();
        }

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
    	this.ivKondkey = "1"; 
    	this.ivEkurs = pvGattung.getGD669();
    	if (!pvGattung.getGD660().isEmpty())
    	{
    		this.ivEdat = pvGattung.getGD660();
    	}
    	else
    	{
    		this.ivEdat = pvGattung.getGD322();
    	}

        if (pvBestandsdaten.getAktivPassiv().equals("1")) // nur Aktiv
        {
            this.ivEnddat = pvGattung.getGD910();
        }

        this.ivAtkonv = "1"; // Konstant 'Danach'(1)
    	this.ivAtkonvmod = "0"; // Erst einmal Konstant '0' -> Problem MAVIS-Produkttyp
    	this.ivAtkonvtag = "0"; // Konstant '0'
    	this.ivBankkal = "DE";  // Konstant 'DE'
    	this.ivSpread = pvGattung.getGD808C();
    	this.ivCap = pvGattung.getGD804E();
    	if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
    	{
    		this.ivDatltztfix = pvGattung.getGD815B();
    	}
    			
    	this.ivFloor = pvGattung.getGD803E();
    	//if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
    	//{
    		
    		this.ivFaellig = pvGattung.getGD910();
    	//}
    	this.ivFixkalart = "1"; // Konstant 'Banktage'(1)
    	this.ivFixkonv = MappingWertpapiere.ermittleFixingKonvention(pvGattung.getGD809C());
    	if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
    	{
    		this.ivFixrhyth = MappingWertpapiere.ermittleZinsrythmus(pvGattung.getGD311A(), pvGattung.getGD811()); // Auch hier Zinsrythmus verwenden
    	}
    	this.ivFixtage = pvGattung.getGD809();
    	this.ivFixtagedir = "2"; // Konstant 'Davor'(2)
    	this.ivFixtagemod = "0"; // Konstant '0'
    	this.ivKalfix = "DE"; // Konstant 'DE'
    	this.ivKalkonv = MappingWertpapiere.ermittleKalenderkonvention(pvGattung.getGD821B());
    	    	
    	if (pvBestandsdaten.getAktivPassiv().equals("1"))
    	{
    	  if (pvGattung.getGD821B().equals("09") || pvGattung.getGD821B().equals("10"))
    	  { // Ungerade(2)
    	    this.ivKupbas = "2";
    	    this.ivKupbasodd = "2";
    	  }  
    	  else // Gerade(1)
    	  {
      	    this.ivKupbas = "1";
      	    this.ivKupbasodd = "1";    		
    	  }
    	}
    	if (pvBestandsdaten.getAktivPassiv().equals("2"))
    	{
    	       // Anpassungen an TXS 4.03 wegen geaenderter Barwertberechnungen dort an variablen Papieren
    	       if ("010202".equals(pvBestandsdaten.getWertpapierart()))
    	       {
    	           this.ivKupbas = "0";
    	           this.ivKupbasodd = "0";
    	           //this.ivDatltztfix = pvGattung.getGD815B(); // Hier noetig...
    	           //lvEl_konddaten.setAttribute("fixrhyth",lvNodeSA01.getChildText("Zinszahlungstyp").trim());
    	       }
    	       else
    	       {    
    	     	  if (pvGattung.getGD821B().equals("09") || pvGattung.getGD821B().equals("10"))
    	    	  { // Ungerade(2)
    	    	    this.ivKupbas = "2";
    	    	    this.ivKupbasodd = "2";
    	    	  }  
    	    	  else // Gerade(1)
    	    	  {
    	      	    this.ivKupbas = "1";
    	      	    this.ivKupbasodd = "1";    		
    	    	  }
    	       }
    	}
    	if (pvGattung.getGD900().isEmpty())
    	{
    		this.ivLrate = pvBestandsdaten.getNominalbetrag();
    	}
    	else
    	{
    		this.ivLrate = pvGattung.getGD921B();
    	}
    	this.ivMonendkonv = "1"; // Konstant 'gleich'(1)
    	this.ivMantilg = "1"; // Cashflows werden angeliefert - Aenderung zur alten Schnittstelle
    	this.ivManzins = "1"; // Cashflows werden angeliefert - Aenderung zur alten Schnittstelle
    	this.ivNomzins = pvGattung.getGD801A();
    	this.ivRefzins = MappingWertpapiere.ermittleReferenzzins(pvGattung.getGD808(), pvGattung.getGD808A(), pvGattung.getGD808B());
    	// CT 27.05.2019 - Duplizierung des Referenzzins in 'ivRefzinstxt'
        this.ivRefzinstxt = this.ivRefzins;

        //if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
    	//{
    		this.ivRkurs = pvGattung.getGD861A();
    	//}
    	if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
    	{
    		this.ivTilgabw = "0"; // Konstant '0'
    	}
    	if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
    	{
    		this.ivTilgbeg = pvGattung.getGD910();
    	}
    	this.ivTilgdat = pvGattung.getGD910();
    	this.ivTilgperkonv = "1"; // Konstant 'anchor'(1)
    	
        // Tilgungsrythmus defaultmaessig auf 'bullet' (0) setzen - CT 11.08.2015
    	//if (pvBestandsdaten.getAktivPassiv().equals("2"))
    	//{	
    	  this.ivTilgryth = "0";
    	//}
    	/* else
    	{
      		// GD842     	TXS-Wert
    		// 1            12
    		// 3            6
    		// 4            3
    		// A            1
    		// <> blank     13

    		// Defaultwert '0'
    		this.ivTilgryth = "0";
    		if (!(pvGattung.getGD842().equals("1") || pvGattung.getGD842().equals("3") || pvGattung.getGD842().equals("4") || pvGattung.getGD842().equals("A")))
    		{
    			this.ivTilgryth = "13";
    		}
    		else
    		{
    		  if (pvGattung.getGD842().equals("1"))
    		  {
    			this.ivTilgryth = "12";
    		  }
    		  if (pvGattung.getGD842().equals("3"))
    		  {
    			this.ivTilgryth = "6";
    		  }
    		  if (pvGattung.getGD842().equals("4"))
    		  {
    			this.ivTilgryth = "3";
    		  }
    		  if (pvGattung.getGD842().equals("A"))
    		  {
    			this.ivTilgryth = "1";
    		  }
    		}
    	} */
    	////this.ivTilgsatz = "0";
    	////if (pvGattung.getGD841().equals("3") || pvGattung.getGD841().equals("4") || pvGattung.getGD841().equals("E") || pvGattung.getGD841().equals("B"))
    	////{
    	////	this.ivTilgsatz = "100";
    	////}
    	this.ivTilgsatz = pvGattung.getGD861A();  
    	
    	this.ivZahltyp = MappingWertpapiere.ermittleZahlungstyp(pvGattung.getGD841());
    	if (ivZahltyp.equals("3"))
    	{
    		this.ivTilgver = "0";
    	}
    	this.ivZinsabw = MappingWertpapiere.ermittleZinsabweichung(pvGattung.getGD321());
    	this.ivZinsbeg = pvGattung.getGD322();
    	this.ivZinsdat = pvGattung.getGD290A();
    	this.ivZinsenddat = pvGattung.getGD910();
    	this.ivZinsperkonv = "1"; // Konstant 'anchor'(1)
    	this.ivZinsryth = MappingWertpapiere.ermittleZinsrythmus(pvGattung.getGD311A(), pvGattung.getGD811());
    	this.ivZinstyp = MappingWertpapiere.ermittleZinstyp(pvGattung.getGD805());
    	this.ivZinszahlart = MappingWertpapiere.ermittleZinszahlart(pvGattung.getGD805(), pvGattung.getGD312());
    	this.ivZinsfak = "1"; // Konstant '1'
    	this.ivVzinsdat = "0"; // Konstant '0'
    	this.ivVztilgdat = "0"; // Konstant '0'
    	this.ivWhrg = pvBestandsdaten.getNominalbetragWaehrung();
    	
    	return true;
    }


    /**
     * Importiert die Wertpapier-Informationen fuer KEV
     * @param pvBestandsdaten
     * @param pvGattung
     * @param pvLogger
     * @return
     */
    public boolean importKEVWertpapier(Bestandsdaten pvBestandsdaten, Gattung pvGattung, Logger pvLogger)
    {
        this.ivKondkey = "1";
        this.ivEkurs = pvGattung.getGD669();
        if (!pvGattung.getGD660().isEmpty())
        {
            this.ivEdat = pvGattung.getGD660();
        }
        else
        {
            this.ivEdat = pvGattung.getGD322();
        }
        this.ivEnddat = pvGattung.getGD910();

        this.ivAtkonv = "1"; // Konstant 'Danach'(1)
        this.ivAtkonvmod = "0"; // Erst einmal Konstant '0' -> Problem MAVIS-Produkttyp
        this.ivAtkonvtag = "0"; // Konstant '0'
        this.ivBankkal = "DE";  // Konstant 'DE'
        this.ivSpread = pvGattung.getGD808C();
        if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
        {
            this.ivDatltztfix = pvGattung.getGD815B();
        }

        if (MappingWertpapiere.ermittleZinstyp(pvGattung.getGD805()).equals("1"))
        {
            this.ivRefzins = "keine";
        }
        else
        {
            this.ivRefzins = MappingWertpapiere.ermittleReferenzzins(pvGattung.getGD808(), pvGattung.getGD808A(), pvGattung.getGD808B());
        }

        if (!MappingWertpapiere.ermittleZinstyp(pvGattung.getGD805()).equals("1"))
        {
            this.ivCap = pvGattung.getGD804E();
            this.ivFloor = pvGattung.getGD803E();
        }
          //if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
        //{

        this.ivFaellig = pvGattung.getGD910();
        //}
        this.ivFixkalart = "1"; // Konstant 'Banktage'(1)
        this.ivFixkonv = MappingWertpapiere.ermittleFixingKonvention(pvGattung.getGD809C());
        if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
        {
            this.ivFixrhyth = MappingWertpapiere.ermittleZinsrythmus(pvGattung.getGD311A(), pvGattung.getGD811()); // Auch hier Zinsrythmus verwenden
        }
        this.ivFixtage = pvGattung.getGD809();
        this.ivFixtagedir = "2"; // Konstant 'Davor'(2)
        this.ivFixtagemod = "0"; // Konstant '0'
        this.ivKalfix = "DE"; // Konstant 'DE'
        this.ivKalkonv = MappingWertpapiere.ermittleKalenderkonvention(pvGattung.getGD821B());

        if (pvBestandsdaten.getAktivPassiv().equals("1"))
        {
            if (pvGattung.getGD821B().equals("09") || pvGattung.getGD821B().equals("10"))
            { // Ungerade(2)
                this.ivKupbas = "2";
                this.ivKupbasodd = "2";
            }
            else // Gerade(1)
            {
                this.ivKupbas = "1";
                this.ivKupbasodd = "1";
            }
        }
        if (pvBestandsdaten.getAktivPassiv().equals("2"))
        {
            // Anpassungen an TXS 4.03 wegen geaenderter Barwertberechnungen dort an variablen Papieren
            if ("010202".equals(pvBestandsdaten.getWertpapierart()))
            {
                this.ivKupbas = "0";
                this.ivKupbasodd = "0";
                //this.ivDatltztfix = pvGattung.getGD815B(); // Hier noetig...
                //lvEl_konddaten.setAttribute("fixrhyth",lvNodeSA01.getChildText("Zinszahlungstyp").trim());
            }
            else
            {
                if (pvGattung.getGD821B().equals("09") || pvGattung.getGD821B().equals("10"))
                { // Ungerade(2)
                    this.ivKupbas = "2";
                    this.ivKupbasodd = "2";
                }
                else // Gerade(1)
                {
                    this.ivKupbas = "1";
                    this.ivKupbasodd = "1";
                }
            }
        }
        if (pvGattung.getGD900().isEmpty())
        {
            this.ivLrate = pvBestandsdaten.getNominalbetrag();
        }
        else
        {
            this.ivLrate = pvGattung.getGD921B();
        }
        this.ivMonendkonv = "1"; // Konstant 'gleich'(1)
        this.ivMantilg = "1"; // Cashflows werden angeliefert - Aenderung zur alten Schnittstelle
        this.ivManzins = "1"; // Cashflows werden angeliefert - Aenderung zur alten Schnittstelle
        this.ivNomzins = pvGattung.getGD801A();
        // CT 27.05.2019 - Duplizierung des Referenzzins in 'ivRefzinstxt'
        //this.ivRefzinstxt = this.ivRefzins;

        //if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
        //{
        this.ivRkurs = pvGattung.getGD861A();
        //}
        if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
        {
            this.ivTilgabw = "0"; // Konstant '0'
        }
        if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
        {
            this.ivTilgbeg = pvGattung.getGD910();
        }
        this.ivTilgdat = pvGattung.getGD910();
        this.ivTilgperkonv = "1"; // Konstant 'anchor'(1)

        // Tilgungsrythmus defaultmaessig auf 'bullet' (0) setzen - CT 11.08.2015
        //if (pvBestandsdaten.getAktivPassiv().equals("2"))
        //{
        this.ivTilgryth = "0";
        //}
    	/* else
    	{
      		// GD842     	TXS-Wert
    		// 1            12
    		// 3            6
    		// 4            3
    		// A            1
    		// <> blank     13

    		// Defaultwert '0'
    		this.ivTilgryth = "0";
    		if (!(pvGattung.getGD842().equals("1") || pvGattung.getGD842().equals("3") || pvGattung.getGD842().equals("4") || pvGattung.getGD842().equals("A")))
    		{
    			this.ivTilgryth = "13";
    		}
    		else
    		{
    		  if (pvGattung.getGD842().equals("1"))
    		  {
    			this.ivTilgryth = "12";
    		  }
    		  if (pvGattung.getGD842().equals("3"))
    		  {
    			this.ivTilgryth = "6";
    		  }
    		  if (pvGattung.getGD842().equals("4"))
    		  {
    			this.ivTilgryth = "3";
    		  }
    		  if (pvGattung.getGD842().equals("A"))
    		  {
    			this.ivTilgryth = "1";
    		  }
    		}
    	} */
        ////this.ivTilgsatz = "0";
        ////if (pvGattung.getGD841().equals("3") || pvGattung.getGD841().equals("4") || pvGattung.getGD841().equals("E") || pvGattung.getGD841().equals("B"))
        ////{
        ////	this.ivTilgsatz = "100";
        ////}
        this.ivTilgsatz = pvGattung.getGD861A();

        this.ivZahltyp = MappingWertpapiere.ermittleZahlungstyp(pvGattung.getGD841());
        if (ivZahltyp.equals("3"))
        {
            this.ivTilgver = "0";
        }
        this.ivZinsabw = MappingWertpapiere.ermittleZinsabweichung(pvGattung.getGD321());
        this.ivZinsbeg = pvGattung.getGD322();
        this.ivZinsdat = pvGattung.getGD290A();
        this.ivZinsenddat = pvGattung.getGD910();
        this.ivZinsperkonv = "1"; // Konstant 'anchor'(1)
        this.ivZinsryth = MappingWertpapiere.ermittleZinsrythmus(pvGattung.getGD311A(), pvGattung.getGD811());
        this.ivZinstyp = MappingWertpapiere.ermittleZinstyp(pvGattung.getGD805());
        this.ivZinszahlart = MappingWertpapiere.ermittleZinszahlart(pvGattung.getGD805(), pvGattung.getGD312());
        this.ivZinsfak = "1"; // Konstant '1'
        this.ivVzinsdat = "0"; // Konstant '0'
        this.ivVztilgdat = "0"; // Konstant '0'
        this.ivWhrg = pvBestandsdaten.getNominalbetragWaehrung();

        return true;
    }

    /**
     * Importiert die Darlehensinformationen aus LoanIQ fuer KEV
     * @param pvDarlehen
     * @param pvLogger log4j-Logger
     * @return
     */
    public boolean importKEVLoanIQ(Darlehen pvDarlehen, Logger pvLogger)
    {
        this.ivAtkonv = "1";
        this.ivAtkonvtag = "0";

        // this.ivBankkal = "TARGET"; // Wird nicht beliefert

        // Berechnungsnominale wird nicht beliefert

        this.ivDatltztanp = DatumUtilities.changeDate(pvDarlehen.getLetzteZinsanpassung());
        this.ivDkv = pvDarlehen.getDatumKonditionierung();
        if (!pvDarlehen.getLaufzeitZinsanpassung().equals("99Y"))
        {
            this.ivDlz = pvDarlehen.getLaufzeitZinsanpassung();
        }
        this.ivDlza = pvDarlehen.getLetzteZinsanpassung();
        this.ivDnz = pvDarlehen.getZinsperiodenende();
        this.ivDza = pvDarlehen.getNaechsteZinsanpassung();
        this.ivEnddat = DatumUtilities.changeDate(pvDarlehen.getLaufzeitende());
        //this.ivFaellig = DatumUtilities.changeDate(pvDarlehen.getLaufzeitende());
        this.ivDatltzttilg = DatumUtilities.changeDate(pvDarlehen.getLaufzeitende());

        this.ivFaellig = DatumUtilities.changeDate(pvDarlehen.getFaelligkeit());
        this.ivFixkalart = "1";
        this.ivFixkonv = "1";
        this.ivFixtage = "2";
        this.ivFixtagemod = "0";
        this.ivFixtagedir = "2";
        // Fixing-Rhythmus
        if (pvDarlehen.getLaufzeitZinsanpassung().equals("1M"))
        {
            this.ivFixrhyth = "1";
        }
        if (pvDarlehen.getLaufzeitZinsanpassung().equals("3M"))
        {
            this.ivFixrhyth = "3";
        }
        if (pvDarlehen.getLaufzeitZinsanpassung().equals("6M"))
        {
            this.ivFixrhyth = "6";
        }
        if (pvDarlehen.getLaufzeitZinsanpassung().equals("1Y"))
        {
            this.ivFixrhyth = "12";
        }

        this.ivKalfix = "TARGET";

        this.ivKalkonv = pvDarlehen.getKalenderkonvention();
        this.ivKondkey = "1";

        // Leistungsrate wird nicht angeliefert
        // Buergschaftprozent
        BigDecimal lvBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvDarlehen.getBuergschaftprozent())).divide(new BigDecimal("100.0"), 9, RoundingMode.HALF_UP);

        if (lvBuergschaftprozent.doubleValue() > 0.0)
        {
            this.ivLrate = (StringKonverter.convertString2BigDecimal(pvDarlehen.getRateFaelligkeit())).multiply(lvBuergschaftprozent).toPlainString();
        }
        else
        {
            this.ivLrate = pvDarlehen.getRateFaelligkeit();
        }

        this.ivMantilg = "1";
        this.ivManzins = "0";
        this.ivMonendkonv = "1";
        this.ivNomzins = pvDarlehen.getNominalzinssatz();
        this.ivRefzins = MappingMIDAS.changeReferenzzins(pvDarlehen.getReferenzzins(), pvDarlehen.getBetragwaehrung(), pvDarlehen
            .getLaufzeitZinsanpassung());
        // CT 27.05.2019 - Duplizierung des Referenzzins in 'ivRefzinstxt'
        //this.ivRefzinstxt = this.ivRefzins;

        pvLogger.info("Refzins;" + pvDarlehen.getKontonummer() + ";" + pvDarlehen.getAusplatzierungsmerkmal() + ";" + pvDarlehen
            .getReferenzzins() + ";" + pvDarlehen.getLaufzeitZinsanpassung() + ";" + this.ivRefzins);
        this.ivSpread = pvDarlehen.getSpread();
        //this.ivTilgbeg = DatumUtilities.changeDate(pvDarlehen.getTilgungsbeginn()); -> Wird nicht beliefert
        this.ivTilgdat = DatumUtilities.changeDate(pvDarlehen.getTilgungstermin());
        //this.ivTilgryth = MappingMIDAS.changeZinsrhythmusTilgungsrhythmus(pvDarlehen.getLaufzeitZinsanpassung());
        //this.ivTilgsatz = "";
        this.ivTilgver = "0";
        this.ivWhrg = pvDarlehen.getBetragwaehrung();
        this.ivZahltyp = pvDarlehen.getTilgungsart();
        if (pvDarlehen.getTilgungsart().equals("7"))
        {
            this.ivZahltyp = "1";
        }
        // MIDAS - erst einmal alle Konstant auf 'Raten'
        if (pvDarlehen.getQuellsystem().startsWith("MID"))
        {
            this.ivZahltyp = "2";
        }

        this.ivZbkv = pvDarlehen.getKonditionierungVon();
        this.ivZinsbeg = DatumUtilities.changeDate(pvDarlehen.getBeginnErsteZinsperiode());
        this.ivZinsdat = DatumUtilities.changeDate(pvDarlehen.getZinsperiodenende());

        //String lvZinsrhythmusMIDAS = MappingMIDAS.changeZinsrhythmus(pvDarlehen.getZinszahlungsrythmus());
        String lvZinsrhythmusMIDAS = MappingMIDAS.changeZinsrhythmusTilgungsrhythmus(pvDarlehen.getLaufzeitZinsanpassung());
        this.ivZinsryth = lvZinsrhythmusMIDAS;
        this.ivZinstyp = MappingLoanIQ.changeZinstyp(pvDarlehen.getZinstyp(), pvDarlehen.getMerkmalAktivPassiv(), pvLogger);

        // MIDAS: Zinstyp 'fest', dann Reiter 'Zins variabel' nicht beliefern
        //System.out.println("Zinstyp: " + pvDarlehen.getZinstyp());
        if (pvDarlehen.getZinstyp().equals("FIXED"))
        {
            //System.out.println("Fest...");
            this.ivFixtage = new String();
            this.ivFixtagedir = new String();
            this.ivFixtagemod = new String();
            this.ivKalfix = new String();
            this.ivFixrhyth = new String();
            this.ivRefizins = new String();

            this.ivSpread = new String();
            this.ivFixkalart = new String();
            this.ivFixkonv = new String();
        }

        return true;
    }

    /**
     * Importiert die Termingeldinformationen
     * @param pvTermingeld Termingeld
     * @param pvLogger log4j-Logger
     * @return
     */
    public boolean importTermingeld(Termingeld pvTermingeld, Logger pvLogger)
    {
        this.ivBankkal = "DE";
        this.ivAtkonv = "0";
        this.ivEkurs = "100";
        this.ivMonendkonv = "1";

        this.ivEnddat = DatumUtilities.changeDate(pvTermingeld.getFaelligkeit());
        this.ivEdat = DatumUtilities.changeDate(pvTermingeld.getEmissionsdatum());

        this.ivFaellig = DatumUtilities.changeDate(pvTermingeld.getFaelligkeit());

        this.ivKalkonv = pvTermingeld.getKalenderkonvention().toLowerCase();
        this.ivKondkey = "1";

        this.ivMantilg = "0";
        this.ivManzins = "0";

        this.ivNomzins = pvTermingeld.getZinssatz();

        this.ivTilgbeg = DatumUtilities.changeDate(pvTermingeld.getTilgungsbeginn());
        this.ivTilgdat = DatumUtilities.changeDate(pvTermingeld.getTilgungsbeginn());
        this.ivTilgryth = "0";

        this.ivWhrg = pvTermingeld.getWaehrung();

        this.ivZahltyp = "3";
        this.ivZinsbeg = DatumUtilities.changeDate(pvTermingeld.getZinsbeginn());
        this.ivZinsdat = DatumUtilities.changeDate(pvTermingeld.getZinsende());

        this.ivZinstyp = MappingLoanIQ.changeZinstyp(pvTermingeld.getZinstyp(), "A", pvLogger);
        this.ivZinsryth = "0";

        return true;
    }

}
