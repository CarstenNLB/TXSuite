package nlb.txs.schnittstelle.Zinskurse;


public class Zinskurse
{
  /**
   * Instituts-Nr.
   */
  private String ivInstNr;

  /**
   * Währungscode ISO
   */
  private String ivWaehrungscode;

  /**
   * Mandant
   */
  private String ivMandant;

  /**
   * Buchungskreis
   */
  private String ivBuchungskreis;

  /**
   * Quellensystem
   */
  private String ivQuellsystem;
  
  /**
   * Laufzeit
   */
  private String ivLaufzeit;

  /**
   * Datum Gültigkeit des Kurses
   */
  private String ivGueltigkeitDatum;

  /**
   * Uhrzeit der Feststellung
   */
  private String ivGueltigkeitUhrzeit;

  /**
   * Marktplatz zum Kurs
   */
  private String ivMarktplatz;

  /**
   * Anbieter des Kurses
   */
  private String ivAnbieter;

  /**
   * Identifikation des Kurses
   */
  private String ivIdentifikation;

  /**
   * Briefkurs
   */
  private String ivBriefkurs;

  /**
   * Geldkurs
   */
  private String ivGeldkurs;

  /**
   * Mittelkurs
   */
  private String ivMittelkurs;

  /**
   * Verzinsungsfrequenz
   */
  private String ivVerzinsungsfrequenz;

  /**
   * Tageszaehlmethode 
   */
    private String ivTageszaehlmethode;

  /**
   * Zinsmethode
   */
    private String ivZinsmethode;

  /**
   * Valutatage
   */
    private String ivValutatage;

  /**
   * Zahlungsfrequenz
   */
    private String ivZahlungsfrequenz;

  /**
   * Quotierungsart
   */
    private String ivQuotierungsart;

  /**
   * Arbeitstagekonvention
   */
    private String ivArbeitstagekonvention;

  /**
   * SpezArbeitstageKonv
   */
    private String ivSpezarbeitstagekonvention;

  /**
   * Monatsendekonvention
   */
    private String ivMonatsendekonvention;

  /**
   * Bankkalender
   */
    private String ivBankkalender;

 /**
      * Setzt die Instituts-Nr.
      * @param pvIn 
      */
  public void setInstNr(String pvIn)
  {
        this.ivInstNr = pvIn;
  } 

  /**
   * Liefert die Instituts-Nr.
   * @return 
   */
  public String getInstNr()
  {
    return this.ivInstNr;
  }

    /**
     * Setzt den Währungscode
     * @param pvWc 
     */
  public void setWaehrungscode(String pvWc)
  {
        this.ivWaehrungscode = pvWc;
  } 

  /**
   * Liefert den Währungscode
   * @return 
   */
  public String getWaehrungscode()
  {
    return this.ivWaehrungscode;
  }

    /**
     * Setzt den Mandant
     * @param pvMa 
     */
  public void setMandant(String pvMa)
  {
        this.ivMandant = pvMa;
  } 

  /**
   * Liefert den Mandant
   * @return 
   */
  public String getMandant()
  {
    return this.ivMandant;
  }

    /**
     * Setzt den Buchungskreis
     * @param pvPk 
     */
  public void setBuchungskreis(String pvPk)
  {
        this.ivBuchungskreis = pvPk;
  } 

  /**
   * Liefert den Buchungskreis
   * @return 
   */
  public String getBuchungskreis()
  {
    return this.ivBuchungskreis;
  }

    /**
     * Setzt das Quellsystem
     * @param pvQs 
     */
  public void setQuellsystem(String pvQs)
  {
        this.ivQuellsystem = pvQs;
  } 

  /**
   * Liefert das Quellsystem
   * @return 
   */
  public String getQuellsystem()
  {
    return this.ivQuellsystem;
  }

    /**
     * Setzt die Laufzeit
     * @param pvLz 
     */
  public void setLaufzeit(String pvLz)
  {
        if (pvLz.equals("1ON"))
            this.ivLaufzeit = pvLz.substring(1);
	else
            this.ivLaufzeit = pvLz;
  } 

  /**
   * Liefert die Laufzeit
   * @return 
   */
  public String getLaufzeit()
  {
    return this.ivLaufzeit;
  }

    /**
     * Setzt das Datum Gültigkeit TT.MM.JJJJ
     * @param pvGd 
     */
  public void setGueltigkeitDatum(String pvGd)
  {
        this.ivGueltigkeitDatum = pvGd;
  } 

  /**
   * Liefert das Datum Gültigkeit TT.MM.JJJJ
   * @return 
   */
  public String getGueltigkeitDatum()
  {
    return this.ivGueltigkeitDatum;
  }

    /**
     * Setzt die Uhrzeit der Feststellung
     * @param pvGu 
     */
  public void setGueltigkeitUhrzeit(String pvGu)
  {
        this.ivGueltigkeitUhrzeit = pvGu;
  } 

  /**
   * Liefert die Uhrzeit der Feststellung
   * @return 
   */
  public String getGueltigkeitUhrzeit()
  {
    return this.ivGueltigkeitUhrzeit;
  }

    /**
     * Setzt den Anbieter
     * @param pvAn 
     */
  public void setAnbieter(String pvAn)
  {
        this.ivAnbieter = pvAn;
  } 

  /**
   * Liefert den Anbieter
   * @return 
   */
  public String getAnbieter()
  {
    return this.ivAnbieter;
  }

