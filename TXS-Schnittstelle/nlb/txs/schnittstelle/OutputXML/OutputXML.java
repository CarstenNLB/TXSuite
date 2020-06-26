package nlb.txs.schnittstelle.OutputXML;

import java.io.File;
import java.io.FileOutputStream;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Wechselkurse.Wechselkurse;
import nlb.txs.schnittstelle.Zinskurse.Zinskurse;

public class OutputXML
{
	/**
	 * Der Name der XML-Datei 
	 */
	private String ivFilename;

	/**
	 * 
	 */
	private FileOutputStream ivXmlOS;

	/**
	 * Mandant 
	 */
	private String ivMandant;
  
  /**
   * Konstruktor
   * @param pvFilename 
   * @param pvMandant 
   */
  public OutputXML(String pvFilename, String pvMandant)
  {
	  this.ivFilename = pvFilename;
	  this.ivMandant = pvMandant;
  }
  
  /**
    * Oeffnen der XML-Datei
    */
  public void openXML()
  {
    File ivXmlFile = new File(ivFilename);
    try
    {
      ivXmlOS = new FileOutputStream(ivXmlFile);
    }
    catch (Exception e)
    {
      System.out.println("Konnte XML-Datei nicht oeffnen!");
    }    
  }

  /**
    * Schliessen der XML-Datei
    */
  public void closeXML()
  {
    try
    {
      ivXmlOS.close();
    }
    catch (Exception e)
    {
        System.out.println("Konnte XML-Datei nicht schliessen!");		
    }
  }

  /**
   * Ausgabe des XML-Kopfs
   */
  public void printXMLStart()
  {   
    // Start der XML-Datei schreiben
    try
    {
      ivXmlOS.write((new String("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n")).getBytes());
    }
    catch (Exception e)
    {
      System.out.println("Start: Fehler bei Ausgabe in XML-Datei");
    }
  }
  
  /**
   * 
   * @param pvString
   */
  public void printTXSHeader(String pvString) 
  {
	  try
	  {
		  ivXmlOS.write((new String("<txsi:header valdate=\"" + pvString + "\"/>\n")).getBytes());
	  }
	  catch (Exception e)
	  {
		  System.out.println("TXSHeader: Fehler bei Ausgabe in XML-Datei");
	  }
  }

  /**
   * Ausgabe des Starts der TXSImportDaten in die XML-Datei schreiben
   */
  public void printTXSImportDatenStart()
  {
    try
    {
      ivXmlOS.write((new String("<txsi:importdaten xmlns:txsi=\"http://agens.com/txsimport.xsd\">\n")).getBytes());
    }
    catch (Exception e)
    {
      System.out.println("TXSImportDatenStart: Fehler bei Ausgabe in XML-Datei");
    }
  }

  /**
   * Ausgabe des Ende der TXSImportDaten in die XML-Datei schreiben
   */
  public void printTXSImportDatenEnde()
  {
    // Ende der TXSImportDaten in die XML-Datei schreiben
    try
    {
      ivXmlOS.write((new String("</txsi:importdaten>")).getBytes());
    }
    catch (Exception e)
    {
      System.out.println("TXSImportDatenEnde: Fehler bei Ausgabe in XML-Datei");
    }
  }

