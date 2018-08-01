/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS;

import java.util.HashMap;
import java.util.LinkedList;

import nlb.txs.schnittstelle.SAPCMS.Daten.Vermoegensobjekt;

/**
 * @author tepperc
 *
 */
public class VermoegensobjektListe extends HashMap<String, LinkedList<Vermoegensobjekt>>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvVermoegensObj 
     * 
     */
    public void putVermoegensobjekt(Vermoegensobjekt pvVermoegensObj)
    {     
        LinkedList<Vermoegensobjekt> lvHelpListe;
        
        lvHelpListe = get(pvVermoegensObj.getSicherheitenID());
        if (lvHelpListe == null)
        {
            lvHelpListe = new LinkedList<Vermoegensobjekt>();
            this.put(pvVermoegensObj.getSicherheitenID(), lvHelpListe);
        }
        lvHelpListe.add(pvVermoegensObj);
    }

    /**
     * @param pvSicherheitID 
     * @return 
     * 
     */
    public LinkedList<Vermoegensobjekt> getVermoegensobjekte(String pvSicherheitID)
    {
        return this.get(pvSicherheitID); 
    }
}
