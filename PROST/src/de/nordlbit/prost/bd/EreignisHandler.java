/**
 * 
 */
package de.nordlbit.prost.bd;

import java.util.HashMap;
import java.util.Iterator;

import de.nordlbit.prost.dao.ereignis.EreignisDAO;

/**
 *
 * @author frankew
 */
public class EreignisHandler {
    private static HashMap<Mandant, EreignisHandler> svAllInstances = new HashMap<Mandant, EreignisHandler>();
    private  HashMap<String, Ereignis> ivEreignisse;
    private Mandant ivMandant;
    
   
    public static EreignisHandler getInstance(Mandant pvMandant){
        if(EreignisHandler.svAllInstances.get(pvMandant) == null){
            return new EreignisHandler(pvMandant);
        } else {
            return EreignisHandler.svAllInstances.get(pvMandant);
        }
    }
    
    private EreignisHandler(Mandant pvMandant){
        super();
        this.ivEreignisse = new HashMap<String, Ereignis>();
        this.ivMandant = pvMandant;
        this.reload();
        register(this);
    }
    
   
    
    public void setMandant(Mandant pvMandant){
        this.ivMandant = pvMandant;
    }
    
    public Mandant getMandant(){
        return this.ivMandant;
    }
    
    public HashMap<String, Ereignis> getEreignisse(){
        return this.ivEreignisse;
    }
    
    
    public HashMap<String, Ereignis> getMuster() {
        
        EreignisDAO lvDAO = new EreignisDAO(this.getMandant());
        ivEreignisse = lvDAO.findEreignisseByMandanten(this.getMandant().getId());

        return ivEreignisse;

    }
    
    private static void register(EreignisHandler pvHandler){
        if(svAllInstances == null){
            svAllInstances = new HashMap<Mandant, EreignisHandler>();
        }
        svAllInstances.put(pvHandler.getMandant(), pvHandler);
    }
    
    public void reload(){
        EreignisDAO lvDAO = new EreignisDAO(this.getMandant());
        System.out.println("this.getMandant(): " + this.getMandant());
        HashMap<String, Ereignis> lvEreignisse = lvDAO.findEreignisseByMandanten(this.getMandant().getId());
        if(this.ivEreignisse == null){
            this.ivEreignisse = new HashMap<String, Ereignis>();
        }
        
        if(lvEreignisse != null) {
            for (Iterator<Ereignis> it = lvEreignisse.values().iterator(); it.hasNext();) {
                Ereignis pvEreignis = (Ereignis)it.next();
                if(this.ivEreignisse.get(pvEreignis.getId()) == null){
                    this.ivEreignisse.put(pvEreignis.getId(), pvEreignis);
                }
            }
        }
    }
    
    public void remove(Ereignis pvEreignis){
        this.ivEreignisse.remove(pvEreignis.getId());
    }
    
    public static Ereignis getEndeEreignis(Mandant pvMandant){
        Ereignis lvEndeEreignis = null;
        for(Ereignis pvEreignis : (EreignisHandler.getInstance(pvMandant)).getEreignisse().values()) {
            if (pvEreignis.isEndeEreignis()){
                lvEndeEreignis = pvEreignis;
                break;
            }
        }
        return lvEndeEreignis;
    }
    
    public static Ereignis getStopEreignis(Mandant pvMandant)
    {
        Ereignis lvStopEreignis = null;
        for(Ereignis pvEreignis : (EreignisHandler.getInstance(pvMandant)).getEreignisse().values()) {
            if (pvEreignis.isStopEreignis()){
                lvStopEreignis = pvEreignis;
                break;
            }
        }
        return lvStopEreignis;
    }

}
