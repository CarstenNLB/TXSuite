/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.DeutscheHypo2TXS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSBestandsverzDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson2;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitZuSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisBestandsverz;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

/**
 * @author tepperc
 *
 */
@Deprecated
public class DeutscheHypo2TXS 
{
    /**
     * XML-Ausgabedatei
     */
    private OutputDarlehenXML ivOutputDarlehenXML;
    
    /**
     * DeutscheHypo-File
     */
    private File ivFileDeutscheHypo;
    
    /**
     * Kreditdaten DeutscheHypo
     */
    private KreditDeutscheHypo ivKreditDeutscheHypo;


    /**
     * Startroutine DeutscheHypo2TXS
     * @param args
     */
    public static void main(String args[])
    { 
        if (args.length == 0)
        {
            System.out.println("Starten:");
            System.out.println("DeutscheHypo2TXS <Input-Datei>");
            System.exit(1);
        }
        else
        {
            new DeutscheHypo2TXS(args[args.length - 1]);
        }
        System.exit(0);
    }

    /**
     * Konstruktor
     * @param pvDateiname 
      */
    public DeutscheHypo2TXS(String pvDateiname)
    {
        System.out.println("Dateiname: " + pvDateiname);
        
        ivOutputDarlehenXML = new OutputDarlehenXML("C:\\CT\\Kredite_DeutscheHypo_009_TXS.xml", null);
   
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
        
            readDeutscheHypo(pvDateiname);
        
            ivOutputDarlehenXML.printTXSImportDatenEnde();
            ivOutputDarlehenXML.closeXML();
        }
        else
        {
            System.out.println("Konnte Ausgabedatei " + pvDateiname + " nicht oeffnen");
        }
    }
    
    /**
     * Liest die Excel-Datei der Abteilung Deutsche Hypo ein
     * @param pvDateiname
     */
    private void readDeutscheHypo(String pvDateiname)
    {
        String lvZeile = null;
                
        // Oeffnen der Dateien
        FileInputStream lvFileInputStream = null;
        ivFileDeutscheHypo = new File(pvDateiname);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileDeutscheHypo);
        }
        catch (Exception e)
        {
            System.out.println("Konnte DeutscheHypo-Datei nicht oeffnen!");
            return;
        }

        BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        //boolean lvStart = true;
          
        try
        {
            while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Lesen DeutscheHypo-Datei
            {
              //if (lvStart)
              //{
                  // 1.Zeile ueberlesen
              //    lvStart = false;
              //}
              //else
              //{
                  // Stammdaten eines Kredites der Deutschen Hypo
                  ivKreditDeutscheHypo = new KreditDeutscheHypo();
                  //System.out.println("Zeile: " + lvZeile); 
                  StringBuffer lvHelpZeile = new StringBuffer();
                  int lvAnzahlSemikolons = 0;
                  int lvLauf = 0;
                  while (lvAnzahlSemikolons < 27)
                  {
                      lvHelpZeile.append(lvZeile.charAt(lvLauf));
                      if (lvZeile.charAt(lvLauf) == ';')
                      {
                          lvAnzahlSemikolons++;
                      }
                      lvLauf++;
                  }
                  //System.out.println(lvHelpZeile.toString());
                                                          
                  if (!parseKreditDeutscheHypo(lvHelpZeile.toString())) // Parsen der Felder
                  {
                      System.out.println("Datenfehler DeutscheHypo!!!\n");
                  }
                  
                  // Wiederholende Grundbuch-Daten                  
                  while (lvLauf < lvZeile.length())
                  {
                    lvAnzahlSemikolons = 0;
                    lvHelpZeile = new StringBuffer();
                  
                    while (lvAnzahlSemikolons < 14)
                    {
                      lvHelpZeile.append(lvZeile.charAt(lvLauf));
                      if (lvZeile.charAt(lvLauf) == ';')
                      {
                          lvAnzahlSemikolons++;
                      }
                      lvLauf++;
                    }
                    if (lvHelpZeile.toString().startsWith(";"))
                    {
                        lvLauf = lvZeile.length();
                    }
                    else
                    {
                        //System.out.println("Grundbuch-Daten: " + lvHelpZeile); 
                        parseGrundbuchDaten(lvHelpZeile.toString());
                    }
                  }
                  verarbeiteKreditDeutscheHypo();
              //}
            }
          }
          catch (Exception e)
          {
            System.out.println("Fehler beim Verarbeiten einer Zeile!");
            System.out.println("Zeile: " + lvZeile);
            e.printStackTrace();
          }
        
        try
        {
        	lvBufferedReaderIn.close();
        }
        catch (Exception e)
        {
        	System.out.println("Konnte DeutscheHypo-Datei nicht schliessen!");
        	e.printStackTrace();
        }
    }

    /**
     * Zerlegen der Zeile eines Kredites Deutsche Hypo in einzelne Felder
     * @param pvZeile 
     * @return   
     */
     private boolean parseKreditDeutscheHypo(String pvZeile)
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
              ivKreditDeutscheHypo.setzeWert(lvLfd, stemp);

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
            ivKreditDeutscheHypo.setzeWert(lvLfd, stemp);
        }  
      
        return true; 
     }  

     /**
      * Zerlegen der Zeile eines Grundbuches in einzelne Felder
      * @param pvZeile 
      * @return   
      */
      private boolean parseGrundbuchDaten(String pvZeile)
      {       
          
         String stemp = new String(); // arbeitsbereich/zwischenspeicher feld
         int lvLfd = 0; // lfd feldnr, pruefsumme je satzart
         int lvZzStr = 0; // pointer fuer satzbereich
         
         GrundbuchDaten lvGrundbuchDaten = new GrundbuchDaten();
     
         // steuerung/iteration eingabesatz
         for (lvZzStr = 0; lvZzStr < pvZeile.length(); lvZzStr++)
         {
           // wenn semikolon erkannt
           if (pvZeile.charAt(lvZzStr) == ';')
           {
               lvGrundbuchDaten.setzeWert(lvLfd, stemp);

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
             lvGrundbuchDaten.setzeWert(lvLfd, stemp);
         }  
       
         ivKreditDeutscheHypo.addGrundbuchDaten(lvGrundbuchDaten);
         return true; 
      }  


    /**
     * Verarbeite Deutsche Hypo
     */
    private void verarbeiteKreditDeutscheHypo()
    {
        TXSFinanzgeschaeft ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        TXSKonditionenDaten ivKondDaten = new TXSKonditionenDaten();
        TXSKreditKunde ivKredKunde = new TXSKreditKunde();

        //TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
        TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
        TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
        TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
        TXSVerzeichnisVBlatt lvVevb = new TXSVerzeichnisVBlatt();
        TXSVerzeichnisblattDaten lvVbdaten = new TXSVerzeichnisblattDaten();
        TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
        TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();
        //TXSSicherheitPerson lvShperson = new TXSSicherheitPerson();
                   
        // TXSFinanzgeschaeft
        ivFinanzgeschaeft.setKey(ivKreditDeutscheHypo.getKontonummer());
        ivFinanzgeschaeft.setOriginator(ValueMapping.changeMandant("009"));
        ivFinanzgeschaeft.setQuelle("TXS");  
            
        // TXSFinanzgeschaeftDaten  
        ivFinanzgeschaeftDaten.setAbetrag("0.01");
        ivFinanzgeschaeftDaten.setRkapi("0.01");
        ivFinanzgeschaeftDaten.setNbetrag("0.01");
        ivFinanzgeschaeftDaten.setAusstat("2");
        ivFinanzgeschaeftDaten.setAktivpassiv("1");
        ivFinanzgeschaeftDaten.setAz(ivKreditDeutscheHypo.getKontonummer());
        ivFinanzgeschaeftDaten.setWhrg(ivKreditDeutscheHypo.getWaehrung());
        ivFinanzgeschaeftDaten.setVwhrg(ivKreditDeutscheHypo.getVertragswaehrung());
        //ivFinanzgeschaeftDaten.setNbetrag(ivKreditDeutscheHypo.getNennbetrag());
        ivFinanzgeschaeftDaten.setTyp("1");
        ivFinanzgeschaeftDaten.setKat("1");
        ivFinanzgeschaeftDaten.setZusdat(DatumUtilities.changeDate(ivKreditDeutscheHypo.getZusagedatum()));
        
        // TXSKondDaten
        ivKondDaten.setKondkey("1");
        ivKondDaten.setFaellig("2099-12-31");

        // TXSKredKunde
        ivKredKunde.setKdnr(ivKreditDeutscheHypo.getKontonummer().substring(2,12));
        ivKredKunde.setQuelle("KUNDE");
        ivKredKunde.setOrg(ValueMapping.changeMandant("009"));
        ivKredKunde.setRolle("1");
                             
        // Transaktionen in die Datei schreiben
        ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());
        
        ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());

        ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());

        ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionEnde());     
              
        // TXSKreditSicherheit
        TXSKreditSicherheit lvKredSh = new TXSKreditSicherheit();
        lvKredSh.setKey(ivKreditDeutscheHypo.getSicherheitennummer());
        lvKredSh.setQuelle("TXS");
        lvKredSh.setOrg(ValueMapping.changeMandant("009"));
        lvKredSh.setHauptsh("0");
        lvKredSh.setZuwbetrag("0.01");
        lvKredSh.setWhrg(ivKreditDeutscheHypo.getSicherheitWaehrung());
            
        // TXSSicherheitDaten
        TXSSicherheitDaten lvShdatenAbtr = new TXSSicherheitDaten();
        lvShdatenAbtr.setArt("55");
        lvShdatenAbtr.setGepr("1");
        lvShdatenAbtr.setNbetrag(ivKreditDeutscheHypo.getNennbetrag());
        lvShdatenAbtr.setVbetrag(ivKreditDeutscheHypo.getVerfuegungsbetrag());
        lvShdatenAbtr.setWhrg(ivKreditDeutscheHypo.getSicherheitWaehrung());
        lvShdatenAbtr.setOrigsichant("100");
        lvShdatenAbtr.setRgrunddat(DatumUtilities.changeDate(ivKreditDeutscheHypo.getDatumRechtlicherGrund()));
        lvShdatenAbtr.setReginfo(String2XML.change2XML(ivKreditDeutscheHypo.getBemerkungen().replace("#", ";")));
        lvShdatenAbtr.setRgrund(String2XML.change2XML(ivKreditDeutscheHypo.getRechtsgrundlage()));
        if (lvShdatenAbtr.getRgrund().length() > 255)
        {
            System.out.println("Rechtsgrundlage gekuerzt: " + lvShdatenAbtr.getRgrund());
            lvShdatenAbtr.setRgrund(lvShdatenAbtr.getRgrund().substring(0, 255));
        }
        
        // TXSSicherheitPerson2
        TXSSicherheitPerson2 lvShperson2 = new TXSSicherheitPerson2();
        lvShperson2.setKdnr("0000001115");
        lvShperson2.setOrg(ValueMapping.changeMandant("009"));
        lvShperson2.setQuelle("KUNDE");
        lvShperson2.setRolle("2");

        ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvShperson2.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvShperson2.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvShperson2.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionEnde());

        ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());

        // Originalsicherheit
        TXSSicherheit lvSh = new TXSSicherheit();
        lvSh.setKey(ivKreditDeutscheHypo.getOriginalsicherheit());
        lvSh.setOrg(ValueMapping.changeMandant("009"));
        lvSh.setQuelle("TXS");
        
        // TXSSicherheitDaten
        lvShdaten = new TXSSicherheitDaten();
        if (ivKreditDeutscheHypo.getSicherheitenart().equals("Buchgrundschuld"))
        {
            lvShdaten.setArt("12");
        }
        if (ivKreditDeutscheHypo.getSicherheitenart().equals("Gesamtgrundschuld"))
        {
            lvShdaten.setArt("16");
        }
        lvShdaten.setNbetrag(ivKreditDeutscheHypo.getPfandrechtNennbetrag());
        lvShdaten.setVbetrag(ivKreditDeutscheHypo.getPfandrechtVerfuegungsbetrag());
        lvShdaten.setWhrg(ivKreditDeutscheHypo.getSicherheitWaehrung());
        lvShdaten.setOrigsichant("100");
        lvShdaten.setGepr("1");
        lvShdaten.setRgrund(String2XML.change2XML(ivKreditDeutscheHypo.getPfandrechtRechtlicherGrund()));
        if (lvShdaten.getRgrund().length() > 255)
        {
            System.out.println("Rechtsgrundlage gekuerzt: " + lvShdaten.getRgrund());
            lvShdaten.setRgrund(lvShdaten.getRgrund().substring(0, 255));
        }

        //System.out.println("Rechtlicher Grund von:" + ivKreditDeutscheHypo.getRechtlicherGrundVom());
        lvShdaten.setRgrunddat(DatumUtilities.changeDate(ivKreditDeutscheHypo.getRechtlicherGrundVom()));
        lvShdaten.setReginfo(String2XML.change2XML(ivKreditDeutscheHypo.getBemerkungenOriginalsicherheiten().replace("#", ";")));
        
        // TXSKreditSicherheit ausgeben
        ivOutputDarlehenXML.printTransaktion(lvSh.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvSh.printTXSTransaktionDaten());                     
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());

        int lvLfdNr = 0;
        GrundbuchDaten lvGrundbuchDaten;
        Iterator<GrundbuchDaten> lvIterGrundbuchDaten = ivKreditDeutscheHypo.getListeGrundbuchDaten().iterator();
        while (lvIterGrundbuchDaten.hasNext())
        {
            lvGrundbuchDaten = lvIterGrundbuchDaten.next();
            lvLfdNr++;

            // TXSSicherheitVerzeichnis         
            lvShve = new TXSSicherheitVerzeichnis();
            lvShve.setVenr(ivKreditDeutscheHypo.getGrundbuchnummer() + "_" + lvGrundbuchDaten.getBlatt() + "_" + lvLfdNr);
            if (lvShve.getVenr().length() > 65)
            {
                lvShve.setVenr(lvShve.getVenr().substring(0, 65));
            }
            lvShve.setOrg(ValueMapping.changeMandant("009"));
            lvShve.setQuelle("TXS");

            ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionStart());
            ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionDaten());
         
            // TXSVerzeichnisDaten
            lvVedaten = new TXSVerzeichnisDaten();
            lvVedaten.setArt("1");
            lvVedaten.setAbt("3");
            
            if (lvGrundbuchDaten.getGrundbuchart().equals("Erbbaugrundbuch"))
            {
                lvVedaten.setGbart("4");
            }
            if (lvGrundbuchDaten.getGrundbuchart().equals("Grundbuch"))
            {
                lvVedaten.setGbart("1");
            }
            if (lvGrundbuchDaten.getGrundbuchart().equals("Whg. u. TeileigGB"))
            {
                lvVedaten.setGbart("9");
            }
            if (lvGrundbuchDaten.getGrundbuchart().equals("Whg.-Grundbuch"))
            {
                lvVedaten.setGbart("2");
            }
            lvVedaten.setKat("1");
            lvVedaten.setNrabt(String2XML.change2XML(lvGrundbuchDaten.getAbteilungsnummer()));
            lvVedaten.setBetrag(ivKreditDeutscheHypo.getPfandrechtVerfuegungsbetrag());
            //System.out.println("PfandrechtVerfuegungsbetrag: " + ivKreditDeutscheHypo.getPfandrechtVerfuegungsbetrag());
            lvVedaten.setWhrg(ivKreditDeutscheHypo.getSicherheitWaehrung());
        
            ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionStart());
            ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionDaten());
            ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionEnde());
        
            // TXSVerzeichnisVBlatt
            lvVevb = new TXSVerzeichnisVBlatt();
            lvVevb.setVbnr(ivKreditDeutscheHypo.getGrundbuchnummer() + "_" + lvGrundbuchDaten.getBlatt() + "_" + lvLfdNr);
            if (lvVevb.getVbnr().length() > 40)
            {
                lvVevb.setVbnr(lvVevb.getVbnr().substring(0, 40));
            }
            lvVevb.setOrg(ValueMapping.changeMandant("009"));
            lvVevb.setQuelle("TXS");
            ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionStart());
            ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionDaten());
        
            // TXSVerzeichnisblattDaten
            lvVbdaten = new TXSVerzeichnisblattDaten();
            lvVbdaten.setBand(lvGrundbuchDaten.getBand());
            lvVbdaten.setBlatt(lvGrundbuchDaten.getBlatt());
            if (lvVbdaten.getBlatt().length() > 50)
            {
                System.out.println("Blatt gekuerzt: " + lvVbdaten.getBlatt());
                lvVbdaten.setBlatt(lvVbdaten.getBlatt().substring(0, 50));
            }
            lvVbdaten.setGbvon(lvGrundbuchDaten.getGrundbuchVon() + "; Amtsgericht: " + lvGrundbuchDaten.getAmtsgericht());
            lvVbdaten.setGericht(lvGrundbuchDaten.getAmtsgericht());
            lvVbdaten.setKat("1");
            ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionStart());
            ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionDaten());
            ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionEnde());
        
            ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionEnde());
        
            // TXSVerzeichnisBestandsverz                 
            TXSVerzeichnisBestandsverz lvVebv = new TXSVerzeichnisBestandsverz();
            lvVebv.setBvnr(ivKreditDeutscheHypo.getGrundbuchnummer() + "_" + lvGrundbuchDaten.getBlatt() + "_" + lvLfdNr);
            if (lvVebv.getBvnr().length() > 40)
            {
                lvVebv.setBvnr(lvVebv.getBvnr().substring(0, 40));
            }

            lvVebv.setOrg(ValueMapping.changeMandant("009"));
            lvVebv.setQuelle("TXS"); 
            ivOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionStart());
            ivOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionDaten());
        
            // TXSBestandsverzDaten
            TXSBestandsverzDaten lvBvdaten = new TXSBestandsverzDaten();
            lvBvdaten.setFlur(lvGrundbuchDaten.getFlur());
            lvBvdaten.setFlurst(lvGrundbuchDaten.getFlurstueck());
            lvBvdaten.setGem(lvGrundbuchDaten.getGemarkung());
            lvBvdaten.setLfdnr(String2XML.change2XML(lvGrundbuchDaten.getAbteilungsnummer()));
           
            ivOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionStart());
            ivOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionDaten());
            ivOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionEnde());
            
            ivOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionEnde());

            // TXSVerzeichnisPfandobjekt
            lvVepo = new TXSVerzeichnisPfandobjekt();
            lvVepo.setObjnr(ivKreditDeutscheHypo.getGrundbuchnummer());
            lvVepo.setOrg(ValueMapping.changeMandant("009"));
            lvVepo.setQuelle("TXS");   
            ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionStart());
            ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionDaten());
            // TXSPfandobjektDaten
            lvPodaten = new TXSPfandobjektDaten();
               
            //lvPodaten.setKat("1");
            //alte Version: podaten.setLand(ValueMapping.changeLand(sStaat));
            // CT - 09.07.2012
            //lvPodaten.setLand(lvImmobilie.getLaenderschluessel());
            
            //lvPodaten.setSwert(lvImmobilie.getSachwert());
            //lvPodaten.setEwert(lvImmobilie.getErtragswert());
        
            //lvPodaten.setBwert(lvImmobilie.getBeleihungswert());
             
            lvPodaten.setOrt(lvGrundbuchDaten.getOrt());
            lvPodaten.setPlz(lvGrundbuchDaten.getPostleitzahl());
       
            lvPodaten.setStr(lvGrundbuchDaten.getStrasse());
            // Hausnummer in ein eigenes Feld - CT 25.11.2011
            lvPodaten.setHausnr(lvGrundbuchDaten.getHausnummer());
            lvPodaten.setZusatz(lvGrundbuchDaten.getAdresszusatz());
            //lvPodaten.setWhrg(lvImmobili.getBeleihungswertWaehrung());
            if (!lvGrundbuchDaten.getStrasse().isEmpty())
            {
              lvPodaten.setLand("DE");
            }
            
            ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionStart());
            ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionDaten());
            ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionEnde());
            
            ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionEnde());
            
            ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionEnde());
        }   
        TXSSicherheitZuSicherheit lvZugsh = new TXSSicherheitZuSicherheit();
        lvZugsh.setKey(ivKreditDeutscheHypo.getSicherheitennummer());
        lvZugsh.setQuelle("TXS");
        lvZugsh.setOrg(ValueMapping.changeMandant("009"));
        lvZugsh.setArt("1");
        lvZugsh.setRang("1");
    
        ivOutputDarlehenXML.printTransaktion(lvZugsh.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvZugsh.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvZugsh.printTXSTransaktionEnde());            
    
        ivOutputDarlehenXML.printTransaktion(lvSh.printTXSTransaktionEnde());
    }
}
