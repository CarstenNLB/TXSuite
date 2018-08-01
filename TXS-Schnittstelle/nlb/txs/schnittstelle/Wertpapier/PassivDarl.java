/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.jdom2.Element;

/**
 * @author unnasch
 * Erzeugt die TXS Transaktionen für Namenspapiere aus Darlehen
 */
public class PassivDarl {

	/**
	 * 
	 */
	private String txsprojekt;
    
	/**
	 * Konstruktor
	 * @param pvPassivprojekt
	 */
    public PassivDarl(String pvPassivprojekt) 
    {
        this.txsprojekt = pvPassivprojekt;        
    }
    
    /**
     * Fuellen der TXS Transaktion fgdaten, enthaelt Basisdaten des Finanzgeschaeftes
     * @param pvPassiv
     * @param pvSummenbetrag
     * @param pvQuellsystem
     * @return Element zum Einhaengen in die XML-Ausgabe
     */
    public Element fgdaten_passivdarl(Element pvPassiv, String pvSummenbetrag, String pvQuellsystem)
    {
       Element lvEl_fgdaten = new Element("fgdaten"); 
       
       String lvProdukt;
       String lvTxstyp = "undef";
       String lvKey = "undef"; 
   
     //  el_fgdaten.setAttribute("abetrag",nodeSA04.getChildText("Nominalbetrag").replace(",", "."));
      
       lvEl_fgdaten.setAttribute("aktivpassiv","2");
      
       lvKey = pvPassiv.getChildText("ISIN").trim();
       
       // Folgendes NUR BREMEN !
       // CT - 08.08.2013 Korrektur der fehlerhaften Anlieferung der ISIN
       if (txsprojekt.startsWith("BLB"))
       {    
            lvKey = ""; // Workaround - Immer leer setzen
       }
       // Obiges NUR BREMEN !
       
       if ("".equals(lvKey))
       {
           lvKey = "DE" + pvPassiv.getChildText("Kontonummer");
       }
       
       lvEl_fgdaten.setAttribute("az", lvKey);
       
       lvEl_fgdaten.setAttribute("isin", lvKey);
       
       lvEl_fgdaten.setAttribute("kat","7");
      
       lvEl_fgdaten.setAttribute("nbetrag", pvSummenbetrag);
       
     //  el_fgdaten.setAttribute("nbetrag", passiv.getChildText("UrsprungsKapital"));
       
       // Kennzeichen Rollover
       // Defaultmaessig auf "0" (false) setzen
       // Bei Rollover muss das Kennzeichen gesetzt werden
       if (pvPassiv.getChildText("KennzeichenRollover").equals("F") ||
           pvPassiv.getChildText("KennzeichenRollover").equals("V"))
       {
         lvEl_fgdaten.setAttribute("roll", "1");
       }
       else
       {
           lvEl_fgdaten.setAttribute("roll", "0");          
       }
       
       // Sonderbehandlung Umläufe OEPG für TXS 4.05.450
       if ("PDARLOEPG".equals(pvQuellsystem))
       {
         lvEl_fgdaten.setAttribute("pfandbriefoepg","1");
       }
       
         // Produkttyp umsetzen
         // der TXS Produkttyp ist den dortigen Aufzählungen zu entnehmen
         lvProdukt = pvPassiv.getChildText("ProduktSchluessel");
         
         // -> neue Schlüssel einpflegen ?
         if (lvProdukt.equals("05011") || lvProdukt.equals("05056"))
         {
           lvTxstyp = "020101";                
         }
         if (lvProdukt.equals("05012") || lvProdukt.equals("05057"))
         {
           lvTxstyp = "020201";                
         }
         else if (lvProdukt.equals("05013") || lvProdukt.equals("05058"))
         {
           lvTxstyp = "020101";                
         }
         else if (lvProdukt.equals("05201") || lvProdukt.equals("05202"))
         {
             lvTxstyp = "020201";                
         }

         // Sonderbehandlung für die 'anderen' Register (Schiffe und Flugzeuge)
         if ("PDARLSCHF".equals(pvQuellsystem))
         {
             lvTxstyp = "020301";
         }
         else if ("PDARLFLUG".equals(pvQuellsystem))
         {
             lvTxstyp = "020401";   
         }
         
         lvEl_fgdaten.setAttribute("typ",lvTxstyp);
    
         // urlfzmon wird für passive Wertpapiere nicht gebraucht
         // daher keine Lieferung
    //   el_fgdaten.setAttribute("urlfzmon",nodeSA04.getChildText("Laufzeit"));
       
         // scheint mir nicht korrekt ermittelbar zu sein
    //   el_fgdaten.setAttribute("urtilgsatz",nodeSA01.getChildText("Tilgungssatz").replace(",", "."));
     
       lvEl_fgdaten.setAttribute("vwhrg",pvPassiv.getChildText("Satzwaehrung"));
       
       lvEl_fgdaten.setAttribute("whrg",pvPassiv.getChildText("Satzwaehrung"));
       
     //  el_fgdaten.setAttribute("text", String2XML.change2XML(passiv.getChildText("Kontozusatz1") + " " +
     //                                             passiv.getChildText("Kontozusatz2")          ));
       
       return(lvEl_fgdaten);
    }
       
