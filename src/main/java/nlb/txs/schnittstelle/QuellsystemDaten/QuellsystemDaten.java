/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.QuellsystemDaten;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class QuellsystemDaten 
{
    /**
     * (Ursprungs-)Kontonummer 
     */
    private String ivUrsprungskontonummer;
    
    /**
     * Zielkontonummer
     */
    private String ivZielkontonummer;
    
    /**
     * Originator
     */
    private String ivOriginator;
    
    /**
     * Quellsystem
     */
    private String ivQuellsystem;
    
    /**
     * verbuergt (J/N)
     */
    private String ivVerbuergt;
    
    /**
     * Buergschaftprozent
     */
    private String ivBuergschaftprozent;
    
    /**
     * manuelle Tilgung
     */
    private String ivManTilg;
    
    /**
     * manuelle Zinsen
     */
    private String ivManZins;
    
    /**
     * Faelligkeit
     */
    private String ivFaelligkeit;
    
    /**
     * MerkmalZinstyp
     */
    private String ivMerkmalZinstyp;
    
    /**
     * Amortisierungsbetrag
     */
    private String ivAmortisierungsbetrag;

    /**
     * Nennbetrag
     */
    private String ivNennbetrag;

   /**
    * Restkapital
    */
   private String ivRestkapital;

  /**
   * Rollover Kennzeichen
   */
  private String ivRolloverKennzeichen;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor zur Initialisierung der Instanzvariablen
     */
    public QuellsystemDaten(Logger pvLogger)
    {
        super();
        ivLogger = pvLogger;
        ivUrsprungskontonummer = new String();
        ivZielkontonummer = new String();
        ivOriginator = new String();
        ivQuellsystem = new String();
        ivVerbuergt = new String();
        ivBuergschaftprozent = new String();
        ivManTilg = new String();
        ivManZins = new String();
        ivFaelligkeit = new String();
        ivMerkmalZinstyp = new String();
        ivAmortisierungsbetrag = new String();
        ivNennbetrag = new String();
        ivRestkapital = new String();
        ivRolloverKennzeichen = new String();
    }

    /**
     * Liefert die Ursprungskontonummer
     * @return the ursprungskontonummer
     */
    public String getUrsprungskontonummer() {
        return this.ivUrsprungskontonummer;
    }

    /**
     * Setzt die Ursprungskontonummer
     * @param pvUrsprungskontonummer the ursprungskontonummer to set
     */
    public void setUrsprungskontonummer(String pvUrsprungskontonummer) {
        this.ivUrsprungskontonummer = pvUrsprungskontonummer;
    }

    /**
     * Liefert die Zielkontonummer
     * @return the zielkontonummer
     */
    public String getZielkontonummer() {
        return this.ivZielkontonummer;
    }

    /**
     * Setzt die Zielkontonummer
     * @param pvZielkontonummer the zielkontonummer to set
     */
    public void setZielkontonummer(String pvZielkontonummer) {
        this.ivZielkontonummer = pvZielkontonummer;
    }

    /**
     * Liefert den Originator
     * @return the originator
     */
    public String getOriginator() {
        return this.ivOriginator;
    }

    /**
     * Setzt den Originator
     * @param pvOriginator the originator to set
     */
    public void setOriginator(String pvOriginator) {
        this.ivOriginator = pvOriginator;
    }

    /**
     * Liefert das Quellsystem
     * @return the quellsystem
     */
    public String getQuellsystem() {
        return this.ivQuellsystem;
    }

    /**
     * Setzt das Quellsystem
     * @param pvQuellsystem the quellsystem to set
     */
    public void setQuellsystem(String pvQuellsystem) {
        this.ivQuellsystem = pvQuellsystem;
    }

    /**
     * Liefert das Kennzeichen 'verbuergt'
     * @return the verbuergt
     */
    public String getVerbuergt() {
        return this.ivVerbuergt;
    }

    /**
     * Setzt das Kennzeichen 'verbuergt'
     * @param pvVerbuergt the verbuergt to set
     */
    public void setVerbuergt(String pvVerbuergt) {
        this.ivVerbuergt = pvVerbuergt;
    }

    /**
     * Liefert die Buergschaftprozente
     * @return the buergschaftprozent
     */
    public String getBuergschaftprozent() {
        return this.ivBuergschaftprozent;
    }

    /**
     * Setzt die Buergschaftprozente
     * @param pvBuergschaftprozent the buergschaftprozent to set
     */
    public void setBuergschaftprozent(String pvBuergschaftprozent) {
        this.ivBuergschaftprozent = pvBuergschaftprozent;
    } 
    
    /**
     * Liefert das Kennzeichen 'manuelle Tilgung'
     * @return
     */
    public String getManTilg() 
    {
		return ivManTilg;
	}

    /**
     * Setzt das Kennzeichen 'manuelle Tilgung'
     * @param pvManTilg
     */
	public void setManTilg(String pvManTilg) 
	{
		this.ivManTilg = pvManTilg;
	}

	/**
	 * Liefert das Kennzeichen 'manuelle Zinsen'
	 * @return
	 */
	public String getManZins() 
	{
		return ivManZins;
	}

	/**
	 * Setzt das Kennzeichen 'manuelle Zinsen'
	 * @param pvManZins
	 */
	public void setManZins(String pvManZins) 
	{
		this.ivManZins = pvManZins;
	}

	/**
	 * Liefert die Faelligkeit
	 * @return
	 */
	public String getFaelligkeit() 
	{
		return ivFaelligkeit;
	}

	/**
	 * Setzt die Faelligkeit
	 * @param pvFaelligkeit
	 */
	public void setFaelligkeit(String pvFaelligkeit) 
	{
		this.ivFaelligkeit = pvFaelligkeit;
	}

	/**
	 * Liefert das Zinstypmerkmal
	 * @return
	 */
	public String getMerkmalZinstyp() 
	{
		return ivMerkmalZinstyp;
	}

	/**
	 * Setzt das Zinstypmerkmal
	 * @param pvMerkmalZinstyp
	 */
	public void setMerkmalZinstyp(String pvMerkmalZinstyp) 
	{
		this.ivMerkmalZinstyp = pvMerkmalZinstyp;
	}

	/**
	 * Liefert den Amortisierungsbetrag
	 * @return
	 */
	public String getAmortisierungsbetrag() 
	{
		return ivAmortisierungsbetrag;
	}

	/**
	 * Setzt den Amortisierungsbetrag
	 * @param pvAmortisierungsbetrag
	 */
	public void setAmortisierungsbetrag(String pvAmortisierungsbetrag) 
	{
		this.ivAmortisierungsbetrag = pvAmortisierungsbetrag;
	}

  /**
   * Liefert den Nennbetrag
   * @return Nennbetrag
   */
  public String getNennbetrag()
  {
    return this.ivNennbetrag;
  }

  /**
   * Setzt den Nennbetrag
   * @param pvNennbetrag Nennbetrag
   */
  public void setNennbetrag(String pvNennbetrag)
  {
    this.ivNennbetrag = pvNennbetrag;
  }

  /**
   * Liefert das Restkapital
   */
  public String getRestkapital()
  {
    return this.ivRestkapital;
  }

  /**
   * Setzt das Restkapital
   */
  public void setRestkapital(String pvRestkapital)
  {
     this.ivRestkapital = pvRestkapital;
  }

  /**
   * Liefert das Rollover Kennzeichen
   */
  public String getRolloverKennzeichen()
  {
    return this.ivRolloverKennzeichen;
  }

  /**
     * Setzt einen Wert der QuellsystemDaten
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setQuellsystemDaten(int pvPos, String pvWert) 
    {
    	//ivLogger.info("Pos:" + pvPos + " - Wert:" + pvWert);
        switch (pvPos)
        {
          case 0:
              this.ivUrsprungskontonummer = pvWert;
              break;
          case 1:
              this.ivZielkontonummer = pvWert;
              break;
          case 2:
              this.ivOriginator = pvWert;
              break;
          case 3:
              this.ivQuellsystem = pvWert;
              break;
          case 4:
              this.ivVerbuergt = pvWert;
              break;
          case 5:
              this.ivBuergschaftprozent = pvWert;
              break;
          case 6:
        	  this.ivManTilg = pvWert;
        	  break;
          case 7:
        	  this.ivManZins = pvWert;
        	  break;
          case 8:
        	  this.ivFaelligkeit = pvWert;
        	  break;
          case 9:
        	  this.ivMerkmalZinstyp = pvWert;
        	  break;
          case 10:
        	  this.ivAmortisierungsbetrag = pvWert;
        	  break;
          case 11:
            this.ivNennbetrag = pvWert;
            break;
          case 12:
            this.ivRestkapital = pvWert;
            break;
          case 13:
            this.ivRolloverKennzeichen = pvWert;
            break;
          default:
              ivLogger.info("QuellsystemDaten: Feld " + pvPos + " - Wert: " + pvWert + " undefiniert");
        }
    }
}
