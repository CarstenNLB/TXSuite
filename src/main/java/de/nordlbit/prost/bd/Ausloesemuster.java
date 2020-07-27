/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale Alle Rechte vorbehalten.
 * 
 *******************************************************************************/

package de.nordlbit.prost.bd;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.nordlbit.prost.dao.ausloesemuster.AusloesemusterDAO;
import de.nordlbit.prost.dao.protokoll.ProtokollDAO;

/**
 * @author frankew
 *
 */
public class Ausloesemuster extends PersistentObject implements Comparable<Ausloesemuster>, Protokoll, Cloneable{

    private String ivId;
    private Set<Ereignis> ivEreignisse;
    private Set<Aufgabe> ivMassnahmen;
    private Mandant ivMandant;
    private Vorgang ivVorgang;
    private String ivBezeichnung;

   
    
    /**
     * @param pvEreignisse
     * @param pvMassnahmen
     */
    public Ausloesemuster(Set<Ereignis> pvEreignisse, Set<Aufgabe> pvMassnahmen) {
        super();
        this.ivEreignisse = pvEreignisse;
        this.ivMassnahmen = pvMassnahmen;
        
        try {
            this.setOldValueObjectCopy(this.clone());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Ausloesemuster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the ereignisse
     */
    public Set<Ereignis> getEreignisse() {
        return this.ivEreignisse;
    }

    /**
     * @param pvEreignisse the ereignisse to set
     */
    public void setEreignisse(Set<Ereignis> pvEreignisse) {
        this.ivEreignisse = pvEreignisse;
    }

    /**
     * @return the massnahmen
     */
    public Set<Aufgabe> getMassnahmen() {
        return this.ivMassnahmen;
    }

    /**
     * @param pvMassnahmen the massnahmen to set
     */
    public void setMassnahmen(Set<Aufgabe> pvMassnahmen) {
        this.ivMassnahmen = pvMassnahmen;
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

    /**
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return this.ivBezeichnung;
    }

    /**
     * @param pvBezeichnung the bezeichnung to set
     */
    public void setBezeichnung(String pvBezeichnung) {
        this.ivBezeichnung = pvBezeichnung;
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
    
    public Vorgang getVorgang(){
        return this.ivVorgang;
    }
    
    public void setVorgang(Vorgang pvVorgang){
        this.ivVorgang = pvVorgang;
    }
    
    public boolean save(){
        // TODO Bezeichung speichern
        
        AusloesemusterDAO lvDAO = new AusloesemusterDAO(this.getMandant()); 
        if (this.getId() == null || this.getId().length() == 0){
            lvDAO.insertAusloesemuster(this);
            AusloesemusterHandler.getInstance(this.getMandant()).reload();
        } else {
            // alle alte Verknuepfungen zw. Muster und Ereignis loeschen
            lvDAO.deleteAusloesemusterEreignis(this);    
            // alle alte Verknuepfungen zw. Muster und Aufgabe loeschen
            lvDAO.deleteAusloesemusterAufgabe(this);    
            // Schleife ueber alle Ereignisse
            for(Ereignis pvEreignis : this.getEreignisse()) {
                lvDAO.insertAusloesemusterEreignis(this, pvEreignis);
            }   
            // Schleife ueber alle Aufgaben
            for(Aufgabe pvAufgabe : this.getMassnahmen()) {
                lvDAO.insertAusloesemusterAufgabe(this, pvAufgabe);
            } 
            
           // Protokolleintrag
          ProtokollDAO lvProtokollDAO = new ProtokollDAO(this.getMandant());
          lvProtokollDAO.insertProtokollEintrag(Protokoll.UPDATE, this.getId(), "Ausloesemuster", this.getProtokollEintrag(Protokoll.STRING, Protokoll.UPDATE), this.getMandant().getId());

        }
        return true;
    }
    
    @Override
    public String toString(){
        // Schleife ueber alle Aufgaben
        String lvMassnahme = "";
        for(Aufgabe pvAufgabe : this.getMassnahmen()) {
            lvMassnahme = pvAufgabe.getBezeichnung();
            break;
        } 
        if(this.getMassnahmen().size() > 1){
            lvMassnahme = lvMassnahme + ",...";
        }
        return this.getId() + ": " + ((this.getBezeichnung() != null)? this.getBezeichnung() : lvMassnahme);
    }
    
    public boolean delete(){
        AusloesemusterDAO lvDAO = new AusloesemusterDAO(this.getMandant()); 
        lvDAO.deleteAusloesemuster(this);
        AusloesemusterHandler.getInstance(this.getMandant()).remove(this);
        return true;
    }
    
    /**
     * 
     */
    public int compareTo(Ausloesemuster pvAusloesemuster) 
    {
        //Ausloesemuster lvVergleichMuster = (Ausloesemuster) pvO;
        
        return this.getId().compareTo(pvAusloesemuster.getId());
    }
       
    public boolean isEmpty(){
        if(this.getEreignisse().isEmpty() && this.getMassnahmen().isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getProtokollEintrag(int pvProtokollEintragFormat, String pvProtokollEintragTyp) {
        
        String pvEintrag = "";
        Ausloesemuster lvCompareObject = (Ausloesemuster)this.getOldValueObjectCopy();
        if(pvProtokollEintragFormat == Protokoll.STRING){
            if(pvProtokollEintragTyp.equalsIgnoreCase( Protokoll.UPDATE)){
                String lvOldValueEreignisse = "(";
                for(Ereignis pvEreignis : lvCompareObject.getEreignisse()) {
                    lvOldValueEreignisse = lvOldValueEreignisse + pvEreignis.getId() + "-" + pvEreignis.getName() + ", ";
                }
                if(lvOldValueEreignisse.length() > 3){
                    lvOldValueEreignisse = lvOldValueEreignisse.substring(0, lvOldValueEreignisse.length() - 2); // letztes  ", " entfernen  
                }
                lvOldValueEreignisse = lvOldValueEreignisse + ")";   
                
                String lvEreignisse = "{";
                for(Ereignis pvEreignis : this.getEreignisse()) {
                    lvEreignisse = lvEreignisse + pvEreignis.getId() + "-" + pvEreignis.getName() + ", ";
                }
                if(lvEreignisse.length() > 3){                
                    lvEreignisse = lvEreignisse.substring(0, lvEreignisse.length() - 2); // letztes  ", " entfernen
                }
                lvEreignisse = lvEreignisse + "} ";               
                pvEintrag = pvEintrag + "Ereignisse: " + lvEreignisse +  lvOldValueEreignisse  + ";";
                
                String lvOldValueMassnahmen = "(";
                for(Aufgabe pvAufgabe : lvCompareObject.getMassnahmen()) {
                    lvOldValueMassnahmen = lvOldValueEreignisse + pvAufgabe.getId() + "-" + pvAufgabe.getBezeichnung() + ", ";
                }
                if(lvOldValueMassnahmen.length() > 3){
                    lvOldValueMassnahmen = lvOldValueMassnahmen.substring(0, lvOldValueMassnahmen.length() - 2); // letztes  ", " entfernen
                }
                lvOldValueMassnahmen = lvOldValueMassnahmen + ")";                 
                
                String lvMassnahmen = "{";
                for(Aufgabe pvAufgabe : this.getMassnahmen()) {
                    lvMassnahmen = lvMassnahmen + pvAufgabe.getId() + "-" + pvAufgabe.getBezeichnung();
                }
                lvMassnahmen = lvMassnahmen + "} ";
                pvEintrag = pvEintrag + " Massnahmen: " + lvMassnahmen  + lvOldValueMassnahmen;              
                
            } else { // INSERT, DELETE
                String lvEreignisse = "{";
                for(Ereignis pvEreignis : this.getEreignisse()) {
                    lvEreignisse = lvEreignisse + pvEreignis.getId() + "-" + pvEreignis.getName() + ", ";;
                }
                if(lvEreignisse.length() > 3){
                    lvEreignisse = lvEreignisse.substring(0, lvEreignisse.length() - 2); // letztes  ", " entfernen
                }
                lvEreignisse = lvEreignisse + "} ";               
                pvEintrag = pvEintrag + "Ereignisse: " + lvEreignisse;
                
                String lvMassnahmen = "{";
                for(Aufgabe pvAufgabe : this.getMassnahmen()) {
                    lvMassnahmen = lvMassnahmen + pvAufgabe.getId() + "-" + pvAufgabe.getBezeichnung() + ", ";;
                }
                if(lvMassnahmen.length() > 3){
                    lvMassnahmen = lvMassnahmen.substring(0, lvMassnahmen.length() - 2); // letztes  ", " entfernen
                }
                lvMassnahmen = lvMassnahmen + "} ";
                pvEintrag = pvEintrag + " Massnahmen: " + lvMassnahmen;              

            }            
            return pvEintrag;
        }
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
 
}
