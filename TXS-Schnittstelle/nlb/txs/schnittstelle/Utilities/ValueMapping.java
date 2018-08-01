package nlb.txs.schnittstelle.Utilities;

import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQ;
import nlb.txs.schnittstelle.SAPCMS.Daten.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.SAPCMS.Daten.Vermoegensobjekt;

public class ValueMapping 
{
    
    /**
     * @param pvText 
     * @return 
     * 
     */
    public static String changeKundengruppe(String pvText)
    {
        // A_1 Ausl., Zentralregierungen
        // A_2 internationale Institutionen
        // A_3 Ausl., Regionalreg./öff. Geb.-körpersch.
        // B_1 Bund
        // B_2 Andere durch Bund abgesichert
        // C   Bundesländer
        // D   Städte, Gemeinden, Kreise, Bezirke
        // E   öffentliche Unternehmen, Zweckverbände
        // G   Kreditinstitute
        // H   sonst. kommunale GP
        // F   Unternehmen von Städten Gemeinden und Kommunen
        // A_4 Andere ausl. Städte und Gemeinden
        // A_5 Andere ausl. Regionen Provinzen und Staaten
        // A_6 Andere ausl. staatliche oder öffentliche Unternehmen
        // -1  undefiniert

        boolean lvGefunden = false;
        
        if (pvText.equals("A_1"))
        {
            pvText = "Ausl., Zentralregierungen";
            lvGefunden = true;
        }
        if (pvText.equals("A_2"))
        {
            pvText = "internationale Institutionen";
            lvGefunden = true;
        }
        if (pvText.equals("A_3"))
        {
            pvText = "Ausl., Regionalreg./öff. Geb.-körpersch.";
            lvGefunden = true;
        }
        if (pvText.equals("B_1"))
        {
            pvText = "Bund";
            lvGefunden = true;
        }
        if (pvText.equals("B_2"))
        {
            pvText = "Andere durch Bund abgesichert";
            lvGefunden = true;
        }
        if (pvText.equals("C"))
        {
            pvText = "Bundesländer";
            lvGefunden = true;
        }
        if (pvText.equals("D"))
        {
            pvText = "Städte, Gemeinden, Kreise, Bezirke";
            lvGefunden = true;
        }
        if (pvText.equals("E"))
        {
            pvText = "öffentliche Unternehmen, Zweckverbände";
            lvGefunden = true;
        }
        if (pvText.equals("G"))
        {
            pvText = "Kreditinstitute";
            lvGefunden = true;
        }
        if (pvText.equals("H"))
        {
            pvText = "sonst. kommunale GP";
            lvGefunden = true;
        }
        if (pvText.equals("F"))
        {
            pvText = "Unternehmen von Städten Gemeinden und Kommunen";
            lvGefunden = true;
        }
        if (pvText.equals("A_4"))
        {
            pvText = "Andere ausl. Städte und Gemeinden";
            lvGefunden = true;
        }
        if (pvText.equals("A_5"))
        {
            pvText = "Andere ausl. Regionen Provinzen und Staaten";
            lvGefunden = true;
        }
        if (pvText.equals("A_6"))
        {
            pvText = "Andere ausl. staatliche oder öffentliche Unternehmen";
            lvGefunden = true;
        }
        if (!lvGefunden)
            System.out.println("Unbekannte Kundengruppe: " + pvText);

        // Die Länge der Kundengruppe darf maximal 40 Zeichen sein
        if (pvText.length() > 39)
        {
            pvText = pvText.substring(0, 39);
        }
        return pvText;
    }
    
    /**
     *
     * @param pvText
     * @return
     */
    public static String changeMonatsendekonvention(String pvText)
    {
        boolean lvGefunden = false;
        if (pvText.equalsIgnoreCase("keine"))
        {
          pvText = "0";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("gleich"))
        {
          pvText = "1";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("letzter"))
        {
          pvText = "2";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("immerletzter"))
        {
          pvText = "3";
          lvGefunden = true;
        }
         if (!lvGefunden)
          System.out.println("Unbekannte Monatsendekonvention: " + pvText);
        return pvText;
    } 

    
    /**
     *
     * @param pvText
     * @return
     */
    public static String changeArbeitstagskonvention(String pvText)
    {
        boolean lvGefunden = false;
        if (pvText.equalsIgnoreCase("keine"))
        {
          pvText = "0";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("danach"))
        {
          pvText = "1";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("davor"))
        {
          pvText = "2";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("keine3112"))
        {
          pvText = "10";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("danach3112"))
        {
          pvText = "11";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("davor3112"))
        {
          pvText = "12";
          lvGefunden = true;
        }
        if (!lvGefunden)
          System.out.println("Unbekannte Arbeitstagskonvention: " + pvText);
        return pvText;
    }
    
    /** 
     *
     * @param pvText
     * @return
     */
    public static String changeZinstyp(String pvText)
    {
        boolean lvGefunden = false;
        if (pvText.equalsIgnoreCase("fest"))
        {
          pvText = "1";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("variabel"))
        {
          pvText = "2";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("revFloater"))
        {
          pvText = "3";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("zeroBond"))
        {
          pvText = "4";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("staffZins"))
        {
          pvText = "5";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("superFRN"))
        {
          pvText = "6";
          lvGefunden = true;
        }
         if (!lvGefunden)
          System.out.println("Unbekannter Zinstyp: " + pvText);
        return pvText;
    }
    
    /**
     *
     * @param pvText
     * @return
     */
    public static String changePeriodenbeginnEnde(String pvText)
    {
        boolean lvGefunden = false;
        if (pvText.equalsIgnoreCase("keine"))
        {
          pvText = "0";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("B"))
        {
          pvText = "1";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("E"))
        {
          pvText = "2";
          lvGefunden = true;
        }
         if (!lvGefunden)
          System.out.println("Unbekannter PeriodenBeginnEnde: " + pvText);
        return pvText;
    }

    /**
	 *
     * @param pvText
     * @return
     */
    public static String changeAbweichung(String pvText)
    {
        boolean lvGefunden = false;
        if (pvText.equalsIgnoreCase("keine"))
        {
          pvText = "0";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("kurz"))
        {
          pvText = "1";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("lang"))
        {
          pvText = "2";
          lvGefunden = true;
        }
         if (!lvGefunden)
          System.out.println("Unbekannte Zinsabweichung: " + pvText);
        return pvText;
    }

    /**
     *
     * @param pvText
     * @return
     */
    public static String changeKalenderart(String pvText)
    {
        boolean lvGefunden = false;
        if (pvText.equalsIgnoreCase("keine"))
        {
          pvText = "0";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("Banktage"))
        {
          pvText = "1";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("Kalendertage"))
        {
          pvText = "2";
          lvGefunden = true;
        }
         if (!lvGefunden)
          System.out.println("Unbekannte Kalenderart: " + pvText);
        return pvText;
    }

    
    /**
     * 
     * @param text
     * @return
     */
    public static String changeZinszahlart(String text)
    {
        boolean gefunden = false;
        if (text.equalsIgnoreCase("keine"))
        {
          text = "0";
          gefunden = true;
        }
        if (text.equalsIgnoreCase("nachschuessig"))
        {
          text = "1";
          gefunden = true;
        }
        if (text.equalsIgnoreCase("vorschuessig"))
        {
          text = "2";
          gefunden = true;
        }
         if (!gefunden)
          System.out.println("Unbekannte Zinszahlart: " + text);
        return text;
    }
    
    /**
     *
     * @param text
     * @return
     */
    public static String changePeriodenkonvention(String text)
    {
        boolean gefunden = false;
        if (text.equalsIgnoreCase("keine"))
        {
          text = "0";
          gefunden = true;
        }
        if (text.equalsIgnoreCase("anchor"))
        {
          text = "1";
          gefunden = true;
        }
        if (text.equalsIgnoreCase("anchorback"))
        {
          text = "2";
          gefunden = true;
        }
         if (!gefunden)
          System.out.println("Unbekannte Periodenkonvention: " + text);
        return text;
    }
    
    /**
     *
     * @param pvText
     * @return
     */
    public static String changeKalenderkonvention(String pvText)
    {
        if (pvText.equalsIgnoreCase("keine"))
        {
          pvText = "0";
        }
        if (pvText.equalsIgnoreCase("30E_360"))
        {
          pvText = "30e_360";    
        }
        // Die restlichen Auspraegungen sind in Ordnung
        return pvText;
    }
    
    /**
     *
     * @param pvText
     * @return
     */
    public static String changeKuponbasis(String pvText)
    {
        boolean lvGefunden = false;
        if (pvText.equalsIgnoreCase("keine"))
        {
          pvText = "0";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("gerade"))
        {
          pvText = "1";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("ungerade"))
        {
          pvText = "2";
          lvGefunden = true;
        }
        if (pvText.equalsIgnoreCase("gleich"))
        {
          pvText = "3";
          lvGefunden = true;
        }
        if (!lvGefunden)
          System.out.println("Unbekannte Kuponbasis: " + pvText);
        return pvText;
    } 
       
    /**
     * @param pvObjektart 
     * @return 
     * 
     */
    public static String mapNutzungsartDarlehen(String pvObjektart)
    {
      // TXS 6 - Schiffsbeleihungen - Objektart x70             
      // TXS 3 - Landwirtschaftliche Nutzung - Objektart x2, x3, x4              
      // TXS 5 - Sonstige Grundstücke - Objektart x60             
      // TXS 2 - Gewerblich - Objektart x5, x6 ohne x60             
      // TXS 4 - Bauplatz - Objektart x10, x14                
      // TXS 1 - Wohnwirtschaftlich - Objektart x1 ohne x10, x14                
      // TXS 0 - Ohne Zuordnung - Rest                
        
      String lvBuffer1 = pvObjektart.substring(0,3);

      if (lvBuffer1.substring(1,3).equals("70"))
          return "6";
      
      if (lvBuffer1.substring(1,2).equals("2") || lvBuffer1.substring(1,2).equals("3") || lvBuffer1.substring(1,2).equals("4"))
          return "3";
      
      if (lvBuffer1.substring(1,3).equals("60"))
          return "5";
      
      if (lvBuffer1.substring(1,2).equals("5") || lvBuffer1.substring(1,2).equals("6"))
          return "2";
      
      if (lvBuffer1.substring(1,3).equals("10") || lvBuffer1.substring(1,3).equals("14"))
          return "4";
      
      if (lvBuffer1.substring(1,2).equals("1"))
          return "1";
          
      return "0";
    }
    
    /**
     * @param pvObjektart 
     * @return 
     * 
     */
    public static String mapObjektgruppeDarlehen(String pvObjektart)
    {
      // TXS 9 - Wohnwirtschaftlich genutztes Grundstück - Objektart x10
      // TXS 1 - Einfamilienhaus - Objektart x16
      // TXS 4 - Mehrfamilienhaus - Objektart x12
      // TXS 5 - Wohnung - Objektart x17
      // TXS 9 - Wohnwirtschaftlich genutztes Grundstück - Objektart x14
      // TXS 9 - Wohnwirtschaftlich genutztes Grundstück - Objektart x15
      // TXS 58 - Landwirtschaftlich genutztes Grundstück - Objektart x2, x3, x4
      // TXS 57 - Gewerblich genutztes Grundstück - Objektart x50
      // TXS 33 - Klinik (z.B. Krankenhaus, Rehaklinik) - Objektart x51
      // TXS 36 - Kurheim / Sanatorium - Objektart x52
      // TXS 34 - Pflegeheim - Objektart x53
      // TXS 21 - Handelsgebäude - Objektart x54
      // TXS 31 - Hotel / Gaststätte - Objektart x55
      // TXS 42 - Bürogebäude - Objektart x56
      // TXS 15 - Industriegebäude - Objektart x57
      // TXS 15 - Industriegebäude - Objektart x58
      // TXS 13 - Sonstiges gewerblich genutztes Gebäude - Objektart x59
      // TXS 57 - Gewerblich genutztes Grundstück - Objektart x60
      // TXS 57 - Gewerblich genutztes Grundstück - Objektart x61
      // TXS 38 - Sportanlage / Freizeitanlage - Objektart x62
      // TXS 52 - Lagerhalle / Warenlagergebäude / Kühlhaus - Objektart x63
      // TXS 40 - Arztpraxis (inkl. medizinische Behandlungsinstitute) - Objektart x64
      // TXS 55 - Parkhaus / Tiefgarage / Waschhalle - Objektart x65
      // TXS 19 - Fabrik-, Werkstattgeb. - Objektart x66
      // TXS 52 - Lagerhalle / Warenlagergebäude / Kühlhaus - Objektart x67
      // TXS 15 - Industriegebäude - Objektart x68
      // TXS <Leer> - Objektart x69, x70, x75
        
        String lvBuffer1 = pvObjektart.substring(0,3);
        
        if (lvBuffer1.substring(1,3).equals("10") || lvBuffer1.substring(1,3).equals("14") || lvBuffer1.substring(1,3).equals("15"))
            return "9";
        if (lvBuffer1.substring(1,3).equals("16"))
            return "1";
        if (lvBuffer1.substring(1,3).equals("12"))
            return "4";
        if (lvBuffer1.substring(1,3).equals("17"))
            return "5";
        
        if (lvBuffer1.substring(1,2).equals("2") || lvBuffer1.substring(1,2).equals("3") || lvBuffer1.substring(1,2).equals("4"))
            return "58";
        
        if (lvBuffer1.substring(1,3).equals("50") || lvBuffer1.substring(1,3).equals("60") || lvBuffer1.substring(1,3).equals("61"))
            return "57";        
        if (lvBuffer1.substring(1,3).equals("51"))
            return "33";
        if (lvBuffer1.substring(1,3).equals("52"))
            return "36";
        if (lvBuffer1.substring(1,3).equals("53"))
            return "34";
        if (lvBuffer1.substring(1,3).equals("54"))
            return "21";
        if (lvBuffer1.substring(1,3).equals("55"))
            return "31";
        if (lvBuffer1.substring(1,3).equals("56"))
            return "42";
        if (lvBuffer1.substring(1,3).equals("57") || lvBuffer1.substring(1,3).equals("58") || lvBuffer1.substring(1,3).equals("68"))
            return "15";
        if (lvBuffer1.substring(1,3).equals("59"))
            return "13";
        
        if (lvBuffer1.substring(1,3).equals("62"))
            return "38";
        if (lvBuffer1.substring(1,3).equals("63"))
            return "52";
        if (lvBuffer1.substring(1,3).equals("64"))
            return "40";
        if (lvBuffer1.substring(1,3).equals("65"))
            return "55";
        if (lvBuffer1.substring(1,3).equals("66"))
            return "19";
        if (lvBuffer1.substring(1,3).equals("67"))
            return "52";

       return new String();
    }
    
    /**
     * CT 26.03.2012 - Diese Funktion wird nur noch 
     * von TXX404 - alte Schnittstelle verwendet.
     * @param pvText
     * @return String mit 'neuer' Objektgruppe
     */
    public static String changeObjektgruppe(String pvText)
    {
        // EDR
        // 010 Wohnungsgrundstücke ohne spezielle Definition                 
        // 011  Ein- und Zweifamilienhäuser                                   
        // 012  Mehrfamilienhäuser (inkl. Mietwohnanlagen ab 3 Wohneinheiten) 
        // 013  Eigentumswohnungen (sowohl Eigen- als auch Fremdnutzung)      
        // 014  Sonstige Wohngrundstücke                                      
        // 015  gemischte Nutzung mit mehr als 50% Wohnungsbau                
        // 020  Landwirtschaftliche Grundstücke                               
        // 050  Gewerbliche Bauplätze                                         
        // 051  Kliniken (z.B. Krankenhäuser, Rehakliniken)                   
        // 052  Kurheime / Sanatorien                                         
        // 053  Pflegeheime                                                   
        // 054  Einkaufszentren / SB-Märkte / Kaufhäuser                      
        // 055  Hotels und Gaststätten (inkl. Fremdenheime/Pensionen/Kantinen)
        // 056  Büro- und Verwaltungsgebäude (inkl. Kanzleigebäude)           
        // 057  Produktionsstätten / Fabriken / Druckereien                   
        // 058  Brauereien                                                    
        // 059  Einrichtungen der Infrastruktur und Forschung                 
        // 060  Sonstige Grundstücke / Kommunalkredite                        
        // 061  gemischte Nutzung mit mehr als 50% gewerblich                 
        // 062  Sportanlagen / Freizeitanlagen                                
        // 063  Lagerhallen/Warenlagergebäude/Tankstellengeb./Kühlhäuser      
        // 064  Arztpraxen (inkl. medizinische Behandlungsinstitute, ambulante
        // 065  Parkhäuser / Tiefgaragen / Waschhallen                        
        // 066  Werkstätten / Hobelwerke                                      
        // 067  Vergnügungs- und Unterhaltungsstätten                         
        // 068  Windkraftanlagen                                              
        // 094  unbekannt                                                     

        // TXS
        // 9  Wohnwirtschaftlich genutztes Grundstück
        // 1   Einfamilienhaus
        // 4   Mehrfamilienhaus
        // 5   Wohnung
        // 9   Wohnwirtschaftlich genutztes Grundstück
        // 9   Wohnwirtschaftlich genutztes Grundstück
        // 58  Landwirtschaftlich genutztes Grundstück
        // 57  Gewerblich genutztes Grundstück
        // 33  Klinik
        // 36  Kurheim 
        // 34  Pflegeheim
        // 13  Sonstiges gewerblich genutztes Gebäude
        // 31  Hotel / Gaststätte
        // 42  Bürogebäude
        // 15  Industriegebäude
        // 15  Industriegebäude
        // 15  Industriegebäude
        // -1  
        // 57  Gewerblich genutztes Grundstück
        // 38  Sportanlage / Freizeitanlage
        // 52  Lagerhalle / Warenlagergebäude / Kühlhaus
        // 40  Arztpraxis (inkl. medizinische Behandlungsinstitute)
        // 55  Parkhaus / Tiefgarage / Waschhalle
        // 19  Werkstatt
        // 51  Vergnügungs- und Unterhaltungsstätten
        // 15  Industriegebäude
        // -1  
        
        boolean lvGefunden = false;

        // Sonderbehandlung von '000'
        if (pvText.equalsIgnoreCase("000"))
        {
          pvText = "";
          lvGefunden = true;
        }       
        
        if (pvText.equalsIgnoreCase("010"))
        {
          pvText = "9";
          lvGefunden = true;
        }

        if (pvText.equalsIgnoreCase("011"))
        {
          pvText = "1";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("012"))
        {
          pvText = "4";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("013"))
        {
           pvText = "5";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("014"))
        {
           pvText = "9";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("015"))
        {
           pvText = "9";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("020"))
        {
          pvText = "58";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("050"))
        {
          pvText = "57";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("051"))
        {
           pvText = "33";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("052"))
        {
          pvText = "36";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("053"))
        {
          pvText = "34";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("054"))
        {
          pvText = "21";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("055"))
        {
          pvText = "31";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("056"))
        {
          pvText = "42";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("057"))
        {
          pvText = "15";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("058"))
        {
          pvText = "15";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("059"))
        {
          pvText = "13";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("060"))
        {
           pvText = "57";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("061"))
        {
           pvText = "57";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("062"))
        {
           pvText = "38";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("063"))
        {
           pvText = "52";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("064"))
        {
           pvText = "40";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("065"))
        {
           pvText = "55";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("066"))
        {
           pvText = "19";
           lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("067"))
        {
          pvText = "52";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("068"))
        {
          pvText = "15";
          lvGefunden = true;
        }
            
        if (pvText.equalsIgnoreCase("094"))
        {
          pvText = "";
          lvGefunden = true;
        }
        
       if (!lvGefunden)
            System.out.println("Unbekannte Objektgruppe: " + pvText);   

        return pvText;
    }
    
