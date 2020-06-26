/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.nordlbit.prost.dao.aufgabe.AufgabeDAO;

/**
 *
 * @author frankew
 */
public class Trigger extends Aufgabe implements Protokoll, Cloneable 
{
	/**
	 * 
	 */
    private String ivAusloesezeitpunkt;
    
    /**
     * 
     */
    private String ivDateisucheAufgabenID;
    
    /**
     * 
     * @param pvId
     * @param pvBezeichnung
     * @param pvAusloesezeitpunkt
     * @param pvUnbedingteAufgabe
     * @param pvFertigstellungsEreignis
     * @param pvAktivEreignis
     * @param pvLoeschEreignis
     * @param pvMandant
     * @param pvSortNr
     * @param pvExklusiv
     * @param pvDateisucheAufgabenID
     */
    public Trigger(String pvId, String pvBezeichnung, String pvAusloesezeitpunkt, boolean pvUnbedingteAufgabe, Ereignis pvFertigstellungsEreignis, Ereignis pvAktivEreignis, Ereignis pvLoeschEreignis, Mandant pvMandant, Integer pvSortNr, boolean pvExklusiv, String pvDateisucheAufgabenID) 
    {
        super(pvId, pvBezeichnung, null, null, pvUnbedingteAufgabe,pvFertigstellungsEreignis, pvAktivEreignis, pvLoeschEreignis, pvMandant, pvSortNr, pvExklusiv);
        this.ivAusloesezeitpunkt = pvAusloesezeitpunkt;
        this.ivDateisucheAufgabenID = pvDateisucheAufgabenID;
        
        try {
            this.setOldValueObjectCopy(this.clone());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Ereignis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean save() {
        AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant());
        if (this.getId() == null || this.getId().length() == 0){
            lvDAO.insertAufgabe(this, "0", false, false, true);
            AufgabeHandler.getInstance(this.getMandant()).reloadTrigger();
        } else {
            lvDAO.updateAufgabe( this, "0", false, false, true);
        }
        return true;
    }

    @Override
    public boolean delete() {
        AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant());
        lvDAO.deleteAufgabe(this);
        AufgabeHandler.getInstance(this.getMandant()).remove(this);
        return true;  
    }
    
    /**
     * 
     */
    public String toString(){
        return this.getBezeichnung();
    }
    
    /**
     * 
     * @param pvParam
     */
    public void setAusloesezeitpunkt(String pvParam){
        this.ivAusloesezeitpunkt = pvParam;
    }
    
    /**
     * 
     * @return
     */
    public String getAusloesezeitpunkt(){
        return this.ivAusloesezeitpunkt;
    }
    
    /**
     * Setzt die AufgabenID der zu stoppenden Dateisuche
     * @param pvParam
     */
    public void setDateisucheAugabenID(String pvParam)
    {
    	this.ivDateisucheAufgabenID = pvParam;
    }
    
    /**
     * Liefert die AufgabenID der zu stoppenden Dateisuche
     * @return
     */
    public String getDateisucheAufgabenID()
    {
    	return this.ivDateisucheAufgabenID;
    }

    @Override
    public String getProtokollEintrag(int pvProtokollEintragFormat, String pvProtokollEintragTyp) {
        String pvEintrag = "";
        Trigger lvCompareObject = (Trigger)this.getOldValueObjectCopy();
        if(pvProtokollEintragFormat == Protokoll.STRING){
            if(pvProtokollEintragTyp.equalsIgnoreCase( Protokoll.UPDATE)){
                String lvOldValue = "";
                lvOldValue = lvCompareObject.getBezeichnung() != null ? "-" : lvCompareObject.getBezeichnung().equals(this.getBezeichnung()) ? ""  : (lvCompareObject.getId() + "-" + lvCompareObject.getBezeichnung());
                pvEintrag = pvEintrag + "Bezeichnung: " + this.getBezeichnung() + "(" + lvOldValue  + ")";

                lvOldValue = lvCompareObject.getAusloesezeitpunkt() != null ? "-" : lvCompareObject.getAusloesezeitpunkt().equals(this.getAusloesezeitpunkt())? ""  : lvCompareObject.getAusloesezeitpunkt();
                pvEintrag = pvEintrag + " Ausloesezeitpunkt: " + this.getAusloesezeitpunkt() + "(" + lvOldValue + ")";
                
                lvOldValue = lvCompareObject.getFertigstellungsEreignis() != null ? "-" : lvCompareObject.getFertigstellungsEreignis().getId().equals(this.getFertigstellungsEreignis().getId()) ? "" : lvCompareObject.getFertigstellungsEreignis().getName();
                pvEintrag = pvEintrag + " Fertigereignis: " + this.getFertigstellungsEreignis().getName() + "(" + lvOldValue + ")";  
            } else { // INSERT, DELETE
                pvEintrag = pvEintrag + "Bezeichnung: " + this.getBezeichnung();
                pvEintrag = pvEintrag + " Ausloesezeitpunkt: " + this.getAusloesezeitpunkt();
                pvEintrag = pvEintrag + " Fertigereignis: " + this.getFertigstellungsEreignis().getName();
            }            
            return pvEintrag;
        }
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
