/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.Kunde;

import java.util.LinkedList;

/**
 * @author tepperc
 *
 */
public class OSPKundenListe extends LinkedList<OSPKunde>
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
     * Prueft, ob es einen Kunden mit der Kundennummer gibt 
     * @param pvNr Kundennummer
     * @return true - Kunde vorhanden; false - Keinen Kunden gefunden
     * 
     */
    public boolean contains(String pvNr)
    {
        OSPKunde lvHelpKunde;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpKunde = this.get(x);
            if (lvHelpKunde.getKundennr().equals(pvNr))
            {
              return true;
            }
        }
        return false;
    }
 
    /**
     * Liefert den Kunden zur Kundennummer
     * @param pvNr Kundennummer
     * @return Kunde zur Kundennummer
     * 
     */
    public OSPKunde getOSPKunde(String pvNr)
    {
        OSPKunde lvHelpKunde;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpKunde = this.get(x);
            if (lvHelpKunde.getKundennr().equals(pvNr))
            {
              return lvHelpKunde;
            }
        }
        return null;
    }

    /**
     * @return 
     * 
     */
    public String toString()
    {
        String lvHelpString;
        
        lvHelpString = "Anzahl Kunden: " + this.size() + "\n";
        OSPKunde lvHelpKunde;
        for (int x = 0; x < this.size(); x++)
        {
            lvHelpKunde = this.get(x);
            lvHelpString = lvHelpString + lvHelpKunde.toString();
        }
        return lvHelpString;
    }

    /**
     * @return the bestandsdatum
     */
    public String getBestandsdatum() {
        return this.ivBestandsdatum;
    }

    /**
     * @param pvBestandsdatum the bestandsdatum to set
     */
    public void setBestandsdatum(String pvBestandsdatum) {
        this.ivBestandsdatum = pvBestandsdatum;
    }
}
