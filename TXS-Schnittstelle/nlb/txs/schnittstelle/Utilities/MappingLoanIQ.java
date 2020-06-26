/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Utilities;

import org.apache.log4j.Logger;

/**
 * Diese Klasse enthaelt die Mappings fuer die LoanIQ-Verarbeitung.
 * @author Carsten Tepper
 */
public abstract class MappingLoanIQ 
{
    /**
     * Aendert den LoanIQ-Schluessel auf den TXS-Schluessel fuer den Zinsrythmus
     * @param pvText LoanIQ-Schluessel fuer den Zinsrythmus
     * @param pvLogger log4j-Logger
     * @return TXS-Schluessel fuer den Zinsrythmus
     */
    public static String changeZinsrythmus(String pvText, Logger pvLogger)
    {
        // TXS-Schluessel TXS-Text
        // 0      Bullet
        // 1      Monatlich
        // 10     Alle 10 Monate
        // 1000   Taeglich
        // 11     Alle 11 Monate
        // 12     Jaehrlich
        // 13     Sonderfall
        // 14000  Vierzehntaegig
        // 2      Alle 2 Monate
        // 2000   Zweitaegig
        // 3      Vierteljaehrlich
        // 3000   Dreitaegig
        // 30000  Drei�igtaegig
        // 4      Alle 4 Monate
        // 4000   Viertaegig
        // 5      Alle 5 Monate
        // 5000   F�nft�gig
        // 6      Halbjaehrlich
        // 6000   Sechst�gig
        // 60000  Sechzigtaegig
        // 7      Alle 7 Monate
        // 7000   Siebentaegig
        // 8      Alle 8 Monate
        // 9      Alle 9 Monate
        // 90000  Neunzigtaegig
        // ON     Overnight
        // TN     Tomorrow-Next

        // LoanIQ-Text LoanIQ-Schluessel
        // 'DAL'    365
        // '1MON'   12
        // '1WEK'   52
        // '10MON'   1
        // '11MON'   1
        // '2MON'    6
        // '2WEK'   26
        // '3WEK'   17 -> Keine Abbildung
        // '4MON'    3
        // '5MON'    2
        // '7MON'    1
        // '8MON'    1
        // '9MON'    1   
        // '1YEA'    1
        // '6MON'    2
        // '3MON'    4
        // '12MON'  12
 
        // Default Sonderfall - Damit eine Beleiferung moeglich ist
        String lvHelpText = "13";
        
        /*
        // Taeglich
        if (pvText.equals("365"))
        {
            lvHelpText = "1000";
        }
        
        // Woechentlich
        if (pvText.equals("52"))
        {
            lvHelpText = "7000";
        }
        
        // Vierzehntaegig
        if (pvText.equals("26"))
        {
            lvHelpText = "14000";
        }
        
        // Monatlich
        if (pvText.equals("12"))
        {
            lvHelpText = "1";
        }
            
        // Alle 2 Monate
        if (pvText.equals("6"))
        {
            lvHelpText = "2";
        }
        
        // Vierteljaehrlich
        if (pvText.equals("4"))
        {
            lvHelpText = "3";
        }
        
        // Halbjaehrlich
        if (pvText.equals("2"))
        {
            lvHelpText = "6";
        }
        
        // Jaehrlich
        if (pvText.equals("1"))
        {
            lvHelpText = "12";
        } */
        
        // Taeglich
        //if (pvText.equals("365"))
        //{
        //    lvHelpText = "1000";
        //}
        
        // Woechentlich
        //if (pvText.equals("52"))
        //{
        //    lvHelpText = "7000";
        //}
        
        // Vierzehntaegig
        //if (pvText.equals("26"))
        //{
        //    lvHelpText = "14000";
        //}
        
        // Monatlich
        if (pvText.equals("1M"))
        {
            lvHelpText = "1";
        }
            
        // Alle 2 Monate
        //if (pvText.equals("6"))
        //{
        //    lvHelpText = "2";
        //}
        
        // Vierteljaehrlich
        if (pvText.equals("3M"))
        {
            lvHelpText = "3";
        }
        // Alle 4 Monate
        if (pvText.equals("4M"))
        {
        	lvHelpText = "4";
        }
        
        // Halbjaehrlich
        if (pvText.equals("6M"))
        {
            lvHelpText = "6";
        }
        
        // Jaehrlich
        if (pvText.equals("1Y"))
        {
            lvHelpText = "12";
        }
        
            
        return lvHelpText;
    }
    
    
    /**
     * Aendert den LoanIQ-Schluessel auf den TXS-Schluessel fuer den Zinstyp
     * @param pvZinstyp Zinstyp aus LoanIQ
     * @param pvMerkmalAktivPassiv MerkmalAktivPassiv aus LoanIQ
     * @param pvLogger log4j-Logger
     * @return TXS-Schluessel fuer den Zinstyp
     * 
     */
    public static String changeZinstyp(String pvZinstyp, String pvMerkmalAktivPassiv, Logger pvLogger)
    {
        // Definition Lau vom 17.09.2014
        // Passiv (wie unten):   
        //   FIXED auf Fester Zins
        //   FLOAT auf Variabler Zins
        //   ZERO auf Zero-Bond
        //   STEP auf Staffelzins

        // Aktiv:
        //   FIXED auf Fester Zins
        //   FLOAT auf Variabler Zins?
        //   ZERO auf Fester Zins
        //   STEP auf Staffelzins

        // LoanIQ - TXS
        // FIXED - 1 (Fester Zins)
        // FLOAT - 2 (Variabler Zins)
        // STEP - 5 (Staffelzins)
        // ZERO - 4 (Zero-Bond)
        
        String lvHelpText = pvZinstyp;
        if (pvMerkmalAktivPassiv.equals("A"))
        {
          if (pvZinstyp.equalsIgnoreCase("FIXED"))
          {
            lvHelpText = "1";
          }
          if (pvZinstyp.equalsIgnoreCase("FLOAT"))
          {
            lvHelpText = "2";
          }
          if (pvZinstyp.equalsIgnoreCase("STEP"))
          {
            lvHelpText = "5";
          }
          if (pvZinstyp.equalsIgnoreCase("ZERO"))
          {
            lvHelpText = "1";
          }
        }
        if (pvMerkmalAktivPassiv.equalsIgnoreCase("P"))
        {
            if (pvZinstyp.equalsIgnoreCase("FIXED"))
            {
              lvHelpText = "1";
            }
            if (pvZinstyp.equalsIgnoreCase("FLOAT"))
            {
              lvHelpText = "2";
            }
            if (pvZinstyp.equalsIgnoreCase("STEP"))
            {
              lvHelpText = "5";
            }
            if (pvZinstyp.equalsIgnoreCase("ZERO"))
            {
              lvHelpText = "4";
            }            
        }
        
        if (!(lvHelpText.equals("1") || lvHelpText.equals("2") || lvHelpText.equals("4") || lvHelpText.equals("5")))
        {
            pvLogger.error("Unbekannter Zinstyp: " + pvZinstyp);
        }
        
        return lvHelpText;
        
    }

