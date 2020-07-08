/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.File;
import java.io.FileOutputStream;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class DarlehenXML 
{
    /**
     *  Name der XML-Ausgabedatei
     */
    private String ivFilename;

    /**
     * 
     */
    private FileOutputStream ivXmlOS;

  /**
   *  log4j-Logger
   */
  private Logger ivLogger;

  /**
     * Konstruktor
     * @param pvFilename Name der XML-Ausgabedatei
     * @param pvLogger log4j-Logger
     */
    public DarlehenXML(String pvFilename, Logger pvLogger)
    {
        this.ivFilename = pvFilename;
        this.ivLogger = pvLogger;
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
        ivLogger.error("Konnte Darlehen XML-Datei nicht oeffnen!");
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
          ivLogger.error("Konnte Darlehen XML-Datei nicht schliessen!");
      }
    }

    /**
     * Ausgabe des XML-Starts
     * @param pvVorlaufsatz 
     */
    public void printXMLStart(DWHVOR pvVorlaufsatz)
    {   
      // Start der XML-Datei schreiben
      try
      {
        ivXmlOS.write(("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>" + StringKonverter.lineSeparator).getBytes());
        ivXmlOS.write(("<Institut nr=\"" + pvVorlaufsatz.getsDwvinst()+ "\">" + StringKonverter.lineSeparator).getBytes());
      }
      catch (Exception e)
      {
        ivLogger.error("Start: Fehler bei Ausgabe in Darlehen XML-Datei");
      }
    }
     
    /**
     * 
     */
    public void printXMLEnde()
    {
        // Ende der XML-Datei schreiben
        try
        {
           ivXmlOS.write(("</Institut>" + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Ende: Fehler bei Ausgabe in Darlehen XML-Datei");
        }       
    }

    
    /**
     * Schreibt die Kunde-Request Datei
     * @param pvDarlehen 
     */
    public void printDarlehen(Darlehen pvDarlehen)
    {
        try
        {
           ivXmlOS.write(("  <Darlehen nr=\"" + pvDarlehen.getKontonummer() + "\" >" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Darlehenssystem>" + pvDarlehen.getHerkunftDarlehen() + "</Darlehenssystem>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Sicherheitensystem>" + pvDarlehen.getHerkunftSicherheit() + "</Sicherheitensystem>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Datenherkunft>" + pvDarlehen.getHerkunftDaten() + "</Datenherkunft>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kundennummer>" + pvDarlehen.getKundennummer() + "</Kundennummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kusyma>" + pvDarlehen.getKusyma() + "</Kusyma>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Objektnummer>" + pvDarlehen.getObjektnummer() + "</Objektnummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Objektart>" + pvDarlehen.getObjektart() + "</Objektart>" + StringKonverter.lineSeparator).getBytes());
           if (!pvDarlehen.getSelektionskennzeichen().equals("E") || !pvDarlehen.getSelektionskennzeichen().equals("W"))
           {
             ivXmlOS.write(("    <Selektionskennzeichen></Selektionskennzeichen>" + StringKonverter.lineSeparator).getBytes());
           }
           else
           {
               ivXmlOS.write(("    <Selektionskennzeichen>" + pvDarlehen.getSelektionskennzeichen() + "</Selektionskennzeichen>" + StringKonverter.lineSeparator).getBytes());
           }
           ivXmlOS.write(("    <Kontostatus>" + pvDarlehen.getKontostatus() + "</Kontostatus>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <ZinsrechnungSchluessel>" + pvDarlehen.getZinsrechnungSchluessel() + "</ZinsrechnungSchluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kontozustand>" + pvDarlehen.getKontozustand() + "</Kontozustand>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kontonummer>" + pvDarlehen.getKontonummer() + "</Kontonummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <ISIN>" + pvDarlehen.getIsin() + "</ISIN>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Globalurkundennr>" + pvDarlehen.getGlobalurkundennr() + "</Globalurkundennr>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Finanzkontonummer>" + pvDarlehen.getFinanzkontonummer() + "</Finanzkontonummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <BuergschaftSchluessel>" + pvDarlehen.getBuergschaftSchluessel() + "</BuergschaftSchluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <SicherheitenSchluessel>" + pvDarlehen.getSicherheitenSchluessel() + "</SicherheitenSchluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <ProduktSchluessel>" + pvDarlehen.getProduktSchluessel() + "</ProduktSchluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kompensationsschluessel>" + pvDarlehen.getKompensationsschluessel() + "</Kompensationsschluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Satzwaehrung>" + pvDarlehen.getSatzwaehrung() + "</Satzwaehrung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Vollvalutierungsdatum>" + pvDarlehen.getVollvalutierungsdatum() + "</Vollvalutierungsdatum>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Bewilligungsdatum>" + pvDarlehen.getBewilligungsdatum() + "</Bewilligungsdatum>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Tilgungsbeginn>" + pvDarlehen.getTilgungsbeginn() + "</Tilgungsbeginn>" + StringKonverter.lineSeparator).getBytes());
           //ivXmlOS.write((new String("    <NePE>" + pvDarlehen.getNePE() + "</NePE>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write(("    <Tilgungstermin>" + pvDarlehen.getTilgungstermin() + "</Tilgungstermin>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Annahmedatum>" + pvDarlehen.getAnnahmeDatum() + "</Annahmedatum>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <ZinsanpassungDatum>" + pvDarlehen.getZinsanpassungDatum() + "</ZinsanpassungDatum>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <LetzteZinsanpassung>" + pvDarlehen.getLetzteZinsanpassung() + "</LetzteZinsanpassung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <NaechsteZinsfaelligkeit>" + pvDarlehen.getNaechsteZinsfaelligkeit() + "</NaechsteZinsfaelligkeit>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <VertragBis>" + pvDarlehen.getVertragBis() + "</VertragBis>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Buergennummer>" + pvDarlehen.getBuergennummer() + "</Buergennummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <MaLeReSchluessel>" + pvDarlehen.getMaLeReSchluessel() + "</MaLeReSchluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Deckungsschluessel>" + pvDarlehen.getDeckungsschluessel() + "</Deckungsschluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <TilgungsschluesselKTR>" + pvDarlehen.getTilgungsschluesselKTR() + "</TilgungsschluesselKTR>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <TilgungsschluesselKON>" + pvDarlehen.getTilgungsschluesselKON() + "</TilgungsschluesselKON>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <TilgungsschluesselUltimo>" + pvDarlehen.getTilgungsschluesselUltimo() + "</TilgungsschluesselUltimo>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Zinsschluessel>" + pvDarlehen.getZinsschluessel() + "</Zinsschluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <TilgungJahr>" + pvDarlehen.getTilgungJahr() + "</TilgungJahr>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Zinsbeginn>" + pvDarlehen.getZinsbeginn() + "</Zinsbeginn>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <LetzterZinstermin>" + pvDarlehen.getLetzterZinstermin() + "</LetzterZinstermin>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <NaechsterZinstermin>" + pvDarlehen.getNaechsterZinstermin() + "</NaechsterZinstermin>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <ZusageDatum>" + pvDarlehen.getZusageDatum() + "</ZusageDatum>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kontozusatz1>" + String2XML.change2XML(pvDarlehen.getKontoZusatz1()) + "</Kontozusatz1>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kontozusatz2>" + String2XML.change2XML(pvDarlehen.getKontoZusatz2()) + "</Kontozusatz2>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Anpassungstermin>" + pvDarlehen.getAnpassungstermin() + "</Anpassungstermin>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Zinstyp>" + pvDarlehen.getZinstyp() + "</Zinstyp>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <GueltigAbDatum>" + pvDarlehen.getGueltigAbDatum() + "</GueltigAbDatum>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <KondDatum>" + pvDarlehen.getKondDatum() + "</KondDatum>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Berechnungsgrundlage>" + pvDarlehen.getBerechnungsgrundlage() + "</Berechnungsgrundlage>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Faelligkeitstermin>" + pvDarlehen.getFaelligkeitstermin() + "</Faelligkeitstermin>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <KondEinrichtungsdatum>" + pvDarlehen.getKondEinrichtungsdatum() + "</KondEinrichtungsdatum>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <FaelligkeitBes>" + pvDarlehen.getFaelligkeitBes() + "</FaelligkeitBes>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Rechnungsmod>" + pvDarlehen.getRechnungsmod() + "</Rechnungsmod>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <KennzeichenAktPas>" + pvDarlehen.getKennzeichenAktPas() + "</KennzeichenAktPas>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kundengruppe>" + pvDarlehen.getKundengruppe() + "</Kundengruppe>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <MerkmalKreditart>" + pvDarlehen.getMerkmalKreditart() + "</MerkmalKreditart>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kredittyp>" + pvDarlehen.getKredittyp() + "</Kredittyp>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <VorNotADat>" + pvDarlehen.getVorNotADat() + "</VorNotADat>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <KennzeichenVorAus>" + pvDarlehen.getKennzeichenVorAus() + "</KennzeichenVorAus>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <BuchungsdatumPlus2>" + pvDarlehen.getBuchungsdatumPlus2() + "</BuchungsdatumPlus2>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <SollstellungSchluessel>" + pvDarlehen.getSollstellungSchluessel() + "</SollstellungSchluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <FristigkeitSchluessel>" + pvDarlehen.getFristigkeitSchluessel() + "</FristigkeitSchluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <AnzahlTilgungsperioden>" + pvDarlehen.getAnzahlTilgungsperioden() + "</AnzahlTilgungsperioden>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <GrTilgPer>" + pvDarlehen.getGrTilgPer() + "</GrTilgPer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <AnzahlZinsperioden>" + pvDarlehen.getAnzahlZinsperioden() + "</AnzahlZinsperioden>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <GrZinsperiode>" + pvDarlehen.getGrZinsperiode() + "</GrZinsperiode>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <LaufzeitZinsanpassung>" + pvDarlehen.getLaufzeitZinsanpassung() + "</LaufzeitZinsanpassung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <KennzeichenRollover>" + pvDarlehen.getKennzeichenRollover() + "</KennzeichenRollover>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <TilgungNePE>" + pvDarlehen.getTilgungNePE() + "</TilgungNePE>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <TilgungVorFTage>" + pvDarlehen.getTilgungVorFTage() + "</TilgungVorFTage>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <ZinsVorFTage>" + pvDarlehen.getZinsVorFTage() + "</ZinsVorFTage>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <KennzeichenKonditionen>" + pvDarlehen.getKennzeichenKonditionen() + "</KennzeichenKonditionen>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <UrsprungsLaufzeit>" + pvDarlehen.getUrsprungsLaufzeit() + "</UrsprungsLaufzeit>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <UrsprungsKapital>" + pvDarlehen.getUrsprungsKapital() + "</UrsprungsKapital>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Restkapital>" + pvDarlehen.getRestkapital() + "</Restkapital>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <OffZusage>" + pvDarlehen.getOffZusage() + "</OffZusage>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <BeleihungProzent>" + pvDarlehen.getBeleihungProzent() + "</BeleihungProzent>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <BuergschaftProzent>" + pvDarlehen.getBuergschaftProzent() + "</BuergschaftProzent>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Auszahlungskurs>" + pvDarlehen.getAuszahlungskurs() + "</Auszahlungskurs>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Berichtigungsposten>" + pvDarlehen.getBerichtigungsposten() + "</Berichtigungsposten>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <TilgungProzent>" + pvDarlehen.getTilgungProzent() + "</TilgungProzent>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <DarlehenszinssatzProzent>" + pvDarlehen.getDarlehenszinssatzProzent() + "</DarlehenszinssatzProzent>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <AnnuitaetenzinssatzProzent>" + pvDarlehen.getAnnuitaetenzinssatzProzent() + "</AnnuitaetenzinssatzProzent>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Berechnungsnominale>" + pvDarlehen.getBerechnungsnominale() + "</Berechnungsnominale>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <RisikoaufschlagProzent>" + pvDarlehen.getRisikoaufschlagProzent() + "</RisikoaufschlagProzent>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Tilgungsbetrag>" + pvDarlehen.getTilgungsbetrag() + "</Tilgungsbetrag>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Cap>" + pvDarlehen.getCap() + "</Cap>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Floor>" + pvDarlehen.getFloor() + "</Floor>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <SummeRueckstellungsleistung>" + pvDarlehen.getSummeRueckstellungsleistung() + "</SummeRueckstellungsleistung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <SummeNoNiFaeTilgung>" + pvDarlehen.getSummeNoNiFaeTilgung() + "</SummeNoNiFaeTilgung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <SummeVorauszahlung>" + pvDarlehen.getSummeVorauszahlungen() + "</SummeVorauszahlung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <TilgungRueckstellung>" + pvDarlehen.getTilgungRueckstellung() + "</TilgungRueckstellung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <SummeUKAP>" + pvDarlehen.getSummeUKAP() + "</SummeUKAP>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <SummeRKAP>" + pvDarlehen.getSummeRKAP() + "</SummeRKAP>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Zuweisungsbetrag>" + pvDarlehen.getZuweisungsbetrag() + "</Zuweisungsbetrag>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Solldeckung>" + pvDarlehen.getSolldeckung() + "</Solldeckung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Sondertilgung>" + pvDarlehen.getSondertilgung() + "</Sondertilgung>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("  </Darlehen>" + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Fehler bei Ausgabe in Darlehen XML-Datei");
        }
    }


}
