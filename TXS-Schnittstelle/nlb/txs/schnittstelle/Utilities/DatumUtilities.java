package nlb.txs.schnittstelle.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * In dieser Klasse sind Datum Hilfsfunktionen.
 * @author Carsten Tepper
 */
public abstract class DatumUtilities 
{	
	/**
	 * Konvertiert das Datum im Format 'TT.MM.JJJJ' in das TXS-Format 'JJJJ-MM-TT'
	 * @param pvDatum zu konvertierendes Datum im Format 'TT.MM.JJJJ'
	 * @return konvertiertes Datum im TXS-Format 'JJJJ-MM-TT'
	 */
	public static String changeDate(String pvDatum)
	{
		String lvNeuesDatum = pvDatum;

		try
		{
		  if (pvDatum.length() > 0)
		  {
		    lvNeuesDatum = pvDatum.substring(6,10)
                         + "-" + pvDatum.substring(3,5)
                         + "-" + pvDatum.substring(0,2);
		  }
		}
		catch (Exception exp)
		{
			System.out.println("Konnte Datum '" + pvDatum + "' nicht umwandeln!");
		}

		return lvNeuesDatum;
	}
	
	/**
	 * Konvertiert das Datum im Format 'JJJJMMTT' in das Format 'TT.MM.JJJJ'
	 * @param pvDatum zu konvertierendes Datum im Format 'JJJJMMTT'
	 * @return konvertiertes Datum im Format 'TT.MM.JJJJ'
	 */
	public static String changeDatePoints(String pvDatum)
	{
		String lvNeuesDatum = pvDatum;

		try
		{
		  if (pvDatum.length() > 0)
		  {
			  lvNeuesDatum = pvDatum.substring(6,8)
				   	       + "." + pvDatum.substring(4,6)
					         + "." + pvDatum.substring(0,4);
		  }
		}
		catch (Exception exp)
		{
			System.out.println("Konnte Datum '" + pvDatum + "' nicht umwandeln!");
		}

		return lvNeuesDatum;
	}
	
	/**
	 * Erzeugt einen Timestamp
	 * @param pvDatum Datum im Format 'JJJJMMTT'
	 * @param pvZeit Uhrzeit im Format 'SSMMss' (Stunden Minuten Sekunden)
	 * @return Einen Zeitstempel im Format 'JJJJ-MM-TTTSS:MM:ssZ'
	 */
	public static String createTimestamp(String pvDatum, String pvZeit)
	{
	    String lvTimestamp = pvDatum.substring(0, 4) + "-" 
	                       + pvDatum.substring(4, 6) + "-"
	                       + pvDatum.substring(6, 8) + "T"
	                       + pvZeit.substring(0, 2) + ":"
	                       + pvZeit.substring(2, 4) + ":"
	                       + pvZeit.substring(4, 6) + "Z";
	    return lvTimestamp;
	}
	
	/**
	 * @param pvFile Verweis auf eine Datei
	 * @return das Datum aus dem Dateinamen
	 */
	/*
	public static String getFileDate(File pvFile)
	{
		//System.out.println(pvFile.getName());
		Date lvFileDate = new Date(pvFile.lastModified());
		//System.out.println(lvFileDate.toString());
		return "";
	} */
	
	/**
	 * Berechnet die Differenztage zwischen einem Start- und einem Endedatum
	 * Das Startdatum muss immer kleiner als das Endedatum sein.
	 * @param pvStartDatum Startdatum Startdatum
	 * @param pvEndeDatum Endedatum Endedatum
	 * @return Die Differenztage zwischen Start- und Endedatum
	 */
	public static int calculateDays(String pvStartDatum, String pvEndeDatum)
	{
	    long lvDifferenzTage = 0;
	    
	    // Zwei Instanzen werden erzeugt
	    Calendar lvCal1 = Calendar.getInstance();
	    Calendar lvCal2 = Calendar.getInstance();
	    
	    // Setzt die beiden Datum
	    lvCal1.set((new Integer(pvStartDatum.substring(6,10))).intValue(), 
	             (new Integer(pvStartDatum.substring(3,5))).intValue() - 1, 
	             (new Integer(pvStartDatum.substring(0,2))).intValue());
	    
        lvCal2.set((new Integer(pvEndeDatum.substring(6,10))).intValue(), 
                (new Integer(pvEndeDatum.substring(3,5))).intValue() - 1, 
                (new Integer(pvEndeDatum.substring(0,2))).intValue());
	    
        // Umwandlung in Millisekunden
        long lvMillis1 = lvCal1.getTimeInMillis();
        long lvMillis2 = lvCal2.getTimeInMillis();
        
        // Berechnet die Differnz in Millisekunden
        long lvDiff = lvMillis2 - lvMillis1;
        
        // Berechnet die Differenztage
	    lvDifferenzTage = lvDiff / (24 * 60 * 60 * 1000);
	   
	    // Liefert das Ergebnis (Datentyp int) zurueck
	    return (int)lvDifferenzTage;
	}
	
