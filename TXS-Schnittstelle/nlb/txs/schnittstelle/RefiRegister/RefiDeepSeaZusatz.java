package nlb.txs.schnittstelle.RefiRegister;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

@Deprecated
public class RefiDeepSeaZusatz 
{
    /**
     * Kontonummer
     */
    private String ivKontonummer;
     
    /**
     * Sicherheiten-ID
     */
    private String ivSicherheitenID;
    
    /**
     * Objekt-ID
     */
    private String ivObjektID;
    
    /**
     * Kundennummer
     */
    private String ivKundennummer;
    
    /**
     * Nominalbetrag
     */
    private String ivNominalbetrag;
    
    /**
     * Waehrung des Nominalbetrags
     */
    private String ivNominalbetragWaehrung;
    
    /**
     * Quelle der Angabe des Nominalbetrags
     */
    private String ivNominalbetragQuelle;
    
    /**
     * Schiffsregisterblatt
     */
    private String ivSchiffsregister;
    
    /**
     * Rechtlicher Grund
     */
    private String ivRechtlicherGrund;
    
    /**
     * Datum rechtlicher Grund
     */
    private String ivRechtlicherGrundDatum;
    
    /**
     * Konsortialanteil der NORD/LB (absolut)
     */
    private String ivKonsortialanteil;

	/**
	 * IMO-Nummer des Schiffs
	 */
	private String ivIMONummer;

	/**
	 * Info RefiRegister (Spalte 7)
	 */
	private String ivBemerkung;

