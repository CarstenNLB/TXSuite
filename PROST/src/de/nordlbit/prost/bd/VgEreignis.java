/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.bd;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * @author frankew
 *
 */
public class VgEreignis implements Comparable<VgEreignis> 
{
    private Ereignis ivEreignis;
    private Timestamp ivAusloesezeitpunkt;
    private String ivVorgangId;
    private Mandant ivMandant;
    private static HashMap<String, HashMap<Ereignis, VgEreignis>> ivEreignisse;

    /**
     * @return the vorgang
     */
    public String getVorgangId() {
        return this.ivVorgangId;
    }

    /**
     * @param pvVorgang the vorgang to set
     */
    public void setVorgangId(String pvVorgangId) {
        this.ivVorgangId = pvVorgangId;
    }
    
    public Mandant getMandant(){
        return this.ivMandant;
    }
    
    public void setMandant(Mandant pvMandant){
        this.ivMandant = pvMandant;
    }

    /**
     * @return the ivEreignisse
     */
    public static HashMap<String, HashMap<Ereignis, VgEreignis>> getIvEreignisse() {
        return ivEreignisse;
    }

    /**
     * @param pvIvEreignisse the ivEreignisse to set
     */
    public static void setIvEreignisse(HashMap<String, HashMap<Ereignis, VgEreignis>> pvIvEreignisse) {
        ivEreignisse = pvIvEreignisse;
    }

    /**
     * @param pvEreignis
     */
    public VgEreignis(String pvVorgangId, Ereignis pvEreignis) {
        super();
        this.ivVorgangId = pvVorgangId;
        this.ivEreignis = pvEreignis;
    }

    public VgEreignis(String pvVorgangId, Ereignis pvEreignis,Mandant pvMandant, Timestamp pvAusloesezeitpunkt) {
        super();
        this.ivVorgangId = pvVorgangId;
        this.ivEreignis = pvEreignis;
        this.ivMandant = pvMandant;
        this.ivAusloesezeitpunkt = pvAusloesezeitpunkt;
    }
    
    public static VgEreignis getVgEreignis(String pvVorgang, Ereignis pvEreignis) {
        VgEreignis lvVgEreignis;
        if (VgEreignis.getIvEreignisse() == null) {
            VgEreignis.setIvEreignisse(new HashMap<String, HashMap<Ereignis, VgEreignis>>());
        }
        if (VgEreignis.getIvEreignisse().get(pvVorgang) == null) {
            VgEreignis.getIvEreignisse().put(pvVorgang, new HashMap<Ereignis, VgEreignis>());
        }
        lvVgEreignis = VgEreignis.getIvEreignisse().get(pvVorgang).get(pvEreignis);
        if (lvVgEreignis == null) {
            lvVgEreignis = new VgEreignis(pvVorgang, pvEreignis);
            VgEreignis.getIvEreignisse().get(pvVorgang).put(pvEreignis, lvVgEreignis);
        }

        return lvVgEreignis;
    }

    /**
     * @return the ereignis
     */
    public Ereignis getEreignis() {
        return this.ivEreignis;
    }

    /**
     * @param pvEreignis the ereignis to set
     */
    public void setEreignis(Ereignis pvEreignis) {
        this.ivEreignis = pvEreignis;
    }

    /**
     * @return the ausloesezeitpunkt
     */
    public Timestamp getAusloesezeitpunkt() {
        return this.ivAusloesezeitpunkt;
    }

    /**
     * @param pvAusloesezeitpunkt the ausloesezeitpunkt to set
     */
    public void setAusloesezeitpunkt(Timestamp pvAusloesezeitpunkt) {
        this.ivAusloesezeitpunkt = pvAusloesezeitpunkt;
    }

    public String toString() {
        return "VgEreignis: " + this.getEreignis().toString();
    }

    /**
     * 
     */
    public int compareTo(VgEreignis pvVgEreignis) 
    {
          //VgEreignis lvCompareObj = o;
          return this.getAusloesezeitpunkt().compareTo(pvVgEreignis.getAusloesezeitpunkt());
    }

}
