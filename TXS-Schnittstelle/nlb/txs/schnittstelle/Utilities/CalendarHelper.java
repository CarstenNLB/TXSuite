/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author tepperc
 *
 */
public class CalendarHelper 
{
	/**
	 * 
	 */
    private HashMap <Integer , Vector<Calendar> > ivMapFreeDays = new HashMap <Integer, Vector<Calendar>> (); 
           
    /** 
     *  Ermittelt ob das uebergebene Datum 
     *  ein Arbeitstag ist 
     * 
     * @param pvCal zu untersuchendes Datum 
     * @return <code> true </code> wenn das Datum ein Werktag ist 
     */ 
    public boolean isWorkingDay(Calendar pvCal)
    { 
        Vector <Calendar> lvFreedays = null; 
        Integer lvYear = new Integer (pvCal.get(Calendar.YEAR)); 
                
        pvCal.set(Calendar.HOUR,0); 
        pvCal.set(Calendar.MINUTE,0); 
        pvCal.set(Calendar.SECOND,0); 
                
        boolean lvRet = true; 
        // wenn der Tag auf Samstag oder Sonntag faellt, gleich false zurueckgeben 
        if (pvCal.get(Calendar.DAY_OF_WEEK) == 1 || 
                pvCal.get(Calendar.DAY_OF_WEEK) == 7)
        { 
            return false; 
        } 
        else 
        { 
            if (ivMapFreeDays.get(lvYear) == null)
            { 
                // erstmal die Feiertage ermitteln 
                identifyFreeDays (pvCal.get(Calendar.YEAR)); 
            } 
            lvFreedays = ivMapFreeDays.get(lvYear); 
            for (int i = 0; i < lvFreedays.size(); i++)
            { 
                //System.out.println(cal.getTimeInMillis() + " == "  + freedays.get(i).getTimeInMillis());
                if (pvCal.getTimeInMillis() == lvFreedays.get(i).getTimeInMillis())
                { 
                    lvRet = false; 
                } 
            }
            
            // Schaltjahre ermitteln
            if (lvYear % 400 == 0)
            {
               lvRet = true;
            }
            else
            {
               if (lvYear % 100 == 0)
               { 
                   lvRet = false;
               }
               else
               {
                   if (lvYear % 4 == 0)
                   {
                       lvRet = true;
                   }
               }
            }
        } 
                
        return lvRet; 
    } 
           
    /**
     * Ermittlung von Feiertagen eines Jahres
     * @param pvYear
     */
    private void identifyFreeDays(int pvYear)
    { 
        // Osterfeiertage 
        Vector <Calendar> lvVec = new Vector <Calendar>(); 
        GregorianCalendar lvTemp; 
        int i = pvYear % 19; 
        int j = pvYear / 100; 
        int k = pvYear % 100; 
        int l = (19*i+j-(j/4)-((j-((j + 8)/25)+1)/3)+15)%30; 
        int m = (32+2*(j%4)+2*(k/4)-l-(k%4))%7; 
        int n = l+m-7*((i+11*l+22*m)/451)+114; 
        int month = n / 31; 
        int day   = (n % 31) + 1; 
        // Karfreitag ausschliessen 
        lvTemp = new GregorianCalendar( pvYear, month-1, day ,0,0,0); 
        lvTemp.add(Calendar.DAY_OF_YEAR, -2); 
        //System.out.println("Karfreitag"); 
        //System.out.println(printDate (temp)); 
        lvVec.add(lvTemp); 
        lvTemp = (GregorianCalendar)lvTemp.clone(); 
        // Ostermontag ausschliessen 
        lvTemp.add(Calendar.DAY_OF_YEAR, 3); 
        //System.out.println("Ostermontag"); 
        //System.out.println(printDate (temp)); 
        lvVec.add(lvTemp); 
        lvTemp = (GregorianCalendar)lvTemp.clone(); 
        
        // Christi Himmelfahrt ausschlieﬂen 
        // 40 Tage nach Ostersonntag 
        lvTemp.add(Calendar.DAY_OF_MONTH, 38); 
        //System.out.println("Christi Himmelfahrt "); 
        printDate (lvTemp); 
        lvVec.add(lvTemp); 
        lvTemp = (GregorianCalendar)lvTemp.clone(); 
        
        // Pfingsmontag ausschlieﬂen 
        lvTemp.add(Calendar.DAY_OF_MONTH, 11); 
        //System.out.println("Pfingsmontag "); 
        printDate (lvTemp); 
        lvVec.add(lvTemp); 
        lvTemp = (GregorianCalendar)lvTemp.clone(); 
        
        // den ersten Mai ausschlieﬂen 
        lvTemp = new GregorianCalendar(pvYear, 4, 1); 
        lvVec.add(lvTemp); 
        
        // den dritten Oktober ausschlieﬂen 
        lvTemp = new GregorianCalendar(pvYear, 9, 3); 
        lvVec.add(lvTemp); 
        
        // Weihnachtsfeiertage ausschlieﬂen 
        lvTemp = new GregorianCalendar(pvYear, 11, 24); 
        lvVec.add(lvTemp); 
        lvTemp = new GregorianCalendar(pvYear, 11, 25); 
        lvVec.add(lvTemp); 
        lvTemp = new GregorianCalendar(pvYear, 11, 26); 
        lvVec.add(lvTemp); 
                
        // Silvester und Neujahr ausschlieﬂen 
        lvTemp = new GregorianCalendar(pvYear, 11, 31); 
        lvVec.add(lvTemp); 
        lvTemp = new GregorianCalendar(pvYear, 0, 1); 
        lvVec.add(lvTemp); 
        
        ivMapFreeDays.put(new Integer(pvYear), lvVec ); 
    } 
          
    /**
     * 
     * @param pvCal
     * @return
     */
    public String printDate(Calendar pvCal)
    { 
        SimpleDateFormat lvSdf = new SimpleDateFormat(); 
        lvSdf.applyPattern( "dd.MM.yyyy" ); 
        return lvSdf.format(pvCal.getTime());            
    }

    /**
     * 
     * @param pvCal
     * @return
     */
    public String printDateWithMinus(Calendar pvCal)
    { 
        SimpleDateFormat lvSdf = new SimpleDateFormat(); 
        lvSdf.applyPattern( "yyyy-MM-dd" ); 
        return lvSdf.format(pvCal.getTime());            
    }
    
    /**
     * 
     * @param pvCal
     * @return
     */
    public String printDateAndTime(Calendar pvCal)
    { 
        SimpleDateFormat lvSdf = new SimpleDateFormat(); 
        lvSdf.applyPattern( "dd.MM.yyyy HH:mm:ss" ); 
        return lvSdf.format(pvCal.getTime());                
    }
    
    /**
     * @param pvCal 
     * @return 
     * 
     */
    public String printDateAsNumber(Calendar pvCal)
    {
        SimpleDateFormat lvSdf = new SimpleDateFormat();
        lvSdf.applyPattern( "yyyyMMdd");
        return lvSdf.format(pvCal.getTime());   
    }
    
    /**
     * @param pvCal
     * @return
     */
    public String printTimeAsNumber(Calendar pvCal)
    {
        SimpleDateFormat lvSdf = new SimpleDateFormat();
        lvSdf.applyPattern( "HHmmss");
        return lvSdf.format(pvCal.getTime());   	
    }
    
}
