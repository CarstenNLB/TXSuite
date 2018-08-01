/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS.Entities;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

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
     * Verbuergungssatz
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
     * Konstruktor
     */
    public Sicherheitenvereinbarung() 
    {
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
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * @return the sicherheitenvereinbarungsId
     */
    public String getSicherheitenvereinbarungsId() {
        return this.ivSicherheitenvereinbarungsId;
    }

    /**
     * @param pvSicherheitenvereinbarungsId the sicherheitenvereinbarungsId to set
     */
    public void setSicherheitenvereinbarungsId(String pvSicherheitenvereinbarungsId) 
    {
        this.ivSicherheitenvereinbarungsId = pvSicherheitenvereinbarungsId;
    }

    /**
     * @return the sicherheitenvereinbarungsart
     */
    public String getSicherheitenvereinbarungsart() {
        return this.ivSicherheitenvereinbarungsart;
    }

    /**
     * @param pvSicherheitenvereinbarungsart the sicherheitenvereinbarungsart to set
     */
    public void setSicherheitenvereinbarungsart(String pvSicherheitenvereinbarungsart) {
        this.ivSicherheitenvereinbarungsart = pvSicherheitenvereinbarungsart;
    }

    /**
     * @return the nominalwert
     */
    public String getNominalwert() {
        return this.ivNominalwert;
    }

    /**
     * @param pvNominalwert the nominalwert to set
     */
    public void setNominalwert(String pvNominalwert) {
        this.ivNominalwert = pvNominalwert;
    }

    /**
     * @return the nominalwertWaehrung
     */
    public String getNominalwertWaehrung() {
        return this.ivNominalwertWaehrung;
    }

    /**
     * @param pvNominalwertWaehrung the nominalwertWaehrung to set
     */
    public void setNominalwertWaehrung(String pvNominalwertWaehrung) {
        this.ivNominalwertWaehrung = pvNominalwertWaehrung;
    }

    /**
     * @return the anzusetzenderWert
     */
    public String getAnzusetzenderWert() {
        return this.ivAnzusetzenderWert;
    }

    /**
     * @param pvAnzusetzenderWert the anzusetzenderWert to set
     */
    public void setAnzusetzenderWert(String pvAnzusetzenderWert) {
        this.ivAnzusetzenderWert = pvAnzusetzenderWert;
    }

    /**
     * @return the anzusetzenderWertWaehrung
     */
    public String getAnzusetzenderWertWaehrung() {
        return this.ivAnzusetzenderWertWaehrung;
    }

    /**
     * @param pvAnzusetzenderWertWaehrung the anzusetzenderWertWaehrung to set
     */
    public void setAnzusetzenderWertWaehrung(String pvAnzusetzenderWertWaehrung) {
        this.ivAnzusetzenderWertWaehrung = pvAnzusetzenderWertWaehrung;
    }

    /**
     * @return the anzusetzenderWertDatum
     */
    public String getAnzusetzenderWertDatum() {
        return this.ivAnzusetzenderWertDatum;
    }

    /**
     * @param pvAnzusetzenderWertDatum the anzusetzenderWertDatum to set
     */
    public void setAnzusetzenderWertDatum(String pvAnzusetzenderWertDatum) {
        this.ivAnzusetzenderWertDatum = pvAnzusetzenderWertDatum;
    }

    /**
     * @return the abschlussdatum
     */
    public String getAbschlussdatum() {
        return this.ivAbschlussdatum;
    }

    /**
     * @param pvAbschlussdatum the abschlussdatum to set
     */
    public void setAbschlussdatum(String pvAbschlussdatum) {
        this.ivAbschlussdatum = pvAbschlussdatum;
    }

    /**
     * @return the gueltigkeitsbeginn
     */
    public String getGueltigkeitsbeginn() {
        return this.ivGueltigkeitsbeginn;
    }

    /**
     * @param pvGueltigkeitsbeginn the gueltigkeitsbeginn to set
     */
    public void setGueltigkeitsbeginn(String pvGueltigkeitsbeginn) {
        this.ivGueltigkeitsbeginn = pvGueltigkeitsbeginn;
    }

    /**
     * @return the gueltigkeitsende
     */
    public String getGueltigkeitsende() {
        return this.ivGueltigkeitsende;
    }

    /**
     * @param pvGueltigkeitsende the gueltigkeitsende to set
     */
    public void setGueltigkeitsende(String pvGueltigkeitsende) {
        this.ivGueltigkeitsende = pvGueltigkeitsende;
    }

    /**
     * 
     * @return
     */
    public String getVerbuergungssatz() 
    {
		return ivVerbuergungssatz;
	}

    /**
     * 
     * @param pvVerbuergungssatz
     */
	public void setVerbuergungssatz(String pvVerbuergungssatz) 
	{
		this.ivVerbuergungssatz = pvVerbuergungssatz;
	}

	/**
     * @return the anwendbaresRecht
     */
    public String getAnwendbaresRecht() {
        return this.ivAnwendbaresRecht;
    }

    /**
     * @param pvAnwendbaresRecht the anwendbaresRecht to set
     */
    public void setAnwendbaresRecht(String pvAnwendbaresRecht) {
        this.ivAnwendbaresRecht = pvAnwendbaresRecht;
    }

    /**
     * @return the abstraktKennzeichen
     */
    public String getAbstraktKennzeichen() {
        return this.ivAbstraktKennzeichen;
    }

    /**
     * @param pvAbstraktKennzeichen the abstraktKennzeichen to set
     */
    public void setAbstraktKennzeichen(String pvAbstraktKennzeichen) {
        this.ivAbstraktKennzeichen = pvAbstraktKennzeichen;
    }

    /**
     * @return the gesamtgrundschuldKennzeichen
     */
    public String getGesamtgrundschuldKennzeichen() {
        return this.ivGesamtgrundschuldKennzeichen;
    }

    /**
     * @param pvGesamtgrundschuldKennzeichen the gesamtgrundschuldKennzeichen to set
     */
    public void setGesamtgrundschuldKennzeichen(String pvGesamtgrundschuldKennzeichen) {
        this.ivGesamtgrundschuldKennzeichen = pvGesamtgrundschuldKennzeichen;
    }

    /**
     * @return the grundschuldbriefKennzeichen
     */
    public String getGrundschuldbriefKennzeichen() {
        return this.ivGrundschuldbriefKennzeichen;
    }

    /**
     * @param pvGrundschuldbriefKennzeichen the grundschuldbriefKennzeichen to set
     */
    public void setGrundschuldbriefKennzeichen(String pvGrundschuldbriefKennzeichen) {
        this.ivGrundschuldbriefKennzeichen = pvGrundschuldbriefKennzeichen;
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
     * @return the verfuegungsbetragWaehrung
     */
    public String getVerfuegungsbetragWaehrung() {
        return this.ivVerfuegungsbetragWaehrung;
    }

    /**
     * @param pvVerfuegungsbetragWaehrung the verfuegungsbetragWaehrung to set
     */
    public void setVerfuegungsbetragWaehrung(String pvVerfuegungsbetragWaehrung) {
        this.ivVerfuegungsbetragWaehrung = pvVerfuegungsbetragWaehrung;
    }

    /**
     * @return the exposureKennzeichen
     */
    public String getExposureKennzeichen() {
        return this.ivExposureKennzeichen;
    }

    /**
     * @param pvExposureKennzeichen the exposureKennzeichen to set
     */
    public void setExposureKennzeichen(String pvExposureKennzeichen) {
        this.ivExposureKennzeichen = pvExposureKennzeichen;
    }

    /**
     * @return the qualitativeAusnahmen
     */
    public String getQualitativeAusnahmen() {
        return this.ivQualitativeAusnahmen;
    }

    /**
     * @param pvQualitativeAusnahmen the qualitativeAusnahmen to set
     */
    public void setQualitativeAusnahmen(String pvQualitativeAusnahmen) {
        this.ivQualitativeAusnahmen = pvQualitativeAusnahmen;
    }

    /**
     * @return the hinderungsgrundAnrechnung
     */
    public String getHinderungsgrundAnrechnung() {
        return this.ivHinderungsgrundAnrechnung;
    }

    /**
     * @param pvHinderungsgrundAnrechnung the hinderungsgrundAnrechnung to set
     */
    public void setHinderungsgrundAnrechnung(String pvHinderungsgrundAnrechnung) {
        this.ivHinderungsgrundAnrechnung = pvHinderungsgrundAnrechnung;
    }

    /**
     * @return the mindestanforderungenKennzeichen
     */
    public String getMindestanforderungenKennzeichen() {
        return this.ivMindestanforderungenKennzeichen;
    }

    /**
     * @param pvMindestanforderungenKennzeichen the mindestanforderungenKennzeichen to set
     */
    public void setMindestanforderungenKennzeichen(String pvMindestanforderungenKennzeichen) {
        this.ivMindestanforderungenKennzeichen = pvMindestanforderungenKennzeichen;
    }

    /**
     * @return the qualitativesMindestkriterium
     */
    public String getQualitativesMindestkriterium() {
        return this.ivQualitativesMindestkriterium;
    }

    /**
     * @param pvQualitativesMindestkriterium the qualitativesMindestkriterium to set
     */
    public void setQualitativesMindestkriterium(String pvQualitativesMindestkriterium) {
        this.ivQualitativesMindestkriterium = pvQualitativesMindestkriterium;
    }

    /**
     * @return the hinderungsgrund
     */
    public String getHinderungsgrund() {
        return this.ivHinderungsgrund;
    }

    /**
     * @param pvHinderungsgrund the hinderungsgrund to set
     */
    public void setHinderungsgrund(String pvHinderungsgrund) {
        this.ivHinderungsgrund = pvHinderungsgrund;
    }
  
    /**
     * @param pvZeile 
     * @return 
     * 
     */
    public boolean parseSicherheitenvereinbarung(String pvZeile)
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
     
     return true;
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
              this.setAnzusetzenderWertDatum(pvWert);
              break;
          case 9:
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
          	  this.setVerbuergungssatz(pvWert);
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
          default:
              System.out.println("Sicherheitenvereinbarung: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt der Sicherheitenvereinbarung in einen String
     * @return
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
        lvOut.append("SicherheitenvereinbarungsId: " + this.ivSicherheitenvereinbarungsId + StringKonverter.lineSeparator);
        lvOut.append("Sicherheitenvereinbarungsart: " + this.ivSicherheitenvereinbarungsart + StringKonverter.lineSeparator);
        lvOut.append("Nominalwert: " + this.ivNominalwert + StringKonverter.lineSeparator);
        lvOut.append("Nominalwertwaehrung: " + this.ivNominalwertWaehrung + StringKonverter.lineSeparator);
        lvOut.append("Anzusetzender Wert: " + this.ivAnzusetzenderWert + StringKonverter.lineSeparator);
        lvOut.append("Anzusetzender Wert Waehrung: " + this.ivAnzusetzenderWertWaehrung + StringKonverter.lineSeparator);
        lvOut.append("Anzusetzender Wert Datum: " + this.ivAnzusetzenderWertDatum + StringKonverter.lineSeparator);
        lvOut.append("Abschlussdatum: " + this.ivAbschlussdatum + StringKonverter.lineSeparator);
        lvOut.append("Gueltigkeitsbeginn: " + this.ivGueltigkeitsbeginn + StringKonverter.lineSeparator);
        lvOut.append("Gueltigkeitsende: " + this.ivGueltigkeitsende + StringKonverter.lineSeparator);
        lvOut.append("Qualitative Ausnahmen: " + this.ivQualitativeAusnahmen + StringKonverter.lineSeparator);
        lvOut.append("Hinderungsgrund Anrechnung: " + this.ivHinderungsgrundAnrechnung + StringKonverter.lineSeparator);
        lvOut.append("Mindestanforderungen Kennzeichen: " + this.ivMindestanforderungenKennzeichen + StringKonverter.lineSeparator);
        lvOut.append("Qualitatives Mindestkriterium: " + this.ivQualitativesMindestkriterium + StringKonverter.lineSeparator);
        lvOut.append("Hinderungsgrund: " + this.ivHinderungsgrund + StringKonverter.lineSeparator);
        lvOut.append("Verbuergungssatz: " + this.ivVerbuergungssatz + StringKonverter.lineSeparator);
        lvOut.append("Anwendbares Recht: " + this.ivAnwendbaresRecht + StringKonverter.lineSeparator);
        lvOut.append("Abstrakt Kennzeichen: " + this.ivAbstraktKennzeichen + StringKonverter.lineSeparator);
        lvOut.append("Gesamtgrundschuld Kennzeichen: " + this.ivGesamtgrundschuldKennzeichen + StringKonverter.lineSeparator);
        lvOut.append("Grundschuldbrief Kennzeichen: " + this.ivGrundschuldbriefKennzeichen + StringKonverter.lineSeparator);
        lvOut.append("Verfuegungsbetrag: " + this.ivVerfuegungsbetrag + StringKonverter.lineSeparator);
        lvOut.append("Verfuegungsbetragwaehrung: " + this.ivVerfuegungsbetragWaehrung + StringKonverter.lineSeparator);
        lvOut.append("Exposure Kennzeichen: " + this.ivExposureKennzeichen + StringKonverter.lineSeparator);

        return lvOut.toString();
    }
}
