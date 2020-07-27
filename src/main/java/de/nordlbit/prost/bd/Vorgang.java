/**
 * *****************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale Alle Rechte
 * vorbehalten.
 *
 ******************************************************************************
 */
package de.nordlbit.prost.bd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.nordlbit.prost.FilePing.FilePingThread;
import de.nordlbit.prost.Utilities.DatumUtilities;
import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.MailVersand;
import de.nordlbit.prost.Utilities.StringKonverter;
import de.nordlbit.prost.dao.BuchungstagVerwaltung;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.TaskPingDAO;
import de.nordlbit.prost.dao.aufgabe.AufgabeDAO;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.ereignisvektor.EreignisVektorDAO;
import de.nordlbit.prost.dao.mandanten.MandantenDAO;
import de.nordlbit.prost.dao.vorgang.VorgangDAO;

/**
 * @author frankew
 *
 */
public class Vorgang implements Comparable<Vorgang> {

    private static Vorgang svInstance;
    private static String svInstitut;
    private static FilePingThread svFilePingThread;
    private static Mandant svMandant;
    private static String svIntervallDateisuche;
    private static String svIntervallDurchlauf;
    private static String svIntervallTrigger;
    private static String svMailUsername;
    private static String svMailPassword;
    private static String svMailSmtpHost;
    
    private Set<Aufgabe> ivAufgaben;
    //private static HashMap<String, Vorgang> ivVorgaenge;
    private String ivId;
    private String ivBuchungstag;
    private boolean ivIsRestartMode = false;
    private TaskPingDAO ivTaskPingDAO;
    
    /**
     * MailVersand TXS-Team
     */
    private MailVersand ivMailVersandTXS;
    
    /**
     * MailVersand Fachbereich
     */
    private MailVersand ivMailVersandFB;
    
    /**
     * Zeigt an, ob die Verarbeitung im exklusiven Modus ist
     */
    private boolean ivInExklusivModus = false;
    
    /**
     * Die ID der exklusiven Aufgabe
     */
    private String ivVgAufgabeExklusivID;
    
    /**
     * 
     * @param pvMandant
     * @param pvBuchungstag
     * @return
     */
    public static HashMap<String, Vorgang> getVorgaenge(Mandant pvMandant, String pvBuchungstag) {
        VorgangDAO lvDAO = new VorgangDAO(pvMandant);
        HashMap<String, Vorgang> lvResult = lvDAO.findVorgaengeByID(pvMandant.getId(), pvBuchungstag);

        return lvResult;
    }

    /**
     * @param pvId
     * @param pvBuchungstag 
     * @param pvMandant 
     * @param pvInstitut 
     */
    public Vorgang(String pvId, String pvBuchungstag, Mandant pvMandant, String pvInstitut) 
    {
        ivTaskPingDAO = new TaskPingDAO(pvMandant);
        this.ivId = pvId;
        svMandant = pvMandant;
        this.ivBuchungstag = pvBuchungstag;
        svInstitut = pvInstitut;
    }

