/*******************************************************************************
 * Copyright (c) 2017 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Kunde;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class KundennummernUmstellung
{
    /**
     * log4j-Logger
     */
    private static Logger LOGGER = Logger.getLogger("TXSKundennummernUmstellungLogger"); 

    /**
     * Liste mit KundennummernUmstellungDaten
     */
    private HashMap<String, KundennummernUmstellungDaten> ivListeKundennummernUmstellungDaten;
    
	/**
	 * Konstruktor
	 * @param pvInstitut
	 * @param pvImportVerzeichnis
	 * @param pvExportVerzeichnis
	 * @param pvKundennummernUmstellungDatei
	 * @param pvInputDatei
	 * @param pvOutputDatei
	 */
	public KundennummernUmstellung(String pvInstitut, String pvImportVerzeichnis, String pvExportVerzeichnis, String pvKundennummernUmstellungDatei, String pvInputDatei, String pvOutputDatei)
	{
		// Initialisierung der Liste fuer die KundennummernUmstellungDaten
		ivListeKundennummernUmstellungDaten = new HashMap<String, KundennummernUmstellungDaten>();
		
		// Einlesen der KundennummernUmstellungDaten
		leseKundennummernUmstellungDaten(pvImportVerzeichnis + "\\" + pvKundennummernUmstellungDatei);
		
		// Statistik doppelter Kundennummern
		Collection<KundennummernUmstellungDaten> lvCollectionKundennummernUmstellungDaten = ivListeKundennummernUmstellungDaten.values();
		Iterator<KundennummernUmstellungDaten> lvIteratorKundennummernUmstellungDaten = lvCollectionKundennummernUmstellungDaten.iterator();
		while (lvIteratorKundennummernUmstellungDaten.hasNext())
		{
			KundennummernUmstellungDaten lvKundennummernUmstellungDaten = lvIteratorKundennummernUmstellungDaten.next();
			LOGGER.info(lvKundennummernUmstellungDaten.toString());
		}
		
		// Umstellung der Kundennummern
		umstellenKundennummern(pvImportVerzeichnis + "\\" + pvInputDatei, pvExportVerzeichnis + "\\" + pvOutputDatei);
	}
	
	/**
	 * Einlesen der KundennummernUmstellungDaten
	 * @param pvFilename
	 */
	private void leseKundennummernUmstellungDaten(String pvFilename)
	{
        int lvZaehlerKundennummern = 0;
        File lvKundennummernUmstellung = new File(pvFilename);
        if (lvKundennummernUmstellung != null && lvKundennummernUmstellung.exists())
        {
          FileInputStream lvKundennummernUmstellungIs = null;
          BufferedReader lvKundennummernUmstellungIn = null;
          try
          {
            lvKundennummernUmstellungIs = new FileInputStream(lvKundennummernUmstellung);
            lvKundennummernUmstellungIn = new BufferedReader(new InputStreamReader(lvKundennummernUmstellungIs));
          }
          catch (Exception e)
          {
        	  LOGGER.info("Konnte Datei '" + lvKundennummernUmstellung.getAbsolutePath() + "' nicht oeffnen!");
          }
          String lvZeile = new String();
          boolean lvFirst = true;
          try
          {
            while ((lvZeile = lvKundennummernUmstellungIn.readLine()) != null)  // Einlesen KundennummernUmstellungDatei
            {
              if (lvFirst)
              {
            	  lvFirst = false;
              }
              else
              {
            	  if (lvZeile != null)
            	  {
            		  if (!lvZeile.startsWith("**** END OF FILE ****"))
            		  {
            			  KundennummernUmstellungDaten lvKundennummernUmstellungDaten = new KundennummernUmstellungDaten();
            			  lvKundennummernUmstellungDaten.parseDaten(lvZeile);
            			  ivListeKundennummernUmstellungDaten.put(lvKundennummernUmstellungDaten.getKdNrBLB(), lvKundennummernUmstellungDaten);
            			  LOGGER.info("Kundennummern-Umstellung: " + lvZeile);
            			  lvZaehlerKundennummern++;
            		  }
            	  }
              }
            }
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvZeile);
              e.printStackTrace();
          }
          try
          {
            lvKundennummernUmstellungIn.close();
            lvKundennummernUmstellungIs.close();
          }
          catch (Exception e)
          {
              LOGGER.error("Konnte Datei '" + lvKundennummernUmstellung.getAbsolutePath() + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Datei '" + lvKundennummernUmstellung.getAbsolutePath() + "' gefunden...");
        }
        LOGGER.info("Anzahl Kundennummern-Umstellungen: " + lvZaehlerKundennummern);
	}
	
	/**
	 * Umstellen der Kundennummern 
	 * @param pvInputFilename
	 * @param pvOutputFilename
	 */
	private void umstellenKundennummern(String pvInputFilename, String pvOutputFilename)
	{
		LOGGER.info("Kundennummern umstellen...");
		LOGGER.info("Input-Datei: " + pvInputFilename);
		LOGGER.info("Output-Datei: " + pvOutputFilename);
		
        File lvInputDatei = new File(pvInputFilename);
        File lvOutputDatei = new File(pvOutputFilename);
        
        FileInputStream lvFis = null;
        FileOutputStream lvFos = null;
        
        boolean lvOkay = true;
        try
        {
            lvFis = new FileInputStream(lvInputDatei);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Eingabe-Datei '" + pvInputFilename + "' nicht oeffnen!");
            lvOkay = false;
        }

        try
        {
            lvFos = new FileOutputStream(lvOutputDatei);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputFilename + "' nicht oeffnen!");
            lvOkay = false;
        }
        
        if (!lvOkay)
        {
        	return;
        }
        
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));   
        String lvZeile = new String();
        
        int lvZeileCounter = 0;
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Einlesen der Input-Datei
            { 
              // Kundennummer ermitteln	
              String lvKundennummer = lvZeile.substring(0, lvZeile.indexOf(";"));	
              if (ivListeKundennummernUmstellungDaten.containsKey(lvKundennummer))
              {
            	  KundennummernUmstellungDaten lvKundennummernUmstellungDaten = ivListeKundennummernUmstellungDaten.get(lvKundennummer);
            	  LOGGER.info("Kundennummer von " + lvKundennummer + " auf " + lvKundennummernUmstellungDaten.getKdNrNLB() + " umgestellt.");
            	  lvZeile = lvZeile.replace(lvKundennummer, lvKundennummernUmstellungDaten.getKdNrNLB());
              }
              
              // Buergennummer ermitteln
              // Nur KTR-Zeile relevant
              if (lvZeile.contains(";KTR;"))
              {
            	  String lvBuergennummer = lvZeile.substring(240,250);
                  if (ivListeKundennummernUmstellungDaten.containsKey(lvBuergennummer))
                  {
                	  KundennummernUmstellungDaten lvKundennummernUmstellungDaten = ivListeKundennummernUmstellungDaten.get(lvBuergennummer);
                	  LOGGER.info("Buergennummer von " + lvBuergennummer + " auf " + lvKundennummernUmstellungDaten.getKdNrNLB() + " umgestellt.");
                	  lvZeile = lvZeile.replace(lvBuergennummer, lvKundennummernUmstellungDaten.getKdNrNLB());
                  }            	  
              }
              lvFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
              lvZeileCounter++;
            }
        }
        catch (Exception exp)
        {
            LOGGER.error("Verarbeitungsfehler in Zeile " + lvZeileCounter);
            LOGGER.error(lvZeile);
            exp.printStackTrace();
        }
 
        try
        {
        	lvIn.close();
        	lvFis.close();
        }
        catch (Exception exp)
        {
            LOGGER.error("Konnte Eingabe-Datei '" + pvInputFilename + "' nicht schliessen!");        	
        }

        try
        {
        	lvFos.close();
        }
        catch (Exception exp)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputFilename + "' nicht schliessen!");        	
        }
	}
	
	/**
	 * Startroutine
	 * @param argv
	 */
	public static void main(String[] argv)
	{
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("KundennummernUmstellung <ini-Datei>");
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
                String lvLoggingXML = lvReader.getPropertyString("KundennummernUmstellung", "log4jconfig", "Fehler");
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
                                        
                String lvImportVerzeichnis = lvReader.getPropertyString("KundennummernUmstellung", "ImportVerzeichnis", "Fehler");
                if (lvImportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                String lvExportVerzeichnis = lvReader.getPropertyString("KundennummernUmstellung", "ExportVerzeichnis", "Fehler");
                if (lvExportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                String lvKundennummernUmstellungDatei =  lvReader.getPropertyString("KundennummernUmstellung", "KundennummernUmstellungDatei", "Fehler");
                if (lvKundennummernUmstellungDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein KundennummernUmstellung-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvInputDatei =  lvReader.getPropertyString("KundennummernUmstellung", "InputDatei", "Fehler");
                if (lvInputDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein Input-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
                
                String lvOutputDatei =  lvReader.getPropertyString("KundennummernUmstellung", "OutputDatei", "Fehler");
                if (lvOutputDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein Output-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                new KundennummernUmstellung(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvKundennummernUmstellungDatei, lvInputDatei, lvOutputDatei);                
            }
        }
        
		System.exit(0);
	}
	
	/**
	 * Interne Klasse fuer die Daten der KundennummernUmstellung
	 */
	private class KundennummernUmstellungDaten
	{
		/**
		 * Kundennummer bei der Bremer Landesbank (BLB) 
		 */
		private String ivKdNrBLB;
		
		/**
		 * Kundennummer bei der Bundesbank
		 */
		private String ivKdNrBundesbank;

		/**
		 * Kundennummer bei der NORD/LB (NLB)
		 */
		private String ivKdNrNLB;
		
		/**
		 * Konstruktor - Initialisiert die Felder mit leeren Strings
		 */
		public KundennummernUmstellungDaten() 
		{	
			this.ivKdNrBLB = new String();
			this.ivKdNrBundesbank = new String();
			this.ivKdNrNLB = new String();
		}

		/**
		 * @return the ivKdNrBLB
		 */
		public String getKdNrBLB() 
		{
			return ivKdNrBLB;
		}

		/**
		 * @param pvKdNrBLB the ivKdNrBLB to set
		 */
		public void setKdNrBLB(String pvKdNrBLB) 
		{
			this.ivKdNrBLB = pvKdNrBLB;
		}

		/**
		 * @return the ivKdNrBundesbank
		 */
		public String getKdNrBundesbank() 
		{
			return ivKdNrBundesbank;
		}

		/**
		 * @param pvKdNrBundesbank the ivKdNrBundesbank to set
		 */
		public void setKdNrBundesbank(String pvKdNrBundesbank) 
		{
			this.ivKdNrBundesbank = pvKdNrBundesbank;
		}
		
		/**
		 * @return the ivKdNrNLB
		 */
		public String getKdNrNLB() 
		{
			return ivKdNrNLB;
		}

		/**
		 * @param pvKdNrNLB the ivKdNrNLB to set
		 */
		public void setKdNrNLB(String pvKdNrNLB) 
		{
			this.ivKdNrNLB = pvKdNrNLB;
		}
		
		/**
		 * Zerlegt den String in einzelne Felder
		 * @pvZeile Den zu zerlegenden String
		 */
		public void parseDaten(String pvZeile)
		{
		   this.setKdNrBLB(pvZeile.substring(0, pvZeile.indexOf(";")));
		   pvZeile = pvZeile.substring(pvZeile.indexOf(";") + 1);
		   this.setKdNrBundesbank(pvZeile.substring(0, pvZeile.indexOf(";")));
		   this.setKdNrNLB(pvZeile.substring(pvZeile.indexOf(";") + 1));
		}
		
		/**
		 * Schreibt die Daten in einen String
		 * @return Daten in einem String
		 */
		public String toString()
		{
			StringBuilder lvHelpStringBuilder = new StringBuilder();
			
			lvHelpStringBuilder.append("KdNrBLB: " + this.getKdNrBLB());
			lvHelpStringBuilder.append(" - KdNrBundesbank: " + this.getKdNrBundesbank());
			lvHelpStringBuilder.append(" - KdNrNLB: " + this.getKdNrNLB());
			
			return lvHelpStringBuilder.toString();
		}
	}
	
}
