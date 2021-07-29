/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.FilePing;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.StringCrypt;
import de.nordlbit.prost.Utilities.StringKonverter;
import de.nordlbit.prost.bd.Vorgang;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.ereignisvektor.EreignisVektorDAO;

/**
 * @author tepperc
 *
 */
public class FilePingThread extends Thread 
{
    /**
     * Liste der FilePing-Tasks
     */
    private ArrayList<FilePingTask> ivListeFilePingTasks;
    
    /**
     * Server(name)
     */
    private String ivServer;
    
    /**
     * Verzeichnispfad
     */
    private String ivPfad;
    
    /**
     * Username
     */
    private String ivUsername;
    
    /**
     * Password 
     */
    private String ivPassword;
        
    /**
     * Konstruktor
     */
    public FilePingThread()
    {
        ivListeFilePingTasks = new ArrayList<FilePingTask>();
    } 
    
    /**
     * Fuegt einen FilePingTask hinzu
     * @param pvFilePingTask
     */
    public void addFilePingTask(FilePingTask pvFilePingTask)
    {
        ivListeFilePingTasks.add(pvFilePingTask);
    }
    
    /**
     * Loescht einen FilePingTask
     * @param pvFilePingTask 
     */
    public void removeFilePingTask(FilePingTask pvFilePingTask)
    {
        ivListeFilePingTasks.remove(pvFilePingTask);
    }
    
    /**
     * Liefert die Liste der FilePingTask
     * @return Liste der FilePingTask
     */
    public ArrayList<FilePingTask> getFilePingTasks()
    {
    	return this.ivListeFilePingTasks;
    }
    
    /**
     * 
     */
    public void run()
    {
    	boolean lvMount = false;
        FilePingTask helpFilePingTask = null;
        while (true)
        {
        	if (ivListeFilePingTasks.size() > 0)
        	{
        		mountDrive();
        		lvMount = true;
        	}
            
            for (int x = 0; x < ivListeFilePingTasks.size(); x++)
            {
                helpFilePingTask = ivListeFilePingTasks.get(x);
                if (existsFile(helpFilePingTask))
                {
                  // Erst 5 Minuten warten, damit die Datei korrekt uebertragen werden kann.
                  // CT 11.10.2017
                  try
                  {
                      Thread.sleep(300000);
                  }
                  catch (Exception exp)
                  {
                      System.out.println("Problem beim Sleep vom FilePingThread...");
                  }

                  EreignisVektorDAO ivEreignisVektorDAO = new EreignisVektorDAO(helpFilePingTask.getMandant());
                  AufgabenVektorDAO ivAufgabenVektorDAO = new AufgabenVektorDAO(helpFilePingTask.getMandant());
               
                  //helpFilePingTask.getLogging().write("Datei " + helpFilePingTask.getDateiname() + " vorhanden...\n");
                  Calendar lvCal = Calendar.getInstance();
                  ivEreignisVektorDAO.insertEreignisVektor(printDateAndTime(lvCal), helpFilePingTask.getVorgangID(), helpFilePingTask.getMandantenID(), helpFilePingTask.getEreignisID());
                  //helpFilePingTask.getLogging().write("Fertigereignis: " + printDateAndTime(lvCal) + " " + helpFilePingTask.getVorgangID() + " " + helpFilePingTask.getMandantenID() + " " + helpFilePingTask.getEreignisID() + "\n");
                  ivAufgabenVektorDAO.setEndeDatum(helpFilePingTask.getAufgabenVektorID(), printDateAndTime(lvCal));
                  //helpFilePingTask.getLogging().write("Aufgabe erledigt -> " + helpFilePingTask.getAufgabenVektorID() + " " + printDateAndTime(lvCal) + "\n");
                  //helpFilePingTask.getLogging().closeLog();
                  ivListeFilePingTasks.remove(helpFilePingTask);
                }
            }
            
            if (lvMount)
            {
            	unmountDrive();
            	lvMount = false;
            }

            try
            {
                Thread.sleep(((Long)StringKonverter.convertString2Long(Vorgang.getIntervallDateisuche())).longValue() * 1000);
            }
            catch (Exception exp)
            {
                System.out.println("Problem beim FilePingThread...");
            }
        }
    }
       
    /**
     * Alle Dateien gefunden    
     */
    /* private boolean alleGefunden()
    {
        for (int x = 0; x < ivListeFilePingTasks.size(); x++)
        {
            FilePingTask helpFilePingTask = ivListeFilePingTasks.get(x);
            if (helpFilePingTask.isGefunden())
                return true;
        }
        return false;
    } */
        
    /**
     * Existiert das File?
     * @param pvFilePingTask Verweis auf FilePingTask
     * @return true -> File existiert; false -> File existiert nicht
     */ 
    private boolean existsFile(FilePingTask pvFilePingTask)
    {
        //Calendar lvCal = Calendar.getInstance();
        //pvFilePingTask.getLogging().write(printDateAndTime(lvCal) + "\n");
        //pvFilePingTask.getLogging().write("Zeitzone: " + lvCal.getTimeZone().toString() + "\n");
        
        //pvFilePingTask.getLogging().write("Start Filetest " + pvFilePingTask.getDateiname() + "...\n");
        File file = new File(pvFilePingTask.getDateiname());
        boolean lvExists = file.exists();
        if (lvExists && pvFilePingTask.getFileSize() < file.length())
        {
            pvFilePingTask.setFileSize(file.length());
            lvExists = false;
        }
        //pvFilePingTask.getLogging().write("Ende Filetest " + pvFilePingTask.getDateiname() + "...\n");

        return lvExists;
    } 

