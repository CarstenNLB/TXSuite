/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten.Daten;

import java.util.LinkedList;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
@Deprecated
public class Grundbucheintrag 
{
    /**
     * Sicherheiten-ID
     */
    private String ivSicherheitenID;
    
    /**
     * Objekt-ID
     */
    private String ivObjektID;
    
    /**
     * Grundbuch-ID 
     */
    private String ivGrundbuchID;
    
    /**
     * Nr. in Abteilung III des Grundbuchs
     */
    private String ivLfdNr;
    
    /**
     * Laufende Nr. des Grundst�cks
     */
    private String ivLfdNrGrundstuecks;
    
    /**
     * Amtsgericht
     */
    private String ivAmtsgericht;
    
    /**
     * Grundbuch von
     */
    private String ivGrundbuchVon;
    
    /**
     * Band
     */
    private String ivBand;
    
    /**
     * Blatt/Heft
     */
    private String ivBlatt;
    
    /**
     * Lage/Gemarkung
     */
    private String ivGemarkung;
    
    /**
     * Fl�che
     */
    private String ivFlaeche;
    
    /**
     * Flur
     */
    private String ivFlur;
    
    /**
     * Flurstueck
     */
    private LinkedList<Bestandsverzeichnis> ivBestandsverzeichnis;

    /**
     * Konstruktor 
     */
    public Grundbucheintrag()
    {
        ivSicherheitenID = new String();
        ivObjektID = new String();
        ivGrundbuchID = new String();
        ivLfdNr = new String();
        ivLfdNrGrundstuecks = new String();
        ivAmtsgericht = new String();
        ivGrundbuchVon = new String();
        ivBand = new String();
        ivBlatt = new String();
        ivGemarkung = new String();
        ivFlaeche = new String();
        ivFlur = new String();
        ivBestandsverzeichnis = new LinkedList<Bestandsverzeichnis>();  
    }
    
    /**
     * @return the sicherheitenID
     */
    public String getSicherheitenID() {
        return this.ivSicherheitenID;
    }

    /**
     * @param pvSicherheitenID the sicherheitenID to set
     */
    public void setSicherheitenID(String pvSicherheitenID) {
        this.ivSicherheitenID = pvSicherheitenID;
    }

    /**
     * @return the objektID
     */
    public String getObjektID() {
        return this.ivObjektID;
    }

    /**
     * @param pvObjektID the objektID to set
     */
    public void setObjektID(String pvObjektID) {
        this.ivObjektID = pvObjektID;
    }

    /**
     * @return the grundbuchID
     */
    public String getGrundbuchID() {
        return this.ivGrundbuchID;
    }

    /**
     * @param pvGrundbuchID the grundbuchID to set
     */
    public void setGrundbuchID(String pvGrundbuchID) {
        this.ivGrundbuchID = pvGrundbuchID;
    }

    /**
     * @return the lfdNr
     */
    public String getLfdNr() {
        return this.ivLfdNr;
    }

    /**
     * @param pvLfdNr the lfdNr to set
     */
    public void setLfdNr(String pvLfdNr) {
        this.ivLfdNr = pvLfdNr;
    }

    /**
     * @return the lfdNrGrundstuecks
     */
    public String getLfdNrGrundstuecks() {
        return this.ivLfdNrGrundstuecks;
    }

    /**
     * @param pvLfdNrGrundstuecks the lfdNrGrundstuecks to set
     */
    public void setLfdNrGrundstuecks(String pvLfdNrGrundstuecks) {
        this.ivLfdNrGrundstuecks = pvLfdNrGrundstuecks;
    }

    /**
     * @return the amtsgericht
     */
    public String getAmtsgericht() {
        return this.ivAmtsgericht;
    }

    /**
     * @param pvAmtsgericht the amtsgericht to set
     */
    public void setAmtsgericht(String pvAmtsgericht) {
        this.ivAmtsgericht = pvAmtsgericht;
    }

