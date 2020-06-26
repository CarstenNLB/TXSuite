/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.bd;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.nordlbit.prost.dao.aufgabe.AufgabeDAO;

/**
 * @author frankew
 *
 */
public class Dateisuche extends Aufgabe implements Protokoll, Cloneable {

    private static HashMap<String, Aufgabe> ivAufgaben;
    private String ivDateiname;
    private String ivDateiPfad;
    private String ivQualifier;
    

    /*
    public static HashMap<String, Aufgabe> getAufgaben() {

        if (Dateisuche.ivAufgaben != null) {
            return ivAufgaben;
        }

        Dateisuche.reload();
        
        return ivAufgaben;
    }
    
    public static HashMap<String, Dateisuche> getDateisuche(Mandant pvMandant) {
        HashMap<String, Dateisuche> lvDateisuche = new HashMap<String, Dateisuche>();
        if(Dateisuche.getAufgaben() != null) {
            for (Iterator it = Dateisuche.getAufgaben().values().iterator(); it.hasNext();) {
                Dateisuche pvAufgabe = (Dateisuche)it.next();
                if (pvAufgabe.getMandant().getId().equals(pvMandant.getId())){
                    lvDateisuche.put(pvAufgabe.getId(), pvAufgabe);
                }
            }
        }
        return lvDateisuche;
    }
    
        public static HashMap<String, Dateisuche> getUnbedingteAufgaben(Mandant pvMandant) {
        HashMap<String, Dateisuche> lvDateisuche = new HashMap<String, Dateisuche>();
        if(Dateisuche.getAufgaben() != null) {
            for (Iterator it = Dateisuche.getDateisuche(pvMandant).values().iterator(); it.hasNext();) {
                Dateisuche pvAufgabe = (Dateisuche)it.next();
                if(pvAufgabe.isUnbedingteAufgabe()){
                    lvDateisuche.put(pvAufgabe.getId(), pvAufgabe);    
                } 
            }
        }
        return lvDateisuche;
    }

    static void reload(){
        AufgabeDAO lvDAO = new AufgabeDAO();
        HashMap<String, Aufgabe> lvAufgaben = lvDAO.getDateisuchen();
        if(Dateisuche.ivAufgaben == null){
            Dateisuche.ivAufgaben = new HashMap<String, Aufgabe>();
        }
        
        if(lvAufgaben != null) {
            for (Iterator it = lvAufgaben.values().iterator(); it.hasNext();) {
                Dateisuche pvAufgabe = (Dateisuche)it.next();
                if(Dateisuche.ivAufgaben.get(pvAufgabe.getId()) == null){
                    Dateisuche.ivAufgaben.put(pvAufgabe.getId(), pvAufgabe);
                }
            }
        }
    }
*/

