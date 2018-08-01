/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier.Gattungsdaten;

/**
 * @author tepperc
 *
 */
public class KuendigungTermin 
{
    /**
     * Kuendigungskurs
     */
    private String ivKuendKurs;
    
    /**
     * Kuendigungsprozent
     */
    private String ivKuendPer;

    /**
     * Konstruktor
     */
    public KuendigungTermin()
    {
        this.ivKuendKurs = new String();
        this.ivKuendPer = new String();   	
    }
    
    /**
     * @param pvKuendKurs
     * @param pvKuendPer
     */
    public KuendigungTermin(String pvKuendKurs, String pvKuendPer) 
    {
        this.ivKuendKurs = pvKuendKurs;
        this.ivKuendPer = pvKuendPer;
    }

    /**
     * @return the kuendKurs
     */
    public String getKuendKurs() {
        return this.ivKuendKurs;
    }

    /**
     * @param pvKuendKurs the kuendKurs to set
     */
    public void setKuendKurs(String pvKuendKurs) {
        this.ivKuendKurs = pvKuendKurs;
    }

    /**
     * @return the kuendPer
     */
    public String getKuendPer() {
        return this.ivKuendPer;
    }

    /**
     * @param pvKuendPer the kuendPer to set
     */
    public void setKuendPer(String pvKuendPer) {
        this.ivKuendPer = pvKuendPer;
    }  
    
    /**
     * 
     */
    public String toString()
    {
    	StringBuilder lvBuilder = new StringBuilder();
    	lvBuilder.append("Kuendigungskurs: " + ivKuendKurs);
    	lvBuilder.append("Kuendigungsprozent: " + ivKuendPer);
    	return lvBuilder.toString();
    }
}
