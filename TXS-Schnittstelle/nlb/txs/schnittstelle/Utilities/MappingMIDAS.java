/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Utilities;

import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQ;

/**
 * @author tepperc
 *
 */
public abstract class MappingMIDAS 
{
    /**
     * Ermittelt die MIDAS-Kontonummer
     * @param pvNiederlassung Niederlassung (London, New York, Singapur und Shanghai)
     * @param pvKontonummer Ausgangskontonummer
     * @return
     */
    public static String ermittleMIDASKontonummer(String pvNiederlassung, String pvKontonummer)
    {
        // Generierung von 10-stelligen MIDAS-Kontonummern:
        // 45                3           3                   0*                         5429
        // Schluessel fuer   Kredit      Niederlassung       mit '0' auffuellen,        6-stellige Ausgangskontonummer
        // ausl. Nieder-                 0 ... London        wenn Ausgangskontonummer   gemaeß Kreditakte
        // lassung                       1 ... New York      nicht 6-stellig ist.
        //                               3 ... Singapur
        //                               7 ... Shanghai

        StringBuilder lvHelpString = new StringBuilder();
        // Konstant fuer ausl. Niederlassung
        lvHelpString.append("45"); 
        // Konstant fuer Kredit
        lvHelpString.append("3");
        // Niederlassung
        if (pvNiederlassung.equals("MIDNLDN"))
        {
            lvHelpString.append("0");
        }
        if (pvNiederlassung.equals("MIDNNYC"))
        {
            lvHelpString.append("1");
        }
        if (pvNiederlassung.equals("MIDNSGP"))
        {
            lvHelpString.append("3");
        }
        if (pvNiederlassung.equals("MIDNSHA"))
        {
            lvHelpString.append("7");
        }
        // Kontonummern mit '0' auffuellen
        if (pvKontonummer.length() == 1)
        {
            lvHelpString.append("00000");
        }
        if (pvKontonummer.length() == 2)
        {
            lvHelpString.append("0000");
        }
        if (pvKontonummer.length() == 3)
        {
            lvHelpString.append("000");
        }
        if (pvKontonummer.length() == 4)
        {
            lvHelpString.append("00");
        }
        if (pvKontonummer.length() == 5)
        {
            lvHelpString.append("0");
        }
        lvHelpString.append(pvKontonummer);
        
        return lvHelpString.toString();
    }
    
