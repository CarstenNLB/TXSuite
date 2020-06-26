/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Filtern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetragListe;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.OutputObjektZuweisungsbetrag;
import nlb.txs.schnittstelle.RefiRegister.RefiRegisterFilterElement;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Diese Klasse zerlegt die "gro�e" Darlehensdatei in registerbezogene Dateien (Hypotheken, Kommunal, Schiffe, Flugzeuge, Deckungspooling,...).
 * Ebenfalls werden die Kunden- und Buergennummern in KundeRequest-Dateien geschrieben.
 * @author Carsten Tepper
 */
public class DarlehenFiltern 
{
    /**
     * log4j-Logger
     */
    private static Logger LOGGER = Logger.getLogger("TXSDarlehenFilternLogger"); 
   
    /**
     * Ausgabedatei der Kundennummern
     */
    private KundennummernOutput ivKundennummernOutput;
    
    /**
     * Ausgabedatei der Kundennummern von Durchleitungskredite
     */
    private KundennummernOutput ivKundennummernOutputDlk;
    
    /**
     * Ausgabedatei der Kundennummern von RefiRegister-Geschaeften
     */
    private KundennummernOutput ivKundennummernOutputRefiRegister;
    
    /**
     * Ausgabedatei der Kundennummern von LettreDeGage-Gesch�ften
     */
    private KundennummernOutput ivKundennummernOutputLettreDeGage;

    /**
     * Ausgabedatei der Kundennummern von KEV
     */
    private KundennummernOutput ivKundennummernOutputKEV;

    /**
     * Liste von Kundennummern der PfandBG-Geschaefte
     */
    private KundeListe ivKnrListe;
        
    /**
     * Liste von Kundennummern der Durchleitungskredite
     */
    private KundeListe ivDlkKnrListe;
    
    /**
     * Liste von Kundennummern der RefiRegister-Geschaefte
     */
    private KundeListe ivRefiRegisterKnrListe;
    
    /**
     * Liste von Kundennummern der LettreDeGage-Geschaefte
     */
    private KundeListe ivLettreDeGageKnrListe;

    /**
     * Liste von Kundennummern der KEV-Geschaefte
     */
    private KundeListe ivKEVKnrListe;

    // FileInput- und FileOutputStreams
    private FileInputStream ivFis = null;
    private FileOutputStream ivFos = null;
    private FileOutputStream ivKonsortialFos = null;
    private FileOutputStream ivDppFos = null;
    private FileOutputStream ivSchiffeFos = null;
    private FileOutputStream ivFlugzeugeFos = null;
    private FileOutputStream ivDurchleitungskrediteAktivFos = null;
    private FileOutputStream ivDurchleitungskreditePassivFos = null;
    private FileOutputStream ivOEPGFos = null;
    private FileOutputStream ivKEVFos = null;
    private FileOutputStream ivAusplatzierungsmerkmalFos = null;
    private FileOutputStream ivKonsortialgeschaefteFos = null;
    private FileOutputStream ivAktivkontenDatenFos = null;
    private FileOutputStream ivDarlehenLettreDeGageFos = null;
    private FileOutputStream ivDarlehenDeepSeaFos = null;
    
    /**
     * Zuweisungsbetraege fuer die Objekte herausschreiben
     */
    private OutputObjektZuweisungsbetrag ivOozw;
    
    /**
     * Restliche Darlehen in einer Liste von Strings
     */
    private ListeObjekte ivListeObjekte;
    
    /**
     * Unterkonten bei Konsortialdarlehen in einer Liste von Strings
     */
    private ListeObjekte ivListePassivObjekte;
    
    /**
     * Darlehen von DPP in einer Liste von Strings
     */
    private ListeObjekte ivListeDppObjekte;

    /**
     * Schiffedarlehen in einer Liste von Strings
     */
    private ListeObjekte ivListeSchiffeObjekte;
 
    /**
     * Flugzeugedarlehen in einer Liste von Strings
     */
    private ListeObjekte ivListeFlugzeugeObjekte;

    /**
     * DarlehenOEPG in einer Liste von Strings
     */
    private ListeObjekte ivListeOEPGObjekte;
    
    /**
     * Durchleitungskredite Aktiv in einer Liste von Strings
     */
    private ListeObjekte ivListeDurchleitungskrediteAktiv;
    
    /**
     * Durchleitungskredite Passiv in einer Liste von Strings
     */
    private ListeObjekte ivListeDurchleitungskreditePassiv;
   
    /**
     * DarlehenLettreDeGage in einer Liste von Strings
     */
    private ListeObjekte ivListeDarlehenLettreDeGage;
    
    /**
     * DarlehenDeepSea in einer Liste von Strings
     */
    private ListeObjekte ivListeDarlehenDeepSea;

    /**
     * DarlehenKEV in einer Liste von Strings
     */
    private ListeObjekte ivListeKEVObjekte;

    /**
     * Zaehlervariablen fuer die Statistik
     */
    // Anzahl Kundennummern Pfandbrief
    private int ivAnzahlKundennrPfandbrief;
    // Anzahl Buergennummern Pfandbrief
    private int ivAnzahlBuergennrPfandbrief;
    // Anzahl Kundennummern Konsortial
    private int ivAnzahlKundennrRefiRegister;
    // Anzahl Kundennummern LettreDeGage
    private int ivAnzahlKundennrLettreDeGage;
    // Anzahl Kundennummern KEV
    private int ivAnzahlKundennrKEV;
    // Anzahl Kredite mit Deckungskennzeichen 'A'
    private int ivAnzahlAltfaelle;
    // Anzahl Kredite PfandBG
    private int ivAnzahlTXS;
    // Anzahl Kredite Deckungspooling
    private int ivAnzahlDPP;
    // Anzahl Kredite Schiffspfandbrief
    private int ivAnzahlSchiffe;
    // Anzahl Kredite Flugzeugpfandbrief
    private int ivAnzahlFlugzeuge;
    // Anzahl Kredite LettreDeGage
    private int ivAnzahlLettreDeGage;
    // Anzahl Kredite DeepSea
    private int ivAnzahlDeepSea;
    // Anzahl Durchleitungskredite Aktiv
    private int ivAnzahlDurchleitungskrediteAktiv;
    // Anzahl Durchleitungskredite Passiv
    private int ivAnzahlDurchleitungskreditePassiv;
    // Anzahl Kredite KEV
    private int ivAnzahlKEV;

    /**
     * Liste von RefiRegisterFilterElemente
     */
    private HashMap<String, RefiRegisterFilterElement> ivListeRefiRegisterFilterElemente;
    
