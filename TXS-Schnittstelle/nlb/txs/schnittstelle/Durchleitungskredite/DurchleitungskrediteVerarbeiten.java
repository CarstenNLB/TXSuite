/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Durchleitungskredite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import nlb.txs.schnittstelle.Darlehen.LeseVorlaufsatz;
import nlb.txs.schnittstelle.Darlehen.Daten.DarlehenKomplett;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.BAUFI;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.INF;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KON;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KONTS;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTOZB;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTR;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTS;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.OBJ;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.REC;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.UMS;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author tepperc
 *
 */
public class DurchleitungskrediteVerarbeiten 
{

    // Logger fuer Durchleitungskredite
    private static Logger LOGGER_DLK = Logger.getLogger("TXSDLKLogger"); 
    
    // Status BAUFI
    private final int BAUFI = 1;
    
    // Status INF
    private final int INF = 2;
    
    // Status KON
    private final int KON = 3;
    
    // Status KONTS
    private final int KONTS = 4;

    // Status KTOZB
    private final int KTOZB = 5;

    // Status KTR
    private final int KTR = 6;

    // Status KTS
    private final int KTS = 7;

    // Status OBJ
    private final int OBJ = 8;

    // Status REC
    private final int REC = 9;

    // Status UMS
    private final int UMS = 10;
    
    // Status undefiniert
    private final int UNDEFINIERT = 11;

    /**
     * Aktueller Status - z.B. KTR
     */
    private int ivStatus;
    
    // Ini-Parameter
    private String ivInstitutsnummer;
    private String ivImportVerzeichnis;
    private String ivExportVerzeichnis;
    private String ivDarlehenAktivImportDatei;
    private String ivDarlehenPassivImportDatei;
    private String ivDarlehenAktivExportDatei;
    private String ivDarlehenPassivExportDatei;
    private String ivDarlehenKonsortialExportDatei;
    private String ivKundeResponseDatei;
    
    // Verarbeitungsmodus Aktiv
    public static final int AKTIV = 0;
    
    // Verarbeitungsmodus Passiv
    public static final int PASSIV = 1;
    
    /**
     * Aktueller Verarbeitungsmodus - z.B. Aktiv
     */
    private int ivModus;
    
    // DarKa-Segmente
    private DWHVOR ivVorlaufsatz;
    private DarlehenKomplett ivOriginalDarlehen;
    private BAUFI ivBaufi;
    private INF ivInf;
    private KON ivKon;
    private KONTS ivKonts;
    private KTOZB ivKtozb;
    private KTR ivKtr;
    private KTS ivKts;
    private OBJ ivObj;
    private REC ivRec;
    private UMS ivUms;

    /**
     * Kundennummer
     */
    private String ivKundennummer;     
    
    /**
     * Objektnummer 
     */
    private String ivObjektnummer;
    
    /**
     * Kontonummer
     */                             
    private String ivKontonummer;   

    private String ivAlteKontonummer;

    /**
     * Zaehler fuer die Kredite Aktiv
     */
    private int ivAnzahlAktiv;
    
    /**
     * Zaehler fuer die Kredite Passiv
     */
    private int ivAnzahlPassiv;
    
    /**
     * Export der Kredite Aktiv
     */
    private AktivExport ivAktivExport;
    
    /**
     * Export der Kredite Passiv
     */
    private PassivExport ivPassivExport;
    
    /**
     * Kundendaten
     */
    private Document ivDocument;

