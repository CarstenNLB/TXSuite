package nlb.txs.schnittstelle.LoanIQ.RefiRegister;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
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

public class RefiRegisterPassiv 
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
	  * @param pvSicherheitenDaten
	  * @param pvOutputDarlehenXML
	  * @param pvLogger
	  */
	  public RefiRegisterPassiv(SicherheitenDaten pvSicherheitenDaten, OutputDarlehenXML pvOutputDarlehenXML, Logger pvLogger)
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
     * @param pvDarlehen 
     * @param pvVorlaufsatz
     */
     public void importDarlehen2Transaktion(Darlehen pvDarlehen, Vorlaufsatz pvVorlaufsatz)
     {
         ivFinanzgeschaeft.initTXSFinanzgeschaeft();
         ivSliceInDaten.initTXSSliceInDaten();
         ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
         ivKondDaten.initTXSKonditionenDaten();
         ivKredkundeKonsorte.initTXSKreditKunde();
         ivKredkundeKreditnehmer.initTXSKreditKunde();
            
         boolean lvOkayDarlehen = true;
         
         //RefiZusatz lvHelpRefiZusatz = null;
         ////for (RefiDeepSeaZusatz lvHelpRefiZusatz:pvListeRefiDeepSeaZusatz.values())
         ////{
         //Collection<RefiZusatz> lvHelpRefiZusatzCollection = ivListeRefiZusatz.values();
         //Iterator<RefiZusatz> lvHelpRefiZusatzIter = lvHelpRefiZusatzCollection.iterator();
         //while (lvHelpRefiZusatzIter.hasNext())
         //{
             //lvHelpRefiZusatz = lvHelpRefiZusatzIter.next();
             //System.out.println(lvHelpRefiZusatz.getPassivkontonummer() + " == " + pvZielDarlehen.getKontonummer());
             //if (lvHelpRefiZusatz.getPassivkontonummer().equals(pvDarlehen.getKontonummer()))
             //{
             //    System.out.println("Gleich...");
             //    break;
             //}
             //else
             //{
           ////      lvHelpRefiZusatz = null;
             //}
         ////}
         
         //String lvHelpKontonummer = new String();
         
         // TXSFinanzgeschaeft fuellen
         ivFinanzgeschaeft.setKey(pvDarlehen.getKontonummer() + "_" + pvDarlehen.getCustomerID());
         ivFinanzgeschaeft.setOriginator(ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer()));
         ivFinanzgeschaeft.setQuelle("ALIQREFI");  
                     
         if (lvOkayDarlehen)
         {
             // TXSFinanzgeschaeftDaten fuellen
             lvOkayDarlehen = ivFinanzgeschaeftDaten.importLoanIQRefiRegister(pvDarlehen, ivLogger);
             ivFinanzgeschaeftDaten.setKonskredit("1");
             ivFinanzgeschaeftDaten.setKonsfuehrer("NORD/LB");
             ivFinanzgeschaeftDaten.setKonsantart("FREMD");
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
             lvOkayDarlehen = ivKondDaten.importLoanIQRefiRegister(pvDarlehen, ivLogger);
             ivKondDaten.setLrate("0.01");
             
         }
         if (lvOkayDarlehen)
         {
             //lvOkayDarlehen = ivKredkundeKonsorte.importDarlehen(DarlehenVerarbeiten.DARKA, pvDarlehen);
             ivKredkundeKonsorte.setKdnr(pvDarlehen.getCustomerID());
             ivKredkundeKonsorte.setOrg(ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer()));
             ivKredkundeKonsorte.setQuelle("KUNDE");
             ivKredkundeKonsorte.setRolle("2");
         }
         if (lvOkayDarlehen)
         {
         ////    AktivkontenDaten lvHelpDatenAktiv = ivListeAktivkontenDaten.get(pvZielDarlehen.getKontonummer());
         ////    if (lvHelpDatenAktiv != null)
         ////    {
         ////        AktivkontenDaten lvHelpDatenKonsortial = ivListeAktivkontenDaten.get(lvHelpDatenAktiv.getKonsortialkontonummer());
         ////        if (lvHelpDatenKonsortial != null)
         ////        {
         ////            ivKredkundeKreditnehmer.setKdnr(lvHelpDatenKonsortial.getKundennummer());                     
         ////        }
         ////    }
        	 ivKredkundeKreditnehmer.setKdnr(pvDarlehen.getKundennummer());
        	 ivKredkundeKreditnehmer.setOrg(ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer()));
        	 ivKredkundeKreditnehmer.setQuelle("KUNDE");
        	 ivKredkundeKreditnehmer.setRolle("0");
         }
            
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

             ivOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionStart());
             ivOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionDaten());
             ivOutputDarlehenXML.printTransaktion(ivKredkundeKreditnehmer.printTXSTransaktionEnde());

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
           
           ivLogger.info("Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvDarlehen.getKontonummer());
         
           // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
           LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvDarlehen.getKontonummer());
           
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
           
                    	   if (lvSicherheitenId != null && lvDeckungskennzeichen)
                    	   {
                    		   ivLogger.info("Sicherheit existiert... -> " + lvSicherheitenId);
                               ivLogger.info("Kredsh-Key: " + lvSicherheitenId + "_" + pvDarlehen.getCustomerID() + "_" + pvDarlehen.getKontonummer() + "_ABTR");
                    		   TXSKreditSicherheit lvKredSh = new TXSKreditSicherheit();
                    		   lvKredSh.setKey(lvSicherheitenId + "_" + pvDarlehen.getCustomerID() + "_" + pvDarlehen.getKontonummer() + "_ABTR");
                    		   lvKredSh.setQuelle("ALIQREFI");
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
                    		   lvHelpPerson.setKdnr(pvDarlehen.getCustomerID());
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
                    		   ivLogger.info("RefiListe:" + pvDarlehen.getKontonummer() + ";" + lvSicherheitenId + ";" + pvDarlehen.getKundennummer());
                    	   }
                    	   else
                    	   {
                    		   ivLogger.info("Konsortial-Hauptkontonummer - Sicherheit existiert nicht...");
                    	   }
                       	}
                 	
               }
           } 
         ////}

         Sicherheiten2RefiRegister lvSicherheiten2RefiRegister = new Sicherheiten2RefiRegister(ivSicherheitenDaten, ivLogger);
         ivOutputDarlehenXML.printTransaktion(lvSicherheiten2RefiRegister.importSicherheitHypotheken(pvDarlehen.getKontonummer(), pvDarlehen.getKontonummer(), pvDarlehen.getCustomerID(), "1", new String(), "ALIQREFI", pvVorlaufsatz.getInstitutsnummer(), null));//pvDarlehen.getKredittyp(), pvDarlehen.getBuergschaftProzent()));

         if (lvOkayDarlehen)
         {
            ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
         }   
     }

}
