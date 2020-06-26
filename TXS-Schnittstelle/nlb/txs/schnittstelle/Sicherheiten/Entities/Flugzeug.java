package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

public class Flugzeug 
{
	/**
	 * GUID fuer Tabelle CMS_AST
	 */
	private String ivId;
	
	/**
	 * Referenz-ID fuer das Objekt
	 */
	private String ivReferenzId;
	
	/**
	 * festgel. Beleihungw.
	 */
	private String ivNominalwert;
	
	/**
	 * Waehrung des Nominalwerts
	 */
	private String ivWaehrungNominalwert;
	
	/**
	 * Current Market Value
	 */
	private String ivVermoegenswert;
	
	/**
	 * Waehrung des endgueltigen Vermoegenswerts
	 */
	private String ivWaehrungVermoegenswert;
	
	/**
	 * Objekttyp
	 */
	private String ivObjekttyp;
	
	/**
	 * Mobilienarten
	 */
	private String ivMobilienart;
	
	/**
	 * Identifikationsnummer
	 */
	private String ivIdentifikationsnummer;
		
	/**
	 * Klassifizierung
	 */
	private String ivKlassifizierung;

	/**
	 * Beschreibung des Objekts
	 */
	private String ivBeschreibungObjekt;

	/**
	 * Herstellungs-/Kaufdatum
	 */
	private String ivHerstellungsdatum;
	
	/**
	 * Ext. Ordnungsnummer/Nummer fuer Objektident.
	 */
	private String ivOrdnungsnummer;
	
	/**
	 * MSN/ESN
	 */
	private String ivMSN;
	
	/**
	 * Triebwerkstyp (Modell)
	 */
	private String ivTriebwerkstyp;
	
	/**
	 * Triebwerksvariante
	 */
	private String ivTriebwerksvariante;
	
	/**
	 * ESN Triebwerk 1
	 */
	private String ivTriebwerk1;
	
	/**
	 * ESN Triebwerk 2
	 */
	private String ivTriebwerk2;
	
	/**
	 * ESN Triebwerk 3
	 */
	private String ivTriebwerk3;
	
	/**
	 * ESN Triebwerk 4
	 */
	private String ivTriebwerk4;
	
	/**
	 * HerstellerTriebwerk
	 */
	private String ivHerstellerTriebwerk;
	
	/**
	 * Flugzeugtyp (Modell)
	 */
	private String ivFlugzeugtyp;
	
	/**
	 * Flugzeugvariante
	 */
	private String ivFlugzeugvariante;
	
	/**
	 * Land der Registrierung/Eintragung
	 */
	private String ivLandRegistrierung;
	
	/**
	 * Kommentarfeld f�r Standortbeschreibung
	 */
	private String ivKommentarfeld;
	
	/**
	 * Betriebsstatus
	 */
	private String ivBetriebsstatus;
	
	/**
	 * Betriebsstatus - alt
	 */
	private String ivBetriebsstatusAlt;

	/**
	 * Branchennummer
	 */
	private String ivBranchennummer;
	
	/**
	 * TXS Deckungskennzeichen
	 */
	private String ivDeckungskennzeichenTXS;
	
	/**
	 * Zeitstempel TXS Deckungskennzeichen
	 */
	private String ivZeitstempelDeckungskennzeichenTXS;
	
	/**
	 * Relevant fuer Deckungsregister
	 */
	private String ivDeckungsregisterRelevant;
	
	/**
	 * Waehrung des Beleihungswerts bei Indeckungnahme
	 */
	private String ivWaehrungBeleihungswertInitial;
	
	/**
	 * Beleihungswert bei Indeckungsnahme
	 */
	private String ivBeleihungswertInitial;
	
	/**
	 * Datum
	 */
	private String ivDatum;
	
	/**
	 * In Deckung
	 */
	private String ivInDeckung;
	
	/**
	 * Status Deckung
	 */
	private String ivStatusDeckung;
	
	/**
	 * Deckungsfaehig
	 */
	private String ivDeckungsfaehig;
	
	/**
	 * Waehrung des aktuellen Beleihungswert
	 */
	private String ivWaehrungBeleihungswertAktuell;
	
	/**
	 * Aktueller Beleihungswert
	 */
	private String ivBeleihungswertAktuell;
	
	/**
	 * Datum des aktuellen Beleihungswert 
	 */
	private String ivDatumBeleihungswertAktuell;
	
	/**
	 * Registernummer
	 */
	private String ivRegisternummer;
	
	/**
	 * Registrierungsdatum
	 */
	private String ivRegistrierungsdatum;
	
	/**
	 * Registerort
	 */
	private String ivRegisterort;
	
	/**
	 * Registerland
	 */
	private String ivRegisterland;
	
	/**
	 * Registerblatt
	 */
	private String ivRegisterblatt;

	/**
	 * log4j-Logger
	 */
	private Logger ivLogger;

