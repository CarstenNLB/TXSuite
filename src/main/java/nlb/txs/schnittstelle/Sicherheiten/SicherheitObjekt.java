/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetrag;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetragListe;
import nlb.txs.schnittstelle.Sicherheiten.Daten.Bestandsverzeichnis;
import nlb.txs.schnittstelle.Sicherheiten.Daten.Geschaeftswert;
import nlb.txs.schnittstelle.Sicherheiten.Daten.Grundbucheintrag;
import nlb.txs.schnittstelle.Sicherheiten.Daten.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.Sicherheiten.Daten.Vermoegensobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSBestandsverzDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisBestandsverz;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
@Deprecated
public class SicherheitObjekt 
{
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Gesch�ftswert
     */
    private Geschaeftswert ivGeschaeftswert;
    
    /**
     * Liste der Sicherheitenvereinbarungen;
     */
    private Sicherheitenvereinbarung ivSicherheitenvereinbarung;
    
    /**
     * Liste der Vermoegensobjekt
     */
    private LinkedList<Vermoegensobjekt> ivListeVermoegensobjekte;
    
    /**
     * Liste der Grundbucheintraege
     */
    private LinkedList<Grundbucheintrag> ivListeGrundbucheintraege;
    
    /**
     * 
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor
     * @param pvGw 
     * @param pvShv 
     * @param pvListeVObj 
     * @param pvListeGBs 
     * @param pvLogger 
     */
    public SicherheitObjekt(Geschaeftswert pvGw, Sicherheitenvereinbarung pvShv,
                            LinkedList<Vermoegensobjekt> pvListeVObj, LinkedList<Grundbucheintrag> pvListeGBs,
                            Logger pvLogger)
    {
        this.ivInstitutsnummer = new String();
        this.ivLogger = pvLogger;
        
        this.ivGeschaeftswert = pvGw;
        this.ivSicherheitenvereinbarung = pvShv;
        if (pvListeVObj == null)
        {
           ivListeVermoegensobjekte = new LinkedList<Vermoegensobjekt>(); 
        }
        else
        {
            this.ivListeVermoegensobjekte = pvListeVObj;
        }
         if (pvListeGBs == null)
        {
            ivListeGrundbucheintraege = new LinkedList<Grundbucheintrag>(); 
        }
        else
        {
            this.ivListeGrundbucheintraege = pvListeGBs;
        }
    }

    /**
     * @return the institutsnummer
     */
    public String getInstitutsnummer() {
        return this.ivInstitutsnummer;
    }

    /**
     * @param pvInstitutsnummer the institutsnummer to set
     */
    public void setInstitutsnummer(String pvInstitutsnummer) {
        this.ivInstitutsnummer = pvInstitutsnummer;
    }

    /**
     * @return the geschaeftswert
     */
    public Geschaeftswert getGeschaeftswert() {
        return this.ivGeschaeftswert;
    }

    /**
     * @param pvGeschaeftswert the geschaeftswert to set
     */
    public void setGeschaeftswert(Geschaeftswert pvGeschaeftswert) {
        this.ivGeschaeftswert = pvGeschaeftswert;
    }

    /**
     * @return the sicherheitenvereinbarung
     */
    public Sicherheitenvereinbarung getSicherheitenvereinbarung() {
        return this.ivSicherheitenvereinbarung;
    }

    /**
     * @param pvSicherheitenvereinbarung the sicherheitenvereinbarung to set
     */
    public void setSicherheitenvereinbarung(Sicherheitenvereinbarung pvSicherheitenvereinbarung) {
        this.ivSicherheitenvereinbarung = pvSicherheitenvereinbarung;
    }

    /**
     * @return the listeVermoegensobjekte
     */
    public LinkedList<Vermoegensobjekt> getListeVermoegensobjekte() {
        return this.ivListeVermoegensobjekte;
    }

    /**
     * @param pvListeVermoegensobjekte the listeVermoegensobjekte to set
     */
    public void setListeVermoegensobjekte(LinkedList<Vermoegensobjekt> pvListeVermoegensobjekte) {
        this.ivListeVermoegensobjekte = pvListeVermoegensobjekte;
    }

    /**
     * @return the listeGrundbucheintraege
     */
    public LinkedList<Grundbucheintrag> getListeGrundbucheintraege() {
        return this.ivListeGrundbucheintraege;
    }

    /**
     * @param pvListeGrundbucheintraege the listeGrundbucheintraege to set
     */
    public void setListeGrundbucheintraege(LinkedList<Grundbucheintrag> pvListeGrundbucheintraege) {
        this.ivListeGrundbucheintraege = pvListeGrundbucheintraege;
    }

    /**
     * @return 
     * 
     */
    public String toString()
    {
        String lvOut = new String();
        if (ivGeschaeftswert != null)
          lvOut = lvOut + ivGeschaeftswert.toString();
        
        if (ivSicherheitenvereinbarung != null)
          lvOut = lvOut + ivSicherheitenvereinbarung.toString();
        
        lvOut = lvOut + ivListeVermoegensobjekte.toString();
        lvOut = lvOut + ivListeGrundbucheintraege.toString();

        return lvOut;
    }