    /**
     * @return the grundbuchVon
     */
    public String getGrundbuchVon() {
        return this.ivGrundbuchVon;
    }

    /**
     * @param pvGrundbuchVon the grundbuchVon to set
     */
    public void setGrundbuchVon(String pvGrundbuchVon) {
        this.ivGrundbuchVon = pvGrundbuchVon;
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
     * @return the gemarkung
     */
    public String getGemarkung() {
        return this.ivGemarkung;
    }

    /**
     * @param pvGemarkung the gemarkung to set
     */
    public void setGemarkung(String pvGemarkung) {
        this.ivGemarkung = pvGemarkung;
    }

    /**
     * @return the flaeche
     */
    public String getFlaeche() {
        return this.ivFlaeche;
    }

    /**
     * @param pvFlaeche the flaeche to set
     */
    public void setFlaeche(String pvFlaeche) {
        this.ivFlaeche = pvFlaeche;
    }

    /**
     * @return the flur
     */
    public String getFlur() {
        return this.ivFlur;
    }

    /**
     * @param pvFlur the flur to set
     */
    public void setFlur(String pvFlur) {
        this.ivFlur = pvFlur;
    }

    /**
     * @return 
     *
     */
    public LinkedList<Bestandsverzeichnis> getBestandsverzeichnis() 
    {
        return this.ivBestandsverzeichnis;
    }

    /**
     * @param pvBestandsverzeichnis 
     * 
     */
    public void setBestandsverzeichnis(LinkedList<Bestandsverzeichnis> pvBestandsverzeichnis) 
    {
        this.ivBestandsverzeichnis = pvBestandsverzeichnis;
    }
    
    /**
     * @param pvBestandsverzeichnis
     * 
     */
    public void addBestandsverzeichnis(Bestandsverzeichnis pvBestandsverzeichnis)
    {
        this.ivBestandsverzeichnis.add(pvBestandsverzeichnis);
    }
    
    /**
     * Schreibt den Inhalt des Objektes in einen String
     * @return 
     */
    public String toString()
    {
        String lvOut = new String();
        
        lvOut = "Grundbucheintrag:" + StringKonverter.lineSeparator;
        lvOut = lvOut + "Sicherheiten-ID: " + ivSicherheitenID + StringKonverter.lineSeparator;
        lvOut = lvOut + "Objekt-ID: " + ivObjektID + StringKonverter.lineSeparator;
        lvOut = lvOut + "Grundbuch-ID: " + ivGrundbuchID + StringKonverter.lineSeparator;
        lvOut = lvOut + "Nr. in Abteilung III des Grundbuchs: " + ivLfdNr + StringKonverter.lineSeparator;
        lvOut = lvOut + "Amtsgericht: " + ivAmtsgericht + StringKonverter.lineSeparator;
        lvOut = lvOut + "Grundbuch von: " + ivGrundbuchVon + StringKonverter.lineSeparator;
        lvOut = lvOut + "Band: " + ivBand + StringKonverter.lineSeparator;
        lvOut = lvOut + "Blatt/Heft: " + ivBlatt + StringKonverter.lineSeparator;
        lvOut = lvOut + "Lage/Gemarkung: " + ivGemarkung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Flaeche: " + ivFlaeche + StringKonverter.lineSeparator;
        for (int x = 0; x < ivBestandsverzeichnis.size(); x++)
        {
            lvOut = lvOut + "Bestandsverzeichnis Nr. " + x + StringKonverter.lineSeparator;
            lvOut = lvOut + "Laufende Nr. des Grundstuecks: " + ivBestandsverzeichnis.get(x).getLfdNrGrundstueck() + StringKonverter.lineSeparator;
            lvOut = lvOut + "Flur: " + ivBestandsverzeichnis.get(x).getFlur() + StringKonverter.lineSeparator;
            lvOut = lvOut + "Flurstueck: " + ivBestandsverzeichnis.get(x).getFlurstueck() + StringKonverter.lineSeparator;
        }
        
        return lvOut;
    }
}
