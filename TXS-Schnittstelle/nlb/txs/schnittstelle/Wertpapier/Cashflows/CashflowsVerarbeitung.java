/*******************************************************************************
 * Copyright (c) 2016 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/
package nlb.txs.schnittstelle.Wertpapier.Cashflows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import nlb.txs.schnittstelle.LoanIQ.Cashflows.QuellsystemListe;
import nlb.txs.schnittstelle.LoanIQ.Cashflows.Daten.QuellsystemDaten;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSCashflowDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Wertpapier.Cashflows.Daten.Cashflow;

public class CashflowsVerarbeitung 
{
    // Logger fuer Cashflows
    private static Logger LOGGER_CASHFLOWS = Logger.getLogger("TXSCashflowsWertpapiereLogger");  
    
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
     * Dateiname der Quellsystem-Datei
     */
    private String ivQuellsystemDatei;
    
    /**
     * Zaehler fuer die Anzahl der Finanzgeschaefte
     */
    private int ivZaehlerCashflows;
    
    /**
     * Zaehler fuer die Anzahl der Finanzgeschaefte, fuer die Cashflows geschrieben wurden 
     */
    private int ivZaehlerWriteFinanzgeschaefte;
    
    /**
     * Cashflow 
     */
    private Cashflow ivCashflow;
    
    /**
     * 
     */
    private QuellsystemListe ivQuellsystemDatenListe;
    
    /**
     * 
     */
    private OutputDarlehenXML ivOutputDarlehenXML;
    
    /**
     * Konstruktor der Cashflow Verarbeitung 
     * @param pvReader 
     */
    public CashflowsVerarbeitung(IniReader pvReader)
    { 
        if (pvReader != null)
        {
            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_CASHFLOWS.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
                        
            ivImportVerzeichnis = pvReader.getPropertyString("CashflowsWertpapiere", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_CASHFLOWS.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("CashflowsWertpapiere", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_CASHFLOWS.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivCashflowsInputDatei =  pvReader.getPropertyString("CashflowsWertpapiere", "CashflowsInput-Datei", "Fehler");
            if (ivCashflowsInputDatei.equals("Fehler"))
            {
                LOGGER_CASHFLOWS.error("Kein CashflowsInput-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivCashflowsOutputDatei =  pvReader.getPropertyString("CashflowsWertpapiere", "CashflowsOutput-Datei", "Fehler");
            if (ivCashflowsOutputDatei.equals("Fehler"))
            {
                LOGGER_CASHFLOWS.error("Kein CashflowsOutput-Dateiname in der ini-Datei...");
                System.exit(1);
            }  
            
            ivQuellsystemDatei =  pvReader.getPropertyString("WertpapiereMAVIS", "Quellsystem-Datei", "Fehler");
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
     * Verarbeitung starten
     */
    private void startVerarbeitung()
    {    
    	// Einlesen der QuellsystemDaten
    	int lvZaehlerQuellsysteme = 0;
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
    			LOGGER_CASHFLOWS.error("Konnte Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDatei + " nicht oeffnen!");
    		}
    		String lvQuellsystemDatenZeile = new String();
    		try
    		{
                while ((lvQuellsystemDatenZeile = lvQuellsystemDatenIn.readLine()) != null)  // Lese Quellsystem-Datei
                {
                  if (lvQuellsystemDatenZeile != null)
                  {
                    //System.out.println(lvQuellsystemDatenZeile);
                    ivQuellsystemDatenListe.parseQuellsystemDaten(lvQuellsystemDatenZeile, 1); // Verarbeitungsmodus LoanIQ --> 1, Wertpapiere wie LoanIQ
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
    	LOGGER_CASHFLOWS.info("Anzahl Quellsysteme: " + lvZaehlerQuellsysteme);               
        
    	// Darlehen XML-Datei im TXS-Format
    	ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivCashflowsOutputDatei);
    	ivOutputDarlehenXML.openXML();
    	ivOutputDarlehenXML.printXMLStart();
    	ivOutputDarlehenXML.printTXSImportDatenStart();
        //ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()));

    	readCashflows(ivImportVerzeichnis + "\\" + ivCashflowsInputDatei);

    	ivOutputDarlehenXML.printTXSImportDatenEnde();
    	ivOutputDarlehenXML.closeXML();
            
    	LOGGER_CASHFLOWS.info(this.getStatistik());
    }
        
        /**
         * Liest die Cashflows ein
         */
        private void readCashflows(String pvDateiname)
        {
            ArrayList<Cashflow> lvListeCashflow = new ArrayList<Cashflow>();
            String lvAktuelleKontonummer = new String();
            
            ivZaehlerCashflows = 0;
            ivZaehlerWriteFinanzgeschaefte = 0;
            String lvZeile = null;
            ivCashflow = new Cashflow();
                  
            // Oeffnen der Dateien
            FileInputStream lvFis = null;
            File ivFileWertpapiereCashflows = new File(pvDateiname);
            try
            {
                lvFis = new FileInputStream(ivFileWertpapiereCashflows);
            }
            catch (Exception e)
            {
                LOGGER_CASHFLOWS.error("Konnte Cashflow-Datei nicht oeffnen!");
                return;
            }
        
            BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
                  
            try
            {
                while ((lvZeile = lvIn.readLine()) != null)  // Lese Cashflow-Datei
                {
                	if (lvZeile.startsWith("##"))
                	{
                		LOGGER_CASHFLOWS.info("Zeile: " + lvZeile);
                	}
                    else
                    {
                        ivCashflow = new Cashflow();
                        if (!ivCashflow.parseCashflow(lvZeile)) // Parsen der Felder
                        {
                            LOGGER_CASHFLOWS.error("Datenfehler!!!\n");
                        }
                        
                        if (lvAktuelleKontonummer.equals(ivCashflow.getISIN()))
                        {
                        	lvListeCashflow.add(ivCashflow);
                        }
                        else
                        {
                            if (!lvAktuelleKontonummer.isEmpty())
                            {
                              LOGGER_CASHFLOWS.info("Anzahl Cashflows - ISIN " + lvAktuelleKontonummer +": " + lvListeCashflow.size());
                              Collections.sort(lvListeCashflow);
                              verarbeiteCashflows(lvListeCashflow);
                            }
                            lvListeCashflow = new ArrayList<Cashflow>();
                            lvListeCashflow.add(ivCashflow);
                            lvAktuelleKontonummer = ivCashflow.getISIN();
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
                LOGGER_CASHFLOWS.error("Konnte WertpapiereCashflows-Datei nicht schliessen!");
            }           
        }

        /**
         * Liefert eine Statistik
         * @return String mit Statistik
         */
        private String getStatistik()
        {
            StringBuilder lvOut = new StringBuilder();
           
            lvOut.append(ivZaehlerCashflows + " Cashflows gelesen..." + StringKonverter.lineSeparator);
            lvOut.append(ivZaehlerWriteFinanzgeschaefte + " Finanzgeschaefte mit Cashflows geschrieben..." + StringKonverter.lineSeparator);
            return lvOut.toString();
        }

        /**
         * Verarbeite Cashflows
         * @param pvListeCashflow Liste der Cashflows zu einer ISIN
         */
        private void verarbeiteCashflows(ArrayList<Cashflow> pvListeCashflow)
        {       
            if (pvListeCashflow.size() == 0)
            {
              LOGGER_CASHFLOWS.info("Liste der Cashflows leer...");
              return;
            }
            QuellsystemDaten lvQuellsystemDaten = null;
            
            if (ivQuellsystemDatenListe.contains(pvListeCashflow.get(0).getISIN() + "_" + pvListeCashflow.get(0).getDepotnummer()))
            {
              lvQuellsystemDaten = ivQuellsystemDatenListe.getQuellsystemDaten(pvListeCashflow.get(0).getISIN() + "_" + pvListeCashflow.get(0).getDepotnummer());
            }
            else
            {
            	LOGGER_CASHFLOWS.info("Nichts gefunden: " + pvListeCashflow.get(0).getISIN() + "_" + pvListeCashflow.get(0).getDepotnummer());
            } 

            TXSFinanzgeschaeft lvFg = new TXSFinanzgeschaeft();
            if (lvQuellsystemDaten != null)
            {
              lvFg.setKey(lvQuellsystemDaten.getZielkontonummer());
            }
            else
            {
                lvFg.setKey(pvListeCashflow.get(0).getISIN());            
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
                  LOGGER_CASHFLOWS.info("Kein Quellsystem fuer Kontonummer " + pvListeCashflow.get(0).getISIN() + " vorhanden!");
                  return;
                }
            }
            else
            {
                LOGGER_CASHFLOWS.info("Konnte keine QuellsystemDaten fuer Kontonummer " + pvListeCashflow.get(0).getISIN() + " ermitteln!");
                return;
            }
            
            TXSCashflowDaten lvCfdaten; 
            ArrayList<TXSCashflowDaten> lvListeCashflows = new ArrayList<TXSCashflowDaten>();
            for (int i = 0; i < pvListeCashflow.size(); i++)
            {
                lvCfdaten = new TXSCashflowDaten();
                String lvBuchungsdatum = pvListeCashflow.get(i).getBuchungsdatum(); //.substring(6) + pvListeCashflow.get(i).getBuchungsdatum().substring(4,6) + pvListeCashflow.get(i).getBuchungsdatum().substring(0,4);
                if (lvQuellsystemDaten != null)
                {
                  lvCfdaten.setCfkey(lvQuellsystemDaten.getZielkontonummer() + "_" + lvBuchungsdatum);
                }
                else
                {
                  lvCfdaten.setCfkey(pvListeCashflow.get(i).getISIN() + "_" + lvBuchungsdatum);
                }
                
                lvCfdaten.setDatum(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvListeCashflow.get(i).getBuchungsdatum())));
                if (pvListeCashflow.get(i).getAblaufart().equals("11")) // || pvListeCashflow.get(i).getAblaufart().equals("12") || pvListeCashflow.get(i).getAblaufart().equals("13") ||
                    //pvListeCashflow.get(i).getAblaufart().equals("14") || pvListeCashflow.get(i).getAblaufart().equals("15") || pvListeCashflow.get(i).getAblaufart().equals("17"))
                {
                    lvCfdaten.setTbetrag(pvListeCashflow.get(i).getBetrag());
                }
                if (pvListeCashflow.get(i).getAblaufart().equals("21"))
                {
                	lvCfdaten.setZbetrag(pvListeCashflow.get(i).getBetrag());
                }
                lvCfdaten.setWhrg(pvListeCashflow.get(i).getBetragswaehrung());
                while (i < (pvListeCashflow.size() - 1) && pvListeCashflow.get(i).getBuchungsdatum().equals(pvListeCashflow.get(i + 1).getBuchungsdatum()))
                {
                    i++;
                    BigDecimal lvHelpTbetrag = StringKonverter.convertString2BigDecimal(lvCfdaten.getTbetrag());
                    BigDecimal lvHelpZbetrag = StringKonverter.convertString2BigDecimal(lvCfdaten.getZbetrag());
                    BigDecimal lvHelpWert = StringKonverter.convertString2BigDecimal(pvListeCashflow.get(i).getBetrag());
                    if (pvListeCashflow.get(i).getAblaufart().equals("11")) // || pvListeCashflow.get(i).getAblaufart().equals("13"))
                    {
                          lvHelpTbetrag = lvHelpTbetrag.add(lvHelpWert);
                          lvCfdaten.setTbetrag(lvHelpTbetrag.toString());
                    }
                    if (pvListeCashflow.get(i).getAblaufart().equals("21"))
                    {
                    	lvHelpZbetrag = lvHelpZbetrag.add(lvHelpWert);
                    	lvCfdaten.setZbetrag(lvHelpZbetrag.toString());
                    }
                }
                if (StringKonverter.convertString2Double(lvCfdaten.getTbetrag()) > 0.0 || StringKonverter.convertString2Double(lvCfdaten.getZbetrag()) > 0.0)
                {
                  lvListeCashflows.add(lvCfdaten);
                }
            }
            
            // Cashflows in Datei schreiben
            if (lvListeCashflows.size() > 0)
            {
                ivOutputDarlehenXML.printTransaktion(lvFg.printTXSTransaktionStart());
            	for (TXSCashflowDaten lvHelpCashflowDaten:lvListeCashflows)
            	{
                    ivOutputDarlehenXML.printTransaktion(lvHelpCashflowDaten.printTXSCashflowDaten());
            	}
                ivOutputDarlehenXML.printTransaktion(lvFg.printTXSTransaktionEnde());
                ivZaehlerWriteFinanzgeschaefte++;
            }
        }
}
