/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS.Daten;

/**
 * @author tepperc
 *
 */
public class Bestandsverzeichnis 
{
    /**
     * 
     */
    private String ivFlur;
    
    /**
     * 
     */
    private String ivFlurstueck;
    
    /**
     * 
     */
    private String lvLfdNrGrundstueck;

    /**
     * Konstruktor
     */
    public Bestandsverzeichnis()
    {
        this.ivFlur = new String();
        this.ivFlurstueck = new String();
        this.lvLfdNrGrundstueck = new String();
    }
    
    /**
     * @return the flur
     */
    public String getFlur() {
        return this.ivFlur;
    }

    /**
     * @param pvFlur the flur to set
     */
    public void setFlur(String pvFlur) {
        this.ivFlur = pvFlur;
    }

    /**
     * @return the flurstueck
     */
    public String getFlurstueck() {
        return this.ivFlurstueck;
    }

    /**
     * @param pvFlurstueck the flurstueck to set
     */
    public void setFlurstueck(String pvFlurstueck) {
        this.ivFlurstueck = pvFlurstueck;
    }

    /**
     * @return the lfdGrundstueck
     */
    public String getLfdNrGrundstueck() {
        return this.lvLfdNrGrundstueck;
    }

    /**
     * @param pvLfdGrundstueck the lfdGrundstueck to set
     */
    public void setLfdNrGrundstueck(String pvLfdGrundstueck) {
        this.lvLfdNrGrundstueck = pvLfdGrundstueck;
    }
    
}
