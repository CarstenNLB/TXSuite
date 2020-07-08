package nlb.txs.schnittstelle.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ObjekteListe extends HashMap<String, String>
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
	 * log4j-Logger
	 */
	private Logger ivLogger;
	
	/**
	 * Konstruktor
	 * @param pvDateiname Name der Datei
	 * @param pvLogger log4j-logger
	 */
	public ObjekteListe(String pvDateiname, Logger pvLogger)
	{
		ivDateiname = pvDateiname;
		ivLogger = pvLogger;

		ivLogger.info("Dateiname: " + ivDateiname);
	}
	
    /**
     * Liest eine Liste von Objekten (String-Objekten) aus einer Datei
     * @param pvValueFilterListe In der Liste von Objekten landen nur Werte, die in dieser Liste enthalten sind.
     */
    public void leseObjekteListe(List<String> pvValueFilterListe)
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
            ivLogger.error("Konnte die Datei '" + ivDateiname + "' nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
             
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Einlesen einer Zeile 
            {
            	if (lvZeile.contains(";"))
            	{
            		String lvKey = lvZeile.substring(0, lvZeile.indexOf(";"));
            		String lvValue = lvZeile.substring(lvZeile.indexOf(";") + 1);
            		if (pvValueFilterListe == null)
            		{
            			this.put(lvKey, lvValue);
            			ivLogger.info("Key: " + lvKey + " - Value: " + lvValue);
            		}
            		else
            		{
            			if (pvValueFilterListe.contains(lvValue))
            			{
            				this.put(lvKey, lvValue);
            				ivLogger.info("Key: " + lvKey + " - Value: " + lvValue);
            			}
            		}
            	}
            	else
            	{
    				    this.put(lvZeile, new String());
    				    ivLogger.info("Key: " + lvZeile);
            	}
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
            ivLogger.error("Konnte die Datei '" + ivDateiname + "' nicht schliessen!");
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
            ivLogger.error("Konnte die Datei '" + ivDateiname + "' nicht oeffnen!");
            return;
        }
    
        for (Map.Entry<String, String> lvMap: this.entrySet())
        {
       		try
       		{
       			if (lvMap.getValue().isEmpty())
       			{
       				lvFos.write((lvMap.getKey() + StringKonverter.lineSeparator).getBytes());       				
       			}
       			else
       			{
       				lvFos.write((lvMap.getKey() + ";" + lvMap.getValue() + StringKonverter.lineSeparator).getBytes());
       			}
         		lvAnzahlObjekte++;
        	}
       		catch (Exception e)
       		{
       			ivLogger.error("Fehler beim Schreiben von: " + lvMap.getKey() + " - " + lvMap.getValue());
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
            ivLogger.error("Konnte die Datei '" + ivDateiname + "' nicht schliessen!");
        }     
     }
}
