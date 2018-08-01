/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Utilities;

/**
 * @author tepperc
 *
 */
public class MappingDPP 
{
    /**
     * @param pvText 
     * @return 
     * 
     */
    public static String changeGrundbuchart(String pvText)
    {   
       boolean lvGefunden = false;
        
        if (pvText.equals("WOG"))
        {
            pvText = "2";
            lvGefunden = true;
        }
        if (pvText.equals("GRB"))
        {
            pvText = "1";
            lvGefunden = true;
        }
        if (pvText.equals("EBG"))
        {
            pvText = "4";
            lvGefunden = true;
        }
        if (pvText.equals("GBG"))
        {
            pvText = "8";
            lvGefunden = true;
        }
        if (pvText.equals("TEG"))
        {
            pvText = "3";
            lvGefunden = true;
        }
        if (pvText.equals("WEG"))
        {
            pvText = "5";
            lvGefunden = true;
        }
        if (pvText.equals("WTG"))
        {
            pvText = "9";
            lvGefunden = true;
        }
        if (pvText.equals("SRE"))
        {
            pvText = "200";
            lvGefunden = true;
        }
        if (pvText.equals("TEB"))
        {
            pvText = "6";
            lvGefunden = true;
        }
        if (!lvGefunden)
            System.out.println("Unbekannte Grundbuchart: " + pvText);

        // Die Länge der Kundengruppe darf maximal 40 Zeichen sein
        //if (text.length() > 39)
        //{
        //    text = text.substring(0, 39);
        //}
        return pvText;
    }
    
    /**
     * @param pvText 
     * @return 
     * 
     */
    public static String changeSicherheitart(String pvText)
    {
        boolean lvGefunden = false;
        
        //System.out.println("CT - pvText: " + pvText);
        if (pvText.equals("FGSH"))
        {
            pvText = "11";
            lvGefunden = true;
        }
        
        if (pvText.equals("FHYP"))
        {
            pvText = "13";
            lvGefunden = true;
        }
        
        if (pvText.equals("HGSH"))
        {
            pvText = "12";
            lvGefunden = true;
        }

        if (pvText.equals("HHYP"))
        {
            pvText = "14";
            lvGefunden = true;
        }
        
        if (!lvGefunden)
            System.out.println("Unbekannte Sicherheitart: " + pvText);
      
        return pvText;
    }
    
    /**
     * @param pvText 
     * @return 
     * 
     */
    public static String changeEigentumstyp(String pvText)
    {
        boolean lvGefunden = false;
        
        if (pvText.equals("E"))
        {
            pvText = "1";
            lvGefunden = true;
        }
        
        if (pvText.equals("F"))
        {
            pvText = "2";
            lvGefunden = true;
        }
        
        if (pvText.equals("G"))
        {
            pvText = "3";
            lvGefunden = true;
        }
        
        if (!lvGefunden)
            System.out.println("Unbekannter Eigentumstyp: " + pvText);
      
        return pvText;
    }

    /**
     * @param pvText 'altes' Land
     * @return String mit 'neuem' Land
     */
    public static String changeLaenderkennzahl(String pvText)
    {
        pvText = pvText.replace("004", "DE");
        
        return pvText;
    }
    
    /**
     * @param pvText 
     * @return 
     * 
     */
    public static String changeObjektgruppe(String pvText)
    {
       boolean lvGefunden = false;
       
       if (pvText.equals("BPLZ"))
       {
           pvText = "97";
           lvGefunden = true;
       }
       if (pvText.equals("DPPL"))
       {
           pvText = "82";
           lvGefunden = true;
       }
       if (pvText.equals("EFHS"))
       {
           pvText = "1";
           lvGefunden = true;
       }
       if (pvText.equals("EHEW"))
       {
           pvText = "2";
           lvGefunden = true;
       }
       if (pvText.equals("HDIM"))
       {
           pvText = "21";
           lvGefunden = true;
       }
       if (pvText.equals("MFHS"))
       {
           pvText = "4";
           lvGefunden = true;
       }
       if (pvText.equals("LAND"))
       {
           pvText = "59";
           lvGefunden = true;
       }
       if (pvText.equals("LANU"))
       {
           pvText = "58";
           lvGefunden = true;
       }
       if (pvText.equals("RHHS"))
       {
           pvText = "10";
           lvGefunden = true;
       }
       if (pvText.equals("ZFHS"))
       {
           pvText = "3";
           lvGefunden = true;
       }
       if (pvText.equals("ETWG"))
       {
           pvText = "5";
           lvGefunden = true;
       }
       if (pvText.equals("FEAN"))
       {
           pvText = "12";
           lvGefunden = true;
       }
       if (pvText.equals("LLZT"))
       {
           pvText = "52";
           lvGefunden = true;
       }
       
       if (!lvGefunden)
           System.out.println("Unbekannte Objektgruppe: " + pvText);
       
       return pvText;
    }
    
    /**
     * @param pvText 
     * @return 
     * 
     */
    public static String changeNutzungsart(String pvText)
    {
       boolean lvGefunden = false;
       
       if (pvText.equals("WWT"))
       {
           pvText = "1";
           lvGefunden = true;
       }
       if (pvText.equals("GWT"))
       {
           pvText = "2";
           lvGefunden = true;
       }
       
       if (!lvGefunden)
           System.out.println("Unbekannte Nutzungsart: " + pvText);
       
       return pvText;
    }

    /**
     * @param pvKundennr 
     * @return 
     * 
     */
    public static String extendKundennummer(String pvKundennr)
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
            System.out.println("extendKundennummer - Laenge der Kundennummer: " + pvKundennr.length());
        }
        
        return pvKundennr;   
    }
}
