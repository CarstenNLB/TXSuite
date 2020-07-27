/*******************************************************************************
 * Copyright (c) 2016 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier.Cashflows;

import nlb.txs.schnittstelle.Utilities.IniReader;
import org.apache.log4j.xml.DOMConfigurator;

public class CashflowsWertpapiere
{
	/**
	 * Startroutine Cashflows
	 * @param argv 
	 */
	public static void main(String argv[])
	{ 
		if (!argv[argv.length - 1].endsWith(".ini"))
		{
			System.out.println("Starten:");
			System.out.println("Cashflows <ini-Datei>");
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
			
			String lvLoggingXML = lvReader.getPropertyString("CashflowsWertpapiere", "log4jconfig", "Fehler");
			if (lvLoggingXML.equals("Fehler"))
			{
				System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
			}
			else
			{
				DOMConfigurator.configure(lvLoggingXML); 
			}            
	           
			new CashflowsWertpapiereVerarbeitung(lvReader);
		}
		System.exit(0);
	}
}
