/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import de.nordlbit.prost.FilePing.FilePingTask;
import de.nordlbit.prost.Utilities.DatumUtilities;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.trigger.TriggerThread;

/**
 *
 * @author frankew
 */
public class VgAufgabe {
    private Vorgang ivVorgang;
    private Aufgabe ivAufgabe;
    private boolean ivInArbeit;
    private boolean ivErledigt;
    private BigDecimal ivAufgabenFortschritt;
    private Timestamp ivAnfangZeitpunkt;
    private Timestamp ivEndeZeitpunkt;  
    private Mandant ivMandant;
    private String ivAufgabenVektorID;
    
    public VgAufgabe(Vorgang pvVorgang, Aufgabe pvAufgabe, String pvAufgabenVektorID){
        super();
        this.ivVorgang = pvVorgang;
        this.ivAufgabe = pvAufgabe;
        this.ivMandant = pvAufgabe.getMandant();
        this.ivAufgabenVektorID = pvAufgabenVektorID;
    }

    public VgAufgabe(Vorgang pvVorgang, Aufgabe pvAufgabe, Mandant pvMandant, boolean pvInArbeit, boolean pvErledigt, Timestamp pvAnfangZeitpunkt, Timestamp pvEndeZeitpunkt){
        super();
        this.ivVorgang = pvVorgang;
        this.ivAufgabe = pvAufgabe;
        this.ivMandant = pvMandant;
        this.ivInArbeit = pvInArbeit;
        this.ivErledigt = pvErledigt;
        this.ivAnfangZeitpunkt = pvAnfangZeitpunkt;
        this.ivEndeZeitpunkt = pvEndeZeitpunkt;
    }
    
    public Mandant getMandant(){
        return this.ivMandant;
    }
    
    public void setMandant(Mandant pvMandant){
        this.ivMandant = pvMandant;
    }
    
    
    public Vorgang getVorgang(){
        return this.ivVorgang;
    }
    
    public void setVorgang(Vorgang pvVorgang){
        this.ivVorgang = pvVorgang;
    }

    public Aufgabe getAufgabe(){
        return this.ivAufgabe;
    }
    
    public void setAufgabe(Aufgabe pvAufgabe){
        this.ivAufgabe = pvAufgabe;
    }
    
