/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Flugzeuge;

import org.apache.log4j.xml.DOMConfigurator;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Utilities.IniReader;

/**
 * @author tepperc
 *
 */
public class DarlehenFlugzeuge 
{
    /**
     * Startroutine DarKa
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("DarlehenFlugzeuge <ini-Datei>");
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
            
            String lvLoggingXML = lvReader.getPropertyString("Flugzeuge", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
              System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
                DOMConfigurator.configure(lvLoggingXML); 
            }            
            
            new DarlehenVerarbeiten(DarlehenVerarbeiten.FLUGZEUGE, lvReader);
        }
        System.exit(0);
    }

}
