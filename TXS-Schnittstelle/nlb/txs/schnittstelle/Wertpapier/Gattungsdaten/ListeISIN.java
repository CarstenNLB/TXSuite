/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier.Gattungsdaten;

import java.util.HashSet;

/**
 * @author tepperc
 *
 */
public class ListeISIN extends HashSet<String>
{
 
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Ueberprueft, ob die ISIN schon existiert.
     * @param pvISIN
     * @return 
     * 
     */
    public boolean containsISIN(String pvISIN)
    {
        return this.contains(pvISIN);
    }
        
    /**
     * Fuegt eine ISIN hinzu
     * @param pvISIN
     */
    public void addISIN(String pvISIN)
    {
        this.add(pvISIN);
    }
}
