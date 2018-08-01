/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier;

import java.util.List;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Utilities.WPSuffix;

import org.jdom2.Element;

/**
 * @author Stefan Unnasch
 * Erzeugt die TXS Transaktionen fuer aktive Finanzgeschaefte aus MAVIS
 */
public class Aktiv {
    
	/**
	 * 
	 */
    String ivTxsprojekt;
    
    /**
     * Konstruktor
     * @param pvAktivprojekt
     */
    public Aktiv(String pvAktivprojekt) 
    {
        this.ivTxsprojekt = pvAktivprojekt;        
    }

    /**
     * Fuellen der TXS Transaktion fgdaten, enthaelt Basisdaten des Finanzgeschaeftes 
     * @param pvAktiv 
     * @param pvKey 
     * @param pvN_Betrag 
     * @param pvA_Betrag 
     * @param pvSetzetkurs 
     * @return Element zum Einhaengen in die XML-Ausgabe
     */
    public Element fgdaten_aktiv(Element pvAktiv, Element pvKey, String pvN_Betrag, String pvA_Betrag, boolean pvSetzetkurs)
    {
       Element lvEl_fgdaten = new Element("fgdaten"); 
     
       List<Element> lvListSA03 = pvAktiv.getChildren("SATZSA03");
       Element lvNodeSA03 = (Element) lvListSA03.get(0);
       
       List<Element> lvListSA01 = pvAktiv.getChildren("SATZSA01");
       Element lvNodeSA01 = (Element) lvListSA01.get(0);
       
    // neu TXS Release Q3 2012
       List<Element> lvListZUSATZ = pvAktiv.getChildren("TXSZUSATZ");
       Element lvNodeZUSATZ = (Element) lvListZUSATZ.get(0);
              
       // fgdaten
       lvEl_fgdaten.setAttribute("aktivpassiv","1");
       
       lvEl_fgdaten.setAttribute("az",pvKey.getChildText("Produkt"));
       
       lvEl_fgdaten.setAttribute("isin",pvKey.getChildText("Produkt"));
       
       // Depotnummer neues benutzerdefiniertes Feld in TXS Q3 2012 
       lvEl_fgdaten.setAttribute("depotnummer",pvKey.getChildText("DepotNR"));
                            
       lvEl_fgdaten.setAttribute("kat","2");
      
       lvEl_fgdaten.setAttribute("vwhrg",lvNodeSA03.getChildText("Waehrung"));
       
       lvEl_fgdaten.setAttribute("whrg",lvNodeSA03.getChildText("Waehrung"));
       
    //   el_fgdaten.setAttribute("nbetrag",nodeSA03.getChildText("Nominalbetrag").replace(",", "."));
       
    //   el_fgdaten.setAttribute("abetrag",nodeSA03.getChildText("Betrag").replace(",", "."));
   
       lvEl_fgdaten.setAttribute("nbetrag", pvN_Betrag);
       
       // Unnasch 20120123
       lvEl_fgdaten.setAttribute("rkapi", pvN_Betrag);
              
       // Auzahlungsbetrag, fragwuerdig ?!?!?!
       lvEl_fgdaten.setAttribute("abetrag", pvA_Betrag);
   
       //if ("ja".equals(pvSetzetkurs))
       if (pvSetzetkurs)
       {
         lvEl_fgdaten.setAttribute("tkurs",lvNodeSA03.getChildText("Einstandskurs").replace(",", "."));
       }
       
       //el_fgdaten.setAttribute("urlfzmon",nodeSA03.getChildText("Laufzeit"));
       
       lvEl_fgdaten.setAttribute("urtilgsatz",lvNodeSA01.getChildText("Tilgungssatz").replace(",", "."));
       
       lvEl_fgdaten.setAttribute("kuendr",lvNodeSA01.getChildText("Kuendigungsrecht"));
       
       // die ganze Typermittlung ist Muell und muesste anhand der Gattungsdaten und mavprod neu 
       // aufgestellt werden
       
       String lvmytyp="21"; // -> generell Inhaberpapier
       
       if (lvNodeSA03.getChildText("WPArt").startsWith("01"))
       {
           lvmytyp = "21";
       }
       else if (lvNodeSA03.getChildText("WPArt").startsWith("02"))
       {
           lvmytyp = "20"; // namenspapiere kommen bisher nicht aus MAVIS
       }
       else if ("undefiniert".equals(lvNodeSA03.getChildText("WPArt"))) // Schiffe/Flugzeuge/OEPG werden
       {                                                                // von M710XML nicht behandelt
           lvmytyp = "21";
       }    
       else 
       {
           lvmytyp = lvNodeSA03.getChildText("WPArt");
       }
    
       lvEl_fgdaten.setAttribute("typ",lvmytyp);
            
       // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
       // Aktive Wertpapiere aus der Liquiditaetsreserve oder der sichernden Ueberdeckung erhalten repo -> 'Ja'
       // CT 06.08.2015
       if (("K2".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // sichernde Ueberdeckung KO
           ("H2".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // sichernde Ueberdeckung Hypo
           ("S2".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // sichernde Ueberdeckung Schiffe
           ("F2".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // sichernde Ueberdeckung Flugzeuge
           ("K4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Liquiditaetsreserve KO
           ("H4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Liquiditaetsreserve HPF
           ("S4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Liquiditaetsreserve Schiffe 
           ("F4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Liquiditaetsreserve Flugzeuge
           ("O4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // sichernde Ueberdeckung OEPG KO
           ("O2".equals(lvNodeSA01.getChildText("AusplazMM"))))    // sichernde Ueberdeckung OEPG Hypo
       {
           lvEl_fgdaten.setAttribute("repo", "1");
       }
       
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
     * Fuellen der TXS Transaktion slicedaten, enthaelt Daten des Finanzgeschaeftes zum Deckungsregister 
     * @param pvAktiv 
     * @param pvKey 
     * @return Element zum Einhaengen in die XML-Ausgabe
     */
    public Element slicedaten_aktiv(Element pvAktiv, Element pvKey)
    {
        // zum Lesen aus dem Eingangs XML
        List<Element> lvListSA03 = pvAktiv.getChildren("SATZSA03");
        Element lvNodeSA03 = (Element) lvListSA03.get(0);

        // zum Lesen aus dem Eingangs XML
        List<Element> lvListSA01 = pvAktiv.getChildren("SATZSA01");
        Element lvNodeSA01 = (Element) lvListSA01.get(0);
       
        // element slicedaten für die Ausgabe
        Element lvEl_slicedaten = new Element("slicedaten");
        
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // (historisch gewachsenem) suffix ermitteln
       String lvMySuffix = "";

       // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
       if (lvNodeSA01.getChildText("AusplazMM").startsWith("S") || // Schiffe
    	   lvNodeSA01.getChildText("AusplazMM").startsWith("F") || // Flugzeuge
    	   lvNodeSA01.getChildText("AusplazMM").startsWith("O"))   // Altbestand
       {
    	   
           lvMySuffix = "_" + lvNodeSA01.getChildText("Deckungsschluessel");         
        }
        else
        {
           lvMySuffix = WPSuffix.getSuffixMAVIS(lvNodeSA03.getChildText("Ersatzdeckungstyp"), lvNodeSA03.getChildText("WPArt"));                
        }
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // key ist Produkt plus (historisch gewachsenem) suffix
       lvEl_slicedaten.setAttribute("key", pvKey.getChildText("Produkt") + lvMySuffix);
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Aktiv/Passiv hier immer aktiv
       lvEl_slicedaten.setAttribute("aktivpassiv", "1");
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Deckungstyp ermitteln
       // setattribute unten !
       String lvMydecktyp = "";
 
       if (lvNodeSA01.getChildText("AusplazMM").endsWith("2") || "O4".equals(lvNodeSA01.getChildText("AusplazMM")))
       {
           lvMydecktyp = "2"; // Sichernde Ueberdeckung
       }
       else if (!lvNodeSA01.getChildText("AusplazMM").startsWith("O") && (lvNodeSA01.getChildText("AusplazMM").endsWith("3") || lvNodeSA01.getChildText("AusplazMM").endsWith("4")))
            {
    		   lvMydecktyp = "3"; // Weitere Deckung
            }
            else 
            {   
            	lvMydecktyp = "1"; // Klassische Deckung
            }
         
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Liquiditätsreserve, setattribute unten !
       String lvMynliqui ="";
       // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
       if (("K4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Kommunal
   	       ("H4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Hypo
           ("S4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Schiffe
           ("F4".equals(lvNodeSA01.getChildText("AusplazMM")))   ) // Flugzeuge
       {
    	   lvMynliqui = "1"; // hier gleichzeitig Liquidtätsreserve
       }
       else 
       {     
    	   lvMynliqui = "0"; // keine Liquidtätsreserve
       }
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       //// An dieser Stelle die Umsetzung auf die "neuen" Transaktionen/pools Schiffe/Flugzeuge/ÖPG 
       String lvmytx="";
       String lvmypool="";
       String ivneuesTxsprojekt;  
       
       // kommt noch aus MAVIS mit, da PfandBG BLB oder NLB im Namen traegt 
       // die "neuen" Projekte tragen das Institut aber nicht im Namen
       ivneuesTxsprojekt = ivTxsprojekt; 
       
       //if (lvNodeSA03.getChildText("WPArt").substring(2, 4).equals("01"))
       //{
       //    lvmytx = "Hypothekenpfandbrief";
       //    lvmypool = "Hypothekenpfandbrief";
       //}
       //else if (lvNodeSA03.getChildText("WPArt").substring(2, 4).equals("02"))
       //     {
       //     	lvmytx = "Öffentlicher Pfandbrief";
       //     	lvmypool = "Öffentlicher Pfandbrief";
       //     }
       if (lvNodeSA01.getChildText("AusplazMM").startsWith("H"))
       {
           lvmytx = "Hypothekenpfandbrief";
           lvmypool = "Hypothekenpfandbrief";    	   
       }
       
       if (lvNodeSA01.getChildText("AusplazMM").startsWith("K"))
       {
    	   lvmytx = "Öffentlicher Pfandbrief";
    	   lvmypool = "Öffentlicher Pfandbrief";
       }
       
       // Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
       if (lvNodeSA01.getChildText("AusplazMM").startsWith("S"))
       {
           ivneuesTxsprojekt="Schiffspfandbrief";
           lvmytx = "Schiffspfandbrief";
           lvmypool = "Schiffspfandbrief";
       }
       else if (lvNodeSA01.getChildText("AusplazMM").startsWith("F"))
            {
              lvmytx = "Flugzeugpfandbrief";
              lvmypool = "Flugzeugpfandbrief";
              ivneuesTxsprojekt = "Flugzeugpfandbrief"; // nur NLB
            }
            else if (lvNodeSA01.getChildText("AusplazMM").startsWith("O"))
                 {
            	    // OEPG kann nur Oeffentlich sein...
            		lvmytx = "Öffentlicher Pfandbrief";
            		lvmypool = "Öffentlicher Pfandbrief";
            	   	ivneuesTxsprojekt = "ÖPG";   
                 }
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
              
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // setzten der oben ermittelten Werte
       lvEl_slicedaten.setAttribute("prj", ivneuesTxsprojekt);
       
       lvEl_slicedaten.setAttribute("tx", lvmytx);
      
       lvEl_slicedaten.setAttribute("pool", lvmypool);
       
       lvEl_slicedaten.setAttribute("decktyp",lvMydecktyp);
       
       lvEl_slicedaten.setAttribute("nliqui",lvMynliqui);
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Nominalbetrag
       lvEl_slicedaten.setAttribute("nbetrag", lvNodeSA03.getChildText("Nominalbetrag").replace(",", "."));
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // bis zu betrag
       lvEl_slicedaten.setAttribute("bis", lvNodeSA03.getChildText("Nominalbetrag").replace(",", "."));
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Waehrung
       lvEl_slicedaten.setAttribute("whrg", lvNodeSA03.getChildText("Waehrung"));
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       return (lvEl_slicedaten);
    }
    
    /**
     * Fuellen der TXS Transaktion slicedaten, enthaelt Daten des Finanzgeschaeftes zum Deckungsregister 
     * @param pvAktiv 
     * @param pvKey 
     * @return Element zum einhängen in die XML-Ausgabe
     */
   public Element slicedaten_aktiv_old(Element pvAktiv, Element pvKey)
    {
        // zum Lesen aus dem Eingangs XML
        List<Element> lvListSA03 = pvAktiv.getChildren("SATZSA03");
        Element lvNodeSA03 = (Element) lvListSA03.get(0);

        // zum Lesen aus dem Eingangs XML
        List<Element> lvListSA01 = pvAktiv.getChildren("SATZSA01");
        Element lvNodeSA01 = (Element) lvListSA01.get(0);
       
        // element slicedaten für die Ausgabe
        Element lvEl_slicedaten = new Element("slicedaten");
        
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // (historisch gewachsenem) suffix ermitteln
       String lvMySuffix = "";

       /* Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
       if (("S".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
           ("V".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
           ("3".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // sichernde ÜD Schiffe
           ("7".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Liqui Schiffe
           ("U".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
           ("W".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) || 
           ("4".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // sichernde ÜD Flugzeuge
           ("8".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Liqui Flugzeuge
           ("A".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||   // auch für die Altdeckung
           ("0".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
           ("9".equals(lvNodeSA01.getChildText("Deckungsschluessel")))   )                                                                        
           
       {
          lvMySuffix = "_" + lvNodeSA01.getChildText("Deckungsschluessel");         
       }
       else
       {
          lvMySuffix = WPSuffix.getSuffix(lvNodeSA03.getChildText("Ersatzdeckungstyp"), lvNodeSA03.getChildText("WPArt"));;                
       }
       */
       // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
       if (lvNodeSA01.getChildText("AusplazMM").startsWith("S") || // Schiffe
    	   lvNodeSA01.getChildText("AusplazMM").startsWith("F") || // Flugzeuge
    	   lvNodeSA01.getChildText("AusplazMM").startsWith("O"))   // Altbestand
       {
    	   
           lvMySuffix = "_" + lvNodeSA01.getChildText("Deckungsschluessel");         
        }
        else
        {
           lvMySuffix = WPSuffix.getSuffixMAVIS(lvNodeSA03.getChildText("Ersatzdeckungstyp"), lvNodeSA03.getChildText("WPArt"));;                
        }
  
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
 
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // key ist Produkt plus (historisch gewachsenem) suffix
       lvEl_slicedaten.setAttribute("key", pvKey.getChildText("Produkt") + lvMySuffix);
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Aktiv/Passiv hier immer aktiv
       lvEl_slicedaten.setAttribute("aktivpassiv", "1");
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Deckungstyp ermitteln (für PfandBG aus dem Suffix, sonst direkt aus dem Deckungskennzeichen
       // setattribute unten !
       String lvMydecktyp = "";
       String lvAusserDeckungsschluessel = WPSuffix.getSuffixMAVIS(lvNodeSA03.getChildText("Ersatzdeckungstyp"), lvNodeSA03.getChildText("WPArt"));
       
       if ((lvAusserDeckungsschluessel.equals("KS")) ||
            (lvAusserDeckungsschluessel.equals("PS")) ||
            // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
            ("S2".equals(lvNodeSA01.getChildText("AusplazMM"))) ||
            //("3".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Schiffe
            ("F".equals(lvNodeSA01.getChildText("AusplazMM"))) ||
            //("4".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Flugzeuge
            ("O4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||
            //("0".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // OEPG KO
            ("O2".equals(lvNodeSA01.getChildText("AusplazMM"))))
            //("9".equals(lvNodeSA01.getChildText("Deckungsschluessel")))   ) // OEPG HYPO
       {
           lvMydecktyp = "2"; // Sichernde Überdeckung
       }
       else if (lvAusserDeckungsschluessel.equals("KO") ||
                lvAusserDeckungsschluessel.equals("PO")   ) 
       {
           lvMydecktyp = "1"; // Klassische Deckung
       }
       else 
       {   
           lvMydecktyp = "3"; // Weitere Deckung
       }
         
       // Sonderfall 
       /* Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
       if (("7".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Schiffe
           ("8".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Flugzeuge
           ("U".equals(lvNodeSA01.getChildText("Deckungsschluessel")))     // Flugzeuge ALLE Aktiva->weitere Deckung
           ) 
       {
           lvMydecktyp = "3"; // Weitere Deckung
       } */
       // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
       if (("S4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Schiffe
           ("F4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Flugzeuge
           (lvNodeSA01.getChildText("AusplazMM").startsWith("F")))     // Flugzeuge ALLE Aktiva->weitere Deckung
       {
    	   lvMydecktyp = "3"; // Weitere Deckung
       } 

       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Liquiditätsreserve, setattribute unten !
       String lvMynliqui ="";
       /* Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
        if (("5".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Kommunal
            ("6".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Hypo
            ("7".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||  // Schiffe
            ("8".equals(lvNodeSA01.getChildText("Deckungsschluessel")))   ) // Flugzeuge
       {
            lvMynliqui = "1"; // hier gleichzeitig Liquidtätsreserve
       }
       else 
       {    
            lvMynliqui = "0"; // keine Liquidtätsreserve
       } */
       	// Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
       	if (("K4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Kommunal
       	    ("H4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Hypo
            ("S4".equals(lvNodeSA01.getChildText("AusplazMM"))) ||  // Schiffe
            ("F4".equals(lvNodeSA01.getChildText("AusplazMM")))   ) // Flugzeuge
          {
               lvMynliqui = "1"; // hier gleichzeitig Liquidtätsreserve
          }
          else 
          {    
               lvMynliqui = "0"; // keine Liquidtätsreserve
          }
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       //// An dieser Stelle die Umsetzung auf die "neuen" Transaktionen/pools Schiffe/Flugzeuge/ÖPG 
       String lvmytx="";
       String lvmypool="";
       String ivneuesTxsprojekt;  
       
       // kommt noch aus MAVIS mit, da PfandBG BLB oder NLB im Namen trägt 
       // die "neuen" Projekte tragen das Institut aber nicht im Namen
       ivneuesTxsprojekt = ivTxsprojekt; 
       
       if (lvNodeSA03.getChildText("WPArt").substring(2, 4).equals("01"))
       {
           lvmytx = "Hypothekenpfandbrief";
           lvmypool = "Hypothekenpfandbrief";
       }
       else if (lvNodeSA03.getChildText("WPArt").substring(2, 4).equals("02"))
       {
            lvmytx = "Öffentlicher Pfandbrief";
            lvmypool = "Öffentlicher Pfandbrief";
       }
       
       // ÖPG kommt über die WPART, hier nur S/U/B
       
       // Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
       //if (("S".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
       //    ("V".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
       //    ("3".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||     // sichernde ÜD Schiffe
       //    ("7".equals(lvNodeSA01.getChildText("Deckungsschluessel")))   )    // Liqui Schiffe
       if (lvNodeSA01.getChildText("AusplazMM").startsWith("S"))
       {
           ivneuesTxsprojekt="Schiffspfandbrief";
           lvmytx = "Schiffspfandbrief";
           lvmypool = "Schiffspfandbrief";
       }
       else //if (("U".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
            //    ("W".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) || 
            //    ("4".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) || // sichernde ÜD Flugzeuge
            //    ("8".equals(lvNodeSA01.getChildText("Deckungsschluessel")))   ) // Liqui Flugzeuge
           if (lvNodeSA01.getChildText("AusplazMM").startsWith("F"))
       {
           lvmytx = "Flugzeugpfandbrief";
           lvmypool = "Flugzeugpfandbrief";
           ivneuesTxsprojekt="Flugzeugpfandbrief"; // nur NLB
       }
       // GMTN herausgenommen - CT 16.06.2016
       //else if ("B".equals(lvNodeSA01.getChildText("Deckungsschluessel")))
       //{
       //    lvmytx = "GMTN";
       //    lvmypool = "GMTN";
       //    ivneuesTxsprojekt="GMTN"; // Gibt es nicht, aber vielleicht irgendwann informatorisch 
       //}
       else //if (("A".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
            //    ("0".equals(lvNodeSA01.getChildText("Deckungsschluessel"))) ||
            //    ("9".equals(lvNodeSA01.getChildText("Deckungsschluessel")))   )
            if (lvNodeSA01.getChildText("AusplazMM").startsWith("O"))
 
       {
           ivneuesTxsprojekt="ÖPG";   
       }
       // Abgegangen herausgenommen - CT 16.06.2016
       //else if ("N".equals(lvNodeSA01.getChildText("Deckungsschluessel")))
       //{
       //    lvmytx = "ABGEGANGEN";
       //    lvmypool = "ABGEGANGEN";
       //    ivneuesTxsprojekt="ABGEGANGEN"; // Gibt es nicht, aber vielleicht auch irgendwann informatorisch 
       //}
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Sonderloesungen fuer die BLB 
       if (ivTxsprojekt.startsWith("BLB"))
       {
           // dieses Papier für die Sichernde Überdeckung Hypo, projekt muss nicht umgesetzt werden
           if ("XS0493444060".equals(pvKey.getChildText("Produkt")))
           {
               lvMydecktyp = "2"; // Sichernde Überdeckung
               lvmypool="Hypothekenpfandbrief";
               lvmytx = "Hypothekenpfandbrief";
           }
           // dieses Papier für die weitere Deckung Schiffe, projekt,pool und tx müssen nicht umgesetzt werden
           if ("DE000A0B1QY7".equals(pvKey.getChildText("Produkt")))
           {
               lvMydecktyp = "3"; // Weitere Deckung
           }
           // dieses Papier für die sichernde Deckung Schiffe, projekt,pool und tx müssen nicht umgesetzt werden
           if ("XS0194605506".equals(pvKey.getChildText("Produkt")))
           {
               lvMydecktyp = "2"; // Sichernde Überdeckung
           }
           // Dieses Papier für die weitere Deckung Schiffe; projekt, pool und tx muessen nicht umgesetzt werden
           if ("DE000LFA1610".equals(pvKey.getChildText("Produkt")))
           {
               lvMydecktyp = "3"; // Weitere Deckung
           }
           
           // Wieder herausgenommen - 08.12.2014 Frau Vierling
           // dieses Papier für die sichernde Deckung ÖPG
           //if ("DE000A1REW10".equals(pvKey.getChildText("Produkt")))
           //{
           //    ivneuesTxsprojekt="ÖPG";
           //    lvmytx = "Öffentlicher Pfandbrief";
           //    lvmypool = "Öffentlicher Pfandbrief";
           //    
           //    lvMydecktyp = "2"; // Sichernde Überdeckung
           //}
            
           // dieses Papier für die weitere Deckung Schiffspfandbriefe SU 20140627
           if ("DE000A1MATC7".equals(pvKey.getChildText("Produkt")))
           {
               ivneuesTxsprojekt="Schiffspfandbrief";
               lvmytx = "Schiffspfandbrief";
               lvmypool = "Schiffspfandbrief";
               
               lvMydecktyp = "3"; // Weitere Deckung
           }
           
           // dieses Papier für die sichernde Deckung ÖPG hypo / SU 20150309
           if ("DE000A0H5VC8".equals(pvKey.getChildText("Produkt")))
           {
               ivneuesTxsprojekt="ÖPG";
               lvmytx = "Hypothekenpfandbrief";
               lvmypool = "Hypothekenpfandbrief";
           }
           
       }
       // Sonderloesungen fuer die NLB
       if (ivTxsprojekt.startsWith("NLB"))
       {
          // Dieses Papier fuer die weitere Deckung Flugzeuge, projekt,pool und tx muessen nicht umgesetzt werden
    	  if ("DE000A0Z1UA1".equals(pvKey.getChildText("Produkt")))
    	  {
    		  lvMydecktyp = "3"; // Weitere Deckung
    	  }
    	  
          // Dieses Papier fuer die weitere Deckung Flugzeuge, projekt,pool und tx muessen nicht umgesetzt werden
    	  if ("DE000NWB14G8".equals(pvKey.getChildText("Produkt")))
    	  {
    		  lvMydecktyp = "3"; // Weitere Deckung
    	  }
    	 
          // Dieses Papier fuer die weitere Deckung Flugzeuge, projekt,pool und tx muessen nicht umgesetzt werden
    	  if ("DE000NWB16C2".equals(pvKey.getChildText("Produkt")))
    	  {
    		  lvMydecktyp = "3"; // Weitere Deckung
    	  }
       }

       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // setzten der oben ermittelten Werte
       lvEl_slicedaten.setAttribute("prj", ivneuesTxsprojekt);
       
       lvEl_slicedaten.setAttribute("tx", lvmytx);
      
       lvEl_slicedaten.setAttribute("pool", lvmypool);
       
       lvEl_slicedaten.setAttribute("decktyp",lvMydecktyp);
       
       lvEl_slicedaten.setAttribute("nliqui",lvMynliqui);
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Nominalbetrag
       lvEl_slicedaten.setAttribute("nbetrag", lvNodeSA03.getChildText("Nominalbetrag").replace(",", "."));
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // bis zu betrag
       lvEl_slicedaten.setAttribute("bis", lvNodeSA03.getChildText("Nominalbetrag").replace(",", "."));
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Währung
       lvEl_slicedaten.setAttribute("whrg", lvNodeSA03.getChildText("Waehrung"));
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       return (lvEl_slicedaten);
    }

    /**
     * Fuellen der TXS Transaktion konddaten, enthaelt Konditionen des Finanzgeschaeftes
     * @param pvAktiv 
     * @param pvKey 
     * @param pvBLZ
     * @return Element zum einhaengen in die XML-Ausgabe
     */
    public Element konddaten_aktiv(Element pvAktiv, Element pvKey, String pvBLZ)
    {
       Element lvEl_konddaten = new Element("konddaten");
     
       List<Element> lvListSA01 = pvAktiv.getChildren("SATZSA01");
       Element lvNodeSA01 = (Element) lvListSA01.get(0);

       List<Element> lvListSA03 = pvAktiv.getChildren("SATZSA03");
       Element lvNodeSA03 = (Element) lvListSA03.get(0);
        
        /////////////
        //  List lvListZUSATZ = pvAktiv.getChildren("TXSZUSATZ");
        //  Element lvNodeZUSATZ = (Element) lvListZUSATZ.get(0);
        ////////////
        
       // konddaten
        lvEl_konddaten.setAttribute("kondkey", "1");
       
       lvEl_konddaten.setAttribute("ekurs", lvNodeSA01.getChildText("Emissionskurs").replace(",", "."));
       
     //  el_konddaten.setAttribute("rkurs", nodeSA01.getChildText("Rueckzahlungskurs").replace(",", "."));
        
        if (!"0".equals(lvNodeSA01.getChildText("Emissionsdatum")))
       {
            lvEl_konddaten.setAttribute("edat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Emissionsdatum")));
       }
       
        lvEl_konddaten.setAttribute("atkonv", ValueMapping.changeArbeitstagskonvention(lvNodeSA01.getChildText("Arbeitstagkonv")));
       
       lvEl_konddaten.setAttribute("atkonvmod", lvNodeSA01.getChildText("ArbeitstagkonvMOD"));
       
       lvEl_konddaten.setAttribute("atkonvtag", lvNodeSA01.getChildText("ArbeitstagkonvTAG"));
       
       lvEl_konddaten.setAttribute("bankkal", lvNodeSA01.getChildText("Kalender"));
       
       lvEl_konddaten.setAttribute("spread", lvNodeSA01.getChildText("Zinszuschlag").replace(",", "."));
       
       lvEl_konddaten.setAttribute("cap", lvNodeSA01.getChildText("Obergrenze").replace(",", "."));
                       
       lvEl_konddaten.setAttribute("floor", lvNodeSA01.getChildText("Untergrenze").replace(",", "."));
       
       lvEl_konddaten.setAttribute("fixkalart", ValueMapping.changeKalenderart(lvNodeSA01.getChildText("KalenderKonvRef")));
       
       lvEl_konddaten.setAttribute("fixkonv", ValueMapping.changePeriodenbeginnEnde(lvNodeSA01.getChildText("Fixingkonv")));
       
       lvEl_konddaten.setAttribute("fixtage", lvNodeSA01.getChildText("Fixingtage"));
       
       lvEl_konddaten.setAttribute("fixtagedir", ValueMapping.changeArbeitstagskonvention(lvNodeSA01.getChildText("FixingtageDIR")));
       
       lvEl_konddaten.setAttribute("fixtagemod", lvNodeSA01.getChildText("FixingtageMOD"));
       
       lvEl_konddaten.setAttribute("kalfix", lvNodeSA01.getChildText("KALFixing"));
       
       // einmal aus MAVIS abfangen
        if (lvNodeSA01.getChildText("KalendKonvZins").equals("keine"))
       {
            lvEl_konddaten.setAttribute("kalkonv", "0");
       }
       else if (lvNodeSA01.getChildText("KalendKonvZins").equals("30E_360"))
       {
            lvEl_konddaten.setAttribute("kalkonv", "30e_360");
       }
       else
       {
            lvEl_konddaten.setAttribute("kalkonv", lvNodeSA01.getChildText("KalendKonvZins"));
       }
     
       lvEl_konddaten.setAttribute("kupbas", ValueMapping.changeKuponbasis(lvNodeSA01.getChildText("Kuponbasis")));
       
       lvEl_konddaten.setAttribute("kupbasodd", ValueMapping.changeKuponbasis(lvNodeSA01.getChildText("KuponbasisODD")));
       
       lvEl_konddaten.setAttribute("lrate", lvNodeSA01.getChildText("Leistungsrate").replace(",", "."));
       
       lvEl_konddaten.setAttribute("monendkonv", ValueMapping.changeMonatsendekonvention(lvNodeSA01.getChildText("MonatsendKonv")));
       
       // es werden aus MAVIS momentan keine cashflows geliefert
        lvEl_konddaten.setAttribute("mantilg", "0");
       
       // es werden aus MAVIS momentan keine cashflows geliefert
        lvEl_konddaten.setAttribute("manzins", "0");
       
       lvEl_konddaten.setAttribute("nomzins", lvNodeSA01.getChildText("Nominalzinssatz").replace(",", "."));
       
       lvEl_konddaten.setAttribute("refzins", lvNodeSA01.getChildText("Referenzzins").trim());
                     
   //    el_konddaten.setAttribute("tilgabw","0");
       
        if (!("0".equals(lvNodeSA01.getChildText("Tilgungstermin"))))
       {   
            lvEl_konddaten.setAttribute("tilgdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Tilgungstermin")));
       }
       
        lvEl_konddaten.setAttribute("tilgperkonv", ValueMapping.changePeriodenkonvention(lvNodeSA01.getChildText("TilperiodenKonv")));
             
       // Tilgungsrythmus defaultmaessig auf 'bullet' (0) setzen - CT 11.08.2015
       //// BLB-Variante - CT 08.12.2015 - Mit BLB diskutieren
       if (pvBLZ.equals("25050000")) // NLB
       {
           lvEl_konddaten.setAttribute("tilgryth", "0");    	   
       }
       else // BLB
       {
           lvEl_konddaten.setAttribute("tilgryth", lvNodeSA01.getChildText("Tilgungstyp").trim());
       }
       
       lvEl_konddaten.setAttribute("tilgsatz", lvNodeSA01.getChildText("Tilgungssatz").replace(",", "."));
       
       lvEl_konddaten.setAttribute("zahltyp", lvNodeSA01.getChildText("Zahlungstyp"));
       
       if ("3".equals(lvNodeSA01.getChildText("Zahlungstyp")))
       {
            lvEl_konddaten.setAttribute("tilgver", "0");
       }

        lvEl_konddaten.setAttribute("zinsabw", ValueMapping.changeAbweichung(lvNodeSA01.getChildText("Zinsabweichung").trim()));
       
       if (!("0".equals(lvNodeSA01.getChildText("Zinsbeginn"))))
       {    
            lvEl_konddaten.setAttribute("zinsbeg", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Zinsbeginn")));
       }
         
       // spezial für zeros ?!?!
        if ("0".equals(lvNodeSA01.getChildText("Zinstermin")))
       {
            if (!("0".equals(lvNodeSA01.getChildText("Zinsbeginn"))))
         {  
                lvEl_konddaten.setAttribute("zinsdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Zinsbeginn")));
         }
       }
       else
       {
            lvEl_konddaten.setAttribute("zinsdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Zinstermin")));
       }    
       
        if (!("0".equals(lvNodeSA01.getChildText("Zinsenddatum"))))
       {  
            lvEl_konddaten.setAttribute("zinsenddat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Zinsenddatum")));
       }
       
        lvEl_konddaten.setAttribute("zinsperkonv", ValueMapping.changePeriodenkonvention(lvNodeSA01.getChildText("ZinsperiodenKonv")));
       
       lvEl_konddaten.setAttribute("zinsryth", lvNodeSA01.getChildText("Zinszahlungstyp").trim());
       
       lvEl_konddaten.setAttribute("zinstyp", ValueMapping.changeZinstyp(lvNodeSA01.getChildText("Zinstyp")));
       
       lvEl_konddaten.setAttribute("zinszahlart", ValueMapping.changeZinszahlart(lvNodeSA01.getChildText("Zinszahlungsart")));
         
       lvEl_konddaten.setAttribute("zinsfak", lvNodeSA01.getChildText("Zinsfaktor").replace(",", "."));
       
       if (!("0".equals(lvNodeSA01.getChildText("Voltztkupdatum"))))
       {
            lvEl_konddaten.setAttribute("vzinsdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Voltztkupdatum")));
       }
                     
        if (!("0".equals(lvNodeSA01.getChildText("Vorltzttildatum"))))
       {
            lvEl_konddaten.setAttribute("vtilgdat", DatumUtilities.FormatDatum(lvNodeSA01.getChildText("Vorltzttildatum")));
       }
        
        lvEl_konddaten.setAttribute("whrg", lvNodeSA03.getChildText("Waehrung"));
       
       return (lvEl_konddaten);
    }

    /**
     * Fuellen der TXS Transaktion kredkunde, enthaelt Informationen zum Geschaeftspartner am Finanzgeschaeft 
     * @param pvAktiv 
     * @param pvKey 
     * @param pvBLZ 
     * @param pvQuellsystem 
     * @return Element zum einhaengen in die XML-Ausgabe 
     */
    public Element kredkunde_aktiv(Element pvAktiv, Element pvKey, String pvBLZ, String pvQuellsystem)
    {
       Element lvEl_kredkunde = new Element("kredkunde");
     
       Element lvEl_persdaten = new Element("persdaten");
              
       List<Element> lvListSA02 = pvAktiv.getChildren("SATZSA02");
       Element lvNodeSA02 = (Element) lvListSA02.get(0);
       
       // neu TXS Release Q3 2012
       List<Element> lvListZUSATZ = pvAktiv.getChildren("TXSZUSATZ");
       Element lvNodeZUSATZ = (Element) lvListZUSATZ.get(0);
              
       // kredkunde 0en auf zehn stellen auffuellen
       String lvKdnrhelper = "0000000000" + lvNodeSA02.getChildText("Kundennummer");
       
       lvKdnrhelper = lvKdnrhelper.substring(lvKdnrhelper.length() - 10, lvKdnrhelper.length());
       
       lvEl_kredkunde.setAttribute("kdnr", lvKdnrhelper);
       
       lvEl_kredkunde.setAttribute("quelle", pvQuellsystem);
       
       lvEl_kredkunde.setAttribute("org", pvBLZ);
       
       // Kundenrolle Hauptkreditnehmer - CT 12.09.2017
       lvEl_kredkunde.setAttribute("rolle", "0");
        
       // persdaten
       lvEl_persdaten.setAttribute("nname", lvNodeSA02.getChildText("Bezeichnung1"));
       
       lvEl_persdaten.setAttribute("vname", lvNodeSA02.getChildText("Bezeichnung2"));
       
       lvEl_persdaten.setAttribute("land", ValueMapping.changeLand(lvNodeSA02.getChildText("Land")));
       
       lvEl_persdaten.setAttribute("ort", lvNodeSA02.getChildText("Ort"));
       
       lvEl_persdaten.setAttribute("plz", lvNodeSA02.getChildText("PLZ"));
       
       lvEl_persdaten.setAttribute("str", lvNodeSA02.getChildText("Strasse"));
       
       // neu TXS Release Q3 2012
       lvEl_persdaten.setAttribute("kusyma", lvNodeZUSATZ.getChildText("KUSYMA"));
              
       lvEl_persdaten.setAttribute("kundennummereigen", lvNodeZUSATZ.getChildText("KDNR"));
       
       // persdaten unter kredkunde hängen
       lvEl_kredkunde.addContent(lvEl_persdaten);
       
       return (lvEl_kredkunde);
    }
}
