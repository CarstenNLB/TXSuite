/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

import de.nordlbit.prost.dao.aufgabe.AufgabeDAO;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author frankew
 */
public class AufgabeHandler {
        private static HashMap<Mandant, AufgabeHandler> svAllInstances = new HashMap<Mandant, AufgabeHandler>();
        //private static HashMap<String, Aufgabe> ivAllAufgaben;
        private HashMap<String, Aufgabe> ivDateisuche;
        private HashMap<String, Aufgabe> ivProzessStarter;
        private HashMap<String, Aufgabe> ivTrigger;
        private Mandant ivMandant;
    
        /*
    public static HashMap<String, Aufgabe> getAllAufgaben() {

        if (AufgabeHandler.ivAllAufgaben != null) {
            return ivAllAufgaben;
        }

        //Dateisuche.reload();
        
        return ivAllAufgaben;
    }
    */
    public HashMap<String, Aufgabe> getDateisuche() {
        return ivDateisuche;
    }
    
    public static HashMap<String, Aufgabe> getDateisuche(Mandant pvMandant) {
        return AufgabeHandler.getInstance(pvMandant).getDateisuche();
    }
    
    
    public HashMap<String, Aufgabe> getProzessStarter() {
        return ivProzessStarter;
    }
    
    public static HashMap<String, Aufgabe> getProzessStarter(Mandant pvMandant) {
        return AufgabeHandler.getInstance(pvMandant).getProzessStarter();
    }
    
    public HashMap<String, Aufgabe> getTrigger() {
        return ivTrigger;
    }
    
    public static HashMap<String, Aufgabe> getTrigger(Mandant pvMandant) {
        return AufgabeHandler.getInstance(pvMandant).getTrigger();
    }
     
     
     
    public static HashSet<Aufgabe> getUnbedingteAufgaben(Mandant lvMandant){
        HashSet<Aufgabe> lvResultSet = new HashSet<Aufgabe>();
        for(Aufgabe pvAufgabe : AufgabeHandler.getInstance(lvMandant).getDateisuche().values()){
            if(pvAufgabe.isUnbedingteAufgabe()){
                lvResultSet.add(pvAufgabe);
            }
        }
        for(Aufgabe pvAufgabe : AufgabeHandler.getInstance(lvMandant).getProzessStarter().values()){
            if(pvAufgabe.isUnbedingteAufgabe()){
                lvResultSet.add(pvAufgabe);
            }
        }
        for(Aufgabe pvAufgabe : AufgabeHandler.getInstance(lvMandant).getTrigger().values()){
            if(pvAufgabe.isUnbedingteAufgabe()){
                lvResultSet.add(pvAufgabe);
            }
        }        
        
        return lvResultSet;
    }
    
    private AufgabeHandler(Mandant pvMandant){
        super();
        this.ivDateisuche = new HashMap<String, Aufgabe>();
        this.ivProzessStarter = new HashMap<String, Aufgabe>();
        this.ivTrigger = new HashMap<String, Aufgabe>();
        this.ivMandant = pvMandant;
        this.reloadDateisuche();
        this.reloadProzessStarter();
        this.reloadTrigger();
        AufgabeHandler.register(this);
    }
    

    public void reloadDateisuche(){
        AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant());
        HashMap<String, Aufgabe> lvAufgaben = lvDAO.getDateisuchen();
        if(this.ivDateisuche == null){
            this.ivDateisuche = new HashMap<String, Aufgabe>();
        }
        
        if(lvAufgaben != null) {
            for (Iterator<Aufgabe> it = lvAufgaben.values().iterator(); it.hasNext();) {
                Dateisuche pvAufgabe = (Dateisuche)it.next();
                if(this.ivDateisuche.get(pvAufgabe.getId()) == null && this.getMandant().getId().equals(pvAufgabe.getMandant().getId())){
                    this.ivDateisuche.put(pvAufgabe.getId(), pvAufgabe);
                }
            }
        }
    }
    
    public void reloadProzessStarter(){
        AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant()); 
        HashMap<String, Aufgabe> lvAufgaben = lvDAO.getProzessstarter();
        if(this.ivProzessStarter == null){
            this.ivProzessStarter = new HashMap<String, Aufgabe>();
        }
        
        if(lvAufgaben != null) {
            for (Iterator<Aufgabe> it = lvAufgaben.values().iterator(); it.hasNext();) {
                ProzessStarter pvAufgabe = (ProzessStarter)it.next();
                if(this.ivProzessStarter.get(pvAufgabe.getId()) == null && this.getMandant().getId().equals(pvAufgabe.getMandant().getId())){
                    this.ivProzessStarter.put(pvAufgabe.getId(), pvAufgabe);
                }
            }
        }
    }

    public void reloadTrigger(){
        AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant()); 
        HashMap<String, Aufgabe> lvAufgaben = lvDAO.getTrigger();
        if(this.ivTrigger == null){
            this.ivTrigger = new HashMap<String, Aufgabe>();
        }
        
        if(lvAufgaben != null) {
            for (Iterator<Aufgabe> it = lvAufgaben.values().iterator(); it.hasNext();) {
                Trigger pvAufgabe = (Trigger)it.next();
                if(this.ivTrigger.get(pvAufgabe.getId()) == null && this.getMandant().getId().equals(pvAufgabe.getMandant().getId())){
                    this.ivTrigger.put(pvAufgabe.getId(), pvAufgabe);
                }
            }
        }
    }
    
    public void setMandant(Mandant pvMandant){
        this.ivMandant = pvMandant;
    }
    
    public Mandant getMandant(){
        return this.ivMandant;
    }
    
    public static AufgabeHandler getInstance(Mandant pvMandant){
        if(AufgabeHandler.svAllInstances.get(pvMandant) == null){
            return new AufgabeHandler(pvMandant);
        } else {
            return AufgabeHandler.svAllInstances.get(pvMandant);
        }
    }
    
    private static void register(AufgabeHandler pvHandler){
        if(svAllInstances == null){
            svAllInstances = new HashMap<Mandant, AufgabeHandler>();
        }
        svAllInstances.put(pvHandler.getMandant(), pvHandler);
    }
    
    public void remove(Aufgabe pvAufgabe){
        if(pvAufgabe instanceof Dateisuche){
            this.ivDateisuche.remove(pvAufgabe.getId());
        }
        else if(pvAufgabe instanceof ProzessStarter){
            this.ivProzessStarter.remove(pvAufgabe.getId());            
        }
        else if(pvAufgabe instanceof Trigger){
            this.ivTrigger.remove(pvAufgabe.getId());            
        }

    }
    
}
