package nlb.txs.schnittstelle.LoanIQ.RefiRegister;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenBlock;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.RefiRegister.RefiDeepSeaZusatz;
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
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;

/**
 * Diese Klasse wurde nur für DeepSea benoetigt.
 */
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
     * Sicherheiten-Daten
     */
    private SicherheitenDaten ivSicherheitenDaten;
    
    /**
     * OutputDarlehenXML
     */
    private OutputDarlehenXML ivOutputDarlehenXML;
    
    /**
     * Logger
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor
     * @param pvLogger
     */
    public RefiRegisterAktiv(SicherheitenDaten pvSicherheitenDaten, OutputDarlehenXML pvOutputDarlehenXML, Logger pvLogger)
    {
    	this.ivSicherheitenDaten = pvSicherheitenDaten;
    	this.ivOutputDarlehenXML = pvOutputDarlehenXML;
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
     * @param pvDarlehenBlock
     * @param pvVorlaufsatz
     * @param pvListeRefiDeepSeaZusatz
     */
     public void importDarlehen2Transaktion(DarlehenBlock pvDarlehenBlock, Vorlaufsatz pvVorlaufsatz, HashMap<String, RefiDeepSeaZusatz> pvListeRefiDeepSeaZusatz)
     {
         ivFinanzgeschaeft.initTXSFinanzgeschaeft();
         ivSliceInDaten.initTXSSliceInDaten();
         ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
         ivKondDaten.initTXSKonditionenDaten();
         ivKredkundeKonsorte.initTXSKreditKunde();
         //ivKredkundeKreditnehmer.initTXSKreditKunde();
            
         boolean lvOkayDarlehen = true;

       lvOkayDarlehen = ivFinanzgeschaeft.importLoanIQ(pvDarlehenBlock, pvVorlaufsatz, ivLogger);
         ivFinanzgeschaeft.setQuelle("ALIQREFI");  
                     
         if (lvOkayDarlehen)
         {
             // TXSFinanzgeschaeftDaten
            lvOkayDarlehen = ivFinanzgeschaeftDaten.importLoanIQ(pvDarlehenBlock, pvVorlaufsatz, ivLogger);

           ////ivFinanzgeschaeftDaten.setKonskredit("1");
            ////ivFinanzgeschaeftDaten.setKonsfuehrer("NORD/LB");
            ////ivFinanzgeschaeftDaten.setKonsantart("FREMD");
              
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
             lvOkayDarlehen = ivKondDaten.importLoanIQ(pvDarlehenBlock, pvVorlaufsatz, ivLogger);
             ivKondDaten.setLrate("0.01");
         }
         if (lvOkayDarlehen)
         {
             lvOkayDarlehen = ivKredkundeKonsorte.importLoanIQ(pvDarlehenBlock, pvVorlaufsatz, ivLogger);
             ////ivKredkundeKonsorte.setRolle("2");
         }

         ivKredkundeKreditnehmer.setKdnr("0080126288");
         ivKredkundeKreditnehmer.setOrg(ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer()));
         ivKredkundeKreditnehmer.setQuelle("KUNDE");
         ivKredkundeKreditnehmer.setRolle("2");

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
           
           ivLogger.info("Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvDarlehenBlock.getDarlehenNetto().getKontonummer());
         
           // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
           LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvDarlehenBlock.getDarlehenNetto().getKontonummer());
           
           if (lvHelpListe != null)
           {
               int x;
               for (x = 0; x < lvHelpListe.size(); x++)
               {
                 lvShum = lvHelpListe.get(x);
                 //System.out.println("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
                 if (lvShum != null)
                 {
                     //if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
                     //{
                       Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSicherheitenDaten.getListeSicherheitenvereinbarung().get(lvShum.getSicherheitenvereinbarungId());
                       String lvSicherheitenId = lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId();
                     //}
                       boolean lvDeckungskennzeichen = false;
                       
                       // Last suchen
                       Last lvLast = null;
                       Collection<Last> lvCollectionLast = ivSicherheitenDaten.getListeLast().values();
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
                                Collection<Immobilie> lvCollectionImmobilie = ivSicherheitenDaten.getListeImmobilie().values();
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
                                Collection<Schiff> lvCollectionSchiff = ivSicherheitenDaten.getListeSchiff().values();
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

                   //String lvHelpKontonummer = new String();
                   //System.out.println("CTCTCT: " + pvDarlehenBlock.getKontonummer() + "_" + lvSicherheitenId);
                   RefiDeepSeaZusatz lvRefiDeepSeaZusatz = pvListeRefiDeepSeaZusatz.get(pvDarlehenBlock.getKontonummer() + "_" + lvSicherheitenId);

                  if (lvRefiDeepSeaZusatz != null) {
                      ivFinanzgeschaeftDaten.setNbetrag(lvRefiDeepSeaZusatz.getNominalbetrag().trim().replace(",", "."));
                      ivFinanzgeschaeftDaten.setAbetrag(lvRefiDeepSeaZusatz.getNominalbetrag().trim().replace(",", "."));
                      ivFinanzgeschaeftDaten.setWhrg(lvRefiDeepSeaZusatz.getNominalbetragWaehrung().trim());
                      ivFinanzgeschaeftDaten.setVwhrg(lvRefiDeepSeaZusatz.getNominalbetragWaehrung().trim());
                      ivFinanzgeschaeftDaten.setText(String2XML.change2XML(lvRefiDeepSeaZusatz.getBemerkung()));
                      //System.out.println("CTCTCT - " + ivFinanzgeschaeftDaten.getWhrg());
                      ivKondDaten.setWhrg(ivFinanzgeschaeftDaten.getWhrg());
                  }

                   // Transaktionen in die Datei schreiben
                   if (lvOkayDarlehen)
                   {
                     ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());

                     ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
                     ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
                     ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());

                     //pvOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSSliceInDatenStart());
                     //pvOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSSliceInDaten());
                     //pvOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSSliceInDatenEnde());

                     ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
                     ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
                     ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());

                     ivOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionStart());
                     ivOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionDaten());
                     ivOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionEnde());

                     ivOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionStart());
                     ivOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionDaten());
                     ivOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionEnde());

                     x = lvHelpListe.size();
                   }

