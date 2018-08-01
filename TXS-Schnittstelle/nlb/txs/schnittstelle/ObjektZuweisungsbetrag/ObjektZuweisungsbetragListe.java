/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.ObjektZuweisungsbetrag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class ObjektZuweisungsbetragListe extends HashMap<String, ObjektZuweisungsbetrag>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvObjektnummer 
     * @param pvZuweisungsbetrag 
     * 
     */
    public void addObjektZuweisungsbetrag(String pvObjektnummer, String pvZuweisungsbetrag)
    {
        //System.out.println("Objektnummer: " + pvObjektnummer);
        if (pvZuweisungsbetrag.startsWith("-"))
            pvZuweisungsbetrag = pvZuweisungsbetrag.substring(1);
        //System.out.println("Zuweisungsbetrag: " + pvZuweisungsbetrag);
        if (this.contains(pvObjektnummer))
        {
            getObjektZuweisungsbetrag(pvObjektnummer).addZuweisungsbetrag(StringKonverter.convertString2BigDecimal(pvZuweisungsbetrag));
        }
        else
        {
            this.put(pvObjektnummer, new ObjektZuweisungsbetrag(pvObjektnummer, 
                     StringKonverter.convertString2BigDecimal(pvZuweisungsbetrag)));
        }
        //System.out.println("Summe: " + this.get(pvObjektnummer));
    }
    
    /**
     * @param pvNr 
     * @return 
     * 
     */
    public boolean contains(String pvNr)
    {       
        return this.containsKey(pvNr);
    }

    /**
     * @param pvNr 
     * @return 
     * 
     */
    public ObjektZuweisungsbetrag getObjektZuweisungsbetrag(String pvNr)
    {
        return this.get(pvNr);
    }

   
    /**
     * @param pvOozw 
     * 
     */
    public void printObjektZuweisungsbetragListe(OutputObjektZuweisungsbetrag pvOozw)
    {   
        Collection<ObjektZuweisungsbetrag> lvObjekte = this.values();
        Iterator<ObjektZuweisungsbetrag> lvIterObjekte = lvObjekte.iterator();
        while (lvIterObjekte.hasNext())
        {
            ObjektZuweisungsbetrag lvObjekt = lvIterObjekte.next();
            pvOozw.printObjektZuweisungsbetrag(lvObjekt);
        }
    }
}
