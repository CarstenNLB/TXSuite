package nlb.txs.schnittstelle.Rueckmeldung;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * Diese Klasse enthaelt die Daten eines LoanIQ-Objekts fuer die Rueckmeldung.
 * @author Carsten Tepper
 */
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
	 * Liefert die Kontonummer
	 * @return the ivKontonummer
	 */
	public String getKontonummer() 
	{
		return ivKontonummer;
	}

	/**
	 * Setzt die Kontonummer
	 * @param pvKontonummer the ivKontonummer to set
	 */
	public void setKontonummer(String pvKontonummer) 
	{
		this.ivKontonummer = pvKontonummer;
	}

	/**
	 * Liefert den Buergschaftprozentsatz
	 * @return the ivBuergschaftprozent
	 */
	public String getBuergschaftprozent() 
	{
		return ivBuergschaftprozent;
	}

	/**
	 * Setzt den Buergschaftprozentsatz
	 * @param pvBuergschaftprozent the ivBuergschaftprozent to set
	 */
	public void setBuergschaftprozent(String pvBuergschaftprozent) 
	{
		this.ivBuergschaftprozent = pvBuergschaftprozent;
	}

	/**
	 * Liefert das Konsortialkennzeichen
	 * @return the ivKennzeichenKonsortial
	 */
	public String getKennzeichenKonsortial() 
	{
		return ivKennzeichenKonsortial;
	}

	/**
	 * Setzt das Konsortialkennzeichen
	 * @param pvKennzeichenKonsortial the ivKennzeichenKonsortial to set
	 */
	public void setKennzeichenKonsortial(String pvKennzeichenKonsortial) 
	{
		this.ivKennzeichenKonsortial = pvKennzeichenKonsortial;
	}

	/**
	 * Liefert den Konsortialanteil
	 * @return the ivKonsortialanteil
	 */
	public String getKonsortialanteil() 
	{
		return ivKonsortialanteil;
	}

	/**
	 * Setzt den Konsortialanteil
	 * @param pvKonsortialanteil the ivKonsortialanteil to set
	 */
	public void setKonsortialanteil(String pvKonsortialanteil) 
	{
		this.ivKonsortialanteil = pvKonsortialanteil;
	}

    /**
     * Liefert das Restkapital (Netto)
     * @return
     */
    public String getRestkapitalNetto() 
    {
		return ivRestkapitalNetto;
	}

    /**
     * Setzt das Restkapital (Netto)
     * @param pvRestkapitalNetto
     */
	public void setRestkapitalNetto(String pvRestkapitalNetto) 
	{
		this.ivRestkapitalNetto = pvRestkapitalNetto;
	}

	/**
	 * Liefert das Restkapital (Brutto)
	 * @return
	 */
	public String getRestkapitalBrutto() 
	{
		return ivRestkapitalBrutto;
	}

	/**
	 * Setzt das Restkapital (Brutto)
	 * @param pvRestkapitalBrutto
	 */
	public void setRestkapitalBrutto(String pvRestkapitalBrutto) 
	{
		this.ivRestkapitalBrutto = pvRestkapitalBrutto;
	}
	
    /**
     * Liefert die Solldeckung (Netto)
     * @return
     */
	public String getSolldeckungNetto() 
	{
		return ivSolldeckungNetto;
	}

	/**
	 * Setzt die Solldeckung (Netto)
	 * @param pvSolldeckungNetto
	 */
	public void setSolldeckungNetto(String pvSolldeckungNetto) 
	{
		this.ivSolldeckungNetto = pvSolldeckungNetto;
	}

	/**
	 * Liefert die Solldeckung (Brutto)
	 * @return
	 */
	public String getSolldeckungBrutto() 
	{
		return ivSolldeckungBrutto;
	}

	/**
	 * Setzt die Solldeckung (Brutto)
	 * @param pvSolldeckungBrutto
	 */
	public void setSolldeckungBrutto(String pvSolldeckungBrutto) 
	{
		this.ivSolldeckungBrutto = pvSolldeckungBrutto;
	}

	/**
	 * Zerlegt eine Zeile in die unterschiedlichen Daten/Felder
	 * @param pvZeile die zu zerlegende Zeile
	 */
    public void parseLoanIQObjekt(String pvZeile)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
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

        lvOut.append("Kontonummer: ");
        lvOut.append(ivKontonummer);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Buergschaftprozent: ");
        lvOut.append(ivBuergschaftprozent);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("KennzeichenKonsortial: ");
        lvOut.append(ivKennzeichenKonsortial);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("Konsortialanteil: ");
        lvOut.append(ivKonsortialanteil);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("RestkapitalNetto: ");
        lvOut.append(ivRestkapitalNetto);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("RestkapitalBrutto: ");
        lvOut.append(ivRestkapitalBrutto);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("SolldeckungNetto: ");
        lvOut.append(ivSolldeckungNetto);
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append("SolldeckungBrutto: ");
        lvOut.append(ivSolldeckungBrutto);
        lvOut.append(StringKonverter.lineSeparator);

        return lvOut.toString();
    }

}
