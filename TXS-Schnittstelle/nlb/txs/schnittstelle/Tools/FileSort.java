package nlb.txs.schnittstelle.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

public class FileSort 
{
	
    /**
     * Startroutine FileSort
     * @param argv 
     */
    public static void main(String argv[])
    { 
    	        
    	new FileSort(argv[argv.length - 2], argv[argv.length - 1]);
 
        System.exit(0);
    }

    /**
     * Konstruktor
     * @param pvSource
     * @param pvTarget
     */
    public FileSort(String pvSource, String pvTarget)
    {
       	System.out.println("Eingabedatei: " + pvSource);
    	System.out.println("Ausgabedatei: " + pvTarget);
    
    	ArrayList<String> lvListeZeilen = new ArrayList<String>();
    	String lvZeile;
    	        
        // Oeffnen Source1
        FileInputStream lvFileInputStream = null;
        File ivFileSource = new File(pvSource);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileSource);
        }
        catch (Exception e)
        {
            System.out.println("Konnte die Eingabedatei nicht oeffnen!");
            System.exit(1);
        }

        BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        
        String lvErsteZeile = new String();
        String lvLetzteZeile = new String();
        try
        {
        	lvErsteZeile = lvBufferedReaderIn.readLine();

        	while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Einlesen
        	{
        		if (lvZeile.startsWith("## EOF"))
        			lvLetzteZeile = lvZeile;
        		else
        			lvListeZeilen.add(lvZeile);
        	}

        }
        catch (Exception exp)
        {
        	System.out.println("Fehler beim Fuellen der Zeilenliste");
        }

        
        try
        {
            lvFileInputStream.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen der Eingabedatei");
        }
 
        System.out.println(lvErsteZeile);
        System.out.println(lvListeZeilen.size());
        System.out.println(lvLetzteZeile);
        
        Collections.sort(lvListeZeilen);
                
        // FileOutputStream fuer Target
        FileOutputStream ivFosTarget = null;
        
        File lvFileTarget = new File(pvTarget);
        try
        {
            ivFosTarget = new FileOutputStream(lvFileTarget);
        }
        catch (Exception e)
        {
            System.out.println("Konnte die Ausgabedatei nicht oeffnen!");
            System.exit(1);
        }

        try
        {
    		ivFosTarget.write((lvErsteZeile + StringKonverter.lineSeparator).getBytes());
        	
    		for (int i = 0; i < lvListeZeilen.size(); i++)
        	{
        		lvZeile = lvListeZeilen.get(i);
        		ivFosTarget.write((lvZeile + StringKonverter.lineSeparator).getBytes());
        	}
        	
    		ivFosTarget.write((lvLetzteZeile + StringKonverter.lineSeparator).getBytes());

        }
        catch (Exception exp)
        {
        	System.out.println("Fehler beim Schreiben in die Ausgabedatei");
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
}
