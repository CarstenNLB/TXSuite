package nlb.txs.schnittstelle.LoanIQ.ANNADatei;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;


public class ANNADatei
{

    // Wird nur zu Testzwecken benoetigt!
    private static Logger LOGGER_ANNA = Logger.getLogger("TXS_ANNA_Logger");  

    /**
     * Liste mit ANNA-Daten
     */
    private ArrayList<ANNADaten> ivListeANNADaten;
    
    /**
     * log4j-Logger
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor
     * @param pvFilename 
     * @param pvLogger 
     */
    public ANNADatei(String pvFilename, Logger pvLogger) 
    {   
        this.ivLogger = pvLogger;
        this.ivListeANNADaten = new ArrayList<ANNADaten>();
        readANNADaten(pvFilename);
    }

    /**
     * 
     * @return
     */
    public ArrayList<ANNADaten> getListeANNADaten() 
    {
		return ivListeANNADaten;
	}

    /**
     * 
     * @param ivListeANNADaten
     */
	public void setListeANNADaten(ArrayList<ANNADaten> pvListeANNADaten) 
	{
		this.ivListeANNADaten = pvListeANNADaten;
	}

	/**
     * Liest die ANNADaten ein
     */
    private void readANNADaten(String pvDateiname)
    {
        ivLogger.info("Start - readANNADaten");
        String lvZeile = null;
        int lvZeilenNummer = 0;          
        
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileANNA = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileANNA);
        }
        catch (Exception e)
        {
            ivLogger.info("Konnte ANNA-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
              
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen ANNA-Datei
            {
            	lvZeilenNummer++;
            	if (lvZeilenNummer == 1 || lvZeilenNummer == 2) 
            	{
            		ivLogger.info(lvZeile);
            	}
            	if (lvZeilenNummer > 2)
            	{
            		ANNADaten lvANNADaten = new ANNADaten();
            		lvANNADaten.parseANNADaten(lvZeile);
            		ivListeANNADaten.add(lvANNADaten);
            	}
            }
        }
        catch (Exception e)
        {
            ivLogger.error("Fehler beim Verarbeiten einer Zeile!");
            ivLogger.error("Zeile: " + lvZeile);
            e.printStackTrace();
        }
              
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            ivLogger.error("Konnte ANNA-Datei nicht schliessen!");
        } 
        ivLogger.info("Anzahl eingelesener Zeilen: " + lvZeilenNummer);
        ivLogger.info("Anzahl eingelesener ANNA-Daten: " + ivListeANNADaten.size());
     }

    /**
     * Gibt es die Kontonummer des Quellsystems in der Liste der ANNA-Daten
     * @param pvKontonummer Kontonummer aus dem Quellsystem
     * @return true - Kontonummer existiert im Quellsystem, false - ansonsten
     */
    public boolean existsKontonummerQuellsystem(String pvKontonummer)
    {
    	for (ANNADaten lvAnnaDaten:ivListeANNADaten)
    	{
    		if (lvAnnaDaten.getKontonummerQuellsystem().equals(pvKontonummer))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
	
    /**
     * Startroutine (main) Anna
     * Wird nur zu Testzwecken benoetigt!
     * @param args
     */
    public static void main(String args[])
    { 
        if (args.length != 1)
        {
            System.out.println("Starten:");
            System.out.println("ANNADatei <Datei>");
            System.exit(1);
        }
        else
        {            
            new ANNADatei(args[args.length - 1], LOGGER_ANNA);
        }
        System.exit(0);
    }

}
