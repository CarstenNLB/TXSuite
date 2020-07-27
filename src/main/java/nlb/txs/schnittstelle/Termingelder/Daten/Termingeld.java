/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Termingelder.Daten;

import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import org.apache.log4j.Logger;

public class Termingeld
{
  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Quelle
   */
  private String ivQuelle;

  /**
   * Kontonummer
   */
  private String ivKontonummer;

  /**
   * Ausplatzierungsmerkmal
   */
  private String ivAusplatzierungsmerkmal;

  /**
   * Stichtag
   */
  private String ivStichtag;

  /**
   * Saldo
   */
  private String ivSaldo;

  /**
   * Waehrung
   */
  private String ivWaehrung;

  /**
   * Emissionsdatum
   */
  private String ivEmissionsdatum;

  /**
   * Zinstyp
   */
  private String ivZinstyp;

  /**
   * Zinsssatz
   */
  private String ivZinssatz;

  /**
   * Zinsbeginn
   */
  private String ivZinsbeginn;

  /**
   * Zinsende
   */
  private String ivZinsende;

  /**
   * Tilgungsbeginn
   */
  private String ivTilgungsbeginn;

  /**
   * Faelligkeit
   */
  private String ivFaelligkeit;

  /**
   * Kalenderkonvention
   */
  private String ivKalenderkonvention;

  /**
   * Schluessel Handelskat
   */
  private String ivSchluesselHandeskat;

  /**
   * Geschaeftsnummer/TradeID
   */
  private String ivGeschaeftsnummer;

  /**
   * Kontonummer der einlegenden Bank
   */
  private String ivKontonummerEinlegendeBank;

  /**
   * Swift 57 A
   */
  private String ivSwift57A;

  /**
   * Swift 58 A
   */
  private String ivSwift58A;

  /**
   * Geschaeftspartnernummer
   */
  private String ivGeschaeftspartnernummer;

  /**
   * Geschaeftspartnername
   */
  private String ivGeschaeftspartnername;

  /**
   * Konstruktor - Initialisiert alle Variablen mit einem leeren String
   */
  public Termingeld(Logger pvLogger)
  {
    this.ivLogger = pvLogger;
    this.ivQuelle = new String();
    this.ivKontonummer = new String();
    this.ivAusplatzierungsmerkmal = new String();
    this.ivStichtag = new String();
    this.ivSaldo = new String();
    this.ivWaehrung = new String();
    this.ivEmissionsdatum = new String();
    this.ivZinstyp = new String();
    this.ivZinssatz = new String();
    this.ivZinsbeginn = new String();
    this.ivZinsende = new String();
    this.ivTilgungsbeginn = new String();
    this.ivFaelligkeit = new String();
    this.ivKalenderkonvention = new String();
    this.ivSchluesselHandeskat = new String();
    this.ivGeschaeftsnummer = new String();
    this.ivKontonummerEinlegendeBank = new String();
    this.ivSwift57A = new String();
    this.ivSwift58A = new String();
    this.ivGeschaeftspartnernummer = new String();
    this.ivGeschaeftspartnername = new String();
  }


  /**
   * Zerlegt eine Termingeldzeile in die einzelnen Felder/Werte
   * @param pvZeile
   * @return
   */
  public boolean parseTermingeld(String pvZeile)
  {
    String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
    int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
    int    lvZzStr=0;              // pointer fuer satzbereich

    // Steuerung/Iteration Eingabesatz
    for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
    {
      // wenn Semikolon erkannt
      if (pvZeile.charAt(lvZzStr) == ';')
      {
        //System.out.println(lvTemp);
        this.setTermingeld(lvLfd, lvTemp);

        lvLfd++;                  // naechste Feldnummer
        // loeschen Zwischenbuffer
        lvTemp = new String();
      }
      else
      {
        // uebernehmen Byte aus Eingabesatzposition
        lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
      }
    } // ende for

    return true;
  }

