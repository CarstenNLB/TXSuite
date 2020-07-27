/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.ObjektZuweisungsbetrag;

import java.math.BigDecimal;

/**
 * @author tepperc
 *
 */
public class ObjektZuweisungsbetrag 
{
    /**
     * 
     */
    private String lvObjektnummer;
    
    /**
     * 
     */
    private BigDecimal lvZuweisungsbetrag;
    
    /**
     * Konstruktor
     * @param pvObjektnummer 
     * @param pvZuweisungsbetrag 
     */
    public ObjektZuweisungsbetrag(String pvObjektnummer, BigDecimal pvZuweisungsbetrag)
    {
        this.lvObjektnummer = pvObjektnummer;
        this.lvZuweisungsbetrag = pvZuweisungsbetrag;
    }

    /**
     * @return the objektnummer
     */
    public String getObjektnummer() {
        return this.lvObjektnummer;
    }

    /**
     * @param pvObjektnummer the objektnummer to set
     */
    public void setObjektnummer(String pvObjektnummer) {
        this.lvObjektnummer = pvObjektnummer;
    }

    /**
     * @return the zuweisungsbetrag
     */
    public BigDecimal getZuweisungsbetrag() {
        return this.lvZuweisungsbetrag;
    }

    /**
     * @param pvZuweisungsbetrag the zuweisungsbetrag to set
     */
    public void setZuweisungsbetrag(BigDecimal pvZuweisungsbetrag) {
        this.lvZuweisungsbetrag = pvZuweisungsbetrag;
    }

    /**
     * @param zw 
     * 
     */
    public void addZuweisungsbetrag(BigDecimal zw)
    {
        this.lvZuweisungsbetrag = this.lvZuweisungsbetrag.add(zw);
    }
    
    /**
     * @return 
     * 
     */
    public String toString()
    {
        return this.lvObjektnummer + ";" + this.lvZuweisungsbetrag.toString();
    }
}
