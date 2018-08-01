package nlb.txs.schnittstelle.LoanIQ.RefiRegister;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQBlock;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.SAPCMS.SAPCMS_Neu;
import nlb.txs.schnittstelle.SAPCMS.Entities.Immobilie;
import nlb.txs.schnittstelle.SAPCMS.Entities.Last;
import nlb.txs.schnittstelle.SAPCMS.Entities.Schiff;
import nlb.txs.schnittstelle.SAPCMS.Entities.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.SAPCMS.Entities.Sicherungsumfang;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;

import org.apache.log4j.Logger;

public class RefiRegisterAktiv
{
    // Transaktionen
    private TXSFinanzgeschaeft ivFinanzgeschaeft;
    private TXSSliceInDaten ivSliceInDaten;
    private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
    private TXSKonditionenDaten ivKondDaten;
    private TXSKreditKunde ivKredkundeKonsorte;
    //private TXSKreditKunde ivKredkundeKreditnehmer;

    /**
     * SAP CMS
     */
    private SAPCMS_Neu ivSapcms;
    
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
    public RefiRegisterAktiv(SAPCMS_Neu pvSapcms, OutputDarlehenXML pvOutputDarlehenXML, Logger pvLogger) 
    {
    	this.ivSapcms = pvSapcms;
    	this.ivOutputDarlehenXML = pvOutputDarlehenXML;
        this.ivLogger = pvLogger;
             
        this.ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        this.ivSliceInDaten = new TXSSliceInDaten();
        this.ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        this.ivKondDaten = new TXSKonditionenDaten();
        this.ivKredkundeKonsorte = new TXSKreditKunde();
        //this.ivKredkundeKreditnehmer = new TXSKreditKunde();
    }
    
    /**
     * Importiert die Darlehensinformationen in die TXS-Transaktionen
     * @param pvZielDarlehen 
     * @param pvVorlaufsatz 
     */
     public void importDarlehen2Transaktion(DarlehenLoanIQBlock pvDarlehenBlock, Vorlaufsatz pvVorlaufsatz)
     {
         ivFinanzgeschaeft.initTXSFinanzgeschaeft();
         ivSliceInDaten.initTXSSliceInDaten();
         ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
         ivKondDaten.initTXSKonditionenDaten();
         ivKredkundeKonsorte.initTXSKreditKunde();
         //ivKredkundeKreditnehmer.initTXSKreditKunde();
            
         boolean lvOkayDarlehen = true;
         
         //String lvHelpKontonummer = new String();
         
         lvOkayDarlehen = ivFinanzgeschaeft.importLoanIQ(pvDarlehenBlock, pvVorlaufsatz, ivLogger);
         ivFinanzgeschaeft.setQuelle("ADARLREFI");  
                     
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

             //pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionStart());
             //pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionDaten());
             //pvOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionEnde());

             ivOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionStart());
             ivOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionDaten());
             ivOutputDarlehenXML.printTransaktion(ivKredkundeKonsorte.printTXSTransaktionEnde());
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
           
           ivLogger.info("Suche SAPCMS SicherheitObjekt zu Kontonummer " + pvDarlehenBlock.getDarlehenLoanIQNetto().getKontonummer());
         
           // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
           LinkedList<Sicherungsumfang> lvHelpListe = ivSapcms.getListeSicherungsumfang().get(pvDarlehenBlock.getDarlehenLoanIQNetto().getKontonummer());
           
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
                       Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSapcms.getListeSicherheitenvereinbarung().get(lvShum.getSicherheitenvereinbarungId());
                       String lvSicherheitenId = lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId();
                     //}
                       boolean lvDeckungskennzeichen = false;
                       
                       // Last suchen
                       Last lvLast = null;
                       Collection<Last> lvCollectionLast = ivSapcms.getListeLast().values();
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
                                Collection<Immobilie> lvCollectionImmobilie = ivSapcms.getListeImmobilie().values();
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
                                Collection<Schiff> lvCollectionSchiff = ivSapcms.getListeSchiff().values();
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
                    		   lvHelpPerson.setKdnr(pvDarlehenBlock.getDarlehenLoanIQNetto().getKundennummer());
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

         
         ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitRefiRegister(pvDarlehenBlock.getKontonummer(), pvDarlehenBlock.getKontonummer(), pvDarlehenBlock.getDarlehenLoanIQNetto().getKundennummer(), 
        		                                                                    "1", //new String("" + ValueMapping.ermittleKredittyp(pvDarlehenBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal(), pvDarlehenBlock.getDarlehenLoanIQNetto().getBuergschaftprozent())), 
        		                                                                    pvDarlehenBlock.getDarlehenLoanIQNetto().getBuergschaftprozent(), "ADARLREFI", 
        		                                                                    pvDarlehenBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal()));

         if (lvOkayDarlehen)
         {
            ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
         }   
     }
     

}
