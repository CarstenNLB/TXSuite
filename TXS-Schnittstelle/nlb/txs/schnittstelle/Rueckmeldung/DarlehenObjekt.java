/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

/**
 * @author tepperc
 *
 */
public class DarlehenObjekt 
{
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
     * @param pvAusplatzierungsmerkmal 
     * @param pvDeckungsstatus 
     * @param pvOriginator 
     * @param pvQuellsystem 
     * @param pvSolldeckung 
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
     * @return the ausplatzierungsmerkmal
     */
    public String getAusplatzierungsmerkmal() {
        return this.ivAusplatzierungsmerkmal;
    }

    /**
     * @param pvAusplatzierungsmerkmal the ausplatzierungsmerkmal to set
     */
    public void setAusplatzierungsmerkmal(String pvAusplatzierungsmerkmal) {
        this.ivAusplatzierungsmerkmal = pvAusplatzierungsmerkmal;
    }

    /**
     * @return the deckungsstatus
     */
    public String getDeckungsstatus() {
        return this.ivDeckungsstatus;
    }

    /**
     * @param pvDeckungsstatus the deckungsstatus to set
     */
    public void setDeckungsstatus(String pvDeckungsstatus) {
        this.ivDeckungsstatus = pvDeckungsstatus;
    }

    /**
     * @return the originator
     */
    public String getOriginator() {
        return this.ivOriginator;
    }

    /**
     * @param pvOriginator the originator to set
     */
    public void setOriginator(String pvOriginator) {
        this.ivOriginator = pvOriginator;
    }

    /**
     * @return the quellsystem
     */
    public String getQuellsystem() {
        return this.ivQuellsystem;
    }

    /**
     * @param pvQuellsystem the quellsystem to set
     */
    public void setQuellsystem(String pvQuellsystem) {
        this.ivQuellsystem = pvQuellsystem;
    }

    /**
     * @return the solldeckung
     */
    public String getSolldeckung() {
        return this.ivSolldeckung;
    }

    /**
     * @param pvSolldeckung the solldeckung to set
     */
    public void setSolldeckung(String pvSolldeckung) {
        this.ivSolldeckung = pvSolldeckung;
    }
    
}
