/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.FRISCO2TXS;

/**
 * @author tepperc
 *
 */
@Deprecated
public class ObjektDaten 
{
    /**
     * Objekt Nr
     */
    private String ivObjektNr;
    
    /**
     * Konto-Nr / Forderung
     */
    private String ivKontonummer;
    
    /**
     * Finanzierungsnummer
     */
    private String ivFinanzierungsnummer;
    
    /**
     * Beginn der Finanzierung
     */
    private String ivBeginnFinanzierung;
    
    /**
     * Branchennummer
     */
    private String ivBranchennummer;
    
    /**
     * akt. Beleihungswert
     */
    private String ivBeleihungswertAkt;
    
    /**
     * W�hrung des akt. Beleihungswert
     */
    private String ivWaehrungBeleihungswertAkt;
    
    /**
     * Beleihungswert vom
     */
    private String ivBeleihungswertVom;
    
    /**
     * Beleihungswert Indeckungnahme
     */
    private String ivBeleihungswertInd;
    
    /**
     * W�hrung des Beleihungswerts Indeckungnahme
     */
    private String ivWaehrungBeleihungswertInd;
    
    /**
     * Beleihungswert Indeckungnahme vom 
     */
    private String ivBeleihungswertIndVom;
    
    /** 
     * Verzeichnisblattnummer
     */
    private String ivVerzeichnisblattNr;
    
    /**
     * Registerland
     */
    private String ivRegisterland;
    
    /**
     * Registerort
     */
    private String ivRegisterort;
    
    /**
     * Blatt 
     */
    private String ivBlatt;
    
    /**
     * Registereintragsnr
     */
    private String ivRegistereintragsnr;
    
    /**
     * Rang
     */
    private String ivRang;
    
    /**
     * Laufende Nr. Abteilung III
     */
    private String ivAbteilung3;
    
    /**
     * Sicherheitennummer
     */
    private String ivSicherheitenNr;
       
    /**
     * W�hrung vom Nennbetrag und Verf�gungsbetrag
     */
    private String ivWaehrungSicherheit;
    
    /**
     * urspr. Forderung / Nennbetrag
     */
    private String ivNennbetrag;
    
    /**
     * Verfuegungsbetrag
     */
    private String ivVerfuegungsbetrag;
    
    /**
     * Nennbetrag Finanzierung
     */
    private String ivNennbetragFinanzierung;
    
    /**
     * Waehrung Nennbetrag Finanzierung
     */
    private String ivWaehrungNennbetragFinanzierung;
    
    /**
     * Fertigstellungdatum
     */
    private String ivFertigstellung;
    
    /**
     * Auslieferungsdatum
     */
    private String ivAuslieferung;

    /**
     * Endedatum/Faelligkeit der Finanzierung
     */
    private String ivFaelligkeitFinanzierung; 
    
    /**
     *
     */
    public ObjektDaten() 
    {
        super();
        this.ivObjektNr = new String();
        this.ivKontonummer = new String();
        this.ivFinanzierungsnummer = new String();
        this.ivBeginnFinanzierung = new String();
        this.ivBranchennummer = new String();
        this.ivBeleihungswertAkt = new String();
        this.ivWaehrungBeleihungswertAkt = new String();
        this.ivBeleihungswertVom = new String();
        this.ivBeleihungswertInd = new String();
        this.ivWaehrungBeleihungswertInd = new String();
        this.ivBeleihungswertIndVom = new String();
        this.ivVerzeichnisblattNr = new String();
        this.ivRegisterland = new String();
        this.ivRegisterort = new String();
        this.ivBlatt = new String();
        this.ivRegistereintragsnr = new String();
        this.ivRang = new String();
        this.ivAbteilung3 = new String();
        this.ivSicherheitenNr = new String();
        this.ivNennbetrag = new String();
        this.ivVerfuegungsbetrag = new String();
        this.ivNennbetragFinanzierung = new String();
        this.ivWaehrungNennbetragFinanzierung = new String();
        this.ivFertigstellung = new String();
        this.ivAuslieferung = new String();
        this.ivFaelligkeitFinanzierung = new String();
    }

    /**
     * @return the objektNr
     */
    public String getObjektNr() {
        return this.ivObjektNr;
    }

    /**
     * @param pvObjektNr the objektNr to set
     */
    public void setObjektNr(String pvObjektNr) {
        this.ivObjektNr = pvObjektNr;
    }

    /**
     * @return the kontonummer
     */
    public String getKontonummer() {
        return this.ivKontonummer;
    }

