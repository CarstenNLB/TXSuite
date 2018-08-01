/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.OutputXML;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author tepperc
 *
 */
public class OutputDarlehenXML 
{
    private String ivFilename;
    
    private File ivXmlFile;
    private FileOutputStream ivXmlOS;
    
    /**
     * Konstruktor
     * @param pvFilename 
     * @param mandant 
     */
    public OutputDarlehenXML(String pvFilename) //, String mandant)
    {
        this.ivFilename = pvFilename;
        //this.mandant = mandant;
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
        System.out.println("Konnte XML-Datei nicht oeffnen!");
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
          System.out.println("Konnte XML-Datei nicht schliessen!");       
      }
    }

    /**
     * Ausgabe des XML-Kopfs in die XML-Datei
     */
    public void printXMLStart()
    {   
      // Start der XML-Datei schreiben
      try
      {
        ivXmlOS.write((new String("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n")).getBytes());
      }
      catch (Exception e)
      {
        System.out.println("Start: Fehler bei Ausgabe in XML-Datei");
      }
    }
    
    /**
     * Schreibt den TXSHeader in die XML-Datei
     * @param pvValdate 
     * @return 
     */
    public void printTXSHeader(String pvValdate) 
    {
        try
        {
          ivXmlOS.write((new String("  <txsi:header valdate=\"" + pvValdate + "\"/>\n")).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in XML-Datei");
        }
    }

    /**
     * Schreibt den Start der TXSImportDaten in die XML-Datei
     * @return 
     */
    public void printTXSImportDatenStart()
    {
        try
        {
          ivXmlOS.write((new String("<txsi:importdaten xmlns:txsi=\"http://agens.com/txsimport.xsd\">\n")).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in XML-Datei");
        }
    }

    /**
     * Schreibt das Ende der TXSImportDaten in die XML-Datei
     * @return 
     */
    public void printTXSImportDatenEnde()
    {
        try
        {
          ivXmlOS.write((new String("</txsi:importdaten>")).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in XML-Datei");
        }
    }
    
    /**
     * Schreibt eine Transaktion in die XML-Datei
     * @param pvText 
     */
    public void printTransaktion(String pvText)
    {
        // Transaktion in die XML-Datei schreiben
        try
        {
           ivXmlOS.write(pvText.getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe einer Transaktion in die XML-Datei");
        }
    }
    
    /**
     * Schreibt eine Transaktion in die XML-Datei
     * @param pvText 
     */
    public void printTransaktion(StringBuffer pvText)
    {
        // Transaktion in die XML-Datei schreiben
        try
        {
           ivXmlOS.write(pvText.toString().getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe einer Transaktion in die XML-Datei");
        }        
    }

}
