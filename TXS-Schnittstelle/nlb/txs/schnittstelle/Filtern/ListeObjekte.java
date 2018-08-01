/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Filtern;

import java.util.HashMap;

/**
 * @author tepperc
 *
 */
public class ListeObjekte extends HashMap<String, Boolean>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvId 
     * @return 
     * 
     */
    public Boolean getObjekt(String pvId)
    {
        return this.get(pvId);
    }

    /**
     * @param pvId 
     * 
     */
    public void addObjekt(String pvId) 
    {
        this.put(pvId, new Boolean(true));
    }

}
