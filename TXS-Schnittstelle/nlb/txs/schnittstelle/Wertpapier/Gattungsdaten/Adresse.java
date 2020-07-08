/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier.Gattungsdaten;

/**
 * @author tepperc
 *
 */
public class Adresse 
{
    /**
     * Kundennummer
     */
    private String ivKundennummer;
    
    /**
     * Name
     */
    private String ivName;
    
    /**
     * Strasse
     */
    private String ivStrasse;
    
    /**
     * Postleitzahl
     */
    private String ivPostleitzahl;
    
    /**
     * Ort
     */
    private String ivOrt;

    /**
     * Konstruktor
     */
    public Adresse()
    {
        this.ivKundennummer = new String();
        this.ivName = new String();
        this.ivStrasse = new String();
        this.ivPostleitzahl = new String();
        this.ivOrt = new String();    	
    }
    
    /**
     * Konstruktor mit Parametern
     * @param pvKundennummer
     * @param pvName
     * @param pvStrasse
     * @param pvPostleitzahl
     * @param pvOrt
     */
    public Adresse(String pvKundennummer, String pvName, String pvStrasse, String pvPostleitzahl, String pvOrt) 
    {
        this.ivKundennummer = pvKundennummer;
        this.ivName = pvName;
        this.ivStrasse = pvStrasse;
        this.ivPostleitzahl = pvPostleitzahl;
        this.ivOrt = pvOrt;
    }

    /**
     * Liefert die Kundennummer
     * @return the kundennummer
     */
    public String getKundennummer() {
        return this.ivKundennummer;
    }

    /**
     * Setzt die Kundennummer
     * @param pvKundennummer the kundennummer to set
     */
    public void setKundennummer(String pvKundennummer) {
        this.ivKundennummer = pvKundennummer;
    }

    /**
     * Liefert den Namen
     * @return the name
     */
    public String getName() {
        return this.ivName;
    }

    /**
     * Setzt den Namen
     * @param pvName the name to set
     */
    public void setName(String pvName) {
        this.ivName = pvName;
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
     * Liefert die Adresse als String
     * @return Adresse als String
     */
    public String toString()
    {
    	StringBuilder lvBuilder = new StringBuilder();
    	lvBuilder.append("Kundennummer: " + ivKundennummer);
    	lvBuilder.append("Name: " + ivName);
    	lvBuilder.append("Strasse: " + ivStrasse);
    	lvBuilder.append("Postleitzahl: " + ivPostleitzahl);
    	lvBuilder.append("Ort: " + ivOrt);
    	return lvBuilder.toString();
    }
}
