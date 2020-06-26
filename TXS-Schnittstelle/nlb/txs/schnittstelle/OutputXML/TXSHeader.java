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
@Deprecated
public class TXSHeader 
{
    /**
     * Liefert den TXSHeader im XML-Format
     * @param pvValdate Datum
     * @return TXSHeader im XML-Format
     */
    public String printTXSHeader(String pvValdate) 
    {
        return "<txsi:header valdate=\"" + pvValdate + "\"/>\n";
    }

}
