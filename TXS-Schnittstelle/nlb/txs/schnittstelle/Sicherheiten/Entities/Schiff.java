package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * 
 * @author tepperc
 *
 */
public class Schiff 
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
	 * Globally Unique Identifier
	 */
	private String ivGUI;
	
	/**
	 * Schiffsname
	 */
	private String ivSchiffsname;
	
	/**
	 * Chartername, unter dem das Schiff gechartert wird
	 */
	private String ivChartername;
	
	/**
	 * IMO-Nummer
	 */
	private String ivIMONummer;
	
	/**
	 * Schiff ID
	 */
	private String ivSchiffId;
	
	/**
	 * Schiffsart
	 */
	private String ivSchiffsart;
	
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
	 * Register Land
	 */
	private String ivRegisterLand;
	
	/**
	 * Registerblatt
	 */
	private String ivRegisterblatt;
	
	/**
	 * Waehrung des Beleihungswerts
	 */
	private String ivWaehrungBeleihungswertIndeckungnahme;
	
	/**
	 * Bel.wert bei Indeckungnahme
	 */
	private String ivBeleihungswertIndeckungnahme;
	
	/**
	 * Datum des Bel.wert bei Indeckungnahme
	 */
	private String ivDatumBeleihungswertIndeckungnahme;
	
	/**
	 * In Deckung
	 */
	private String ivInDeckung;
	
	/**
	 * CMS SHP NordLB: Status Deckung
	 */
	private String ivStatusDeckung;
	
	/**
	 * Deckungsfaehig
	 */
	private String ivDeckungsfaehig;
	
	/**
	 * Waehrung des aktuellen Beleihungswerts
	 */
	private String ivWaehrungBeleihungswertAktuell;
	
	/**
	 * Aktueller Bel.wert
	 */
	private String ivBeleihungswertAktuell;
	
	/**
	 * Datum des aktuellen Beleihungswert 
	 */
	private String ivDatumBeleihungswertAktuell;
	
	/**
	 * Zeitstempel fuer Deckungskennzeichen
	 */
	private String ivZeitstempelDeckungskennzeichen;
	
	/**
	 * Relevant fuer Deckungsregister
	 */
	private String ivDeckungsregisterRelevant;
	
	/**
	 * TXS Deckungskennzeichen
	 */
	private String ivDeckungskennzeichenTXS;
	
	/**
	 * Zeitstempel TXS Deckungskennzeichen
	 */
	private String ivZeitstempelDeckungskennzeichenTXS;
	
	/**
	 * Schiffs-Nummer
	 */
	private String ivSchiffsnummer;
		
	/**
	 * Schiffstypen Klassifizierung
	 */
	private String ivSchiffstypen;
	
	/**
	 * Werft, auf der das Schiff gebaut wurde
	 */
	private String ivWerft;
	
	/**
	 * Baunummer der Werft
	 */
	private String ivBaunummerWerft;
	
	/**
	 * Datum der Ausflaggung 
	 */
	private String ivDatumAusflaggung;
	
	/**
	 * CMS: Kennzeichen Schiffsbauwerk (ja/nein)
	 */
	private String ivKennzeichenSchiffsbauwerk;
	
	/**
	 * Auslieferungsdatum Soll
	 */
	private String ivAuslieferungsdatumSoll;
	
	/**
	 * Ablieferungsdatum des Schiffes
	 */
	private String ivAblieferungsdatum;

	/**
	 * log4j-Logger
	 */
	private Logger ivLogger;

	/**
	 * Konstruktor
	 * @param pvLogger log4j-Logger
	 */
	public Schiff(Logger pvLogger)
	{
		this.ivLogger = pvLogger;
		this.ivId = new String();
		this.ivReferenzId = new String();
		this.ivNominalwert = new String();
		this.ivWaehrungNominalwert = new String();
		this.ivVermoegenswert = new String();
		this.ivWaehrungVermoegenswert = new String();
		this.ivGUI = new String();
		this.ivSchiffsname = new String();
		this.ivChartername = new String();
		this.ivIMONummer = new String();
		this.ivSchiffId = new String();
		this.ivSchiffsart = new String();
		this.ivRegisternummer = new String();
		this.ivRegistrierungsdatum = new String();
		this.ivRegisterort = new String();
		this.ivRegisterLand = new String();
		this.ivRegisterblatt = new String();
		this.ivWaehrungBeleihungswertIndeckungnahme = new String();
		this.ivBeleihungswertIndeckungnahme = new String();
		this.ivDatumBeleihungswertIndeckungnahme = new String();
		this.ivInDeckung = new String();
		this.ivStatusDeckung = new String();
		this.ivDeckungsfaehig = new String();
		this.ivWaehrungBeleihungswertAktuell = new String();
		this.ivBeleihungswertAktuell = new String();
		this.ivDatumBeleihungswertAktuell = new String();
		this.ivZeitstempelDeckungskennzeichen = new String();
		this.ivDeckungskennzeichenTXS = new String();
		this.ivZeitstempelDeckungskennzeichenTXS = new String();
		this.ivDeckungsregisterRelevant = new String();
		this.ivSchiffsnummer = new String();
		this.ivSchiffstypen = new String();
		this.ivWerft = new String();
		this.ivBaunummerWerft = new String();
		this.ivDatumAusflaggung = new String();
		this.ivKennzeichenSchiffsbauwerk = new String();
		this.ivAuslieferungsdatumSoll = new String();
		this.ivAblieferungsdatum = new String();
	}

	/**
	 * Liefert die ID des Schiffs
	 * @return the ivSchiffId
	 */
	public String getId() {
		return ivId;
	}

	/**
	 * Setzt die ID des Schiffs
	 * @param pvId the ivId to set
	 */
	public void setId(String pvId) {
		this.ivId = pvId;
	}

	/**
	 * Liefert die ID der Referenz
	 * @return the ivReferenzId
	 */
	public String getReferenzId() {
		return ivReferenzId;
	}

	/**
	 * Setzt die ID der Referenz
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
	 * Liefert die GUI
	 * @return the ivGUI
	 */
	public String getGUI() {
		return ivGUI;
	}

	/**
	 * Setzt die GUI
	 * @param pvGUI the ivGUI to set
	 */
	public void setGUI(String pvGUI) {
		this.ivGUI = pvGUI;
	}

	/**
	 * Liefert den Schiffsnamen
	 * @return the ivSchiffsname
	 */
	public String getSchiffsname() {
		return ivSchiffsname;
	}

	/**
	 * Setzt den Schiffsnamen
	 * @param pvSchiffsname the ivSchiffsname to set
	 */
	public void setSchiffsname(String pvSchiffsname) {
		this.ivSchiffsname = pvSchiffsname;
	}

	/**
	 * Liefert den Charternamen
	 * @return the ivChartername
	 */
	public String getChartername() {
		return ivChartername;
	}

	/**
	 * Setzt den Charternamen
	 * @param pvChartername the ivChartername to set
	 */
	public void setChartername(String pvChartername) {
		this.ivChartername = pvChartername;
	}

	/**
	 * Liefert die IMO-Nummer
	 * @return the ivIMONummer
	 */
	public String getIMONummer() {
		return ivIMONummer;
	}

	/**
	 * Setzt die IMO-Nummer
	 * @param pvIMONummer the ivIMONummer to set
	 */
	public void setIMONummer(String pvIMONummer) {
		this.ivIMONummer = pvIMONummer;
	}

	/**
	 * Liefert die ID des Schiffs
	 * @return the ivSchiffId
	 */
	public String getSchiffId() {
		return ivSchiffId;
	}

	/**
	 * Setzt die ID des Schiffs
	 * @param pvSchiffId the ivID to set
	 */
	public void setSchiffId(String pvSchiffId) {
		this.ivSchiffId = pvSchiffId;
	}

	/**
	 * Liefert die Schiffart
	 * @return the ivSchiffsart
	 */
	public String getSchiffsart() {
		return ivSchiffsart;
	}

	/**
	 * Setzt die Schiffart
	 * @param pvSchiffsart the ivSchiffsart to set
	 */
	public void setSchiffsart(String pvSchiffsart) {
		this.ivSchiffsart = pvSchiffsart;
	}

	/**
	 * Liefert die Registernummer
	 * @return the ivRegisternummer
	 */
	public String getRegisternummer() {
		return ivRegisternummer;
	}

	/**
	 * Setzt die Registernummer
	 * @param pvRegisternummer the ivRegisternummer to set
	 */
	public void setRegisternummer(String pvRegisternummer) {
		this.ivRegisternummer = pvRegisternummer;
	}

	/**
	 * Liefert das Registrierungsdatum
	 * @return the ivRegistrierungsdatum
	 */
	public String getRegistrierungsdatum() {
		return ivRegistrierungsdatum;
	}

	/**
	 * Setzt das Registrierungsdatum
	 * @param pvRegistrierungsdatum the ivRegistrierungsdatum to set
	 */
	public void setRegistrierungsdatum(String pvRegistrierungsdatum) {
		this.ivRegistrierungsdatum = pvRegistrierungsdatum;
	}

	/**
	 * Liefert den Registerort
	 * @return the ivRegisterort
	 */
	public String getRegisterort() {
		return ivRegisterort;
	}

	/**
	 * Setzt den Registerort
	 * @param pvRegisterort the ivRegisterort to set
	 */
	public void setRegisterort(String pvRegisterort) {
		this.ivRegisterort = pvRegisterort;
	}

	/**
	 * Liefert das Registerland
	 * @return the ivRegisterLand
	 */
	public String getRegisterLand() {
		return ivRegisterLand;
	}

	/**
	 * Setzt das Registerland
	 * @param pvRegisterLand the ivRegisterLand to set
	 */
	public void setRegisterLand(String pvRegisterLand) {
		this.ivRegisterLand = pvRegisterLand;
	}

	/**
	 * Liefert das Registerblatt
	 * @return the ivRegisterblatt
	 */
	public String getRegisterblatt() {
		return ivRegisterblatt;
	}

	/**
	 * Setzt das Registerblatt
	 * @param pvRegisterblatt the ivRegisterblatt to set
	 */
	public void setRegisterblatt(String pvRegisterblatt) {
		this.ivRegisterblatt = pvRegisterblatt;
	}

	/**
	 * Liefert die Waehrung des Beleihungswerts bei Indeckungnahme
	 * @return the ivWaehrungBeleihungswertIndeckungnahme
	 */
	public String getWaehrungBeleihungswertIndeckungnahme() {
		return ivWaehrungBeleihungswertIndeckungnahme;
	}

	/**
	 * Setzt die Waehrung des Beleihungswerts bei Indeckungsnahme
	 * @param pvWaehrungBeleihungswertIndeckungnahme the ivWaehrungBeleihungswertIndeckungnahme to set
	 */
	public void setWaehrungBeleihungswertIndeckungnahme(String pvWaehrungBeleihungswertIndeckungnahme) {
		this.ivWaehrungBeleihungswertIndeckungnahme = pvWaehrungBeleihungswertIndeckungnahme;
	}

	/**
	 * Liefert den Beleihungswert bei Indeckungnahme
	 * @return the ivBeleihungswertIndeckungnahme
	 */
	public String getBeleihungswertIndeckungnahme() {
		return ivBeleihungswertIndeckungnahme;
	}

	/**
	 * Setzt den Beleihungswert bei Indeckungnahme
	 * @param pvBeleihungswertIndeckungnahme the ivBeleihungswertIndeckungnahme to set
	 */
	public void setBeleihungswertIndeckungnahme(String pvBeleihungswertIndeckungnahme) {
		this.ivBeleihungswertIndeckungnahme = pvBeleihungswertIndeckungnahme;
	}

	/**
	 * Liefert das Datum des Beleihungswerts bei Indeckungnahme
	 * @return the ivDatumBeleihungswertIndeckungnahme
	 */
	public String getDatumBeleihungswertIndeckungnahme() {
		return ivDatumBeleihungswertIndeckungnahme;
	}

	/**
	 * Setzt das Datum des Beleihungswerts bei Indeckungnahme
	 * @param pvDatumBeleihungswertIndeckungnahme the ivDatumBeleihungswertIndeckungnahme to set
	 */
	public void setDatumBeleihungswertIndeckungnahme(
			String pvDatumBeleihungswertIndeckungnahme) {
		this.ivDatumBeleihungswertIndeckungnahme = pvDatumBeleihungswertIndeckungnahme;
	}

	/**
	 * Liefert das Kennzeichen Indeckung
	 * @return the ivInDeckung
	 */
	public String getInDeckung() {
		return ivInDeckung;
	}

	/**
	 * Setzt das Kennzeichen Indeckung
	 * @param pvInDeckung the ivInDeckung to set
	 */
	public void setInDeckung(String pvInDeckung) {
		this.ivInDeckung = pvInDeckung;
	}

	/**
	 * Liefert den Status der Deckung
	 * @return the ivStatusDeckung
	 */
	public String getStatusDeckung() {
		return ivStatusDeckung;
	}

	/**
	 * Setzt den Status der Deckung
	 * @param pvStatusDeckung the ivStatusDeckung to set
	 */
	public void setStatusDeckung(String pvStatusDeckung) {
		this.ivStatusDeckung = pvStatusDeckung;
	}

	/**
	 * Liefert das Kennzeichen Deckungsfaehig
	 * @return the ivDeckungsfaehig
	 */
	public String getDeckungsfaehig() {
		return ivDeckungsfaehig;
	}

	/**
	 * Setzt das Kennzeichen Deckungsfaehig
	 * @param pvDeckungsfaehig the ivDeckungsfaehig to set
	 */
	public void setDeckungsfaehig(String pvDeckungsfaehig) {
		this.ivDeckungsfaehig = pvDeckungsfaehig;
	}

	/**
	 * Liefert die Waehrung des aktuellen Beleihungswerts
	 * @return the ivWaehrungBeleihungswertAktuell
	 */
	public String getWaehrungBeleihungswertAktuell() {
		return ivWaehrungBeleihungswertAktuell;
	}

	/**
	 * Setzt die Waehrung des aktuellen Beleihungswerts
	 * @param pvWaehrungBeleihungswertAktuell the ivWaehrungBeleihungswertAktuell to set
	 */
	public void setWaehrungBeleihungswertAktuell(
			String pvWaehrungBeleihungswertAktuell) {
		this.ivWaehrungBeleihungswertAktuell = pvWaehrungBeleihungswertAktuell;
	}

	/**
	 * Liefert den aktuellen Beleihungswert
	 * @return the ivBeleihungswertAktuell
	 */
	public String getBeleihungswertAktuell() {
		return ivBeleihungswertAktuell;
	}

	/**
	 * Setzt den aktuellen Beleihungswert
	 * @param pvBeleihungswertAktuell the ivBeleihungswertAktuell to set
	 */
	public void setBeleihungswertAktuell(String pvBeleihungswertAktuell) {
		this.ivBeleihungswertAktuell = pvBeleihungswertAktuell;
	}

	/**
	 * Liefert das Datum des aktuellen Beliehungswerts
	 * @return the ivDatumBeleihungswertAktuell
	 */
	public String getDatumBeleihungswertAktuell() {
		return ivDatumBeleihungswertAktuell;
	}

	/**
	 * Setzt das Datum des aktuellen Beleihungswerts
	 * @param pvDatumBeleihungswertAktuell the ivDatumBeleihungswertAktuell to set
	 */
	public void setDatumBeleihungswertAktuell(String pvDatumBeleihungswertAktuell) {
		this.ivDatumBeleihungswertAktuell = pvDatumBeleihungswertAktuell;
	}

	/**
	 * Liefert den Zeitstempel des Deckungskennzeichen
	 * @return the ivZeitstempelDeckungskennzeichen
	 */
	public String getZeitstempelDeckungskennzeichen() {
		return ivZeitstempelDeckungskennzeichen;
	}

	/**
	 * Setzt den Zeitstempel des Deckungskennzeichen
	 * @param pvZeitstempelDeckungskennzeichen the ivZeitstempelDeckungskennzeichen to set
	 */
	public void setZeitstempelDeckungskennzeichen(
			String pvZeitstempelDeckungskennzeichen) {
		this.ivZeitstempelDeckungskennzeichen = pvZeitstempelDeckungskennzeichen;
	}

	/**
	 * Liefert das TXS-Deckungskennzeichen
	 * @return
	 */
	public String getDeckungskennzeichenTXS() 
	{
		return this.ivDeckungskennzeichenTXS;
	}

	/**
	 * Setzt das TXS-Deckungskennzeichen
	 * @param pvDeckungskennzeichenTXS
	 */
	public void setDeckungskennzeichenTXS(String pvDeckungskennzeichenTXS) 
	{
		this.ivDeckungskennzeichenTXS = pvDeckungskennzeichenTXS;
	}

	/**
	 * Liefert den Zeitstempel des TXS-Deckungskennzeichen
	 * @return
	 */
	public String getZeitstempelDeckungskennzeichenTXS()
	{
		return this.ivZeitstempelDeckungskennzeichenTXS;
	}

	/**
	 * Setzt den Zeitstempel des TXS-Deckungskennzeichen
	 * @param pvZeitstempelDeckungskennzeichenTXS
	 */
	public void setZeitstempelDeckungskennzeichenTXS(String pvZeitstempelDeckungskennzeichenTXS)
	{
		this.ivZeitstempelDeckungskennzeichenTXS = pvZeitstempelDeckungskennzeichenTXS;
	}
	
	/**
	 * Liefert das Kennzeichen, ob Deckungsregister relevant
	 * @return
	 */
	public String getDeckungsregisterRelevant()
	{
		return this.ivDeckungsregisterRelevant;
	}
	
	/**
	 * Setzt das Kennzeichen 'Deckungsregister relevant'
	 * @param pvDeckungsregisterRelevant
	 */
	public void setDeckungsregisterRelevant(String pvDeckungsregisterRelevant)
	{
		this.ivDeckungsregisterRelevant = pvDeckungsregisterRelevant;
	}
	
	/**
	 * Liefert die Schiffsnummer
	 * @return the ivSchiffsnummer
	 */
	public String getSchiffsnummer() {
		return ivSchiffsnummer;
	}

	/**
	 * Setzt die Schiffsnummer
	 * @param pvSchiffsnummer the ivSchiffsnummer to set
	 */
	public void setSchiffsnummer(String pvSchiffsnummer) {
		this.ivSchiffsnummer = pvSchiffsnummer;
	}

	/**
	 * Liefert den Schiffstypen
	 * @return the ivSchiffstypen
	 */
	public String getSchiffstypen() {
		return ivSchiffstypen;
	}

	/**
	 * Setzt den Schiffstypen
	 * @param pvSchiffstypen the ivSchiffstypen to set
	 */
	public void setSchiffstypen(String pvSchiffstypen) {
		this.ivSchiffstypen = pvSchiffstypen;
	}

	/**
	 * Liefert die Werft
	 * @return the ivWerft
	 */
	public String getWerft() {
		return ivWerft;
	}

	/**
	 * Setzt die Werft
	 * @param pvWerft the ivWerft to set
	 */
	public void setWerft(String pvWerft) {
		this.ivWerft = pvWerft;
	}

	/**
	 * Liefert die Baunummer der Werft
	 * @return the ivBaunummerWerft
	 */
	public String getBaunummerWerft() {
		return ivBaunummerWerft;
	}

	/**
	 * Setzt die Baunummer der Werft
	 * @param pvBaunummerWerft the ivBaunummerWerft to set
	 */
	public void setBaunummerWerft(String pvBaunummerWerft) {
		this.ivBaunummerWerft = pvBaunummerWerft;
	}

	/**
	 * Liefert das Datum der Ausflaggung
	 * @return the ivDatumAusflaggung
	 */
	public String getDatumAusflaggung() {
		return ivDatumAusflaggung;
	}

	/**
	 * Setzt das Datum der Ausflaggung
	 * @param pvDatumAusflaggung the ivDatumAusflaggung to set
	 */
	public void setDatumAusflaggung(String pvDatumAusflaggung) {
		this.ivDatumAusflaggung = pvDatumAusflaggung;
	}

	/**
	 * Liefert das Kennzeichen der Schiffsbauwerk
	 * @return the ivKennzeichenSchiffsbauwerk
	 */
	public String getKennzeichenSchiffsbauwerk() {
		return ivKennzeichenSchiffsbauwerk;
	}

	/**
	 * Setzt das Kennzeichen der Schiffsbauwerk
	 * @param pvKennzeichenSchiffsbauwerk the ivKennzeichenSchiffsbauwerk to set
	 */
	public void setKennzeichenSchiffsbauwerk(String pvKennzeichenSchiffsbauwerk) {
		this.ivKennzeichenSchiffsbauwerk = pvKennzeichenSchiffsbauwerk;
	}

	/**
	 * Liefert das Auslieferungsdatum (Soll)
	 * @return the ivAuslieferungsdatumSoll
	 */
	public String getAuslieferungsdatumSoll() {
		return ivAuslieferungsdatumSoll;
	}

	/**
	 * Setzt das Auslieferungsdatum (Soll)
	 * @param pvAuslieferungsdatumSoll the ivAuslieferungsdatumSoll to set
	 */
	public void setAuslieferungsdatumSoll(String pvAuslieferungsdatumSoll) {
		this.ivAuslieferungsdatumSoll = pvAuslieferungsdatumSoll;
	}

	/**
	 * Liefert das Ablieferungsdatum
	 * @return the ivAblieferungsdatum
	 */
	public String getAblieferungsdatum() {
		return ivAblieferungsdatum;
	}

	/**
	 * Setzt das Ablieferungsdatum
	 * @param pvAblieferungsdatum the ivAblieferungsdatum to set
	 */
	public void setAblieferungsdatum(String pvAblieferungsdatum) {
		this.ivAblieferungsdatum = pvAblieferungsdatum;
	}

    /**
     * Zerlegt eine Zeile in die einzelnen Felder
     * @param pvZeile die zu zerlegende Zeile
		 * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseSchiff(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setSchiffSPOT(lvLfd, lvTemp);
       
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
     this.setSchiffSPOT(lvLfd, lvTemp);
   }
    
    /**
     * Setzt einen Wert des Schiffes
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setSchiffSPOT(int pvPos, String pvWert)
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
          //case 7:
        	  // Festsetzungsdatum
        	  // Einfach ueberlesen
        	  //break;
          case 7:
    		this.setGUI(pvWert);
    		break;
          case 8:
    		this.setSchiffsname(pvWert);
    		break;
          case 9:
    		this.setChartername(pvWert);
    		break;
          case 10:
    		this.setIMONummer(pvWert);
    		break;
          case 11:
            Long helpId = new Long(pvWert); // Fuehrende Nullen entfernen
            this.setSchiffId(helpId.toString());
    		break;
          case 12:
    		this.setSchiffsart(pvWert);
    		break;
          case 13:
    		this.setRegisternummer(pvWert);
    		break;
          case 14:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
    		this.setRegistrierungsdatum(pvWert);
    		break;
          case 15:
    		this.setRegisterort(pvWert);
    		break;
          case 16:
    		this.setRegisterLand(pvWert);
    		break;
          case 17:
    		this.setRegisterblatt(pvWert);
    		break;
          case 18:
    		this.setWaehrungBeleihungswertIndeckungnahme(pvWert);
    		break;
          case 19:
    		this.setBeleihungswertIndeckungnahme(pvWert.trim());
    		break;
          case 20:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
						this.setDatumBeleihungswertIndeckungnahme(pvWert);
    		break;
          case 21:
    		this.setInDeckung(pvWert);
    		break;
          case 22:
    		this.setStatusDeckung(pvWert);
    		break;
          case 23:
    		this.setDeckungsfaehig(pvWert);
    		break;
          case 24:
    		this.setWaehrungBeleihungswertAktuell(pvWert);
    		break;
          case 25:
    		this.setBeleihungswertAktuell(pvWert.trim());
    		break;
          case 26:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
						this.setDatumBeleihungswertAktuell(pvWert);
    		break;
          case 27:
    		this.setZeitstempelDeckungskennzeichen(pvWert);
    		break;
          case 28:
        	this.setDeckungskennzeichenTXS(pvWert);
        	break;
          case 29:
        	this.setZeitstempelDeckungskennzeichenTXS(pvWert);
        	break;
          //case 30:
        	//this.setDeckungsregisterRelevant(pvWert);
          //  break;
          case 30:
    		this.setSchiffsnummer(pvWert);
    		break;
          case 31:
    		this.setSchiffstypen(pvWert);
    		break;
          case 32:
    		this.setWerft(pvWert);
    		break;
          case 33:
    		this.setBaunummerWerft(pvWert);
    		break;
          case 34:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
						this.setDatumAusflaggung(pvWert);
    		break;
          case 35:
    		this.setKennzeichenSchiffsbauwerk(pvWert);
    		break;
          case 36:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
						this.setAuslieferungsdatumSoll(pvWert);
    		break;
          case 37:
						if (pvWert.length() == 8)
						{
							pvWert = DatumUtilities.changeDatePoints(pvWert);
						}
						this.setAblieferungsdatum(pvWert);
    		break;
         default:
              ivLogger.info("Schiff: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }

	/**
	 * Setzt einen Wert des Schiffes
	 * @param pvPos Position
	 * @param pvWert Wert
	 */
	private void setSchiff(int pvPos, String pvWert)
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
				// Festsetzungsdatum
				// Ueberlesen
			  break;
			case 8:
				this.setGUI(pvWert);
				break;
			case 9:
				this.setSchiffsname(pvWert);
				break;
			case 10:
				this.setChartername(pvWert);
				break;
			case 11:
				this.setIMONummer(pvWert);
				break;
			case 12:
				Long helpId = new Long(pvWert); // Fuehrende Nullen entfernen
				this.setSchiffId(helpId.toString());
				break;
			case 13:
				this.setSchiffsart(pvWert);
				break;
			case 14:
				this.setRegisternummer(pvWert);
				break;
			case 15:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setRegistrierungsdatum(pvWert);
				break;
			case 16:
				this.setRegisterort(pvWert);
				break;
			case 17:
				this.setRegisterLand(pvWert);
				break;
			case 18:
				this.setRegisterblatt(pvWert);
				break;
			case 19:
				this.setWaehrungBeleihungswertIndeckungnahme(pvWert);
				break;
			case 20:
				this.setBeleihungswertIndeckungnahme(((pvWert.trim()).replace(".",  "")).replace(",","."));
				break;
			case 21:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setDatumBeleihungswertIndeckungnahme(pvWert);
				break;
			case 22:
				this.setInDeckung(pvWert);
				break;
			case 23:
				this.setStatusDeckung(pvWert);
				break;
			case 24:
				this.setDeckungsfaehig(pvWert);
				break;
			case 25:
				this.setWaehrungBeleihungswertAktuell(pvWert);
				break;
			case 26:
				this.setBeleihungswertAktuell(((pvWert.trim()).replace(".",  "")).replace(",","."));
				break;
			case 27:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setDatumBeleihungswertAktuell(pvWert);
				break;
			case 28:
				this.setZeitstempelDeckungskennzeichen(pvWert);
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
				this.setSchiffsnummer(pvWert);
				break;
			case 33:
				this.setSchiffstypen(pvWert);
				break;
			case 34:
				this.setWerft(pvWert);
				break;
			case 35:
				this.setBaunummerWerft(pvWert);
				break;
			case 36:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setDatumAusflaggung(pvWert);
				break;
			case 37:
				this.setKennzeichenSchiffsbauwerk(pvWert);
				break;
			case 38:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setAuslieferungsdatumSoll(pvWert);
				break;
			case 39:
				if (pvWert.length() == 8)
				{
					pvWert = DatumUtilities.changeDatePoints(pvWert);
				}
				this.setAblieferungsdatum(pvWert);
				break;
			default:
				System.out.println("Schiff: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
		}
	}

	/**
     * Schreibt den Inhalt des Schiffes in einen String
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
        lvOut.append("WaehrungVermoegenswert: " + this.ivWaehrungVermoegenswert + StringKonverter.lineSeparator);
        lvOut.append("GUI: " + this.ivGUI + StringKonverter.lineSeparator);
        lvOut.append("Schiffsname: " + this.ivSchiffsname + StringKonverter.lineSeparator);
        lvOut.append("Chartername: " + this.ivChartername + StringKonverter.lineSeparator);
        lvOut.append("IMONummer: " + this.ivIMONummer + StringKonverter.lineSeparator);
        lvOut.append("SchiffsId: " + this.ivSchiffId + StringKonverter.lineSeparator);
        lvOut.append("Schiffsart: " + this.ivSchiffsart + StringKonverter.lineSeparator);
        lvOut.append("Registernummer: " + this.ivRegisternummer + StringKonverter.lineSeparator);
        lvOut.append("Registerdatum: " + this.ivRegistrierungsdatum + StringKonverter.lineSeparator);
        lvOut.append("Registerort: " + this.ivRegisterort + StringKonverter.lineSeparator);
        lvOut.append("RegisterLand: " + this.ivRegisterLand + StringKonverter.lineSeparator);
        lvOut.append("Registerblatt: " + this.ivRegisterblatt + StringKonverter.lineSeparator);
        lvOut.append("WaehrungBeleihungswertIndeckungnahme: " + this.ivWaehrungBeleihungswertIndeckungnahme + StringKonverter.lineSeparator);
        lvOut.append("BeleihungswertIndeckungnahme: " + this.ivBeleihungswertIndeckungnahme + StringKonverter.lineSeparator);
        lvOut.append("DatumBeleihungswertIndeckungnahme: " + this.ivDatumBeleihungswertIndeckungnahme + StringKonverter.lineSeparator);
        lvOut.append("InDeckung: " + this.ivInDeckung + StringKonverter.lineSeparator);
        lvOut.append("StatusDeckung: " + this.ivStatusDeckung + StringKonverter.lineSeparator);
        lvOut.append("Deckungsfaehig: " + this.ivDeckungsfaehig + StringKonverter.lineSeparator);
        lvOut.append("WaehrungBeleihungswertAktuell: " + this.ivWaehrungBeleihungswertAktuell + StringKonverter.lineSeparator);
        lvOut.append("BeleihungswertAktuell: " + this.ivBeleihungswertAktuell + StringKonverter.lineSeparator);
        lvOut.append("DatumBeleihungswertAktuell: " + this.ivDatumBeleihungswertAktuell + StringKonverter.lineSeparator);
        lvOut.append("ZeitstempelDeckungskennzeichen: " + this.ivZeitstempelDeckungskennzeichen + StringKonverter.lineSeparator);
        lvOut.append("DeckungsregisterRelevant: " + this.ivDeckungsregisterRelevant + StringKonverter.lineSeparator);
        lvOut.append("DeckungskennzeichenTXS: " + this.ivDeckungskennzeichenTXS + StringKonverter.lineSeparator);
        lvOut.append("ZeitstempelDeckungskennzeichenTXS: " + this.ivZeitstempelDeckungskennzeichenTXS + StringKonverter.lineSeparator);
        lvOut.append("Schiffsnummer: " + this.ivSchiffsnummer + StringKonverter.lineSeparator);
        lvOut.append("Schiffstypen: " + this.ivSchiffstypen + StringKonverter.lineSeparator);
        lvOut.append("Werft: " + this.ivWerft + StringKonverter.lineSeparator);
        lvOut.append("BaunummerWerft: " + this.ivBaunummerWerft + StringKonverter.lineSeparator);
        lvOut.append("DatumAusflaggung: " + this.ivDatumAusflaggung + StringKonverter.lineSeparator);
        lvOut.append("KennzeichenSchiffsbauwerk: " + this.ivKennzeichenSchiffsbauwerk + StringKonverter.lineSeparator);
        lvOut.append("AuslieferungsdatumSoll: " + this.ivAuslieferungsdatumSoll + StringKonverter.lineSeparator);
        lvOut.append("Ablieferungsdatum: " + this.ivAblieferungsdatum + StringKonverter.lineSeparator);
        
        return lvOut.toString();
    }

}
