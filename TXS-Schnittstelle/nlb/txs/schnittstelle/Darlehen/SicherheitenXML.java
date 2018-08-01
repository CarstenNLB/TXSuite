/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.File;
import java.io.FileOutputStream;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class SicherheitenXML 
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
    public SicherheitenXML(String pvFilename)
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
        System.out.println("Konnte die Sicherheiten XML-Datei nicht oeffnen!");
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
          System.out.println("Konnte die Sicherheiten XML-Datei nicht schliessen!");       
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
          System.out.println("Start: Fehler bei Ausgabe in die Sicherheiten XML-Datei");
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
          System.out.println("Start: Fehler bei Ausgabe in die Sicherheiten XML-Datei");
        }       
    }

    
    /**
     * Schreibt die Sicherheit in die XML-Datei
     * @param pvSicherheit
     */
    public void printSicherheit(Sicherheit pvSicherheit)
    {
        // Header der XML-Datei schreiben
        try
        {
           ivXmlOS.write((new String("  <Sicherheit nr=\"" + pvSicherheit.getObjektnummer() + "\">" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Darlehenssystem>" + pvSicherheit.getHerkunftDarlehen() + "</Darlehenssystem>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Sicherheitensystem>" + pvSicherheit.getHerkunftSicherheit() + "</Sicherheitensystem>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Datenherkunft>" + pvSicherheit.getHerkunftDaten() + "</Datenherkunft>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Kundennummer>" + pvSicherheit.getKundennummer() + "</Kundennummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Kontonummer>" + pvSicherheit.getKontonummer() + "</Kontonummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Sicherheitenschluessel>" + pvSicherheit.getSicherheitenschluessel() + "</Sicherheitenschluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Infotyp>" + pvSicherheit.getInfotyp() + "</Infotyp>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Buchungsschluessel>" + pvSicherheit.getBuchungsschluessel() + "</Buchungsschluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Zuweisungsbetrag>" + pvSicherheit.getZuweisungsbetrag() + "</Zuweisungsbetrag>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Grundschuld>" + pvSicherheit.getGrundschuld() + "</Grundschuld>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Nominalwert>" + pvSicherheit.getNominalwert() + "</Nominalwert>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    <Verfuegungsbetrag>" + pvSicherheit.getVerfuegungsbetrag() + "</Verfuegungsbetrag>" + StringKonverter.lineSeparator)).getBytes());
           //xmlOS.write((new String("      <FremdeRechte>" + sicherheit.getFremdeRechte() + "</FremdeRechte>\n")).getBytes());
           //xmlOS.write((new String("      <EigeneRechte>" + sicherheit.getEigeneRechte() + "</EigeneRechte>\n")).getBytes());
           ivXmlOS.write((new String("  </Sicherheit>" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in die Sicherheiten XML-Datei");
        }
    }


}
