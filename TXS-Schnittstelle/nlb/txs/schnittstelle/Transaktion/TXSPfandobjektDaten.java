/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPVermoegensobjekt;
import nlb.txs.schnittstelle.Utilities.MappingDPP;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;


/**
 * @author tepperc
 *
 */
public class TXSPfandobjektDaten implements TXSTransaktion
{
    /**
     * 
     */
    private String ivAnzgew;
    
    /**
     * 
     */
    private String ivAnzobj;
    
    /**
     * 
     */
    private String ivAnzwhg;
    
    /**
     * 
     */
    private String ivAusldat;
    
    /**
     * 
     */
    private String ivAusst;
    
    /**
     * 
     */
    private String ivBelaus;
    
    /**
     * 
     */
    private String ivBelausmw;
    
    /**
     * 
     */
    private String ivBewdat;
    
    /**
     * 
     */
    private String ivBewerter;
    
    /**
     * 
     */
    private String ivBez;
    
    /**
     * 
     */
    private String ivBranche;
    
    /**
     * 
     */
    private String ivBranchetxt;
    
    /**
     * 
     */
    private String ivBrmiete;
    
    /**
     * 
     */
    private String ivBrmmiete;
    
    /**
     * Branchennummer - Flugzueg
     */
    private String ivBrnr;
    
    /**
     * Betriebsstatus (Flugzeuge)
     */
    private String ivBstatus;
    
    /**
     * 
     */
    private String ivBwert;
    
    /**
     * 
     */
    private String ivBwertdat;
    
    /**
     * Containerkapazität. Nur relevant für Pfandobjekte der Kategorie "Schiff"
     */
    private String ivContkap;
    
    /**
     * Distanz (Flugzeuge)
     */
    private String ivDist;
    
    /**
     * 
     */
    private String ivDscr;    
    
    /**
     * 
     */
    private String ivEigtyp;
    
    /**
     * 
     */
    private String ivErgaenztxt;
    
    /**
     * 
     */
    private String ivErtragsf;
    
    /**
     * 
     */
    private String ivEwert;
    
    /**
     * Datum der Fertigstellung des Pfandobjekts. Nur relevant für Pfandobjekte der Kategorien "Schiff" und "Flugzeug"
     */
    private String ivFertstdat;
    
    /**
     * 
     */
    private String ivFlottnr;
    
    /**
     * 
     */
    private String ivFlut;
    
    /**
     * 
     */
    private String ivFoerdart;
    
    /**
     * Flugzeugtyp
     */
    private String ivFtyp;
    
    /**
     * 
     */
    private String ivGebiet;
    
    /**
     * 
     */
    private String ivGem;
    
    /**
     * 
     */
    private String ivGeschw;
    
    /**
     * 
     */
    private String ivGeteilt;
    
    /**
     * 
     */
    private String ivHausnr;
    
    /**
     * 
     */
    private String ivIcr;
    
    /**
     * 
     */
    private String ivImo;
    
    /**
     * 
     */
    private String ivInvtyp;
    
    /**
     * 
     */
    private String ivJahr;
    
    /**
     * 
     */
    private String ivKat;
    
    /**
     * 
     */
    private String ivKdat;
    
    /**
     * 
     */
    private String ivKpreis;
    
    /**
     * 
     */
    private String ivKreisnr;
    
    /**
     * 
     */
    private String ivLand;
    
    /**
     * 
     */
    private String ivModjahr;
    
    /**
     * Flugzeugmuster
     */
    private String ivMuster;
    
    /**
     * 
     */
    private String ivMwert;
    
    /**
     * 
     */
    private String ivMwertber;
    
    /**
     * 
     */
    private String ivMwertdat;
    
    /**
     * 
     */
    private String ivNart;
    
    /**
     * 
     */
    private String ivNtmiete;
    
    /**
     * 
     */
    private String ivNtmmiete;
    
    /**
     * 
     */
    private String ivOgrp;
    
    /**
     * 
     */
    private String ivOrt;
    
    /**
     * 
     */
    private String ivPlz;
    
    /**
     * 
     */
    private String ivQmges;
    
    /**
     * 
     */
    private String ivQmgew;
    
    /**
     * 
     */
    private String ivQmgrund;
    
    /**
     * 
     */
    private String ivQmwo;
    
    /**
     * 
     */
    private String ivRatint;
    
    /**
     * 
     */
    private String ivRisikotxt;
    
    /**
     * Branchennummer - Schiffe
     */
    private String ivSbrnr;
    
    /**
     * Seriennummer MSN (Flugzeug)
     */
    private String ivSn;
    
    /**
     * 
     */
    private String ivSpeigtyp;
    
    /**
     * 
     */
    private String ivStockw;
    
    /**
     * 
     */
    private String ivStr;
    
    /**
     * 
     */
    private String ivStyp;
    
    /**
     * 
     */
    private String ivSumfrsp;
    
    /**
     * 
     */
    private String ivSumnbetrag;
    
    /**
     * 
     */
    private String ivSumrdpf;
    
    /**
     * 
     */
    private String ivSumrkap;
    
    /**
     * 
     */
    private String ivSumsdpf;
    
    /**
     * 
     */
    private String ivSwert;
    
    /**
     * 
     */
    private String ivTdw;
    
    /**
     * 
     */
    private String ivUrmwert;
    
    /**
     * 
     */
    private String ivUrmwertdat;
    
    /**
     * 
     */
    private String ivVar;
    
    /**
     * 
     */
    private String ivVermiettxt;
  
    /**
     * 
     */
    private String ivWhrg;

    /**
     * 
     */
    private String ivZusatz;
    
    /**
     * Benutzerdefiniertes Feld Konsortialanteil
     */
    private String ivKonsortialanteil;
    
    /**
     * Benutzerdefiniertes Feld Beleihungswert initial
     */
    private String ivBlhwertinit;
    
    /**
     * Benutzerdefiniertes Feld FinanzObjektNr
     */
    private String ivFinobjnr;
    
    /**
     * RefiRegister-Feld: Keine Adresse -> kadr
     */
    private String ivKadr;
    
