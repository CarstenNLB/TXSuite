/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.FilePing;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.ereignisvektor.EreignisVektorDAO;
import de.nordlbit.prost.dao.mandanten.MandantenDAO;

/**
 * @author tepperc
 *
 */
public class FilePing 
{   
    /**
     * Startroutine
     * @param args
     */
    public static void main(String[] args) 
    {
       Calendar c = Calendar.getInstance();
       c.roll(Calendar.MINUTE, true);
       Timer t = new Timer(false);
       t.schedule(new TimerTasker(args[args.length - 6], args[args.length - 5], args[args.length - 4], args[args.length - 3], args[args.length - 2], args[args.length - 1]), 
                  c.getTime(), 120000);
    }

    /**
     * Timer-Task 
     */
    private static class TimerTasker extends TimerTask 
    {                    
       /**
        * Dateiname
        */
       private String ivDatei;
       
       /**
        * Vorgang-ID
        */
       private String ivVorgangID;
       
       /**
        * Mandant
        */
       private String ivMandant;
       
       /**
        * Mandantenname
        */
       private String ivMandantenname;
       
       /**
        * Ereignis-ID
        */
       private String ivEreignisID;
       
       /**
        * AufgabenVektor-ID
        */
       private String ivAufgabenVektorID;
       
       /**
        * Datenbankzugriff auf den Ereignis-Vektor
        */
       private EreignisVektorDAO ivEreignisVektorDAO;
       
       /**
        * Datenbankzugriff auf den Aufgaben-Vektor
        */
       private AufgabenVektorDAO ivAufgabenVektorDAO;
       
       /**
        * Konstruktor
        * @param pvFilenameINI 
        * @param pvDatei 
        * @param pvVorgangID 
        * @param pvMandant 
        * @param pvEreignisID 
        * @param pvAufgabenVektorID 
        */
       public TimerTasker(String pvFilenameINI, String pvDatei, String pvVorgangID, String pvMandant, String pvEreignisID, String pvAufgabenVektorID)
       {
           IniReader lvReader = null;
           String lvInstitut = new String();
           String lvMandantenname = new String();
           try 
           {
               lvReader = new IniReader(pvFilenameINI);
                           
               lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
               if (lvInstitut.equals("Fehler"))
               {
                   System.out.println("[Konfiguration]: Kein Institut in der 'prost.ini'...");
               }
               System.out.println("Institut: " + lvInstitut);
               
               lvMandantenname = lvReader.getPropertyString("Konfiguration", "Mandant", "Fehler");
               if (lvMandantenname.equals("Fehler"))
               {
                   System.out.println("[Konfiguration]: Kein Mandantenname in der 'prost.ini'...");
               }
               System.out.println("Mandantenname: " + lvMandantenname);
               this.ivMandantenname = lvMandantenname;

           }
           catch (Exception exp)
           {
               System.out.println("Fehler beim Laden der 'prost.ini'...");
               System.exit(0);
           }
                   
           //Datenbankparameter einlesen
           if (!DatenbankZugriff.readParameter(pvFilenameINI, lvMandantenname))
           {
               System.out.println("[Datenbank]: Fehler beim Laden der 'prost.ini'...");
               System.exit(0);
           }      
           
           MandantenDAO lvMandantenDAO = new MandantenDAO(lvMandantenname);
           Mandant lvMandant = Mandant.getMandanten().get(lvMandantenDAO.findMandantenID(lvInstitut));
           
           this.ivDatei = pvDatei;
           this.ivVorgangID = pvVorgangID;
           this.ivMandant = pvMandant;
           this.ivEreignisID = pvEreignisID;    
           this.ivAufgabenVektorID = pvAufgabenVektorID;
           ivEreignisVektorDAO = new EreignisVektorDAO(lvMandant);
           ivAufgabenVektorDAO = new AufgabenVektorDAO(lvMandant);
       }
       
       /**
        * Start des Suchtasks
        */
       public void run()
       {
           System.out.println("Suchdatei: " + ivDatei);
      
           if (existsFile(ivDatei))
           {
             System.out.println("Datei " + ivDatei + " vorhanden...");
             Calendar lvCal = Calendar.getInstance();
             DatenbankZugriff.openConnection(ivMandantenname);
             ivEreignisVektorDAO.insertEreignisVektor(printDateAndTime(lvCal), ivVorgangID, ivMandant, ivEreignisID);
             ivAufgabenVektorDAO.setEndeDatum(ivAufgabenVektorID, printDateAndTime(lvCal));
             DatenbankZugriff.closeConnection();
             System.exit(0);
           }
           else
             System.out.println("Datei nicht gefunden...");
       }

       /**
        * Existiert das File?
        * @param filename Name des Files
        * @return true -> File existiert; false -> File existiert nicht
        */ 
       private boolean existsFile(String filename)
       {
           File file = new File(filename);
           return file.exists();
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

    }    
}
