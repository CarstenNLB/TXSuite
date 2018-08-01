package nlb.txs.schnittstelle.SAPCMS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetragListe;
import nlb.txs.schnittstelle.SAPCMS.Daten.Bestandsverzeichnis;
import nlb.txs.schnittstelle.SAPCMS.Daten.Geschaeftswert;
import nlb.txs.schnittstelle.SAPCMS.Daten.Grundbucheintrag;
import nlb.txs.schnittstelle.SAPCMS.Daten.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.SAPCMS.Daten.Vermoegensobjekt;
import nlb.txs.schnittstelle.SAPCMS.Daten.Vorlaufsatz;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;

public class SAPCMS
{
    // Status Sicherheitenvereinbarung
    private final int SHVB = 0;
    
    // Status Vermoegensobjekt
    private final int VOBJ = 1;
    
    // Status Grundbucheintrag
    private final int GBE = 2;
    
    // Status Geschaeftswert
    private final int GW = 3;
    
    // Status undefiniert
    private final int UNDEFINIERT = 4;

    // Status
    private int ivStatus;
    
    private File ivFileSAPCMS;    
    	    
    private Vorlaufsatz ivVorlaufsatz;
    private Sicherheitenvereinbarung ivSicherheitenvereinbarung;
    private Vermoegensobjekt ivVermoegensobjekt;
    private Grundbucheintrag ivGrundbucheintrag;
    private Bestandsverzeichnis ivBestandsverzeichnis;
    private Geschaeftswert ivGeschaeftswert;
    
    private SicherheitenvereinbarungListe ivShvbListe;
    private VermoegensobjektListe ivVobjListe;
    private GrundbucheintragListe ivGbeListe;
    private GeschaeftswertListe ivGwListe;
    
	private int ivZaehlerVorlaufsatz = 0;
	private int ivZaehlerSicherheitenvereinbarung = 0;
	private int ivZaehlerVermoegensobjekt = 0;
	private int ivZaehlerGrundbucheintrag = 0;
	private int ivZaehlerGeschaeftswert = 0;

	private ObjektZuweisungsbetragListe ivOzwListe;
	
	private Logger ivLogger;
		
	/**
	 * @param pvFilename 
	 * @param pvLogger 
	 * @param pvFosIDs 
	 * 
	 */
	public SAPCMS(String pvFilename, Logger pvLogger) //, FileOutputStream pvFosIDs)
	{	
	    this.ivLogger = pvLogger;
	    readSAPCMS(pvFilename);
	}

	/**
	 * 
	 */
	private void readSAPCMS(String pvDateiname)
	{
	    String lvZeile = null;
	    ivVorlaufsatz = new Vorlaufsatz();
	    ivShvbListe = new SicherheitenvereinbarungListe();
	    ivVobjListe = new VermoegensobjektListe();
	    ivGbeListe = new GrundbucheintragListe();
	    ivGwListe = new GeschaeftswertListe();
	    
	    ivOzwListe = new ObjektZuweisungsbetragListe();
    	      
	    // Oeffnen der Dateien
    	      FileInputStream lvFis = null;
    	      ivFileSAPCMS = new File(pvDateiname);
    	      try
    	      {
    	        lvFis = new FileInputStream(ivFileSAPCMS);
    	      }
    	      catch (Exception e)
    	      {
    	        System.out.println("Konnte SAPCMS-Datei nicht oeffnen!");
    	        return;
    	      }
    
    	      BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
    	      boolean lvStart = true;
    	      
    	      try
    	      {
    	        while ((lvZeile = lvIn.readLine()) != null)  // Lesen SAPCMS-Datei
    	        {
    	          if (lvStart)
    	          {
    	              parseVorlaufsatz(lvZeile);
    	              ivZaehlerVorlaufsatz++;
    	              lvStart = false;
    	          }
    	          else
    	          {
    	            if (!parseSAPCMS(lvZeile)) // Parsen der Felder
    	            {
    		          System.out.println("Datenfehler!!!\n");
    	            }
    	          }
    	        }
    	      }
    	      catch (Exception e)
    	      {
    	        System.out.println("Fehler beim Verarbeiten einer Zeile!");
    	        e.printStackTrace();
    	      }
    
    	      
    	      try
    	      {
    	        lvIn.close();
    	      }
    	      catch (Exception e)
    	      {
    	        System.out.println("Konnte SAPCMS-Datei nicht schliessen!");
    	      }
    	         
    	      //if (ivLogging)
    	      //{
    	      ivLogger.info(printStatistik());
    	      //}
    	    }


