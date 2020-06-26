/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.trigger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.nordlbit.prost.FilePing.FilePingTask;
import de.nordlbit.prost.Utilities.StringKonverter;
import de.nordlbit.prost.bd.VgAufgabe;
import de.nordlbit.prost.bd.Vorgang;
import de.nordlbit.prost.dao.BuchungstagVerwaltung;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.ereignisvektor.EreignisVektorDAO;
import de.nordlbit.prost.dao.vorgang.VorgangDAO;

/**
 * @author tepperc
 *
 */
public class TriggerThread extends Thread
{    
    /**
     * Vorgang-ID
     */
    private String ivVorgangID;
    
    /**
     * Mandanten-ID
     */
    private String ivMandantenID;
    
    /**
     * Fertigereignis-ID
     */
    private String ivEreignisID;
    
    /**
     * AufgabenVektor-ID
     */
    private String ivAufgabenVektorID;
    
    /**
     * Uhrzeit
     */
    private String ivTime;
    
    /**
     * Die AufgabenVektor-ID der eventuell zu stoppenden Dateisuche
     */
    private String ivDateisucheAufgabenID;
    
    /**
     * Konstruktor
     * @param pvVorgangID 
     * @param pvMandantenID 
     * @param pvEreignisID 
     * @param pvAufgabenVektorID 
     * @param pvTime 
     * @param pvDateisucheAufgabenID
     */
    public TriggerThread(String pvVorgangID, String pvMandantenID, String pvEreignisID, String pvAufgabenVektorID, String pvTime, String pvDateisucheAufgabenID)
    {
        this.ivVorgangID = pvVorgangID;
        this.ivMandantenID = pvMandantenID;
        this.ivEreignisID = pvEreignisID;
        this.ivAufgabenVektorID = pvAufgabenVektorID;
        this.ivTime = pvTime;
        this.ivDateisucheAufgabenID = pvDateisucheAufgabenID;
    }
    
    /**
     * Test-Konstruktor
     * @param pvTime 
     */
    public TriggerThread(String pvTime)
    {
        BuchungstagVerwaltung ivBuchungstagVerwaltung = new BuchungstagVerwaltung(Vorgang.getSvMandant());

        if (reachedTime(pvTime, ivBuchungstagVerwaltung))
        {
            System.out.println(pvTime.toString() + " erreicht...");
         }
    }
    
    /**
     * 
     */
    public void run()
    {
        EreignisVektorDAO ivEreignisVektorDAO = new EreignisVektorDAO(Vorgang.getSvMandant());
        AufgabenVektorDAO ivAufgabenVektorDAO = new AufgabenVektorDAO(Vorgang.getSvMandant());
        BuchungstagVerwaltung ivBuchungstagVerwaltung = new BuchungstagVerwaltung(Vorgang.getSvMandant());
        System.out.println("Dateisuche stoppen - ID " + ivDateisucheAufgabenID);
        while (!reachedTime(ivTime, ivBuchungstagVerwaltung))
        {
            try
            {
              Thread.sleep(((Long)StringKonverter.convertString2Long(Vorgang.getIntervallTrigger())).longValue() * 1000);
            }
            catch (Exception exp)
            {
                System.out.println("Problem beim Sleep des TriggerThreads...");
            }
        }
        
        // CT 30.01.2018
        // Ein Trigger kann eine Dateisuche stoppen, d.h. aus der Liste der FilePingTasks loeschen
        VorgangDAO lvVorgangDAO = new VorgangDAO(Vorgang.getSvMandant());
        
        HashMap<String, VgAufgabe> lvListeVgAufgaben = ivAufgabenVektorDAO.findAufgabenByVorgangID(lvVorgangDAO.findVorgangByID(ivVorgangID));
        VgAufgabe lvHelpVgAufgabe = null;
        for (VgAufgabe lvVgAufgabe : lvListeVgAufgaben.values())
        {
        	if (lvVgAufgabe.getAufgabe().getId().equals(ivDateisucheAufgabenID))
        	{
        		lvHelpVgAufgabe = lvVgAufgabe;
        	}
        }
        
        if (lvHelpVgAufgabe != null)
        {
        	System.out.println("Size: " + Vorgang.getFilePingThread().getFilePingTasks().size());
            ArrayList<FilePingTask> lvHelpFilePingTaskListe = new ArrayList<FilePingTask>();
        	for (FilePingTask lvFilePingTask : Vorgang.getFilePingThread().getFilePingTasks())
        	{
        		lvHelpFilePingTaskListe.add(lvFilePingTask);
        	}
            
        	for (FilePingTask lvFilePingTask : lvHelpFilePingTaskListe)
        	{
        		if (lvFilePingTask.getAufgabenVektorID().equals(lvHelpVgAufgabe.getAufgabenVektorID()))
        		{
        			Vorgang.getFilePingThread().removeFilePingTask(lvFilePingTask);
        		}
        	}
        }

        Calendar lvCal = Calendar.getInstance();
        ivEreignisVektorDAO.insertEreignisVektor(printDateAndTime(lvCal), ivVorgangID, ivMandantenID, ivEreignisID);
        ivAufgabenVektorDAO.setEndeDatum(ivAufgabenVektorID, printDateAndTime(lvCal));
    }         

    /**
     * Uhrzeit erreicht?
     * @param pvTime Uhrzeit
     * @return true -> Uhrzeit erreicht; false -> Uhrzeit nicht erreicht
     */ 
    private boolean reachedTime(String pvTime, BuchungstagVerwaltung pvBuchungstagVerwaltung)
    {
        boolean lvReached = false;
        
        String lvHelpTime = pvTime.replaceAll(":", "");
        int lvTime1 = (new Integer(lvHelpTime)).intValue();
       
        Calendar lvCal = Calendar.getInstance();
        if (pvBuchungstagVerwaltung.isBuchungstag(printDateAsBuchungstag(lvCal)))
        {
          int lvTime2 = (new Integer(printOnlyTime(lvCal))).intValue();
          if (lvTime2 == lvTime1)
          {
            lvReached = true;
          }
        }
        
        return lvReached;
    } 
 
    /**
     * 
     * @param pvCal
     * @return
     */
    private String printOnlyTime(Calendar pvCal)
    { 
        SimpleDateFormat lvSdf = new SimpleDateFormat(); 
        lvSdf.setTimeZone(pvCal.getTimeZone());
        lvSdf.applyPattern("HHmm"); 
        return lvSdf.format(pvCal.getTime());                
    }
    
    /**
     * 
     * @param pvCal
     * @return
     */
    private String printDateAsBuchungstag(Calendar pvCal)
    { 
        SimpleDateFormat lvSdf = new SimpleDateFormat(); 
        lvSdf.setTimeZone(pvCal.getTimeZone());
        lvSdf.applyPattern("yyMMdd"); 
        return lvSdf.format(pvCal.getTime());                
    }
    
    /**
     * 
     * @param pvCal
     * @return
     */
    private String printDateAndTime(Calendar pvCal)
    { 
        SimpleDateFormat lvSdf = new SimpleDateFormat(); 
        lvSdf.setTimeZone(pvCal.getTimeZone());
        lvSdf.applyPattern( "yyyy-MM-dd HH:mm:ss" ); 
        return lvSdf.format(pvCal.getTime());                
    }
    
    /**
     * Hauptprogramm
     * @param args 
     * 
     */
    public static void main(String args[])
    {
        new TriggerThread("16:30");
        System.exit(0);
    }
}