  /**
   * Aendert den LoanIQ-Schluessel auf den TXS-Schluessel fuer die Arbeitskonvention
   * Auspraegungen:
   * modified following || following -> Danach (1)
   * preceding -> Davor (2)
   * keine -> Keine (0)
   * @param pvArbeitskonvention Arbeitskonvention aus LoanIQ
   * @param pvLogger log4j-Logger
   * @return TXS-Schluessel fuer die Arbeitskonvention
   */
  public static String changeArbeitskonvention(String pvArbeitskonvention, Logger pvLogger) {
        String lvHelpText = pvArbeitskonvention;
        if (lvHelpText.equalsIgnoreCase("MOD_FOLLOW") || lvHelpText.equalsIgnoreCase("FOLLOWING"))
        {
            lvHelpText = "1";
        }
        
        if (lvHelpText.equalsIgnoreCase("PRECEDING"))
        {
            lvHelpText = "2";
        }
        
        if (lvHelpText.equalsIgnoreCase("NO_CHANGE"))
        {
            lvHelpText = "0";
        }
        
        if (!(lvHelpText.equals("0") || lvHelpText.equals("1") || lvHelpText.equals("2")))
        {
            pvLogger.error("Unbekannte Arbeitskonvention: " + pvArbeitskonvention);
        }
        
        return lvHelpText;
    }

  /**
   * Aendert den LoanIQ-Schluessel/-Wert auf den TXS-Schluessel fuer den Referenzzins
   * @param pvReferenzzins Referenzzins aus LoanIQ
   * @param pvLaufzeit Laufzeit aus LoanIQ
   * @param pvLogger log4j-Logger
   * @return TXS-Schluessel fuer den Referenzzins
   */
  public static String changeReferenzzins(
      String pvReferenzzins, String pvLaufzeit, Logger pvLogger) {
        // Defauft-Referenzzins 'keine' setzen
        String lvHelpText = "keine";
        
        if (!pvReferenzzins.isEmpty())
        {
          String lvWaehrung = pvReferenzzins.substring(0,3);
          String lvReferenzzins = pvReferenzzins.substring(4);
          if (lvReferenzzins.equals("EURIBOR"))
          {
        	String lvSuffix = "D";  
        	if (pvLaufzeit.equals("1Y"))
        	{
        		lvSuffix = "MD";
        	}
            lvHelpText = lvReferenzzins + pvLaufzeit + lvSuffix; 
          }
        
          if (lvReferenzzins.equals("LIBOR"))
          {
            lvHelpText = lvWaehrung + lvReferenzzins + pvLaufzeit + "D";
            if (lvWaehrung.equals("EUR") || lvWaehrung.equals("GBP"))
            {
                lvHelpText = "LI" + lvWaehrung + pvLaufzeit + "D";
            }
          }
        }
        
        return lvHelpText;
    }
    
}