	  /**
	   * @return 
	   */
	  public String printStatistik()
	  {
	    String lvOut = new String();
	    lvOut = ivVorlaufsatz.toString();
	    
	    lvOut = lvOut + StringKonverter.lineSeparator;
 	    lvOut = lvOut + ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen..." + StringKonverter.lineSeparator;
	    lvOut = lvOut + ivZaehlerSicherheitenvereinbarung + " Sicherheitenvereinbarungen gelesen..." + StringKonverter.lineSeparator;
	    lvOut = lvOut + ivZaehlerVermoegensobjekt + " Vermoegensobjekte gelesen..." + StringKonverter.lineSeparator;
	    lvOut = lvOut + ivZaehlerGrundbucheintrag + " Grundbucheintraege gelesen..." + StringKonverter.lineSeparator;
	    lvOut = lvOut + ivZaehlerGeschaeftswert + " Geschaeftswerte gelesen..." + StringKonverter.lineSeparator;
	 
	    lvOut = lvOut + ivShvbListe.size() + " Sicherheitenvereinbarungen in der Liste..." + StringKonverter.lineSeparator;
	    lvOut = lvOut + ivVobjListe.size() + " Vermoegensobjekte in der Liste..." + StringKonverter.lineSeparator;
	    lvOut = lvOut + ivGbeListe.size() + " Grundbucheintraege in der Liste..." + StringKonverter.lineSeparator;
	    lvOut = lvOut + ivGwListe.size() + " Geschaeftswerte in der Liste..." + StringKonverter.lineSeparator;

	    return lvOut;
	  }

	   /**
	    * @param pvZeile 
	    * @return 
	    * 
        */
      public boolean parseVorlaufsatz(String pvZeile)
      {
        String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
        int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
        int    lvZzStr=0;              // pointer fuer satzbereich
        
        // steuerung/iteration eingabesatz
        for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
        {

          // wenn semikolon erkannt
          if (pvZeile.charAt(lvZzStr) == ';')
          {
            setVorlaufsatz(lvLfd, lvTemp);
          
            lvLfd++;                  // naechste feldnummer
          
            // loeschen zwischenbuffer
            lvTemp = new String();

          }
          else
          {
              // uebernehmen byte aus eingabesatzposition
              lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
          }
        } // ende for
        
        //if (stemp.equals("E"))
        //    System.out.println("Vorlaufsatz: Endekennzeichen erreicht.");

        return true;
      }
	  
      /**
       * Setzt einen Wert des Vorlaufsatzes
       * @param pvPos Position
       * @param pvWert Wert
       */
      private void setVorlaufsatz(int pvPos, String pvWert)
      {
          switch (pvPos)
          {
            case 0:
                ivVorlaufsatz.setDateiname(pvWert);
                break;
            case 1:
                ivVorlaufsatz.setBankarea(pvWert);
                break;
            case 2:
                ivVorlaufsatz.setBuchungsdatum(pvWert);
                break;
            case 3:
                ivVorlaufsatz.setVerarbeitungskennzeichen(pvWert);
                break;
            case 4:
                ivVorlaufsatz.setProgrammname(pvWert);
                break;
            case 5:
                ivVorlaufsatz.setUsername(pvWert);
                break;
            case 6:
                ivVorlaufsatz.setSystemname(pvWert);
                break;
            case 7:
                ivVorlaufsatz.setTimestamp(pvWert);
                break;
            default:
                System.out.println("Vorlaufsatz: undefiniert");
          }
      }
 
