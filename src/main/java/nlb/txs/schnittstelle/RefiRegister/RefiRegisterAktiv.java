package nlb.txs.schnittstelle.RefiRegister;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Immobilie;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Last;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Schiff;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Sicherungsumfang;
import nlb.txs.schnittstelle.Sicherheiten.Sicherheiten2RefiRegister;
import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson2;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;

@Deprecated
public class RefiRegisterAktiv 
{	
    // Transaktionen
    private TXSFinanzgeschaeft ivFinanzgeschaeft;
    private TXSSliceInDaten ivSliceInDaten;
    private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
    private TXSKonditionenDaten ivKondDaten;
    private TXSKreditKunde ivKredkundeKonsorte;
    private TXSKreditKunde ivKredkundeKreditnehmer;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor mit Initialisierung der Instanzvariablen
     * @param pvLogger log4j-Logger
     */
    public RefiRegisterAktiv(Logger pvLogger) 
    {
        this.ivLogger = pvLogger;
             
        this.ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        this.ivSliceInDaten = new TXSSliceInDaten();
        this.ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        this.ivKondDaten = new TXSKonditionenDaten();
        this.ivKredkundeKonsorte = new TXSKreditKunde();
        this.ivKredkundeKreditnehmer = new TXSKreditKunde();
    }

  /**
   * Importiert die Darlehensinformationen in die TXS-Transaktionen
   * @param pvZielDarlehen
   * @param pvOutputDarlehenXML
   * @param pvSicherheitenDaten
   * @param pvVerarbeitungstyp
   */
  public void importDarlehen2Transaktion(Darlehen pvZielDarlehen, OutputDarlehenXML pvOutputDarlehenXML, SicherheitenDaten pvSicherheitenDaten, int pvVerarbeitungstyp)
  {
    //System.out.println("Darlehen: " + pvZielDarlehen.getKontonummer() + " Kompensationsschluessel: " + pvZielDarlehen.getKompensationsschluessel());

    //System.out.println("Kontonummer: " + pvZielDarlehen.getKontonummer() + " Buergschaftprozent: " + pvZielDarlehen.getBuergschaftProzent());
    ivFinanzgeschaeft.initTXSFinanzgeschaeft();
    ivSliceInDaten.initTXSSliceInDaten();
    ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
    ivKondDaten.initTXSKonditionenDaten();
    ivKredkundeKonsorte.initTXSKreditKunde();
    //ivKredkundeKreditnehmer.initTXSKreditKunde();

    boolean lvOkayDarlehen = true;

    //String lvHelpKontonummer = new String();

    lvOkayDarlehen = ivFinanzgeschaeft.importDarlehen(DarlehenVerarbeiten.DARKA, pvZielDarlehen);
    ivFinanzgeschaeft.setQuelle("ADARLREFI");

    // Protokollierung fuer DeepSea - 07.10.2016
    //if (pvVerarbeitungstyp == RefiRegisterVerarbeitung.DEEP_SEA)
    //{
    //  ivLogger.info("DeepSea;DeepSea;DarKa;" + pvZielDarlehen.getKontonummer() + ";" + pvZielDarlehen.getBewilligendeOE() + ";" + pvZielDarlehen.getVerwaltendeOE() + ";" + pvZielDarlehen.getProduktverantwortlicheOE() + ";" + pvZielDarlehen.getKundennummer() + ";" + pvZielDarlehen.getUrsprungsKapital() + ";" + pvZielDarlehen.getSatzwaehrung());
    //}

    if (lvOkayDarlehen)
    {
      // TXSFinanzgeschaeftDaten
      lvOkayDarlehen = ivFinanzgeschaeftDaten.importDarlehen(pvZielDarlehen);
      if (pvVerarbeitungstyp == RefiRegisterVerarbeitung.REFI_REG)
      {
        ivFinanzgeschaeftDaten.setKonskredit("1");
        ivFinanzgeschaeftDaten.setKonsfuehrer("NORD/LB");
        ivFinanzgeschaeftDaten.setKonsantart("FREMD");
      }
      // Kommentar
      //if (lvHelpRefiZusatz != null)
      //{
      //  ivFinanzgeschaeftDaten.setText(String2XML.change2XML(lvHelpRefiZusatz.getKommentarFinanzgeschaeft()));
      //}

      //if (StringKonverter.convertString2Double(lvRestkapitalEigenString) > 0.0)
      //{
      //BigDecimal lvErgebnis = lvRestkapitalFremd.divide(lvRestkapitalEigen, 9, RoundingMode.HALF_UP).multiply(new BigDecimal("100.0"));
      //ivFinanzgeschaeftDaten.setKonsquo(lvErgebnis.toString());
      //}
      //else
      //{
      //ivFinanzgeschaeftDaten.setKonsquo("0.0");
      //}
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivKondDaten.importDarlehen(DarlehenVerarbeiten.DARKA, pvZielDarlehen, ivLogger);
      ivKondDaten.setLrate("0.01");

    }
    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivKredkundeKonsorte.importDarlehen(DarlehenVerarbeiten.DARKA, pvZielDarlehen);
      if (pvVerarbeitungstyp == RefiRegisterVerarbeitung.REFI_REG)
      {
        ivKredkundeKonsorte.setRolle("2");
      }
    }
    ////if (lvOkayDarlehen)
    ////{
    ////    AktivkontenDaten lvHelpDatenAktiv = ivListeAktivkontenDaten.get(pvZielDarlehen.getKontonummer());
    ////    if (lvHelpDatenAktiv != null)
    ////    {
    ////        AktivkontenDaten lvHelpDatenKonsortial = ivListeAktivkontenDaten.get(lvHelpDatenAktiv.getKonsortialkontonummer());
    ////        if (lvHelpDatenKonsortial != null)
    ////        {
    ////            ivKredkundeKreditnehmer.setKdnr(lvHelpDatenKonsortial.getKundennummer());
    ////        }
    ////    }
    ////    ivKredkundeKreditnehmer.setOrg(ValueMapping.changeMandant(pvZielDarlehen.getInstitutsnummer()));
    ////    ivKredkundeKreditnehmer.setQuelle("KUNDE");
    ////    ivKredkundeKreditnehmer.setRolle("1");
    ////}