    /**
     * 
     * @param pvId
     * @param pvBezeichnung
     * @param pvDateiname
     * @param pvDateipfad
     * @param pvZeitfensterVon
     * @param pvZeitfensterBis
     * @param pvUnbedingteAufgabe
     * @param pvQualifier
     * @param pvFertigstellungsEreignis
     * @param pvAktivEreignis
     * @param pvLoeschEreignis
     * @param pvMandant
     * @param pvSortNr
     * @param pvExklusiv
     */
    public Dateisuche(String pvId, String pvBezeichnung, String pvDateiname, String pvDateipfad, String pvZeitfensterVon, String pvZeitfensterBis, boolean pvUnbedingteAufgabe, String pvQualifier, Ereignis pvFertigstellungsEreignis, Ereignis pvAktivEreignis, Ereignis pvLoeschEreignis, Mandant pvMandant, Integer pvSortNr, boolean pvExklusiv) 
    {
        super(pvId, pvBezeichnung, pvZeitfensterVon, pvZeitfensterBis, pvUnbedingteAufgabe, pvFertigstellungsEreignis, pvAktivEreignis, pvLoeschEreignis, pvMandant, pvSortNr, pvExklusiv);
        this.ivDateiname = pvDateiname;
        this.ivDateiPfad = pvDateipfad;
        this.ivQualifier = pvQualifier;
        
        try {
            this.setOldValueObjectCopy(this.clone());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Ereignis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


    /**
     * @return the dateiname
     */
    public String getDateiname() {
        return this.ivDateiname;
    }

    /**
     * @param pvDateiname the dateiname to set
     */
    public void setDateiname(String pvDateiname) {
        this.ivDateiname = pvDateiname;
    }

    /**
     * @return the dateiPfad
     */
    public String getDateiPfad() {
        return this.ivDateiPfad;
    }

    /**
     * @param pvDateiPfad the dateiPfad to set
     */
    public void setDateiPfad(String pvDateiPfad) {
        this.ivDateiPfad = pvDateiPfad;
    }


        /**
     * @return the qualifier
     */
    public String getQualifier() {
        return this.ivQualifier;
    }

    /**
     * @param pvQualifier the qualifier to set
     */
    public void setQualifier(String pvQualifier) {
        this.ivQualifier = pvQualifier;
    }
    
    
    public boolean save(){
            AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant());
            if (this.getId() == null || this.getId().length() == 0){
            lvDAO.insertAufgabe(this, "0", true, false, false);
            AufgabeHandler.getInstance(this.getMandant()).reloadDateisuche();
        } else {
            lvDAO.updateAufgabe(this, "0", true, false, false);
        }
        return true;
    }
    
    public boolean delete(){
            AufgabeDAO lvDAO = new AufgabeDAO(this.getMandant()); 
            lvDAO.deleteAufgabe(this);
            AufgabeHandler.getInstance(this.getMandant()).remove(this);
        return true;  
    }
    
    public String toString(){
        return this.getBezeichnung();
    }

    @Override
    public String getProtokollEintrag(int pvProtokollEintragFormat, String pvProtokollEintragTyp) {
        String pvEintrag = "";
        Dateisuche lvCompareObject = (Dateisuche)this.getOldValueObjectCopy();
        if(pvProtokollEintragFormat == Protokoll.STRING){
            if(pvProtokollEintragTyp.equalsIgnoreCase( Protokoll.UPDATE)){
                String lvOldValue = lvCompareObject.getBezeichnung() == null ? "-" : lvCompareObject.getBezeichnung().equals(this.getBezeichnung()) ? ""  : lvCompareObject.getBezeichnung();
                pvEintrag = pvEintrag + "Bezeichnung: " + this.getBezeichnung() + "(" + lvOldValue  + ")";
                
                lvOldValue = lvCompareObject.getDateiPfad() == null ? "-" : lvCompareObject.getDateiPfad().equals(this.getDateiPfad())? ""  : lvCompareObject.getDateiPfad();
                pvEintrag = pvEintrag + " DateiPfad: " + this.getDateiPfad() + "(" + lvOldValue + ")";
                
                lvOldValue = lvCompareObject.getDateiname() == null ? "-" : lvCompareObject.getDateiname().equals(this.getDateiname())? ""  : lvCompareObject.getDateiname();
                pvEintrag = pvEintrag + " Dateiname: " + this.getDateiname() + "(" + lvOldValue + ")";
                
                lvOldValue = lvCompareObject.getQualifier() == null ? "-" : lvCompareObject.getQualifier().equals(this.getQualifier())? ""  : lvCompareObject.getQualifier();            
                pvEintrag = pvEintrag + " Qualifier: " + this.getQualifier() + "(" + lvOldValue + ")";
                
                lvOldValue = lvCompareObject.getFertigstellungsEreignis() == null ? "-" : lvCompareObject.getFertigstellungsEreignis().getId().equals(this.getFertigstellungsEreignis().getId()) ? "" : lvCompareObject.getFertigstellungsEreignis().getName();
                pvEintrag = pvEintrag + " Fertigereignis: " + this.getFertigstellungsEreignis().getName() + "(" + lvOldValue + ")";  
            } else { // INSERT, DELETE
                pvEintrag = pvEintrag + "Bezeichnung: " + this.getBezeichnung();
                pvEintrag = pvEintrag + "DateiPfad: " + this.getDateiPfad();
                pvEintrag = pvEintrag + "Dateiname: " + this.getDateiname();
                pvEintrag = pvEintrag + "Qualifier: " + this.getQualifier();
                pvEintrag = pvEintrag + " Fertigereignis: " + this.getFertigstellungsEreignis().getName();
            }            
            return pvEintrag;
        }
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
