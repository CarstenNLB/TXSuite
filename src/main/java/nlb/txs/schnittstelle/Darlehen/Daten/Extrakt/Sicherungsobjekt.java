/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten.Extrakt;

import java.math.BigDecimal;

import nlb.txs.schnittstelle.Darlehen.Daten.DarlehenKomplett;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.INF;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class Sicherungsobjekt 
{
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
     * Objektnummer
     */
    private String ivObjektnummer;
    
    /**
     * Objektart
     */
    private String ivObjektart;
 
    /**
     * Postleitzahl des Objekts
     */
    private String ivPostleitzahl;
    
    /**
     * Ort des Objekts
     */
    private String ivOrt;
    
    /**
     * Strasse des Objekts
     */
    private String ivStrasse;
    
    /**
     * Beleihungsgebiet
     */
    private String ivBeleihungsgebiet;
    
    /**
     * Kundennummer
     */
    private String ivKundennummer;

    /**
     * Kontonummer
     */
    private String ivKontonummer;
    
    /**
     * Grundbuch
     */
    private String ivGrundbuch;
    
    /**
     * Band
     */
    private String ivBand;
    
    /**
     * Blatt
     */
    private String ivBlatt;
    
    /**
     * Laufende Nummer
     */
    private String ivLaufendeNummer;
    
    /**
     * Verwendungszweck
     */
    private String ivVerwendungszweck;
    
    /**
     * Zusatzsicherheit
     */
    private String ivZusatzsicherheit;
    
    /**
     * WKZ-Beleihungswert
     */
    private String ivBeleihungswertWKZ;
    
    /**
     * Baujahr
     */
    private String ivBaujahr;
    
    /**
     * Beleihungswert
     */
    private String ivBeleihungswert;
    
    /**
     * Sachwert
     */
    private String ivSachwert;
    
    /**
     * Ertragswert
     */
    private String ivErtragswert;
    
    /**
     * Grundschuld
     */
    private String ivGrundschuld;

    /**
     * Konstruktor
     */
    public Sicherungsobjekt() 
    {
        this.ivHerkunftDarlehen = new String();
        this.ivHerkunftSicherheit = new String();
        this.ivHerkunftDaten = new String();
        this.ivObjektnummer = new String();
        this.ivObjektart = new String();
        this.ivPostleitzahl = new String();
        this.ivOrt = new String();
        this.ivStrasse = new String();
        this.ivBeleihungsgebiet = new String();
        this.ivKundennummer = new String();
        this.ivKontonummer = new String();
        this.ivGrundbuch = new String();
        this.ivBand = new String();
        this.ivBlatt = new String();
        this.ivLaufendeNummer = new String();
        this.ivVerwendungszweck = new String();
        this.ivZusatzsicherheit = new String();
        this.ivBeleihungswertWKZ = new String();
        this.ivBaujahr = new String();
        this.ivBeleihungswert = new String();
        this.ivSachwert = new String();
        this.ivErtragswert = new String();
        this.ivGrundschuld = new String();
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
     * @return the postleitzahl
     */
    public String getPostleitzahl() {
        return this.ivPostleitzahl;
    }

    /**
     * @param pvPostleitzahl the postleitzahl to set
     */
    public void setPostleitzahl(String pvPostleitzahl) {
        this.ivPostleitzahl = pvPostleitzahl;
    }

    /**
     * @return the ort
     */
    public String getOrt() {
        return this.ivOrt;
    }

    /**
     * @param pvOrt the ort to set
     */
    public void setOrt(String pvOrt) {
        this.ivOrt = pvOrt;
    }

    /**
     * @return the strasse
     */
    public String getStrasse() {
        return this.ivStrasse;
    }

    /**
     * @param pvStrasse the strasse to set
     */
    public void setStrasse(String pvStrasse) {
        this.ivStrasse = pvStrasse;
    }

    /**
     * @return the beleihungsgebiet
     */
    public String getBeleihungsgebiet() {
        return this.ivBeleihungsgebiet;
    }

    /**
     * @param pvBeleihung the beleihung to set
     */
    public void setBeleihungsgebiet(String pvBeleihung) {
        this.ivBeleihungsgebiet = pvBeleihung;
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
     * @return the grundbuch
     */
    public String getGrundbuch() {
        return this.ivGrundbuch;
    }

    /**
     * @param pvGrundbuch the grundbuch to set
     */
    public void setGrundbuch(String pvGrundbuch) {
        this.ivGrundbuch = pvGrundbuch;
    }

    /**
     * @return the band
     */
    public String getBand() {
        return this.ivBand;
    }

    /**
     * @param pvBand the band to set
     */
    public void setBand(String pvBand) {
        this.ivBand = pvBand;
    }

    /**
     * @return the blatt
     */
    public String getBlatt() {
        return this.ivBlatt;
    }

    /**
     * @param pvBlatt the blatt to set
     */
    public void setBlatt(String pvBlatt) {
        this.ivBlatt = pvBlatt;
    }

    /**
     * @return the laufendeNummer
     */
    public String getLaufendeNummer() {
        return this.ivLaufendeNummer;
    }

    /**
     * @param pvLaufendeNummer the laufendeNummer to set
     */
    public void setLaufendeNummer(String pvLaufendeNummer) {
        this.ivLaufendeNummer = pvLaufendeNummer;
    }

    /**
     * @return the verwendungszweck
     */
    public String getVerwendungszweck() {
        return this.ivVerwendungszweck;
    }

    /**
     * @param pvVerwendungszweck the verwendungszweck to set
     */
    public void setVerwendungszweck(String pvVerwendungszweck) {
        this.ivVerwendungszweck = pvVerwendungszweck;
    }

    /**
     * @return the zusatzsicherheit
     */
    public String getZusatzsicherheit() {
        return this.ivZusatzsicherheit;
    }

    /**
     * @param pvZusatzsicherheit the zusatzsicherheit to set
     */
    public void setZusatzsicherheit(String pvZusatzsicherheit) {
        this.ivZusatzsicherheit = pvZusatzsicherheit;
    }

    /**
     * @return the beleihungswertWKZ
     */
    public String getBeleihungswertWKZ() {
        return this.ivBeleihungswertWKZ;
    }

    /**
     * @param pvBeleihungswertWKZ the beleihungswertWKZ to set
     */
    public void setBeleihungswertWKZ(String pvBeleihungswertWKZ) {
        this.ivBeleihungswertWKZ = pvBeleihungswertWKZ;
    }

    /**
     * @return the baujahr
     */
    public String getBaujahr() {
        return this.ivBaujahr;
    }

    /**
     * @param pvBaujahr the baujahr to set
     */
    public void setBaujahr(String pvBaujahr) {
        this.ivBaujahr = pvBaujahr;
    }

    /**
     * @return the beleihungswert
     */
    public String getBeleihungswert() {
        return this.ivBeleihungswert;
    }

    /**
     * @param pvBeleihungswert the beleihungswert to set
     */
    public void setBeleihungswert(String pvBeleihungswert) {
        this.ivBeleihungswert = pvBeleihungswert;
    }

    /**
     * @return the sachwert
     */
    public String getSachwert() {
        return this.ivSachwert;
    }

    /**
     * @param pvSachwert the sachwert to set
     */
    public void setSachwert(String pvSachwert) {
        this.ivSachwert = pvSachwert;
    }

    /**
     * @return the ertragswert
     */
    public String getErtragswert() {
        return this.ivErtragswert;
    }

    /**
     * @param pvErtragswert the ertragswert to set
     */
    public void setErtragswert(String pvErtragswert) {
        this.ivErtragswert = pvErtragswert;
    }

    /**
     * @return the grundschuld
     */
    public String getGrundschuld() {
        return this.ivGrundschuld;
    }

    /**
     * @param pvGrundschuld the grundschuld to set
     */
    public void setGrundschuld(String pvGrundschuld) {
        this.ivGrundschuld = pvGrundschuld;
    }
    
    /**
     * Extrahiert ein Sicherungsobjekt aus einem Darlehen
     * @param pvDarlehen 
     * @return 
     */
    public boolean extractSicherungsobjekt(DarlehenKomplett pvDarlehen, Logger pvLogger)
    {
        if (pvDarlehen.getOBJ() == null)
        {
            pvLogger.info("Sicherungsobjekt --> null");
            return false;
        }
        int helpInteger = (new Integer(pvDarlehen.getOBJ().getsOao())).intValue();
        if (pvDarlehen.getOBJ().getsOp().isEmpty() && helpInteger == 0)
        {
            // Nichts da 
            pvLogger.info("Sicherungsobjekt: Nichts da...");
            return false;
        }
        // Ansonsten die Daten uebernehmen
        this.ivHerkunftDarlehen = "FID";
        this.ivHerkunftSicherheit = "DAR";
        this.ivHerkunftDaten = "VDW";
        
        this.ivObjektnummer = pvDarlehen.getOBJ().getKopf().getsDwhonr();
        this.ivObjektart = pvDarlehen.getOBJ().getsOao();
        this.ivPostleitzahl = pvDarlehen.getOBJ().getsOp();
        this.ivOrt = pvDarlehen.getOBJ().getsOo().trim();
        this.ivStrasse = pvDarlehen.getOBJ().getsOs().trim();
        this.ivBeleihungsgebiet = pvDarlehen.getOBJ().getsObg();
       
        this.ivKundennummer = pvDarlehen.getOBJ().getsOkunr();
        this.ivKontonummer = pvDarlehen.getKTS().getKopf().getsDwhknr();
        pvLogger.info("extractSicherungsobjekt: " + this.ivKontonummer + " Kundennummer: " + ivKundennummer);
   
        if (pvDarlehen.getOBJ().getsOgr().trim().isEmpty())
        {
            this.ivGrundbuch = "Grundbuch";
        }
        else
        {
            this.ivGrundbuch = pvDarlehen.getOBJ().getsOgr().trim();
        }
        
        if (pvDarlehen.getOBJ().getsOba().trim().isEmpty())
        {
            this.ivBand = "-  ";
        }
        else
        {
            this.ivBand = pvDarlehen.getOBJ().getsOba().trim();
        }
        
        if (pvDarlehen.getOBJ().getsObt().trim().isEmpty())
        {
            this.ivBlatt = "Blatt";
        }
        else
        {
            this.ivBlatt = pvDarlehen.getOBJ().getsObt().trim();
        }
        // Es gibt keine 'Laufende Nummer' im Darlehensystem
        this.ivLaufendeNummer = "";
        
        this.ivVerwendungszweck = pvDarlehen.getBAUFI().getsVzw1();
        this.ivZusatzsicherheit = pvDarlehen.getBAUFI().getsVbss();
        this.ivBeleihungswertWKZ = pvDarlehen.getKTS().getsDwiso();
        this.ivBaujahr = pvDarlehen.getBAUFI().getsVbbj();
        this.ivBeleihungswert = pvDarlehen.getBAUFI().getsVbbelw();
        this.ivSachwert = pvDarlehen.getBAUFI().getsVbsach();
        this.ivErtragswert = pvDarlehen.getBAUFI().getsVbbert();
        this.ivGrundschuld = pvDarlehen.getBAUFI().getsVbgs();
        
        BigDecimal lvZuweisungsbetrag = new BigDecimal(0.0);
        INF lvHelpINF = pvDarlehen.getINFZ();
        if (lvHelpINF != null)
        {
            BigDecimal helpDouble = (new BigDecimal(lvHelpINF.getsBdmb()));
            lvZuweisungsbetrag = helpDouble;
            if (pvDarlehen.getREC().getsNkzakpa().equals("A") && helpDouble.doubleValue() < 0.0)
            {
                lvZuweisungsbetrag = lvZuweisungsbetrag.abs();
            }
        }
        
        if (lvZuweisungsbetrag.doubleValue() == 0.0)
        {
            pvLogger.info("Konto " + ivKontonummer + " - Kein SiObj, da Zuweisungsbetrag = 0");
            //System.out.println("Darlehenssystem: " + this.herkunftDarlehen);
            //System.out.println("Sicherheitensystem: " + this.herkunftSicherheit);
            //System.out.println("Datenherkunft: " + this.herkunftDaten);
            //System.out.println("Kundennummer: " + this.kundennummer);
            //System.out.println("Objektnummer: " + this.objektnummer);
            //System.out.println("Kontonummer: " + this.kontonummer);
            
            return false;
        }
 
        return true;
    }
}