    //if (ivSapcms.existsSicherheitObjekt(ivZielDarlehen.getKontonummer()))
    //{
    //    System.out.println("Kontonummer - Sicherheit existiert...");
    //}
    //else
    //{
    //    System.out.println("Kontonummer - Sicherheit existiert nicht...");
    //}


    // Transaktionen in die Datei schreiben
    if (lvOkayDarlehen)
    {
      pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());

      pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());

      //pvOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSSliceInDatenStart());
      //pvOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSSliceInDaten());
      //pvOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSSliceInDatenEnde());

      pvOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());

      //pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionStart());
      //pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionDaten());
      //pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionEnde());
    }

    ////if (lvHelpRefiRegisterFilterElement != null)
    ////{
    ////  if (lvHelpRefiRegisterFilterElement.getKonsortialHauptkontonummer().equals("0000000000"))
    ////  {
    ////    lvHelpKontonummer = lvHelpRefiRegisterFilterElement.getPassivKontonummer();
    ////    System.out.println("PassivKontonummer: " + lvHelpKontonummer);
    ////  }
    ////  else
    ////  {
    ////      lvHelpKontonummer = lvHelpRefiRegisterFilterElement.getKonsortialHauptkontonummer();
    ////      System.out.println("KonsortialHauptkontonummer: " + lvHelpKontonummer);
    ////  }

    Sicherungsumfang lvShum = null;

    ivLogger.info("Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvZielDarlehen.getKontonummer());

    // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
    LinkedList<Sicherungsumfang> lvHelpListe = pvSicherheitenDaten.getListeSicherungsumfang().get(pvZielDarlehen.getKontonummer());

    if (lvHelpListe != null)
    {
      for (int x = 0; x < lvHelpListe.size(); x++)
      {
        lvShum = lvHelpListe.get(x);
        //System.out.println("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
        if (lvShum != null)
        {
          //if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
          //{
          Sicherheitenvereinbarung lvSicherheitenvereinbarung = pvSicherheitenDaten.getListeSicherheitenvereinbarung().get(lvShum.getSicherheitenvereinbarungId());
          String lvSicherheitenId = lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId();
          //}
          boolean lvDeckungskennzeichen = false;

          // Last suchen
          Last lvLast = null;
          Collection<Last> lvCollectionLast = pvSicherheitenDaten.getListeLast().values();
          Iterator<Last> lvIteratorLast = lvCollectionLast.iterator();
          while (lvIteratorLast.hasNext())
          {
            Last lvHelpLast = lvIteratorLast.next();
            lvLast = null;
            if (lvHelpLast.getSicherheitenvereinbarungId().equals(lvSicherheitenvereinbarung.getId()))
            {
              lvLast = lvHelpLast;
            }

            if (lvLast != null)
            {
              // Immobilie suchen
              Immobilie lvImmobilie = null;
              Collection<Immobilie> lvCollectionImmobilie = pvSicherheitenDaten.getListeImmobilie().values();
              Iterator<Immobilie> lvIteratorImmobilie = lvCollectionImmobilie.iterator();
              while (lvIteratorImmobilie.hasNext())
              {
                Immobilie lvHelpImmobilie = lvIteratorImmobilie.next();
                if (lvHelpImmobilie.getId().equals(lvLast.getImmobilieId()))
                {
                  lvImmobilie = lvHelpImmobilie;
                }
              }

              // Schiff suchen
              Schiff lvSchiff = null;
              Collection<Schiff> lvCollectionSchiff = pvSicherheitenDaten.getListeSchiff().values();
              Iterator<Schiff> lvIteratorSchiff = lvCollectionSchiff.iterator();
              while (lvIteratorSchiff.hasNext())
              {
                Schiff lvHelpSchiff = lvIteratorSchiff.next();
                if (lvHelpSchiff.getId().equals(lvLast.getImmobilieId()))
                {
                  lvSchiff = lvHelpSchiff;
                }
              }

              // Wenn Deckungskennzeichen 'D', 'F' oder 'R' ist, dann Sicherheitenabtretung hinzufuegen
              if (lvImmobilie != null)
              {
                //System.out.println("Deckungskennzeichen - " + lvSicherheitenId + ": " + lvImmobilie.getDeckungskennzeichen());
                if (lvImmobilie.getDeckungskennzeichen().equals("F") || lvImmobilie.getDeckungskennzeichen().equals("D") || lvImmobilie.getDeckungskennzeichen().equals("R"))
                {
                  lvDeckungskennzeichen = true;
                }
              }

              if (lvSchiff != null)
              {
                //System.out.println("Deckungskennzeichen - " + lvSicherheitenId + ": " + lvSchiff.getStatusDeckung());
                //if (lvSchiff.getStatusDeckung().equals("V") || lvSchiff.getStatusDeckung().equals("S") || lvSchiff.getStatusDeckung().equals("R"))
                //{
                lvDeckungskennzeichen = true;
                //}
              }
            }
          }

          if (pvVerarbeitungstyp == RefiRegisterVerarbeitung.REFI_REG)
          {
            if (lvSicherheitenId != null && lvDeckungskennzeichen)
            {
              ivLogger.info("Sicherheit existiert... -> " + lvSicherheitenId);

              TXSKreditSicherheit lvKredSh = new TXSKreditSicherheit();
              lvKredSh.setKey(lvSicherheitenId + "_" + pvZielDarlehen.getKundennummer() + "_" + pvZielDarlehen.getKontonummer() + "_ABTR");
              lvKredSh.setQuelle("ADARLREFI");
              lvKredSh.setOrg(ValueMapping.changeMandant(pvZielDarlehen.getInstitutsnummer()));
              lvKredSh.setHauptsh("0");
              lvKredSh.setZuwbetrag("0.01");
              lvKredSh.setWhrg("EUR");

              TXSSicherheitDaten lvShdatenAbtr = new TXSSicherheitDaten();
              lvShdatenAbtr.setArt("55");
              //System.out.println("Anzahl RefiZusatz: " + pvListeRefiZusatz.size());
              //System.out.println("get: " + pvZielDarlehen.getKontonummer() + lvSicherheitenId);
              //RefiZusatz lvRefiZusatz = pvListeRefiZusatz.get(pvZielDarlehen.getKontonummer() + lvSicherheitenId);
              //if (lvRefiZusatz != null)
              //{
              //  lvShdatenAbtr.setRgrund(String2XML.change2XML(lvRefiZusatz.getRechtsgrundlage()));
              //  lvShdatenAbtr.setRgrunddat(DatumUtilities.changeDate(lvRefiZusatz.getDatumRechtlicherGrund()));
              //  lvShdatenAbtr.setDsepsichzerkl(DatumUtilities.changeDate(lvRefiZusatz.getDatumRechtlicherGrund()));
              //  lvShdatenAbtr.setOrigsichant(lvRefiZusatz.getUmfang());
              //  lvShdatenAbtr.setReginfo(String2XML.change2XML(lvRefiZusatz.getBemerkung()));
              //}

              TXSSicherheitPerson2 lvHelpPerson = new TXSSicherheitPerson2();
              lvHelpPerson.setKdnr(pvZielDarlehen.getKundennummer());
              lvHelpPerson.setOrg(ValueMapping.changeMandant(pvZielDarlehen.getInstitutsnummer()));
              lvHelpPerson.setQuelle("KUNDE");
              lvHelpPerson.setRolle("2");

              pvOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionStart());
              pvOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionDaten());
              pvOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionStart());
              pvOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionDaten());
              pvOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionEnde());

              pvOutputDarlehenXML.printTransaktion(lvHelpPerson.printTXSTransaktionStart());
              pvOutputDarlehenXML.printTransaktion(lvHelpPerson.printTXSTransaktionDaten());
              pvOutputDarlehenXML.printTransaktion(lvHelpPerson.printTXSTransaktionEnde());

              pvOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionEnde());
              ivLogger.info("RefiListe:" + pvZielDarlehen.getKontonummer() + ";" + lvSicherheitenId + ";" + pvZielDarlehen.getKundennummer());
            }
            else
            {
              ivLogger.info("Konsortial-Hauptkontonummer - Sicherheit existiert nicht...");
            }
          }
        }
      }
    }
    ////}

    Sicherheiten2RefiRegister lvSicherheiten2RefiRegister = new Sicherheiten2RefiRegister(pvSicherheitenDaten, ivLogger);
    pvOutputDarlehenXML.printTransaktion(lvSicherheiten2RefiRegister.importSicherheitHypotheken(pvZielDarlehen.getKontonummer(), pvZielDarlehen.getKontonummer(), pvZielDarlehen.getKundennummer(), pvZielDarlehen.getKredittyp(), pvZielDarlehen.getBuergschaftProzent(), "ADARLREFI", pvZielDarlehen.getInstitutsnummer(), null));

    if (lvOkayDarlehen)
    {
      pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
    }
  }
}
