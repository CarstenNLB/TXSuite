/*******************************************************************************
 * Copyright (c) 2018 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Utilities;

import org.apache.log4j.Logger;

public abstract class MappingKunde 
{
    /**
     * Die Kundennummer wird auf 10-Stellen mit Nullen aufgefuellt.
     * @param pvKundennr Kundennummer
     * @param pvLogger log4j-Logger
     * @return 10-stellige Kundennummer
     */
    public static String extendKundennummer(String pvKundennr, Logger pvLogger)
    {
        switch (pvKundennr.length())
        {
          case 1:
              pvKundennr = "000000000" + pvKundennr;
              break;
          case 2:
              pvKundennr = "00000000" + pvKundennr;
              break;
          case 3:
              pvKundennr = "0000000" + pvKundennr;
              break;
          case 4:
              pvKundennr = "000000" + pvKundennr;
              break;
          case 5:
              pvKundennr = "00000" + pvKundennr;
              break;
          case 6:
              pvKundennr = "0000" + pvKundennr;
              break;
          case 7:
              pvKundennr = "000" + pvKundennr;
              break;
          case 8:
              pvKundennr = "00" + pvKundennr;
              break;
          case 9:
              pvKundennr = "0" + pvKundennr;
              break;
          case 10:
              // Nichts zu erledigen, die Laenge ist passend.
              break;
          default:
            pvLogger.error("extendKundennummer - Laenge der Kundennummer: " + pvKundennr.length());
        }
        
        return pvKundennr;   
    }

}
