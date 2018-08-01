package nlb.txs.schnittstelle.SAPCMS.Entities;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

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
	 * Kommentarfeld für Standortbeschreibung
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
	 * Konstruktor
	 */
	public Flugzeug()
	{
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
	 * @return the ivId
	 */
	public String getId() {
		return ivId;
	}

	/**
	 * @param ivId the ivId to set
	 */
	public void setId(String ivId) {
		this.ivId = ivId;
	}

	/**
	 * @return the ivReferenzId
	 */
	public String getReferenzId() {
		return ivReferenzId;
	}

	/**
	 * @param ivReferenzId the ivReferenzId to set
	 */
	public void setReferenzId(String ivReferenzId) {
		this.ivReferenzId = ivReferenzId;
	}

	/**
	 * @return the ivNominalwert
	 */
	public String getNominalwert() {
		return ivNominalwert;
	}

	/**
	 * @param ivNominalwert the ivNominalwert to set
	 */
	public void setNominalwert(String ivNominalwert) {
		this.ivNominalwert = ivNominalwert;
	}

	/**
	 * @return the ivWaehrungNominalwert
	 */
	public String getWaehrungNominalwert() {
		return ivWaehrungNominalwert;
	}

	/**
	 * @param ivWaehrungNominalwert the ivWaehrungNominalwert to set
	 */
	public void setWaehrungNominalwert(String ivWaehrungNominalwert) {
		this.ivWaehrungNominalwert = ivWaehrungNominalwert;
	}

	/**
	 * @return the ivVermoegenswert
	 */
	public String getVermoegenswert() {
		return ivVermoegenswert;
	}

	/**
	 * @param ivVermoegenswert the ivVermoegenswert to set
	 */
	public void setVermoegenswert(String ivVermoegenswert) {
		this.ivVermoegenswert = ivVermoegenswert;
	}

	/**
	 * @return the ivWaehrungVermoegenswert
	 */
	public String getWaehrungVermoegenswert() {
		return ivWaehrungVermoegenswert;
	}

	/**
	 * @param ivWaehrungVermoegenswert the ivWaehrungVermoegenswert to set
	 */
	public void setWaehrungVermoegenswert(String ivWaehrungVermoegenswert) {
		this.ivWaehrungVermoegenswert = ivWaehrungVermoegenswert;
	}

	/**
	 * @return the ivObjekttyp
	 */
	public String getObjekttyp() {
		return ivObjekttyp;
	}

	/**
	 * @param ivObjekttyp the ivObjekttyp to set
	 */
	public void setObjekttyp(String ivObjekttyp) {
		this.ivObjekttyp = ivObjekttyp;
	}

	/**
	 * @return the ivMobilienart
	 */
	public String getMobilienart() {
		return ivMobilienart;
	}

	/**
	 * @param ivMobilienart the ivMobilienart to set
	 */
	public void setMobilienart(String ivMobilienart) {
		this.ivMobilienart = ivMobilienart;
	}

	/**
	 * @return the ivIdentifikationsnummer
	 */
	public String getIdentifikationsnummer() {
		return ivIdentifikationsnummer;
	}

	/**
	 * @param ivIdentifikationsnummer the ivIdentifikationsnummer to set
	 */
	public void setIdentifikationsnummer(String ivIdentifikationsnummer) {
		this.ivIdentifikationsnummer = ivIdentifikationsnummer;
	}
	
	/**
	 * @return the ivKlassifizierung
	 */
	public String getKlassifizierung() {
		return ivKlassifizierung;
	}

	/**
	 * @param ivKlassifizierung the ivKlassifizierung to set
	 */
	public void setKlassifizierung(String ivKlassifizierung) {
		this.ivKlassifizierung = ivKlassifizierung;
	}

	/**
	 * @return the ivBeschreibungObjekt
	 */
	public String getBeschreibungObjekt() {
		return ivBeschreibungObjekt;
	}

	/**
	 * @param ivBeschreibungObjekt the ivBeschreibungObjekt to set
	 */
	public void setBeschreibungObjekt(String ivBeschreibungObjekt) {
		this.ivBeschreibungObjekt = ivBeschreibungObjekt;
	}

	/**
	 * 
	 * @return
	 */
	public String getHerstellungsdatum() 
	{
		return ivHerstellungsdatum;
	}

	/**
	 * 
	 * @param ivHerstellungsdatum
	 */
	public void setHerstellungsdatum(String pvHerstellungsdatum) 
	{
		this.ivHerstellungsdatum = pvHerstellungsdatum;
	}

	/**
	 * 
	 * @return
	 */
	public String getOrdnungsnummer() 
	{
		return ivOrdnungsnummer;
	}

	/**
	 * 
	 * @param ivOrdnungsnummer
	 */
	public void setOrdnungsnummer(String pvOrdnungsnummer) 
	{
		this.ivOrdnungsnummer = pvOrdnungsnummer;
	}

	/**
	 * 
	 * @return
	 */
	public String getMSN() 
	{
		return ivMSN;
	}

	/**
	 * 
	 * @param ivMSN
	 */
	public void setMSN(String pvMSN) 
	{
		this.ivMSN = pvMSN;
	}

	/**
	 * 
	 * @return
	 */
	public String getTriebwerkstyp() 
	{
		return ivTriebwerkstyp;
	}

	/**
	 * 
	 * @param ivTriebwerkstyp
	 */
	public void setTriebwerkstyp(String pvTriebwerkstyp) 
	{
		this.ivTriebwerkstyp = pvTriebwerkstyp;
	}

	/**
	 * 
	 * @return
	 */
	public String getTriebwerksvariante() 
	{
		return ivTriebwerksvariante;
	}
	
    /**
     * 
     * @param ivTriebwerksvariante
     */
	public void setTriebwerksvariante(String pvTriebwerksvariante) 
	{
		this.ivTriebwerksvariante = pvTriebwerksvariante;
	}

	/**
	 * 
	 * @return
	 */
	public String getTriebwerk1() 
	{
		return ivTriebwerk1;
	}

	/**
	 * 
	 * @param ivTriebwerk1
	 */
	public void setTriebwerk1(String pvTriebwerk1) 
	{
		this.ivTriebwerk1 = pvTriebwerk1;
	}

	/**
	 * 
	 * @return
	 */
	public String getTriebwerk2() 
	{
		return ivTriebwerk2;
	}

	/**
	 * 
	 * @param ivTriebwerk2
	 */
	public void setTriebwerk2(String pvTriebwerk2) 
	{
		this.ivTriebwerk2 = pvTriebwerk2;
	}

	/**
	 * 
	 * @return
	 */
	public String getTriebwerk3() 
	{
		return ivTriebwerk3;
	}

	/**
	 * 
	 * @param ivTriebwerk3
	 */
	public void setTriebwerk3(String pvTriebwerk3) 
	{
		this.ivTriebwerk3 = pvTriebwerk3;
	}

	/**
	 * 
	 * @return
	 */
	public String getTriebwerk4() 
	{
		return ivTriebwerk4;
	}

	/**
	 * 
	 * @param ivTriebwerk4
	 */
	public void setTriebwerk4(String pvTriebwerk4) 
	{
		this.ivTriebwerk4 = pvTriebwerk4;
	}

	/**
	 * 
	 * @return
	 */
	public String getHerstellerTriebwerk() 
	{
		return ivHerstellerTriebwerk;
	}

	/**
	 * 
	 * @param ivHerstellerTriebwerk
	 */
	public void setHerstellerTriebwerk(String pvHerstellerTriebwerk) 
	{
		this.ivHerstellerTriebwerk = pvHerstellerTriebwerk;
	}

	/**
	 * @return the ivFlugzeugtyp
	 */
	public String getFlugzeugtyp() {
		return ivFlugzeugtyp;
	}

	/**
	 * @param ivFlugzeugtyp the ivFlugzeugtyp to set
	 */
	public void setFlugzeugtyp(String ivFlugzeugtyp) {
		this.ivFlugzeugtyp = ivFlugzeugtyp;
	}

	/**
	 * @return the ivFlugzeugvariante
	 */
	public String getFlugzeugvariante() {
		return ivFlugzeugvariante;
	}

	/**
	 * @param ivFlugzeugvariante the ivFlugzeugvariante to set
	 */
	public void setFlugzeugvariante(String ivFlugzeugvariante) {
		this.ivFlugzeugvariante = ivFlugzeugvariante;
	}

	/**
	 * @return the ivLandRegistrierung
	 */
	public String getLandRegistrierung() {
		return ivLandRegistrierung;
	}

	/**
	 * @param ivLandRegistrierung the ivLandRegistrierung to set
	 */
	public void setLandRegistrierung(String ivLandRegistrierung) {
		this.ivLandRegistrierung = ivLandRegistrierung;
	}

	/**
	 * @return the ivKommentarfeld
	 */
	public String getKommentarfeld() {
		return ivKommentarfeld;
	}

	/**
	 * @param ivKommentarfeld the ivKommentarfeld to set
	 */
	public void setKommentarfeld(String ivKommentarfeld) {
		this.ivKommentarfeld = ivKommentarfeld;
	}

	/**
	 * @return the ivBetriebsstatus
	 */
	public String getBetriebsstatus() {
		return ivBetriebsstatus;
	}

	/**
	 * @param ivBetriebsstatus the ivBetriebsstatus to set
	 */
	public void setBetriebsstatus(String ivBetriebsstatus) {
		this.ivBetriebsstatus = ivBetriebsstatus;
	}

	/**
	 * @return the ivBetriebsstatusAlt
	 */
	public String getBetriebsstatusAlt() {
		return ivBetriebsstatusAlt;
	}

	/**
	 * @param ivBetriebsstatusAlt the ivBetriebsstatusAlt to set
	 */
	public void setBetriebsstatusAlt(String ivBetriebsstatusAlt) {
		this.ivBetriebsstatusAlt = ivBetriebsstatusAlt;
	}

	/**
	 * 
	 * @return
	 */
	public String getBranchennummer() 
	{
		return ivBranchennummer;
	}

	/**
	 * 
	 * @param ivBranchennummer
	 */
	public void setBranchennummer(String pvBranchennummer) 
	{
		this.ivBranchennummer = pvBranchennummer;
	}

	/**
	 * 
	 * @return
	 */
    public String getDeckungskennzeichenTXS() 
    {
		return ivDeckungskennzeichenTXS;
	}

    /**
     * 
     * @param ivDeckungskennzeichenTXS
     */
	public void setDeckungskennzeichenTXS(String pvDeckungskennzeichenTXS) 
	{
		this.ivDeckungskennzeichenTXS = pvDeckungskennzeichenTXS;
	}

	/**
	 * 
	 * @return
	 */
	public String getZeitstempelDeckungskennzeichenTXS() 
	{
		return ivZeitstempelDeckungskennzeichenTXS;
	}

	public void setZeitstempelDeckungskennzeichenTXS(String pvZeitstempelDeckungskennzeichenTXS) 
	{
		this.ivZeitstempelDeckungskennzeichenTXS = pvZeitstempelDeckungskennzeichenTXS;
	}

	/**
	 * @return
	 */
	public String getDeckungsregisterRelevant()
	{
		return this.ivDeckungsregisterRelevant;
	}
	
	/**
	 * @param
	 */
	public void setDeckungsregisterRelevant(String pvDeckungsregisterRelevant)
	{
		this.ivDeckungsregisterRelevant = pvDeckungsregisterRelevant;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getWaehrungBeleihungswertInitial() 
	{
		return ivWaehrungBeleihungswertInitial;
	}

	/**
	 * 
	 * @param ivWaehrungBeleihungswertInitial
	 */
	public void setWaehrungBeleihungswertInitial(String pvWaehrungBeleihungswertInitial) 
	{
		this.ivWaehrungBeleihungswertInitial = pvWaehrungBeleihungswertInitial;
	}

	/**
	 * 
	 * @return
	 */
	public String getBeleihungswertInitial() 
	{
		return ivBeleihungswertInitial;
	}

	/**
	 * 
	 * @param ivBeleihungswertInitial
	 */
	public void setBeleihungswertInitial(String pvBeleihungswertInitial) 
	{
		this.ivBeleihungswertInitial = pvBeleihungswertInitial;
	}

	/**
	 * 
	 * @return
	 */
	public String getDatum() 
	{
		return ivDatum;
	}

	/**
	 * 
	 * @param ivDatum
	 */
	public void setDatum(String pvDatum) 
	{
		this.ivDatum = pvDatum;
	}

	/**
	 * 
	 * @return
	 */
	public String getInDeckung() 
	{
		return ivInDeckung;
	}

	/**
	 * 
	 * @param ivInDeckung
	 */
	public void setInDeckung(String pvInDeckung) 
	{
		this.ivInDeckung = pvInDeckung;
	}

	/**
	 * 
	 * @return
	 */
	public String getStatusDeckung() 
	{
		return ivStatusDeckung;
	}

	/**
	 * 
	 * @param ivStatusDeckung
	 */
	public void setStatusDeckung(String pvStatusDeckung) 
	{
		this.ivStatusDeckung = pvStatusDeckung;
	}

	/**
	 * 
	 * @return
	 */
	public String getDeckungsfaehig() 
	{
		return ivDeckungsfaehig;
	}

	/**
	 * 
	 * @param ivDeckungsfaehig
	 */
	public void setDeckungsfaehig(String pvDeckungsfaehig) 
	{
		this.ivDeckungsfaehig = pvDeckungsfaehig;
	}

	/**
	 * 
	 * @return
	 */
	public String getWaehrungBeleihungswertAktuell() 
	{
		return ivWaehrungBeleihungswertAktuell;
	}

	/**
	 * 
	 * @param ivWaehrungBeleihungswertAktuell
	 */
	public void setWaehrungBeleihungswertAktuell(String pvWaehrungBeleihungswertAktuell) 
	{
		this.ivWaehrungBeleihungswertAktuell = pvWaehrungBeleihungswertAktuell;
	}

	/**
	 * 
	 * @return
	 */
	public String getBeleihungswertAktuell() 
	{
		return ivBeleihungswertAktuell;
	}

	/**
	 * 
	 * @param ivBeleihungswertAktuell
	 */
	public void setBeleihungswertAktuell(String pvBeleihungswertAktuell) 
	{
		this.ivBeleihungswertAktuell = pvBeleihungswertAktuell;
	}

	/**
	 * 
	 * @return
	 */
	public String getDatumBeleihungswertAktuell() 
	{
		return ivDatumBeleihungswertAktuell;
	}

	/**
	 * 
	 * @param ivDatumBeleihungswertAktuell
	 */
	public void setDatumBeleihungswertAktuell(String pvDatumBeleihungswertAktuell) 
	{
		this.ivDatumBeleihungswertAktuell = pvDatumBeleihungswertAktuell;
	}

	/**
	 * 
	 * @return
	 */
	public String getRegisternummer() 
	{
		return ivRegisternummer;
	}

	/**
	 * 
	 * @param ivRegisternummer
	 */
	public void setRegisternummer(String pvRegisternummer) 
	{
		this.ivRegisternummer = pvRegisternummer;
	}

	/**
	 * 
	 * @return
	 */
	public String getRegistrierungsdatum() 
	{
		return ivRegistrierungsdatum;
	}

	/**
	 * 
	 * @param ivRegistrierungsdatum
	 */
	public void setRegistrierungsdatum(String pvRegistrierungsdatum) 
	{
		this.ivRegistrierungsdatum = pvRegistrierungsdatum;
	}

	/**
	 * 
	 * @return
	 */
	public String getRegisterort() 
	{
		return ivRegisterort;
	}

	/**
	 * 
	 * @param ivRegisterort
	 */
	public void setRegisterort(String pvRegisterort) 
	{
		this.ivRegisterort = pvRegisterort;
	}

	/**
	 * 
	 * @return
	 */
	public String getRegisterland() 
	{
		return ivRegisterland;
	}

	/**
	 * 
	 * @param ivRegisterland
	 */
	public void setRegisterland(String pvRegisterland) 
	{
		this.ivRegisterland = pvRegisterland;
	}

	/**
	 * 
	 * @return
	 */
	public String getRegisterblatt() 
	{
		return ivRegisterblatt;
	}

	/**
	 * 
	 * @param ivRegisterblatt
	 */
	public void setRegisterblatt(String pvRegisterblatt) 
	{
		this.ivRegisterblatt = pvRegisterblatt;
	}

	/**
     * @param pvZeile 
     * @return 
     * 
     */
    public boolean parseFlugzeug(String pvZeile)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
     int    lvZzStr=0;              // pointer fuer satzbereich
     
     // steuerung/iteration eingabesatz
     for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setFlugzeug(lvLfd, lvTemp);
       
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
     this.setFlugzeug(lvLfd, lvTemp);     
     
     return true;
   }
    
    /**
     * Setzt einen Wert des Flugzeuges
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setFlugzeug(int pvPos, String pvWert)
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
          case 31:
        	  this.setDeckungsregisterRelevant(pvWert); 
        	  break;
          case 32:
        	  this.setWaehrungBeleihungswertInitial(pvWert);
        	  break;
          case 33:
        	  this.setBeleihungswertInitial(((pvWert.trim()).replace(".",  "")).replace(",","."));
        	  break;
          case 34:
        	  this.setDatum(pvWert);
        	  break;
          case 35:
        	  this.setInDeckung(pvWert);
        	  break;
          case 36:
        	  this.setStatusDeckung(pvWert);
        	  break;
          case 37:
        	  this.setDeckungsfaehig(pvWert);
        	  break;
          case 38:
        	  this.setWaehrungBeleihungswertAktuell(pvWert);
        	  break;
          case 39:
        	  this.setBeleihungswertAktuell(((pvWert.trim()).replace(".",  "")).replace(",","."));
        	  break;
          case 40:
        	  this.setDatumBeleihungswertAktuell(pvWert);
        	  break;
          case 41:
        	  this.setRegisternummer(pvWert);
        	  break;
          case 42:
        	  this.setRegistrierungsdatum(pvWert);
        	  break;
          case 43:
        	  this.setRegisterort(pvWert);
        	  break;
          case 44:
        	  this.setRegisterland(pvWert);
        	  break;
          case 45:
        	  this.setRegisterblatt(pvWert);
        	  break;
         default:
              System.out.println("Flugzeug: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
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
