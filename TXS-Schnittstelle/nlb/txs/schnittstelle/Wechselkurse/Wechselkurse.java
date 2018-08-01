package nlb.txs.schnittstelle.Wechselkurse;

public class Wechselkurse
{
  /**
   * Instituts-Nr. 
   */
  private String ivInstNr;

  /**
   * Währungscode
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
   * Quellsystem fuer EDR
   */
  private String ivQuellsystem;
  
  /**
   * Datum Gültigkeit TT.MM.JJJJ
   */
  private String ivGueltigkeitDatum;

  /**
   * Uhrzeit der Feststellung
   */
  private String ivGueltigkeitUhrzeit;

  /**
   * Anbieter/Lieferant
   */
  private String ivAnbieter;

  /**
   * Marktplatz
   */
  private String ivMarktplatz;

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

}