    /**
     * Setzt den Marktplatz
     * @param pvMp 
     */
  public void setMarktplatz(String pvMp)
  {
        this.ivMarktplatz = pvMp;
  } 

  /**
   * Liefert den Marktplatz
   * @return 
   */
  public String getMarktplatz()
  {
    return this.ivMarktplatz;
  }

  /**
   * Liefert die Identifikation des Kurses
   * @return 
   */
  public String getIdentifikation()
  {
    return this.ivIdentifikation;
  }

    /**
      * Setzt die Identifikation des Kurses
      * @param pvId 
      */
  public void setIdentifikation(String pvId)
  {
        if (pvId.startsWith("1ON"))
            this.ivIdentifikation = pvId.substring(1);
	else
            this.ivIdentifikation = pvId;
  }

    /**
     * Setzt den Briefkurs
     * @param pvBk 
     */
  public void setBriefkurs(String pvBk)
  {
        this.ivBriefkurs = pvBk;
  } 

  /**
   * Liefert den Briefkurs
   * @return 
   */
  public String getBriefkurs()
  {
    return this.ivBriefkurs;
  }

    /**
     * Setzt den Geldkurs
     * @param pvGk 
     */
  public void setGeldkurs(String pvGk)
  {
        this.ivGeldkurs = pvGk;
  } 

  /**
   * Liefert den Geldkurs
   * @return 
   */
  public String getGeldkurs()
  {
    return this.ivGeldkurs;
  }

    /**
     * Setzt den Mittelkurs
     * @param pvMk 
     */
  public void setMittelkurs(String pvMk)
  {
        this.ivMittelkurs = pvMk;
  } 

  /**
   * Liefert den Mittelkurs
   * @return 
   */
  public String getMittelkurs()
  {
    return this.ivMittelkurs;
  }

  /**
   * Liefert die Verzinsungsfrequenz
   * @return 
   */
  public String getVerzinsungsfrequenz()
  {
    return this.ivVerzinsungsfrequenz;
  }

    /**
     * Setzt die Verzinsungsfrequenz
     * @param pvVf  
     */
  public void setVerzinsungsfrequenz(String pvVf)
  {
        this.ivVerzinsungsfrequenz = pvVf;
  }

  /**
   * Liefert die Tageszaehlmethode
   * @return 
   */
  public String getTageszaehlmethode()
  {
        return this.ivTageszaehlmethode;
  }

    /**
     * Setzt die Tageszaehlmethode
     * @param pvTzm 
     */
  public void setTageszaehlmethode(String pvTzm)
  {
        this.ivTageszaehlmethode = pvTzm;
  }

 /**
   * Liefert die Zinsmethode
   * @return 
   */
  public String getZinsmethode()
  {
        return this.ivZinsmethode;
  }

    /**
     * Setzt die Zinsmethode
     * @param pvZm 
     */
  public void setZinsmethode(String pvZm)
  {
        this.ivZinsmethode = pvZm;
  }

  /**
   * Liefert die Valutatage
   * @return 
   */
  public String getValutatage()
  {
        return this.ivValutatage;
  }

    /**
     * Setzt die Valutatage
     * @param pvVt 
     */
  public void setValutatage(String pvVt)
  {
        this.ivValutatage = pvVt;
  }

  /**
   * Liefert die Zahlungsfrequenz
   * @return 
   */
  public String getZahlungsfrequenz()
  {
        return this.ivZahlungsfrequenz;
  }

    /**
     * Setzt die Zahlungsfrequenz
     * @param pvZf 
     */
  public void setZahlungsfrequenz(String pvZf)
  {
        this.ivZahlungsfrequenz = pvZf;
  }

  /**
   * Liefert die Quotierungsart
   * @return 
   */
  public String getQuotierungsart()
  {
        return this.ivQuotierungsart;
  }

    /**
     * Setzt die Quotierungsart
     * @param pvQa 
     */
  public void setQuotierungsart(String pvQa)
  {
        this.ivQuotierungsart = pvQa;
  }

  /**
   * Liefert die Arbeitstagekonvention
   * @return 
   */
  public String getArbeitstagekonvention()
  {
        return this.ivArbeitstagekonvention;
  }

    /**
     * Setzt die Arbeitstagekonvention
     * @param pvAk 
     */
  public void setArbeitstagekonvention(String pvAk)
  {
        this.ivArbeitstagekonvention = pvAk;
  }

  /**
   * Liefert die SpezArbeitstageKonv
   * @return 
   */
  public String getSpezArbeitstagekonv()
  {
        return this.ivSpezarbeitstagekonvention;
  }

    /**
     * Setzt die SpezArbeitstageKonv
     * @param pvSak 
     */
  public void setSpezArbeitstagekonv(String pvSak)
  {
        this.ivSpezarbeitstagekonvention = pvSak;
  }

  /**
   * Liefert die Monatsendekonvention
   * @return 
   */
  public String getMonatsendekonvention()
  {
        return this.ivMonatsendekonvention;
  }

    /**
     * Setzt die Monatsendekonvention
     * @param pvMk 
     */
  public void setMonatsendekonvention(String pvMk)
  {
        this.ivMonatsendekonvention = pvMk;
  }

  /**
   * Liefert den Bankkalender
   * @return 
   */
  public String getBankkalender()
  {
        return this.ivBankkalender;
  }

    /**
     * Setzt den Bankkalender
     * @param pvBk 
     */
  public void setBankkalender(String pvBk)
  {
        this.ivBankkalender = pvBk;
  }
}