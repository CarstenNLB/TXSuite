/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.Darlehen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.DarlehenXML;
import nlb.txs.schnittstelle.Darlehen.LeseVorlaufsatz;
import nlb.txs.schnittstelle.Darlehen.Daten.DarlehenKomplett;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
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
import nlb.txs.schnittstelle.Deckungspooling.DeckungspoolingVerarbeitung;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPSicherheitenVerarbeitung;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.InputObjektZuweisungsbetrag;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetragListe;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
@Deprecated
public class DarlehenVerarbeitung 
{
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

    // Status
    private int ivStatus;
    
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

    private File ivFileDarlehen;    
    
    private DWHVOR ivVorlaufsatz;
    private DarlehenKomplett ivOriginalDarlehen;
    private Darlehen ivZielDarlehen;
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
    
    private int ivZaehlerVorlaufsatz = 0;
    private int ivZaehlerBAUFI = 0;
    private int ivZaehlerINF = 0;
    private int ivZaehlerKON = 0;
    private int ivZaehlerKONTS = 0;
    private int ivZaehlerKTOZB = 0;
    private int ivZaehlerKTR = 0;
    private int ivZaehlerKTS = 0;
    private int ivZaehlerOBJ = 0;
    private int ivZaehlerREC = 0;
    private int ivZaehlerUMS = 0;
   
    //private OutputKontonummern outputKto;
    // CT - fuer Debugzwecke
    //private KontoListe ktoListe;
    
    private DeckungspoolingVerarbeitung ivDpp;
    
    private DarlehenXML ivDarlehenXML;
    private OutputDarlehenXML ivOutputDarlehenXML;
                
    // Zuweisungsbetrag der Objekte aus Darlehen --> aufsummiert
    private InputObjektZuweisungsbetrag ivOzw;
    
    // ObjektZuweisungsbetragListe
    private ObjektZuweisungsbetragListe ivOzwListe;
       
    // Transaktionen
    private TXSFinanzgeschaeft ivFg;
    private TXSSliceInDaten ivSliceInDaten;
    private TXSFinanzgeschaeftDaten ivFgdaten; 
    private TXSKonditionenDaten ivKondDaten;
    private TXSKreditKunde ivKredKunde;
    
    // Logger
    private Logger ivLogger;
    
    /**
     * 
     * @param pvLogger
     * @param pvDpp
     * @param pvImportVerzeichnis
     * @param pvExportVerzeichnis
     * @param pvObjektZWDatei
     * @param pvDarlehenDatei 
     * @param pvDarlehenTXSDatei 
     * @param pvDarlehenImportDatei 
     */
    public DarlehenVerarbeitung(Logger pvLogger, DeckungspoolingVerarbeitung pvDpp, String pvImportVerzeichnis, String pvExportVerzeichnis,
                                String pvObjektZWDatei, String pvDarlehenDatei, String pvDarlehenTXSDatei, String pvDarlehenImportDatei)
    {
        this.ivLogger = pvLogger;
        this.ivDpp = pvDpp;
        
        this.ivFg = new TXSFinanzgeschaeft();
        this.ivSliceInDaten = new TXSSliceInDaten();
        this.ivFgdaten = new TXSFinanzgeschaeftDaten();
        this.ivKondDaten = new TXSKonditionenDaten();
        this.ivKredKunde = new TXSKreditKunde();
        
        // ObjektZuweisungsbetrag-Datei einlesen
        ivOzw = new InputObjektZuweisungsbetrag(pvImportVerzeichnis + "\\" + pvObjektZWDatei);
        ivOzw.open();
        ivOzwListe = ivOzw.readObjektZuweisungsbetrag();
        ivOzw.close();
        
        // Darlehen XML-Datei
        ivDarlehenXML = new DarlehenXML(pvExportVerzeichnis + "\\" + pvDarlehenDatei, ivLogger);
        ivDarlehenXML.openXML();
        
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(pvExportVerzeichnis + "\\" + pvDarlehenTXSDatei, ivLogger);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
                
        // Darlehen einlesen und verarbeiten
        readDarlehen(pvImportVerzeichnis + "\\" + pvDarlehenImportDatei);
 
        // Alle Files wieder schliessen ...        
        ivDarlehenXML.printXMLEnde();
        ivDarlehenXML.closeXML();
        
        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML(); 
    }
    
