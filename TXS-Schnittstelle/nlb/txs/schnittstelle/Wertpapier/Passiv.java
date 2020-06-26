/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier;

import java.util.List;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.jdom2.Element;

/**
 * @author Stefan Unnasch
 *
 * Erzeugt die TXS Transaktionen f�r passive Finanzgeschaefte aus MAVIS
 */
@Deprecated
public class Passiv {
        
    /**  
     * Fuellen der TXS Transaktion fgdaten, enthaelt Basisdaten des Finanzgeschaeftes
     * @param pvPassiv 
     * @param pvKey 
     * @return Element zum einhaengen in die XML-Ausgabe 
     */  
    public Element fgdaten_passiv(Element pvPassiv, Element pvKey)
    {
       Element lvEl_fgdaten = new Element("fgdaten"); 
        
       List<Element> lvListSA01 = pvPassiv.getChildren("SATZSA01");
       Element lvNodeSA01 = (Element) lvListSA01.get(0);
       
       List<Element> lvListSA04 = pvPassiv.getChildren("SATZSA04");
       Element lvNodeSA04 = (Element) lvListSA04.get(0);
       
       // neu TXS Release Q3 2012
       List<Element> lvListZUSATZ = pvPassiv.getChildren("TXSZUSATZ");
       Element lvNodeZUSATZ = (Element) lvListZUSATZ.get(0);
              
       // fgdaten
       lvEl_fgdaten.setAttribute("abetrag",lvNodeSA04.getChildText("Saldo").replace(",", "."));
      
       lvEl_fgdaten.setAttribute("aktivpassiv","2");
       
       lvEl_fgdaten.setAttribute("az",pvKey.getChildText("Produkt"));
       
       lvEl_fgdaten.setAttribute("isin",pvKey.getChildText("Produkt"));
       
       lvEl_fgdaten.setAttribute("kat","7");
       
       lvEl_fgdaten.setAttribute("nbetrag",lvNodeSA04.getChildText("Nominalbetrag").replace(",", "."));
       
       // Kommt aus GD942 oder GD952
       lvEl_fgdaten.setAttribute("kuendr",lvNodeSA01.getChildText("Kuendigungsrecht"));
      
       // Anpassungen wegen der Uml�ufe zu �PG/Schiffen/Flugzeugen
       // diese zun�chst aussteuern
       String lvmeinTyp = lvNodeSA04.getChildText("WPArt");
       
       // Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
       if ("O0".equals(lvNodeSA01.getChildText("AusplazMM")) || "O1".equals(lvNodeSA01.getChildText("AusplazMM")) || "O3".equals(lvNodeSA01.getChildText("AusplazMM")))
       {
           // Typzuordung wie Pfandbg
           lvEl_fgdaten.setAttribute("pfandbriefoepg","1");
           
       }
       else if (("S0".equals(lvNodeSA01.getChildText("AusplazMM"))) ||
                ("S1".equals(lvNodeSA01.getChildText("AusplazMM"))))  
       {
           lvmeinTyp="010301";
       }
       else if (("F0".equals(lvNodeSA01.getChildText("AusplazMM"))) ||
                ("F1".equals(lvNodeSA01.getChildText("AusplazMM"))))  
       {
           // zun�chst nur eine direkte Zuordnung
           lvmeinTyp="010401";
       }
      
       // wie oben ermittelt setzen
       lvEl_fgdaten.setAttribute("typ", lvmeinTyp);
     
       // ???
       //el_fgdaten.setAttribute("urlfzmon",nodeSA04.getChildText("Laufzeit"));
       
       lvEl_fgdaten.setAttribute("urtilgsatz",lvNodeSA01.getChildText("Tilgungssatz").replace(",", "."));
     
       lvEl_fgdaten.setAttribute("vwhrg",lvNodeSA04.getChildText("Waehrung"));
       
       lvEl_fgdaten.setAttribute("whrg",lvNodeSA01.getChildText("Betragswaehrung"));
       
       // neu TXS Release Q3 2012
       lvEl_fgdaten.setAttribute("mavprod",lvNodeZUSATZ.getChildText("MAVPROD")); 
    
       lvEl_fgdaten.setAttribute("gd160",lvNodeZUSATZ.getChildText("GD160"));
       
       lvEl_fgdaten.setAttribute("gd260",lvNodeZUSATZ.getChildText("GD260"));
       
       lvEl_fgdaten.setAttribute("gd776",lvNodeZUSATZ.getChildText("GD776")); 
       
       lvEl_fgdaten.setAttribute("gd776b",lvNodeZUSATZ.getChildText("GD776B")); 
       
       lvEl_fgdaten.setAttribute("gibnot",lvNodeZUSATZ.getChildText("GIBNOT")); 
       
       return(lvEl_fgdaten); 
    }
   
