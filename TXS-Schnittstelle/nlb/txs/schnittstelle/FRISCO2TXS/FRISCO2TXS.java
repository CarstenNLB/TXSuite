/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.FRISCO2TXS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzierungKredit;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSTriebwerkDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Wechselkurse.LeseWechselkurse;
import nlb.txs.schnittstelle.Wechselkurse.Wechselkurse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author tepperc
 *
 */
public class FRISCO2TXS 
{
 
    /**
     * Waehrung der Solldeckung aus DarKa
     */
    private HashMap<String, String> ivHashMapSolldeckungWaehrung;

    /**
     * Institut
     */
    private String ivInstitut;
    
    /**
     * Verarbeitung Schiff oder Flugzeug
     */
    private String ivVerarbeitung;
    
    /**
     * FRISCO-File
     */
    private File ivFileFRISCO;
    
    /**
     * Schiffsdaten
     */
    private Schiff ivSchiff;

    /**
     * Flugzeugdaten
     */
    private Flugzeug ivFlugzeug;
    
    /**
     * XML-Ausgabedatei
     */
    private OutputDarlehenXML ivOutputDarlehenXML;

    /**
     * Solldeckung aus DarKa
     */
    private HashMap<String, String> ivHashMapSolldeckungBetrag;
    
    /**
     * Wechselkurse
     */
    private HashMap<String, Wechselkurse> ivHashMapWechselkurse;
        
    /**
     * Startroutine FRISCO2TXS
     * @param argv 
     */
    public static void main(String argv[])
    { 
        if (argv.length == 0)
        {
            System.out.println("Starten:");
            System.out.println("FRISCO2TXS [BLB|NLB] [Schiffe|Flugzeuge] <FRISCO-Datei>");
            System.exit(0);
        }
        else
        {
            new FRISCO2TXS(argv[argv.length - 5], argv[argv.length - 4], argv[argv.length - 3], argv[argv.length - 2], argv[argv.length - 1]);
        }
        System.exit(0);
    }