    /**
     *
     */
    public TXSPfandobjektDaten()
    {
        this.ivAnzgew = new String();
        this.ivAnzobj = new String();
        this.ivAnzwhg = new String();
        this.ivAusldat = new String();
        this.ivAusst = new String();
        this.ivBelaus = new String();
        this.ivBelausmw = new String();
        this.ivBewdat = new String();
        this.ivBewerter = new String();
        this.ivBez = new String();
        this.ivBranche = new String();
        this.ivBranchetxt = new String();
        this.ivBrmiete = new String();
        this.ivBrmmiete = new String();
        this.ivBrnr = new String();
        this.ivBstatus = new String();
        this.ivBwert = new String();
        this.ivBwertdat = new String();
        this.ivContkap = new String();
        this.ivDist = new String();
        this.ivDscr = new String();
        this.ivEigtyp = new String();
        this.ivErgaenztxt = new String();
        this.ivErtragsf = new String();
        this.ivEwert = new String();
        this.ivFertstdat = new String();
        this.ivFlottnr = new String();
        this.ivFlut = new String();
        this.ivFoerdart = new String();
        this.ivFtyp = new String();
        this.ivGebiet = new String();
        this.ivGem = new String();
        this.ivGeschw = new String();
        this.ivGeteilt = new String();
        this.ivHausnr = new String();
        this.ivIcr = new String();
        this.ivImo = new String();
        this.ivInvtyp = new String();
        this.ivJahr = new String();
        this.ivKat = new String();
        this.ivKdat = new String();
        this.ivKpreis = new String();
        this.ivKreisnr = new String();
        this.ivLand = new String();
        this.ivModjahr = new String();
        this.ivMuster = new String();
        this.ivMwert = new String();
        this.ivMwertber = new String();
        this.ivMwertdat = new String();
        this.ivNart = new String();
        this.ivNtmiete = new String();
        this.ivNtmmiete = new String();
        this.ivOgrp = new String();
        this.ivOrt = new String();
        this.ivPlz = new String();
        this.ivQmges = new String();
        this.ivQmgew = new String();
        this.ivQmgrund = new String();
        this.ivQmwo = new String();
        this.ivRatint = new String();
        this.ivRisikotxt = new String();
        this.ivSbrnr = new String();
        this.ivSn = new String();
        this.ivSpeigtyp = new String();
        this.ivStockw = new String();
        this.ivStr = new String();
        this.ivStyp = new String();
        this.ivSumfrsp = new String();
        this.ivSumnbetrag = new String();
        this.ivSumrdpf = new String();
        this.ivSumrkap = new String();
        this.ivSumsdpf = new String();
        this.ivSwert = new String();
        this.ivTdw = new String();
        this.ivUrmwert = new String();
        this.ivUrmwertdat = new String();
        this.ivVar = new String();
        this.ivVermiettxt = new String();
        this.ivWhrg = new String();
        this.ivZusatz = new String();
        this.ivKonsortialanteil = new String();
        this.ivBlhwertinit = new String();
        this.ivFinobjnr = new String();
        this.ivKadr = new String();
    }

    /**
     * @return the anzgew
     */
    public String getAnzgew() {
        return this.ivAnzgew;
    }

    /**
     * @param pvAnzgew the anzgew to set
     */
    public void setAnzgew(String pvAnzgew) {
        this.ivAnzgew = pvAnzgew;
    }

    /**
     * @return the anzobj
     */
    public String getAnzobj() {
        return this.ivAnzobj;
    }

    /**
     * @param pvAnzobj the anzobj to set
     */
    public void setAnzobj(String pvAnzobj) {
        this.ivAnzobj = pvAnzobj;
    }

    /**
     * @return the anzwhg
     */
    public String getAnzwhg() {
        return this.ivAnzwhg;
    }

    /**
     * @param pvAnzwhg the anzwhg to set
     */
    public void setAnzwhg(String pvAnzwhg) {
        this.ivAnzwhg = pvAnzwhg;
    }

    /**
     * @return the ausldat
     */
    public String getAusldat() {
        return this.ivAusldat;
    }

    /**
     * @param pvAusldat the ausldat to set
     */
    public void setAusldat(String pvAusldat) {
        this.ivAusldat = pvAusldat;
    }

    /**
     * @return the ausst
     */
    public String getAusst() {
        return this.ivAusst;
    }

    /**
     * @param pvAusst the ausst to set
     */
    public void setAusst(String pvAusst) {
        this.ivAusst = pvAusst;
    }

    /**
     * @return the belaus
     */
    public String getBelaus() {
        return this.ivBelaus;
    }

    /**
     * @param pvBelaus the belaus to set
     */
    public void setBelaus(String pvBelaus) {
        this.ivBelaus = pvBelaus;
    }

    /**
     * @return the belausmw
     */
    public String getBelausmw() {
        return this.ivBelausmw;
    }

    /**
     * @param pvBelausmw the belausmw to set
     */
    public void setBelausmw(String pvBelausmw) {
        this.ivBelausmw = pvBelausmw;
    }

    /**
     * @return the bewdat
     */
    public String getBewdat() {
        return this.ivBewdat;
    }

    /**
     * @param pvBewdat the bewdat to set
     */
    public void setBewdat(String pvBewdat) {
        this.ivBewdat = pvBewdat;
    }

    /**
     * @return the bewerter
     */
    public String getBewerter() {
        return this.ivBewerter;
    }

    /**
     * @param pvBewerter the bewerter to set
     */
    public void setBewerter(String pvBewerter) {
        this.ivBewerter = pvBewerter;
    }

    /**
     * @return the bez
     */
    public String getBez() {
        return this.ivBez;
    }

    /**
     * @param pvBez the bez to set
     */
    public void setBez(String pvBez) {
        this.ivBez = pvBez;
    }

    /**
     * @return the branche
     */
    public String getBranche() {
        return this.ivBranche;
    }

    /**
     * @param pvBranche the branche to set
     */
    public void setBranche(String pvBranche) {
        this.ivBranche = pvBranche;
    }

    /**
     * @return the branchetxt
     */
    public String getBranchetxt() {
        return this.ivBranchetxt;
    }

    /**
     * @param pvBranchetxt the branchetxt to set
     */
    public void setBranchetxt(String pvBranchetxt) {
        this.ivBranchetxt = pvBranchetxt;
    }

    /**
     * @return the brmiete
     */
    public String getBrmiete() {
        return this.ivBrmiete;
    }

    /**
     * @param pvBrmiete the brmiete to set
     */
    public void setBrmiete(String pvBrmiete) {
        this.ivBrmiete = pvBrmiete;
    }

    /**
     * @return the brmmiete
     */
    public String getBrmmiete() {
        return this.ivBrmmiete;
    }

    /**
     * @param pvBrmmiete the brmmiete to set
     */
    public void setBrmmiete(String pvBrmmiete) {
        this.ivBrmmiete = pvBrmmiete;
    }

    /**
     * @return the brnr
     */
    public String getBrnr() {
        return this.ivBrnr;
    }

    /**
     * @param pvBrnr the brnr to set
     */
    public void setBrnr(String pvBrnr) {
        this.ivBrnr = pvBrnr;
    }

