/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

/**
 * Diese Klasse enthaelt die Daten eines Darlehen-Objekts fuer die Rueckmeldung.
 * @author tepperc
 */
public class DarlehenObjekt {
    /**
     * Ausplatzierungsmerkmal
     */
    private String ivAusplatzierungsmerkmal;
    
    /**
     * Deckungsstatus 
     */
    private String ivDeckungsstatus;
    
    /**
     * Originator
     */
    private String ivOriginator;
    
    /**
     * Quellsystem
     */
    private String ivQuellsystem;
    
    /**
     * Solldeckung
     */
    private String ivSolldeckung;
    
    /**
     * Konstruktor - Initialisiert die Strings
     */
    public DarlehenObjekt()
    {
        ivAusplatzierungsmerkmal = new String();
        ivDeckungsstatus = new String();
        ivOriginator = new String();
        ivQuellsystem = new String();
        ivSolldeckung = new String();
    }
    
    /**
     * Konstruktor mit Parameter
     * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
     * @param pvDeckungsstatus Deckungsstatus
     * @param pvOriginator Originator
     * @param pvQuellsystem Quellsystem
     * @param pvSolldeckung Solldeckung
     */
    public DarlehenObjekt(String pvAusplatzierungsmerkmal, String pvDeckungsstatus, String pvOriginator, String pvQuellsystem, String pvSolldeckung)
    {
        ivAusplatzierungsmerkmal = pvAusplatzierungsmerkmal;
        ivDeckungsstatus = pvDeckungsstatus;
        ivOriginator = pvOriginator;
        ivQuellsystem = pvQuellsystem;
        ivSolldeckung = pvSolldeckung;
    }

    /**
     * Liefert das Ausplatzierungsmerkmal
     * @return the ausplatzierungsmerkmal
     */
    public String getAusplatzierungsmerkmal() {
        return this.ivAusplatzierungsmerkmal;
    }

    /**
     * Setzt das Ausplatzierungsmerkmal
     * @param pvAusplatzierungsmerkmal the ausplatzierungsmerkmal to set
     */
    public void setAusplatzierungsmerkmal(String pvAusplatzierungsmerkmal) {
        this.ivAusplatzierungsmerkmal = pvAusplatzierungsmerkmal;
    }

    /**
     * Liefert den Deckungsstatus
     * @return the deckungsstatus
     */
    public String getDeckungsstatus() {
        return this.ivDeckungsstatus;
    }

    /**
     * Setzt den Deckungsstatus
     * @param pvDeckungsstatus the deckungsstatus to set
     */
    public void setDeckungsstatus(String pvDeckungsstatus) {
        this.ivDeckungsstatus = pvDeckungsstatus;
    }

    /**
     * Liefert den Originator
     * @return the originator
     */
    public String getOriginator() {
        return this.ivOriginator;
    }

    /**
     * Setzt den Originator
     * @param pvOriginator the originator to set
     */
    public void setOriginator(String pvOriginator) {
        this.ivOriginator = pvOriginator;
    }

    /**
     * Liefert das Quellsystem
     * @return the quellsystem
     */
    public String getQuellsystem() {
        return this.ivQuellsystem;
    }

    /**
     * Setzt das Quellsystem
     * @param pvQuellsystem the quellsystem to set
     */
    public void setQuellsystem(String pvQuellsystem) {
        this.ivQuellsystem = pvQuellsystem;
    }

    /**
     * Liefert die Solldeckung
     * @return the solldeckung
     */
    public String getSolldeckung() {
        return this.ivSolldeckung;
    }

    /**
     * Setzt die Solldeckung
     * @param pvSolldeckung the solldeckung to set
     */
    public void setSolldeckung(String pvSolldeckung) {
        this.ivSolldeckung = pvSolldeckung;
    }
    
}
