/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class DarlehenLoanIQBlock 
{
    /**
     * Kontonummer des DarlehenLoanIQBlock
     */
    private String ivKontonummer;
    
    /**
     * Quellsystem des DarlehenLoanIQBlock
     */
    private String ivQuellsystem;
    
    /**
     * DarlehenLoanIQBrutto
     */
    private DarlehenLoanIQ ivDarlehenLoanIQBrutto;
    
    /**
     * DarlehenLoanIQNetto
     */
    private DarlehenLoanIQ ivDarlehenLoanIQNetto;
    
    /**
     * Liste der DarlehenLoanIQFremd
     */
    private ArrayList<DarlehenLoanIQ> ivListeDarlehenLoanIQFremd;

    /**
     * Konstruktor
     */
    public DarlehenLoanIQBlock() 
    {
        this.ivKontonummer = new String();
        this.ivQuellsystem = new String();
        this.ivDarlehenLoanIQBrutto = null;
        this.ivDarlehenLoanIQNetto = null;
        this.ivListeDarlehenLoanIQFremd = new ArrayList<DarlehenLoanIQ>();
    }

    /**
     * Liefert die Kontonummer
     * @return the kontonummer
     */
    public String getKontonummer() {
        return this.ivKontonummer;
    }

    /**
     * @param pvKontonummer the kontonummer to set
     */
    public void setKontonummer(String pvKontonummer) {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * Liefert das Quellsystem
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
     * @return the darlehenLoanIQBrutto
     */
    public DarlehenLoanIQ getDarlehenLoanIQBrutto() {
        return this.ivDarlehenLoanIQBrutto;
    }

    /**
     * @param pvDarlehenLoanIQBrutto the darlehenLoanIQBrutto to set
     */
    public void setDarlehenLoanIQBrutto(DarlehenLoanIQ pvDarlehenLoanIQBrutto) {
        this.ivDarlehenLoanIQBrutto = pvDarlehenLoanIQBrutto;
    }

    /**
     * @return the darlehenLoanIQNetto
     */
    public DarlehenLoanIQ getDarlehenLoanIQNetto() {
        return this.ivDarlehenLoanIQNetto;
    }

    /**
     * @param pvDarlehenLoanIQNetto the darlehenLoanIQNetto to set
     */
    public void setDarlehenLoanIQNetto(DarlehenLoanIQ pvDarlehenLoanIQNetto) {
        this.ivDarlehenLoanIQNetto = pvDarlehenLoanIQNetto;
    }

    /**
     * @return the listeDarlehenLoanIQFremd
     */
    public ArrayList<DarlehenLoanIQ> getListeDarlehenLoanIQFremd() {
        return this.ivListeDarlehenLoanIQFremd;
    }

    /**
     * @param pvListeDarlehenLoanIQFremd the listeDarlehenLoanIQFremd to set
     */
    public void setListeDarlehenLoanIQFremd(ArrayList<DarlehenLoanIQ> pvListeDarlehenLoanIQFremd) {
        this.ivListeDarlehenLoanIQFremd = pvListeDarlehenLoanIQFremd;
    }
    
    /**
     * Existiert DarlehenLoanIQBrutto? 
     * @return True -> Ja; False -> Nein
     */
    public boolean existsDarlehenLoanIQBrutto()
    {
      if (ivDarlehenLoanIQBrutto == null)
          return false;
      else
          return true;
    }
 
    /**
     * Existiert DarlehenLoanIQNetto? 
     * @return True -> Ja; False -> Nein
     */
    public boolean existsDarlehenLoanIQNetto()
    {
      if (ivDarlehenLoanIQNetto == null)
          return false;
      else
          return true;
    }

    /**
     * Ist die Liste DarlehenLoanIQFremd leer?
     * @return True -> Liste leer; False -> Es gibt mindestens ein DarlehenLoanIQFremd
     */
    public boolean isListeDarlehenLoanIQFremdEmpty()
    {
        return ivListeDarlehenLoanIQFremd.isEmpty();
    }
    
    /**
     * Zerlegt eine Zeile nach Feldern und fuegt sie in den LoanIQBlock als B,N oder F-Zeile ein
     * @param pvZeile 
     * @param pvLogger 
     * @return 
     *       
     */
   public boolean parseDarlehen(String pvZeile, Logger pvLogger)
   {                 
     DarlehenLoanIQ lvDarlehenLoanIQ = new DarlehenLoanIQ(pvLogger);
     String lvHelpKontonummer = new String();
     if (pvZeile.startsWith("LOANIQ"))
     {
    	 lvHelpKontonummer = pvZeile.substring(7, 17);
     }
     if (pvZeile.startsWith("MID"))
     {
    	 //System.out.println("Index: " + pvZeile.substring(8).indexOf(";"));
    	 lvHelpKontonummer = pvZeile.substring(8, 8 + pvZeile.substring(8).indexOf(";"));
     }
     //System.out.println("lvHelpKontonummer: " + lvHelpKontonummer);
     if (!ivKontonummer.isEmpty())
     {
       if (!lvHelpKontonummer.equals(ivKontonummer))
       {
         return false;
       }
     }
     
     if (!lvDarlehenLoanIQ.parseDarlehen(pvZeile)) // Parsen der Felder
     {
         pvLogger.error("Datenfehler in Zeile:\n");
         pvLogger.error(pvZeile);
         return false;
     }
     
     // Kontonummer im DarlehenLoanIQBlock hinterlegen
     if (ivKontonummer.isEmpty())
     {
         ivKontonummer = lvDarlehenLoanIQ.getKontonummer();
     }
     // Quellsystem im DarlehenLoanIQBlock hinterlegen
     if (ivQuellsystem.isEmpty())
     {
         ivQuellsystem = lvDarlehenLoanIQ.getQuellsystem();
     }
     if (lvDarlehenLoanIQ.getKennzeichenBruttoNettoFremd().equals("B"))
     {
         ivDarlehenLoanIQBrutto = lvDarlehenLoanIQ;
     }
     if (lvDarlehenLoanIQ.getKennzeichenBruttoNettoFremd().equals("N"))
     {
         ivDarlehenLoanIQNetto = lvDarlehenLoanIQ;
     }
     if (lvDarlehenLoanIQ.getKennzeichenBruttoNettoFremd().equals("F"))
     {
         ivListeDarlehenLoanIQFremd.add(lvDarlehenLoanIQ);
     }  
     
     return true; 
   }

}