    /**
     * Konstruktor
     * @param pvInstitut 
     * @param pvVerarbeitung 
     * @param pvDateiname 
     * @param pvSolldeckungDateiname
     * @param pvWechselkurseDateiname 
     */
    public FRISCO2TXS(String pvInstitut, String pvVerarbeitung, String pvDateiname, String pvSolldeckungDateiname, String pvWechselkurseDateiname)
    {
        System.out.println("Dateiname: " + pvDateiname);
        System.out.println("Solldeckung - Dateiname: " + pvSolldeckungDateiname);
        System.out.println("Wechselkurse - Dateiname: " + pvWechselkurseDateiname);
        
        // Solldeckung aus DarKa einlesen
        this.ivHashMapSolldeckungBetrag = new HashMap<String, String>();
        this.ivHashMapSolldeckungWaehrung = new HashMap<String, String>();
        leseDarKaDaten(pvSolldeckungDateiname);
 
        // Wechselkurse einlesen
        this.ivHashMapWechselkurse = new HashMap<String, Wechselkurse>();
        leseWechselkurse(pvWechselkurseDateiname);
        
        this.ivInstitut = pvInstitut;
        this.ivVerarbeitung = pvVerarbeitung;
        
        // Darlehen XML-Datei im TXS-Format
        //if (pvVerarbeitung.equals("Schiffe"))
        //{
        //  ivOutputDarlehenXML = new OutputDarlehenXML("D:\\" + pvVerarbeitung + "_" + pvInstitut + "_TXS.xml");
        //}
        //if (pvVerarbeitung.equals("Flugzeuge"))
        //{
        ivOutputDarlehenXML = new OutputDarlehenXML("D:\\" + pvVerarbeitung + "_" + pvInstitut + "_TXS.xml");
        //}
        if (ivOutputDarlehenXML != null)
        {
            ivOutputDarlehenXML.openXML();
            ivOutputDarlehenXML.printXMLStart();
            ivOutputDarlehenXML.printTXSImportDatenStart();
            // Aktuelle Datum in 'valdate' schreiben
            Calendar lvCal = GregorianCalendar.getInstance();
            //Calendar lvCal = Calendar.getInstance();
            CalendarHelper lvCh = new CalendarHelper();
            ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(lvCh.printDate(lvCal)));
        
            readFRISCO(pvDateiname);
        
            ivOutputDarlehenXML.printTXSImportDatenEnde();
            ivOutputDarlehenXML.closeXML();
        }
        else
        {
            System.out.println("Konnte Ausgabedatei " + pvVerarbeitung + " nicht oeffnen");
        }
    }
    
    /**
     * Liest die Schiffs- und Flugzeugdaten vom FRISCO-Export
     * @param pvDateiname
     */
    private void readFRISCO(String pvDateiname)
    {
        String lvZeile = null;
                
        // Oeffnen der Dateien
        FileInputStream lvFileInputStream = null;
        ivFileFRISCO = new File(pvDateiname);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileFRISCO);
        }
        catch (Exception e)
        {
            System.out.println("Konnte FRISCO-Datei nicht oeffnen!");
            return;
        }

        BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        boolean lvStart = true;
          
        try
        {
            while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Lesen FRISCO-Datei
            {
              if (lvStart)
              {
                  // 1.Zeile ueberlesen
                  lvStart = false;
              }
              else
              {
                  if (ivVerarbeitung.equals("Schiffe"))
                  {
                    ivSchiff = new Schiff();
                    if (!parseSchiff(lvZeile)) // Parsen der Felder
                    {
                        System.out.println("Datenfehler Schiff!!!\n");
                    }
                    verarbeiteSchiff();
                  }
                  if (ivVerarbeitung.equals("Flugzeuge"))
                  {
                      ivFlugzeug = new Flugzeug();
                      if (!parseFlugzeug(lvZeile)) // Parsen der Felder
                      {
                          System.out.println("Datenfehler Flugzeug!!!\n");
                      }
                      verarbeiteFlugzeug();
                  }
              }
            }
          }
          catch (Exception e)
          {
            System.out.println("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
          }
    }
    
    /**
     * Zerlegen einer Zeile von Schiffsdaten in einzelne Felder
     * @param pvZeile 
     * @return       
     */
     private boolean parseSchiff(String pvZeile)
     {         
        String stemp = new String(); // arbeitsbereich/zwischenspeicher feld
        int lvLfd = 0; // lfd feldnr, pruefsumme je satzart
        int lvZzStr = 0; // pointer fuer satzbereich
                
        // steuerung/iteration eingabesatz
        for (lvZzStr = 0; lvZzStr < pvZeile.length(); lvZzStr++)
        {
          // wenn semikolon erkannt
          if (pvZeile.charAt(lvZzStr) == ';')
          {
              ivSchiff.setzeWert(lvLfd, stemp);

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
            ivSchiff.setzeWert(lvLfd, stemp);
      }  
      
      return true; 
     }

     /**
      * Zerlegen der Zeile von Flugzeugdaten in einzelne Felder
      * @param pvZeile 
      * @return   
      */
      private boolean parseFlugzeug(String pvZeile)
      {         
         String stemp = new String(); // arbeitsbereich/zwischenspeicher feld
         int lvLfd = 0; // lfd feldnr, pruefsumme je satzart
         int lvZzStr = 0; // pointer fuer satzbereich
                
         // steuerung/iteration eingabesatz
         for (lvZzStr = 0; lvZzStr < pvZeile.length(); lvZzStr++)
         {
           // wenn semikolon erkannt
           if (pvZeile.charAt(lvZzStr) == ';')
           {
               ivFlugzeug.setzeWert(lvLfd, stemp);

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
             ivFlugzeug.setzeWert(lvLfd, stemp);
       }  
       
       return true; 
      }  

    /**
     * Verarbeite Schiffe
     */
    private void verarbeiteSchiff()
    {
        TXSFinanzgeschaeft lvFinanzgeschaeft = new TXSFinanzgeschaeft();   
        lvFinanzgeschaeft.setKey(ivSchiff.getKontonummer());
        lvFinanzgeschaeft.setOriginator(ValueMapping.changeMandant(ivInstitut));
        lvFinanzgeschaeft.setQuelle("ADARLSCHF");
               
        TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
        //if (ivInstitut.equals("NLB"))
        //{
        //   lvKredsh.setKey(ivSchiff.getSicherheitenNr());
        //}
        //else
        //{
        lvKredsh.setKey(ivSchiff.getSicherheitenNr());
        //}
        lvKredsh.setOrg(ValueMapping.changeMandant(ivInstitut));
        lvKredsh.setQuelle("TXS");
        if (ivHashMapSolldeckungBetrag.get(ivSchiff.getKontonummer()) != null)
        {            
          lvKredsh.setZuwbetrag(ivHashMapSolldeckungBetrag.get(ivSchiff.getKontonummer()));
          lvKredsh.setWhrg(ivHashMapSolldeckungWaehrung.get(ivSchiff.getKontonummer()));
        }

        // Defaultmaessig auf 'Ja' (1)
        lvKredsh.setHauptsh("1");
        
        TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
        // Schiffshypothek
        lvShdaten.setArt("56");
        lvShdaten.setBez(String2XML.change2XML(ivSchiff.getSchiffsname()));
        // Defaultmaessig auf 'Ja' (1)
        lvShdaten.setGepr("1");
        
        lvShdaten.setNbetrag(ivSchiff.getNennbetrag());

        BigDecimal lvHelpKonsortialanteil = (new BigDecimal(ivSchiff.getVerfuegungsbetrag())).divide(new BigDecimal(ivSchiff.getNennbetrag()), 9, RoundingMode.HALF_UP);

        // Verfuegungsbetrag - nur NLB
        // min { 0,6 * BLW --  (Vorlasten/Gleichlasten --> falls vorhanden); Nominalwert der Sicherheit}  * Konsortialanteil 
        if (ivInstitut.equals("NLB"))
        {
          BigDecimal lvHelpBeleihungswert = new BigDecimal(ivSchiff.getBeleihungswertAkt());
          BigDecimal lvHelpNennbetrag = new BigDecimal(ivSchiff.getNennbetrag());
          BigDecimal lvHelpWechselkursBeleihungswert = new BigDecimal("1.0");
          BigDecimal lvHelpWechselkursNennbetrag = new BigDecimal("1.0");
          lvHelpBeleihungswert = (lvHelpBeleihungswert.multiply(new BigDecimal("0.6"))).multiply(lvHelpKonsortialanteil);
          lvHelpNennbetrag = lvHelpNennbetrag.multiply(lvHelpKonsortialanteil);
          
          if (ivHashMapWechselkurse.get(ivSchiff.getWaehrungBeleihungswertAkt()) != null)
          {
              lvHelpWechselkursBeleihungswert = new BigDecimal((ivHashMapWechselkurse.get(ivSchiff.getWaehrungBeleihungswertAkt())).getMittelkurs());
          }
          if (ivHashMapWechselkurse.get(ivSchiff.getWaehrungSicherheit()) != null)
          {
              lvHelpWechselkursNennbetrag = new BigDecimal((ivHashMapWechselkurse.get(ivSchiff.getWaehrungSicherheit())).getMittelkurs());
          }
          BigDecimal lvHelpBeleihungswertEUR = lvHelpBeleihungswert.divide(lvHelpWechselkursBeleihungswert, 9, RoundingMode.HALF_UP);
          BigDecimal lvHelpNennbetragEUR = lvHelpNennbetrag.divide(lvHelpWechselkursNennbetrag, 9, RoundingMode.HALF_UP);
          
          if (lvHelpBeleihungswertEUR.doubleValue() < lvHelpNennbetragEUR.doubleValue())
          {
              lvShdaten.setVbetrag(lvHelpBeleihungswert.toString());
              lvShdaten.setWhrg(ivSchiff.getWaehrungBeleihungswertAkt());
          }
          else
          {
              lvShdaten.setVbetrag(lvHelpNennbetrag.toString());
              lvShdaten.setWhrg(ivSchiff.getWaehrungSicherheit());
          }
        }
        else
        {
          lvShdaten.setVbetrag(ivSchiff.getVerfuegungsbetrag());
          lvShdaten.setWhrg(ivSchiff.getWaehrungSicherheit());
        }
         
        TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
        //if (ivInstitut.equals("NLB"))
        //{
        //   lvShve.setVenr(ivSchiff.getObjektNr().substring(ivSchiff.getObjektNr().length() - 11));
        //}
        //else
        //{
            lvShve.setVenr(ivSchiff.getRegistereintragsnr());
        //}
        lvShve.setOrg(ValueMapping.changeMandant(ivInstitut));
        lvShve.setQuelle("TXS");
        
        TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
        TXSVerzeichnisblattDaten lvVbdaten = new TXSVerzeichnisblattDaten();
        TXSVerzeichnisVBlatt lvVevb = new TXSVerzeichnisVBlatt();

        // ZuweisungsbetragPO
        if (ivInstitut.equals("NLB"))
        {
            lvVedaten.setBetrag(lvShdaten.getVbetrag());
            lvVedaten.setWhrg(lvShdaten.getWhrg());              
        }
        else
        {
          // min {Konsortialanteil in Prozent * 60% * aktuelle Beleihungswert; Verfuegungsbetrag}
          BigDecimal lvHelpWechselkursVerf = new BigDecimal("1.0");
          BigDecimal lvHelpWechselkursBeleih = new BigDecimal("1.0");

          if (ivHashMapWechselkurse.get(ivSchiff.getWaehrungSicherheit()) != null)
          {
            lvHelpWechselkursVerf = new BigDecimal((ivHashMapWechselkurse.get(ivSchiff.getWaehrungSicherheit())).getMittelkurs());
          }
          if (ivHashMapWechselkurse.get(ivSchiff.getWaehrungBeleihungswertAkt()) != null)
          {
            lvHelpWechselkursBeleih = new BigDecimal((ivHashMapWechselkurse.get(ivSchiff.getWaehrungBeleihungswertAkt())).getMittelkurs());
          }
          //System.out.println("Wechselkurs - Verfuegungsbetrag (" + ivSchiff.getWaehrungSicherheit() + "): " + lvHelpWechselkursVerf.toString());
          //System.out.println("Wechselkurs - Beleihungswert (" + ivSchiff.getWaehrungBeleihungswertAkt() + "): " + lvHelpWechselkursBeleih.toString());
       
          BigDecimal lvHelpVerf = new BigDecimal(ivSchiff.getVerfuegungsbetrag());
          BigDecimal lvHelpBeleihAkt = new BigDecimal(ivSchiff.getBeleihungswertAkt());
          //System.out.println("Verfuegungsbetrag: " + ivSchiff.getVerfuegungsbetrag());
          //System.out.println("Beleihungswert: " + ivSchiff.getBeleihungswertAkt());
          //lvHelpKonsortialanteil = lvHelpVerf.divide(new BigDecimal(ivSchiff.getNennbetrag()), 9, RoundingMode.HALF_UP);
          lvHelpBeleihAkt = lvHelpBeleihAkt.multiply(new BigDecimal("0.6")).multiply(lvHelpKonsortialanteil); // 60% Prozent * Konsortialanteil     
 
          BigDecimal lvHelpVerfEUR = lvHelpVerf.divide(lvHelpWechselkursVerf, 9, RoundingMode.HALF_UP);
          BigDecimal lvHelpBeleihAktEUR = lvHelpBeleihAkt.divide(lvHelpWechselkursBeleih, 9, RoundingMode.HALF_UP);
        
          //System.out.println(lvHelpVerf.toString() + " --> EUR " + lvHelpVerfEUR.toString());
          //System.out.println(lvHelpBeleihAkt.toString() + " --> EUR " + lvHelpBeleihAktEUR.toString());
        
          if (lvHelpVerfEUR.doubleValue() < lvHelpBeleihAktEUR.doubleValue()) 
          {
              lvVedaten.setBetrag(lvHelpVerf.toString());
              lvVedaten.setWhrg(ivSchiff.getWaehrungSicherheit());
          }
          else
          {
              lvVedaten.setBetrag(lvHelpBeleihAkt.toString());
              lvVedaten.setWhrg(ivSchiff.getWaehrungBeleihungswertAkt());
          }
        }
                  
        if (ivSchiff.getRegisterland().equals("DE"))
        {   

            lvVedaten.setAbt("3");
            lvVedaten.setGbart("200");
            lvVedaten.setNrabt(ivSchiff.getAbteilung3());
        }
        else
        {
            lvVedaten.setGbart("201");
            lvVedaten.setNrabt("n.r.");
        }
                       
        lvVedaten.setKat("2");
        lvVevb.setVbnr(ivSchiff.getRegistereintragsnr() + ivSchiff.getAbteilung3());
        lvVevb.setOrg(ValueMapping.changeMandant(ivInstitut));
        lvVevb.setQuelle("TXS");
        
        lvVbdaten.setKat("2");
        if (ivInstitut.equals("NLB"))
        {
            lvVbdaten.setGbvon(String2XML.change2XML(ivSchiff.getRegisterort()));
        }
        else
        {
            lvVbdaten.setGbvon(String2XML.change2XML(convertRegisterort(ivSchiff.getRegisterland() + ivSchiff.getRegisterort())));
        }
        lvVbdaten.setBlatt(ivSchiff.getBlatt()); //.replace("Blatt ", ""));

        TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
        lvVepo.setObjnr(ivSchiff.getObjektNr());
        lvVepo.setOrg(ValueMapping.changeMandant(ivInstitut));
        lvVepo.setQuelle("TXS");
        
        TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();
        lvPodaten.setBez(String2XML.change2XML(ivSchiff.getSchiffsname()));
        lvPodaten.setImo(ivSchiff.getImoNr());
        lvPodaten.setKat("2");
        lvPodaten.setWhrg(ivSchiff.getWaehrungBeleihungswertAkt());
        lvPodaten.setBwert(ivSchiff.getBeleihungswertAkt());
        lvPodaten.setBwertdat(DatumUtilities.changeDate(ivSchiff.getBeleihungswertVom()));
        lvPodaten.setLand(ivSchiff.getRegisterland());
        // Konsortialanteil errechnen
        lvHelpKonsortialanteil = lvHelpKonsortialanteil.multiply(new BigDecimal("100")); // * 100 -> Prozent
        lvPodaten.setKonsortialanteil(lvHelpKonsortialanteil.toString());
        lvPodaten.setBlhwertinit(ivSchiff.getBeleihungswertInd() + " " + ivSchiff.getWaehrungBeleihungswertInd());
        
        setzeSchiffsdaten(lvPodaten);
        
        lvPodaten.setAusldat(DatumUtilities.changeDate(ivSchiff.getAuslieferung()));
        lvPodaten.setFertstdat(DatumUtilities.changeDate(ivSchiff.getFertigstellung()));
        
        // TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
        TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
        lvFinanzierung.setKey(ivSchiff.getFinanzierungsnummer());
        lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivInstitut));
        lvFinanzierung.setQuelle("TXS");           
        
        // TXSFinanzgeschaeftDaten setzen
        TXSFinanzgeschaeftDaten lvFgdaten = new TXSFinanzgeschaeftDaten();
        lvFgdaten.setAz(ivSchiff.getFinanzierungsnummer());
        lvFgdaten.setAktivpassiv("1");
        lvFgdaten.setKat("8");
        lvFgdaten.setTyp("70");
        lvFgdaten.setZusdat(DatumUtilities.changeDate(ivSchiff.getBeginnFinanzierung()));
        lvFgdaten.setNbetrag(ivSchiff.getNennbetragFinanzierung());
        lvFgdaten.setWhrg(ivSchiff.getWaehrungNennbetragFinanzierung());
        //lvFgdaten.setBemdeckregvfin(String2XML.change2XML(ivSchiff.getBemerkungDeckRegVFin()));
        
        // TXSKonditionenDaten setzen
        TXSKonditionenDaten lvKond = new TXSKonditionenDaten();
        lvKond.setKondkey(ivSchiff.getFinanzierungsnummer());
        lvKond.setFaellig(DatumUtilities.changeDate(ivSchiff.getFaelligkeitFinanzierung()));
       
        // TXSFinanzierungKredit setzen
        TXSFinanzierungKredit lvFinkred = new TXSFinanzierungKredit();
        lvFinkred.setKey(ivSchiff.getKontonummer());
        lvFinkred.setOriginator(ValueMapping.changeMandant(ivInstitut));
        lvFinkred.setQuelle("ADARLSCHF");

        // Direkt in die Datei schreiben  
        // TXSFinanzgeschaeft
        ivOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionStart());
                
        // TXSKreditSicherheit
        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionDaten());
   
         // TXSSicherheitDaten
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());
   
         // TXSSicherheitVerzeichnis
        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionDaten());

         // TXSVerzeichnisDaten
        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionEnde());

        // TXSVerzeichnisBlatt 
        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionDaten());

        // TXSVerzeichnisblattDaten
        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionEnde());
   
        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionEnde());
        
        // TXSVerzeichnisPfandobjekt
        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionDaten());
   
         // TXSPfandobjektDaten
        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionEnde());
   
        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionEnde());
   
        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionEnde());
   
        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionEnde());
     
        ivOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionEnde());
        
        // TXSFinanzierungKredit
        ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvFinkred.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvFinkred.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionEnde());
    }

    /**
     * Setzt Schiffstyp, Objektgruppe/Schiffsart, Nutzungsart und Ertragsfaehigkeit
     * @param pvPodaten
     */
    private void setzeSchiffsdaten(TXSPfandobjektDaten pvPodaten)
    {
        // Schiffstyp
        pvPodaten.setStyp(ivSchiff.getBranchennummer());
        
        // Objektgruppe - Schiffsart
        if (ivSchiff.getBranchennummer().equals("50111") || ivSchiff.getBranchennummer().equals("50121") ||
            ivSchiff.getBranchennummer().equals("50122") || ivSchiff.getBranchennummer().equals("50271") ||
            ivSchiff.getBranchennummer().equals("50272") || ivSchiff.getBranchennummer().equals("50273") ||
            ivSchiff.getBranchennummer().equals("50274") || ivSchiff.getBranchennummer().equals("50275") ||
            ivSchiff.getBranchennummer().equals("50310") || ivSchiff.getBranchennummer().equals("50320") ||
            ivSchiff.getBranchennummer().equals("50410") || ivSchiff.getBranchennummer().equals("50420") ||
            ivSchiff.getBranchennummer().equals("50430"))
        {
            pvPodaten.setOgrp("102");
        }
        
        if (ivSchiff.getBranchennummer().equals("50221") || ivSchiff.getBranchennummer().equals("50222") ||
            ivSchiff.getBranchennummer().equals("50223") || ivSchiff.getBranchennummer().equals("50224") ||
            ivSchiff.getBranchennummer().equals("50225") || ivSchiff.getBranchennummer().equals("50226") ||
            ivSchiff.getBranchennummer().equals("50227"))
        {
            pvPodaten.setOgrp("101");
        }
        
        if (ivSchiff.getBranchennummer().equals("50231") || ivSchiff.getBranchennummer().equals("50232") ||
            ivSchiff.getBranchennummer().equals("50233") || ivSchiff.getBranchennummer().equals("50234"))
        {
            pvPodaten.setOgrp("100");
        }
        
        if (ivSchiff.getBranchennummer().equals("50255") || ivSchiff.getBranchennummer().equals("50256") ||
            ivSchiff.getBranchennummer().equals("50241") || ivSchiff.getBranchennummer().equals("50242") ||
            ivSchiff.getBranchennummer().equals("50243") || ivSchiff.getBranchennummer().equals("50244") ||
            ivSchiff.getBranchennummer().equals("50245") || ivSchiff.getBranchennummer().equals("50251") ||
            ivSchiff.getBranchennummer().equals("50252") || ivSchiff.getBranchennummer().equals("50253") ||
            ivSchiff.getBranchennummer().equals("50254"))
        {
            pvPodaten.setOgrp("103");
        }
        
        // Multipurpose
        if (ivSchiff.getBranchennummer().equals("50261") || ivSchiff.getBranchennummer().equals("50262") ||
            ivSchiff.getBranchennummer().equals("50263") || ivSchiff.getBranchennummer().equals("50264") ||
            ivSchiff.getBranchennummer().equals("50265") || ivSchiff.getBranchennummer().equals("50266") ||
            ivSchiff.getBranchennummer().equals("50267") || ivSchiff.getBranchennummer().equals("50268") ||
            ivSchiff.getBranchennummer().equals("50269"))
        {
            pvPodaten.setOgrp("104");
        }
                    
        // Nutzungsart
        if (ivSchiff.getBranchennummer().equals("50310") || ivSchiff.getBranchennummer().equals("50320") ||
            ivSchiff.getBranchennummer().equals("50410") || ivSchiff.getBranchennummer().equals("50420") ||
            ivSchiff.getBranchennummer().equals("50430"))
        {
            pvPodaten.setNart("9");
        }
        else
        {
            pvPodaten.setNart("10");
        }
        
        // Ertragsfaehigkeit
        pvPodaten.setErtragsf("SCHIFF");

    }
    
    /**
     * Verarbeite Flugzeuge
     */
    private void verarbeiteFlugzeug()
    {
        // TXSFinanzgeschaeft setzen
        TXSFinanzgeschaeft lvFinanzgeschaeft = new TXSFinanzgeschaeft();   
        lvFinanzgeschaeft.setKey(ivFlugzeug.getKontonummer());
        lvFinanzgeschaeft.setOriginator(ValueMapping.changeMandant(ivInstitut));
        lvFinanzgeschaeft.setQuelle("ADARLFLUG");
                
        TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
        lvKredsh.setKey(ivFlugzeug.getObjektNr());
        lvKredsh.setOrg(ValueMapping.changeMandant(ivInstitut));
        lvKredsh.setQuelle("TXS");
        lvKredsh.setWhrg(ivFlugzeug.getWaehrungSicherheit());
        if (ivHashMapSolldeckungBetrag.get(ivFlugzeug.getKontonummer()) != null)
        {
          lvKredsh.setZuwbetrag(ivHashMapSolldeckungBetrag.get(ivFlugzeug.getKontonummer()));
          lvKredsh.setWhrg(ivHashMapSolldeckungWaehrung.get(ivFlugzeug.getKontonummer()));
        }
        
        TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
        lvShdaten.setArt("57");
        // Defaultmaessig auf 'Ja' (1)
        lvShdaten.setGepr("1");
        
        lvShdaten.setNbetrag(ivFlugzeug.getNennbetrag());
        
        BigDecimal lvHelpKonsortialanteil = (new BigDecimal(ivFlugzeug.getVerfuegungsbetrag())).divide(new BigDecimal(ivFlugzeug.getNennbetrag()), 9, RoundingMode.HALF_UP);

        //lvShdaten.setVbetrag(ivFlugzeug.getVerfuegungsbetrag());      
        BigDecimal lvHelpBeleihungswert = new BigDecimal(ivFlugzeug.getBeleihungswertAkt());
        BigDecimal lvHelpNennbetrag = new BigDecimal(ivFlugzeug.getNennbetrag());
        BigDecimal lvHelpWechselkursBeleihungswert = new BigDecimal("1.0");
        BigDecimal lvHelpWechselkursNennbetrag = new BigDecimal("1.0");
        //System.out.println("Vor: " + lvHelpBeleihungswert);
        lvHelpBeleihungswert = (lvHelpBeleihungswert.multiply(new BigDecimal("0.6"))).multiply(lvHelpKonsortialanteil);
        lvHelpNennbetrag = lvHelpNennbetrag.multiply(lvHelpKonsortialanteil);

        //System.out.println("Nach: " + lvHelpBeleihungswert);

        //if (!ivFlugzeug.getWaehrungSicherheit().equals("EUR"))
        //{
          if (ivHashMapWechselkurse.get(ivFlugzeug.getWaehrungBeleihungswertAkt()) != null)
          {
            lvHelpWechselkursBeleihungswert = new BigDecimal((ivHashMapWechselkurse.get(ivFlugzeug.getWaehrungBeleihungswertAkt())).getMittelkurs());
          }
        //}
        //if (!ivFlugzeug.getWaehrungBeleihungswertAkt().equals("EUR"))
        //{
          if (ivHashMapWechselkurse.get(ivFlugzeug.getWaehrungSicherheit()) != null)
          {
            lvHelpWechselkursNennbetrag = new BigDecimal((ivHashMapWechselkurse.get(ivFlugzeug.getWaehrungSicherheit())).getMittelkurs());
          }
        //}
        BigDecimal lvHelpBeleihungswertEUR = lvHelpBeleihungswert.divide(lvHelpWechselkursBeleihungswert, 9, RoundingMode.HALF_UP);
        BigDecimal lvHelpNennbetragEUR = lvHelpNennbetrag.divide(lvHelpWechselkursNennbetrag, 9, RoundingMode.HALF_UP);
        
        if (lvHelpBeleihungswertEUR.doubleValue() < lvHelpNennbetragEUR.doubleValue())
        {
            lvShdaten.setVbetrag(lvHelpBeleihungswert.toString());
            lvShdaten.setWhrg(ivFlugzeug.getWaehrungBeleihungswertAkt());
        }
        else
        {
            lvShdaten.setVbetrag(lvHelpNennbetrag.toString());
            lvShdaten.setWhrg(ivFlugzeug.getWaehrungSicherheit());
        }
        
        //lvShdaten.setWhrg(ivFlugzeug.getWaehrungSicherheit());
        
        TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
        lvShve.setVenr(ivFlugzeug.getRegistereintragsnr()); //ivFlugzeug.getObjektNr() + ivFlugzeug.getAbteilung3());
        lvShve.setOrg(ValueMapping.changeMandant(ivInstitut));
        lvShve.setQuelle("TXS");
        
        // ZuweisungsbetragPO
        // min {Konsortialanteil in Prozent * 60% * aktuelle Beleihungswert; Verfuegungsbetrag}
        //BigDecimal lvHelpWechselkursVerf = new BigDecimal("1.0");
        //BigDecimal lvHelpWechselkursBeleih = new BigDecimal("1.0");
        //if (ivHashMapWechselkurse.get(ivFlugzeug.getWaehrungSicherheit()) != null)
        //{
           // lvHelpWechselkursVerf = new BigDecimal((ivHashMapWechselkurse.get(ivFlugzeug.getWaehrungSicherheit())).getMittelkurs());
        //}
        //if (ivHashMapWechselkurse.get(ivFlugzeug.getWaehrungBeleihungswertAkt()) != null)
        //{
          //  lvHelpWechselkursBeleih = new BigDecimal((ivHashMapWechselkurse.get(ivFlugzeug.getWaehrungBeleihungswertAkt())).getMittelkurs());
        //}
        //System.out.println("Wechselkurs - Verfuegungsbetrag (" + ivFlugzeug.getWaehrungSicherheit() + "): " + lvHelpWechselkursVerf.toString());
        //System.out.println("Wechselkurs - Beleihungswert (" + ivFlugzeug.getWaehrungBeleihungswertAkt() + "): " + lvHelpWechselkursBeleih.toString());

        //BigDecimal lvHelpVerf = new BigDecimal(ivFlugzeug.getVerfuegungsbetrag());
        BigDecimal lvHelpBeleihAkt = new BigDecimal(ivFlugzeug.getBeleihungswertAkt());
        //lvHelpKonsortialanteil = lvHelpVerf.divide(new BigDecimal(ivFlugzeug.getNennbetrag()), 9, RoundingMode.HALF_UP);
        //System.out.println("lvHelpKonsortialanteil: " + lvHelpKonsortialanteil.toString());
        lvHelpBeleihAkt = (lvHelpBeleihAkt.multiply(new BigDecimal("0.6"))).multiply(lvHelpKonsortialanteil); // 60% Prozent * Konsortialanteil
        
        //BigDecimal lvHelpVerfEUR = lvHelpVerf.divide(lvHelpWechselkursVerf, 9, RoundingMode.HALF_UP);
        //BigDecimal lvHelpBeleihAktEUR = lvHelpBeleihAkt.divide(lvHelpWechselkursBeleih, 9, RoundingMode.HALF_UP);
        
        //System.out.println(lvHelpVerf.toString() + " --> EUR " + lvHelpVerfEUR.toString());
        //System.out.println(lvHelpBeleihAkt.toString() + " --> EUR " + lvHelpBeleihAktEUR.toString());
        
        TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
        TXSVerzeichnisVBlatt lvVevb = new TXSVerzeichnisVBlatt();
        TXSVerzeichnisblattDaten lvVbdaten = new TXSVerzeichnisblattDaten();
        if (ivFlugzeug.getRegisterland().equals("Deutschland"))
        {
            lvVedaten.setAbt("3");
            lvVedaten.setGbart("300");
            lvVedaten.setNrabt(ivFlugzeug.getAbteilung3());
        }
        else
        {
            lvVedaten.setGbart("301");
            lvVedaten.setNrabt("n.r.");
        }
        lvVedaten.setRang(ivFlugzeug.getRang());
        lvVedaten.setKat("3");

        lvVedaten.setBetrag(lvShdaten.getVbetrag());
        lvVedaten.setWhrg(lvShdaten.getWhrg());              

        //if (lvHelpVerfEUR.doubleValue() < lvHelpBeleihAktEUR.doubleValue())
        //{
        //    lvVedaten.setBetrag(lvHelpVerf.toString());
        //    lvVedaten.setWhrg(ivFlugzeug.getWaehrungSicherheit());
        //}
        //else
        //{
        //    lvVedaten.setBetrag(lvHelpBeleihAkt.toString());
        //    lvVedaten.setWhrg(ivFlugzeug.getWaehrungBeleihungswertAkt());
        //}
          
        lvVevb.setVbnr(ivFlugzeug.getRegistereintragsnr()); //+ ivFlugzeug.getAbteilung3());
        lvVevb.setOrg(ValueMapping.changeMandant(ivInstitut));
        lvVevb.setQuelle("TXS");
        
        lvVbdaten.setKat("3");
        lvVbdaten.setGbvon(ivFlugzeug.getRegisterort());
        lvVbdaten.setBlatt(ivFlugzeug.getBlatt().replace("Blatt ", ""));
        
        TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
        lvVepo.setObjnr(ivFlugzeug.getFlugzeugnummerVO()); // Erst mal nicht: + "_" + ivFlugzeug.getFinanzObjektNr());
        lvVepo.setOrg(ValueMapping.changeMandant(ivInstitut));
        lvVepo.setQuelle("TXS");
        
        TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();
        lvPodaten.setKat("3");
        // Konsortialanteil errechnen
        lvHelpKonsortialanteil = lvHelpKonsortialanteil.multiply(new BigDecimal("100")); // * 100 -> Prozent
        lvPodaten.setKonsortialanteil(lvHelpKonsortialanteil.toString());
        lvPodaten.setBlhwertinit(ivFlugzeug.getBeleihungswertInd() + " " + ivFlugzeug.getWaehrungBeleihungswertInd());
        
        // Land umschluesseln
        if (ivFlugzeug.getRegisterland().equals("USA"))
          lvPodaten.setLand("US");
        if (ivFlugzeug.getRegisterland().equals("Frankreich"))
          lvPodaten.setLand("FR");
        if (ivFlugzeug.getRegisterland().equals("Großbritannien"))
          lvPodaten.setLand("GB");
        if (ivFlugzeug.getRegisterland().equals("Deutschland"))
            lvPodaten.setLand("DE");
        if (ivFlugzeug.getRegisterland().equals("Irland"))
            lvPodaten.setLand("IE");
        if (ivFlugzeug.getRegisterland().equals("Norwegen"))
            lvPodaten.setLand("NO");
        
        lvPodaten.setBrnr(ivFlugzeug.getBranchennummer());
        lvPodaten.setWhrg(ivFlugzeug.getWaehrungBeleihungswertAkt());
        lvPodaten.setBwert(ivFlugzeug.getBeleihungswertAkt());
        lvPodaten.setBwertdat(DatumUtilities.changeDate(ivFlugzeug.getBeleihungswertVom()));
        lvPodaten.setMuster(ivFlugzeug.getFlugzeugmuster());
        lvPodaten.setVar(ivFlugzeug.getFlugzeugvariante());
        lvPodaten.setSn(ivFlugzeug.getSeriennrMSN());
        lvPodaten.setBez(ivFlugzeug.getKennzeichen());
        setzeFlugzeugdaten(lvPodaten);
        lvPodaten.setAusldat(DatumUtilities.changeDate(ivFlugzeug.getAuslieferung()));
        lvPodaten.setFertstdat(DatumUtilities.changeDate(ivFlugzeug.getFertigstellung()));
        lvPodaten.setFinobjnr(ivFlugzeug.getFinanzObjektNr());  
        
        // TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
        TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
        lvFinanzierung.setKey(ivFlugzeug.getFinanzierungsnummer());
        lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivInstitut));
        lvFinanzierung.setQuelle("TXS");           
        
        // TXSFinanzgeschaeftDaten setzen
        TXSFinanzgeschaeftDaten lvFgdaten = new TXSFinanzgeschaeftDaten();
        lvFgdaten.setAz(ivFlugzeug.getFinanzierungsnummer());
        lvFgdaten.setAktivpassiv("1");
        lvFgdaten.setKat("8");
        lvFgdaten.setTyp("71");
        lvFgdaten.setZusdat(DatumUtilities.changeDate(ivFlugzeug.getBeginnFinanzierung()));
        lvFgdaten.setNbetrag(ivFlugzeug.getNennbetragFinanzierung().replace(",", "."));
        lvFgdaten.setWhrg(ivFlugzeug.getWaehrungNennbetragFinanzierung());
        
        // TXSKonditionenDaten setzen
        TXSKonditionenDaten lvKond = new TXSKonditionenDaten();
        lvKond.setKondkey(ivFlugzeug.getFinanzierungsnummer());
        lvKond.setFaellig(DatumUtilities.changeDate(ivFlugzeug.getFaelligkeitFinanzierung()));

        // TXSFinanzierungKredit setzen
        TXSFinanzierungKredit lvFinkred = new TXSFinanzierungKredit();
        lvFinkred.setKey(ivFlugzeug.getKontonummer());
        lvFinkred.setOriginator(ValueMapping.changeMandant(ivInstitut));
        lvFinkred.setQuelle("ADARLFLUG");   
        
        // Direkt in die Datei schreiben  
        // TXSFinanzgeschaeft
        ivOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionStart());
               
        // TXSKreditSicherheit
        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionDaten());
   
         // TXSSicherheitDaten
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());
   
         // TXSSicherheitVerzeichnis
        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionDaten());

         // TXSVerzeichnisDaten
        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionEnde());

        // TXSVerzeichnisBlatt 
        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionDaten());

        // TXSVerzeichnisblattDaten
        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionEnde());
        
        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionEnde());
        
         // TXSPfandobjekt
        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionDaten());
   
         // TXSPfandobjektDaten
        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionEnde());
   
        // TXSTriebwerkDaten
        TXSTriebwerkDaten lvTwdaten = null;

        Iterator<String> lvSeriennummernIter = ivFlugzeug.getSeriennummern().iterator();
        while (lvSeriennummernIter.hasNext())
        {
          String lvSeriennummer = lvSeriennummernIter.next();
          lvTwdaten = new TXSTriebwerkDaten();
          lvTwdaten.setBstatus("1");
          lvTwdaten.setTwkey(lvSeriennummer);
          lvTwdaten.setSn(lvSeriennummer);
          lvTwdaten.setTyp(ivFlugzeug.getTriebwerktyp());
          lvTwdaten.setVariante(ivFlugzeug.getTriebwerktyp() + "_" + ivFlugzeug.getTriebwerkvariante());
          
          ivOutputDarlehenXML.printTransaktion(lvTwdaten.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(lvTwdaten.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(lvTwdaten.printTXSTransaktionEnde());
        }
        
        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionEnde());
   
        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionEnde());
   
        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionEnde());
     
        ivOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionEnde());
 
        // TXSFinanzierungKredit
        ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvFinkred.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvFinkred.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionEnde());
    }
    
    /**
     * Setzt Schiffstyp, Objektgruppe/Schiffsart, Nutzungsart und Ertragsfaehigkeit
     * @param pvPodaten
     */
    private void setzeFlugzeugdaten(TXSPfandobjektDaten pvPodaten)
    {        
        // Flugzeugtyp, Objektgruppe/Flugzeugart, Distanz
        if (ivFlugzeug.getBranchennummer().equals("5111"))
        {
            pvPodaten.setFtyp("12");
            pvPodaten.setOgrp("204");
            pvPodaten.setDist("2");
        }
        if (ivFlugzeug.getBranchennummer().equals("51121"))
        {
            pvPodaten.setFtyp("9");
            pvPodaten.setOgrp("203");
            pvPodaten.setDist("2");
        }
        if (ivFlugzeug.getBranchennummer().equals("51122"))
        {
            pvPodaten.setFtyp("10");
            pvPodaten.setOgrp("203");
            pvPodaten.setDist("2");
        }
        if (ivFlugzeug.getBranchennummer().equals("51123"))
        {
            pvPodaten.setFtyp("11");
            pvPodaten.setOgrp("203");
            pvPodaten.setDist("2");
        }
        if (ivFlugzeug.getBranchennummer().equals("51131"))
        {
            pvPodaten.setFtyp("4");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("2");
        }
        if (ivFlugzeug.getBranchennummer().equals("51132"))
        {
            pvPodaten.setFtyp("5");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("3");
        }
        if (ivFlugzeug.getBranchennummer().equals("51133"))
        {
            pvPodaten.setFtyp("6");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("2");
        }
        if (ivFlugzeug.getBranchennummer().equals("51134"))
        {
            pvPodaten.setFtyp("7");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("3");
        }
        if (ivFlugzeug.getBranchennummer().equals("51135"))
        {
            pvPodaten.setFtyp("8");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("3");
        }
        if (ivFlugzeug.getBranchennummer().equals("51141") || ivFlugzeug.getBranchennummer().equals("51142"))
        {
            pvPodaten.setFtyp("15");
            pvPodaten.setOgrp("206");
            if (ivFlugzeug.getBranchennummer().equals("51141"))
                pvPodaten.setDist("3");
            else
                pvPodaten.setDist("4");      
        }
        if (ivFlugzeug.getBranchennummer().equals("51143") || ivFlugzeug.getBranchennummer().equals("51144"))
        {
            pvPodaten.setFtyp("16");
            pvPodaten.setOgrp("206");
            if (ivFlugzeug.getBranchennummer().equals("51143"))
                pvPodaten.setDist("3");
            else
                pvPodaten.setDist("4");
        }
        if (ivFlugzeug.getBranchennummer().equals("51151"))
        {
            pvPodaten.setFtyp("13");
            pvPodaten.setOgrp("205");
            pvPodaten.setDist("4");
        }
        if (ivFlugzeug.getBranchennummer().equals("51152"))
        {
            pvPodaten.setFtyp("14");
            pvPodaten.setOgrp("205");
            pvPodaten.setDist("4");
        }
        if (ivFlugzeug.getBranchennummer().equals("5116"))
        {
            pvPodaten.setFtyp("1");
            pvPodaten.setOgrp("200");
            pvPodaten.setDist("1");
        }
        if (ivFlugzeug.getBranchennummer().equals("51211") || ivFlugzeug.getBranchennummer().equals("51212"))
        {
            pvPodaten.setFtyp("2");
            pvPodaten.setOgrp("201");
            if (ivFlugzeug.getBranchennummer().equals("51211"))
                pvPodaten.setDist("3");
            else
                pvPodaten.setDist("4");
        }
        if (ivFlugzeug.getBranchennummer().equals("51213") || ivFlugzeug.getBranchennummer().equals("51214"))
        {
            pvPodaten.setFtyp("3");
            pvPodaten.setOgrp("201");
            if (ivFlugzeug.getBranchennummer().equals("51213"))
                pvPodaten.setDist("3");
            else
                pvPodaten.setDist("4");
        }
        
        // Nutzungsart
        if (ivFlugzeug.getBranchennummer().equals("51211") || ivFlugzeug.getBranchennummer().equals("51212") ||
            ivFlugzeug.getBranchennummer().equals("51213") || ivFlugzeug.getBranchennummer().equals("51214"))
        {
            // Guetertransport
            pvPodaten.setNart("12");
        }
        else
        {
            // Personentransport
            pvPodaten.setNart("11");
        }
        
        // Betriebsstatus
        // Defaultmaessig 'In Betrieb' -> 1
        pvPodaten.setBstatus("1");
        
        // Ertragsfaehigkeit
        // unbekannt...  
    }
    
    /**
     * Einlesen der DarKa-Daten
     * @param pvDateiname
     */
    private void leseDarKaDaten(String pvDateiname)
    {
        Document lvDocument = null;
        
        SAXBuilder lvBuilder = null;
        lvBuilder = new SAXBuilder();
        lvBuilder.setExpandEntities(true);
          
        try
        {
            lvDocument = (Document)lvBuilder.build(pvDateiname);
        }
        catch(IOException io)
        {
             System.out.println(io.getMessage());
        }
        catch(JDOMException jdomex)
        {
             System.out.println(jdomex.getMessage());
        }
   
        if (lvDocument != null)
        {
          Element lvRootNode = lvDocument.getRootElement();
          String lvInstitut = lvRootNode.getAttributeValue("nr");

          System.out.println("DarKa - Solldeckung");
          System.out.println("Institut: " + lvInstitut);
        
          List<Element> lvListDarlehen = lvRootNode.getChildren("Darlehen");

          System.out.println("Anzahl gefundener Darlehen: " + lvListDarlehen.size());

          Element lvNodeDarlehen;
          for (int i = 0; i < lvListDarlehen.size(); i++)
          {
              lvNodeDarlehen = (Element)lvListDarlehen.get(i);
              ivHashMapSolldeckungBetrag.put(lvNodeDarlehen.getAttributeValue("nr"), lvNodeDarlehen.getChildText("Solldeckung"));
              ivHashMapSolldeckungWaehrung.put(lvNodeDarlehen.getAttributeValue("nr"), lvNodeDarlehen.getChildText("Satzwaehrung"));
              //ivHashMapVertragBis.put(lvNodeDarlehen.getAttributeValue("nr"), lvNodeDarlehen.getChildText("VertragBis"));
          }
        }
    }
    
    /**
     * Einlesen der Wechselkurse
     * @param pvDateiname
     */
    private void leseWechselkurse(String pvDateiname)
    {
        String lvZeile = null;
        Wechselkurse lvWechselkurse;
        LeseWechselkurse lvLeseWechselkurse = new LeseWechselkurse(pvDateiname);
        
        // Oeffnen der Dateien
        FileInputStream lvFileInputStream = null;
        File lvFileWechselkurse = new File(pvDateiname);
        try
        {
            lvFileInputStream = new FileInputStream(lvFileWechselkurse);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Wechselkurse-Datei nicht oeffnen!");
            return;
        }

        BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        boolean lvStart = true;
          
        try
        {
            while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Lesen Wechselkurse-Datei
            {
              if (lvStart)
              {
                  // 1.Zeile ueberlesen
                  lvStart = false;
              }
              else
              {
                  
                  lvWechselkurse = lvLeseWechselkurse.parseWechselkurse(lvZeile);
                  ivHashMapWechselkurse.put(lvWechselkurse.getWaehrungscode(), lvWechselkurse);
              }
            }
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
        System.out.println("Gelesene Wechselkurse: " + ivHashMapWechselkurse.size());        
    }
    
    /**
     * Konvertiert den Registerort - nur BremerLB
     * @param pvText
     */
    private String convertRegisterort(String pvText)
    {
        if (pvText.equals("DE1"))
        {
            pvText = "Bremen";
        }
        if (pvText.equals("DE2"))
        {
            pvText = "Emden";
        }
        if (pvText.equals("DE3"))
        {
            pvText = "Hamburg";
        }
        if (pvText.equals("DE4"))
        {
            pvText = "Köln";
        }
        if (pvText.equals("DE5"))
        {
            pvText = "Brake / Unterweser";
        }
        if (pvText.equals("DE6"))
        {
            pvText = "Lübeck";
        }
        if (pvText.equals("DE7"))
        {
            pvText = "Sankt Goar";
        }
        if (pvText.equals("GI1"))
        {
            pvText = "Gibraltar";
        }
        if (pvText.equals("GR1"))
        {
            pvText = "Piraeus";
        }
        if (pvText.equals("MH1"))
        {
            pvText = "Majuro";
        }
        if (pvText.equals("MT1"))
        {
            pvText = "Valetta";
        }
        if (pvText.equals("PA1"))
        {
            pvText = "Panama City";
        }
        return pvText;
    }
}
