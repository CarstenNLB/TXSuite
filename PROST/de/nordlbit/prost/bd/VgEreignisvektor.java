/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.bd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.ausloesemuster.AusloesemusterDAO;
import de.nordlbit.prost.dao.ereignisvektor.EreignisVektorDAO;

/**
 * @author frankew
 *
 */
public class VgEreignisvektor {

    private Vorgang ivVorgang;
    private HashMap<String, VgEreignis> ivVgEreignisse;
    private Set<Ausloesemuster> ivMuster;
    private Set<VgAusloesemuster> ivVgMuster;
    private Set<Aufgabe> ivErkannteAufgaben;

    
    public static Collection<VgEreignis> getAllVgEreignisse(Mandant pvMandant){
        EreignisVektorDAO lvDAO = new EreignisVektorDAO(pvMandant);
        ArrayList<VgEreignis> lvCol = new ArrayList<VgEreignis>();

        for(VgEreignis pvEreignis : lvDAO.getVgEreignisse().values()){
            if(pvEreignis.getMandant().getId().equals(pvMandant.getId())){
                lvCol.add(pvEreignis);
            }
        }
        
        return lvCol;
        
    }
    
    public static Collection<VgEreignis> getVgEreignisse(Vorgang pvVorgang){
        EreignisVektorDAO lvDAO = new EreignisVektorDAO(Vorgang.getSvMandant());
        ArrayList<VgEreignis> lvCol = new ArrayList<VgEreignis>();

        for(VgEreignis pvEreignis : lvDAO.findVgEreignisseByVorgangID(pvVorgang.getId()).values()){
                lvCol.add(pvEreignis);
        }
        
        return lvCol;
        
    } 
     
    
    /**
     * @param pvVorgang 
     * 
     */
    public VgEreignisvektor(Vorgang pvVorgang) {
        super();
        ivVorgang = pvVorgang;
        ivVgEreignisse = new HashMap<String, VgEreignis>();
        ivMuster = new HashSet<Ausloesemuster>();
        ivVgMuster = new HashSet<VgAusloesemuster>();
        ivErkannteAufgaben = new HashSet<Aufgabe>();
        loadMuster();
    }

    /**
     * 
     * @return
     */
    public Set<Aufgabe> doMusterErkennung()
    {
        this.removeAllErkannteAufgaben();
        // TODO juengste Ereignisse aus Ereignisvektortabelle lesen
        this.loadVgEreignisse();
        for(VgEreignis pvVgEreignis : this.getVgEreignisse().values()){
           this.checkMuster(pvVgEreignis);
        }
        this.loadAufgabenForRestart();
        return this.ivErkannteAufgaben;
    }
    
    public void loadAufgabenForRestart(){
        // Aufgaben aus der DB laden, die neu gestartet werde sollen
        
        AufgabenVektorDAO lvDAO = new AufgabenVektorDAO(Vorgang.getSvMandant());
        HashMap<String, VgAufgabe> lvVgAufabenMap = lvDAO.findAufgabenByVorgangID(this.getVorgang());
        for (Iterator<VgAufgabe> it = lvVgAufabenMap.values().iterator(); it.hasNext();) {
            VgAufgabe lvVgAufgabe = it.next();
            if(!lvVgAufgabe.isInArbeit() && !lvVgAufgabe.isErledigt()){
                this.addErkannteAufgabe(lvVgAufgabe.getAufgabe());
                lvDAO.deleteAufgabeInAufgabenVektor(lvVgAufgabe.getAufgabenVektorID(), lvVgAufgabe.getAufgabe().getId());
             }
        }
        
    }
    
    public VgEreignis getLastVgEreignis(){
        // TODO
        return null;
    }
    
