/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.util.HashMap;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;


/**
 * @author tepperc
 *
 */
public class SicherungsobjekteListe extends HashMap<String, Sicherungsobjekt> 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Prueft, ob es ein Sicherungsobjekt zur Objektnummer gibt 
     * @param pvNr Objektnummer
     * @return true - Sicherungsobjekt vorhanden; false - Kein Sicherungsobjekt vorhanden
     */
    public boolean contains(String pvNr)
    {
        /* Sicherungsobjekt lvHelpSObj;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpSObj = this.get(x);
            if (lvHelpSObj.getObjektnummer().equals(pvNr))
            {
              return true;
            }
        }
        return false; */
        return this.containsKey(pvNr);
    }
    
    /**
     * Liefert das Sicherungsobjekt zur Objektnummer
     * @param pvNr Objektnummer
     * @return Sicherungsobjekt zur Objektnummer
     */
    public Sicherungsobjekt getSicherungsobjekt(String pvNr)
    {
        /* Sicherungsobjekt lvHelpSObj;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpSObj = this.get(x);
            if (lvHelpSObj.getObjektnummer().equals(pvNr))
            {
              return lvHelpSObj;
            }
        }
        return null;*/
        return this.get(pvNr);
    }

}