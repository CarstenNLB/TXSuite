/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.DeutscheHypo2TXS;

/**
 * @author tepperc
 *
 */
@Deprecated
public class GrundbuchDaten 
{
    /**
     * Amtsgericht
     */
    private String ivAmtsgericht;
    
    /**
     * Grundbuchart
     */
    private String ivGrundbuchart;
    
    /**
     * Grundbuch von
     */
    private String ivGrundbuchVon;
    
    /**
     * Band
     */
    private String ivBand;
    
    /**
     * Blatt
     */
    private String ivBlatt;
    
    /**
     * Gemarkung
     */
    private String ivGemarkung;
    
    /**
     * Flur
     */
    private String ivFlur;
    
    /**
     * Flurstueck 
     */
    private String ivFlurstueck;
    
    /**
     * Nr. Abteilung III
     */
    private String ivAbteilungsnummer;
    
    /**
     * Strasse 
     */
    private String ivStrasse;
    
    /**
     * Hausnummer
     */
    private String ivHausnummer;
    
    /**
     * Postleitzahl
     */
    private String ivPostleitzahl;
    
   /**
    * Ort
    */
    private String ivOrt;
    
    /**
     * Adresszusatz
     */
    private String ivAdresszusatz;

    /**
     * Konstruktor
     */
    public GrundbuchDaten()
    {
        this.ivAmtsgericht = new String();
        this.ivGrundbuchart = new String();
        this.ivGrundbuchVon = new String();
        this.ivBand = new String();
        this.ivBlatt = new String();
        this.ivGemarkung = new String();
        this.ivFlur = new String();
        this.ivFlurstueck = new String();
        this.ivAbteilungsnummer = new String();
        this.ivStrasse = new String();
        this.ivHausnummer = new String();
        this.ivPostleitzahl = new String();
        this.ivOrt = new String();
        this.ivAdresszusatz = new String();
    }

    /**
     * Liefert das Amtsgericht
     * @return the amtsgericht
     */
    public String getAmtsgericht() {
        return this.ivAmtsgericht;
    }

    /**
     * Setzt das Amtsgericht
     * @param pvAmtsgericht the amtsgericht to set
     */
    public void setAmtsgericht(String pvAmtsgericht) {
        this.ivAmtsgericht = pvAmtsgericht;
    }

    /**
     * Liefert die Grundbuchart
     * @return the grundbuchart
     */
    public String getGrundbuchart() {
        return this.ivGrundbuchart;
    }

    /**
     * Setzt die Grundbuchart
     * @param pvGrundbuchart the grundbuchart to set
     */
    public void setGrundbuchart(String pvGrundbuchart) {
        this.ivGrundbuchart = pvGrundbuchart;
    }

    /**
     * Liefert das GrundbuchVon
     * @return the grundbuchVon
     */
    public String getGrundbuchVon() {
        return this.ivGrundbuchVon;
    }

    /**
     * Setzt das GrundbuchVon
     * @param pvGrundbuchVon the grundbuchVon to set
     */
    public void setGrundbuchVon(String pvGrundbuchVon) {
        this.ivGrundbuchVon = pvGrundbuchVon;
    }

    /**
     * Liefert das Band
     * @return the band
     */
    public String getBand() {
        return this.ivBand;
    }

    /**
     * Setzt das Band
     * @param pvBand the band to set
     */
    public void setBand(String pvBand) {
        this.ivBand = pvBand;
    }

    /**
     * Liefert das Blatt
     * @return the blatt
     */
    public String getBlatt() {
        return this.ivBlatt;
    }

    /**
     * Setzt das Blatt
     * @param pvBlatt the blatt to set
     */
    public void setBlatt(String pvBlatt) {
        this.ivBlatt = pvBlatt.replace("#", ";");
    }

    /**
     * Liefert die Gemarkung
     * @return the gemarkung
     */
    public String getGemarkung() {
        return this.ivGemarkung;
    }

    /**
     * Setzt die Gemarkung
     * @param pvGemarkung the gemarkung to set
     */
    public void setGemarkung(String pvGemarkung) {
        this.ivGemarkung = pvGemarkung;
    }

    /**
     * Liefert den Flur
     * @return the flur
     */
    public String getFlur() {
        return this.ivFlur;
    }