    /**
     * 
     */
    private void readDarlehen(String pvDateiname)
    {
        String lvZeile = null;
        
        ivOriginalDarlehen = new DarlehenKomplett(ivLogger);
        
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        ivFileDarlehen = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileDarlehen);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Darlehen-Datei nicht oeffnen!");
            return;
        }

        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        boolean lvStart = true;
          
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen Darlehen-Datei
            {
              if (lvStart)
              {
                  LeseVorlaufsatz lvLeseVorlaufsatz = new LeseVorlaufsatz(); 
                  ivVorlaufsatz = lvLeseVorlaufsatz.parseVorlaufsatz(lvZeile);
                  ivZaehlerVorlaufsatz++;
                  ivOriginalDarlehen.setAnzahlDatum(ivVorlaufsatz.getAnzahlDatum());
                  //DatumUtilities.berechneBuchungstagPlus2(vorlaufsatz.getsDwvbdat());
                  ivOriginalDarlehen.setBuchungsdatum(ivVorlaufsatz.getsDwvbdat());
                  ivOriginalDarlehen.setInstitutsnummer(ivVorlaufsatz.getsDwvinst());
                  ivDarlehenXML.printXMLStart(ivVorlaufsatz);
                  ivOutputDarlehenXML.printTXSImportDatenStart();
                  ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(ivVorlaufsatz.getsDwvbdat())));
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
            //System.out.println("Deckungskennzeichen: " + ktr.getsDd());

            // Pruefe, ob das Datum im KON, KONTS und KTOZB korrekt ist
            ivOriginalDarlehen.pruefeDatum();
            
            if (!ivOriginalDarlehen.existsDummySegment() && 
                ivOriginalDarlehen.existsPflichtsegmente() &&   
                (ivKtr.getsDd().equals("F") || ivKtr.getsDd().equals("D") || ivKtr.getsDd().equals("5") || ivKtr.getsDd().equals("6")))
            {
                  verarbeiteDarlehen();
            }
          }
          
          try
          {
            lvIn.close();
          }
          catch (Exception e)
          {
            System.out.println("Konnte Darlehen-Datei nicht schliessen!");
          }
             
          //if (logging)
          //{
          System.out.println(printStatistik());
          //}
        }

      /**
       * @return 
       */
      public String printStatistik()
      {
        StringBuffer lvOut = new StringBuffer();
        lvOut.append(ivVorlaufsatz.toString());
        
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerBAUFI + " BAUFI-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerINF + " INF-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKON + " KON-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKONTS + " KONTS-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKTOZB + " KTOZB-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKTR + " KTR-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKTS + " KTS-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerOBJ + " OBJ-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerREC + " REC-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerUMS + " UMS-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        
        return lvOut.toString();
      }

      /**
       * @param pvZeile 
       * @return 
       *       
       */
     private boolean parseDarlehen(String pvZeile)
     {             
        String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
        int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
        int    lvZzStr=0;              // pointer fuerr satzbereich
        
        ivStatus = UNDEFINIERT;
        
        // steuerung/iteration eingabesatz
        for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
        {

          // wenn semikolon erkannt
          if (pvZeile.charAt(lvZzStr) == ';')
          {
              if (lvLfd < 4)
              {
                  if (lvLfd == 0)
                  {
                      ivKundennummer = lvTemp;
                  }
                  if (lvLfd == 1)
                  {
                      ivObjektnummer = lvTemp;
                  }
                  if (lvLfd == 2)
                  {
                      ivKontonummer = lvTemp;
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
                              //System.out.println("Deckungskennzeichen: " + ktr.getsDd());
                              
                              // Pruefe, ob das Datum in den KON, KONTS und KTOZB korrekt ist
                              ivOriginalDarlehen.pruefeDatum();
                              
                              if (!ivOriginalDarlehen.existsDummySegment() &&
                                  ivOriginalDarlehen.existsPflichtsegmente() &&    
                                 (ivKtr.getsDd().equals("F") || ivKtr.getsDd().equals("D") || ivKtr.getsDd().equals("5") || ivKtr.getsDd().equals("6")))
                              {
                                    verarbeiteDarlehen();
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
                    //stemp = stemp.trim();
                    erzeugeSegment(lvTemp.trim());
                  }
              }
              else
              {
                setzeWert(ivStatus, lvLfd, lvTemp);    
              }

              lvLfd++;                  // naechste feldnummer
              // loeschen zwischenbuffer
              lvTemp = new String();
          }
          else
          {
              // uebernehmen byte aus eingabesatzposition
              lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
          }
      } // ende for  
      
      // Letzten Wert der Zeile/Segment auch noch setzen
      if (!lvTemp.isEmpty())
      {
          setzeWert(ivStatus, lvLfd, lvTemp);
      }  
      return true; 
     }

     /**
      * Verarbeite das Darlehen
      */
     private void verarbeiteDarlehen()
     {
         // Kundennummern rausschreiben
         //System.out.println("Darlehen muss verarbeitet werden...");
                                           
          // OBJ-Segment vorhanden?
         //if (originalDarlehen.getOBJ() != null)
         //{
         //  // Sicherheiten rausschreiben
         //  Sicherheit sicherheit = new Sicherheit();
         //  if (sicherheit.extractSicherheit(originalDarlehen))
         //  {
         //    if (!listeSicherheiten.contains(sicherheit.getObjektnummer()))
         //    {
         //      listeSicherheiten.add(sicherheit);  
         //      sicherheitenXML.printSicherheit(sicherheit);
         //    }
         //  }
          
           // Sicherungsobjekte rausschreiben
           //Sicherungsobjekt sObj = new Sicherungsobjekt();
           //if (sObj.extractSicherungsobjekt(originalDarlehen))
           //{
           //    if (!listeSicherungsobjekte.contains(sObj.getObjektnummer()))
           //    {
           //      listeSicherungsobjekte.add(sObj);
           //      sObjXML.printSicherungsobjekte(sObj);
           //    }
           //}
           
         //}         
         
         //zielDarlehen = new Darlehen(log);
         ivZielDarlehen = new Darlehen(ivLogger, new String());
         ivZielDarlehen.extractDarlehen(ivOriginalDarlehen);
           //if (zielDarlehen.getSelektionskennzeichen().equals("W"))
           //{
           //    zielDarlehen.extractWPBesonderheiten(originalDarlehen);
           //    if (zielDarlehen.darlehenPruefen())
           //    {
           //        darlehenWertpapierXML.printDarlehen(zielDarlehen);
           //    }
           //}
           if (ivZielDarlehen.getSelektionskennzeichen().equals("E"))
           {  
             if (ivZielDarlehen.darlehenPruefen())
             {
               ivDarlehenXML.printDarlehen(ivZielDarlehen);
               try
               {
                 importDarlehen2Transaktion();
               }
               catch (Exception exp)
               {
                   //log.write("Verarbeitungsfehler - Konto " + zielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
                   //log.write(exp.getMessage());
                   exp.printStackTrace();
               }
             }
           }
     }
     
    /**
      * Erzeugen eines Segments
      * @param pvTemp
      */
     private void erzeugeSegment(String pvTemp)
     {
         if (pvTemp.equals("BAUFI"))
         {
             ivStatus = BAUFI;
             ivBaufi = new BAUFI();
             ivBaufi.getKopf().setsDwhkdnr(ivKundennummer);
             ivBaufi.getKopf().setsDwhonr(ivObjektnummer);
             ivBaufi.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerBAUFI++;
         }
         if (pvTemp.equals("INF"))
         {
             ivStatus = INF;
             ivInf = new INF();
             ivInf.getKopf().setsDwhkdnr(ivKundennummer);
             ivInf.getKopf().setsDwhonr(ivObjektnummer);
             ivInf.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerINF++;
         }
         if (pvTemp.equals("KON"))
         {
             ivStatus = KON;
             ivKon = new KON();
             ivKon.getKopf().setsDwhkdnr(ivKundennummer);
             ivKon.getKopf().setsDwhonr(ivObjektnummer);
             ivKon.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKON++;
         }
         if (pvTemp.equals("KONTS"))
         {
             ivStatus = KONTS;
             ivKonts = new KONTS();
             ivKonts.getKopf().setsDwhkdnr(ivKundennummer);
             ivKonts.getKopf().setsDwhonr(ivObjektnummer);
             ivKonts.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKONTS++;
         }
         if (pvTemp.equals("KTOZB"))
         {
             ivStatus = KTOZB;
             ivKtozb = new KTOZB();
             ivKtozb.getKopf().setsDwhkdnr(ivKundennummer);
             ivKtozb.getKopf().setsDwhonr(ivObjektnummer);
             ivKtozb.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKTOZB++;
         }
         if (pvTemp.equals("KTR"))
         {
             ivStatus = KTR;
             ivKtr = new KTR();
             ivKtr.getKopf().setsDwhkdnr(ivKundennummer);
             ivKtr.getKopf().setsDwhonr(ivObjektnummer);
             ivKtr.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKTR++;
         }
         if (pvTemp.equals("KTS"))
         {
             ivStatus = KTS;
             ivKts = new KTS();
             ivKts.getKopf().setsDwhkdnr(ivKundennummer);
             ivKts.getKopf().setsDwhonr(ivObjektnummer);
             ivKts.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKTS++;
         }
         if (pvTemp.equals("OBJ"))
         {
             ivStatus = OBJ;
             ivObj = new OBJ();
             ivObj.getKopf().setsDwhkdnr(ivKundennummer);
             ivObj.getKopf().setsDwhonr(ivObjektnummer);
             ivObj.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerOBJ++;
         }
         if (pvTemp.equals("DWHNREC"))
         {
             ivStatus = REC;
             ivRec = new REC();
             ivRec.getKopf().setsDwhkdnr(ivKundennummer);
             ivRec.getKopf().setsDwhonr(ivObjektnummer);
             ivRec.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerREC++;
         }
         if (pvTemp.equals("UMS"))
         {
             ivStatus = UMS;
             ivUms = new UMS();
             ivUms.getKopf().setsDwhkdnr(ivKundennummer);
             ivUms.getKopf().setsDwhonr(ivObjektnummer);
             ivUms.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerUMS++;
         }
     }
     
     /**
      * F�gt ein Segment dem Darlehen hinzu
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
                     //baufi = null;
                 }
                 if (ivObj != null)
                 {
                     if (ivOriginalDarlehen.getKTS().getKopf().getsDwhkdnr().equals(ivObj.getKopf().getsDwhkdnr()) 
                         && ivOriginalDarlehen.getKTS().getKopf().getsDwhonr().equals(ivObj.getKopf().getsDwhonr()))
                     {
                         ivOriginalDarlehen.setOBJ(ivObj);
                     }
                     //obj = null;
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
                 System.out.println("addSegment: Unbekannte Satzart: " + ivStatus);
         }  
     }
     
     /**
      * Setzen eines Wertes in ein Segment
      */
     private void setzeWert(int pvStatus, int pvLfd, String pvTemp)
     {
         switch (pvStatus)
         {
             case BAUFI:
                 ivBaufi.setBAUFI(pvLfd, pvTemp);
                 break;
             case INF:
                 ivInf.setINF(pvLfd, pvTemp);
                 // CT 08.06.2012 - Wenn OFLEI, dann Kontonummer ins Logfile schreiben
                 if (pvLfd == 4)
                 {
                   //if (inf.getKopf().getsDwhtdb().equals("OFLEI"))
                   //{
                   //  if (log != null)
                   //  {
                   //    log.writeln("OFLEI - Kontonummer: " + inf.getKopf().getsDwhknr());
                   //  }
                   //}
                 }
                 break;
             case KON:
                 ivKon.setKON(pvLfd, pvTemp);
                 break;
             case KONTS:
                 ivKonts.setKONTS(pvLfd, pvTemp);
                 break;
             case KTOZB:
                 ivKtozb.setKTOZB(pvLfd, pvTemp);
                 break;
             case KTR:
                 ivKtr.setKTR(pvLfd, pvTemp);
                 break;
             case KTS:
                 ivKts.setKTS(pvLfd, pvTemp);
                 break;
             case OBJ:
                 ivObj.setOBJ(pvLfd, pvTemp);
                 break;
             case REC:
                 ivRec.setREC(pvLfd, pvTemp);
                 break;
             case UMS:
                 ivUms.setUMS(pvLfd, pvTemp);
                 break;                         
              default:
                 System.out.println("Unbekannte Satzart: " + pvStatus);
         }   
     }
     
     /**
      * Importiert die Darlehensinformationen in die TXS-Transaktionen
      */
     public void importDarlehen2Transaktion()
     {         
         //System.out.println("importDarlehen2Transaktion");
         ivFg.initTXSFinanzgeschaeft();
         ivSliceInDaten.initTXSSliceInDaten();
         ivFgdaten.initTXSFinanzgeschaeftDaten();
         ivKondDaten.initTXSKonditionenDaten();
         ivKredKunde.initTXSKreditKunde();
                  
         boolean lvOkayDarlehen = true;

         // Originator und Institutsnummer ueber die Kontonummer ermitteln
         String lvInstKennung = new String();
         System.out.println("CT - Kontonummer: " + ivZielDarlehen.getKontonummer());
         if (ivDpp.getInstitutskennung(ivZielDarlehen.getKontonummer()).isEmpty())
         {
           System.out.println("Kein Institut fuer Kontonummer " + ivZielDarlehen.getKontonummer() + " gefunden.");
           lvOkayDarlehen = false;
         }
         else
         {
             lvInstKennung = ivDpp.getInstitutskennung(ivZielDarlehen.getKontonummer());
         }
         String lvKundenNr = new String();
         if (ivDpp.getKundennummer(ivZielDarlehen.getKontonummer()).isEmpty())
         {
           System.out.println("Keine Kundennummer fuer Kontonummer " + ivZielDarlehen.getKontonummer() + " gefunden.");
           lvOkayDarlehen = false;
         }
         else
         {
             lvKundenNr = ivDpp.getKundennummer(ivZielDarlehen.getKontonummer());
         }
         
         if (lvOkayDarlehen)
         {
           lvOkayDarlehen = ivFg.importDarlehen(DarlehenVerarbeiten.DPP, ivZielDarlehen);
         }
         
         if (lvOkayDarlehen)
         {
             // Originator und Quelle fuer DPP umsetzen
             ivFg.setKey(lvInstKennung + "_" + ivFg.getKey());
             ivFg.setOriginator(lvInstKennung);
             ivFg.setQuelle("DPP");
             lvOkayDarlehen = ivFgdaten.importDarlehen(DarlehenVerarbeiten.DPP, ivZielDarlehen, ivLogger);
          }
         if (lvOkayDarlehen)
         {
             lvOkayDarlehen = ivSliceInDaten.importDarlehen(DarlehenVerarbeiten.DPP, ivZielDarlehen, ivLogger);
         }
         if (lvOkayDarlehen)
         {
             // Key, Transaktion und Pool fuer DPP je nach Produktschluessel umsetzen
             if (ivZielDarlehen.getProduktSchluessel().equals("00117"))
             {
                 ivSliceInDaten.setKey(ivZielDarlehen.getKontonummer() + "P");
                 ivSliceInDaten.setTx("Hypothekenpfandbrief");
                 ivSliceInDaten.setPool("Hypothekenpfandbrief");
             }
             ivSliceInDaten.setBis(ivFgdaten.getNbetrag());
             
             lvOkayDarlehen = ivKondDaten.importDarlehen(DarlehenVerarbeiten.DARKA, ivZielDarlehen, ivLogger);
             // TXS muss selber rechnen, da keine Cashflows angeliefert werden
             ivKondDaten.setMantilg("0");
             ivKondDaten.setManzins("0");
         }
         if (lvOkayDarlehen)
         {
             lvOkayDarlehen = ivKredKunde.importDarlehen(DarlehenVerarbeiten.DPP, ivZielDarlehen);
         }
         
         if (lvOkayDarlehen)
         {
             // Originator und Quelle fuer DPP umsetzen
             ivKredKunde.setKdnr(lvInstKennung + "_" + lvKundenNr);
             ivKredKunde.setOrg(lvInstKennung);
             ivKredKunde.setQuelle("DPP");
         }
         
         // Transaktionen in die Datei schreiben
         if (lvOkayDarlehen)
         {
             ivOutputDarlehenXML.printTransaktion(ivFg.printTXSTransaktionStart());
 
             ivOutputDarlehenXML.printTransaktion(ivFgdaten.printTXSTransaktionStart()); 
             ivOutputDarlehenXML.printTransaktion(ivFgdaten.printTXSTransaktionDaten()); 
             ivOutputDarlehenXML.printTransaktion(ivFgdaten.printTXSTransaktionEnde());
 
             ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionStart());
             ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionDaten());   
             ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionEnde());

             ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
             ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
             ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());
 
             ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionStart());
             ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionDaten());
             ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionEnde());
          }

         // Sicherheiten aus OSPlus verarbeiten
         OSPSicherheitenVerarbeitung lvShVerarbeitung = ivDpp.getSicherheitenVerarbeitung();
         if (lvShVerarbeitung != null)
         {
             //System.out.println("shVerarbeitung");
             ivOutputDarlehenXML.printTransaktion(lvShVerarbeitung.importSicherheitObjekt2TXSKreditSicherheit(ivZielDarlehen));
         }
                          
         if (lvOkayDarlehen)
         {
             ivOutputDarlehenXML.printTransaktion(ivFg.printTXSTransaktionEnde());
         }
     }
}