    /**
     * @param pvKredittyp 
     * @param pvBuergschaftprozent 
     * @param pvOzwListe  
     * @return 
     * 
     */
    public StringBuffer importSicherheitObjekt2TXSKreditSicherheit(String pvKredittyp, String pvBuergschaftprozent, ObjektZuweisungsbetragListe pvOzwListe, String pvDeckungskennzeichen)//, FileOutputStream pvFosIDs) 
    {
       BigDecimal lvBtrDivHd = new BigDecimal("0.01");
       StringBuffer lvHelpString = new StringBuffer(); 
       //System.out.println("importSicherheitObjekt2TXSKreditSicherheit");
       TXSKreditSicherheit lvKredsh = null;
       TXSSicherheitDaten lvShdaten = null;
       TXSSicherheitVerzeichnis lvShve = null;
       TXSVerzeichnisDaten lvVedaten = null;
       TXSVerzeichnisVBlatt lvVevb = null;
       TXSVerzeichnisblattDaten lvVbdaten = null;
       TXSVerzeichnisBestandsverz lvVebv = null;
       TXSBestandsverzDaten lvBvdaten = null;
       TXSVerzeichnisPfandobjekt lvVepo = null;
       TXSPfandobjektDaten lvPodaten = null;
       Vermoegensobjekt lvVermoegensObj = null;
       Grundbucheintrag lvGbe = null;
       
       for (int x = 0; x < ivListeVermoegensobjekte.size(); x++)
       {
         lvVermoegensObj = ivListeVermoegensobjekte.get(x);
         
         //System.out.println("vObj-ID: " + vObj.getObjektID());
         
         //BigDecimal dWork = new BigDecimal("0.0");
         BigDecimal lvBuerge_Fakt = new BigDecimal("1.0");
         BigDecimal lvKonEigenUKAP_Fakt = new BigDecimal("1.0");
         BigDecimal lvKonEigenRKAP_Fakt = new BigDecimal("1.0");
         if (pvKredittyp.equals("4"))
         { /* mit B�rge .. anteilig */
          if (StringKonverter.convertString2Double(pvBuergschaftprozent) != 0.0)
          { /* nichts da */
           lvBuerge_Fakt = lvBtrDivHd.multiply(StringKonverter.convertString2BigDecimal(pvBuergschaftprozent));
          } /* etwas da */
         } /* mit B�rge .. anteilig */
         // CT 23.08.2012 - Der Konsortial-Faktor darf bei CMS nicht mehr ber�cksichtigt werden
         /* Konsortiale ..... Summe der anderen, nur bei korrektem Schl.... */
         //if (StringKonverter.convertString2Int(darlehen.getKompensationsschluessel()) > 0 &&
         //        StringKonverter.convertString2Int(darlehen.getKompensationsschluessel()) < 20)
         //{ /* Bedeutet Kons.*/
         // if (StringKonverter.convertString2Double(darlehen.getUrsprungsKapital()) != 0 )
         // { /* Anteil rechnen */
         //  dWork = StringKonverter.convertString2BigDecimal(darlehen.getUrsprungsKapital()).subtract(StringKonverter.convertString2BigDecimal(darlehen.getSummeUKAP()));
         //  dKonEigenUKAP_Fakt = dBuerge_Fakt.multiply(
         //                      (dWork.divide(StringKonverter.convertString2BigDecimal(darlehen.getUrsprungsKapital()), 9, RoundingMode.HALF_UP))); /* Max. 1 */
         // } /* Anteil rechnen */
         // if (StringKonverter.convertString2Double(darlehen.getRestkapital()) != 0 )
         // { /* Anteil rechnen */
         //  dWork = StringKonverter.convertString2BigDecimal(darlehen.getRestkapital()).subtract(StringKonverter.convertString2BigDecimal(darlehen.getSummeRKAP()));
         //  dKonEigenRKAP_Fakt = dBuerge_Fakt.multiply(
         //                      (dWork.divide(StringKonverter.convertString2BigDecimal(darlehen.getRestkapital()), 9, RoundingMode.HALF_UP))); /* Max. 1 */
         // } /* Anteil rechnen */
         //} /* Bedeutet Kons.*/
         //else
         //{ /* Nicht konsortial */
          lvKonEigenUKAP_Fakt = lvBuerge_Fakt;
          lvKonEigenRKAP_Fakt = lvBuerge_Fakt;
         //} /* Nicht konsortial */

         /* dKonEigenUKAP_Fakt wird vom dKonEigenRKAP_Fakt gesetzt ! */
         lvKonEigenUKAP_Fakt = lvKonEigenRKAP_Fakt;
         
         BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(ivGeschaeftswert.getZuweisungsbetrag()).multiply(lvKonEigenUKAP_Fakt);
         BigDecimal lvVerfBetrag = StringKonverter.convertString2BigDecimal(ivSicherheitenvereinbarung.getVerfuegungsbetrag()).multiply(lvKonEigenUKAP_Fakt);
         
         //System.out.println("ZW-Betrag: " + geschaeftswert.getZuweisungsbetrag());
         //System.out.println("VF-Betrag: " + sicherheitenvereinbarung.getVerfuegungsbetrag());
         //System.out.println("SicherheitObjekt: dZuwBetrag: " + dZuwBetrag + " dVerfBetrag:" + dVerfBetrag);
         //System.out.println("UKAP_Fakt: " + dKonEigenUKAP_Fakt);
         
         //sicherheitenvereinbarung.setVerfuegungsbetrag(dVerfBetrag.toString());
         //geschaeftswert.setZuweisungsbetrag(dZuwBetrag.toString());
         
         BigDecimal lvVerfBetragBu = new BigDecimal("0.0");
         BigDecimal lvVerfBetragRe = new BigDecimal("0.0");
         BigDecimal lvZuwBetragBu = new BigDecimal("0.0");
         BigDecimal lvZuwBetragRe = new BigDecimal("0.0");
         if (pvKredittyp.equals("1"))
         { /* ohne B�rge .. alles */
          //dVerfBetragRe = StringKonverter.convertString2BigDecimal(sicherheitenvereinbarung.getVerfuegungsbetrag());
          //dZuwBetragRe  = StringKonverter.convertString2BigDecimal(geschaeftswert.getZuweisungsbetrag());
          lvVerfBetragRe = lvVerfBetrag;
          lvZuwBetragRe  = lvZuwBetrag;
          //System.out.println("CT: " + dZuwBetragRe.toString());
         } /* ohne B�rge .. alles */
         if (pvKredittyp.equals("2"))
         { /* mit B�rge .. anteilig */
          if (StringKonverter.convertString2Double(pvBuergschaftprozent) == 0.0)
          { /* nichts da */
           //dVerfBetragRe = StringKonverter.convertString2BigDecimal(sicherheitenvereinbarung.getVerfuegungsbetrag());
           //dZuwBetragRe  = StringKonverter.convertString2BigDecimal(geschaeftswert.getZuweisungsbetrag());
           lvVerfBetragRe = lvVerfBetrag;
           lvZuwBetragRe  = lvZuwBetrag;
          } /* nichts da */
          else
          { /* etwas da */
             //dVerfBetragBu = (StringKonverter.convertString2BigDecimal(sicherheitenvereinbarung.getVerfuegungsbetrag()).multiply(dBtrDivHd).
             //                 multiply(StringKonverter.convertString2BigDecimal(darlehen.getBuergschaftProzent())));
             //dVerfBetragRe = StringKonverter.convertString2BigDecimal(sicherheitenvereinbarung.getVerfuegungsbetrag()).subtract(dVerfBetragBu);
             //dZuwBetragBu  = (StringKonverter.convertString2BigDecimal(geschaeftswert.getZuweisungsbetrag()).multiply(dBtrDivHd).
             //                multiply(StringKonverter.convertString2BigDecimal(darlehen.getBuergschaftProzent())));
             //dZuwBetragRe  = StringKonverter.convertString2BigDecimal(geschaeftswert.getZuweisungsbetrag()).subtract(dZuwBetragBu);
             lvVerfBetragBu = lvVerfBetrag.multiply(lvBtrDivHd).
                             multiply(StringKonverter.convertString2BigDecimal(pvBuergschaftprozent));
             lvVerfBetragRe = lvVerfBetrag.subtract(lvVerfBetragBu);
             lvZuwBetragBu  = lvZuwBetrag.multiply(lvBtrDivHd).
                             multiply(StringKonverter.convertString2BigDecimal(pvBuergschaftprozent));
             lvZuwBetragRe  = lvZuwBetrag.subtract(lvZuwBetragBu);

          } /* etwas da */
         } /* mit B�rge .. anteilig */
         
         //System.out.println("dVerfBetragBu = " + dVerfBetragBu);
         //System.out.println("dVerfBetragRe = " + dVerfBetragRe);
         //System.out.println("dZuwBetragBu = " + dZuwBetragBu);
         //System.out.println("dZuwBetragRe = " + dZuwBetragRe);
          
         // TXSKreditSicherheit
         lvKredsh = new TXSKreditSicherheit();
         lvKredsh.setKey(ivGeschaeftswert.getSicherheitenID()); //+ "_" + vObj.getObjektID());
         lvKredsh.setOrg(ValueMapping.changeMandant(ivInstitutsnummer));
         lvKredsh.setQuelle("ADARLPFBG");
         //System.out.println("Kredittyp: " + darlehen.getKredittyp());
         if (pvKredittyp.equals("2"))
         {
             lvKredsh.setZuwbetrag(lvZuwBetragBu.toString());             
         }
         else
         {
             lvKredsh.setZuwbetrag(lvZuwBetragRe.toString());            
         }
         lvKredsh.setWhrg(ivGeschaeftswert.getZuweisungsbetragWaehrung());
      
         // TXSSicherheitDaten
         lvShdaten = new TXSSicherheitDaten();

         lvShdaten.setArt(ValueMapping.mapSicherheitenArt(lvVermoegensObj, ivSicherheitenvereinbarung));
         
         lvShdaten.setNbetrag(ivSicherheitenvereinbarung.getNominalwert());
         
         // BuergschaftProzent nur bei verbuergt kommunalen Darlehen setzen - CT 27.06.2012
         //if (darlehen.getKredittyp().equals("D") || darlehen.getKredittyp().equals("B"))
         //{
         //  if (StringKonverter.convertString2Double(darlehen.getBuergschaftProzent()) != 0.0)
         //  {
         //    shdaten.setPbsatz(darlehen.getBuergschaftProzent());
         //  }
         //}
  
         if (pvKredittyp.equals("2"))
         {
             lvShdaten.setVbetrag(lvVerfBetragBu.toString());       
         }
         else
         {
           lvShdaten.setVbetrag(lvVerfBetragRe.toString());
         }
         lvShdaten.setWhrg(ivSicherheitenvereinbarung.getNominalwertWaehrung());
 
         // TXSKreditSicherheit ausgeben
         lvHelpString.append(lvKredsh.printTXSTransaktionStart());
         lvHelpString.append(lvKredsh.printTXSTransaktionDaten());
         lvHelpString.append(lvShdaten.printTXSTransaktionStart());
         lvHelpString.append(lvShdaten.printTXSTransaktionDaten());
         lvHelpString.append(lvShdaten.printTXSTransaktionEnde());

         // Grundpfandr. Auslandsimmo -> Kein Grundbucheintrag
         if (lvShdaten.getArt().equalsIgnoreCase("19"))
         {
             lvShve = new TXSSicherheitVerzeichnis();
             // Pfandobjekt-Nr nehmen, da kein Grundbuch vorhanden!!!
             // CT 06.06.2012 - Wegen Eindeutigkeit die Sicherheiten-ID davor schreiben
             lvShve.setVenr(lvVermoegensObj.getSicherheitenID() + "_" + lvVermoegensObj.getObjektID());
             lvShve.setOrg(ValueMapping.changeMandant(ivInstitutsnummer));
             lvShve.setQuelle("ADARLPFBG");
             
             lvHelpString.append(lvShve.printTXSTransaktionStart());
             lvHelpString.append(lvShve.printTXSTransaktionDaten());
             
             // TXSVerzeichnisDaten
             lvVedaten = new TXSVerzeichnisDaten();
             //vedaten.setAbt("3");
             lvVedaten.setGbart("100");
             lvVedaten.setAsichr(lvVermoegensObj.getBemerkung());
             //vedaten.setKat("1");
             //vedaten.setNrabt(gbe.getLfdNr());
             //vedaten.setBetrag(dZuwBetragRe.toString());
             // CT 28.11.2011 - Umgestellt auf Verfuegungsbertrag
             //if (darlehen.getKredittyp().equals("B"))
             //{
             //    vedaten.setBetrag(dVerfBetragBu.toString());       
             //}
             //else
             //{
             //    vedaten.setBetrag(dVerfBetragRe.toString());
             //}
             if (ivSicherheitenvereinbarung.getGesamtgrundschuld().equals("X"))
             {
                 lvVedaten.setBetrag(lvVermoegensObj.getVerfuegungsbetrag());
             }
             else
             {
                 ObjektZuweisungsbetrag lvOzw = pvOzwListe.getObjektZuweisungsbetrag(ivGeschaeftswert.getSicherheitenID());
                 if (lvOzw != null)
                 {
                     lvVedaten.setBetrag((lvOzw.getZuweisungsbetrag().multiply(lvKonEigenUKAP_Fakt)).toString());
                 }
             }
             lvVedaten.setWhrg(ivGeschaeftswert.getZuweisungsbetragWaehrung());

             lvHelpString.append(lvVedaten.printTXSTransaktionStart());
             lvHelpString.append(lvVedaten.printTXSTransaktionDaten());
             lvHelpString.append(lvVedaten.printTXSTransaktionEnde());

             // TXSVerzeichnisPfandobjekt
             lvVepo = new TXSVerzeichnisPfandobjekt();
             lvVepo.setObjnr(lvVermoegensObj.getObjektID());
             lvVepo.setOrg(ValueMapping.changeMandant(ivInstitutsnummer));
             lvVepo.setQuelle("ADARLPFBG");
             lvHelpString.append(lvVepo.printTXSTransaktionStart());
             lvHelpString.append(lvVepo.printTXSTransaktionDaten());
             // TXSPfandobjektDaten
             lvPodaten = new TXSPfandobjektDaten();
             
             /* => TXX404/405 best�cken.....nur bei hypothekar.... */
             /* geht nur mit Bauf bzw. iSIO   ..... */
             /* Best�ckung von Pfandobjekten etc... */
             if (lvVermoegensObj != null)
             { /* Es ist etwas da .... */
               /* Belegung aus SI/SIO-Strukturen */
               /* Einzelne Felder ermitteln ... */
               String lvLand = new String();
               String lvStaat = new String();
               String lvEigentumsTyp = new String();

               lvStaat = ValueMapping.mapLand(lvVermoegensObj.getLand());
               if (lvStaat.isEmpty())
               { /* Default */
                 lvStaat = "100";
               }
               //System.out.println("sStaat: " + sStaat);
               /* Sonderf�lle LandID umsetzen..bspw. Deutschland */
              lvLand = ValueMapping.mapGebiete(lvStaat, lvVermoegensObj.getGebiet());
              /* Pfandobjektgruppe / Pfandobjektnutzungsart / Eigentumstyp */
              //System.out.println("sLand: " + sLand);
              
              /* Eigentumstyp aktuell gar nicht zu mappen !! */
              String lvPfGruppe = new String();
              String lvPfArt = new String();

              lvPfGruppe = ValueMapping.mapPfandObjektGruppe(lvVermoegensObj);
              lvPfArt = ValueMapping.mapNutzungsart(lvVermoegensObj);
              
              // Ertragsfaehigkeit - CT 09.07.2012
              // Defaultm�ssig '3' - voll ertragsfaehig
              String lvErtragsKz = "3";
              // Bauplatz
              if (lvPfArt.startsWith("4"))
              {
                  lvErtragsKz = "2";
              }
              // Baugeld
              if (lvPfArt.startsWith("7"))
              {
                lvErtragsKz = "1";
              }

              lvEigentumsTyp = ValueMapping.mapSP_Eigentumstyp(lvVermoegensObj);
             
              // CT 26.03.2012 - Altes Mapping 
             //podaten.setEigtyp(ValueMapping.changeEigentumstyp(sEigentumsTyp));
             lvPodaten.setEigtyp(ValueMapping.mapEigentumstyp(lvVermoegensObj.getNutzung()));
    
             lvPodaten.setErtragsf(lvErtragsKz);             
             
             //alte Version: podaten.setGebiet(ValueMapping.changeGebiet(sLand));
             // CT - 09.07.2012
             if (!lvVermoegensObj.getGebiet().isEmpty())
             {
               lvPodaten.setGebiet(lvVermoegensObj.getLand() + "_" + lvVermoegensObj.getGebiet());
             }
             
             if (lvVermoegensObj.getFertigstellungsdatum().length() > 4)
                lvPodaten.setJahr(ValueMapping.changeBaujahr(DatumUtilities.changeDatePoints(lvVermoegensObj.getFertigstellungsdatum())));
             lvPodaten.setKat("1");
             lvPodaten.setLand(ValueMapping.changeLand(lvStaat));
             lvPodaten.setSwert(lvVermoegensObj.getSachwert());
             lvPodaten.setEwert(lvVermoegensObj.getErtragswert());
             lvPodaten.setBwert(lvVermoegensObj.getBeleihungswert());
             lvPodaten.setNart(lvPfArt);

             // CT 26.03.2012 - Altes Mapping
             //podaten.setOgrp(ValueMapping.changeObjektgruppe(sPfGruppe));
             lvPodaten.setOgrp(lvPfGruppe);

             lvPodaten.setOrt(lvVermoegensObj.getOrt());
             lvPodaten.setPlz(lvVermoegensObj.getPostleitzahl());
             lvPodaten.setSpeigtyp(lvEigentumsTyp);
             lvPodaten.setStr(lvVermoegensObj.getStrasse());
             // Hausnummer in ein eigenes Feld - CT 25.11.2011
             lvPodaten.setHausnr(lvVermoegensObj.getHausnummer());
             lvPodaten.setWhrg(lvVermoegensObj.getBeleihungswertWaehrung());
             }
             
             lvHelpString.append(lvPodaten.printTXSTransaktionStart());
             lvHelpString.append(lvPodaten.printTXSTransaktionDaten());
             lvHelpString.append(lvPodaten.printTXSTransaktionEnde());
             
             lvHelpString.append(lvVepo.printTXSTransaktionEnde());
             lvHelpString.append(lvShve.printTXSTransaktionEnde());
         }
         
         // TXSSicherheitVerzeichnis
         //System.out.println("Anzahl Grundbucheintraege: " + listeGrundbucheintraege.size());
         for (int y = 0; y < ivListeGrundbucheintraege.size(); y++)
         {
           lvGbe = ivListeGrundbucheintraege.get(y);
           if (lvGbe.getObjektID().equals(lvVermoegensObj.getObjektID()))
           {
             // Logging SAP CMS - CT 13.11.2014
             // Wenn Grundbucheintrag mehrmals in der Liste der Grundbucheintraege, dann in Logdatei schreiben
             int lvHelpAnzahl = 0;
             for (int z = 0; z < ivListeGrundbucheintraege.size(); z++)
             {
                 Grundbucheintrag lvHelpGbe = ivListeGrundbucheintraege.get(z);
                 if (lvHelpGbe.getObjektID().equals(lvGbe.getObjektID()))
                 {
                     lvHelpAnzahl++;
                 }
             }
             if (lvHelpAnzahl > 1 && ivListeGrundbucheintraege.size() > 1)
             {
                 String lvHelpArt = "";
                 if (lvShdaten.getArt().equals("11"))
                     lvHelpArt = "Briefgrundschuld";
                 if (lvShdaten.getArt().equals("12"))
                     lvHelpArt = "Buchgrundschuld";
                 if (lvShdaten.getArt().equals("15"))
                     lvHelpArt = "GesamtBriefgrundschuld";
                 if (lvShdaten.getArt().equals("16"))
                     lvHelpArt = "GesamtBuchgrundschuld";
                 ivLogger.info("Sicherheiten;" + ivGeschaeftswert.getGeschaeftswertID() + ";" + ivGeschaeftswert.getSicherheitenID() + ";" + lvGbe.getObjektID() + ";" + lvGbe.getLfdNr() + ";" + lvHelpArt + ";" + pvDeckungskennzeichen);
             }
             // Logging SAP CMS - CT 13.11.2014
             
             lvShve = new TXSSicherheitVerzeichnis();
             // CT 31.01.2012 venr ver�ndert 
             // <Vermoegensobjekt-ID><Band><Blatt><lfd-Nr Abt3>
             lvShve.setVenr(lvGbe.getObjektID() + lvGbe.getBand() + lvGbe.getBlatt() + lvGbe.getLfdNr());
             // alte: shve.setVenr(gbe.getGrundbuchID());
             if (lvShve.getVenr().length() > 40)
             {
                 lvShve.setVenr(lvShve.getVenr().substring(0, 39));
             }
             // CT 14.02.2012
             //try
             //{
             //  pvFosIDs.write(("shve;" + lvGbe.getGrundbuchID() + ";" + lvShve.getVenr() + ";" + StringKonverter.lineSeparator).getBytes());
             //}
             //catch (Exception e)
             //{
             //    System.out.println("Fehler bei shve-ID...");
             //}
             // CT 14.02.2012
               
             lvShve.setOrg(ValueMapping.changeMandant(ivInstitutsnummer));
             lvShve.setQuelle("ADARLPFBG");
             lvHelpString.append(lvShve.printTXSTransaktionStart());
             lvHelpString.append(lvShve.printTXSTransaktionDaten());
             // TXSVerzeichnisDaten
             lvVedaten = new TXSVerzeichnisDaten();
             lvVedaten.setAbt("3");
             lvVedaten.setGbart("1"); 
             lvVedaten.setKat("1");
             lvVedaten.setNrabt(lvGbe.getLfdNr());
             //vedaten.setBetrag(dZuwBetragRe.toString());
             // CT 28.11.2011 - Umgestellt auf Verfuegungsbetrag
             //if (darlehen.getKredittyp().equals("B"))
             //{
             //    vedaten.setBetrag(dVerfBetragBu.toString());       
             //}
             //else
             //{
             //    vedaten.setBetrag(dVerfBetragRe.toString());
             //}
             if (ivSicherheitenvereinbarung.getGesamtgrundschuld().equals("X"))
             {
                 BigDecimal lvHelpVerf = new BigDecimal(lvVermoegensObj.getVerfuegungsbetrag());
                 if (ivListeGrundbucheintraege.size() > 0)
                 {
                   lvHelpVerf.divide(new BigDecimal(ivListeGrundbucheintraege.size()), 9, RoundingMode.HALF_UP);
                 }
                 // Alt: lvVedaten.setBetrag(lvVermoegensObj.getVerfuegungsbetrag());
                 lvVedaten.setBetrag(lvHelpVerf.toString());
             }
             else
             {
                 ObjektZuweisungsbetrag lvOzw = pvOzwListe.getObjektZuweisungsbetrag(ivGeschaeftswert.getSicherheitenID());
                 if (lvOzw != null)
                 {
                     if (ivListeGrundbucheintraege.size() > 0)
                     {
                         lvOzw.getZuweisungsbetrag().divide(new BigDecimal(ivListeGrundbucheintraege.size()), 9, RoundingMode.HALF_UP);   
                     }
                     lvVedaten.setBetrag(lvOzw.getZuweisungsbetrag().toString());
                 }
             }

             lvVedaten.setWhrg(ivGeschaeftswert.getZuweisungsbetragWaehrung());
             lvHelpString.append(lvVedaten.printTXSTransaktionStart());
             lvHelpString.append(lvVedaten.printTXSTransaktionDaten());
             lvHelpString.append(lvVedaten.printTXSTransaktionEnde());
             
             // TXSVerzeichnisVBlatt
             lvVevb = new TXSVerzeichnisVBlatt();
             // CT 31.01.2012 vbnr ver�ndert 
             // <Vermoegensobjekt-ID><Band><Blatt><lfd-Nr Abt3>
             lvVevb.setVbnr(lvGbe.getObjektID() + lvGbe.getBand() + lvGbe.getBlatt() + lvGbe.getLfdNr());
             // alte: vevb.setVbnr(gbe.getGrundbuchID());
             if (lvVevb.getVbnr().length() > 40)
             {
                 lvVevb.setVbnr(lvVevb.getVbnr().substring(0, 39));
             }
             // CT 14.02.2012
             //try
             //{
             //  pvFosIDs.write(("vevb;" + lvGbe.getGrundbuchID() + ";" + lvVevb.getVbnr() + ";" + StringKonverter.lineSeparator).getBytes());
             //}
             //catch (Exception e)
             //{
             //    System.out.println("Fehler bei vevb-ID...");
             //}
             // CT 14.02.2012

             lvVevb.setOrg(ValueMapping.changeMandant(ivInstitutsnummer));
             lvVevb.setQuelle("ADARLPFBG");
             lvHelpString.append(lvVevb.printTXSTransaktionStart());
             lvHelpString.append(lvVevb.printTXSTransaktionDaten());
             
             // TXSVerzeichnisblattDaten
             lvVbdaten = new TXSVerzeichnisblattDaten();
             lvVbdaten.setBand(lvGbe.getBand());
             if (lvGbe.getBlatt().isEmpty())
             {
                 lvVbdaten.setBlatt("0000");
             }
             else
             {
               lvVbdaten.setBlatt(lvGbe.getBlatt());
             }
             lvVbdaten.setGbvon(lvGbe.getGrundbuchVon());
             if (lvGbe.getGrundbuchVon().isEmpty() && !lvGbe.getAmtsgericht().isEmpty())
             {
                 lvVbdaten.setGbvon(lvGbe.getAmtsgericht());
             }
             lvVbdaten.setKat("1");
             lvHelpString.append(lvVbdaten.printTXSTransaktionStart());
             lvHelpString.append(lvVbdaten.printTXSTransaktionDaten());
             lvHelpString.append(lvVbdaten.printTXSTransaktionEnde());
             
             lvHelpString.append(lvVevb.printTXSTransaktionEnde());
             
             // TXSVerzeichnisBestandsverz
             // Es kann Grundbucheintraege mit mehreren Flurstueck geben
             if (lvGbe.getBestandsverzeichnis().size() > 1)
             {
                 ivLogger.info("Grundbuch mit mehreren Flurstuecken - Aktenzeichen: " + ivGeschaeftswert.getGeschaeftswertID());
             }
             Bestandsverzeichnis lvBestandsverzeichnis;
             for (int f = 0; f < lvGbe.getBestandsverzeichnis().size(); f++)
             {
                 lvBestandsverzeichnis = lvGbe.getBestandsverzeichnis().get(f);
                 lvVebv = new TXSVerzeichnisBestandsverz();
                 // CT 31.01.2012 bvnr ver�ndert 
                 // <Vermoegensobjekt-ID><Band><Blatt><lfd-Nr Grundstueck>
                 lvVebv.setBvnr(lvGbe.getObjektID() + lvGbe.getBand() + lvGbe.getBlatt() + lvGbe.getLfdNr()
                              + lvBestandsverzeichnis.getLfdNrGrundstueck());
 
                              //+ gbe.getLfdNr()+ flurstueck);
                 if (lvVebv.getBvnr().length() > 40)
                 {
                     lvVebv.setBvnr(lvVebv.getBvnr().substring(0, 39));
                 }
                 // CT 14.02.2012
                 //try
                 //{
                 //  pvFosIDs.write(("vebv;" + lvGbe.getGrundbuchID() + ";" + lvVebv.getBvnr() + ";" + StringKonverter.lineSeparator).getBytes());
                 //}
                 //catch (Exception e)
                 //{
                 //    System.out.println("Fehler bei vebv-ID...");
                 //}
                 // CT 14.02.2012
                 
                 // alte: vebv.setBvnr(gbe.getGrundbuchID());
                 lvVebv.setOrg(ValueMapping.changeMandant(ivInstitutsnummer));
                 lvVebv.setQuelle("ADARLPFBG");
                 lvHelpString.append(lvVebv.printTXSTransaktionStart());
                 lvHelpString.append(lvVebv.printTXSTransaktionDaten());
                 // TXSBestandsverzDaten
                 lvBvdaten = new TXSBestandsverzDaten();
                 lvBvdaten.setFlur(lvBestandsverzeichnis.getFlur());
                 lvBvdaten.setFlurst(lvBestandsverzeichnis.getFlurstueck());
                 lvBvdaten.setLfdnr(lvBestandsverzeichnis.getLfdNrGrundstueck());
                 if (lvGbe.getBlatt().isEmpty() && !lvGbe.getFlur().isEmpty())
                 {
                     lvBvdaten.setGem(lvGbe.getGemarkung());
                 }
             
                 lvHelpString.append(lvBvdaten.printTXSTransaktionStart());
                 lvHelpString.append(lvBvdaten.printTXSTransaktionDaten());
                 lvHelpString.append(lvBvdaten.printTXSTransaktionEnde());
             
                 lvHelpString.append(lvVebv.printTXSTransaktionEnde());
             } 
             
             // TXSVerzeichnisPfandobjekt
             lvVepo = new TXSVerzeichnisPfandobjekt();
             lvVepo.setObjnr(lvVermoegensObj.getObjektID());
             lvVepo.setOrg(ValueMapping.changeMandant(ivInstitutsnummer));
             lvVepo.setQuelle("ADARLPFBG");
             lvHelpString.append(lvVepo.printTXSTransaktionStart());
             lvHelpString.append(lvVepo.printTXSTransaktionDaten());
             // TXSPfandobjektDaten
             lvPodaten = new TXSPfandobjektDaten();
             
             /* Best�ckung von Pfandobjekten etc... */
             if (lvVermoegensObj != null)
             { /* Es ist etwas da .... */
               /* Belegung aus SI/SIO-Strukturen */
               /* Einzelne Felder ermitteln ... */

              /* Eigentumstyp aktuell gar nicht zu mappen !! */
              String lvPfGruppe = new String();
              String lvPfArt = new String();
              //String sPfEigTyp = new String();

              lvPfGruppe = ValueMapping.mapPfandObjektGruppe(lvVermoegensObj);
              lvPfArt = ValueMapping.mapNutzungsart(lvVermoegensObj);

              // Ertragsfaehigkeit - CT 09.07.2012
              // Defaultm�ssig '3' - voll ertragsfaehig
              String lvErtragsKz = "3";
              // Bauplatz
              if (lvPfArt.startsWith("4"))
              {
                  lvErtragsKz = "2";
              }
              // Baugeld
              if (lvPfArt.startsWith("7"))
              {
                lvErtragsKz = "1";
              }
                           
            // CT 26.03.2012 - Altes Mapping 
            //podaten.setEigtyp(ValueMapping.changeEigentumstyp(sEigentumsTyp));
              lvPodaten.setEigtyp(ValueMapping.mapEigentumstyp(lvVermoegensObj.getNutzung()));
              
            lvPodaten.setErtragsf(lvErtragsKz);
            //alte Version: podaten.setGebiet(ValueMapping.changeGebiet(sLand));
            // CT - 09.07.2012
            if (!lvVermoegensObj.getGebiet().isEmpty())
            {
              lvPodaten.setGebiet(lvVermoegensObj.getLand() + "_" + lvVermoegensObj.getGebiet());
            }
            
             if (lvVermoegensObj.getFertigstellungsdatum().length() > 4)
                lvPodaten.setJahr(ValueMapping.changeBaujahr(DatumUtilities.changeDatePoints(lvVermoegensObj.getFertigstellungsdatum())));
             lvPodaten.setKat("1");
             //alte Version: podaten.setLand(ValueMapping.changeLand(sStaat));
             // CT - 09.07.2012
             lvPodaten.setLand(lvVermoegensObj.getLand());
             
             lvPodaten.setSwert(lvVermoegensObj.getSachwert());
             lvPodaten.setEwert(lvVermoegensObj.getErtragswert());
             lvPodaten.setBwert(lvVermoegensObj.getBeleihungswert());
             lvPodaten.setNart(lvPfArt);
             
             // CT 26.03.2012 - Altes Mapping
             //podaten.setOgrp(ValueMapping.changeObjektgruppe(sPfGruppe));
             lvPodaten.setOgrp(lvPfGruppe);
             
             lvPodaten.setOrt(lvVermoegensObj.getOrt());
             lvPodaten.setPlz(lvVermoegensObj.getPostleitzahl());

             String sSP_EigentumsTyp = new String();
             sSP_EigentumsTyp = ValueMapping.mapSP_Eigentumstyp(lvVermoegensObj);
             lvPodaten.setSpeigtyp(sSP_EigentumsTyp);
             
             lvPodaten.setStr(lvVermoegensObj.getStrasse());
             // Hausnummer in ein eigenes Feld - CT 25.11.2011
             lvPodaten.setHausnr(lvVermoegensObj.getHausnummer());
             lvPodaten.setWhrg(lvVermoegensObj.getBeleihungswertWaehrung());
             
             lvHelpString.append(lvPodaten.printTXSTransaktionStart());
             lvHelpString.append(lvPodaten.printTXSTransaktionDaten());
             lvHelpString.append(lvPodaten.printTXSTransaktionEnde());
             
             lvHelpString.append(lvVepo.printTXSTransaktionEnde());
             
             lvHelpString.append(lvShve.printTXSTransaktionEnde());
            }
           }
         }
          
       lvHelpString.append(lvKredsh.printTXSTransaktionEnde());
       }
       return lvHelpString;
    }
    
 }
