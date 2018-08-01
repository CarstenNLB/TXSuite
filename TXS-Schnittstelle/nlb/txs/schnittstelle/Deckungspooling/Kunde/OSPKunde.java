/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.Kunde;

import java.util.LinkedList;

/**
 * @author tepperc
 *
 */
public class OSPKunde 
{
    
    /**
     * Kundennummer/Personennummer
     */
    private String ivKundennr;
    
    /**
     * Vorname
     */
    private String ivVorname;
    
    /**
     * Nachname
     */
    private String ivNachname;
    
    /**
     * Erweiterung
     */
    private String ivErweiterung;
    
    /**
     * Kusyma
     */
    private String ivKusyma;
    
    /**
     * Rolle
     */
    private String ivRolle;
    
    /**
     * Beruf
     */
    private String ivBeruf;
 
    /**
     * Liste der DarKa-Kontonummern
     */
    private LinkedList<String> ivListeKontonummern;
    
    /**
     * Strasse 
     */
    private String ivStrasse;
    
    /**
     * Hausnummer
     */
    private String ivHausnr;
    
    /**
     * Postleitzahl
     */
    private String ivPlz;
    
    /**
     * Ort
     */
    private String ivOrt;
    
    /**
     * Land
     */
    private String ivLand;
    
    /**
     * Gebiet
     */
    private String ivGebiet;

    /**
     * Konstruktor 
     */
    public OSPKunde() 
    {
        this.ivKundennr = new String();
        this.ivVorname = new String();
        this.ivNachname = new String();
        this.ivErweiterung = new String();
        this.ivKusyma = new String();
        this.ivRolle = new String();
        this.ivBeruf = new String();
        this.ivListeKontonummern = new LinkedList<String>();
        this.ivStrasse = new String();
        this.ivHausnr = new String();
        this.ivPlz = new String();
        this.ivOrt = new String();
        this.ivLand = new String();
        this.ivGebiet = new String();
    }

    /**
     * @return the kundennr
     */
    public String getKundennr() {
        return this.ivKundennr;
    }

    /**
     * @param pvKundennr the kundennr to set
     */
    public void setKundennr(String pvKundennr) {
        this.ivKundennr = pvKundennr;
    }

    /**
     * @return the vorname
     */
    public String getVorname() {
        return this.ivVorname;
    }

    /**
     * @param pvVorname the vorname to set
     */
    public void setVorname(String pvVorname) {
        this.ivVorname = pvVorname;
    }

    /**
     * @return the nachname
     */
    public String getNachname() {
        return this.ivNachname;
    }

    /**
     * @param pvNachname the nachname to set
     */
    public void setNachname(String pvNachname) {
        this.ivNachname = pvNachname;
    }

    /**
     * @return the erweiterung
     */
    public String getErweiterung() {
        return this.ivErweiterung;
    }

    /**
     * @param pvErweiterung the erweiterung to set
     */
    public void setErweiterung(String pvErweiterung) {
        this.ivErweiterung = pvErweiterung;
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
     * @return the rolle
     */
    public String getRolle() {
        return this.ivRolle;
    }

    /**
     * @param pvRolle the rolle to set
     */
    public void setRolle(String pvRolle) {
        this.ivRolle = pvRolle;
    }

    /**
     * @return the beruf
     */
    public String getBeruf() {
        return this.ivBeruf;
    }

    /**
     * @param pvBeruf the beruf to set
     */
    public void setBeruf(String pvBeruf) {
        this.ivBeruf = pvBeruf;
    }

    /**
     * @return the listeKontonummern
     */
    public LinkedList<String> getListeKontonummern() {
        return this.ivListeKontonummern;
    }

    /**
     * @param pvListeKontonummern the listeKontonummern to set
     */
    public void setListeKontonummern(LinkedList<String> pvListeKontonummern) {
        this.ivListeKontonummern = pvListeKontonummern;
    }

    /**
     * @return the str
     */
    public String getStr() {
        return this.ivStrasse;
    }

    /**
     * @param pvStr the str to set
     */
    public void setStr(String pvStr) {
        this.ivStrasse = pvStr;
    }

    /**
     * @return the hausnr
     */
    public String getHausnr() {
        return this.ivHausnr;
    }

    /**
     * @param pvHausnr the hausnr to set
     */
    public void setHausnr(String pvHausnr) {
        this.ivHausnr = pvHausnr;
    }

    /**
     * @return the plz
     */
    public String getPlz() {
        return this.ivPlz;
    }

    /**
     * @param pvPlz the plz to set
     */
    public void setPlz(String pvPlz) {
        this.ivPlz = pvPlz;
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
     * @return the land
     */
    public String getLand() {
        return this.ivLand;
    }

    /**
     * @param pvLand the land to set
     */
    public void setLand(String pvLand) {
        this.ivLand = pvLand;
    }

    /**
     * @return the gebiet
     */
    public String getGebiet() {
        return this.ivGebiet;
    }

    /**
     * @param pvGebiet the gebiet to set
     */
    public void setGebiet(String pvGebiet) {
        this.ivGebiet = pvGebiet;
    }
    
    /**
     * @param pvKontonummer 
     * @return 
     * 
     */
    public boolean containsKontonummer(String pvKontonummer)
    {
        boolean lvExists = false;
        
        String lvKto;
        for (int x = 0; x < ivListeKontonummern.size(); x++)
        {
            lvKto = ivListeKontonummern.get(x);
            if (lvKto.equals(pvKontonummer))
            {
                lvExists = true;
            }
        }
        return lvExists;
    }
    
    /**
     * @return 
     * 
     */
    public String toString()
    {
        String helpString;
        helpString = "Kunde:\n";
        helpString = helpString + "Kundennummer: " + ivKundennr + "\n";
        helpString = helpString + "Vorname: " + ivVorname + "\n";
        helpString = helpString + "Nachname: " + ivNachname + "\n";
        helpString = helpString + "Erweiterung: " + ivErweiterung + "\n";
        helpString = helpString + "Kusyma: " + ivKusyma + "\n";
        helpString = helpString + "Rolle: " + ivRolle + "\n";
        helpString = helpString + "Beruf: " + ivBeruf + "\n";
        helpString = helpString + "Kontonummern:\n";
        for (int x = 0; x < ivListeKontonummern.size(); x++)
        {
           helpString = helpString + ivListeKontonummern.get(x) + "\n";
        }
        helpString = helpString + "Strasse: " + ivStrasse + "\n";
        helpString = helpString + "Hausnummer: " + ivHausnr + "\n";
        helpString = helpString + "Postleitzahl: " + ivPlz + "\n";
        helpString = helpString + "Ort: " + ivOrt + "\n";
        helpString = helpString + "Land: " + ivLand + "\n";
        helpString = helpString + "Gebiet: " + ivGebiet + "\n";
        
        return helpString;
    }
}
