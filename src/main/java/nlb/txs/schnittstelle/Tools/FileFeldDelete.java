package nlb.txs.schnittstelle.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

public class FileFeldDelete 
{

	/**
	 * Trennzeichen
	 */
	private final char ivTrennzeichen = '\t';
	
	/**
     * Startroutine FileFeldDelete
     * @param argv 
     */
    public static void main(String argv[])
    { 
    	new FileFeldDelete(argv[argv.length - 4], argv[argv.length - 3], StringKonverter.convertString2Int(argv[argv.length - 2]), argv[argv.length - 1]);
        System.exit(0);
    }
    
    /**
     * Konstruktor
     * @param pvSource Dateiname der Eingabedatei
     * @param pvTarget Dateiname der Ausgabedatei
     * @param pvPosition Position des zu loeschenden Feldes
     * @param pvStartText Beginnt die Zeile mit diesem Text, dann wird das Feld an der entsprechenden Position in dieser Zeile geloescht.
     */
    public FileFeldDelete(String pvSource, String pvTarget, int pvPosition, String pvStartText)
    {
       	System.out.println("Eingabedatei: " + pvSource);
    	System.out.println("Ausgabedatei: " + pvTarget);
    	System.out.println("Position: " + pvPosition);
    	System.out.println("StartText: " + pvStartText);

    	String lvZeile = new String();
    	
        // FileOutputStream fuer Ausgabedatei
        FileOutputStream ivFosTarget = null;
        
        File lvFileTarget = new File(pvTarget);
        try
        {
            ivFosTarget = new FileOutputStream(lvFileTarget);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Ausgabedatei nicht oeffnen!");
        }
        
        // Oeffnen der Eingabedatei
        FileInputStream lvFileInputStream = null;
        File ivFileSource1 = new File(pvSource);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileSource1);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Eingabedatei nicht oeffnen!");
            System.exit(1);
        }

        BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        
        try
        {
        	while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Einlesen
        	{
        		//System.out.println("Zeile: " + lvZeile);
        		if (lvZeile.startsWith(pvStartText))
        		{
        		  ivFosTarget.write((deleteFeld(lvZeile, pvPosition) + StringKonverter.lineSeparator).getBytes());
        		}
        		else
        		{
          		  ivFosTarget.write((lvZeile + StringKonverter.lineSeparator).getBytes());     			
        		}
        	}

        }
        catch (Exception exp)
        {
        	System.out.println("Fehler bei der Verarbeitung einer Zeile");
        	System.out.println("Zeile: " + lvZeile);
        }
        
        try
        {
            lvFileInputStream.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen der Eingabedatei");
        }
        
        try
        {
            ivFosTarget.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen der Ausgabedatei");
        }
    }
    
    /**
     * Entfernt das Feld aus der Zeile
     * @param pvZeile Zeile
     * @param pvPosition Position des Feldes
     */
    private String deleteFeld(String pvZeile, int pvPosition)
    {
      //System.out.println("Zeile: " + pvZeile);
      StringBuilder lvHelpStringBuilder = new StringBuilder();
      int lvAnzahlTrennzeichen = 0;
      int lvPositionZeile = 0;
      while (lvAnzahlTrennzeichen < pvPosition - 1)
      {
       	  if (pvZeile.charAt(lvPositionZeile) == ivTrennzeichen)
       	  {
       		  lvAnzahlTrennzeichen++;
       	  }   		 
    	  lvHelpStringBuilder.append(pvZeile.charAt(lvPositionZeile));
    	  lvPositionZeile++;
      }
      while (pvZeile.charAt(lvPositionZeile) != ivTrennzeichen)
      {
    	  lvPositionZeile++;
      }
      lvHelpStringBuilder.append(pvZeile.substring(lvPositionZeile + 1));
      //System.out.println("Result: " + lvHelpStringBuilder.toString());
      return lvHelpStringBuilder.toString();
    }

}
