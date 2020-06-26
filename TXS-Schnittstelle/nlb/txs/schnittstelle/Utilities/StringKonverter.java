/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Utilities;

import java.math.BigDecimal;

/**
 * @author tepperc
 *
 */
public abstract class StringKonverter 
{
    /**
     * Line Separator
     */
    public static final String lineSeparator = System.getProperty("line.separator");
     
    /**
     * Konvertiert eine Zahl im Datentyp String in einen Wert im Datentyp double
     * @param pvText Zahl im Datentyp String
     * @return Wert im Datentyp double
     */
    public static double convertString2Double(String pvText)
    {
        // Erst Leerzeichen entfernen
        pvText = pvText.trim();
        
        double lvHelpDouble = 0.0;
        if (!pvText.isEmpty())
        {
          try
          {
            lvHelpDouble = (new Double(pvText)).doubleValue();
          }
          catch (NumberFormatException exp)
          {
            System.out.println("Fehlerhaftes Format --> 0.0 wird geliefert!");
            System.out.println("String: " + pvText);
          }
        }
        //System.out.println("Wert: " + helpDouble);
        return lvHelpDouble;
    }

    /**
     * Konvertiert eine Zahl im Datentyp String in einen Wert im Datentyp long
     * @param pvText Zahl im Datentyp String
     * @return Wert im Datentyp long
     */
    public static long convertString2Long(String pvText)
    {
        // Erst Leerzeichen entfernen
        pvText = pvText.trim();

        long lvHelpLong = 0;
        if (!pvText.isEmpty())
        {
          try
          {
            lvHelpLong = (new Long(pvText)).longValue();
          }
          catch (NumberFormatException exp)
          {
            System.out.println("Fehlerhaftes Format --> 0 wird geliefert!");
            System.out.println("String: " + pvText);
          }
        }
        //System.out.println("Wert: " + helpDouble);
        return lvHelpLong;
    }
 
    /**
     * Konvertiert eine Zahl im Datentyp String in einen Wert im Datentyp int
     * @param pvText Zahl im Datentyp String
     * @return Wert im Datentyp int
     */
    public static int convertString2Int(String pvText)
    {
        // Erst Leerzeichen entfernen
        pvText = pvText.trim();

        int lvHelpInt = 0;
        if (!pvText.isEmpty())
        {
          try
          {
            lvHelpInt = (new Integer(pvText)).intValue();
          }
          catch (NumberFormatException exp)
          {
            System.out.println("Fehlerhaftes Format --> 0 wird geliefert!");
            System.out.println("String: " + pvText);
          }
          //System.out.println("Wert: " + helpInt);
        }
       return lvHelpInt;
    }

    /**
     * Konvertiert eine Zahl im Datentyp String in einen Wert im Datentyp BigDecimal
     * @param pvText Zahl im Datentyp String
     * @return Wert im Datentyp BigDecimal
     */
    public static BigDecimal convertString2BigDecimal(String pvText)
    {
        // Erst Leerzeichen entfernen
        pvText = pvText.trim();
       
        BigDecimal lvHelpBigDecimal = new BigDecimal(0.0);
        if (!pvText.isEmpty())
        {
          try
          {
            lvHelpBigDecimal = new BigDecimal(pvText);
          }
          catch (NumberFormatException exp)
          {
            System.out.println("Fehlerhaftes Format --> 0.0 wird geliefert!");
            System.out.println("String: " + pvText);
          }
          //System.out.println("Wert: " + helpInt);
        }
       return lvHelpBigDecimal;
    }
    
    /**
     * Setzt in einem Sicherheiten Wertfeld das Komma
     * Sicherheiten Wertfeld -> Nachkommastellen setzen
     * @param pvValue 
     * @param pvNachkommastellen 
     * @return 
     */
    @Deprecated
    public static String convertWertfeldSAPCMS(String pvValue, int pvNachkommastellen)
    {
        String lvHelpValue = new String();
        
        if (!pvValue.contains("."))
        {    
          if (pvValue.length() > pvNachkommastellen)
          {
            lvHelpValue = pvValue.substring(0, pvValue.length() - pvNachkommastellen) 
                                     + "." + pvValue.substring(pvValue.length() - pvNachkommastellen);
          } 
          else
            lvHelpValue = "0.0";
        }
        else
            lvHelpValue = pvValue;

        return lvHelpValue;
    }
}
