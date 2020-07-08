/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv;

import nlb.txs.schnittstelle.Utilities.IniReader;

/**
 * @author tepperc
 *
 */
public class LoanIQPassiv 
{
    /**
     * Startroutine LoanIQPassiv
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("LoanIQPassiv <ini-Datei>");
            System.exit(0);
        }
        else
        {
            IniReader lvReader = null;
            try 
            {
                lvReader = new IniReader(argv[argv.length - 1]);
            }
            catch (Exception exp)
            {
                System.out.println("Fehler beim Laden der ini-Datei...");
                System.exit(0);
            }
            
            new LoanIQPassivVerarbeitung(lvReader);
        }
        System.exit(0);
    }

}
