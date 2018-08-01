/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.File;
import java.io.FileOutputStream;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class SicherungsobjekteXML 
{
    /**
     * 
     */
    private String ivFilename;
    
    /**
     * 
     */
    private File ivXmlFile;
    
    /**
     * 
     */
    private FileOutputStream ivXmlOS;
 
    /**
     * Konstruktor
     * @param pvFilename 
     * @param mandant 
     */
    public SicherungsobjekteXML(String pvFilename)
    {
        this.ivFilename = pvFilename;
    }
    
    /**
      * Oeffnen der XML-Datei
      */
    public void openXML()
    {
      ivXmlFile = new File(ivFilename);
      try
      {
        ivXmlOS = new FileOutputStream(ivXmlFile);
      }
      catch (Exception e)
      {
        System.out.println("Konnte die Sicherungsobjekte XML-Datei nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der XML-Datei
      */
    public void closeXML()
    {
      try
      {
        ivXmlOS.close();
      }
      catch (Exception e)
      {
          System.out.println("Konnte die Sicherungsobjekte XML-Datei nicht schliessen!");       
      }
    }
     
    /**
     * @param pvVorlaufsatz 
     * 
     */
    public void printXMLStart(DWHVOR pvVorlaufsatz)
    {
        // RequestStart der XML-Datei schreiben
        try
        {
            ivXmlOS.write((new String("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>" + StringKonverter.lineSeparator)).getBytes());
            ivXmlOS.write((new String("<Institut nr=\"" + pvVorlaufsatz.getsDwvinst()+ "\">" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in die Sicherungsobjekte XML-Datei");
        }
       
    }

    /**
     * 
     */
    public void printXMLEnde()
    {
        // Header der XML-Datei schreiben
        try
        {
           ivXmlOS.write((new String("</Institut>" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in die Sicherungsobjekte XML-Datei");
        }       
    }

    
    /**
     * Schreibt das Sicherungsobjekt in die XML-Datei
     * @param pvSicherungsObj 
     */
    public void printSicherungsobjekte(Sicherungsobjekt pvSicherungsObj)
    {
        // Header der XML-Datei schreiben
        try
        {
           ivXmlOS.write((new String("  <Sicherungsobjekt nr=\"" + pvSicherungsObj.getObjektnummer() + "\">" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Darlehenssystem>" + pvSicherungsObj.getHerkunftDarlehen() + "</Darlehenssystem>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Sicherheitensystem>" + pvSicherungsObj.getHerkunftSicherheit() + "</Sicherheitensystem>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Datenherkunft>" + pvSicherungsObj.getHerkunftDaten() + "</Datenherkunft>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Objektart>" + pvSicherungsObj.getObjektart() + "</Objektart>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Postleitzahl>" + pvSicherungsObj.getPostleitzahl() + "</Postleitzahl>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Ort>" + pvSicherungsObj.getOrt() + "</Ort>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Strasse>" + pvSicherungsObj.getStrasse() + "</Strasse>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Beleihungsgebiet>" + pvSicherungsObj.getBeleihungsgebiet() + "</Beleihungsgebiet>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Kundennummer>" + pvSicherungsObj.getKundennummer() + "</Kundennummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Kontonummer>" + pvSicherungsObj.getKontonummer() + "</Kontonummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Grundbuch>" + pvSicherungsObj.getGrundbuch() + "</Grundbuch>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Band>" + pvSicherungsObj.getBand() + "</Band>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Blatt>" + pvSicherungsObj.getBlatt() + "</Blatt>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <LaufendeNr>" + pvSicherungsObj.getLaufendeNummer() + "</LaufendeNr>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Verwendungszweck>" + pvSicherungsObj.getVerwendungszweck() + "</Verwendungszweck>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Zusatzsicherheit>" + pvSicherungsObj.getZusatzsicherheit() + "</Zusatzsicherheit>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <WKZ-Beleihungswert>" + pvSicherungsObj.getBeleihungswertWKZ() + "</WKZ-Beleihungswert>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Baujahr>" + pvSicherungsObj.getBaujahr() + "</Baujahr>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Beleihungswert>" + pvSicherungsObj.getBeleihungswert() + "</Beleihungswert>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Sachwert>" + pvSicherungsObj.getSachwert() + "</Sachwert>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Ertragswert>" + pvSicherungsObj.getErtragswert() + "</Ertragswert>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Grundschuld>" + pvSicherungsObj.getGrundschuld() + "</Grundschuld>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("  </Sicherungsobjekt>" + StringKonverter.lineSeparator)).getBytes());  
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in die Sicherungsobjekte XML-Datei");
        }
    }
}
