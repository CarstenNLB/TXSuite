/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Utilities;

import org.apache.log4j.Logger;


/**
 * @author tepperc
 *
 */
public abstract class WPSuffix 
{
    /**
     * Ermittelt die Key-Endung von Wertpapieren aus MAVIS
     * @param pvDepotKZ Depotkennzeichen
     * @param pvWpArt Wertpapierart
     * @return
     */
    @Deprecated
    public static String getSuffixMAVIS(String pvDepotKZ, String pvWpArt)
    {
       String lvSuffix = new String();
       
       // Hypothekar
       if (pvWpArt.substring(2,4).equals("01"))
       {
           lvSuffix = "P";
           if (pvDepotKZ.equals("Wertpapier(Ordentlich)") || pvDepotKZ.equals("WPAnlage(SD)") ||
               pvDepotKZ.equals("BankKredit") || pvDepotKZ.equals("BankSV") ||
               pvDepotKZ.equals("BankeigeneDeckung") || pvDepotKZ.equals("Bargeld") ||
               pvDepotKZ.equals("Bundesbank"))
           {
               lvSuffix = lvSuffix + "W";
           }
           if (pvDepotKZ.equals("Wertpapier(Ersatzdeckung)") || pvDepotKZ.equals("WPErsatzdeckung") ||
               pvDepotKZ.equals("Bankguthaben") || pvDepotKZ.equals("WPAnlage(ED)"))
           {
               lvSuffix = lvSuffix + "S";
           }
       }
       
       // Kommunal
       if (pvWpArt.substring(2,4).equals("02"))
       {
           lvSuffix = "K";
           if (pvDepotKZ.equals("WPAnlage(SD)") || pvDepotKZ.equals("BankKredit") || 
                   pvDepotKZ.equals("BankSV") || pvDepotKZ.equals("BankeigeneDeckung") || 
                   pvDepotKZ.equals("Bargeld") || pvDepotKZ.equals("Bundesbank"))
           {
               lvSuffix = lvSuffix + "W";
           }
           if (pvDepotKZ.equals("Wertpapier(Ersatzdeckung)") || pvDepotKZ.equals("WPErsatzdeckung") ||
                   pvDepotKZ.equals("Bankguthaben") || pvDepotKZ.equals("WPAnlage(ED)"))
           {
               lvSuffix = lvSuffix + "S";
           }
           if (pvDepotKZ.equals("Wertpapier(Ordentlich)")) 
           {
               lvSuffix = lvSuffix + "O";
           }
       }
          
       if (lvSuffix.isEmpty())
       {
           System.out.println("Konnte keinen WP-Suffix ermitteln.");
       }
       
       return lvSuffix;
    }

    /**
     * Ermittelt die Key-Endung von Wertpapieren anhand des Ausplatzierungsmerkmals
     * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
     * @param pvLogger log4j-Logger
     * @return
     */
    public static String getSuffix(String pvAusplatzierungsmerkmal, Logger pvLogger)
    {
       StringBuilder lvSuffix = new StringBuilder();
       
       // Hypotheken
       if (pvAusplatzierungsmerkmal.startsWith("H"))
       {
           lvSuffix.append("P");
       }
       // Kommunal
       if (pvAusplatzierungsmerkmal.startsWith("K"))
       {
           lvSuffix.append("K");
       }
       // Flugzeuge
       //if (pvAusplatzierungsmerkmal.startsWith("F"))
       //{
       //   lvSuffix.append("F");
       //}
       // Schiffe
       //if (pvAusplatzierungsmerkmal.startsWith("S"))
       //{
       //	   lvSuffix.append("S");
       //}
          
       // Ordentliche Deckung
       if (pvAusplatzierungsmerkmal.endsWith("1"))
       {
    	   lvSuffix.append("O");
       }
       // Weitere Deckung
       if (pvAusplatzierungsmerkmal.endsWith("2"))
       {
           lvSuffix.append("W");
       }
       // Sicherende Deckung
       if (pvAusplatzierungsmerkmal.endsWith("3"))
       {
           lvSuffix.append("S");
       }

       // Sichernde Deckung Hyp. (O2) und Kom. (O4)
       if (pvAusplatzierungsmerkmal.equals("O2") || pvAusplatzierungsmerkmal.equals("O4"))
       {
         lvSuffix.append("S");
       }

       if (lvSuffix.toString().isEmpty())
       {
           pvLogger.info("Konnte keinen WP-Suffix ermitteln.");
       }
       
       return lvSuffix.toString();
    }

}
