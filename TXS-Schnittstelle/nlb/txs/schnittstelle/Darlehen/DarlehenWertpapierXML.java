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
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class DarlehenWertpapierXML
{
    /**
     * 
     */
    private String ivFilename;
    
    /**
     * 
     */
    private File ivXmlFile;
    
    /**
     * 
     */
    private FileOutputStream ivXmlOS;
 
    /**
     * Konstruktor
     * @param pvFilename 
     * @param mandant 
     */
    public DarlehenWertpapierXML(String pvFilename)
    {
        this.ivFilename = pvFilename;
    }
    
    /**
      * Oeffnen der XML-Datei
      */
    public void openXML()
    {
      ivXmlFile = new File(ivFilename);
      try
      {
        ivXmlOS = new FileOutputStream(ivXmlFile);
      }
      catch (Exception e)
      {
        System.out.println("Konnte DarlehenWP XML-Datei nicht oeffnen!");
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
          System.out.println("Konnte DarlehenWP XML-Datei nicht schliessen!");       
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
        ivXmlOS.write((new String("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>" + StringKonverter.lineSeparator)).getBytes());
        ivXmlOS.write((new String("<Institut nr=\"" + pvVorlaufsatz.getsDwvinst()+ "\" valdate=\"" + DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvVorlaufsatz.getsDwvbdat())) + "\">" + StringKonverter.lineSeparator)).getBytes());
      }
      catch (Exception e)
      {
        System.out.println("Start: Fehler bei Ausgabe in DarlehenWP XML-Datei");
      }
    }
     
    /**
     * 
     */
    public void printXMLEnde()
    {
        // Header der XML-Datei schreiben
        try
        {
           ivXmlOS.write((new String("</Institut>" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in DarlehenWP XML-Datei");
        }       
    }

    
    /**
     * Schreibt die DarlehenWP-Datei
     * @param pvDarlehen 
     */
    public void printDarlehen(Darlehen pvDarlehen)
    {
        // Header der XML-Datei schreiben
        try
        {
           ivXmlOS.write((new String("    <DarlehenWP nr=\"" + pvDarlehen.getKontonummer() + "\" >" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Darlehenssystem>" + pvDarlehen.getHerkunftDarlehen() + "</Darlehenssystem>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Sicherheitensystem>" + pvDarlehen.getHerkunftSicherheit() + "</Sicherheitensystem>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Datenherkunft>" + pvDarlehen.getHerkunftDaten() + "</Datenherkunft>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kundennummer>" + pvDarlehen.getKundennummer() + "</Kundennummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kusyma>" + pvDarlehen.getKusyma() + "</Kusyma>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Objektnummer>" + pvDarlehen.getObjektnummer() + "</Objektnummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Objektart>" + pvDarlehen.getObjektart() + "</Objektart>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kontostatus>" + pvDarlehen.getKontostatus() + "</Kontostatus>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <ZinsrechnungSchluessel>" + pvDarlehen.getZinsrechnungSchluessel() + "</ZinsrechnungSchluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kontozustand>" + pvDarlehen.getKontozustand() + "</Kontozustand>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kontonummer>" + pvDarlehen.getKontonummer() + "</Kontonummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <ISIN>" + pvDarlehen.getIsin() + "</ISIN>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Globalurkundennr>" + pvDarlehen.getGlobalurkundennr() + "</Globalurkundennr>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Finanzkontonummer>" + pvDarlehen.getFinanzkontonummer() + "</Finanzkontonummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <BuergschaftSchluessel>" + pvDarlehen.getBuergschaftSchluessel() + "</BuergschaftSchluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <SicherheitenSchluessel>" + pvDarlehen.getSicherheitenSchluessel() + "</SicherheitenSchluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <ProduktSchluessel>" + pvDarlehen.getProduktSchluessel() + "</ProduktSchluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kompensationsschluessel>" + pvDarlehen.getKompensationsschluessel() + "</Kompensationsschluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Satzwaehrung>" + pvDarlehen.getSatzwaehrung() + "</Satzwaehrung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Vollvalutierungsdatum>" + pvDarlehen.getVollvalutierungsdatum() + "</Vollvalutierungsdatum>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Bewilligungsdatum>" + pvDarlehen.getBewilligungsdatum() + "</Bewilligungsdatum>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Tilgungsbeginn>" + pvDarlehen.getTilgungsbeginn() + "</Tilgungsbeginn>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Tilgungstermin>" + pvDarlehen.getTilgungstermin() + "</Tilgungstermin>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Annahmedatum>" + pvDarlehen.getAnnahmeDatum() + "</Annahmedatum>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <ZinsanpassungDatum>" + pvDarlehen.getZinsanpassungDatum() + "</ZinsanpassungDatum>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <LetzteZinsanpassung>" + pvDarlehen.getLetzteZinsanpassung() + "</LetzteZinsanpassung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <NaechsteZinsfaelligkeit>" + pvDarlehen.getNaechsteZinsfaelligkeit() + "</NaechsteZinsfaelligkeit>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <VertragBis>" + pvDarlehen.getVertragBis() + "</VertragBis>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Buergennummer>" + pvDarlehen.getBuergennummer() + "</Buergennummer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <MaLeReSchluessel>" + pvDarlehen.getMaLeReSchluessel() + "</MaLeReSchluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Deckungsschluessel>" + pvDarlehen.getDeckungsschluessel() + "</Deckungsschluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <TilgungsschluesselKTR>" + pvDarlehen.getTilgungsschluesselKTR() + "</TilgungsschluesselKTR>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <TilgungsschluesselKON>" + pvDarlehen.getTilgungsschluesselKON() + "</TilgungsschluesselKON>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <TilgungsschluesselUltimo>" + pvDarlehen.getTilgungsschluesselUltimo() + "</TilgungsschluesselUltimo>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Zinsschluessel>" + pvDarlehen.getZinsschluessel() + "</Zinsschluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <TilgungJahr>" + pvDarlehen.getTilgungJahr() + "</TilgungJahr>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Zinsbeginn>" + pvDarlehen.getZinsbeginn() + "</Zinsbeginn>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <LetzterZinstermin>" + pvDarlehen.getLetzterZinstermin() + "</LetzterZinstermin>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <NaechsterZinstermin>" + pvDarlehen.getNaechsterZinstermin() + "</NaechsterZinstermin>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <ZusageDatum>" + pvDarlehen.getZusageDatum() + "</ZusageDatum>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kontozusatz1>" + String2XML.change2XML(pvDarlehen.getKontoZusatz1()) + "</Kontozusatz1>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kontozusatz2>" + String2XML.change2XML(pvDarlehen.getKontoZusatz2()) + "</Kontozusatz2>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Anpassungstermin>" + pvDarlehen.getAnpassungstermin() + "</Anpassungstermin>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Zinstyp>" + pvDarlehen.getZinstyp() + "</Zinstyp>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <GueltigAbDatum>" + pvDarlehen.getGueltigAbDatum() + "</GueltigAbDatum>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <KondDatum>" + pvDarlehen.getKondDatum() + "</KondDatum>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Berechnungsgrundlage>" + pvDarlehen.getBerechnungsgrundlage() + "</Berechnungsgrundlage>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Faelligkeitstermin>" + pvDarlehen.getFaelligkeitstermin() + "</Faelligkeitstermin>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <KondEinrichtungsdatum>" + pvDarlehen.getKondEinrichtungsdatum() + "</KondEinrichtungsdatum>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <FaelligkeitBes>" + pvDarlehen.getFaelligkeitBes() + "</FaelligkeitBes>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Rechnungsmod>" + pvDarlehen.getRechnungsmod() + "</Rechnungsmod>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <KennzeichenAktPas>" + pvDarlehen.getKennzeichenAktPas() + "</KennzeichenAktPas>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kundengruppe>" + pvDarlehen.getKundengruppe() + "</Kundengruppe>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <MerkmalKreditart>" + pvDarlehen.getMerkmalKreditart() + "</MerkmalKreditart>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Kredittyp>" + pvDarlehen.getKredittyp() + "</Kredittyp>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <VorNotADat>" + pvDarlehen.getVorNotADat() + "</VorNotADat>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <KennzeichenVorAus>" + pvDarlehen.getKennzeichenVorAus() + "</KennzeichenVorAus>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <BuchungsdatumPlus2>" + pvDarlehen.getBuchungsdatumPlus2() + "</BuchungsdatumPlus2>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <SollstellungSchluessel>" + pvDarlehen.getSollstellungSchluessel() + "</SollstellungSchluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <FristigkeitSchluessel>" + pvDarlehen.getFristigkeitSchluessel() + "</FristigkeitSchluessel>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <AnzahlTilgungsperioden>" + pvDarlehen.getAnzahlTilgungsperioden() + "</AnzahlTilgungsperioden>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <GrTilgPer>" + pvDarlehen.getGrTilgPer() + "</GrTilgPer>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <AnzahlZinsperioden>" + pvDarlehen.getAnzahlZinsperioden() + "</AnzahlZinsperioden>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <GrZinsperiode>" + pvDarlehen.getGrZinsperiode() + "</GrZinsperiode>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <LaufzeitZinsanpassung>" + pvDarlehen.getLaufzeitZinsanpassung() + "</LaufzeitZinsanpassung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <KennzeichenRollover>" + pvDarlehen.getKennzeichenRollover() + "</KennzeichenRollover>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <TilgungNePE>" + pvDarlehen.getTilgungNePE() + "</TilgungNePE>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <TilgungVorFTage>" + pvDarlehen.getTilgungVorFTage() + "</TilgungVorFTage>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <ZinsVorFTage>" + pvDarlehen.getZinsVorFTage() + "</ZinsVorFTage>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <KennzeichenKonditionen>" + pvDarlehen.getKennzeichenKonditionen() + "</KennzeichenKonditionen>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <UrsprungsLaufzeit>" + pvDarlehen.getUrsprungsLaufzeit() + "</UrsprungsLaufzeit>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <UrsprungsKapital>" + pvDarlehen.getUrsprungsKapital() + "</UrsprungsKapital>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Restkapital>" + pvDarlehen.getRestkapital() + "</Restkapital>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <OffZusage>" + pvDarlehen.getOffZusage() + "</OffZusage>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <BeleihungProzent>" + pvDarlehen.getBeleihungProzent() + "</BeleihungProzent>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <BuergschaftProzent>" + pvDarlehen.getBuergschaftProzent() + "</BuergschaftProzent>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Auszahlungskurs>" + pvDarlehen.getAuszahlungskurs() + "</Auszahlungskurs>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Berichtigungsposten>" + pvDarlehen.getBerichtigungsposten() + "</Berichtigungsposten>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <TilgungProzent>" + pvDarlehen.getTilgungProzent() + "</TilgungProzent>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <DarlehenszinssatzProzent>" + pvDarlehen.getDarlehenszinssatzProzent() + "</DarlehenszinssatzProzent>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <AnnuitaetenzinssatzProzent>" + pvDarlehen.getAnnuitaetenzinssatzProzent() + "</AnnuitaetenzinssatzProzent>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Berechnungsnominale>" + pvDarlehen.getBerechnungsnominale() + "</Berechnungsnominale>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <RisikoaufschlagProzent>" + pvDarlehen.getRisikoaufschlagProzent() + "</RisikoaufschlagProzent>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Tilgungsbetrag>" + pvDarlehen.getTilgungsbetrag() + "</Tilgungsbetrag>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Cap>" + pvDarlehen.getCap() + "</Cap>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Floor>" + pvDarlehen.getFloor() + "</Floor>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <SummeRueckstellungsleistung>" + pvDarlehen.getSummeRueckstellungsleistung() + "</SummeRueckstellungsleistung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <SummeNoNiFaeTilgung>" + pvDarlehen.getSummeNoNiFaeTilgung() + "</SummeNoNiFaeTilgung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <SummeVorauszahlung>" + pvDarlehen.getSummeVorauszahlungen() + "</SummeVorauszahlung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <TilgungRueckstellung>" + pvDarlehen.getTilgungRueckstellung() + "</TilgungRueckstellung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <SummeUKAP>" + pvDarlehen.getSummeUKAP() + "</SummeUKAP>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <SummeRKAP>" + pvDarlehen.getSummeRKAP() + "</SummeRKAP>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Zuweisungsbetrag>" + pvDarlehen.getZuweisungsbetrag() + "</Zuweisungsbetrag>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Solldeckung>" + pvDarlehen.getSolldeckung() + "</Solldeckung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Sondertilgung>" + pvDarlehen.getSondertilgung() + "</Sondertilgung>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Umsatz12>" + pvDarlehen.getUmsatz12() + "</Umsatz12>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("      <Umsatz19>" + pvDarlehen.getUmsatz19() + "</Umsatz19>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("    </DarlehenWP>" + StringKonverter.lineSeparator)).getBytes());           
        }
        catch (Exception e)
        {
          System.out.println("Start: Fehler bei Ausgabe in DarlehenWP XML-Datei");
        }
    }

}
