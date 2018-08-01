package nlb.txs.schnittstelle.Wertpapier.Cashflows.Daten;

import java.math.BigDecimal;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class Cashflow implements Comparable<Cashflow>
{
    /**
     * ISIN
     */
	private String ivISIN;
	
	/**
	 * Depotnummer (DEPOT_NR)
	 */
	private String ivDepotnummer;
	
	/**
	 * Kennzeichen SOMA (KZ_SOMA)
	 */
	private String ivKennzeichenSOMA;
	
	/**
	 * Datum SOMA (DATUM_SOMA)
	 */
	private String ivDatumSOMA;
	
	/**
	 * Institutsnummer (INST) 
	 */
	private String ivInstitutsnummer;
	
	/**
	 * Ablaufart (ART)
	 */
	private String ivAblaufart;
	
	/**
	 * Wertstellungsdatum (DATWE)
	 */
	private String ivWertstellungsdatum;
	
	/**
	 * Buchungsdatum (DATBU)
	 */
	private String ivBuchungsdatum;
	
	/**
	 * Betragswaehrung (ISO)
	 */
	private String ivBetragswaehrung;
	
	/**
	 * Betrag
	 */
	private String ivBetrag;
	
    /**
     * Konstruktor
	 */
	public Cashflow() 
	{
		this.ivISIN = new String();
		this.ivDepotnummer = new String();
		this.ivKennzeichenSOMA = new String();
		this.ivDatumSOMA = new String();
		this.ivInstitutsnummer = new String();
		this.ivAblaufart = new String();
		this.ivWertstellungsdatum = new String();
		this.ivBuchungsdatum = new String();
		this.ivBetragswaehrung = new String();
		this.ivBetrag = new String();
	}

	/**
	 * Liefert die ISIN
	 * @return the ivISIN
	 */
	public String getISIN() 
	{
		return ivISIN;
	}

	/**
	 * Setzt die ISIN
	 * @param pvISIN die zu setzende ISIN
	 */
	public void setISIN(String pvISIN) 
	{
		this.ivISIN = pvISIN;
	}

	/**
	 * Liefert die Depotnummer
	 * @return the ivDepotnummer
	 */
	public String getDepotnummer() 
	{
		return ivDepotnummer;
	}

	/**
	 * @param pvDepotnummer die zu setzende Depotnummer
	 */
	public void setDepotnummer(String pvDepotnummer) 
	{
		this.ivDepotnummer = pvDepotnummer;
	}

	/**
	 * Liefert das KennzeichenSOMA
	 * @return the ivKennzeichenSOMA
	 */
	public String getKennzeichenSOMA() 
	{
		return ivKennzeichenSOMA;
	}

	/**
	 * Setzt das KennzeichenSOMA
	 * @param pvKennzeichenSOMA das zu setzende KennzeichenSOMA
	 */
	public void setKennzeichenSOMA(String pvKennzeichenSOMA) 
	{
		this.ivKennzeichenSOMA = pvKennzeichenSOMA;
	}

	/**
	 * Liefert das DatumSOMA
	 * @return the ivDatumSOMA
	 */
	public String getDatumSOMA() 
	{
		return ivDatumSOMA;
	}

	/**
	 * Setzt das DatumSOMA
	 * @param pvDatumSOMA das zu setzende DatumSOMA
	 */
	public void setDatumSOMA(String pvDatumSOMA) 
	{
		this.ivDatumSOMA = pvDatumSOMA;
	}

	/**
	 * Liefert die Institusnummer
	 * @return the ivInstitutsnummer
	 */
	public String getInstitutsnummer() 
	{
		return ivInstitutsnummer;
	}

	/**
	 * Setzt die Institutsnummer
	 * @param pvInstitutsnummer die zu setzende Institutsnummer
	 */
	public void setInstitutsnummer(String pvInstitutsnummer) 
	{
		this.ivInstitutsnummer = pvInstitutsnummer;
	}

	/**
	 * Liefert die Ablaufart
	 * @return the ivAblaufart
	 */
	public String getAblaufart() 
	{
		return ivAblaufart;
	}

	/**
	 * Setzt die Ablaufart
	 * @param pvAblaufart die zu setzende Ablaufart
	 */
	public void setAblaufart(String pvAblaufart) 
	{
		this.ivAblaufart = pvAblaufart;
	}

	/**
	 * Liefert das Wertstellungsdatum
	 * @return the ivWertstellungsdatum
	 */
	public String getWertstellungsdatum()
	{
		return ivWertstellungsdatum;
	}

	/**
	 * Setzt das Wertstellungsdatum
	 * @param pvDatumWE das zu setzende Wertstellungsdatum
	 */
	public void setWertstellungsdatum(String pvWertstellungsdatum) 
	{
		this.ivWertstellungsdatum = pvWertstellungsdatum;
	}

	/**
	 * Liefert das Buchungsdatum
	 * @return the ivBuchungsdatum
	 */
	public String getBuchungsdatum() 
	{
		return ivBuchungsdatum;
	}

	/**
	 * Setzt das Buchungsdatum
	 * @param pvBuchungsdatum das zu setzende Buchungsdatum
	 */
	public void setBuchungsdatum(String pvBuchungsdatum) 
	{
		this.ivBuchungsdatum = pvBuchungsdatum;
	}

	/**
	 * Liefert die Betragswaehrung
	 * @return the ivBetragswaehrung
	 */
	public String getBetragswaehrung() 
	{
		return ivBetragswaehrung;
	}

	/**
	 * Setzt die Betragswaehrung
	 * @param pvBetragswaehrung the ivBetragswaehrung to set
	 */
	public void setBetragswaehrung(String pvBetragswaehrung) 
	{
		this.ivBetragswaehrung = pvBetragswaehrung;
	}

	/**
	 * Liefert den Betrag
	 * @return the ivBetrag
	 */
	public String getBetrag() 
	{
		return ivBetrag;
	}

	/**
	 * Setzt den Betrag
	 * @param pvBetrag den zu setzenden Betrag
	 */
	public void setBetrag(String pvBetrag) 
	{
		this.ivBetrag = pvBetrag;
	}
	
    /**
     * @param pvZeile 
     * @return 
     *       
     */
	public boolean parseCashflow(String pvZeile)
	{                 
      String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
      int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
      int    lvZzStr=0;              // pointer fuer satzbereich
            
      // Steuerung/Iteration Eingabesatz
      for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
      {

        // wenn Semikolon erkannt
        if (pvZeile.charAt(lvZzStr) == ';')
        {
            //System.out.println(lvTemp);
            this.setCashflow(lvLfd, lvTemp);
            
            lvLfd++;                  // naechste Feldnummer
            // loeschen Zwischenbuffer
            lvTemp = new String();
        }
        else
        {
            // uebernehmen Byte aus Eingabesatzposition
            lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
        }
      } // ende for  
      
      // Letzte Wert muss noch gesetzt werden
      this.setCashflow(lvLfd, lvTemp);
      
      return true; 
	}

	/**
     * Setzt einen Wert des Cashflows
     * @param pvPos Position
     * @param pvWert Wert
	 */
	public void setCashflow(int pvPos, String pvWert) 
    {
		//System.out.println("Pos: " + pvPos + " - Wert: " + pvWert);
        switch (pvPos)
        {
          case 0:
              this.ivISIN = pvWert;
              break;
          case 1:
              this.ivDepotnummer = pvWert;
              break;
          case 2:
              this.ivKennzeichenSOMA = pvWert;
              break;
          case 3:
              this.ivDatumSOMA = pvWert;
              break;
          case 4:
              this.ivInstitutsnummer = pvWert;
              break;
          case 5:
              this.ivAblaufart = pvWert;
              break;
          case 6:
              this.ivWertstellungsdatum = pvWert;
              break;
          case 7:
              this.ivBuchungsdatum = pvWert;
              break;
          case 8:
              this.ivBetragswaehrung = pvWert;
              break;
          case 9:
        	  if (pvWert.startsWith("-")) // Minuszeichen entfernen
        	  {
        		  pvWert = pvWert.substring(1);
        	  }
        	  BigDecimal lvHelpBetrag = StringKonverter.convertString2BigDecimal(pvWert.trim());
              this.ivBetrag = lvHelpBetrag.toPlainString();
              break;
          default:
              System.out.println("Cashflow: Feld " + pvPos + " undefiniert");
        }
    }

	/**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Cashflow pvCashflow) 
    {
        return DatumUtilities.changeDate(DatumUtilities.changeDatePoints(this.getBuchungsdatum())).compareTo(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvCashflow.getBuchungsdatum())));
    }

}