	  /**
	   * @param pvZeile 
	   * @return 
	   * 	   
	   */
	 public boolean parseSAPCMS(String pvZeile)
	 {	  
        String lvShID = null;
               
	    String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
	    int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
	    int    lvZzStr=0;              // pointer fuer satzbereich
	    
	    ivStatus = UNDEFINIERT;
	    
	    // steuerung/iteration eingabesatz
	    for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
	    {

	      // wenn semikolon erkannt
	      if (pvZeile.charAt(lvZzStr) == ';')
	      {
	          if (lvLfd < 3)
	          {
	              if (lvLfd == 0)
	              {
	                  //System.out.println("Institut: " + vorlaufsatz.getOnlyBankarea());
	                  
	                  // Bankarea: 0001 ist schlecht!
	                  //if (!stemp.equals(vorlaufsatz.getBankareaValue()))
	                  //{
	                  //    System.out.println("Falscher Bankbereich");
	                  //    return false;
	                  //}
	              }
	              if (lvLfd == 1)
	              {
	                  lvShID = lvTemp;
	              }
	              if (lvLfd == 2)
	              {
	                if (lvTemp.equals("10"))
	                {
	                    ivStatus = SHVB;
	                    ivSicherheitenvereinbarung = new Sicherheitenvereinbarung();
	                    ivSicherheitenvereinbarung.setSicherheitenID(lvShID);
	                    ivZaehlerSicherheitenvereinbarung++;
	                }
	                if (lvTemp.equals("20"))
                    {
                        ivStatus = VOBJ;
                        ivVermoegensobjekt = new Vermoegensobjekt();
                        ivVermoegensobjekt.setSicherheitenID(lvShID);
                        ivZaehlerVermoegensobjekt++;
                    }
	                if (lvTemp.equals("21"))
                    {
                        ivStatus = GBE;
                        ivGrundbucheintrag = new Grundbucheintrag();
                        ivGrundbucheintrag.setSicherheitenID(lvShID);
                        ivBestandsverzeichnis = new Bestandsverzeichnis();
                        ivZaehlerGrundbucheintrag++;
                    }
	                if (lvTemp.equals("30"))
                    {
                        ivStatus = GW;
                        ivGeschaeftswert = new Geschaeftswert();
                        ivGeschaeftswert.setSicherheitenID(lvShID);
                        ivZaehlerGeschaeftswert++;
                    }
	              }
	          }
	          else
	          {
	              //System.out.println(stemp);
	              switch (ivStatus)
	              {
	                  case SHVB:
	                      setSicherheitenvereinbarung(lvLfd, lvTemp);
	                      break;
	                  case VOBJ:
	                      setVermoegensobjekt(lvLfd, lvTemp);
	                      break;
	                  case GBE:
	                      setGrundbucheintrag(lvLfd, lvTemp);
	                      break;
	                  case GW:
	                      setGeschaeftswert(lvLfd, lvTemp);
	                      break;
	                  default:
	                      System.out.println("Unbekannte Satzart: " + ivStatus + "\n");
	              }
	          }

	          lvLfd++;                  // naechste feldnummer
	          // loeschen zwischenbuffer
	          lvTemp = new String();
	      }
	      else
	      {
	          // uebernehmen byte aus eingabesatzposition
	          lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
	      }
	  } // ende for  
	   
	  if (lvTemp.equals("E") && ivStatus == SHVB)
	  {
	      ivShvbListe.addSicherheitenvereinbarung(ivSicherheitenvereinbarung);
	  }
	      
      if (lvTemp.equals("E") && ivStatus == VOBJ)
      {
          ivVobjListe.putVermoegensobjekt(ivVermoegensobjekt);
      }
      
      if (lvTemp.equals("E") && ivStatus == GBE)
      {
          if (!ivGbeListe.existsGrundbucheintrag(ivGrundbucheintrag))
          {
            ivGbeListe.putGrundbucheintrag(ivGrundbucheintrag);
          }
          //else
          //{
              //if (ivLogging)
              //{
                  //ivLogger.info("Sicherheit: " + ivGrundbucheintrag.getSicherheitenID() + " mit mehreren Flurstuecken.");
                  //ivLogger.info("Grundbuch: " + ivGrundbucheintrag.getGrundbuchID());
              //}
          //}
      }
      
      if (lvTemp.equals("E") && ivStatus == GW)
      {
          ivGwListe.putGeschaeftswert(ivGeschaeftswert);
          ivOzwListe.addObjektZuweisungsbetrag(ivGeschaeftswert.getSicherheitenID(), ivGeschaeftswert.getZuweisungsbetrag());
      }
      
	  return true; 
	 }
	 
