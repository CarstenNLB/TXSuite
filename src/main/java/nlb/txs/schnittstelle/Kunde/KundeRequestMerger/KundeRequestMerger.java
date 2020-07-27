/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Kunde.KundeRequestMerger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import nlb.txs.schnittstelle.Darlehen.KundeRequestXML;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;

public class KundeRequestMerger 
{
	/**
	 * log4j-Logger KundeRequestMerger
	 */
    private static Logger LOGGER = Logger.getLogger("TXSKundeRequestMergerLogger");
	
    /**
     * Institutsname
     */
    private String ivInstitutsname;
    
    /**
     * Buchungsdatum
     */
    private String ivBuchungsdatum;
    
    /**
     * Liste der Kundennummern der Kundendateien
     */
    private HashSet<String> ivListeKundennr;
     
    /**
     * Konstruktor
     * @param pvFilenameListeKundeIn Liste der Textdateien mit Kundennummern (Eingabedateien fuer die XML-KundeRequest Ausgabedatei)
     * @param pvFilenameOut Name der XML-KundeRequest Ausgabedatei
     * @param pvInstitutsname Institutsname
     * @param pvDaypointerFileout Name der Datei, in der der Buchungsdatum steht
     */
    public KundeRequestMerger(String pvFilenameListeKundeIn, String pvFilenameOut, String pvInstitutsname, String pvDaypointerFileout)
    {
        ivInstitutsname = pvInstitutsname;
        ivListeKundennr = new HashSet<String>();
        leseKunden(pvFilenameListeKundeIn, pvDaypointerFileout);
        schreibeKunden(pvFilenameOut);
    }
	