    /**
     * @return the bstatus
     */
    public String getBstatus() {
        return this.ivBstatus;
    }

    /**
     * @param pvBstatus the bstatus to set
     */
    public void setBstatus(String pvBstatus) {
        this.ivBstatus = pvBstatus;
    }

    /**
     * @return the bwert
     */
    public String getBwert() {
        return this.ivBwert;
    }

    /**
     * @param pvBwert the bwert to set
     */
    public void setBwert(String pvBwert) 
    {
        this.ivBwert = pvBwert;
    }
 
    /**
     * @return the bwert
     */
    public String getBwertdat() {
        return this.ivBwertdat;
    }

    /**
     * @param pvBwertdat the bwert to set
     */
    public void setBwertdat(String pvBwertdat) 
    {
        this.ivBwertdat = pvBwertdat;
    }

    /**
     * @return the contkap
     */
    public String getContkap() {
        return this.ivContkap;
    }

    /**
     * @param pvContkap the contkap to set
     */
    public void setContkap(String pvContkap) {
        this.ivContkap = pvContkap;
    }

    /**
     * @return the dist
     */
    public String getDist() {
        return this.ivDist;
    }

    /**
     * @param pvDist the dist to set
     */
    public void setDist(String pvDist) {
        this.ivDist = pvDist;
    }

    /**
     * @return the dscr
     */
    public String getDscr() {
        return this.ivDscr;
    }

    /**
     * @param pvDscr the dscr to set
     */
    public void setDscr(String pvDscr) {
        this.ivDscr = pvDscr;
    }

    /**
     * @return the eigtyp
     */
    public String getEigtyp() {
        return this.ivEigtyp;
    }

    /**
     * @param pvEigtyp the eigtyp to set
     */
    public void setEigtyp(String pvEigtyp) {
        this.ivEigtyp = pvEigtyp;
    }

    /**
     * @return the ergaenztxt
     */
    public String getErgaenztxt() {
        return this.ivErgaenztxt;
    }

    /**
     * @param pvErgaenztxt the ergaenztxt to set
     */
    public void setErgaenztxt(String pvErgaenztxt) {
        this.ivErgaenztxt = pvErgaenztxt;
    }

    /**
     * @return the ertragsf
     */
    public String getErtragsf() {
        return this.ivErtragsf;
    }

    /**
     * @param pvErtragsf the ertragsf to set
     */
    public void setErtragsf(String pvErtragsf) {
        this.ivErtragsf = pvErtragsf;
    }

    /**
     * @return the ewert
     */
    public String getEwert() {
        return this.ivEwert;
    }

    /**
     * @param pvEwert the ewert to set
     */
    public void setEwert(String pvEwert) {
        this.ivEwert = pvEwert;
    }

    /**
     * @return the fertstdat
     */
    public String getFertstdat() {
        return this.ivFertstdat;
    }

    /**
     * @param pvFertstdat the fertstdat to set
     */
    public void setFertstdat(String pvFertstdat) {
        this.ivFertstdat = pvFertstdat;
    }

    /**
     * @return the flottnr
     */
    public String getFlottnr() {
        return this.ivFlottnr;
    }

    /**
     * @param pvFlottnr the flottnr to set
     */
    public void setFlottnr(String pvFlottnr) {
        this.ivFlottnr = pvFlottnr;
    }

    /**
     * @return the flut
     */
    public String getFlut() {
        return this.ivFlut;
    }

    /**
     * @param pvFlut the flut to set
     */
    public void setFlut(String pvFlut) {
        this.ivFlut = pvFlut;
    }

    /**
     * @return the foerdart
     */
    public String getFoerdart() {
        return this.ivFoerdart;
    }

    /**
     * @param pvFoerdart the foerdart to set
     */
    public void setFoerdart(String pvFoerdart) {
        this.ivFoerdart = pvFoerdart;
    }

    /**
     * @return the ftyp
     */
    public String getFtyp() {
        return this.ivFtyp;
    }

    /**
     * @param pvFtyp the ftyp to set
     */
    public void setFtyp(String pvFtyp) {
        this.ivFtyp = pvFtyp;
    }

    /**
     * @return the gebiet
     */
    public String getGebiet() {
        return this.ivGebiet;
    }

    /**
     * @param pvGebiet the gebiet to set
     */
    public void setGebiet(String pvGebiet) {
        this.ivGebiet = pvGebiet;
    }

    /**
     * @return the gem
     */
    public String getGem() {
        return this.ivGem;
    }

    /**
     * @param pvGem the gem to set
     */
    public void setGem(String pvGem) {
        this.ivGem = pvGem;
    }

    /**
     * @return the geschw
     */
    public String getGeschw() {
        return this.ivGeschw;
    }

    /**
     * @param pvGeschw the geschw to set
     */
    public void setGeschw(String pvGeschw) {
        this.ivGeschw = pvGeschw;
    }

    /**
     * @return the geteilt
     */
    public String getGeteilt() {
        return this.ivGeteilt;
    }

    /**
     * @param pvGeteilt the geteilt to set
     */
    public void setGeteilt(String pvGeteilt) {
        this.ivGeteilt = pvGeteilt;
    }

    /**
     * @return the hausnr
     */
    public String getHausnr() {
        return this.ivHausnr;
    }

    /**
     * @param pvHausnr the hausnr to set
     */
    public void setHausnr(String pvHausnr) {
        this.ivHausnr = pvHausnr;
    }

    /**
     * @return the icr
     */
    public String getIcr() {
        return this.ivIcr;
    }

    /**
     * @param pvIcr the icr to set
     */
    public void setIcr(String pvIcr) {
        this.ivIcr = pvIcr;
    }

    /**
     * @return the imo
     */
    public String getImo() {
        return this.ivImo;
    }

    /**
     * @param pvImo the imo to set
     */
    public void setImo(String pvImo) {
        this.ivImo = pvImo;
    }

    /**
     * @return the invtyp
     */
    public String getInvtyp() {
        return this.ivInvtyp;
    }

    /**
     * @param pvInvtyp the invtyp to set
     */
    public void setInvtyp(String pvInvtyp) {
        this.ivInvtyp = pvInvtyp;
    }

    /**
     * @return the jahr
     */
    public String getJahr() {
        return this.ivJahr;
    }

