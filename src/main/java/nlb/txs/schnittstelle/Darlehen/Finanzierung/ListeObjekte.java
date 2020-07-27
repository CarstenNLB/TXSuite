/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Finanzierung;

import java.util.HashMap;

/**
 * @author tepperc
 *
 */
public class ListeObjekte extends HashMap<String, ObjektElement>
{
    /**
     * @param pvObjektNr
     * @return 
     * 
     */
    public ObjektElement getObjekt(String pvObjektNr)
    {
        return this.get(pvObjektNr);
    }

    /**
     * @param pvObjektElement
     * 
     */
    public void addObjekt(ObjektElement pvObjektElement) 
    {
        this.put(pvObjektElement.getObjektNr(), pvObjektElement);
    }

}