    /**
     * Ermittelt den Kredittyp anhand des Ausplatzierungsmerkmals und Buergschaftprozent
     * @return 
     */
    public static int ermittleKredittyp(String pvAusplatzierungsmerkmal, String pvBuergschaftprozent)
    {
    	// Defaultmaessig undefiniert
        int lvKredittyp = DarlehenLoanIQ.UNDEFINIERT;
        
        // Hypothekendarlehen
        if (pvAusplatzierungsmerkmal.startsWith("H") || pvAusplatzierungsmerkmal.equals("O1") || pvAusplatzierungsmerkmal.equals("O2"))
        {        
            if (StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
            {
              lvKredittyp = DarlehenLoanIQ.KOMMUNALVERBUERGTE_HYPOTHEK;   
            }
            else
            {
              lvKredittyp = DarlehenLoanIQ.HYPOTHEK_1A;
            }
        }
        
        // Kommunaldarlehen
        if (pvAusplatzierungsmerkmal.startsWith("K") || pvAusplatzierungsmerkmal.equals("O3") || pvAusplatzierungsmerkmal.equals("O4"))
        {
            if (StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
            {
                lvKredittyp = DarlehenLoanIQ.VERBUERGT_KOMMUNAL;
            }
            else
            {
                lvKredittyp = DarlehenLoanIQ.REIN_KOMMUNAL;
            }
        }
        
        // Flugzeugdarlehen
        if (pvAusplatzierungsmerkmal.startsWith("F"))
        {
            lvKredittyp = DarlehenLoanIQ.FLUGZEUGDARLEHEN;
        }
        
        // Schiffsdarlehen
        if (pvAusplatzierungsmerkmal.startsWith("S"))
        {
            lvKredittyp = DarlehenLoanIQ.SCHIFFSDARLEHEN;
        }
        
        // Bankkredit
        if (pvAusplatzierungsmerkmal.endsWith("3"))
        {
        	lvKredittyp = DarlehenLoanIQ.BANKKREDIT;
        }
        
        // Sonstige Schuldverschreibung
        if (pvAusplatzierungsmerkmal.endsWith("2"))
        {
        	lvKredittyp = DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG;
        }
        
        return lvKredittyp;        
    }

    
    /**
     * Aendert den MIDAS-Schluessel auf den TXS-Schluessel
     * @param pvText
     * @return
     */
    public static String changeZinsrhythmus(String pvText)
    {
        // 0   keine Zinstermine
        // 1   täglich
        // 3   3 Tage
        // 7   wöchentlich
        // 14  zweiwöchentlich
        // 21  dreiwöchentlich
        // 30  monatlich
        // 60  zweimonatlich
        // 90  vierteljährl.
        // 120 viermonatlich
        // 150 5 Monate
        // 180 halbjährlich
        // 210 7 Monate
        // 240 8 Monate
        // 270 neunmonatlich
        // 300 10 Monate
        // 330 11 Monate
        // 360 jährlich
        // 720 2 Jahre
        // 1.080   3 Jahre
        // 1.440   4 Jahre
        // 1.800   5 Jahre
        // 2.160   6 Jahre
        // 2.520   7 Jahre
        // 2.880   8 Jahre
        // 3.240   9 Jahre
        // 3.600   10 Jahre
        // ...    
        String lvHelpText = pvText;
        
        // Taeglich
        if (pvText.equals("1"))
        {
            lvHelpText = "1000";
        }
        
        // Woechentlich
        if (pvText.equals("7"))
        {
            lvHelpText = "7000";
        }
        
        // Vierzehntaegig
        if (pvText.equals("14"))
        {
            lvHelpText = "14000";
        }
        
        // Monatlich
        if (pvText.equals("30"))
        {
            lvHelpText = "1";
        }
            
        // Alle 2 Monate
        if (pvText.equals("60"))
        {
            lvHelpText = "2";
        }
        
        // Vierteljaehrlich
        if (pvText.equals("90"))
        {
            lvHelpText = "3";
        }
        
        // Halbjaehrlich
        if (pvText.equals("180"))
        {
            lvHelpText = "6";
        }
        
        // Jaehrlich
        if (pvText.equals("360"))
        {
            lvHelpText = "12";
        }
            
        return lvHelpText;
    }
    
    /**
     * Mapping vom Zins- und Tilgungsrhythmus
     * Aendert den LoanIQ-Schluessel auf den TXS-Schluessel 
     * @param pvText
     * @return
     */
    public static String changeZinsrhythmusTilgungsrhythmus(String pvText)
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
        // 30000  Dreißigtaegig
        // 4      Alle 4 Monate
        // 4000   Viertaegig
        // 5      Alle 5 Monate
        // 5000   Fünftägig
        // 6      Halbjaehrlich
        // 6000   Sechstägig
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
     * @param pvReferenzzins 
     * @param pvWaehrung 
     * @param pvLaufzeit
     * @return 
     * 
     */
    public static String changeReferenzzins(String pvReferenzzins, String pvWaehrung, String pvLaufzeit)
    {
        // Defauft-Referenzzins 'keine' setzen
        String lvHelpText = "keine";
        
        // Rhythmus ermitteln
        //String lvHelpRhythmus = new String();
        //int lvLaufzeitInt = (new Integer(pvLaufzeit)).intValue();
        //if (lvLaufzeitInt < 360)
        //{
        //    lvHelpRhythmus = "MD";
        //}
        //else
        //{
        //    lvHelpRhythmus = "YD";
        //}
        //lvLaufzeitInt = lvLaufzeitInt / 30;
        //String lvHelpLaufzeit = "" + lvLaufzeitInt + lvHelpRhythmus;
        
        //System.out.println("lvHelpLaufzeit: " + lvHelpLaufzeit);
        
        if (pvReferenzzins.endsWith("EURIBOR"))
        {
           lvHelpText = "EURIBOR" + pvLaufzeit + "D"; 
        }
        
        if (pvReferenzzins.endsWith("LIBOR"))
        {
            lvHelpText = pvReferenzzins.replace("-",  "") + pvLaufzeit + "D";
            if (pvWaehrung.equals("EUR") || pvWaehrung.equals("GBP"))
            {
                lvHelpText = "LI" + pvWaehrung + pvLaufzeit + "D";
            }
        }
        
        return lvHelpText;
    }

}
