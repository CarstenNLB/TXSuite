/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS;

import java.util.HashMap;
import java.util.LinkedList;

import nlb.txs.schnittstelle.SAPCMS.Daten.Geschaeftswert;

/**
 * @author tepperc
 *
 */
public class GeschaeftswertListe extends HashMap<String, LinkedList<Geschaeftswert>>
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvGw 
     * 
     */
    public void putGeschaeftswert(Geschaeftswert pvGw)
    {     
        LinkedList<Geschaeftswert> lvHelpListe;
        
        lvHelpListe = get(pvGw.getGeschaeftswertID());
        if (lvHelpListe == null)
        {
            lvHelpListe = new LinkedList<Geschaeftswert>();
            this.put(pvGw.getGeschaeftswertID(), lvHelpListe);
        }
        lvHelpListe.add(pvGw);
    }

    /**
     * @param pvKontonummer 
     * @return 
     * 
     */
    public LinkedList<Geschaeftswert> getGeschaeftswert(String pvKontonummer)
    {
        return get(pvKontonummer); 
    }
}