	/**
	 * Konstruktor
	 * @param pvLogger log4j-Logger
	 */
	public Flugzeug(Logger pvLogger)
	{
		this.ivLogger = pvLogger;
		this.ivId = new String();
		this.ivReferenzId = new String();
		this.ivNominalwert = new String();
		this.ivWaehrungNominalwert = new String();
		this.ivVermoegenswert = new String();
		this.ivWaehrungVermoegenswert = new String();
		this.ivObjekttyp = new String();
		this.ivMobilienart = new String();
		this.ivIdentifikationsnummer = new String();
		this.ivKlassifizierung = new String();
		this.ivBeschreibungObjekt = new String();
		this.ivHerstellungsdatum = new String();
		this.ivOrdnungsnummer = new String();
		this.ivMSN = new String();
		this.ivTriebwerkstyp = new String();
		this.ivTriebwerksvariante = new String(); 
		this.ivTriebwerk1 = new String();
		this.ivTriebwerk2 = new String();
		this.ivTriebwerk3 = new String();
		this.ivTriebwerk4 = new String();
		this.ivHerstellerTriebwerk = new String();
		this.ivFlugzeugtyp = new String();
		this.ivFlugzeugvariante = new String();
		this.ivLandRegistrierung = new String();
		this.ivKommentarfeld = new String();
		this.ivBetriebsstatus = new String();
		this.ivBetriebsstatusAlt = new String();
		this.ivBranchennummer = new String();
		this.ivDeckungskennzeichenTXS = new String();
		this.ivZeitstempelDeckungskennzeichenTXS = new String();
		this.ivDeckungsregisterRelevant = new String();
		this.ivWaehrungBeleihungswertInitial = new String();
		this.ivBeleihungswertInitial = new String();
		this.ivDatum = new String();
		this.ivInDeckung = new String();
		this.ivStatusDeckung = new String();
		this.ivDeckungsfaehig = new String();
		this.ivWaehrungBeleihungswertAktuell = new String();
		this.ivBeleihungswertAktuell = new String();
		this.ivDatumBeleihungswertAktuell = new String();
		this.ivRegisternummer = new String();
		this.ivRegistrierungsdatum = new String();
		this.ivRegisterort = new String();
		this.ivRegisterland = new String();
		this.ivRegisterblatt = new String();
	}

	/**
	 * Liefert die ID
	 * @return the ivId
	 */
	public String getId() {
		return ivId;
	}

	/**
	 * Setzt die ID
	 * @param pvId the ivId to set
	 */
	public void setId(String pvId) {
		this.ivId = pvId;
	}

	/**
	 * Liefert die Referenz-ID
	 * @return the ivReferenzId
	 */
	public String getReferenzId() {
		return ivReferenzId;
	}

	/**
	 * Setzt die Referenz-ID
	 * @param pvReferenzId the ivReferenzId to set
	 */
	public void setReferenzId(String pvReferenzId) {
		this.ivReferenzId = pvReferenzId;
	}

	/**
	 * Liefert den Nominalwert
	 * @return the ivNominalwert
	 */
	public String getNominalwert() {
		return ivNominalwert;
	}

	/**
	 * Setzt den Nominalwert
	 * @param pvNominalwert the ivNominalwert to set
	 */
	public void setNominalwert(String pvNominalwert) {
		this.ivNominalwert = pvNominalwert;
	}

	/**
	 * Liefert die Waehrung des Nominalwerts
	 * @return the ivWaehrungNominalwert
	 */
	public String getWaehrungNominalwert() {
		return ivWaehrungNominalwert;
	}

	/**
	 * Setzt die Waehrung des Nominalwerts
	 * @param pvWaehrungNominalwert the ivWaehrungNominalwert to set
	 */
	public void setWaehrungNominalwert(String pvWaehrungNominalwert) {
		this.ivWaehrungNominalwert = pvWaehrungNominalwert;
	}

	/**
	 * Liefert den Vermoegenswert
	 * @return the ivVermoegenswert
	 */
	public String getVermoegenswert() {
		return ivVermoegenswert;
	}

	/**
	 * Setzt den Vermoegenswert
	 * @param pvVermoegenswert the ivVermoegenswert to set
	 */
	public void setVermoegenswert(String pvVermoegenswert) {
		this.ivVermoegenswert = pvVermoegenswert;
	}

	/**
	 * Liefert die Waehrung des Vermoegenswert
	 * @return the ivWaehrungVermoegenswert
	 */
	public String getWaehrungVermoegenswert() {
		return ivWaehrungVermoegenswert;
	}

	/**
	 * Setzt die Waehrung des Vermoegenswert
	 * @param pvWaehrungVermoegenswert the ivWaehrungVermoegenswert to set
	 */
	public void setWaehrungVermoegenswert(String pvWaehrungVermoegenswert) {
		this.ivWaehrungVermoegenswert = pvWaehrungVermoegenswert;
	}

	/**
	 * Liefert den Objekttyp
	 * @return the ivObjekttyp
	 */
	public String getObjekttyp() {
		return ivObjekttyp;
	}

	/**
	 * Setzt den Objekttyp
	 * @param pvObjekttyp the ivObjekttyp to set
	 */
	public void setObjekttyp(String pvObjekttyp) {
		this.ivObjekttyp = pvObjekttyp;
	}

	/**
	 * Liefert die Mobilienart
	 * @return the ivMobilienart
	 */
	public String getMobilienart() {
		return ivMobilienart;
	}

	/**
	 * Setzt die Mobilienart
	 * @param pvMobilienart the ivMobilienart to set
	 */
	public void setMobilienart(String pvMobilienart) {
		this.ivMobilienart = pvMobilienart;
	}

