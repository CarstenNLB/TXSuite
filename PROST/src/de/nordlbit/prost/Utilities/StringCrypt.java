/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.Utilities;

/**
 * @author tepperc
 *
 */
public abstract class StringCrypt 
{
    /**
     * @param pvText 
     * @return 
    *
    */
  public static String decodeROT13(String pvText)
  {
    StringBuilder helpText = new StringBuilder();
    for (int x = 0;x < pvText.length();x++)
    {
      char c = pvText.charAt(x);
      if       (c >= 'a' && c <= 'm') c += 13;
      else if  (c >= 'A' && c <= 'M') c += 13;
      else if  (c >= 'n' && c <= 'z') c -= 13;
      else if  (c >= 'N' && c <= 'Z') c -= 13;
      helpText.append(c);
    }
    return helpText.toString();   
  }

}
