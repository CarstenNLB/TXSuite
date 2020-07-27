/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten.Extrakt;

import nlb.txs.schnittstelle.Darlehen.Daten.DarlehenKomplett;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.INF;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class Sicherheit 
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
     * Kundennummer
     */
    private String ivKundennummer;
    
    /**
     * Objektnummer
     */
    private String ivObjektnummer;
    
    /**
     * Kontonummer
     */
    private String ivKontonummer;
    
    /**
     * Sicherheitenschluessel
     */
    private String ivSicherheitenschluessel;
    
    /**
     * INF-SICHV-Infotyp
     */
    private String ivInfotyp;
    
    /**
     * INF-SICHV-Buchungsschluessel
     */
    // Evt. double?
    private String ivBuchungsschluessel;
    
    /**
     * INF-SICHV-Zuweisungsbetrag
     */
    // Evt. double?
    private String ivZuweisungsbetrag;
    
    /**
     * BAUFI - Grundschuld (EUR)
     */
    // Evt. double?
    private String ivGrundschuld;
    
    /**
     * Nominalwert Gesamt(EUR)
     * BAUFI - Grundschuld (EUR)
     */
    // Evt. double?
    private String ivNominalwert;
    
    /**
     * Verfuegungsbetrag
     * KTS - Ursprungskapital
     */
    private String ivVerfuegungsbetrag;
    
    /**
     * Fremde Rechte 
     * Wird nicht verwendet 
     */
    private String ivFremdeRechte;
    
    /**
     * Eigene Rechte
     * Wird nicht verwendet
     */
    private String ivEigeneRechte;

    /**
     */
    public Sicherheit()
    {
        this.ivHerkunftDarlehen = new String();
        this.ivHerkunftSicherheit = new String();
        this.ivHerkunftDaten = new String();
        this.ivKundennummer = new String();
        this.ivObjektnummer = new String();
        this.ivKontonummer = new String();
        this.ivSicherheitenschluessel = new String();
        this.ivInfotyp = new String();
        this.ivBuchungsschluessel = new String();
        this.ivZuweisungsbetrag = new String();
        this.ivGrundschuld = new String();
        this.ivNominalwert = new String();
        this.ivVerfuegungsbetrag = new String();
        this.ivFremdeRechte = new String();
        this.ivEigeneRechte = new String();
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
     * @return the sicherheitenschluessel
     */
    public String getSicherheitenschluessel() {
        return this.ivSicherheitenschluessel;
    }
    
    /**
     * @param pvSicherheitenschluessel 
     */
    public void setSicherheitenschluessel(String pvSicherheitenschluessel) {
        this.ivSicherheitenschluessel = pvSicherheitenschluessel;
    }
    
    /**
     * @return the infotyp
     */
    public String getInfotyp() {
        return this.ivInfotyp;
    }
    
    /**
     * @param pvInfotyp the infotyp to set
     */
    public void setInfotyp(String pvInfotyp) {
        this.ivInfotyp = pvInfotyp;
    }
    
    /**
     * @return the buchungsschluessel
     */
    public String getBuchungsschluessel() {
        return this.ivBuchungsschluessel;
    }
    
    /**
     * @param pvBuchungsschluessel the buchungsschluessel to set
     */
    public void setBuchungsschluessel(String pvBuchungsschluessel) {
        this.ivBuchungsschluessel = pvBuchungsschluessel;
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
     * @return the fremdeRechte
     */
    public String getFremdeRechte() {
        return this.ivFremdeRechte;
    }
    
    /**
     * @param pvFremdeRechte the fremdeRechte to set
     */
    public void setFremdeRechte(String pvFremdeRechte) {
        this.ivFremdeRechte = pvFremdeRechte;
    }
    
    /**
     * @return the eigeneRechte
     */
    public String getEigeneRechte() {
        return this.ivEigeneRechte;
    }
    
    /**
     * @param pvEigeneRechte the eigeneRechte to set
     */
    public void setEigeneRechte(String pvEigeneRechte) {
        this.ivEigeneRechte = pvEigeneRechte;
    };

    /**
     * Extrahiert eine Sicherheit aus einem Darlehen 
     * @param pvDdarlehen 
     * @return 
     */
    public boolean extractSicherheit(DarlehenKomplett pvDdarlehen)
    {
        this.ivHerkunftDarlehen = "FID";
        this.ivHerkunftSicherheit = "DAR";
        this.ivHerkunftDaten = "VDW";
        
        this.ivKundennummer = pvDdarlehen.getOBJ().getsOkunr();
        this.ivKontonummer = pvDdarlehen.getKTS().getKopf().getsDwhknr();
        this.ivObjektnummer = pvDdarlehen.getOBJ().getKopf().getsDwhonr();
        this.ivSicherheitenschluessel = pvDdarlehen.getKTS().getsDsi();
        
        INF lvHelpINF = pvDdarlehen.getINFV();
        if (lvHelpINF != null)
        {
           // Etwas vorhanden 
           //System.out.println("INFV:\n" + helpINF.toString());
            this.ivInfotyp = lvHelpINF.getsBbart();
            if (!lvHelpINF.getsBb().trim().isEmpty())
           {
                this.ivBuchungsschluessel = lvHelpINF.getsBb();
           }
        }
        
        lvHelpINF = pvDdarlehen.getINFZ();
        if (lvHelpINF != null)
        {
            // Etwas vorhanden
            //System.out.println("INFZ:\n" + helpINF.toString());
            this.ivZuweisungsbetrag = lvHelpINF.getsBdmb();
            if (pvDdarlehen.getREC().getsNkzakpa().equals("A") && lvHelpINF.getsBdmb().startsWith("-"))
            {
                this.ivZuweisungsbetrag = this.ivZuweisungsbetrag.substring(1);
            }
        }
        // Falls der Zuweisungsbetrag 0.0 ist, dann Sicherheit nicht verwenden
        if (!this.ivZuweisungsbetrag.isEmpty())
        {
            if (StringKonverter.convertString2Double(this.ivZuweisungsbetrag) == 0.0)
          {
            System.out.println("Betrag 0 bei Sicherheit");
            //System.out.println("Darlehenssystem: " + this.herkunftDarlehen);
            //System.out.println("Sicherheitensystem: " + this.herkunftSicherheit);
            //System.out.println("Datenherkunft: " + this.herkunftDaten);
            //System.out.println("Kundennummer: " + this.kundennummer);
            //System.out.println("Objektnummer: " + this.objektnummer);
            //System.out.println("Kontonummer: " + this.kontonummer);
            
            return false;
          }
        }
        
        this.ivGrundschuld = pvDdarlehen.getBAUFI().getsVbgs();
        // Grundschuld -> Nominal- und Verfuegungsbetrag
        this.ivNominalwert = pvDdarlehen.getBAUFI().getsVbgs();
        this.ivVerfuegungsbetrag = this.ivZuweisungsbetrag;
        
        return true;
    }
}