    /**
     * Liest die Kundennummern ein. Dazu muss vorher eine Liste der Kundendateien eingelesen werden.
     * @param pvFilenameListeKundeIn Dateiname der Liste der Kundendateien
     * @param pvDaypointerFileout Dateiname des Buchungsdatum
     */
    private void leseKunden(String pvFilenameListeKundeIn, String pvDaypointerFileout)
    {
    	LOGGER.info("Start --> leseKunden...");
    	HashSet<String> ivListeKundeIn = new HashSet<String>();
     
    	// Einlesen des Buchungsdatum
    	File ivFileDaypointer = new File(pvDaypointerFileout);
        if (ivFileDaypointer != null && ivFileDaypointer.exists())
        {
          FileInputStream lvDaypointerFis = null;
          BufferedReader lvDaypointerBr = null;
          try
          {
            lvDaypointerFis = new FileInputStream(ivFileDaypointer);
            lvDaypointerBr = new BufferedReader(new InputStreamReader(lvDaypointerFis));
          }
          catch (Exception e)
          {
            LOGGER.info("Konnte Datei '" + pvDaypointerFileout + "' nicht oeffnen!");
          }
          try
          {
            ivBuchungsdatum = lvDaypointerBr.readLine();  
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten:" + ivBuchungsdatum);
              e.printStackTrace();
          }
          try
          {
            lvDaypointerBr.close();
            lvDaypointerFis.close();
          }
          catch (Exception e)
          {
              LOGGER.error("Konnte Datei '" + pvDaypointerFileout + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Datei '" + pvDaypointerFileout + "' gefunden...");
        }
        LOGGER.info("Buchungsdatum: " + ivBuchungsdatum);

    	// Einlesen der Liste der Kundendateien
        int lvZaehlerKundeIn = 0;
        String lvKundeIn = new String();
        
        File ivFileListeKundeIn = new File(pvFilenameListeKundeIn);

        if (ivFileListeKundeIn != null && ivFileListeKundeIn.exists())
        {
          FileInputStream lvListeKundeInFis = null;
          BufferedReader lvListeKundeInBr = null;
          try
          {
            lvListeKundeInFis = new FileInputStream(ivFileListeKundeIn);
            lvListeKundeInBr = new BufferedReader(new InputStreamReader(lvListeKundeInFis));
          }
          catch (Exception e)
          {
            LOGGER.info("Konnte Datei '" + pvFilenameListeKundeIn + "' nicht oeffnen!");
          }
          try
          {
            LOGGER.info("Folgende KundeIn-Dateien werden verarbeitet:");  
            while ((lvKundeIn = lvListeKundeInBr.readLine()) != null)  
            {
              if (lvKundeIn != null)
              {
                ivListeKundeIn.add(lvKundeIn);
                LOGGER.info("KundeIn-Datei: " + lvKundeIn);
                lvZaehlerKundeIn++;
              }
            }
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvKundeIn);
              e.printStackTrace();
          }
          try
          {
            lvListeKundeInBr.close();
            lvListeKundeInFis.close();
          }
          catch (Exception e)
          {
              LOGGER.error("Konnte Datei '" + pvFilenameListeKundeIn + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Datei '" + pvFilenameListeKundeIn + "' gefunden...");
        }
        LOGGER.info(lvZaehlerKundeIn + " KundeIn-Dateien enthalten...");
        
        // Kundennummern einlesen
        int lvZaehlerKundennummernGesamt = 0;
        Iterator<String> lvIterKundeIn = ivListeKundeIn.iterator();
        while (lvIterKundeIn.hasNext())
        {
            int lvZaehlerKundennummern = 0;
            lvKundeIn = lvIterKundeIn.next();
            File ivFileKundeIn = new File(lvKundeIn);

            if (ivFileKundeIn != null && ivFileKundeIn.exists())
            {
              FileInputStream lvKundeInFis = null;
              BufferedReader lvKundeInBr = null;
              try
              {
                lvKundeInFis = new FileInputStream(ivFileKundeIn);
                lvKundeInBr = new BufferedReader(new InputStreamReader(lvKundeInFis));
              }
              catch (Exception e)
              {
                LOGGER.info("Konnte Datei '" + lvKundeIn + "' nicht oeffnen!");
              }
              String lvKundennummer = new String();
              try
              {
                LOGGER.info("Folgende Kundennummern sind enthalten:");  
                while ((lvKundennummer = lvKundeInBr.readLine()) != null)  
                {
                  if (lvKundeIn != null)
                  {
                	  // Vorlaufsatz ueberlesen
                	  if (lvZaehlerKundennummern == 0)
                	  {
                		  lvZaehlerKundennummern++;
                		  LOGGER.info("Vorlaufsatz: " + lvKundennummer);
                	  }
                	  else // Kundennummer verarbeiten
                	  {
                		  ivListeKundennr.add(lvKundennummer);
                		  LOGGER.info("Kundennummer: " + lvKundennummer);
                		  lvZaehlerKundennummern++;
                		  lvZaehlerKundennummernGesamt++;
                	  }
                  }
                }
              }
              catch (Exception e)
              {
                  LOGGER.error("Fehler beim Verarbeiten von:" + lvKundennummer);
                  e.printStackTrace();
              }
              try
              {
                lvKundeInBr.close();
                lvKundeInFis.close();
              }
              catch (Exception e)
              {
                  LOGGER.error("Konnte Datei '" + lvKundeIn + "' nicht schliessen!");
              }
            }
            else
            {
                LOGGER.info("Keine Datei '" + lvKundeIn + "' gefunden...");
            }
            if (lvZaehlerKundennummern == 0)
            {
              LOGGER.info(lvZaehlerKundennummern + " Kundennummern enthalten...");
            }
            else
            {
              LOGGER.info((lvZaehlerKundennummern - 1) + " Kundennummern enthalten...");
            }
        }
        LOGGER.info("insgesamt " + lvZaehlerKundennummernGesamt + " Kundennummern...");
    }
    
    /**
     * Schreibt die Kundendaten in die TXS XML-Datei
     * @param pvFilenameOut
     */
    private void schreibeKunden(String pvFilenameOut)
    {
    	LOGGER.info("Start --> schreibeKunden...");
    	StringBuilder lvFilenameOut = new StringBuilder();
    	lvFilenameOut.append(pvFilenameOut);
    	if (ivInstitutsname.equals("NLB"))
    	{
    		lvFilenameOut.append("_009_");
    	}
    	if (ivInstitutsname.equals("BLB"))
    	{
    		lvFilenameOut.append("_004_");
    	}
    	lvFilenameOut.append(ivBuchungsdatum.replace("-", "").trim());
    	lvFilenameOut.append(".xml");
    	LOGGER.info("Ausgabedatei: " + lvFilenameOut.toString());
    	
        KundeRequestXML lvReqXML = new KundeRequestXML(lvFilenameOut.toString(), LOGGER);
        lvReqXML.openXML();
        // Kunde-Request XML-Start
        lvReqXML.printXMLStart();

        Calendar lvCal = Calendar.getInstance();
        CalendarHelper lvCh = new CalendarHelper();

        lvReqXML.printXMLHeader(ivBuchungsdatum.replace("-",  ""), lvCh.printDateAsNumber(lvCal), lvCh.printTimeAsNumber(lvCal));
        if (ivInstitutsname.equals("NLB"))
        {
          lvReqXML.printXMLRequestStart("009");
        }
        if (ivInstitutsname.equals("BLB"))
        {
            lvReqXML.printXMLRequestStart("004");        	
        }

        LOGGER.info("Ausgabe von " + ivListeKundennr.size() + " Kundennummern.");

        String lvHelpKundennr;
        Iterator<String> lvIterKundennummer = ivListeKundennr.iterator();
        while (lvIterKundennummer.hasNext())
        {
            lvHelpKundennr = lvIterKundennummer.next();
            if (!lvHelpKundennr.isEmpty())
            {
              lvReqXML.printKundeRequest(MappingKunde.extendKundennummer(lvHelpKundennr, LOGGER));
            }
        }
        
        // Kunde-Request XML-Ende
        lvReqXML.printXMLRequestEnde();
        lvReqXML.printXMLEnde();
        
        // Kunde-Request XML-Datei schliessen
        lvReqXML.closeXML();       
    }

    /**
     * Startroutine KundeRequestMerger
     * @param argv Aufrufparameter
     */
    public static void main(String argv[])
    { 
        if (argv.length == 0)
        {
            System.out.println("Starten:");
            System.out.println("KundeRequestMerger <ini-Datei>");
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
            
            String lvLoggingXML = lvReader.getPropertyString("KundeRequestMerger", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
              System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            { 
                DOMConfigurator.configure(lvLoggingXML); 
            }            

            String lvInstitutsname = new String();
            String lvExportVerzeichnis = new String();
            String lvListeKundeInDatei = new String();
            String lvKundeRequestOutput = new String();
            String lvDaypointerFileout = new String();
            if (lvReader != null)
            {

                lvInstitutsname = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
                if (lvInstitutsname.equals("Fehler"))
                {
                    LOGGER.error("Keine Institutsnummer in der ini-Datei...");
                    System.exit(1);
                }
                
                lvExportVerzeichnis = lvReader.getPropertyString("KundeRequestMerger", "ExportVerzeichnis", "Fehler");
                if (lvExportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                lvListeKundeInDatei = lvReader.getPropertyString("KundeRequestMerger", "ListeKundeInDatei", "Fehler");
                if (lvListeKundeInDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein ListeKundeIn-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                lvKundeRequestOutput = lvReader.getPropertyString("KundeRequestMerger", "KundeRequestOutput", "Fehler");
                if (lvKundeRequestOutput.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeRequestOutput-Dateiname in der ini-Datei...");
                    System.exit(1);
                }  
                
                lvDaypointerFileout = lvReader.getPropertyString("Konfiguration", "DPFILE", "Fehler");
                if (lvDaypointerFileout.equals("Fehler"))
                {
                    LOGGER.error("Kein [Konfiguration][DPFILE] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("DPFILE=" + lvDaypointerFileout);                    
                }
            }
            
            new KundeRequestMerger(lvListeKundeInDatei, lvExportVerzeichnis + "\\" + lvKundeRequestOutput, lvInstitutsname, lvDaypointerFileout);
        }
        System.exit(0);
    }

}
