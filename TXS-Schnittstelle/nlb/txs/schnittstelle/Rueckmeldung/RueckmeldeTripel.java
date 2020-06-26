/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

/**
 * Die Klasse enthaelt die Daten eines SAP CMS Rueckmeldeobjekt bzw. -zeile.
 * @author tepperc
 */
public class RueckmeldeTripel 
{
	/**
	 * Originator
	 */
	private String ivOriginator;
	
    /**
     * Geschaeftswert-ID
     */
    private String ivGeschaeftswertID;
    
    /**
     * Sicherheiten-ID
     */
    private String ivSicherheitenID;
    
    /**
     * Objektnummer
     */
    private String ivObjektID;
    
    /**
     * Status des Geschaeftswerts
     */
    private String ivGeschaeftswertStatus;
    
    /** 
     * Status der Sicherheit
     */
    private String ivSicherheitenStatus;
    
    /**
     * Status des Objekts
     */
    private String ivObjektStatus;
    
    /**
     * Konstruktor
     * @param pvOriginator Originator
     * @param pvGeschaeftswertID ID des Geschaeftswerts
     * @param pvSicherheitenID  ID der Sicherheit
     * @param pvObjID ID des Objekts
     * @param pvGeschaeftswertStatus Status des Geschaeftswerts
     * @param pvSicherheitenStatus Status der Sicherheit
     * @param pvObjektStatus Status des Objekts
     */
    public RueckmeldeTripel(String pvOriginator, String pvGeschaeftswertID, String pvSicherheitenID, String pvObjID, String pvGeschaeftswertStatus, String pvSicherheitenStatus, String pvObjektStatus)
    {
      this.ivOriginator = pvOriginator;
    	this.ivGeschaeftswertID = pvGeschaeftswertID;
    	this.ivSicherheitenID = pvSicherheitenID;
    	this.ivObjektID = pvObjID;
    	this.ivGeschaeftswertStatus = pvGeschaeftswertStatus;
    	this.ivSicherheitenStatus = pvSicherheitenStatus;
    	this.ivObjektStatus = pvObjektStatus;
    }

    /**
     * Liefert den Originator
	   * @return Originator
	   */
	  public String getOriginator()
	{
		return ivOriginator;
	}

	  /**
     * Setzt den Originator
	   * @param pvOriginator den zu setzenden Originator
	   */
	  public void setOriginator(String pvOriginator)
	{
		this.ivOriginator = pvOriginator;
	}

	  /**
     * Liefert die ID des Geschaeftswerts
     * @return ID des Geschaeftswerts
     */
    public String getGeschaeftswertID() 
    {
        return this.ivGeschaeftswertID;
    }

    /**
     * Setzt die ID die Geschaeftswerts
     * @param pvGeschaeftswertID die zu setzende ID des Geschaeftswerts
     */
    public void setGeschaeftswertID(String pvGeschaeftswertID) 
    {
        this.ivGeschaeftswertID = pvGeschaeftswertID;
    }

    /**
     * Liefert die ID der Sicherheit
     * @return ID der Sicherheit
     */
    public String getSicherheitenID() 
    {
        return this.ivSicherheitenID;
    }

    /**
     * Setzt die ID der Sicherheit
     * @param pvSicherheitenID die zu setzende ID der Sicherheit
     */
    public void setSicherheitenID(String pvSicherheitenID) 
    {
        this.ivSicherheitenID = pvSicherheitenID;
    }

    /**
     * Liefert die ID des Objekts
     * @return ID des Objekts
     */
    public String getObjektID() 
    {
        return this.ivObjektID;
    }

    /**
     * Setzt die ID des Objekts
     * @param pvObjektID die zu setzende ID des Objekts
     */
    public void setObjektID(String pvObjektID) 
    {
        this.ivObjektID = pvObjektID;
    }

    /**
     * Liefert den Status des Geschaeftswert
     * @return Status des Geschaeftswert
     */
    public String getGeschaeftswertStatus() {
        return this.ivGeschaeftswertStatus;
    }

    /**
     * Setzt den Status des Geschaeftswerts
     * @param pvGeschaeftswertStatus den zu setzenden Status des Geschaeftswerts
     */
    public void setGeschaeftswertStatus(String pvGeschaeftswertStatus) {
        this.ivGeschaeftswertStatus = pvGeschaeftswertStatus;
    }

    /**
     * Liefert den Status der Sicherheit
     * @return Status der Sicherheit
     */
    public String getSicherheitenStatus() {
        return this.ivSicherheitenStatus;
    }

    /**
     * Setzt den Status der Sicherheit
     * @param pvSicherheitenStatus den zu setztenden Status der Sicherheit
     */
    public void setSicherheitStatus(String pvSicherheitenStatus) {
        this.ivSicherheitenStatus = pvSicherheitenStatus;
    }

    /**
     * Liefert den Status des Objekts
     * @return Status des Objekts
     */
    public String getObjektStatus() {
        return this.ivObjektStatus;
    }

    /**
     * Setzt den Status des Objekts
     * @param pvObjektStatus den zu setzenden Status des  Objekts
     */
    public void setObjektStatus(String pvObjektStatus) {
        this.ivObjektStatus = pvObjektStatus;
    }
}