    /**
     * Fuellen der TXS Transaktion konddaten, enthaelt Konditionen des Finanzgeschaeftes
     * @param pvPassiv
     * @param pvKorrekturBelieferung 
     * @return Element zum einhängen in die XML-Ausgabe
     */
    public Element konddaten_passivdarl(Element pvPassiv, boolean pvKorrekturBelieferung)
    {   
      String lvProdukt;
      
      // Produkttyp umsetzen
      lvProdukt = pvPassiv.getChildText("ProduktSchluessel");
        
      Element lvEl_konddaten = new Element("konddaten");
       
      lvEl_konddaten.setAttribute("kondkey","1");
      
      lvEl_konddaten.setAttribute("whrg",pvPassiv.getChildText("Satzwaehrung"));
      
      lvEl_konddaten.setAttribute("atkonv","0");
            
      lvEl_konddaten.setAttribute("atkonvmod","0");
      
      // immer 0
      lvEl_konddaten.setAttribute("atkonvtag","0");
      
      // hier immer DE
      lvEl_konddaten.setAttribute("bankkal","DE");
      
      // Aenderung 20180103
      //lvEl_konddaten.setAttribute("cap",pvPassiv.getChildText("Cap"));
  
      // Aenderung 20180103
      //lvEl_konddaten.setAttribute("floor",pvPassiv.getChildText("Floor"));
            
      // Aenderung 20120613
      //el_konddaten.setAttribute("edat",DatumUtilities.FormatDatum(passiv.getChildText("Zinsbeginn")));
      lvEl_konddaten.setAttribute("edat",DatumUtilities.FormatDatum(pvPassiv.getChildText("Vollvalutierungsdatum")));
      
      lvEl_konddaten.setAttribute("ekurs", pvPassiv.getChildText("Auszahlungskurs"));
      
      // immer Banktage
      lvEl_konddaten.setAttribute("fixkalart","1");
      
      // immer anchor
      lvEl_konddaten.setAttribute("fixkonv","1");
      
      lvEl_konddaten.setAttribute("fixtage","2");
      
      lvEl_konddaten.setAttribute("fixtagedir","0");
      
      // immer 0
      lvEl_konddaten.setAttribute("fixtagemod","0");
      
      // immer target
      lvEl_konddaten.setAttribute("kalfix","TARGET");
      
      // Kalenderkonvention aus Schluessel manuelle Leitungsrechung
      String lvKalkonv = "0";
      String lvMalere = pvPassiv.getChildText("MaLeReSchluessel");
      if ("9".equals(lvMalere))                      
      {                                       
        lvKalkonv = "act_act_isma";                   
      }                                       
      else if ("8".equals(lvMalere))                     
      {                                         
        lvKalkonv = "act_act";                       
      }                                        
      else if (("A".equals(lvMalere)) ||
               ("B".equals(lvMalere))   )                                             
      {                                    
        lvKalkonv = "act_leap";                     
      }                                
      else if ((" ".equals(lvMalere)) ||
               ("6".equals(lvMalere)) ||
               ("7".equals(lvMalere))   )                                               
      {                          
        lvKalkonv = "30_360";                      
      } 
      else if (("3".equals(lvMalere)) ||
               ("5".equals(lvMalere))   ) 
      {                               
        lvKalkonv ="act_365";                    
      }
      else if (("1".equals(lvMalere)) ||
               ("4".equals(lvMalere))   ) 
      {    
        lvKalkonv = "act_360";   
      }         
      else
      {
        lvKalkonv = "0";     
      }
      
      lvEl_konddaten.setAttribute("kalkonv",lvKalkonv);
      
      // kupon ungerade->2, sonst->1
      String MaLeReSchluessel = pvPassiv.getChildText("MaLeReSchluessel");
     
      if (("8".equals(MaLeReSchluessel)) ||
          ("9".equals(MaLeReSchluessel)) ||
          ("A".equals(MaLeReSchluessel)) ||
          ("B".equals(MaLeReSchluessel))   )
      {
        lvEl_konddaten.setAttribute("kupbas","2");
        lvEl_konddaten.setAttribute("kupbasodd","2");
      }
      else
      {
        lvEl_konddaten.setAttribute("kupbas","1");
        lvEl_konddaten.setAttribute("kupbasodd","1");
      }
      
      // immer gleich
      lvEl_konddaten.setAttribute("monendkonv","1");
      
      // passive Tilgungen werden in frisco.java abgeschnitten !
      lvEl_konddaten.setAttribute("mantilg","0");
      
      lvEl_konddaten.setAttribute("nomzins",pvPassiv.getChildText("DarlehenszinssatzProzent"));
      
      // ???
      lvEl_konddaten.setAttribute("refzins",ValueMapping.mapRefZins(pvPassiv.getChildText("Berechnungsgrundlage")));
      
      lvEl_konddaten.setAttribute("spread", pvPassiv.getChildText("RisikoaufschlagProzent"));
      
      // immer kurz
      lvEl_konddaten.setAttribute("tilgabw","1");
            
      // aufpassen, prolongierte
      // Sonderbehandlung Rollover
      String lvKzro = pvPassiv.getChildText("KennzeichenRollover");
      
      if (("F".equals(lvKzro)) || ("V".equals(lvKzro)))  
      {
        lvEl_konddaten.setAttribute("manzins","0");
      }    
      else   
      {
        lvEl_konddaten.setAttribute("manzins","1");  
      }
          
      
      // CT 22.01.2015 - Faelligkeit auf Tilgungsbeginn umgestellt
      //if (("F".equals(lvKzro)) || ("V".equals(lvKzro)))  
      //{
      //  lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
      //  lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
      //  lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
      //}    
      //else
      //{    
      //  if (!("00000000".equals(pvPassiv.getChildText("ZinsanpassungDatum"))))
      //  {
      //    lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
      //    lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
      //    lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
      //  }    
      //  else
      //  {
      //    lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
      //    lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
      //    lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
      //  }
      //}
      if (txsprojekt.startsWith("NLB"))
      {
        lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
        lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
        lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn"))); 
        lvEl_konddaten.setAttribute("enddat", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
      }
      
      
      //System.out.println("Faelligkeit - Kontonummer: " + pvPassiv.getChildText("Kontonummer") + " - " + pvKorrekturBelieferung);
      // CT 08.07.2014 - Sonderbehandlung -> Faelligkeit = Tilgungstermin
      //if (pvKorrekturBelieferung)
      //{
      //    lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungstermin")));          
      //}
      
      
      // CT 24.09.2012 - Sonderbehandlung BLB
      // Faelligkeit = DZA + 1, wenn Zinsbesonderheit = 4 oder 9
      // Faelligkeit = DZA, sonst
      if (txsprojekt.startsWith("BLB"))
      {
        if ("9".equals(lvMalere) || "4".equals(lvMalere))
        {
            lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(DatumUtilities.addTagOhnePruefung(pvPassiv.getChildText("ZinsanpassungDatum"))));
            lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(DatumUtilities.addTagOhnePruefung(pvPassiv.getChildText("ZinsanpassungDatum"))));
            lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(DatumUtilities.addTagOhnePruefung(pvPassiv.getChildText("ZinsanpassungDatum"))));
        }
        else
        {
            lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
            lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
            lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));            
        }  
        
        if (("F".equals(lvKzro)) || ("V".equals(lvKzro)))  
        {
            lvEl_konddaten.setAttribute("faellig", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
            lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
            lvEl_konddaten.setAttribute("tilgbeg", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));                        
        }    
      }
      
      // Tilgungsrythmus, aus Anzahl der Tilgungen (pro Jahr) und dem Tilgungsschlüssel ableiten
      // Endfällig -> Sonderfall
      // Namenspapiere -> Sonderfall
      
      String lvTilgryth = "";
      String lvAnzahlTilgperioden = "000";
      
      lvAnzahlTilgperioden = pvPassiv.getChildText("AnzahlTilgungsperioden");

      
      if ("001".equals(lvAnzahlTilgperioden))
      {
          if ("3".equals(pvPassiv.getChildText("TilgungsschluesselKON"))) 
          {
            lvTilgryth = "13"; // endfällig
          }
          else
          {    
            lvTilgryth = "12"; // eine echte Tilgung pro Jahr 
          }
      }
      else if ("012".equals(lvAnzahlTilgperioden))
      {
          lvTilgryth = "1"; // monatlich usw
      }
      else if ("002".equals(lvAnzahlTilgperioden))
      {
          lvTilgryth = "6";
      }
      else if ("004".equals(lvAnzahlTilgperioden))
      {
          lvTilgryth = "3";
      }
         
      // namenspapiere immer bullet
      if (lvProdukt.equals("05011") || lvProdukt.equals("05012") ||
          lvProdukt.equals("05101") || lvProdukt.equals("05103") ||
          lvProdukt.equals("05201") || lvProdukt.equals("05202") ||
          lvProdukt.equals("05013") || lvProdukt.equals("05056") ||
          lvProdukt.equals("05057") || lvProdukt.equals("05058") ||
          lvProdukt.equals("05156") || lvProdukt.equals("05157")   )
      {
          lvTilgryth = "0";
      }
          
      lvEl_konddaten.setAttribute("tilgryth",lvTilgryth);
      
      lvEl_konddaten.setAttribute("tilgsatz",pvPassiv.getChildText("TilgungProzent"));
      
      // es kommt nur endfällig in Frage
      lvEl_konddaten.setAttribute("zahltyp","3");
      
      lvEl_konddaten.setAttribute("tilgver","0");
      
      // daher ist die Rate immer das Restkapital
      // aber achtung, für schon geradegestellte Positionen wird von Darlehen
      // ein Restkapital von 0 geliefert, daher wird für Kontostatus 8 anders 
      // verfahren
      
      if (pvPassiv.getChildText("Kontozustand").equals("8"))
      {
        lvEl_konddaten.setAttribute("lrate", pvPassiv.getChildText("SummeNoNiFaeTilgung"));  
      }
      else
      {
        lvEl_konddaten.setAttribute("lrate", pvPassiv.getChildText("Restkapital"));
      } 
              
      // immer kurz      
      lvEl_konddaten.setAttribute("zinsabw","1");
      
      lvEl_konddaten.setAttribute("zinsbeg",DatumUtilities.FormatDatum(pvPassiv.getChildText("Zinsbeginn")));
      
      // Definition Zinstermin ...................
      // CT - 17.04.2014
      String lvNULLDat1 = "00000000";
      String lvZinsTerm = new String();
      if (pvPassiv.getChildText("LetzterZinstermin").equals(lvNULLDat1) ||
          pvPassiv.getChildText("LetzterZinstermin").isEmpty())
      { // Nichts verwertbares
          lvZinsTerm = pvPassiv.getChildText("NaechsterZinstermin");
      } // Nichts verwertbares
      else
      { // Letztes Zinsperiodenende o.k. 
          lvZinsTerm = pvPassiv.getChildText("LetzterZinstermin");
      } // Letztes Zinsperiodenende o.k.
      
      lvEl_konddaten.setAttribute("zinsdat",DatumUtilities.FormatDatum(DatumUtilities.addTagOhnePruefung(lvZinsTerm)));
      
      //if (("F".equals(lvKzro)) || ("V".equals(lvKzro)))  
      //{
          lvEl_konddaten.setAttribute("zinsenddat", DatumUtilities.FormatDatum(pvPassiv.getChildText("VertragBis")));    
      //}
      //else
      //{    
      //    // aufpassen , prolongierte
      //    if (!("00000000".equals(pvPassiv.getChildText("ZinsanpassungDatum"))))
      //    {
      //        lvEl_konddaten.setAttribute("zinsenddat", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
      //    }    
      //    else
      //    {
      //        lvEl_konddaten.setAttribute("zinsenddat", DatumUtilities.FormatDatum(pvPassiv.getChildText("VertragBis")));
      //    }
      //}
      
      // immer anchor
      lvEl_konddaten.setAttribute("zinsperkonv","1");
      
      // Zinsrythmus anhand zweier Felder und für zeros Sonderfall
      String lvZinsryth ="";
      
      String lvAnzahlZinsperioden = pvPassiv.getChildText("AnzahlZinsperioden");
      
      String lvGrTilgPer = pvPassiv.getChildText("GrTilgPer");
     
      if ((0.0 == Double.parseDouble(pvPassiv.getChildText("DarlehenszinssatzProzent"))) ||
          ("ZERO      ".equals(pvPassiv.getChildText("Berechnungsgrundlage")))              )
      {
          lvZinsryth = "13";  
      }
      else
      {    
          if ("001".equals(lvAnzahlZinsperioden))
          {
              lvZinsryth = "12";
          }
          else if ("012".equals(lvAnzahlZinsperioden))
          {
            lvZinsryth = "1";
          }
          else if ("002".equals(lvAnzahlZinsperioden))
          {
              lvZinsryth = "6";
          }
          else if ("004".equals(lvAnzahlZinsperioden))
          {
              lvZinsryth = "3";
          }
          else
          {
             if ("330".equals(lvGrTilgPer))
             {
                 lvZinsryth = "11"; 
             }
             else if ("300".equals(lvGrTilgPer))
             {
                 lvZinsryth = "10"; 
             }
             else if ("270".equals(lvGrTilgPer))
             {
                 lvZinsryth = "9"; 
             }
             else if ("240".equals(lvGrTilgPer))
             {
                 lvZinsryth = "8"; 
             }
             else if ("210".equals(lvGrTilgPer))
             {
                 lvZinsryth = "7"; 
             }
             else if ("150".equals(lvGrTilgPer))
             {
                 lvZinsryth = "5"; 
             }
             else if ("120".equals(lvGrTilgPer))
             {
                 lvZinsryth = "4"; 
             }
             else
             {
                 lvZinsryth = "0";               
             }
          }
      }      
      lvEl_konddaten.setAttribute("zinsryth",lvZinsryth);
       
      String lvTxszinstyp = "";
      
      // eventuell nach ValueMapping auslagern
      // welche Ausprägungen aus Darlehen gibt es ??
      if (pvPassiv.getChildText("Zinstyp").equals("F"))
      {
          
       // Aber Achtung, Floater auf EURIBOR usw habe auch ein 'F' sind aber variabel
       // Zeros ebenfalls  
       // und es gibt auch Zeros ohne ZERO, diese werden über den Zinsssatz 0 identifiziert   
       // Telefonat mit R.Lau 111222
          if (0.0 == Double.parseDouble(pvPassiv.getChildText("DarlehenszinssatzProzent")))
          {
              lvTxszinstyp = "4";   
          }
             //123456790
          else if (("          ".equals(pvPassiv.getChildText("Berechnungsgrundlage"))) ||
            
              ("FESTDARL  ".equals(pvPassiv.getChildText("Berechnungsgrundlage")))    )
          {
            lvTxszinstyp = "1";        
          }
          else if ("ZERO      ".equals(pvPassiv.getChildText("Berechnungsgrundlage")))
          {
              lvTxszinstyp = "4";   
          }
          else
          {
            lvTxszinstyp = "2";   
          }
      }
      else if (pvPassiv.getChildText("Zinstyp").equals("V"))
      {
          lvTxszinstyp = "2";
      }    
      else
      {
          lvTxszinstyp = "unbekannt";
      }    
      
      lvEl_konddaten.setAttribute("zinstyp", lvTxszinstyp);
      
      // Zinszahlungsart aus NachsterZinstermin und NaechsteZinsfaelligkeit
      String lvNachsterZinstermin = pvPassiv.getChildText("NaechsterZinstermin");
      
      String lvNaechsteZinsfaelligkeit = pvPassiv.getChildText("NaechsteZinsfaelligkeit"); 
      
      if (lvNachsterZinstermin.compareTo(lvNaechsteZinsfaelligkeit) < 0)
      {
        lvEl_konddaten.setAttribute("zinszahlart","1");
      }
      else if (lvNachsterZinstermin.compareTo(lvNaechsteZinsfaelligkeit) > 0)
      {
        lvEl_konddaten.setAttribute("zinszahlart","2");
      }
      else
      {
        lvEl_konddaten.setAttribute("zinszahlart","0");
      }
      
      return(lvEl_konddaten);
    }  
    
    /**
     * Füllen der TXS Transaktion wpposdaten, enthält weitere Wertpapierdaten zum Finanzgeschäft
     * @param pvPassiv 
     * @param pvKorrekturBelieferung 
     * @param pvRestsaldoNull 
     * @return Element zum einhängen in die XML-Ausgabe
     */
    public Element wpposdaten_passivdarl(Element pvPassiv, boolean pvKorrekturBelieferung, boolean pvRestsaldoNull)
    {
      Element lvEl_wpposdaten = new Element("wpposdaten");
       
      String lvKey = pvPassiv.getChildText("ISIN").trim();
      
      // NUR BREMEN !
      // CT - 08.08.2013 Korrektur der fehlerhaften Anlieferung der ISIN
      if (txsprojekt.startsWith("BLB"))
      {    
           lvKey = ""; // Workaround - Immer leer setzen
      }
      // OBEN NUR BREMEN
      
      if ("".equals(lvKey))
      {
        lvKey = "DE" + pvPassiv.getChildText("Kontonummer");
          
        lvEl_wpposdaten.setAttribute("wpposkey", lvKey +"_0");
        
        lvEl_wpposdaten.setAttribute("lfdnr", "0"); // immer 0
      }
      else
      {    
        lvEl_wpposdaten.setAttribute("wpposkey", lvKey + "_" + pvPassiv.getChildText("Kontonummer"));
        
        lvEl_wpposdaten.setAttribute("lfdnr", pvPassiv.getChildText("Kontonummer")); 
      }
      
      lvEl_wpposdaten.setAttribute("whrg",pvPassiv.getChildText("Satzwaehrung"));
       
      lvEl_wpposdaten.setAttribute("nbetrag", pvPassiv.getChildText("UrsprungsKapital"));
      
      //  el_wpposdaten.setAttribute("abwfaell", DatumUtilities.FormatDatum(passiv.getChildText("Tilgungstermin")));
      
      // aufpassen , prolongierte
      // Sonderbehandlung Rollover
      String lvKzro = pvPassiv.getChildText("KennzeichenRollover");

      if (("F".equals(lvKzro)) || ("V".equals(lvKzro)))  
      {
        lvEl_wpposdaten.setAttribute("abwfaell", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
      }    
      else
      {    
          if (!("00000000".equals(pvPassiv.getChildText("ZinsanpassungDatum"))))
          {
              lvEl_wpposdaten.setAttribute("abwfaell", DatumUtilities.FormatDatum(pvPassiv.getChildText("ZinsanpassungDatum")));
          }    
          else
          {
              lvEl_wpposdaten.setAttribute("abwfaell", DatumUtilities.FormatDatum(pvPassiv.getChildText("Tilgungsbeginn")));
          }
      }
      
      System.out.println("Restkapital - Kontonummer: " + pvPassiv.getChildText("Kontonummer") + " - " + pvKorrekturBelieferung);

      // aber achtung, für schon geradegestellte Positionen wird von Darlehen
      // ein Restkapital von 0 geliefert, daher wird für Kontostatus 8 anders verfahren
      // Sonderbehandlung CT 08.07.2014 - Restkapital nicht liefern, wenn Umsatz12 == 0.0
      //if (!pvKorrekturBelieferung)
      //{
          if (!pvRestsaldoNull)
          {
            if (pvPassiv.getChildText("Kontozustand").equals("8"))
            {
              lvEl_wpposdaten.setAttribute("rkapi", pvPassiv.getChildText("SummeNoNiFaeTilgung"));  
            }
            else
            {
              lvEl_wpposdaten.setAttribute("rkapi", pvPassiv.getChildText("Restkapital"));
            }
          }
          else
          {
              lvEl_wpposdaten.setAttribute("rkapi", pvPassiv.getChildText("0.0"));             
          }
      //}
      
      return(lvEl_wpposdaten);
    }
    
    /**
     * Fuellen der TXS Transaktion kredkunde, enthaelt Informationen zum Geschaeftspartner am Finanzgeschaeft
     * @param pvPassiv
     * @param pvQuellsystem
     * @return Element zum Einhaengen in die XML-Ausgabe
     */
    public Element kredkunde_passivdarl(Element pvPassiv, String pvQuellsystem)
    {
      String lvKdnrhelper = "";  
      
      Element lvEl_kredkunde = new Element("kredkunde");
     
      lvKdnrhelper = "0000000000" + pvPassiv.getChildText("Kundennummer");
      
      lvKdnrhelper = lvKdnrhelper.substring(lvKdnrhelper.length()-10, lvKdnrhelper.length() );
      
      lvEl_kredkunde.setAttribute("kdnr", lvKdnrhelper );
      
      lvEl_kredkunde.setAttribute("quelle",pvQuellsystem);
      
      lvEl_kredkunde.setAttribute("rolle","90");
          
      return(lvEl_kredkunde);
    }     
}
