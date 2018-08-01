/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author tepperc
 *
 */
public class OutputRueckmeldung 
{
    private String ivFilename;
    
    private File ivFile;
    private FileOutputStream ivFos;
    
    /**
     * Konstruktor
     * @param pvFilename  
     */
    public OutputRueckmeldung(String pvFilename)
    {
        this.ivFilename = pvFilename;
    }
    
    /**
      * Oeffnen der Rueckmelde-Datei fuer SAPCMS
      */
    public void open()
    {
      //System.out.println(filename);
      ivFile = new File(ivFilename);
      try
      {
        ivFos = new FileOutputStream(ivFile);
      }
      catch (Exception e)
      {
        System.out.println("Konnte Rueckmelde-Datei nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der XML-Datei
      */
    public void close()
    {
      try
      {
        ivFos.close();
      }
      catch (Exception e)
      {
          System.out.println("Konnte Rueckmelde-Datei nicht schliessen!");       
      }
    }
    
    /**
     * Schreibt eine Rueckmeldezeile raus
     * @param pvZeile Rueckmeldezeile
     */
    public void printRueckmeldezeile(String pvZeile)
    {
        //System.out.println(zeile);
        try
        {
           ivFos.write(pvZeile.getBytes());
        }
        catch (Exception e)
        {
          //e.printStackTrace();
          System.out.println("Start: Fehler bei Ausgabe der Rueckmeldezeile in die Datei");
        }
    }
}
