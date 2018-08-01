/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.ObjektZuweisungsbetrag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author tepperc
 *
 */
public class InputObjektZuweisungsbetrag 
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
    private FileInputStream ivFis;
 
    /**
     * Konstruktor
     * @param pvFilename 
     */
    public InputObjektZuweisungsbetrag(String pvFilename)
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
        ivFis = new FileInputStream(ivFile);
      }
      catch (Exception e)
      {
        System.out.println("Konnte Datei nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der Datei
      */
    public void close()
    {
      try
      {
        ivFis.close();
      }
      catch (Exception e)
      {
          System.out.println("Konnte Datei nicht schliessen!");       
      }
    }
    
    /**
     * Liest die ObjektZuweisungsbetrag ein
     * @return 
     */
    public ObjektZuweisungsbetragListe readObjektZuweisungsbetrag()
    {
        String lvZeile;
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(ivFis));
        ObjektZuweisungsbetragListe lvOzwListe = new ObjektZuweisungsbetragListe();  
        
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen der Datei
            {
                //System.out.println(s);
                //System.out.println(s.substring(0, s.indexOf(";")));
                //System.out.println(s.substring(s.indexOf(";") + 1));
                lvOzwListe.addObjektZuweisungsbetrag(lvZeile.substring(0, lvZeile.indexOf(";")), lvZeile.substring(lvZeile.indexOf(";") + 1));
            }
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
        
        return lvOzwListe;
    }
    
}
