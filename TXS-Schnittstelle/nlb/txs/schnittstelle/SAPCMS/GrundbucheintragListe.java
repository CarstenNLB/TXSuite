/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS;

import java.util.HashMap;
import java.util.LinkedList;

import nlb.txs.schnittstelle.SAPCMS.Daten.Grundbucheintrag;

/**
 * @author tepperc
 *
 */
public class GrundbucheintragListe extends HashMap<String, LinkedList<Grundbucheintrag>>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvGbe 
     * 
     */
    public void putGrundbucheintrag(Grundbucheintrag pvGbe)
    {     
        LinkedList<Grundbucheintrag> lvHelpListe;
        
        lvHelpListe = get(pvGbe.getSicherheitenID());
        if (lvHelpListe == null)
        {
            lvHelpListe = new LinkedList<Grundbucheintrag>();
            this.put(pvGbe.getSicherheitenID(), lvHelpListe);
        }
        lvHelpListe.add(pvGbe);
    }

    /**
     * @param pvSicherheitID 
     * @return 
     * 
     */
    public LinkedList<Grundbucheintrag> getGrundbucheintraege(String pvSicherheitID)
    {
        return this.get(pvSicherheitID);
    }
    
    /**
     *
     * @param pvGbe 
     * @return 
     * 
     */
    public boolean existsGrundbucheintrag(Grundbucheintrag pvGbe)
    {        
        LinkedList<Grundbucheintrag> lvHelpGbeListe = this.get(pvGbe.getSicherheitenID());
        // Wenn die Liste der Grundbucheintraege leer ist, dann gibt es den 
        // Grundbucheintrag noch nicht
        if (lvHelpGbeListe == null || lvHelpGbeListe.isEmpty())
        {
            return false;
        }
        
        Grundbucheintrag lvHelpGbe;
        boolean lvIdentisch;
        
        for (int x = 0; x < lvHelpGbeListe.size(); x++)
        {
            lvIdentisch = true;
            lvHelpGbe = lvHelpGbeListe.get(x);
            if (!lvHelpGbe.getObjektID().equals(pvGbe.getObjektID()))
            {
                lvIdentisch = false;
            }
            if (!lvHelpGbe.getGrundbuchVon().equals(pvGbe.getGrundbuchVon()))
            {
                lvIdentisch = false;
            }
            if (!lvHelpGbe.getBand().equals(pvGbe.getBand()))
            {
                lvIdentisch = false;
            }
            if (!lvHelpGbe.getBlatt().equals(pvGbe.getBlatt()))
            {
                lvIdentisch = false;
            }
            if (!lvHelpGbe.getLfdNr().equals(pvGbe.getLfdNr()))
            {
                lvIdentisch = false;
            }

            // Identisches Grundbuch gefunden
            if (lvIdentisch)
            {
               lvHelpGbe.addBestandsverzeichnis(pvGbe.getBestandsverzeichnis().get(0));
               return true;
            }
        }
               
        return false;
    }

}