    public void start() 
    {
    	// TriggerThreads starten
        if (this.getAufgabe() instanceof Trigger)
        {
            (new TriggerThread(this.getVorgang().getId(), this.getMandant().getId(), this.getAufgabe().getFertigstellungsEreignis().getId(), this.getAufgabenVektorID(), ((Trigger)this.getAufgabe()).getAusloesezeitpunkt(), ((Trigger)this.getAufgabe()).getDateisucheAufgabenID())).start();
        }
        
        // DateisucheThreads starten
        if (this.getAufgabe() instanceof Dateisuche)
        {
            try 
            {
                String lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + ((Dateisuche)this.getAufgabe()).getDateiname();

                if (((Dateisuche)this.getAufgabe()).getQualifier() != null)
                {
                    if (((Dateisuche)this.getAufgabe()).getDateiname().contains("|"))
                    {
                        if (((Dateisuche)this.getAufgabe()).getQualifier().equals("_jjjjmmdd.xml"))
                        {
                            lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + ((Dateisuche)this.getAufgabe()).getDateiname().substring(0, ((Dateisuche)this.getAufgabe()).getDateiname().indexOf('|')) + "_" + this.getBuchungstag().substring(6) + this.getBuchungstag().substring(3,5) + this.getBuchungstag().substring(0,2)+ ".xml";
                        }
                        if (((Dateisuche)this.getAufgabe()).getQualifier().equals("_jjjjmmdd.csv"))
                        {
                            lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + ((Dateisuche)this.getAufgabe()).getDateiname().substring(0, ((Dateisuche)this.getAufgabe()).getDateiname().indexOf('|')) + "_" + this.getBuchungstag().substring(6) + this.getBuchungstag().substring(3,5) + this.getBuchungstag().substring(0,2)+ ".csv";
                        }
                        if (((Dateisuche)this.getAufgabe()).getQualifier().equals(".Djjmmdd"))
                        {
                            lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + ((Dateisuche)this.getAufgabe()).getDateiname().substring(0, ((Dateisuche)this.getAufgabe()).getDateiname().indexOf('|')) + ".D" + this.getBuchungstag().substring(8) + this.getBuchungstag().substring(3,5) + this.getBuchungstag().substring(0,2);
                        }
                        if (((Dateisuche)this.getAufgabe()).getQualifier().equals("jjjjmmdd_"))
                        {
                            lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + this.getBuchungstag().substring(6) + this.getBuchungstag().substring(3,5) + this.getBuchungstag().substring(0,2) + "_" + ((Dateisuche)this.getAufgabe()).getDateiname().substring(0, ((Dateisuche)this.getAufgabe()).getDateiname().indexOf('|'));
                        }
                    }
                    else
                    {
                      if (((Dateisuche)this.getAufgabe()).getQualifier().equals("_jjjjmmdd.xml"))
                      {
                        lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + ((Dateisuche)this.getAufgabe()).getDateiname() + "_" + this.getBuchungstag().substring(6) + this.getBuchungstag().substring(3,5) + this.getBuchungstag().substring(0,2)+ ".xml";
                      }
                      if (((Dateisuche)this.getAufgabe()).getQualifier().equals("_jjjjmmdd.csv"))
                      {
                        lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + ((Dateisuche)this.getAufgabe()).getDateiname() + "_" + this.getBuchungstag().substring(6) + this.getBuchungstag().substring(3,5) + this.getBuchungstag().substring(0,2)+ ".csv";
                      }
                      if (((Dateisuche)this.getAufgabe()).getQualifier().equals(".Djjmmdd"))
                      {
                        lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + ((Dateisuche)this.getAufgabe()).getDateiname() + ".D" + this.getBuchungstag().substring(8) + this.getBuchungstag().substring(3,5) + this.getBuchungstag().substring(0,2);
                      }
                      if (((Dateisuche)this.getAufgabe()).getQualifier().equals("jjjjmmdd_"))
                      {
                        lvDateiname = ((Dateisuche)this.getAufgabe()).getDateiPfad() + this.getBuchungstag().substring(6) + this.getBuchungstag().substring(3,5) + this.getBuchungstag().substring(0,2) + "_" + ((Dateisuche)this.getAufgabe()).getDateiname();
                      }
                    }
                }

                System.out.println("Parameter: " + lvDateiname + " " + this.getVorgang().getId() + " " + this.getMandant().getId() + " " + this.getAufgabe().getFertigstellungsEreignis().getId() + " " + this.getAufgabenVektorID());
                FilePingTask helpFilePingTask = new FilePingTask(lvDateiname, this.getVorgang().getId(), this.getMandant().getId(), this.getAufgabe().getFertigstellungsEreignis().getId(), this.getAufgabenVektorID(), this.getMandant());
                Vorgang.getFilePingThread().addFilePingTask(helpFilePingTask);
                // CT 19.12.2014
                //(new FilePingThread(lvDateiname, this.getVorgang().getId(), this.getMandant().getId(), this.getAufgabe().getFertigstellungsEreignis().getId(), this.getAufgabenVektorID(), this.getMandant().getInstitutsId())).start();
                // CT 19.12.2014
                //new ProcessBuilder("D:\\FilePing\\FilePing.bat", lvDateiname, this.getId(), this.getMandant().getId(), this.getAufgabe().getFertigstellungsEreignis().getId(), lvAufgabenVektorID).start();
                //Process p = Runtime.getRuntime().exec("D:\\FilePing\\FilePing.bat " + lvDateiname + " " + this.getId() + " " + this.getMandant().getId() + " " + this.getAufgabe().getFertigstellungsEreignis().getId() + " " + lvAufgabenVektorID);
                //Thread.sleep(1000);
                //System.out.println("Exit-Value: " + p.exitValue());
            }
            catch (Exception exp)
            {
                System.out.println("Konnte Dateisuche nicht starten...");
            }
        }
        // ProzessStarter starten
        if (this.getAufgabe() instanceof ProzessStarter)
        {
            try 
            {
                System.out.println("ProzessStarter...");
                //new ProcessBuilder(((ProzessStarter)this.getAufgabe()).getPfad() + ((ProzessStarter)this.getAufgabe()).getProzessname(), this.getVorgang().getId(), this.getMandant().getId(), this.getAufgabe().getFertigstellungsEreignis().getId(), this.getAufgabenVektorID()).start();
                ProcessBuilder builder = new ProcessBuilder(((ProzessStarter)this.getAufgabe()).getPfad() + ((ProzessStarter)this.getAufgabe()).getProzessname(), this.getVorgang().getId(), this.getMandant().getId(), this.getAufgabe().getFertigstellungsEreignis().getId(), this.getAufgabenVektorID(), DatumUtilities.changeDate(this.getBuchungstag()));
                Process process = builder.start();
                String output = loadStream(process.getInputStream());
                String error  = loadStream(process.getErrorStream());
                int rc = process.waitFor();
                System.out.println("Process ended with rc=" + rc);
                System.out.println("\nStandard Output:\n");
                System.out.println(output);
                System.out.println("\nStandard Error:\n");
                System.out.println(error);
         
                //Process p = Runtime.getRuntime().exec(( + " " +  + " " +  + " " +  + " " + );               
                
                //(new ProzessStarterThread((ProzessStarter)this.getAufgabe(), this.getVorgang().getId(), this.getMandant(), this.getAufgabe().getFertigstellungsEreignis().getId(), this.getAufgabenVektorID())).start();
            }
            catch (Exception exp)
            {
                System.out.println("Konnte ProzessStarter nicht starten...");
            }
        }
    }
    
