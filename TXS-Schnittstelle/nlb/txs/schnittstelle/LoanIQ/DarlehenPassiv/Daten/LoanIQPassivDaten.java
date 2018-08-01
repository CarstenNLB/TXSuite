/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class LoanIQPassivDaten 
{
	/**
	 * log4j-Logger
	 */
	private Logger ivLogger;
	
    /**
     * Kontonummer
     */
    private String ivKontonummer;
    
    /**
     * Quellsystem
     */
    private String ivQuellsystem;
    
    /**
     * Gueltigkeitsdatum
     */
    private String ivGueltigkeitsdatum;
    
    /**
     * Originator/Darlehensgeber
     */
    private String ivOriginator;
    
    /**
     * Merkmal Aktiv/Passiv
     */
    private String ivMerkmalAktivPassiv;
    
    /**
     * Aktenzeichen (hier ISIN)
     */
    private String ivAktenzeichen;
    
    /**
     * Nennbetrag (Ursprungskapital/nominal)
     */
    private String ivNennbetrag;
    
    /**
     * Kredittyp
     */
    private String ivKredittyp;
    
    /**
     * Betragswaehrung
     */
    private String ivWaehrung;
    
    /**
     * Roll-Over Kennzeichen
     * N -> Nein (Grundstellung)
     * F -> Roll-Over-Geschaeft mit fester Dauer der Zinsbindung (floatendes Darlehen fest)
     * V -> Roll-Over-Geschaeft mit variabler Dauer der Zinsbindung (floatendes Darlehen variabel)
     */
    private String ivRolloverKennzeichen;
    
    /**
     * Kuendigungsdatum nach BGB §489
     */
    private String ivKuendigungsdatum;
    
    /**
     * Obergrenze/Cap
     */
    private String ivCap;
    
    /**
     * Emissionsdatum
     */
    private String ivEmissionsdatum;
    
    /**
     * Emissionskurs
     */
    private String ivEmissionskurs;
    
    /**
     * Kalenderkonvention (Tageszählweise) entsprechend SCHL_ID 9003
     */
    private String ivKalenderkonvention;
    
    /**
     * Kuponbasis gerade
     */
    private String ivKuponbasis;
    
    /**
     * Kuponbasis ungerade 
     */
    private String ivKuponbasisOdd;
    
    /**
     * Untergrenze/Floor
     */
    private String ivFloor;
    
    /**
     * Rate je Faelligkeit
     */
    private String ivLeistungsrate;
    
    /**
     * Nominalzinssatz
     */
    private String ivNominalzinssatz;
    
    /**
     * Name des Referenzindex bei variabler Verzinsung entsprechend SCHL_ID 1110
     */
    private String ivReferenzzins;
    
    /**
     * Zinszuschlag/Spread
     */
    private String ivSpread;
    
    /**
     * Datum Tilgungsbeginn
     */
    private String ivTilgungsbeginn;
    
    /**
     * Tilgungsrhythmus
     */
    private String ivTilgungsrhythmus;
    
    /**
     * Tilgungssatz
     */
    private String ivTilgungssatz;
    
    /**
     * Datum Beginn der Zinsperiode
     */
    private String ivZinsbeginn;
    
    /**
     * Zinstermin/Datum Zinsfaelligkeit
     */
    private String ivZinstermin;
    
    /**
     * Zinsendedatum
     */
    private String ivZinsendedatum;
    
    /**
     * Zinszahlungsrhythmus/Anzahl der Zinsperioden pro Jahr
     */
    private String ivZinszahlungsrhythmus;
    
    /**
     * Merkmal Zinssatz
     * FIXED -> fest
     * FLOAT -> variabel
     */
    private String ivMerkmalZinssatz;
    
    /**
     * Datum Zinsperiodenende 
     */
    private String ivZinsperiodenende;
    
    /**
     * Restkapital
     */
    private String ivRestkapital;
    
    /**
     * Datum der naechsten Zinsanpassung
     */
    private String ivNaechsteZinsanpassung;
    
    /**
     * Datum der letzten Zinsanpassung
     */
    private String ivLetzteZinsanpassung;
    
    /**
     * Datum der Konditionierung
     */
    private String ivDatumKonditionierung;
    
    /**
     * Kundennummer
     */
    private String ivKundennummer;
    
    /**
     * Ausplatzierungsmerkmal 
     */
    private String ivAusplatzierungsmerkmal;
    
    /**
     * Deckungsschluessel entsprechend SCHL_ID 7006
     */
    private String ivDeckungsschluessel;
    
    /**
     * Urkundennummer
     */
    private String ivUrkundennummer;
    
    /**
     * Laufzeitende-Datum
     */
    private String ivLaufzeitende;
    
    /**
     * Ausstatungsstatus
     */
    private String ivAuszahlungsstatus;
    
    /**
     * Gegenkontonummer
     */
    private String ivGegenkontonummer;

    /**
     * Facility Referenz
     */
    private String ivFacilityReferenz;
    
    /**
     * Facility Nominal
     */
    private String ivFacilityNominal;
    
    /**
     * Deal Referenz
     */
    private String ivDealReferenz;
    
    /**
     * Deal Nominal
     */
    private String ivDealNominal;
    
    /**
     * Initialisierung der Strings
     */
    public LoanIQPassivDaten(Logger pvLogger) 
    {
    	this.ivLogger = pvLogger;
        this.ivKontonummer = new String();
        this.ivQuellsystem = new String();
        this.ivGueltigkeitsdatum = new String();
        this.ivOriginator = new String();
        this.ivMerkmalAktivPassiv = new String();
        this.ivAktenzeichen = new String();
        this.ivNennbetrag = new String();
        this.ivKredittyp = new String();
        this.ivWaehrung = new String();
        this.ivRolloverKennzeichen = new String();
        this.ivKuendigungsdatum = new String();
        this.ivCap = new String();
        this.ivEmissionsdatum = new String();
        this.ivEmissionskurs = new String();
        this.ivKalenderkonvention = new String();
        this.ivKuponbasis = new String();
        this.ivKuponbasisOdd = new String();
        this.ivFloor = new String();
        this.ivLeistungsrate = new String();
        this.ivNominalzinssatz = new String();
        this.ivReferenzzins = new String();
        this.ivSpread = new String();
        this.ivTilgungsbeginn = new String();
        this.ivTilgungsrhythmus = new String();
        this.ivTilgungssatz = new String();
        this.ivZinsbeginn = new String();
        this.ivZinstermin = new String();
        this.ivZinsendedatum = new String();
        this.ivZinszahlungsrhythmus = new String();
        this.ivMerkmalZinssatz = new String();
        this.ivZinsperiodenende = new String();
        this.ivRestkapital = new String();
        this.ivNaechsteZinsanpassung = new String();
        this.ivLetzteZinsanpassung = new String();
        this.ivDatumKonditionierung = new String();
        this.ivKundennummer = new String();
        this.ivAusplatzierungsmerkmal = new String();
        this.ivDeckungsschluessel = new String();
        this.ivUrkundennummer = new String();
        this.ivLaufzeitende = new String();
        this.ivAuszahlungsstatus = new String();
        this.ivGegenkontonummer = new String();
        this.ivFacilityReferenz = new String();
        this.ivFacilityNominal = new String();
        this.ivDealReferenz = new String();
        this.ivDealNominal = new String();
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
     * @return the gueltigkeitsdatum
     */
    public String getGueltigkeitsdatum() {
        return this.ivGueltigkeitsdatum;
    }

    /**
     * @param pvGueltigkeitsdatum the gueltigkeitsdatum to set
     */
    public void setGueltigkeitsdatum(String pvGueltigkeitsdatum) {
        this.ivGueltigkeitsdatum = pvGueltigkeitsdatum;
    }

    /**
     * @return the originator
     */
    public String getOriginator() {
        return this.ivOriginator;
    }

    /**
     * @param pvOriginator the originator to set
     */
    public void setOriginator(String pvOriginator) {
        this.ivOriginator = pvOriginator;
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
     * @return the aktenzeichen
     */
    public String getAktenzeichen() {
        return this.ivAktenzeichen;
    }

    /**
     * @param pvAktenzeichen the aktenzeichen to set
     */
    public void setAktenzeichen(String pvAktenzeichen) {
        this.ivAktenzeichen = pvAktenzeichen;
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
     * @return the kredittyp
     */
    public String getKredittyp() {
        return this.ivKredittyp;
    }

    /**
     * @param pvKredittyp the kredittyp to set
     */
    public void setKredittyp(String pvKredittyp) {
        this.ivKredittyp = pvKredittyp;
    }

    /**
     * @return the waehrung
     */
    public String getWaehrung() {
        return this.ivWaehrung;
    }

    /**
     * @param pvWaehrung the waehrung to set
     */
    public void setWaehrung(String pvWaehrung) {
        this.ivWaehrung = pvWaehrung;
    }

    /**
     * @return the rolloverKennzeichen
     */
    public String getRolloverKennzeichen() {
        return this.ivRolloverKennzeichen;
    }

    /**
     * @param pvRolloverKennzeichen the rolloverKennzeichen to set
     */
    public void setRolloverKennzeichen(String pvRolloverKennzeichen) {
        this.ivRolloverKennzeichen = pvRolloverKennzeichen;
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
     * @return the emissionsdatum
     */
    public String getEmissionsdatum() {
        return this.ivEmissionsdatum;
    }

    /**
     * @param pvEmissionsdatum the emissionsdatum to set
     */
    public void setEmissionsdatum(String pvEmissionsdatum) {
        this.ivEmissionsdatum = pvEmissionsdatum;
    }

    /**
     * @return the emissionskurs
     */
    public String getEmissionskurs() {
        return this.ivEmissionskurs;
    }

    /**
     * @param pvEmissionskurs the emissionskurs to set
     */
    public void setEmissionskurs(String pvEmissionskurs) {
        this.ivEmissionskurs = pvEmissionskurs;
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
     * @return the kuponbasis
     */
    public String getKuponbasis() {
        return this.ivKuponbasis;
    }

    /**
     * @param pvKuponbasis the kuponbasis to set
     */
    public void setKuponbasis(String pvKuponbasis) {
        this.ivKuponbasis = pvKuponbasis;
    }

    /**
     * @return the kuponbasisOdd
     */
    public String getKuponbasisOdd() {
        return this.ivKuponbasisOdd;
    }

    /**
     * @param pvKuponbasisOdd the kuponbasisOdd to set
     */
    public void setKuponbasisOdd(String pvKuponbasisOdd) {
        this.ivKuponbasisOdd = pvKuponbasisOdd;
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
     * @return the leistungsrate
     */
    public String getLeistungsrate() {
        return this.ivLeistungsrate;
    }

    /**
     * @param pvLeistungsrate the leistungsrate to set
     */
    public void setLeistungsrate(String pvLeistungsrate) {
        this.ivLeistungsrate = pvLeistungsrate;
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
     * @return the referenzzins
     */
    public String getReferenzzins() {
        return this.ivReferenzzins;
    }

    /**
     * @param pvReferenzzins the referenzzins to set
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
     * @return the tilgungssatz
     */
    public String getTilgungssatz() {
        return this.ivTilgungssatz;
    }

    /**
     * @param pvTilgungssatz the tilgungssatz to set
     */
    public void setTilgungssatz(String pvTilgungssatz) {
        this.ivTilgungssatz = pvTilgungssatz;
    }

    /**
     * @return the zinsbeginn
     */
    public String getZinsbeginn() {
        return this.ivZinsbeginn;
    }

    /**
     * @param pvZinsbeginn the zinsbeginn to set
     */
    public void setZinsbeginn(String pvZinsbeginn) {
        this.ivZinsbeginn = pvZinsbeginn;
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
     * @return the zinsendedatum
     */
    public String getZinsendedatum() {
        return this.ivZinsendedatum;
    }

    /**
     * @param pvZinsendedatum the zinsendedatum to set
     */
    public void setZinsendedatum(String pvZinsendedatum) {
        this.ivZinsendedatum = pvZinsendedatum;
    }

    /**
     * @return the zinszahlungsrhythmus
     */
    public String getZinszahlungsrhythmus() {
        return this.ivZinszahlungsrhythmus;
    }

    /**
     * @param pvZinszahlungsrhythmus the zinszahlungsrhythmus to set
     */
    public void setZinszahlungsrhythmus(String pvZinszahlungsrhythmus) {
        this.ivZinszahlungsrhythmus = pvZinszahlungsrhythmus;
    }

    /**
     * @return the merkmalZinssatz
     */
    public String getMerkmalZinssatz() {
        return this.ivMerkmalZinssatz;
    }

    /**
     * @param pvMerkmalZinssatz the merkmalZinssatz to set
     */
    public void setMerkmalZinssatz(String pvMerkmalZinssatz) {
        this.ivMerkmalZinssatz = pvMerkmalZinssatz;
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
     * @return the urkundennummer
     */
    public String getUrkundennummer() {
        return this.ivUrkundennummer;
    }

    /**
     * @param pvUrkundennummer the urkundennummer to set
     */
    public void setUrkundennummer(String pvUrkundennummer) {
        this.ivUrkundennummer = pvUrkundennummer;
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
     * @return the ausstatungsstatus
     */
    public String getAuszahlungsstatus() {
        return this.ivAuszahlungsstatus;
    }

    /**
     * @param pvAuszahlungsstatus 
     */
    public void setAuszahlungsstatus(String pvAuszahlungsstatus) {
        this.ivAuszahlungsstatus = pvAuszahlungsstatus;
    }

    /**
     * @return the gegenkontonummer
     */
    public String getGegenkontonummer() {
        return this.ivGegenkontonummer;
    }

    /**
     * @param pvGegenkontonummer the gegenkontonummer to set
     */
    public void setGegenkontonummer(String pvGegenkontonummer) {
        this.ivGegenkontonummer = pvGegenkontonummer;
    }
    
    /**
     * 
     * @return
     */
    public String getFacilityReferenz() 
    {
		return ivFacilityReferenz;
	}

    /**
     * 
     * @param pvFacilityReferenz
     */
	public void setFacilityReferenz(String pvFacilityReferenz) 
	{
		this.ivFacilityReferenz = pvFacilityReferenz;
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
	public String getDealReferenz() 
	{
		return ivDealReferenz;
	}

	/**
	 * 
	 * @param pvDealReferenz
	 */
	public void setDealReferenz(String pvDealReferenz) 
	{
		this.ivDealReferenz = pvDealReferenz;
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
     * @param pvZeile 
     * @return 
     *       
     */
    public boolean parsePassivDaten(String pvZeile)
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
            this.setPassivDaten(lvLfd, lvTemp);
            
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
    public void setPassivDaten(int pvPos, String pvWert) 
    {
        //System.out.println("Pos: " + pvPos + " Wert:" + pvWert);
        switch (pvPos)
        {
          case 0:
              this.ivKontonummer = pvWert.trim();
              break;
          case 1:
              this.ivQuellsystem = pvWert.trim();
              break;
          case 2:
              this.ivGueltigkeitsdatum = pvWert;
              break;
          case 3:
              this.ivOriginator = pvWert;
              break;
          case 4:
              this.ivMerkmalAktivPassiv = pvWert;
              break;
          case 5:
              this.ivAktenzeichen = pvWert.trim();
              break;
          case 6:
              this.ivNennbetrag = pvWert.replace(",",".");
              break;
          case 7:
              this.ivKredittyp = pvWert;
              break;
          case 8:
              this.ivWaehrung = pvWert;
              break;
          case 9:
              this.ivRolloverKennzeichen = pvWert;
              break;
          case 10:
              this.ivKuendigungsdatum = pvWert;
              break;
          case 11:
              this.ivCap = pvWert.replace(",",".");
              break;
          case 12:
              this.ivEmissionsdatum = pvWert;
              break;
          case 13:
              this.ivEmissionskurs = pvWert.replace(",",".");
              break;
          case 14:
              //pvWert = pvWert.replace(",", ".");
              //if (pvWert.startsWith("-"))
              //{
              //    pvWert = pvWert.substring(1);
              //}
              this.ivKalenderkonvention = pvWert;
              break;
          case 15:
              this.ivKuponbasis = pvWert;
              break;
          case 16:
              this.ivKuponbasisOdd = pvWert;
              break;
          case 17:
              this.ivFloor = pvWert.replace(",",".");
              break;
          case 18:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
              this.ivLeistungsrate = pvWert;
              break;
          case 19:
              this.ivNominalzinssatz = pvWert.replace(",",".");
              break;
          case 20:
              this.ivReferenzzins = pvWert;
              break;
          case 21:
              this.ivSpread = pvWert.replace(",",".");
              break;
          case 22:
              this.ivTilgungsbeginn = pvWert;
              break;
          case 23:
              this.ivTilgungsrhythmus = pvWert;
              break;
          case 24:
              this.ivTilgungssatz = pvWert.replace(",",".");
              break;
          case 25:
              this.ivZinsbeginn = pvWert;
              break;
          case 26:
              this.ivZinstermin = pvWert;
              break;
          case 27:
              this.ivZinsendedatum = pvWert;
              break;
          case 28:
              this.ivZinszahlungsrhythmus = pvWert;
              break;
          case 29:
              this.ivMerkmalZinssatz = pvWert;
              break;
          case 30:
              this.ivZinsperiodenende = pvWert.replace(",",".");
              break;
          case 31:
              this.ivRestkapital = pvWert.replace(",",".");
              break;
          case 32:
              this.ivNaechsteZinsanpassung = pvWert;
              break;
          case 33:
              this.ivLetzteZinsanpassung = pvWert;
              break;
          case 34:
              this.ivDatumKonditionierung = pvWert;
              break;
          case 35:
              this.ivKundennummer = pvWert.trim();
              break;
          case 36:
              this.ivAusplatzierungsmerkmal = pvWert;
              break;
          case 37:
              this.ivDeckungsschluessel = pvWert;
              break;
          case 38:
              this.ivUrkundennummer = pvWert;
              break;
          case 39:
              this.ivLaufzeitende = pvWert;
              break;
          case 40:
              this.ivAuszahlungsstatus = pvWert;
              break;
          case 41:
              this.ivGegenkontonummer = pvWert;
              break;
          case 42:
        	  this.ivFacilityReferenz = pvWert;
        	  break;
          case 43:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
              this.ivFacilityNominal = pvWert;
              break;
          case 44:
        	  this.ivDealReferenz = pvWert;
        	  break;
          case 45:
              pvWert = pvWert.replace(",", ".");
              if (pvWert.startsWith("-"))
              {
                  pvWert = pvWert.substring(1);
              }
              this.ivDealNominal = pvWert;
              break;
         default:
              ivLogger.error("DarlehenLoanIQPassiv: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
        }
    }

}
