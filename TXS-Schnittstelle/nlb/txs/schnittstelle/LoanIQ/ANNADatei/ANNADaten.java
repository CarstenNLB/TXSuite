package nlb.txs.schnittstelle.LoanIQ.ANNADatei;

import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * 
 * @author tepperc
 *
 */
public class ANNADaten 
{
	/**
	 * Trennzeichen
	 */
	private final char ivTrennzeichen = '|';

    /**
     * ID_EXTERN_QUELLSYSTEM
     */
	private String ivIDExternQuellsystem;
	
	/**
	 * INSTITUTSNUMMER_QUELLSYSTEM
	 */
	private String ivInstitutsnummerQuellsystem;
		
	/**
	 * ANWENDUNGSNUMMER_QUELLSYSTEM
	 */
	private String ivAnwendungsnummerQuellsystem;
	
	/**
	 * KONTONUMMER_QUELLSYSTEM
	 */
	private String ivKontonummerQuellsystem;
	
	/**
	 * SATZART_QUELLSYSTEM
	 */
	private String ivSatzartQuellsystem;
	
	/**
	 * UNTERSCHLUESSEL_QUELLSYSTEM
	 */
	private String ivUnterschluesselQuellsystem;
	
	/**
	 * KUNDENNUMMER_SAP_ID
	 */
	private String ivKundennummerSAPID;
	
	/**
	 * ID_EXTERN_LOANIQ_OST_FAC
	 */
	private String ivIDExternLoanIQ;
	
	/**
	 * INSTITUTSNUMMER_LOANIQ
	 */
	private String ivInstitutsnummerLoanIQ;
	
	/**
	 * ANWENDUNGSNUMMER_LOANIQ
	 */
	private String ivAnwendungsnummerLoanIQ;
	
	/**
	 * SATZART_LOANIQ
	 */
	private String ivSatzartLoanIQ;
	
	/**
	 * UNTERSCHLUESSEL_LOANIQ
	 */
	private String ivUnterschluesselLoanIQ;
	
	/**
	 * TYP_DES_OUTSTANDING
	 */
	private String ivTypOutstanding;

	/**
	 * OUTSTANDING_ALIAS
	 */
	private String ivOutstandingAlias;
	
	/**
	 * FACILITY_NUMMER
	 */
	private String ivFacilityNummer;
	
	/**
	 * DEAL_NUMMER
	 */
	private String ivDealNummer;
	
	/**
	 * DEAL_NAME
	 */
	private String ivDealName;
	
	/**
	 * HAUPTKONTONUMMER_QUELLSYSTEM
	 */
	private String ivHauptkontonummerQuellsystem;
	
	/**
	 * KOMPENSATIONSKENNZEICHEN_QUELLSYSTEM
	 */
	private String ivKompensationskennzeichenQuellsystem;

	/**
	 * log4j-Logger
	 */
	private Logger ivLogger;

	/**
	 * Konstruktor - Initialisiert die Instanzvariablen
	 */
	public ANNADaten(Logger pvLogger)
	{
		ivLogger = pvLogger;
		this.ivIDExternQuellsystem = new String();
		this.ivInstitutsnummerQuellsystem = new String();
		this.ivAnwendungsnummerQuellsystem = new String();
		this.ivKontonummerQuellsystem = new String();
		this.ivSatzartQuellsystem = new String();
		this.ivUnterschluesselQuellsystem = new String();
		this.ivKundennummerSAPID = new String();
		this.ivIDExternLoanIQ = new String();
		this.ivInstitutsnummerLoanIQ = new String();
		this.ivAnwendungsnummerLoanIQ = new String();
		this.ivSatzartLoanIQ = new String();
		this.ivUnterschluesselLoanIQ = new String();
		this.ivTypOutstanding = new String();
		this.ivOutstandingAlias = new String();
		this.ivFacilityNummer = new String();
		this.ivDealNummer = new String();
		this.ivDealName = new String();
		this.ivHauptkontonummerQuellsystem = new String();
		this.ivKompensationskennzeichenQuellsystem = new String();
	}

    /**
     * Liefert die IDExternQuellsystem
     * @return
     */
	public String getIDExternQuellsystem() 
	{
		return ivIDExternQuellsystem;
	}

	/**
	 * Setzt die IDExternQuellsystem
	 * @param pvIDExternQuellsystem
	 */
	public void setIDExternQuellsystem(String pvIDExternQuellsystem) 
	{
		this.ivIDExternQuellsystem = pvIDExternQuellsystem;
	}

	/**
	 * Liefert die Institutsnummer des Quellsystems
	 * @return
	 */
	public String getInstitutsnummerQuellsystem() 
	{
		return ivInstitutsnummerQuellsystem;
	}

