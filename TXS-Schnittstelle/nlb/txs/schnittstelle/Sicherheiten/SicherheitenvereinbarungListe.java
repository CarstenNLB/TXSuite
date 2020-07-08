/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten;

import java.util.HashMap;

import nlb.txs.schnittstelle.Sicherheiten.Daten.Sicherheitenvereinbarung;

/**
 * @author tepperc
 *
 */
@Deprecated
public class SicherheitenvereinbarungListe extends HashMap<String, Sicherheitenvereinbarung>
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvSicherheitenID 
     * @return 
     * 
     */
    public Sicherheitenvereinbarung getSicherheitenvereinbarung(String pvSicherheitenID)
    {
        //Sicherheitenvereinbarung result = new Sicherheitenvereinbarung();
        //Sicherheitenvereinbarung shvb = null;
        //for (int x = 0; x < this.size(); x++)
        //{
        //    shvb = (Sicherheitenvereinbarung) this.get(x);
        //    if (shvb.getSicherheitenID().equals(sicherheitenID))
        //    {
        //        return shvb;
        //    }
        //}

        return this.get(pvSicherheitenID);
    }

    /**
     * @param pvSicherheitenvereinbarung
     */
    public void addSicherheitenvereinbarung(Sicherheitenvereinbarung pvSicherheitenvereinbarung) 
    {
        this.put(pvSicherheitenvereinbarung.getSicherheitenID(), pvSicherheitenvereinbarung);
    }

}