    /**
     * Kopiert das File, wenn es vorhanden ist
     * @param filename Name des Files
     * @return true -> File konnte kopiert werden; false -> File konnte nicht kopiert werden
     */
    /* private boolean copyFile(String filename)
    {
        Calendar lvCal = Calendar.getInstance();
        ivLogging.write(printDateAndTime(lvCal) + "\n");
        ivLogging.write("Zeitzone: " + lvCal.getTimeZone().toString() + "\n");
        ivLogging.write("Start Filetest " + filename + "...\n");
        
        //String command = "runas /savecred /user:kbk\\TXSTRV01 \"xcopy " + filename + " D:\\TXS_009_PROD\\DATEN\\LB /Y /I\"";
        String command = "runas /savecred /user:kbk\\TXSTRV01 \"xcopy " + filename + " D:\\TXS_009_TETB\\DATEN\\LB /Y /I\"";
        
        int lvExitValue = 0;
        try 
        {
          Runtime runtime = Runtime.getRuntime();
          Process process = runtime.exec(command);
          lvExitValue = process.exitValue();
          ivLogging.write("ExitValue: " + lvExitValue);
        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Kopieren...");
            ivLogging.write("Fehler beim Kopieren...\n");
        }

        ivLogging.write("Ende Filetest " + filename + "...\n");
        
        if (lvExitValue != 0)
            return false;
        else
            return true;
    } */
    
    /**
     * Mounten eines Verzeichnisses
     */
    private void mountDrive()
    {
        long timeout = 300000;
        String command = "net.exe use ";

        command = command + "W: \\\\" + ivServer + "\\" + ivPfad + " ";
        command = command + ivPassword + " /USER:" + ivUsername;
        //System.out.println("Command: " + command);
        try 
        {
           //Runtime.getRuntime().exec(command);
          Runtime runtime = Runtime.getRuntime();
          Process process = runtime.exec(command);
          //pvLogging.write("Mount-Process gestartet...\n");  

          Worker worker = new Worker(process);
          worker.start();
          //pvLogging.write("Mount-Worker gestartet...\n");
          try 
          {
            worker.join(timeout);
            if (worker.exit == null)
            {
                //pvLogging.write("TimeoutException...\n");
               throw new TimeoutException();
            }
          } 
          catch (InterruptedException ex) 
          {
            //pvLogging.write("InterruptedException...\n");  
            worker.interrupt();
            Thread.currentThread().interrupt();
            throw ex;
          } 
          finally 
          {
            process.destroy();
            //pvLogging.write("Mount-Process zerstoert...\n");
          }
        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Mount...");
            //pvLogging.write("Fehler beim Mount...\n");
        }
    }
    
    /**
     * Unmounten eines Verzeichnisses
     */
    private void unmountDrive()
    {
        long timeout = 300000;
        String command = "c:\\windows\\system32\\net.exe use ";

        command = command + "W: /DELETE";
        try 
        {
          //Runtime.getRuntime().exec(command);
          Runtime runtime = Runtime.getRuntime();
          Process process = runtime.exec(command);
          //pvLogging.write("Mount-Process gestartet...\n");  

          Worker worker = new Worker(process);
          worker.start();
          //pvLogging.write("Mount-Worker gestartet...\n");
          try 
          {
            worker.join(timeout);
            if (worker.exit == null)
            {
                //pvLogging.write("TimeoutException...\n");
               throw new TimeoutException();
            }
          } 
          catch (InterruptedException ex) 
          {
              //pvLogging.write("InterruptedException...\n");  
            worker.interrupt();
            Thread.currentThread().interrupt();
            throw ex;
          } 
          finally 
          {
            process.destroy();
            //pvLogging.write("Mount-Process zerstoert...\n");
          }

        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Unmount...");
            //pvLogging.write("Fehler beim Unmount...\n");
        }
    }
    
    /**
     * Liest die TVL-Parameter ein
     * @param pvFilename
     * @return
     */
    public boolean readParameter(String pvFilename)
    {
        //TVL-Parameter einlesen
        IniReader lvReader = null;
        try 
        {
            lvReader = new IniReader(pvFilename);
            ivServer = lvReader.getPropertyString("TVL", "Server", "Fehler");
            if (ivServer.equals("Fehler"))
            {
                System.out.println("Kein TVL-Server in der 'prost.ini'...");
                return false;
            }
            System.out.println("TVL-Server: " + ivServer);
                        
            ivPfad = lvReader.getPropertyString("TVL", "Pfad", "Fehler");
            if (ivPfad.equals("Fehler"))
            {
                System.out.println("Kein TVL-Pfad in der 'prost.ini'...");
                return false;
            }
            System.out.println("TVL-Pfad: " + ivPfad);

            ivUsername =  lvReader.getPropertyString("TVL", "Username", "Fehler");
            if (ivUsername.equals("Fehler"))
            {
                System.out.println("Kein TVL-Username in der 'prost.ini'...");
               return false;
            }
            System.out.println("TVL-Username: " + ivUsername);
            
            ivPassword = lvReader.getPropertyString("TVL", "Password", "Fehler");
            if (ivPassword.equals("Fehler"))
            {
                System.out.println("Kein TVL-Password in der 'prost.ini'...");
                return false;
            }
            System.out.println("TVL-Password: " + ivPassword);
            ivPassword = StringCrypt.decodeROT13(ivPassword);
        }
        catch (Exception exp)
        {
            return false;
        }
 
        return true;
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
     * 
     * @author tepperc
     *
     */
    private static class Worker extends Thread 
    {
        private final Process process;
        private Integer exit;
        
        private Worker(Process process) 
        {
           this.process = process;
        }
    
        public void run() 
        {
            try 
            { 
                exit = process.waitFor();
            } 
            catch (InterruptedException ignore) 
            {
                return;
            }
        }  
    }
}

