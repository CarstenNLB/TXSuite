/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Filtern;

/**
 * @author tepperc
 *
 */
@Deprecated
public class Kunde
{
    /**
     * Kundennummer
     */
    private String ivKundennummer;
        
    /**
     * Konstruktor
     * @param pvNr Kundennummer
     */
    public Kunde(String pvNr)
    {
        this.ivKundennummer = pvNr;
    }
    
    /**
     * Liefert die Kundennummer
     * @return Kundennummer
     */
    public String getKundennummer()
    {
        return this.ivKundennummer;
    }
    
    /**
     * Setzt die Kundennummer
     * @param pvNr 
     */
    public void setKundennummer(String pvNr)
    {
        this.ivKundennummer = pvNr;
    }
    }
