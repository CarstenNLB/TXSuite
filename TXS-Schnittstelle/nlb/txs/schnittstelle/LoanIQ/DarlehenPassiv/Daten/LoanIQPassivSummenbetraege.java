/*******************************************************************************
 * Copyright (c) 2016 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten;

import java.math.BigDecimal;

public class LoanIQPassivSummenbetraege 
{
	/**
	 * Nominalbetrag
	 */
	private BigDecimal ivNominalbetrag;
	
	/**
	 * Leistungsrate
	 */
	private BigDecimal ivLeistungsrate;
	
	/**
	 * Konstruktor
	 * Setzt den Nominalbetrag und die Leistungsrate auf '0.0'
	 */
	public LoanIQPassivSummenbetraege()
	{
		ivNominalbetrag = new BigDecimal("0.0");
		ivLeistungsrate = new BigDecimal("0.0");
	}

	/**
	 * Liefert den Nominalbetrag
	 * @return Nominalbetrag
	 */
	public BigDecimal getNominalbetrag() 
	{
		return this.ivNominalbetrag;
	}

	/**
	 * Addiert die Nominalbetraege
	 * @param pvNominalbetrag der zu addierende Nominalbetrag
	 */
	public void addNominalbetrag(BigDecimal pvNominalbetrag) 
	{
		this.ivNominalbetrag = this.ivNominalbetrag.add(pvNominalbetrag);
	}

	/**
	 * Liefert die Leistungsrate
	 * @return Leistungsrate
	 */
	public BigDecimal getLeistungsrate() 
	{
		return this.ivLeistungsrate;
	}

	/**
	 * Addiert die Leistungsraten
	 * @param pvLeistungsrate die zu addierende Leistungsrate
	 */
	public void addLeistungsrate(BigDecimal pvLeistungsrate) 
	{
		this.ivLeistungsrate = this.ivLeistungsrate.add(pvLeistungsrate);
	}
}