	  /**
	   * Setzt einen Wert der Sicherheitenvereinbarung
	   * @param pvPos Position
	   * @param pvWert Wert
	   */
	  private void setSicherheitenvereinbarung(int pvPos, String pvWert)
	  {
	      switch (pvPos)
	      {
	      	case 3:
	      		ivSicherheitenvereinbarung.setSicherheitenTyp(pvWert);
	      		break;
	      	case 4:
	      		ivSicherheitenvereinbarung.setSicherheitengeber(pvWert);
	      		break;
	      	case 5:
	      		ivSicherheitenvereinbarung.setPartnerfunktion(pvWert);
	      		break;
	      	case 6:
	      		ivSicherheitenvereinbarung.setGueltigVon(pvWert);
	      		break;
	      	case 7:
	      		ivSicherheitenvereinbarung.setGueltigBis(pvWert);
	      		break;
	      	case 8:
	      		ivSicherheitenvereinbarung.setNominalwert(pvWert);
	      		break;
	      	case 9:
	      		ivSicherheitenvereinbarung.setNominalwertWaehrung(pvWert);
	      		break;
	      	case 10:
	      		ivSicherheitenvereinbarung.setAnzusetzenderWert(pvWert);
	      		break;
	      	case 11:
	      		ivSicherheitenvereinbarung.setAnzusetzenderWertWaehrung(pvWert);
	      		break;
	      	case 12:
	      		ivSicherheitenvereinbarung.setVerfuegungsbetrag(pvWert);
	      		break;
	      	case 13:
	      		ivSicherheitenvereinbarung.setVerfuegungsbetragWaehrung(pvWert);
	      		break;
	      	case 14:
	      		ivSicherheitenvereinbarung.setAnzahlPortions(pvWert);
	      		break;
	      	case 15:
	      		ivSicherheitenvereinbarung.setGesamtgrundschuld(pvWert);
	      		break;
	      	case 16:
	      		ivSicherheitenvereinbarung.setGrundschuldbrief(pvWert);
	      		break;
	      	default:
	      		System.out.println("Sicherheitenvereinbarung: Position: " + pvPos + " Wert: " + pvWert + " undefiniert");
	      }
	  } 
	  
