/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Finanzierung;

/**
 * @author tepperc
 *
 */
public class ObjektElement 
{
    /**
     * Finanzierung Externer Key
     */
    private String ivFinanzierungExternerKey;
    
    /**
     * Objekt-/Facility-Nr
     */
    private String ivObjektNr;
    
    /**
     * Quellsystem
     */
    private String ivQuellsystem;
    
    /**
     * Konstruktor
     */
    public ObjektElement()
    {
        ivFinanzierungExternerKey = new String();
        ivObjektNr = new String();
        ivQuellsystem = new String();
    }

    /**
     * @return the finanzierungExternerKey
     */
    public String getFinanzierungExternerKey() {
        return this.ivFinanzierungExternerKey;
    }

    /**
     * @param pvFinanzierungExternerKey the finanzierungExternerKey to set
     */
    public void setFinanzierungExternerKey(String pvFinanzierungExternerKey) {
        this.ivFinanzierungExternerKey = pvFinanzierungExternerKey;
    }

    /**
     * @return the objektNr
     */
    public String getObjektNr() {
        return this.ivObjektNr;
    }

    /**
     * @param pvObjektNr the objektNr to set
     */
    public void setObjektNr(String pvObjektNr) {
        this.ivObjektNr = pvObjektNr;
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
    
}
