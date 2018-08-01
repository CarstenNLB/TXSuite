/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten;

import org.apache.log4j.Logger;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class DarlehenLoanIQ
{	
    /**
     * Hypothek1A
     */ 
    public static final int HYPOTHEK_1A = 1;
    
     /**
      * Kommunalverbuergte Hypothek
      */
    public static final int KOMMUNALVERBUERGTE_HYPOTHEK = 2;
    
    /**
     * Rein Kommunal
     */
    public static final int REIN_KOMMUNAL = 3;
    
    /**
     * Verbuergt Kommunal
     */
    public static final int VERBUERGT_KOMMUNAL = 4;
    
    /**
     * Schiffsdarlehen
     */
    public static final int SCHIFFSDARLEHEN = 66;

    /**
     * Flugzeugdarlehen
     */
    public static final int FLUGZEUGDARLEHEN = 67;
    
    /**
     * Sonstige Schuldverschreibung
     */
    public static final int SONSTIGE_SCHULDVERSCHREIBUNG = 23;
    
    /**
     * Bankkredit
     */
    public static final int BANKKREDIT = 64;
    
    /**
     * Undefiniert
     */
    public static final int UNDEFINIERT = 0;
    
    /**
     * log4j-Logger
     */
    private Logger ivLogger;
    
    /**
     * Quellsystem
     */
    private String ivQuellsystem;
    
    /**
     * Externer Key/Kontonummer
     */
    private String ivKontonummer;
    
    /**
     * External Customer ID
     */
    private String ivCustomerID;
    
    /**
     * Kennzeichen Hostbank
     */
    private String ivKennzeichenHostbank;
    
    /**
     * Deal Referenz
     */
    private String ivDealReferenz;
    
    /**
     * Facility Referenz
     */
    private String ivFacilityReferenz;
    
    /**
     * ISIN
     */
    private String ivISIN;
    
    /**
     * Merkmal Aktiv/Passiv
     */
    private String ivMerkmalAktivPassiv;
    
    /**
     * Kennzeichen Brutto/Netto/Fremd
     * B -> Brutto 
     * N -> Netto
     * F -> Fremd
     */
    private String ivKennzeichenBruttoNettoFremd;
    
    /**
     * Auszahlungsstatus
     */
    private String ivAuszahlungsstatus;
    
    /**
     * Auszahlungsdatum
     */
    private String ivAuszahlungsdatum;
    
    /**
     * Auszahlungsverpflichtung
     */
    private String ivAuszahlungsverpflichtung;
    
    /**
     * Konsortialfuehrer
     */
    private String ivKonsortialfuehrer;
    
    /**
     * Kennzeichen Konsortialkredit
     */
    private String ivKennzeichenKonsortialkredit;
    
    /**
     * Nennbetrag (Ursprungskapital/Nominal) 
     */
    private String ivNennbetrag;
        
    /**
     * Restkapital
     */
    private String ivRestkapital;
    
    /**
     * Ausplatzierungs-/Abtretungsmerkmal
     */
    private String ivAusplatzierungsmerkmal;
    
    /**
     * Betragswaehrung
     */
    private String ivBetragwaehrung;
    
    /**
     * Zusagedatum
     */
    private String ivZusagedatum;
    
    /**
     * Deckungsschluessel
     */
    private String ivDeckungsschluessel;
    
    /**
     * Produktschluessel
     */
    private String ivProduktschluessel;
    
    /**
     * Kennzeichen Rollover
     */
    private String ivKennzeichenRollover;
    
    /**
     * Kontoeinrichtung
     */
    private String ivKontoeinrichtung;
    
    /**
     * Bewilligende OE
     */
    private String ivBewilligendeOE;
    
    /**
     * Verwaltende OE
     */
    private String ivVerwaltendeOE;
    
    /**
     * PAS 
     */
    private String POE;
    
    /**
     * Risk Type
     */
    private String ivRiskType;
    
    /**
     * Deal Classification
     */
    private String ivDealClassification;
    
    /**
     * Loan Purpose Type
     */
    private String ivLoanPurposeType;
    
    /**
     * Financing Type 
     */
    private String ivFinancingType;
    
    /**
     * Buergschaftsprozent
     */
    private String ivBuergschaftprozent;
    
    /**
     * Kuendigungsdatum
     */
    private String ivKuendigungsdatum;
    
    /**
     * Eigenanteil NordLB
     */
    private String ivEigenanteil;
    
    /**
     * Buergennummer
     */
    private String ivBuergennummer;
    
    /**
     * Arbeitskonvention
     */
    private String ivArbeitskonvention;
        
    /**
     * Berechnungsnominale
     */
    private String ivBerechnungsnominale;
        
    /**
     * Datum der Konditionierung
     */
    private String ivDatumKonditionierung;
    
    /**
     * Laufzeit Zinsanpassung
     */
    private String ivLaufzeitZinsanpassung;
    
    /**
     * Letzte Zinsanpassung
     */
    private String ivLetzteZinsanpassung;
    
    /**
     * Zinsperiodenende
     */
    private String ivZinsperiodenende;
    
    /**
     * Naechste Zinsanpassung
     */
    private String ivNaechsteZinsanpassung;
    
    /**
     * Schluessel Zinsrechnung
     */
    private String ivSchluesselZinsrechnung;
    
    /**
     * Laufzeitende
     */
    private String ivLaufzeitende;
    
    /**
     * Faelligkeit/ Datumende der Zinsbindung
     */
    private String ivFaelligkeit;
        
    /**
     * Kalenderkonvention
     */
    private String ivKalenderkonvention;
    
    /**
     * Rate pro Faelligkeit
     */
    private String ivRateFaelligkeit;
        
    /**
     * Nominalzinssatz
     */
    private String ivNominalzinssatz;
    
    /**
     * Referenzzinssatz
     */
    private String ivReferenzzins;
    
    /**
     * Zinszuschlag/Spread
     */
    private String ivSpread;
    
    /**
     * Tilgungsbeginn
     */
    private String ivTilgungsbeginn;
    
    /**
     * Tilgungsperiodenende
     */
    private String ivTilgungsperiodenende;
    
    /**
     * Tilgungstermin
     */
    private String ivTilgungstermin;
    
    /**
     * Tilgungsprozentsatz
     */
    private String ivTilgungsprozentsatz;
    
    /**
     * Tilgungsrhythmus
     */
    private String ivTilgungsrhythmus;
    
    /**
     * Vorfaelligkeitstage
     */
    private String ivVorfaelligkeitstage;
    
    /**
     *  Tilgungsart
     */
    private String ivTilgungsart;
    
    /**
     * Konditionierung von
     */
    private String ivKonditionierungVon;
    
    /**
     * Beginn erste Zinsperiode
     */
    private String ivBeginnErsteZinsperiode;
    
    /**
     * Zinstermin
     */
    private String ivZinstermin;
    
    /**
     * Zinszahlungsrythmus
     */
    private String ivZinszahlungsrythmus;
    
    /**
     * Zinstyp
     */
    private String ivZinstyp;
    
    /**
     * Kundennummer
     */
    private String ivKundennummer;
    
    /**
     * BetragBis/Solldeckung
     */
    private String ivSolldeckung;
       
    /**
     * Durchleitungsgeschaeft Gegenkonto Passiv
     */
    private String ivGegenkontoPassiv;
    
    /**
     * Facility Nominal
     */
    private String ivFacilityNominal;
    
    /**
     * Deal Nominal
     */
    private String ivDealNominal;
    
    
    /**
     * Konstruktor - Initialisiert alle Variablen mit einem leeren String
     */
    public DarlehenLoanIQ(Logger pvLogger) 
    {
    	this.ivLogger = pvLogger;
        this.ivQuellsystem = new String();
        this.ivKontonummer = new String();
        this.ivCustomerID = new String();
        this.ivKennzeichenHostbank = new String();
        this.ivDealReferenz = new String();
        this.ivFacilityReferenz = new String();
        this.ivISIN = new String();
        this.ivMerkmalAktivPassiv = new String();
        this.ivKennzeichenBruttoNettoFremd = new String();
        this.ivAuszahlungsstatus = new String();
        this.ivAuszahlungsdatum = new String();
        this.ivAuszahlungsverpflichtung = new String();
        this.ivKonsortialfuehrer = new String();
        this.ivKennzeichenKonsortialkredit = new String();
        this.ivNennbetrag = new String();
        this.ivRestkapital = new String();
        this.ivAusplatzierungsmerkmal = new String();
        this.ivBetragwaehrung = new String();
        this.ivZusagedatum = new String();
        this.ivDeckungsschluessel = new String();
        this.ivProduktschluessel = new String();
        this.ivKennzeichenRollover = new String();
        this.ivKontoeinrichtung = new String();
        this.ivBewilligendeOE = new String();
        this.ivVerwaltendeOE = new String();
        this.POE = new String();
        this.ivRiskType = new String();
        this.ivDealClassification = new String();
        this.ivLoanPurposeType = new String();
        this.ivFinancingType = new String();
        this.ivBuergschaftprozent = new String();
        this.ivKuendigungsdatum = new String();
        this.ivEigenanteil = new String();
        this.ivBuergennummer = new String();
        this.ivArbeitskonvention = new String();
        this.ivBerechnungsnominale = new String();
        this.ivDatumKonditionierung = new String();
        this.ivLaufzeitZinsanpassung = new String();
        this.ivLetzteZinsanpassung = new String();
        this.ivZinsperiodenende = new String();
        this.ivNaechsteZinsanpassung = new String();
        this.ivSchluesselZinsrechnung = new String();
        this.ivLaufzeitende = new String();
        this.ivFaelligkeit = new String();
        this.ivKalenderkonvention = new String();
        this.ivRateFaelligkeit = new String();
        this.ivNominalzinssatz = new String();
        this.ivReferenzzins = new String();
        this.ivSpread = new String();
        this.ivTilgungsbeginn = new String();
        this.ivTilgungsperiodenende = new String();
        this.ivTilgungstermin = new String();
        this.ivTilgungsprozentsatz = new String();
        this.ivTilgungsrhythmus = new String();
        this.ivVorfaelligkeitstage = new String();
        this.ivTilgungsart = new String();
        this.ivKonditionierungVon = new String();
        this.ivBeginnErsteZinsperiode = new String();
        this.ivZinstermin = new String();
        this.ivZinszahlungsrythmus = new String();
        this.ivZinstyp = new String();
        this.ivKundennummer = new String();
        this.ivSolldeckung = new String();
        this.ivGegenkontoPassiv = new String();
        this.ivFacilityNominal = new String();
        this.ivDealNominal = new String();
    }

    /**
     * @return the quellsystem
     */
    public String getQuellsystem() {
        return this.ivQuellsystem;
    }

    /**
     * @param pvQuellsystem the quellsystem to set
     */
    public void setQuellsystem(String pvQuellsystem) {
        this.ivQuellsystem = pvQuellsystem;
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
     * @return the customerID
     */
    public String getCustomerID() {
        return this.ivCustomerID;
    }

    /**
     * @param pvCustomerID the customerID to set
     */
    public void setCustomerID(String pvCustomerID) {
        this.ivCustomerID = pvCustomerID;
    }

    /**
     * @return the kennzeichenHostbank
     */
    public String getKennzeichenHostbank() {
        return this.ivKennzeichenHostbank;
    }

    /**
     * @param pvKennzeichenHostbank the kennzeichenHostbank to set
     */
    public void setKennzeichenHostbank(String pvKennzeichenHostbank) {
        this.ivKennzeichenHostbank = pvKennzeichenHostbank;
    }

    /**
     * @return the dealReferenz
     */
    public String getDealReferenz() {
        return this.ivDealReferenz;
    }

    /**
     * @param pvDealReferenz the dealReferenz to set
     */
    public void setDealReferenz(String pvDealReferenz) {
        this.ivDealReferenz = pvDealReferenz;
    }

    /**
     * @return the facilityReferenz
     */
    public String getFacilityReferenz() {
        return this.ivFacilityReferenz;
    }

    /**
     * @param pvFacilityReferenz the facilityReferenz to set
     */
    public void setFacilityReferenz(String pvFacilityReferenz) {
        this.ivFacilityReferenz = pvFacilityReferenz;
    }

    /**
     * @return the iSIN
     */
    public String getISIN() {
        return this.ivISIN;
    }

    /**
     * @param pvISIN the iSIN to set
     */
    public void setISIN(String pvISIN) {
        this.ivISIN = pvISIN;
    }

    /**
     * @return the merkmalAktivPassiv
     */
    public String getMerkmalAktivPassiv() {
        return this.ivMerkmalAktivPassiv;
    }

    /**
     * @param pvMerkmalAktivPassiv the merkmalAktivPassiv to set
     */
    public void setMerkmalAktivPassiv(String pvMerkmalAktivPassiv) {
        this.ivMerkmalAktivPassiv = pvMerkmalAktivPassiv;
    }

    /**
     * @return the auszahlungsstatus
     */
    public String getAuszahlungsstatus() {
        return this.ivAuszahlungsstatus;
    }

    /**
     * @param pvAuszahlungsstatus the auszahlungsstatus to set
     */
    public void setAuszahlungsstatus(String pvAuszahlungsstatus) {
        this.ivAuszahlungsstatus = pvAuszahlungsstatus;
    }

    /**
     * @return the kennzeichenBruttoNetto
     */
    public String getKennzeichenBruttoNettoFremd() {
        return this.ivKennzeichenBruttoNettoFremd;
    }

    /**
     * @param pvKennzeichenBruttoNettoFremd the kennzeichenBruttoNetto to set
     */
    public void setKennzeichenBruttoNettoFremd(String pvKennzeichenBruttoNettoFremd) {
        this.ivKennzeichenBruttoNettoFremd = pvKennzeichenBruttoNettoFremd;
    }

    /**
     * @return the auszahlungsdatum
     */
    public String getAuszahlungsdatum() {
        return this.ivAuszahlungsdatum;
    }

    /**
     * @param pvAuszahlungsdatum the auszahlungsdatum to set
     */
    public void setAuszahlungsdatum(String pvAuszahlungsdatum) {
        this.ivAuszahlungsdatum = pvAuszahlungsdatum;
    }

    /**
     * @return the auszahlungsverpflichtung
     */
    public String getAuszahlungsverpflichtung() {
        return this.ivAuszahlungsverpflichtung;
    }

    /**
     * @param pvAuszahlungsverpflichtung the auszahlungsverpflichtung to set
     */
    public void setAuszahlungsverpflichtung(String pvAuszahlungsverpflichtung) {
        this.ivAuszahlungsverpflichtung = pvAuszahlungsverpflichtung;
    }

    /**
     * @return the konsortialfuehrer
     */
    public String getKonsortialfuehrer() {
        return this.ivKonsortialfuehrer;
    }

    /**
     * @param pvKonsortialfuehrer the konsortialfuehrer to set
     */
    public void setKonsortialfuehrer(String pvKonsortialfuehrer) {
        this.ivKonsortialfuehrer = pvKonsortialfuehrer;
    }

    /**
     * @return the kennzeichenKonsortialkredit
     */
    public String getKennzeichenKonsortialkredit() {
        return this.ivKennzeichenKonsortialkredit;
    }

    /**
     * @param pvKennzeichenKonsortialkredit the kennzeichenKonsortialkredit to set
     */
    public void setKennzeichenKonsortialkredit(String pvKennzeichenKonsortialkredit) {
        this.ivKennzeichenKonsortialkredit = pvKennzeichenKonsortialkredit;
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
     * @return the restkapital
     */
    public String getRestkapital() {
        return this.ivRestkapital;
    }

    /**
     * @param pvRestkapital the restkapital to set
     */
    public void setRestkapital(String pvRestkapital) {
        this.ivRestkapital = pvRestkapital;
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
     * @return the betragwaehrung
     */
    public String getBetragwaehrung() {
        return this.ivBetragwaehrung;
    }

    /**
     * @param pvBetragwaehrung the betragwaehrung to set
     */
    public void setBetragwaehrung(String pvBetragwaehrung) {
        this.ivBetragwaehrung = pvBetragwaehrung;
    }

    /**
     * @return the zusagedatum
     */
    public String getZusagedatum() {
        return this.ivZusagedatum;
    }

    /**
     * @param pvZusagedatum the zusagedatum to set
     */
    public void setZusagedatum(String pvZusagedatum) {
        this.ivZusagedatum = pvZusagedatum;
    }

    /**
     * @return the deckungsschluessel
     */
    public String getDeckungsschluessel() {
        return this.ivDeckungsschluessel;
    }

    /**
     * @param pvDeckungsschluessel the deckungsschluessel to set
     */
    public void setDeckungsschluessel(String pvDeckungsschluessel) {
        this.ivDeckungsschluessel = pvDeckungsschluessel;
    }

    /**
     * @return the produktschluessel
     */
    public String getProduktschluessel() {
        return this.ivProduktschluessel;
    }

    /**
     * @param pvProduktschluessel the produktschluessel to set
     */
    public void setProduktschluessel(String pvProduktschluessel) {
        this.ivProduktschluessel = pvProduktschluessel;
    }

    /**
     * @return the kennzeichenRollover
     */
    public String getKennzeichenRollover() {
        return this.ivKennzeichenRollover;
    }

    /**
     * @param pvKennzeichenRollover the kennzeichenRollover to set
     */
    public void setKennzeichenRollover(String pvKennzeichenRollover) {
        this.ivKennzeichenRollover = pvKennzeichenRollover;
    }

    /**
     * @return the kontoeinrichtung
     */
    public String getKontoeinrichtung() {
        return this.ivKontoeinrichtung;
    }

    /**
     * @param pvKontoeinrichtung the kontoeinrichtung to set
     */
    public void setKontoeinrichtung(String pvKontoeinrichtung) {
        this.ivKontoeinrichtung = pvKontoeinrichtung;
    }

    /**
     * @return the bewilligendeOE
     */
    public String getBewilligendeOE() {
        return this.ivBewilligendeOE;
    }

    /**
     * @param pvBewilligendeOE the bewilligendeOE to set
     */
    public void setBewilligendeOE(String pvBewilligendeOE) {
        this.ivBewilligendeOE = pvBewilligendeOE;
    }

    /**
     * @return the verwaltendeOE
     */
    public String getVerwaltendeOE() {
        return this.ivVerwaltendeOE;
    }

    /**
     * @param pvVerwaltendeOE the verwaltendeOE to set
     */
    public void setVerwaltendeOE(String pvVerwaltendeOE) {
        this.ivVerwaltendeOE = pvVerwaltendeOE;
    }

    /**
     * @return the pOE
     */
    public String getPOE() {
        return this.POE;
    }

    /**
     * @param pvPOE the pOE to set
     */
    public void setPOE(String pvPOE) {
        this.POE = pvPOE;
    }

    /**
     * @return the riskType
     */
    public String getRiskType() {
        return this.ivRiskType;
    }

    /**
     * @param pvRiskType the riskType to set
     */
    public void setRiskType(String pvRiskType) {
        this.ivRiskType = pvRiskType;
    }

    /**
     * @return the dealClassification
     */
    public String getDealClassification() {
        return this.ivDealClassification;
    }

    /**
     * @param pvDealClassification the dealClassification to set
     */
    public void setDealClassification(String pvDealClassification) {
        this.ivDealClassification = pvDealClassification;
    }

    /**
     * @return the loanPurposeType
     */
    public String getLoanPurposeType() {
        return this.ivLoanPurposeType;
    }

    /**
     * @param pvLoanPurposeType the loanPurposeType to set
     */
    public void setLoanPurposeType(String pvLoanPurposeType) {
        this.ivLoanPurposeType = pvLoanPurposeType;
    }

    /**
     * @return the financingType
     */
    public String getFinancingType() {
        return this.ivFinancingType;
    }

    /**
     * @param pvFinancingType the financingType to set
     */
    public void setFinancingType(String pvFinancingType) {
        this.ivFinancingType = pvFinancingType;
    }

    /**
     * @return the buergschaftprozent
     */
    public String getBuergschaftprozent() {
        return this.ivBuergschaftprozent;
    }

    /**
     * @param pvBuergschaftprozent the buergschaftprozent to set
     */
    public void setBuergschaftprozent(String pvBuergschaftprozent) {
        this.ivBuergschaftprozent = pvBuergschaftprozent;
    }

    /**
     * @return the kuendigungsdatum
     */
    public String getKuendigungsdatum() {
        return this.ivKuendigungsdatum;
    }

    /**
     * @param pvKuendigungsdatum the kuendigungsdatum to set
     */
    public void setKuendigungsdatum(String pvKuendigungsdatum) {
        this.ivKuendigungsdatum = pvKuendigungsdatum;
    }

    /**
     * @return the eigenanteil
     */
    public String getEigenanteil() {
        return this.ivEigenanteil;
    }

    /**
     * @param pvEigenanteil the eigenanteil to set
     */
    public void setEigenanteil(String pvEigenanteil) {
        this.ivEigenanteil = pvEigenanteil;
    }

    /**
     * @return the buergennummer
     */
    public String getBuergennummer() {
        return this.ivBuergennummer;
    }

    /**
     * @param pvBuergennummer the buergennummer to set
     */
    public void setBuergennummer(String pvBuergennummer) {
        this.ivBuergennummer = pvBuergennummer;
    }

    /**
     * @return the arbeitskonvention
     */
    public String getArbeitskonvention() {
        return this.ivArbeitskonvention;
    }

    /**
     * @param pvArbeitskonvention the arbeitskonvention to set
     */
    public void setArbeitskonvention(String pvArbeitskonvention) {
        this.ivArbeitskonvention = pvArbeitskonvention;
    }

    /**
     * @return the berechnungsnominale
     */
    public String getBerechnungsnominale() {
        return this.ivBerechnungsnominale;
    }

    /**
     * @param pvBerechnungsnominale the berechnungsnominale to set
     */
    public void setBerechnungsnominale(String pvBerechnungsnominale) {
        this.ivBerechnungsnominale = pvBerechnungsnominale;
    }

    /**
     * @return the datumKonditionierung
     */
    public String getDatumKonditionierung() {
        return this.ivDatumKonditionierung;
    }

    /**
     * @param pvDatumKonditionierung the datumKonditionierung to set
     */
    public void setDatumKonditionierung(String pvDatumKonditionierung) {
        this.ivDatumKonditionierung = pvDatumKonditionierung;
    }

    /**
     * @return the laufzeitZinsanpassung
     */
    public String getLaufzeitZinsanpassung() {
        return this.ivLaufzeitZinsanpassung;
    }

    /**
     * @param pvLaufzeitZinsanpassung the laufzeitZinsanpassung to set
     */
    public void setLaufzeitZinsanpassung(String pvLaufzeitZinsanpassung) {
        this.ivLaufzeitZinsanpassung = pvLaufzeitZinsanpassung;
    }

    /**
     * @return the letzteZinsanpassung
     */
    public String getLetzteZinsanpassung() {
        return this.ivLetzteZinsanpassung;
    }

    /**
     * @param pvLetzteZinsanpassung the letzteZinsanpassung to set
     */
    public void setLetzteZinsanpassung(String pvLetzteZinsanpassung) {
        this.ivLetzteZinsanpassung = pvLetzteZinsanpassung;
    }

    /**
     * @return the zinsperiodenende
     */
    public String getZinsperiodenende() {
        return this.ivZinsperiodenende;
    }

    /**
     * @param pvZinsperiodenende the zinsperiodenende to set
     */
    public void setZinsperiodenende(String pvZinsperiodenende) {
        this.ivZinsperiodenende = pvZinsperiodenende;
    }

    /**
     * @return the naechsteZinsanpassung
     */
    public String getNaechsteZinsanpassung() {
        return this.ivNaechsteZinsanpassung;
    }

    /**
     * @param pvNaechsteZinsanpassung the naechsteZinsanpassung to set
     */
    public void setNaechsteZinsanpassung(String pvNaechsteZinsanpassung) {
        this.ivNaechsteZinsanpassung = pvNaechsteZinsanpassung;
    }

    /**
     * @return the schluesselZinsrechnung
     */
    public String getSchluesselZinsrechnung() {
        return this.ivSchluesselZinsrechnung;
    }

    /**
     * @param pvSchluesselZinsrechnung the schluesselZinsrechnung to set
     */
    public void setSchluesselZinsrechnung(String pvSchluesselZinsrechnung) {
        this.ivSchluesselZinsrechnung = pvSchluesselZinsrechnung;
    }

    /**
     * @return the laufzeitende
     */
    public String getLaufzeitende() {
        return this.ivLaufzeitende;
    }

    /**
     * @param pvLaufzeitende the laufzeitende to set
     */
    public void setLaufzeitende(String pvLaufzeitende) {
        this.ivLaufzeitende = pvLaufzeitende;
    }

    /**
     * @return the faelligkeit
     */
    public String getFaelligkeit() {
        return this.ivFaelligkeit;
    }

    /**
     * @param pvFaelligkeit the faelligkeit to set
     */
    public void setFaelligkeit(String pvFaelligkeit) {
        this.ivFaelligkeit = pvFaelligkeit;
    }

    /**
     * @return the kalenderkonvention
     */
    public String getKalenderkonvention() {
        return this.ivKalenderkonvention;
    }

    /**
     * @param pvKalenderkonvention the kalenderkonvention to set
     */
    public void setKalenderkonvention(String pvKalenderkonvention) {
        this.ivKalenderkonvention = pvKalenderkonvention;
    }

    /**
     * @return the rateFaelligkeit
     */
    public String getRateFaelligkeit() {
        return this.ivRateFaelligkeit;
    }

    /**
     * @param pvRateFaelligkeit the rateFaelligkeit to set
     */
    public void setRateFaelligkeit(String pvRateFaelligkeit) {
        this.ivRateFaelligkeit = pvRateFaelligkeit;
    }

    /**
     * @return the nominalzinssatz
     */
    public String getNominalzinssatz() {
        return this.ivNominalzinssatz;
    }

    /**
     * @param pvNominalzinssatz the nominalzinssatz to set
     */
    public void setNominalzinssatz(String pvNominalzinssatz) {
        this.ivNominalzinssatz = pvNominalzinssatz;
    }

    /**
     * @return the referenzzinssatz
     */
    public String getReferenzzins() {
        return this.ivReferenzzins;
    }

    /**
     * @param pvReferenzzins the referenzzinssatz to set
     */
    public void setReferenzzins(String pvReferenzzins) {
        this.ivReferenzzins = pvReferenzzins;
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
     * @return the tilgungsbeginn
     */
    public String getTilgungsbeginn() {
        return this.ivTilgungsbeginn;
    }

    /**
     * @param pvTilgungsbeginn the tilgungsbeginn to set
     */
    public void setTilgungsbeginn(String pvTilgungsbeginn) {
        this.ivTilgungsbeginn = pvTilgungsbeginn;
    }

    /**
     * @return the tilgungsperiodenende
     */
    public String getTilgungsperiodenende() {
        return this.ivTilgungsperiodenende;
    }

    /**
     * @param pvTilgungsperiodenende the tilgungsperiodenende to set
     */
    public void setTilgungsperiodenende(String pvTilgungsperiodenende) {
        this.ivTilgungsperiodenende = pvTilgungsperiodenende;
    }

    /**
     * @return the tilgungstermin
     */
    public String getTilgungstermin() {
        return this.ivTilgungstermin;
    }

    /**
     * @param pvTilgungstermin the tilgungstermin to set
     */
    public void setTilgungstermin(String pvTilgungstermin) {
        this.ivTilgungstermin = pvTilgungstermin;
    }

    /**
     * @return the tilgungsprozentsatz
     */
    public String getTilgungsprozentsatz() {
        return this.ivTilgungsprozentsatz;
    }

    /**
     * @param pvTilgungsprozentsatz the tilgungsprozentsatz to set
     */
    public void setTilgungsprozentsatz(String pvTilgungsprozentsatz) {
        this.ivTilgungsprozentsatz = pvTilgungsprozentsatz;
    }

    /**
     * @return the tilgungsrhythmus
     */
    public String getTilgungsrhythmus() {
        return this.ivTilgungsrhythmus;
    }

    /**
     * @param pvTilgungsrhythmus the tilgungsrhythmus to set
     */
    public void setTilgungsrhythmus(String pvTilgungsrhythmus) {
        this.ivTilgungsrhythmus = pvTilgungsrhythmus;
    }

    /**
     * @return the vorfaelligkeitstage
     */
    public String getVorfaelligkeitstage() {
        return this.ivVorfaelligkeitstage;
    }

    /**
     * @param pvVorfaelligkeitstage the vorfaelligkeitstage to set
     */
    public void setVorfaelligkeitstage(String pvVorfaelligkeitstage) {
        this.ivVorfaelligkeitstage = pvVorfaelligkeitstage;
    }

    /**
     * @return the tilgungsart
     */
    public String getTilgungsart() {
        return this.ivTilgungsart;
    }

    /**
     * @param pvTilgungsart the tilgungsart to set
     */
    public void setTilgungsart(String pvTilgungsart) {
        this.ivTilgungsart = pvTilgungsart;
    }

    /**
     * @return the konditionierungVon
     */
    public String getKonditionierungVon() {
        return this.ivKonditionierungVon;
    }

    /**
     * @param pvKonditionierungVon the konditionierungVon to set
     */
    public void setKonditionierungVon(String pvKonditionierungVon) {
        this.ivKonditionierungVon = pvKonditionierungVon;
    }

    /**
     * @return the beginnErsteZinsperiode
     */
    public String getBeginnErsteZinsperiode() {
        return this.ivBeginnErsteZinsperiode;
    }

    /**
     * @param pvBeginnErsteZinsperiode the beginnErsteZinsperiode to set
     */
    public void setBeginnErsteZinsperiode(String pvBeginnErsteZinsperiode) {
        this.ivBeginnErsteZinsperiode = pvBeginnErsteZinsperiode;
    }

    /**
     * @return the zinstermin
     */
    public String getZinstermin() {
        return this.ivZinstermin;
    }

    /**
     * @param pvZinstermin the zinstermin to set
     */
    public void setZinstermin(String pvZinstermin) {
        this.ivZinstermin = pvZinstermin;
    }

    /**
     * @return the zinszahlungsrythmus
     */
    public String getZinszahlungsrythmus() {
        return this.ivZinszahlungsrythmus;
    }

    /**
     * @param pvZinszahlungsrythmus the zinszahlungsrythmus to set
     */
    public void setZinszahlungsrythmus(String pvZinszahlungsrythmus) {
        this.ivZinszahlungsrythmus = pvZinszahlungsrythmus;
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
     * @return the kundennummer
     */
    public String getKundennummer() {
        return this.ivKundennummer;
    }

    /**
     * @param pvKundennummer the kundennummer to set
     */
    public void setKundennummer(String pvKundennummer) {
        this.ivKundennummer = pvKundennummer;
    }

    /**
     * @return the solldeckung
     */
    public String getSolldeckung() {
        return this.ivSolldeckung;
    }

    /**
     * @param pvSolldeckung the solldeckung to set
     */
    public void setSolldeckung(String pvSolldeckung) {
        this.ivSolldeckung = pvSolldeckung;
    }

    /**
     * @return the gegenkontoPassiv
     */
    public String getGegenkontoPassiv() {
        return this.ivGegenkontoPassiv;
    }

    /**
     * @param pvGegenkontoPassiv the gegenkontoPassiv to set
     */
    public void setGegenkontoPassiv(String pvGegenkontoPassiv) {
        this.ivGegenkontoPassiv = pvGegenkontoPassiv;
    }

    /**
     * 
     * @return
     */
    public String getFacilityNominal() 
    {
		return ivFacilityNominal;
	}

    /**
     * 
     * @param pvFacilityNominal
     */
	public void setFacilityNominal(String pvFacilityNominal) 
	{
		this.ivFacilityNominal = pvFacilityNominal;
	}

	/**
	 * 
	 * @return
	 */
	public String getDealNominal() 
	{
		return ivDealNominal;
	}

	/**
	 * 
	 * @param pvDealNominal
	 */
	public void setDealNominal(String pvDealNominal) 
	{
		this.ivDealNominal = pvDealNominal;
	}

	/**
	 * Zerlegt eine Darlehenszeile in die einzelnen Felder/Werte
     * @param pvZeile
     * @return      
     */
   public boolean parseDarlehen(String pvZeile)
   {                 
      String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
      int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
      int    lvZzStr=0;              // pointer fuer satzbereich
         
      //System.out.println("pvZeile: " + pvZeile);
      // Steuerung/Iteration Eingabesatz
      for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
      {
        // wenn Semikolon erkannt
        if (pvZeile.charAt(lvZzStr) == ';')
        {
            //System.out.println(lvTemp);
            this.setDarlehen(lvLfd, lvTemp);
            
            lvLfd++;                  // naechste Feldnummer
            // loeschen Zwischenbuffer
            lvTemp = new String();
        }
        else
        {
            // uebernehmen Byte aus Eingabesatzposition
            lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
        }
      } // ende for  
     
      return true; 
   }

    /**
     * Setzt einen Wert des Darlehens
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setDarlehen(int pvPos, String pvWert) 
    {
        //System.out.println("Pos: " + pvPos + " Wert:" + pvWert);
        switch (pvPos)
        {
          case 0:
              this.ivQuellsystem = pvWert;
              break;
          case 1:
              this.ivKontonummer = pvWert;
              break;
          case 2:
              this.ivCustomerID = pvWert;
              break;
          case 3:
              this.ivKennzeichenHostbank = pvWert;
              break;
          case 4:
              this.ivDealReferenz = pvWert;
              break;
          case 5:
              this.ivFacilityReferenz = pvWert;
              break;
          case 6:
              this.ivISIN = pvWert;
              break;
          case 7:
              this.ivMerkmalAktivPassiv = pvWert;
              break;
          case 8:
              this.ivKennzeichenBruttoNettoFremd = pvWert;
              break;
          case 9:
              this.ivAuszahlungsstatus = pvWert;
              break;
          case 10:
              this.ivAuszahlungsdatum = pvWert;
              break;
          case 11:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                 pvWert = pvWert.substring(1);
              }
              this.ivAuszahlungsverpflichtung = pvWert;
              break;
          case 12:
              this.ivKonsortialfuehrer = pvWert;
              break;
          case 13:
              this.ivKennzeichenKonsortialkredit = pvWert;
              break;
          case 14:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
              this.ivNennbetrag = pvWert;
              break;
          case 15:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
                 this.ivRestkapital = pvWert;
              break;
          case 16:
              this.ivAusplatzierungsmerkmal = pvWert;
              break;
          case 17:
              this.ivBetragwaehrung = pvWert;
              break;
          case 18:
              this.ivZusagedatum = pvWert;
              break;
          case 19:
              this.ivDeckungsschluessel = pvWert;
              break;
          case 20:
              this.ivProduktschluessel = pvWert;
              break;
          case 21:
              this.ivKennzeichenRollover = pvWert;
              break;
          case 22:
              this.ivKontoeinrichtung = pvWert;
              break;
          case 23:
              this.ivBewilligendeOE = pvWert;
              break;
          case 24:
              this.ivVerwaltendeOE = pvWert;
              break;
          case 25:
              this.POE = pvWert;
              break;
          case 26:
              this.ivRiskType = pvWert;
              break;
          case 27:
              this.ivDealClassification = pvWert;
              break;
          case 28:
              this.ivLoanPurposeType = pvWert;
              break;
          case 29:
              this.ivFinancingType = pvWert;
              break;
          case 30:
              this.ivBuergschaftprozent = pvWert.replace(",", ".");
              break;
          case 31:
              this.ivKuendigungsdatum = pvWert;
              break;
          case 32:
              this.ivEigenanteil = pvWert;
              break;
          case 33:
              this.ivBuergennummer = pvWert;
              break;
          case 34:
              this.ivArbeitskonvention = pvWert;
              break;
          case 35:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
                  this.ivBerechnungsnominale = pvWert;
              break;
          case 36:
              this.ivDatumKonditionierung = pvWert;
              break;
          case 37:
              this.ivLaufzeitZinsanpassung = pvWert;
              break;
          case 38:
              this.ivLetzteZinsanpassung = pvWert;
              break;
          case 39:
              this.ivZinsperiodenende = pvWert;
              break;
          case 40:
              this.ivNaechsteZinsanpassung = pvWert;
              break;
          case 41:
              this.ivSchluesselZinsrechnung = pvWert;
              break;
          case 42:
              this.ivLaufzeitende = pvWert;
              break;
          case 43:
              this.ivArbeitskonvention = pvWert;
              break;
          case 44:
              this.ivFaelligkeit = pvWert;
              break;
          case 45:
              this.ivKalenderkonvention = pvWert;
              break;
          case 46:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                 pvWert = pvWert.substring(1);
              }
                  this.ivRateFaelligkeit = pvWert;
              break;
          case 47:
              this.ivNominalzinssatz = pvWert.replace(",", ".");
              break;
          case 48:
              this.ivReferenzzins = pvWert;
              break;
          case 49:
              this.ivSpread = pvWert.replace(",", ".");
              break;
          case 50:
              this.ivTilgungsbeginn = pvWert;
              break;
          case 51:
              this.ivTilgungsperiodenende = pvWert;
              break;
          case 52:
              this.ivTilgungstermin = pvWert;
              break;
          case 53:
              pvWert = pvWert.replace(",", ".");
              this.ivTilgungsprozentsatz = pvWert;
              break;
          case 54:
              this.ivVorfaelligkeitstage = pvWert;
              break;
          case 55:
              this.ivTilgungsart = pvWert;
              break;
          case 56:
              this.ivKonditionierungVon = pvWert;
              break;
          case 57:
              this.ivBeginnErsteZinsperiode = pvWert;
              break;
          case 58:
              this.ivZinstermin = pvWert;
              break;
          case 59:
              this.ivZinszahlungsrythmus = pvWert;
              break;
          case 60:
              this.ivZinstyp = pvWert;
              break;
          case 61:
              this.ivKundennummer = pvWert;
              break;
          case 62:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
              
              this.ivSolldeckung = pvWert;
              break;
          case 63:
              this.ivTilgungsrhythmus = pvWert;
              break;
          case 64:
              this.ivGegenkontoPassiv = pvWert;
              break;
          case 65:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
              this.ivFacilityNominal = pvWert;
              break;
          case 66:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
              this.ivDealNominal = pvWert;
              break;
          default:
              ivLogger.error("DarlehenLoanIQ: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
        }
    }
    
    /**
     * Ermittelt den Kredittyp anhand des Ausplatzierungsmerkmals
     * @return 
     */
    // CT 12.09.2017 - Methode wird nicht mehr benoetigt! --> ValueMapping.ermittleKredittyp
    /*
    public int ermittleKredittyp()
    {
    	// Defaultmaessig undefiniert
        int lvKredittyp = UNDEFINIERT;
        
        // Hypothekendarlehen
        if (this.ivAusplatzierungsmerkmal.startsWith("H") || this.ivAusplatzierungsmerkmal.equals("O1") || this.ivAusplatzierungsmerkmal.equals("O2"))
        {        
            //if (StringKonverter.convertString2Double(this.ivBuergschaftprozent) > 0.0)
            //{
            //  lvKredittyp = KOMMUNALVERBUERGTE_HYPOTHEK;   
            //}
            //else
            //{
              lvKredittyp = HYPOTHEK_1A;
            //}
        }
        
        // Kommunaldarlehen
        if (this.ivAusplatzierungsmerkmal.startsWith("K") || this.ivAusplatzierungsmerkmal.equals("O3") || this.ivAusplatzierungsmerkmal.equals("O4"))
        {
            if (StringKonverter.convertString2Double(this.ivBuergschaftprozent) > 0.0)
            {
                lvKredittyp = VERBUERGT_KOMMUNAL;
            }
            else
            {
                lvKredittyp = REIN_KOMMUNAL;
            }
        }
        
        // Flugzeugdarlehen
        if (this.ivAusplatzierungsmerkmal.startsWith("F"))
        {
            lvKredittyp = FLUGZEUGDARLEHEN;
        }
        
        // Schiffsdarlehen
        if (this.ivAusplatzierungsmerkmal.startsWith("S"))
        {
            lvKredittyp = SCHIFFSDARLEHEN;
        }
        
        // Bankkredit
        if (this.ivAusplatzierungsmerkmal.endsWith("3"))
        {
        	lvKredittyp = BANKKREDIT;
        }
        
        // Sonstige Schuldverschreibung
        if (this.ivAusplatzierungsmerkmal.endsWith("2"))
        {
        	lvKredittyp = SONSTIGE_SCHULDVERSCHREIBUNG;
        }
        
        return lvKredittyp;        
    } */
    
    /**
     * Liefert das Darlehen aus LoanIQ als String
     * @return
     */
    public String toString()
    {
        StringBuilder lvHelpString = new StringBuilder();

        lvHelpString.append("Quellsystem: " + this.ivQuellsystem + StringKonverter.lineSeparator);
        lvHelpString.append("Kontonummer: " + this.ivKontonummer + StringKonverter.lineSeparator);
        lvHelpString.append("CustomerID: " + this.ivCustomerID + StringKonverter.lineSeparator);
        lvHelpString.append("KennzeichenHostbank: " + this.ivKennzeichenHostbank + StringKonverter.lineSeparator);
        lvHelpString.append("DealReferenz: " + this.ivDealReferenz + StringKonverter.lineSeparator);
        lvHelpString.append("FacilityReferenz: " + this.ivFacilityReferenz + StringKonverter.lineSeparator);
        lvHelpString.append("ISIN: " + this.ivISIN + StringKonverter.lineSeparator);
        lvHelpString.append("MerkmalAktivPassiv: " + this.ivMerkmalAktivPassiv + StringKonverter.lineSeparator);
        lvHelpString.append("KennzeichenBruttoNettoFremd: " + this.ivKennzeichenBruttoNettoFremd + StringKonverter.lineSeparator);
        lvHelpString.append("Auszahlungsstatus: " + this.ivAuszahlungsstatus + StringKonverter.lineSeparator);
        lvHelpString.append("Auszahlungsdatum: " + this.ivAuszahlungsdatum + StringKonverter.lineSeparator);
        lvHelpString.append("Auszahlungsverpflichtung: " + this.ivAuszahlungsverpflichtung + StringKonverter.lineSeparator);
        lvHelpString.append("Konsortialfuehrer: " + this.ivKonsortialfuehrer + StringKonverter.lineSeparator);
        lvHelpString.append("KennzeichenKonsortialkredit: " + this.ivKennzeichenKonsortialkredit + StringKonverter.lineSeparator);         
        lvHelpString.append("Nennbetrag: " + this.ivNennbetrag + StringKonverter.lineSeparator);
        lvHelpString.append("Restkapital: " + this.ivRestkapital + StringKonverter.lineSeparator);
        lvHelpString.append("Ausplatzierungsmerkmal: " + this.ivAusplatzierungsmerkmal + StringKonverter.lineSeparator);
        lvHelpString.append("Betragswaehrung: " + this.ivBetragwaehrung + StringKonverter.lineSeparator);
        lvHelpString.append("Zusagedatum: " + this.ivZusagedatum + StringKonverter.lineSeparator);
        lvHelpString.append("Deckungsschluessel: " + this.ivDeckungsschluessel + StringKonverter.lineSeparator);
        lvHelpString.append("Produktschluessel: " + this.ivProduktschluessel + StringKonverter.lineSeparator);
        lvHelpString.append("KennzeichenRollover: " + this.ivKennzeichenRollover + StringKonverter.lineSeparator);
        lvHelpString.append("Kontoeinrichtung: " + this.ivKontoeinrichtung + StringKonverter.lineSeparator);
        lvHelpString.append("BewillingendeOE: " + this.ivBewilligendeOE + StringKonverter.lineSeparator);
        lvHelpString.append("VerwaltendeOE: " + this.ivVerwaltendeOE + StringKonverter.lineSeparator);
        lvHelpString.append("POE: " + this.POE + StringKonverter.lineSeparator);
        lvHelpString.append("RiskType: " + this.ivRiskType + StringKonverter.lineSeparator);
        lvHelpString.append("DealClassification: " + this.ivDealClassification + StringKonverter.lineSeparator);
        lvHelpString.append("LoanPurposeType: " + this.ivLoanPurposeType + StringKonverter.lineSeparator);
        lvHelpString.append("FinancingType: " + this.ivFinancingType + StringKonverter.lineSeparator);
        lvHelpString.append("Buergschaftprozent: " + this.ivBuergschaftprozent + StringKonverter.lineSeparator);
        lvHelpString.append("Kuendigungsdatum: " + this.ivKuendigungsdatum + StringKonverter.lineSeparator);
        lvHelpString.append("Eigenanteil: " + this.ivEigenanteil + StringKonverter.lineSeparator);
        lvHelpString.append("Buergennummer: " + this.ivBuergennummer + StringKonverter.lineSeparator);
        lvHelpString.append("Arbeitskonvention: " + this.ivArbeitskonvention + StringKonverter.lineSeparator);
        lvHelpString.append("Berechnungsnominale: " + this.ivBerechnungsnominale + StringKonverter.lineSeparator);
        lvHelpString.append("DatumKonditionierung: " + this.ivDatumKonditionierung + StringKonverter.lineSeparator);
        lvHelpString.append("LaufzeitZinsanpassung: " + this.ivLaufzeitZinsanpassung + StringKonverter.lineSeparator);
        lvHelpString.append("LetzteZinsanpassung: " + this.ivLetzteZinsanpassung + StringKonverter.lineSeparator);
        lvHelpString.append("Zinsperiodenende: " + this.ivZinsperiodenende + StringKonverter.lineSeparator);
        lvHelpString.append("NaechsteZinsanpassung: " + this.ivNaechsteZinsanpassung + StringKonverter.lineSeparator);
        lvHelpString.append("SchluesselZinsrechnung: " + this.ivSchluesselZinsrechnung + StringKonverter.lineSeparator);
        lvHelpString.append("Laufzeitende: " + this.ivLaufzeitende + StringKonverter.lineSeparator);
        lvHelpString.append("Faelligkeit: " + this.ivFaelligkeit + StringKonverter.lineSeparator);
        lvHelpString.append("Kalenderkonvention: " + this.ivKalenderkonvention + StringKonverter.lineSeparator);
        lvHelpString.append("RateFaelligkeit: " + this.ivRateFaelligkeit + StringKonverter.lineSeparator);
        lvHelpString.append("Nominalzinssatz: " + this.ivNominalzinssatz + StringKonverter.lineSeparator);
        lvHelpString.append("Referenzzins: " + this.ivReferenzzins + StringKonverter.lineSeparator);
        lvHelpString.append("Spread: " + this.ivSpread + StringKonverter.lineSeparator);
        lvHelpString.append("Tilgungsbeginn: " + this.ivTilgungsbeginn + StringKonverter.lineSeparator);
        lvHelpString.append("Tilgungsperiodenende: " + this.ivTilgungsperiodenende + StringKonverter.lineSeparator);
        lvHelpString.append("Tilgungstermin: " + this.ivTilgungstermin + StringKonverter.lineSeparator);
        lvHelpString.append("Tilgungsprozentsatz: " + this.ivTilgungsprozentsatz + StringKonverter.lineSeparator);
        lvHelpString.append("Tilgungsrhythmus: " + this.ivTilgungsrhythmus + StringKonverter.lineSeparator);
        lvHelpString.append("Vorfaelligkeitstage: " + this.ivVorfaelligkeitstage + StringKonverter.lineSeparator);
        lvHelpString.append("Tilgungsart: " + this.ivTilgungsart + StringKonverter.lineSeparator);
        lvHelpString.append("KonditionierungVon: " + this.ivKonditionierungVon + StringKonverter.lineSeparator);
        lvHelpString.append("BeginnErsteZinsperiode: " + this.ivBeginnErsteZinsperiode + StringKonverter.lineSeparator);
        lvHelpString.append("Zinstermin: " + this.ivZinstermin + StringKonverter.lineSeparator);
        lvHelpString.append("Zinszahlungsrythmus: " + this.ivZinszahlungsrythmus + StringKonverter.lineSeparator);
        lvHelpString.append("Zinstyp: " + this.ivZinstyp + StringKonverter.lineSeparator);
        lvHelpString.append("Kundennummer: " + this.ivKundennummer + StringKonverter.lineSeparator);
        lvHelpString.append("Solldeckung: " + this.ivSolldeckung + StringKonverter.lineSeparator);
        lvHelpString.append("GegenkontoPassiv: " + this.ivGegenkontoPassiv + StringKonverter.lineSeparator);
        lvHelpString.append("FacilityNominal: " + this.ivFacilityNominal + StringKonverter.lineSeparator);
        lvHelpString.append("DealNominal: " + this.ivDealNominal + StringKonverter.lineSeparator);

        return lvHelpString.toString();
    }    
}