    /**
     * Startroutine
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("DarlehenFiltern <ini-Datei>");
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
                String lvLoggingXML = lvReader.getPropertyString("DarlehenFiltern", "log4jconfig", "Fehler");
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
                                        
                String lvImportVerzeichnis = lvReader.getPropertyString("DarlehenFiltern", "ImportVerzeichnis", "Fehler");
                if (lvImportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                String lvExportVerzeichnis = lvReader.getPropertyString("DarlehenFiltern", "ExportVerzeichnis", "Fehler");
                if (lvExportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenInput =  lvReader.getPropertyString("DarlehenFiltern", "DarlehenInput", "Fehler");
                if (lvDarlehenInput.equals("Fehler"))
                {
                    LOGGER.error("Kein DarlehenInput-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
               
                String lvDarlehenOutput = lvReader.getPropertyString("DarlehenFiltern", "DarlehenOutput", "Fehler");
                if (lvDarlehenOutput.equals("Fehler"))
                {
                    LOGGER.error("Kein DarlehenOutput-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenKonsortial = lvReader.getPropertyString("DarlehenFiltern", "DarlehenKonsortial", "Fehler");
                if (lvDarlehenKonsortial.equals("Fehler"))
                {
                    LOGGER.error("Kein Konsortialdarlehen-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenDPP = lvReader.getPropertyString("DarlehenFiltern", "DarlehenDPP", "Fehler");
                if (lvDarlehenDPP.equals("Fehler"))
                {
                    LOGGER.error("Kein Deckungspooling-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenSchiffe = lvReader.getPropertyString("DarlehenFiltern", "DarlehenSchiffe", "Fehler");
                if (lvDarlehenSchiffe.equals("Fehler"))
                {
                    LOGGER.error("Kein DarlehenSchiffe-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenFlugzeuge = lvReader.getPropertyString("DarlehenFiltern", "DarlehenFlugzeuge", "Fehler");
                if (lvDarlehenFlugzeuge.equals("Fehler"))
                {
                    LOGGER.error("Kein DarlehenFlugzeuge-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenOEPG = lvReader.getPropertyString("DarlehenFiltern", "DarlehenOEPG", "Fehler");
                if (lvDarlehenOEPG.equals("Fehler"))
                {
                    LOGGER.error("Kein DarlehenOEPG-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenKEV = lvReader.getPropertyString("DarlehenFiltern", "DarlehenKEV", "Fehler");
                if (lvDarlehenKEV.equals("Fehler"))
                {
                    LOGGER.error("Kein DarlehenKEV-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDurchleitungskrediteAktiv = lvReader.getPropertyString("DarlehenFiltern", "DurchleitungskrediteAktiv", "Fehler");
                if (lvDurchleitungskrediteAktiv.equals("Fehler"))
                {
                    LOGGER.error("Kein DurchleitungskrediteAktiv-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDurchleitungskreditePassiv = lvReader.getPropertyString("DarlehenFiltern", "DurchleitungskreditePassiv", "Fehler");
                if (lvDurchleitungskreditePassiv.equals("Fehler"))
                {
                    LOGGER.error("Kein DurchleitungskreditePassiv-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenDeepSea = lvReader.getPropertyString("DarlehenFiltern", "DarlehenDeepSea", "Fehler");
                if (lvDarlehenDeepSea.equals("Fehler"))
                {
                    LOGGER.error("Kein DarlehenDeepSea-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvDarlehenLettreDeGage = lvReader.getPropertyString("DarlehenFiltern", "DarlehenLettreDeGage", "Fehler");
                if (lvDarlehenLettreDeGage.equals("Fehler"))
                {
                    LOGGER.error("Kein DarlehenLettreDeGage-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
                
                String lvObjektZWOutput = lvReader.getPropertyString("DarlehenFiltern", "OZWOutput", "Fehler");
                if (lvObjektZWOutput.equals("Fehler"))
                {
                    LOGGER.error("Kein ObjektZWOutput-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvKundeOutput = lvReader.getPropertyString("DarlehenFiltern", "RequestDatei", "Fehler");
                if (lvKundeOutput.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeRequest-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
                
                String lvKundeDlkOutput = lvReader.getPropertyString("DarlehenFiltern", "RequestDlkDatei", "Fehler");
                if (lvKundeDlkOutput.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeRequest-Dateiname fuer Durchleitungskredite in der ini-Datei...");
                    System.exit(1);
                }

                String lvKundeKonsortialOutput = lvReader.getPropertyString("DarlehenFiltern", "RequestKonsortialDatei", "Fehler");
                if (lvKundeKonsortialOutput.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeRequest-Dateiname fuer Konsortialkredite in der ini-Datei...");
                    System.exit(1);
                }

                String lvKundeLettreDeGageOutput = lvReader.getPropertyString("DarlehenFiltern", "RequestLettreDeGageDatei", "Fehler");
                if (lvKundeLettreDeGageOutput.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeRequest-Dateiname fuer LettreDeGage-Geschaefte in der ini-Datei...");
                    System.exit(1);
                }

                String lvKundeKEVOutput = lvReader.getPropertyString("DarlehenFiltern", "RequestKEVDatei", "Fehler");
                if (lvKundeKEVOutput.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeRequest-Dateiname fuer KEV-Geschaefte in der ini-Datei...");
                    System.exit(1);
                }

                String lvAusplatzierungsmerkmalDatei = lvReader.getPropertyString("DarlehenFiltern", "AusplatzierungsmerkmalDatei", "Fehler");
                if (lvAusplatzierungsmerkmalDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein Ausplatzierungsmerkmal-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
                
                String lvKonsortialgeschaefteDatei = lvReader.getPropertyString("DarlehenFiltern", "KonsortialgeschaefteDatei", "Fehler");
                if (lvKonsortialgeschaefteDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein Konsortialgeschaefte-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
                
                String lvAktivkontenDatenDatei = lvReader.getPropertyString("DarlehenFiltern", "AktivkontenDatenDatei", "Fehler");
                if (lvAktivkontenDatenDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein AktivkontenDaten-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
                
                //String lvDaypointerFileout = lvReader.getPropertyString("FRISCO", "FRISCODPFILE", "Fehler");
                //if (lvDaypointerFileout.equals("Fehler"))
                //{
                //    LOGGER.error("Kein [FRISCO][FRISCODPFILE] in der ini-Datei...");
                //    System.exit(1);
                //}
                //else
                //{
                //    LOGGER.info("FRISCODPFILE=" + lvDaypointerFileout);                    
                //}
                
                new DarlehenFiltern(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvDarlehenInput, lvDarlehenKonsortial,
                                    lvDarlehenDPP, lvDarlehenSchiffe, lvDarlehenFlugzeuge, lvDarlehenOEPG, lvDarlehenKEV, lvDarlehenOutput,
                                    lvDurchleitungskrediteAktiv, lvDurchleitungskreditePassiv, 
                                    lvDarlehenDeepSea, lvDarlehenLettreDeGage, lvObjektZWOutput, 
                                    lvKundeOutput, lvKundeDlkOutput, lvKundeKonsortialOutput, lvKundeLettreDeGageOutput, lvKundeKEVOutput, lvAusplatzierungsmerkmalDatei,
                                    lvKonsortialgeschaefteDatei, lvAktivkontenDatenDatei, LOGGER);
            }
        }
        System.exit(0);
    }
    
    /**
     * Konstruktor
     * @param pvInstitut
     * @param pvImportVerzeichnis
     * @param pvExportVerzeichnis
     * @param pvDarlehenInput
     * @param pvDarlehenKonsortial 
     * @param pvDarlehenDPP 
     * @param pvDarlehenSchiffe 
     * @param pvDarlehenFlugzeuge 
     * @param pvDarlehenOEPG
     * @param pvDarlehenKEV
     * @param pvDarlehenOutput
     * @param pvDurchleitungskrediteAktiv 
     * @param pvDurchleitungskreditePassiv 
     * @param pvDarlehenDeepSea
     * @param pvDarlehenLettreDeGage
     * @param pvObjektZWOutput
     * @param pvKundeOutput
     * @param pvKundeDlkOutput
     * @param pvKundeKonsortialOutput
     * @param pvKundeLettreDeGageOutput
     * @param pvKundeKEVOutput
     * @param pvAusplatzierungsmerkmalDatei
     * @param pvKonsortialgeschaefteDatei
     * @param pvAktivkontenDatenDatei
     * @param logger 
     */
    public DarlehenFiltern(String pvInstitut, String pvImportVerzeichnis, String pvExportVerzeichnis, String pvDarlehenInput, String pvDarlehenKonsortial,
                           String pvDarlehenDPP, String pvDarlehenSchiffe, String pvDarlehenFlugzeuge, String pvDarlehenOEPG, String pvDarlehenKEV, String pvDarlehenOutput,
                           String pvDurchleitungskrediteAktiv, String pvDurchleitungskreditePassiv, 
                           String pvDarlehenDeepSea, String pvDarlehenLettreDeGage, String pvObjektZWOutput, 
                           String pvKundeOutput, String pvKundeDlkOutput, String pvKundeKonsortialOutput, String pvKundeLettreDeGageOutput, String pvKundeKEVOutput,
                           String pvAusplatzierungsmerkmalDatei, String pvKonsortialgeschaefteDatei, String pvAktivkontenDatenDatei, Logger logger) 
    {   
        ivListeRefiRegisterFilterElemente = new HashMap<String, RefiRegisterFilterElement>();
        
        // Alle Zaehlervariablen mit '0' initialisieren
        ivAnzahlKundennrPfandbrief = 0;
        ivAnzahlBuergennrPfandbrief = 0;
        ivAnzahlKundennrRefiRegister = 0;
        ivAnzahlKundennrLettreDeGage = 0;
        ivAnzahlAltfaelle = 0;
        ivAnzahlTXS = 0;
        ivAnzahlDPP = 0;
        ivAnzahlSchiffe = 0;
        ivAnzahlFlugzeuge = 0;
        ivAnzahlDeepSea = 0;
        ivAnzahlLettreDeGage = 0;
        ivAnzahlDurchleitungskrediteAktiv = 0;
        ivAnzahlDurchleitungskreditePassiv = 0;
        ivAnzahlKEV = 0;
        
        // Alle Listen initialisieren
        ivListeObjekte = new ListeObjekte();
        ivListePassivObjekte = new ListeObjekte();
        ivListeDppObjekte = new ListeObjekte();
        ivListeSchiffeObjekte = new ListeObjekte();
        ivListeFlugzeugeObjekte = new ListeObjekte();
        ivListeOEPGObjekte = new ListeObjekte();
        ivListeDurchleitungskrediteAktiv = new ListeObjekte();
        ivListeDurchleitungskreditePassiv = new ListeObjekte();
        ivListeDarlehenDeepSea = new ListeObjekte();
        ivListeDarlehenLettreDeGage = new ListeObjekte();
        ivListeKEVObjekte = new ListeObjekte();
        
        File lvInputFileDarlehen = new File(pvImportVerzeichnis + "\\" + pvDarlehenInput);
        File lvOutputFileDarlehen = new File(pvExportVerzeichnis + "\\" + pvDarlehenOutput);
        File lvOutputKonsortialFileDarlehen = new File(pvExportVerzeichnis + "\\" + pvDarlehenKonsortial);
        File lvOutputDPPFileDarlehen = new File(pvExportVerzeichnis + "\\" + pvDarlehenDPP);
        File lvOutputSchiffeFileDarlehen = new File(pvExportVerzeichnis + "\\" + pvDarlehenSchiffe);
        File lvOutputFlugzeugeFileDarlehen = new File(pvExportVerzeichnis + "\\" + pvDarlehenFlugzeuge);
        File lvOutputOEPGFileDarlehen = new File(pvExportVerzeichnis + "\\" + pvDarlehenOEPG);
        File lvOutputDurchleitungskrediteAktiv = new File(pvExportVerzeichnis + "\\" + pvDurchleitungskrediteAktiv);
        File lvOutputDurchleitungskreditePassiv = new File(pvExportVerzeichnis + "\\" + pvDurchleitungskreditePassiv);
        File lvOutputDeepSea = new File(pvExportVerzeichnis + "\\" + pvDarlehenDeepSea);
        File lvOutputLettreDeGage = new File(pvExportVerzeichnis + "\\" + pvDarlehenLettreDeGage);
        File lvOutputAusplatzierungsmerkmal = new File(pvExportVerzeichnis + "\\" + pvAusplatzierungsmerkmalDatei);
        File lvOutputKonsortialgeschaefte = new File(pvExportVerzeichnis + "\\" + pvKonsortialgeschaefteDatei);
        File lvOutputAktivkontenDaten = new File(pvExportVerzeichnis + "\\" + pvAktivkontenDatenDatei);
        File lvOutputFileKEV = new File(pvExportVerzeichnis + "\\" + pvDarlehenKEV);
        
        // Wenn eine Datei nicht geoeffnet werden konnte, dann erfolgt ein Programmabbruch
        if (!openStreams(lvInputFileDarlehen, lvOutputFileDarlehen, lvOutputKonsortialFileDarlehen, lvOutputDPPFileDarlehen,
                         lvOutputSchiffeFileDarlehen, lvOutputFlugzeugeFileDarlehen, lvOutputOEPGFileDarlehen, lvOutputFileKEV, lvOutputDurchleitungskrediteAktiv,
                         lvOutputDurchleitungskreditePassiv, lvOutputLettreDeGage, lvOutputDeepSea, lvOutputAusplatzierungsmerkmal, lvOutputKonsortialgeschaefte,
                         lvOutputAktivkontenDaten))
        {
        	return;
        }
        
        ivOozw = new OutputObjektZuweisungsbetrag(pvExportVerzeichnis + "\\" + pvObjektZWOutput);
        
        readDarlehen(pvInstitut, pvExportVerzeichnis, pvKundeOutput, pvKundeDlkOutput, pvKundeKonsortialOutput, pvKundeLettreDeGageOutput, pvKundeKEVOutput);

        zerlegeDarlehen(lvInputFileDarlehen);
        
        // Schreibt die Statistik in die Logdatei
        schreibeStatistik(); 

        // Schliesst alle Streams
        closeStreams();
        
        // KundeRequest-Dateien schliessen
        ivKundennummernOutput.close();        
        ivKundennummernOutputDlk.close();
        ivKundennummernOutputRefiRegister.close();
        ivKundennummernOutputLettreDeGage.close();
        // KundeRequest-Dateien schliessen
     }    
    
