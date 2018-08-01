/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.RefiRegister;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.SAPCMS.SAPCMS_Neu;
import nlb.txs.schnittstelle.SAPCMS.Entities.Immobilie;
import nlb.txs.schnittstelle.SAPCMS.Entities.Last;
import nlb.txs.schnittstelle.SAPCMS.Entities.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.SAPCMS.Entities.Sicherungsumfang;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson2;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class RefiRegisterPassiv 
{
    // Liste mit AktivkontenDaten
    private AktivkontenListe ivListeAktivkontenDaten;
    
    // Transaktionen
    private TXSFinanzgeschaeft ivFinanzgeschaeft;
    private TXSSliceInDaten ivSliceInDaten;
    private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
    private TXSKonditionenDaten ivKondDaten;
    private TXSKreditKunde ivKredkundeKonsorte;
    private TXSKreditKunde ivKredkundeKreditnehmer;

    /**
     * Logger
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor
     * @param pvExportVerzeichnis
     * @param pvLogger
     */
    public RefiRegisterPassiv(String pvExportVerzeichnis, Logger pvLogger) 
    {
        this.ivLogger = pvLogger;
        
        ivListeAktivkontenDaten = new AktivkontenListe();
        
        // AktivkontenDaten in Liste einlesen
        try
        {
            FileReader lvAktivkontenDatenFileReader = new FileReader(pvExportVerzeichnis + "\\AktivkontenDaten.txt");
            BufferedReader lvAktivkontenDatenBufferedReader = new BufferedReader(lvAktivkontenDatenFileReader);
            
            String lvZeile = "";
            
            while ((lvZeile = lvAktivkontenDatenBufferedReader.readLine()) != null)   
            {  
                AktivkontenDaten lvAktivkontenDaten = new AktivkontenDaten(); 
                
                // Kontonummer
                lvAktivkontenDaten.setKontonummer(lvZeile.substring(0,10));
                // Passivkontonummer
                lvAktivkontenDaten.setPassivkontonummer(lvZeile.substring(11,21));                
                // Konsortialkontonummer
                lvAktivkontenDaten.setKonsortialkontonummer(lvZeile.substring(22,32));                
                // Kundennummer
                lvAktivkontenDaten.setKundennummer(lvZeile.substring(33));    
                
                ivListeAktivkontenDaten.addAktivkontenDaten(lvAktivkontenDaten.getKontonummer(), lvAktivkontenDaten);
            }   
            
            lvAktivkontenDatenBufferedReader.close();
        }
        catch (Exception e)
        {
            ivLogger.error("Konnte AktivkontenDaten '" + pvExportVerzeichnis + "/AktivkontenDaten.txt' nicht oeffnen");
        } 
        
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
     * @param pvSapcms 
     * @param pvListeRefiRegisterFilterElemente 
     * @param pvListeRefiZusatz 
     * @param pvLogger 
     * @param pvSAPCMS 
     */
     public void importDarlehen2Transaktion(Darlehen pvZielDarlehen, OutputDarlehenXML pvOutputDarlehenXML, SAPCMS_Neu pvSapcms, HashMap<String, RefiRegisterFilterElement> pvListeRefiRegisterFilterElemente, HashMap<String, RefiZusatz> pvListeRefiZusatz, Logger pvLogger)
     {
         //System.out.println("Darlehen: " + pvZielDarlehen.getKontonummer() + " Kompensationsschluessel: " + pvZielDarlehen.getKompensationsschluessel());  

         ivFinanzgeschaeft.initTXSFinanzgeschaeft();
         ivSliceInDaten.initTXSSliceInDaten();
         ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
         ivKondDaten.initTXSKonditionenDaten();
         ivKredkundeKonsorte.initTXSKreditKunde();
         ivKredkundeKreditnehmer.initTXSKreditKunde();

         //TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
         //TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
         //TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
         //TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
         //TXSVerzeichnisVBlatt lvVevb = new TXSVerzeichnisVBlatt();
         //TXSVerzeichnisblattDaten lvVbdaten = new TXSVerzeichnisblattDaten();
         //TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
         //TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();
         //TXSSicherheitPerson lvShperson = new TXSSicherheitPerson();
            
         boolean lvOkayDarlehen = true;
         
         //System.out.println("pvZielDarlehen.getKontonummer(): " + pvZielDarlehen.getKontonummer());
         RefiRegisterFilterElement lvHelpRefiRegisterFilterElement = pvListeRefiRegisterFilterElemente.get(pvZielDarlehen.getKontonummer());
         String lvHelpKontonummer = new String();
         
         lvOkayDarlehen = ivFinanzgeschaeft.importDarlehen(DarlehenVerarbeiten.DARKA, pvZielDarlehen);
         ivFinanzgeschaeft.setQuelle("ADARLREFI");  
             
         if (lvOkayDarlehen)
         {
            // TXSFinanzgeschaeftDaten 
            lvOkayDarlehen = ivFinanzgeschaeftDaten.importDarlehen(pvZielDarlehen);
            ivFinanzgeschaeftDaten.setKonskredit("1");
            ivFinanzgeschaeftDaten.setKonsfuehrer("NORD/LB");
            ivFinanzgeschaeftDaten.setKonsantart("FREMD");
            // Kommentar
            if (lvHelpRefiRegisterFilterElement != null)
            {
              if (lvHelpRefiRegisterFilterElement.getKonsortialHauptkontonummer().equals("0000000000"))
              {
                lvHelpKontonummer = lvHelpRefiRegisterFilterElement.getPassivKontonummer();
                //System.out.println("PassivKontonummer: " + lvHelpKontonummer);
              }
              else
              {
                  lvHelpKontonummer = lvHelpRefiRegisterFilterElement.getKonsortialHauptkontonummer();
                  //System.out.println("KonsortialHauptkontonummer: " + lvHelpKontonummer);
              }
            }
            
            RefiZusatz lvHelpRefiZusatz = null;
            Collection<RefiZusatz> lvHelpRefiZusatzCollection = pvListeRefiZusatz.values();
            Iterator<RefiZusatz> lvHelpRefiZusatzIter = lvHelpRefiZusatzCollection.iterator();
            while (lvHelpRefiZusatzIter.hasNext())
            {
                lvHelpRefiZusatz = lvHelpRefiZusatzIter.next();
                //System.out.println(lvHelpRefiZusatz.getPassivkontonummer() + " == " + pvZielDarlehen.getKontonummer());
                if (lvHelpRefiZusatz.getPassivkontonummer().equals(pvZielDarlehen.getKontonummer()))
                {
                    System.out.println("Gleich...");
                    break;
                }
                else
                {
                    lvHelpRefiZusatz = null;
                }
            }
            if (lvHelpRefiZusatz != null)
            {
              ivFinanzgeschaeftDaten.setText(String2XML.change2XML(lvHelpRefiZusatz.getKommentarFinanzgeschaeft()));
            }
            
            RefiRegisterFilterElement helpRefiRegisterFilterElementFremd = pvListeRefiRegisterFilterElemente.get(pvZielDarlehen.getKontonummer());
            if (helpRefiRegisterFilterElementFremd != null)
            {
              String lvKonsortialhauptkontonummer = helpRefiRegisterFilterElementFremd.getKonsortialHauptkontonummer();
              String lvRestkapitalFremdString = helpRefiRegisterFilterElementFremd.getRestkapital();
              if (lvRestkapitalFremdString.startsWith("-"))
              {
                lvRestkapitalFremdString = lvRestkapitalFremdString.substring(1);
              }
              BigDecimal lvRestkapitalFremd = StringKonverter.convertString2BigDecimal(lvRestkapitalFremdString);
              RefiRegisterFilterElement helpRefiRegisterFilterElementEigen = pvListeRefiRegisterFilterElemente.get(lvKonsortialhauptkontonummer);
              if (helpRefiRegisterFilterElementEigen != null)
              {
                  String lvRestkapitalEigenString = helpRefiRegisterFilterElementEigen.getRestkapital();
                  if (lvRestkapitalEigenString.startsWith("-"))
                  {
                      lvRestkapitalEigenString = lvRestkapitalEigenString.substring(1);
                  }
                  BigDecimal lvRestkapitalEigen = StringKonverter.convertString2BigDecimal(lvRestkapitalEigenString);
                  if (StringKonverter.convertString2Double(lvRestkapitalEigenString) > 0.0)
                  {
                      BigDecimal lvErgebnis = lvRestkapitalFremd.divide(lvRestkapitalEigen, 9, RoundingMode.HALF_UP).multiply(new BigDecimal("100.0"));
                      ivFinanzgeschaeftDaten.setKonsquo(lvErgebnis.toString());
                  }
                  else
                  {
                      ivFinanzgeschaeftDaten.setKonsquo("0.0");
                  }
              }
            }
         }
                  
         if (lvOkayDarlehen)
         {
             lvOkayDarlehen = ivKondDaten.importDarlehen(pvZielDarlehen, pvLogger);
             ivKondDaten.setLrate("0.01");
             
         }
         if (lvOkayDarlehen)
         {
             lvOkayDarlehen = ivKredkundeKonsorte.importDarlehen(DarlehenVerarbeiten.DARKA, pvZielDarlehen);
             ivKredkundeKonsorte.setRolle("2");
         }
         if (lvOkayDarlehen)
         {
             AktivkontenDaten lvHelpDatenAktiv = ivListeAktivkontenDaten.get(pvZielDarlehen.getKontonummer());
             if (lvHelpDatenAktiv != null)
             {
                 AktivkontenDaten lvHelpDatenKonsortial = ivListeAktivkontenDaten.get(lvHelpDatenAktiv.getKonsortialkontonummer());
                 if (lvHelpDatenKonsortial != null)
                 {
                     ivKredkundeKreditnehmer.setKdnr(lvHelpDatenKonsortial.getKundennummer());                     
                 }
             }
             ivKredkundeKreditnehmer.setOrg(ValueMapping.changeMandant(pvZielDarlehen.getInstitutsnummer()));
             ivKredkundeKreditnehmer.setQuelle("KUNDE");
             ivKredkundeKreditnehmer.setRolle("0");
         }
            
         // Transaktionen in die Datei schreiben
         if (lvOkayDarlehen)
         {
             pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());

             pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
             pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
             pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());

             pvOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
             pvOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
             pvOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());

             pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionStart());
             pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionDaten());
             pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionEnde());

             pvOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionStart());
             pvOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionDaten());
             pvOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionEnde());
         }
              
         if (lvHelpRefiRegisterFilterElement != null)
         {
           //if (lvHelpRefiRegisterFilterElement.getKonsortialHauptkontonummer().equals("0000000000"))
           //{
           //  lvHelpKontonummer = lvHelpRefiRegisterFilterElement.getPassivKontonummer();
           //  System.out.println("PassivKontonummer: " + lvHelpKontonummer);
           //}
           //else
           //{
           //    lvHelpKontonummer = lvHelpRefiRegisterFilterElement.getKonsortialHauptkontonummer();
           //    System.out.println("KonsortialHauptkontonummer: " + lvHelpKontonummer);
           //}

           Sicherungsumfang lvShum = null;
           
           pvLogger.info("Suche SAPCMS SicherheitObjekt zu Kontonummer " + lvHelpKontonummer);
         
           // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
           LinkedList<Sicherungsumfang> lvHelpListe = pvSapcms.getListeSicherungsumfang().get(lvHelpKontonummer);
           
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
                       Sicherheitenvereinbarung lvSicherheitenvereinbarung = pvSapcms.getListeSicherheitenvereinbarung().get(lvShum.getSicherheitenvereinbarungId());
                       String lvSicherheitenId = lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId();
                     //}
                       boolean lvDeckungskennzeichen = false;
                       
                       // Last suchen
                       Last lvLast = null;
                       Collection<Last> lvCollectionLast = pvSapcms.getListeLast().values();
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
                                Collection<Immobilie> lvCollectionImmobilie = pvSapcms.getListeImmobilie().values();
                                Iterator<Immobilie> lvIteratorImmobilie = lvCollectionImmobilie.iterator();
                                while (lvIteratorImmobilie.hasNext())
                                {
                                    Immobilie lvHelpImmobilie = lvIteratorImmobilie.next();
                                    if (lvHelpImmobilie.getId().equals(lvLast.getImmobilieId()))
                                    {
                                        lvImmobilie = lvHelpImmobilie;
                                    }
                                }
                            
                                // Wenn Deckungskennzeichen 'D', 'F' oder 'R' ist, dann Sicherheitenabtretung hinzufuegen
                                  if (lvImmobilie != null)
                                  {
                                    if (lvImmobilie.getDeckungskennzeichen().equals("F") || lvImmobilie.getDeckungskennzeichen().equals("D") || lvImmobilie.getDeckungskennzeichen().equals("R"))
                                    {   
                                        lvDeckungskennzeichen = true;
                                    }
                                  }
                            }
                       }
           
                       if (lvSicherheitenId != null && lvDeckungskennzeichen)
                       {
                           System.out.println("Sicherheit existiert... -> " + lvSicherheitenId);
                           
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
                           RefiZusatz lvRefiZusatz = pvListeRefiZusatz.get(pvZielDarlehen.getKontonummer() + lvSicherheitenId);
                           if (lvRefiZusatz != null)
                           {
                             lvShdatenAbtr.setRgrund(String2XML.change2XML(lvRefiZusatz.getRechtsgrundlage()));
                             lvShdatenAbtr.setRgrunddat(DatumUtilities.changeDate(lvRefiZusatz.getDatumRechtlicherGrund()));
                             lvShdatenAbtr.setDsepsichzerkl(DatumUtilities.changeDate(lvRefiZusatz.getDatumRechtlicherGrund()));                           
                             lvShdatenAbtr.setOrigsichant(lvRefiZusatz.getUmfang());
                             lvShdatenAbtr.setReginfo(String2XML.change2XML(lvRefiZusatz.getBemerkung()));
                           }
                           
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
                           pvLogger.info("RefiListe:" + pvZielDarlehen.getKontonummer() + ";" + ivKredkundeKreditnehmer.getKdnr() + ";" + lvSicherheitenId + ";" + pvZielDarlehen.getKundennummer());
                       }
                       else
                       {
                           System.out.println("Konsortial-Hauptkontonummer - Sicherheit existiert nicht...");
                       }
                 }
               }
           } 
         }

         if (lvOkayDarlehen)
         {
            pvOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
         }   
         
         System.out.println("ivFinanzgeschaeftDaten.getTyp(): " +  ivFinanzgeschaeftDaten.getTyp());
         pvOutputDarlehenXML.printTransaktion(pvSapcms.importSicherheitRefiRegister(lvHelpKontonummer, pvZielDarlehen.getKontonummer(), pvZielDarlehen.getKundennummer(), ivFinanzgeschaeftDaten.getTyp(), pvZielDarlehen.getBuergschaftProzent(), "ADARLREFI", pvZielDarlehen.getAusplatzierungsmerkmal()));
     }
}
