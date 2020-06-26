/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.bd;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.nordlbit.prost.dao.aufgabe.AufgabeDAO;


/**
 * @author frankew
 *
 */
public class ProzessStarter extends Aufgabe implements Protokoll, Cloneable{

    private String ivProzessname;
    private String ivPfad;
    private String[] ivParameter;

    /**
     * 
     * @param pvId
     * @param pvBezeichnung
     * @param pvProzessName
     * @param pvProzessPfad
     * @param pvZeitfensterVon
     * @param pvZeitfensterBis
     * @param pvUnbedingteAufgabe
     * @param pvFertigstellungsEreignis
     * @param pvAktivEreignis
     * @param pvLoeschEreignis
     * @param pvMandant
     * @param pvSortNr
     * @param pvExklusiv
     */
    public ProzessStarter(String pvId, String pvBezeichnung, String pvProzessName, String pvProzessPfad, String pvZeitfensterVon, String pvZeitfensterBis, boolean pvUnbedingteAufgabe, Ereignis pvFertigstellungsEreignis, Ereignis pvAktivEreignis, Ereignis pvLoeschEreignis, Mandant pvMandant, Integer pvSortNr, boolean pvExklusiv) 
    {
        super(pvId, pvBezeichnung, pvZeitfensterVon, pvZeitfensterBis, pvUnbedingteAufgabe, pvFertigstellungsEreignis, pvAktivEreignis, pvLoeschEreignis, pvMandant, pvSortNr, pvExklusiv);
        this.ivProzessname = pvProzessName;
        this.ivPfad = pvProzessPfad;
        
        try {
            this.setOldValueObjectCopy(this.clone());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Ereignis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * @return the prozessname
     */
    public String getProzessname() {
        return this.ivProzessname;
    }

    /**
     * @param pvProzessname the prozessname to set
     */
    public void setProzessname(String pvProzessname) {
        this.ivProzessname = pvProzessname;
    }

    /**
     * @return the pfad
     */
    public String getPfad() {
        return this.ivPfad;
    }

    /**
     * @param pvPfad the pfad to set
     */
    public void setPfad(String pvPfad) {
        this.ivPfad = pvPfad;
    }

    /**
     * @return the parameter
     */
    public String[] getParameter() {
        return this.ivParameter;
    }

    /**
     * @param pvParameter the parameter to set
     */
    public void setParameter(String[] pvParameter) {
        this.ivParameter = pvParameter;
    }

    @Override
    public boolean save()
    {
        AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant());
        if (this.getId() == null || this.getId().length() == 0){
            lvDAO.insertAufgabe(this, "0", false, true, false);
            AufgabeHandler.getInstance(this.getMandant()).reloadProzessStarter();
        } else {
            lvDAO.updateAufgabe(this, "0", false, true, false);
        }
        return true;
    }
    
    @Override
    public boolean delete(){
        AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant());
        lvDAO.deleteAufgabe(this);
        AufgabeHandler.getInstance(this.getMandant()).remove(this);
        return true;  
    }
    
    @Override
    public String toString(){
        return this.getBezeichnung();
    }

    @Override
    public String getProtokollEintrag(int pvProtollEintragFormat, String pvProtokollEintragTyp) {
        String pvEintrag = "";
        ProzessStarter lvCompareObject = (ProzessStarter)this.getOldValueObjectCopy();
        if(pvProtollEintragFormat == Protokoll.STRING){
            if(pvProtokollEintragTyp.equalsIgnoreCase( Protokoll.UPDATE)){
                String lvOldValue = "";
                lvOldValue = lvCompareObject.getBezeichnung() == null ? "-" : lvCompareObject.getBezeichnung().equals(this.getBezeichnung()) ? ""  : lvCompareObject.getBezeichnung();
                pvEintrag = pvEintrag + "Bezeichnung: " + this.getBezeichnung() + "(" + lvOldValue  + ")";
               
                lvOldValue = lvCompareObject.getPfad() == null ? "-" : lvCompareObject.getPfad().equals(this.getPfad())? ""  : lvCompareObject.getPfad();    
                pvEintrag = pvEintrag + " Pfad: " + this.getPfad() + "(" + lvOldValue + ")";
                 
                lvOldValue = lvCompareObject.getProzessname() == null ? "-" : lvCompareObject.getProzessname().equals(this.getProzessname())? ""  : lvCompareObject.getProzessname();   
                System.out.println("Update");
                pvEintrag = pvEintrag + " Prozessname: " + this.getProzessname() + "(" + lvOldValue + ")";
                
                lvOldValue = lvCompareObject.getFertigstellungsEreignis() == null ? "-" : lvCompareObject.getFertigstellungsEreignis().getId().equals(this.getFertigstellungsEreignis().getId()) ? "" : lvCompareObject.getFertigstellungsEreignis().getName(); 
                pvEintrag = pvEintrag + " Fertigereignis: " + this.getFertigstellungsEreignis().getName() + "(" + lvOldValue + ")";                
            } else { // INSERT, DELETE
                pvEintrag = pvEintrag + "Bezeichnung: " + this.getBezeichnung();
                pvEintrag = pvEintrag + " Pfad: " + "-" + this.getPfad();
                pvEintrag = pvEintrag + " Prozessname: " + this.getProzessname();
                pvEintrag = pvEintrag + " Fertigereignis: " + this.getFertigstellungsEreignis().getName();
             }            
            return pvEintrag;
        }
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }


 

}