    /**
     * Setzt den Flur
     * @param pvFlur the flur to set
     */
    public void setFlur(String pvFlur) {
        this.ivFlur = pvFlur;
    }

    /**
     * Liefert das Flurstueck
     * @return the flurstueck
     */
    public String getFlurstueck() {
        return this.ivFlurstueck;
    }

    /**
     * Setzt das Flurstueck
     * @param pvFlurstueck the flurstueck to set
     */
    public void setFlurstueck(String pvFlurstueck) {
        this.ivFlurstueck = pvFlurstueck;
    }

    /**
     * Liefert die Abteilungsnummer
     * @return the abteilungsnummer
     */
    public String getAbteilungsnummer() {
        return this.ivAbteilungsnummer;
    }

    /**
     * Setzt die Abteilungsnummer
     * @param pvAbteilungsnummer the abteilungsnummer to set
     */
    public void setAbteilungsnummer(String pvAbteilungsnummer) {
        this.ivAbteilungsnummer = pvAbteilungsnummer;
    }

    /**
     * Liefert die Strasse
     * @return the strasse
     */
    public String getStrasse() {
        return this.ivStrasse;
    }

    /**
     * Setzt die Strasse
     * @param pvStrasse the strasse to set
     */
    public void setStrasse(String pvStrasse) {
        this.ivStrasse = pvStrasse;
    }

    /**
     * Liefert die Hausnummer
     * @return the hausnummer
     */
    public String getHausnummer() {
        return this.ivHausnummer;
    }

    /**
     * Setzt die Hausnummer
     * @param pvHausnummer the hausnummer to set
     */
    public void setHausnummer(String pvHausnummer) {
        this.ivHausnummer = pvHausnummer;
    }

    /**
     * Liefert die Postleitzahl
     * @return the postleitzahl
     */
    public String getPostleitzahl() {
        return this.ivPostleitzahl;
    }

    /**
     * Setzt die Postleitzahl
     * @param pvPostleitzahl the postleitzahl to set
     */
    public void setPostleitzahl(String pvPostleitzahl) {
        this.ivPostleitzahl = pvPostleitzahl;
    }

    /**
     * Liefert den Ort
     * @return the ort
     */
    public String getOrt() {
        return this.ivOrt;
    }

    /**
     * Setzt den Ort
     * @param pvOrt the ort to set
     */
    public void setOrt(String pvOrt) {
        this.ivOrt = pvOrt;
    }

    /**
     * Liefert den Adresszusatz
     * @return the adresszusatz
     */
    public String getAdresszusatz() {
        return this.ivAdresszusatz;
    }

    /**
     * Setzt den Adresszusatz
     * @param pvAdresszusatz the adresszusatz to set
     */
    public void setAdresszusatz(String pvAdresszusatz) {
        this.ivAdresszusatz = pvAdresszusatz;
    }  
    
    /**
     * Setzt einen Wert der Grundbuch-Daten
     * @param pvLfd 
     * @param pvWert 
     */
    public void setzeWert(int pvLfd, String pvWert)
    {
        System.out.println("GrundbuchDaten - setzeWert - pvLfd: " + pvLfd + " pvWert: " + pvWert);
        switch (pvLfd)
        {
            case 0:
                //System.out.println("Amtsgericht: " + pvWert + " Laenge: " + pvWert.length());
                this.setAmtsgericht(pvWert);
                break;
            case 1:
                this.setGrundbuchart(pvWert);
                break;
            case 2:
                this.setGrundbuchVon(pvWert);
                break;
            case 3:
                this.setBand(pvWert);
                break;
            case 4:
                this.setBlatt(pvWert);
                break;
            case 5:
                this.setGemarkung(pvWert);
                break;
            case 6:
                this.setFlur(pvWert);
                break;
            case 7:
                this.setFlurstueck(pvWert);
                break;
            case 8:
                this.setAbteilungsnummer(pvWert);
                break;
            case 9:
                this.setStrasse(pvWert);
                break;
            case 10:
                this.setHausnummer(pvWert);
                break;
            case 11:
                this.setPostleitzahl(pvWert);
                break;
            case 12:
                this.setOrt(pvWert);
                break;
            case 13:
                this.setAdresszusatz(pvWert);
                break;
        }
    }

}
