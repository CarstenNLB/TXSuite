package nlb.txs.schnittstelle.Marktdaten;

import nlb.txs.schnittstelle.OutputXML.OutputXML;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Wechselkurse.LeseWechselkurse;
import nlb.txs.schnittstelle.Zinskurse.LeseZinskurse;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 
 * @author tepperc
 *
 */
public class Marktdaten
{
    /**
     * log4j-Logger
     */
    private static Logger LOGGER = Logger.getLogger("TXSMarktdatenLogger"); 

    /**
     * Start des Programms
     * @param argv
     */
    public static void main(String argv[])
    {       
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("Marktdaten <ini-Datei>");
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
            if (lvReader != null)
            {
               String lvLoggingXML = lvReader.getPropertyString("Marktdaten", "log4jconfig", "Fehler");
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
                    LOGGER.error("Keine Institutsnummer in der ini-Datei...");
                    System.exit(1);
                }
                                        
                String lvImportVerzeichnis = lvReader.getPropertyString("Marktdaten", "ImportVerzeichnis", "Fehler");
                if (lvImportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                String lvExportVerzeichnis = lvReader.getPropertyString("Marktdaten", "ExportVerzeichnis", "Fehler");
                if (lvExportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                String lvZinskurseDatei =  lvReader.getPropertyString("Marktdaten", "ZinskurseDatei", "Fehler");
                if (lvZinskurseDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein Zinskurse-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
               
                String lvWechselkurseDatei = lvReader.getPropertyString("Marktdaten", "WechselkurseDatei", "Fehler");
                if (lvWechselkurseDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein Wechselkurse-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
 
                String lvOutputXMLDatei = lvReader.getPropertyString("Marktdaten", "OutputXML-Datei", "Fehler");
                if (lvOutputXMLDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein OutputXML-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                new Marktdaten(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvOutputXMLDatei, 
                               lvWechselkurseDatei, lvZinskurseDatei);
            }
        }
        System.exit(0);
    }

    /**
     * Konstruktor mit Parameter (Batch-Betrieb)
     * @param pvMandant 
     * @param pvImportVerzeichnis 
     * @param pvExportVerzeichnis 
     * @param pvOutputXMLDatei 
     * @param pvWechselkurseName 
     * @param pvZinskurseName 
     */
    public Marktdaten(String pvMandant, String pvImportVerzeichnis, String pvExportVerzeichnis, String pvOutputXMLDatei, 
            String pvWechselkurseName, String pvZinskurseName)
    {
       OutputXML lvOXML;
       LOGGER.info("Start Marktdaten");
       LeseWechselkurse lvLw = new LeseWechselkurse(pvImportVerzeichnis + "\\" + pvWechselkurseName);
       LeseZinskurse lvLz = new LeseZinskurse(pvImportVerzeichnis + "\\" + pvZinskurseName);
       
       lvOXML = new OutputXML(pvExportVerzeichnis + "\\" + pvOutputXMLDatei, pvMandant);
       lvOXML.openXML();
       lvOXML.printXMLStart();
       lvOXML.printTXSImportDatenStart();
       if (!(lvLw == null))
       {
         int lvZaehlerWechselkurse = lvLw.readWechselkurseCSV(lvOXML);
         LOGGER.info(lvZaehlerWechselkurse + " Wechselkurse gelesen...");
       }
       
       if (!(lvLz == null))
       {
         int lvZaehlerZinskurse = lvLz.readZinskurseCSV(lvOXML);
         LOGGER.info(lvZaehlerZinskurse + " Zinskurse gelesen...");
       }
       lvOXML.printTXSImportDatenEnde();
       lvOXML.closeXML();
       LOGGER.info("Ende Marktdaten");
    }
}