    /**
     * @param pvJahr the jahr to set
     */
    public void setJahr(String pvJahr) {
        this.ivJahr = pvJahr;
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
     * @return the kdat
     */
    public String getKdat() {
        return this.ivKdat;
    }

    /**
     * @param pvKdat the kdat to set
     */
    public void setKdat(String pvKdat) {
        this.ivKdat = pvKdat;
    }

    /**
     * @return the kpreis
     */
    public String getKpreis() {
        return this.ivKpreis;
    }

    /**
     * @param pvKpreis the kpreis to set
     */
    public void setKpreis(String pvKpreis) {
        this.ivKpreis = pvKpreis;
    }

    /**
     * @return the kreisnr
     */
    public String getKreisnr() {
        return this.ivKreisnr;
    }

    /**
     * @param pvKreisnr the kreisnr to set
     */
    public void setKreisnr(String pvKreisnr) {
        this.ivKreisnr = pvKreisnr;
    }

    /**
     * @return the land
     */
    public String getLand() {
        return this.ivLand;
    }

    /**
     * @param pvLand the land to set
     */
    public void setLand(String pvLand) {
        this.ivLand = pvLand;
    }

    /**
     * @return the modjahr
     */
    public String getModjahr() {
        return this.ivModjahr;
    }

    /**
     * @param pvModjahr the modjahr to set
     */
    public void setModjahr(String pvModjahr) {
        this.ivModjahr = pvModjahr;
    }

    /**
     * @return the muster
     */
    public String getMuster() {
        return this.ivMuster;
    }

    /**
     * @param pvMuster the muster to set
     */
    public void setMuster(String pvMuster) {
        this.ivMuster = pvMuster;
    }

    /**
     * @return the mwert
     */
    public String getMwert() {
        return this.ivMwert;
    }

    /**
     * @param pvMwert the mwert to set
     */
    public void setMwert(String pvMwert) {
        this.ivMwert = pvMwert;
    }

    /**
     * @return the mwertber
     */
    public String getMwertber() {
        return this.ivMwertber;
    }

    /**
     * @param pvMwertber the mwertber to set
     */
    public void setMwertber(String pvMwertber) {
        this.ivMwertber = pvMwertber;
    }

    /**
     * @return the mwertdat
     */
    public String getMwertdat() {
        return this.ivMwertdat;
    }

    /**
     * @param pvMwertdat the mwertdat to set
     */
    public void setMwertdat(String pvMwertdat) {
        this.ivMwertdat = pvMwertdat;
    }

    /**
     * @return the nart
     */
    public String getNart() {
        return this.ivNart;
    }

    /**
     * @param pvNart the nart to set
     */
    public void setNart(String pvNart) {
        this.ivNart = pvNart;
    }

    /**
     * @return the ntmiete
     */
    public String getNtmiete() {
        return this.ivNtmiete;
    }

    /**
     * @param pvNtmiete the ntmiete to set
     */
    public void setNtmiete(String pvNtmiete) {
        this.ivNtmiete = pvNtmiete;
    }

    /**
     * @return the ntmmiete
     */
    public String getNtmmiete() {
        return this.ivNtmmiete;
    }

    /**
     * @param pvNtmmiete the ntmmiete to set
     */
    public void setNtmmiete(String pvNtmmiete) {
        this.ivNtmmiete = pvNtmmiete;
    }

    /**
     * @return the ogrp
     */
    public String getOgrp() {
        return this.ivOgrp;
    }

    /**
     * @param pvOgrp the ogrp to set
     */
    public void setOgrp(String pvOgrp) {
        this.ivOgrp = pvOgrp;
    }

    /**
     * @return the ort
     */
    public String getOrt() {
        return this.ivOrt;
    }

    /**
     * @param pvOrt the ort to set
     */
    public void setOrt(String pvOrt) {
        this.ivOrt = pvOrt;
    }

    /**
     * @return the plz
     */
    public String getPlz() {
        return this.ivPlz;
    }

    /**
     * @param pvPlz the plz to set
     */
    public void setPlz(String pvPlz) {
        this.ivPlz = pvPlz;
    }

    /**
     * @return the qmges
     */
    public String getQmges() {
        return this.ivQmges;
    }

    /**
     * @param pvQmges the qmges to set
     */
    public void setQmges(String pvQmges) {
        this.ivQmges = pvQmges;
    }

    /**
     * @return the qmgew
     */
    public String getQmgew() {
        return this.ivQmgew;
    }

    /**
     * @param pvQmgew the qmgew to set
     */
    public void setQmgew(String pvQmgew) {
        this.ivQmgew = pvQmgew;
    }

    /**
     * @return the qmgrund
     */
    public String getQmgrund() {
        return this.ivQmgrund;
    }

    /**
     * @param pvQmgrund the qmgrund to set
     */
    public void setQmgrund(String pvQmgrund) {
        this.ivQmgrund = pvQmgrund;
    }

    /**
     * @return the qmwo
     */
    public String getQmwo() {
        return this.ivQmwo;
    }

    /**
     * @param pvQmwo the qmwo to set
     */
    public void setQmwo(String pvQmwo) {
        this.ivQmwo = pvQmwo;
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
     * @return the risikotxt
     */
    public String getRisikotxt() {
        return this.ivRisikotxt;
    }

    /**
     * @param pvRisikotxt the risikotxt to set
     */
    public void setRisikotxt(String pvRisikotxt) {
        this.ivRisikotxt = pvRisikotxt;
    }

    /**
     * 
     * @return
     */
    public String getSbrnr() 
    {
		return ivSbrnr;
	}

    /**
     * 
     * @param ivSbrnr
     */
	public void setSbrnr(String ivSbrnr) 
	{
		this.ivSbrnr = ivSbrnr;
	}

	/**
     * @return the sn
     */
    public String getSn() {
        return this.ivSn;
    }

    /**
     * @param pvSn the sn to set
     */
    public void setSn(String pvSn) {
        this.ivSn = pvSn;
    }

    /**
     * @return the speigtyp
     */
    public String getSpeigtyp() {
        return this.ivSpeigtyp;
    }

    /**
     * @param pvSpeigtyp the speigtyp to set
     */
    public void setSpeigtyp(String pvSpeigtyp) {
        this.ivSpeigtyp = pvSpeigtyp;
    }

    /**
     * @return the stockw
     */
    public String getStockw() {
        return this.ivStockw;
    }

    /**
     * @param pvStockw the stockw to set
     */
    public void setStockw(String pvStockw) {
        this.ivStockw = pvStockw;
    }

    /**
     * @return the str
     */
    public String getStr() {
        return this.ivStr;
    }

    /**
     * @param pvStr the str to set
     */
    public void setStr(String pvStr) {
        this.ivStr = pvStr;
    }

    /**
     * @return the styp
     */
    public String getStyp() {
        return this.ivStyp;
    }

    /**
     * @param pvStyp the styp to set
     */
    public void setStyp(String pvStyp) {
        this.ivStyp = pvStyp;
    }

    /**
     * @return the sumfrsp
     */
    public String getSumfrsp() {
        return this.ivSumfrsp;
    }

    /**
     * @param pvSumfrsp the sumfrsp to set
     */
    public void setSumfrsp(String pvSumfrsp) {
        this.ivSumfrsp = pvSumfrsp;
    }

    /**
     * @return the sumnbetrag
     */
    public String getSumnbetrag() {
        return this.ivSumnbetrag;
    }

    /**
     * @param pvSumnbetrag the sumnbetrag to set
     */
    public void setSumnbetrag(String pvSumnbetrag) {
        this.ivSumnbetrag = pvSumnbetrag;
    }

    /**
     * @return the sumrdpf
     */
    public String getSumrdpf() {
        return this.ivSumrdpf;
    }

    /**
     * @param pvSumrdpf the sumrdpf to set
     */
    public void setSumrdpf(String pvSumrdpf) {
        this.ivSumrdpf = pvSumrdpf;
    }

    /**
     * @return the sumrkap
     */
    public String getSumrkap() {
        return this.ivSumrkap;
    }

    /**
     * @param pvSumrkap the sumrkap to set
     */
    public void setSumrkap(String pvSumrkap) {
        this.ivSumrkap = pvSumrkap;
    }

    /**
     * @return the sumsdpf
     */
    public String getSumsdpf() {
        return this.ivSumsdpf;
    }

    /**
     * @param pvSumsdpf the sumsdpf to set
     */
    public void setSumsdpf(String pvSumsdpf) {
        this.ivSumsdpf = pvSumsdpf;
    }

    /**
     * @return the swert
     */
    public String getSwert() {
        return this.ivSwert;
    }

    /**
     * @param pvSwert the swert to set
     */
    public void setSwert(String pvSwert) {
        this.ivSwert = pvSwert;
    }

    /**
     * @return the tdw
     */
    public String getTdw() {
        return this.ivTdw;
    }

    /**
     * @param pvTdw the tdw to set
     */
    public void setTdw(String pvTdw) {
        this.ivTdw = pvTdw;
    }

    /**
     * @return the urmwert
     */
    public String getUrmwert() {
        return this.ivUrmwert;
    }

    /**
     * @param pvUrmwert the urmwert to set
     */
    public void setUrmwert(String pvUrmwert) {
        this.ivUrmwert = pvUrmwert;
    }

    /**
     * @return the urmwertdat
     */
    public String getUrmwertdat() {
        return this.ivUrmwertdat;
    }

    /**
     * @param pvUrmwertdat the urmwertdat to set
     */
    public void setUrmwertdat(String pvUrmwertdat) {
        this.ivUrmwertdat = pvUrmwertdat;
    }

    /**
     * @return the var
     */
    public String getVar() {
        return this.ivVar;
    }

    /**
     * @param pvVar the var to set
     */
    public void setVar(String pvVar) {
        this.ivVar = pvVar;
    }

    /**
     * @return the vermiettxt
     */
    public String getVermiettxt() {
        return this.ivVermiettxt;
    }

    /**
     * @param pvVermiettxt the vermiettxt to set
     */
    public void setVermiettxt(String pvVermiettxt) {
        this.ivVermiettxt = pvVermiettxt;
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
     * @return the zusatz
     */
    public String getZusatz() {
        return this.ivZusatz;
    }

    /**
     * @param pvZusatz the zusatz to set
     */
    public void setZusatz(String pvZusatz) {
        this.ivZusatz = pvZusatz;
    }

    /**
     * @return the konsortialanteil
     */
    public String getKonsortialanteil() {
        return this.ivKonsortialanteil;
    }

    /**
     * @param pvKonsortialanteil the konsortialanteil to set
     */
    public void setKonsortialanteil(String pvKonsortialanteil) {
        this.ivKonsortialanteil = pvKonsortialanteil;
    }

    /**
     * @return the blhwertinit
     */
    public String getBlhwertinit() {
        return this.ivBlhwertinit;
    }

    /**
     * @param pvBlhwertinit the blhwertinit to set
     */
    public void setBlhwertinit(String pvBlhwertinit) {
        this.ivBlhwertinit = pvBlhwertinit;
    }

    /**
     * @return the finobjnr
     */
    public String getFinobjnr() {
        return this.ivFinobjnr;
    }

    /**
     * @param pvFinobjnr the finobjnr to set
     */
    public void setFinobjnr(String pvFinobjnr) {
        this.ivFinobjnr = pvFinobjnr;
    }

    /**
     * @return the kadr
     */
    public String getKadr() {
        return this.ivKadr;
    }

    /**
     * @param pvKadr the kadr to set
     */
    public void setKadr(String pvKadr) {
        this.ivKadr = pvKadr;
    }

    /**
     * TXSPfandobjektDatenStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("        <txsi:podaten ");
    }
    
    /**
     * TXSPfandobjektDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();

        if (this.ivBez.length() > 0)
        {
            lvHelpXML.append("bez=\"" + this.ivBez + "\" ");
        }
        if (this.ivBrnr.length() > 0)
        {
            lvHelpXML.append("brnr=\"" + this.ivBrnr + "\" ");
        }
        if (this.ivBstatus.length() > 0)
        {
            lvHelpXML.append("bstatus=\"" + this.ivBstatus + "\" ");            
        }
        if (this.ivImo.length() > 0)
        {
            lvHelpXML.append("imo=\"" + this.ivImo + "\" ");
        }
        if (this.ivEigtyp.length() > 0)
        {
            lvHelpXML.append("eigtyp=\"" + this.ivEigtyp + "\" ");
        }
        if (this.ivErtragsf.length() > 0)
        {
          lvHelpXML.append("ertragsf=\"" + this.ivErtragsf + "\" ");
        }
        if (this.ivGebiet.length() > 0)
        {
          lvHelpXML.append("gebiet=\"" + this.ivGebiet + "\" ");
        }
        if (this.ivJahr.length() > 0)
        {
            lvHelpXML.append("jahr=\"" + this.ivJahr + "\" ");
        }
        if (this.ivKat.length() > 0)
        {
            lvHelpXML.append("kat=\"" + this.ivKat + "\" ");
        }
        if (this.ivLand.length() > 0)
        {
            lvHelpXML.append("land=\"" + this.ivLand + "\" ");
        }
        if (this.ivSwert.length() > 0)
        {
            lvHelpXML.append("swert=\"" + this.ivSwert + "\" ");
        }
        if (this.ivEwert.length() > 0)
        {
          lvHelpXML.append("ewert=\"" + this.ivEwert + "\" ");
        }
        if (this.ivBwert.length() > 0)
        {
          lvHelpXML.append("bwert=\"" + this.ivBwert + "\" ");
        }
        if (this.ivBwertdat.length() > 0)
        {
            lvHelpXML.append("bwertdat=\"" + this.ivBwertdat + "\" ");
        }
        if (this.ivDist.length() > 0)
        {
            lvHelpXML.append("dist=\"" + this.ivDist + "\" ");
        }
        if (this.ivNart.length() > 0)
        {
          lvHelpXML.append("nart=\"" + this.ivNart + "\" ");
        }
        if (this.ivOgrp.length() > 0)
        {
          lvHelpXML.append("ogrp=\"" + this.ivOgrp + "\" ");
        }
        if (this.ivOrt.length() > 0)
        {
          lvHelpXML.append("ort=\"" + String2XML.change2XML(this.ivOrt) + "\" ");
        }
        if (this.ivPlz.length() > 0)
        {
          lvHelpXML.append("plz=\"" + this.ivPlz + "\" ");
        }
        if (this.ivSpeigtyp.length() > 0)
        {
           lvHelpXML.append("speigtyp=\"" + this.ivSpeigtyp + "\" ");
        }
        if (this.ivStr.length() > 0)
        {
            lvHelpXML.append("str=\"" + String2XML.change2XML(this.ivStr) + "\" ");
        }
        if (this.ivHausnr.length() > 0)
        {
            lvHelpXML.append("hausnr=\"" + String2XML.change2XML(this.ivHausnr) + "\" ");
        }
        if (this.ivStyp.length() > 0)
        {
            lvHelpXML.append("styp=\"" + this.ivStyp + "\" ");
        }
        if (this.ivFtyp.length() > 0)
        {
            lvHelpXML.append("ftyp=\"" + this.ivFtyp + "\" ");
        }
        if (this.ivAusldat.length() > 0)
        {
            lvHelpXML.append("ausldat=\"" + this.ivAusldat + "\" ");
        }
        if (this.ivFertstdat.length() > 0)
        {
            lvHelpXML.append("fertstdat=\"" + this.ivFertstdat + "\" ");
        }
        if (this.ivMuster.length() > 0)
        {
            lvHelpXML.append("muster=\"" + this.ivMuster + "\" ");
        }
        if (this.ivVar.length() > 0)
        {
            lvHelpXML.append("var=\"" + this.ivVar + "\" ");
        }
        if (this.ivSbrnr.length() > 0)
        {
            lvHelpXML.append("sbrnr=\"" + this.ivSbrnr + "\" ");
        }        
        if (this.ivSn.length() > 0)
        {
            lvHelpXML.append("sn=\"" + this.ivSn + "\" ");
        }
        if (this.ivKonsortialanteil.length() > 0)
        {
            lvHelpXML.append("konsortialanteil=\"" + this.ivKonsortialanteil + "\" ");
        }
        if (this.ivBlhwertinit.length() > 0)
        {
            lvHelpXML.append("blhwertinit=\"" + this.ivBlhwertinit + "\" ");           
        }
        if (this.ivFinobjnr.length() > 0)
        {
            lvHelpXML.append("finobjnr=\"" + this.ivFinobjnr + "\" ");
        }
        if (this.ivKadr.length() > 0)
        {
            lvHelpXML.append("kadr=\"" + this.ivKadr + "\" ");
        }
        if (this.ivZusatz.length() > 0)
        {
            lvHelpXML.append("zusatz=\"" + String2XML.change2XML(this.ivZusatz) + "\" ");
        }  
        if (this.ivBwert.length() > 0 || this.ivEwert.length() > 0 || this.ivSwert.length() > 0)
        {
            lvHelpXML.append("whrg=\"" + this.ivWhrg + "\"");
        }
        lvHelpXML.append(">");
        return lvHelpXML;     
    }
    
    /**
     * TXSPfandobjektDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("</txsi:podaten>\n");
    }

    /**
     * @param darlehen 
     * @param pvObj 
     * 
     */
    public void importDarlehen(Darlehen darlehen, Sicherungsobjekt pvObj) 
    {
        /* Sicherheitenschlüssel auf zwei Stellen ... */
        //String sSiSchl = new String();
        //sSiSchl = darlehen.getSicherheitenSchluessel().substring(1,3);
    
        /* => TXX404/405 bestücken..... nur bei hypothekar .... */
        /* geht nur mit Bauf bzw. iSIO   ..... */
        /* Bestückung von Pfandobjekten etc... */
        String lvLand = new String();
        String lvStaat = new String();
        //String sPfGruppe = new String();
        //String sPfArt = new String();
        //String cErtragsKz = new String();

        if (pvObj != null)
        { /* Es ist etwas da .... */
         /* Belegung aus SI/SIO-Strukturen */
         /* Einzelne Felder ermitteln ... */
         String lvBuffer1 = new String();

         lvLand = "undefiniert"; /* nur Deutschland wird def. */
         /* Sonderfälle LandID umsetzen..bspw. Deutschland */
         if (!pvObj.getBeleihungsgebiet().isEmpty() &&
             !pvObj.getBeleihungsgebiet().equals("00000"))
         { /* Es ist was da ..*/
          lvBuffer1 = pvObj.getBeleihungsgebiet();
          if (lvBuffer1.startsWith("91") ||
              lvBuffer1.startsWith("99"))
          { /* Nicht Deutschland */
           lvStaat = lvBuffer1.substring(2,5);
           if (lvStaat.equals("023"))
           { /* Melilla ....*/
            lvStaat = "022";
           } /* Melilla */
           if (lvStaat.equals("106") ||
               lvStaat.equals("107") ||
               lvStaat.equals("108") ||
               lvStaat.equals("109"))
           { /* GrossBri.+Inseln*/
            lvStaat = "006";
           } /* GrossBri.+Inseln*/
           if (lvStaat.equals("626") ||
               lvStaat.equals("858") ||
               StringKonverter.convertString2Int(lvStaat) > 826)
           { /*Sonstige Sonderdef.*/
            lvStaat = "999";
           } /*Sonstige Sonderdef.*/
          } /* Nicht Deutschland */
          else
          { /* Deutschland */
           lvStaat = "100";
           lvLand = new String();
           lvLand = lvBuffer1.substring(0,2);
          } /* Deutschland */
         } /* Es ist was da ..*/
         else
         { /* nichts da .... */
          lvStaat = "100";
          /* Gebiet über PLZ */
          /* Hier ist die PLZ für das Land interessant */
          int lvRetGebiet = -1;
          lvRetGebiet = ValueMapping.PLZ2Land(darlehen.getInstitutsnummer(), pvObj.getPostleitzahl());
          /* Definitionen in der Codetabelle sind falsch ! ...... */
          /* Die führende 100 führt zum Ergebnis "undefiniert" ...*/
          /* Vielleicht wird es später wieder so ....             */
          if (lvRetGebiet == -1)
          {
           lvLand = "undefiniert";
          }
          else
          {
              if (lvRetGebiet < 10)
              {
                  lvLand = "0"; 
              }
              lvLand = lvLand + (new Integer(lvRetGebiet)).toString();
          }
          //if (DEBUG) { /* Test */
          System.out.println("PLZ2Land<" + lvRetGebiet +">-Inst<" + darlehen.getInstitutsnummer() + 
                             ">-PLZ<" + pvObj.getPostleitzahl() + ">=<" + lvLand + ">");
          //} /* DEBUG-Info */
         } /* nichts da .... */
         /* Pfandobjektgruppe / Pfandobjektnutzungsart / Eigentumstyp */
         /* Eigentumstyp aktuell gar nicht zu mappen !! */
         //String sPfEigTyp = new String();
         //sBuffer1 = new String();
         //System.out.println("Length: " + sObj.getObjektart().length() + " Objart: " + sObj.getObjektart());
         //if (!sObj.getObjektart().isEmpty())
         //{
         //sBuffer1 = sObj.getObjektart().substring(0,3);
         //sPfArt = "0";
         //if (sBuffer1.substring(1,3).equals("67") ||
         //    sBuffer1.substring(1,3).equals("69") ||
         //    sBuffer1.substring(1,3).equals("70") ||
         //    sBuffer1.substring(1,3).equals("75"))
         //{ /* Sonderfall 1 */
             //sPfGruppe = "094";
         //    if (sBuffer1.substring(1,3).equals("70"))
         //    { /* Schiffe */
         //        sPfArt = "6";
         //    } /* Schiffe */
         //} /* Sonderfall 1 */
         //else
         //{ /* Nicht Sonderfall 1 */
         // if (sBuffer1.charAt(1) ==  '2' ||
         //     sBuffer1.charAt(1) ==  '3' ||
         //     sBuffer1.charAt(1) ==  '4')
         // { /* Sonderfall 2 - Landwirtschaft */
           //sPfGruppe = "020";
         //  sPfArt = "3";
         // } /* Sonderfall 2 - Landwirtschaft */
         // else
         // { /* Nicht Sonderfall 1 + 2 */
           /* Generelle Übernahme ... */
           //sPfGruppe = "0";
           //sPfGruppe = sPfGruppe + sBuffer1.substring(1,3);
           /* Ein-/Zweifamilienhäuser 016 -> 011 */
           /* Eigentumswohnungen      017 -> 013 */
           //if (sBuffer1.substring(1,3).equals("16"))
           //{
            //sPfGruppe = "011";
           //}
           //if (sBuffer1.substring(1,3).equals("17"))
           //{
            //sPfGruppe = "013";
           //}
           //if (sBuffer1.charAt(1) ==  '5' ||
           //    sBuffer1.charAt(1) ==  '6')
           //{ /* grobe Def. */
           // sPfArt = "2";
           //} /* grobe Def. - gewerblich genutzte GS */
           /* sonstige Grundstücke */
           //if (sBuffer1.substring(1,3).equals("60"))
           //{ /* Sonstige GS */
           // sPfArt = "5";
           //} /* Sonstige GS */
           //if (sBuffer1.charAt(1) ==  '1')
           //{ /* Häuser/Grundstücke ..*/
            //if (sBuffer1.charAt(2) == '0' ||
            //    sBuffer1.charAt(2) == '4')
            //{ /* Bauplatz */
            // sPfArt = "4";
            //} /* Bauplatz */
            //else
            //{ /* Kein Bauplatz .. */
            // sPfArt = "1"; /* Wohngebäde */
            //} /* Kein Bauplatz .. */
           //} /* Häuser/Grundstücke ..*/
          //} /* Nicht Sonderfall 1 + 2*/
         //} /* Nicht Sonderfall 1 */
          
         // Neue Mappingregeln eingefuegt - CT 26.03.2012
         //if (sBuffer1.substring(1,3).equals("54") || sBuffer1.substring(1,3).equals("59") ||
         //   sBuffer1.substring(1,3).equals("60") || sBuffer1.substring(1,3).equals("67"))
         //{
         //   sPfGruppe = "0" + sBuffer1.substring(1,3);
         //}
        }
        //sPfArt = ValueMapping.mapNutzungsartDarlehen(sObj.getObjektart());
        //cErtragsKz = "0";
        //if (sPfArt.startsWith("4"))
        //{
        // cErtragsKz = "4"; /* Bauplatz */
        //}
        
        // Der Eigentumstyp kann aus Darlehen nicht ermittelt werden
        // Ertragsfaehigkeit wird nicht mehr aus DarKa angeliefert - CT 15.06.2012
        //this.ertragsf = ValueMapping.changeErtragsfaehigkeit(cErtragsKz);
        this.ivGebiet = ValueMapping.changeGebiet(lvLand);
        this.ivJahr = ValueMapping.changeBaujahr(pvObj.getBaujahr());
        this.ivKat = "1";
        this.ivLand = ValueMapping.changeLand(lvStaat);
        this.ivSwert = pvObj.getSachwert();
        this.ivEwert = pvObj.getErtragswert();
        this.ivBwert = pvObj.getBeleihungswert();
        // Nutzungsart wird nicht mehr aus Darka angeliefert - CT 05.06.2012
        //this.nart = sPfArt;
        // Objektgruppe wird nicht mehr aus Darka angeliefert - CT 05.06.2012
        //this.ogrp = ValueMapping.mapObjektgruppeDarlehen(sObj.getObjektart());
        this.ivOrt = pvObj.getOrt().trim();
        this.ivPlz = pvObj.getPostleitzahl();
        //this.speigetyp
        this.ivStr = pvObj.getStrasse().trim();
        this.ivWhrg = darlehen.getSatzwaehrung();
    }

    /**
     * @param darlehen
     * @param vObj
     */
    public void importDarlehen(Darlehen darlehen, OSPVermoegensobjekt vObj) 
    {
        /* Sicherheitenschlüssel auf zwei Stellen ... */
        //String sSiSchl = new String();
        //sSiSchl = darlehen.getSicherheitenSchluessel().substring(1,3);
    
        /* => TXX404/405 bestücken..... nur bei hypothekar .... */
        /* geht nur mit Bauf bzw. iSIO   ..... */
        /* Bestückung von Pfandobjekten etc... */
        String lvLand = new String();
        String lvStaat = new String();
        //String sPfGruppe = new String();
        //String sPfArt = new String();
        //String cErtragsKz = new String();

        if (vObj != null)
        { /* Es ist etwas da .... */
         /* Belegung aus SI/SIO-Strukturen */
         /* Einzelne Felder ermitteln ... */
         //String sBuffer1 = new String();

         //sLand = new String(); /* nur Deutschland wird def. */

          lvStaat = "100";
          /* Gebiet über PLZ */
          /* Hier ist die PLZ für das Land interessant */
          int lvRetGebiet = -1;
          lvRetGebiet = ValueMapping.PLZ2Land(darlehen.getInstitutsnummer(), vObj.getPlz());
          /* Definitionen in der Codetabelle sind falsch ! ...... */
          /* Die führende 100 führt zum Ergebnis "undefiniert" ...*/
          /* Vielleicht wird es später wieder so ....             */
          if ( lvRetGebiet == -1 )
          {
           lvLand = "undefiniert";
          }
          else
          {
              if (lvRetGebiet < 10)
              {
                  lvLand = "0"; 
              }
              lvLand = lvLand + (new Integer(lvRetGebiet)).toString();
          }
          //if (DEBUG) { /* Test */
          System.out.println("PLZ2Land<" + lvRetGebiet +">-Inst<" + darlehen.getInstitutsnummer() + 
                             ">-PLZ<" + vObj.getPlz() + ">=<" + lvLand + ">");
          //} /* DEBUG-Info */
         } /* nichts da .... */
         /* Pfandobjektgruppe / Pfandobjektnutzungsart / Eigentumstyp */
         /* Eigentumstyp aktuell gar nicht zu mappen !! */
         //String sPfEigTyp = new String();
         //sBuffer1 = new String();
         //System.out.println("Length: " + sObj.getObjektart().length() + " Objart: " + sObj.getObjektart());
         //if (!sObj.getObjektart().isEmpty())
         //{
         //sBuffer1 = sObj.getObjektart().substring(0,3);
         //sPfArt = "0";
         //if (sBuffer1.substring(1,3).equals("67") ||
         //    sBuffer1.substring(1,3).equals("69") ||
         //    sBuffer1.substring(1,3).equals("70") ||
         //    sBuffer1.substring(1,3).equals("75"))
         //{ /* Sonderfall 1 */
             //sPfGruppe = "094";
         //    if (sBuffer1.substring(1,3).equals("70"))
         //    { /* Schiffe */
         //        sPfArt = "6";
         //    } /* Schiffe */
         //} /* Sonderfall 1 */
         //else
         //{ /* Nicht Sonderfall 1 */
         // if (sBuffer1.charAt(1) ==  '2' ||
         //     sBuffer1.charAt(1) ==  '3' ||
         //     sBuffer1.charAt(1) ==  '4')
         // { /* Sonderfall 2 - Landwirtschaft */
           //sPfGruppe = "020";
         //  sPfArt = "3";
         // } /* Sonderfall 2 - Landwirtschaft */
         // else
         // { /* Nicht Sonderfall 1 + 2 */
           /* Generelle Übernahme ... */
           //sPfGruppe = "0";
           //sPfGruppe = sPfGruppe + sBuffer1.substring(1,3);
           /* Ein-/Zweifamilienhäuser 016 -> 011 */
           /* Eigentumswohnungen      017 -> 013 */
           //if (sBuffer1.substring(1,3).equals("16"))
           //{
            //sPfGruppe = "011";
           //}
           //if (sBuffer1.substring(1,3).equals("17"))
           //{
            //sPfGruppe = "013";
           //}
           //if (sBuffer1.charAt(1) ==  '5' ||
           //    sBuffer1.charAt(1) ==  '6')
           //{ /* grobe Def. */
           // sPfArt = "2";
           //} /* grobe Def. - gewerblich genutzte GS */
           /* sonstige Grundstücke */
           //if (sBuffer1.substring(1,3).equals("60"))
           //{ /* Sonstige GS */
           // sPfArt = "5";
           //} /* Sonstige GS */
           //if (sBuffer1.charAt(1) ==  '1')
           //{ /* Häuser/Grundstücke ..*/
            //if (sBuffer1.charAt(2) == '0' ||
            //    sBuffer1.charAt(2) == '4')
            //{ /* Bauplatz */
            // sPfArt = "4";
            //} /* Bauplatz */
            //else
            //{ /* Kein Bauplatz .. */
            // sPfArt = "1"; /* Wohngebäde */
            //} /* Kein Bauplatz .. */
           //} /* Häuser/Grundstücke ..*/
          //} /* Nicht Sonderfall 1 + 2*/
         //} /* Nicht Sonderfall 1 */
          
         // Neue Mappingregeln eingefuegt - CT 26.03.2012
         //if (sBuffer1.substring(1,3).equals("54") || sBuffer1.substring(1,3).equals("59") ||
         //   sBuffer1.substring(1,3).equals("60") || sBuffer1.substring(1,3).equals("67"))
         //{
         //   sPfGruppe = "0" + sBuffer1.substring(1,3);
         //}
        //}
        //sPfArt = ValueMapping.mapNutzungsartDarlehen(sObj.getObjektart());
        //cErtragsKz = "0";
        //if (sPfArt.startsWith("4"))
        //{
        // cErtragsKz = "4"; /* Bauplatz */
        //}
        
        // Der Eigentumstyp kann aus Darlehen nicht ermittelt werden
        // Ertragsfaehigkeit wird nicht mehr aus DarKa angeliefert - CT 15.06.2012
        //this.ertragsf = ValueMapping.changeErtragsfaehigkeit(cErtragsKz);
        this.ivGebiet = ValueMapping.changeGebiet(lvLand);
        this.ivJahr = ValueMapping.changeBaujahr(vObj.getBaujahr());
        this.ivKat = "1";
        this.ivLand = ValueMapping.changeLand(lvStaat);
        this.ivSwert = vObj.getBodenwert();
        this.ivEwert = vObj.getErtragswert();
        this.ivBwert = vObj.getBeleihungswert();
        this.ivNart = MappingDPP.changeNutzungsart(vObj.getVerwendungsart());
        this.ivEigtyp = MappingDPP.changeEigentumstyp(vObj.getNutzungsart());
        this.ivOgrp = MappingDPP.changeObjektgruppe(vObj.getArt());
        this.ivOrt = vObj.getOrt().trim();
        this.ivPlz = vObj.getPlz();
        //this.speigetyp
        this.ivStr = vObj.getStrasse().trim();
        this.ivWhrg = vObj.getWaehrung();
    
    }
}
