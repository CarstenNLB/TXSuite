package nlb.txs.schnittstelle.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

import org.apache.log4j.Logger;

public class ObjekteListe extends HashSet<String>
{
	/**
	 * Default-ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Dateiname der Liste
	 */
	private String ivDateiname;
	
	/**
	 * Logger
	 */
	private Logger ivLogger;
	
	/**
	 * Konstruktor
	 * @param pvDateiname
	 * @param pvLogger
	 */
	public ObjekteListe(String pvDateiname, Logger pvLogger)
	{
		ivDateiname = pvDateiname;
		ivLogger = pvLogger;
	}
	
    /**
     * Liest eine Liste von Objekten (String-Objekten) aus einer Datei
     */
    public void leseObjekteListe()
    {
        ivLogger.info("Start - readListeObjekte");
        String lvZeile = null;
                      
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File lvFileListeObjekte = new File(ivDateiname);
        try
        {
            lvFis = new FileInputStream(lvFileListeObjekte);
        }
        catch (Exception e)
        {
            ivLogger.error("Konnte die Datei " + ivDateiname + " nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
             
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Einlesen einer Zeile 
            {
                ivLogger.info("Verarbeite: " + lvZeile);
                this.add(lvZeile);
            }
        }
        catch (Exception e)
        {
            ivLogger.error("Fehler beim Verarbeiten eines Objektes: " + lvZeile);
            e.printStackTrace();
        }
        
        ivLogger.info("Anzahl eingelesener Objekte: " + this.size());
        
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            ivLogger.error("Konnte die Datei " + ivDateiname + " nicht schliessen!");
        }     
     }
    
    /**
     * Schreibt eine Liste von Objekten (String-Objekten) in eine Datei
     */
    public void schreibeObjekteListe()
    {
        ivLogger.info("Start - schreibeListeObjekte");
        int lvAnzahlObjekte = 0;
                      
        // Oeffnen der Dateien
        FileOutputStream lvFos = null;
        File lvFileListeObjekte = new File(ivDateiname);
        try
        {
            lvFos = new FileOutputStream(lvFileListeObjekte);
        }
        catch (Exception e)
        {
            ivLogger.error("Konnte die Datei " + ivDateiname + " nicht oeffnen!");
            return;
        }
    
       	for (String lvObjekt:this)
    	{
       		try
       		{
         		lvFos.write((lvObjekt + StringKonverter.lineSeparator).getBytes());
         		lvAnzahlObjekte++;
        	}
       		catch (Exception e)
       		{
       			ivLogger.error("Fehler beim Schreiben eines Objektes: " + lvObjekt);
       			e.printStackTrace();
       		}
    	}
       	
        ivLogger.info("Anzahl geschriebener Objekte: " + lvAnzahlObjekte);
        
        try
        {
            lvFos.close();
        }
        catch (Exception e)
        {
            ivLogger.error("Konnte die Datei " + ivDateiname + " nicht schliessen!");
        }     
     }
}
