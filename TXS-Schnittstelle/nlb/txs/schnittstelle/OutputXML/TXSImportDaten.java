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
public class TXSImportDaten 
{
    /**
     * Liefert den Start der TXSImportDaten als String
     * @return 
     */
    public String printTXSImportDatenStart()
    {
      return new String("<txsi:importdaten xmlns:txsi=\"http://agens.com/txsimport.xsd\">\n");
    }

    /**
     * Liefert das Ende der TXSImportDaten als String
     * @return 
     */
    public String printTXSImportDatenEnde()
    {
      return new String("</txsi:importdaten>");
    }
   
}
