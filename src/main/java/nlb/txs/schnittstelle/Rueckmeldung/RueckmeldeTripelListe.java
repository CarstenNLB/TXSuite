/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

import java.util.Hashtable;

/**
 * @author tepperc
 *
 */
public class RueckmeldeTripelListe extends Hashtable<String, RueckmeldeTripel> 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvRueckmeldeTripel 
     * @return 
     * 
     */
    public boolean contains(RueckmeldeTripel pvRueckmeldeTripel)
    {
        RueckmeldeTripel lvHelpRueckmeldeTripel;
        //for (int x = 0; x < this.size(); x++)
        //{
        lvHelpRueckmeldeTripel = this.get(pvRueckmeldeTripel.getGeschaeftswertID() + pvRueckmeldeTripel.getSicherheitenID() + pvRueckmeldeTripel.getObjektID());
            //if (lvHelpRueckmeldeTripel.getGeschaeftswertID().equals(pvRueckmeldeTripel.getGeschaeftswertID()) && 
            //    lvHelpRueckmeldeTripel.getSicherheitenID().equals(pvRueckmeldeTripel.getSicherheitenID()) &&
            //    lvHelpRueckmeldeTripel.getObjektID().equals(pvRueckmeldeTripel.getObjektID()))
            //{
        if (lvHelpRueckmeldeTripel != null)
        {
          if (lvHelpRueckmeldeTripel.getSicherheitenStatus().equals(pvRueckmeldeTripel.getSicherheitenStatus()))
          {
            return true;
          }
          else
          {
            System.out.println("Fehler: Deckungsstatus unterschiedlich fuer gleiches Tripel-Element.");
            System.out.println("GID: " + pvRueckmeldeTripel.getGeschaeftswertID() + " SID: " + pvRueckmeldeTripel.getSicherheitenID() + " OIB: " + pvRueckmeldeTripel.getObjektID());
            return false;
          }
        //    }
        }
        return false;
    }
}