    /**
     * Fuellen der TXS Transaktion konddaten, enthaelt Daten zur Globalurkunde
     * @param pvPassiv 
     * @param pvKey 
     * @return Element zum einh�ngen in die XML-Ausgabe
     */
    public Element gudaten_passiv(Element pvPassiv, Element pvKey)
    {
        Element lvEl_gudaten = new Element("gudaten"); 
        
        List<Element> lvListSA05 = pvPassiv.getChildren("SATZSA05");
        Element nodeSA05 = (Element) lvListSA05.get(0);
        
        List<Element> lvListSA04 = pvPassiv.getChildren("SATZSA04");
        Element lvNodeSA04 = (Element) lvListSA04.get(0);
        
        List<Element> lvListSA01 = pvPassiv.getChildren("SATZSA01");
        Element lvNodeSA01 = (Element) lvListSA01.get(0);
        
        // Urkundenregistertyp
        
        String lvmyregtyp = "";
        
        lvmyregtyp = lvNodeSA04.getChildText("WPArt").substring(3,4);
        
        // Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
        if (("S0".equals(lvNodeSA01.getChildText("AusplazMM"))) ||
            ("S1".equals(lvNodeSA01.getChildText("AusplazMM"))))
        {
        	lvmyregtyp = "4";
        }
        else if (("F0".equals(lvNodeSA01.getChildText("AusplazMM"))) ||
        		 ("F1".equals(lvNodeSA01.getChildText("AusplazMM"))))
        {
        	lvmyregtyp = "5";
        } 

        lvEl_gudaten.setAttribute("regtyp",lvmyregtyp);
        
        lvEl_gudaten.setAttribute("gukey",pvKey.getChildText("Produkt")+"_1");
        
        // �nderung 20120306
        // spezial f�r zeros mit R�ckzahlungskurs > 100% 
        if (Double.parseDouble(lvNodeSA01.getChildText("Rueckzahlungskurs").replace(",", ".")) > 100) 
        {
         // ivLOGGER.info("Spezialfall zero" + pvKey.getChildText("Produkt"));
         // ivLOGGER.info("R�ckzahlungskurs (>100)=" + lvNodeSA01.getChildText("Rueckzahlungskurs"));
         // ivLOGGER.info("biszu=" + lvNodeSA04.getChildText("Nominalbetrag"));
            
          lvEl_gudaten.setAttribute("biszu", lvNodeSA04.getChildText("Nominalbetrag").replace(",", "."));
        }    
        else
        {
          lvEl_gudaten.setAttribute("biszu",nodeSA05.getChildText("Bis_zu_Betrag").replace(",", "."));
        }
            
        lvEl_gudaten.setAttribute("biszudat",DatumUtilities.FormatDatum(nodeSA05.getChildText("Bis_zu_Datum")));                           
        
        lvEl_gudaten.setAttribute("nr",nodeSA05.getChildText("Nummer"));
                
        // el_gudaten.setAttribute("valuta",nodeSA05.getChildText("Valutierung").replace(",", "."));
        lvEl_gudaten.setAttribute("valuta", lvNodeSA04.getChildText("Nominalbetrag").replace(",", "."));
        
        lvEl_gudaten.setAttribute("whrg",nodeSA05.getChildText("BWaehrung"));
                
        return(lvEl_gudaten);
    }
    