	/**
	 * Liefert die Identifikationsnummer
	 * @return the ivIdentifikationsnummer
	 */
	public String getIdentifikationsnummer() {
		return ivIdentifikationsnummer;
	}

	/**
	 * Setzt die Identifikationsnummer
	 * @param pvIdentifikationsnummer the ivIdentifikationsnummer to set
	 */
	public void setIdentifikationsnummer(String pvIdentifikationsnummer) {
		this.ivIdentifikationsnummer = pvIdentifikationsnummer;
	}
	
	/**
	 * Liefert die Klassifizierung
	 * @return the ivKlassifizierung
	 */
	public String getKlassifizierung() {
		return ivKlassifizierung;
	}

	/**
	 * Setzt die Klassifizierung
	 * @param pvKlassifizierung the ivKlassifizierung to set
	 */
	public void setKlassifizierung(String pvKlassifizierung) {
		this.ivKlassifizierung = pvKlassifizierung;
	}

	/**
	 * Liefert die Beschreibung des Objekts
	 * @return the ivBeschreibungObjekt
	 */
	public String getBeschreibungObjekt() {
		return ivBeschreibungObjekt;
	}

	/**
	 * Setzt die Beschreibung des Objekts
	 * @param pvBeschreibungObjekt the ivBeschreibungObjekt to set
	 */
	public void setBeschreibungObjekt(String pvBeschreibungObjekt) {
		this.ivBeschreibungObjekt = pvBeschreibungObjekt;
	}

	/**
	 * Liefert das Herstellungsdatum
	 * @return
	 */
	public String getHerstellungsdatum() 
	{
		return ivHerstellungsdatum;
	}

	/**
	 * Setzt das Herstellungsdatum
	 * @param pvHerstellungsdatum
	 */
	public void setHerstellungsdatum(String pvHerstellungsdatum) 
	{
		this.ivHerstellungsdatum = pvHerstellungsdatum;
	}

	/**
	 * Liefert die Ordnungsnummer
	 * @return
	 */
	public String getOrdnungsnummer() 
	{
		return ivOrdnungsnummer;
	}

	/**
	 * Setzt die Ordnungsnummer
	 * @param pvOrdnungsnummer
	 */
	public void setOrdnungsnummer(String pvOrdnungsnummer) 
	{
		this.ivOrdnungsnummer = pvOrdnungsnummer;
	}

	/**
	 * Liefert die MSN
	 * @return
	 */
	public String getMSN() 
	{
		return ivMSN;
	}

	/**
	 * Setzt die MSN
	 * @param pvMSN
	 */
	public void setMSN(String pvMSN) 
	{
		this.ivMSN = pvMSN;
	}

	/**
	 * Liefert den Triebwerkstyp
	 * @return
	 */
	public String getTriebwerkstyp() 
	{
		return ivTriebwerkstyp;
	}

	/**
	 * Setzt den Triebwerkstyp
	 * @param pvTriebwerkstyp
	 */
	public void setTriebwerkstyp(String pvTriebwerkstyp) 
	{
		this.ivTriebwerkstyp = pvTriebwerkstyp;
	}

	/**
	 * Liefert die Triebwerksvariante
	 * @return
	 */
	public String getTriebwerksvariante() 
	{
		return ivTriebwerksvariante;
	}
	
    /**
     * Setzt die Triebwerksvariante
     * @param pvTriebwerksvariante
     */
	public void setTriebwerksvariante(String pvTriebwerksvariante) 
	{
		this.ivTriebwerksvariante = pvTriebwerksvariante;
	}

	/**
	 * Liefert das erste Triebwerk
	 * @return
	 */
	public String getTriebwerk1() 
	{
		return ivTriebwerk1;
	}

	/**
	 * Setzt das erste Triebwerk
	 * @param pvTriebwerk1
	 */
	public void setTriebwerk1(String pvTriebwerk1) 
	{
		this.ivTriebwerk1 = pvTriebwerk1;
	}

	/**
	 * Liefert das zweite Triebwerk
	 * @return
	 */
	public String getTriebwerk2() 
	{
		return ivTriebwerk2;
	}

	/**
	 * Setzt das zweite Triebwerk
	 * @param pvTriebwerk2
	 */
	public void setTriebwerk2(String pvTriebwerk2) 
	{
		this.ivTriebwerk2 = pvTriebwerk2;
	}

	/**
	 * Liefert das dritte Triebwerk
	 * @return
	 */
	public String getTriebwerk3() 
	{
		return ivTriebwerk3;
	}

	/**
	 * Setzt das dritte Triebwerk
	 * @param pvTriebwerk3
	 */
	public void setTriebwerk3(String pvTriebwerk3) 
	{
		this.ivTriebwerk3 = pvTriebwerk3;
	}

	/**
	 * Liefert das vierte Triebwerk
	 * @return
	 */
	public String getTriebwerk4() 
	{
		return ivTriebwerk4;
	}

	/**
	 * Setzt das vierte Triebwerk
	 * @param pvTriebwerk4
	 */
	public void setTriebwerk4(String pvTriebwerk4) 
	{
		this.ivTriebwerk4 = pvTriebwerk4;
	}

	/**
	 * Liefert den Hersteller der Triebwerke
	 * @return
	 */
	public String getHerstellerTriebwerk() 
	{
		return ivHerstellerTriebwerk;
	}