    private String loadStream(InputStream s) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(s));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line=br.readLine()) != null)
            sb.append(line).append("\n");
        return sb.toString();
    }
    
    public void enableRestart() 
    {

        if(this.isInArbeit() && !this.isErledigt() && this.getAnfangZeitpunkt() != null){
            // Flag 'isInArbeit' in der DB auf false setzen
            this.setInArbeit(false);
            // AngangZeitpunkt auf null setzen
            this.setAnfangZeitpunkt(null);
            AufgabenVektorDAO lvDAO = new AufgabenVektorDAO(this.getMandant());
            lvDAO.updateAufgabenVektor(this.getAufgabenVektorID(), this.getAnfangZeitpunkt(), this.getEndeZeitpunkt(), this.getVorgang().getId(), this.getMandant().getId(), this.getAufgabe().getId());
        }

    }
    
        /**
     * @return the inArbeit
     */
    public boolean isInArbeit() {
        if(this.isErledigt()){
            return false;
        }
        return this.ivInArbeit;
    }

    /**
     * @param pvInArbeit the inArbeit to set
     */
    public void setInArbeit(boolean pvInArbeit) {
        this.ivInArbeit = pvInArbeit;
    }

    /**
     * @return the erledigt
     */
    public boolean isErledigt() {
        return this.ivErledigt;
    }

    /**
     * @param pvErledigt the erledigt to set
     */
    public void setErledigt(boolean pvErledigt) {
        this.ivErledigt = pvErledigt;
    }
    
    public String toString() {
        return this.getAufgabe().getId() + " unbedingt: " + this.getAufgabe().isUnbedingteAufgabe() + " isErledigt: " + this.isErledigt();
    }
    
        /**
     * @return the aufgabenFortschritt
     */
    public BigDecimal getAufgabenFortschritt() {
        return this.ivAufgabenFortschritt;
    }

    /**
     * @param pvAufgabenFortschritt 
     */
    public void setAufgabenFortschritt(BigDecimal pvAufgabenFortschritt) {
        this.ivAufgabenFortschritt = pvAufgabenFortschritt;
    }
    
    public void setAnfangZeitpunkt(Timestamp pvTimestamp){
        this.ivAnfangZeitpunkt = pvTimestamp;
    }

    public Timestamp getAnfangZeitpunkt(){
        return this.ivAnfangZeitpunkt;
    }
    
    public void setEndeZeitpunkt(Timestamp pvTimestamp){
        this.ivEndeZeitpunkt = pvTimestamp;
    }
    
    public Timestamp getEndeZeitpunkt(){
        return this.ivEndeZeitpunkt;
    }
    
    public Timestamp seit(){
        if(this.getEndeZeitpunkt() != null){
            return this.getEndeZeitpunkt();
        } else {
            return this.getAnfangZeitpunkt();
        }
    }
    
    public boolean isZeitfensterAbgelaufen(){
        boolean lvResult = false;
        
        if(this.getAufgabe().getZeitfensterVon() != null && this.getAufgabe().getZeitfensterBis() != null){
            // Zeitfenster in 
            GregorianCalendar lvZeitfensterCal = new GregorianCalendar();
            // Buchungstag dd.mm.jjjj
            String lvBuchungstag = this.getVorgang().getBuchungstag();
            int lvDay = Integer.parseInt(lvBuchungstag.substring(0, 2));
            int lvMonth = Integer.parseInt(lvBuchungstag.substring(3, 5));
            int lvYear = Integer.parseInt(lvBuchungstag.substring(6));
            // ZeitfensterBis hh:mm
            String lvZeitfensterBis = this.getAufgabe().getZeitfensterBis();
            int lvHour = Integer.parseInt(lvZeitfensterBis.substring(0, 2));
            int lvMin = Integer.parseInt(lvZeitfensterBis.substring(3));
            lvZeitfensterCal.set(lvYear, lvMonth - 1, lvDay, lvHour, lvMin);
            lvZeitfensterCal.add(GregorianCalendar.DAY_OF_YEAR, 1);
            System.out.println(lvZeitfensterCal.getTime());
            
            if(!this.isErledigt() && lvZeitfensterCal.before(new GregorianCalendar())){
                lvResult = true;
            }
        }
        
        return lvResult;
    }
    
    public String getBuchungstag() {
        return this.getVorgang().getBuchungstag();
    }
    
    public String getAufgabenVektorID(){
        return this.ivAufgabenVektorID;
    }
    
}
