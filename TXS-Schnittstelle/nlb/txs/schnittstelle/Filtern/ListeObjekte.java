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
     * UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Liefert den Boolean-Wert (True), wenn die ID des Objekts in der Liste enthalten ist. Ansonsten wird der Boolean-Wert (False) geliefert.
     * @param pvId ID des Objekts
     * @return Boolean-Wert (True) -> ID des Objekts vorhanden; Boolean-Wert (False) -> ID des Objekts nicht vorhanden
     * 
     */
    public Boolean getObjekt(String pvId)
    {
        return this.get(pvId);
    }

    /**
     * Fuegt eine ID eines Objekts hinzu
     * @param pvId ID des Objekts
     */
    public void addObjekt(String pvId) 
    {
        this.put(pvId, new Boolean(true));
    }

}
