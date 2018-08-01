/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.OSPInstitut;

import java.util.LinkedList;

/**
 * @author tepperc
 *
 */
public class OSPInstitutListe extends LinkedList<OSPInstitutDaten>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Prueft, ob es das OSPInstitut gibt 
     * @param pvRegKz
     * @param pvInstnr
     * @return true - OSPInstitut vorhanden; false - Kein OSPInstitut gefunden
     * 
     */
    public boolean contains(String pvRegKz, String pvInstnr)
    {
        OSPInstitutDaten lvHelpInst;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpInst = this.get(x);
            if (lvHelpInst.getRegKz().equals(pvRegKz) && lvHelpInst.getOSPInst().equals(pvInstnr))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Liefert den Kunden zur Kundennummer
     * @param pvRegKz Regionkennzeichen
     * @param pvInstnr OSP-Institutsnummer
     * @return OSPInstitut
     * 
     */
    public OSPInstitutDaten getOSPInstitut(String pvRegKz, String pvInstnr)
    {
        OSPInstitutDaten lvHelpInst;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpInst = this.get(x);
            if (lvHelpInst.getRegKz().equals(pvRegKz) && lvHelpInst.getOSPInst().equals(pvInstnr))
            {
                return lvHelpInst;
            }
        }
        return null;
    }
 
    /**
     * Liefert den Kunden zur Kundennummer
     * @param pvKdNr DarKa-Kd-Nr
     * @return OSPInstitut
     * 
     */
    public OSPInstitutDaten getOSPInstitut(String pvKdNr)
    {
        OSPInstitutDaten lvHelpInst;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpInst = this.get(x);
            if (lvHelpInst.getDarKaKuNr().equals(pvKdNr))
            {
                return lvHelpInst;
            }
        }
        return null;
    }

    /**
     * Liefert die Institutskennung
     * @param kontonummer 
     * @return 
     */
    /* public String getInstitutskennung(String kontonummer)
    {
        String helpKennung = new String();
        OSPInstitutDaten helpInst;

        for (int x = 0; x < this.size(); x++)
        {
            helpInst = this.get(x);
            helpKennung = helpInst.getInstitutskennung(kontonummer);
        }
     
        return helpKennung;
    } */
}