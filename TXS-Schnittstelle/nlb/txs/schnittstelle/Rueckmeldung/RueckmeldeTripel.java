/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

/**
 * @author tepperc
 *
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
     * @param pvOriginator
     * @param pvGeschaeftswertID 
     * @param pvSicherheitenID 
     * @param pvObjID 
     * @param pvGeschaeftswertStatus 
     * @param pvSicherheitenStatus 
     * @param pvObjektStatus 
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
	 * @return the ivOriginator
	 */
	public String getOriginator() 
	{
		return ivOriginator;
	}

	/**
	 * @param pvOriginator the ivOriginator to set
	 */
	public void setOriginator(String pvOriginator) 
	{
		this.ivOriginator = pvOriginator;
	}

	/**
     * @return the geschaeftswertID
     */
    public String getGeschaeftswertID() 
    {
        return this.ivGeschaeftswertID;
    }

    /**
     * @param pvGeschaeftswertID the geschaeftswertID to set
     */
    public void setGeschaeftswertID(String pvGeschaeftswertID) 
    {
        this.ivGeschaeftswertID = pvGeschaeftswertID;
    }

    /**
     * @return the sicherheitenID
     */
    public String getSicherheitenID() 
    {
        return this.ivSicherheitenID;
    }

    /**
     * @param pvSicherheitenID the sicherheitenID to set
     */
    public void setSicherheitenID(String pvSicherheitenID) 
    {
        this.ivSicherheitenID = pvSicherheitenID;
    }

    /**
     * @return the objektID
     */
    public String getObjektID() 
    {
        return this.ivObjektID;
    }

    /**
     * @param pvObjektID the objektID to set
     */
    public void setObjektID(String pvObjektID) 
    {
        this.ivObjektID = pvObjektID;
    }

    /**
     * @return the geschaeftswertStatus
     */
    public String getGeschaeftswertStatus() {
        return this.ivGeschaeftswertStatus;
    }

    /**
     * @param pvGeschaeftswertStatus the geschaeftswertStatus to set
     */
    public void setGeschaeftswertStatus(String pvGeschaeftswertStatus) {
        this.ivGeschaeftswertStatus = pvGeschaeftswertStatus;
    }

    /**
     * @return the sicherheitenStatus
     */
    public String getSicherheitenStatus() {
        return this.ivSicherheitenStatus;
    }

    /**
     * @param pvSicherheitenStatus the sicherheitenStatus to set
     */
    public void setSicherheitStatus(String pvSicherheitenStatus) {
        this.ivSicherheitenStatus = pvSicherheitenStatus;
    }

    /**
     * @return the objektStatus
     */
    public String getObjektStatus() {
        return this.ivObjektStatus;
    }

    /**
     * @param pvObjektStatus the objektStatus to set
     */
    public void setObjektStatus(String pvObjektStatus) {
        this.ivObjektStatus = pvObjektStatus;
    }
}