	/**
	 * Setzt die Institutsnummer des Quellsystems
	 * @param pvInstitutsnummerQuellsystem
	 */
	public void setInstitutsnummerQuellsystem(String pvInstitutsnummerQuellsystem) 
	{
		this.ivInstitutsnummerQuellsystem = pvInstitutsnummerQuellsystem;
	}

	/**
	 * Liefert die Anwendungsnummer des Quellsystems
	 * @return
	 */
	public String getAnwendungsnummerQuellsystem() 
	{
		return ivAnwendungsnummerQuellsystem;
	}

	/**
	 * Setzt die Anwendungsnummer des Quellsystems
	 * @param pvAnwendungsnummerQuellsystem
	 */
	public void setAnwendungsnummerQuellsystem(String pvAnwendungsnummerQuellsystem) 
	{
		this.ivAnwendungsnummerQuellsystem = pvAnwendungsnummerQuellsystem;
	}

	/**
	 * Liefert die Kontonummer des Quellsystems
	 * @return
	 */
	public String getKontonummerQuellsystem() 
	{
		return ivKontonummerQuellsystem;
	}

	/**
	 * Setzt die Kontonummer des Quellsystems
	 * @param pvKontonummerQuellsystem
	 */
	public void setKontonummerQuellsystem(String pvKontonummerQuellsystem) 
	{
		this.ivKontonummerQuellsystem = pvKontonummerQuellsystem;
	}

	/**
	 * Liefert die Satzart des Quellsystems
	 * @return
	 */
	public String getSatzartQuellsystem() 
	{
		return ivSatzartQuellsystem;
	}

	/**
	 * Setzt die Satzart des Quellsystems
	 * @param pvSatzartQuellsystem
	 */
	public void setSatzartQuellsystem(String pvSatzartQuellsystem) 
	{
		this.ivSatzartQuellsystem = pvSatzartQuellsystem;
	}

	/**
	 * Liefert den Unterschluessel des Quellsystems
	 * @return
	 */
	public String getUnterschluesselQuellsystem() 
	{
		return ivUnterschluesselQuellsystem;
	}

	/**
	 * Setzt den Unterschluessel des Quellsystems
	 * @param pvUnterschluesselQuellsystem
	 */
	public void setUnterschluesselQuellsystem(String pvUnterschluesselQuellsystem) 
	{
		this.ivUnterschluesselQuellsystem = pvUnterschluesselQuellsystem;
	}

	/**
	 * Liefert die KundennummerSAPID
	 * @return
	 */
	public String getKundennummerSAPID() 
	{
		return ivKundennummerSAPID;
	}

	/**
	 * Setzt die KundennummerSAPID
	 * @param pvKundennummerSAPID
	 */
	public void setKundennummerSAPID(String pvKundennummerSAPID) 
	{
		this.ivKundennummerSAPID = pvKundennummerSAPID;
	}

	/**
	 * Liefert die IDExternLoanIQ
	 * @return
	 */
	public String getIDExternLoanIQ() 
	{
		return ivIDExternLoanIQ;
	}

	/**
	 * Setzt die IDExternLoanIQ
	 * @param pvIDExternLoanIQ
	 */
	public void setIDExternLoanIQ(String pvIDExternLoanIQ) 
	{
		this.ivIDExternLoanIQ = pvIDExternLoanIQ;
	}

	/**
	 * Liefert die InstitutsnummerLoanIQ
	 * @return
	 */
	public String getInstitutsnummerLoanIQ() 
	{
		return ivInstitutsnummerLoanIQ;
	}

	/**
	 * Setzt die InstitutsnummerLoanIQ
	 * @param pvInstitutsnummerLoanIQ
	 */
	public void setInstitutsnummerLoanIQ(String pvInstitutsnummerLoanIQ) 
	{
		this.ivInstitutsnummerLoanIQ = pvInstitutsnummerLoanIQ;
	}

	/**
	 * Liefert AnwendungsnummerLoanIQ
	 * @return
	 */
	public String getAnwendungsnummerLoanIQ() 
	{
		return ivAnwendungsnummerLoanIQ;
	}

	/**
	 * Setzt die AnwendungsnummerLoanIQ
	 * @param pvAnwendungsnummerLoanIQ
	 */
	public void setAnwendungsnummerLoanIQ(String pvAnwendungsnummerLoanIQ) 
	{
		this.ivAnwendungsnummerLoanIQ = pvAnwendungsnummerLoanIQ;
	}

	/**
	 * Liefert die SatzartLoanIQ
	 * @return
	 */
	public String getSatzartLoanIQ() 
	{
		return ivSatzartLoanIQ;
	}

