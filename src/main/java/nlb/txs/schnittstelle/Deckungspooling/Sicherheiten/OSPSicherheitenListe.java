/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.Sicherheiten;

import java.util.LinkedList;

/**
 * @author tepperc
 *
 */
@Deprecated
public class OSPSicherheitenListe extends LinkedList<OSPSicherheit>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Bestandsdatum der Kundendaten
     */
    private String ivBestandsdatum;

    /**
     * @return the bestandsdatum
     */
    public String getBestandsdatum() 
    {
        return this.ivBestandsdatum;
    }

    /**
     * @param pvBestandsdatum the bestandsdatum to set
     */
    public void setBestandsdatum(String pvBestandsdatum) 
    {
        this.ivBestandsdatum = pvBestandsdatum;
    }
 
}
