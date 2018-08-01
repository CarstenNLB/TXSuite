/**
 * 
 */
package nlb.txs.schnittstelle.Transaktion;

/**
 * @author tepperc
 *
 */
public abstract interface TXSTransaktion 
{
   /**
    * TXSTransaktionStart in einen StringBuffer schreiben
    */
	public abstract StringBuffer printTXSTransaktionStart();
	
	/**
	 * TXSTransaktionDaten in einen StringBuffer schreiben
	 */
	public abstract StringBuffer printTXSTransaktionDaten();
	
	/**
	 * TXSTransaktionEnde in einen StringBuffer schreiben
	 */
	public abstract StringBuffer printTXSTransaktionEnde();
}