	/**
	 * Konstruktor
     */
    public RefiDeepSeaZusatz() 
    {
        this.ivKontonummer = new String();
        this.ivSicherheitenID = new String();
        this.ivObjektID = new String();
        this.ivKundennummer = new String();
        this.ivNominalbetrag = new String();
        this.ivNominalbetragWaehrung = new String();
        this.ivNominalbetragQuelle = new String();
        this.ivSchiffsregister = new String();
        this.ivRechtlicherGrund = new String();
        this.ivRechtlicherGrundDatum = new String();
        this.ivKonsortialanteil = new String();
        this.ivIMONummer = new String();
        this.ivBemerkung = new String();
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
	 * @return the ivSicherheitenID
	 */
	public String getSicherheitenID() 
	{
		return ivSicherheitenID;
	}

	/**
	 * @param pvSicherheitenID the ivSicherheitenID to set
	 */
	public void setSicherheitenID(String pvSicherheitenID) 
	{
		this.ivSicherheitenID = pvSicherheitenID;
	}

	/**
	 * @return the ivObjektID
	 */
	public String getObjektID() 
	{
		return ivObjektID;
	}

	/**
	 * @param pvObjektID the ivObjektID to set
	 */
	public void setObjektID(String pvObjektID) 
	{
		this.ivObjektID = pvObjektID;
	}

	/**
	 * @return the ivKundennummer
	 */
	public String getKundennummer() 
	{
		return ivKundennummer;
	}

	/**
	 * @param pvKundennummer the ivKundennummer to set
	 */
	public void setKundennummer(String pvKundennummer) 
	{
		this.ivKundennummer = pvKundennummer;
	}

	/**
	 * @return the ivNominalbetrag
	 */
	public String getNominalbetrag() 
	{
		return ivNominalbetrag;
	}

	/**
	 * @param pvNominalbetrag the ivNominalbetrag to set
	 */
	public void setNominalbetrag(String pvNominalbetrag) 
	{
		this.ivNominalbetrag = pvNominalbetrag;
	}

	/**
	 * @return the ivNominalbetragWaehrung
	 */
	public String getNominalbetragWaehrung() 
	{
		return ivNominalbetragWaehrung;
	}

	/**
	 * @param pvNominalbetragWaehrung the ivNominalbetragWaehrung to set
	 */
	public void setNominalbetragWaehrung(String pvNominalbetragWaehrung) 
	{
		this.ivNominalbetragWaehrung = pvNominalbetragWaehrung;
	}

	/**
	 * @return the ivNominalbetragQuelle
	 */
	public String getNominalbetragQuelle() 
	{
		return ivNominalbetragQuelle;
	}

	/**
	 * @param pvNominalbetragQuelle the ivNominalbetragQuelle to set
	 */
	public void setNominalbetragQuelle(String pvNominalbetragQuelle) 
	{
		this.ivNominalbetragQuelle = pvNominalbetragQuelle;
	}

	/**
	 * @return the ivSchiffsregister
	 */
	public String getSchiffsregister() 
	{
		return ivSchiffsregister;
	}

	/**
	 * @param pvSchiffsregister the ivSchiffsregister to set
	 */
	public void setSchiffsregister(String pvSchiffsregister) 
	{
		this.ivSchiffsregister = pvSchiffsregister;
	}

	/**
	 * @return the ivRechtlicherGrund
	 */
	public String getRechtlicherGrund() 
	{
		return ivRechtlicherGrund;
	}

	/**
	 * @param pvRechtlicherGrund the ivRechtlicherGrund to set
	 */
	public void setRechtlicherGrund(String pvRechtlicherGrund) 
	{
		this.ivRechtlicherGrund = pvRechtlicherGrund;
	}

	/**
	 * @return the ivRechtlicherGrundDatum
	 */
	public String getRechtlicherGrundDatum() 
	{
		return ivRechtlicherGrundDatum;
	}

	/**
	 * @param pvRechtlicherGrundDatum the ivRechtlicherGrundDatum to set
	 */
	public void setRechtlicherGrundDatum(String pvRechtlicherGrundDatum)
	{
		this.ivRechtlicherGrundDatum = pvRechtlicherGrundDatum;
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
	public String getMONummer() {
		return ivIMONummer;
	}

	/**
	 *
	 * @param pvIMONummer
	 */
	public void setIMONummer(String pvIMONummer) {
		this.ivIMONummer = pvIMONummer;
	}

	/**
	 *
	 * @return
	 */
	public String getBemerkung() {
		return ivBemerkung;
	}

	/**
	 *
	 * @param pvBemerkung
	 */
	public void setBemerkung(String pvBemerkung) {
		this.ivBemerkung = pvBemerkung;
	}

	/**
     * @param pvZeile 
     * @param pvAnzahlZeilen
     * @return 
     * 
     */
    public boolean parseRefiDeepSeaZusatz(String pvZeile, int pvAnzahlZeilen)
    {
    	String lvTemp = new String();  // arbeitsbereich/zwischenspeicher feld
    	int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
    	int    lvZzStr=0;              // pointer fuer satzbereich
     
    	// steuerung/iteration eingabesatz
    	for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
    	{

    		// wenn Semikolon erkannt
    		if (pvZeile.charAt(lvZzStr) == ';')
    		{
    			this.setRefiDeepSeaZusatz(lvLfd, lvTemp, pvAnzahlZeilen);
       
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
    	this.setRefiDeepSeaZusatz(lvLfd, lvTemp, pvAnzahlZeilen);     
    
    	return true;
    }
    
    /**
     * Setzt einen Wert des RefiDeepSeaZusatz
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setRefiDeepSeaZusatz(int pvPos, String pvWert, int pvAnzahlZeilen)
    {
        switch (pvPos)
        {
            case 0:
                this.setKontonummer(pvWert);
                break;
            case 1:
              this.setSicherheitenID(pvWert);
              break;
            //case 2:
            //  this.setObjektID(pvWert);
            //  break;
            case 2:
              this.setKundennummer(pvWert);
              break;
            case 3:
              this.setNominalbetrag(pvWert.replace(".", ""));
              break;
            case 4:
              this.setNominalbetragWaehrung(pvWert);
              break;
            //case 6:
            //  this.setNominalbetragQuelle(pvWert); //.replace("%", ""));
            //  break;
            //case 7:
            //  this.setSchiffsregister(pvWert); //.replace("#", ";"));
            //  break;
            case 5:
              this.setRechtlicherGrund(pvWert);
              //System.out.println("Zeile " + (pvAnzahlZeilen + 1) + ": " + pvWert);
              break;
            case 6:
              this.setRechtlicherGrundDatum(pvWert); //.replace("#", ";"));
              break;
            case 7:
            	this.setKonsortialanteil(pvWert);
            	break;
					case 8:
						this.setIMONummer(pvWert);
						break;
					case 9:
						this.setBemerkung(pvWert);
						break;
          default:
              //this.setBemerkung(this.getBemerkung() + ";" + pvWert);
              System.out.println("Zeile " + (pvAnzahlZeilen + 1) + " - RefiDeepSeaZusatz: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des RefiRegisterFilterElements in einen String
     * @return
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Kontonummer: " + ivKontonummer + StringKonverter.lineSeparator);
        lvOut.append("SicherheitenID: " + ivSicherheitenID + StringKonverter.lineSeparator);
        lvOut.append("ObjektID: " + ivObjektID + StringKonverter.lineSeparator);
        lvOut.append("Kundennummer: " + ivKundennummer + StringKonverter.lineSeparator);
        lvOut.append("Nominalbetrag: " + ivNominalbetrag + StringKonverter.lineSeparator);
        lvOut.append("NominalbetragWaehrung: " + ivNominalbetragWaehrung + StringKonverter.lineSeparator);
        lvOut.append("NominalbetragQuelle: " + ivNominalbetragQuelle + StringKonverter.lineSeparator);
        lvOut.append("Schiffsregister: " + ivSchiffsregister + StringKonverter.lineSeparator);
        lvOut.append("Rechtlicher Grund: " + ivRechtlicherGrund + StringKonverter.lineSeparator);
        lvOut.append("Rechtlicher Grund Datum: " + ivRechtlicherGrundDatum + StringKonverter.lineSeparator);
        lvOut.append("Konsortialanteil: " + ivKonsortialanteil + StringKonverter.lineSeparator);
        lvOut.append("IMO-Nummer: " + ivIMONummer + StringKonverter.lineSeparator);
        lvOut.append("Bemerkung: " + ivBemerkung + StringKonverter.lineSeparator);

        return lvOut.toString();
    }
}