    /**
     * Ausgabe eines Wechselkurses im XML-Format
     * @param pvWk Ein Wechselkurs
     */
  public void printWechselkurse2XML(Wechselkurse pvWk)
  {
        String lvXmlString = new String();
    // Start des Satzes
        lvXmlString = "  <txsi:wkurs ";

    // Originator des Wechselkurses
        lvXmlString = lvXmlString + "org=\"" + ValueMapping.changeMandant(ivMandant);
 
    // Hier bin ich mir nicht sicher, ob MANDANT oder
    // BUKREIS korrekt ist.
    //xmlString = xmlString + wk.getBuchungskreis();
        //xmlString = xmlString + wk.getMandant();

    // Quellsystem
        lvXmlString = lvXmlString + "\" quelle=\"";
        lvXmlString = lvXmlString + pvWk.getQuellsystem();

    // Waehrungscode
        lvXmlString = lvXmlString + "\" whrg=\"";
        lvXmlString = lvXmlString + pvWk.getWaehrungscode();

    // Datum Gueltigkeit
        lvXmlString = lvXmlString + "\" datum=\"";
        lvXmlString = lvXmlString + DatumUtilities.changeDate(pvWk.getGueltigkeitDatum());

    // Uhrzeit der Feststellung
    //if (strlen(wk->GUHR) > 0)
    //{
        lvXmlString = lvXmlString + "\" zeit=\"";
        lvXmlString = lvXmlString + pvWk.getGueltigkeitUhrzeit();
    //}

    // Mittelkurs
    //if (strlen(wk->MKURS) > 0)
    //{
        lvXmlString = lvXmlString + "\" kurs=\"";
        lvXmlString = lvXmlString + pvWk.getMittelkurs();
    //}

    // Briefkurs
        if (pvWk.getBriefkurs().length() > 0)
    {
            lvXmlString = lvXmlString + "\" bkurs=\"";
            lvXmlString = lvXmlString + pvWk.getBriefkurs();
    }

    // Geldkurs
        if (pvWk.getGeldkurs().length() > 0)
    {
            lvXmlString = lvXmlString + "\" gkurs=\"";
            lvXmlString = lvXmlString + pvWk.getGeldkurs();
    }

    // Marktplatz
        if (pvWk.getMarktplatz().length() > 0)
    {
            lvXmlString = lvXmlString + "\" mplatz=\"";
            lvXmlString = lvXmlString + pvWk.getMarktplatz();
    }

    // Anbieter/Lieferant
        if (pvWk.getAnbieter().length() > 0)
    {
            lvXmlString = lvXmlString + "\" anbieter=\"";
            lvXmlString = lvXmlString + pvWk.getAnbieter();
    }

    // Abschluss des Satzes
        lvXmlString = lvXmlString + "\" />\n";

    //if (iDebug)
    //printf("%s\n", outputXML);

    try
    {
            ivXmlOS.write(lvXmlString.getBytes());
    }
    catch (Exception e)
    {
      System.out.println("Wechselkurse: Fehler bei Ausgabe in XML-Datei");
    }
  }

