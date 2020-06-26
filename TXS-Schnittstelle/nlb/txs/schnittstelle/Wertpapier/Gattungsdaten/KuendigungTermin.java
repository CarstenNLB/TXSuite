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
     * Konstruktor - Initialisierung mit leeren Strings
     */
    public KuendigungTermin()
    {
        this.ivKuendKurs = new String();
        this.ivKuendPer = new String();   	
    }
    
    /**
     * Konstruktor
     * @param pvKuendKurs Kuendigungskurs
     * @param pvKuendPer Kuendigungsprozent
     */
    public KuendigungTermin(String pvKuendKurs, String pvKuendPer) 
    {
        this.ivKuendKurs = pvKuendKurs;
        this.ivKuendPer = pvKuendPer;
    }

    /**
     * Liefert den Kuendigungskurs
     * @return Kuendigungskurs
     */
    public String getKuendKurs() {
        return this.ivKuendKurs;
    }

    /**
     * Setzt den Kuendigungskurs
     * @param pvKuendKurs den zu setzenden Kuendigungskurs
     */
    public void setKuendKurs(String pvKuendKurs) {
        this.ivKuendKurs = pvKuendKurs;
    }

    /**
     * Liefert die Kuendigungsprozent
     * @return Kuendigungsprozent
     */
    public String getKuendPer() {
        return this.ivKuendPer;
    }

    /**
     * Setzt die Kuendigungsprozent
     * @param pvKuendPer die zu setzenden Kuendigungsprozent
     */
    public void setKuendPer(String pvKuendPer) {
        this.ivKuendPer = pvKuendPer;
    }  
    
    /**
     * Liefert den Inhalt/die Werte als String
     */
    public String toString()
    {
    	StringBuilder lvBuilder = new StringBuilder();
    	lvBuilder.append("Kuendigungskurs: ");
    	lvBuilder.append(ivKuendKurs);
    	lvBuilder.append("Kuendigungsprozent: ");
    	lvBuilder.append(ivKuendPer);
    	return lvBuilder.toString();
    }
}