	/**
	 * Berechnet die Differenztage zwischen dem '01.01.1900' und dem uebergebenen Datum.
	 * @param pvDatum Datum
	 * @return Die Differenztage zwischen '01.01.1900' und dem uebergebenen Datum
	 */
	public static int berechneAnzahlTage(String pvDatum)
	{
	    int lvHelpInt = 0;
	    
	    String lvSonderJahr1  = "99991231";
	    String lvSonderJahr2  = "00000000";
	    String lvSonderJahr3  = "11110101";
	    
	    if (pvDatum.equals(lvSonderJahr1) ||
	        pvDatum.equals(lvSonderJahr2) ||
	        pvDatum.equals(lvSonderJahr3))
	    { // Sonderfall
	     lvHelpInt = 0;
	    } // Sonderfall
	    else
	    {
	      // Datum im Format JJJJMMTT umwandeln in TT.MM.JJJJ
	      if (pvDatum.length() == 8)
	      {
	        pvDatum = pvDatum.substring(6, 8) + "." + pvDatum.substring(4, 6) + "." + pvDatum.substring(0,4);
	      }

	      lvHelpInt = calculateDays("01.01.1900", pvDatum);
	    }

	    return lvHelpInt;
	}
	
	/**
	 * Ermittelt den Buchungstag plus 2 Tage
	 * @param pvDatum Buchungsdatum
	 * @return Buchungsdatum plus 2 Tage
	 */
	public static String berechneBuchungstagPlus2(String pvDatum)
	{
		  String lvHelpDatum = new String();

	    if (pvDatum != null)
	    {
	       // Datum im Format JJJJMMTT umwandeln in TT.MM.JJJJ
	       if (pvDatum.length() == 8)
	       {
	           pvDatum = pvDatum.substring(6, 8) + "." + pvDatum.substring(4, 6) + "." + pvDatum.substring(0,4);
	       }

	       //System.out.println("Datum: " + datum);
	       CalendarHelper lvCalHelper = new CalendarHelper();
	    
	       Calendar lvCal = Calendar.getInstance();
	       // Setzt das Datum
	       lvCal.clear();
	       lvCal.set((new Integer(pvDatum.substring(6,10))).intValue(), 
	               (new Integer(pvDatum.substring(3,5))).intValue() - 1, 
	               (new Integer(pvDatum.substring(0,2))).intValue());
	       //System.out.println("Buchungstag: " + calHelper.printDate(cal));
	       // plus 1.Tag
	       lvCal.add(Calendar.DAY_OF_MONTH, 1);
	       while (!lvCalHelper.isWorkingDay(lvCal))
	       {
	           lvCal.add(Calendar.DAY_OF_MONTH, 1);
	           //System.out.println("+1: " + calHelper.printDate(cal));
	       }
	       // plus 2.Tag
	       lvCal.add(Calendar.DAY_OF_MONTH, 1);
	       while (!lvCalHelper.isWorkingDay(lvCal))
	       {
	           lvCal.add(Calendar.DAY_OF_MONTH, 1);
	           //System.out.println("+1: " + calHelper.printDate(cal));
	       }
	       //System.out.println("Buchungstag + 2: " + calHelper.printDate(cal));
	       lvHelpDatum = DatumUtilities.printDate(lvCal);
	     }

	    return lvHelpDatum;
	}
	
	 /**
	  * Addiert einen Tag auf das uebergebene Datum ohne jegliche Pruefung
	  * @param pvDatum Datum
	  * @return Datum plus ein Tag
	  */
    public static String addTagOhnePruefung(String pvDatum)
    {
        String lvHelpDatum = new String();
        
        if (pvDatum.length() == 8)
        {
           lvHelpDatum = changeDatePoints(pvDatum);   
        }

        Calendar lvCal = Calendar.getInstance();
        // Setzt das Datum
        lvCal.clear();
        lvCal.set((new Integer(lvHelpDatum.substring(6,10))).intValue(), 
                (new Integer(lvHelpDatum.substring(3,5))).intValue() - 1, 
                (new Integer(lvHelpDatum.substring(0,2))).intValue());
        // plus 1.Tag
        lvCal.add(Calendar.DAY_OF_MONTH, 1);

        lvHelpDatum = printDate(lvCal);
        
        return lvHelpDatum;
    }
	
