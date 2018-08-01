/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten.Extrakt;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import nlb.txs.schnittstelle.Darlehen.Daten.DarlehenKomplett;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class Darlehen 
{
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Buchungsdatum
     */
    private String ivBuchungsdatum;
    
    /**
     * Filename der Umsatz19
     */
    private String ivFilenameUmsatz19;
    
    /**
     * Herkunft des Darlehens
     */
    private String ivHerkunftDarlehen;
    
    /**
     * Herkunft der Sicherheit
     */
    private String ivHerkunftSicherheit;
    
    /**
     * Datenherkunft
     */
    private String ivHerkunftDaten;
    
    /**
     * Kundennummer
     */
    private String ivKundennummer;
    
    /**
     * Kusyma
     */
    private String ivKusyma;
    
    /**
     * Objektnummer
     */
    private String ivObjektnummer;
    
    /**
     * Objektart
     */
    private String ivObjektart;
    
    /**
     * Selektionskennzeichen
     * E = Darlehen, W = WP
     */
    private String ivSelektionskennzeichen;
    
    /**
     * Kontotyp
     * 01 - aktiv; 02 - passiv; 03 - konsortial aktiv; 04 - konsortial passiv
     */
    private String ivKontotyp;
    
    /**
     * Konto-Einrichtung
     */
    private String ivKontoEinrichtung;
    
    /**
     * Kontostatus
     */
    private String ivKontostatus;
    
    /**
     * Schluessel Zinsrechnung
     */
    private String ivZinsrechnungSchluessel;
    
    /**
     * Kontozustand
     */
    private String ivKontozustand;
    
    /**
     * Kontonummer
     */
    private String ivKontonummer;
    
    /**
     * Wertpapierkennummer
     */
    private String ivIsin;
    
    /**
     * Globalurkundennummer
     */
    private String ivGlobalurkundennr;
    
    /**
     * Finanzkontonummer
     */
    private String ivFinanzkontonummer;
    
    /**
     * Schluessel Buergschaft
     */
    private String ivBuergschaftSchluessel;
    
    /**
     * Sicherheitenschluessel
     */
    private String ivSicherheitenSchluessel;

    /**
     * Produktschluessel
     */
    private String ivProduktSchluessel;
    
    /**
     * Kompensationsschluessel
     */
    private String ivKompensationsschluessel;
    
    /**
     * Satzwaehrung
     */
    private String ivSatzwaehrung;
    
    /**
     * Vollvalutierungsdatum
     */
    private String ivVollvalutierungsdatum;
    
    /**
     * Bewilligungsdatum
     */
    private String ivBewilligungsdatum;
    
    /**
     * Tilgungsbeginn
     */
    private String ivTilgungsbeginn;
        
    /**
     * Tilgungstermin
     */
    private String ivTilgungstermin;
    
    /**
     * Datum Annahme
     */
    private String ivAnnahmeDatum;
    
    /**
     * Datum Zinsanpassung
     */
    private String ivZinsanpassungDatum;
    
    /**
     * Letzte Zinsanpassung;
     */
    private String ivLetzteZinsanpassung;
    
    /**
     * Naechste Zinsfaelligkeit
     */
    private String ivNaechsteZinsfaelligkeit;
    
    /**
     * Vertrag bis
     */
    private String ivVertragBis;
        
    /**
     * Buergennummer
     */
    private String ivBuergennummer;
    
    /**
     * Schluessel man. Leistungsrechnung
     */
    private String ivMaLeReSchluessel;
    
    /**
     * Deckungsschluessel
     */
    private String ivDeckungsschluessel;
    
    /**
     * Tilgungsschluessel - KTR
     */
    private String ivTilgungsschluesselKTR;
    
    /**
     * Tilgungsschluessel - KON
     */
    private String ivTilgungsschluesselKON;
    
    /**
     * Tilgungsschluessel Ultimo
     */
    private String ivTilgungsschluesselUltimo;
    
    /**
     * Ultimo Zinsschluessel
     */
    private String ivZinsschluessel;
    
    /**
     * Tilgung pro Jahr
     */
    private String ivTilgungJahr;
    
    /**
     * Zinsbeginn
     */
    private String ivZinsbeginn;
    
    /**
     * Gueltiger letzter Zinstermin
     */
    private String ivLetzterZinstermin;
    
    /**
     * Gueltiger naechster Zinstermin
     */
    private String ivNaechsterZinstermin;
    
    /**
     * Datum unwider. Zusage
     */
    private String ivZusageDatum;
    
    /**
     * Konto Zusatzangabe 1
     */
    private String ivKontoZusatz1;
    
    /**
     * Konto Zusatzangabe 2
     */
    private String ivKontoZusatz2;
    
    /**
     * Anpassungstermin
     */
    private String ivAnpassungstermin;
    
    /**
     * Schl�ssel Zinsvereinbarung
     */
    private String ivZinstyp;
    
    /**
     * Datum Gueltig ab 
     */
    private String ivGueltigAbDatum;
    
    /**
     * Datum Konditionierung vom
     */
    private String ivKondDatum;
    
    /**
     * Datum der Konditionierung
     */
    private String ivDatumKonditionierung;
    
    /**
     * Berechnungsgrundlage
     */
    private String ivBerechnungsgrundlage;
    
    /**
     * Faelligkeitstermin
     */
    private String ivFaelligkeitstermin;
    
    /**
     * Einrichtungsdatum Kondition
     */
    private String ivKondEinrichtungsdatum;
    
    /**
     * Schl�ssel Faelligkeit Bes.
     */
    private String ivFaelligkeitBes;
    
    /**
     * Schluessel Rechnungsmod
     */
    private String ivRechnungsmod;
    
    /**
     * Kennzeichen aktiv/passiv
     */
    private String ivKennzeichenAktPas;
    
    /**
     * Kundengruppe
     */
    private String ivKundengruppe;
    
    /**
     * Merkmal Kreditart
     */
    private String ivMerkmalKreditart;
    
    /**
     * Kredittyp
     * Ermittelt aus Kusyma
     * Ermittelt aus div. Feldern
     * 1 --> 1aHypothek
     * 2 --> kommunalverbuergte Hyp
     * 3 --> rein Kommunal
     * 4 --> verbuergt Kommunal
     * oder --> undefiniert
     */
    private String ivKredittyp;
    
    /**
     * Datum vorn. Auszahlg.
     */
    private String ivVorNotADat;
    
    /**
     * Kz. vornotierte Auszahlung
     */
    private String ivKennzeichenVorAus;
    
    /**
     * Buchungsdatum + 2
     */
    private String ivBuchungsdatumPlus2;
    
    /**
     * Schluessel Sollstellung
     */
    private String ivSollstellungSchluessel;
    
    /**
     * Schluessel Fristigkeit
     * 
     */
    private String ivFristigkeitSchluessel;
    
    /**
     * Anzahl Tilgungsperioden
     */
    private String ivAnzahlTilgungsperioden;
    
    /**
     * GrTilgPer
     * noch nicht definiert
     */
    private String ivGrTilgPer;
    
    /**
     * Anzahl Zinsperioden
     */
    private String ivAnzahlZinsperioden;
    
    /**
     * Groesse der Zinsperiode
     */
    private String ivGrZinsperiode;
    
    /**
     * Laufzeit der Zinsanpssung
     * 
     */
    private String ivLaufzeitZinsanpassung;

    /**
     * Rollover-Kennzeichen
     */
    private String ivKennzeichenRollover;

    /**
     * 
     */
    private String ivTilgungNePE;

    /**
     * 
     */
    private String ivTilgungVorFTage;

    /**
     * 
     */
    private String ivZinsVorFTage;

    /**
     * 
     */
    private String ivKennzeichenKonditionen;

    /**
     * 
     */
    private String ivUrsprungsLaufzeit;

    /**
     * Ursprungskapital
     */
    private String ivUrsprungsKapital;

    /**
     * Restkapital
     */
    private String ivRestkapital;

    /**
     * 
     */
    private String ivOffZusage;

    /**
     * 
     */
    private String ivBeleihungProzent;

    /**
     * Buergschaftprozent
     */
    private String ivBuergschaftProzent;

    /**
     * Auszahlungskurs
     */
    private String ivAuszahlungskurs;

    /**
     * 
     */
    private String ivBerichtigungsposten;

    /**
     * 
     */
    private String ivTilgungProzent;

    /**
     * 
     */
    private String ivDarlehenszinssatzProzent;

    /**
     * 
     */
    private String ivAnnuitaetenzinssatzProzent;

    /**
     * Berechnungsnominale
     */
    private String ivBerechnungsnominale;

    /**
     * Risikoaufschlag in Prozent
     */
    private String ivRisikoaufschlagProzent;

    /**
     * Tilgungsbetrag
     */
    private String ivTilgungsbetrag;

    /**
     * Cap
     */
    private String ivCap;

    /**
     * Floor
     */
    private String ivFloor;

    /**
     * Summe der Rueckstellungsleistung
     */
    private String ivSummeRueckstellungsleistung;

    /**
     * Summe der noch nicht faelligen Tilgung
     */
    private String ivSummeNoNiFaeTilgung;

    /**
     * Summe der Vorauszahlungen
     */
    private String ivSummeVorauszahlungen;

    /**
     * 
     */
    private String ivTilgungRueckstellung;

    /**
     * 
     */
    private String ivSummeUKAP;

    /**
     * 
     */
    private String ivSummeRKAP;

    /**
     * Zuweisungsbetrag
     */
    private String ivZuweisungsbetrag;

    /**
     * Solldeckung
     */
    private String ivSolldeckung;

    /**
     * Sondertilgung
     */
    private String ivSondertilgung;

    // CT 07.08.2012
    /**
     * Bewilligende Organisationseinheit
     */
    private String ivBewilligendeOE;
    
    /**
     * Verwaltende Organisationseinheit
     */
    private String ivVerwaltendeOE;
    
    /**
     * Produktverantwortliche OE (PAS) 
     */
    private String ivProduktverantwortlicheOE;
    
    /**
     * Kundenbetreuende Stelle
     */
    private String ivKundenbetreuendeOE;  
    // CT 07.08.2012
    
    /**
     * Umsatz12 
     */
    private String ivUmsatz12;
    
    /**
     * Umsatz19
     */
    private String ivUmsatz19;
    
    /**
     * Ausplatzierungsmerkmal
     */
    private String ivAusplatzierungsmerkmal;
    
    /**
     * log4j-Logger
     */
    private Logger ivLogger;        
        
    /**
     * Konstruktor
     * @param pvLogger 
     * @param pvFilenameUmsatz19 
     */
    public Darlehen(Logger pvLogger, String pvFilenameUmsatz19) 
    {
        this.ivLogger = pvLogger;
        this.ivFilenameUmsatz19 = pvFilenameUmsatz19;
        this.ivInstitutsnummer = new String();
        this.ivBuchungsdatum = new String();
        this.ivHerkunftDarlehen = new String();
        this.ivHerkunftSicherheit = new String();
        this.ivHerkunftDaten = new String();
        this.ivKundennummer = new String();
        this.ivKusyma = new String();
        this.ivObjektnummer = new String();
        this.ivObjektart = new String();
        this.ivSelektionskennzeichen = new String();
        this.ivKontotyp = new String();
        this.ivKontostatus = new String();
        this.ivZinsrechnungSchluessel = new String();
        this.ivKontoEinrichtung = new String();
        this.ivKontozustand = new String();
        this.ivKontonummer = new String();
        this.ivIsin = new String();
        this.ivGlobalurkundennr = new String();
        this.ivFinanzkontonummer = new String();
        this.ivBuergschaftSchluessel = new String();
        this.ivSicherheitenSchluessel = new String();
        this.ivProduktSchluessel = new String();
        this.ivKompensationsschluessel = new String();
        this.ivSatzwaehrung = new String();
        this.ivVollvalutierungsdatum = new String();
        this.ivBewilligungsdatum = new String();
        this.ivTilgungsbeginn = new String();
        this.ivTilgungstermin = new String();
        this.ivAnnahmeDatum = new String();
        this.ivZinsanpassungDatum = new String();
        this.ivLetzteZinsanpassung = new String();
        this.ivNaechsteZinsfaelligkeit = new String();
        this.ivVertragBis = new String();
        this.ivBuergennummer = new String();
        this.ivMaLeReSchluessel = new String();
        this.ivDeckungsschluessel = new String();
        this.ivTilgungsschluesselKTR = new String();
        this.ivTilgungsschluesselKON = new String();
        this.ivTilgungsschluesselUltimo = new String();
        this.ivZinsschluessel = new String();
        this.ivTilgungJahr = new String();
        this.ivZinsbeginn = new String();
        this.ivLetzterZinstermin = new String();
        this.ivNaechsterZinstermin = new String();
        this.ivZusageDatum = new String();
        this.ivKontoZusatz1 = new String();
        this.ivKontoZusatz2 = new String();
        this.ivAnpassungstermin = new String();
        this.ivZinstyp = new String();
        this.ivGueltigAbDatum = new String();
        this.ivKondDatum = new String();
        this.ivDatumKonditionierung = new String();
        this.ivBerechnungsgrundlage = new String();
        this.ivFaelligkeitstermin = new String();
        this.ivKondEinrichtungsdatum = new String();
        this.ivFaelligkeitBes = new String();
        this.ivRechnungsmod = new String();
        this.ivKennzeichenAktPas = new String();
        this.ivKundengruppe = new String();
        this.ivMerkmalKreditart = new String();
        this.ivKredittyp = new String();
        this.ivVorNotADat = new String();
        this.ivKennzeichenVorAus = new String();
        this.ivBuchungsdatumPlus2 = new String();
        this.ivSollstellungSchluessel = new String();
        this.ivFristigkeitSchluessel = new String();
        this.ivAnzahlTilgungsperioden = new String();
        this.ivGrTilgPer = new String();
        this.ivAnzahlZinsperioden = new String();
        this.ivGrZinsperiode = new String();
        this.ivLaufzeitZinsanpassung = new String();
        this.ivKennzeichenRollover = new String();
        this.ivTilgungNePE = new String();
        this.ivTilgungVorFTage = new String();
        this.ivZinsVorFTage = new String();
        this.ivKennzeichenKonditionen = new String();
        //this.anzahlKTS = new String();
        //this.anzahlKTOZB = new String();
        this.ivUrsprungsLaufzeit = new String();
        this.ivUrsprungsKapital = new String();
        this.ivRestkapital = new String();
        this.ivOffZusage = new String();
        this.ivBeleihungProzent = new String();
        this.ivBuergschaftProzent = new String();
        this.ivAuszahlungskurs = new String();
        this.ivBerichtigungsposten = new String();
        this.ivTilgungProzent = new String();
        this.ivDarlehenszinssatzProzent = new String();
        this.ivAnnuitaetenzinssatzProzent = new String();
        this.ivBerechnungsnominale = new String();
        this.ivRisikoaufschlagProzent = new String();
        this.ivTilgungsbetrag = new String();
        this.ivCap = new String();
        this.ivFloor = new String();
        this.ivSummeRueckstellungsleistung = new String();
        this.ivSummeNoNiFaeTilgung = new String();
        this.ivSummeVorauszahlungen = new String();
        this.ivTilgungRueckstellung = new String();
        this.ivSummeUKAP = new String();
        this.ivSummeRKAP = new String();
        this.ivZuweisungsbetrag = new String();
        this.ivSolldeckung = new String();
        this.ivSondertilgung = new String();
        this.ivUmsatz12 = new String();
        this.ivUmsatz19 = new String();
        this.ivAusplatzierungsmerkmal = new String();
    }

    /**
     * @return 
     */
    public String getInstitutsnummer()
    {
        return this.ivInstitutsnummer;
    }

    /**
     * @param pvInstnr
     */
    public void setInstitutsnummer(String pvInstnr)
    {
        this.ivInstitutsnummer = pvInstnr;
    }

    /**
     * @return 
     */
    public String getBuchungsdatum()
    {
        return this.ivBuchungsdatum;
    }
    
    /**
     * @param pvDatum
     */
    public void setBuchungsdatum(String pvDatum)
    {
        this.ivBuchungsdatum = pvDatum;
    }
    
    /**
     * @return the herkunftDarlehen
     */
    public String getHerkunftDarlehen() {
        return this.ivHerkunftDarlehen;
    }

    /**
     * @param pvHerkunftDarlehen the herkunftDarlehen to set
     */
    public void setHerkunftDarlehen(String pvHerkunftDarlehen) {
        this.ivHerkunftDarlehen = pvHerkunftDarlehen;
    }

    /**
     * @return the herkunftSicherheit
     */
    public String getHerkunftSicherheit() {
        return this.ivHerkunftSicherheit;
    }

    /**
     * @param pvHerkunftSicherheit the herkunftSicherheit to set
     */
    public void setHerkunftSicherheit(String pvHerkunftSicherheit) {
        this.ivHerkunftSicherheit = pvHerkunftSicherheit;
    }

    /**
     * @return the herkunftDaten
     */
    public String getHerkunftDaten() {
        return this.ivHerkunftDaten;
    }

    /**
     * @param pvHerkunftDaten the herkunftDaten to set
     */
    public void setHerkunftDaten(String pvHerkunftDaten) {
        this.ivHerkunftDaten = pvHerkunftDaten;
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
     * @return the kusyma
     */
    public String getKusyma() {
        return this.ivKusyma;
    }

    /**
     * @param pvKusyma the kusyma to set
     */
    public void setKusyma(String pvKusyma) {
        this.ivKusyma = pvKusyma;
    }

    /**
     * @return the objektnummer
     */
    public String getObjektnummer() {
        return this.ivObjektnummer;
    }

    /**
     * @param pvObjektnummer the objektnummer to set
     */
    public void setObjektnummer(String pvObjektnummer) {
        this.ivObjektnummer = pvObjektnummer;
    }

    /**
     * @return the objektart
     */
    public String getObjektart() {
        return this.ivObjektart;
    }

    /**
     * @param pvObjektart the objektart to set
     */
    public void setObjektart(String pvObjektart) {
        this.ivObjektart = pvObjektart;
    }

    /**
     * @return the selektionskennzeichen
     */
    public String getSelektionskennzeichen() {
        return this.ivSelektionskennzeichen;
    }

    /**
     * @param pvSelektionskennzeichen the selektionskennzeichen to set
     */
    public void setSelektionskennzeichen(String pvSelektionskennzeichen) {
        this.ivSelektionskennzeichen = pvSelektionskennzeichen;
    }

    /**
     * @return 
     */
    public String getKontotyp()
    {
        return this.ivKontotyp;
    }
    
    /**
     * @param pvKontotyp 
     */
    public void setKontotyp(String pvKontotyp)
    {
        this.ivKontotyp = pvKontotyp;
    }
    
    /**
     * @return the kontostatus
     */
    public String getKontostatus() {
        return this.ivKontostatus;
    }

    /**
     * @param pvKontostatus the kontostatus to set
     */
    public void setKontostatus(String pvKontostatus) {
        this.ivKontostatus = pvKontostatus;
    }

    /**
     * @return the zinsrechnungSchluessel
     */
    public String getZinsrechnungSchluessel() {
        return this.ivZinsrechnungSchluessel;
    }

    /**
     * @param pvZinsrechnungSchluessel the zinsrechnungSchluessel to set
     */
    public void setZinsrechnungSchluessel(String pvZinsrechnungSchluessel) {
        this.ivZinsrechnungSchluessel = pvZinsrechnungSchluessel;
    }

    /**
     * @return 
     * 
     */
    public String getKontoEinrichtung()
    {
        return this.ivKontoEinrichtung;
    }
    
    /**
     * @param pvKontoEinrichtung 
     * 
     */
    public void setKontoEinrichtung(String pvKontoEinrichtung)
    {
        this.ivKontoEinrichtung = pvKontoEinrichtung;
    }
    
    /**
     * @return the kontozustand
     */
    public String getKontozustand() {
        return this.ivKontozustand;
    }

    /**
     * @param pvKontozustand the kontozustand to set
     */
    public void setKontozustand(String pvKontozustand) {
        this.ivKontozustand = pvKontozustand;
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
     * @return the isin
     */
    public String getIsin() {
        return this.ivIsin;
    }

    /**
     * @param pvIsin the isin to set
     */
    public void setIsin(String pvIsin) {
        this.ivIsin = pvIsin;
    }

    /**
     * @return the globalurkundennr
     */
    public String getGlobalurkundennr() {
        return this.ivGlobalurkundennr;
    }

    /**
     * @param pvGlobalurkundennr the globalurkundennr to set
     */
    public void setGlobalurkundennr(String pvGlobalurkundennr) {
        this.ivGlobalurkundennr = pvGlobalurkundennr;
    }

    /**
     * @return the finanzkontonummer
     */
    public String getFinanzkontonummer() {
        return this.ivFinanzkontonummer;
    }

    /**
     * @param pvFinanzkontonummer the finanzkontonummer to set
     */
    public void setFinanzkontonummer(String pvFinanzkontonummer) {
        this.ivFinanzkontonummer = pvFinanzkontonummer;
    }

    /**
     * @return the buergschaftSchluessel
     */
    public String getBuergschaftSchluessel() {
        return this.ivBuergschaftSchluessel;
    }

    /**
     * @param pvBuergschaftSchluessel the buergschaftSchluessel to set
     */
    public void setBuergschaftSchluessel(String pvBuergschaftSchluessel) {
        this.ivBuergschaftSchluessel = pvBuergschaftSchluessel;
    }

    /**
     * @return the sicherheitenSchluessel
     */
    public String getSicherheitenSchluessel() {
        return this.ivSicherheitenSchluessel;
    }

    /**
     * @param pvSicherheitenSchluessel the sicherheitenSchluessel to set
     */
    public void setSicherheitenSchluessel(String pvSicherheitenSchluessel) {
        this.ivSicherheitenSchluessel = pvSicherheitenSchluessel;
    }

    /**
     * @return the produktSchluessel
     */
    public String getProduktSchluessel() {
        return this.ivProduktSchluessel;
    }

    /**
     * @param pvProduktSchluessel the produktSchluessel to set
     */
    public void setProduktSchluessel(String pvProduktSchluessel) {
        this.ivProduktSchluessel = pvProduktSchluessel;
    }

    /**
     * @return the kompensationsschluessel
     */
    public String getKompensationsschluessel() {
        return this.ivKompensationsschluessel;
    }

    /**
     * @param pvKompensationsschluessel the kompensationsschluessel to set
     */
    public void setKompensationsschluessel(String pvKompensationsschluessel) {
        this.ivKompensationsschluessel = pvKompensationsschluessel;
    }

    /**
     * @return the satzwaehrung
     */
    public String getSatzwaehrung() {
        return this.ivSatzwaehrung;
    }

    /**
     * @param pvSatzwaehrung the satzwaehrung to set
     */
    public void setSatzwaehrung(String pvSatzwaehrung) {
        this.ivSatzwaehrung = pvSatzwaehrung;
    }

    /**
     * @return the vollvalutierungsdatum
     */
    public String getVollvalutierungsdatum() {
        return this.ivVollvalutierungsdatum;
    }

    /**
     * @param pvVollvalutierungsdatum the vollvalutierungsdatum to set
     */
    public void setVollvalutierungsdatum(String pvVollvalutierungsdatum) {
        this.ivVollvalutierungsdatum = pvVollvalutierungsdatum;
    }

    /**
     * @return the bewillungsdatum
     */
    public String getBewilligungsdatum() {
        return this.ivBewilligungsdatum;
    }

    /**
     * @param pvBewillungsdatum the bewillungsdatum to set
     */
    public void setBewilligungsdatum(String pvBewillungsdatum) {
        this.ivBewilligungsdatum = pvBewillungsdatum;
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
     * @return the annahmeDatum
     */
    public String getAnnahmeDatum() {
        return this.ivAnnahmeDatum;
    }

    /**
     * @param pvAnnahmeDatum the annahmeDatum to set
     */
    public void setAnnahmeDatum(String pvAnnahmeDatum) {
        this.ivAnnahmeDatum = pvAnnahmeDatum;
    }

    /**
     * @return the zinsanpassungDatum
     */
    public String getZinsanpassungDatum() {
        return this.ivZinsanpassungDatum;
    }

    /**
     * @param pvZinsanpassungDatum the zinsanpassungDatum to set
     */
    public void setZinsanpassungDatum(String pvZinsanpassungDatum) {
        this.ivZinsanpassungDatum = pvZinsanpassungDatum;
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
     * @return the naechsteZinsfaelligkeit
     */
    public String getNaechsteZinsfaelligkeit() {
        return this.ivNaechsteZinsfaelligkeit;
    }

    /**
     * @param pvNaechsteZinsfaelligkeit the naechsteZinsfaelligkeit to set
     */
    public void setNaechsteZinsfaelligkeit(String pvNaechsteZinsfaelligkeit) {
        this.ivNaechsteZinsfaelligkeit = pvNaechsteZinsfaelligkeit;
    }

    /**
     * @return the vertragBis
     */
    public String getVertragBis() {
        return this.ivVertragBis;
    }

    /**
     * @param pvVertragBis the vertragBis to set
     */
    public void setVertragBis(String pvVertragBis) {
        this.ivVertragBis = pvVertragBis;
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
     * @return the maLeReSchluessel
     */
    public String getMaLeReSchluessel() {
        return this.ivMaLeReSchluessel;
    }

    /**
     * @param pvMaLeReSchluessel the maLeReSchluessel to set
     */
    public void setMaLeReSchluessel(String pvMaLeReSchluessel) {
        this.ivMaLeReSchluessel = pvMaLeReSchluessel;
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
     * @return the tilgungsschluessel
     */
    public String getTilgungsschluesselKTR() {
        return this.ivTilgungsschluesselKTR;
    }

    /**
     * @param pvTilgungsschluessel the tilgungsschluessel to set
     */
    public void setTilgungsschluesselKTR(String pvTilgungsschluessel) {
        this.ivTilgungsschluesselKTR = pvTilgungsschluessel;
    }
    /**
     * @return the tilgungsschluessel
     */
    public String getTilgungsschluesselKON() {
        return this.ivTilgungsschluesselKON;
    }

    /**
     * @param pvTilgungsschluessel the tilgungsschluessel to set
     */
    public void setTilgungsschluesselKON(String pvTilgungsschluessel) {
        this.ivTilgungsschluesselKON = pvTilgungsschluessel;
    }

    
    
    /**
     * @return the tilgungsschluesselUltimo
     */
    public String getTilgungsschluesselUltimo() {
        return this.ivTilgungsschluesselUltimo;
    }

    /**
     * @param pvTilgungsschluesselUltimo the tilgungsschluesselUltimo to set
     */
    public void setTilgungsschluesselUltimo(String pvTilgungsschluesselUltimo) {
        this.ivTilgungsschluesselUltimo = pvTilgungsschluesselUltimo;
    }

    /**
     * @return the zinsschluessel
     */
    public String getZinsschluessel() {
        return this.ivZinsschluessel;
    }

    /**
     * @param pvZinsschluessel the zinsschluessel to set
     */
    public void setZinsschluessel(String pvZinsschluessel) {
        this.ivZinsschluessel = pvZinsschluessel;
    }

    /**
     * @return the tilgungJahr
     */
    public String getTilgungJahr() {
        return this.ivTilgungJahr;
    }

    /**
     * @param pvTilgungJahr the tilgungJahr to set
     */
    public void setTilgungJahr(String pvTilgungJahr) {
        this.ivTilgungJahr = pvTilgungJahr;
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
     * @return the letzterZinstermin
     */
    public String getLetzterZinstermin() {
        return this.ivLetzterZinstermin;
    }

    /**
     * @param pvLetzterZinstermin the letzterZinstermin to set
     */
    public void setLetzterZinstermin(String pvLetzterZinstermin) {
        this.ivLetzterZinstermin = pvLetzterZinstermin;
    }

    /**
     * @return the naechsterZinstermin
     */
    public String getNaechsterZinstermin() {
        return this.ivNaechsterZinstermin;
    }

    /**
     * @param pvNaechsterZinstermin the naechsterZinstermin to set
     */
    public void setNaechsterZinstermin(String pvNaechsterZinstermin) {
        this.ivNaechsterZinstermin = pvNaechsterZinstermin;
    }

    /**
     * @return the zusageDatum
     */
    public String getZusageDatum() {
        return this.ivZusageDatum;
    }

    /**
     * @param pvZusageDatum the zusageDatum to set
     */
    public void setZusageDatum(String pvZusageDatum) {
        this.ivZusageDatum = pvZusageDatum;
    }

    /**
     * @return the kontoZusatz1
     */
    public String getKontoZusatz1() {
        return this.ivKontoZusatz1;
    }

    /**
     * @param pvKontoZusatz1 the kontoZusatz1 to set
     */
    public void setKontoZusatz1(String pvKontoZusatz1) {
        this.ivKontoZusatz1 = pvKontoZusatz1;
    }

    /**
     * @return the kontoZusatz2
     */
    public String getKontoZusatz2() {
        return this.ivKontoZusatz2;
    }

    /**
     * @param pvKontoZusatz2 the kontoZusatz2 to set
     */
    public void setKontoZusatz2(String pvKontoZusatz2) {
        this.ivKontoZusatz2 = pvKontoZusatz2;
    }

    /**
     * @return the anpassungstermin
     */
    public String getAnpassungstermin() {
        return this.ivAnpassungstermin;
    }

    /**
     * @param pvAnpassungstermin the anpassungstermin to set
     */
    public void setAnpassungstermin(String pvAnpassungstermin) {
        this.ivAnpassungstermin = pvAnpassungstermin;
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
     * @return the gueltigAbDatum
     */
    public String getGueltigAbDatum() {
        return this.ivGueltigAbDatum;
    }

    /**
     * @param pvGueltigAbDatum the gueltigAbDatum to set
     */
    public void setGueltigAbDatum(String pvGueltigAbDatum) {
        this.ivGueltigAbDatum = pvGueltigAbDatum;
    }

    /**
     * @return the kondDatum
     */
    public String getKondDatum() {
        return this.ivKondDatum;
    }

    /**
     * @param pvKondDatum the kondDatum to set
     */
    public void setKondDatum(String pvKondDatum) {
        this.ivKondDatum = pvKondDatum;
    }

    /**
     * @return 
     * 
     */
    public String getDatumKonditionierung()
    {
        return this.ivDatumKonditionierung;
    }
    
    /**
     * @param pvDatumKonditionierung 
     * 
     */
    public void setDatumKonditionierung(String pvDatumKonditionierung)
    {
        this.ivDatumKonditionierung = pvDatumKonditionierung;
    }
    
    /**
     * @return the berechnungsgrundlage
     */
    public String getBerechnungsgrundlage() {
        return this.ivBerechnungsgrundlage;
    }

    /**
     * @param pvBerechnungsgrundlage the berechnungsgrundlage to set
     */
    public void setBerechnungsgrundlage(String pvBerechnungsgrundlage) {
        this.ivBerechnungsgrundlage = pvBerechnungsgrundlage;
    }

    /**
     * @return the faelligkeitstermin
     */
    public String getFaelligkeitstermin() {
        return this.ivFaelligkeitstermin;
    }

    /**
     * @param pvFaelligkeitstermin the faelligkeitstermin to set
     */
    public void setFaelligkeitstermin(String pvFaelligkeitstermin) {
        this.ivFaelligkeitstermin = pvFaelligkeitstermin;
    }

    /**
     * @return the kondEinrichtungsdatum
     */
    public String getKondEinrichtungsdatum() {
        return this.ivKondEinrichtungsdatum;
    }

    /**
     * @param pvKondEinrichtungsdatum the kondEinrichtungsdatum to set
     */
    public void setKondEinrichtungsdatum(String pvKondEinrichtungsdatum) {
        this.ivKondEinrichtungsdatum = pvKondEinrichtungsdatum;
    }

    /**
     * @return the faelligkeitBes
     */
    public String getFaelligkeitBes() {
        return this.ivFaelligkeitBes;
    }

    /**
     * @param pvFaelligkeitBes the faelligkeitBes to set
     */
    public void setFaelligkeitBes(String pvFaelligkeitBes) {
        this.ivFaelligkeitBes = pvFaelligkeitBes;
    }

    /**
     * @return the rechnungsmod
     */
    public String getRechnungsmod() {
        return this.ivRechnungsmod;
    }

    /**
     * @param pvRechnungsmod the rechnungsmod to set
     */
    public void setRechnungsmod(String pvRechnungsmod) {
        this.ivRechnungsmod = pvRechnungsmod;
    }

    /**
     * @return the kennzeichenAktPas
     */
    public String getKennzeichenAktPas() {
        return this.ivKennzeichenAktPas;
    }

    /**
     * @param pvKennzeichenAktPas the kennzeichenAktPas to set
     */
    public void setKennzeichenAktPas(String pvKennzeichenAktPas) {
        this.ivKennzeichenAktPas = pvKennzeichenAktPas;
    }

    /**
     * @return the kundengruppe
     */
    public String getKundengruppe() {
        return this.ivKundengruppe;
    }

    /**
     * @param pvKundengruppe the kundengruppe to set
     */
    public void setKundengruppe(String pvKundengruppe) {
        this.ivKundengruppe = pvKundengruppe;
    }

    /**
     * @return the merkmalKreditart
     */
    public String getMerkmalKreditart() {
        return this.ivMerkmalKreditart;
    }

    /**
     * @param pvMerkmalKreditart the merkmalKreditart to set
     */
    public void setMerkmalKreditart(String pvMerkmalKreditart) {
        this.ivMerkmalKreditart = pvMerkmalKreditart;
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
     * @return the vorNotADat
     */
    public String getVorNotADat() {
        return this.ivVorNotADat;
    }

    /**
     * @param pvVorNotADat the vorNotADat to set
     */
    public void setVorNotADat(String pvVorNotADat) {
        this.ivVorNotADat = pvVorNotADat;
    }

    /**
     * @return the kennzeichenVorAus
     */
    public String getKennzeichenVorAus() {
        return this.ivKennzeichenVorAus;
    }

    /**
     * @param pvKennzeichenVorAus the kennzeichenVorAus to set
     */
    public void setKennzeichenVorAus(String pvKennzeichenVorAus) {
        this.ivKennzeichenVorAus = pvKennzeichenVorAus;
    }

    /**
     * @return the buchungsdatumPlus2
     */
    public String getBuchungsdatumPlus2() {
        return this.ivBuchungsdatumPlus2;
    }

    /**
     * @param pvBuchungsdatumPlus2 the buchungsdatumPlus2 to set
     */
    public void setBuchungsdatumPlus2(String pvBuchungsdatumPlus2) {
        this.ivBuchungsdatumPlus2 = pvBuchungsdatumPlus2;
    }

    /**
     * @return the sollstellungSchluessel
     */
    public String getSollstellungSchluessel() {
        return this.ivSollstellungSchluessel;
    }

    /**
     * @param pvSollstellungSchluessel the sollstellungSchluessel to set
     */
    public void setSollstellungSchluessel(String pvSollstellungSchluessel) {
        this.ivSollstellungSchluessel = pvSollstellungSchluessel;
    }

    /**
     * @return the fristigkeitSchluessel
     */
    public String getFristigkeitSchluessel() {
        return this.ivFristigkeitSchluessel;
    }

    /**
     * @param pvFristigkeitSchluessel the fristigkeitSchluessel to set
     */
    public void setFristigkeitSchluessel(String pvFristigkeitSchluessel) {
        this.ivFristigkeitSchluessel = pvFristigkeitSchluessel;
    }

    /**
     * @return the anzahlTilgungsperioden
     */
    public String getAnzahlTilgungsperioden() {
        return this.ivAnzahlTilgungsperioden;
    }

    /**
     * @param pvAnzahlTilgungsperioden the anzahlTilgungsperioden to set
     */
    public void setAnzahlTilgungsperioden(String pvAnzahlTilgungsperioden) {
        this.ivAnzahlTilgungsperioden = pvAnzahlTilgungsperioden;
    }

    /**
     * @return the grTilgPer
     */
    public String getGrTilgPer() {
        return this.ivGrTilgPer;
    }

    /**
     * @param pvGrTilgPer the grTilgPer to set
     */
    public void setGrTilgPer(String pvGrTilgPer) {
        this.ivGrTilgPer = pvGrTilgPer;
    }

    /**
     * @return the anzahlZinsperioden
     */
    public String getAnzahlZinsperioden() {
        return this.ivAnzahlZinsperioden;
    }

    /**
     * @param pvAnzahlZinsperioden the anzahlZinsperioden to set
     */
    public void setAnzahlZinsperioden(String pvAnzahlZinsperioden) {
        this.ivAnzahlZinsperioden = pvAnzahlZinsperioden;
    }

    /**
     * @return the grZinsperiode
     */
    public String getGrZinsperiode() {
        return this.ivGrZinsperiode;
    }

    /**
     * @param pvGrZinsperiode the grZinsperiode to set
     */
    public void setGrZinsperiode(String pvGrZinsperiode) {
        this.ivGrZinsperiode = pvGrZinsperiode;
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
     * @return the tilgungNePE
     */
    public String getTilgungNePE() {
        return this.ivTilgungNePE;
    }

    /**
     * @param pvTilgungNePE the tilgungNePE to set
     */
    public void setTilgungNePE(String pvTilgungNePE) {
        this.ivTilgungNePE = pvTilgungNePE;
    }

    /**
     * @return the tilgungVorFTage
     */
    public String getTilgungVorFTage() {
        return this.ivTilgungVorFTage;
    }

    /**
     * @param pvTilgungVorFTage the tilgungVorFTage to set
     */
    public void setTilgungVorFTage(String pvTilgungVorFTage) {
        this.ivTilgungVorFTage = pvTilgungVorFTage;
    }

    /**
     * @return the zinsVorFTage
     */
    public String getZinsVorFTage() {
        return this.ivZinsVorFTage;
    }

    /**
     * @param pvZinsVorFTage the zinsVorFTage to set
     */
    public void setZinsVorFTage(String pvZinsVorFTage) {
        this.ivZinsVorFTage = pvZinsVorFTage;
    }

    /**
     * @return the kennzeichenKonditionen
     */
    public String getKennzeichenKonditionen() {
        return this.ivKennzeichenKonditionen;
    }

    /**
     * @param pvKennzeichenKonditionen the kennzeichenKonditionen to set
     */
    public void setKennzeichenKonditionen(String pvKennzeichenKonditionen) {
        this.ivKennzeichenKonditionen = pvKennzeichenKonditionen;
    }

    /**
     * @return the ursprungsLaufzeit
     */
    public String getUrsprungsLaufzeit() {
        return this.ivUrsprungsLaufzeit;
    }

    /**
     * @param pvUrsprungsLaufzeit the ursprungsLaufzeit to set
     */
    public void setUrsprungsLaufzeit(String pvUrsprungsLaufzeit) {
        this.ivUrsprungsLaufzeit = pvUrsprungsLaufzeit;
    }

    /**
     * @return the ursprungsKapital
     */
    public String getUrsprungsKapital() {
        return this.ivUrsprungsKapital;
    }

    /**
     * @param pvUrsprungsKapital the ursprungsKapital to set
     */
    public void setUrsprungsKapital(String pvUrsprungsKapital) {
        this.ivUrsprungsKapital = pvUrsprungsKapital;
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
     * @return the offZusage
     */
    public String getOffZusage() {
        return this.ivOffZusage;
    }

    /**
     * @param pvOffZusage the offZusage to set
     */
    public void setOffZusage(String pvOffZusage) {
        this.ivOffZusage = pvOffZusage;
    }

    /**
     * @return the beleihungProzent
     */
    public String getBeleihungProzent() {
        return this.ivBeleihungProzent;
    }

    /**
     * @param pvBeleihungProzent the beleihungProzent to set
     */
    public void setBeleihungProzent(String pvBeleihungProzent) {
        this.ivBeleihungProzent = pvBeleihungProzent;
    }

    /**
     * @return the buergschaftProzent
     */
    public String getBuergschaftProzent() {
        return this.ivBuergschaftProzent;
    }

    /**
     * @param pvBuergschaftProzent the buergschaftProzent to set
     */
    public void setBuergschaftProzent(String pvBuergschaftProzent) {
        this.ivBuergschaftProzent = pvBuergschaftProzent;
    }

    /**
     * @return the auszahlungskurs
     */
    public String getAuszahlungskurs() {
        return this.ivAuszahlungskurs;
    }

    /**
     * @param pvAuszahlungskurs the auszahlungskurs to set
     */
    public void setAuszahlungskurs(String pvAuszahlungskurs) {
        this.ivAuszahlungskurs = pvAuszahlungskurs;
    }

    /**
     * @return the berichtigungsposten
     */
    public String getBerichtigungsposten() {
        return this.ivBerichtigungsposten;
    }

    /**
     * @param pvBerichtigungsposten the berichtigungsposten to set
     */
    public void setBerichtigungsposten(String pvBerichtigungsposten) {
        this.ivBerichtigungsposten = pvBerichtigungsposten;
    }

    /**
     * @return the tilgungProzent
     */
    public String getTilgungProzent() {
        return this.ivTilgungProzent;
    }

    /**
     * @param pvTilgungProzent the tilgungProzent to set
     */
    public void setTilgungProzent(String pvTilgungProzent) {
        this.ivTilgungProzent = pvTilgungProzent;
    }

    /**
     * @return the darlehenszinssatzProzent
     */
    public String getDarlehenszinssatzProzent() {
        return this.ivDarlehenszinssatzProzent;
    }

    /**
     * @param pvDarlehenszinssatzProzent the darlehenszinssatzProzent to set
     */
    public void setDarlehenszinssatzProzent(String pvDarlehenszinssatzProzent) {
        this.ivDarlehenszinssatzProzent = pvDarlehenszinssatzProzent;
    }

    /**
     * @return the annuitaetenzinssatzProzent
     */
    public String getAnnuitaetenzinssatzProzent() {
        return this.ivAnnuitaetenzinssatzProzent;
    }

    /**
     * @param pvAnnuitaetenzinssatzProzent the annuitaetenzinssatzProzent to set
     */
    public void setAnnuitaetenzinssatzProzent(String pvAnnuitaetenzinssatzProzent) {
        this.ivAnnuitaetenzinssatzProzent = pvAnnuitaetenzinssatzProzent;
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
     * @return the risikoaufschlagProzent
     */
    public String getRisikoaufschlagProzent() {
        return this.ivRisikoaufschlagProzent;
    }

    /**
     * @param pvRisikoaufschlagProzent the risikoaufschlagProzent to set
     */
    public void setRisikoaufschlagProzent(String pvRisikoaufschlagProzent) {
        this.ivRisikoaufschlagProzent = pvRisikoaufschlagProzent;
    }

    /**
     * @return the tilgungsbetrag
     */
    public String getTilgungsbetrag() {
        return this.ivTilgungsbetrag;
    }

    /**
     * @param pvTilgungsbetrag the tilgungsbetrag to set
     */
    public void setTilgungsbetrag(String pvTilgungsbetrag) {
        this.ivTilgungsbetrag = pvTilgungsbetrag;
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
     * @return the summeRueckstellungsleistung
     */
    public String getSummeRueckstellungsleistung() {
        return this.ivSummeRueckstellungsleistung;
    }

    /**
     * @param pvSummeRueckstellungsleistung the summeRueckstellungsleistung to set
     */
    public void setSummeRueckstellungsleistung(String pvSummeRueckstellungsleistung) {
        this.ivSummeRueckstellungsleistung = pvSummeRueckstellungsleistung;
    }

    /**
     * @return the summeNoNiFaeTilgung
     */
    public String getSummeNoNiFaeTilgung() {
        return this.ivSummeNoNiFaeTilgung;
    }

    /**
     * @param pvSummeNoNiFaeTilgung the summeNoNiFaeTilgung to set
     */
    public void setSummeNoNiFaeTilgung(String pvSummeNoNiFaeTilgung) {
        this.ivSummeNoNiFaeTilgung = pvSummeNoNiFaeTilgung;
    }

    /**
     * @return the summeVorauszahlungen
     */
    public String getSummeVorauszahlungen() {
        return this.ivSummeVorauszahlungen;
    }

    /**
     * @param pvSummeVorauszahlungen the summeVorauszahlungen to set
     */
    public void setSummeVorauszahlungen(String pvSummeVorauszahlungen) {
        this.ivSummeVorauszahlungen = pvSummeVorauszahlungen;
    }

    /**
     * @return the tilgungRueckstellung
     */
    public String getTilgungRueckstellung() {
        return this.ivTilgungRueckstellung;
    }

    /**
     * @param pvTilgungRueckstellung the tilgungRueckstellung to set
     */
    public void setTilgungRueckstellung(String pvTilgungRueckstellung) {
        this.ivTilgungRueckstellung = pvTilgungRueckstellung;
    }

    /**
     * @return the summeUKAP
     */
    public String getSummeUKAP() {
        return this.ivSummeUKAP;
    }

    /**
     * @param pvSummeUKAP the summeUKAP to set
     */
    public void setSummeUKAP(String pvSummeUKAP) {
        this.ivSummeUKAP = pvSummeUKAP;
    }

    /**
     * @return the summeRKAP
     */
    public String getSummeRKAP() {
        return this.ivSummeRKAP;
    }

    /**
     * @param pvSummeRKAP the summeRKAP to set
     */
    public void setSummeRKAP(String pvSummeRKAP) {
        this.ivSummeRKAP = pvSummeRKAP;
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
     * @return the sondertilgung
     */
    public String getSondertilgung() {
        return this.ivSondertilgung;
    }

    /**
     * @param pvSondertilgung the sondertilgung to set
     */
    public void setSondertilgung(String pvSondertilgung) {
        this.ivSondertilgung = pvSondertilgung;
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
     * @return the produktverantwortlicheOE
     */
    public String getProduktverantwortlicheOE() {
        return this.ivProduktverantwortlicheOE;
    }

    /**
     * @param pvProduktverantwortlicheOE the produktverantwortlicheOE to set
     */
    public void setProduktverantwortlicheOE(String pvProduktverantwortlicheOE) {
        this.ivProduktverantwortlicheOE = pvProduktverantwortlicheOE;
    }

    /**
     * @return the kundenbetreuendeOE
     */
    public String getKundenbetreuendeOE() {
        return this.ivKundenbetreuendeOE;
    }

    /**
     * @param pvKundenbetreuendeOE the kundenbetreuendeOE to set
     */
    public void setKundenbetreuendeOE(String pvKundenbetreuendeOE) {
        this.ivKundenbetreuendeOE = pvKundenbetreuendeOE;
    }

    /**
     * @return 
     */
    public String getUmsatz12()
    {
        return ivUmsatz12;
    }
    
    /**
     * @param pvUmsatz12 
     */
    public void setUmsatz12(String pvUmsatz12)
    {
        this.ivUmsatz12 = pvUmsatz12;
    }

    /**
     * @return 
     */
    public String getUmsatz19()
    {
        return ivUmsatz19;
    }
    
    /**
     * @param pvUmsatz19 
     */
    public void setUmsatz19(String pvUmsatz19)
    {
        this.ivUmsatz19 = pvUmsatz19;
    }
    
    /**
     * @return the ausplatzierungmerkmal
     */
    public String getAusplatzierungsmerkmal() {
        return this.ivAusplatzierungsmerkmal;
    }

    /**
     * @param pvAusplatzierungsmerkmal the ausplatzierungmerkmal to set
     */
    public void setAusplatzierungsmerkmal(String pvAusplatzierungsmerkmal) {
        this.ivAusplatzierungsmerkmal = pvAusplatzierungsmerkmal;
    }

    /**
     * Extrahiert ein Darlehen aus dem Ausgangsdarlehen 
     * @param pvDarlehen 
     * @return
     */
    public void extractDarlehen(DarlehenKomplett pvDarlehen)
    {
        String lvNULLDat1 = "00000000";
        
        this.ivInstitutsnummer = pvDarlehen.getInstitutsnummer();
        this.ivBuchungsdatum = pvDarlehen.getBuchungsdatum();
        this.ivHerkunftDarlehen = "FID";
        this.ivHerkunftDaten = "VDW";
        // Ist ein OBJ-Segment vorhanden?
        if (pvDarlehen.getOBJ() != null)
        {
          this.ivKundennummer = pvDarlehen.getOBJ().getsOkunr();
          this.ivKusyma = pvDarlehen.getOBJ().getsOk();
          this.ivObjektnummer = pvDarlehen.getOBJ().getKopf().getsDwhonr();
          this.ivObjektart = pvDarlehen.getOBJ().getsOao();
        }
        else
        {
            this.ivKundennummer = pvDarlehen.getKTR().getKopf().getsDwhkdnr();
            this.ivKusyma = "";
            this.ivObjektnummer = pvDarlehen.getKTR().getKopf().getsDwhonr();
            this.ivObjektart = "";
        }
          
        this.ivSelektionskennzeichen = pvDarlehen.getKTS().getKopf().getsDwhres11();
        this.ivKontotyp = pvDarlehen.getKTS().getKopf().getsDwhtyp();
        this.ivKontoEinrichtung = pvDarlehen.getKTS().getsDein();
        this.ivKontostatus = pvDarlehen.getKTS().getKopf().getsDwhres10();
        this.ivZinsrechnungSchluessel = pvDarlehen.getKTS().getsDzr();
        this.ivKontozustand = pvDarlehen.getKTS().getsDkz();
        this.ivKontonummer = pvDarlehen.getKTS().getKopf().getsDwhknr();
        
        this.ivIsin = pvDarlehen.getKTR().getsDwpk();
        this.ivGlobalurkundennr = pvDarlehen.getKTR().getsDurk();
        this.ivFinanzkontonummer = pvDarlehen.getKTS().getsDfk();
        
        this.ivBuergschaftSchluessel = pvDarlehen.getKTS().getsDsb();
        this.ivSicherheitenSchluessel = pvDarlehen.getKTS().getsDsi();
        this.ivProduktSchluessel = pvDarlehen.getKTS().getsDpd();
        this.ivKompensationsschluessel = pvDarlehen.getKTS().getsDkom();
        this.ivSatzwaehrung = pvDarlehen.getKTS().getsDwiso();
        this.ivVollvalutierungsdatum = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDdv());
        this.ivBewilligungsdatum = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDdb());
        this.ivTilgungsbeginn = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDtb());
        this.ivTilgungNePE = DatumUtilities.pruefTag(pvDarlehen.getKTR().getsDnt());
        this.ivTilgungstermin = DatumUtilities.pruefTag(pvDarlehen.getKTR().getsDlte());
        // Leerdatum
        if (this.ivTilgungstermin.equals(lvNULLDat1))
        {
            this.ivTilgungstermin = DatumUtilities.pruefTag(pvDarlehen.getKTR().getsDnt());
        }
        
        int lvAnzsDtb = DatumUtilities.berechneAnzahlTage(pvDarlehen.getKTS().getsDtb());
        int lvAnzsDlte = DatumUtilities.berechneAnzahlTage(pvDarlehen.getKTR().getsDlte());
        int lvAnzsDnt = DatumUtilities.berechneAnzahlTage(pvDarlehen.getKTR().getsDnt());
        
        if (StringKonverter.convertString2Double(pvDarlehen.getREC().getsNtilb()) == 0.0)
        { // Keine Tilgung
          if (lvAnzsDtb > lvAnzsDlte && lvAnzsDtb > lvAnzsDnt)
          { // Umsetzen
              this.ivTilgungstermin = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDtb());
              //System.out.println("(" + this.ivKontonummer + ")-TilgTerm-dtb(" 
              //                   + pvDarlehen.getKTS().getsDtb() + ";" + lvAnzsDtb + ")-dnt("
              //                   + pvDarlehen.getKTR().getsDlte() + ";" + lvAnzsDnt + ")-Tilg("
              //                   + StringKonverter.convertString2Double(pvDarlehen.getREC().getsNtilb()) + ")");
          } // Umsetzen
        } // Keine Tilgung
        
        this.ivAnnahmeDatum = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDda());
        this.ivZinsanpassungDatum = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDza());
        this.ivLetzteZinsanpassung = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDlzip());
        this.ivNaechsteZinsfaelligkeit = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDnf());
        this.ivVertragBis = DatumUtilities.pruefTag(pvDarlehen.getKTR().getsDdvb());
        
        this.ivBuergennummer = pvDarlehen.getKTR().getsDkb();
        this.ivMaLeReSchluessel = pvDarlehen.getKTR().getsDml();
        this.ivDeckungsschluessel = pvDarlehen.getKTR().getsDd();
        this.ivAusplatzierungsmerkmal = pvDarlehen.getKTR().getAusplatzierungsmerkmal();
        this.ivTilgungsschluesselKTR = pvDarlehen.getKTR().getsDti();
        this.ivTilgungsschluesselUltimo = pvDarlehen.getKTR().getsDuft();
        this.ivZinsschluessel = pvDarlehen.getKTR().getsDufz();
                
        this.ivTilgungJahr = pvDarlehen.getKTR().getsDtp();
        this.ivZinsbeginn = DatumUtilities.pruefTag(pvDarlehen.getKTR().getsDzib());
        this.ivLetzterZinstermin = DatumUtilities.pruefTag(pvDarlehen.getKTR().getsDlze());
        this.ivNaechsterZinstermin = DatumUtilities.pruefTag(pvDarlehen.getKTR().getsDnz());
        this.ivKontoZusatz1 = pvDarlehen.getKTR().getsDzus1();
        this.ivKontoZusatz2 = pvDarlehen.getKTR().getsDzus2();
        // Anpassungstermin wird auf den aktuellen Saetzen ermittelt
        // Default .. KTS - letzte Zinsanpassung
        this.ivAnpassungstermin = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDlzip());
        int lvAnpTermTage = 0;
        lvAnpTermTage = DatumUtilities.berechneAnzahlTage(this.ivAnpassungstermin);
        // Pruefung KON .., per Def. ist Stammsatz <= heute !
        if (pvDarlehen.getKON() != null)
        {
            this.ivTilgungsschluesselKON = pvDarlehen.getKON().getsKoti();
            //System.out.println("AnzahlDatum: " + pvDarlehen.getKON().getAnzahlDatum());
            if (pvDarlehen.getKON().getAnzahlDatum() > 0)
            {
                //System.out.println(pvDarlehen.getKON().getAnzahlDatum() + " > " +lvAnpTermTage);
                if (pvDarlehen.getKON().getAnzahlDatum() > lvAnpTermTage)
                {
                    lvAnpTermTage = pvDarlehen.getKON().getAnzahlDatum();
                    this.ivAnpassungstermin = DatumUtilities.pruefTag(pvDarlehen.getKON().getsKog());
                    this.ivKennzeichenRollover = pvDarlehen.getKON().getsKoro();
                }
            }
        }
        
        //if (ivLog != null)
        //{
          /*  if (pvDarlehen.getKONTSZI() != null)
            {
              if (pvDarlehen.getKONTSTI() != null)
              {
                ivLogger.info("KONTS056;" + this.ivKontonummer + ";" + this.ivTilgungsschluesselKON + ";" 
                            + this.ivTilgungsschluesselKTR + ";" +this.ivZinsrechnungSchluessel + ";"
                            + pvDarlehen.getKONTSZI().getsKsfb() + ";" + pvDarlehen.getKONTSZI().getsKsuf() + ";" + pvDarlehen.getKONTSZI().getsKsrm() + ";"
                            + pvDarlehen.getKONTSTI().getsKsfb() + ";" + pvDarlehen.getKONTSTI().getsKsuf() + ";" + pvDarlehen.getKONTSTI().getsKsrm() + ";");
              }
              else
              {
                  ivLogger.info("KONTS056;" + this.ivKontonummer + ";" + this.ivTilgungsschluesselKON + ";" 
                          + this.ivTilgungsschluesselKTR + ";" +this.ivZinsrechnungSchluessel + ";"
                          + pvDarlehen.getKONTSZI().getsKsfb() + ";" + pvDarlehen.getKONTSZI().getsKsuf() + ";" + pvDarlehen.getKONTSZI().getsKsrm() + ";"
                          + "-" + ";" + "-" + ";");                 
              }
            }
            else
            {
                if (pvDarlehen.getKONTSTI() != null)
                {
                    ivLogger.info("KONTS056;" + this.ivKontonummer + ";" + this.ivTilgungsschluesselKON + ";" 
                            + this.ivTilgungsschluesselKTR + ";" +this.ivZinsrechnungSchluessel + ";"
                            + "-" + ";" + "-" + ";"
                            + pvDarlehen.getKONTSTI().getsKsfb() + ";" + pvDarlehen.getKONTSTI().getsKsuf() + ";" + pvDarlehen.getKONTSTI().getsKsrm() + ";");                   
                }
                else
                {
                  ivLogger.info("KONTS056;" + this.ivKontonummer + ";" + this.ivTilgungsschluesselKON + ";" 
                          + this.ivTilgungsschluesselKTR + ";" +this.ivZinsrechnungSchluessel + ";"
                          + "-" + ";" + "-" + ";" + "-" + ";" + "-" + ";");                
                }
            } */
        //}

        // Pruefung KTOZB ..., per Def. ist Stammsatz <= heute
        if (pvDarlehen.getKTOZB() != null)
        {
            if (pvDarlehen.getKTOZB().getAnzahlDatum() > 0)
            {
                int lvTmpAnzTage = DatumUtilities.berechneAnzahlTage(pvDarlehen.getKTOZB().getsZbza());
                if (lvTmpAnzTage > lvAnpTermTage)
                {
                    lvAnpTermTage = lvTmpAnzTage;
                    this.ivAnpassungstermin = DatumUtilities.pruefTag(pvDarlehen.getKTOZB().getsZbza());
                }
                // Jetzt Check des "zukuenftigen" Segments
                if (pvDarlehen.getKTOZBNextBuchungsdatum() != null)
                {
                    lvTmpAnzTage = DatumUtilities.berechneAnzahlTage(pvDarlehen.getKTOZBNextBuchungsdatum().getsZbza());
                    if (lvTmpAnzTage > lvAnpTermTage)
                    {
                        lvAnpTermTage = lvTmpAnzTage;
                        this.ivAnpassungstermin = DatumUtilities.pruefTag(pvDarlehen.getKTOZBNextBuchungsdatum().getsZbza());                       
                        ivLogger.info("->KtoNr<" + this.ivKontonummer + "> KTOZBNextBuchdat<" + this.ivAnpassungstermin + "> gesetzt");
                    }
                }
            }
        }
        // Pruefung KONTS
        // Erst Tilgung, dann Zinsen
        if (pvDarlehen.getKONTSTI() != null)
        {
            if (pvDarlehen.getKONTSTI().getAnzahlDatum() > 0)
            {
                if (pvDarlehen.getKONTSTI().getAnzahlDatum() > lvAnpTermTage)
                {
                    lvAnpTermTage = pvDarlehen.getKONTSTI().getAnzahlDatum();
                    this.ivAnpassungstermin = DatumUtilities.pruefTag(pvDarlehen.getKONTSTI().getsKsg());
                }
            }
        }
        if (pvDarlehen.getKONTSZI() != null)
        {
            if (pvDarlehen.getKONTSZI().getAnzahlDatum() > 0)
            {
                if (pvDarlehen.getKONTSZI().getAnzahlDatum() > lvAnpTermTage)
                {
                    lvAnpTermTage = pvDarlehen.getKONTSZI().getAnzahlDatum();
                    this.ivAnpassungstermin = DatumUtilities.pruefTag(pvDarlehen.getKONTSZI().getsKsg());
                }
            }
        }
        if (this.ivAnpassungstermin.equals(pvDarlehen.getKTS().getsDlzip()))
        {
            this.ivAnpassungstermin = DatumUtilities.addTag(DatumUtilities.pruefTag(this.ivAnpassungstermin));
        }
        
        this.ivZinstyp = pvDarlehen.getKTOZB().getsZbzv();
        this.ivGueltigAbDatum = DatumUtilities.pruefTag(pvDarlehen.getKTOZB().getsZbg());
        this.ivKondDatum = DatumUtilities.pruefTag(pvDarlehen.getKTOZB().getsZbkv());
        this.ivDatumKonditionierung = DatumUtilities.pruefTag(pvDarlehen.getKTR().getsDkv());
        this.ivLaufzeitZinsanpassung = pvDarlehen.getKTOZB().getsZbza();
        this.ivBerechnungsgrundlage = pvDarlehen.getKTOZB().getsZbbg();
        // Naechste Faelligkeit aus KTS ... spaeter vielleicht aus KONS
        this.ivFaelligkeitstermin = DatumUtilities.pruefTag(pvDarlehen.getKTS().getsDntf());
        if (pvDarlehen.getKONTSTI() != null)
        {
            this.ivKondEinrichtungsdatum = DatumUtilities.pruefTag(pvDarlehen.getKONTSTI().getsKsein());
        }
        
        this.ivFaelligkeitBes = "0"; // Grundstellung
        this.ivRechnungsmod = " ";   // Grundstellung

        if (pvDarlehen.getKONTSZI() != null)
        { // Es gibt einen
            this.ivFaelligkeitBes = pvDarlehen.getKONTSZI().getsKsfb();
            this.ivRechnungsmod   = pvDarlehen.getKONTSZI().getsKsrm();
        }  // Es gibt einen
        
        this.ivKennzeichenAktPas = pvDarlehen.getREC().getsNkzakpa();
        this.ivKundengruppe = pvDarlehen.getREC().getsNkungr();
        this.ivMerkmalKreditart = pvDarlehen.getREC().getsNkreart();
        umsetzenMerkmalKreditart();
             
        this.ivBuergschaftProzent = pvDarlehen.getKTR().getsDbue();
        this.ivKredittyp = (new Integer(ValueMapping.ermittleKredittyp(pvDarlehen.getKTR().getAusplatzierungsmerkmal(), pvDarlehen.getKTR().getsDbue()))).toString();
                 
        if (pvDarlehen.getINFV() != null)
        { // es ist etwas da
            this.ivVorNotADat = pvDarlehen.getINFV().getsBmfd();
            this.ivKennzeichenVorAus = pvDarlehen.getINFV().getsBbart();
        }
        
        this.ivBuchungsdatumPlus2 = DatumUtilities.berechneBuchungstagPlus2(pvDarlehen.getBuchungsdatum());
        this.ivSollstellungSchluessel = pvDarlehen.getKTS().getsDso();
        this.ivFristigkeitSchluessel = pvDarlehen.getKTS().getsDf();
        // zurzeit aus KTR, spaeter vielleicht mal aus KONTS ...
        this.ivAnzahlTilgungsperioden = pvDarlehen.getKTR().getsDtp();
        this.ivGrTilgPer = "0";
        // zurzeit aus KTR, spaeter vielleicht mal aus KONTS ...
        this.ivAnzahlZinsperioden = pvDarlehen.getKTR().getsDzp();
        this.ivGrZinsperiode = pvDarlehen.getKTR().getsDzpg();
        this.ivLaufzeitZinsanpassung = pvDarlehen.getKTR().getsDlz();
        
        // Start: Vorfaelligkeitstage ermitteln
        int lvTagePE = 0;
        int lvTageFae = 0;
        lvTageFae = DatumUtilities.berechneAnzahlTage(this.ivFaelligkeitstermin);
        lvTagePE = DatumUtilities.berechneAnzahlTage(this.ivTilgungNePE);
        
        this.ivTilgungVorFTage = "0"; // Default
        if (lvTagePE != 0 && lvTageFae != 0)
        { // Beide Daten da
            this.ivTilgungVorFTage = "" + (lvTagePE - lvTageFae);    
        }
        
        lvTagePE = 0;
        lvTageFae = 0;
        lvTageFae = DatumUtilities.berechneAnzahlTage(this.ivNaechsteZinsfaelligkeit);
        lvTagePE = DatumUtilities.berechneAnzahlTage(this.ivNaechsterZinstermin);
        this.ivZinsVorFTage = "0"; // Default
        if (lvTagePE != 0 && lvTageFae != 0)
        { // Beide Daten da
            this.ivZinsVorFTage = "" + (lvTagePE - lvTageFae);    
        }
        if (!this.ivTilgungVorFTage.equals(this.ivZinsVorFTage))
        {
            ivLogger.info("-->KtoVerf<>" + this.ivKontonummer +"(TilPE" + this.ivTilgungNePE 
            	          + ";TilFae" + this.ivFaelligkeitstermin + ";ZsPE" + this.ivNaechsterZinstermin 
            	          + ";ZSFae" + this.ivNaechsteZinsfaelligkeit + ";T" + this.ivTilgungVorFTage 
            	          + ";Z" + this.ivZinsVorFTage + ")");
        }
        // Ende: Vorfaelligkeitstage ermitteln
       
        // Erste Satz bekommt eine 1 ...die Folgekonditionen Zaehler + 2
        this.ivKennzeichenKonditionen = "1";
        this.ivUrsprungsLaufzeit = pvDarlehen.getKTR().getsDlu();
        // Aktive und negative Werte --> mit -1 multiplizieren
        this.ivUrsprungsKapital = pvDarlehen.getKTS().getsDu();
        if (this.ivKennzeichenAktPas.equals("A") && StringKonverter.convertString2Double(pvDarlehen.getKTS().getsDu()) < 0.0)
        {
            this.ivUrsprungsKapital = this.ivUrsprungsKapital.substring(1);
        }
        this.ivRestkapital = pvDarlehen.getKTS().getsDrk();
        if (this.ivKennzeichenAktPas.equals("A") && StringKonverter.convertString2Double(pvDarlehen.getKTS().getsDrk()) < 0.0)
        {
            this.ivRestkapital = this.ivRestkapital.substring(1);
        }
        this.ivOffZusage = pvDarlehen.getKTS().getsDav();
        if (this.ivKennzeichenAktPas.equals("A") && StringKonverter.convertString2Double(pvDarlehen.getKTS().getsDav()) < 0.0)
        {
            this.ivOffZusage = this.ivOffZusage.substring(1);
        }
        
        this.ivBeleihungProzent = pvDarlehen.getKTR().getsDbw();
        //System.out.println("KTR->sDbw: " + darlehen.getKTR().getsDbw());
        this.ivAuszahlungskurs = pvDarlehen.getKTR().getsDak();
        this.ivBerichtigungsposten = pvDarlehen.getKTR().getsDbszi();
        
        if (pvDarlehen.getKON() != null)
        { // Gibt es ein KON-Segment?
            this.ivTilgungProzent = pvDarlehen.getKON().getsKot();
            this.ivDarlehenszinssatzProzent = pvDarlehen.getKON().getsKozi();
            this.ivAnnuitaetenzinssatzProzent = pvDarlehen.getKON().getsKoaz();
            this.ivBerechnungsnominale = pvDarlehen.getKON().getsKobno();
            if (this.ivKennzeichenAktPas.equals("A") && StringKonverter.convertString2Double(pvDarlehen.getKON().getsKobno()) < 0.0)
            {
                this.ivBerechnungsnominale = this.ivBerechnungsnominale.substring(1);
            }
        }
         
        // CT 28.08.2012
        this.ivRisikoaufschlagProzent = "0.0";
        //ivLogger.info("Spread;" + this.ivKontonummer + ";" + this.ivZinsrechnungSchluessel + ";" + this.ivKennzeichenRollover + ";" + pvDarlehen.getKTOZB().getsZbabg() + ";");
        if (ivZinsrechnungSchluessel.equals("K"))
        {
          this.ivRisikoaufschlagProzent = pvDarlehen.getKTOZB().getsZbabg();
        }
        
        // Berechnung der Leistungsrate
        //double leistungsrateJahr = 0.0;
        BigDecimal lvLeistungsratePeriode = new BigDecimal("0.0");
        if (this.ivTilgungsschluesselKON.equals("1"))
        { // Nur Annuitaetendarlehen
          if (pvDarlehen.getKON() != null)
          {
              if (StringKonverter.convertString2Double(pvDarlehen.getKON().getsKole()) != 0.0)
              {
                  lvLeistungsratePeriode = new BigDecimal(pvDarlehen.getKON().getsKole());
              }
              else
              {
                  lvLeistungsratePeriode = new BigDecimal(pvDarlehen.getKON().getsKotr());
              }
          }
        }
        //System.out.println("LeistungsratePeriode: " + leistungsratePeriode);
        
        // Tilgungsbetrag kann naechste Leistung oder Tilgungsrate sein
        if (this.ivTilgungsschluesselKON.equals("2"))
        { // Abzahlung
          this.ivTilgungsbetrag = pvDarlehen.getKON().getsKotr();
        }
        else
        { // keine Abzahlung
          if (this.ivTilgungsschluesselKON.equals("1"))
          { // errechnete Leistungsrate
            this.ivTilgungsbetrag = lvLeistungsratePeriode.toString();
          }
          else
          { // keine Abzahlung und errechnete Leistungsrate
            if (this.ivTilgungsschluesselKON.equals("7"))
            { // vorgegebene Leistungsrate
              if (pvDarlehen.getKON() != null)
              { // Aktuelle Letungsrate
                  if (StringKonverter.convertString2Double(pvDarlehen.getKON().getsKole()) != 0.0)
                  {
                      this.ivTilgungsbetrag = pvDarlehen.getKON().getsKole();
                  }
                  else
                  {
                      this.ivTilgungsbetrag = pvDarlehen.getKON().getsKotr();
                  }
                      
              }
            }    
          }
        }
        
        // Alle bekommen den Tilgungsbetrag, wenn nicht Abzahlungskredit,
        // dann werden die Zinsen addiert
        BigDecimal lvHelpTilgungsbetrag = new BigDecimal(pvDarlehen.getREC().getsNtilb());
        if (!this.ivTilgungsschluesselKON.equals("2"))
        { // keine Abzahlung
          lvHelpTilgungsbetrag = lvHelpTilgungsbetrag.add(new BigDecimal(pvDarlehen.getREC().getsNzinb()));
          //System.out.println("Zinsen: " + StringKonverter.convertString2Double(darlehen.getREC().getsNzinb()));
        }
        if (this.ivKennzeichenAktPas.equals("A") && lvHelpTilgungsbetrag.doubleValue() < 0.0)
        {
          lvHelpTilgungsbetrag = lvHelpTilgungsbetrag.abs();    
        }
        this.ivTilgungsbetrag = lvHelpTilgungsbetrag.toString();
        
        // Debug-Ausgabe - spaeter per Variable erlauben
        //    ivLogger.info("Leistungsrate<" + this.ivTilgungsbetrag + ">"
        //                 + "Koti<" + this.ivTilgungsschluesselKON + ">"
        //                 + "BNO<" + this.ivBerechnungsnominale + ">" 
        //                 + "ZsPr<" + this.ivDarlehenszinssatzProzent + ">"
        //                 + "AnPr<" + this.ivAnnuitaetenzinssatzProzent + ">"
        //                 + "Per<" + pvDarlehen.getKTR().getsDtp() + ">");
        
        this.ivCap = pvDarlehen.getKTR().getsDhzi();
        this.ivFloor = pvDarlehen.getKTR().getsDnzi();
        
        BigDecimal lvHelpDouble = new BigDecimal(pvDarlehen.getREC().getsSurule());
        if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
          lvHelpDouble = lvHelpDouble.abs();
        this.ivSummeRueckstellungsleistung = lvHelpDouble.toString();
        
        lvHelpDouble = new BigDecimal(pvDarlehen.getREC().getsSunnft());
        if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
          lvHelpDouble = lvHelpDouble.abs();
        this.ivSummeNoNiFaeTilgung = lvHelpDouble.toString();

        lvHelpDouble = new BigDecimal(pvDarlehen.getREC().getsSuvorz());
        if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
          lvHelpDouble = lvHelpDouble.abs();
        this.ivSummeVorauszahlungen = lvHelpDouble.toString();

        // Erweiterung offene Leistung rueckst. Tilgung
        lvHelpDouble = new BigDecimal(pvDarlehen.getREC().getsNolrt());
        if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
          lvHelpDouble = lvHelpDouble.abs();
        this.ivTilgungRueckstellung = lvHelpDouble.toString();

        // Konsortiale ... Summe der anderen, nur bei korrektem Schl�ssel
        int lvHelpInt = StringKonverter.convertString2Int(this.ivKompensationsschluessel);
        if (lvHelpInt > 0 && lvHelpInt < 20)
        { // Bedeutet Konsortial
            lvHelpDouble = new BigDecimal(pvDarlehen.getREC().getsNkukap());
            if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
              lvHelpDouble = lvHelpDouble.abs();
            this.ivSummeUKAP = lvHelpDouble.toString();

            lvHelpDouble = new BigDecimal(pvDarlehen.getREC().getsKdrk());
            if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
              lvHelpDouble = lvHelpDouble.abs();
            this.ivSummeRKAP = lvHelpDouble.toString();
        }
        
        // Sondertilgung ... Umsatz 019
        if (pvDarlehen.getUMS() != null)
        { // UMS-Segment vorhanden
          lvHelpDouble = new BigDecimal(pvDarlehen.getUMS().getsUdmb());
          if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
            lvHelpDouble = lvHelpDouble.abs();
          this.ivSondertilgung = lvHelpDouble.toString();
        }
        
        // Solldeckung aus INF-SICHV-Y-B-K
        if (pvDarlehen.getINFS() != null)
        { // etwas da
            lvHelpDouble = new BigDecimal(pvDarlehen.getINFS().getsBdmb());
            if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
              lvHelpDouble = lvHelpDouble.abs();
            this.ivSolldeckung = lvHelpDouble.toString();           
        }
        
        // Zuweisungsbetrag aus INF-SICHV-Z-O
        if (pvDarlehen.getINFZ() != null)
        { // etwas da
            lvHelpDouble = new BigDecimal(pvDarlehen.getINFZ().getsBdmb());
            if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
              lvHelpDouble = lvHelpDouble.abs();
            this.ivZuweisungsbetrag = lvHelpDouble.toString();             
        }
             
        // CT 07.08.2012
        this.ivBewilligendeOE = pvDarlehen.getKTS().getsDbs();
        this.ivVerwaltendeOE = pvDarlehen.getKTS().getsDvs();
        this.ivProduktverantwortlicheOE = pvDarlehen.getKTS().getsDpdv();
        this.ivKundenbetreuendeOE = pvDarlehen.getOBJ().getsOks();
        // CT 07.08.2012
        
        this.ivUmsatz12 = pvDarlehen.getREC().getUmsatz12();
        this.ivUmsatz19 = pvDarlehen.getREC().getUmsatz19();
        
        // CT 27.07.2016 - Logging fuer BaFin Umstellung
        ivLogger.info("BaFin;" + this.ivKontonummer + ";" + this.ivObjektnummer + ";" + this.ivKundennummer + ";" + this.getDeckungsschluessel() + ";" + this.getAusplatzierungsmerkmal() + "; " + this.ivProduktSchluessel + ";" + this.ivUrsprungsKapital + ";" + this.ivBerechnungsnominale);
    } 
    
    /**
     * Extrahiert die WP Besonderheiten aus dem Ausgangsdarlehen 
     * @param pvDarlehen Die kompletten Darlehensinformationen
     */
    public void extractWPBesonderheiten(DarlehenKomplett pvDarlehen)
    {
        // Produktschluessel Weiterleitung
        String lvProduktschluesselWeiterleitung = "05201";
        // KFW BLB Kundennr. 1
        String lvKFWKDBLB1 = "0000001022";
        // KFW BLB Kundennr. 2
        String lvKFWKDBLB2 = "0000001041";
        
        // ISIN
        // Wenn keine ISIN => DE<KtoNr>
        if (pvDarlehen.getKTR().getsDwpk().trim().isEmpty())
        { // Kein Eintrag in der ISIN
          if (this.ivProduktSchluessel.equals(lvProduktschluesselWeiterleitung))
          {
              if (pvDarlehen.getInstitutsnummer().equals("004"))
              {
                  if (this.ivKundennummer.equals(lvKFWKDBLB1) || this.ivKundennummer.equals(lvKFWKDBLB2))
                  {
                      ivLogger.info("->KfwKtobeiWPuebernahme-BLB: " + this.ivKontonummer 
                                       + "(" + pvDarlehen.getInstitutsnummer() + ";" + this.ivHerkunftDarlehen
                                       + ";" + this.ivHerkunftDaten + ";" + this.ivKundennummer + ";" + lvKFWKDBLB1
                                       + ";" + lvKFWKDBLB2 + ")");
                      //return false;
                  }
              }
          }
        
          // CT - 03.01.2012 Leere ISIN liefern
          //this.isin = "DE" + this.kontonummer; 
          
        }
        else
        { // ISIN vorhanden, was auch immer drin steht
          this.ivIsin = pvDarlehen.getKTR().getsDwpk();   
        }
        
        // Global-Urkundennr
        // Wenn Namenspapier, dann Globalurkundennummer loeschen
        if (this.ivProduktSchluessel.equals("05011") || this.ivProduktSchluessel.equals("05012") ||
            this.ivProduktSchluessel.equals("05101") || this.ivProduktSchluessel.equals("05103") ||
            this.ivProduktSchluessel.equals("05201") || this.ivProduktSchluessel.equals("05202") ||
            this.ivProduktSchluessel.equals("05013") || this.ivProduktSchluessel.equals("05056") ||
            this.ivProduktSchluessel.equals("05057") || this.ivProduktSchluessel.equals("05058") ||
            this.ivProduktSchluessel.equals("05156") || this.ivProduktSchluessel.equals("05157"))
        {
            this.ivGlobalurkundennr = "";
        }
        else
        { // Keine Namenspapiere
            this.ivGlobalurkundennr = pvDarlehen.getKTR().getsDurk();
        }
        
        // Basiert auf Zins- und Tilgungsplan
        BigDecimal lvHelpDouble = (new BigDecimal(pvDarlehen.getREC().getsNtilb())).add(new BigDecimal(pvDarlehen.getREC().getsNzinb()));
        if (this.ivKennzeichenAktPas.equals("A") && lvHelpDouble.doubleValue() < 0.0)
        {
            lvHelpDouble = lvHelpDouble.abs();
        }
        this.ivTilgungsbetrag = lvHelpDouble.toString();
                
        // Debug-Ausgabe - spaeter per Variable erlauben
        //   ivLogger.info("Leistungsrate<" + this.ivTilgungsbetrag + ">"
        //                 + "Koti<" + this.ivTilgungsschluesselKON + ">"
        //                 + "BNO<" + this.ivBerechnungsnominale + ">" 
        //                 + "ZsPr<" + this.ivDarlehenszinssatzProzent + ">"
        //                 + "AnPr<" + this.ivAnnuitaetenzinssatzProzent + ">"
        //                 + "Per<" + pvDarlehen.getKTR().getsDtp() + ">"); 

        ivLogger.info("Konto " + this.ivKontonummer + ": UMS012=" + this.ivUmsatz12 + " und UMS019=" + this.ivUmsatz19);
        // Rueckkaeufe/Kuendigungsrecht
        // UMS019 != 0.0, dann in Umsatz19-Liste eintragen
        if (StringKonverter.convertString2Double(this.ivUmsatz19) != 0.0)
        {
            try
            {
                // CT 08.07.2014
                // Liste der Objekte mit Umsatz19 rausschreiben 
                File lvFileUmsatz19 = new File(ivFilenameUmsatz19);
                FileOutputStream lvFilterUmsatz19Os = null;

                try
                {
                  lvFilterUmsatz19Os = new FileOutputStream(lvFileUmsatz19);
                }
                catch (Exception e)
                {
                  ivLogger.error("Konnte " + ivFilenameUmsatz19 + " nicht oeffnen!");
                }    

                try
                {
                    lvFilterUmsatz19Os.write((this.ivIsin + "_" + this.ivKontonummer + StringKonverter.lineSeparator).getBytes());
                }
                catch (Exception e)
                {
                    ivLogger.error("Fehler bei Ausgabe in die Datei " + ivFilenameUmsatz19);
                }
                
                try
                {
                  lvFilterUmsatz19Os.close();
                }
                catch (Exception e)
                {
                    ivLogger.error("Konnte " + ivFilenameUmsatz19 + " nicht schliessen!");
                }   
            }
            catch (Exception exp)
            {
              ivLogger.error("Konnte " + this.ivIsin + "_" + this.ivKontonummer + " nicht in Umsatz19-Datei schreiben");
            }
        }

        // CT 08.10.2014 herausgenommen - 05.11.2014 wieder drin
        // WP > 8 kommen ab Ende April 2009 aus Darlehen
        // Pruefung Ktozustand = 8 und RKAP = 0 -> Ktozustand = 9
        if (this.ivKontozustand.equals("8"))
        { // Spezialpruefung
          lvHelpDouble = ((StringKonverter.convertString2BigDecimal(this.ivRestkapital)).add(StringKonverter.convertString2BigDecimal(this.ivSummeNoNiFaeTilgung))).add(StringKonverter.convertString2BigDecimal(this.ivSondertilgung));
          if (lvHelpDouble.doubleValue() == 0.0)
          {
              // CT 13.03.2012 - ausgebaut
              //long value = StringKonverter.convertString2Long(this.tilgungstermin); 
              //System.out.println("value: " + value);
              //System.out.println("Buchungsdatum: " + StringKonverter.convertString2Long(darlehen.getBuchungsdatum()));
              // CT 13.02.2012 - ausgebaut
              this.ivKontozustand = "9";
        
              ivLogger.info("-->Konto " + this.ivKontonummer + "(Restkapital:"
                            + this.ivRestkapital + ")=>KtoZustand-" + this.ivKontozustand 
                            + "- gesetzt");
           }
        }
        // CT 08.10.2014 herausgenommen - 05.11.2014 wieder drin
    }
    
    /**
     * Ermittelt den Kredittyp
     * @return Kredittyp
     */
    //private String ermittleKredittyp()
    //{
    //	String lvKredittyp = new String();
    	
        /*
        String lvSiSchl = this.ivSicherheitenSchluessel.substring(1); 

        //System.out.println("lvSiSchl: " + lvSiSchl);
        //System.out.println("MerkmalKreditart: " + this.ivMerkmalKreditart);
        
       
        lvKredittyp = "U"; // Default -> undefiniert
        if (this.ivMerkmalKreditart.equals("01"))
        {
        	lvKredittyp = "A"; // Realkredit
        }
        else
        { // Kein Realkredit
          if (this.ivMerkmalKreditart.equals("02"))
          {
        	  lvKredittyp = "C"; // Kredit an Kommunen
          }
          else
          { // weder 01 noch 02
              if (this.ivMerkmalKreditart.equals("04"))
              { // Kommunalverbuergt
                  if (lvSiSchl.equals("01") || 
                      lvSiSchl.equals("02") ||
                      lvSiSchl.equals("03") ||
                      lvSiSchl.equals("04") ||
                      lvSiSchl.equals("05") ||
                      lvSiSchl.equals("06") ||
                      lvSiSchl.equals("31"))
                  { // Hypotheken
                	  lvKredittyp = "B";  
                  }
                  else
                  { // Keine Hypotheken
                	  lvKredittyp = "D";
                  }
              }
          }
        } */
        
        // CT 15.06.2016 - Kann erst einmal nicht verwendet werden, da das Ausplatzierungsmerkmal in DarKa nicht
        // gesetzt wird.
        //System.out.println("Konto " + this.ivKontonummer + ": " + this.ivAusplatzierungsmerkmal + " " + this.ivBuergschaftProzent);
       // if ((this.ivDeckungsschluessel.equals("F") || this.ivDeckungsschluessel.equals("V") || this.ivDeckungsschluessel.equals("W"))
       //     && this.ivAusplatzierungsmerkmal.trim().isEmpty())
       // {
       //   ivLogger.info("Ausplatzierungsmerkmal;" + this.ivKontonummer + ";" + this.ivDeckungsschluessel + ";" + this.ivAusplatzierungsmerkmal + ";" + this.ivProduktSchluessel);
       // }
       // if (this.ivAusplatzierungsmerkmal.startsWith("H") || this.ivAusplatzierungsmerkmal.equals("O1"))
       // {
       // 	// Buergschaftprozent spielt keine Rolle -> Bei Hypotheken keine Buergschaft anliefern - CT 11.08.2016
       // 	lvKredittyp = "A";
       // }
       // if (this.ivAusplatzierungsmerkmal.startsWith("K") || this.ivAusplatzierungsmerkmal.equals("O3") || this.ivAusplatzierungsmerkmal.equals("O4"))
       // {
       // 	if (StringKonverter.convertString2Double(this.ivBuergschaftProzent) > 0.0)
       // 	{
       // 		lvKredittyp = "D";
       // 	}
       // 	else
       // 	{
       // 		lvKredittyp = "C";
       // 	}
       // }
        
        // Redefinition diverser Mappings auf Basis der ISIN               
        //String lvKISINPR1 = "ELK";
        //String lvKISINPR2 = "EBK";

        //String lvWISIN = this.ivIsin.toUpperCase();

        //if (lvWISIN.startsWith(lvKISINPR1) ||
        //    lvWISIN.startsWith(lvKISINPR2))
        //{// ISIN hat Redefinition
        //	ivLogger.info("ISIN Redefinition - Konto " + ivKontonummer + " - ISIN: " + lvWISIN);
         /* Deckungstyp - Stelle 4 => Pos 3 im Feld - 1-stellig */
         //if (sWISIN.charAt(3) == 'K')
         //{ /* DeckTyp - 1  */
          //cDeck = 'K' ;
          //sKTyp = "Kommunal";
         //} /* DeckTyp - 1  */
         //if (sWISIN.charAt(3) == 'P')
         //{ /* DeckTyp - 2  */
          //cDeck = 'P' ;
          //sKTyp = "Pfandbriefe";
         //} /* DeckTyp - 2  */
         /* Kredittyp   - Stelle 5/6 => Pos 4/5 im Feld - 2-stellig */
        // if (lvWISIN.substring(4, 6).equals("HR"))
         //{ /* Kredittyp - 1  */
        //	 lvKredittyp = "A";
         //} /* Kredittyp - 1  */
         //if (lvWISIN.substring(4, 6).equals("HB"))
         //{ /* Kredittyp - 2  */
        //	 lvKredittyp = "B";
         //} /* Kredittyp - 2  */
         //if (lvWISIN.substring(4, 6).equals("KR"))
         //{ /* Kredittyp - 3  */
        //	 lvKredittyp = "C";
         //} /* Kredittyp - 3  */
         //if (lvWISIN.substring(4, 6).equals("KB"))
         //{ /* Kredittyp - 4  */
        //	 lvKredittyp = "D";
        // } /* Kredittyp - 4  */
        //} /* ISIN hat Redefinition .. */

        /*
        //System.out.println("Kredittyp: " + lvKredittyp);
        // Nur zwei Zeichen !!!
        String lvHelpKomSchl = this.ivBuergschaftSchluessel.substring(1);
        // Definition von KreditTyp ...im Notfall DeckungsTyp danach def.
        if (lvKredittyp.equals("U"))
        { // hat immer noch nicht gezogen
            if (isProduktschluesselPfandbrief(this.ivProduktSchluessel) ||
                isProduktschluesselKommunal(this.ivProduktSchluessel))
                {
                   // Buergschaftsart "90" ergaenzt - CT 15.04.2013
                   if (lvHelpKomSchl.equals("10") || lvHelpKomSchl.equals("20") ||
                       lvHelpKomSchl.equals("50") || lvHelpKomSchl.equals("70") ||
                       lvHelpKomSchl.equals("90"))
                   { // Buergschaft
                     if (isProduktschluesselPfandbrief(this.ivProduktSchluessel)) 
                     {
                    	 lvKredittyp = "B";
                     }
                     if (isProduktschluesselKommunal(this.ivProduktSchluessel))
                     {
                    	 lvKredittyp = "D";
                     }
                   }
                   else
                   { // Keine Buergschaft
                       if (isProduktschluesselPfandbrief(this.ivProduktSchluessel)) 
                       {
                    	   lvKredittyp = "A";
                       }
                       if (isProduktschluesselKommunal(this.ivProduktSchluessel))
                       {
                    	   lvKredittyp = "C";
                       }                      
                   }
                }
        } */
       
       // return lvKredittyp;
   // }
    
    
    /**
     * Pruefungen, die zur Ablehnung des Darlehens fuehren
     * @return true - Darlehen okay; false - Darlehen ablehnen
     */
    public boolean darlehenPruefen()
    {
        boolean lvOkay = true;
        // Pruefungen, die zur Ablehnung fuehren
        int lvHelpInt = StringKonverter.convertString2Int(this.ivKompensationsschluessel);
        if (lvHelpInt > 19)
        { // Bedeutet Fremde!
            //System.out.println("-->Konto " + this.ivKontonummer + "("
            //        + this.ivHerkunftDarlehen + ";" + this.ivHerkunftDaten
            //        + ";" + this.ivKundennummer + ";" + this.ivObjektnummer
            //        + ") Fremdkonsortial");
            //if (ivLog != null)
            //{
                ivLogger.info("-->Konto " + this.ivKontonummer + "("
                    + this.ivHerkunftDarlehen + ";" + this.ivHerkunftDaten
                    + ";" + this.ivKundennummer + ";" + this.ivObjektnummer
                    + ") Fremdkonsortial");
            //}
            lvOkay = false;
        }
        
        lvHelpInt = StringKonverter.convertString2Int(this.ivKontozustand);
       
        if (lvHelpInt > 8)
        { // Uninteressante Konten
            //System.out.println("-->Konto " + this.ivKontonummer + "("
            //        + this.ivHerkunftDarlehen + ";" + this.ivHerkunftDaten
            //        + ";" + this.ivKundennummer + ";" + this.ivObjektnummer
            //        + ")KtoZustand-" + this.ivKontozustand + "-darf nicht");
            //if (ivLog != null)
            //{
                ivLogger.info("-->Konto " + this.ivKontonummer + "("
                    + this.ivHerkunftDarlehen + ";" + this.ivHerkunftDaten
                    + ";" + this.ivKundennummer + ";" + this.ivObjektnummer
                    + ")KtoZustand-" + this.ivKontozustand + "-darf nicht");
            //}
            lvOkay = false;
        }
        
        // Produktschluessel 01002 und 01003 nicht anliefern 
        // KFW-Durchleitungskredite
        if (this.ivInstitutsnummer.equals("004") && 
            (this.ivProduktSchluessel.equals("01002") || this.ivProduktSchluessel.equals("01003")))
        {
            ivLogger.info("-->Konto " + this.ivKontonummer + "("
                    + this.ivProduktSchluessel + ")-Produktschluessel wird nicht angeliefert");
            lvOkay = false;
        }
        
        return lvOkay;
    }
    
    /**
     * Umsetzung des Merkmal Kreditarts
     * Von intern auf KWG - Definition umschluesseln
     */
    private void umsetzenMerkmalKreditart()
    {
        if (this.ivMerkmalKreditart.equals("01") || this.ivMerkmalKreditart.equals("02") ||
            this.ivMerkmalKreditart.equals("03") || this.ivMerkmalKreditart.equals("04") ||
            this.ivMerkmalKreditart.equals("06") || this.ivMerkmalKreditart.equals("11") ||
            this.ivMerkmalKreditart.equals("13") || this.ivMerkmalKreditart.equals("20") ||
            this.ivMerkmalKreditart.equals("22") || this.ivMerkmalKreditart.equals("42") ||
            this.ivMerkmalKreditart.equals("43") || this.ivMerkmalKreditart.equals("60") ||
            this.ivMerkmalKreditart.equals("62") || this.ivMerkmalKreditart.equals("63") ||
            this.ivMerkmalKreditart.equals("64") || this.ivMerkmalKreditart.equals("65") ||
            this.ivMerkmalKreditart.equals("70") || this.ivMerkmalKreditart.equals("72") ||
            this.ivMerkmalKreditart.equals("73") || this.ivMerkmalKreditart.equals("74") ||
            this.ivMerkmalKreditart.equals("75"))
        { // Pers. Kredit, Grundst.
            this.ivMerkmalKreditart = "08";
        }
        if (this.ivMerkmalKreditart.equals("12") || this.ivMerkmalKreditart.equals("21") ||
            this.ivMerkmalKreditart.equals("40") || this.ivMerkmalKreditart.equals("41") ||
            this.ivMerkmalKreditart.equals("71"))
        { // Kommunalverbuergter Kredit
            this.ivMerkmalKreditart = "04";
        }
        if (this.ivMerkmalKreditart.equals("05"))
        { // Kredit an Kommunen (Direktkredit)
            this.ivMerkmalKreditart = "02";
        }
        if (this.ivMerkmalKreditart.equals("10") || this.ivMerkmalKreditart.equals("30") ||
            this.ivMerkmalKreditart.equals("50") || this.ivMerkmalKreditart.equals("61"))
        { // dinglich gesicherter Kredit (Realkredit)
            this.ivMerkmalKreditart = "01";
        }
        // "00" bleibt ... Grundstellung, Passivkonten
    }
    
    // CT 15.04.2013
    // 00316, 00322, 00608, 00636, 00637, 00643 ergaenzt
    // 01003, 01004, 01005 entfernt
    /**
     * Ist Produktschluessel Pfandbrief
     * @param prSchluessel Produktschluessel
     * @return true - Produktschluessel Pfandbrief; false - ansonsten
     */
    private boolean isProduktschluesselPfandbrief(String prSchluessel)
    {
        boolean lvIsGefunden = false;
        String[] sPfand = {"00303","00304","00305","00312","00315","00316","00319","00322","00323","00441",
                           "00443","00504","00505","00509","00602","00603","00604","00605","00606","00608",
                           "00609","00610","00611","00613","00614","00617","00620","00621","00622","00623",
                           "00630","00631","00632","00633","00634","00635","00636","00637","00640","00641",
                           "00642","00643","00702","00703","00705","00706","00707","00708","00712","03912"};
                
        int lauf = 0;
        while (lauf < sPfand.length && !lvIsGefunden)
        {
            if (prSchluessel.equals(sPfand[lauf]))
            {
                lvIsGefunden = true;
            }
            lauf++;
        }
        return lvIsGefunden;
    }
    
    // CT 15.04.2013
    // 00809, 00825, 00828 ergaenzt
    // 00506, 00702, 00712, 00826, 01002, 01003, 01004, 01005 entfernt
    // 00303, 00505, 00706 nach Produktschluessel Pfandbrief uebertragen
    // Wunsch BLB: Produktschluessel 211, 213 auch Kommunal -> noch nicht eingebaut... 
    /**
     * Ist Produktschluessel Kommunal
     * @param prSchluessel Produktschluessel
     * @return true - Produktschluessel Kommunal; false - ansonsten
     */
    private boolean isProduktschluesselKommunal(String prSchluessel)
    {
        boolean lvIsGefunden = false;
        String[] lvKommunal = {"00101","00103","00105","00106","00108","00109","00111","00112","00114",
                              "00115","00119","00131","00201","00202","00203","00204","00205","00208",
                              "00306","00307","00309","00310","00311","00314","00317","00318","00327",
                              "00402","00403","00404","00405","00412","00415","00416","00417","00802","00807","00809",
                              "00821","00822","00825","00827","00828","00829",
                              "03911","03912","03913","03917","04502","04503","04504","04505","04506",
                              "04507","04510","90100","90200","90300","90400","90500","90600"};
                        
        int lvLauf = 0;
        while (lvLauf < lvKommunal.length && !lvIsGefunden)
        {
            if (prSchluessel.equals(lvKommunal[lvLauf]))
            {
                lvIsGefunden = true;
            }
            lvLauf++;
        }
        return lvIsGefunden;
    }
    
}