	/**
	 * Setzt die SatzartLoanIQ
	 * @param pvSatzartLoanIQ
	 */
	public void setSatzartLoanIQ(String pvSatzartLoanIQ) 
	{
		this.ivSatzartLoanIQ = pvSatzartLoanIQ;
	}

	/**
	 * Liefert den UnterschluesselLoanIQ
	 * @return
	 */
	public String getUnterschluesselLoanIQ() 
	{
		return ivUnterschluesselLoanIQ;
	}

	/**
	 * Setzt den UnterschluesselLoanIQ
	 * @param pvUnterschluesselLoanIQ
	 */
	public void setUnterschluesselLoanIQ(String pvUnterschluesselLoanIQ) 
	{
		this.ivUnterschluesselLoanIQ = pvUnterschluesselLoanIQ;
	}

	/**
	 * Liefert den TypOutstanding
	 * @return
	 */
	public String getTypOutstanding() 
	{
		return ivTypOutstanding;
	}

	/**
	 * Setzt den TypOutstanding
	 * @param pvTypOutstanding
	 */
	public void setTypOutstanding(String pvTypOutstanding) 
	{
		this.ivTypOutstanding = pvTypOutstanding;
	}

	/**
	 * Liefert den OustandingAlias
	 * @return
	 */
	public String getOutstandingAlias() 
	{
		return ivOutstandingAlias;
	}

	/**
	 * Setzt den OutstandingAlias
	 * @param pvOutstandingAlias
	 */
	public void setOutstandingAlias(String pvOutstandingAlias) 
	{
		this.ivOutstandingAlias = pvOutstandingAlias;
	}

	/**
	 * Liefert die FacilityNummer
	 * @return
	 */
	public String getFacilityNummer() 
	{
		return ivFacilityNummer;
	}

	/**
	 * Setzt die FacilityNummer
	 * @param pvFacilityNummer
	 */
	public void setFacilityNummer(String pvFacilityNummer) 
	{
		this.ivFacilityNummer = pvFacilityNummer;
	}

	/**
	 * Liefert die DealNummer
	 * @return
	 */
	public String getDealNummer() 
	{
		return ivDealNummer;
	}

	/**
	 * Setzt die DealNummer
	 * @param pvDealNummer
	 */
	public void setDealNummer(String pvDealNummer) 
	{
		this.ivDealNummer = pvDealNummer;
	}

	/**
	 * Liefert den DealName
	 * @return
	 */
	public String getDealName() 
	{
		return ivDealName;
	}

	/**
	 * Setzt den DealName
	 * @param pvDealName
	 */
	public void setDealName(String pvDealName) 
	{
		this.ivDealName = pvDealName;
	}

	/**
	 * Liefert die HauptkontonummerQuellsystem
	 * @return
	 */
	public String getHauptkontonummerQuellsystem() 
	{
		return ivHauptkontonummerQuellsystem;
	}

	/**
	 * Setzt die HauptkontonummerQuellsystem
	 * @param pvHauptkontonummerQuellsystem
	 */
	public void setHauptkontonummerQuellsystem(String pvHauptkontonummerQuellsystem) 
	{
		this.ivHauptkontonummerQuellsystem = pvHauptkontonummerQuellsystem;
	}

	/**
	 * Liefert das KompensationskennzeichenQuellsystem
	 * @return
	 */
	public String getKompensationskennzeichenQuellsystem() 
	{
		return ivKompensationskennzeichenQuellsystem;
	}

	/**
	 * Setzt das KompensationskennzeichenQuellsystem
	 * @param pvKompensationskennzeichenQuellsystem
	 */
	public void setKompensationskennzeichenQuellsystem(String pvKompensationskennzeichenQuellsystem) 
	{
		this.ivKompensationskennzeichenQuellsystem = pvKompensationskennzeichenQuellsystem;
	}

	/**
     * Zerlegt eine Zeile der ANNA-Datei 
     * @param pvZeile die zu zerlegende Zeile
     */
	public void parseANNADaten(String pvZeile)
	{
      String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
      int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

      // steuerung/iteration eingabesatz
      for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
      {

        // wenn Trennzeichen erkannt
        if (pvZeile.charAt(lvZzStr) == ivTrennzeichen)
        {
          this.setANNADaten(lvLfd, lvTemp);
          lvLfd++;                  // naechste Feldnummer
          // Zwischenbuffer loeschen
          lvTemp = new String();
        }
        else
        {
           // uebernehmen byte aus eingabesatzposition
           lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
        }
      } // ende for
	}
    
