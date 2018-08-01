/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.Cashflows;

import org.apache.log4j.xml.DOMConfigurator;

import nlb.txs.schnittstelle.Utilities.IniReader;

/**
 * @author tepperc
 *
 */
public class CashflowsLoanIQ 
{
    /**
     * Startroutine Zahlungsstrom
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("CashflowsLoanIQ <ini-Datei>");
            System.exit(1);
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
                System.exit(1);
            }
            
            String lvLoggingXML = lvReader.getPropertyString("CashflowsLoanIQ", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
              System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
                DOMConfigurator.configure(lvLoggingXML);
            }            
            
            new CashflowsVerarbeitung(lvReader, CashflowsVerarbeitung.LOANIQ);
        }
        System.exit(0);
    }

}
