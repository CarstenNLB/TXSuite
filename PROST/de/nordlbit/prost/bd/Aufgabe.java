/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.bd;


/**
 * @author frankew
 *
 */
public abstract class Aufgabe extends PersistentObject implements Comparable<Aufgabe> 
{
	/**
	 * 
	 */
    private String ivId;
    
    /**
     * 
     */
    private Integer ivSortNr = new Integer("9999");
    
    /**
     * 
     */
    private String ivZeitfensterVon;
    
    /**
     * 
     */
    private String ivZeitfensterBis;
    
    /**
     * 
     */
    private boolean ivUnbedingteAufgabe = false;
    
    /**
     * 
     */
    private Ereignis ivFertigstellungsEreignis;
    
    /**
     * 
     */
    private Mandant ivMandant;
    
    /**
     * 
     */
    private String ivBezeichnung;
    
    /**
     * 
     */
    private Ereignis ivAktivEreignis;
    
    /**
     * 
     */
    private Ereignis ivLoeschEreignis;

    /**
     * 
     */
    private boolean ivExklusiv;
    
    /**
     * @param pvId
     * @param pvBezeichnung 
     * @param pvZeitfensterVon
     * @param pvZeitfensterBis
     * @param pvUnbedingteAufgabe
     * @param pvInArbeit
     * @param pvErledigt
     * @param pvFertigstellungsEreignis
     * @param pvAktivEreignis
     * @param pvLoeschEreignis
     * @param pvMandant 
     * @param pvSortNr 
     */
    public Aufgabe(String pvId, String pvBezeichnung, String pvZeitfensterVon, String pvZeitfensterBis, boolean pvUnbedingteAufgabe,
            Ereignis pvFertigstellungsEreignis, Ereignis pvAktivEreignis, Ereignis pvLoeschEreignis, Mandant pvMandant, Integer pvSortNr, boolean pvExklusiv) 
    {
        this.ivId = pvId;
        this.ivBezeichnung = pvBezeichnung;
        this.ivZeitfensterVon = pvZeitfensterVon;
        this.ivZeitfensterBis = pvZeitfensterBis;
        this.ivUnbedingteAufgabe = pvUnbedingteAufgabe;
        this.ivFertigstellungsEreignis = pvFertigstellungsEreignis;
        this.ivAktivEreignis = pvAktivEreignis;
        this.ivLoeschEreignis = pvLoeschEreignis;
        this.ivMandant = pvMandant;
        this.ivSortNr = pvSortNr;
        this.ivExklusiv = pvExklusiv;
    }

