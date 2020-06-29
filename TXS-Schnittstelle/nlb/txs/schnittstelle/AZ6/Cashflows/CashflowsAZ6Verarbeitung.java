/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.AZ6.Cashflows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import nlb.txs.schnittstelle.LoanIQ.Cashflows.Daten.Cashflow;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.QuellsystemDaten.QuellsystemDaten;
import nlb.txs.schnittstelle.QuellsystemDaten.QuellsystemListe;
import nlb.txs.schnittstelle.Transaktion.TXSCashflowDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class CashflowsAZ6Verarbeitung
{
    // Logger fuer Cashflows
    private static Logger LOGGER_CASHFLOWS = Logger.getLogger("TXSCashflowsAZ6Logger");
    
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Import-Verzeichnis der Cashflows-Datei
     */
    private String ivImportVerzeichnis;
    
    /**
     * Export-Verzeichnis der TXS-Importdatei
     */
    private String ivExportVerzeichnis;
    
    /**
     * Dateiname der Cashflows-Datei
     */
    private String ivCashflowsInputDatei;
    
    /**
     * Dateiname der TXS-Importdatei
     */
    private String ivCashflowsOutputDatei;

    /**
     * Dateiname der AZ6 Quellsystem-Datei
     */
    private String ivQuellsystemDatei;

    /**
     * Liste von Kontonummern mit Quellsystem
     */
    private QuellsystemListe ivQuellsystemDatenListe;
    
    /**
     * Vorlaufsatz der Cashflow-Datei
     */
    private Vorlaufsatz ivVorlaufsatz;
       
    /**
     * Ein Cashflow 
     */
    private Cashflow ivCashflow;
    
    /**
     * Zaehler fuer die Anzahl der Vorlaufsaetze
     */
    private int ivZaehlerVorlaufsatz;
    
    /**
     * Zaehler fuer die Anzahl der Finanzgeschaefte
     */
    private int ivZaehlerCashflows;
    
    /**
     * Zaehler fuer die Anzahl der Finanzgeschaefte, fuer die Cashflows geschrieben wurden 
     */
    private int ivZaehlerWriteFinanzgeschaefte;
    
    /**
     * 
     */
    private OutputDarlehenXML ivOutputDarlehenXML;

    /**
     * Statistikvariablen
     */
    private BigDecimal ivSummeAblaufart17AAZ6PFBG = new BigDecimal("0.0");

    /**
     * Konstruktor fuer Verarbeitung Cashflows 
     * @param pvReader
     */
    public CashflowsAZ6Verarbeitung(IniReader pvReader)
    {
      LOGGER_CASHFLOWS = Logger.getLogger("TXSCashflowsAZ6Logger");

        if (pvReader != null)
        {
          ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
          if (ivInstitutsnummer.equals("Fehler"))
          {
            LOGGER_CASHFLOWS.error("Keine Institutsnummer in der ini-Datei...");
            System.exit(1);
          }

          ivImportVerzeichnis = pvReader.getPropertyString("CashflowsAZ6", "ImportVerzeichnis", "Fehler");
          if (ivImportVerzeichnis.equals("Fehler"))
          {
            LOGGER_CASHFLOWS.error("Kein Import-Verzeichnis in der ini-Datei...");
            System.exit(1);
          }

          ivExportVerzeichnis = pvReader.getPropertyString("CashflowsAZ6", "ExportVerzeichnis", "Fehler");
          if (ivExportVerzeichnis.equals("Fehler"))
          {
            LOGGER_CASHFLOWS.error("Kein Export-Verzeichnis in der ini-Datei...");
            System.exit(1);
          }

          ivCashflowsInputDatei =  pvReader.getPropertyString("CashflowsAZ6", "CashflowsInput-Datei", "Fehler");
          if (ivCashflowsInputDatei.equals("Fehler"))
          {
            LOGGER_CASHFLOWS.error("Kein CashflowsInput-Dateiname in der ini-Datei...");
            System.exit(1);
          }

          ivCashflowsOutputDatei =  pvReader.getPropertyString("CashflowsAZ6", "CashflowsOutput-Datei", "Fehler");
          if (ivCashflowsOutputDatei.equals("Fehler"))
          {
            LOGGER_CASHFLOWS.error("Kein CashflowsOutput-Dateiname in der ini-Datei...");
            System.exit(1);
          }

          ivQuellsystemDatei =  pvReader.getPropertyString("AZ6", "Quellsystem-Datei", "Fehler");
          if (ivQuellsystemDatei.equals("Fehler"))
          {
            LOGGER_CASHFLOWS.error("Kein Quellsystem-Dateiname in der ini-Datei...");
            System.exit(1);
          }

          // Verarbeitung starten
          startVerarbeitung();
        }
    }

    /**
     * 
     */
    private void startVerarbeitung()
    {    
        int lvZaehlerQuellsysteme = 0;

         // Einlesen der LoanIQQuellsystemDaten
          File lvQuellsystemDatenFile = new File(ivImportVerzeichnis + "\\" + ivQuellsystemDatei);
          ivQuellsystemDatenListe = new QuellsystemListe();
          if (lvQuellsystemDatenFile != null && lvQuellsystemDatenFile.exists())
          {
            FileInputStream lvQuellsystemDatenIs = null;
            BufferedReader lvQuellsystemDatenIn = null;
            try
            {
              lvQuellsystemDatenIs = new FileInputStream(lvQuellsystemDatenFile);
              lvQuellsystemDatenIn = new BufferedReader(new InputStreamReader(lvQuellsystemDatenIs));
            }
            catch (Exception e)
            {
              LOGGER_CASHFLOWS.info("Konnte Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDatei + " nicht oeffnen!");
            }
            String lvQuellsystemDatenZeile = new String();
            try
            {
              while ((lvQuellsystemDatenZeile = lvQuellsystemDatenIn.readLine()) != null)  // Lese Quellsystem-Datei
              {
                if (lvQuellsystemDatenZeile != null)
                {
                  //System.out.println(lvQuellsystemDatenZeile);
                  ivQuellsystemDatenListe.parseQuellsystemDaten(lvQuellsystemDatenZeile, LOGGER_CASHFLOWS);
                  lvZaehlerQuellsysteme++;
                }
              }
            }
            catch (Exception e)
            {
              LOGGER_CASHFLOWS.error("Fehler beim Verarbeiten einer Zeile:" + lvQuellsystemDatenZeile);
              e.printStackTrace();
            }
            try
            {
              lvQuellsystemDatenIn.close();
              lvQuellsystemDatenIs.close();
            }
            catch (Exception e)
            {
              LOGGER_CASHFLOWS.error("Konnte Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDatei + " nicht schliessen!");
            }
          }
          else
          {
            LOGGER_CASHFLOWS.info("Keine Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDatei + " gefunden...");
          }

        LOGGER_CASHFLOWS.info("Anzahl QuellsystemDaten: " + lvZaehlerQuellsysteme);
    	
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivCashflowsOutputDatei, LOGGER_CASHFLOWS);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
        ivOutputDarlehenXML.printTXSImportDatenStart();
        
        readCashflows(ivImportVerzeichnis + "\\" + ivCashflowsInputDatei);

        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML();
        
        LOGGER_CASHFLOWS.info(printStatistik());
    }
    
    /**
     * Liest die Cashflows
     */
    private void readCashflows(String pvDateiname)
    {
        ArrayList<Cashflow> lvListeCashflow = new ArrayList<Cashflow>();
        String lvAktuelleKontonummer = new String();
        
        ivZaehlerVorlaufsatz = 0;
        ivZaehlerCashflows = 0;
        ivZaehlerWriteFinanzgeschaefte = 0;
        String lvZeile = null;
        ivVorlaufsatz = new Vorlaufsatz(LOGGER_CASHFLOWS);
        ivCashflow = new Cashflow();
              
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileLoanIQ = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileLoanIQ);
        }
        catch (Exception e)
        {
            LOGGER_CASHFLOWS.error("Konnte Cashflow-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        boolean lvStart = true;
              
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lese Cashflow-Datei
            {
                if (lvStart)
                {
                    ivVorlaufsatz.parseVorlaufsatz(lvZeile);
                    ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()));
                    ivZaehlerVorlaufsatz++;
                    lvStart = false;
                    
                }
                else
                {
                    ivCashflow = new Cashflow();
                    if (!ivCashflow.parseCashflow(lvZeile)) // Parsen der Felder
                    {
                        LOGGER_CASHFLOWS.error("Datenfehler!!!\n");
                    }
                    if (lvAktuelleKontonummer.equals(ivCashflow.getKontonummer()))
                    {
                      lvListeCashflow.add(ivCashflow);
                    }
                    else
                    {
                        if (!lvAktuelleKontonummer.isEmpty())
                        {
                          LOGGER_CASHFLOWS.info("Anzahl Cashflows - Kontonummer " + lvAktuelleKontonummer +": " + lvListeCashflow.size());
                          Collections.sort(lvListeCashflow);
                          verarbeiteCashflows(lvListeCashflow);
                        }
                        lvListeCashflow = new ArrayList<Cashflow>();
                        lvListeCashflow.add(ivCashflow);
                        lvAktuelleKontonummer = ivCashflow.getKontonummer();
                    }

                    ivZaehlerCashflows++;
                }
            }
        }
        catch (Exception e)
        {
            LOGGER_CASHFLOWS.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
        if (!lvAktuelleKontonummer.isEmpty())
        {       
          Collections.sort(lvListeCashflow);
          verarbeiteCashflows(lvListeCashflow);
          LOGGER_CASHFLOWS.info("Anzahl Cashflows - Kontonummer " + lvAktuelleKontonummer +": " + lvListeCashflow.size());
        }
              
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_CASHFLOWS.error("Konnte AZ6-Datei nicht schliessen!");
        }           
    }

    /**
     * Liefert eine Statistik als String
     * @return 
     */
    private String printStatistik()
    {
        StringBuilder lvOut = new StringBuilder();
        lvOut.append(ivVorlaufsatz.toString());
       
        lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerCashflows + " Cashflows gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerWriteFinanzgeschaefte + " Finanzgeschaefte mit Cashflows geschrieben..." + StringKonverter.lineSeparator);
        lvOut.append("AAZ6PFBG - Summe Ablaufart 17: " + ivSummeAblaufart17AAZ6PFBG + StringKonverter.lineSeparator);

        return lvOut.toString();
    }

    /**
     * Verarbeite Cashflows
     * @param pvListeCashflow 
     */
    private void verarbeiteCashflows(ArrayList<Cashflow> pvListeCashflow)
    {
        QuellsystemDaten lvQuellsystemDaten = null; 
        
        if (pvListeCashflow.size() == 0)
        {
          LOGGER_CASHFLOWS.info("Liste der Cashflows leer...");
          return;
        }

		  if (ivQuellsystemDatenListe.contains(pvListeCashflow.get(0).getKontonummer()))
		  {
			  lvQuellsystemDaten = ivQuellsystemDatenListe.getQuellsystemDaten(pvListeCashflow.get(0).getKontonummer());
		  }
		
        TXSFinanzgeschaeft lvFg = new TXSFinanzgeschaeft();
        if (lvQuellsystemDaten != null)        
        {
        	lvFg.setKey(lvQuellsystemDaten.getZielkontonummer());
        }
        else
        {
        	lvFg.setKey(pvListeCashflow.get(0).getKontonummer());
        }
        lvFg.setOriginator(ValueMapping.changeMandant(pvListeCashflow.get(0).getInstitutsnummer()));
        if (lvQuellsystemDaten != null)
        {

          if (!lvQuellsystemDaten.getQuellsystem().isEmpty())
            {
              lvFg.setQuelle(lvQuellsystemDaten.getQuellsystem());
            }
            else
            {
              LOGGER_CASHFLOWS.info("Kein Quellsystem fuer Kontonummer " + pvListeCashflow.get(0).getKontonummer() + " vorhanden!");
              return;
            }
        }
        else
        {
            LOGGER_CASHFLOWS.info("Konnte keine QuellsystemDaten fuer Kontonummer " + pvListeCashflow.get(0).getKontonummer() + " ermitteln!");
            return;
        }

        /*
      // Bei Zeros Zins und Tilgung am Ende (bei Faelligkeit) hinterlegen
		if (lvQuellsystemDaten.getMerkmalZinstyp().equals("ZERO"))
		{
		  Cashflow lvErsteCashflow = pvListeCashflow.get(0);
		  ArrayList<Cashflow> lvHelpListeCashflow = new ArrayList<Cashflow>();
		  for (Cashflow lvHelpCashflow:pvListeCashflow)
      {
        if (lvHelpCashflow.getArtNummer().equals("17"))
        {
          lvHelpListeCashflow.add(lvHelpCashflow);
        }
      }
		  pvListeCashflow = lvHelpListeCashflow;

		  LOGGER_CASHFLOWS.info(lvQuellsystemDaten.getUrsprungskontonummer() + " - " + lvQuellsystemDaten.getNennbetrag());
		  LOGGER_CASHFLOWS.info(lvQuellsystemDaten.getUrsprungskontonummer() + " - " + lvQuellsystemDaten.getAmortisierungsbetrag());
		  // Tilgung bei Zeros
      BigDecimal lvNennbetrag = StringKonverter.convertString2BigDecimal(lvQuellsystemDaten.getNennbetrag());
      BigDecimal lvAmortisierungsbetrag = StringKonverter.convertString2BigDecimal(lvQuellsystemDaten.getAmortisierungsbetrag());
      BigDecimal lvRestkapital = lvNennbetrag.subtract(lvAmortisierungsbetrag);
      LOGGER_CASHFLOWS.info("Zero - Tilgung " + lvErsteCashflow.getISIN() + "_" + lvErsteCashflow.getKontonummer() + ":" + lvRestkapital.toPlainString());
      Cashflow lvZeroTilgungCashflow = new Cashflow();
      lvZeroTilgungCashflow.setArtNummer("ZET"); // Zero-Tilgung
      lvZeroTilgungCashflow.setBruttoNetto("N"); // Netto
      lvZeroTilgungCashflow.setBuchungsdatum(DatumUtilities.changeDatePoints(lvQuellsystemDaten.getFaelligkeit().replace("-", "")));
      lvZeroTilgungCashflow.setInstitutsnummer(lvErsteCashflow .getInstitutsnummer());
      lvZeroTilgungCashflow.setISIN(lvErsteCashflow .getISIN());
      lvZeroTilgungCashflow.setKontonummer(lvErsteCashflow .getKontonummer());
      lvZeroTilgungCashflow.setQuellsystem(lvErsteCashflow .getQuellsystem());
      lvZeroTilgungCashflow.setWaehrung(lvErsteCashflow .getWaehrung());
      lvZeroTilgungCashflow.setWert(lvRestkapital.toPlainString());
      lvZeroTilgungCashflow.setWertstellungsdatum(DatumUtilities.changeDatePoints(lvQuellsystemDaten.getFaelligkeit().replace("-", "")));
      pvListeCashflow.add(lvZeroTilgungCashflow);

      // Zinsen bei Zeros
      //BigDecimal lvZinsbetrag = lvNennbetrag.subtract(lvRestkapital);
      LOGGER_CASHFLOWS.info("Zero - Zinsen " + lvErsteCashflow.getISIN() + "_" + lvErsteCashflow.getKontonummer() + ":" + lvQuellsystemDaten.getAmortisierungsbetrag());
			Cashflow lvZeroZinsenCashflow = new Cashflow();
      lvZeroZinsenCashflow.setArtNummer("ZEZ"); // Zero-Zinsen
      lvZeroZinsenCashflow.setBruttoNetto("N"); // Netto
      lvZeroZinsenCashflow.setBuchungsdatum(DatumUtilities.changeDatePoints(lvQuellsystemDaten.getFaelligkeit().replace("-", "")));
      lvZeroZinsenCashflow.setInstitutsnummer(lvErsteCashflow.getInstitutsnummer());
      lvZeroZinsenCashflow.setISIN(lvErsteCashflow.getISIN());
      lvZeroZinsenCashflow.setKontonummer(lvErsteCashflow.getKontonummer());
      lvZeroZinsenCashflow.setQuellsystem(lvErsteCashflow.getQuellsystem());
      lvZeroZinsenCashflow.setWaehrung(lvErsteCashflow .getWaehrung());
      lvZeroZinsenCashflow.setWert(lvQuellsystemDaten.getAmortisierungsbetrag());
      lvZeroZinsenCashflow.setWertstellungsdatum(DatumUtilities.changeDatePoints(lvQuellsystemDaten.getFaelligkeit().replace("-", "")));
			pvListeCashflow.add(lvZeroZinsenCashflow);
		} */

        String lvManTilg = "0";
        String lvManZins = "0";
        ivOutputDarlehenXML.printTransaktion(lvFg.printTXSTransaktionStart());
        
        TXSCashflowDaten lvCfdaten = null; 
        for (int i = 0; i < pvListeCashflow.size();i++)
        {
            //LOGGER_CASHFLOWS.info("Cashflow: " + pvListeCashflow.get(i).getKontonummer() + " - " + pvListeCashflow.get(i).getArtNummer() + " - " + pvListeCashflow.get(i).getBuchungsdatum() + " - " + pvListeCashflow.get(i).getBruttoNetto() + " - " + pvListeCashflow.get(i).getWert());

            lvCfdaten = new TXSCashflowDaten();
            String lvBuchungsdatum = pvListeCashflow.get(i).getBuchungsdatum().substring(6) + pvListeCashflow.get(i).getBuchungsdatum().substring(3,5) + pvListeCashflow.get(i).getBuchungsdatum().substring(0,2);
            if (lvQuellsystemDaten != null)
            {
             	if (lvQuellsystemDaten.getQuellsystem().startsWith("P"))
            	{
                    lvCfdaten.setCfkey(lvQuellsystemDaten.getZielkontonummer() + "_" + lvQuellsystemDaten.getUrsprungskontonummer() + "_" + lvBuchungsdatum);            		
            	}
            	else
            	{
                   lvCfdaten.setCfkey(lvQuellsystemDaten.getZielkontonummer() + "_" + lvBuchungsdatum);
            	}
            }
            else
            {
              lvCfdaten.setCfkey(pvListeCashflow.get(i).getKontonummer() + "_" + lvBuchungsdatum);
            }
            
            lvCfdaten.setDatum(DatumUtilities.changeDate(pvListeCashflow.get(i).getBuchungsdatum()));
            if (lvQuellsystemDaten.getQuellsystem().startsWith("P"))
            {
            	if (pvListeCashflow.get(i).getArtNummer().equals("11") || pvListeCashflow.get(i).getArtNummer().equals("12") || pvListeCashflow.get(i).getArtNummer().equals("13") ||
            		pvListeCashflow.get(i).getArtNummer().equals("14") || pvListeCashflow.get(i).getArtNummer().equals("15"))
            		
            	{
             		  lvCfdaten.setTbetrag(pvListeCashflow.get(i).getWert());
            	}

            	/*
              if (pvListeCashflow.get(i).getArtNummer().equals("17") && lvQuellsystemDaten.getMerkmalZinstyp().equals("ZERO"))
              {
                BigDecimal lvNennbetrag = StringKonverter.convertString2BigDecimal(lvQuellsystemDaten.getNennbetrag());
                BigDecimal lvAmortisierungsbetrag = StringKonverter.convertString2BigDecimal(lvQuellsystemDaten.getAmortisierungsbetrag());
                BigDecimal lvRestkapital = lvNennbetrag.subtract(lvAmortisierungsbetrag);

                BigDecimal lvResultZinsen = StringKonverter.convertString2BigDecimal(pvListeCashflow.get(i).getWert()).subtract(lvRestkapital);
                LOGGER_CASHFLOWS.info("Zero - Kuendigungstermin - Zinsen " + pvListeCashflow.get(0).getISIN() + "_" + pvListeCashflow.get(0).getKontonummer() + ":" + lvResultZinsen.toPlainString());
                lvCfdaten.setTbetrag(lvResultZinsen.toPlainString());
              } */

            	if (pvListeCashflow.get(i).getArtNummer().equals("21"))
            	{
             			lvCfdaten.setZbetrag(pvListeCashflow.get(i).getWert());
            	}
            }
            else
            {
               	if (pvListeCashflow.get(i).getArtNummer().equals("11") || pvListeCashflow.get(i).getArtNummer().equals("12") || pvListeCashflow.get(i).getArtNummer().equals("13") ||
                	pvListeCashflow.get(i).getArtNummer().equals("14") || pvListeCashflow.get(i).getArtNummer().equals("15") || pvListeCashflow.get(i).getArtNummer().equals("17"))
                	{
                		lvCfdaten.setTbetrag(pvListeCashflow.get(i).getWert());
                	}
                	if (pvListeCashflow.get(i).getArtNummer().equals("21"))
                	{
                			lvCfdaten.setZbetrag(pvListeCashflow.get(i).getWert());
                	}
            }

            /*
          //Zero-Tilgung anhaengen
          if (pvListeCashflow.get(i).getArtNummer().equals("ZET"))
          {
            if (ivModus == CashflowsVerarbeitung.LOANIQ)
            {
              //lvHelpTbetrag = lvHelpTbetrag.add(lvHelpWert);
              lvCfdaten.setTbetrag(pvListeCashflow.get(i).getWert());
              LOGGER_CASHFLOWS.info("ZET: "  + lvCfdaten.getCfkey() + " - " + pvListeCashflow.get(i).getWert());
            }
          }

          //Zero-Zinsen anhaengen
          if (pvListeCashflow.get(i).getArtNummer().equals("ZEZ"))
          {
            if (ivModus == CashflowsVerarbeitung.LOANIQ)
            {
              //lvHelpZbetrag = lvHelpZbetrag.add(lvHelpWert);
              lvCfdaten.setZbetrag(pvListeCashflow.get(i).getWert());
              LOGGER_CASHFLOWS.info("ZEZ: "  + lvCfdaten.getCfkey() + " - " + pvListeCashflow.get(i).getWert());
            }
          } */

          if (pvListeCashflow.get(i).getArtNummer().equals("17"))
        	{		 
        		if (lvFg.getQuelle().equals("AAZ6PFBG"))
        		{
        			ivSummeAblaufart17AAZ6PFBG = ivSummeAblaufart17AAZ6PFBG.add(StringKonverter.convertString2BigDecimal(pvListeCashflow.get(i).getWert()));
        		}
        	}

            lvCfdaten.setWhrg(pvListeCashflow.get(i).getWaehrung());
            while (i < (pvListeCashflow.size() - 1) && pvListeCashflow.get(i).getBuchungsdatum().equals(pvListeCashflow.get(i + 1).getBuchungsdatum()))
            {
                i++;
                BigDecimal lvHelpTbetrag = StringKonverter.convertString2BigDecimal(lvCfdaten.getTbetrag());
                BigDecimal lvHelpZbetrag = StringKonverter.convertString2BigDecimal(lvCfdaten.getZbetrag());
                //LOGGER_CASHFLOWS.info(lvCfdaten.getCfkey() + " - " + lvCfdaten.getTbetrag() + " - " + lvCfdaten.getZbetrag());
                BigDecimal lvHelpWert = StringKonverter.convertString2BigDecimal(pvListeCashflow.get(i).getWert());
                if (pvListeCashflow.get(i).getArtNummer().equals("11") || pvListeCashflow.get(i).getArtNummer().equals("13"))
                {
                  //if (!lvQuellsystemDaten.getMerkmalZinstyp().equals("ZERO"))
                  //{
                      lvHelpTbetrag = lvHelpTbetrag.add(lvHelpWert);
                      lvCfdaten.setTbetrag(lvHelpTbetrag.toString());
                      //LOGGER_CASHFLOWS.info("Summierung: " + lvCfdaten.getCfkey() + " - " + lvCfdaten.getTbetrag() + " - " + lvCfdaten.getZbetrag());
                  //}
                }

              if (pvListeCashflow.get(i).getArtNummer().equals("21"))
                {
                	//if (ivModus == CashflowsVerarbeitung.LOANIQ)
                	//{
                		//if (!lvQuellsystemDaten.getMerkmalZinstyp().equals("ZERO"))
                		//{
                			lvHelpZbetrag = lvHelpZbetrag.add(lvHelpWert);
                			lvCfdaten.setZbetrag(lvHelpZbetrag.toString());
                		//}
                	//}
                }

              /*
              //Zero-Tilgung anhaengen
              if (pvListeCashflow.get(i).getArtNummer().equals("ZET"))
              {
                if (ivModus == CashflowsVerarbeitung.LOANIQ)
                {
                  lvHelpTbetrag = lvHelpTbetrag.add(lvHelpWert);
                  lvCfdaten.setTbetrag(lvHelpTbetrag.toPlainString());
                  //LOGGER_CASHFLOWS.info("ZET: "  + lvCfdaten.getCfkey() + " - " + lvHelpTbetrag.toPlainString());
                }
              }

              //Zero-Zinsen anhaengen
              if (pvListeCashflow.get(i).getArtNummer().equals("ZEZ"))
              {
                if (ivModus == CashflowsVerarbeitung.LOANIQ)
                {
                  lvHelpZbetrag = lvHelpZbetrag.add(lvHelpWert);
                  lvCfdaten.setZbetrag(lvHelpZbetrag.toPlainString());
                  //LOGGER_CASHFLOWS.info("ZEZ: "  + lvCfdaten.getCfkey() + " - " + lvHelpZbetrag.toPlainString());
                }
              } */

            }
            // Es wird ueberprueft, ob Tilgung oder Zinsen geliefert werden
            if (StringKonverter.convertString2Double(lvCfdaten.getTbetrag()) > 0.0)
            {
            	lvManTilg = "1";
            }

            if (StringKonverter.convertString2Double(lvCfdaten.getZbetrag()) > 0.0)
            {
            	lvManZins = "1";
            }
            
            if (StringKonverter.convertString2Double(lvCfdaten.getTbetrag()) > 0.0 || StringKonverter.convertString2Double(lvCfdaten.getZbetrag()) > 0.0)
            { 
              ivOutputDarlehenXML.printTransaktion(lvCfdaten.printTXSCashflowDaten());
            }
        }
        
        ivOutputDarlehenXML.printTransaktion(lvFg.printTXSTransaktionEnde());

        // Stimmt 'mantilg' und 'manzins'
        if (ivQuellsystemDatenListe.get(pvListeCashflow.get(0).getKontonummer()) != null)
        {
          if (!ivQuellsystemDatenListe.get(pvListeCashflow.get(0).getKontonummer()).getManTilg().equals(lvManTilg))
          {
            LOGGER_CASHFLOWS.info("Unterschiedliche Tilgungsschalter:" + pvListeCashflow.get(0).getKontonummer() + ";" + lvFg.getKey() + ";" + lvFg.getQuelle() + ";" + lvManTilg + ";" + ivQuellsystemDatenListe.get(pvListeCashflow.get(0).getKontonummer()).getManTilg());
          }

          if (!ivQuellsystemDatenListe.get(pvListeCashflow.get(0).getKontonummer()).getManZins().equals(lvManZins))
          {
            LOGGER_CASHFLOWS.info("Unterschiedliche Zinsschalter:" + pvListeCashflow.get(0).getKontonummer() + ";" + lvFg.getKey() + ";" + lvFg.getQuelle() + ";" + lvManZins + ";" + ivQuellsystemDatenListe.get(pvListeCashflow.get(0).getKontonummer()).getManZins());
          }
        }
        else
        {
          LOGGER_CASHFLOWS.info("Mantilg/Manzins - Keine QuellsystemDaten gefunden - " + pvListeCashflow.get(0).getKontonummer());
        }

        ivZaehlerWriteFinanzgeschaefte++;
    }
    
}