    /**
     * Oeffnen der Streams
     * @param pvInputFileDarlehen
     * @param pvOutputFileDarlehen
     * @param pvOutputKonsortialFileDarlehen
     * @param pvOutputDPPFileDarlehen
     * @param pvOutputSchiffeFileDarlehen
     * @param pvOutputFlugzeugeFileDarlehen
     * @param pvOutputOEPGFileDarlehen
     * @param pvOutputFileKEV
     * @param pvOutputDurchleitungskrediteAktiv
     * @param pvOutputDurchleitungskreditePassiv
     * @param pvOutputLettreDeGage
     * @param pvOutputDeepSea
     * @param pvOutputAusplatzierungsmerkmal
     * @param pvOutputKonsortialgeschaefte
   	 * @param pvOutputAktivkontenDaten
     * @return
     */
    private boolean openStreams(File pvInputFileDarlehen, File pvOutputFileDarlehen, File pvOutputKonsortialFileDarlehen, File pvOutputDPPFileDarlehen,
    		                    File pvOutputSchiffeFileDarlehen, File pvOutputFlugzeugeFileDarlehen, File pvOutputOEPGFileDarlehen, File pvOutputFileKEV, File pvOutputDurchleitungskrediteAktiv,
    		                    File pvOutputDurchleitungskreditePassiv, File pvOutputLettreDeGage, File pvOutputDeepSea, File pvOutputAusplatzierungsmerkmal, File pvOutputKonsortialgeschaefte,
    		                    File pvOutputAktivkontenDaten)
    {
        boolean lvOeffnenOkay = true;

        try
        {
            ivAusplatzierungsmerkmalFos = new FileOutputStream(pvOutputAusplatzierungsmerkmal);
        }
        catch (Exception e)
        {
            LOGGER.info("Konnte Outputdatei '" + pvOutputAusplatzierungsmerkmal.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }

        try
        {
            ivKonsortialgeschaefteFos = new FileOutputStream(pvOutputKonsortialgeschaefte);
        }
        catch (Exception e)
        {
            LOGGER.info("Konnte Outputdatei '" + pvOutputKonsortialgeschaefte.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }

        try
        {
            ivAktivkontenDatenFos = new FileOutputStream(pvOutputAktivkontenDaten);
        }
        catch (Exception e)
        {
            LOGGER.info("Konnte Outputdatei '" + pvOutputAktivkontenDaten.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }
        
        try
        {
            ivFis = new FileInputStream(pvInputFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Eingabe-Datei '" + pvInputFileDarlehen.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }
        
        try
        {
            ivFos = new FileOutputStream(pvOutputFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputFileDarlehen.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }

        try
        {
            ivKonsortialFos = new FileOutputStream(pvOutputKonsortialFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputKonsortialFileDarlehen.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }
        
        try
        {
            ivDppFos = new FileOutputStream(pvOutputDPPFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputDPPFileDarlehen.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }
        
        try
        {
            ivSchiffeFos = new FileOutputStream(pvOutputSchiffeFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputSchiffeFileDarlehen.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }
        
        try
        {
            ivFlugzeugeFos = new FileOutputStream(pvOutputFlugzeugeFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputFlugzeugeFileDarlehen.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }

        try
        {
            ivOEPGFos = new FileOutputStream(pvOutputOEPGFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputOEPGFileDarlehen.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }

        try
        {
            ivKEVFos = new FileOutputStream(pvOutputFileKEV);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputFileKEV.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }

        try
        {
            ivDurchleitungskrediteAktivFos = new FileOutputStream(pvOutputDurchleitungskrediteAktiv);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputDurchleitungskrediteAktiv.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }

        try
        {
            ivDurchleitungskreditePassivFos = new FileOutputStream(pvOutputDurchleitungskreditePassiv);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputDurchleitungskreditePassiv.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;
        }
        
        try 
        {
        	ivDarlehenLettreDeGageFos = new FileOutputStream(pvOutputLettreDeGage);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputLettreDeGage.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;      	
        }

        try 
        {
        	ivDarlehenDeepSeaFos = new FileOutputStream(pvOutputDeepSea);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputDeepSea.getAbsolutePath() + "' nicht oeffnen!");
            lvOeffnenOkay = false;      	
        }

        return lvOeffnenOkay;
    }
    
    /**
     * Schliessen der Streams
     */
    private void closeStreams()
    {
        try
        {
            ivAusplatzierungsmerkmalFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'Ausplatzierungsmerkmal.txt' nicht schliessen!");     
        }

        try
        {
            ivKonsortialgeschaefteFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'Konsortialgeschaefte.txt' nicht schliessen!");     
        }

        try
        {
            ivAktivkontenDatenFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'AktivkontenDaten.txt' nicht schliessen!");     
        }
        
        try
        {
          ivFis.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Eingabe-Datei nicht schliessen!");     
        }

        try
        {
          ivFos.close();
          ivKonsortialFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei nicht schliessen!");     
        }
        
        try
        {
          ivDppFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'Deckungspooling.txt' nicht schliessen!");     
        } 

        try
        {
          ivSchiffeFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'Schiffedarlehen.txt' nicht schliessen!");     
        } 

        try
        {
          ivFlugzeugeFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'Flugzeugedarlehen.txt' nicht schliessen!");     
        }

        try
        {
          ivOEPGFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'DarlehenOEPG.txt' nicht schliessen!");     
        }

        try
        {
            ivKEVFos.close();
        }
        catch (IOException e)
        {
            LOGGER.error("Konnte Ausgabe-Datei 'DarlehenKEV.txt' nicht schliessen!");
        }

        try
        {
          ivDurchleitungskrediteAktivFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'DurchleitungskrediteAktiv.txt' nicht schliessen!");     
        }

        try
        {
          ivDurchleitungskreditePassivFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'DurchleitungskreditePassiv.txt' nicht schliessen!");     
        }

        try
        {
          ivDarlehenDeepSeaFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'Darlehen_DeepSea.txt' nicht schliessen!");     
        }

        try
        {
          ivDarlehenLettreDeGageFos.close();
        }
        catch (IOException e)
        {
          LOGGER.error("Konnte Ausgabe-Datei 'Darlehen_LettreDeGage.txt' nicht schliessen!");     
        }
    }
    
    /**
     * @param pvInstitut 
     * @param pvExportVerzeichnis
     * @param pvKundeOutput 
     * @param pvKundeDlkOutput
     * @param pvKundeKonsortialOutput
     * @param pvKundeLettreDeGageOutput
     * @param pvKundeKEVOutput
     */
    private void readDarlehen(String pvInstitut, String pvExportVerzeichnis, String pvKundeOutput, String pvKundeDlkOutput, String pvKundeKonsortialOutput, String pvKundeLettreDeGageOutput, String pvKundeKEVOutput)
    {
        String lvZeile = null;
        boolean lvInDeckung = false;
        boolean lvInLiq = false;
        ObjektZuweisungsbetragListe lvOzwListe = new ObjektZuweisungsbetragListe();
        
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(ivFis));   
        String lvZeileKTS = new String();
        
        int lvZeileCounter = 0;
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen Darlehen-Datei
            {               
               lvZeileCounter++;
               if (lvZeileCounter == 1)
               {
                   if (!ValueMapping.changeMandant(pvInstitut).equals(ValueMapping.changeMandant(lvZeile.substring(71, 74))))
                   {
                       LOGGER.error("Institut unterschiedlich!");
                       LOGGER.error("ini-Datei: " + pvInstitut + " Belieferung: " + lvZeile.substring(71, 74));
                       lvIn.close();
                       return;
                   }
                    
                   // daypointer zur externen Weiterverarbeitung in Datei schreiben
                   //FileOutputStream lvDaypointerost = null;
                   //File lvDaypointerFile = new File(pvDaypointerFileout);
                   
                   //try
                   //{
                   //    lvDaypointerost = new FileOutputStream(lvDaypointerFile);
                   //}
                   //catch (Exception e)
                   //{
                   //    LOGGER.error("Konnte Daypointerdatei " + pvDaypointerFileout +" nicht schreiben!");
                   //}    
                   
                   //try
                   //{
                   //   lvDaypointerost.write(("20" + lvZeile.substring(83,85) + "-" + lvZeile.substring(85,87) + "-" + lvZeile.substring(87,89)).getBytes());
                   //}
                   //catch (Exception e)
                   //{
                   //  LOGGER.error("Konnte Daypointerdatei " + pvDaypointerFileout +" nicht schreiben!");
                   //}
               
                   ivKnrListe = new KundeListe();
                   // KundeRequest-Datei Pfandbrief oeffnen und Vorlaufsatz in die Datei schreiben
                   ivKundennummernOutput = new KundennummernOutput(pvExportVerzeichnis + "\\" + pvKundeOutput, LOGGER);
                   ivKundennummernOutput.open();
                   ivKundennummernOutput.printVorlaufsatz(pvInstitut, "Pfandbrief");
      
                   ivDlkKnrListe = new KundeListe();
                   // KundeRequest-Datei Durchleitungskredite(DLK) oeffnen und Vorlaufsatz in die Datei schreiben
                   ivKundennummernOutputDlk = new KundennummernOutput(pvExportVerzeichnis + "\\" + pvKundeDlkOutput, LOGGER);
                   ivKundennummernOutputDlk.open();
                   ivKundennummernOutputDlk.printVorlaufsatz(pvInstitut, "DLK");
                   
                   ivRefiRegisterKnrListe = new KundeListe();
                   // KundeRequest-Datei RefiReg oeffnen und Vorlaufsatz in die Datei schreiben
                   ivKundennummernOutputRefiRegister = new KundennummernOutput(pvExportVerzeichnis + "\\" + pvKundeKonsortialOutput, LOGGER);
                   ivKundennummernOutputRefiRegister.open();
                   ivKundennummernOutputRefiRegister.printVorlaufsatz(pvInstitut, "RefiReg");
                   
                   ivLettreDeGageKnrListe = new KundeListe();
                   // KundeRequest-Datei LettreDeGage oeffnen und Vorlaufsatz in die Datei schreiben
                   ivKundennummernOutputLettreDeGage = new KundennummernOutput(pvExportVerzeichnis + "\\" + pvKundeLettreDeGageOutput, LOGGER);
                   ivKundennummernOutputLettreDeGage.open();
                   ivKundennummernOutputLettreDeGage.printVorlaufsatz(pvInstitut, "LettreDeGage");

                   ivKEVKnrListe = new KundeListe();
                   // KundeRequest-Datei LettreDeGage oeffnen und Vorlaufsatz in die Datei schreiben
                   ivKundennummernOutputKEV = new KundennummernOutput(pvExportVerzeichnis + "\\" + pvKundeKEVOutput, LOGGER);
                   ivKundennummernOutputKEV.open();
                   ivKundennummernOutputKEV.printVorlaufsatz(pvInstitut, "KEV");
               }
               else
               {
            	   if (lvZeile.length() > 36)
            	   {
            		   if (lvZeile.substring(33, 36).equals("KTS"))
            		   {
            			   lvZeileKTS = lvZeile;
            		   }
            		   else
            		   {
            	     	   // Kunden und Buergen in die Liste einfuegen
            	     	   if (lvZeile.substring(33, 36).equals("KTR"))
            	     	   {
            	     		  einfuegenInKundenListe(lvZeile, lvZeileKTS.substring(316, 321));  

            	     		  sortiereDarlehen(lvZeile, lvZeileKTS, ivAktivkontenDatenFos, ivAusplatzierungsmerkmalFos, ivKonsortialgeschaefteFos);

            	     		  lvInDeckung = false;
                              lvInLiq = false;
                              if (lvZeile.charAt(225) == 'D' || lvZeile.charAt(225) == 'F')
                                  lvInDeckung = true;
                              if (lvZeile.charAt(225) == '5' || lvZeile.charAt(225) == '6')
                                  lvInLiq = true;
       
            	     	   }
            		   }
                   
            		   // Verfuegungsbetraege summieren
            		   if (lvZeile.substring(33, 36).equals("INF"))
            		   {
            			   if (lvZeile.substring(42, 47).equals("SICHV"))
            			   {
            				   if (lvZeile.charAt(71) == 'Z')
            				   {
            					   if (lvInDeckung && !lvInLiq)
            					   {
            						   lvOzwListe.addObjektZuweisungsbetrag(lvZeile.substring(11, 21), lvZeile.substring(113, 131));
            						   //System.out.println("Kundennr: " + s.substring(0, 10));
            						   //System.out.println("Objektnr: " + s.substring(11, 21));
            						   //System.out.println("Kontonr : " + s.substring(22, 32));
            						   //System.out.println("Zuweisungsbetrag: " + s.substring(113, 131));
            					   }
            				   }
            			   }
                       
            		   }
            	   }
               }
            }
        }
        catch (Exception e)
        {
          LOGGER.error("Verarbeitungsfehler in Zeile " + lvZeileCounter);
          LOGGER.error(lvZeile);
          e.printStackTrace();
        }
        
        ivOozw.open();
        lvOzwListe.printObjektZuweisungsbetragListe(ivOozw);
        ivOozw.close();
        
        Collection<RefiRegisterFilterElement> ivCollRefiRegisterFilterElemente = ivListeRefiRegisterFilterElemente.values();
        Iterator<RefiRegisterFilterElement> ivIterRefiRegisterFilterElemente = ivCollRefiRegisterFilterElemente.iterator();
        while (ivIterRefiRegisterFilterElemente.hasNext())
        {
          RefiRegisterFilterElement lvHelpRefi = ivIterRefiRegisterFilterElemente.next();
          //System.out.println(lvHelpRefi.getKontonummer() + ": " + lvHelpRefi.getKompensationsschluessel() + " - " + lvHelpRefi.getPassivKontonummer());  
          if (StringKonverter.convertString2Int(lvHelpRefi.getKompensationsschluessel()) > 19)
          {
            RefiRegisterFilterElement lvHelpRefiAktiv = ivListeRefiRegisterFilterElemente.get(lvHelpRefi.getPassivKontonummer());
            if (lvHelpRefiAktiv != null)
            {
              if (lvHelpRefiAktiv.getDeckungskennzeichen().equals("D") || lvHelpRefiAktiv.getDeckungskennzeichen().equals("F") ||
                  lvHelpRefiAktiv.getDeckungskennzeichen().equals("S") || lvHelpRefiAktiv.getDeckungskennzeichen().equals("V") ||
                  lvHelpRefiAktiv.getDeckungskennzeichen().equals("U") || lvHelpRefiAktiv.getDeckungskennzeichen().equals("W") ||
                  lvHelpRefiAktiv.getDeckungskennzeichen().equals("1") || lvHelpRefiAktiv.getDeckungskennzeichen().equals("2") ||
                  lvHelpRefiAktiv.getDeckungskennzeichen().equals("3") || lvHelpRefiAktiv.getDeckungskennzeichen().equals("4") ||
                  lvHelpRefiAktiv.getDeckungskennzeichen().equals("5") || lvHelpRefiAktiv.getDeckungskennzeichen().equals("6") ||
                  lvHelpRefiAktiv.getDeckungskennzeichen().equals("7") || lvHelpRefiAktiv.getDeckungskennzeichen().equals("8") ||
                  lvHelpRefiAktiv.getDeckungskennzeichen().equals("9") || lvHelpRefiAktiv.getDeckungskennzeichen().equals("0"))
              {
                LOGGER.info("Konsortial: " + lvHelpRefi.getKontonummer() + ";" + lvHelpRefi.getDeckungskennzeichen() + ";" + lvHelpRefi.getKompensationsschluessel() + ";" + lvHelpRefi.getPassivKontonummer() + ";" +
                             lvHelpRefiAktiv.getKontonummer() + ";" + lvHelpRefiAktiv.getDeckungskennzeichen() + ";" + lvHelpRefiAktiv.getKompensationsschluessel());
              }
            }
            else
            {
                LOGGER.info("Konsortial: " + lvHelpRefi.getKontonummer() + " - Kein Aktiv-Konto " + lvHelpRefi.getPassivKontonummer());  
            }
          }
        }
      
        LOGGER.info("1.Durchlauf: " + lvZeileCounter + " Zeilen eingelesen...");

        try
        {
          lvIn.close();
        }
        catch (Exception exp)
        {
          LOGGER.error("BufferedReader nicht schliessen!");
        }
    }
    
    /**
     * Zerlegt die Darlehensdatei - Zweite Durchlauf
     * @param pvInputFileDarlehen
     */
    private void zerlegeDarlehen(File pvInputFileDarlehen)
    {
        int lvZeileCounter = 0;
        String lvZeile = null;
        
        try
        {
            ivFis = new FileInputStream(pvInputFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Eingabe-Datei nicht oeffnen!");
            LOGGER.error("File: " + pvInputFileDarlehen.getAbsolutePath());
        }

        BufferedReader lvIn = new BufferedReader(new InputStreamReader(ivFis));
        try
        {
            //while (lauf < listeObjekte.size() && (s = in.readLine()) != null)  // Lesen Darlehen-Datei
            while ((lvZeile = lvIn.readLine()) != null)
            {
               lvZeileCounter++;
               if (lvZeileCounter == 1)
               {
                   ivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivKonsortialFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivDppFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivSchiffeFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivFlugzeugeFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivOEPGFos.write((lvZeile + StringKonverter.lineSeparator).getBytes()); 
                   ivDurchleitungskrediteAktivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivDurchleitungskreditePassivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivDarlehenDeepSeaFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivDarlehenLettreDeGageFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                   ivKEVFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
               }
               else
               {
                 if (lvZeile.length() > 36)
                 {
                   if (ivListeObjekte.getObjekt(lvZeile.substring(0,21)) != null)
                   {
                     // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                     if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                     {
                       ivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                       //lvZeileCounter++;
                     }
                     else
                     {
                       if (!lvZeile.substring(22, 32).equals("0000000000"))
                       {
                         ivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                     }
                  }
                   
                   if (ivListePassivObjekte.getObjekt(lvZeile.substring(0,21)) != null)
                   {
                	 // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                     if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                     {
                       ivKonsortialFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                       //lvZeileCounter++;
                     }
                     else
                     {
                       if (!lvZeile.substring(22, 32).equals("0000000000"))
                       {
                         ivKonsortialFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                     }
                   }
                     
                   // Deckungspooling rausschreiben
                   if (ivListeDppObjekte.getObjekt(lvZeile.substring(0,21)) != null) 
                   {
                       // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                       if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                       {
                         ivDppFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                       else
                       {
                         if (!lvZeile.substring(22, 32).equals("0000000000"))
                         {
                           ivDppFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                           //lvZeileCounter++;
                         }
                       }
                   }
                   
                   // Schiffe rausschreiben
                   if (ivListeSchiffeObjekte.getObjekt(lvZeile.substring(0,21)) != null) 
                   {
                       // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                       if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                       {
                         ivSchiffeFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                       else
                       {
                         if (!lvZeile.substring(22, 32).equals("0000000000"))
                         {
                           ivSchiffeFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                           //lvZeileCounter++;
                         }
                       }
                   }
                   
                   // Flugzeuge rausschreiben
                   if (ivListeFlugzeugeObjekte.getObjekt(lvZeile.substring(0,21)) != null) 
                   {
                       // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                       if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                       {
                         ivFlugzeugeFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                       else
                       {
                         if (!lvZeile.substring(22, 32).equals("0000000000"))
                         {
                           ivFlugzeugeFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                           //lvZeileCounter++;
                         }
                       }
                   }

                   // OEPG rausschreiben
                   if (ivListeOEPGObjekte.getObjekt(lvZeile.substring(0,21)) != null) 
                   {
                       // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                       if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                       {
                         ivOEPGFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                       else
                       {
                         if (!lvZeile.substring(22, 32).equals("0000000000"))
                         {
                           ivOEPGFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                           //lvZeileCounter++;
                         }
                       }
                   }

                     // KEV rausschreiben
                     if (ivListeKEVObjekte.getObjekt(lvZeile.substring(0,21)) != null)
                     {
                         // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                         if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                         {
                             ivKEVFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                             //lvZeileCounter++;
                         }
                         else
                         {
                             if (!lvZeile.substring(22, 32).equals("0000000000"))
                             {
                                 ivKEVFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                                 //lvZeileCounter++;
                             }
                         }
                     }

                     // DurchleitungskrediteAktiv rausschreiben
                   if (ivListeDurchleitungskrediteAktiv.getObjekt(lvZeile.substring(0,21)) != null) 
                   {
                       // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                       if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                       {
                         ivDurchleitungskrediteAktivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                       else
                       {
                         if (!lvZeile.substring(22, 32).equals("0000000000"))
                         {
                           ivDurchleitungskrediteAktivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                           //lvZeileCounter++;
                         }
                       }
                   }

                   // DurchleitungskreditePassiv rausschreiben
                   if (ivListeDurchleitungskreditePassiv.getObjekt(lvZeile.substring(0,21)) != null) 
                   {
                       // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                       if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                       {
                         ivDurchleitungskreditePassivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                       else
                       {
                         if (!lvZeile.substring(22, 32).equals("0000000000"))
                         {
                           ivDurchleitungskreditePassivFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                           //lvZeileCounter++;
                         }
                       }
                   }
                   
                   // Darlehen_DeepSea rausschreiben
                   if (ivListeDarlehenDeepSea.getObjekt(lvZeile.substring(0,21)) != null) 
                   {
                       // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                       if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                       {
                         ivDarlehenDeepSeaFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                       else
                       {
                         if (!lvZeile.substring(22, 32).equals("0000000000"))
                         {
                           ivDarlehenDeepSeaFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                           //lvZeileCounter++;
                         }
                       }
                   }
                   
                   // Darlehen_LettreDeGage rausschreiben
                   if (ivListeDarlehenLettreDeGage.getObjekt(lvZeile.substring(0,21)) != null) 
                   {
                       // OBJ- und BAUFI-Segmente auf jeden Fall ausgeben
                       if (lvZeile.substring(33, 36).equals("OBJ") || lvZeile.substring(33, 38).equals("BAUFI"))
                       {
                         ivDarlehenLettreDeGageFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                         //lvZeileCounter++;
                       }
                       else
                       {
                         if (!lvZeile.substring(22, 32).equals("0000000000"))
                         {
                           ivDarlehenLettreDeGageFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
                           //lvZeileCounter++;
                         }
                       }
                   }
                 }
               }
            }
        }
        catch (Exception e)
        {
          LOGGER.error("Verarbeitungsfehler in Zeile " + lvZeileCounter);
          LOGGER.error(lvZeile);
          e.printStackTrace();
        } 

        LOGGER.info("2.Durchlauf: " + lvZeileCounter + " Zeilen eingelesen...");
        
        try
        {
          lvIn.close();
        }
        catch (Exception exp)
        {
        	LOGGER.error("BufferedReader nicht schliessen!");
        }
    }
    
    /**
     * Sortiert die Darlehen in die entsprechenden Listen bzw. Ausgabedateien ein
     * @param pvZeile
     * @param pvZeileKTS
     * @param pvAktivkontenDatenFos
     * @param pvAusplatzierungsmerkmalFos
     * @param pvKonsortialgeschaefteFos
     */
    private void sortiereDarlehen(String pvZeile, String pvZeileKTS, FileOutputStream pvAktivkontenDatenFos, FileOutputStream pvAusplatzierungsmerkmalFos, FileOutputStream pvKonsortialgeschaefteFos)
    {
    	// Wenn in der KTR- oder KTS-Zeile 'DUMMY' steht, dann muss das Darlehen ignoriert werden
    	if (pvZeile.contains(";DUMMY;") || pvZeileKTS.contains(";DUMMY;"))
    	{
    		LOGGER.info("Dummy-Zeile: " + pvZeile.substring(0, 32));
    		return;
    	}
    	// Daten aus der KTS-Zeile ermitteln
        String lvProduktschluessel = pvZeileKTS.substring(316, 321);
        String lvDarlehensbesonderheit = pvZeileKTS.substring(322, 325);
        String lvKontozustand = pvZeileKTS.substring(333, 334);
        String lvRestkapital = pvZeileKTS.substring(226, 244);
        String lvKompensationsschluessel = pvZeileKTS.substring(330, 332).trim();
        String lvPassivkontonummer = pvZeileKTS.substring(264,274);

        // Logging von leeren Ausplatzierungsmerkmalen
        if (pvZeile.substring(604,606).trim().isEmpty())
        {
        	LOGGER.info("Leeres Ausplatzierungsmerkmal;" + pvZeile.substring(22,32) + ";" + lvKontozustand + ";" + lvRestkapital + ";" + lvProduktschluessel + ";" + pvZeile.substring(604, 606) + ";" + pvZeile.charAt(225) + ";" + pvZeileKTS.substring(296, 305));
        }
        
        String lvAltesObjekt = new String();

        if (pvZeile.length() > 36)
        {
        	// Konsortialdarlehen - Kompensationsschluessel gefuellt
        	if (lvKompensationsschluessel.length() > 0)
        	{ 
        		ivListePassivObjekte.addObjekt(pvZeile.substring(0,21));
                
                // Kundennummer RefiRegister fuer SPOT-Request rausschreiben
                if (!ivRefiRegisterKnrListe.containsKunde(pvZeile.substring(0,10)))
                {
                    ivRefiRegisterKnrListe.addKunde(pvZeile.substring(0,10));
                    ivKundennummernOutputRefiRegister.printKundennummer(pvZeile.substring(0,10));
                    LOGGER.info("Kundennummer: " + pvZeile.substring(0,10));
                    ivAnzahlKundennrRefiRegister++;
                }
        	}
              
        	// Darlehen von DPP - Produktschluessel 00116, 00115 oder 00117
        	// Nur noch Produktschluessel '00117' fuer DPP herausfiltern
        	if (lvProduktschluessel.endsWith("0117"))
        	{
            	LOGGER.info("DPP;" + pvZeile.substring(0,32) + ";" + lvProduktschluessel + ";");
                ivAnzahlDPP++;
                ivListeDppObjekte.addObjekt(pvZeile.substring(0,21));
        	}
            
        	// Darf nicht Deckungspooling sein...
            if (!lvProduktschluessel.equals("00116") && !lvProduktschluessel.equals("00115") && !lvProduktschluessel.equals("00117"))
            {
            	// AktivkontenDaten ausgeben - RefiRegister
            	try
            	{
            		pvAktivkontenDatenFos.write((pvZeile.substring(22,32) + ";" + lvPassivkontonummer + ";" + pvZeile.substring(283, 293) + ";" + pvZeile.substring(0, 10) + StringKonverter.lineSeparator).getBytes());  
            	}
            	catch (Exception exp)
            	{
            		LOGGER.error("Fehler bei Ausgabe in die Datei 'AktivkontenDaten.txt'");
            	}
                
            	if (StringKonverter.convertString2Int(lvProduktschluessel) == 605 || StringKonverter.convertString2Int(lvProduktschluessel) == 620 ||
            		StringKonverter.convertString2Int(lvProduktschluessel) == 621 || StringKonverter.convertString2Int(lvProduktschluessel) == 630 ||
                    StringKonverter.convertString2Int(lvProduktschluessel) == 631 || StringKonverter.convertString2Int(lvProduktschluessel) == 634 ||
                    StringKonverter.convertString2Int(lvProduktschluessel) == 635 || StringKonverter.convertString2Int(lvProduktschluessel) == 640 ||
                    StringKonverter.convertString2Int(lvProduktschluessel) == 641)
            	{
            		LOGGER.info("RoP: " + pvZeile.substring(0,32) + ";" + lvProduktschluessel + ";" + lvDarlehensbesonderheit + ";" + pvZeile.substring(225, 226) + ";" + pvZeile.substring(601, 603));                               
            	}
            		
            	// Nicht relevante Ausplatzierungsmerkmale in eine Datei schreiben
            	if (//pvZeile.charAt(604) == 'A' || //pvZeile.charAt(604) == 'B' ||
            		pvZeile.charAt(604) == 'C' ||
            		pvZeile.charAt(604) == 'D' || pvZeile.charAt(604) == 'E' || //pvZeile.charAt(604) == 'G' ||
                    pvZeile.charAt(604) == 'P' || pvZeile.charAt(604) == 'R' || pvZeile.charAt(604) == 'W' ||
                    pvZeile.charAt(604) == 'N')
            	{
            		try
            		{
            			pvAusplatzierungsmerkmalFos.write((pvZeile.substring(22,32) + ";" + pvZeile.substring(604, 606) + StringKonverter.lineSeparator).getBytes());
                    }
                    catch (Exception exp)
                    {
                    	LOGGER.error("Fehler bei Ausgabe in die Datei 'Ausplatzierungsmerkmal.txt'");
                    }
            	}
                
            	if (lvKompensationsschluessel.length() > 0)
            	{
            		if (!pvZeile.substring(22,32).equals("0000000000"))
            		{
            			RefiRegisterFilterElement lvRefiRegisterFilterElement = new RefiRegisterFilterElement(pvZeile.substring(22,32), pvZeile.substring(225, 226), lvKompensationsschluessel, lvProduktschluessel, pvZeile.substring(283, 293), lvPassivkontonummer, lvRestkapital);  
                        ivListeRefiRegisterFilterElemente.put(pvZeile.substring(22,32), lvRefiRegisterFilterElement);
                        LOGGER.info("Konsortial: " + pvZeile.substring(22,32) + ";" + pvZeile.substring(225, 226) + ";" + lvKompensationsschluessel + ";" + pvZeile.substring(283, 293));
                        if (lvKompensationsschluessel.equals("01") || lvKompensationsschluessel.equals("10") || lvKompensationsschluessel.equals("11") || // Aktiv 
                            lvKompensationsschluessel.equals("21") || lvKompensationsschluessel.equals("22") || lvKompensationsschluessel.equals("23"))   // Passiv
                        {
                            if (!lvProduktschluessel.equals("01002") && !lvProduktschluessel.equals("01003") && !lvProduktschluessel.equals("01004") &&
                                !lvProduktschluessel.equals("01005") && !lvProduktschluessel.equals("01006") && !lvProduktschluessel.equals("01007") &&
                                !lvProduktschluessel.equals("01008") && !lvProduktschluessel.equals("05201") && !lvProduktschluessel.equals("05202") && 
                                !lvProduktschluessel.equals("05203")) // DLK nicht verwenden
                            {
                            	try
                            	{
                            		pvKonsortialgeschaefteFos.write((pvZeile.substring(22,32) + ";" + pvZeile.substring(225, 226) + ";" + lvKompensationsschluessel + ";" + lvProduktschluessel + ";" + pvZeile.substring(283, 293) + ";" + lvPassivkontonummer + ";" + lvRestkapital  + StringKonverter.lineSeparator).getBytes());
                            	}
                            	catch (Exception exp)
                            	{
                            		LOGGER.error("Fehler bei Ausgabe in die Datei 'Konsortialgeschaefte.txt'");
                            	}
                            }
                            	
                        }
                        //lvKompensationsschluessel = new String();
                        //lvPassivkontonummer = new String();
            		}
            	}
            	
            	// Kredite OEPG filtern
            	// Ausplatzierungsmerkmal beginnt mit 'O' (OEPG)
            	if (pvZeile.charAt(604) == 'O')
            	{
                        LOGGER.info("Altfall;" + pvZeile.substring(0,32) + ";" + lvProduktschluessel + ";" + lvKontozustand + ";" + lvRestkapital + ";" + pvZeile.substring(442,450) + ";");
                        ivAnzahlAltfaelle++;
                        ivListeOEPGObjekte.addObjekt(pvZeile.substring(0,21));
            	}
                
            	// Kredite Schiffe filtern
            	// Ausplatzierungsmerkmal beginnt mit 'S' (Schiffe)
            	if (pvZeile.charAt(604) == 'S')
            	{
                        LOGGER.info("Schiffe;" + pvZeile.substring(0,32) + ";" + lvProduktschluessel + ";");
                        ivAnzahlSchiffe++;
                        ivListeSchiffeObjekte.addObjekt(pvZeile.substring(0,21));  
            	}
                
            	// Kredite Flugzeuge filtern
            	// Ausplatzierungsmerkmal beginnt mit 'F' (Flugzeugen)
            	if (pvZeile.charAt(604) == 'F')
            	{
                        LOGGER.info("Flugzeuge;" + pvZeile.substring(0,32) + ";" + lvProduktschluessel + ";");
                        ivAnzahlFlugzeuge++;
                        ivListeFlugzeugeObjekte.addObjekt(pvZeile.substring(0,21)); 
            	}
                
            	// Kredite PfandBG filtern
            	// Ausplatzierungsmerkmal beginnt mit 'H' (Hypotheken) und 'K' (Kommunal)
            	if (pvZeile.charAt(604) == 'H' || pvZeile.charAt(604) == 'K')
            	{
                       if (!lvAltesObjekt.equals(pvZeile.substring(0,21)))
                        {
                            ivListeObjekte.addObjekt(pvZeile.substring(0,21));
                            lvAltesObjekt = pvZeile.substring(0, 21);
                            LOGGER.info("TXS;" + pvZeile.substring(0,32) + ";" + lvProduktschluessel + ";");
                            ivAnzahlTXS++;
                        }
            	}
                
            	// Durchleitungskredite Aktiv filtern
            	// Produktschluessel == 01002 || 01003 || 01004 || 01005 || 01006 || 01007 || 01008
            	if (lvProduktschluessel.equals("01002") || lvProduktschluessel.equals("01003") || lvProduktschluessel.equals("01004") ||
            		lvProduktschluessel.equals("01005") || lvProduktschluessel.equals("01006") || lvProduktschluessel.equals("01007") ||
            		lvProduktschluessel.equals("01008"))
            	{
                         LOGGER.info("DurchleitungskreditAktiv;" + pvZeile.substring(0,32) + ";" + lvProduktschluessel + ";");
                         ivAnzahlDurchleitungskrediteAktiv++;
                         ivListeDurchleitungskrediteAktiv.addObjekt(pvZeile.substring(0,21)); 
                         // Kundennummer fuer SPOT-Resquest rausschreiben
                         if (!ivDlkKnrListe.containsKunde(pvZeile.substring(0,10)))
                         {
                             ivDlkKnrListe.addKunde(pvZeile.substring(0,10));
                             ivKundennummernOutputDlk.printKundennummer(pvZeile.substring(0,10));
                             LOGGER.info("DLK - Aktiv - Kundennummer: " + pvZeile.substring(0,10));
                         }
            	}
                
            	// Durchleitungskredite Passiv filtern
            	// Produktschluessel == 05201 || 05202 || 05203
            	if (lvProduktschluessel.equals("05201") || lvProduktschluessel.equals("05202") || lvProduktschluessel.equals("05203"))
            	{
                         LOGGER.info("DurchleitungskreditPassiv;" + pvZeile.substring(0,32) + ";" + lvProduktschluessel + ";");
                         ivAnzahlDurchleitungskreditePassiv++;
                         ivListeDurchleitungskreditePassiv.addObjekt(pvZeile.substring(0,21));                                    
                         // Kundennummer fuer SPOT-Request rausschreiben
                         if (!ivDlkKnrListe.containsKunde(pvZeile.substring(0,10)))
                         {
                             ivDlkKnrListe.addKunde(pvZeile.substring(0,10));
                             ivKundennummernOutputDlk.printKundennummer(pvZeile.substring(0,10));
                             LOGGER.info("DLK - Passiv - Kundennummer: " + pvZeile.substring(0,10));
                         }
            	} 
 
            	// Darlehen DeepSea filtern -> Nur Ausplatzierungsmerkmal 'B1'
            	if (pvZeile.charAt(604) == 'B' && pvZeile.charAt(605) == '1')
            	{
                    	 ivAnzahlDeepSea++;
                         ivListeDarlehenDeepSea.addObjekt(pvZeile.substring(0,21));  	
                         LOGGER.info("Darlehen - DeepSea;" + pvZeile.substring(0,32) + ";" + pvZeile.substring(604,606) + ";");
                         // Kundennummer RefiRegister fuer SPOT-Request rausschreiben
                         if (!ivRefiRegisterKnrListe.containsKunde(pvZeile.substring(0,10)))
                         {
                             ivRefiRegisterKnrListe.addKunde(pvZeile.substring(0,10));
                             ivKundennummernOutputRefiRegister.printKundennummer(pvZeile.substring(0,10));
                             LOGGER.info("Kundennummer: " + pvZeile.substring(0,10));
                             ivAnzahlKundennrRefiRegister++;
                         }
            	}
            	
            	// Darlehen LettreDeGage filtern
            	if (pvZeile.charAt(604) == 'G')
            	{
                    	 ivAnzahlLettreDeGage++;
                         ivListeDarlehenLettreDeGage.addObjekt(pvZeile.substring(0,21));  	
                         LOGGER.info("Darlehen - LettreDeGage;" + pvZeile.substring(0,32) + ";" + pvZeile.substring(604,606) + ";");
                         // Kundennummer LettreDeGage/Luxemburg fuer SPOT-Request rausschreiben
                         if (!ivLettreDeGageKnrListe.containsKunde(pvZeile.substring(0,10)))
                         {
                             ivLettreDeGageKnrListe.addKunde(pvZeile.substring(0,10));
                             ivKundennummernOutputLettreDeGage.printKundennummer(pvZeile.substring(0,10));
                             LOGGER.info("LettreDeGage - Kundennummer: " + pvZeile.substring(0,10));
                             ivAnzahlKundennrLettreDeGage++;
                         }
            	}

                // Darlehen KEV filtern
                if (pvZeile.charAt(604) == 'A')
                {
                    ivAnzahlKEV++;
                    ivListeKEVObjekte.addObjekt(pvZeile.substring(0,21));
                    LOGGER.info("Darlehen - KEV;" + pvZeile.substring(0,32) + ";" + pvZeile.substring(604,606) + ";");
                    // Kundennummer KEV fuer SPOT-Request rausschreiben
                    if (!ivKEVKnrListe.containsKunde(pvZeile.substring(0,10)))
                    {
                        ivKEVKnrListe.addKunde(pvZeile.substring(0,10));
                        ivKundennummernOutputKEV.printKundennummer(pvZeile.substring(0,10));
                        LOGGER.info("KEV - Kundennummer: " + pvZeile.substring(0,10));
                        ivAnzahlKundennrKEV++;
                    }
                }

            }
        }
    }
    
    /**
     * Fuegt die Kunden- und Buergennummer in die Liste ein
     * @param pvZeile KTR-Zeile eines Finanzgeschaeftes
     * @param pvProduktschluessel Aktuelle Produktschluessel
     */
    private void einfuegenInKundenListe(String pvZeile, String pvProduktschluessel)
    {
		if (pvZeile.charAt(604) == 'H' || pvZeile.charAt(604) == 'K' || pvZeile.charAt(604) == 'F' || pvZeile.charAt(604) == 'S' || pvZeile.charAt(604) == 'O')
    	{
            if (!pvProduktschluessel.equals("00116") && !pvProduktschluessel.equals("00115") && !pvProduktschluessel.equals("00117"))
            {
            	try
            	{
            		// Kundennummer fuer SPOT-Request rausschreiben
            		if (!ivKnrListe.containsKunde(pvZeile.substring(0,10)))
            		{
            			ivKnrListe.addKunde(pvZeile.substring(0,10));
            			ivKundennummernOutput.printKundennummer(pvZeile.substring(0,10));
            			LOGGER.info("Kundennummer: " + pvZeile.substring(0,10));
            			ivAnzahlKundennrPfandbrief++;
            		}

            		// Buergennummer fuer SPOT rausschreiben
            		if (!pvZeile.substring(240,250).equals("0000000000"))
            		{ // 0000000000 -> Keine Buergennummer vorhanden!
            			if (!ivKnrListe.containsKunde(pvZeile.substring(240,250)))
            			{
            				ivKnrListe.addKunde(pvZeile.substring(240,250));
            				ivKundennummernOutput.printKundennummer(pvZeile.substring(240,250));
            				LOGGER.info("Buergennummer: " + pvZeile.substring(240,250));
            				ivAnzahlBuergennrPfandbrief++;
            			}
            		}
            	}
            	catch (Exception exp)
            	{
            		LOGGER.error("einfuegenInKundenListe: Konnte Kunden- und Buergennummer nicht einfuegen!");
            		LOGGER.error("Zeile: " + pvZeile);
            	}
            }
		}
    }
    
    /**
     * Schreibt die Statistik in die Logdatei
     */
    private void schreibeStatistik()
    {
    	LOGGER.info("Anzahl Konten DarKa: " + ivAnzahlTXS);
    	LOGGER.info("Anzahl Altfaelle - OEPG: " + ivAnzahlAltfaelle);
    	LOGGER.info("Anzahl Konten DPP: " + ivAnzahlDPP);
    	LOGGER.info("Anzahl Konten Schiffe: " + ivAnzahlSchiffe);
        LOGGER.info("Anzahl Konten Flugzeuge: " + ivAnzahlFlugzeuge);
        LOGGER.info("Anzahl Durchleitungskredite Aktiv: " + ivAnzahlDurchleitungskrediteAktiv);
        LOGGER.info("Anzahl Durchleitungskredite Passiv: " + ivAnzahlDurchleitungskreditePassiv);
        LOGGER.info("Anzahl Darlehen DeepSea: " + ivAnzahlDeepSea);
        LOGGER.info("Anzahl Darlehen LettreDeGage: " + ivAnzahlLettreDeGage);
        LOGGER.info("Anzahl Darlehen KEV: " + ivAnzahlKEV);
        LOGGER.info("Anzahl Kundennummern Pfandbrief: " + ivAnzahlKundennrPfandbrief);
        LOGGER.info("Anzahl Buergennummern Pfandbrief: " + ivAnzahlBuergennrPfandbrief);
        LOGGER.info("Anzahl Kundennummern Konsortial: " + ivAnzahlKundennrRefiRegister);
        LOGGER.info("Anzahl Kundennummern LettreDeGage: " + ivAnzahlKundennrLettreDeGage);
        LOGGER.info("Anzahl Kundennummern KEV: " + ivAnzahlKundennrKEV);
    }
}