	/**
	 * Setzt den Hersteller der Triebwerke
	 * @param pvHerstellerTriebwerk
	 */
	public void setHerstellerTriebwerk(String pvHerstellerTriebwerk) 
	{
		this.ivHerstellerTriebwerk = pvHerstellerTriebwerk;
	}

	/**
	 * Liefert den Flugzeugtyp
	 * @return the ivFlugzeugtyp
	 */
	public String getFlugzeugtyp() {
		return ivFlugzeugtyp;
	}

	/**
	 * Setzt den Flugzeugtyp
	 * @param pvFlugzeugtyp the ivFlugzeugtyp to set
	 */
	public void setFlugzeugtyp(String pvFlugzeugtyp) {
		this.ivFlugzeugtyp = pvFlugzeugtyp;
	}

	/**
	 * Liefert die Flugzeugvariante
	 * @return the ivFlugzeugvariante
	 */
	public String getFlugzeugvariante() {
		return ivFlugzeugvariante;
	}

	/**
	 * Setzt die Flugzeugvariante
	 * @param pvFlugzeugvariante the ivFlugzeugvariante to set
	 */
	public void setFlugzeugvariante(String pvFlugzeugvariante) {
		this.ivFlugzeugvariante = pvFlugzeugvariante;
	}

	/**
	 * Liefert das Land der Registrierung
	 * @return the ivLandRegistrierung
	 */
	public String getLandRegistrierung() {
		return ivLandRegistrierung;
	}

	/**
	 * Setzt das Land der Registrierung
	 * @param pvLandRegistrierung the ivLandRegistrierung to set
	 */
	public void setLandRegistrierung(String pvLandRegistrierung) {
		this.ivLandRegistrierung = pvLandRegistrierung;
	}

	/**
	 * Liefert das Kommentarfeld
	 * @return the ivKommentarfeld
	 */
	public String getKommentarfeld() {
		return ivKommentarfeld;
	}

	/**
	 * Setzt das Kommentarfeld
	 * @param pvKommentarfeld the ivKommentarfeld to set
	 */
	public void setKommentarfeld(String pvKommentarfeld) {
		this.ivKommentarfeld = pvKommentarfeld;
	}

	/**
	 * Liefert den Betriebsstatus
	 * @return the ivBetriebsstatus
	 */
	public String getBetriebsstatus() {
		return ivBetriebsstatus;
	}

	/**
	 * Setzt den Betriebsstatus
	 * @param pvBetriebsstatus the ivBetriebsstatus to set
	 */
	public void setBetriebsstatus(String pvBetriebsstatus) {
		this.ivBetriebsstatus = pvBetriebsstatus;
	}

	/**
	 * Liefert den "alten" Betriebsstatus
	 * @return the ivBetriebsstatusAlt
	 */
	public String getBetriebsstatusAlt() {
		return ivBetriebsstatusAlt;
	}

	/**
	 * Setzt den "alten" Betriebsstatus
	 * @param pvBetriebsstatusAlt the ivBetriebsstatusAlt to set
	 */
	public void setBetriebsstatusAlt(String pvBetriebsstatusAlt) {
		this.ivBetriebsstatusAlt = pvBetriebsstatusAlt;
	}

	/**
	 * Liefert die Branchennummer
	 * @return
	 */
	public String getBranchennummer() 
	{
		return ivBranchennummer;
	}

	/**
	 * Setzt die Branchennummer
	 * @param pvBranchennummer
	 */
	public void setBranchennummer(String pvBranchennummer) 
	{
		this.ivBranchennummer = pvBranchennummer;
	}

	/**
	 * Liefert das Deckungskennzeichen von TXS
	 * @return
	 */
    public String getDeckungskennzeichenTXS() 
    {
		return ivDeckungskennzeichenTXS;
	}

    /**
     * Setzt das Deckungskennzeichen von TXS
     * @param pvDeckungskennzeichenTXS
     */
	public void setDeckungskennzeichenTXS(String pvDeckungskennzeichenTXS) 
	{
		this.ivDeckungskennzeichenTXS = pvDeckungskennzeichenTXS;
	}

	/**
	 * Liefert den Zeitstempel des Deckungskennzeichen von TXS 
	 * @return
	 */
	public String getZeitstempelDeckungskennzeichenTXS() 
	{
		return ivZeitstempelDeckungskennzeichenTXS;
	}

	/**
	 * Setzt den Zeitstempel des Deckungskennzeichen von TXS
	 * @param pvZeitstempelDeckungskennzeichenTXS
	 */
	public void setZeitstempelDeckungskennzeichenTXS(String pvZeitstempelDeckungskennzeichenTXS) 
	{
		this.ivZeitstempelDeckungskennzeichenTXS = pvZeitstempelDeckungskennzeichenTXS;
	}

	/**
	 * Liefert das Kennzeichen, ob das Flugzeug Deckungsregister relevant ist
	 * @return
	 */
	public String getDeckungsregisterRelevant()
	{
		return this.ivDeckungsregisterRelevant;
	}
	
	/**
	 * Setzt das Kennzeichen, ob das Flug Deckungsregister relevant ist
	 * @param
	 */
	public void setDeckungsregisterRelevant(String pvDeckungsregisterRelevant)
	{
		this.ivDeckungsregisterRelevant = pvDeckungsregisterRelevant;
	}
	
