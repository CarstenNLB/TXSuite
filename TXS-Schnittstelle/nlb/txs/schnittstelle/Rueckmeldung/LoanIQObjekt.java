package nlb.txs.schnittstelle.Rueckmeldung;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

public class LoanIQObjekt 
{
	/**
	 * Kontonummer
	 */
	private String ivKontonummer;
	
	/**
	 * Buergschaftprozent
	 */
	private String ivBuergschaftprozent;
	
	/**
	 * Kennzeichen Konsortial
	 */
	private String ivKennzeichenKonsortial;
	
	/**
	 * Konsortialanteil in Prozent
	 */
	private String ivKonsortialanteil;
	
	/**
	 * Restkapital - Netto
	 */
	private String ivRestkapitalNetto;
	
	/**
	 * Restkapital - Brutto
	 */
	private String ivRestkapitalBrutto;
	
	/**
	 * Solldeckung - Netto
	 */
	private String ivSolldeckungNetto;
	
	/**
	 * Solldeckung - Brutto
	 */
	private String ivSolldeckungBrutto;
	
	/**
	 * Konstruktor
	 */
	public LoanIQObjekt()
	{
		this.ivKontonummer = new String();
		this.ivBuergschaftprozent = new String();
		this.ivKennzeichenKonsortial = new String();
		this.ivKonsortialanteil = new String();
		this.ivRestkapitalNetto = new String();
		this.ivRestkapitalBrutto = new String();
		this.ivSolldeckungNetto = new String();
		this.ivSolldeckungBrutto = new String();
	}

	/**
	 * @return the ivKontonummer
	 */
	public String getKontonummer() 
	{
		return ivKontonummer;
	}

	/**
	 * @param pvKontonummer the ivKontonummer to set
	 */
	public void setKontonummer(String pvKontonummer) 
	{
		this.ivKontonummer = pvKontonummer;
	}

	/**
	 * @return the ivBuergschaftprozent
	 */
	public String getBuergschaftprozent() 
	{
		return ivBuergschaftprozent;
	}

	/**
	 * @param pvBuergschaftprozent the ivBuergschaftprozent to set
	 */
	public void setBuergschaftprozent(String pvBuergschaftprozent) 
	{
		this.ivBuergschaftprozent = pvBuergschaftprozent;
	}

	/**
	 * @return the ivKennzeichenKonsortial
	 */
	public String getKennzeichenKonsortial() 
	{
		return ivKennzeichenKonsortial;
	}

	/**
	 * @param pvKennzeichenKonsortial the ivKennzeichenKonsortial to set
	 */
	public void setKennzeichenKonsortial(String pvKennzeichenKonsortial) 
	{
		this.ivKennzeichenKonsortial = pvKennzeichenKonsortial;
	}

	/**
	 * @return the ivKonsortialanteil
	 */
	public String getKonsortialanteil() 
	{
		return ivKonsortialanteil;
	}

	/**
	 * @param pvKonsortialanteil the ivKonsortialanteil to set
	 */
	public void setKonsortialanteil(String pvKonsortialanteil) 
	{
		this.ivKonsortialanteil = pvKonsortialanteil;
	}

    /**
     * 
     * @return
     */
    public String getRestkapitalNetto() 
    {
		return ivRestkapitalNetto;
	}

    /**
     * 
     * @param pvRestkapitalNetto
     */
	public void setRestkapitalNetto(String pvRestkapitalNetto) 
	{
		this.ivRestkapitalNetto = pvRestkapitalNetto;
	}

	/**
	 * 
	 * @return
	 */
	public String getRestkapitalBrutto() 
	{
		return ivRestkapitalBrutto;
	}

	/**
	 * 
	 * @param pvRestkapitalBrutto
	 */
	public void setRestkapitalBrutto(String pvRestkapitalBrutto) 
	{
		this.ivRestkapitalBrutto = pvRestkapitalBrutto;
	}
	
    /**
     * 
     * @return
     */
	public String getSolldeckungNetto() 
	{
		return ivSolldeckungNetto;
	}

	/**
	 * 
	 * @param pvSolldeckungNetto
	 */
	public void setSolldeckungNetto(String pvSolldeckungNetto) 
	{
		this.ivSolldeckungNetto = pvSolldeckungNetto;
	}

	/**
	 * 
	 * @return
	 */
	public String getSolldeckungBrutto() 
	{
		return ivSolldeckungBrutto;
	}

	/**
	 * 
	 * @param pvSolldeckungBrutto
	 */
	public void setSolldeckungBrutto(String pvSolldeckungBrutto) 
	{
		this.ivSolldeckungBrutto = pvSolldeckungBrutto;
	}

	/**
     * @param pvZeile 
     * @return 
     * 
     */
    public boolean parseLoanIQObjekt(String pvZeile)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
     int    lvZzStr=0;              // pointer fuer satzbereich
     
     // steuerung/iteration eingabesatz
     for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Semikolon erkannt
       if (pvZeile.charAt(lvZzStr) == ';')
       {
         this.setLoanIQObjekt(lvLfd, lvTemp);
       
         lvLfd++;                  // naechste Feldnummer
       
         // loeschen Zwischenbuffer
         lvTemp = new String();

       }
       else
       {
           // uebernehmen byte aus eingabesatzposition
           lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
       }
     } // ende for
     
     // Letzte Feld auch noch setzen
     this.setLoanIQObjekt(lvLfd, lvTemp);     
     
     return true;
    }
    
    /**
     * Setzt einen Wert des LoanIQObjekts
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setLoanIQObjekt(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
          case 0:
              this.setKontonummer(pvWert);
              break;
          case 1:
              this.setBuergschaftprozent(pvWert);
              break;
          case 2:
              this.setKennzeichenKonsortial(pvWert);
              break;
          case 3:
        	  this.setKonsortialanteil(pvWert);
        	  break;
          case 4:
              this.setRestkapitalNetto(pvWert);
              break;
          case 5:
              this.setRestkapitalBrutto(pvWert);
              break;
          case 6:
        	  this.setSolldeckungNetto(pvWert);
        	  break;
          case 7:
        	  this.setSolldeckungBrutto(pvWert);
        	  break;
          default:
              System.out.println("LoanIQObjekt: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des LoanIQObjekt in einen String
     * @return
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Kontonummer: " + ivKontonummer + StringKonverter.lineSeparator);
        lvOut.append("Buergschaftprozent: " + ivBuergschaftprozent + StringKonverter.lineSeparator);
        lvOut.append("KennzeichenKonsortial: " + ivKennzeichenKonsortial + StringKonverter.lineSeparator);
        lvOut.append("Konsortialanteil: " + ivKonsortialanteil + StringKonverter.lineSeparator);
        lvOut.append("RestkapitalNetto: " + ivRestkapitalNetto + StringKonverter.lineSeparator);
        lvOut.append("RestkapitalBrutto: " + ivRestkapitalBrutto + StringKonverter.lineSeparator);
        lvOut.append("SolldeckungNetto: " + ivSolldeckungNetto + StringKonverter.lineSeparator);
        lvOut.append("SolldeckungBrutto: " + ivSolldeckungBrutto + StringKonverter.lineSeparator);

        return lvOut.toString();
    }

}
