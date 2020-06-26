/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Utilities.IniReader;

/**
 * @author tepperc
 *
 */
@Deprecated
public class Deckungspooling 
{
    /**
     * 
     */
    //private static Logger LOGGER = Logger.getLogger("DPPKundeLogger"); 
    
    /**
     * Startroutine Deckungspooling
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("DarlehenDPP <ini-Datei>");
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
            
            new DarlehenVerarbeiten(DarlehenVerarbeiten.DPP, lvReader);
        }
        System.exit(0);
    }

    
    /**
     * Startroutine
     * @param argv 
     */
    /* public static void main(String[] argv) 
    {
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("Deckungspooling <ini-Datei>");
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
            if (lvReader != null)
            {
                String lvLoggingXML = lvReader.getPropertyString("log4j", "log4jconfig", "Fehler");
                if (lvLoggingXML.equals("Fehler"))
                {
                  System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
                }
                else
                {
                    DOMConfigurator.configure(lvLoggingXML); 
                }            

                String lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
                if (lvInstitut.equals("Fehler"))
                {
                    //System.out.println("Keine Institutsnummer in der ini-Datei...");
                    LOGGER.error("Keine Institutsnummer in der ini-Datei...");
                    System.exit(0);
                }

                String lvImportVerzeichnis = lvReader.getPropertyString("Deckungspooling", "ImportVerzeichnis", "Fehler");
                if (lvImportVerzeichnis.equals("Fehler"))
                {
                    //System.out.println("Kein Import-Verzeichnis in der ini-Datei...");
                    LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
                    System.exit(0);
                }

                String lvExportVerzeichnis = lvReader.getPropertyString("Deckungspooling", "ExportVerzeichnis", "Fehler");
                if (lvExportVerzeichnis.equals("Fehler"))
                {
                    //System.out.println("Kein Export-Verzeichnis in der ini-Datei...");
                    LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
                    System.exit(0);
                }

                String lvOSPKundenDatei =  lvReader.getPropertyString("Deckungspooling", "OSPKunden-Datei", "Fehler");
                if (lvOSPKundenDatei.equals("Fehler"))
                {
                    //System.out.println("Kein OSPKunden-Dateiname in der ini-Datei...");
                    LOGGER.error("Kein OSPKunden-Dateiname in der ini-Datei...");
                    System.exit(0);
                }
                
                String lvTXSKundenDatei =  lvReader.getPropertyString("Deckungspooling", "TXSDPPKunden-Datei", "Fehler");
                if (lvTXSKundenDatei.equals("Fehler"))
                {
                    //System.out.println("Kein TXSKunden-Dateiname in der ini-Datei...");
                    LOGGER.error("Kein TXSKunden-Dateiname in der ini-Datei...");
                    System.exit(0);
                }
                     
                String lvOSPSicherheitenDatei =  lvReader.getPropertyString("Deckungspooling", "OSPSicherheiten-Datei", "Fehler");
                if (lvOSPSicherheitenDatei.equals("Fehler"))
                {
                    //System.out.println("Kein OSPSicherheiten-Dateiname in der ini-Datei...");
                    LOGGER.error("Kein OSPSicherheiten-Dateiname in der ini-Datei...");
                    System.exit(0);
                }
                
                String lvObjektZWDatei = lvReader.getPropertyString("Darlehen", "ObjektZW-Datei", "Fehler");
                if (lvObjektZWDatei.equals("Fehler"))
                {
                    //System.out.println("Kein ObjektZW-Dateiname in der ini-Datei...");
                    LOGGER.error("Kein ObjektZW-Dateiname in der ini-Datei...");
                    System.exit(0);
                }
                
                String lvDarlehenDatei = lvReader.getPropertyString("Deckungspooling", "DarlehenDPP-Datei", "Fehler");
                if (lvDarlehenDatei.equals("Fehler"))
                {
                    //System.out.println("Kein DarlehenDPP-Dateiname in der ini-Datei...");
                    LOGGER.error("Kein DarlehenDPP-Dateiname in der ini-Datei...");
                    System.exit(0);
                }
                
                String lvDarlehenImportDatei =  lvReader.getPropertyString("Deckungspooling", "DarlehenDPPImport-Datei", "Fehler");
                if (lvDarlehenImportDatei.equals("Fehler"))
                {
                    //System.out.println("Kein DarlehenDPPImport-Dateiname in der ini-Datei...");
                    LOGGER.error("Kein DarlehenDPPImport-Dateiname in der ini-Datei...");
                    System.exit(0);
                }
               
                String lvDarlehenTXSDatei = lvReader.getPropertyString("Deckungspooling", "DarlehenTXSDPP-Datei", "Fehler");
                if (lvDarlehenTXSDatei.equals("Fehler"))
                {
                    //System.out.println("Kein DarlehenTXSDPP-Dateiname in der ini-Datei...");
                    LOGGER.error("Kein DarlehenTXSDPP-Dateiname in der ini-Datei...");
                    System.exit(0);
                }

                // Deckungspoolingdaten verarbeiten
                DeckungspoolingVerarbeitung lvDpp = new DeckungspoolingVerarbeitung(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvOSPKundenDatei, lvTXSKundenDatei, lvOSPSicherheitenDatei, LOGGER);
                
                // Darlehen verarbeiten
                new DarlehenVerarbeitung(LOGGER, lvDpp, lvImportVerzeichnis, lvExportVerzeichnis, lvObjektZWDatei, lvDarlehenDatei, lvDarlehenTXSDatei, lvDarlehenImportDatei);
            }
        }
        System.exit(0);
    } */

}
