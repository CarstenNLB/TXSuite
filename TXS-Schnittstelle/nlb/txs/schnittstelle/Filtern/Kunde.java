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
public class Kunde 
{
    /**
     *   
     */
    private String ivKundennummer;
        
    /**
     * 
     * @param pvNr
     */
    public Kunde(String pvNr)
    {
        this.ivKundennummer = pvNr;
    }
    
    /**
     * Liefert eine Kundennummer
     * @return 
     */
    public String getKundennummer()
    {
        return this.ivKundennummer;
    }
    
    /**
     * Setzt die Kontonummer
     * @param pvNr 
     */
    public void setKundennummer(String pvNr)
    {
        this.ivKundennummer = pvNr;
    }
    }
