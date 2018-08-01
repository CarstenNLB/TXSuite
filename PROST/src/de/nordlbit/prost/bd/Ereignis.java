/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.bd;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.nordlbit.prost.dao.ereignis.EreignisDAO;


/**
 * @author frankew
 *
 */
public class Ereignis extends PersistentObject implements Comparable<Ereignis>, Protokoll, Cloneable 
{

    private String ivId;
    private String ivName;
    private Mandant ivMandant;
    private boolean ivEndeEreignis = false;
    private boolean ivStopEreignis = false;

    /**
     * Konstruktor
     * @param pvId
     * @param pvName
     * @param pvMandant 
     */
    public Ereignis(String pvId, String pvName, Mandant pvMandant) 
    {
    	super();
        this.ivId = pvId;
        this.ivName = pvName;
        this.ivMandant = pvMandant;
    }
    
    /**
     * Konstruktor
     * @param pvId
     * @param pvName
     * @param pvMandant
     * @param pvEndeEreignis
     * @param pvStopEreignis
     */
    public Ereignis(String pvId, String pvName, Mandant pvMandant, boolean pvEndeEreignis, boolean pvStopEreignis) {
        super();
        this.ivId = pvId;
        this.ivName = pvName;
        this.ivMandant = pvMandant;
        this.ivEndeEreignis = pvEndeEreignis;
        this.ivStopEreignis = pvStopEreignis;
        try {
            this.setOldValueObjectCopy(this.clone());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Ereignis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.ivName;
    }

    /**
     * @param pvName the name to set
     */
    public void setName(String pvName) {
        this.ivName = pvName;
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    public String toString() {
        return this.getName();
    }

    /**
     * @return the mandant
     */
    public Mandant getMandant() {
        return this.ivMandant;
    }

    /**
     * @param pvMandant the mandant to set
     */
    public void setMandant(Mandant pvMandant) {
        this.ivMandant = pvMandant;
    }
    
    public boolean save(){
        // es darf nur ein Endeergeignis geben!
        if(this.isEndeEreignis()){
            // das alte Endeergnis suchen und das Flag dort zuruecksetzen.
            for(Ereignis pvEreignis : EreignisHandler.getInstance(this.getMandant()).getEreignisse().values()){
                if(!pvEreignis.getId().equals(this.getId()) && pvEreignis.isEndeEreignis()){
                    pvEreignis.setEndeEreignis(false);
                    pvEreignis.save();
                    break;
                }
            }
        }
        
        // es darf nur ein Stop-Ereignis geben!
        if(this.isStopEreignis()){
            // das alte Stop-Ereignis suchen und das Flag dort zuruecksetzen.
            for(Ereignis pvEreignis : EreignisHandler.getInstance(this.getMandant()).getEreignisse().values()){
                if(!pvEreignis.getId().equals(this.getId()) && pvEreignis.isStopEreignis()){
                    pvEreignis.setStopEreignis(false);
                    pvEreignis.save();
                    break;
                }
            }
        }
        
        EreignisDAO lvDAO = new EreignisDAO(this.getMandant());         
        if (this.getId() == null || this.getId().length() == 0){
            lvDAO.insertEreignis(this);
            EreignisHandler.getInstance(this.getMandant()).reload();
        } else {
            lvDAO.updateEreignis(this);
        }
        return true;
    }
    
    public boolean delete(){
        EreignisDAO lvDAO = new EreignisDAO(this.getMandant()); 
        lvDAO.deleteEreignis(this);
        EreignisHandler.getInstance(this.getMandant()).remove(this);
        return true;  
    }
    
    public boolean isEndeEreignis(){
       return this.ivEndeEreignis;
    }

    public void setEndeEreignis(boolean pvBoolean){
        this.ivEndeEreignis = pvBoolean;
    }
    
    public boolean isStopEreignis(){
       return this.ivStopEreignis;
    }

    public void setStopEreignis(boolean pvBoolean){
        this.ivStopEreignis = pvBoolean;
    }

    /**
     * 
     */
    public int compareTo(Ereignis pvEreignis) 
    {
        //Ereignis lvVergleichObj = (Ereignis)o;
        return (this.getName().compareTo(pvEreignis.getName()));
    }
    
    /**
     * 
     */
    public String getProtokollEintrag(int pvProtokollEintragFormat, String pvProtokollEintragTyp) {
        String pvEintrag = "";
        Ereignis lvCompareObject = (Ereignis)this.getOldValueObjectCopy();
        if(pvProtokollEintragFormat == Protokoll.STRING){
            if(pvProtokollEintragTyp.equalsIgnoreCase( Protokoll.UPDATE)){
                String lvOldValue = lvCompareObject.getName() == null ? "-" : lvCompareObject.getName().equals(this.getName()) ? ""  : (lvCompareObject.getId() + "-" + lvCompareObject.getName());
                pvEintrag = pvEintrag + "Name: " + this.getName() + "(" + lvOldValue  + ")";
                
                lvOldValue = ("" + lvCompareObject.isEndeEreignis()).equals("" + this.isEndeEreignis()) ? ""  : ("" + lvCompareObject.isEndeEreignis());                
                pvEintrag = pvEintrag + "EndeEreignis: " + this.isEndeEreignis() + "(" + lvOldValue + ")";
                
                lvOldValue = ("" + lvCompareObject.isStopEreignis()).equals("" + this.isStopEreignis()) ? ""  : ("" + lvCompareObject.isStopEreignis());    
                pvEintrag = pvEintrag + "StopEreignis: " + this.isStopEreignis() + "(" + lvOldValue + ")";
            } else { // INSERT, DELETE
                pvEintrag = pvEintrag + " Name: " + this.getName();
                pvEintrag = pvEintrag + " EndeEreignis: " + this.isEndeEreignis();
                pvEintrag = pvEintrag + " StopEreignis: " + this.isStopEreignis();
            }            
            return pvEintrag;
        }
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
        
    /*
    @Override
    public Ereignis clone(){
        try
        {
        return (Ereignis) super.clone();
        }
        catch ( CloneNotSupportedException e ) {
        // Kann eigentlich nicht passieren, da Cloneable
        throw new InternalError();
        }
    }
    */ 
}