	/**
	 * Liefert die Waehrung des initialen Beleihungswert
	 * @return
	 */
	public String getWaehrungBeleihungswertInitial() 
	{
		return ivWaehrungBeleihungswertInitial;
	}

	/**
	 * Setzt die Waehrunh des initialen Beleihungswert
	 * @param pvWaehrungBeleihungswertInitial
	 */
	public void setWaehrungBeleihungswertInitial(String pvWaehrungBeleihungswertInitial) 
	{
		this.ivWaehrungBeleihungswertInitial = pvWaehrungBeleihungswertInitial;
	}

	/**
	 * Liefert den initialen Beleihungswert
	 * @return
	 */
	public String getBeleihungswertInitial() 
	{
		return ivBeleihungswertInitial;
	}

	/**
	 * Setzt den initialen Beleihungswert
	 * @param pvBeleihungswertInitial
	 */
	public void setBeleihungswertInitial(String pvBeleihungswertInitial) 
	{
		this.ivBeleihungswertInitial = pvBeleihungswertInitial;
	}

	/**
	 * Liefert das Datum
	 * @return
	 */
	public String getDatum() 
	{
		return ivDatum;
	}

	/**
	 * Setzt das Datum
	 * @param pvDatum
	 */
	public void setDatum(String pvDatum) 
	{
		this.ivDatum = pvDatum;
	}

	/**
	 * Liefert das Kennzeichen, ob das Flugzeug in Deckung ist
	 * @return
	 */
	public String getInDeckung() 
	{
		return ivInDeckung;
	}

	/**
	 * Setzt das Kennzeichen, ob das Flugzeug in Deckung ist
	 * @param pvInDeckung
	 */
	public void setInDeckung(String pvInDeckung) 
	{
		this.ivInDeckung = pvInDeckung;
	}

	/**
	 * Liefert den Deckungsstatus
	 * @return
	 */
	public String getStatusDeckung() 
	{
		return ivStatusDeckung;
	}

	/**
	 * Setzt den Deckungsstatus
	 * @param pvStatusDeckung
	 */
	public void setStatusDeckung(String pvStatusDeckung) 
	{
		this.ivStatusDeckung = pvStatusDeckung;
	}

	/**
	 * Liefert das Kennzeichen 'Deckungsfaehig'
	 * @return
	 */
	public String getDeckungsfaehig() 
	{
		return ivDeckungsfaehig;
	}

	/**
	 * Setzt das Kennzeichen 'Deckungsfaehig'
	 * @param pvDeckungsfaehig
	 */
	public void setDeckungsfaehig(String pvDeckungsfaehig) 
	{
		this.ivDeckungsfaehig = pvDeckungsfaehig;
	}

	/**
	 * Liefert die Waehrung des aktuellen Beleihungswert
	 * @return
	 */
	public String getWaehrungBeleihungswertAktuell() 
	{
		return ivWaehrungBeleihungswertAktuell;
	}

	/**
	 * Setzt die Waehrung des aktuellen Beleihungswert
	 * @param pvWaehrungBeleihungswertAktuell
	 */
	public void setWaehrungBeleihungswertAktuell(String pvWaehrungBeleihungswertAktuell) 
	{
		this.ivWaehrungBeleihungswertAktuell = pvWaehrungBeleihungswertAktuell;
	}

	/**
	 * Liefert den aktuellen Beleihungswert
	 * @return
	 */
	public String getBeleihungswertAktuell() 
	{
		return ivBeleihungswertAktuell;
	}

	/**
	 * Setzt den aktuellen Beliehungswert
	 * @param pvBeleihungswertAktuell
	 */
	public void setBeleihungswertAktuell(String pvBeleihungswertAktuell) 
	{
		this.ivBeleihungswertAktuell = pvBeleihungswertAktuell;
	}

	/**
	 * Liefert das Datum des aktuellen Beleihungswert
	 * @return
	 */
	public String getDatumBeleihungswertAktuell() 
	{
		return ivDatumBeleihungswertAktuell;
	}

	/**
	 * Setzt das Datum des aktuellen Beleihungswert
	 * @param pvDatumBeleihungswertAktuell
	 */
	public void setDatumBeleihungswertAktuell(String pvDatumBeleihungswertAktuell) 
	{
		this.ivDatumBeleihungswertAktuell = pvDatumBeleihungswertAktuell;
	}

	/**
	 * Liefert die Registernummer
	 * @return
	 */
	public String getRegisternummer() 
	{
		return ivRegisternummer;
	}

	/**
	 * Setzt die Registernummer
	 * @param pvRegisternummer
	 */
	public void setRegisternummer(String pvRegisternummer) 
	{
		this.ivRegisternummer = pvRegisternummer;
	}

	/**
	 * Liefert das Datum der Registrierung
	 * @return
	 */
	public String getRegistrierungsdatum() 
	{
		return ivRegistrierungsdatum;
	}

	/**
	 * Setzt das Datum der Registrierung
	 * @param pvRegistrierungsdatum
	 */
	public void setRegistrierungsdatum(String pvRegistrierungsdatum) 
	{
		this.ivRegistrierungsdatum = pvRegistrierungsdatum;
	}