    private void loadVgEreignisse(){
        EreignisVektorDAO lvDAO = new EreignisVektorDAO(Vorgang.getSvMandant()); 
        HashMap<String, VgEreignis> lvVektorEreignisse  = lvDAO.findVgEreignisseByVorgangID(this.ivVorgang.getId());
        this.ivVgEreignisse.clear();
        for(VgEreignis pvVgEreignis : lvVektorEreignisse.values()){
            this.ivVgEreignisse.put(pvVgEreignis.getEreignis().getId(), pvVgEreignis);
        }

    }
    
    public void checkMuster(VgEreignis pvVgEreignis) {
        for (VgAusloesemuster pvVgMuster : this.ivVgMuster) {
            if (!pvVgMuster.isGefunden() && pvVgMuster.isZutreffend(pvVgEreignis)) {
                Set<Aufgabe> lvAufgaben = pvVgMuster.getAusloesemuster().getMassnahmen();
                for (Aufgabe pvAufgabe : lvAufgaben) 
                {
                    this.addErkannteAufgabe(pvAufgabe);
                }
            }
        }

    }

    public boolean hasEreignis(Ereignis pvEreignis) {
        boolean lvResult = false;
        for (VgEreignis lvVgEreignis : this.getVgEreignisse().values()) {
            if (lvVgEreignis.getEreignis().getId().equals(pvEreignis.getId())) {
                lvResult = true;
                break;
            }
        }
        return lvResult;
    }

    public void loadMuster() {
        AusloesemusterDAO lvDAO = new AusloesemusterDAO(Vorgang.getSvMandant()); 
        Collection<Ausloesemuster> lvMuster = lvDAO.findAusloesemusterByMandant(Vorgang.getSvMandant().getId()).values();
        this.getMuster().addAll(lvMuster);
        // VgAusloesemuster anlegen
        for (Ausloesemuster pvAusloesemuster : lvMuster) {
            this.getVgMuster().add(new VgAusloesemuster(this.getVorgang(), pvAusloesemuster));
        }
    }

    /**
     * @return the vgEreignisse
     */
    public HashMap<String, VgEreignis> getVgEreignisse() {
        return this.ivVgEreignisse;
    }

    /**
     * @param pvVgEreignisse the vgEreignisse to set
     */
    public void setVgEreignisse(HashMap<String, VgEreignis> pvVgEreignisse) {
        this.ivVgEreignisse = pvVgEreignisse;
    }

    /**
     * @return the muster
     */
    public Set<Ausloesemuster> getMuster() {
        return this.ivMuster;
    }

    /**
     * @param pvMuster the muster to set
     */
    public void setMuster(Set<Ausloesemuster> pvMuster) {
        this.ivMuster = pvMuster;
    }

    /**
     * @return the vgMuster
     */
    public Set<VgAusloesemuster> getVgMuster() {
        return this.ivVgMuster;
    }

    /**
     * @param pvVgMuster the vgMuster to set
     */
    public void setVgMuster(Set<VgAusloesemuster> pvVgMuster) {
        this.ivVgMuster = pvVgMuster;
    }


    /**
     * @return the neueAufgaben
     */
    public Set<Aufgabe> getErkannteAufgaben() {
        return this.ivErkannteAufgaben;
    }

    /**
     * @param pvNeueAufgaben the neueAufgaben to set
     */
    public void setErkannteAufgaben(Set<Aufgabe> pvNeueAufgaben) {
        this.ivErkannteAufgaben = pvNeueAufgaben;
    }

    public void addErkannteAufgabe(Aufgabe pvAufgabe) {
        System.out.println("erkannte (Folge)Aufgabe: " + pvAufgabe.toString());
        this.getErkannteAufgaben().add(pvAufgabe);
    }

    public void removeAllErkannteAufgaben() {
        this.getErkannteAufgaben().clear();
    }

    /**
     * @return the vorgang
     */
    public Vorgang getVorgang() {
        return this.ivVorgang;
    }

    /**
     * @param pvVorgang the vorgang to set
     */
    public void setVorgang(Vorgang pvVorgang) {
        this.ivVorgang = pvVorgang;
    }
}