    /**
     * Setzt einen Wert der ANNA-Daten
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setANNADaten(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
          case 0:
              this.setIDExternQuellsystem(pvWert);
              break;
          case 1:
              this.setInstitutsnummerQuellsystem(pvWert);
              break;
          case 2:
              this.setAnwendungsnummerQuellsystem(pvWert);
              break;
          case 3:
              this.setKontonummerQuellsystem(pvWert);
              break;
          case 4:
        	  this.setSatzartQuellsystem(pvWert);
        	  break;
          case 5:
        	  this.setUnterschluesselQuellsystem(pvWert);
        	  break;
          case 6:
        	  this.setKundennummerSAPID(pvWert);
        	  break;
          case 7:
        	  this.setIDExternLoanIQ(pvWert);
        	  break;
          case 8:
        	  this.setInstitutsnummerLoanIQ(pvWert);
        	  break;
          case 9:
        	  this.setAnwendungsnummerLoanIQ(pvWert);
        	  break;
          case 10:
        	  this.setSatzartLoanIQ(pvWert);
        	  break;
          case 11:
        	  this.setUnterschluesselLoanIQ(pvWert);
        	  break;
          case 12:
        	  this.setTypOutstanding(pvWert);
        	  break;
          case 13:
        	  this.setOutstandingAlias(pvWert);
        	  break;
          case 14:
        	  this.setFacilityNummer(pvWert);
        	  break;
          case 15:
        	  this.setDealNummer(pvWert);
        	  break;
          case 16:
        	  this.setDealName(pvWert);
        	  break;
          case 17:
        	  this.setHauptkontonummerQuellsystem(pvWert);
        	  break;
          case 18:
        	  this.setKompensationskennzeichenQuellsystem(pvWert);
        	  break;
           default:
              ivLogger.info("ANNA-Daten: undefiniert - Position: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Liefert einen String mit den Inhalten der ANNA-Daten zurueck
     * @return Inhalt der ANNA-Daten
     */
    public String toString()
    {
        StringBuilder lvHelpString = new StringBuilder();
        
        lvHelpString.append("IDExternQuellsystem: " + this.ivIDExternQuellsystem + StringKonverter.lineSeparator);
        lvHelpString.append("InstitutsnummerQuellsystem: " + this.ivInstitutsnummerQuellsystem + StringKonverter.lineSeparator);
        lvHelpString.append("AnwendungsnummerQuellsystem: " + this.ivAnwendungsnummerQuellsystem + StringKonverter.lineSeparator);
        lvHelpString.append("KontonummerQuellsystem: " + this.ivKontonummerQuellsystem + StringKonverter.lineSeparator);
        lvHelpString.append("SatzartQuellsystem: " + this.ivSatzartQuellsystem + StringKonverter.lineSeparator);
        lvHelpString.append("UnterschluesselQuellsystem: " + this.ivUnterschluesselQuellsystem + StringKonverter.lineSeparator);
        lvHelpString.append("KundennummerSAPID: " + this.ivKundennummerSAPID + StringKonverter.lineSeparator);
        lvHelpString.append("IDExternLoanIQ: " + this.ivIDExternLoanIQ + StringKonverter.lineSeparator);
        lvHelpString.append("InstitutsnummerLoanIQ: " + this.ivInstitutsnummerLoanIQ + StringKonverter.lineSeparator);
        lvHelpString.append("AnwendungsnummerLoanIQ: " + this.ivAnwendungsnummerLoanIQ + StringKonverter.lineSeparator);
        lvHelpString.append("SatzartLoanIQ: " + this.ivSatzartLoanIQ + StringKonverter.lineSeparator);
        lvHelpString.append("UnterschluesselLoanIQ: " + this.ivUnterschluesselLoanIQ + StringKonverter.lineSeparator);
        lvHelpString.append("TypOutstanding: " + this.ivTypOutstanding + StringKonverter.lineSeparator);
        lvHelpString.append("OutstandingAlias: " + this.ivOutstandingAlias + StringKonverter.lineSeparator);
        lvHelpString.append("FacilityNummer: " + this.ivFacilityNummer + StringKonverter.lineSeparator);
        lvHelpString.append("DealNummer: " + this.ivDealNummer + StringKonverter.lineSeparator);
        lvHelpString.append("DealName: " + this.ivDealName + StringKonverter.lineSeparator);
        lvHelpString.append("HauptkontonummerQuellsystem: " + this.ivHauptkontonummerQuellsystem + StringKonverter.lineSeparator);
        lvHelpString.append("KompensationskennzeichenQuellsystem: " + this.ivKompensationskennzeichenQuellsystem + StringKonverter.lineSeparator);
        
        return lvHelpString.toString(); 
    }
}