	/**
	 * Liefert den Registerort
	 * @return
	 */
	public String getRegisterort() 
	{
		return ivRegisterort;
	}

	/**
	 * Setzt den Registerort
	 * @param pvRegisterort
	 */
	public void setRegisterort(String pvRegisterort) 
	{
		this.ivRegisterort = pvRegisterort;
	}

	/**
	 * Liefert das Registerland
	 * @return
	 */
	public String getRegisterland() 
	{
		return ivRegisterland;
	}

	/**
	 * Setzt das Registerland
	 * @param pvRegisterland
	 */
	public void setRegisterland(String pvRegisterland) 
	{
		this.ivRegisterland = pvRegisterland;
	}

	/**
	 * Liefert das Registerblatt
	 * @return
	 */
	public String getRegisterblatt() 
	{
		return ivRegisterblatt;
	}

	/**
	 * Setzt das Registerblatt
	 * @param pvRegisterblatt
	 */
	public void setRegisterblatt(String pvRegisterblatt) 
	{
		this.ivRegisterblatt = pvRegisterblatt;
	}

	  /**
	   * Zerlegt die Zeile in die einzelnen Daten/Felder
     * @param pvZeile Die zu zerlegende Zeile
		 * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseFlugzeug(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setFlugzeugSPOT(lvLfd, lvTemp);
       
         lvLfd++;                  // naechste Feldnummer
       
         // loeschen Zwischenbuffer
         lvTemp = new String();
       }
       else
       {
           // uebernehmen byte aus eingabesatzposition
           lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
       }
     } // ende for
     
     // Letzte Feld auch noch setzen
     this.setFlugzeugSPOT(lvLfd, lvTemp);
   }

	/**
	 * Setzt einen Wert des Flugzeuges
	 * @param pvPos Position
	 * @param pvWert Wert
	 */
	private void setFlugzeug(int pvPos, String pvWert)
	{
		switch (pvPos)
		{
			case 0:
				// Satzart
				break;
			case 1:
				this.setId(pvWert);
				break;
			case 2:
				this.setReferenzId(pvWert);
				break;
			case 3:
				this.setNominalwert(((pvWert.trim()).replace(".",  "")).replace(",","."));
				break;
			case 4:
				this.setWaehrungNominalwert(pvWert);
				break;
			case 5:
				this.setVermoegenswert(((pvWert.trim()).replace(".",  "")).replace(",","."));
				break;
			case 6:
				this.setWaehrungVermoegenswert(pvWert);
				break;
			case 7:
			  // Festsetzungsdatum
			  // Einfach ueberlesen
			  break;
			case 8:
				this.setObjekttyp(pvWert);
				break;
			case 9:
				this.setMobilienart(pvWert);
				break;
			case 10:
				this.setIdentifikationsnummer(pvWert);
				break;
			case 11:
				this.setKlassifizierung(pvWert);
				break;
			case 12:
				this.setBeschreibungObjekt(pvWert);
				break;
			case 13:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setHerstellungsdatum(pvWert);
				break;
			case 14:
				this.setOrdnungsnummer(pvWert);
				break;
			case 15:
				this.setMSN(pvWert);
				break;
			case 16:
				this.setTriebwerkstyp(pvWert);
				break;
			case 17:
				this.setTriebwerksvariante(pvWert);
				break;
			case 18:
				this.setTriebwerk1(pvWert);
				break;
			case 19:
				this.setTriebwerk2(pvWert);
				break;
			case 20:
				this.setTriebwerk3(pvWert);
				break;
			case 21:
				this.setTriebwerk4(pvWert);
				break;
			case 22:
				this.setHerstellerTriebwerk(pvWert);
				break;
			case 23:
				this.setFlugzeugtyp(pvWert);
				break;
			case 24:
				this.setFlugzeugvariante(pvWert);
				break;
			case 25:
				this.setLandRegistrierung(pvWert);
				break;
			case 26:
				this.setKommentarfeld(pvWert);
				break;
			case 27:
				this.setBetriebsstatus(pvWert);
				break;
			case 28:
				this.setBetriebsstatusAlt(pvWert);
				break;
			case 29:
				this.setBranchennummer(pvWert);
				break;
			case 30:
				this.setDeckungskennzeichenTXS(pvWert);
				break;
			case 31:
				this.setZeitstempelDeckungskennzeichenTXS(pvWert);
				break;
			case 32:
			  this.setDeckungsregisterRelevant(pvWert);
			  break;
			case 33:
				this.setWaehrungBeleihungswertInitial(pvWert);
				break;
			case 34:
				this.setBeleihungswertInitial(((pvWert.trim()).replace(".",  "")).replace(",","."));
				break;
			case 35:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setDatum(pvWert);
				break;
			case 36:
				this.setInDeckung(pvWert);
				break;
			case 37:
				this.setStatusDeckung(pvWert);
				break;
			case 38:
				this.setDeckungsfaehig(pvWert);
				break;
			case 39:
				this.setWaehrungBeleihungswertAktuell(pvWert);
				break;
			case 40:
				this.setBeleihungswertAktuell(((pvWert.trim()).replace(".",  "")).replace(",","."));
				break;
			case 41:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setDatumBeleihungswertAktuell(pvWert);
				break;
			case 42:
				this.setRegisternummer(pvWert);
				break;
			case 43:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setRegistrierungsdatum(pvWert);
				break;
			case 44:
				this.setRegisterort(pvWert);
				break;
			case 45:
				this.setRegisterland(pvWert);
				break;
			case 46:
				this.setRegisterblatt(pvWert);
				break;
			default:
				ivLogger.info("Flugzeug: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
		}
	}

	/**
     * Setzt einen Wert des Flugzeuges - Variante SPOT
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setFlugzeugSPOT(int pvPos, String pvWert)
    {
    	//System.out.println("Pos: " + pvPos + " Wert: " + pvWert);
        switch (pvPos)
        {
          case 0:
              // Satzart
              break;
          case 1:
        	  this.setId(pvWert);
        	  break;
          case 2:
      		this.setReferenzId(pvWert);
      		break;
          case 3:
    		     ////this.setNominalwert(((pvWert.trim()).replace(".",  "")).replace(",","."));
						this.setNominalwert(pvWert.trim().replace(",","."));
    		   break;
          case 4:
    		    this.setWaehrungNominalwert(pvWert);
    		    break;
          case 5:
    		     ////this.setVermoegenswert(((pvWert.trim()).replace(".",  "")).replace(",","."));
						this.setVermoegenswert(pvWert.trim().replace(",","."));
						break;
          case 6:
    		this.setWaehrungVermoegenswert(pvWert);
    		break;
          //case 7:
        	  // Festsetzungsdatum
        	  // Einfach ueberlesen
        	  //break;
          case 7:
    		this.setObjekttyp(pvWert);
    		break;
          case 8:
    		this.setMobilienart(pvWert);
    		break;
          case 9:
    		this.setIdentifikationsnummer(pvWert);
    		break;
          case 10:
    		this.setKlassifizierung(pvWert);
    		break;
          case 11:
    		this.setBeschreibungObjekt(pvWert);
    		break;
          case 12:
          	if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
        	  this.setHerstellungsdatum(pvWert);
        	  break;
          case 13:
        	  this.setOrdnungsnummer(pvWert);
        	  break;
          case 14:
        	  this.setMSN(pvWert);
        	  break;
          case 15:
        	  this.setTriebwerkstyp(pvWert);
        	  break;
          case 16:
        	  this.setTriebwerksvariante(pvWert);
        	  break;
          case 17:
        	  this.setTriebwerk1(pvWert);
        	  break;
          case 18:
        	  this.setTriebwerk2(pvWert);
        	  break;
          case 19:
        	  this.setTriebwerk3(pvWert);
        	  break;
          case 20:
        	  this.setTriebwerk4(pvWert);
        	  break;
          case 21:
        	  this.setHerstellerTriebwerk(pvWert);
        	  break;
          case 22:
        	  this.setFlugzeugtyp(pvWert);
        	  break;
          case 23:
        	  this.setFlugzeugvariante(pvWert);
        	  break;
          case 24:
        	  this.setLandRegistrierung(pvWert);
        	  break;
          case 25:
        	  this.setKommentarfeld(pvWert);
        	  break;
          case 26:
        	  this.setBetriebsstatus(pvWert);
        	  break;
          case 27:
        	  this.setBetriebsstatusAlt(pvWert);
        	  break;
          case 28:
        	  this.setBranchennummer(pvWert);
        	  break;
          case 29:
        	  this.setDeckungskennzeichenTXS(pvWert);
        	  break;
          case 30:
        	  this.setZeitstempelDeckungskennzeichenTXS(pvWert);
        	  break;
          //case 31:
        	//  this.setDeckungsregisterRelevant(pvWert);
        	//  break;
          case 31:
        	  this.setWaehrungBeleihungswertInitial(pvWert);
        	  break;
          case 32:
        	  ////this.setBeleihungswertInitial(((pvWert.trim()).replace(".",  "")).replace(",","."));
						this.setBeleihungswertInitial(pvWert.trim().replace(",","."));
        	  break;
          case 33:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
						this.setDatum(pvWert);
        	  break;
          case 34:
        	  this.setInDeckung(pvWert);
        	  break;
          case 35:
        	  this.setStatusDeckung(pvWert);
        	  break;
          case 36:
        	  this.setDeckungsfaehig(pvWert);
        	  break;
          case 37:
        	  this.setWaehrungBeleihungswertAktuell(pvWert);
        	  break;
          case 38:
        	  ////this.setBeleihungswertAktuell(((pvWert.trim()).replace(".",  "")).replace(",","."));
						this.setBeleihungswertAktuell(pvWert.trim().replace(",","."));
        	  break;
          case 39:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
						this.setDatumBeleihungswertAktuell(pvWert);
        	  break;
          case 40:
        	  this.setRegisternummer(pvWert);
        	  break;
          case 41:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
						this.setRegistrierungsdatum(pvWert);
        	  break;
          case 42:
        	  this.setRegisterort(pvWert);
        	  break;
          case 43:
        	  this.setRegisterland(pvWert);
        	  break;
          case 44:
        	  this.setRegisterblatt(pvWert);
        	  break;
         default:
              ivLogger.info("Flugzeug: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des Flugzeuges in einen String
     * @return
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
        lvOut.append("ReferenzId: " + ivReferenzId + StringKonverter.lineSeparator);
        lvOut.append("Nominalwert: " + this.ivNominalwert + StringKonverter.lineSeparator);
        lvOut.append("WaehrungNominalwert: " + this.ivWaehrungNominalwert + StringKonverter.lineSeparator);
        lvOut.append("Vermoegenswert: " + this.ivVermoegenswert + StringKonverter.lineSeparator);
        lvOut.append("WaehrungVermoegenswert:" + this.ivWaehrungVermoegenswert + StringKonverter.lineSeparator);
        lvOut.append("Objekttyp: " + this.ivObjekttyp + StringKonverter.lineSeparator);
        lvOut.append("Mobilienart: " + this.ivMobilienart + StringKonverter.lineSeparator);
        lvOut.append("Identifikationsnummer: " + this.ivIdentifikationsnummer + StringKonverter.lineSeparator);
        lvOut.append("Klassifizierung: " + this.ivKlassifizierung + StringKonverter.lineSeparator);
        lvOut.append("BeschreibungObjekt: " + this.ivBeschreibungObjekt + StringKonverter.lineSeparator);
        lvOut.append("Herstellungsdatum: " + this.ivHerstellungsdatum + StringKonverter.lineSeparator);
        lvOut.append("Ordnungsnummer: " + this.ivOrdnungsnummer + StringKonverter.lineSeparator);
        lvOut.append("MSN: " + this.ivMSN + StringKonverter.lineSeparator);
        lvOut.append("Triebwerkstyp: " + this.ivTriebwerkstyp + StringKonverter.lineSeparator);
        lvOut.append("Triebwerksvariante: " + this.ivTriebwerksvariante + StringKonverter.lineSeparator);
        lvOut.append("Triebwerk1: " + this.ivTriebwerk1 + StringKonverter.lineSeparator);
        lvOut.append("Triebwerk2: " + this.ivTriebwerk2 + StringKonverter.lineSeparator);
        lvOut.append("Triebwerk3: " + this.ivTriebwerk3 + StringKonverter.lineSeparator);
        lvOut.append("Triebwerk4: " + this.ivTriebwerk4 + StringKonverter.lineSeparator);
        lvOut.append("HerstellerTriebwerk: " + this.ivHerstellerTriebwerk + StringKonverter.lineSeparator);
        lvOut.append("Flugzeugtyp: " + this.ivFlugzeugtyp + StringKonverter.lineSeparator);
        lvOut.append("Flugzeugvariante: " + this.ivFlugzeugvariante + StringKonverter.lineSeparator);
        lvOut.append("LandRegistrierung: " + this.ivLandRegistrierung + StringKonverter.lineSeparator);
        lvOut.append("Kommentarfeld: " + this.ivKommentarfeld + StringKonverter.lineSeparator);
        lvOut.append("Betriebsstatus: " + this.ivBetriebsstatus + StringKonverter.lineSeparator);
        lvOut.append("BetriebsstatusAlt: " + this.ivBetriebsstatusAlt + StringKonverter.lineSeparator);
        lvOut.append("Branchennummer: " + this.ivBranchennummer + StringKonverter.lineSeparator);      
        lvOut.append("DeckungskennzeichenTXS: " + this.ivDeckungskennzeichenTXS + StringKonverter.lineSeparator);
        lvOut.append("Zeitstempel DeckungskennzeichenTXS: " + this.ivZeitstempelDeckungskennzeichenTXS + StringKonverter.lineSeparator);
        lvOut.append("DeckungsregisterRelevant: " + this.ivDeckungsregisterRelevant + StringKonverter.lineSeparator);
        lvOut.append("WaehrungBeleihungswertInitial: " + this.ivWaehrungBeleihungswertInitial + StringKonverter.lineSeparator);
        lvOut.append("BeleihungswertInitial: " + this.ivBeleihungswertInitial + StringKonverter.lineSeparator);
        lvOut.append("Datum: " + this.ivDatum + StringKonverter.lineSeparator);
        lvOut.append("InDeckung: " + this.ivInDeckung + StringKonverter.lineSeparator);
        lvOut.append("StatusDeckung: " + this.ivStatusDeckung + StringKonverter.lineSeparator);
        lvOut.append("Deckungsfaehig: " + this.ivDeckungsfaehig + StringKonverter.lineSeparator);
        lvOut.append("WaehrungBeleihungswertAktuell: " + this.ivWaehrungBeleihungswertAktuell + StringKonverter.lineSeparator);
        lvOut.append("BeleihungswertAktuell: " + this.ivBeleihungswertAktuell + StringKonverter.lineSeparator);
        lvOut.append("DatumBeleihungswertAktuell: " + this.ivDatumBeleihungswertAktuell + StringKonverter.lineSeparator);
        lvOut.append("Registernummer: " + this.ivRegisternummer + StringKonverter.lineSeparator);
        lvOut.append("Registrierdatum: " + this.ivRegistrierungsdatum + StringKonverter.lineSeparator);
        lvOut.append("Registerort: " + this.ivRegisterort + StringKonverter.lineSeparator);
        lvOut.append("Registerland: " + this.ivRegisterland + StringKonverter.lineSeparator);
        lvOut.append("Registerblatt: " + this.ivRegisterblatt + StringKonverter.lineSeparator);
        
        return lvOut.toString();
    }
}
