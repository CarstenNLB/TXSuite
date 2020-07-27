/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.util.HashMap;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;

/**
 * @author tepperc
 *
 */
public class SicherheitenListe extends HashMap<String, Sicherheit>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Prueft, ob es eine Sicherheit zur Objektnummer gibt 
     * @param pvNr Objektnummer
     * @return true - Sicherheit vorhanden; false - Keine Sicherheit vorhanden
     * 
     */
    public boolean contains(String pvNr)
    {
        /* Sicherheit lvHelpSicherheit;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpSicherheit = this.get(x);
            if (lvHelpSicherheit.getObjektnummer().equals(pvNr))
            {
              return true;
            }
        }
        return false; */
        return this.containsKey(pvNr);
        
    }
 
    /**
     * Liefert die Sicherheit zur Objektnummer
     * @param pvNr Objektnummer
     * @return Sicherheit zur Objektnummer
     * 
     */
    public Sicherheit getSicherheit(String pvNr)
    {
        /* Sicherheit lvHelpSicherheit;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpSicherheit = this.get(x);
            if (lvHelpSicherheit.getObjektnummer().equals(pvNr))
            {
              return lvHelpSicherheit;
            }
        }
        return null;
        */
        return this.get(pvNr);
    }

}

