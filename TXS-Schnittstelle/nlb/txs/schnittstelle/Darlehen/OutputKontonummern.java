/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author tepperc
 *
 */
public class OutputKontonummern 
{
    /**
     * 
     */
    private String ivFilename;
    
    /**
     * 
     */
    private File ivKtoFile;
    
    /**
     * 
     */
    private FileOutputStream ivKtoOS;
 
    /**
     * Konstruktor
     * @param pvFilename 
     */
    public OutputKontonummern(String pvFilename)
    {
        this.ivFilename = pvFilename;
    }
    
    /**
      * Oeffnen der Datei
      */
    public void open()
    {
      ivKtoFile = new File(ivFilename);
      try
      {
        ivKtoOS = new FileOutputStream(ivKtoFile);
      }
      catch (Exception e)
      {
        System.out.println("Konnte Kontonummern Datei nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der Datei
      */
    public void close()
    {
      try
      {
        ivKtoOS.close();
      }
      catch (Exception e)
      {
          System.out.println("Konnte Kontonummern Datei nicht schliessen!");       
      }
    }
    
    /**
     * Schreibt die Kontonummer in die Datei
     * @param pvKontonummer 
     */
    public void printKontonummer(String pvKontonummer)
    {
        // Header der XML-Datei schreiben
        try
        {
           ivKtoOS.write((new String(pvKontonummer + "\n")).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe der Kontonummer in die Datei");
        }
    }

}
