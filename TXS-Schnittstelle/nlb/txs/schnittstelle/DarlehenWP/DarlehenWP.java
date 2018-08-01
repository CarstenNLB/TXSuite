/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.DarlehenWP;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import nlb.txs.schnittstelle.Utilities.IniReader;

/**
 * @author Stefan Unnasch
 *
 * Dieses Programm liest Wertpapierdaten in XML-Form und gibt eine XML-Datei für die Schnittstelle der Anwendung TXS
 * aus
 * 
 * Als Werkzeug zur XML-Verabrbeitung wird jdom benutzt
 *
 */
public class DarlehenWP {
       
    /**
     * Startroutine DarlehenWP
     * @param args Zwei Parameter
     * @param args[0] 
     * 
     * Erstes Argument:
     * Quellsystem PDARLPFBG, PDARLOEPG, PDARLFLUG, PDARLSCHF
     *      * 
     * @param args[1]
     * Zweites Argument:
     * .ini Datei, z.B. C:\Temp\txs_batch.ini
     * 
     * hierin muss enthalten sein
     * 
     * [Konfiguration]
     * Institut=BLB
     * 
     * [DARLWP]
     * DARLWPXMLEIN=Y:/trans/NLB111222/DarlehenWP.xml
     * DARLWPXMLAUS=Y:/trans/NLB111222/DarlWP_TXS.xml
     * 
     */
    public static void main(String args[])
    { 
        if (!args[args.length - 1].endsWith(".ini"))
        {
            System.out.println("Fehler, Aufruf mit Quellsystem und ini-Datei");
            System.out.println("DarlehenWP <Quellsystem> <ini-Datei>");
            System.exit(0);
        }
        else
        {
            IniReader lvReader = null;
            try 
            {
                lvReader = new IniReader(args[args.length - 1]);
            }
            catch (Exception exp)
            {
                System.out.println("Fehler beim Laden der ini-Datei...");
                System.exit(0);
            }
            
            // zuerst log4 mit property versorgen
            String lvLog4jprop = "Fehler";
            if (args[0].equals("PDARLPFBG"))
            {
            	lvLog4jprop = lvReader.getPropertyString("DARLWP", "log4jconfigPFBG", "Fehler");
            }
            if (args[0].equals("PDARLFLUG"))
            {
            	lvLog4jprop = lvReader.getPropertyString("DARLWP", "log4jconfigFLUG", "Fehler");            	
            }
            if (args[0].equals("PDARLSCHF"))
            {
            	lvLog4jprop = lvReader.getPropertyString("DARLWP", "log4jconfigSCHF", "Fehler");
            }
            if (args[0].equals("PDARLOEPG"))
            {
            	lvLog4jprop = lvReader.getPropertyString("DARLWP", "log4jconfigOEPG", "Fehler");
            }
            if (lvLog4jprop.equals("Fehler"))
            {
            	if (args[0].equals("PDARLPFBG"))
            	{
            		System.out.println("Kein [DARLWP][log4jconfigPFBG] in der ini-Datei, logging auf console");
            	}
            	if (args[0].equals("PDARLFLUG"))
            	{
                    System.out.println("Kein [DARLWP][log4jconfigFLUG] in der ini-Datei, logging auf console");            		
            	}
            	if (args[0].equals("PDARLSCHF"))
            	{
                    System.out.println("Kein [DARLWP][log4jconfigSCHF] in der ini-Datei, logging auf console");            		
            	}
            	if (args[0].equals("PDARLOEPG"))
            	{
                    System.out.println("Kein [DARLWP][log4jconfigOEPG] in der ini-Datei, logging auf console");            		
            	}
                BasicConfigurator.configure();
            }
            else
            {
                System.out.println("log4jconfig=" + lvLog4jprop);  
                // PropertyConfigurator.configure(log4jprop);
                    
                DOMConfigurator.configure(lvLog4jprop); 
            }
                           
            new DarlehenWPVerarbeitung(lvReader, args[0]);
        }
        System.exit(0);
    }

 }
