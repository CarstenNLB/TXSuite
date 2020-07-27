/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.MAVIS;

import nlb.txs.schnittstelle.Utilities.IniReader;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Stefan Unnasch
 *
 * Dieses Programm verarbeitet XML-Dateien aus der Anwendung MAVIS (Job F0*DP3TX) und liefert als Ergebnis 
 * eine XML-Datei im Format der Anwendung TXS.
 *
 * Die Verarbeitung ist inhaltlich nach aktiven und passiven Papieren getrennt.
 *
 * Zum Lesen und Schreiben der XML-Dateien wird jdom benutzt.
 */
@Deprecated
public class MAVIS 
{

    /**
     * Startroutine MAVIS
     * @param args argv[0] .ini Datei, z.B. C:\Temp\txs_batch.ini
     * 
     * hierin muss enthalten sein:
     * 
     *  [Konfiguration]
     *  Institut=BLB
     * 
     *  [MAVIS]
     *  MAVXMLAUS=Y:/trans/BLB111207/MAVIS_TXS.xml
     *  MAVISDIR=Y:/trans/BLB111207/
     *  MAVISLOG=mavislog.txt
     *  MAVISMSK=A1PBAT.BM.I004.DPMAVIS.M710X.XMLOUT
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("MAVIS <ini-Datei>");
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
            
            String lvLoggingXML = lvReader.getPropertyString("MAVIS", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
            	System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
                DOMConfigurator.configure(lvLoggingXML);
            }            
            
            new MAVISVerarbeiten(lvReader);
        }
        System.exit(0);
    }

}