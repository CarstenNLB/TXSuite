package nlb.txs.schnittstelle.KEV;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Utilities.IniReader;

import org.apache.log4j.xml.DOMConfigurator;

public class KEVExtractorDarKa 
{
	// aus DarKa (NLB&BLB) eine Liste erstellen – Selektionsmerkmal Ausplatzierungsmerkmal = A0/A1
	//		InstNr;KtoNr;DZR;ZBBG;DLZ;DHZI;DNZI;

    /**
     * Startroutine KEVExtractorDarKa
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("LoanIQ <ini-Datei>");
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
            
            String lvLoggingXML = lvReader.getPropertyString("Darlehen", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
            	System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
                DOMConfigurator.configure(lvLoggingXML);
            }            

            new DarlehenVerarbeiten(DarlehenVerarbeiten.KEV_EXTRACTOR, lvReader);

        }
        System.exit(0);
    }
}
