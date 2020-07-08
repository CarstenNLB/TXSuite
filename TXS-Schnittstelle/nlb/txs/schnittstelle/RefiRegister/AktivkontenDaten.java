/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.RefiRegister;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class AktivkontenDaten 
{
    /**
     * (Aktiv)Kontonummer
     */
    private String ivKontonummer;
    
    /**
     * Passivkontonummer
     */
    private String ivPassivkontonummer;
    
    /**
     * Konsortialkontonummer
     */
    private String ivKonsortialkontonummer;
    
    /**
     * Kundennummer zur (Aktiv)Kontonummer
     */
    private String ivKundennummer;

    /**
     * Konstruktor zur Initialisierung der
     */
    public AktivkontenDaten() 
    {
        super();
        this.ivKontonummer = new String();
        this.ivPassivkontonummer = new String();
        this.ivKonsortialkontonummer = new String();
        this.ivKundennummer = new String();
    }

    /**
     * Liefert die Kontonummer
     * @return the kontonummer
     */
    public String getKontonummer() 
    {
        return this.ivKontonummer;
    }

    /**
     * Setzt die Kontonummer
     * @param pvKontonummer the kontonummer to set
     */
    public void setKontonummer(String pvKontonummer) 
    {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * Liefert die Kontonummer des Passivgeschaeft
     * @return the passivkontonummer
     */
    public String getPassivkontonummer() 
    {
        return this.ivPassivkontonummer;
    }

    /**
     * Setzt die Kontonummer des Passivgeschaefts
     * @param pvPassivkontonummer the passivkontonummer to set
     */
    public void setPassivkontonummer(String pvPassivkontonummer) 
    {
        this.ivPassivkontonummer = pvPassivkontonummer;
    }

    /**
     * Liefert die Kontonummer des Konsortialgeschaefts
     * @return the konsortialkontonummer
     */
    public String getKonsortialkontonummer() 
    {
        return this.ivKonsortialkontonummer;
    }

    /**
     * Setzt die Kontonummer des Konsortialgeschaefts
     * @param pvKonsortialkontonummer the konsortialkontonummer to set
     */
    public void setKonsortialkontonummer(String pvKonsortialkontonummer) 
    {
        this.ivKonsortialkontonummer = pvKonsortialkontonummer;
    }

    /**
     * Liefert die Kundennummer
     * @return the kundennummer
     */
    public String getKundennummer() 
    {
        return this.ivKundennummer;
    }

    /**
     * Setzt die Kundennummer
     * @param pvKundennummer the kundennummer to set
     */
    public void setKundennummer(String pvKundennummer) 
    {
        this.ivKundennummer = pvKundennummer;
    }

    /**
     * Schreibt die AktivkontenDaten in einen String
     * @return String mit AktivkontenDaten
     */
    public String toString()
    {
        StringBuilder ivHelpText = new StringBuilder();
        ivHelpText.append("Kontonummer: ");
        ivHelpText.append(ivKontonummer);
        ivHelpText.append(StringKonverter.lineSeparator);
        ivHelpText.append("Passivkontonummer: ");
        ivHelpText.append(ivPassivkontonummer);
        ivHelpText.append(StringKonverter.lineSeparator);
        ivHelpText.append("Konsortialkontonummer: ");
        ivHelpText.append(ivKonsortialkontonummer);
        ivHelpText.append(StringKonverter.lineSeparator);
        ivHelpText.append("Kundennummer: ");
        ivHelpText.append(ivKundennummer);
        ivHelpText.append(StringKonverter.lineSeparator);
        
        return ivHelpText.toString();
    }
}
