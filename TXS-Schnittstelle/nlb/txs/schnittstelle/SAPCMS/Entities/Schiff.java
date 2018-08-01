package nlb.txs.schnittstelle.SAPCMS.Entities;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

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
	 * Zeitstempel für Deckungskennzeichen
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
	private String ivZeitstempelDeckunskennzeichenTXS;
	
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
	 * Konstruktor
	 */
	public Schiff() 
	{
		super();
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
		this.ivZeitstempelDeckunskennzeichenTXS = new String();
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
	 * @return the ivSchiffId
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
	 * @return the ivGUI
	 */
	public String getGUI() {
		return ivGUI;
	}

	/**
	 * @param ivGUI the ivGUI to set
	 */
	public void setGUI(String ivGUI) {
		this.ivGUI = ivGUI;
	}

	/**
	 * @return the ivSchiffsname
	 */
	public String getSchiffsname() {
		return ivSchiffsname;
	}

	/**
	 * @param ivSchiffsname the ivSchiffsname to set
	 */
	public void setSchiffsname(String ivSchiffsname) {
		this.ivSchiffsname = ivSchiffsname;
	}

	/**
	 * @return the ivChartername
	 */
	public String getChartername() {
		return ivChartername;
	}

	/**
	 * @param ivChartername the ivChartername to set
	 */
	public void setChartername(String ivChartername) {
		this.ivChartername = ivChartername;
	}

	/**
	 * @return the ivIMONummer
	 */
	public String getIMONummer() {
		return ivIMONummer;
	}

	/**
	 * @param ivIMONummer the ivIMONummer to set
	 */
	public void setIMONummer(String ivIMONummer) {
		this.ivIMONummer = ivIMONummer;
	}

	/**
	 * @return the ivSchiffId
	 */
	public String getSchiffId() {
		return ivSchiffId;
	}

	/**
	 * @param ivID the ivID to set
	 */
	public void setSchiffId(String ivSchiffId) {
		this.ivSchiffId = ivSchiffId;
	}

	/**
	 * @return the ivSchiffsart
	 */
	public String getSchiffsart() {
		return ivSchiffsart;
	}

	/**
	 * @param ivSchiffsart the ivSchiffsart to set
	 */
	public void setSchiffsart(String ivSchiffsart) {
		this.ivSchiffsart = ivSchiffsart;
	}

	/**
	 * @return the ivRegisternummer
	 */
	public String getRegisternummer() {
		return ivRegisternummer;
	}

	/**
	 * @param ivRegisternummer the ivRegisternummer to set
	 */
	public void setRegisternummer(String ivRegisternummer) {
		this.ivRegisternummer = ivRegisternummer;
	}

	/**
	 * @return the ivRegistrierungsdatum
	 */
	public String getRegistrierungsdatum() {
		return ivRegistrierungsdatum;
	}

	/**
	 * @param ivRegistrierungsdatum the ivRegistrierungsdatum to set
	 */
	public void setRegistrierungsdatum(String ivRegistrierungsdatum) {
		this.ivRegistrierungsdatum = ivRegistrierungsdatum;
	}

	/**
	 * @return the ivRegisterort
	 */
	public String getRegisterort() {
		return ivRegisterort;
	}

	/**
	 * @param ivRegisterort the ivRegisterort to set
	 */
	public void setRegisterort(String ivRegisterort) {
		this.ivRegisterort = ivRegisterort;
	}

	/**
	 * @return the ivRegisterLand
	 */
	public String getRegisterLand() {
		return ivRegisterLand;
	}

	/**
	 * @param ivRegisterLand the ivRegisterLand to set
	 */
	public void setRegisterLand(String ivRegisterLand) {
		this.ivRegisterLand = ivRegisterLand;
	}

	/**
	 * @return the ivRegisterblatt
	 */
	public String getRegisterblatt() {
		return ivRegisterblatt;
	}

	/**
	 * @param ivRegisterblatt the ivRegisterblatt to set
	 */
	public void setRegisterblatt(String ivRegisterblatt) {
		this.ivRegisterblatt = ivRegisterblatt;
	}

	/**
	 * @return the ivWaehrungBeleihungswertIndeckungnahme
	 */
	public String getWaehrungBeleihungswertIndeckungnahme() {
		return ivWaehrungBeleihungswertIndeckungnahme;
	}

	/**
	 * @param ivWaehrungBeleihungswertIndeckungnahme the ivWaehrungBeleihungswertIndeckungnahme to set
	 */
	public void setWaehrungBeleihungswertIndeckungnahme(
			String ivWaehrungBeleihungswertIndeckungnahme) {
		this.ivWaehrungBeleihungswertIndeckungnahme = ivWaehrungBeleihungswertIndeckungnahme;
	}

	/**
	 * @return the ivBeleihungswertIndeckungnahme
	 */
	public String getBeleihungswertIndeckungnahme() {
		return ivBeleihungswertIndeckungnahme;
	}

	/**
	 * @param ivBeleihungswertIndeckungnahme the ivBeleihungswertIndeckungnahme to set
	 */
	public void setBeleihungswertIndeckungnahme(
			String ivBeleihungswertIndeckungnahme) {
		this.ivBeleihungswertIndeckungnahme = ivBeleihungswertIndeckungnahme;
	}

	/**
	 * @return the ivDatumBeleihungswertIndeckungnahme
	 */
	public String getDatumBeleihungswertIndeckungnahme() {
		return ivDatumBeleihungswertIndeckungnahme;
	}

	/**
	 * @param ivDatumBeleihungswertIndeckungnahme the ivDatumBeleihungswertIndeckungnahme to set
	 */
	public void setDatumBeleihungswertIndeckungnahme(
			String ivDatumBeleihungswertIndeckungnahme) {
		this.ivDatumBeleihungswertIndeckungnahme = ivDatumBeleihungswertIndeckungnahme;
	}

	/**
	 * @return the ivInDeckung
	 */
	public String getInDeckung() {
		return ivInDeckung;
	}

	/**
	 * @param ivInDeckung the ivInDeckung to set
	 */
	public void setInDeckung(String ivInDeckung) {
		this.ivInDeckung = ivInDeckung;
	}

	/**
	 * @return the ivStatusDeckung
	 */
	public String getStatusDeckung() {
		return ivStatusDeckung;
	}

	/**
	 * @param ivStatusDeckung the ivStatusDeckung to set
	 */
	public void setStatusDeckung(String ivStatusDeckung) {
		this.ivStatusDeckung = ivStatusDeckung;
	}

	/**
	 * @return the ivDeckungsfaehig
	 */
	public String getDeckungsfaehig() {
		return ivDeckungsfaehig;
	}

	/**
	 * @param ivDeckungsfaehig the ivDeckungsfaehig to set
	 */
	public void setDeckungsfaehig(String ivDeckungsfaehig) {
		this.ivDeckungsfaehig = ivDeckungsfaehig;
	}

	/**
	 * @return the ivWaehrungBeleihungswertAktuell
	 */
	public String getWaehrungBeleihungswertAktuell() {
		return ivWaehrungBeleihungswertAktuell;
	}

	/**
	 * @param ivWaehrungBeleihungswertAktuell the ivWaehrungBeleihungswertAktuell to set
	 */
	public void setWaehrungBeleihungswertAktuell(
			String ivWaehrungBeleihungswertAktuell) {
		this.ivWaehrungBeleihungswertAktuell = ivWaehrungBeleihungswertAktuell;
	}

	/**
	 * @return the ivBeleihungswertAktuell
	 */
	public String getBeleihungswertAktuell() {
		return ivBeleihungswertAktuell;
	}

	/**
	 * @param ivBeleihungswertAktuell the ivBeleihungswertAktuell to set
	 */
	public void setBeleihungswertAktuell(String ivBeleihungswertAktuell) {
		this.ivBeleihungswertAktuell = ivBeleihungswertAktuell;
	}

	/**
	 * @return the ivDatumBeleihungswertAktuell
	 */
	public String getDatumBeleihungswertAktuell() {
		return ivDatumBeleihungswertAktuell;
	}

	/**
	 * @param ivDatumBeleihungswertAktuell the ivDatumBeleihungswertAktuell to set
	 */
	public void setDatumBeleihungswertAktuell(String ivDatumBeleihungswertAktuell) {
		this.ivDatumBeleihungswertAktuell = ivDatumBeleihungswertAktuell;
	}

	/**
	 * @return the ivZeitstempelDeckungskennzeichen
	 */
	public String getZeitstempelDeckungskennzeichen() {
		return ivZeitstempelDeckungskennzeichen;
	}

	/**
	 * @param ivZeitstempelDeckungskennzeichen the ivZeitstempelDeckungskennzeichen to set
	 */
	public void setZeitstempelDeckungskennzeichen(
			String ivZeitstempelDeckungskennzeichen) {
		this.ivZeitstempelDeckungskennzeichen = ivZeitstempelDeckungskennzeichen;
	}

	/**
	 * 
	 * @return
	 */
	public String getDeckungskennzeichenTXS() 
	{
		return this.ivDeckungskennzeichenTXS;
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
	public String getZeitstempelDeckunskennzeichenTXS() 
	{
		return this.ivZeitstempelDeckunskennzeichenTXS;
	}

	/**
	 * 
	 * @param ivZeitstempelDeckunskennzeichenTXS
	 */
	public void setZeitstempelDeckunskennzeichenTXS(String pvZeitstempelDeckunskennzeichenTXS) 
	{
		this.ivZeitstempelDeckunskennzeichenTXS = pvZeitstempelDeckunskennzeichenTXS;
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
	 * @param ivSchiffsnummer the ivSchiffsnummer to set
	 */
	public void setSchiffsnummer(String ivSchiffsnummer) {
		this.ivSchiffsnummer = ivSchiffsnummer;
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
	 * @param ivSchiffstypen the ivSchiffstypen to set
	 */
	public void setSchiffstypen(String ivSchiffstypen) {
		this.ivSchiffstypen = ivSchiffstypen;
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
	 * @param ivWerft the ivWerft to set
	 */
	public void setWerft(String ivWerft) {
		this.ivWerft = ivWerft;
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
	 * @param ivBaunummerWerft the ivBaunummerWerft to set
	 */
	public void setBaunummerWerft(String ivBaunummerWerft) {
		this.ivBaunummerWerft = ivBaunummerWerft;
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
	 * @param ivDatumAusflaggung the ivDatumAusflaggung to set
	 */
	public void setDatumAusflaggung(String ivDatumAusflaggung) {
		this.ivDatumAusflaggung = ivDatumAusflaggung;
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
	 * @param ivKennzeichenSchiffsbauwerk the ivKennzeichenSchiffsbauwerk to set
	 */
	public void setKennzeichenSchiffsbauwerk(String ivKennzeichenSchiffsbauwerk) {
		this.ivKennzeichenSchiffsbauwerk = ivKennzeichenSchiffsbauwerk;
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
	 * @param ivAuslieferungsdatumSoll the ivAuslieferungsdatumSoll to set
	 */
	public void setAuslieferungsdatumSoll(String ivAuslieferungsdatumSoll) {
		this.ivAuslieferungsdatumSoll = ivAuslieferungsdatumSoll;
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
	 * @param ivAblieferungsdatum the ivAblieferungsdatum to set
	 */
	public void setAblieferungsdatum(String ivAblieferungsdatum) {
		this.ivAblieferungsdatum = ivAblieferungsdatum;
	}

    /**
     * Zerlegt eine Zeile in die einzelnen Felder
     * @param pvZeile 
     * @return Immer 'true'
     */
    public boolean parseSchiff(String pvZeile)
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
         this.setSchiff(lvLfd, lvTemp);
       
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
     this.setSchiff(lvLfd, lvTemp);     
     
     return true;
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
    		this.setBeleihungswertIndeckungnahme(((pvWert.trim()).replace(".",  "")).replace(",","."));
    		break;
          case 20:
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
    		this.setBeleihungswertAktuell(((pvWert.trim()).replace(".",  "")).replace(",","."));
    		break;
          case 26:
    		this.setDatumBeleihungswertAktuell(pvWert);
    		break;
          case 27:
    		this.setZeitstempelDeckungskennzeichen(pvWert);
    		break;
          case 28:
        	this.setDeckungskennzeichenTXS(pvWert);
        	break;
          case 29:
        	this.setZeitstempelDeckunskennzeichenTXS(pvWert);
        	break;
          case 30:
        	this.setDeckungsregisterRelevant(pvWert);
            break;
          case 31:
    		this.setSchiffsnummer(pvWert);
    		break;
          case 32:
    		this.setSchiffstypen(pvWert);
    		break;
          case 33:
    		this.setWerft(pvWert);
    		break;
          case 34:
    		this.setBaunummerWerft(pvWert);
    		break;
          case 35:
    		this.setDatumAusflaggung(pvWert);
    		break;
          case 36:
    		this.setKennzeichenSchiffsbauwerk(pvWert);
    		break;
          case 37:
    		this.setAuslieferungsdatumSoll(pvWert);
    		break;
          case 38:
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
        lvOut.append("ZeitstempelDeckungskennzeichenTXS: " + this.ivZeitstempelDeckunskennzeichenTXS + StringKonverter.lineSeparator);
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