	  /**
       * Setzt einen Wert des Vermoegensobjekt
       * @param pvPos Position
       * @param pvWert Wert
       */
      private void setVermoegensobjekt(int pvPos, String pvWert)
      {
          switch (pvPos)
          {
            case 3:
                ivVermoegensobjekt.setObjektID(pvWert);
                break;
            case 4:
                ivVermoegensobjekt.setObjektTyp(pvWert);
                break;
            case 5:
                ivVermoegensobjekt.setEigentuemer(pvWert);
                break;
            case 6:
                ivVermoegensobjekt.setPartnerfunktion(pvWert);
                break;
            case 7:
                ivVermoegensobjekt.setNominalwert(pvWert);
                break;
            case 8:
                ivVermoegensobjekt.setNominalwertWaehrung(pvWert);
                break;
            case 9:
                ivVermoegensobjekt.setBeleihungswert(pvWert);
                break;
            case 10:
                ivVermoegensobjekt.setBeleihungswertWaehrung(pvWert);
                break;
            case 11:
                ivVermoegensobjekt.setVerfuegungsbetrag(pvWert);
                break;
            case 12:
                ivVermoegensobjekt.setVerfuegungsbetragWaehrung(pvWert);
                break;
            case 13:
                ivVermoegensobjekt.setStrasse(pvWert);
                break;
            case 14:
                ivVermoegensobjekt.setOrt(pvWert);
                break;
            case 15:
                ivVermoegensobjekt.setPostleitzahl(pvWert);
                break;
            case 16:
                ivVermoegensobjekt.setLand(pvWert);
                break;
            case 17:
                ivVermoegensobjekt.setGebiet(pvWert);
                break;
            case 18:
                ivVermoegensobjekt.setHausnummer(pvWert);
                break;
            case 19:
                ivVermoegensobjekt.setNutzungsart(pvWert);
                break;
            case 20:
                ivVermoegensobjekt.setErtragswert(pvWert);
                break;
            case 21:
                ivVermoegensobjekt.setErtragswertWaehrung(pvWert);
                break;
            case 22:
                ivVermoegensobjekt.setSachwert(pvWert);
                break;
            case 23:
                ivVermoegensobjekt.setSachwertWaehrung(pvWert);
                break;
            case 24:
                ivVermoegensobjekt.setNutzung(pvWert);
                break;
            case 25:
                ivVermoegensobjekt.setGewerblicheNutzung(pvWert);
                break;
            case 26:
                ivVermoegensobjekt.setFertigstellungProzent(pvWert);
                break;
            case 27:
                ivVermoegensobjekt.setFertigstellungsdatum(pvWert);
                break;
            case 28:
                ivVermoegensobjekt.setGesamtflaeche(pvWert);
                break;
            case 29:
                ivVermoegensobjekt.setDeckungskennzeichen(pvWert);
                break;
            case 30:
                ivVermoegensobjekt.setBemerkung(pvWert);
                break;
            default:
                System.out.println("Vermoegensobjekt: Position: " + pvPos + " Wert: " + pvWert + " undefiniert");
          }
      } 

      /**
       * Setzt einen Wert des Grundbucheintrags
       * @param pvPos Position
       * @param pvWert Wert
       */
      private void setGrundbucheintrag(int pvPos, String pvWert)
      {
          switch (pvPos)
          {
            case 3:
                ivGrundbucheintrag.setObjektID(pvWert);
                break;
            case 4:
                ivGrundbucheintrag.setGrundbuchID(pvWert);
                break;
            case 5:
                ivGrundbucheintrag.setLfdNr(pvWert);
                break;
            case 6:
                //grundbucheintrag.setLfdNrGrundstuecks(wert);
                ivBestandsverzeichnis.setLfdNrGrundstueck(pvWert);
                break;
            case 7:
                ivGrundbucheintrag.setAmtsgericht(pvWert);
                break;
            case 8:
                ivGrundbucheintrag.setGrundbuchVon(pvWert);
                break;
            case 9:
                ivGrundbucheintrag.setBand(pvWert);
                break;
            case 10:
                ivGrundbucheintrag.setBlatt(pvWert);
                break;
            case 11:
                ivGrundbucheintrag.setGemarkung(pvWert);
                break;
            case 12:
                ivGrundbucheintrag.setFlaeche(pvWert);
                break;
            case 13:
                //grundbucheintrag.setFlur(wert); - CT 23.02.2012
                ivBestandsverzeichnis.setFlur(pvWert);
                break;
            case 14:
                //grundbucheintrag.addFlurstueck(wert); - CT 23.02.2012
                ivBestandsverzeichnis.setFlurstueck(pvWert);
                ivGrundbucheintrag.addBestandsverzeichnis(ivBestandsverzeichnis);
                break;
             default:
                System.out.println("Grundbucheintrag: Position: " + pvPos + " Wert: " + pvWert + " undefiniert");
          }
      } 