  /**
   * Setzt einen Wert des Termingeldes
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setTermingeld(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.ivQuelle = pvWert.trim();
        break;
      case 1:
        this.ivKontonummer = pvWert;
        break;
      case 2:
        this.ivAusplatzierungsmerkmal = pvWert;
        break;
      case 3:
        this.ivStichtag = pvWert;
        break;
      case 4:
        pvWert = pvWert.replace(",", ".");
        if (pvWert.startsWith("-"))
        {
          pvWert = pvWert.substring(1);
        }
        this.ivSaldo = pvWert;
        break;
      case 5:
        this.ivWaehrung = pvWert;
        break;
      case 6:
        this.ivEmissionsdatum = pvWert;
        break;
      case 7:
        this.ivZinstyp = pvWert;
        break;
      case 8:
        pvWert = pvWert.replace(",", ".");
        this.ivZinssatz = pvWert;
        break;
      case 9:
        this.ivZinsbeginn = pvWert;
        break;
      case 10:
        this.ivZinsende = pvWert;
        break;
      case 11:
        this.ivTilgungsbeginn = pvWert;
        break;
      case 12:
        this.ivFaelligkeit = pvWert;
        break;
      case 13:
        this.ivKalenderkonvention = pvWert;
        break;
      case 14:
        this.ivSchluesselHandeskat = pvWert.trim();
        break;
      case 15:
        this.ivGeschaeftsnummer = pvWert;
        break;
      case 16:
        this.ivKontonummerEinlegendeBank = pvWert;
        break;
      case 17:
        this.ivSwift57A = pvWert;
        break;
      //case 18:
      //  this.ivSwift58A = pvWert;
      //  break;
      case 18:
        this.ivGeschaeftspartnernummer = pvWert;
        break;
      case 19:
        this.ivGeschaeftspartnername = pvWert;
        break;
        default:
        ivLogger.error("Termingeld: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  /**
   * Verarbeite das Termingeld
   * @param pvOutputDarlehenXML
   */
  public void verarbeiteTermingeld(OutputDarlehenXML pvOutputDarlehenXML, Vorlaufsatz pvVorlaufsatz)
  {
    importTermingeld2Transaktion(pvOutputDarlehenXML, pvVorlaufsatz);
  }


