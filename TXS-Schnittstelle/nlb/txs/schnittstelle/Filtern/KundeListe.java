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
     * UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Ueberprueft, ob die Kundennummer bereits existiert.
     * @param pvNr Kundennummer
     * @return true -> Kundennummer existiert; false -> Kundennummer gibt es noch nicht
     * 
     */
    public boolean containsKunde(String pvNr)
    {
         return this.contains(pvNr);
    }
    
    /**
     * Fuegt eine Kundenummer hinzu
     * @param pvNr Kundennummer
     */
    public void addKunde(String pvNr)
    {
        this.add(pvNr);
    }
}
