package nlb.txs.schnittstelle.Utilities;

import java.io.File;
import java.io.FileOutputStream;

public class LoggingCSV
{
	// Separator-Zeichen der CSV-Zeile
    private static final String csvSeparator = ";";
	
	/**
	 * Enthaelt den (aktuellen) Stand der CSV-Zeile
	 */
	private StringBuilder csvLine;
	
	/**
	 * Konstruktor zur Initialisierung einer leeren CSV-Zeile
	 */
	public LoggingCSV()
	{
		csvLine = new StringBuilder();
	}
	
	/**
	 * Fuegt den String zur CSV-Zeile hinzu
	 * @param pvText
	 */
	public void addString2CSV(String pvText)
	{
		csvLine.append(pvText + csvSeparator);
	}
	
	/** 
	 * Liefert die CSV-Zeile als String
	 * @return
	 */
	public String getCSV()
	{
		return csvLine.toString();
	}
	
	/**
	 * Schreibt die CSV-Zeile in eine Datei
	 * @param pvFilename
	 */
	public void writeCSV2File(String pvFilename)
	{
    	FileOutputStream lvCsvOS = null;

		
		// Oeffnet das File zu schreiben
	    File lvCsvFile = new File(pvFilename);
	    try
	    {
	    	lvCsvOS = new FileOutputStream(lvCsvFile);
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Konnte Logging CSV-Datei nicht oeffnen!");
	    }    
	    
	    try
	    {
	    	lvCsvOS.write((this.getCSV() + StringKonverter.lineSeparator).getBytes());
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Fehler bei Ausgabe in die Logging CSV-Datei");
	    }
	    
	    try
	    {
	        lvCsvOS.close();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Konnte Logging CSV-Datei nicht schliessen!");       
	    }



	}
		
}
