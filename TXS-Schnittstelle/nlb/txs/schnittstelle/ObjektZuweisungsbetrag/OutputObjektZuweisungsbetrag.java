/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.ObjektZuweisungsbetrag;

import java.io.File;
import java.io.FileOutputStream;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class OutputObjektZuweisungsbetrag 
{
    /**
     * 
     */
    private String ivFilename;
    
    /**
     * 
     */
    private File ivFile;
    
    /**
     * 
     */
    private FileOutputStream ivFos;
 
    /**
     * Konstruktor
     * @param pvFilename 
     */
    public OutputObjektZuweisungsbetrag(String pvFilename)
    {
        this.ivFilename = pvFilename;
    }
    
    /**
      * Oeffnen der Datei
      */
    public void open()
    {
      ivFile = new File(ivFilename);
      try
      {
        ivFos = new FileOutputStream(ivFile);
      }
      catch (Exception e)
      {
        System.out.println("Konnte Datei nicht oeffnen!");
        System.out.println("File: " + ivFile.getAbsolutePath());
      }    
    }

    /**
      * Schliessen der Datei
      */
    public void close()
    {
      try
      {
        ivFos.close();
      }
      catch (Exception e)
      {
          System.out.println("Konnte Datei nicht schliessen!");       
      }
    }
    
    /**
     * Schreibt das Objekt mit Zuweisungsbetrag in die Datei
     * @param pvOzw 
     * @param ObjektZuweisungsbetrag
     */
    public void printObjektZuweisungsbetrag(ObjektZuweisungsbetrag pvOzw)
    {
        // 
        try
        {
           ivFos.write((pvOzw.toString() + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in die Datei");
        }
    }
}
