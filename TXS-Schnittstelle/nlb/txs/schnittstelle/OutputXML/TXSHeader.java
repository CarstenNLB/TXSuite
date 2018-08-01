/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.OutputXML;

/**
 * @author tepperc
 *
 */
public class TXSHeader 
{
    /**
     * 
     * @param pvValdate 
     * @return 
     */
    public String printTXSHeader(String pvValdate) 
    {
        return new String("<txsi:header valdate=\"" + pvValdate + "\"/>\n");
    }

}