    /**
     * Konstruktor fuer Verarbeitung Durchleitungskredite
     * @param pvModus 
     * @param pvReader 
     */
    public DurchleitungskrediteVerarbeiten(IniReader pvReader)
    {
        
        if (pvReader != null)
        {
            String lvLoggingXML = pvReader.getPropertyString("log4j", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
              System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
                DOMConfigurator.configure(lvLoggingXML); 
            }            

            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_DLK.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
            
            ivImportVerzeichnis = pvReader.getPropertyString("Durchleitungskredite", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_DLK.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("Durchleitungskredite", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_DLK.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivDarlehenAktivImportDatei =  pvReader.getPropertyString("Durchleitungskredite", "DarlehenAktivImport-Datei", "Fehler");
            if (ivDarlehenAktivImportDatei.equals("Fehler"))
            {
                LOGGER_DLK.error("Kein DarlehenAktivImport-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            ivDarlehenPassivImportDatei =  pvReader.getPropertyString("Durchleitungskredite", "DarlehenPassivImport-Datei", "Fehler");
            if (ivDarlehenPassivImportDatei.equals("Fehler"))
            {
                LOGGER_DLK.error("Kein DarlehenPassivImport-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivDarlehenAktivExportDatei =  pvReader.getPropertyString("Durchleitungskredite", "DarlehenAktivExport-Datei", "Fehler");
            if (ivDarlehenAktivExportDatei.equals("Fehler"))
            {
                LOGGER_DLK.error("Kein DarlehenAktivExport-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            ivDarlehenPassivExportDatei =  pvReader.getPropertyString("Durchleitungskredite", "DarlehenPassivExport-Datei", "Fehler");
            if (ivDarlehenPassivExportDatei.equals("Fehler"))
            {
                LOGGER_DLK.error("Kein DarlehenPassivExport-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            ivDarlehenKonsortialExportDatei =  pvReader.getPropertyString("Durchleitungskredite", "DarlehenKonsortialExport-Datei", "Fehler");
            if (ivDarlehenKonsortialExportDatei.equals("Fehler"))
            {
                LOGGER_DLK.error("Kein DarlehenKonsortialExport-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivKundeResponseDatei = pvReader.getPropertyString("Kunde_Pfandbrief", "ResponseDatei", "Fehler");
            if (ivKundeResponseDatei.equals("Fehler"))
            {
                LOGGER_DLK.error("Kein KundeResponse-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            // Kundendaten einlesen
            leseKunden(ivExportVerzeichnis + "\\" + ivKundeResponseDatei);      
            
            // Verarbeitung Aktiv starten
            startVerarbeitungAktiv();
            // Status wieder zuruecksetzen
            ivStatus = UNDEFINIERT;
            ivOriginalDarlehen.clearLists();
            ivInf = null;
            ivKon = null;
            ivKonts = null;
            ivKtozb = null;
            ivKtr = null;
            ivKts = null;
            ivRec = null;
            ivUms = null;

            // Verarbeitung Passiv starten
            startVerarbeitungPassiv();
            
            LOGGER_DLK.info(printStatistik());
        }

    }

    /**
     * Verarbeitung der Kredite Aktiv
     */
    private void startVerarbeitungAktiv()
    {        
        ivModus = AKTIV;
        ivAktivExport = new AktivExport(ivExportVerzeichnis, ivDarlehenAktivExportDatei, ivDarlehenKonsortialExportDatei, LOGGER_DLK);
        
        Calendar lvCal = Calendar.getInstance();
        CalendarHelper lvCh = new CalendarHelper();

        LOGGER_DLK.info("Start Aktiv: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
       
        // Zaehler Aktiv auf '0' setzen
        ivAnzahlAktiv = 0;

        // Darlehen einlesen und verarbeiten
        readDarlehen(ivImportVerzeichnis + "\\" + ivDarlehenAktivImportDatei);
  
        ivAktivExport.close();
        
        lvCal = Calendar.getInstance();
        LOGGER_DLK.info("Ende Aktiv: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);       
    }

    
    /**
     * Verarbeitung der Kredite Passiv
     */
    private void startVerarbeitungPassiv()
    {        
        ivModus = PASSIV;
        ivPassivExport = new PassivExport(ivExportVerzeichnis, ivDarlehenPassivExportDatei, LOGGER_DLK);
        
        Calendar lvCal = Calendar.getInstance();
        CalendarHelper lvCh = new CalendarHelper();

        LOGGER_DLK.info("Start Passiv: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
       
        // Zaehler Passiv auf '0' setzen
        ivAnzahlPassiv = 0;

        // Darlehen einlesen und verarbeiten
        readDarlehen(ivImportVerzeichnis + "\\" + ivDarlehenPassivImportDatei);
  
        ivPassivExport.close();
        
        lvCal = Calendar.getInstance();
        LOGGER_DLK.info("Ende Passiv: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);       
    }
  
    /**
     * 
     */
    private void readDarlehen(String pvDateiname)
    {
        String lvZeile = null;
                
        ivOriginalDarlehen = new DarlehenKomplett(LOGGER_DLK);
        
        // Oeffnen der Dateien
        FileInputStream lvFileInputStream = null;
        File ivFileDarlehen = new File(pvDateiname);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileDarlehen);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Darlehen-Datei nicht oeffnen!");
            return;
        }

        BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        boolean lvStart = true;
          
        try
        {
            while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Lesen Darlehen-Datei
            {
              if (lvStart)
              {
                  LeseVorlaufsatz lvLeseVorlaufsatz = new LeseVorlaufsatz(); 
                  ivVorlaufsatz = lvLeseVorlaufsatz.parseVorlaufsatz(lvZeile);
                  //ivZaehlerVorlaufsatz++;
                  ivOriginalDarlehen.setAnzahlDatum(ivVorlaufsatz.getAnzahlDatum());
                  ivOriginalDarlehen.setBuchungsdatum(ivVorlaufsatz.getsDwvbdat());
                  ivOriginalDarlehen.setInstitutsnummer(ivVorlaufsatz.getsDwvinst());
                  lvStart = false;
              }
              else
              {
                  if (!parseDarlehen(lvZeile)) // Parsen der Felder
                  {
                      System.out.println("Datenfehler!!!\n");
                  }
                  //System.out.println(s);
                  if (!ivKontonummer.equals("0000000000"))
                  {
                      einfuegenSegment();
                      ivAlteKontonummer = ivKontonummer;
                  }
              }
            }
          }
          catch (Exception e)
          {
            System.out.println("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
          }
   
          // Das letzte Darlehen muss noch verarbeitet werden
          if (ivKtr != null)
          {
            if (ivModus == AKTIV)
            {
                if (ivAktivExport.export(ivOriginalDarlehen, ivVorlaufsatz.getsDwvbdat(), getKundenname(ivOriginalDarlehen.getKTR().getKopf().getsDwhkdnr())))
                {
                    ivAnzahlAktiv++;
                }
            }
            if (ivModus == PASSIV)
            {
                if (ivPassivExport.export(ivOriginalDarlehen, ivVorlaufsatz.getsDwvbdat(), getKundenname(ivOriginalDarlehen.getKTR().getKopf().getsDwhkdnr())))
                {
                    ivAnzahlPassiv++;
                }                
            }
          }
          
          try
          {
            lvBufferedReaderIn.close();
          }
          catch (Exception e)
          {
            System.out.println("Konnte Darlehen-Datei nicht schliessen!");
          }
             
        }
        
 
    /**
     * @param pvZeile 
     * @return 
     *       
     */
     private boolean parseDarlehen(String pvZeile)
     {             
        String stemp = new String(); // arbeitsbereich/zwischenspeicher feld
        int lvLfd = 0; // lfd feldnr, pruefsumme je satzart
        int lvZzStr = 0; // pointer fuer satzbereich
        
        ivStatus = UNDEFINIERT;
        
        // steuerung/iteration eingabesatz
        for (lvZzStr = 0; lvZzStr < pvZeile.length(); lvZzStr++)
        {
            // wenn semikolon erkannt
            if (pvZeile.charAt(lvZzStr) == ';')
            {
                if (lvLfd < 4)
                {
                    if (lvLfd == 0)
                    {
                      ivKundennummer = stemp;
                    }
                    if (lvLfd == 1)
                    {
                      ivObjektnummer = stemp;
                    }
                    if (lvLfd == 2)
                    {
                      ivKontonummer = stemp;
                    }
                  
                    if (lvLfd == 3)
                    {
                      if (ivAlteKontonummer != null)
                      {
                        if (!ivKontonummer.equals(ivAlteKontonummer))
                        {
                          if (!ivKontonummer.equals("0000000000"))
                          {
                            //System.out.println("Kontonummerwechsel: von " + alteKontonummer + " nach " + kontonummer);
                            if (ivKtr != null)
                            {   
                                if (ivModus == AKTIV)
                                {
                                  if (ivAktivExport.export(ivOriginalDarlehen, ivVorlaufsatz.getsDwvbdat(), getKundenname(ivOriginalDarlehen.getKTR().getKopf().getsDwhkdnr())))
                                  {
                                    ivAnzahlAktiv++;
                                  }
                                }
                                if (ivModus == PASSIV)
                                {
                                  if (ivPassivExport.export(ivOriginalDarlehen, ivVorlaufsatz.getsDwvbdat(), getKundenname(ivOriginalDarlehen.getKTR().getKopf().getsDwhkdnr())))
                                  {
                                      ivAnzahlPassiv++;
                                  }
                                }
                            }
                            ivOriginalDarlehen.clearLists();
                            ivInf = null;
                            ivKon = null;
                            ivKonts = null;
                            ivKtozb = null;
                            ivKtr = null;
                            ivKts = null;
                            ivRec = null;
                            ivUms = null;
                          }
                        }
                      }
                      erzeugeSegment(stemp.trim());
                    }
                }
                else
                {
                    setzeWert(ivStatus, lvLfd, stemp);
                }

                lvLfd++; // naechste feldnummer
                // loeschen zwischenbuffer
                stemp = new String();
            }
            else
            {
              // uebernehmen byte aus eingabesatzposition
                stemp = stemp + pvZeile.charAt(lvZzStr);
            }
        } // ende for  
      
        // Letzten Wert der Zeile/Segment auch noch setzen
        if (!stemp.isEmpty())
        {
            setzeWert(ivStatus, lvLfd, stemp);
        }  
        return true; 
     }


     /**
      * Liest die Kundendaten von SPOT
      * @param pvFilename Dateiname der Kundendaten von SPOT
      */
     private void leseKunden(String pvFilename)
     {
         // Zur Eingabe
         SAXBuilder lvBuilder = null;
         lvBuilder = new SAXBuilder();
         lvBuilder.setExpandEntities(true);
           
         try
         {
             ivDocument = (Document)lvBuilder.build(pvFilename);
         }
         catch(IOException io)
         {
              LOGGER_DLK.error(io.getMessage());
         }
         catch(JDOMException jdomex)
         {
              LOGGER_DLK.error(jdomex.getMessage());
         }
     }
     
     /**
      * Liefert den Namen des Kunden
      */
     private String getKundenname(String pvKundennummer)
     {         
         Element lvRootNode = ivDocument.getRootElement();
         //Element lvNodeHeader = (Element)lvRootNode.getChildren("Header").get(0);       
         Element lvNodeResponse = (Element)lvRootNode.getChildren("Response").get(0);
         Element lvNodeInstitut = (Element)lvNodeResponse.getChildren("Institut").get(0);
          
         List<Element> lvListKunde = lvNodeInstitut.getChildren("Kunde");

         Element lvNodeKunde;
         for (int i = 0; i < lvListKunde.size(); i++)
         {
           lvNodeKunde = lvListKunde.get(i);  
           String lvKundennummer = lvNodeKunde.getAttributeValue("nr");
           if (lvKundennummer.equals(pvKundennummer))
           {
               // lvNodeKunde.getChildText("Vorname");
               return lvNodeKunde.getChildText("Nachname");
           }
         }
         
         return new String();
     }

     /**
      * Erzeugen eines Segments
      * @param pvStemp
      */
     private void erzeugeSegment(String pvStemp)
     {
        if (pvStemp.equals("BAUFI"))
         {
            ivStatus = BAUFI;
            ivBaufi = new BAUFI();
            ivBaufi.getKopf().setsDwhkdnr(ivKundennummer);
            ivBaufi.getKopf().setsDwhonr(ivObjektnummer);
            ivBaufi.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerBAUFI++;
         }
        if (pvStemp.equals("INF"))
         {
            ivStatus = INF;
            ivInf = new INF();
            ivInf.getKopf().setsDwhkdnr(ivKundennummer);
            ivInf.getKopf().setsDwhonr(ivObjektnummer);
            ivInf.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerINF++;
         }
        if (pvStemp.equals("KON"))
         {
            ivStatus = KON;
            ivKon = new KON();
            ivKon.getKopf().setsDwhkdnr(ivKundennummer);
            ivKon.getKopf().setsDwhonr(ivObjektnummer);
            ivKon.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerKON++;
         }
        if (pvStemp.equals("KONTS"))
         {
            ivStatus = KONTS;
            ivKonts = new KONTS();
            ivKonts.getKopf().setsDwhkdnr(ivKundennummer);
            ivKonts.getKopf().setsDwhonr(ivObjektnummer);
            ivKonts.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerKONTS++;
         }
        if (pvStemp.equals("KTOZB"))
         {
            ivStatus = KTOZB;
            ivKtozb = new KTOZB();
            ivKtozb.getKopf().setsDwhkdnr(ivKundennummer);
            ivKtozb.getKopf().setsDwhonr(ivObjektnummer);
            ivKtozb.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerKTOZB++;
         }
        if (pvStemp.equals("KTR"))
         {
            ivStatus = KTR;
            ivKtr = new KTR();
            ivKtr.getKopf().setsDwhkdnr(ivKundennummer);
            ivKtr.getKopf().setsDwhonr(ivObjektnummer);
            ivKtr.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerKTR++;
         }
        if (pvStemp.equals("KTS"))
         {
            ivStatus = KTS;
            ivKts = new KTS();
            ivKts.getKopf().setsDwhkdnr(ivKundennummer);
            ivKts.getKopf().setsDwhonr(ivObjektnummer);
            ivKts.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerKTS++;
         }
        if (pvStemp.equals("OBJ"))
         {
            ivStatus = OBJ;
            ivObj = new OBJ();
            ivObj.getKopf().setsDwhkdnr(ivKundennummer);
            ivObj.getKopf().setsDwhonr(ivObjektnummer);
            ivObj.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerOBJ++;
         }
        if (pvStemp.equals("DWHNREC"))
         {
            ivStatus = REC;
            ivRec = new REC();
            ivRec.getKopf().setsDwhkdnr(ivKundennummer);
            ivRec.getKopf().setsDwhonr(ivObjektnummer);
            ivRec.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerREC++;
         }
        if (pvStemp.equals("UMS"))
         {
            ivStatus = UMS;
            ivUms = new UMS();
            ivUms.getKopf().setsDwhkdnr(ivKundennummer);
            ivUms.getKopf().setsDwhonr(ivObjektnummer);
            ivUms.getKopf().setsDwhknr(ivKontonummer);
            //ivZaehlerUMS++;
         }
     }
     
     /**
      * Fuegt ein Segment dem Darlehen hinzu
      */
     private void einfuegenSegment()
     {
         switch (ivStatus)
         {
             case BAUFI:
                ivOriginalDarlehen.setBAUFI(ivBaufi);
                 break;
             case INF:
                ivOriginalDarlehen.addINF(ivInf);
                 break;
             case KON:
                ivOriginalDarlehen.addKON(ivKon);
                 break;
             case KONTS:
                ivOriginalDarlehen.addKONTS(ivKonts);
                 break;
             case KTOZB:
                ivOriginalDarlehen.addKTOZB(ivKtozb);
                 break;
             case KTR:
                ivOriginalDarlehen.setKTR(ivKtr);
                 break;
             case KTS:
                ivOriginalDarlehen.setKTS(ivKts);
                if (ivBaufi != null)
                 {
                    if (ivOriginalDarlehen.getKTS().getKopf().getsDwhkdnr().equals(ivBaufi.getKopf().getsDwhkdnr())
                            && ivOriginalDarlehen.getKTS().getKopf().getsDwhonr().equals(ivBaufi.getKopf().getsDwhonr()))
                         {
                        ivOriginalDarlehen.setBAUFI(ivBaufi);
                         }
                 }
                if (ivObj != null)
                 {
                    if (ivOriginalDarlehen.getKTS().getKopf().getsDwhkdnr().equals(ivObj.getKopf().getsDwhkdnr())
                            && ivOriginalDarlehen.getKTS().getKopf().getsDwhonr().equals(ivObj.getKopf().getsDwhonr()))
                     {
                        ivOriginalDarlehen.setOBJ(ivObj);
                     }
                 }
                 break;
             case OBJ:
                ivOriginalDarlehen.setOBJ(ivObj);
                 break;
             case REC:
                ivOriginalDarlehen.setREC(ivRec);
                 break;
             case UMS:
                ivOriginalDarlehen.setUMS(ivUms);
                 break;                         
              default:
                 System.out.println("einfuegenSegment - Unbekannte Satzart: " + ivStatus);
         }  
     }
     
     /**
      * Setzen eines Wertes in ein Segment
      * @param pvStatus
      * @param pvLfd
      * @param pvStemp
      */
    private void setzeWert(int pvStatus, int pvLfd, String pvStemp)
     {
        switch (pvStatus)
         {
             case BAUFI:
                ivBaufi.setBAUFI(pvLfd, pvStemp);
                 break;
             case INF:
                ivInf.setINF(pvLfd, pvStemp);
                 break;
             case KON:
                ivKon.setKON(pvLfd, pvStemp);
                 break;
             case KONTS:
                ivKonts.setKONTS(pvLfd, pvStemp);
                 break;
             case KTOZB:
                ivKtozb.setKTOZB(pvLfd, pvStemp);
                 break;
             case KTR:
                ivKtr.setKTR(pvLfd, pvStemp);
                 break;
             case KTS:
                ivKts.setKTS(pvLfd, pvStemp);
                 break;
             case OBJ:
                ivObj.setOBJ(pvLfd, pvStemp);
                 break;
             case REC:
                ivRec.setREC(pvLfd, pvStemp);
                 break;
             case UMS:
                ivUms.setUMS(pvLfd, pvStemp);
                 break;                         
              default:
                System.out.println("setzeWert - Unbekannte Satzart: " + pvStatus);
         }   
     }

    /**
     * @return 
     */
    public String printStatistik()
    {
      StringBuffer lvStringBuffer = new StringBuffer();
      lvStringBuffer.append(ivVorlaufsatz.toString());
      
      lvStringBuffer.append(StringKonverter.lineSeparator);
      /* lvStringBuffer.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerOBJ + " OBJ-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerBAUFI + " BAUFI-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerKTS + " KTS-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerKTR + " KTR-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerINF + " INF-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerKON + " KON-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerKONTS + " KONTS-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerKTOZB + " KTOZB-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerREC + " REC-Segmente gelesen...");
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append(ivZaehlerUMS + " UMS-Segmente gelesen..."); */
      lvStringBuffer.append("Anzahl Kredite - Aktiv: " + ivAnzahlAktiv);
      lvStringBuffer.append(StringKonverter.lineSeparator);
      lvStringBuffer.append("Anzahl Kredite - Passiv: " + ivAnzahlPassiv);
      lvStringBuffer.append(StringKonverter.lineSeparator);
      
      return lvStringBuffer.toString();
    } 
}