  /**
   * Importiert die Termingeldinformationen in die TXS-Transaktionen
   * @param pvOutputDarlehenXML
   * @param pvVorlaufsatz
   */
  private void importTermingeld2Transaktion(OutputDarlehenXML pvOutputDarlehenXML, Vorlaufsatz pvVorlaufsatz)
  {
    // Transaktionen
    TXSFinanzgeschaeft lvFinanzgeschaeft = new TXSFinanzgeschaeft();
    TXSSliceInDaten lvSliceInDaten = new TXSSliceInDaten();
    TXSFinanzgeschaeftDaten lvFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
    TXSKonditionenDaten lvKondDaten = new TXSKonditionenDaten();
    TXSKreditKunde lvKredkunde = new TXSKreditKunde();

    //ivFinanzgeschaeft.initTXSFinanzgeschaeft();
    //ivSliceInDaten.initTXSSliceInDaten();
    //ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
    //ivKondDaten.initTXSKonditionenDaten();
    //ivKredkunde.initTXSKreditKunde();

    //TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
    //TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
    //TXSSicherheitPerson lvShperson = new TXSSicherheitPerson();

    boolean lvOkayTermingeld = true;

    lvOkayTermingeld = lvFinanzgeschaeft.importTermingeld(this, pvVorlaufsatz, ivLogger);

    if (lvOkayTermingeld)
    {
      lvOkayTermingeld = lvFinanzgeschaeftDaten.importTermingeld(this, pvVorlaufsatz, ivLogger);
    }

    if (lvOkayTermingeld)
    {
      lvOkayTermingeld = lvSliceInDaten.importTermingeld(this, pvVorlaufsatz, ivLogger);
    }

    if (lvOkayTermingeld)
    {
      lvOkayTermingeld = lvKondDaten.importTermingeld(this, ivLogger);
    }

    if (lvOkayTermingeld)
    {
      lvOkayTermingeld = lvKredkunde.importTermingeld(this, pvVorlaufsatz, ivLogger);
    }

    // Transaktionen in die Datei schreiben
    if (lvOkayTermingeld)
    {

      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionStart());

      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionEnde());
    }

    if (lvOkayTermingeld)
    {
      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionEnde());
    }
  }

  /**
   * Liefert die Quelle(system)
   * @return Quelle(system)
   */
  public String getQuelle()
  {
    return ivQuelle;
  }

  /**
   * Liefert die Kontonummer
   * @return Kontonummer
   */
  public String getKontonummer()
  {
    return ivKontonummer;
  }

  /**
   * Liefert das Ausplatzierungsmerkmal
   * @return Ausplatzierungsmerkmal
   */
  public String getAusplatzierungsmerkmal()
  {
    return ivAusplatzierungsmerkmal;
  }

  /**
   * Liefert den Stichtag
   * @return Stichtag
   */
  public String getStichtag()
  {
    return ivStichtag;
  }

  /**
   * Liefert den Saldo
   * @return Saldo
   */
  public String getSaldo()
  {
    return ivSaldo;
  }

  /**
   * Liefert die Waehrung
   * @return Waehrung
   */
  public String getWaehrung()
  {
    return ivWaehrung;
  }

  /**
   * Liefert das Emissionsdatum
   * @return Emissionsdatum
   */
  public String getEmissionsdatum()
  {
    return ivEmissionsdatum;
  }

  /**
   * Liefert den Zinstyp
   * @return Zinstyp
   */
  public String getZinstyp()
  {
    return ivZinstyp;
  }

  /**
   * Liefert den Zinssatz
   * @return Zinssatz
   */
  public String getZinssatz()
  {
    return ivZinssatz;
  }

  /**
   * Liefert den Zinsbeginn
   * @return Zinsbeginn
   */
  public String getZinsbeginn()
  {
    return ivZinsbeginn;
  }

  /**
   * Liefert das Zinsende
   * @return Zinsende
   */
  public String getZinsende()
  {
    return ivZinsende;
  }

  /**
   * Liefert den Tilgungsbeginn
   * @return Tilgungsbeginn
   */
  public String getTilgungsbeginn()
  {
    return ivTilgungsbeginn;
  }

  /**
   * Liefert die Faelligkeit
   * @return Faelligkeit
   */
  public String getFaelligkeit()
  {
    return ivFaelligkeit;
  }

  /**
   * Liefert die Kalenderkonvention
   * @return Kalenderkonvention
   */
  public String getKalenderkonvention()
  {
    return ivKalenderkonvention;
  }

  /**
   * Liefert den Schluessel der Handelskategorie
   * @return Schluessel der Handelskategorie
   */
  public String getSchluesselHandeskat()
  {
    return ivSchluesselHandeskat;
  }

  /**
   * Liefert die Geschaeftsnummer
   * @return Geschaeftsnummer
   */
  public String getGeschaeftsnummer()
  {
    return ivGeschaeftsnummer;
  }

  /**
   * Liefert die Kontonummer der einlegenden Bank
   * @return Kontonummer der einlegenden Bank
   */
  public String getKontonummerEinlegendeBank()
  {
    return ivKontonummerEinlegendeBank;
  }

  /**
   * Liefert die Swift 57A
   * @return Swift 57A
   */
  public String getSwift57A()
  {
    return ivSwift57A;
  }

  /**
   * Liefert die Swift 58A
   * @return Swift 58A
   */
  public String getSwift58A()
  {
    return ivSwift58A;
  }

  /**
   * Liefert die Geschaeftspartnernummer
   * @return Geschaeftspartnernummer
   */
  public String getGeschaeftspartnernummer()
  {
    return ivGeschaeftspartnernummer;
  }

  /**
   * Liefert den Geschaeftspartnername
   * @return Geschaeftspartnername
   */
  public String getGeschaeftspartnername()
  {
    return ivGeschaeftspartnername;
  }

}