    /**
     * @param pvText 'alter' CashflowTyp
     * @return String mit 'neuem' CashflowTyp
     */
    public static String changeCashflowTyp(String pvText)
    {
        boolean lvGefunden = false;
        // Tilgung
        if (pvText.equalsIgnoreCase("Tilgung"))
        {
          pvText = "1";
          lvGefunden = true;
        }
        // Zins
        if (pvText.equalsIgnoreCase("Zins"))
        {
          pvText = "2";
          lvGefunden = true;
        }
        if (!lvGefunden)
          System.out.println("Unbekannter CashFlow-Typ: " + pvText);
        return pvText;
    }   

    
    /**
     * @param pvText 'altes' Gebiet
     *  @return String mit 'neuem' Gebiet
     */
    public static String changeGebiet(String pvText)
    {
        // Sonderbehandlung Wien
        if (pvText.equals("038_W"))
        {
          pvText = "AT_W";
          return pvText;
        }
        
        // Sonderbehandlung Madrid
        if (pvText.equals("011_28"))
        {
            pvText = "";
            return pvText;
        }
        
        // DE-Gebiete Verarbeitung
        pvText = pvText.replace("100", "DE");
        if (!pvText.startsWith("DE_"))
          pvText = "DE_" + pvText;
        //Gebietsschlüssel fehlerhaft
        if (pvText.equals("DE_00"))
            pvText = "";
        // 003_undefiniert --> Amsterdam, Niederlande
        // 060_undefiniert --> Warschau, Polen
        if (pvText.endsWith("_undefiniert"))
            pvText = "";
        return pvText;
    }
        
    /**
     * @param pvText 'altes' Land
     * @return String mit 'neuem' Land
     */
    public static String changeLand(String pvText)
    {
        pvText = pvText.replace("001", "FR");
        pvText = pvText.replace("003", "NL");
        pvText = pvText.replace("005", "IT");
        pvText = pvText.replace("006", "GB");
        pvText = pvText.replace("007", "IE");
        pvText = pvText.replace("008", "DK");
        pvText = pvText.replace("011", "ES");
        pvText = pvText.replace("018", "LU");
        pvText = pvText.replace("028", "NO");
        pvText = pvText.replace("032", "FI");
        pvText = pvText.replace("038", "AT");
        pvText = pvText.replace("039", "CH");
        pvText = pvText.replace("052", "TR");
        pvText = pvText.replace("054", "LV");
        pvText = pvText.replace("060", "PL");
        pvText = pvText.replace("064", "HU");
        pvText = pvText.replace("068", "BG");
        pvText = pvText.replace("075", "RU");
        pvText = pvText.replace("081", "UZ");
        pvText = pvText.replace("092", "HR");
        pvText = pvText.replace("100", "DE");
        pvText = pvText.replace("104", "LU");
        pvText = pvText.replace("388", "ZA");
        pvText = pvText.replace("400", "US");
        pvText = pvText.replace("404", "CA");
        pvText = pvText.replace("412", "MX");
        pvText = pvText.replace("463", "KY");
        pvText = pvText.replace("472", "TT");
        pvText = pvText.replace("484", "VE");
        pvText = pvText.replace("528", "AR");
        pvText = pvText.replace("616", "IR");
        pvText = pvText.replace("632", "SA");
        pvText = pvText.replace("647", "AE");
        pvText = pvText.replace("720", "CN");
        pvText = pvText.replace("732", "JP");
        pvText = pvText.replace("740", "HK");
        
        // staatenlos --> leere Zeichenkette
        pvText = pvText.replace("999", "");
        
        return pvText;
    }

    /**
     * @param pvText
     * @return
     */
    public static String changeBaujahr(String pvText) 
    {
        if (!pvText.isEmpty())
        {
            if (pvText.length() > 7)
            {
              pvText = pvText.substring(6);
            }
        }
        //if (text.equals("9999"))
        //{
        //    text = "";
        //}
        //System.out.println("Text: " + text);
        return pvText;
    }
    
    /**
     * Waehrung
     * @param pvText Waehrungscode
     * @return String mit 'neuem' Waehrungscode
     */
    public static String changeWaehrung(String pvText)
    {        
        boolean lvGefunden = false;
        String[] lvEdr = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                        "11", "12", "13", "14", "15", "16", "17", "18", "19",
                        "20", "21", "22", "23", "24", "25", "26", "27", "28",
                        "29", "30", "31", "32", "33", "34", "35", "36", "37",
                        "38", "39", "40", "41", "42", "43", "44"};
        
        String[] lvTxs = {"DEM", "EUR", "ITL", "NLG", "ATS", "FRF", "BEF", "FIM",
                        "IEP", "LUF", "PTE", "ESP", "CHF", "SEK", "USD", "JPY",
                        "DKK", "GBP", "ISK", "NOK", "PLZ", "CZK", "TRL", "HUF",
                        "SIT", "CAD", "CNY", "CYP", "GRD", "IDR", "INR", "MAD",
                        "MTL", "MYR", "NZD", "SAR", "SKK", "THB", "XEU", "ZAR",
                        "HKD", "AUD", "PLN", "SGD"};
        
        int lvLauf = 0;
        while (lvLauf < lvEdr.length && !lvGefunden)
        {
            if (pvText.equals(lvEdr[lvLauf]))
            {
                pvText = lvTxs[lvLauf];
                lvGefunden = true;
            }
            lvLauf++;
        }

        if (!lvGefunden)
          System.out.println("Unbekannte Waehrung: " + pvText); 

