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
     * Konstruktor
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
     * @return the kontonummer
     */
    public String getKontonummer() 
    {
        return this.ivKontonummer;
    }

    /**
     * @param pvKontonummer the kontonummer to set
     */
    public void setKontonummer(String pvKontonummer) 
    {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * @return the passivkontonummer
     */
    public String getPassivkontonummer() 
    {
        return this.ivPassivkontonummer;
    }

    /**
     * @param pvPassivkontonummer the passivkontonummer to set
     */
    public void setPassivkontonummer(String pvPassivkontonummer) 
    {
        this.ivPassivkontonummer = pvPassivkontonummer;
    }

    /**
     * @return the konsortialkontonummer
     */
    public String getKonsortialkontonummer() 
    {
        return this.ivKonsortialkontonummer;
    }

    /**
     * @param pvKonsortialkontonummer the konsortialkontonummer to set
     */
    public void setKonsortialkontonummer(String pvKonsortialkontonummer) 
    {
        this.ivKonsortialkontonummer = pvKonsortialkontonummer;
    }

    /**
     * @return the kundennummer
     */
    public String getKundennummer() 
    {
        return this.ivKundennummer;
    }

    /**
     * @param pvKundennummer the kundennummer to set
     */
    public void setKundennummer(String pvKundennummer) 
    {
        this.ivKundennummer = pvKundennummer;
    }

    /**
     * AktivkontenDaten in einen String schreiben
     * @return 
     */
    public String toString()
    {
        StringBuilder ivHelpText = new StringBuilder();
        ivHelpText.append("Kontonummer: " + ivKontonummer + StringKonverter.lineSeparator);
        ivHelpText.append("Passivkontonummer: " + ivPassivkontonummer + StringKonverter.lineSeparator);
        ivHelpText.append("Konsortialkontonummer: " + ivKonsortialkontonummer + StringKonverter.lineSeparator);
        ivHelpText.append("Kundennummer: " + ivKundennummer + StringKonverter.lineSeparator);
        
        return ivHelpText.toString();
    }
}