    /**
     * Fuellen der TXS Transaktion konddaten, enthaelt Konditionen des Finanzgeschaeftes
     * @param pvPassiv 
     * @param pvKey 
     * @param pvBLZ
     * @return Element zum einhaengen in die XML-Ausgabe
     */
    public Element konddaten_passiv(Element pvPassiv, Element pvKey, String pvBLZ)
    {
       Element lvEl_konddaten = new Element("konddaten"); 
       
       List<Element> lvListSA01 = pvPassiv.getChildren("SATZSA01");
       Element lvNodeSA01 = (Element) lvListSA01.get(0);
  
       List<Element> lvListSA04 = pvPassiv.getChildren("SATZSA04");
       Element lvNodeSA04 = (Element) lvListSA04.get(0);
       
       // konddaten
       lvEl_konddaten.setAttribute("kondkey","1");
       
       // siehe M710XML / Mischmasch aus GD910 (Letzter Tilgungstermin) und SOMA
       lvEl_konddaten.setAttribute("faellig",DatumUtilities.FormatDatum(lvNodeSA04.getChildText("Sonderfaelligkeiten")));
       
       // Endedatum auf Faelligkeit setzen - Lau 10.08.2015
       //// BLB-Variante: Kein enddat - CT 08.12.2015 - mit BLB diskutieren
       lvEl_konddaten.setAttribute("enddat", DatumUtilities.FormatDatum(lvNodeSA04.getChildText("Sonderfaelligkeiten")));
       
       lvEl_konddaten.setAttribute("ekurs", lvNodeSA01.getChildText("Emissionskurs").replace(",", "."));
       
       // aktiv ab 20120306
       lvEl_konddaten.setAttribute("rkurs", lvNodeSA01.getChildText("Rueckzahlungskurs").replace(",", "."));
              
       lvEl_konddaten.setAttribute("edat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Emissionsdatum")));
       
       lvEl_konddaten.setAttribute("atkonv",ValueMapping.changeArbeitstagskonvention(lvNodeSA01.getChildText("Arbeitstagkonv")));
       
       lvEl_konddaten.setAttribute("atkonvmod",lvNodeSA01.getChildText("ArbeitstagkonvMOD"));
       
       lvEl_konddaten.setAttribute("atkonvtag",lvNodeSA01.getChildText("ArbeitstagkonvTAG"));
       
       lvEl_konddaten.setAttribute("bankkal",lvNodeSA01.getChildText("Kalender"));
       
       lvEl_konddaten.setAttribute("spread", lvNodeSA01.getChildText("Zinszuschlag").replace(",", "."));
       
       lvEl_konddaten.setAttribute("cap", lvNodeSA01.getChildText("Obergrenze").replace(",", "."));
                       
       lvEl_konddaten.setAttribute("floor", lvNodeSA01.getChildText("Untergrenze").replace(",", "."));
       
       lvEl_konddaten.setAttribute("kalfix",lvNodeSA01.getChildText("KALFixing"));
       
       // einmal aus MAVIS abfangen
       if (lvNodeSA01.getChildText("KalendKonvZins").equals("keine"))
       {
         lvEl_konddaten.setAttribute("kalkonv","0");
       }
       else if (lvNodeSA01.getChildText("KalendKonvZins").equals("30E_360"))
       {
         lvEl_konddaten.setAttribute("kalkonv","30e_360");
       }
       else
       {
         lvEl_konddaten.setAttribute("kalkonv",lvNodeSA01.getChildText("KalendKonvZins"));
       }
   
       lvEl_konddaten.setAttribute("lrate", lvNodeSA01.getChildText("Leistungsrate").replace(",", "."));
       
       lvEl_konddaten.setAttribute("monendkonv",ValueMapping.changeMonatsendekonvention(lvNodeSA01.getChildText("MonatsendKonv")));
       
       // es werden aus MAVIS momentan keine cashflows geliefert
       lvEl_konddaten.setAttribute("mantilg","0");
       // es werden aus MAVIS momentan keine cashflows geliefert
       lvEl_konddaten.setAttribute("manzins","0");
       
       lvEl_konddaten.setAttribute("nomzins", lvNodeSA01.getChildText("Nominalzinssatz").replace(",", "."));
       
       lvEl_konddaten.setAttribute("refzins", lvNodeSA01.getChildText("Referenzzins").trim());
                     
       lvEl_konddaten.setAttribute("tilgabw","0");
       
       // siehe M710XML / Mischmasch aus GD910 (Letzter Tilgungstermin) und SOMA 
       lvEl_konddaten.setAttribute("tilgdat",DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Tilgungstermin")));
   
       // siehe M710XML / Mischmasch aus GD910 (Letzter Tilgungstermin) und SOMA
       lvEl_konddaten.setAttribute("tilgbeg",DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Tilgungstermin")));
       
       lvEl_konddaten.setAttribute("tilgperkonv", ValueMapping.changePeriodenkonvention(lvNodeSA01.getChildText("TilperiodenKonv")));
              
       // Tilgungsrythmus defaultmaessig auf 'bullet' (0) setzen - CT 26.02.2015
       //// BLB-Variante - CT 8.12.2015 - mit BLB diskutieren
       if (pvBLZ.equals("25050000")) // NLB
       {
           lvEl_konddaten.setAttribute("tilgryth", "0");    	   
       }
       else // BLB
       {
           lvEl_konddaten.setAttribute("tilgryth", lvNodeSA01.getChildText("Tilgungstyp").trim());
       }
       
       lvEl_konddaten.setAttribute("tilgsatz",lvNodeSA01.getChildText("Tilgungssatz").replace(",", "."));
       
       lvEl_konddaten.setAttribute("zahltyp", lvNodeSA01.getChildText("Zahlungstyp"));
       
       if ("3".equals(lvNodeSA01.getChildText("Zahlungstyp")))
       {
         lvEl_konddaten.setAttribute("tilgver","0");
       }
     
       lvEl_konddaten.setAttribute("zinsabw",ValueMapping.changeAbweichung(lvNodeSA01.getChildText("Zinsabweichung").trim()));
       
       lvEl_konddaten.setAttribute("zinsbeg", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Zinsbeginn")));
              
       lvEl_konddaten.setAttribute("zinsenddat",DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Zinsenddatum")));
       
       lvEl_konddaten.setAttribute("zinsperkonv", ValueMapping.changePeriodenkonvention(lvNodeSA01.getChildText("ZinsperiodenKonv")));
       
       lvEl_konddaten.setAttribute("zinsryth",lvNodeSA01.getChildText("Zinszahlungstyp").trim());
       
       lvEl_konddaten.setAttribute("zinstyp",ValueMapping.changeZinstyp(lvNodeSA01.getChildText("Zinstyp")));
       
       lvEl_konddaten.setAttribute("zinszahlart",ValueMapping.changeZinszahlart(lvNodeSA01.getChildText("Zinszahlungsart")));
       
       lvEl_konddaten.setAttribute("zinsfak",lvNodeSA01.getChildText("Zinsfaktor").replace(",", "."));
       
       if (!("0".equals(lvNodeSA01.getChildText("Voltztkupdatum"))))
       {
           lvEl_konddaten.setAttribute("vzinsdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Voltztkupdatum")));           
       }
       
       if (!("0".equals(lvNodeSA01.getChildText("Vorltzttildatum"))))
       {
           lvEl_konddaten.setAttribute("vtilgdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Vorltzttildatum")));           
       }
           
       lvEl_konddaten.setAttribute("whrg", lvNodeSA04.getChildText("BWaehrung"));
       
       lvEl_konddaten.setAttribute("fixkalart",ValueMapping.changeKalenderart(lvNodeSA01.getChildText("KalenderKonvRef")));
       
       lvEl_konddaten.setAttribute("fixkonv",ValueMapping.changePeriodenbeginnEnde(lvNodeSA01.getChildText("Fixingkonv")));
       
       lvEl_konddaten.setAttribute("fixtage",lvNodeSA01.getChildText("Fixingtage"));
       
       lvEl_konddaten.setAttribute("fixtagedir",ValueMapping.changeArbeitstagskonvention(lvNodeSA01.getChildText("FixingtageDIR")));
       
       lvEl_konddaten.setAttribute("fixtagemod",lvNodeSA01.getChildText("FixingtageMOD"));
       
       // Anpassungen an TXS 4.03 wegen ge�nderter Barwertberechnungen dort an variablen Papieren
       if ("010202".equals(lvNodeSA04.getChildText("WPArt")))
       {
           //System.out.println("Sonderfall 010202 ");
           
           lvEl_konddaten.setAttribute("kupbas", "0");
           
           lvEl_konddaten.setAttribute("kupbasodd", "0");
           
           lvEl_konddaten.setAttribute("datltztfix",DatumUtilities.FormatDatum(lvNodeSA01.getChildText("LTZTFIXDATUM")));
           
           lvEl_konddaten.setAttribute("fixrhyth",lvNodeSA01.getChildText("Zinszahlungstyp").trim());
       }
       else
       {    
           lvEl_konddaten.setAttribute("kupbas", ValueMapping.changeKuponbasis(lvNodeSA01.getChildText("Kuponbasis")));
           
           lvEl_konddaten.setAttribute("kupbasodd", ValueMapping.changeKuponbasis(lvNodeSA01.getChildText("KuponbasisODD")));
       }
       
       // spezial f�r zeros ?!?!
       if ("0".equals(lvNodeSA01.getChildText("Zinstermin"))) 
       {
         lvEl_konddaten.setAttribute("zinsdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Zinsbeginn")));
       }
       else
       {
         lvEl_konddaten.setAttribute("zinsdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Zinstermin")));
       }    
       
       return(lvEl_konddaten); 
    }
    
    /**
     * Fuellen der TXS Transaktion wpposdaten, enthaelt weitere Wertpapierdaten zum Finanzgeschaeft 
     * @param pvPassiv 
     * @param pvKey 
     * @return Element zum einhaengen in die XML-Ausgabe 
     */
    public Element wpposdaten_passiv(Element pvPassiv, Element pvKey)
    {
      List<Element> lvListSA04 = pvPassiv.getChildren("SATZSA04");
      Element lvNodeSA04 = (Element) lvListSA04.get(0);  
        
      Element lvEl_wpposdaten = new Element("wpposdaten"); 
      
      lvEl_wpposdaten.setAttribute("wpposkey",pvKey.getChildText("Produkt")+"_0");
      
      lvEl_wpposdaten.setAttribute("lfdnr","0");
      
   //   el_wpposdaten.setAttribute("abwfaell",DatumUtilities.FormatDatum(nodeSA04.getChildText("Sonderfaelligkeiten")));
      
      lvEl_wpposdaten.setAttribute("whrg", lvNodeSA04.getChildText("BWaehrung"));
      
      lvEl_wpposdaten.setAttribute("nbetrag", lvNodeSA04.getChildText("Nominalbetrag").replace(",", "."));
      
      lvEl_wpposdaten.setAttribute("rkapi", lvNodeSA04.getChildText("Saldo").replace(",", "."));
      
      return(lvEl_wpposdaten);  
        
    }
}