    /**
     * @param pvId
     * @param pvBezeichnung 
     * @param pvUnbedingteAufgabe
     * @param pvFertigstellungsEreignis
     * @param pvAktivEreignis
     * @param pvLoeschEreignis
     * @param pvMandant 
     * @param pvSortNr 
     * @param pvMonatsultimo 
     * @param pvQuartalsultimo 
     * @param pvJahresultimo 
     */
    public Aufgabe(String pvId, String pvBezeichnung, boolean pvUnbedingteAufgabe, Ereignis pvFertigstellungsEreignis, Ereignis pvAktivEreignis, Ereignis pvLoeschEreignis, Mandant pvMandant, Integer pvSortNr, boolean pvMonatsultimo, boolean pvQuartalsultimo, boolean pvJahresultimo) {
        super();
        this.ivId = pvId;
        this.ivBezeichnung = pvBezeichnung;
        this.ivUnbedingteAufgabe = pvUnbedingteAufgabe;
        this.ivFertigstellungsEreignis = pvFertigstellungsEreignis;
        this.ivAktivEreignis = pvAktivEreignis;
        this.ivLoeschEreignis = pvLoeschEreignis;
        this.ivMandant = pvMandant;
        this.ivSortNr = pvSortNr;
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
     * @return the zeitfensterVon
     */
    public String getZeitfensterVon() {
        return this.ivZeitfensterVon;
    }

    /**
     * @param pvZeitfensterVon the zeitfensterVon to set
     */
    public void setZeitfensterVon(String pvZeitfensterVon) {
        this.ivZeitfensterVon = pvZeitfensterVon;
    }

    /**
     * @return the zeitfensterBis
     */
    public String getZeitfensterBis() {
        return this.ivZeitfensterBis;
    }

    /**
     * @param pvZeitfensterBis the zeitfensterBis to set
     */
    public void setZeitfensterBis(String pvZeitfensterBis) {
        this.ivZeitfensterBis = pvZeitfensterBis;
    }

    /**
     * @return the unbedingteAufgabe
     */
    public boolean isUnbedingteAufgabe() {
        return this.ivUnbedingteAufgabe;
    }

    /**
     * @param pvUnbedingteAufgabe the unbedingteAufgabe to set
     */
    public void setUnbedingteAufgabe(boolean pvUnbedingteAufgabe) {
        this.ivUnbedingteAufgabe = pvUnbedingteAufgabe;
    }


    /**
     * @return the fertigstellungsEreignis
     */
    public Ereignis getFertigstellungsEreignis() {
        return this.ivFertigstellungsEreignis;
    }

    /**
     * @param pvFertigstellungsEreignis the fertigstellungsEreignis to set
     */
    public void setFertigstellungsEreignis(Ereignis pvFertigstellungsEreignis) {
        this.ivFertigstellungsEreignis = pvFertigstellungsEreignis;
    }

    /**
     * 
     * @return
     */
    public Ereignis getAktivEreignis() 
    {
		return ivAktivEreignis;
	}

    /**
     * 
     * @param pvAktivEreignis
     */
	public void setAktivEreignis(Ereignis pvAktivEreignis) 
	{
		this.ivAktivEreignis = pvAktivEreignis;
	}

	/**
	 * 
	 * @return
	 */
	public Ereignis getLoeschEreignis() 
	{
		return ivLoeschEreignis;
	}

	/**
	 * 
	 * @param pvLoeschEreignis
	 */
	public void setLoeschEreignis(Ereignis pvLoeschEreignis) 
	{
		this.ivLoeschEreignis = pvLoeschEreignis;
	}

	/**
	 * 
	 */
	public String toString() {
        return this.getId() + " unbedingt: " + this.isUnbedingteAufgabe();
    }

    /**
     * @return the sortNr
     */
    public Integer getSortNr() {
        return this.ivSortNr;
    }

    /**
     * @param pvSortNr the sortNr to set
     */
    public void setSortNr(Integer pvSortNr) {
        this.ivSortNr = pvSortNr;
    }

    /**
     * 
     */
    public int compareTo(Aufgabe pvAufgabe) 
    {
        //Aufgabe lvVergleichAufgabe = (Aufgabe) pvO;
        
        if (this.isUnbedingteAufgabe() && pvAufgabe.isUnbedingteAufgabe()) {
            if(this.getSortNr() != null && pvAufgabe.getSortNr() != null){
                return this.getSortNr().compareTo(pvAufgabe.getSortNr());
            } else if(this.getSortNr() == null && pvAufgabe.getSortNr() != null) {
                return 1;
            } else if(this.getSortNr() != null && pvAufgabe.getSortNr() == null){
                return -1;
            } else {
                return 0;
            }
        }
        if (this.isUnbedingteAufgabe() && !pvAufgabe.isUnbedingteAufgabe()) {
            return -1;
        }
        if (!this.isUnbedingteAufgabe() && pvAufgabe.isUnbedingteAufgabe()) {
            return 1;
        }

        return 0;
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
     * 
     * @return
     */
    public boolean isExklusiv() 
    {
		return ivExklusiv;
	}

    /**
     * 
     * @param pvExklusiv
     */
	public void setExklusiv(boolean pvExklusiv) 
	{
		this.ivExklusiv = pvExklusiv;
	}

	public abstract boolean save();
    
    public abstract boolean delete();

}
