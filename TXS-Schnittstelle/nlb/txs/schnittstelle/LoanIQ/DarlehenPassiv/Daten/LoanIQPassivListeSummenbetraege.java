package nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten;

import java.util.HashMap;

public class LoanIQPassivListeSummenbetraege extends HashMap<String, LoanIQPassivSummenbetraege> 
{
    /**
     * Liefert die eventuell vorhandenen LoanIQPassivSummenbetraege
     * @param pvISIN ISIN
     * @return 
     * 
     */
    public LoanIQPassivSummenbetraege getSummenbetraege(String pvISIN)
    {
        return this.get(pvISIN);
    }

    /**
     * Setzt die Summenbetraege
     * @param pvISIN ISIN
     * @param pvSummenbetraege Summenbetrag
     * 
     */
    public void putSummenbetraege(String pvISIN, LoanIQPassivSummenbetraege pvSummenbetraege) 
    {
        this.put(pvISIN, pvSummenbetraege);
    }
}
