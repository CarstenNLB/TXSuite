package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

public class Triebwerk 
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
	 * Beschreibung des Objekts
	 */
	private String ivBeschreibungObjekt;
	
	/**
	 * Klassifizierung
	 */
	private String ivKlassifizierung;
	
	/**
	 * Triebwerkstyp (Modell)
	 */
	private String ivTriebwerkstyp;
	
	/**
	 * Triebwerksvariante
	 */
	private String ivTriebwerksvariante;
	
	/**
	 * Land der Registrierung/Eintragung
	 */
	private String ivLandRegistrierung;
	
	/**
	 * Kommentarfeld fuer Standortbeschreibung
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
	 * Hersteller Triebwerk
	 */
	private String ivHerstellerTriebwerk;

	/**
	 * log4j-Logger
	 */
	private Logger ivLogger;

	/**
	 * Konstruktor
	 * @param pvLogger log4j-Logger
	 */
	public Triebwerk(Logger pvLogger)
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
		this.ivBeschreibungObjekt = new String();
		this.ivKlassifizierung = new String();
		this.ivTriebwerkstyp = new String();
		this.ivTriebwerksvariante = new String();
		this.ivLandRegistrierung = new String();
		this.ivKommentarfeld = new String();
		this.ivBetriebsstatus = new String();
		this.ivBetriebsstatusAlt = new String();
		this.ivTriebwerk1 = new String();
		this.ivTriebwerk2 = new String();
		this.ivTriebwerk3 = new String();
		this.ivTriebwerk4 = new String();
		this.ivHerstellerTriebwerk = new String();
	}

	/**
	 * Liefert die Triebwerk-ID
	 * @return the ivTriebwerkId
	 */
	public String getId() {
		return ivId;
	}

	/**
	 * Setzt die triebwerk-ID
	 * @param ivId the ivTriebwerkId to set
	 */
	public void setId(String ivId) {
		this.ivId = ivId;
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
	 * @param ivReferenzId the ivReferenzId to set
	 */
	public void setReferenzId(String ivReferenzId) {
		this.ivReferenzId = ivReferenzId;
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
	 * @param ivNominalwert the ivNominalwert to set
	 */
	public void setNominalwert(String ivNominalwert) {
		this.ivNominalwert = ivNominalwert;
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
	 * @param ivWaehrungNominalwert the ivWaehrungNominalwert to set
	 */
	public void setWaehrungNominalwert(String ivWaehrungNominalwert) {
		this.ivWaehrungNominalwert = ivWaehrungNominalwert;
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
	 * @param ivVermoegenswert the ivVermoegenswert to set
	 */
	public void setVermoegenswert(String ivVermoegenswert) {
		this.ivVermoegenswert = ivVermoegenswert;
	}

	/**
	 * Liefert die Waehrung des Vermoegenswerts
	 * @return the ivWaehrungVermoegenswert
	 */
	public String getWaehrungVermoegenswert() {
		return ivWaehrungVermoegenswert;
	}

	/**
	 * Setzt die Waehrung des Vermoegenswerts
	 * @param ivWaehrungVermoegenswert the ivWaehrungVermoegenswert to set
	 */
	public void setWaehrungVermoegenswert(String ivWaehrungVermoegenswert) {
		this.ivWaehrungVermoegenswert = ivWaehrungVermoegenswert;
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
	 * @param ivObjekttyp the ivObjekttyp to set
	 */
	public void setObjekttyp(String ivObjekttyp) {
		this.ivObjekttyp = ivObjekttyp;
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
	 * @param ivMobilienart the ivMobilienart to set
	 */
	public void setMobilienart(String ivMobilienart) {
		this.ivMobilienart = ivMobilienart;
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
	 * @param ivIdentifikationsnummer the ivIdentifikationsnummer to set
	 */
	public void setIdentifikationsnummer(String ivIdentifikationsnummer) {
		this.ivIdentifikationsnummer = ivIdentifikationsnummer;
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
	 * @param ivBeschreibungObjekt the ivBeschreibungObjekt to set
	 */
	public void setBeschreibungObjekt(String ivBeschreibungObjekt) {
		this.ivBeschreibungObjekt = ivBeschreibungObjekt;
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
	 * @param ivKlassifizierung the ivKlassifizierung to set
	 */
	public void setKlassifizierung(String ivKlassifizierung) {
		this.ivKlassifizierung = ivKlassifizierung;
	}

	/**
	 * Liefert den Triebwerkstyp
	 * @return the ivTriebwerkstyp
	 */
	public String getTriebwerkstyp() {
		return ivTriebwerkstyp;
	}

	/**
	 * Setzt den Triebwerkstyp
	 * @param ivTriebwerkstyp the ivTriebwerkstyp to set
	 */
	public void setTriebwerkstyp(String ivTriebwerkstyp) {
		this.ivTriebwerkstyp = ivTriebwerkstyp;
	}

	/**
	 * Liefert die Triebwerksvariante
	 * @return the ivTriebwerksvariante
	 */
	public String getTriebwerksvariante() {
		return ivTriebwerksvariante;
	}

	/**
	 * Setzt die Triebwerksvariante
	 * @param ivTriebwerksvariante the ivTriebwerksvariante to set
	 */
	public void setTriebwerksvariante(String ivTriebwerksvariante) {
		this.ivTriebwerksvariante = ivTriebwerksvariante;
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
	 * @param ivLandRegistrierung the ivLandRegistrierung to set
	 */
	public void setLandRegistrierung(String ivLandRegistrierung) {
		this.ivLandRegistrierung = ivLandRegistrierung;
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
	 * @param ivKommentarfeld the ivKommentarfeld to set
	 */
	public void setKommentarfeld(String ivKommentarfeld) {
		this.ivKommentarfeld = ivKommentarfeld;
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
	 * @param ivBetriebsstatus the ivBetriebsstatus to set
	 */
	public void setBetriebsstatus(String ivBetriebsstatus) {
		this.ivBetriebsstatus = ivBetriebsstatus;
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
	 * @param ivBetriebsstatusAlt the ivBetriebsstatusAlt to set
	 */
	public void setBetriebsstatusAlt(String ivBetriebsstatusAlt) {
		this.ivBetriebsstatusAlt = ivBetriebsstatusAlt;
	}

	/**
	 * Liefert das erste Triebwerk
	 * @return the ivTriebwerk1
	 */
	public String getTriebwerk1() {
		return ivTriebwerk1;
	}

	/**
	 * Setzt das erste Triebwerk
	 * @param ivTriebwerk1 the ivTriebwerk1 to set
	 */
	public void setTriebwerk1(String ivTriebwerk1) {
		this.ivTriebwerk1 = ivTriebwerk1;
	}

	/**
	 * Liefert das zweite Triebwerk
	 * @return the ivTriebwerk2
	 */
	public String getTriebwerk2() {
		return ivTriebwerk2;
	}

	/**
	 * Setzt das zweite Triebwerk
	 * @param ivTriebwerk2 the ivTriebwerk2 to set
	 */
	public void setTriebwerk2(String ivTriebwerk2) {
		this.ivTriebwerk2 = ivTriebwerk2;
	}

	/**
	 * Liefert das dritte Triebwerk
	 * @return the ivTriebwerk3
	 */
	public String getTriebwerk3() {
		return ivTriebwerk3;
	}

	/**
	 * Setzt das dritte Triebwerk
	 * @param ivTriebwerk3 the ivTriebwerk3 to set
	 */
	public void setTriebwerk3(String ivTriebwerk3) {
		this.ivTriebwerk3 = ivTriebwerk3;
	}

	/**
	 * Liefert das vierte Triebwerk
	 * @return the ivTriebwerk4
	 */
	public String getTriebwerk4() {
		return ivTriebwerk4;
	}

	/**
	 * Setzt das vierte Triebwerk
	 * @param ivTriebwerk4 the ivTriebwerk4 to set
	 */
	public void setTriebwerk4(String ivTriebwerk4) {
		this.ivTriebwerk4 = ivTriebwerk4;
	}

	/**
	 * Liefert den Hersteller der Triebwerke
	 * @return the ivHerstellerTriebwerk
	 */
	public String getHerstellerTriebwerk() {
		return ivHerstellerTriebwerk;
	}

	/**
	 * Setzt den Hersteller der Triebwerke
	 * @param ivHerstellerTriebwerk the ivHerstellerTriebwerk to set
	 */
	public void setHerstellerTriebwerk(String ivHerstellerTriebwerk) {
		this.ivHerstellerTriebwerk = ivHerstellerTriebwerk;
	}
	
	
    /**
     * Zerlegt die Zeile in die einzelnen Daten/Felder
     * @param pvZeile die zu zerlegende Zeile
		 * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseTriebwerk(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setTriebwerk(lvLfd, lvTemp);
       
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
     this.setTriebwerk(lvLfd, lvTemp);
   }
    
    /**
     * Setzt einen Wert des Triebwerks
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setTriebwerk(int pvPos, String pvWert)
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
    		this.setNominalwert(pvWert);
    		break;
          case 4:
    		this.setWaehrungNominalwert(pvWert);
    		break;
          case 5:
    		this.setVermoegenswert(pvWert);
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
    		this.setBeschreibungObjekt(pvWert);
    		break;
          case 12:
    		this.setKlassifizierung(pvWert);
    		break;
          case 13:
    		this.setTriebwerkstyp(pvWert);
    		break;
          case 14:
    		this.setTriebwerksvariante(pvWert);
    		break;
          case 15:
    		this.setLandRegistrierung(pvWert);
    		break;
          case 16:
    		this.setKommentarfeld(pvWert);
    		break;
          case 17:
    		this.setBetriebsstatus(pvWert);
    		break;
          case 18:
    		this.setBetriebsstatusAlt(pvWert);
    		break;
          case 19:
    		this.setTriebwerk1(pvWert);
    		break;
          case 20:
    		this.setTriebwerk2(pvWert);
    		break;
          case 21:
    		this.setTriebwerk3(pvWert);
    		break;
          case 22:
    		this.setTriebwerk4(pvWert);
    		break;
          case 23:
    		this.setHerstellerTriebwerk(pvWert);
    		break;
    		default:
           ivLogger.info("Triebwerk: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
    }
    }
    
    /**
     * Schreibt den Inhalt des Triebwerks in einen String
     * @return
     */
    public String toString()
    {
        String lvOut = new String();

        lvOut = "Id: " + ivId + StringKonverter.lineSeparator;
        lvOut = lvOut + "ReferenzId: " + ivReferenzId + StringKonverter.lineSeparator;
        lvOut = lvOut + "Nominalwert: " + this.ivNominalwert + StringKonverter.lineSeparator;
        lvOut = lvOut + "WaehrungNominalwert: " + this.ivWaehrungNominalwert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Vermoegenswert: " + this.ivVermoegenswert + StringKonverter.lineSeparator;
        lvOut = lvOut + "WaehrungVermoegenswert: " + this.ivWaehrungVermoegenswert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Objekttyp: " + this.ivObjekttyp + StringKonverter.lineSeparator;
        lvOut = lvOut + "Mobilienart: " + this.ivMobilienart + StringKonverter.lineSeparator;
        lvOut = lvOut + "Identifikationsnummer: " + this.ivIdentifikationsnummer + StringKonverter.lineSeparator;
        lvOut = lvOut + "BeschreibungObjekt: " + this.ivBeschreibungObjekt + StringKonverter.lineSeparator;
        lvOut = lvOut + "Klassifizierung: " + this.ivKlassifizierung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Triebwerkstyp:" + this.ivTriebwerkstyp + StringKonverter.lineSeparator;
        lvOut = lvOut + "Triebwerksvariante: " + this.ivTriebwerksvariante + StringKonverter.lineSeparator;
        lvOut = lvOut + "LandRegistrierung: " + this.ivLandRegistrierung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kommentarfeld: " + this.ivKommentarfeld + StringKonverter.lineSeparator;
        lvOut = lvOut + "Betriebstatus: " + this.ivBetriebsstatus + StringKonverter.lineSeparator;
        lvOut = lvOut + "BetriebsstatusAlt: " + this.ivBetriebsstatusAlt + StringKonverter.lineSeparator;
        lvOut = lvOut + "Triebwerk1: " + this.ivTriebwerk1 + StringKonverter.lineSeparator;
        lvOut = lvOut + "Triebwerk2: " + this.ivTriebwerk2 + StringKonverter.lineSeparator;
        lvOut = lvOut + "Triebwerk3: " + this.ivTriebwerk3 + StringKonverter.lineSeparator;
        lvOut = lvOut + "Triebwerk4: " + this.ivTriebwerk4 + StringKonverter.lineSeparator;
        lvOut = lvOut + "HerstellerTriebwerk: " + this.ivHerstellerTriebwerk + StringKonverter.lineSeparator;

        return lvOut;
    }

}
