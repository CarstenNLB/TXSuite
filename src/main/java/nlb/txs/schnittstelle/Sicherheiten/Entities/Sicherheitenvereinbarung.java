/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class Sicherheitenvereinbarung 
{
    /**
     * GUID fuer Tabelle CMS_CAG (Sicherheitenvereinbarung)
     */
    private String ivId;
    
    /**
     * Sicherheitenvereinbarungs-ID
     */
    private String ivSicherheitenvereinbarungsId;
    
    /**
     * Sicherheitenvereinbarungsart
     */
    private String ivSicherheitenvereinbarungsart;
    
    /**
     * Nominalwert der Sicherheitenvereinbarung
     */
    private String ivNominalwert;
    
    /**
     * Waehrung fuer Nominalwert der Sicherheitenvereinbarung
     */
    private String ivNominalwertWaehrung;
    
    /**
     * Anzusetzender Wert
     */
    private String ivAnzusetzenderWert;
    
    /**
     * Waehrung des anzusetzenden Wertes
     */
    private String ivAnzusetzenderWertWaehrung;
    
    /**
     * Datum, an dem der anzusetzende Wert berechnet wurde
     */
    private String ivAnzusetzenderWertDatum;
    
    /**
     * Abschlussdatum der Sicherheitenvereinbarung
     */
    private String ivAbschlussdatum;
    
    /**
     * Gueltigkeitsbeginn des Sicherheitenvereinbarungsdokuments
     */
    private String ivGueltigkeitsbeginn;
    
    /**
     * Gueltigkeitsende des Sicherheitenvereinbarungsdokuments
     */
    private String ivGueltigkeitsende;
    
    /**
     * Qualitative Ausnahmen
     */
    private String ivQualitativeAusnahmen;
    
    /**
     * Hinderungsgrund fuer Anrechnung
     */
    private String ivHinderungsgrundAnrechnung;
    
    /**
     * Kennzeichen: Mindestanforderungen erfuellt fuer Sicherheitenvereinbarung (fuer Basel II)
     */
    private String ivMindestanforderungenKennzeichen;
    
    /**
     * Qualitatives Mindestkriterium
     */
    private String ivQualitativesMindestkriterium;
    
    /**
     * Hinderungsgrund (Freitext)
     */
    private String ivHinderungsgrund;
    
    /**
     * Verbuergungssatz in Prozent
     */
    private String ivVerbuergungssatz;
    
    /**
     * Anwendbares Recht
     */
    private String ivAnwendbaresRecht;
    
    /**
     * Kennzeichen: Sicherheitenvereinbarung ist abstrakt
     */
    private String ivAbstraktKennzeichen;
    
    /**
     * Kennzeichen: Gesamtgrundschuld oder Flottenhypothek
     */
    private String ivGesamtgrundschuldKennzeichen;
    
    /**
     * Kennzeichen: Grundschuldbrief vorhanden
     */
    private String ivGrundschuldbriefKennzeichen;
    
    /**
     * Verfuegungsbetrag
     */
    private String ivVerfuegungsbetrag;
    
    /**
     * Waehrung des Verfuegungsbetrags
     */
    private String ivVerfuegungsbetragWaehrung;
    
    /**
     * Kennzeichen: Exposure vermindernd
     */
    private String ivExposureKennzeichen;

    /**
     * Kennzeichen Ausfallbuergschaft
     */
    private String ivAusfallbuergschaftKennzeichen;

    /**
     * Ausfallbuergschaft in Prozent
     */
    private String ivAusfallbuergschaft;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Sicherheitenvereinbarung(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivId = new String();
        this.ivSicherheitenvereinbarungsId = new String();
        this.ivSicherheitenvereinbarungsart = new String();
        this.ivNominalwert = new String();
        this.ivNominalwertWaehrung = new String();
        this.ivAnzusetzenderWert = new String();
        this.ivAnzusetzenderWertWaehrung = new String();
        this.ivAnzusetzenderWertDatum = new String();
        this.ivAbschlussdatum = new String();
        this.ivGueltigkeitsbeginn = new String();
        this.ivGueltigkeitsende = new String();
        this.ivQualitativeAusnahmen = new String();
        this.ivHinderungsgrundAnrechnung = new String();
        this.ivMindestanforderungenKennzeichen = new String();
        this.ivQualitativesMindestkriterium = new String();
        this.ivHinderungsgrund = new String();
        this.ivVerbuergungssatz = new String();
        this.ivAnwendbaresRecht = new String();
        this.ivAbstraktKennzeichen = new String();
        this.ivGesamtgrundschuldKennzeichen = new String();
        this.ivGrundschuldbriefKennzeichen = new String();
        this.ivVerfuegungsbetrag = new String();
        this.ivVerfuegungsbetragWaehrung = new String();
        this.ivExposureKennzeichen = new String();
        this.ivAusfallbuergschaftKennzeichen = new String();
        this.ivAusfallbuergschaft = new String();
    }

    /**
     * Liefert die ID
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * Setzt die ID
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * Liefert die ID der Sicherheitenvereinbarung
     * @return the sicherheitenvereinbarungsId
     */
    public String getSicherheitenvereinbarungsId() {
        return this.ivSicherheitenvereinbarungsId;
    }

    /**
     * Setzt die ID der Sicherheitenvereinbarung
     * @param pvSicherheitenvereinbarungsId the sicherheitenvereinbarungsId to set
     */
    public void setSicherheitenvereinbarungsId(String pvSicherheitenvereinbarungsId) 
    {
        this.ivSicherheitenvereinbarungsId = pvSicherheitenvereinbarungsId;
    }

    /**
     * Liefert die Sicherheitenvereinbarungsart
     * @return the sicherheitenvereinbarungsart
     */
    public String getSicherheitenvereinbarungsart() {
        return this.ivSicherheitenvereinbarungsart;
    }

    /**
     * Setzt die Sicherheitenvereinbarungsart
     * @param pvSicherheitenvereinbarungsart the sicherheitenvereinbarungsart to set
     */
    public void setSicherheitenvereinbarungsart(String pvSicherheitenvereinbarungsart) {
        this.ivSicherheitenvereinbarungsart = pvSicherheitenvereinbarungsart;
    }

    /**
     * Liefert den Nominalwert
     * @return the nominalwert
     */
    public String getNominalwert() {
        return this.ivNominalwert;
    }

    /**
     * Setzt den Nominalwert
     * @param pvNominalwert the nominalwert to set
     */
    public void setNominalwert(String pvNominalwert) {
        this.ivNominalwert = pvNominalwert;
    }

    /**
     * Liefert die Waehrung des Nominalwerts
     * @return the nominalwertWaehrung
     */
    public String getNominalwertWaehrung() {
        return this.ivNominalwertWaehrung;
    }

    /**
     * Setzt die Waehrung des Nominalwerts
     * @param pvNominalwertWaehrung the nominalwertWaehrung to set
     */
    public void setNominalwertWaehrung(String pvNominalwertWaehrung) {
        this.ivNominalwertWaehrung = pvNominalwertWaehrung;
    }

    /**
     * Liefert den anzusetzenden Wert
     * @return the anzusetzenderWert
     */
    public String getAnzusetzenderWert() {
        return this.ivAnzusetzenderWert;
    }

    /**
     * Setzt den anzusetzenden Wert
     * @param pvAnzusetzenderWert the anzusetzenderWert to set
     */
    public void setAnzusetzenderWert(String pvAnzusetzenderWert) {
        this.ivAnzusetzenderWert = pvAnzusetzenderWert;
    }

    /**
     * Liefert die Waehrung des anzusetzenden Werts
     * @return the anzusetzenderWertWaehrung
     */
    public String getAnzusetzenderWertWaehrung() {
        return this.ivAnzusetzenderWertWaehrung;
    }

    /**
     * Setzt die Waehrung des anzusetzenden Werts
     * @param pvAnzusetzenderWertWaehrung the anzusetzenderWertWaehrung to set
     */
    public void setAnzusetzenderWertWaehrung(String pvAnzusetzenderWertWaehrung) {
        this.ivAnzusetzenderWertWaehrung = pvAnzusetzenderWertWaehrung;
    }

    /**
     * Liefert das Datum des anzusetzenden Werts
     * @return the anzusetzenderWertDatum
     */
    public String getAnzusetzenderWertDatum() {
        return this.ivAnzusetzenderWertDatum;
    }

    /**
     * Setzt das Datum des anzusetzenden Werts
     * @param pvAnzusetzenderWertDatum the anzusetzenderWertDatum to set
     */
    public void setAnzusetzenderWertDatum(String pvAnzusetzenderWertDatum) {
        this.ivAnzusetzenderWertDatum = pvAnzusetzenderWertDatum;
    }

    /**
     * Liefert das Abschlussdatum
     * @return the abschlussdatum
     */
    public String getAbschlussdatum() {
        return this.ivAbschlussdatum;
    }

    /**
     * Setzt das Abschlussdatum
     * @param pvAbschlussdatum the abschlussdatum to set
     */
    public void setAbschlussdatum(String pvAbschlussdatum) {
        this.ivAbschlussdatum = pvAbschlussdatum;
    }

    /**
     * Liefert den Gueltigkeitsbeginn
     * @return the gueltigkeitsbeginn
     */
    public String getGueltigkeitsbeginn() {
        return this.ivGueltigkeitsbeginn;
    }

    /**
     * Setzt den Gueltigkeitsbeginn
     * @param pvGueltigkeitsbeginn the gueltigkeitsbeginn to set
     */
    public void setGueltigkeitsbeginn(String pvGueltigkeitsbeginn) {
        this.ivGueltigkeitsbeginn = pvGueltigkeitsbeginn;
    }

    /**
     * Liefert das Gueltigkeitsende
     * @return the gueltigkeitsende
     */
    public String getGueltigkeitsende() {
        return this.ivGueltigkeitsende;
    }

    /**
     * Setzt das Gueltigkeitsende
     * @param pvGueltigkeitsende the gueltigkeitsende to set
     */
    public void setGueltigkeitsende(String pvGueltigkeitsende) {
        this.ivGueltigkeitsende = pvGueltigkeitsende;
    }

    /**
     * Liefert den Verbuergungssatz
     * @return
     */
    public String getVerbuergungssatz() 
    {
		return ivVerbuergungssatz;
	}

    /**
     * Setzt den Verbuergungssatz
     * @param pvVerbuergungssatz
     */
	  public void setVerbuergungssatz(String pvVerbuergungssatz)
	  {
	  	this.ivVerbuergungssatz = pvVerbuergungssatz;
	  }

	  /**
     * Liefert das anwendbares Recht
     * @return the anwendbaresRecht
     */
    public String getAnwendbaresRecht() {
        return this.ivAnwendbaresRecht;
    }

    /**
     * Setzt das anwendbares Recht
     * @param pvAnwendbaresRecht the anwendbaresRecht to set
     */
    public void setAnwendbaresRecht(String pvAnwendbaresRecht) {
        this.ivAnwendbaresRecht = pvAnwendbaresRecht;
    }

    /**
     * Liefert das abstrakt Kennzeichen
     * @return the abstraktKennzeichen
     */
    public String getAbstraktKennzeichen() {
        return this.ivAbstraktKennzeichen;
    }

    /**
     * Setzt das abstrakt Kennzeichen
     * @param pvAbstraktKennzeichen the abstraktKennzeichen to set
     */
    public void setAbstraktKennzeichen(String pvAbstraktKennzeichen) {
        this.ivAbstraktKennzeichen = pvAbstraktKennzeichen;
    }

    /**
     * Liefert das Kennzeichen Gesamtgrundschuld
     * @return the gesamtgrundschuldKennzeichen
     */
    public String getGesamtgrundschuldKennzeichen() {
        return this.ivGesamtgrundschuldKennzeichen;
    }

    /**
     * Setzt das Kennzeichen Gesamtgrundschuld
     * @param pvGesamtgrundschuldKennzeichen the gesamtgrundschuldKennzeichen to set
     */
    public void setGesamtgrundschuldKennzeichen(String pvGesamtgrundschuldKennzeichen) {
        this.ivGesamtgrundschuldKennzeichen = pvGesamtgrundschuldKennzeichen;
    }

    /**
     * Liefert das Kennzeichen Grundschuldbrief
     * @return the grundschuldbriefKennzeichen
     */
    public String getGrundschuldbriefKennzeichen() {
        return this.ivGrundschuldbriefKennzeichen;
    }

    /**
     * Setzt das Kennzeichen Grundschuldbrief
     * @param pvGrundschuldbriefKennzeichen the grundschuldbriefKennzeichen to set
     */
    public void setGrundschuldbriefKennzeichen(String pvGrundschuldbriefKennzeichen) {
        this.ivGrundschuldbriefKennzeichen = pvGrundschuldbriefKennzeichen;
    }

    /**
     * Liefert den Verfuegungsbetrag
     * @return the verfuegungsbetrag
     */
    public String getVerfuegungsbetrag() {
        return this.ivVerfuegungsbetrag;
    }

    /**
     * Setzt den Verfuegungsbetrag
     * @param pvVerfuegungsbetrag the verfuegungsbetrag to set
     */
    public void setVerfuegungsbetrag(String pvVerfuegungsbetrag) {
        this.ivVerfuegungsbetrag = pvVerfuegungsbetrag;
    }

    /**
     * Liefert die Waehrung des Verfuegungsbetrag
     * @return the verfuegungsbetragWaehrung
     */
    public String getVerfuegungsbetragWaehrung() {
        return this.ivVerfuegungsbetragWaehrung;
    }

    /**
     * Setzt die Waehrung des Verfuegungsbetrag
     * @param pvVerfuegungsbetragWaehrung the verfuegungsbetragWaehrung to set
     */
    public void setVerfuegungsbetragWaehrung(String pvVerfuegungsbetragWaehrung) {
        this.ivVerfuegungsbetragWaehrung = pvVerfuegungsbetragWaehrung;
    }

    /**
     * Liefert das Kennzeichen Exposure
     * @return the exposureKennzeichen
     */
    public String getExposureKennzeichen() {
        return this.ivExposureKennzeichen;
    }

    /**
     * Setzt das Kennzeichen Exposure
     * @param pvExposureKennzeichen the exposureKennzeichen to set
     */
    public void setExposureKennzeichen(String pvExposureKennzeichen) {
        this.ivExposureKennzeichen = pvExposureKennzeichen;
    }

    /**
     * Liefert die qualitativen Ausnahmen
     * @return the qualitativeAusnahmen
     */
    public String getQualitativeAusnahmen() {
        return this.ivQualitativeAusnahmen;
    }

    /**
     * Setzt die qualitativen Ausnahmen
     * @param pvQualitativeAusnahmen the qualitativeAusnahmen to set
     */
    public void setQualitativeAusnahmen(String pvQualitativeAusnahmen) {
        this.ivQualitativeAusnahmen = pvQualitativeAusnahmen;
    }

    /**
     * Liefert die Hinderungsgrund Anrechnung
     * @return the hinderungsgrundAnrechnung
     */
    public String getHinderungsgrundAnrechnung() {
        return this.ivHinderungsgrundAnrechnung;
    }

    /**
     * Setzt die Hinderungsgrund Anrechnung
     * @param pvHinderungsgrundAnrechnung the hinderungsgrundAnrechnung to set
     */
    public void setHinderungsgrundAnrechnung(String pvHinderungsgrundAnrechnung) {
        this.ivHinderungsgrundAnrechnung = pvHinderungsgrundAnrechnung;
    }

    /**
     * Liefert das Kennzeichen Mindestanforderungen
     * @return the mindestanforderungenKennzeichen
     */
    public String getMindestanforderungenKennzeichen() {
        return this.ivMindestanforderungenKennzeichen;
    }

    /**
     * Setzt das Kennzeichen Mindestanforderungen
     * @param pvMindestanforderungenKennzeichen the mindestanforderungenKennzeichen to set
     */
    public void setMindestanforderungenKennzeichen(String pvMindestanforderungenKennzeichen) {
        this.ivMindestanforderungenKennzeichen = pvMindestanforderungenKennzeichen;
    }

    /**
     * Liefert das qualitative Mindeskriterium
     * @return the qualitativesMindestkriterium
     */
    public String getQualitativesMindestkriterium() {
        return this.ivQualitativesMindestkriterium;
    }

    /**
     * Setzt das qualitative Mindeskriterium
     * @param pvQualitativesMindestkriterium the qualitativesMindestkriterium to set
     */
    public void setQualitativesMindestkriterium(String pvQualitativesMindestkriterium) {
        this.ivQualitativesMindestkriterium = pvQualitativesMindestkriterium;
    }

    /**
     * Liefert den Hinderungsgrund
     * @return the hinderungsgrund
     */
    public String getHinderungsgrund() {
        return this.ivHinderungsgrund;
    }

    /**
     * Setzt den Hinderungsgrund
     * @param pvHinderungsgrund the hinderungsgrund to set
     */
    public void setHinderungsgrund(String pvHinderungsgrund) {
        this.ivHinderungsgrund = pvHinderungsgrund;
    }

    /**
     * Liefert das Kennzeichen der Ausfallbuergschaft
     * @return
     */
    public String getAusfallbuergschaftKennzeichen()
    {
        return ivAusfallbuergschaftKennzeichen;
    }

    /**
     * Setzt das Kennzeichen der Ausfallbuergschaft
     * @param pvAusfallbuergschaftKennzeichen
     */
    public void setAusfallbuergschaftKennzeichen(String pvAusfallbuergschaftKennzeichen)
    {
        this.ivAusfallbuergschaftKennzeichen = pvAusfallbuergschaftKennzeichen;
    }

    /**
     * Liefert die Ausfallbuergschaft in Prozent
     * @return
     */
    public String getAusfallbuergschaft()
    {
        return ivAusfallbuergschaft;
    }

    /**
     * Setzt die Ausfallbuergschaft in Prozent
     * @param pvAusfallbuergschaft
     */
    public void setAusfallbuergschaft(String pvAusfallbuergschaft)
    {
        this.ivAusfallbuergschaft = pvAusfallbuergschaft;
    }

    /**
     * Zerlegt eine Zeile in die unterschiedlichen Daten/Felder
     * @param pvZeile Die zu zerlegende Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseSicherheitenvereinbarung(String pvZeile, int pvQuelle)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Tabulator erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setSicherheitenvereinbarung(lvLfd, lvTemp);
       
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
     this.setSicherheitenvereinbarung(lvLfd, lvTemp);
   }
    
    /**
     * Setzt einen Wert der Sicherheitenvereinbarung
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setSicherheitenvereinbarung(int pvPos, String pvWert)
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
              Long helpId = new Long(pvWert); // Fuehrende Nullen entfernen
              this.setSicherheitenvereinbarungsId(helpId.toString());
              break;
          case 3:
              this.setSicherheitenvereinbarungsart(pvWert);
              break;
          case 4:
              pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setNominalwert(pvWert.trim());
              break;
          case 5:
              this.setNominalwertWaehrung(pvWert);
              break;
          case 6:
              pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setAnzusetzenderWert(pvWert.trim());
              break;
          case 7:
              this.setAnzusetzenderWertWaehrung(pvWert);
              break;
          case 8:
              if (pvWert.length() == 8)
              {
                  pvWert = DatumUtilities.changeDatePoints(pvWert);
              }
              this.setAnzusetzenderWertDatum(pvWert);
              break;
          case 9:
              if (pvWert.length() == 8)
              {
                  pvWert = DatumUtilities.changeDatePoints(pvWert);
              }
              this.setAbschlussdatum(pvWert);
              break;
          case 10:
              this.setGueltigkeitsbeginn(pvWert);
              break;
          case 11:
              this.setGueltigkeitsende(pvWert);
              break;
          case 12:
              this.setQualitativeAusnahmen(pvWert);
              break;
          case 13:
              this.setHinderungsgrundAnrechnung(pvWert);
              break;
          case 14:
              this.setMindestanforderungenKennzeichen(pvWert);
              break;
          case 15:
              this.setQualitativesMindestkriterium(pvWert);
              break;
          case 16:
              this.setHinderungsgrund(pvWert);
              break;
          case 17:
              pvWert = pvWert.replace(",", ".");
              this.setVerbuergungssatz(pvWert.trim());
          	  break;
          case 18:
              this.setAnwendbaresRecht(pvWert);
              break;
          case 19:
              this.setAbstraktKennzeichen(pvWert);
              break;
          case 20:
              this.setGesamtgrundschuldKennzeichen(pvWert);
              break;
          case 21:
              this.setGrundschuldbriefKennzeichen(pvWert);
              break;
          case 22:
              pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setVerfuegungsbetrag(pvWert.trim());
              break;
          case 23:
              this.setVerfuegungsbetragWaehrung(pvWert);
              break;
          case 24:
              this.setExposureKennzeichen(pvWert);
              break;
              case 25:
                 this.setAusfallbuergschaftKennzeichen(pvWert);
                  break;
            case 26:
                pvWert = pvWert.replace(",", ".");
                this.setAusfallbuergschaft(pvWert.trim());
                break;
          default:
              ivLogger.info("Sicherheitenvereinbarung: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }

    /**
     * Setzt einen Wert der Sicherheitenvereinbarung
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setSicherheitenvereinbarungSPOT(int pvPos, String pvWert)
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
                Long helpId = new Long(pvWert); // Fuehrende Nullen entfernen
                this.setSicherheitenvereinbarungsId(helpId.toString());
                break;
            case 3:
                this.setSicherheitenvereinbarungsart(pvWert);
                break;
            case 4:
                ////pvWert = pvWert.replace(".", "");
                //pvWert = pvWert.replace(",", ".");
                this.setNominalwert(pvWert.trim());
                break;
            case 5:
                this.setNominalwertWaehrung(pvWert);
                break;
            case 6:
                ////pvWert = pvWert.replace(".", "");
                //pvWert = pvWert.replace(",", ".");
                this.setAnzusetzenderWert(pvWert.trim());
                break;
            case 7:
                this.setAnzusetzenderWertWaehrung(pvWert);
                break;
            case 8:
                if (pvWert.length() == 8)
                {
                    pvWert = DatumUtilities.changeDatePoints(pvWert);
                }
                this.setAnzusetzenderWertDatum(pvWert);
                break;
            case 9:
                if (pvWert.length() == 8)
                {
                    pvWert = DatumUtilities.changeDatePoints(pvWert);
                }
                this.setAbschlussdatum(pvWert);
                break;
            case 10:
                this.setGueltigkeitsbeginn(pvWert);
                break;
            case 11:
                this.setGueltigkeitsende(pvWert);
                break;
             case 12:
                this.setAnwendbaresRecht(pvWert);
                break;
            case 13:
                this.setAbstraktKennzeichen(pvWert);
                break;
            case 14:
                this.setGesamtgrundschuldKennzeichen(pvWert);
                break;
            case 15:
                this.setGrundschuldbriefKennzeichen(pvWert);
                break;
            case 16:
                ////pvWert = pvWert.replace(".", "");
                //pvWert = pvWert.replace(",", ".");
                //ivLogger.info(ivSicherheitenvereinbarungsId + " - Verfuegungsbetrag: " + pvWert);
                this.setVerfuegungsbetrag(pvWert.trim());
                break;
            case 17:
                this.setVerfuegungsbetragWaehrung(pvWert);
                break;
            case 18:
                this.setExposureKennzeichen(pvWert);
                break;
                case 19:
                this.setQualitativeAusnahmen(pvWert);
                break;
            case 20:
                this.setHinderungsgrundAnrechnung(pvWert);
                break;
            case 21:
                this.setMindestanforderungenKennzeichen(pvWert);
                break;
            case 22:
                this.setQualitativesMindestkriterium(pvWert);
                break;
            case 23:
                this.setHinderungsgrund(pvWert);
                break;
            case 24:
                this.setAusfallbuergschaftKennzeichen(pvWert);
                break;
            case 25:
                //pvWert = pvWert.replace(",", ".");
                this.setAusfallbuergschaft(pvWert.trim());
                break;
                case 26:
                //pvWert  = pvWert.replace(",", ".");
                this.setVerbuergungssatz(pvWert.trim());
                break;
            default:
                ivLogger.info("Sicherheitenvereinbarung: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }

    /**
     * Schreibt den Inhalt der Sicherheitenvereinbarung in einen String
     * @return Inhalt der Sicherheitenvereinbarung in einem String
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Id: ");
        lvOut.append(ivId);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("SicherheitenvereinbarungsId: ");
        lvOut.append(this.ivSicherheitenvereinbarungsId);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Sicherheitenvereinbarungsart: ");
        lvOut.append(this.ivSicherheitenvereinbarungsart);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Nominalwert: ");
        lvOut.append(this.ivNominalwert);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Nominalwertwaehrung: ");
        lvOut.append(this.ivNominalwertWaehrung);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Anzusetzender Wert: ");
        lvOut.append(this.ivAnzusetzenderWert);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Anzusetzender Wert Waehrung: ");
        lvOut.append(this.ivAnzusetzenderWertWaehrung);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Anzusetzender Wert Datum: ");
        lvOut.append(this.ivAnzusetzenderWertDatum);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Abschlussdatum: ");
        lvOut.append(this.ivAbschlussdatum);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Gueltigkeitsbeginn: ");
        lvOut.append(this.ivGueltigkeitsbeginn);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Gueltigkeitsende: ");
        lvOut.append(this.ivGueltigkeitsende);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Qualitative Ausnahmen: ");
        lvOut.append(this.ivQualitativeAusnahmen);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Hinderungsgrund Anrechnung: ");
        lvOut.append(this.ivHinderungsgrundAnrechnung);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Mindestanforderungen Kennzeichen: ");
        lvOut.append(this.ivMindestanforderungenKennzeichen);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Qualitatives Mindestkriterium: ");
        lvOut.append(this.ivQualitativesMindestkriterium);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Hinderungsgrund: ");
        lvOut.append(this.ivHinderungsgrund);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Verbuergungssatz: ");
        lvOut.append(this.ivVerbuergungssatz);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Anwendbares Recht: ");
        lvOut.append(this.ivAnwendbaresRecht);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Abstrakt Kennzeichen: ");
        lvOut.append(this.ivAbstraktKennzeichen);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Gesamtgrundschuld Kennzeichen: ");
        lvOut.append(this.ivGesamtgrundschuldKennzeichen);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Grundschuldbrief Kennzeichen: ");
        lvOut.append(this.ivGrundschuldbriefKennzeichen);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Verfuegungsbetrag: ");
        lvOut.append(this.ivVerfuegungsbetrag);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Verfuegungsbetragwaehrung: ");
        lvOut.append(this.ivVerfuegungsbetragWaehrung);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Exposure Kennzeichen: ");
        lvOut.append(this.ivExposureKennzeichen);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Ausfallbuergschaft Kennzeichen: ");
        lvOut.append(this.ivAusfallbuergschaftKennzeichen);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Ausfallbuergschaft: ");
        lvOut.append(this.ivAusfallbuergschaft);
        lvOut.append(StringKonverter.lineSeparator);
        return lvOut.toString();
    }
}
