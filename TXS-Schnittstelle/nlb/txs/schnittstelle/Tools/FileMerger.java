package nlb.txs.schnittstelle.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

public class FileMerger 
{
    /**
     * Startroutine FileMerger
     * @param argv 
     */
    public static void main(String argv[])
    { 
    	        
    	new FileMerger(argv[argv.length - 3], argv[argv.length - 2], argv[argv.length - 1]);
 
        System.exit(0);
    }
    
    /**
     * Konstruktor
     * @param pvSource1
     * @param pvSource2
     * @param pvTarget
     */
    public FileMerger(String pvSource1, String pvSource2, String pvTarget)
    {
       	System.out.println("Source1: " + pvSource1);
    	System.out.println("Source2: " + pvSource2);
    	System.out.println("Target: " + pvTarget);

    	String lvZeile;
    	
        // FileOutputStream fuer Target
        FileOutputStream ivFosTarget = null;
        
        File lvFileTarget = new File(pvTarget);
        try
        {
            ivFosTarget = new FileOutputStream(lvFileTarget);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Target nicht oeffnen!");
        }
        
        // Oeffnen Source1
        FileInputStream lvFileInputStream = null;
        File ivFileSource1 = new File(pvSource1);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileSource1);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Source1 nicht oeffnen!");
            System.exit(1);
        }

        BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        
        try
        {
        	while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Einlesen
        	{
        		ivFosTarget.write((lvZeile + StringKonverter.lineSeparator).getBytes());
        	}

        }
        catch (Exception exp)
        {
        	System.out.println("Fehler beim Einfuegen von Source1");
        }
        
        try
        {
            lvFileInputStream.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen Source1");
        }

        // Oeffnen Source2
        File ivFileSource2 = new File(pvSource2);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileSource2);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Source2 nicht oeffnen!");
            System.exit(1);
        }

        lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        
        try
        {
        	while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Einlesen
        	{
        		ivFosTarget.write((lvZeile + StringKonverter.lineSeparator).getBytes());
        	}

        }
        catch (Exception exp)
        {
        	System.out.println("Fehler beim Einfuegen von Source2");
        }

        try
        {
            lvFileInputStream.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen Source2");
        }
        
        try
        {
            ivFosTarget.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen vom Target");
        }

    }

}
