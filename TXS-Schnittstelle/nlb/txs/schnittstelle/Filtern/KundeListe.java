/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Filtern;

import java.util.HashSet;

/**
 * @author tepperc
 *
 */
public class KundeListe extends HashSet<String>
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Überprüft, ob die Kundennummer schon existiert.
     * @param pvNr 
     * @return 
     * 
     */
    public boolean containsKunde(String pvNr)
    {
       /* String lvHelpKundennummer;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpKundennummer = (String) this.get(x);
            if (lvHelpKundennummer.equals(pvNr))
            {
                return true;
            }
        }
        return false; */
        return this.contains(pvNr);
    }
    
    /**
     * Fügt eine Kundenummer hinzu
     * @param pvNr 
     */
    public void addKunde(String pvNr)
    {
        this.add(pvNr);
    }
}