    /**
     * @param pvKontonummer the kontonummer to set
     */
    public void setKontonummer(String pvKontonummer) {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * @return the finanzierungsnummer
     */
    public String getFinanzierungsnummer() {
        return this.ivFinanzierungsnummer;
    }

    /**
     * @param pvFinanzierungsnummer the finanzierungsnummer to set
     */
    public void setFinanzierungsnummer(String pvFinanzierungsnummer) {
        this.ivFinanzierungsnummer = pvFinanzierungsnummer;
    }

    /**
     * @return the beginnFinanzierung
     */
    public String getBeginnFinanzierung() {
        return this.ivBeginnFinanzierung;
    }

    /**
     * @param pvBeginnFinanzierung the beginnFinanzierung to set
     */
    public void setBeginnFinanzierung(String pvBeginnFinanzierung) {
        this.ivBeginnFinanzierung = pvBeginnFinanzierung;
    }

    /**
     * @return the branchennummer
     */
    public String getBranchennummer() {
        return this.ivBranchennummer;
    }

    /**
     * @param pvBranchennummer the branchennummer to set
     */
    public void setBranchennummer(String pvBranchennummer) {
        this.ivBranchennummer = pvBranchennummer;
    }

    /**
     * @return the beleihungswertAkt
     */
    public String getBeleihungswertAkt() {
        return this.ivBeleihungswertAkt;
    }

    /**
     * @param pvBeleihungswertAkt the beleihungswertAkt to set
     */
    public void setBeleihungswertAkt(String pvBeleihungswertAkt) {
        this.ivBeleihungswertAkt = pvBeleihungswertAkt;
    }

    /**
     * @return the waehrungBeleihungswertAkt
     */
    public String getWaehrungBeleihungswertAkt() {
        return this.ivWaehrungBeleihungswertAkt;
    }

    /**
     * @param pvWaehrungBeleihungswertAkt the waehrungBeleihungswertAkt to set
     */
    public void setWaehrungBeleihungswertAkt(String pvWaehrungBeleihungswertAkt) {
        this.ivWaehrungBeleihungswertAkt = pvWaehrungBeleihungswertAkt;
    }

    /**
     * @return the beleihungswertVom
     */
    public String getBeleihungswertVom() {
        return this.ivBeleihungswertVom;
    }

    /**
     * @param pvBeleihungswertVom the beleihungswertVom to set
     */
    public void setBeleihungswertVom(String pvBeleihungswertVom) {
        this.ivBeleihungswertVom = pvBeleihungswertVom;
    }

    /**
     * @return the beleihungswertInd
     */
    public String getBeleihungswertInd() {
        return this.ivBeleihungswertInd;
    }

    /**
     * @param pvBeleihungswertInd the beleihungswertInd to set
     */
    public void setBeleihungswertInd(String pvBeleihungswertInd) {
        this.ivBeleihungswertInd = pvBeleihungswertInd;
    }

    /**
     * @return the waehrungBeleihungswertInd
     */
    public String getWaehrungBeleihungswertInd() {
        return this.ivWaehrungBeleihungswertInd;
    }

    /**
     * @param pvWaehrungBeleihungswertInd the waehrungBeleihungswertInd to set
     */
    public void setWaehrungBeleihungswertInd(String pvWaehrungBeleihungswertInd) {
        this.ivWaehrungBeleihungswertInd = pvWaehrungBeleihungswertInd;
    }

    /**
     * @return the beleihungswertIndVom
     */
    public String getBeleihungswertIndVom() {
        return this.ivBeleihungswertIndVom;
    }

    /**
     * @param pvBeleihungswertIndVom the beleihungswertIndVom to set
     */
    public void setBeleihungswertIndVom(String pvBeleihungswertIndVom) {
        this.ivBeleihungswertIndVom = pvBeleihungswertIndVom;
    }

    /**
     * @return the verzeichnisblattNr
     */
    public String getVerzeichnisblattNr() {
        return this.ivVerzeichnisblattNr;
    }

    /**
     * @param pvVerzeichnisblattNr the verzeichnisblattNr to set
     */
    public void setVerzeichnisblattNr(String pvVerzeichnisblattNr) {
        this.ivVerzeichnisblattNr = pvVerzeichnisblattNr;
    }

    /**
     * @return the registerland
     */
    public String getRegisterland() {
        return this.ivRegisterland;
    }

    /**
     * @param pvRegisterland the registerland to set
     */
    public void setRegisterland(String pvRegisterland) {
        this.ivRegisterland = pvRegisterland;
    }

    /**
     * @return the registerort
     */
    public String getRegisterort() {
        return this.ivRegisterort;
    }

    /**
     * @param pvRegisterort the registerort to set
     */
    public void setRegisterort(String pvRegisterort) {
        this.ivRegisterort = pvRegisterort;
    }

    /**
     * @return the blatt
     */
    public String getBlatt() {
        return this.ivBlatt;
    }

    /**
     * @param pvBlatt the blatt to set
     */
    public void setBlatt(String pvBlatt) {
        this.ivBlatt = pvBlatt;
    }

    /**
     * @return the registereintragsnr
     */
    public String getRegistereintragsnr() {
        return this.ivRegistereintragsnr;
    }

    /**
     * @param pvRegistereintragsnr the registereintragsnr to set
     */
    public void setRegistereintragsnr(String pvRegistereintragsnr) {
        this.ivRegistereintragsnr = pvRegistereintragsnr;
    }

    /**
     * @return the rang
     */
    public String getRang() {
        return this.ivRang;
    }

    /**
     * @param pvRang the rang to set
     */
    public void setRang(String pvRang) {
        this.ivRang = pvRang;
    }

    /**
     * @return the abteilung3
     */
    public String getAbteilung3() {
        return this.ivAbteilung3;
    }

    /**
     * @param pvAbteilung3 the abteilung3 to set
     */
    public void setAbteilung3(String pvAbteilung3) {
        this.ivAbteilung3 = pvAbteilung3;
    }

    /**
     * @return the sicherheiteitenNr
     */
    public String getSicherheitenNr() {
        return this.ivSicherheitenNr;
    }

    /**
     * @param pvSicherheiteitenNr the sicherheiteitenNr to set
     */
    public void setSicherheitenNr(String pvSicherheiteitenNr) {
        this.ivSicherheitenNr = pvSicherheiteitenNr;
    }

    /**
     * @return the waehrungSicherheit
     */
    public String getWaehrungSicherheit() {
        return this.ivWaehrungSicherheit;
    }

    /**
     * @param pvWaehrungSicherheit the waehrungSicherheit to set
     */
    public void setWaehrungSicherheit(String pvWaehrungSicherheit) {
        this.ivWaehrungSicherheit = pvWaehrungSicherheit;
    }

    /**
     * @return the nennbetrag
     */
    public String getNennbetrag() {
        return this.ivNennbetrag;
    }

    /**
     * @param pvNennbetrag the nennbetrag to set
     */
    public void setNennbetrag(String pvNennbetrag) {
        this.ivNennbetrag = pvNennbetrag;
    }

    /**
     * @return the verfuegungsbetrag
     */
    public String getVerfuegungsbetrag() {
        return this.ivVerfuegungsbetrag;
    }

    /**
     * @param pvVerfuegungsbetrag the verfuegungsbetrag to set
     */
    public void setVerfuegungsbetrag(String pvVerfuegungsbetrag) {
        this.ivVerfuegungsbetrag = pvVerfuegungsbetrag;
    }

    /**
     * @return the nennbetragFinanzierung
     */
    public String getNennbetragFinanzierung() {
        return this.ivNennbetragFinanzierung;
    }

    /**
     * @param pvNennbetragFinanzierung the nennbetragFinanzierung to set
     */
    public void setNennbetragFinanzierung(String pvNennbetragFinanzierung) {
        this.ivNennbetragFinanzierung = pvNennbetragFinanzierung;
    }

    /**
     * @return the waehrungNennbetragFinanzierung
     */
    public String getWaehrungNennbetragFinanzierung() {
        return this.ivWaehrungNennbetragFinanzierung;
    }

    /**
     * @param pvWaehrungNennbetragFinanzierung the waehrungNennbetragFinanzierung to set
     */
    public void setWaehrungNennbetragFinanzierung(String pvWaehrungNennbetragFinanzierung) {
        this.ivWaehrungNennbetragFinanzierung = pvWaehrungNennbetragFinanzierung;
    }

    /**
     * @return the fertigstellung
     */
    public String getFertigstellung() {
        return this.ivFertigstellung;
    }

    /**
     * @param pvFertigstellung the fertigstellung to set
     */
    public void setFertigstellung(String pvFertigstellung) {
        this.ivFertigstellung = pvFertigstellung;
    }

    /**
     * @return the auslieferung
     */
    public String getAuslieferung() {
        return this.ivAuslieferung;
    }

    /**
     * @param pvAuslieferung the auslieferung to set
     */
    public void setAuslieferung(String pvAuslieferung) {
        this.ivAuslieferung = pvAuslieferung;
    }

    /**
     * @return the faelligkeitFinanzierung
     */
    public String getFaelligkeitFinanzierung() {
        return this.ivFaelligkeitFinanzierung;
    }

    /**
     * @param pvFaelligkeitFinanzierung the faelligkeitFinanzierung to set
     */
    public void setFaelligkeitFinanzierung(String pvFaelligkeitFinanzierung) {
        this.ivFaelligkeitFinanzierung = pvFaelligkeitFinanzierung;
    }

}
