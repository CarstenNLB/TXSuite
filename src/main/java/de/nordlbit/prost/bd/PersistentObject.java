/**
 * 
 */
package de.nordlbit.prost.bd;

import java.sql.Timestamp;

/**
 *
 * @author frankew
 */
public abstract class PersistentObject 
{
    private Timestamp ivGueltigVonDatum;
    private Timestamp ivGueltigBisDatum; 
    private Timestamp ivAenderungsDatum;
    private String ivUser;
    private Object ivOldValueObjectCopy;

    public void setGueltigVonDatum(Timestamp pvTimestamp){
        this.ivGueltigVonDatum = pvTimestamp;
    }
    
    public Timestamp getGueltigVonDatum(){
        return this.ivGueltigVonDatum;
    }
    
    public void setGueltigBisDatum(Timestamp pvTimestamp){
        this.ivGueltigBisDatum = pvTimestamp;
    }
    
    public Timestamp getGueltigBisDatum(){
        return this.ivGueltigBisDatum;
    }
    
    public void setAenderungsDatum(Timestamp pvTimestamp){
        this.ivAenderungsDatum = pvTimestamp;
    }
    
    public Timestamp getAenderungsDatum(){
        return this.ivAenderungsDatum;
    }
    
    public void setUser(String pvUser){
        this.ivUser = pvUser;
    }
    
    public String getUser(){
        return this.ivUser;
    }
    
    public void setOldValueObjectCopy(Object pvObject){
        this.ivOldValueObjectCopy = pvObject;
    }
    
    public Object getOldValueObjectCopy(){
        return this.ivOldValueObjectCopy;
    }
}
