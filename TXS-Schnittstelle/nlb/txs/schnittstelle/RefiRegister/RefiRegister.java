/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.RefiRegister;

import org.apache.log4j.xml.DOMConfigurator;

import nlb.txs.schnittstelle.Utilities.IniReader;

/**
 * Diese Klasse beinhaltet nur die Startroutine(main).
 * Es wird eine Instanz 'IniReader' angelegt und log4j konfiguriert.
 * @author tepperc
 */
public class RefiRegister 
{
    /**
     * Startroutine RefiRegister
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("RefiRegister <ini-Datei>");
            System.exit(1);
        }
        else
        {
        	// IniReader instanzieren
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
            
            // log4j konfigurieren
            String lvLoggingXML = lvReader.getPropertyString("RefiRegister", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
            	System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
            	DOMConfigurator.configure(lvLoggingXML); 
            }            
            
            // Aufruf des Konstruktors der RefiRegister-Verarbeitung
            new RefiRegisterVerarbeitung(lvReader, RefiRegisterVerarbeitung.REFI_REG);
        }
        System.exit(0);
    }

}
