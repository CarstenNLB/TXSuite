/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS.Entities;

import nlb.txs.schnittstelle.SAPCMS.SAPCMS_Neu;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class Sicherungsumfang 
{
    /**
     * GUID fuer Tabelle CMS_CAG_RBL (Sicherungsumfang)
     */
    private String ivId;
    
    /**
     * GUID fuer Tabelle CMS_CAG (Sicherheitenvereinbarung)
     */
    private String ivSicherheitenvereinbarungId;
    
    /**
     * GUID fuer Tabelle CMS_CAG_POR (???)
     */
    private String ivCAG_POR;
    
    /**
     * Kennzeichen des Sicherheitenvereinbarungsteils
     */
    private String ivKennzeichen;
    
    /**
     * Wert des Sicherheitenvereinbarungsteil
     */
    private String ivWert;
    
    /**
     * Gibt die Groesse des Sicherheitenvereinbarungsteils in SV an
     */
    private String ivGroesse;
    
    /**
     * Sperrkategorie fuer Sicherheitenvereinbarungsteils
     */
    private String ivSperrkategorie;
    
    /**
     * Einschraenkung fuer Teil
     */
    private String ivEinschraenkung; 
    
    /**
     * SV-ID basierend auf einer anderen (Referenz-)SV
     */
    private String ivReferenzId;
    
    /**
     * Legt Rangverhaeltnis des SV-Teils fest
     */
    private String ivRang;
    
    /**
     * Kennzeichen fuer Geschaeftswertart
     */
    private String ivGeschaeftswertartKennzeichen;
    
    /**
     * Laufende Nummer der Akte an einem Lagerort
     */
    private String ivLaufendeNummerAkte;
    
    /**
     * Kreditsystem
     */
    private String ivKreditsystem;
    
    /**
     * GeschaeftswertID
     */
    private String ivGeschaeftswertId;
    
    /**
     * Identifikationsnummer des Geschaeftspartners
     */
    private String ivGeschaeftspartnerId;
    
    /**
     * Geschaeftspartnerfunktion
     */
    private String ivGeschaeftsparterfunktion;

    /**
     * Gueltigkeitsbeginn der Verknuepfung SV - GW 
     */
    private String ivGueltigkeitsbeginn;
    
    /**
     * Gueltigkeitsende der Verknuepfung SV - GW
     */
    private String ivGueltigkeitsende;
    
    /**
     * Prioritaet der Verknuepfung SV - GW
     */
    private String ivPrioritaet;
    
    /**
     * Rangklasse der Verknuepfung SV - GW
     */
    private String ivRangklasse;
    
    /**
     * Kennzeichen fuer manuelle/automatische Links
     */
    private String ivLinksKennzeichen;
    
    /**
     * Gibt an, ob SV eine Uebergangssicherheit ist
     */
    private String ivUebergangssicherheit;
    
    /**
     * Zuweisungsbetrag
     */
    private String ivZuweisungsbetrag;
    
    /**
     * Zuweisungsbetragwaehrung
     */
    private String ivZuweisungsbetragWaehrung;

    /**
     * Relevant fuer Deckungsregister
     */
    private String ivDeckungsregisterRelevant;
    
    /**
     * Konstruktor
     */
    public Sicherungsumfang() 
    {      
        this.ivId = new String();
        this.ivSicherheitenvereinbarungId = new String();
        this.ivCAG_POR = new String();
        this.ivKennzeichen = new String();
        this.ivWert = new String();
        this.ivGroesse = new String();
        this.ivSperrkategorie = new String();
        this.ivEinschraenkung = new String();
        this.ivReferenzId = new String();
        this.ivRang = new String();
        this.ivGeschaeftswertartKennzeichen = new String();
        this.ivLaufendeNummerAkte = new String();
        this.ivKreditsystem = new String();
        this.ivGeschaeftswertId = new String();
        this.ivGeschaeftspartnerId = new String();
        this.ivGeschaeftsparterfunktion = new String();
        this.ivGueltigkeitsbeginn = new String();
        this.ivGueltigkeitsende = new String();
        this.ivPrioritaet = new String();
        this.ivRangklasse = new String();
        this.ivLinksKennzeichen = new String();
        this.ivUebergangssicherheit = new String();
        this.ivZuweisungsbetrag = new String();
        this.ivZuweisungsbetragWaehrung = new String();
        this.ivDeckungsregisterRelevant = new String();
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
     * @return the sicherheitenvereinbarungId
     */
    public String getSicherheitenvereinbarungId() {
        return this.ivSicherheitenvereinbarungId;
    }

    /**
     * @param pvSicherheitenvereinbarungId the sicherheitenvereinbarungId to set
     */
    public void setSicherheitenvereinbarungId(String pvSicherheitenvereinbarungId) {
        this.ivSicherheitenvereinbarungId = pvSicherheitenvereinbarungId;
    }

    /**
     * @return the cAG_POR
     */
    public String getCAG_POR() {
        return this.ivCAG_POR;
    }

    /**
     * @param pvCAGPOR the cAG_POR to set
     */
    public void setCAG_POR(String pvCAGPOR) {
        this.ivCAG_POR = pvCAGPOR;
    }

    /**
     * @return the kennzeichen
     */
    public String getKennzeichen() {
        return this.ivKennzeichen;
    }

    /**
     * @param pvKennzeichen the kennzeichen to set
     */
    public void setKennzeichen(String pvKennzeichen) {
        this.ivKennzeichen = pvKennzeichen;
    }

    /**
     * @return the wert
     */
    public String getWert() {
        return this.ivWert;
    }

    /**
     * @param pvWert the wert to set
     */
    public void setWert(String pvWert) {
        this.ivWert = pvWert;
    }

    /**
     * @return the groesse
     */
    public String getGroesse() {
        return this.ivGroesse;
    }

    /**
     * @param pvGroesse the groesse to set
     */
    public void setGroesse(String pvGroesse) {
        this.ivGroesse = pvGroesse;
    }

    /**
     * @return the sperrkategorie
     */
    public String getSperrkategorie() {
        return this.ivSperrkategorie;
    }

    /**
     * @param pvSperrkategorie the sperrkategorie to set
     */
    public void setSperrkategorie(String pvSperrkategorie) {
        this.ivSperrkategorie = pvSperrkategorie;
    }

    /**
     * @return the einschraenkung
     */
    public String getEinschraenkung() {
        return this.ivEinschraenkung;
    }

    /**
     * @param pvEinschraenkung the einschraenkung to set
     */
    public void setEinschraenkung(String pvEinschraenkung) {
        this.ivEinschraenkung = pvEinschraenkung;
    }

    /**
     * @return the referenzId
     */
    public String getReferenzId() {
        return this.ivReferenzId;
    }

    /**
     * @param pvReferenzId the referenzId to set
     */
    public void setReferenzId(String pvReferenzId) {
        this.ivReferenzId = pvReferenzId;
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
     * @return the geschaeftswertartKennzeichen
     */
    public String getGeschaeftswertartKennzeichen() {
        return this.ivGeschaeftswertartKennzeichen;
    }

    /**
     * @param pvGeschaeftswertartKennzeichen the geschaeftswertartKennzeichen to set
     */
    public void setGeschaeftswertartKennzeichen(String pvGeschaeftswertartKennzeichen) {
        this.ivGeschaeftswertartKennzeichen = pvGeschaeftswertartKennzeichen;
    }

    /**
     * @return the laufendeNummerAkte
     */
    public String getLaufendeNummerAkte() {
        return this.ivLaufendeNummerAkte;
    }

    /**
     * @param pvLaufendeNummerAkte the laufendeNummerAkte to set
     */
    public void setLaufendeNummerAkte(String pvLaufendeNummerAkte) {
        this.ivLaufendeNummerAkte = pvLaufendeNummerAkte;
    }

    /**
     * @return the kreditsystem
     */
    public String getKreditsystem() {
        return this.ivKreditsystem;
    }

    /**
     * @param pvKreditsystem the kreditsystem to set
     */
    public void setKreditsystem(String pvKreditsystem) {
        this.ivKreditsystem = pvKreditsystem;
    }

    /**
     * @return the geschaeftswertId
     */
    public String getGeschaeftswertId() {
        return this.ivGeschaeftswertId;
    }

    /**
     * @param pvGeschaeftswertId the geschaeftswertId to set
     */
    public void setGeschaeftswertId(String pvGeschaeftswertId) {
        this.ivGeschaeftswertId = pvGeschaeftswertId;
    }

    /**
     * @return the geschaeftspartnerId
     */
    public String getGeschaeftspartnerId() {
        return this.ivGeschaeftspartnerId;
    }

    /**
     * @param pvGeschaeftspartnerId the geschaeftspartnerId to set
     */
    public void setGeschaeftspartnerId(String pvGeschaeftspartnerId) {
        this.ivGeschaeftspartnerId = pvGeschaeftspartnerId;
    }

    /**
     * @return the geschaeftsparterfunktion
     */
    public String getGeschaeftsparterfunktion() {
        return this.ivGeschaeftsparterfunktion;
    }

    /**
     * @param pvGeschaeftsparterfunktion the geschaeftsparterfunktion to set
     */
    public void setGeschaeftsparterfunktion(String pvGeschaeftsparterfunktion) {
        this.ivGeschaeftsparterfunktion = pvGeschaeftsparterfunktion;
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
     * @return the prioritaet
     */
    public String getPrioritaet() {
        return this.ivPrioritaet;
    }

    /**
     * @param pvPrioritaet the prioritaet to set
     */
    public void setPrioritaet(String pvPrioritaet) {
        this.ivPrioritaet = pvPrioritaet;
    }

    /**
     * @return the rangklasse
     */
    public String getRangklasse() {
        return this.ivRangklasse;
    }

    /**
     * @param pvRangklasse the rangklasse to set
     */
    public void setRangklasse(String pvRangklasse) {
        this.ivRangklasse = pvRangklasse;
    }

    /**
     * @return the linksKennzeichen
     */
    public String getLinksKennzeichen() {
        return this.ivLinksKennzeichen;
    }

    /**
     * @param pvLinksKennzeichen the linksKennzeichen to set
     */
    public void setLinksKennzeichen(String pvLinksKennzeichen) {
        this.ivLinksKennzeichen = pvLinksKennzeichen;
    }

    /**
     * @return the uebergangssicherheit
     */
    public String getUebergangssicherheit() {
        return this.ivUebergangssicherheit;
    }

    /**
     * @param pvUebergangssicherheit the uebergangssicherheit to set
     */
    public void setUebergangssicherheit(String pvUebergangssicherheit) {
        this.ivUebergangssicherheit = pvUebergangssicherheit;
    }

    /**
     * @return the zuweisungsbetrag
     */
    public String getZuweisungsbetrag() {
        return this.ivZuweisungsbetrag;
    }

    /**
     * @param pvZuweisungsbetrag the zuweisungsbetrag to set
     */
    public void setZuweisungsbetrag(String pvZuweisungsbetrag) {
        this.ivZuweisungsbetrag = pvZuweisungsbetrag;
    }

    /**
     * @return the zuweisungsbetragWaehrung
     */
    public String getZuweisungsbetragWaehrung() {
        return this.ivZuweisungsbetragWaehrung;
    }

    /**
     * @param pvZuweisungsbetragWaehrung the zuweisungsbetragWaehrung to set
     */
    public void setZuweisungsbetragWaehrung(String pvZuweisungsbetragWaehrung) {
        this.ivZuweisungsbetragWaehrung = pvZuweisungsbetragWaehrung;
    }
    
    /**
     * 
     */
    public String getDeckungsregisterRelevant()
    {
    	return this.ivDeckungsregisterRelevant;
    }
    
    /**
     * 
     */
    public void setDeckungsregisterRelevant(String pvDeckungsregisterRelevant)
    {
    	this.ivDeckungsregisterRelevant = pvDeckungsregisterRelevant;
    }
    
    /**
     * @param pvZeile 
     * @param pvSAPCMS 
     * @return 
     * 
     */
    public boolean parseSicherungsumfang(String pvZeile, SAPCMS_Neu pvSAPCMS)
    {
     String lvTemp = new String();  // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
     int    lvZzStr=0;              // pointer fuer satzbereich
     
     // steuerung/iteration eingabesatz
     for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn semikolon erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setSicherungsumfang(lvLfd, lvTemp, pvSAPCMS);
       
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
     this.setSicherungsumfang(lvLfd, lvTemp, pvSAPCMS);     
     
     return true;
   }
    
    /**
     * Setzt einen Wert des Sicherungsumfangs
     * @param pvPos Position
     * @param pvWert Wert
     * @param pvSAPCMS 
     */
    private void setSicherungsumfang(int pvPos, String pvWert, SAPCMS_Neu pvSAPCMS)
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
              this.setSicherheitenvereinbarungId(pvWert);
              break;
          case 3:
              this.setCAG_POR(pvWert);
              break;
          case 4:
              this.setKennzeichen(pvWert);
              break;
          case 5:
              this.setWert(pvWert);
              break;
          case 6:
              this.setGroesse(pvWert.replace(",", ".").trim());
              break;
          case 7:
              this.setSperrkategorie(pvWert);
              break;
          case 8:
              this.setEinschraenkung(pvWert);
              break;
          case 9:
              this.setReferenzId(pvWert);
              break;
          case 10:
              this.setRang(pvWert);
              break;
          case 11:
              this.setGeschaeftswertartKennzeichen(pvWert);
              break;
          case 12:
              this.setLaufendeNummerAkte(pvWert);
              break;
          case 13:
              this.setKreditsystem(pvWert);
              break;
          case 14:
              this.setGeschaeftswertId(pvWert.substring(pvWert.lastIndexOf("-") + 1));
              break;
          case 15:
              this.setGeschaeftspartnerId(pvWert);
              break;
          case 16:
              this.setGeschaeftsparterfunktion(pvWert);
              break;
          case 17:
              this.setGueltigkeitsbeginn(pvWert);
              break;
          case 18:
              this.setGueltigkeitsende(pvWert);
              break;
          case 19:
              this.setPrioritaet(pvWert);
              break;
          case 20:
              this.setRangklasse(pvWert);
              break;
          case 21:
              this.setLinksKennzeichen(pvWert);
              break;
          case 22:
              this.setUebergangssicherheit(pvWert);
              break;
          case 23:
              pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setZuweisungsbetrag(pvWert.trim());
              // Zuweisungsbetrag in die Liste eintragen
              //System.out.println("Sicherheiten-ID: " + this.getSicherheitenvereinbarungId() + " Zuweisungsbetrag: " +  this.getZuweisungsbetrag());
              pvSAPCMS.getObjektZuweisungsbetragListe().addObjektZuweisungsbetrag(this.getSicherheitenvereinbarungId(), this.getZuweisungsbetrag());
              break;
          case 24:
              this.setZuweisungsbetragWaehrung(pvWert);
              break;
          case 25:
          	  this.setDeckungsregisterRelevant(pvWert);
              break;
          default:
              System.out.println("Sicherungsumfang: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt eines Sicherungsumfang in einen String
     * @return
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);        
        lvOut.append("SicherheitenvereinbarungId: " + this.ivSicherheitenvereinbarungId + StringKonverter.lineSeparator);
        lvOut.append("CAG_POR: " + this.ivCAG_POR + StringKonverter.lineSeparator);
        lvOut.append("Kennzeichen: " + this.ivKennzeichen + StringKonverter.lineSeparator);
        lvOut.append("Wert: " + this.ivWert + StringKonverter.lineSeparator);
        lvOut.append("Groesse: " + this.ivGroesse + StringKonverter.lineSeparator);
        lvOut.append("Sperrkategorie: " + this.ivSperrkategorie + StringKonverter.lineSeparator);
        lvOut.append("Einschraenkung: " + this.ivEinschraenkung + StringKonverter.lineSeparator);
        lvOut.append("ReferenzId: " + this.ivReferenzId + StringKonverter.lineSeparator);
        lvOut.append("Rang: " + this.ivRang + StringKonverter.lineSeparator);
        lvOut.append("GeschaeftswertartKennzeichen: " + this.ivGeschaeftswertartKennzeichen + StringKonverter.lineSeparator);
        lvOut.append("LaufendeNummerAkte: " + this.ivLaufendeNummerAkte + StringKonverter.lineSeparator);
        lvOut.append("Kreditsystem: " + this.ivKreditsystem + StringKonverter.lineSeparator);
        lvOut.append("GeschaeftswertId: " + this.ivGeschaeftswertId + StringKonverter.lineSeparator);
        lvOut.append("GeschaeftspartnerId: " + this.ivGeschaeftspartnerId + StringKonverter.lineSeparator);
        lvOut.append("Geschaeftspartnerfunktion: " + this.ivGeschaeftsparterfunktion + StringKonverter.lineSeparator);
        lvOut.append("Gueltigkeitsbeginn: " + this.ivGueltigkeitsbeginn + StringKonverter.lineSeparator);
        lvOut.append("Gueltigkeitsende: " + this.ivGueltigkeitsende + StringKonverter.lineSeparator);
        lvOut.append("Prioritaet: " + this.ivPrioritaet + StringKonverter.lineSeparator);
        lvOut.append("Rangklasse: " + this.ivRangklasse + StringKonverter.lineSeparator);
        lvOut.append("LinksKennzeichen: " + this.ivLinksKennzeichen + StringKonverter.lineSeparator);
        lvOut.append("Uebergangssicherheit: " + this.ivUebergangssicherheit + StringKonverter.lineSeparator);
        lvOut.append("Zuweisungsbetrag: " + this.ivZuweisungsbetrag + StringKonverter.lineSeparator);
        lvOut.append("Zuweisungsbetragwaehrung: " + this.ivZuweisungsbetragWaehrung + StringKonverter.lineSeparator);
        lvOut.append("DeckungsregisterRelevant: " + this.ivDeckungsregisterRelevant + StringKonverter.lineSeparator);
        
        return lvOut.toString();
    }
}