    /**
     * Ausgabe eines Zinskurses im XML-Format
     * @param pvZk Ein Zinskurs
     */ 
  public void printZinskurse2XML(Zinskurse pvZk)
  {
        String lvXmlString = new String();
 
    // Start des XML-String
        lvXmlString = "  <txsi:mzins ";

    // Originator des Wechselkurses
        lvXmlString = lvXmlString + "org=\"" + ValueMapping.changeMandant(ivMandant);
  
    // Quellsystem
        lvXmlString = lvXmlString + "\" quelle=\"";
        lvXmlString = lvXmlString + pvZk.getQuellsystem();

    // Waehrungscode
        lvXmlString = lvXmlString + "\" whrg=\"";
        lvXmlString = lvXmlString + pvZk.getWaehrungscode();

    // Laufzeit
        lvXmlString = lvXmlString + "\" laufzeit=\"";
        lvXmlString = lvXmlString + pvZk.getLaufzeit();

    // Datum Gueltigkeit
        lvXmlString = lvXmlString + "\" datum=\"";
        lvXmlString = lvXmlString + DatumUtilities.changeDate(pvZk.getGueltigkeitDatum());

    // Uhrzeit der Feststellung
    //if (strlen(zk->GUHR) > 0)
    //{
        lvXmlString = lvXmlString + "\" zeit=\"";
        lvXmlString = lvXmlString + pvZk.getGueltigkeitUhrzeit();
    //}

    // Marktplatz
    //if (strlen(zk->MARKT) > 0)
    //{
        lvXmlString = lvXmlString + "\" mplatz=\"";
        lvXmlString = lvXmlString + pvZk.getMarktplatz();
    //}

    // Anbieter/Lieferant
    //if (strlen(zk->VENDOR) > 0)
    //{
        lvXmlString = lvXmlString + "\" anbieter=\"";
        lvXmlString = lvXmlString + pvZk.getAnbieter();
    //}

    // Identifikation
    //if (strlen(zk->KIDENT) > 0)
    //{
        lvXmlString = lvXmlString + "\" id=\"";
        lvXmlString = lvXmlString + pvZk.getIdentifikation();
    //}

    // Briefkurs
        if (pvZk.getBriefkurs().length() > 0)
    {
            lvXmlString = lvXmlString + "\" bkurs=\"";
            lvXmlString = lvXmlString + pvZk.getBriefkurs();
    }

    // Geldkurs
        if (pvZk.getGeldkurs().length() > 0)
    {
            lvXmlString = lvXmlString + "\" gkurs=\"";
            lvXmlString = lvXmlString + pvZk.getGeldkurs();
    }

    // Mittelkurs
    //if (strlen(zk->MKURS) > 0)
    //{
        lvXmlString = lvXmlString + "\" kurs=\"";
        lvXmlString = lvXmlString + pvZk.getMittelkurs();
    //}

    // Verzinsungsfrequenz/Referenzzins
        if (pvZk.getVerzinsungsfrequenz().length() > 0)
    {
            lvXmlString = lvXmlString + "\" refzins=\"";
            lvXmlString = lvXmlString + pvZk.getVerzinsungsfrequenz();
    }

    // Tageszaehlmethode/Kalenderkonvention
        if (pvZk.getTageszaehlmethode().length() > 0)
    {
            lvXmlString = lvXmlString + "\" kalkonv=\"";
            lvXmlString = lvXmlString + ValueMapping.changeKalenderkonvention(pvZk.getTageszaehlmethode());
    }

    // Zinsmethode
        if (pvZk.getZinsmethode().length() > 0)
    {
            lvXmlString = lvXmlString + "\" zinsmeth=\"";
            lvXmlString = lvXmlString + pvZk.getZinsmethode();
    }

    // Valutatage
        if (pvZk.getValutatage().length() > 0)
    {
            lvXmlString = lvXmlString + "\" valtage=\"";
            lvXmlString = lvXmlString + pvZk.getValutatage();
    }

    // Zahlungsfrequenz/Zahlungsintervall
        if (pvZk.getZahlungsfrequenz().length() > 0)
    {
            lvXmlString = lvXmlString + "\" zahlryth=\"";
            lvXmlString = lvXmlString + pvZk.getZahlungsfrequenz();
    }

    // Quotierungsart
        if (pvZk.getQuotierungsart().length() > 0)
    {
            lvXmlString = lvXmlString + "\" quot=\"";
            lvXmlString = lvXmlString + pvZk.getQuotierungsart();
    }

    // Arbeitstagekonvention
        if (pvZk.getArbeitstagekonvention().length() > 0)
    {
            lvXmlString = lvXmlString + "\" atkonv=\"";
            lvXmlString = lvXmlString + ValueMapping.changeArbeitstagskonvention(pvZk.getArbeitstagekonvention());
    }

    // Spezifikation Arbeitstagekonvention
        if (pvZk.getSpezArbeitstagekonv().length() > 0)
    {
            lvXmlString = lvXmlString + "\" atkonvmod=\"";
            lvXmlString = lvXmlString + pvZk.getSpezArbeitstagekonv();
    }

    // Monatsendekonvention
        if (pvZk.getMonatsendekonvention().length() > 0)
    {
            lvXmlString = lvXmlString + "\" monendkonv=\"";
            lvXmlString = lvXmlString + ValueMapping.changeMonatsendekonvention(pvZk.getMonatsendekonvention());
    }

    // Bankkalender
    //if (strlen(zk->BKALEND) > 0)
    //{
        lvXmlString = lvXmlString + "\" bankkal=\"";
        lvXmlString = lvXmlString + pvZk.getBankkalender();
    //}

    // Abschluss des XML-String
        lvXmlString = lvXmlString + "\" />\n";
  
    //if (iDebug)
      //printf("%s\n", outputXML);

   try
    {
            ivXmlOS.write(lvXmlString.getBytes());
    }
    catch (Exception e)
    {
      System.out.println("Fehler bei Ausgabe in XML-Datei");
    }
  }
}