/*
                       ////if (pvVerarbeitungstyp == RefiRegisterVerarbeitung.REFI_REG)
                       ////{
                    	   if (lvSicherheitenId != null && lvDeckungskennzeichen)
                    	   {
                    		   ivLogger.info("Sicherheit existiert... -> " + lvSicherheitenId);
                           
                    		   TXSKreditSicherheit lvKredSh = new TXSKreditSicherheit();
                    		   lvKredSh.setKey(lvSicherheitenId + "_" + pvDarlehenBlock.getDarlehenLoanIQNetto().getKundennummer() + "_" + pvDarlehenBlock.getDarlehenLoanIQNetto().getKontonummer() + "_ABTR");
                    		   lvKredSh.setQuelle("ADARLREFI");
                    		   lvKredSh.setOrg(ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer()));
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
                    		   lvHelpPerson.setKdnr("0080126288"); //pvDarlehenBlock.getDarlehenLoanIQNetto().getKundennummer());
                    		   lvHelpPerson.setOrg(ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer()));
                    		   lvHelpPerson.setQuelle("KUNDE");
                    		   lvHelpPerson.setRolle("2");
                           
                    		   ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionStart());
                    		   ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionDaten());
                    		   ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionStart());
                    		   ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionDaten());
                    		   ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionEnde());
                          
                    		   ivOutputDarlehenXML.printTransaktion(lvHelpPerson.printTXSTransaktionStart());
                    		   ivOutputDarlehenXML.printTransaktion(lvHelpPerson.printTXSTransaktionDaten());
                    		   ivOutputDarlehenXML.printTransaktion(lvHelpPerson.printTXSTransaktionEnde());

                    		   ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionEnde());   
                    		   ivLogger.info("RefiListe:" + pvDarlehenBlock.getKontonummer() + ";" + lvSicherheitenId + ";" + pvDarlehenBlock.getDarlehenLoanIQNetto().getKundennummer());
                    	   }
                    	   else
                    	   {
                    		   ivLogger.info("Konsortial-Hauptkontonummer - Sicherheit existiert nicht...");
                    	   }
                       	////} */
                 }
               }
           } 
         ////}

         
         ////////ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitRefiRegister(pvDarlehenBlock.getKontonummer(), pvDarlehenBlock.getKontonummer(), pvDarlehenBlock.getDarlehenLoanIQNetto().getKundennummer(), 
         ////////		 							                                        "1", //new String("" + ValueMapping.ermittleKredittyp(pvDarlehenBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal(), pvDarlehenBlock.getDarlehenLoanIQNetto().getBuergschaftprozent())), 
         ////////		                                                                    pvDarlehenBlock.getDarlehenLoanIQNetto().getBuergschaftprozent(), "ADARLREFI", 
         ////////		                                                                    pvDarlehenBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal()));
       Sicherheiten2RefiRegister lvSicherheiten2RefiRegister = new Sicherheiten2RefiRegister(ivSicherheitenDaten, ivLogger);

       //ivOutputDarlehenXML.printTransaktion(lvSicherheiten2RefiRegister.importSicherheitRefiRegisterDeepSea(pvDarlehenBlock.getKontonummer(), pvListeRefiDeepSeaZusatz));
           
         if (lvOkayDarlehen)
         {
            ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
         }   
     }
     

}
