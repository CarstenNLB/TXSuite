/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

import java.sql.Timestamp;

/**
 *
 * @author frankew
 */
public class ProtokollEintrag {
    
    String ivId;
    String ivBenutzer;
    String ivAktion;
    String ivObjectID;
    String ivObjectClass;
    String ivDaten;
    Mandant ivMandant;
    Timestamp ivAenderungszeitpunkt;
           
    
    
    public ProtokollEintrag(String pvId, String pvBenutzer, String pvAktion, String pvObjectID, String pvObjectClass, String pvDaten, Mandant pvMandant, Timestamp pvAenderungszeitpunkt){
        this.ivId = pvId;
        this.ivBenutzer = pvBenutzer;
        this.ivAktion = pvAktion;
        this.ivObjectID = pvObjectID;
        this.ivObjectClass = pvObjectClass;
        this.ivDaten = pvDaten;
        this.ivMandant = pvMandant;
        this.ivAenderungszeitpunkt = pvAenderungszeitpunkt; 
    }
    
    public void setId(String pvId){
        this.ivId = pvId;
    }
    
    public String getId(){
        return this.ivId;
    }
    
    public void setBenutzer(String pvBenutzer){
        this.ivBenutzer = pvBenutzer;
    }
    
    public String getBenutzer(){
        return this.ivBenutzer;
    }
    
    public void setAktion(String pvAktion){
        this.ivAktion = pvAktion;
    }
    
    public String getAktion(){
        return this.ivAktion;
    }
    
    public void setObjectID(String pvObjectID){
        this.ivObjectID = pvObjectID;
    }
    
    public String getObjectID(){
        return this.ivObjectID;
    }
    
    public void setObjectClass(String pvObjectClass){
        this.ivObjectClass = pvObjectClass;
    }
    
    public String getObjectClass(){
        return this.ivObjectClass;
    }
    
    public void setDaten(String pvDaten){
        this.ivDaten = pvDaten;
    }
    
    public String getDaten(){
        return this.ivDaten;
    }
    
    public void setMandant(Mandant pvMandant){
        this.ivMandant = pvMandant;
    }
    
    public Mandant getMandant(){
        return this.ivMandant;
    }
    
    public void setAenderungszeitpunkt(Timestamp pvAenderungszeitpunkt){
        this.ivAenderungszeitpunkt = pvAenderungszeitpunkt;
    }
    
    public Timestamp getAenderungszeitpunkt(){
        return this.ivAenderungszeitpunkt;
    }
    
}
