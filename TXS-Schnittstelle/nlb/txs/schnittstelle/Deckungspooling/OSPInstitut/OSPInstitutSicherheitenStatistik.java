/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.OSPInstitut;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
@Deprecated
public class OSPInstitutSicherheitenStatistik 
{
    /**
     * Die Anzahl der gelieferten Sicherheiten
     */
    private int ivAnzahlSicherheiten;
    
    /**
     * Die Anzahl der gelieferten Vermoegensobjekte
     */
    private int ivAnzahlVermoegensobjekte;
    
    /**
     * Die Anzahl der gelieferten Grundbuecher
     */
    private int ivAnzahlGrundbuecher;
    
    /**
     * Konstruktor
     * Setzt die Anzahl der Sicherheiten, Vermoegensobjekte und Grundbuecher auf 0
     */
    public OSPInstitutSicherheitenStatistik()
    {
        ivAnzahlSicherheiten = 0;
        ivAnzahlVermoegensobjekte = 0;
        ivAnzahlGrundbuecher = 0;
    }

    /**
     * Liefert die Anzahl der Sicherheiten
     * @return the anzahlSicherheiten
     */
    public int getAnzahlSicherheiten() 
    {
        return this.ivAnzahlSicherheiten;
    }

    /**
     * Setzt die Anzahl der Sicherheiten
     * @param pvAnzahlSicherheiten the anzahlSicherheiten to set
     */
    public void setAnzahlSicherheiten(int pvAnzahlSicherheiten) {
        this.ivAnzahlSicherheiten = pvAnzahlSicherheiten;
    }

    /**
     * Liefert die Anzahl der Vermoegensobjekte
     * @return the anzahlVermoegensobjekte
     */
    public int getAnzahlVermoegensobjekte() {
        return this.ivAnzahlVermoegensobjekte;
    }

    /**
     * Setzt die Anzahl der Vermoegensobjekte
     * @param pvAnzahlVermoegensobjekte the anzahlVermoegensobjekte to set
     */
    public void setAnzahlVermoegensobjekte(int pvAnzahlVermoegensobjekte) {
        this.ivAnzahlVermoegensobjekte = pvAnzahlVermoegensobjekte;
    }

    /**
     * Liefert die Anzahl der Grundbuecher
     * @return the anzahlGrundbuecher
     */
    public int getAnzahlGrundbuecher() {
        return this.ivAnzahlGrundbuecher;
    }

    /**
     * Setzt die Anzahl der Grundbuecher
     * @param pvAnzahlGrundbuecher the anzahlGrundbuecher to set
     */
    public void setAnzahlGrundbuecher(int pvAnzahlGrundbuecher) {
        this.ivAnzahlGrundbuecher = pvAnzahlGrundbuecher;
    }

    /**
     * Liefert die Anzahl an Sicherhieten, Vermoegensobjekte und Grundbuecher als String
     * @return 
     */
    public String toString()
    {
        StringBuilder lvHelpString = new StringBuilder();
        
        lvHelpString.append("Start Sicherheiten Statistik:" + StringKonverter.lineSeparator);
        lvHelpString.append("Anzahl Sicherheiten: " + ivAnzahlSicherheiten + StringKonverter.lineSeparator);
        lvHelpString.append("Anzahl Vermoegensobjekte: " + ivAnzahlVermoegensobjekte + StringKonverter.lineSeparator);
        lvHelpString.append("Anzahl Grundbuecher: " + ivAnzahlGrundbuecher + StringKonverter.lineSeparator);
        lvHelpString.append("Ende Sicherheiten Statistik:" + StringKonverter.lineSeparator);
        
        return lvHelpString.toString();
    }

}