        return pvText;
    }
    
    /**
     * Mandant
     * @param pvText 
     * @return 
     */
    public static String changeMandant(String pvText)
    {        
      boolean lvGefunden = false;
      
      if (pvText.equals("009") || pvText.equals("NLB"))
      {
          pvText = "25050000";
          lvGefunden = true;
      }
      
      if (pvText.equals("004") || pvText.equals("BLB"))
      {
          pvText = "29050000";
          lvGefunden = true;
      }
        
      if (!lvGefunden)
          System.out.println("Unbekannter Mandant: " + pvText); 

      return pvText;
    }
      
    
    // Neue Schnittstelle - Mappings
    
    /**
     * @param pvVermoegensObj 
     * @param pvShvb 
     * @return 
     * 
     */
    public static String mapSicherheitenArt(Vermoegensobjekt pvVermoegensObj, Sicherheitenvereinbarung pvShvb) 
    {
        String lvBriefgrundschuld = "11";
        String lvBriefhypothek = "13";
        String lvBuchgrundschuld = "12";
        String lvBuchhypothek = "14";
        String lvGesamtBriefgrundschuld = "15";
        String lvGesamtBriefhypothek = "17";
        String lvGesamtBuchgrundschuld = "16";
        String lvGesamtBuchhypothek = "18";
        String lvGrundpfandAusland = "19";
     
     /*================================================================|
     |  ---- Verarbeitung ----                                         |
     |================================================================*/
     
     if (!pvVermoegensObj.getLand().startsWith("DE")) 
     { /* Nicht DEUTSCH => Ausl. */
            return lvGrundpfandAusland;
     } /* Nicht DEUTSCH => Ausl. */

     if (pvShvb.getSicherheitenTyp().equals("010100")) 
     { /* Briefgrundschuld */
        if (pvShvb.getGesamtgrundschuld().equals("X"))
             return lvGesamtBriefgrundschuld;
        else return lvBriefgrundschuld;
     } /* Briefgrundschuld */

     if (pvShvb.getSicherheitenTyp().equals("010200")) 
     { /* Buchgrundschuld */
        if (pvShvb.getGesamtgrundschuld().equals("X"))
                return lvGesamtBuchgrundschuld;
        else return lvBuchgrundschuld;
     } /* Buchgrundschuld */

     if (pvShvb.getSicherheitenTyp().equals("010300")) 
     { /* Briefhypothek */
        if (pvShvb.getGesamtgrundschuld().equals("X"))
             return lvGesamtBriefhypothek;
        else return lvBriefhypothek;
     } /* Briefhypothek */

     if (pvShvb.getSicherheitenTyp().equals("010400")) 
     { /* Buchhypothek */
        if (pvShvb.getGesamtgrundschuld().equals("X"))
                return lvGesamtBuchhypothek;
        else return lvBuchhypothek;
     } /* Buchhypothek */
    
     // besser - CT 05.07.2012 
     return new String();
    }

    /**
     * Konvertiert in die Sicherheitenart
     * @param pvLand 
     * @param pvSicherheitenvereinbarungsart 
     * @param pvGesamtgrundschuldKennzeichen 
     * @return 
     */
    public static String mapSicherheitenArt(String pvLand, String pvSicherheitenvereinbarungsart, String pvGesamtgrundschuldKennzeichen) 
    {
        String lvBriefgrundschuld = "11";
        String lvBriefhypothek = "13";
        String lvBuchgrundschuld = "12";
        String lvBuchhypothek = "14";
        String lvGesamtBriefgrundschuld = "15";
        String lvGesamtBriefhypothek = "17";
        String lvGesamtBuchgrundschuld = "16";
        String lvGesamtBuchhypothek = "18";
        String lvGrundpfandAusland = "19";
     
     /*================================================================|
     |  ---- Verarbeitung ----                                         |
     |================================================================*/
     
     if (!pvLand.startsWith("DE")) 
     { /* Nicht DEUTSCH => Ausl. */
            return lvGrundpfandAusland;
     } /* Nicht DEUTSCH => Ausl. */

     if (pvSicherheitenvereinbarungsart.equals("010100")) 
     { /* Briefgrundschuld */
        if (pvGesamtgrundschuldKennzeichen.equals("X"))
             return lvGesamtBriefgrundschuld;
        else return lvBriefgrundschuld;
     } /* Briefgrundschuld */

     if (pvSicherheitenvereinbarungsart.equals("010200")) 
     { /* Buchgrundschuld */
        if (pvGesamtgrundschuldKennzeichen.equals("X"))
                return lvGesamtBuchgrundschuld;
        else return lvBuchgrundschuld;
     } /* Buchgrundschuld */

     if (pvSicherheitenvereinbarungsart.equals("010300")) 
     { /* Briefhypothek */
        if (pvGesamtgrundschuldKennzeichen.equals("X"))
             return lvGesamtBriefhypothek;
        else return lvBriefhypothek;
     } /* Briefhypothek */

     if (pvSicherheitenvereinbarungsart.equals("010400")) 
     { /* Buchhypothek */
        if (pvGesamtgrundschuldKennzeichen.equals("X"))
                return lvGesamtBuchhypothek;
        else return lvBuchhypothek;
     } /* Buchhypothek */
    
     // besser - CT 05.07.2012 
     return new String();
    }

    /**
     * 
     * @param pvObj
     * @return
     */
    public static String mapSP_Eigentumstyp(Vermoegensobjekt pvObj)
    {
      //System.out.println("ObjektTyp: " + vObj.getObjektTyp());
      //System.out.println("Fertigstellungsprozent: " + vObj.getFertigstellungProzent());
      //System.out.println("Nutzungsart: " + vObj.getNutzungsart());
      
        long lvNutzung;
     /*================================================================|
     |  ---- Verarbeitung ----                                         |
     |================================================================*/
        lvNutzung = StringKonverter.convertString2Long(pvObj.getNutzung());
        if (pvObj.getObjektTyp().equals("010710"))
     { /* Wohnimmobilie */
            if (pvObj.getNutzungsart().equals("100001"))
      { /* Einfamilienhaus */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("27"); /* V. 5.1 vom 14.04.2008 */
        } /* Fertig */
        else
        { /* nicht Null und nicht 100 */
         return("20"); /* V. 5.1 vom 14.04.2008 */
        } /* nicht Null und nicht 100 */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("29");
        } /* Fertig */
        else
        {
         return("22");
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Einfamilienhaus */
            if (pvObj.getNutzungsart().equals("100002"))
      { /* Zweifamilienhaus */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("27"); /* V. 5.1 vom 14.04.2008 */
        } /* Fertig */
        else
        { /* nicht Null und nicht 100 */
         return("20"); /* V. 5.1 vom 14.04.2008 */
        } /* nicht Null und nicht 100 */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("29");
        } /* Fertig */
        else
        {
         return("22");  /* unfertig */
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Zweifamilienhaus */
            if (pvObj.getNutzungsart().equals("100003"))
      { /* Reihenhaus */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("27"); /* V. 5.1 vom 14.04.2008 */
        } /* Fertig */
        else
        { /* nicht Null und nicht 100 */
         return("20"); /* V. 5.1 vom 14.04.2008 */
        } /* nicht Null und nicht 100 */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("29");
        } /* Fertig */
        else
        {
         return("22");  /* unfertig */
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Reihenhaus */
            if (pvObj.getNutzungsart().equals("100004"))
      { /* Doppelhaushälfte */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("27"); /* V. 5.1 vom 14.04.2008 */
        } /* Fertig */
        else
        { /* nicht Null und nicht 100 */
         return("20"); /* V. 5.1 vom 14.04.2008 */
        } /* nicht Null und nicht 100 */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("29");
        } /* Fertig */
        else
        {
         return("22");  /* unfertig */
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Doppelhaushälfte */
            if (pvObj.getNutzungsart().equals("100005"))
      { /* Eigentumswohnung */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("8");
        } /* Fertig */
        else
        {
         return("21");
        }
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("10");
        } /* Fertig */
        else
        {
         return("24");
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Eigentumswohnung */
            if (pvObj.getNutzungsart().equals("100007"))
      { /* MFH */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("28");
        } /* Fertig */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("30");
        } /* Fertig */
       } /* vermietet, verpachtet, Leasing */
      } /* MFH */
     } /* Wohnimmobilie */
        if (pvObj.getObjektTyp().equals("010720"))
     { /*Wohn-und Geschäftsimmobilie*/
            if (pvObj.getNutzungsart().equals("100008"))
      { /* MFH */
                if (lvNutzung == 3)
       { /* Gemischt */
                    if (StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) <= 33.0)
        { /* V. 5.1 vom 14.04.2008 */
                        if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                                || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
          return("30");
         } /* Fertig */
         else
         {
          return("22");
         }
        } /* nur dann */
       } /* Gemischt */
      } /* MFH */
            if (pvObj.getNutzungsart().equals("100009"))
      { /* Wohn-/Gesch. */
                if (lvNutzung == 3)
       { /* Gemischt */
                    if (StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) > 33.0)
        { /* V. 5.1 vom 14.04.2008 */
                        if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                                || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
         { /* Fertig */
          return("1");
         } /* Fertig */
         else
         {
          return("14");
         }
        } /* nur dann */
       } /* Gemischt */
      } /* Wohn-/Gesch. */
     } /* Wohn-und Geschäftsimmobilie */
        if (pvObj.getObjektTyp().equals("010730"))
     { /*Büro-und Geschäftsimmobilie*/
            if (pvObj.getNutzungsart().equals("100010"))
      { /* Büro */
                if (lvNutzung != 3)
       { /* Gemischt */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("1");
        } /* Fertig */
        else
        {
         return("14");
        }
       } /* Gemischt */
      } /* Büro */
     } /* Büro-und Geschäftsimmobilie */
        if (pvObj.getObjektTyp().equals("010740"))
     { /*Handelsimmobilie*/
            if (pvObj.getNutzungsart().equals("100011"))
      { /* Handelsimmobile */
                if (lvNutzung != 3)
       { /* Gemischt */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("3");
        } /* Fertig */
        else
        {
         return("16");
        }
       } /* Gemischt */
      } /* Handelsimmobile */
     } /* Handelsimmobilie */
        if (pvObj.getObjektTyp().equals("010750"))
     { /*Sozialimmobilie*/
            if (pvObj.getNutzungsart().equals("100012"))
      { /* Sozialimmobilie */
       // CT 29.12.2011 if (lNutzung == 3)
       //{ /* Gemischt */
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("6");
        } /* Fertig */
        else
        {
         return("19");
        }
       // CT 29.12.2011 } /* Gemischt */
      } /* Sozialimmobilie */
     } /* Sozialimmobilie */
        if (pvObj.getObjektTyp().equals("010760"))
     { /*Beherbergung und Gastronomie*/
            if (pvObj.getNutzungsart().equals("100013"))
      { /* Beh. und Gastronomie */
                if (lvNutzung != 3)
       { /* Nicht Gemischt */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("4");
        } /* Fertig */
        else
        {
         return("17");
        }
       } /* Nicht Gemischt */
      } /* Beherbergung und Gastronomie */
     } /* Beherbergung und Gastronomie */
        if (pvObj.getObjektTyp().equals("010770"))
     { /*Lagergebäude*/
            if (pvObj.getNutzungsart().equals("100013"))
      { /* Lagergebäude */
                if (lvNutzung != 3)
       { /* Nicht Gemischt */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("5");
        } /* Fertig */
        else
        {
         return("18");
        }
       } /* Nicht Gemischt */
      } /* Lagergebäude */
     } /*Lagergebäude */
        if (pvObj.getObjektTyp().equals("010780"))
     { /*Gewerbe- und Indust.*/
            if (pvObj.getNutzungsart().equals("100015"))
      { /* Gewerbe- und Indust.*/
                if (lvNutzung != 3)
       { /* Nicht Gemischt */
                    if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                            || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
        { /* Fertig */
         return("2");
        } /* Fertig */
        else
        {
         return("15");
        }
       } /* Nicht Gemischt */
      } /* Gewerbe- und Industrieimmoblie */
     } /*Gewerbe- und Industrieimmoblie */
        if (pvObj.getObjektTyp().equals("010790"))
     { /*Freizeitimmobilie*/
            if (pvObj.getNutzungsart().equals("100016"))
      { /* Freizeitimmobilie*/
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
       { /* Fertig */
        return("6");
       } /* Fertig */
       else
       {
        return("19");
       }
      } /*Freizeitimmobilie */
     } /*Freizeitimmobilie */
        if (pvObj.getObjektTyp().equals("010800"))
     { /*Verkehrsimmobilie*/
            if (pvObj.getNutzungsart().equals("100017"))
      { /* Verkehrsimmobilie*/
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
       { /* Fertig */
        return("6");
       } /* Fertig */
       else
       {
        return("19");
       }
      } /*Verkehrsimmobilie */
     } /*Verkehrsimmobilie */
        if (pvObj.getObjektTyp().equals("010810"))
     { /*Spezialimmobilie*/
            if (pvObj.getNutzungsart().equals("100018"))
      { /* Spezialimmobilie*/
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
       { /* Fertig */
        return("6");
       } /* Fertig */
       else
       {
        return("19");
       }
      } /*Spezialimmobilie */
     } /*Spezialimmobilie */
        if (pvObj.getObjektTyp().equals("010820"))
     { /* landwirtschaftliche Gr.*/
            if (pvObj.getNutzungsart().equals("100019"))
      { /* landwirtschaftliche Gr.*/
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
       { /* Fertig */
        return("12");
       } /* Fertig */
      } /*landwirtschaftliche Gr. */
     } /*landwirtschaftliche Gr. */
        if (pvObj.getObjektTyp().equals("010830"))
     { /* sonst landwirt. Gr.*/
            if (pvObj.getNutzungsart().equals("100020"))
      { /* sonst landwirt. Gr.*/
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
       { /* Fertig */
        return("12");
       } /* Fertig */
      } /* sonst landwirt. Gr. */
     } /* sonst landwirt. Gr. */
        if (pvObj.getObjektTyp().equals("010840"))
     { /* außerlandwirt. Gr.*/
            if (pvObj.getNutzungsart().equals("100021"))
      { /* außerlandwirt Gr.*/
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
       { /* Fertig */
        return("13");
       } /* Fertig */
      } /*außerlandwirt. */
     } /*außerlandwirt */
        if (pvObj.getObjektTyp().equals("010850"))
     { /* unbebaute  Gr.*/
            if (pvObj.getNutzungsart().equals("100022"))
      { /* Wohn*/
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
       { /* Fertig */
        return("13");
       } /* Fertig */
      } /*Wohn */
            if (pvObj.getNutzungsart().equals("100023"))
      { /* Gewerbe */
                if (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0
                        || (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 100.0))
       { /* Fertig */
        return("12");
       } /* Fertig */
      } /* Gewerbe  */
     } /*unbebaute  Gr. */
     return new String();
    } /*end Map_ETyp () */

    /**
     * 
     * @param pvImmobilienobjektartId 
     * @param pvGradBaufertigstellung 
     * @param pvNutzung 
     * @param pvNutzungsart 
     * @param pvGewerblicheNutzungProzent 
     * @return
     */
    public static String mapSP_Eigentumstyp(String pvImmobilienobjektartId, String pvGradBaufertigstellung, String pvNutzung, String pvNutzungsart, String pvGewerblicheNutzungProzent)
    {
      //System.out.println("ObjektartId: " + pvImmobilienobjektartId);
      //System.out.println("GradBaufertigstellung: " + pvGradBaufertigstellung);
      //System.out.println("Nutzung: " + pvNutzung);
      //System.out.println("Nutzungsart: " + pvNutzungsart);
      //System.out.println("GewerblicheNutzungProzent: " + pvGewerblicheNutzungProzent);
      
        long lvNutzung;
        lvNutzung = StringKonverter.convertString2Long(pvNutzung);
        if (pvImmobilienobjektartId.equals("010710"))
     { /* Wohnimmobilie */
            if (pvNutzungsart.equals("100001"))
      { /* Einfamilienhaus */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("27"); /* V. 5.1 vom 14.04.2008 */
        } /* Fertig */
        else
        { /* nicht Null und nicht 100 */
         return("20"); /* V. 5.1 vom 14.04.2008 */
        } /* nicht Null und nicht 100 */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("29");
        } /* Fertig */
        else
        {
         return("22");
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Einfamilienhaus */
            if (pvNutzungsart.equals("100002"))
      { /* Zweifamilienhaus */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("27"); /* V. 5.1 vom 14.04.2008 */
        } /* Fertig */
        else
        { /* nicht Null und nicht 100 */
         return("20"); /* V. 5.1 vom 14.04.2008 */
        } /* nicht Null und nicht 100 */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("29");
        } /* Fertig */
        else
        {
         return("22");  /* unfertig */
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Zweifamilienhaus */
            if (pvNutzungsart.equals("100003"))
      { /* Reihenhaus */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("27"); /* V. 5.1 vom 14.04.2008 */
        } /* Fertig */
        else
        { /* nicht Null und nicht 100 */
         return("20"); /* V. 5.1 vom 14.04.2008 */
        } /* nicht Null und nicht 100 */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("29");
        } /* Fertig */
        else
        {
         return("22");  /* unfertig */
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Reihenhaus */
            if (pvNutzungsart.equals("100004"))
      { /* Doppelhaushälfte */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("27"); /* V. 5.1 vom 14.04.2008 */
        } /* Fertig */
        else
        { /* nicht Null und nicht 100 */
         return("20"); /* V. 5.1 vom 14.04.2008 */
        } /* nicht Null und nicht 100 */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("29");
        } /* Fertig */
        else
        {
         return("22");  /* unfertig */
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Doppelhaushälfte */
            if (pvNutzungsart.equals("100005"))
      { /* Eigentumswohnung */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("8");
        } /* Fertig */
        else
        {
         return("21");
        }
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("10");
        } /* Fertig */
        else
        {
         return("24");
        }
       } /* vermietet, verpachtet, Leasing */
      } /* Eigentumswohnung */
            if (pvNutzungsart.equals("100007"))
      { /* MFH */
                if (lvNutzung == 1)
       { /* Eigennutzung */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("28");
        } /* Fertig */
       } /* Eigennutzung */
                if (lvNutzung == 2 || lvNutzung == 5 || lvNutzung == 6)
       { /* */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("30");
        } /* Fertig */
       } /* vermietet, verpachtet, Leasing */
      } /* MFH */
     } /* Wohnimmobilie */
        if (pvImmobilienobjektartId.equals("010720"))
     { /*Wohn-und Geschäftsimmobilie*/
            if (pvNutzungsart.equals("100008"))
      { /* MFH */
                if (lvNutzung == 3)
       { /* Gemischt */
                    if (StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) <= 33.0)
        { /* V. 5.1 vom 14.04.2008 */
                        if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                                || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
          return("30");
         } /* Fertig */
         else
         {
          return("22");
         }
        } /* nur dann */
       } /* Gemischt */
      } /* MFH */
            if (pvNutzungsart.equals("100009"))
      { /* Wohn-/Gesch. */
                if (lvNutzung == 3)
       { /* Gemischt */
                    if (StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) > 33.0)
        { /* V. 5.1 vom 14.04.2008 */
                        if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                                || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
         { /* Fertig */
          return("1");
         } /* Fertig */
         else
         {
          return("14");
         }
        } /* nur dann */
       } /* Gemischt */
      } /* Wohn-/Gesch. */
     } /* Wohn-und Geschäftsimmobilie */
        if (pvImmobilienobjektartId.equals("010730"))
     { /*Büro-und Geschäftsimmobilie*/
            if (pvNutzungsart.equals("100010"))
      { /* Büro */
                if (lvNutzung != 3)
       { /* Gemischt */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("1");
        } /* Fertig */
        else
        {
         return("14");
        }
       } /* Gemischt */
      } /* Büro */
     } /* Büro-und Geschäftsimmobilie */
        if (pvImmobilienobjektartId.equals("010740"))
     { /*Handelsimmobilie*/
            if (pvNutzungsart.equals("100011"))
      { /* Handelsimmobile */
                if (lvNutzung != 3)
       { /* Gemischt */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("3");
        } /* Fertig */
        else
        {
         return("16");
        }
       } /* Gemischt */
      } /* Handelsimmobile */
     } /* Handelsimmobilie */
        if (pvImmobilienobjektartId.equals("010750"))
     { /*Sozialimmobilie*/
            if (pvNutzungsart.equals("100012"))
      { /* Sozialimmobilie */
       // CT 29.12.2011 if (lNutzung == 3)
       //{ /* Gemischt */
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("6");
        } /* Fertig */
        else
        {
         return("19");
        }
       // CT 29.12.2011 } /* Gemischt */
      } /* Sozialimmobilie */
     } /* Sozialimmobilie */
        if (pvImmobilienobjektartId.equals("010760"))
     { /*Beherbergung und Gastronomie*/
            if (pvNutzungsart.equals("100013"))
      { /* Beh. und Gastronomie */
                if (lvNutzung != 3)
       { /* Nicht Gemischt */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("4");
        } /* Fertig */
        else
        {
         return("17");
        }
       } /* Nicht Gemischt */
      } /* Beherbergung und Gastronomie */
     } /* Beherbergung und Gastronomie */
        if (pvImmobilienobjektartId.equals("010770"))
     { /*Lagergebäude*/
            if (pvNutzungsart.equals("100013"))
      { /* Lagergebäude */
                if (lvNutzung != 3)
       { /* Nicht Gemischt */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("5");
        } /* Fertig */
        else
        {
         return("18");
        }
       } /* Nicht Gemischt */
      } /* Lagergebäude */
     } /*Lagergebäude */
        if (pvImmobilienobjektartId.equals("010780"))
     { /*Gewerbe- und Indust.*/
            if (pvNutzungsart.equals("100015"))
      { /* Gewerbe- und Indust.*/
                if (lvNutzung != 3)
       { /* Nicht Gemischt */
                    if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                            || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
        { /* Fertig */
         return("2");
        } /* Fertig */
        else
        {
         return("15");
        }
       } /* Nicht Gemischt */
      } /* Gewerbe- und Industrieimmoblie */
     } /*Gewerbe- und Industrieimmoblie */
        if (pvImmobilienobjektartId.equals("010790"))
     { /*Freizeitimmobilie*/
            if (pvNutzungsart.equals("100016"))
      { /* Freizeitimmobilie*/
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
       { /* Fertig */
        return("6");
       } /* Fertig */
       else
       {
        return("19");
       }
      } /*Freizeitimmobilie */
     } /*Freizeitimmobilie */
        if (pvImmobilienobjektartId.equals("010800"))
     { /*Verkehrsimmobilie*/
            if (pvNutzungsart.equals("100017"))
      { /* Verkehrsimmobilie*/
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
       { /* Fertig */
        return("6");
       } /* Fertig */
       else
       {
        return("19");
       }
      } /*Verkehrsimmobilie */
     } /*Verkehrsimmobilie */
        if (pvImmobilienobjektartId.equals("010810"))
     { /*Spezialimmobilie*/
            if (pvNutzungsart.equals("100018"))
      { /* Spezialimmobilie*/
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
       { /* Fertig */
        return("6");
       } /* Fertig */
       else
       {
        return("19");
       }
      } /*Spezialimmobilie */
     } /*Spezialimmobilie */
        if (pvImmobilienobjektartId.equals("010820"))
     { /* landwirtschaftliche Gr.*/
            if (pvNutzungsart.equals("100019"))
      { /* landwirtschaftliche Gr.*/
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
       { /* Fertig */
        return("12");
       } /* Fertig */
      } /*landwirtschaftliche Gr. */
     } /*landwirtschaftliche Gr. */
        if (pvImmobilienobjektartId.equals("010830"))
     { /* sonst landwirt. Gr.*/
            if (pvNutzungsart.equals("100020"))
      { /* sonst landwirt. Gr.*/
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
       { /* Fertig */
        return("12");
       } /* Fertig */
      } /* sonst landwirt. Gr. */
     } /* sonst landwirt. Gr. */
        if (pvImmobilienobjektartId.equals("010840"))
     { /* außerlandwirt. Gr.*/
            if (pvNutzungsart.equals("100021"))
      { /* außerlandwirt Gr.*/
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
       { /* Fertig */
        return("13");
       } /* Fertig */
      } /*außerlandwirt. */
     } /*außerlandwirt */
        if (pvImmobilienobjektartId.equals("010850"))
     { /* unbebaute  Gr.*/
            if (pvNutzungsart.equals("100022"))
      { /* Wohn*/
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
       { /* Fertig */
        return("13");
       } /* Fertig */
      } /*Wohn */
            if (pvNutzungsart.equals("100023"))
      { /* Gewerbe */
                if (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0
                        || (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
       { /* Fertig */
        return("12");
       } /* Fertig */
      } /* Gewerbe  */
     } /*unbebaute  Gr. */
     return new String();
    } /*end Map_ETyp () */

    /**
     * @param pvObj 
     * @return 
     * 
     */
    public static String mapNutzungsart(Vermoegensobjekt pvObj)
    {
        String lvOhneZuordnung = "0";
        String lvWohngebaeude = "1";
        String lvGewerblicheNutzung = "2";
        String lvLandwirtschaft = "3";
        String lvBauplatz = "4";
        String lvSonstigeGrundstuecke = "5";
     /*================================================================|
     |  ---- Verarbeitung ----                                         |
     |================================================================*/
        if (pvObj.getObjektTyp().equals("010710")
                && StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) <= 33.0
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvWohngebaeude);
     }

        if (pvObj.getObjektTyp().equals("010710")
                && StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) > 33.0
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010720")
                && StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) <= 33.0
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvWohngebaeude);
     }

        if (pvObj.getObjektTyp().equals("010720")
                && StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) > 33.0
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010730")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010740")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010750")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010760")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010770")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010780")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010790")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010800")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010810")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010840")
                && StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) > 33.0
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010840")
                && StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) <= 33.0
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvSonstigeGrundstuecke);
     }

        if (pvObj.getObjektTyp().equals("010820")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvLandwirtschaft);
     }

        if (pvObj.getObjektTyp().equals("010830")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvGewerblicheNutzung);
     }

        if (pvObj.getObjektTyp().equals("010850")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) != 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) != 100.0))
     {
            return (lvBauplatz);
     }

        if (pvObj.getObjektTyp().equals("010850")
                && (StringKonverter.convertString2Double(pvObj.getFertigstellungProzent()) == 0.0 || StringKonverter.convertString2Double(pvObj
                        .getFertigstellungProzent()) == 100.0))
     {
            return (lvSonstigeGrundstuecke);
     }
        return (lvOhneZuordnung);

    }/*end Map_NutzungsArt () */

    /**
     * 
     * @param pvImmobilienobjektartId 
     * @param pvGewerblicheNutzungProzent 
     * @param pvGradBaufertigstellung 
     * @return 
     */
    public static String mapNutzungsart(String pvImmobilienobjektartId, String pvGewerblicheNutzungProzent, String pvGradBaufertigstellung)
    {
        String lvOhneZuordnung = "0";
        String lvWohngebaeude = "1";
        String lvGewerblicheNutzung = "2";
        String lvLandwirtschaft = "3";
        String lvBauplatz = "4";
        String lvSonstigeGrundstuecke = "5";

    if (pvImmobilienobjektartId.equals("010710")
        && StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) <= 33.0
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvWohngebaeude);
    }

    if (pvImmobilienobjektartId.equals("010710")
        && StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) > 33.0
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010720")
        && StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) <= 33.0
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvWohngebaeude);
    }

    if (pvImmobilienobjektartId.equals("010720")
        && StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) > 33.0
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010730")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010740")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010750")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010760")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010770")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010780")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010790")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010800")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010810")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010840")
        && StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) > 33.0
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010840")
        && StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) <= 33.0
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvSonstigeGrundstuecke);
    }

    if (pvImmobilienobjektartId.equals("010820")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvLandwirtschaft);
    }

    if (pvImmobilienobjektartId.equals("010830")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvGewerblicheNutzung);
    }

    if (pvImmobilienobjektartId.equals("010850")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) != 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) != 100.0))
    {
        return (lvBauplatz);
    }

    if (pvImmobilienobjektartId.equals("010850")
        && (StringKonverter.convertString2Double(pvGradBaufertigstellung) == 0.0 || StringKonverter.convertString2Double(pvGradBaufertigstellung) == 100.0))
    {
        return (lvSonstigeGrundstuecke);
    }
     
    return (lvOhneZuordnung);

    }
    
    /**
     * @param pvObj 
     * @return 
     * 
     */
    public static String mapPfandObjektGruppe(Vermoegensobjekt pvObj)
    {
        String Tab[][] =
        {
         {"1","100001"},
         {"1","100002"},
         {"1","100003"},
         {"1","100004"},
         {"4","100007"},
         {"4","100008"},
         {"5","100005"},
         {"9","100022"},
         {"58","100019"},
         {"58","100020"},  /* 10 */
         {"57","100023"},
         {"36","100012"},
         {"21","100011"},
         {"31","100013"},
         {"42","100010"},
         {"15","100015"},
         {"15","100017"},
         {"9","100021"},
         {"57","100023"}, 
         {"38","100016"},  /* 20 */
         {"52","100014"},
         {"***","***"}     /* 22 */
        };

       int i=0;

        /*================================================================|
        |  ---- Verarbeitung ----                                         |
        |================================================================*/

        if (pvObj.getNutzungsart().equals("100009") || pvObj.getNutzungsart().equals("100018"))
       {
            if (StringKonverter.convertString2Double(pvObj.getGewerblicheNutzung()) > 33.0)
        {
         return "13";
        }
        else
        {
         return "4";
        }
       }
       
        while (!Tab[i][1].equals("***"))
        {
            if (Tab[i][1].equals(pvObj.getNutzungsart()))
         {
           return(Tab[i][0]);
         }
         i++;
        }
        
        return new String();
    }

    /**
     * 
     * @param pvNutzungsart 
     * @param pvGewerblicheNutzungProzent 
     * @return  
     */
    public static String mapPfandObjektGruppe(String pvNutzungsart, String pvGewerblicheNutzungProzent)
    {
        String Tab[][] =
        {
         {"1","100001"},
         {"1","100002"},
         {"1","100003"},
         {"1","100004"},
         {"4","100007"},
         {"4","100008"},
         {"5","100005"},
         {"9","100022"},
         {"58","100019"},
         {"58","100020"},  /* 10 */
         {"57","100023"},
         {"36","100012"},
         {"21","100011"},
         {"31","100013"},
         {"42","100010"},
         {"15","100015"},
         {"15","100017"},
         {"9","100021"},
         {"57","100023"}, 
         {"38","100016"},  /* 20 */
         {"52","100014"},
         {"***","***"}     /* 22 */
        };

       int i=0;

       if (pvNutzungsart.equals("100009") || pvNutzungsart.equals("100018"))
       {
            if (StringKonverter.convertString2Double(pvGewerblicheNutzungProzent) > 33.0)
        {
         return "13";
        }
        else
        {
         return "4";
        }
       }
       
        while (!Tab[i][1].equals("***"))
        {
            if (Tab[i][1].equals(pvNutzungsart))
         {
           return(Tab[i][0]);
         }
         i++;
        }
        
        return new String();
    }

    
    /**
     * 
     * @param pvStaat
     * @param pvGebiet
     * @return
     */
    public static String mapGebiete(String pvStaat, String pvGebiet)
    {
        String lvTab[][] =
        {
         {"800_ACT","ACT"},
         {"800_NSW","NSW"},
         {"800_NT","NT"},
         {"800_QLD","QLD"},
         {"800_SA","SA"},
         {"800_TAS","TAS"},
         {"800_VIC","VIC"},
         {"800_WA","WA"},
         {"404_AB","AB"},
         {"404_BC","BC"},      /* 10 */
         {"404_MB","MB"},
         {"404_NB",""},
         {"404_NF","NL"},
         {"404_NS",""},
         {"404_ON","ON"},
         {"036_AG","AG"},
         {"036_AI",""},
         {"036_AR",""},
         {"036_BE","BE"},
         {"036_BL","BL"},      /* 20 */
         {"036_BS","BS"},
         {"036_FR","FR"},
         {"036_GE","GE"},
         {"036_GL","GL"},
         {"036_GR","GR"},
         {"036_JU","JU"},
         {"036_LU","LU"},
         {"036_NE","NE"},
         {"036_NW","NW"},
         {"036_OW","OW"},      /* 30 */
         {"036_SG","SG"},
         {"036_SH","SH"},
         {"036_SO","SO"},
         {"036_SZ","SZ"},
         {"036_TG","TG"},
         {"036_TI","TI"},
         {"036_UR","UR"},
         {"036_VD","VD"},
         {"036_VS","VS"},
         {"036_ZG","ZG"},      /* 40 */
         {"036_ZH","ZH"},
         {"720_010","10"},
         {"720_020","20"},
         {"720_030","30"},
         {"720_040","40"},
         {"720_050","50"},
         {"720_060","60"},
         {"720_070","70"},
         {"720_080","80"},
         {"720_090","90"},     /* 50 */
         {"720_100","100"},
         {"720_110","110"},
         {"720_120","120"},
         {"720_130","130"},
         {"720_140","140"},
         {"720_150","150"},
         {"720_160","160"},
         {"720_170","170"},
         {"720_180","180"},
         {"706_undefiniert",""}, /* 60 */
         {"063_undefiniert",""},
         {"091_undefiniert",""},
         {"342_undefiniert",""},
         {"011_undefiniert",""},
         {"669_undefiniert",""},
         {"999_undefiniert",""},
         {"449_undefiniert",""},
         {"329_undefiniert",""},
         {"465_undefiniert",""},
         {"408_undefiniert",""}, /* 70 */
         {"467_undefiniert",""},
         {"224_undefiniert",""},
         {"388_undefiniert",""},
         {"728_undefiniert",""},
         {"492_undefiniert",""},
         {"027_undefiniert",""},
         {"393_undefiniert",""},
         {"608_undefiniert",""},
         {"082_undefiniert",""},
         {"736_undefiniert",""}, /* 80 */
         {"352_undefiniert",""},
         {"680_undefiniert",""},
         {"280_undefiniert",""},
         {"817_undefiniert",""},
         {"472_undefiniert",""},
         {"244_undefiniert",""},
         {"061_undefiniert",""},
         {"062_undefiniert",""},
         {"212_undefiniert",""},
         {"052_undefiniert",""}, /* 90 */
         {"080_undefiniert",""},
         {"454_undefiniert",""},
         {"807_undefiniert",""},
         {"350_undefiniert",""},
         {"072_undefiniert",""},
         {"064_undefiniert",""},
         {"524_undefiniert",""},
         {"081_undefiniert",""},
         {"816_undefiniert",""},
         {"045_undefiniert",""}, /* 100 */
         {"484_undefiniert",""},
         {"647_undefiniert",""},
         {"400_undefiniert",""},
         {"690_undefiniert",""},
         {"811_undefiniert",""},
         {"073_undefiniert",""},
         {"819_undefiniert",""},
         {"322_undefiniert",""},
         {"306_undefiniert",""},
         {"600_undefiniert",""}, /* 110 */
         {"000_undefiniert",""},
         {"038_B","B"},
         {"038_K","K"},
         {"038_NÖ","NÖ"},
         {"038_OÖ","OÖ"},
         {"038_S","S"},
         {"038_ST","ST"},
         {"038_T","T"},
         {"038_V","V"},
         {"038_W","W"},          /* 120 */
         {"220_undefiniert",""},
         {"310_undefiniert",""},
         {"334_undefiniert",""},
         {"660_undefiniert",""},
         {"070_undefiniert",""},
         {"208_undefiniert",""},
         {"457_undefiniert",""},
         {"810_undefiniert",""},
         {"043_undefiniert",""},
         {"330_undefiniert",""}, /* 130 */
         {"446_undefiniert",""},
         {"459_undefiniert",""},
         {"528_undefiniert",""},
         {"077_undefiniert",""},
         {"474_undefiniert",""},
         {"078_undefiniert",""},
         {"800_undefiniert",""},
         {"802_undefiniert",""},
         {"625_undefiniert",""},
         {"453_undefiniert",""}, /* 140 */
         {"640_undefiniert",""},
         {"666_undefiniert",""},
         {"469_undefiniert",""},
         {"102_undefiniert",""},
         {"421_undefiniert",""},
         {"284_undefiniert",""},
         {"413_undefiniert",""},
         {"675_undefiniert",""},
         {"516_undefiniert",""},
         {"093_undefiniert",""}, /* 150 */
         {"391_undefiniert",""},
         {"508_undefiniert",""},
         {"357_undefiniert",""},
         {"468_undefiniert",""},
         {"703_undefiniert",""},
         {"068_undefiniert",""},
         {"100_undefiniert",""},
         {"236_undefiniert",""},
         {"328_undefiniert",""},
         {"022_undefiniert",""}, /* 160 */
         {"512_undefiniert",""},
         {"720_undefiniert",""},
         {"436_undefiniert",""},
         {"008_undefiniert",""},
         {"460_undefiniert",""},
         {"456_undefiniert",""},
         {"338_undefiniert",""},
         {"500_undefiniert",""},
         {"272_undefiniert",""},
         {"428_undefiniert",""}, /* 170 */
         {"336_undefiniert",""},
         {"053_undefiniert",""},
         {"041_undefiniert",""},
         {"529_undefiniert",""},
         {"815_undefiniert",""},
         {"032_undefiniert",""},
         {"001_undefiniert",""},
         {"496_undefiniert",""},
         {"822_undefiniert",""},
         {"314_undefiniert",""}, /* 180 */
         {"252_undefiniert",""},
         {"076_undefiniert",""},
         {"276_undefiniert",""},
         {"044_undefiniert",""},
         {"473_undefiniert",""},
         {"009_undefiniert",""},
         {"406_undefiniert",""},
         {"006_undefiniert",""},
         {"458_undefiniert",""},
         {"416_undefiniert",""}, /* 190 */
         {"257_undefiniert",""},
         {"260_undefiniert",""},
         {"488_undefiniert",""},
         {"452_undefiniert",""},
         {"424_undefiniert",""},
         {"740_undefiniert",""},
         {"664_undefiniert",""},
         {"700_undefiniert",""},
         {"612_undefiniert",""},
         {"616_undefiniert",""}, /* 200 */
         {"007_undefiniert",""},
         {"024_undefiniert",""},
         {"624_undefiniert",""},
         {"005_undefiniert",""},
         {"464_undefiniert",""},
         {"732_undefiniert",""},
         {"653_undefiniert",""},
         {"628_undefiniert",""},
         {"090_undefiniert",""},
         {"463_undefiniert",""}, /* 210 */
         {"302_undefiniert",""},
         {"696_undefiniert",""},
         {"404_undefiniert",""},
         {"021_undefiniert",""},
         {"247_undefiniert",""},
         {"079_undefiniert",""},
         {"644_undefiniert",""},
         {"346_undefiniert",""},
         {"083_undefiniert",""},
         {"812_undefiniert",""}, /* 220 */
         {"480_undefiniert",""},
         {"375_undefiniert",""},
         {"318_undefiniert",""},
         {"092_undefiniert",""},
         {"448_undefiniert",""},
         {"636_undefiniert",""},
         {"684_undefiniert",""},
         {"395_undefiniert",""},
         {"054_undefiniert",""},
         {"604_undefiniert",""}, /* 230 */
         {"268_undefiniert",""},
         {"216_undefiniert",""},
         {"037_undefiniert",""},
         {"055_undefiniert",""},
         {"104_undefiniert",""},
         {"743_undefiniert",""},
         {"370_undefiniert",""},
         {"386_undefiniert",""},
         {"701_undefiniert",""},
         {"667_undefiniert",""}, /* 240 */
         {"232_undefiniert",""},
         {"046_undefiniert",""},
         {"204_undefiniert",""},
         {"824_undefiniert",""},
         {"462_undefiniert",""},
         {"228_undefiniert",""},
         {"373_undefiniert",""},
         {"377_undefiniert",""},
         {"096_undefiniert",""},
         {"412_undefiniert",""}, /* 250 */
         {"823_undefiniert",""},
         {"074_undefiniert",""},
         {"716_undefiniert",""},
         {"470_undefiniert",""},
         {"366_undefiniert",""},
         {"676_undefiniert",""},
         {"389_undefiniert",""},
         {"803_undefiniert",""},
         {"672_undefiniert",""},
         {"809_undefiniert",""}, /* 260 */
         {"804_undefiniert",""},
         {"814_undefiniert",""},
         {"432_undefiniert",""},
         {"003_undefiniert",""},
         {"478_undefiniert",""},
         {"240_undefiniert",""},
         {"288_undefiniert",""},
         {"724_undefiniert",""},
         {"820_undefiniert",""},
         {"028_undefiniert",""}, /* 270 */
         {"038_undefiniert",""},
         {"649_undefiniert",""},
         {"662_undefiniert",""},
         {"825_undefiniert",""},
         {"442_undefiniert",""},
         {"801_undefiniert",""},
         {"520_undefiniert",""},
         {"504_undefiniert",""},
         {"708_undefiniert",""},
         {"813_undefiniert",""}, /* 280 */
         {"890_undefiniert",""},
         {"060_undefiniert",""},
         {"010_undefiniert",""},
         {"372_undefiniert",""},
         {"324_undefiniert",""},
         {"066_undefiniert",""},
         {"075_undefiniert",""},
         {"806_undefiniert",""},
         {"378_undefiniert",""},
         {"047_undefiniert",""}, /* 290 */
         {"311_undefiniert",""},
         {"632_undefiniert",""},
         {"030_undefiniert",""},
         {"039_undefiniert",""},
         {"036_undefiniert",""},
         {"248_undefiniert",""},
         {"094_undefiniert",""},
         {"355_undefiniert",""},
         {"264_undefiniert",""},
         {"382_undefiniert",""}, /* 300 */
         {"732_36","36"},
         {"732_37","37"},
         {"732_38","38"},
         {"732_39","39"},
         {"732_40","40"},
         {"732_41","41"},
         {"732_42","42"},
         {"732_43","43"},
         {"732_44","44"},
         {"732_45","45"},        /* 310 */
         {"732_46","46"},
         {"732_47","47"},
         {"412_AGS","AGS"},
         {"412_BCN","BC"},
         {"412_BCS","BCS"},
         {"412_CHI","CHI"},
         {"412_CHS","CHS"},
         {"412_CMP","CMP"},
         {"412_COA","COA"},
         {"412_COL","COL"},      /* 320 */
         {"412_DF","DF"},
         {"412_DGO","DGO"},
         {"412_GRO","GRO"},
         {"412_GTO","GTO"},
         {"412_HGO","HGO"},
         {"412_JAL","JAL"},
         {"412_MCH","MCH"},
         {"412_MEX","MEX"},
         {"412_MOR","MOR"},
         {"412_NL","NAY"},       /* 330 */
         {"412_OAX","NL"},
         {"412_PUE","OAX"},
         {"412_QR","PUE"},
         {"412_QRO","QR"},
         {"412_SIN","QRO"},
         {"412_SLP","SIN"},
         {"412_SON","SLP"},
         {"412_TAB","SON"},
         {"412_TLX","TAB"},
         {"412_TMS","TLX"},      /* 340 */
         {"412_VER","TMS"},
         {"412_YUC","VER"},
         {"412_ZAC","YUC"},
         {"028_01","ZAC"},
         {"028_02","02"},
         {"028_03","03"},
         {"028_04","04"},
         {"028_05","05"},
         {"028_06","06"},
         {"028_07","07"},        /* 350 */
         {"028_08","08"},
         {"028_09","09"},
         {"028_10","10"},
         {"028_11","11"},
         {"028_12","12"},
         {"028_14","14"},
         {"028_15","15"},
         {"028_16","16"},
         {"028_17","17"},
         {"028_18","18"},        /* 360 */
         {"011_31","31"},
         {"011_32","32"},
         {"011_33","33"},
         {"011_34","34"},
         {"011_35","35"},
         {"011_36","36"},
         {"011_37","37"},
         {"011_38","38"},
         {"011_39","39"},
         {"011_40","40"},        /* 370 */
         {"011_41","41"},
         {"011_42","42"},
         {"011_43","43"},
         {"011_44","44"},
         {"011_45","45"},
         {"011_46","46"},
         {"011_47","47"},
         {"011_48","48"},
         {"011_49","49"},
         {"011_50","50"},        /* 380 */
         {"001_01","01"},
         {"001_02","02"},
         {"001_03","03"},
         {"001_04","04"},
         {"001_05","05"},
         {"001_06","06"},
         {"001_07","07"},
         {"001_08","08"},
         {"001_09","09"},
         {"001_10","10"},        /* 390 */
         {"001_11","11"},
         {"001_12","12"},
         {"001_13","13"},
         {"001_14","14"},
         {"001_15","15"},
         {"001_16","16"},
         {"001_17","17"},
         {"001_18","18"},
         {"001_19","19"},
         {"001_21","21"},        /* 400 */
         {"001_22","22"},
         {"001_23","23"},
         {"001_24","24"},
         {"001_25","25"},
         {"001_26","26"},
         {"001_27","27"},
         {"001_28","28"},
         {"001_29","29"},
         {"001_2A","2A"},
         {"001_2B","2B"},        /* 410 */
         {"001_30","30"},
         {"001_31","31"},
         {"001_32","32"},
         {"001_33","33"},
         {"001_34","34"},
         {"001_35","35"},
         {"001_36","36"},
         {"001_37","37"},
         {"001_38","38"},
         {"001_39","39"},        /* 420 */
         {"006_ST","ST"},
         {"006_SU",""},
         {"006_SX","SW"},
         {"006_SY","SY"},
         {"006_TA",""},
         {"006_TW",""},
         {"006_TY","TY"},
         {"006_WA","WA"},
         {"006_WG","WG"},
         {"006_WI","WI"},        /* 430 */
         {"006_WM",""},
         {"006_WO","WC"},
         {"006_YK",""},
         {"006_YN","YN"},
         {"006_YS","YS"},
         {"006_YW","YW"},
         {"007_CK","01"},
         {"007_CL","02"},
         {"007_CW","03"},
         {"007_DB","04"},        /* 440 */
         {"007_DG","05"},
         {"007_GW","GW"},
         {"007_KD","KD"},
         {"007_KK","KK"},
         {"007_KV",""},
         {"007_KY","KY"},
         {"007_LF","LF"},
         {"007_LI","LI"},
         {"007_LM","LM"},
         {"007_LS","LS"},        /* 450 */
         {"007_LT","LT"},
         {"007_MH","MH"},
         {"007_MT","MT"},
         {"007_MY","MY"},
         {"007_OS","OF"},
         {"007_RC","RC"},
         {"007_SG","SG"},
         {"007_TP","TP"},
         {"007_WF","WF"},
         {"007_WK","WK"},        /* 460 */
         {"007_WM","WM"},
         {"007_WX","WX"},
         {"005_AG","AG"},
         {"005_AL","AL"},
         {"005_AN","AN"},
         {"005_AO","AO"},
         {"005_AP","AP"},
         {"005_AQ","AQ"},
         {"005_AR","AR"},
         {"005_AT","AT"},        /* 470 */
         {"005_AV","AV"},
         {"005_BA","BA"},
         {"005_BG","BG"},
         {"005_BI","BI"},
         {"005_BL","BL"},
         {"005_BN","BN"},
         {"005_BO","BO"},
         {"005_BR","BR"},
         {"005_BS","BS"},
         {"005_BZ","BZ"},        /* 480 */
         {"001_40","CA"},
         {"001_41","CB"},
         {"001_42","CE"},
         {"001_43","CH"},
         {"001_44","CL"},
         {"001_45","CN"},
         {"001_46","CO"},
         {"001_47","CR"},
         {"001_48","CS"},
         {"001_49","CT"},        /* 490 */
         {"001_50","CZ"},
         {"001_51","EN"},
         {"001_52","FC"},
         {"001_53","53"},
         {"001_54","54"},
         {"001_55","55"},
         {"001_56","56"},
         {"001_57","57"},
         {"001_58","58"},
         {"001_59","59"},        /* 500 */
         {"001_60","60"},
         {"001_61","61"},
         {"001_62","62"},
         {"001_63","63"},
         {"001_64","64"},
         {"001_65","65"},
         {"001_66","66"},
         {"001_67","67"},
         {"001_68","68"},
         {"001_69","69"},        /* 510 */
         {"001_70","70"},
         {"001_71","71"},
         {"001_72","72"},
         {"001_73","73"},
         {"001_74","74"},
         {"001_75","75"},
         {"001_76","76"},
         {"001_77","77"},
         {"001_78","78"},
         {"001_79","79"},        /* 520 */
         {"001_80","80"},
         {"001_81","81"},
         {"001_82","82"},
         {"001_83","83"},
         {"001_84","84"},
         {"001_85","85"},
         {"001_86","86"},
         {"001_87","87"},
         {"001_88","88"},
         {"001_89","89"},        /* 530 */
         {"001_90","90"},
         {"001_91","91"},
         {"001_92","92"},
         {"001_93","93"},
         {"001_94","94"},
         {"001_95","95"},
         {"001_97","97"},
         {"001_971","971"},
         {"001_972","972"},
         {"001_973","973"},      /* 540 */
         {"001_974","974"},
         {"001_975","975"},
         {"001_976","976"},
         {"001_99","99"},
         {"006_AM","AM"},
         {"006_AT","AT"},
         {"006_AV",""},
         {"006_BE","BR"},
         {"006_BK","BK"},
         {"006_BO",""},          /* 550 */
         {"006_BU","BU"},
         {"006_CA","CA"},
         {"006_CE","1"},
         {"006_CH","CH"},
         {"006_CL",""},
         {"006_CO","CO"},
         {"006_CU",""},
         {"006_CV",""},
         {"006_DB","DB"},
         {"006_DF","DF"},        /* 560 */
         {"006_DN","DN"},
         {"006_DO","DO"},
         {"006_DU","DU"},
         {"006_DV","DV"},
         {"006_DY",""},
         {"006_ES","ES"},
         {"006_FI","FI"},
         {"006_FM","FM"},
         {"006_GL","GL"},
         {"006_GM",""},          /* 570 */
         {"006_GR",""},
         {"006_GW",""},
         {"006_GY",""},
         {"006_HA","HA"},
         {"006_HI",""},
         {"006_HT","HT"},
         {"006_HU",""},
         {"006_HW","HW"},
         {"006_IM",""},
         {"006_IW","IW"},        /* 580 */
         {"006_KE","KE"},
         {"006_LA","LA"},
         {"006_LD","LD"},
         {"006_LE","LE"},
         {"006_LI","LI"},
         {"006_LO","LD"},
         {"006_LT","WK"},
         {"006_MG","MG"},
         {"006_MY",""},
         {"006_NH","NH"},        /* 590 */
         {"006_NK","NK"},
         {"006_NT","NT"},
         {"006_NU","NU"},
         {"006_OX","OX"},
         {"006_PO",""},
         {"006_SC",""},
         {"006_SG","SG"},
         {"006_SH","SH"},
         {"006_SK","SK"},
         {"006_SO","SO"},        /* 600 */
         {"005_CA","CA"},
         {"005_CB","CB"},
         {"005_CE","CE"},
         {"005_CH","CH"},
         {"005_CL","CL"},
         {"005_CN","CN"},
         {"005_CO","CO"},
         {"005_CR","CR"},
         {"005_CS","CS"},
         {"005_CT","CT"},        /* 610 */
         {"005_CZ","CZ"},
         {"005_EN","EN"},
         {"005_FE","FC"},
         {"005_FG","FE"},
         {"005_FI","FG"},
         {"005_FO","FI"},
         {"005_FR","FR"},
         {"005_GE","GE"},
         {"005_GO","GO"},
         {"005_GR","GR"},        /* 620 */
         {"005_IM","IM"},
         {"005_IS","IS"},
         {"005_KR","KR"},
         {"005_LC","LC"},
         {"005_LE","LE"},
         {"005_LI","LI"},
         {"005_LO","LO"},
         {"005_LT","LT"},
         {"005_LU","LU"},
         {"005_MC","MC"},        /* 630 */
         {"005_ME","ME"},
         {"005_MI","MI"},
         {"005_MN","MN"},
         {"005_MO","MO"},
         {"005_MS","MS"},
         {"005_MT","MT"},
         {"005_NA","NA"},
         {"005_NO","NO"},
         {"005_NU","NU"},
         {"005_OR","OR"},        /* 640 */
         {"005_PA","PA"},
         {"005_PC","PC"},
         {"005_PD","PD"},
         {"005_PE","PE"},
         {"005_PG","PG"},
         {"005_PI","PI"},
         {"005_PN","PN"},
         {"005_PO","PO"},
         {"005_PR","PR"},
         {"005_PS","PT"},        /* 650 */
         {"005_PT","PU"},
         {"005_PV","PV"},
         {"005_PZ","PZ"},
         {"005_RA","RA"},
         {"005_RC","RC"},
         {"005_RE","RE"},
         {"005_RG","RG"},
         {"005_RI","RI"},
         {"005_RM","RM"},
         {"005_RN","RN"},        /* 660 */
         {"005_RO","RO"},
         {"005_RV","SA"},
         {"005_SA","SI"},
         {"005_SI","SO"},
         {"005_SO","SP"},
         {"005_SP","SR"},
         {"005_SR","SS"},
         {"005_SS","SV"},
         {"005_SV","TA"},
         {"005_TA","TE"},        /* 670 */
         {"005_TE","TN"},
         {"005_TN","TO"},
         {"005_TO","TP"},
         {"005_TP","TR"},
         {"005_TR","TS"},
         {"005_TS","TV"},
         {"005_TV","UD"},
         {"005_UD","VA"},
         {"005_VA","VB"},
         {"005_VC","VC"},        /* 680 */
         {"005_VE","VE"},
         {"005_VI","VI"},
         {"005_VR","VR"},
         {"005_VT","VT"},
         {"005_VV","VV"},
         {"732_01","01"},
         {"732_02","02"},
         {"732_03","03"},
         {"732_04","04"},
         {"732_05","05"},        /* 690 */
         {"732_06","06"},
         {"732_07","07"},
         {"732_08","08"},
         {"732_09","09"},
         {"732_10","10"},
         {"732_11","11"},
         {"732_12","12"},
         {"732_13","13"},
         {"732_14","14"},
         {"732_15","15"},        /* 700 */
         {"732_16","16"},
         {"732_17","17"},
         {"732_18","18"},
         {"732_19","19"},
         {"732_20","20"},
         {"732_21","21"},
         {"732_22","22"},
         {"732_23","23"},
         {"732_24","24"},
         {"732_25","25"},        /* 710 */
         {"732_26","26"},
         {"732_27","27"},
         {"732_28","28"},
         {"732_29","29"},
         {"732_30","30"},
         {"732_31","31"},
         {"732_32","32"},
         {"732_33","33"},
         {"732_34","34"},
         {"732_35","35"},        /* 720 */
         {"720_190","190"},
         {"720_200","200"},
         {"720_210","210"},
         {"720_220","220"},
         {"720_230","230"},
         {"720_240","240"},
         {"720_250","250"},
         {"720_260","260"},
         {"720_270","270"},
         {"720_280","280"},      /* 730 */
         {"720_290","290"},
         {"720_300","300"},
         {"720_310","TWN"},
         {"100_01","01"},
         {"100_02","02"},
         {"100_03","03"},
         {"100_04","04"},
         {"100_05","05"},
         {"100_06","06"},
         {"100_07","07"},        /* 740 */
         {"100_08","08"},
         {"100_09","09"},
         {"100_10","10"},
         {"100_11","11"},
         {"100_12","12"},
         {"100_13","13"},
         {"100_14","14"},
         {"100_15","15"},
         {"100_16","16"},
         {"008_001","01"},       /* 750 */
         {"011_01","01"},
         {"011_02","02"},
         {"011_03","03"},
         {"011_04","04"},
         {"011_05","05"},
         {"011_06","06"},
         {"011_07","07"},
         {"011_08","08"},
         {"011_09","09"},
         {"011_10","10"},        /* 760 */
         {"011_11","11"},
         {"011_12","12"},
         {"011_13","13"},
         {"011_14","14"},
         {"011_15","15"},
         {"011_16","16"},
         {"011_17","17"},
         {"011_18","18"},
         {"011_19","19"},
         {"011_20","20"},        /* 770 */
         {"011_21","21"},
         {"011_22","22"},
         {"011_23","23"},
         {"011_24","24"},
         {"011_25","25"},
         {"011_26","26"},
         {"011_27","27"},
         {"011_28","28"},
         {"011_29","29"},
         {"011_30","30"},        /* 780 */
         {"400_MP","MP"},
         {"400_MS","MS"},
         {"400_MT","MT"},
         {"400_NC","NC"},
         {"400_ND","ND"},
         {"400_NE","NE"},
         {"400_NH","NH"},
         {"400_NJ","NJ"},
         {"400_NM","NM"},
         {"400_NV","NV"},        /* 790 */
         {"400_NY","NY"},
         {"400_OH","OH"},
         {"400_OK","OK"},
         {"400_OR","OR"},
         {"400_PA","PA"},
         {"400_PR","PR"},
         {"400_RI","RI"},
         {"400_SC","SC"},
         {"400_SD","SD"},
         {"400_TN","TN"},        /* 800 */
         {"400_TX","TX"},
         {"400_UT","UT"},
         {"400_VA","VA"},
         {"400_VI","VI"},
         {"400_VT","VT"},
         {"400_WA","WA"},
         {"400_WI","WI"},
         {"400_WV","WV"},
         {"400_WY","WY"},
         {"undefiniert",""},     /* 810 */
         {"002_undefiniert",""},
         {"017_undefiniert",""},
         {"018_undefiniert",""},
         {"039_AG","AG"},
         {"039_AI","AI"},
         {"039_AR","AR"},
         {"039_BE","BE"},
         {"039_BL","BL"},
         {"039_BS","BS"},
         {"039_FR","FR"},        /* 820 */
         {"039_GE","GE"},
         {"039_GL","GL"},
         {"039_GR","GR"},
         {"039_JU","JU"},
         {"039_LU","LU"},
         {"039_NE","NE"},
         {"039_NW","NW"},
         {"039_OW","OW"},
         {"039_SG","SG"},
         {"039_SH","SH"},        /* 830 */
         {"039_SO","SO"},
         {"039_SZ","SZ"},
         {"039_TG","TG"},
         {"039_TI","TI"},
         {"039_undefiniert",""},
         {"039_UR","UR"},
         {"039_VD","VD"},
         {"039_VS","VS"},
         {"039_ZG","ZG"},
         {"039_ZH","ZH"},        /* 840 */
         {"107_undefiniert",""},
         {"108_undefiniert",""},
         {"109_undefiniert",""},
         {"626_undefiniert",""},
         {"830_undefiniert",""},
         {"831_undefiniert",""},
         {"833_undefiniert",""},
         {"832_undefiniert",""},
         {"834_undefiniert",""},
         {"835_undefiniert",""}, /* 850 */
         {"836_undefiniert",""},
         {"838_undefiniert",""},
         {"839_undefiniert",""},
         {"892_undefiniert",""},
         {"893_undefiniert",""},
         {"894_undefiniert",""},
         {"028_19","19"},
         {"028_20","20"},
         {"028_21",""},
         {"010_10","10"},        /* 860 */
         {"010_11","11"},
         {"010_12","12"},
         {"010_13","13"},
         {"010_14","14"},
         {"010_15","15"},
         {"010_16","16"},
         {"010_17","17"},
         {"010_20","20"},
         {"010_21","21"},
         {"010_22","22"},        /* 870 */
         {"010_23","23"},
         {"010_24","24"},
         {"010_25","25"},
         {"010_26","26"},
         {"010_27","27"},
         {"010_28","28"},
         {"010_29","29"},
         {"010_30","30"},
         {"010_31","31"},
         {"010_32","32"},        /* 880 */
         {"010_33","33"},
         {"010_34","34"},
         {"010_40","40"},
         {"010_41","41"},
         {"010_42","42"},
         {"010_43","43"},
         {"010_50","50"},
         {"010_60","60"},
         {"010_70","70"},
         {"400_AK","AK"},        /* 890 */
         {"400_AL","AL"},
         {"400_AR","AR"},
         {"400_AS","AS"},
         {"400_AZ","AZ"},
         {"400_CA","CA"},
         {"400_CO","CO"},
         {"400_CT","CT"},
         {"400_DC","DC"},
         {"400_DE","DE"},
         {"400_FL","FL"},        /* 900 */
         {"400_GA","GA"},
         {"400_GU","GU"},
         {"400_HI","HI"},
         {"400_IA","IA"},
         {"400_ID","ID"},
         {"400_IL","IL"},
         {"400_IN","IN"},
         {"400_KS","KS"},
         {"400_KY","KY"},
         {"400_LA","LA"},        /* 910 */
         {"400_MA","MA"},
         {"400_MD","MD"},
         {"400_ME","ME"},
         {"400_MI","MI"},
         {"400_MN","MN"},
         {"***","***"}           /* 916 */
        };
        String lvRet;
        int i = 0;
        /*================================================================|
        |  ---- Verarbeitung ----                                         |
        |================================================================*/
        //System.out.println("Gebiet: " + sGebiet + " Staat: " + sStaat);
        if (pvGebiet == null) return new String();

        while (!lvTab[i][1].equals("***"))
        {
            if (lvTab[i][1].equals(pvGebiet) && lvTab[i][0].startsWith(pvStaat))
         {
                return (lvTab[i][0]);
         }
          i++;
        }
        lvRet = pvStaat + "_undefiniert";
        return lvRet;
    }

    /**
     * @param pvLand 
     * @return 
     * 
     */
    public static String mapLand(String pvLand)
    {
        String lvTab[][] =
        {
         {"660","AF"},
         {"220","EG"},
         {"070","AL"},
         {"208","DZ"},
         {"457","VI"},
         {"810","UM"},
         {"832",""},
         {"043","AD"},
         {"330","AO"},
         {"446","AI"},      /* 10 */
         {"459","AG"},
         {"310",""},
         {"528","AR"},
         {"077","AM"},
         {"474","AW"},
         {"078","AZ"},
         {"334","ET"},
         {"802",""},
         {"800","AU"},
         {"625","PS"},      /* 20 */
         {"453","BS"},
         {"640","BH"},
         {"666","BD"},
         {"469","BB"},
         {"017","BE"},
         {"102","BE"},
         {"421","BZ"},
         {"284","BJ"},
         {"413","BM"},
         {"675","BT"},      /* 30 */
         {"516","BO"},
         {"093","BA"},
         {"391","BW"},
         {"892","BV"},
         {"508","BR"},
         {"468","VG"},
         {"357","IO"},
         {"703","BN"},
         {"068","BG"},
         {"100","DE"},      /* 40 */
         {"236","BF"},
         {"328","BI"},
         {"022",""},
         {"512","CL"},
         {"720","CN"},
         {"436","CR"},
         {"008","DK"},
         {"460","DM"},
         {"456","DO"},
         {"338","DJ"},      /* 50 */
         {"500","EC"},
         {"428","SV"},
         {"272","CI"},
         {"336","ER"},
         {"053","EE"},
         {"529","FK"},
         {"041","FO"},
         {"815","FJ"},
         {"032","FI"},
         {"001","FR"},      /* 60 */
         {"001","MC"},
         {"496","GF"},
         {"822","PF"},
         {"894","FR"},
         {"314","GA"},
         {"252","GM"},
         {"076","GE"},
         {"276","GH"},
         {"044","GI"},
         {"473","GD"},      /* 70 */
         {"009","GR"},
         {"406","GL"},
         {"006","GB"},
         {"458","GP"},
         {"831","GU"},
         {"416","GT"},
         {"107",""},
         {"260","GN"},
         {"260","GQ"},
         {"257","GW"},      /* 80 */
         {"488","GY"},
         {"452","HT"},
         {"835","HM"},
         {"424","HN"},
         {"740","HK"},
         {"664","IN"},
         {"700","ID"},
         {"109","IM"},
         {"612","IQ"},
         {"616","IR"},      /* 90 */
         {"007","IE"},
         {"024","IS"},
         {"624","IL"},
         {"005","IT"},
         {"464","JM"},
         {"732","JP"},
         {"653","YE"},
         {"108",""},
         {"628","JO"},
         {"090","MK"},      /* 100 */
         {"463","KY"},
         {"696","KH"},
         {"302","CM"},
         {"404","CA"},
         {"021",""},
         {"247","CV"},
         {"079","KZ"},
         {"644","QA"},
         {"346","KE"},
         {"083","KG"},      /* 110 */
         {"812","KI"},
         {"833","CC"},
         {"480","CO"},
         {"375","KM"},
         {"318","CD"},
         {"318","CG"},
         {"092","HR"},
         {"448","CU"},
         {"636","KW"},
         {"684","LA"},      /* 120 */
         {"395","LS"},
         {"054","LV"},
         {"604","LB"},
         {"268","LR"},
         {"216","LY"},
         {"037","LI"},
         {"055","LT"},
         {"104","LU"},
         {"018","LU"},
         {"743","MO"},      /* 130 */
         {"370","MG"},
         {"386","MW"},
         {"701","MY"},
         {"667","MV"},
         {"232","ML"},
         {"046","MT"},
         {"204","MA"},
         {"824","MH"},
         {"462","MQ"},
         {"228","MR"},      /* 140 */
         {"373","MU"},
         {"377","YT"},
         {"096","MK"},
         {"412","MX"},
         {"823","FM"},
         {"074","MD"},
         {"716","MN"},
         {"470","MS"},
         {"366","MZ"},
         {"676","MM"},      /* 150 */
         {"389","NA"},
         {"803","NR"},
         {"672","NP"},
         {"809","NC"},
         {"804","NZ"},
         {"814","CK"},
         {"432","NI"},
         {"478","AN"},
         {"003","NL"},
         {"002","NL"},      /* 160 */
         {"240","NE"},
         {"288","NG"},
         {"838","NU"},
         {"724","KR"},
         {"820","MP"},
         {"836","NF"},
         {"028","NO"},
         {"649","OM"},
         {"038","AT"},
         {"662","PK"},      /* 170 */
         {"825","PW"},
         {"442","PA"},
         {"801","PG"},
         {"520","PY"},
         {"504","PE"},
         {"708","PH"},
         {"813","PN"},
         {"890","AQ"},
         {"060","PL"},
         {"010","PT"},      /* 180 */
         {"372","RE"},
         {"324","RW"},
         {"066","RO"},
         {"075","RU"},
         {"806","SB"},
         {"378","ZM"},
         {"830","AS"},
         {"047","SM"},
         {"311","ST"},
         {"632","SA"},      /* 190 */
         {"030","SE"},
         {"039","CH"},
         {"036",""},
         {"248","SN"},
         {"094","YU"},
         {"355","SC"},
         {"264","SL"},
         {"382","ZW"},
         {"706","SG"},
         {"063","SK"},      /* 200 */
         {"091","SI"},
         {"342","SO"},
         {"011","ES"},
         {"669","LK"},
         {"465","LC"},
         {"408","PM"},
         {"467","VC"},
         {"449",""},
         {"329","SH"},
         {"999",""},        /* 210 */
         {"388","ZA"},
         {"224","SD"},
         {"893","GS"},
         {"728","KR"},
         {"492","SR"},
         {"027","SJ"},
         {"393","SZ"},
         {"608","SY"},
         {"082","TJ"},
         {"736","TW"},      /* 220 */
         {"352","TZ"},
         {"680","TH"},
         {"626","TP"},
         {"280","TG"},
         {"839","TK"},
         {"817","TO"},
         {"472","TT"},
         {"244","TD"},
         {"061","CZ"},
         {"062","CZ"},      /* 230 */
         {"212","TN"},
         {"052","TR"},
         {"080","TM"},
         {"454","TC"},
         {"807","TV"},
         {"350","UG"},
         {"072","UA"},
         {"064","HU"},
         {"524","UY"},
         {"081","UZ"},      /* 240 */
         {"816","VU"},
         {"045","VA"},
         {"484","VE"},
         {"647","AE"},
         {"400","US"},
         {"690","VN"},
         {"811","WF"},
         {"834","CX"},
         {"073","BY"},
         {"819","WS"},      /* 250 */
         {"322",""},
         {"306","CF"},
         {"600","CY"},
         {"***","***"}      /* 255 */
        };
        int i = 0;
        /*================================================================|
        |  ---- Verarbeitung ----                                         |
        |================================================================*/
        if (pvLand == null) return new String();

        while (!lvTab[i][1].equals("***"))
        {
            if (lvTab[i][1].equals(pvLand))
         {
                return lvTab[i][0];
         }
         i++;
        }
        return null;
    }

    /**
     * 
     * @param pvInst 
     * @param pvPLZ 
     * @return
     */
    public static int PLZ2Land(String pvInst, String pvPLZ)
    {
        int lvRetwert2;
        String lvDefPLZ = "NNNNN";
        int lvPLZ;
     /*================================================================|
     |  ---- Verarbeitung ----                                         |
     |================================================================*/
        lvRetwert2 = -1;
        if (!pvPLZ.equals(lvDefPLZ) && !pvPLZ.isEmpty())
     { /* PLZ ist da */
            lvPLZ = StringKonverter.convertString2Int(pvPLZ);
            if ((lvPLZ < 1000) || (lvPLZ > 99998))
      {
                lvRetwert2 = -1;
      }
            if ((lvPLZ > 1000) && (lvPLZ < 10000))
      { /* PLZ 0* */
                lvRetwert2 = 14; /* Sachsen default */
                if ((lvPLZ > 1940) && (lvPLZ < 1999))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 3000) && (lvPLZ < 3254))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 4890) && (lvPLZ < 6000))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 4580) && (lvPLZ < 4640))
       { /* */
                    lvRetwert2 = 16; /* Thüringen */
       } /* */
                if ((lvPLZ > 6550) && (lvPLZ < 6579))
       { /* */
                    lvRetwert2 = 16; /* Thüringen */
       } /* */
                if ((lvPLZ > 7200) && (lvPLZ < 7981))
       { /* */
                    lvRetwert2 = 16; /* Thüringen */
       } /* */
                if ((lvPLZ > 7984) && (lvPLZ < 7990))
       { /* */
                    lvRetwert2 = 16; /* Thüringen */
       } /* */
                if ((lvPLZ > 6000) && (lvPLZ < 6549))
       { /* */
                    lvRetwert2 = 15; /* Sachsen-Anhalt */
       } /* */
                if ((lvPLZ > 6600) && (lvPLZ < 6929))
       { /* */
                    lvRetwert2 = 15; /* Sachsen-Anhalt */
       } /* */
      } /* PLZ 0* */
            if ((lvPLZ > 10000) && (lvPLZ < 20000))
      { /* PLZ 1* */
                lvRetwert2 = 13; /* Mecklenburg-Vorpommern default */
                if ((lvPLZ > 10000) && (lvPLZ < 14331))
       { /* */
                    lvRetwert2 = 11; /* Berlin */
       } /* */
                if ((lvPLZ > 14714) && (lvPLZ < 14716))
       { /* */
                    lvRetwert2 = 15; /* Sachsen-Anhalt */
       } /* */
                if ((lvPLZ > 14400) && (lvPLZ < 14715))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 14722) && (lvPLZ < 16950))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 17260) && (lvPLZ < 17292))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 17308) && (lvPLZ < 17310))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 17320) && (lvPLZ < 17322))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 17325) && (lvPLZ < 17327))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 17334) && (lvPLZ < 17336))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 17336) && (lvPLZ < 17338))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 19306) && (lvPLZ < 19358))
       { /* */
                    lvRetwert2 = 12; /* Brandenburg */
       } /* */
                if ((lvPLZ > 19270) && (lvPLZ < 19274))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
      } /* PLZ 1* */
            if ((lvPLZ > 19999) && (lvPLZ < 30000))
      { /* PLZ 2* */
                lvRetwert2 = 1; /* Schleswig-Holstein default */
                if ((lvPLZ > 19999) && (lvPLZ < 21038))
       { /* */
                    lvRetwert2 = 2; /* Hamburg */
       } /* */
                if ((lvPLZ > 21038) && (lvPLZ < 21171))
       { /* */
                    lvRetwert2 = 2; /* Hamburg */
       } /* */
                if ((lvPLZ > 22000) && (lvPLZ < 22114))
       { /* */
                    lvRetwert2 = 2; /* Hamburg */
       } /* */
                if ((lvPLZ > 22114) && (lvPLZ < 22787))
       { /* */
                    lvRetwert2 = 2; /* Hamburg */
       } /* */
                if ((lvPLZ > 27498) && (lvPLZ < 27500))
       { /* */
                    lvRetwert2 = 2; /* Hamburg */
       } /* */
                if ((lvPLZ > 21201) && (lvPLZ < 21450))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 21521) && (lvPLZ < 21523))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 21600) && (lvPLZ < 21790))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 26000) && (lvPLZ < 27479))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 27606) && (lvPLZ < 27999))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 28783) && (lvPLZ < 29400))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 29430) && (lvPLZ < 30001))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 27500) && (lvPLZ < 27581))
       { /* */
                    lvRetwert2 = 4; /* Bremen */
       } /* */
                if ((lvPLZ > 28000) && (lvPLZ < 28780))
       { /* */
                    lvRetwert2 = 4; /* Bremen */
       } /* */
                if ((lvPLZ > 23920) && (lvPLZ < 24000))
       { /* */
                    lvRetwert2 = 13; /* Mecklenburg-Vorpommern */
       } /* */
                if ((lvPLZ > 29400) && (lvPLZ < 29417))
       { /* */
                    lvRetwert2 = 15; /* Sachsen-Anhalt */
       } /* */
      } /* PLZ 2* */
            if ((lvPLZ > 30000) && (lvPLZ < 40000))
      { /* PLZ 3* */
                lvRetwert2 = 3; /* Niedersachsen default */
                if ((lvPLZ > 34000) && (lvPLZ < 34330))
       { /* */
                    lvRetwert2 = 6; /* Hessen */
       } /* */
                if ((lvPLZ > 34355) && (lvPLZ < 34400))
       { /* */
                    lvRetwert2 = 6; /* Hessen */
       } /* */
                if ((lvPLZ > 34440) && (lvPLZ < 36400))
       { /* */
                    lvRetwert2 = 6; /* Hessen */
       } /* */
                if ((lvPLZ > 37193) && (lvPLZ < 37196))
       { /* */
                    lvRetwert2 = 6; /* Hessen */
       } /* */
                if ((lvPLZ > 37200) && (lvPLZ < 37300))
       { /* */
                    lvRetwert2 = 6; /* Hessen */
       } /* */
                if ((lvPLZ > 32000) && (lvPLZ < 33830))
       { /* */
                    lvRetwert2 = 5; /* Nordrhein-Westfalen */
       } /* */
                if ((lvPLZ > 34400) && (lvPLZ < 34440))
       { /* */
                    lvRetwert2 = 5; /* Nordrhein-Westfalen */
       } /* */
                if ((lvPLZ > 37650) && (lvPLZ < 37689))
       { /* */
                    lvRetwert2 = 5; /* Nordrhein-Westfalen */
       } /* */
                if ((lvPLZ > 37691) && (lvPLZ < 37697))
       { /* */
                    lvRetwert2 = 5; /* Nordrhein-Westfalen */
       } /* */
                if ((lvPLZ > 36400) && (lvPLZ < 36470))
       { /* */
                    lvRetwert2 = 16; /* Thüringen */
       } /* */
                if ((lvPLZ > 37300) && (lvPLZ < 37360))
       { /* */
                    lvRetwert2 = 16; /* Thüringen */
       } /* */
                if ((lvPLZ > 38480) && (lvPLZ < 38490))
       { /* */
                    lvRetwert2 = 15; /* Sachsen-Anhalt */
       } /* */
                if ((lvPLZ > 38800) && (lvPLZ < 39650))
       { /* */
                    lvRetwert2 = 15; /* Sachsen-Anhalt */
       } /* */
      } /* PLZ 3* */
            if ((lvPLZ > 40000) && (lvPLZ < 50000))
      { /* PLZ 4* */
                lvRetwert2 = 5; /* Nordrhein-Westfalen default */
                if ((lvPLZ > 48441) && (lvPLZ < 48466))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 48477) && (lvPLZ < 48481))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 48485) && (lvPLZ < 48489))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 48496) && (lvPLZ < 48532))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 49000) && (lvPLZ < 49460))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
                if ((lvPLZ > 49550) && (lvPLZ < 49850))
       { /* */
                    lvRetwert2 = 3; /* Niedersachsen */
       } /* */
      } /* PLZ 4* */
            if ((lvPLZ > 50000) && (lvPLZ < 60000))
      { /* PLZ 5* */
                lvRetwert2 = 5; /* Nordrhein-Westfalen default */
                if ((lvPLZ > 51597) && (lvPLZ < 51599))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 53400) && (lvPLZ < 53580))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 53613) && (lvPLZ < 53620))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 54180) && (lvPLZ < 55240))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 55252) && (lvPLZ < 56870))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 57500) && (lvPLZ < 57649))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 55239) && (lvPLZ < 55253))
       { /* */
                    lvRetwert2 = 6; /* Hessen */
       } /* */
      } /* PLZ 5* */
            if ((lvPLZ > 60000) && (lvPLZ < 70000))
      { /* PLZ 6* */
                lvRetwert2 = 6; /* Hessen default */
                if ((lvPLZ > 63700) && (lvPLZ < 63775))
       { /* */
                    lvRetwert2 = 9; /* Bayern */
       } /* */
                if ((lvPLZ > 63775) && (lvPLZ < 63929))
       { /* */
                    lvRetwert2 = 9; /* Bayern */
       } /* */
                if ((lvPLZ > 63929) && (lvPLZ < 63940))
       { /* */
                    lvRetwert2 = 9; /* Bayern */
       } /* */
                if ((lvPLZ > 63927) && (lvPLZ < 63929))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 64753) && (lvPLZ < 64755))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 68001) && (lvPLZ < 68313))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 68519) && (lvPLZ < 68550))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 68700) && (lvPLZ < 69235))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 68239) && (lvPLZ < 69430))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 69434) && (lvPLZ < 69470))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 69488) && (lvPLZ < 69503))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 69509) && (lvPLZ < 69515))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 65325) && (lvPLZ < 65327))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 65390) && (lvPLZ < 65392))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 65557) && (lvPLZ < 65583))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 65620) && (lvPLZ < 65627))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 65325) && (lvPLZ < 65327))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 65628) && (lvPLZ < 65630))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 66460) && (lvPLZ < 66510))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 66840) && (lvPLZ < 67830))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
                if ((lvPLZ > 66000) && (lvPLZ < 66460))
       { /* */
                    lvRetwert2 = 10; /* Saarland */
       } /* */
                if ((lvPLZ > 66510) && (lvPLZ < 66840))
       { /* */
                    lvRetwert2 = 10; /* Saarland */
       } /* */
      } /* PLZ 6* */
            if ((lvPLZ > 70000) && (lvPLZ < 80000))
      { /* PLZ 7* */
                lvRetwert2 = 8; /* Baden-Würtenberg default */
                if ((lvPLZ > 74593) && (lvPLZ < 74596))
       { /* */
                    lvRetwert2 = 9; /* Bayern */
       } /* */
                if ((lvPLZ > 76710) && (lvPLZ < 76892))
       { /* */
                    lvRetwert2 = 7; /* Rheinland-Pfalz */
       } /* */
      } /* PLZ 7* */
            if ((lvPLZ > 80000) && (lvPLZ < 90000))
      { /* PLZ 8* */
                lvRetwert2 = 9; /* Bayern default */
                if ((lvPLZ > 88000) && (lvPLZ < 88100))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 88146) && (lvPLZ < 88148))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 88180) && (lvPLZ < 89080))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 89080) && (lvPLZ < 89086))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 89089) && (lvPLZ < 89199))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 89500) && (lvPLZ < 89620))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
      } /* PLZ 8* */
            if ((lvPLZ > 90000) && (lvPLZ < 100000))
      { /* PLZ 9* */
                lvRetwert2 = 9; /* Bayern default */
                if ((lvPLZ > 96500) && (lvPLZ < 96530))
       { /* */
                    lvRetwert2 = 16; /* Thüringen */
       } /* */
                if ((lvPLZ > 98500) && (lvPLZ < 99999))
       { /* */
                    lvRetwert2 = 16; /* Thüringen */
       } /* */
                if ((lvPLZ > 97860) && (lvPLZ < 97878))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 97892) && (lvPLZ < 97901))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
                if ((lvPLZ > 97910) && (lvPLZ < 98000))
       { /* */
                    lvRetwert2 = 8; /* Baden-Würtenberg */
       } /* */
      } /* PLZ 9* */
     } /* PLZ ist da */
        if ((lvRetwert2 == -1) || (pvPLZ.equals(lvDefPLZ)))
     { /* Besetzung nach InstNr */
            switch (pvInst.charAt(0))
      {
       case '0' :
                    lvRetwert2 = 3;
            break;
       case '1' :
                    lvRetwert2 = 1;
            break;
       case '2' :
                    lvRetwert2 = 10;
            break;
       case '5' :
                    lvRetwert2 = 12;
            break;
       case '7' :
                    lvRetwert2 = 14;
            break;
       default:
                    lvRetwert2 = 1;
            break;
      } /* switch 1.St. der InstNr. */
     } /* Besetzung nach InstNr */
        return (lvRetwert2); /* nichts gefunden, kein EOF */
    }/*end PLZ2Land ()*/

    /**
     * @param pvKusyma 
     * @return 
     * 
     */
    public static String mapKoerperschaft(String pvKusyma)
    {
        /*
        0xxxxxxx        G       
        10000000    B_1     
        10000001    B_2     
        11000000    C       
        12000000    C       
        13000000    D       
        14000000    D       
        15xxxxxx        E       
        16xxxxxx        E       
        17xxxxxx        E       
        18xxxxxx        B_2     
        19xxxxxx        E       
        40xxxxxx        F       
        41xxxxxx        F       
        42xxxxxx        F       
        43xxxxxx        F       
        44xxxxxx        F       
        45xxxxxx        F       
        50xxxxxx        A_2     
        51xxxxxx        A_2     
        52xxxxxx        A_2     
        53xxxxxx        A_2     
        60xxxxxx        A_1     
        62xxxxxx        A_3     
        63xxxxxx        A_4     
        64xxxxxx        A_3     
        65xxxxxx        A_6     
        66xxxxxx        A_6     
        Rest default    H       */
        if (pvKusyma.startsWith("0"))
        {
            // EDR --> G
            // TXS --> ZZ_003 ?
        }
        if (pvKusyma.equals("10000000"))
        {
            // EDR --> B_1
            // TXS --> ZZ_003 ?
        }
        if (pvKusyma.equals("10000001"))
        {
            // EDR --> B_2
            // TXS --> ZZ_003 ?
        }
        if (pvKusyma.equals("11000000") || pvKusyma.equals("12000000"))
        {
            // EDR --> C
            // TXS --> DE_<Gebiet> z.B. DE_025 (Schleswig Holstein)
        }
        if (pvKusyma.equals("13000000") || pvKusyma.equals("14000000"))
        {
            // EDR --> D
            // TXS --> DE_028
        }
        if (pvKusyma.startsWith("15") || pvKusyma.startsWith("16") || pvKusyma.startsWith("17") || pvKusyma.startsWith("19"))
        {
            // EDR --> E
            // TXS --> DE_029
        }
        if (pvKusyma.startsWith("18"))
        {
            // EDR --> B_2
            // TXS --> ZZ_003 ?
        }
        if (pvKusyma.startsWith("40") || pvKusyma.startsWith("41") || pvKusyma.startsWith("42") || pvKusyma.startsWith("43") || pvKusyma.startsWith("44")
                || pvKusyma.startsWith("45"))
        {
            // EDR --> F
            // TXS --> DE_029
            //         FI_453
            //         NO_457
            //         SE_338
            //         DK_109
        }
        if (pvKusyma.startsWith("50") || pvKusyma.startsWith("51") || pvKusyma.startsWith("52") || pvKusyma.startsWith("53"))
        {
            // EDR --> A_2
            // TXS --> ZZ_003 ?
        }
        if (pvKusyma.startsWith("60"))
        {
            // EDR --> A_1
            // TXS --> <Land>_001 z.B. DE_001
        }
        if (pvKusyma.startsWith("62") || pvKusyma.startsWith("64"))
        {
            // EDR --> A_3
            // TXS --> ZZ_003 ?
        }
        if (pvKusyma.startsWith("63"))
        {
            // EDR --> A_4
            // TXS --> ZZ_003 ?
        }
        if (pvKusyma.startsWith("65") || pvKusyma.startsWith("66"))
        {
            // EDR --> A_6
            // TXS --> ZZ_003 ?
        }
        
        // Rest EDR --> H
        //      TXS --> APG_004
            
        
        return pvKusyma;
    }

    /**
     * @param pvNutzung
     * @return 
     * 
     */
    public static String mapEigentumstyp(String pvNutzung)
    {
        // CMS-Nutzung = Eigennutzung (000001)                          --> TXS-Eigentumstyp = Eigengenutzt (1)
        // CMS-Nutzung = Vermietet oder verpachtet (000002)             --> TXS-Eigentumstyp = Fremdgenutzt (2)
        // CMS-Nutzung = Gemischt (000003)                              --> TXS-Eigentumstyp = Eigen-/fremdgenutzt (3)
        // CMS-Nutzung = Nicht genutzt (000004)                         --> 'unbekannt' (leer) 
        // CMS-Nutzung = Leasing mit/ohne Kaufoption (000005/000006)    --> TXS-Eigentumstyp = Fremdgenutzt (2)
        String lvEigentumstyp = new String();
        if (pvNutzung.equals("000001"))
        {
            lvEigentumstyp = "1";
        }
        if (pvNutzung.equals("000002"))
        {
            lvEigentumstyp = "2";
        }
        if (pvNutzung.equals("000003"))
        {
            lvEigentumstyp = "3";
        }
        if (pvNutzung.equals("000004"))
        {
            lvEigentumstyp = "";
        }
        if (pvNutzung.equals("000005") || pvNutzung.equals("000006"))
        {
            lvEigentumstyp = "2";
        }
        
        return lvEigentumstyp;
    }

    /**
     * @param pvLandnum dreistelliger numerischer Code gemäß Aussenwirtschaftsstatistik
     * @return zweistelliger Ländercode gemäß ISO
     * @author Unnasch
     */
    public static String mapAWV2ISO(String pvLandnum)
    {
        String lvLaenderTab[][] =
        {
        {"004","DE"},  
        {"039","CH"},
        {"106","GB"},
        {"660","AF"},
        {"220","EG"},
        {"070","AL"},
        {"208","DZ"},
        {"457","VI"},
        {"810","UM"},
        {"832",""},
        {"043","AD"},
        {"330","AO"},
        {"446","AI"},      /* 10 */
        {"459","AG"},
        {"310",""},
        {"528","AR"},
        {"077","AM"},
        {"474","AW"},
        {"078","AZ"},
        {"334","ET"},
        {"802",""},
        {"800","AU"},
        {"625","PS"},      /* 20 */
        {"453","BS"},
        {"640","BH"},
        {"666","BD"},
        {"469","BB"},
        {"017","BE"},
        {"102","BE"},
        {"421","BZ"},
        {"284","BJ"},
        {"413","BM"},
        {"675","BT"},      /* 30 */
        {"516","BO"},
        {"093","BA"},
        {"391","BW"},
        {"892","BV"},
        {"508","BR"},
        {"468","VG"},
        {"357","IO"},
        {"703","BN"},
        {"068","BG"},
        {"100","DE"},      /* 40 */
        {"236","BF"},
        {"328","BI"},
        {"022",""},
        {"512","CL"},
        {"720","CN"},
        {"436","CR"},
        {"008","DK"},
        {"460","DM"},
        {"456","DO"},
        {"338","DJ"},      /* 50 */
        {"500","EC"},
        {"428","SV"},
        {"272","CI"},
        {"336","ER"},
        {"053","EE"},
        {"529","FK"},
        {"041","FO"},
        {"815","FJ"},
        {"032","FI"},
        {"001","FR"},      /* 60 */
        {"001","MC"},
        {"496","GF"},
        {"822","PF"},
        {"894","FR"},
        {"314","GA"},
        {"252","GM"},
        {"076","GE"},
        {"276","GH"},
        {"044","GI"},
        {"473","GD"},      /* 70 */
        {"009","GR"},
        {"406","GL"},
        {"006","GB"},
        {"458","GP"},
        {"831","GU"},
        {"416","GT"},
        {"107",""},
        {"260","GN"},
        {"260","GQ"},
        {"257","GW"},      /* 80 */
        {"488","GY"},
        {"452","HT"},
        {"835","HM"},
        {"424","HN"},
        {"740","HK"},
        {"664","IN"},
        {"700","ID"},
        {"109","IM"},
        {"612","IQ"},
        {"616","IR"},      /* 90 */
        {"007","IE"},
        {"024","IS"},
        {"624","IL"},
        {"005","IT"},
        {"464","JM"},
        {"732","JP"},
        {"653","YE"},
        {"108",""},
        {"628","JO"},
        {"090","MK"},      /* 100 */
        {"463","KY"},
        {"696","KH"},
        {"302","CM"},
        {"404","CA"},
        {"021",""},
        {"247","CV"},
        {"079","KZ"},
        {"644","QA"},
        {"346","KE"},
        {"083","KG"},      /* 110 */
        {"812","KI"},
        {"833","CC"},
        {"480","CO"},
        {"375","KM"},
        {"318","CD"},
        {"318","CG"},
        {"092","HR"},
        {"448","CU"},
        {"636","KW"},
        {"684","LA"},      /* 120 */
        {"395","LS"},
        {"054","LV"},
        {"604","LB"},
        {"268","LR"},
        {"216","LY"},
        {"037","LI"},
        {"055","LT"},
        {"104","LU"},
        {"018","LU"},
        {"743","MO"},      /* 130 */
        {"370","MG"},
        {"386","MW"},
        {"701","MY"},
        {"667","MV"},
        {"232","ML"},
        {"046","MT"},
        {"204","MA"},
        {"824","MH"},
        {"462","MQ"},
        {"228","MR"},      /* 140 */
        {"373","MU"},
        {"377","YT"},
        {"096","MK"},
        {"412","MX"},
        {"823","FM"},
        {"074","MD"},
        {"716","MN"},
        {"470","MS"},
        {"366","MZ"},
        {"676","MM"},      /* 150 */
        {"389","NA"},
        {"803","NR"},
        {"672","NP"},
        {"809","NC"},
        {"804","NZ"},
        {"814","CK"},
        {"432","NI"},
        {"478","AN"},
        {"003","NL"},
        {"002","NL"},      /* 160 */
        {"240","NE"},
        {"288","NG"},
        {"838","NU"},
        {"724","KR"},
        {"820","MP"},
        {"836","NF"},
        {"028","NO"},
        {"649","OM"},
        {"038","AT"},
        {"662","PK"},      /* 170 */
        {"825","PW"},
        {"442","PA"},
        {"801","PG"},
        {"520","PY"},
        {"504","PE"},
        {"708","PH"},
        {"813","PN"},
        {"890","AQ"},
        {"060","PL"},
        {"010","PT"},      /* 180 */
        {"372","RE"},
        {"324","RW"},
        {"066","RO"},
        {"075","RU"},
        {"806","SB"},
        {"378","ZM"},
        {"830","AS"},
        {"047","SM"},
        {"311","ST"},
        {"632","SA"},      /* 190 */
        {"030","SE"},
        {"036",""},
        {"248","SN"},
        {"094","YU"},
        {"355","SC"},
        {"264","SL"},
        {"382","ZW"},
        {"706","SG"},
        {"063","SK"},      /* 200 */
        {"091","SI"},
        {"342","SO"},
        {"011","ES"},
        {"669","LK"},
        {"465","LC"},
        {"408","PM"},
        {"467","VC"},
        {"449",""},
        {"329","SH"},
        {"999",""},        /* 210 */
        {"388","ZA"},
        {"224","SD"},
        {"893","GS"},
        {"728","KR"},
        {"492","SR"},
        {"027","SJ"},
        {"393","SZ"},
        {"608","SY"},
        {"082","TJ"},
        {"736","TW"},      /* 220 */
        {"352","TZ"},
        {"680","TH"},
        {"626","TP"},
        {"280","TG"},
        {"839","TK"},
        {"817","TO"},
        {"472","TT"},
        {"244","TD"},
        {"061","CZ"},
        {"062","CZ"},      /* 230 */
        {"212","TN"},
        {"052","TR"},
        {"080","TM"},
        {"454","TC"},
        {"807","TV"},
        {"350","UG"},
        {"072","UA"},
        {"064","HU"},
        {"524","UY"},
        {"081","UZ"},      /* 240 */
        {"816","VU"},
        {"045","VA"},
        {"484","VE"},
        {"647","AE"},
        {"400","US"},
        {"690","VN"},
        {"811","WF"},
        {"834","CX"},
        {"073","BY"},
        {"819","WS"},      /* 250 */
        {"322",""},
        {"306","CF"},
        {"600","CY"}
       };
       
       // Verarbeitung 
        if (pvLandnum == null) return new String("UNKNOWN");

       for (int i = 0; i < lvLaenderTab.length; i++)
       {
            if (lvLaenderTab[i][0].equals(pvLandnum))
        {
                return lvLaenderTab[i][1];
        }
       }
       
       return ("UNKNOWN");
    }

    /**
     * @param pvDarlRefZins max. zehn Zeichen 
     * @return Referenzzins gemäß TXS-Enum Referenzzins
     * @author Unnasch
     */
    public static String mapRefZins(String pvDarlRefZins)
    {
        String lvRefZinsTab[][] =
        {
        //0123456789         
        {"          ","keine"},  
        {"ZERO      ","keine"},
        {"FESTDARL  ","keine"},
        {"M001EURIBO","EURIBOR1MD"},
        {"M003EURIBO","EURIBOR3MD"},
        {"M006EURIBO","EURIBOR6MD"},
        {"M012EURIBO","EURIBOR1YMD"},
        {"M012EURCMS","Y001EURCMS"},
        {"Y001EURCMS","Y001EURCMS"},
        {"Y010EURCMS","Y010EURCMS"},
        {"Y020EURCMS","Y020EURCMS"},
        {"Y030EURCMS","Y030EURCMS"}
        };
        
        for (int i = 0; i < lvRefZinsTab.length; i++)
        {
            if (pvDarlRefZins.equals(lvRefZinsTab[i][0]))
          {
                return (lvRefZinsTab[i][1]);
          }
        }
        
        return("");
    }
    
    /**
     * Ermittelt den Kredittyp
     * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
     * @param pvBuergschaftprozent Buergschaftprozent
     */
    public static int ermittleKredittyp(String pvAusplatzierungsmerkmal, String pvBuergschaftprozent)
    {
    	int lvKredittyp = DarlehenLoanIQ.UNDEFINIERT;
    	
    	if (pvAusplatzierungsmerkmal.startsWith("K"))
    	{
    		if (pvAusplatzierungsmerkmal.endsWith("0") || pvAusplatzierungsmerkmal.endsWith("1"))
    		{
    			if (StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
    			{
    			  lvKredittyp = DarlehenLoanIQ.VERBUERGT_KOMMUNAL; // Verbuergt Kommunal
    			}
    			else
    			{
    		      lvKredittyp = DarlehenLoanIQ.REIN_KOMMUNAL;  // Rein Kommunal		
    			}
    		}
    		if (pvAusplatzierungsmerkmal.endsWith("2") || pvAusplatzierungsmerkmal.endsWith("4"))
    		{
    		  lvKredittyp = DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG; // Sonstige Schuldverschreibung	
    		}
    		if (pvAusplatzierungsmerkmal.endsWith("3"))
    		{
    		  lvKredittyp = DarlehenLoanIQ.BANKKREDIT; // Bankkredit
    		}
    	}

    	if (pvAusplatzierungsmerkmal.startsWith("H"))
    	{
    		if (pvAusplatzierungsmerkmal.endsWith("0") || pvAusplatzierungsmerkmal.endsWith("1"))
    		{
    		  lvKredittyp = DarlehenLoanIQ.HYPOTHEK_1A; // 1aHypothek
    		}
    		if (pvAusplatzierungsmerkmal.endsWith("2") || pvAusplatzierungsmerkmal.endsWith("3") || pvAusplatzierungsmerkmal.endsWith("4"))
    		{
    		  lvKredittyp = DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG; // Sonstige Schuldverschreibung	
    		}	
    	}

    	if (pvAusplatzierungsmerkmal.startsWith("S"))
    	{
    		if (pvAusplatzierungsmerkmal.endsWith("0") || pvAusplatzierungsmerkmal.endsWith("1"))
    		{
    		  lvKredittyp = DarlehenLoanIQ.SCHIFFSDARLEHEN; // Schiffsdarlehen
    		}
    		if (pvAusplatzierungsmerkmal.endsWith("2") || pvAusplatzierungsmerkmal.endsWith("3") || pvAusplatzierungsmerkmal.endsWith("4"))
    		{
    		  lvKredittyp = DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG; // Sonstige Schuldverschreibung	
    		}	   		
    	}

    	if (pvAusplatzierungsmerkmal.startsWith("F"))
    	{
    		if (pvAusplatzierungsmerkmal.endsWith("0") || pvAusplatzierungsmerkmal.endsWith("1"))
    		{
    		  lvKredittyp = DarlehenLoanIQ.FLUGZEUGDARLEHEN; // Flugzeugdarlehen
    		}
    		if (pvAusplatzierungsmerkmal.endsWith("2") || pvAusplatzierungsmerkmal.endsWith("3") || pvAusplatzierungsmerkmal.endsWith("4"))
    		{
    		  lvKredittyp = DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG; // Sonstige Schuldverschreibung	
    		}	    		
    	}
    	
    	if (pvAusplatzierungsmerkmal.equals("O0") || pvAusplatzierungsmerkmal.equals("O3"))
    	{
			if (StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
			{
			  lvKredittyp = DarlehenLoanIQ.VERBUERGT_KOMMUNAL; // Verbuergt Kommunal 
			}
			else
			{
		      lvKredittyp = DarlehenLoanIQ.REIN_KOMMUNAL;  // Rein Kommunal		
			}

    	}

    	if (pvAusplatzierungsmerkmal.equals("O1"))
    	{
			if (StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
			{
			  lvKredittyp = DarlehenLoanIQ.KOMMUNALVERBUERGTE_HYPOTHEK; // Kommunalverbuergte Hypothek
			}
			else
			{
		      lvKredittyp = DarlehenLoanIQ.HYPOTHEK_1A;  // 1aHypothek		
			}

    	}
    	
    	if (pvAusplatzierungsmerkmal.equals("O2") || pvAusplatzierungsmerkmal.equals("O4"))
    	{
    		lvKredittyp = DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG; // Sonstige Schuldverschreibung
    	}
    	
    	return lvKredittyp;
    }
    
    /**
     * Ermittelt den Deckungstyp
     * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
     */
    public static String ermittleDeckungstyp(String pvAusplatzierungsmerkmal)
    {
    	String lvDeckungstyp = "undefiniert";
        if (pvAusplatzierungsmerkmal.endsWith("0") || pvAusplatzierungsmerkmal.endsWith("1"))
        {
        	lvDeckungstyp = "1"; // Ordentl. gattungsklassische Deckung
        }
        
        if (pvAusplatzierungsmerkmal.endsWith("2")) 
        {
        	lvDeckungstyp = "2"; // Sichernde Ueberdeckung
        }
        
        if (pvAusplatzierungsmerkmal.endsWith("3") || pvAusplatzierungsmerkmal.endsWith("4"))
        {
        	lvDeckungstyp = "3"; // Weitere Deckung
        }
        
        // Sonderregeln OEPG
        if (pvAusplatzierungsmerkmal.equals("O3"))
        {
        	lvDeckungstyp = "1"; // Ordentl. gattungsklassische Deckung
        }

        if (pvAusplatzierungsmerkmal.equals("O4"))
        {
        	lvDeckungstyp = "2"; // Sichernde Ueberdeckung
        }
        
    	return lvDeckungstyp;
    }
    
    /**
     * Ermittelt den Zahlungstyp
     * @param pvGD841
     * @return
     */
    public static String ermittleZahlungstyp(String pvGD841)
    {
    	// 1	Annuitaet
    	// 3	Endfaellig
    	// 0	Keine
    	// 2	Raten
    	// 9	Sonderfall

    	// Default -> Sonderfall
    	String lvZahlungstyp = "9"; 
    	// 2 -> Endfaellig/Festdarlehen
    	if (pvGD841.equals("2"))
    	{
    		lvZahlungstyp = "3";
    	}
    	// 3,4,8,B,D,E und G -> Annuitaet/Tildungsdarlehen
    	if (pvGD841.equals("3") || pvGD841.equals("4") || pvGD841.equals("8") || pvGD841.equals("B") || pvGD841.equals("D") || pvGD841.equals("E") || pvGD841.equals("G"))
    	{
    		lvZahlungstyp = "1";
    	}
    	// 2 -> Raten/Abzahlungsdarlehen
    	if (pvGD841.equals("5"))
    	{
    		lvZahlungstyp = "2";
    	}
    	
    	return lvZahlungstyp;
    }
    
    /**
     * Ermittelt die Zinsabweichung
     * @param pvGD321
     * @return
     */
    public static String ermittleZinsabweichung(String pvGD321)
    {
      // TXS
      // 0		Keine
      // 1		Kurzzahlung
      // 2		Ueberlange Zahlung

      // Mapping	
      // 11		kurz                              
      // 12		lang                              
      // 15		lang                              
      // 18		kurz                              
      // 19		kurz                              
      // 21		kurz                              
      // 22		lang                              
      // 23		lang                              
      // 25		lang                              
      // 26		kurz                              
      // 27		kurz                              
      // 30		lang                              
      // 31		kurz                              
      // 32		lang                              
      // 33		kurz                              
      // 34		lang                              
      // 35		kurz                              
      // 36		lang                              
      // sonst	keine 
      
      // Default -> Keine
      String lvZinsabweichung = "0";
      // 11,18,19,21,26,27,31,33 und 35 -> Kurzzahlung
      if (pvGD321.equals("11") || pvGD321.equals("18") || pvGD321.equals("19") || pvGD321.equals("21") || pvGD321.equals("26") || pvGD321.equals("27") || pvGD321.equals("31") || pvGD321.equals("33") || pvGD321.equals("35"))
      {
    	  lvZinsabweichung = "1";
      }
      // 12,15,22,23,25,30,32,34 und 36 -> Ueberlange Zahlung
      if (pvGD321.equals("12") || pvGD321.equals("15") || pvGD321.equals("22") || pvGD321.equals("23") || pvGD321.equals("25") || pvGD321.equals("30") || pvGD321.equals("32") || pvGD321.equals("34") || pvGD321.equals("36"))
      {
    	  lvZinsabweichung = "2";
      }
      
      return lvZinsabweichung;
    }
    
    /**
     * Ermittelt den Zinsrythmus
     * @param pvGD311A
     * @param pvGD811
     * @return
     */
    public static String ermittleZinsrythmus(String pvGD311A, String pvGD811)
    {	
       // GD311A	GD811		Output
       // 14,15	 		    	0
       //		 	0			1
       // 			1			12
       //		 	2			6
       // 			3			4
       //		 	4			3
       //		 	5			9
       //		 	6			2
       //		 	A			5
       //		 	B			7
       //		 	C			8
       // 			D			10
       // 			E			11
       //			<> blank 	13
    	
      String lvZinsrythmus = new String();
      if (pvGD311A.equals("14") || pvGD311A.equals("15"))
      {
    	  lvZinsrythmus = "0";
      }
      else
      {
    	 if (!pvGD811.isEmpty())
    	 {
    		 lvZinsrythmus = "13";
    		 
    		 if (pvGD811.equals("0"))
    		 {
    			 lvZinsrythmus = "1";
    		 }
    		 if (pvGD811.equals("1"))
    		 {
    			 lvZinsrythmus = "12";
    		 }
    		 if (pvGD811.equals("2"))
    		 {
    			 lvZinsrythmus = "6";
    		 }
    		 if (pvGD811.equals("3"))
    		 {
    			 lvZinsrythmus = "4";
    		 }
    		 if (pvGD811.equals("4"))
    		 {
    			 lvZinsrythmus = "3";
    		 }
    		 if (pvGD811.equals("5"))
    		 {
    			 lvZinsrythmus = "9";
    		 }
    		 if (pvGD811.equals("6"))
    		 {
    			 lvZinsrythmus = "2";
    		 }
    		 if (pvGD811.equals("A"))
    		 {
    			 lvZinsrythmus = "5";
    		 }
    		 if (pvGD811.equals("B"))
    		 {
    			 lvZinsrythmus = "7";
    		 }
    		 if (pvGD811.equals("C"))
    		 {
    			 lvZinsrythmus = "8";
    		 }
    		 if (pvGD811.equals("D"))
    		 {
    			 lvZinsrythmus = "10";
    		 }
    		 if (pvGD811.equals("E"))
    		 {
    			 lvZinsrythmus = "11";
    		 }
    	 }
      }
      
      return lvZinsrythmus;
    }
    
    /**
     * Ermittelt den Zinstyp
     * @param pvGD805
     * @return
     */
    public static String ermittleZinstyp(String pvGD805)
    {
    	//GD805		TXS-Wert		TXS-Schluessel
    	// F		fest            1                  
    	// H		staffZins       5                  
    	// R		revFloater      3                  
    	// V		variabel        2                  
    	// Z		zeroBond        4                  
    	// sonst	undefiniert  

    	String lvZinstyp = new String();
    	
    	if (pvGD805.equals("F"))
    	{
    		lvZinstyp = "1";
    	}
    	if (pvGD805.equals("H"))
    	{
    		lvZinstyp = "5";
    	}
    	if (pvGD805.equals("R"))
    	{
    		lvZinstyp = "3";
    	}
    	if (pvGD805.equals("V"))
    	{
    		lvZinstyp = "2";
    	}
    	if (pvGD805.equals("Z"))
    	{
    		lvZinstyp = "4";
    	}
    	
    	return lvZinstyp;
	
    }
    
    /**
     * 
     * @param pvGD805
     * @param pvGD312
     * @return
     */
    public static String ermittleZinszahlart(String pvGD805, String pvGD312)
    {
    	// Wenn (GD805 = „Z“ )  -> keine
    	// sonst
    	// Input-GD312 	| Output
    	// 4 			| vorschuessig                      
    	// 7 			| vorschuessig                      
    	// 1 			| nachschuessig                     
    	// 6 			| nachschuessig                     
    	// sonst 		| keine              

    	// TXS-Schluessel		TXS-Text
    	// 0					Keine
    	// 1					Nachschuessig
    	// 2					Vorschuessig

    	// Default 'Keine'
    	String lvZinszahlart = "0";
    	if (!pvGD805.equals("Z"))
    	{
    		if (pvGD312.equals("4") || pvGD312.equals("7"))
    		{
    			lvZinszahlart = "2";
    		}
    		if (pvGD312.equals("1") || pvGD312.equals("6"))
    		{
    			lvZinszahlart = "1";
    		}
    	}
    	
    	return lvZinszahlart;
    }
    
    /**
     * 
     * @param pvGD809C
     * @return
     */
    public static String ermittleFixingKonvention(String pvGD809C)
    {
    	// Input-GD809C		Output			TXS-Schluessel
    	// 1				Beginn  		1                               
    	// 3				Beginn          1                      
    	// 2				Ende            2                      
    	// 4				Ende            2                      
    	// 5				Beginn          1                      
    	// 6				Ende            2                      
    	// sonst			keine			0
    	
    	// Default 'keine'
    	String lvFixingKonvention = "0";

    	if (pvGD809C.equals("1") || pvGD809C.equals("3") || pvGD809C.equals("5"))
    	{
    		lvFixingKonvention = "1";
    	}
    	if (pvGD809C.equals("2") || pvGD809C.equals("4") || pvGD809C.equals("6"))
    	{
    		lvFixingKonvention = "2";
    	}
    	
    	return lvFixingKonvention;
    }
    
    public static String ermittleKalenderkonvention(String pvGD821B)
    {
    	// Leer -> 0
    	// 03 -> act_360
    	// 09 -> act_act_isma
    	// 01 -> 30_360
    	// 06 -> 30e_360
    	// 04 -> act_365f
    	
    	String lvKalenderkonvention = new String();
    	if (pvGD821B.isEmpty())
    	{
    		lvKalenderkonvention = "0";
    	}
    	else
    	{
    		if (pvGD821B.equals("01"))
    			lvKalenderkonvention = "30_360";
    		if (pvGD821B.equals("03"))
    			lvKalenderkonvention = "act_360";
    		if (pvGD821B.equals("04"))
    			lvKalenderkonvention = "act_365f";
    		if (pvGD821B.equals("06"))
    			lvKalenderkonvention = "30e_360";
    		if (pvGD821B.equals("09"))
    			lvKalenderkonvention = "act_act_isma";
    	}
    	
    	return lvKalenderkonvention;
    }
    
    public static String ermittleReferenzzins(String pvGD808, String pvGD808A, String pvGD808B)
    {
    	// GD808B	GD808A	GD808	Code833
		//							BOT6M                             
	 	//							LIGBP1MD                          
    	// 3		C		23		LIEUR3MD                          
    	// 6		C		23		LIEUR6MD                          
    	// 1		C		23		LIEUR1MD                          
    	// 9		C		23		LIEUR9MD                          
    	// 12		C		23		LIEUR1YD                          
    	// 2		C		23		LIEUR2MD                          
    	//	 	 					LIGBP1WKD                         
    	//	 	 					LIGBP2MD                          
    	//	 	 					LIGBP6MD                          
    	// 12		C		52		REX10Y                            
    	// 1		B		23		LIEUR1WKD                         
    	//	 	 					LIGBP3MD                          
    	//	 	 					LIGBP1YD                          
    	// 3		B		36		EURIBOR3WD                        
    	// 					64		EONIA                             
    	// 2		B		36		EURIBOR2WD                        
    	//	 	 					LIGBP9MD                          
    	// 1		C		36		EURIBOR1MD                        
    	// 12		C		36		EURIBOR1YMD                       
    	// 2		C		36		EURIBOR2MD                        
    	// 3		C		36		EURIBOR3MD                        
    	// 5		C		36		EURIBOR5MD                        
    	// 6		C		36		EURIBOR6MD                        
    	// 7		C		36		EURIBOR7MD                        
    	// 8		C		36		EURIBOR8MD                        
    	// 11		C		36		EURIBOR11MD                       
    	// 9		C		36		EURIBOR9MD                        
    	// 1		B		36		EURIBORSWD                        
    	// 10		C		36		EURIBOR10MD                       
    	// 4		C		36		EURIBOR4MD                        
    	//					<> 23, 36, 52, 64	Sonderfall                        
    	//	 				blank	keine
    	
    	// Default 'Sonderfall'
    	String lvReferenzzins = "Sonderfall";
    	if (pvGD808.equals("23"))
    	{
    		if (pvGD808A.equals("C"))
    		{
       			lvReferenzzins = "LIEUR" + pvGD808B + "MD";
    			if (pvGD808B.equals("12"))
    			{
    				lvReferenzzins = "LIEUR1YD";
    			}
    		}
    		if (pvGD808A.equals("B"))
    		{
    			if (pvGD808B.equals("1"))
    			{
    				lvReferenzzins = "LIEUR1WKD";
    			}   			
    		}
    	}
    	if (pvGD808.equals("36"))
    	{
    		if (pvGD808A.equals("C"))
    		{
    			lvReferenzzins = "EURIBOR" + pvGD808B + "MD";
    			if (pvGD808B.equals("12"))
    			{
    				lvReferenzzins = "EURIBOR1YMD";
    			}
    		}
    		if (pvGD808A.equals("B"))
    		{
    			lvReferenzzins = "EURIBOR" + pvGD808B + "WD";
    			if (pvGD808B.equals("1"))
    			{
    				lvReferenzzins = "EURIBORSWD";
    			}
    		}   		
    	}
    	if (pvGD808.equals("52"))
    	{
    		lvReferenzzins = "REX10Y";
    	}
    	if (pvGD808.equals("64"))
    	{
    		lvReferenzzins = "EONIA";
    	}
    	
    	return lvReferenzzins;
    }
}
