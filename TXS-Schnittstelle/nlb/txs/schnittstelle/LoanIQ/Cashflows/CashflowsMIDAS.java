package nlb.txs.schnittstelle.LoanIQ.Cashflows;

import nlb.txs.schnittstelle.Utilities.IniReader;

import org.apache.log4j.xml.DOMConfigurator;

public class CashflowsMIDAS 
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
           System.out.println("CashflowsMIDAS <ini-Datei>");
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
           
           String lvLoggingXML = lvReader.getPropertyString("CashflowsMIDAS", "log4jconfig", "Fehler");
           if (lvLoggingXML.equals("Fehler"))
           {
             System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
           }
           else
           {
               DOMConfigurator.configure(lvLoggingXML); 
           }            
           
           new CashflowsVerarbeitung(lvReader, CashflowsVerarbeitung.MIDAS);
       }
       System.exit(0);
   }

}
