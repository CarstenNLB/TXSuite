/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten;

import nlb.txs.schnittstelle.Utilities.IniReader;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author tepperc
 *
 */
public class SAPCMS_Neu 
{
    // Wird nur zu Testzwecken benoetigt!
    private static Logger LOGGER_SAPCMS = Logger.getLogger("TXSSAPCMSLogger");

    /**
     * Sicherheiten-Daten aus SAP CMS
     */
    private SicherheitenDaten ivSicherheitenDaten;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvFilename Name der Datei mit den Sicherheiten-Daten aus SAP CMS
     * @param pvLogger log4j-Logger
     */
    public SAPCMS_Neu(String pvFilename, Logger pvLogger) 
    {   
        this.ivLogger = pvLogger;

        this.ivSicherheitenDaten = new SicherheitenDaten(pvFilename, SicherheitenDaten.SAPCMS, pvLogger);

        // Initialisierung der Liste Zuweisungsbetraege
        //ivOzwListe = new ObjektZuweisungsbetragListe();
        // Initialisierung der Listen Entitaeten
        //ivListeSicherheitenvereinbarung = new HashMap<String, Sicherheitenvereinbarung>();
        //ivListeSicherungsumfang = new HashMap<String, LinkedList<Sicherungsumfang>>();
        //ivListeLast = new HashMap<String, Last>();
        //ivListeGrundbuchverweis = new HashMap<String, Grundbuchverweis>();
        //ivListeGeschaeftspartner = new HashMap<String, Geschaeftspartner>();
        //ivListeImmobilie = new HashMap<String, Immobilie>();
        //ivListeFlugzeug = new HashMap<String, Flugzeug>();
        //ivListeTriebwerk = new HashMap<String, Triebwerk>();
        //ivListeSchiff = new HashMap<String, Schiff>();
        //ivListeBeleihungssatz = new HashMap<String, Beleihungssatz>();
        //ivListeGrundbuchblatt = new HashMap<String, Grundbuchblatt>();
        //ivListeGrundstueck = new HashMap<String, Grundstueck>();

        //ivSAPCMS2Pfandbrief = new Sicherheiten2Pfandbrief(ivSicherheitenDaten, pvLogger);
        //ivSAPCMS2RefiRegister = new Sicherheiten2RefiRegister(ivSicherheitenDaten, pvLogger);
        //readSAPCMS(pvFilename);
    }

    /**
     * Existiert eine Sicherheit zur Kontonummer
     * @param pvKontonummer Kontonummer
     * @return
     */
    /* public String getSicherheitId(String pvKontonummer)
    {
        Sicherungsumfang lvShum = null;
        
        ivLogger.info("Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvKontonummer);
      
        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);
        
        // Nur Sicherheiten verwenden, wenn ZW > 0 ist
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
              lvShum = lvHelpListe.get(x);
              ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
              if (lvShum != null)
              {
                  ////if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
                  ////{
                    Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSicherheitenDaten.getListeSicherheitenvereinbarung().get(lvShum.getSicherheitenvereinbarungId());
                    return lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId();
                  ////}
              }
            } 
        }
        return null;
    } */

  /**
   * Liefert die Sicherheiten-Daten aus SAP CMS
   * @return Sicherheiten-Daten aus SAP CMS
   */
  public SicherheitenDaten getSicherheitenDaten()
  {
    return this.ivSicherheitenDaten;
  }

  /**
     * Startroutine (main) SAPCMS_Neu
     * Wird nur zu Testzwecken benoetigt!
     * @param args Uebergabeparameter
     */
    public static void main(String args[])
    { 
         if (!args[args.length - 1].endsWith(".ini"))
         {
            System.out.println("Starten:");
            System.out.println("Sicherheiten <ini-Datei>");
            System.exit(1);
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
                System.exit(1);
            }
        	
            String lvLoggingXML = lvReader.getPropertyString("SAPCMS", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
            	System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
                DOMConfigurator.configure(lvLoggingXML);
            }     
            
            String lvImportVerzeichnisSAPCMS = lvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
            if (lvImportVerzeichnisSAPCMS.equals("Fehler"))
            {
                LOGGER_SAPCMS.error("Kein SAPCMS Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            String lvSapcmsDatei = lvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
            if (lvSapcmsDatei.equals("Fehler"))
            {
                LOGGER_SAPCMS.error("Kein SAPCMS-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            SAPCMS_Neu lvSAPCMS = new SAPCMS_Neu(lvImportVerzeichnisSAPCMS + "\\" + lvSapcmsDatei, LOGGER_SAPCMS);
        }
        System.exit(0);
    }

}
