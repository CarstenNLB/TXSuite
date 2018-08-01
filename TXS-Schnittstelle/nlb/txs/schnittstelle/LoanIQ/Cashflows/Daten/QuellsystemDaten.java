/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.Cashflows.Daten;

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
     * Konstruktor
     */
    public QuellsystemDaten()
    {
        super();
        ivUrsprungskontonummer = new String();
        ivZielkontonummer = new String();
        ivOriginator = new String();
        ivQuellsystem = new String();
        ivVerbuergt = new String();
        ivBuergschaftprozent = new String();
    }

    /**
     * @return the ursprungskontonummer
     */
    public String getUrsprungskontonummer() {
        return this.ivUrsprungskontonummer;
    }

    /**
     * @param pvUrsprungskontonummer the ursprungskontonummer to set
     */
    public void setUrsprungskontonummer(String pvUrsprungskontonummer) {
        this.ivUrsprungskontonummer = pvUrsprungskontonummer;
    }

    /**
     * @return the zielkontonummer
     */
    public String getZielkontonummer() {
        return this.ivZielkontonummer;
    }

    /**
     * @param pvZielkontonummer the zielkontonummer to set
     */
    public void setZielkontonummer(String pvZielkontonummer) {
        this.ivZielkontonummer = pvZielkontonummer;
    }

    /**
     * @return the originator
     */
    public String getOriginator() {
        return this.ivOriginator;
    }

    /**
     * @param pvOriginator the originator to set
     */
    public void setOriginator(String pvOriginator) {
        this.ivOriginator = pvOriginator;
    }

    /**
     * @return the quellsystem
     */
    public String getQuellsystem() {
        return this.ivQuellsystem;
    }

    /**
     * @param pvQuellsystem the quellsystem to set
     */
    public void setQuellsystem(String pvQuellsystem) {
        this.ivQuellsystem = pvQuellsystem;
    }

    /**
     * @return the verbuergt
     */
    public String getVerbuergt() {
        return this.ivVerbuergt;
    }

    /**
     * @param pvVerbuergt the verbuergt to set
     */
    public void setVerbuergt(String pvVerbuergt) {
        this.ivVerbuergt = pvVerbuergt;
    }

    /**
     * @return the buergschaftprozent
     */
    public String getBuergschaftprozent() {
        return this.ivBuergschaftprozent;
    }

    /**
     * @param pvBuergschaftprozent the buergschaftprozent to set
     */
    public void setBuergschaftprozent(String pvBuergschaftprozent) {
        this.ivBuergschaftprozent = pvBuergschaftprozent;
    } 
    
    /**
     * Setzt einen Wert der QuellsystemDaten
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setQuellsystemDaten(int pvPos, String pvWert) 
    {
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
          default:
              System.out.println("QuellsystemDaten: Feld " + pvPos + " undefiniert");
        }
    }
}