    /**
     * @param argv
     * @throws InterruptedException 
     */
    public static void main(String argv[]) // throws InterruptedException 
    {
        String lvFilenameINI = argv[argv.length - 1];
        IniReader lvReader = null;
        String lvBuchungstag = new String();
        //String lvMandantenname = new String();
        try 
        {
            lvReader = new IniReader(lvFilenameINI);
            lvBuchungstag = lvReader.getPropertyString("Konfiguration", "Buchungstag", "Fehler");
            if (lvBuchungstag.equals("Fehler"))
            {
                System.out.println("[Konfiguration]: Kein Buchungstag in der 'prost.ini'...");
                System.exit(1);
            }
            System.out.println("Buchungstag: " + lvBuchungstag);
                        
            svInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (svInstitut.equals("Fehler"))
            {
                System.out.println("[Konfiguration]: Kein Institut in der 'prost.ini'...");
                System.exit(1);
            }
            System.out.println("Institut: " + svInstitut);
            
            //lvMandantenname = lvReader.getPropertyString("Konfiguration", "Mandant", "Fehler");
            //if (lvMandantenname.equals("Fehler"))
            //{
            //    System.out.println("[Konfiguration]: Kein Mandantenname in der 'prost.ini'...");
            //}
            //System.out.println("Mandantenname: " + lvMandantenname);

            svIntervallDateisuche = lvReader.getPropertyString("Intervall", "Dateisuche", "Fehler");
            if (svIntervallDateisuche.equals("Fehler"))
            {
                System.out.println("[Intervall]: Keine Intervallzeit fuer die Dateisuche in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("Intervallzeit fuer die Dateisuche: " + svIntervallDateisuche);

            svIntervallDurchlauf = lvReader.getPropertyString("Intervall", "Durchlauf", "Fehler");
            if (svIntervallDurchlauf.equals("Fehler"))
            {
                System.out.println("[Intervall]: Keine Intervallzeit fuer den Durchlauf in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("Intervallzeit fuer den Durchlauf: " + svIntervallDurchlauf);

            svIntervallTrigger = lvReader.getPropertyString("Intervall", "Trigger", "Fehler");
            if (svIntervallTrigger.equals("Fehler"))
            {
                System.out.println("[Intervall]: Keine Intervallzeit fuer die Trigger in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("Intervallzeit fuer die Trigger: " + svIntervallTrigger);
            
            svMailUsername = lvReader.getPropertyString("Mail", "Username", "Fehler");
            if (svMailUsername.equals("Fehler"))
            {
                System.out.println("[Mail]: Kein Username fuer den SMTP-Host in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("Username SMTP-Host: " + svMailUsername);

            svMailPassword = lvReader.getPropertyString("Mail", "Password", "Fehler");
            if (svMailPassword.equals("Fehler"))
            {
                System.out.println("[Mail]: Kein Password fuer den SMTP-Host in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("Password SMTP-Host: " + svMailPassword);

            svMailSmtpHost = lvReader.getPropertyString("Mail", "SmtpHost", "Fehler");
            if (svMailSmtpHost.equals("Fehler"))
            {
                System.out.println("[Mail]: Kein SmtpHost in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("SmtpHost: " + svMailSmtpHost);            
        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Laden der 'prost.ini'...");
            System.exit(1);
        }
                
        //Datenbankparameter einlesen
        if (!DatenbankZugriff.readParameter(lvFilenameINI, svInstitut))
        {
            System.out.println("[Datenbank]: Fehler beim Laden der 'prost.ini'...");
            System.exit(1);
        }
               
        //System.out.println("CTCTCT: " + svInstitut);
        //System.out.println("lvMandantenname: " +lvMandantenname);
        MandantenDAO lvMandantenDAO = new MandantenDAO(svInstitut);
        Mandant.setMandanten(lvMandantenDAO.getMandanten());
        //System.out.println("MandantenID: " + lvMandantenDAO.findMandantenID(svInstitut));
        svMandant = Mandant.getMandanten().get(lvMandantenDAO.findMandantenID(svInstitut));
        //lvMandant.setName(lvMandantenname);
                
        BuchungstagVerwaltung lvBuchungstagVerwaltung = new BuchungstagVerwaltung(svMandant);
        
        // FilePingThread starten
        svFilePingThread = new FilePingThread();
        if (!svFilePingThread.readParameter(lvFilenameINI))
        {
            System.out.println("[TVL]: Fehler beim Laden der 'prost.ini'...");
            System.exit(1);
        }
        svFilePingThread.start();
        //System.out.println(lvMandant.getName());
        
        while (!lvBuchungstag.isEmpty())
        {           
            String lvId = "" + System.currentTimeMillis();
            startVorgang(lvId, lvBuchungstag, svMandant, svInstitut);
            String lvHelpBuchungstag = lvBuchungstagVerwaltung.naechsteBuchungsdatum(lvBuchungstag.substring(8) + lvBuchungstag.substring(3,5) + lvBuchungstag.substring(0,2));
            lvBuchungstag = lvHelpBuchungstag.substring(4) + "." + lvHelpBuchungstag.substring(2,4) + ".20" + lvHelpBuchungstag.substring(0,2);
            System.out.println("Neuer Buchungstag: " + lvBuchungstag);
         }
       
        System.exit(0);
     }
    
    /**
     * Startet einen Vorgang
     * @param pvID
     * @param pvBuchungstag 
     * @param pvMandant 
     */
    private static void startVorgang(String pvID, String pvBuchungstag, Mandant pvMandant, String pvInstitut)
    {   
        Vorgang lvVorgang = new Vorgang(pvID, pvBuchungstag, pvMandant, pvInstitut);
        lvVorgang.save();
        lvVorgang.start();
    }
    
    /**
     * 
     */
    private void start()
    {   
        ivMailVersandTXS = new MailVersand(svMailUsername, svMailPassword, svMailSmtpHost, "txsuite@nordlb.de");
        ivMailVersandFB = new MailVersand(svMailUsername, svMailPassword, svMailSmtpHost, "");
    	
    	System.out.println("Vorgang.getSvMandant(): " + Vorgang.getSvMandant());
        this.loadUnbedingteAufgaben(Vorgang.getSvMandant());
        VgEreignisvektor lvEreignisVektor = new VgEreignisvektor(this);

        AufgabenVektorDAO lvDAO = new AufgabenVektorDAO(Vorgang.getSvMandant());
        EreignisVektorDAO lvEreignisVektorDAO = new EreignisVektorDAO(Vorgang.getSvMandant());
        
    	Set<Aufgabe> lvHelpAufgaben = new HashSet<Aufgabe>();
        for (Aufgabe lvAufgabe : this.ivAufgaben)
        {
            lvHelpAufgaben.add(lvAufgabe);
        }
        for (Aufgabe lvAufgabe : lvHelpAufgaben)
        {
            //lvDAO.insertAufgabenVektor(Vorgang.printDateAndTime(), null, this.getId(), this.getMandant().getId(), pvAufgabe.getId());
            //DatenbankZugriff.commit();
            //String lvAufgabenVektorID = lvDAO.findAufgabenVektorID(this.getId(), this.getMandant().getId(), pvAufgabe.getId());


            String lvAufgabenVektorID = lvDAO.insertAufgabenVektorReturnID(DatumUtilities.printDateAndTime(), null, this.getId(), Vorgang.getSvMandant().getId(), lvAufgabe.getId());
            System.out.println("lvAufgabenVektorID: " + lvAufgabenVektorID);  

            // Start der unbedingten Aufgaben
            if (lvAufgabe.isUnbedingteAufgabe())
            {
              VgAufgabe lvVgAufgabe = new VgAufgabe(this, lvAufgabe, lvAufgabenVektorID);
              lvVgAufgabe.start();
              this.ivAufgaben.remove(lvAufgabe);
              if (lvAufgabe.getAktivEreignis() != null)
              {
                  Calendar lvCal = Calendar.getInstance();
                  SimpleDateFormat lvSdf = new SimpleDateFormat();
                  lvSdf.setTimeZone(lvCal.getTimeZone());
                  lvSdf.applyPattern( "yyyy-MM-dd HH:mm:ss" ); 

                  lvEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), this.getId(), Vorgang.getSvMandant().getId(), lvAufgabe.getAktivEreignis().getId());
              }
              if (lvAufgabe.getLoeschEreignis() != null)
              {
            	  lvEreignisVektorDAO.deleteStopEreignisVektor(this.getId(), Vorgang.getSvMandant().getId(), lvAufgabe.getLoeschEreignis().getId());
              }
            }
         }
        
        int lvDurchlauf = 0;
        // abhaengig davon, ob das "Endeereignis" vorhanden ist
        Ereignis lvEndeEreignis = EreignisHandler.getEndeEreignis(Vorgang.getSvMandant());
        if (lvEndeEreignis != null) {
            while (!lvEreignisVektor.hasEreignis(lvEndeEreignis)) 
            {
                Ereignis lvStopEreignis = EreignisHandler.getStopEreignis(Vorgang.getSvMandant());
                if (!lvEreignisVektor.hasEreignis(lvStopEreignis))
                {
                  System.out.println("Naechste Durchlauf...");
            	  if (!ivInExklusivModus)
            	  {
            		  // Mustererkennung aufrufen (Zeitintervall)
            		  Set<Aufgabe> lvFolgeAufgaben = lvEreignisVektor.doMusterErkennung();
            		  
            		  // Folgeaufgaben in die Aufgabenliste schreiben
            		  for (Aufgabe pvAufgabe:lvFolgeAufgaben)
            		  {
             			  ivAufgaben.add(pvAufgabe);
            		  }
            		  
            		  lvHelpAufgaben = new HashSet<Aufgabe>();
            		  for (Aufgabe pvAufgabe : this.ivAufgaben)
            		  {
            			  lvHelpAufgaben.add(pvAufgabe);
            		  }
            		  
            		  for (Aufgabe lvAufgabe : lvHelpAufgaben) 
            		  {
                		  // Aufgabe starten
                		  String lvAufgabenVektorID = lvDAO.insertAufgabenVektorReturnID(DatumUtilities.printDateAndTime(), null, this.getId(), Vorgang.getSvMandant().getId(), lvAufgabe.getId());
                		  VgAufgabe lvVgAufgabe = new VgAufgabe(this, lvAufgabe, lvAufgabenVektorID);
                		  lvVgAufgabe.start();
                		  ivAufgaben.remove(lvAufgabe);
                		  if (lvAufgabe.getAktivEreignis() != null)
                		  {
                			  Calendar lvCal = Calendar.getInstance();
                			  SimpleDateFormat lvSdf = new SimpleDateFormat();
                			  lvSdf.setTimeZone(lvCal.getTimeZone());
                			  lvSdf.applyPattern( "yyyy-MM-dd HH:mm:ss" ); 

                			  lvEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), this.getId(), Vorgang.getSvMandant().getId(), lvAufgabe.getAktivEreignis().getId());
                		  }
                		  if (lvAufgabe.getLoeschEreignis() != null)
                		  { 
                			  lvEreignisVektorDAO.deleteStopEreignisVektor(this.getId(), Vorgang.getSvMandant().getId(), lvAufgabe.getLoeschEreignis().getId());
                		  }
                		  if (lvAufgabe.isExklusiv())
                		  {
                			  ivVgAufgabeExklusivID = lvVgAufgabe.getAufgabenVektorID();
                    		  ivInExklusivModus = lvAufgabe.isExklusiv();
                    		  break; // For-Schleife verlassen, da exklusive Aufgabe gefunden wurde
                		  }
                	  }
            	  }
            	  else
            	  { 
            		  VgAufgabe lvHelpVgAufgabe = (lvDAO.findAufgabenByVorgangID(this)).get(ivVgAufgabeExklusivID);
            		  System.out.println(lvHelpVgAufgabe.getAufgabe().getBezeichnung() + " - Erledigt: " + lvHelpVgAufgabe.isErledigt());
            		  if (lvHelpVgAufgabe.isErledigt())
            		  {
            			  ivInExklusivModus = false;
            		  }
                  }
                  lvDurchlauf++;
                }
                else
                {
                    System.out.println("Stop Ereignis gesetzt...");
                }
                if (lvDurchlauf == 60)
                {
                    ivTaskPingDAO.insertZeitpunkt(this.getId(), Vorgang.getSvMandant().getId());
                    lvDurchlauf = 0; 
                }
                try
                {
                    Thread.sleep(((Long)StringKonverter.convertString2Long(svIntervallDurchlauf)).longValue() * 1000);
                }
                catch (Exception exp)
                {
                  System.out.println("Fehler beim Sleep des Durchlaufes...");   
                }
            }
        }  
    }
    
    /**
     * Laedt die unbedingten Aufgaben in den HashSet
     * @param pvMandant
     */
    public void loadUnbedingteAufgaben(Mandant pvMandant) {
        Set<Aufgabe> lvResult = new HashSet<Aufgabe>();
        System.out.println("pvMandant: " + pvMandant);
        AufgabeDAO lvDAO = new AufgabeDAO(pvMandant);
        Collection<Aufgabe> lvAufgaben = lvDAO.findUnbedingteProzessstarterByMandant(true, pvMandant.getId()).values();
        lvResult.addAll(lvAufgaben);
        
        
        lvAufgaben = lvDAO.findUnbedingteDateisuchenByMandant(true, pvMandant.getId()).values();
        lvResult.addAll(lvAufgaben);
        
        lvAufgaben = lvDAO.findUnbedingteTriggerByMandant(true, pvMandant.getId()).values();
        lvResult.addAll(lvAufgaben);
        
        this.ivAufgaben = lvResult;
    }
    
    /**
     * Liefert das Institut
     * @return 
     */
    public static String getSvInstitut()
    {
        return svInstitut;
    }

    /**
     * Liefert die Vorgang-Instanz
     * @return the svInstance
     */
    public static Vorgang getSvInstance() {
        return svInstance;
    }

    /**
     * Liefert den FilePingThread
     * @return 
     */
    public static FilePingThread getFilePingThread()
    {
        return svFilePingThread;
    }
    
    /**
     * Liefert die Intervallzeit fuer die Dateisuche
     * @return
     */
    public static String getIntervallDateisuche()
    {
    	return svIntervallDateisuche;
    }
    
    /**
     * Liefert die Intervallzeit fuer den Durchlauf
     * @return
     */
    public static String getIntervallDurchlauf()
    {
    	return svIntervallDurchlauf;
    }

    /**
     * Liefert die Intervallzeit fuer die Trigger
     * @return
     */
    public static String getIntervallTrigger()
    {
    	return svIntervallTrigger;
    }
    
    /**
     * Liefert den Mandanten
     * @return the mandant
     */
    public static Mandant getSvMandant() {
        return svMandant;
    }

    /**
     * 
     * @return
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * 
     * @param pvId
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * 
     * @return
     */
    public String getBuchungstag() {
        return this.ivBuchungstag;
    }

    /**
     * 
     * @param pvBuchungstag
     */
    public void setBuchungstag(String pvBuchungstag) {
        this.ivBuchungstag = pvBuchungstag;
    } 

    /**
     * Liefert die Vorgang-ID und den Buchungstag als String
     * @return Vorgang-ID : Buchungstag
     */
    public String toString() 
    {
        return this.getId() + " : " + this.getBuchungstag();
    }
    
    /**
     * 
     */
    private void save()
    {
        VorgangDAO lvDAO = new VorgangDAO(Vorgang.getSvMandant());
        lvDAO.insertVorgang(this.getId(), Vorgang.getSvMandant().getId(), this.getBuchungstag());
    }
    
    /**
     * 
     * @param pvVorgang
     * @return
     */
    public int compareTo(Vorgang pvVorgang) 
    {
        if(this.getId() != null && pvVorgang.getId() != null){
            return this.getId().compareTo(pvVorgang.getId());
        }

        return 0;
    }
    
    /**
     * 
     * @return
     */
    public boolean isRestartMode(){
        return this.ivIsRestartMode;
    }
    
    /**
     * 
     * @param pvBool
     */
    public void setRestartMode(boolean pvBool){
        this.ivIsRestartMode = pvBool;
    }
    
    /**
     * 
     */
    public void restart(){
        // neuen Vorgang mit diesem Buchungstag starten
        String lvId = "" + System.currentTimeMillis();
        Vorgang.startVorgang(lvId, this.getBuchungstag(), Vorgang.getSvMandant(), getSvInstitut());
    }
    
    /**
     * 
     */
    public void close(){
        // diesen Vorgang mit dem 'Ende'-Ereignis abschliessen.
        Ereignis lvEndeEreignis = EreignisHandler.getEndeEreignis(Vorgang.getSvMandant());
        EreignisVektorDAO lvDAO = new EreignisVektorDAO(Vorgang.getSvMandant());
        lvDAO.insertEreignisVektor(null, this.getId(), Vorgang.getSvMandant().getId(), lvEndeEreignis.getId());
        
    }
    
    /**
     * 
     * @return
     */
    public String getLastLebenszeichen(){
        String lvResult = "";
        TaskPingDAO lvDAO = new TaskPingDAO(svMandant);
        lvResult = lvDAO.getLetztenZeitpunkt(this.getId(), Vorgang.getSvMandant().getId());
        return lvResult;
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<String> getAllLebenszeichen(){
        ArrayList<String> lvResult = new ArrayList<String>();
        TaskPingDAO lvDAO = new TaskPingDAO(svMandant);
        lvResult = lvDAO.getZeitpunkte(this.getId(), Vorgang.getSvMandant().getId());
        return lvResult;
    }
}