      /**
       * Setzt einen Wert des Grundbucheintrags
       * @param pvPos Position
       * @param pvWert Wert
       */
      private void setGeschaeftswert(int pvPos, String pvWert)
      {
          switch (pvPos)
          {
            case 3:
                ivGeschaeftswert.setGeschaeftswertID(pvWert);
                break;
            case 4:
                ivGeschaeftswert.setTeilID(pvWert);
                break;
            case 5:
                ivGeschaeftswert.setKreditsystem(pvWert);
                break;
            case 6:
                ivGeschaeftswert.setKreditnehmer(pvWert);
                break;
            case 7:
                ivGeschaeftswert.setPartnerfunktion(pvWert);
                break;
            case 8:
                ivGeschaeftswert.setZuweisungsbetrag(pvWert);
                break;
            case 9:
                ivGeschaeftswert.setZuweisungsbetragWaehrung(pvWert);
                break;
            case 10:
                ivGeschaeftswert.setEinschraenkungTeil(pvWert);
                break;
            case 11:
                ivGeschaeftswert.setTeilwertProzent(pvWert);
                break;
            case 12:
                ivGeschaeftswert.setTeilwert(pvWert);
                break;
            case 13:
                ivGeschaeftswert.setTeilwertWaehrung(pvWert);
                break;
             default:
                System.out.println("Geschaeftswert: Position: " + pvPos + " Wert: " + pvWert + " undefiniert");
          }
      } 
      
      /**
       * Es wird geprüft, ob es zur Kontonummer eine Sicherheit in SAP CMS gibt
       * @param pvKontonummer 
       * @return 
       * 
       */
      public boolean existsSicherheitObjekt(String pvKontonummer)
      {
          LinkedList <Geschaeftswert> lvHelpListe = ivGwListe.get(pvKontonummer);
          if (lvHelpListe == null)
              return false;
          else
              return true;
      }
      
      /**
       * Liefert das SicherheitObjekt
       * @param pvNr Kontonummer
       * @return 
       */
      public SicherheitObjekt getSicherheitObjekt(String pvNr)
      {
        //String s = new String();
        SicherheitObjekt lvResultSicherheitObjekt = null;  
          
        Geschaeftswert lvGw = null;
        Sicherheitenvereinbarung lvShvb = null;
        LinkedList<Vermoegensobjekt> lvListeVObj;
        LinkedList<Grundbucheintrag> lvListeGBs;
        
        //helpListe = gwListe.getGeschaeftswerte(nr);
        //System.out.println("Suche SAPCMS SicherheitObjekt zu Kontonummer " + nr);
      
        //for (int x = 0; x < gwListe.size(); x++)
        //{  
          LinkedList <Geschaeftswert> lvHelpListe = ivGwListe.get(pvNr);
          //System.out.println(nr + " == " + gw.getGeschaeftswertID());
          //if (gw.getGeschaeftswertID().equals(nr))
          //{
            // Nur Sicherheit-Objekt erzeugen, wenn ZW > 0 ist
          if (lvHelpListe != null)
          {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
              lvGw = lvHelpListe.get(x);
              //System.out.println("Zuweisungsbetrag: " + gw.getZuweisungsbetrag());
              if (StringKonverter.convertString2Double(lvGw.getZuweisungsbetrag()) > 0.0)
              {  
                //System.out.println("Geschaeftswert gefunden mit ZW = " + gw.getZuweisungsbetrag());
                //System.out.println("SicherheitenID: " + gw.getSicherheitenID());
                lvShvb = ivShvbListe.getSicherheitenvereinbarung(lvGw.getSicherheitenID());
                //System.out.println("shvb: " + shvb.toString());
                lvListeVObj = ivVobjListe.getVermoegensobjekte(lvGw.getSicherheitenID());
                //System.out.println("listeVObj: " + listeVObj.toString());
                lvListeGBs = ivGbeListe.getGrundbucheintraege(lvGw.getSicherheitenID());
                //System.out.println("listeGBs: " + listeGBs.toString());
                lvResultSicherheitObjekt = new SicherheitObjekt(lvGw, lvShvb, lvListeVObj, lvListeGBs, ivLogger);
                lvResultSicherheitObjekt.setInstitutsnummer(ivVorlaufsatz.getDateiname().substring(20,23));
              }
            } 
          }
        
