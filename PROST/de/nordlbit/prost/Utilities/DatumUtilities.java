package de.nordlbit.prost.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class DatumUtilities 
{	
	/**
	 * @param pvDatum zu konvertierendes Datum
	 * @return konvertierte Datum
	 */
	public static String changeDate(String pvDatum)
	{
		String lvNeuesDatum = pvDatum;
		if (pvDatum.length() > 0)
		{
		  lvNeuesDatum = pvDatum.substring(6,10)
                         + "-" + pvDatum.substring(3,5)
                         + "-" + pvDatum.substring(0,2);
		}
		return lvNeuesDatum;
	}
	
	/**
	 * @param pvDatum zu konvertierendes Datum
	 * @return konvertierte Datum
	 */
	public static String changeDatePoints(String pvDatum)
	{
		String lvNeuesDatum = pvDatum;
		if (pvDatum.length() > 0)
		{
			lvNeuesDatum = pvDatum.substring(6,8)
					       + "." + pvDatum.substring(4,6)
					       + "." + pvDatum.substring(0,4);
		}
		return lvNeuesDatum;
	}
	
	/**
	 * Erzeugt einen Timestamp
	 * @param pvDatum 
	 * @param pvZeit 
	 * @return
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
     * 
     * @return
     */
    public static String printDateAndTime() 
    {
        Calendar lvCal = GregorianCalendar.getInstance();
        SimpleDateFormat lvSdf = new SimpleDateFormat();
        lvSdf.setTimeZone(lvCal.getTimeZone());
        lvSdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        return lvSdf.format(lvCal.getTime());
    }

}
