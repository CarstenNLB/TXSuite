package nlb.txs.schnittstelle.RefiRegister;

import nlb.txs.schnittstelle.Utilities.IniReader;

import org.apache.log4j.xml.DOMConfigurator;

@Deprecated
public class RefiRegisterLettreDeGage 
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
            System.out.println("RefiRegisterLettreDeGage <ini-Datei>");
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
            String lvLoggingXML = lvReader.getPropertyString("RefiRegister_LettreDeGage", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
            	System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
            	DOMConfigurator.configure(lvLoggingXML); 
            }            
            
            // Aufruf des Konstruktors der RefiRegister-Verarbeitung
            //new RefiRegisterVerarbeitung(lvReader, RefiRegisterVerarbeitung.LETTRE_DE_GAGE);
        }
        System.exit(0);
    }

}