        return lvResultSicherheitObjekt;
      }

    /**
     * @param pvKontonummer 
     * @param pvKredittyp 
     * @param pvBuergschaftprozent 
     * @return
     */
    public StringBuffer importSicherheitObjekte(String pvKontonummer, String pvKredittyp, String pvBuergschaftprozent, String pvDeckungskennzeichen) 
    {
        StringBuffer lvBuffer = new StringBuffer();
        SicherheitObjekt lvResultSicherheitObjekt = null;  
          
        Geschaeftswert lvGw = null;
        Sicherheitenvereinbarung lvShvb = null;
        LinkedList<Vermoegensobjekt> lvListeVObj;
        LinkedList<Grundbucheintrag> lvListeGBs;
        
        //helpListe = gwListe.getGeschaeftswerte(nr);
        //System.out.println("Suche SAPCMS SicherheitObjekt zu Kontonummer " + zielDarlehen.getKontonummer());
      
        LinkedList <Geschaeftswert> lvHelpListe = ivGwListe.get(pvKontonummer);
        // Nur Sicherheit-Objekt erzeugen, wenn ZW > 0 ist
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
              lvGw = lvHelpListe.get(x);
              //System.out.println("Zuweisungsbetrag: " + gw.getZuweisungsbetrag());
              //BigDecimal zuweisungsbetrag = new BigDecimal("0.0");
              if (StringKonverter.convertString2Double(lvGw.getZuweisungsbetrag()) > 0.0)
              {  
                // Summe der Zuweisungsbetraege bilden
                //LinkedList <Geschaeftswert> helpZwSummeListe = gwListe.get(zielDarlehen.getKontonummer());
                //Geschaeftswert helpGw;
                //for (int y = 0; y < helpZwSummeListe.size(); y++)
                //{
                //  helpGw = helpListe.get(y);
                //  System.out.println("Kontonummer: " + helpGw.getGeschaeftswertID());
                //  if (helpGw.getSicherheitenID().equals(gw.getSicherheitenID()))
                //  {
                //    zuweisungsbetrag = zuweisungsbetrag.add(StringKonverter.convertString2BigDecimal(gw.getZuweisungsbetrag()));
                //  }
                //}
                                  
                //System.out.println("Geschaeftswert gefunden mit ZW = " + gw.getZuweisungsbetrag());
                //System.out.println("SicherheitenID: " + gw.getSicherheitenID());
                lvShvb = ivShvbListe.getSicherheitenvereinbarung(lvGw.getSicherheitenID());
                //System.out.println("shvb: " + shvb.toString());
                lvListeVObj = ivVobjListe.getVermoegensobjekte(lvGw.getSicherheitenID());
                //System.out.println("listeVObj: " + listeVObj.toString());
                lvListeGBs = ivGbeListe.getGrundbucheintraege(lvGw.getSicherheitenID());
                //System.out.println("listeGBs: " + listeGBs.toString());
                lvResultSicherheitObjekt = new SicherheitObjekt(lvGw, lvShvb, lvListeVObj, lvListeGBs, ivLogger);
                lvResultSicherheitObjekt.setInstitutsnummer(ivVorlaufsatz.getDateiname().substring(20,23));
                lvBuffer.append(lvResultSicherheitObjekt.importSicherheitObjekt2TXSKreditSicherheit(pvKredittyp, pvBuergschaftprozent, ivOzwListe, pvDeckungskennzeichen));//, ivFosIDs));
              }
            } 
          }
        
        return lvBuffer;
    }
}
