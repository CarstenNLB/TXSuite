/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.bd;

import java.util.HashMap;

/**
 * @author frankew
 *
 */
public class VgAusloesemuster {

    private Ausloesemuster ivAusloesemuster;
    private HashMap<String, VgEreignis> ivVgEreignisse;
    private Vorgang ivVorgang;
    private boolean ivGefunden;

    /**
     * @param pvAusloesemuster
     */
    public VgAusloesemuster(Vorgang pvVorgang, Ausloesemuster pvAusloesemuster) {
        super();
        this.ivVorgang = pvVorgang;
        this.ivAusloesemuster = pvAusloesemuster;
        this.ivVgEreignisse = new HashMap<String, VgEreignis>();
        this.fillVgEreignisse(pvVorgang, pvAusloesemuster);
        this.setGefunden(false);
    }

    /**
     * @return the ausloesemuster
     */
    public Ausloesemuster getAusloesemuster() {
        return this.ivAusloesemuster;
    }

    /**
     * @param pvAusloesemuster the ausloesemuster to set
     */
    public void setAusloesemuster(Ausloesemuster pvAusloesemuster) {
        this.ivAusloesemuster = pvAusloesemuster;
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

    public boolean isZutreffend(VgEreignis pvVgEreignis) {
        boolean lvResult = false;

        
        this.getVgEreignisse().remove(pvVgEreignis.getEreignis().getId());
        if (this.getVgEreignisse().isEmpty()) {
            System.out.println("Muster gefunden: " + this.toString());
            this.setGefunden(true);
            lvResult = true;
        }

        return lvResult;
    }
    
    private void fillVgEreignisse(Vorgang pvVorgang, Ausloesemuster pvAusloesemuster) {
        for (Ereignis pvEreignis : pvAusloesemuster.getEreignisse()) {
            VgEreignis lvVgEreignis = new VgEreignis(pvVorgang.getId(), pvEreignis);
            this.getVgEreignisse().put(lvVgEreignis.getEreignis().getId(), lvVgEreignis);
        }
    }

    public String toString() {
        String lvResult = "Ereignisse:\n";
        for (Ereignis pvEreignis : this.getAusloesemuster().getEreignisse()) {
            lvResult = lvResult + pvEreignis.toString() + "\n";
        }

        return lvResult;

    }

    /**
     * @return the gefunden
     */
    public boolean isGefunden() {
        return this.ivGefunden;
    }

    /**
     * @param pvGefunden the gefunden to set
     */
    public void setGefunden(boolean pvGefunden) {
        this.ivGefunden = pvGefunden;
    }

}
