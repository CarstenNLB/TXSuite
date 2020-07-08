/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

import java.util.HashMap;
import java.util.Iterator;

import de.nordlbit.prost.dao.ausloesemuster.AusloesemusterDAO;


/**
 *
 * @author frankew
 */
public class AusloesemusterHandler {
    private static HashMap<Mandant, AusloesemusterHandler> svAllInstances = new HashMap<Mandant, AusloesemusterHandler>();
    private  HashMap<String, Ausloesemuster> ivAusloesemuster;
    private Mandant ivMandant;
    
   
    public static AusloesemusterHandler getInstance(Mandant pvMandant){
        if(AusloesemusterHandler.svAllInstances.get(pvMandant) == null){
            return new AusloesemusterHandler(pvMandant);
        } else {
            return AusloesemusterHandler.svAllInstances.get(pvMandant);
        }
    }
    
    private AusloesemusterHandler(Mandant pvMandant){
        super();
        this.ivAusloesemuster = new HashMap<String, Ausloesemuster>();
        this.ivMandant = pvMandant;
        register(this);
    }
    
    public static HashMap<String, Ausloesemuster> getMuster(Mandant pvMandant) {
        return (AusloesemusterHandler.getInstance(pvMandant)).getMuster();
    }
    
    
    public void setMandant(Mandant pvMandant){
        this.ivMandant = pvMandant;
    }
    
    public Mandant getMandant(){
        return this.ivMandant;
    }
    
    public HashMap<String, Ausloesemuster> getMuster() {
        
        AusloesemusterDAO lvDAO = new AusloesemusterDAO(this.getMandant()); 
        ivAusloesemuster = lvDAO.findAusloesemusterByMandant(this.getMandant().getId());

        return ivAusloesemuster;

    }
    
    private static void register(AusloesemusterHandler pvHandler){
        if(svAllInstances == null){
            svAllInstances = new HashMap<Mandant, AusloesemusterHandler>();
        }
        svAllInstances.put(pvHandler.getMandant(), pvHandler);
    }
    
    public void reload(){
        AusloesemusterDAO lvDAO = new AusloesemusterDAO(this.getMandant());
        // TODO Bezeichung mit laden
        
        HashMap<String, Ausloesemuster> lvAusloesemuster = lvDAO.findAusloesemusterByMandant(this.getMandant().getId());
        if(this.ivAusloesemuster == null){
            this.ivAusloesemuster = new HashMap<String, Ausloesemuster>();
        }
        
        if(lvAusloesemuster != null) {
            for (Iterator<Ausloesemuster> it = lvAusloesemuster.values().iterator(); it.hasNext();) {
                Ausloesemuster pvAusloesemuster = (Ausloesemuster)it.next();
                if(this.ivAusloesemuster.get(pvAusloesemuster.getId()) == null){
                    this.ivAusloesemuster.put(pvAusloesemuster.getId(), pvAusloesemuster);
                }
            }
        }
    }
    
    public void remove(Ausloesemuster pvMuster){
        this.ivAusloesemuster.remove(pvMuster.getId());
    }
    
}
