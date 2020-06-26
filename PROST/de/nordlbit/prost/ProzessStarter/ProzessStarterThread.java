/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.ProzessStarter;

import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.ProzessStarter;

/**
 * @author tepperc
 *
 */
public class ProzessStarterThread extends Thread
{
    /**
     * 
     */
    private ProzessStarter ivProzessStarter;
    
    /**
     * 
     */
    private String ivVorgangID;
    
    /**
     * 
     */
    private Mandant ivMandant;
    
    /**
     * 
     */
    private String ivEreignisID;
    
    /**
     * 
     */
    private String ivAufgabenVektorID;
        
    /**
     * Konstruktor
     * @param pvProzessStarter 
     * @param pvVorgangID 
     * @param pvMandant 
     * @param pvEreignisID 
     * @param pvAufgabenVektorID 
     */
    public ProzessStarterThread(ProzessStarter pvProzessStarter, String pvVorgangID, Mandant pvMandant, String pvEreignisID, String pvAufgabenVektorID)
    {
        this.ivProzessStarter = pvProzessStarter;
        this.ivVorgangID = pvVorgangID;
        this.ivMandant = pvMandant;
        this.ivEreignisID = pvEreignisID;
        this.ivAufgabenVektorID = pvAufgabenVektorID;
    }
    
    /**
     * 
     */
    public void run()
    {
    	try
    	{
    		Runtime.getRuntime().exec(ivProzessStarter.getPfad() + ivProzessStarter.getProzessname() + " " + ivVorgangID + " " + ivMandant.getId() + " " + ivEreignisID + " " + ivAufgabenVektorID);               
    	}
    	catch (Exception exp)
    	{
    		System.out.println("Probleme beim ProzessStarter " + ivProzessStarter.getPfad() + ivProzessStarter.getProzessname() + "...");
    	}
    }   
           
}
