/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Filtern;

import java.util.ArrayList;

/**
 * @author tepperc
 *
 */
@Deprecated
public class KontoListe extends ArrayList<String>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvNr 
     * @return 
     * 
     */
    public boolean contains(String pvNr)
    {
        String lvHelpKontonummer;
        for (int x = 0; x < this.size();x++)
        {
            lvHelpKontonummer = this.get(x);
            if (lvHelpKontonummer.equals(pvNr))
            {
                 return true;
            }
        }
        return false;
    }
    
    /**
     * @param pvNr 
     * @return 
     * 
     */
    public void remove(String pvNr)
    {
        String lvHelpKontonummer;
        for (int x = 0; x < this.size();x++)
        {
            lvHelpKontonummer = this.get(x);
            if (lvHelpKontonummer.equals(pvNr))
            {
                 this.remove(x);
                 return;
            }
        }
    }

}
