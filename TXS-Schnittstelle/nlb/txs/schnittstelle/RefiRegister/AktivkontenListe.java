/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.RefiRegister;

import java.util.HashMap;

/**
 * @author tepperc
 *
 */
public class AktivkontenListe extends HashMap<String, AktivkontenDaten>
{
    /**
     * Ueberprueft, ob es zu der Kontonummer AktivkontenDaten gibt
     * @param pvNr Kontonummer
     * @return true -> Es gibt Daten; false -> Keine Daten vorhanden
     */
    public boolean containsAktivkontenDaten(String pvNr)
    {
        return this.containsKey(pvNr);
    }
    
    /**
     * Fuegt AktivkontenDaten hinzu
     * @param pvNr Kontonummer
     * @param pvAktivkontenDaten AktivkontenDaten
     */
    public void addAktivkontenDaten(String pvNr, AktivkontenDaten pvAktivkontenDaten)
    {
        this.put(pvNr, pvAktivkontenDaten);
    }

    /**
     * Liefert die AktivkontenDaten zur Kontonummer
     * @param pvNr Kontonummer
     * @return AktivkontenDaten
     */
    public AktivkontenDaten getAktivkontenDaten(String pvNr)
    {
      return this.get(pvNr);       
    }
}