	/**
	 * Addiert einen Tag auf das uebergebene Datum mit Pruefung auf Arbeitstag
     * @param lvDatum Datum
     * @return Datum des nächsten Arbeitstag
     * 
     */
    public static String addTag(String lvDatum)
    {
        String lvHelpDatum = new String();
        
        if (lvDatum.length() == 8)
        {
           lvHelpDatum = changeDatePoints(lvDatum);   
        }

        Calendar lvCal = Calendar.getInstance();
        // Setzt das Datum
        lvCal.clear();
        lvCal.set((new Integer(lvHelpDatum.substring(6,10))).intValue(), 
                (new Integer(lvHelpDatum.substring(3,5))).intValue() - 1, 
                (new Integer(lvHelpDatum.substring(0,2))).intValue());
        // plus 1.Tag
        lvCal.add(Calendar.DAY_OF_MONTH, 1);
        CalendarHelper ch = new CalendarHelper();
        while (!ch.isWorkingDay(lvCal))
        {
          lvCal.add(Calendar.DAY_OF_MONTH, 1);   
        }
        lvHelpDatum = printDate(lvCal);
        
        return lvHelpDatum;
    }
    
    /**
     * Formatiert das Datum
     * @param pvCal Ein Calendar-Objekt
     * @return formatiertes Datum als String
     */
    private static String printDate(Calendar pvCal)
    { 
        SimpleDateFormat lvSdf = new SimpleDateFormat(); 
        lvSdf.applyPattern( "yyyyMMdd"); //dd.MM.yyyy" ); 
        return lvSdf.format(pvCal.getTime());
    }
    
    /**
     * Pruefung, auf Tag - bspw. fuer 29.02.1995   
     * Uebergabedatum wird formal geprueft.   
     * Falls Tag unkorrekt, dann wird ein korrigiertes Datum
     * uebergeben. Schaltjahr findet Beruecksichtigung. 
     * @param pvDatum 
     * @return 
     */
    public static String pruefTag(String pvDatum)
    {
        String lvRetDatum = pvDatum;
        boolean lvSchaltjahr = false;
        int lvYear = StringKonverter.convertString2Int(pvDatum.substring(0, 4));
        
        // Februar
        if (lvYear % 400 == 0)
        {
           lvSchaltjahr = true;
        }
        else
        {
           if (lvYear % 100 == 0)
           { 
               lvSchaltjahr = false;
           }
           else
           {
               if (lvYear % 4 == 0)
               {
                   lvSchaltjahr = true;
               }
           }
        }

        // Februar: Immer YYYY0230 und YYYY0231 -> ungueltig 
        if (pvDatum.substring(4, 8).equals("0230") || pvDatum.substring(4, 8).equals("0231"))
        {
            lvRetDatum = pvDatum.substring(0,4) + "0301";
        }

        // Kein Schaltjahr: YYYY0229 -> ungueltig
        if (!lvSchaltjahr)
        {
            if (pvDatum.substring(4, 8).equals("0229"))
            {
                lvRetDatum = pvDatum.substring(0,4) + "0301";
            }
        }
                
        // April YYYY0431 -> ungueltig
        if (pvDatum.substring(4, 8).equals("0431"))
        {
            lvRetDatum = pvDatum.substring(0,4) + "0501";
        }
        
        // Juni YYYY0631 -> ungueltig
        if (pvDatum.substring(4, 8).equals("0631"))
        {
            lvRetDatum = pvDatum.substring(0,4) + "0701";
        }
         
        // September YYYY0931 -> ungueltig
        if (pvDatum.substring(4, 8).equals("0631"))
        {
            lvRetDatum = pvDatum.substring(0,4) + "0701";
        }

        // November YYYY1131 -> ungueltig
        if (pvDatum.substring(4, 8).equals("1131"))
        {
            lvRetDatum = pvDatum.substring(0,4) + "1201";
        }
        
        return lvRetDatum;
    }

    /**
     * Formatiert das Datum
     * @param pvDatum_ein Datum
     * @return formatiertes Datum
     */
    public static String FormatDatum(String pvDatum_ein)
    {
       String lvDatum_aus;  
       
       if ("00000000".equals(pvDatum_ein))
       {
         return("0000-00-00");
       }
       
       SimpleDateFormat lvSdfein = new SimpleDateFormat("yyyyMMdd"); 
       
       SimpleDateFormat lvSdfaus = new SimpleDateFormat("yyyy-MM-dd");  
      
       Date lvDate = null;
        
       try
       {
         lvDate = lvSdfein.parse (pvDatum_ein);
       } 
       catch (ParseException lvException)
       {
         lvException.printStackTrace();
       }  
       
       lvDatum_aus = lvSdfaus.format(lvDate);
       
       return (lvDatum_aus); 
    }
}
