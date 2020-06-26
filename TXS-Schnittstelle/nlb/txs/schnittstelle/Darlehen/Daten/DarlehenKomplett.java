/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten;

import java.util.LinkedList;

import nlb.txs.schnittstelle.Darlehen.Daten.Original.BAUFI;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHKOPF;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.INF;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KON;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KONTS;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTOZB;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTR;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTS;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.OBJ;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.REC;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.UMS;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class DarlehenKomplett 
{
    /**
     * Kopfsatz
     * Muss nur einmal gemerkt werden, da in jedem Segement dieselben Daten
     * stehen. 
     */
    private DWHKOPF ivDwhkopf;
    
    /**
     * Buchungsdatum
     */
    private String ivBuchungsdatum;
    
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Die Anzahl der Tage zwischen dem 01.01.1900 
     * und dem Buchungsdatum aus dem Vorlaufsatz
     */
    private int ivAnzahlBuchungsdatum;
    
    /**
     * BAUFI-Segment
     */
    private BAUFI ivBaufi;
    
    /**
     * Liste der INF-Segmente
     */
    private LinkedList<INF> ivListINF;
    
    /**
     * Liste der KON-Segmente
     */
    private LinkedList<KON> ivListKON;
    
    /**
     * Liste der KONTS-Segmente
     */
    private LinkedList<KONTS> ivListKONTS;
    
    /**
     * Liste der KTOZB-Segmente
     */
    private LinkedList<KTOZB> ivListKTOZB;
    
    /**
     * KTR-Segment
     */
    private KTR ivKtr;
    
    /**
     * Liste der KTS-Segment
     */
    private KTS ivKts;
    
    /**
     * OBJ-Segment
     */
    private OBJ ivObj;
    
    /**
     * REC-Segment
     */
    private REC ivRec;
    
    /**
     * UMS-Segment
     */
    private UMS ivUms;
    
    /**
     * 
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor
     * @param pvLogger 
     */
    public DarlehenKomplett(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivBuchungsdatum = new String();
        ivDwhkopf = new DWHKOPF();
        ivBaufi = null;
        ivListINF = new LinkedList<INF>();
        ivListKON = new LinkedList<KON>();
        ivListKONTS = new LinkedList<KONTS>();
        ivListKTOZB = new LinkedList<KTOZB>();
        ivKtr = null;
        ivKts = null;
        ivObj = null;
        ivRec = null;
        ivUms = null;
     }

    /**
     * @return the dwhkopf
     */
    public DWHKOPF getDwhkopf() 
    {
        return this.ivDwhkopf;
    }

    /**
     * @param pvDwhkopf the dwhkopf to set
     */
    public void setDwhkopf(DWHKOPF pvDwhkopf) 
    {
        this.ivDwhkopf = pvDwhkopf;
    }
    
    /**
     * Setzt das Buchungsdatum
     * @param pvDatum 
     */
    public void setBuchungsdatum(String pvDatum)
    {
        this.ivBuchungsdatum = pvDatum;
    }
    
    /**
     * Liefert das Buchungsdatum
     * @return 
     */
    public String getBuchungsdatum()
    {
        return this.ivBuchungsdatum;
    }

    /**
     * Setzt die Institutsnummer
     * @param pvInstnr
     */
    public void setInstitutsnummer(String pvInstnr)
    {
        this.ivInstitutsnummer = pvInstnr;
    }
    
    /**
     * Liefert die Institutsnummer
     * @return 
     */
    public String getInstitutsnummer()
    {
        return this.ivInstitutsnummer;
    }
    
    /**
     * Setzt die Anzahl der Tage zwischen dem 01.01.1900 
     * und dem Buchungsdatum aus dem Vorlaufsatz
     * @param pvTage 
     */
    public void setAnzahlDatum(int pvTage)
    {
        this.ivAnzahlBuchungsdatum = pvTage;
    }
    
    /**
     * Leeren der Listen des Darlehens
     */
    public void clearLists()
    {
        ivDwhkopf = new DWHKOPF();
        ivBaufi = null;
        ivListINF = new LinkedList<INF>();
        ivListKON = new LinkedList<KON>();
        ivListKONTS = new LinkedList<KONTS>();
        ivListKTOZB = new LinkedList<KTOZB>();
        ivKtr = null;
        ivKts = null;
        ivObj = null;
        ivRec = null;
        ivUms = null;
    }
    
    /**
     * Setzt BAUFI-Segment
     * @param pvBaufi 
     */
    public void setBAUFI(BAUFI pvBaufi)
    {
        this.ivBaufi = pvBaufi;   
    }
    
    /**
     * Liefert BAUFI-Segment
     * @return 
     */
    public BAUFI getBAUFI()
    {
        return this.ivBaufi;
    }

    /**
     * F�gt ein INF-Segment hinzu
     * @param pvInf
     */
    public void addINF(INF pvInf)
    {
        ivListINF.add(pvInf);   
    }
    
    /**
     * @return 
     * 
     */
    public INF getINFZ()
    {
        INF lvInf = null;
        INF lvHelpINF;
        for (int x = 0; x < ivListINF.size(); x++)
        {
            lvHelpINF = ivListINF.get(x);
            if (lvHelpINF.getKopf().getsDwhtdb().equals("SICHV") && 
                lvHelpINF.getsBbart().equals("Z"))
            {
                lvInf = lvHelpINF;
            }
        }
        return lvInf;   
    }
    
    /**
     * 
     * @return
     */
    public INF getINFV()
    {
      INF lvInf = null;
      INF lvHelpINF;
      for (int x = 0; x < ivListINF.size(); x++)
      {
          lvHelpINF = ivListINF.get(x);
          if (lvHelpINF.getKopf().getsDwhtdb().equals("NOTIZ"))
          {
              lvInf = lvHelpINF;
          }
      }
      return lvInf;
    }
   
    /**
     * 
     * @return
     */
    public INF getINFS()
    {
      INF lvInf = null;
      INF lvHelpINF;
      for (int x = 0; x < ivListINF.size(); x++)
      {
          lvHelpINF = ivListINF.get(x);
          //System.out.println(helpINF.getKopf().getsDwhtdb() + " und " + helpINF.getsBbart());
          if (lvHelpINF.getKopf().getsDwhtdb().equals("SICHV") &&
              lvHelpINF.getsBbart().equals("Y"))
          {
              lvInf = lvHelpINF;
          }
      }
      return lvInf;
    }

    /**
     * Liefert das aktuelle INF-Segment der offenen Leistungen aus der Vergangenheit
     * Vergangenheit <= Buchungsdatum 
     * @return 
     */
    public INF getINFOF()
    {
        INF lvInf = null;
        INF lvHelpINF = null;

        for (int x = 0; x < ivListINF.size(); x++)
        {
            lvHelpINF = ivListINF.get(x);
            if (lvHelpINF.getKopf().getsDwhtdb().equals("OFLEI"))
            {
                if (lvHelpINF.getAnzahlDatum() > 0)
                {
                    if (lvHelpINF.getAnzahlDatum() <= this.ivAnzahlBuchungsdatum)
                    {
                        // anzahlDatum <= heute ...
                        if (lvInf != null)
                        {
                            // Wer ist j�nger?
                            if (lvInf.getAnzahlDatum() <= lvHelpINF.getAnzahlDatum())
                            {
                                lvInf = lvHelpINF;
                            }
                        }
                        else
                            lvInf = lvHelpINF;
                    }
                }
            }
        }
        return lvInf;
    }
    
    /**
     * F�gt ein KON-Segment hinzu
     * @param pvKon
     */
    public void addKON(KON pvKon)
    {
        ivListKON.add(pvKon);   
    }
    
    /**
     * Liefert das aktuelle KON-Segment aus der Vergangenheit
     * Vergangenheit <= Buchungsdatum 
     * @return 
     */
    public KON getKON()
    {
        KON lvKon = null;
        KON lvHelpKON = null;

        for (int x = 0; x < ivListKON.size(); x++)
        {
            lvHelpKON = ivListKON.get(x);
            //System.out.println("getKON: " + lvHelpKON.getsKog() + " " + lvHelpKON.isDatum11110101());
            //System.out.println("AnzahlDatum: " + lvHelpKON.getAnzahlDatum());
            //System.out.println("Anzahl Buchungsdatum: " + this.ivAnzahlBuchungsdatum);
            if (lvHelpKON.getAnzahlDatum() > 0)
            {
                if (lvHelpKON.getAnzahlDatum() <= this.ivAnzahlBuchungsdatum)
                {
                    // anzahlDatum <= heute ...
                    if (lvKon != null)
                    {
                        //System.out.println(kon.getAnzahlDatum() + " <= " + helpKON.getAnzahlDatum());
                        // Wer ist j�nger?
                        if (lvKon.getAnzahlDatum() <= lvHelpKON.getAnzahlDatum())
                        {
                            lvKon = lvHelpKON;
                        }
                    }
                    else
                        lvKon = lvHelpKON;
                }
            }
        }
        //System.out.println(lvKon.getsKog() + " " + lvKon.getsKoro());
        return lvKon;
    }
    
    /**
     * Liefert das n�chste KON-Segment nach dem Buchungsdatum
     * @return 
     */
    public KON getKONNextBuchungsdatum()
    {
        KON lvKon = null;
        KON lvHelpKON = null;

        for (int x = 0; x < ivListKON.size(); x++)
        {
            lvHelpKON = ivListKON.get(x);
            if (lvHelpKON.getAnzahlDatum() > 0)
            {
                if (lvHelpKON.getAnzahlDatum() > this.ivAnzahlBuchungsdatum)
                { // in der Zunkunft
                    if (lvKon != null)
                    {
                        // Wer liegt naeher am Buchungsdatum?
                        if (lvKon.getAnzahlDatum() > lvHelpKON.getAnzahlDatum())
                        {
                            lvKon = lvHelpKON;
                        }
                    }
                    else
                        lvKon = lvHelpKON;
                }
            }
        }
        return lvKon;
    }

    
    /**
     * F�gt ein KONTS-Segment hinzu
     * @param pvKonts
     */
    public void addKONTS(KONTS pvKonts)
    {
        ivListKONTS.add(pvKonts);   
    }
    
    /**
     * Liefert das aktuelle KONTSZI-Segment aus der Vergangenheit
     * Vergangenheit <= Buchungsdatum 
     * @return 
     */
    public KONTS getKONTSZI()
    {
        KONTS lvKontszi = null;
        KONTS lvHelpKONTSZI = null;

        for (int x = 0; x < ivListKONTS.size(); x++)
        {
            lvHelpKONTSZI = ivListKONTS.get(x);
            if (lvHelpKONTSZI.getAnzahlDatum() > 0)
            {
                if (lvHelpKONTSZI.getsKsart().equals("56"))
                {
                  if (lvHelpKONTSZI.getAnzahlDatum() <= this.ivAnzahlBuchungsdatum)
                  {
                    // anzahlDatum <= heute ...
                    if (lvKontszi != null)
                    {
                        // Wer ist j�nger?
                        if (lvKontszi.getAnzahlDatum() <= lvHelpKONTSZI.getAnzahlDatum())
                        {
                            lvKontszi = lvHelpKONTSZI;
                        }
                    }
                    else
                        lvKontszi = lvHelpKONTSZI;
                  }
                }
            }
        }
        return lvKontszi;
    }

    /**
     * Liefert das aktuelle KONTSTI-Segment aus der Vergangenheit
     * Vergangenheit <= Buchungsdatum 
     * @return 
     */
    public KONTS getKONTSTI()
    {
        KONTS lvKontsti = null;
        KONTS lvHelpKONTSTI = null;

        for (int x = 0; x < ivListKONTS.size(); x++)
        {
            lvHelpKONTSTI = ivListKONTS.get(x);
            if (lvHelpKONTSTI.getAnzahlDatum() > 0)
            {
                if (lvHelpKONTSTI.getsKsart().equals("63"))
                {
                  if (lvHelpKONTSTI.getAnzahlDatum() <= this.ivAnzahlBuchungsdatum)
                  {
                    // anzahlDatum <= heute ...
                    if (lvKontsti != null)
                    {
                        // Wer ist j�nger?
                        if (lvKontsti.getAnzahlDatum() <= lvHelpKONTSTI.getAnzahlDatum())
                        {
                            lvKontsti = lvHelpKONTSTI;
                        }
                    }
                    else
                        lvKontsti = lvHelpKONTSTI;
                  }
                }
            }
        }
        return lvKontsti;
    }

    /**
     * F�gt ein KTOZB-Segment hinzu
     * @param pvKtozb
     */
    public void addKTOZB(KTOZB pvKtozb)
    {
        ivListKTOZB.add(pvKtozb);   
    }

    /**
     * Liefert das aktuelle KTOZB-Segment aus der Vergangenheit
     * Vergangenheit <= Buchungsdatum
     * @return 
     */
    public KTOZB getKTOZB()
    {
        KTOZB lvKtozb = null;
        KTOZB lvHelpKTOZB = null;

        for (int x = 0; x < ivListKTOZB.size(); x++)
        {
            lvHelpKTOZB = ivListKTOZB.get(x);
            //System.out.println("AnzahlDatum: " + helpKTOZB.getAnzahlDatum());
            if (lvHelpKTOZB.getAnzahlDatum() > 0)
            {
                //System.out.println(helpKTOZB.getAnzahlDatum() + " <= " + this.anzahlBuchungsdatum);
                if (lvHelpKTOZB.getAnzahlDatum() <= this.ivAnzahlBuchungsdatum)
                { // anzahlDatum <= heute ...
                    if (lvKtozb != null)
                    {
                        // Wer ist j�nger?
                        if (lvKtozb.getAnzahlDatum() <= lvHelpKTOZB.getAnzahlDatum())
                        {
                            lvKtozb = lvHelpKTOZB;
                        }
                    }
                    else
                        lvKtozb = lvHelpKTOZB;
                }
            }
        }
        //if (ktozb != null)
        //{
        //  System.out.println("ktozb: " + ktozb.toString());
        //}
        return lvKtozb;
    }

    /**
     * Liefert das n�chste KTOZB-Segment nach dem Buchungsdatum
     * @return 
     */
    public KTOZB getKTOZBNextBuchungsdatum()
    {
        KTOZB lvKtozb = null;
        KTOZB lvHelpKTOZB = null;

        for (int x = 0; x < ivListKTOZB.size(); x++)
        {
            lvHelpKTOZB = ivListKTOZB.get(x);
            if (lvHelpKTOZB.getAnzahlDatum() > 0)
            {
                if (lvHelpKTOZB.getAnzahlDatum() > this.ivAnzahlBuchungsdatum)
                { // in der Zunkunft
                    if (lvKtozb != null)
                    {
                        // Wer liegt n�her am Buchungsdatum?
                        if (lvKtozb.getAnzahlDatum() > lvHelpKTOZB.getAnzahlDatum())
                        {
                            lvKtozb = lvHelpKTOZB;
                        }
                    }
                    else
                        lvKtozb = lvHelpKTOZB;
                }
            }
        }
        return lvKtozb;
    }
    
    /**
     * Liefert KTR-Segment
     * @return 
     */
    public KTR getKTR()
    {
        return this.ivKtr;
    }

    /**
     * Setzt KTR-Segment
     * @param pvKtr
     */
    public void setKTR(KTR pvKtr)
    {
        this.ivKtr = pvKtr;
    }
    
    /**
     * Liefert KTS-Segment
     * @return 
     */
    public KTS getKTS()
    {
        return this.ivKts;
    }
    
    /**
     * Setzt KTS-Segment
     * @param pvKts
     */
    public void setKTS(KTS pvKts)
    {
        this.ivKts = pvKts;
    }
    
    /**
     * Setzt OBJ-Segment
     * @param pvObj
     */
    public void setOBJ(OBJ pvObj)
    {
        this.ivObj = pvObj;   
    }

    /**
     * Liefert OBJ-Segment
     * @return 
     */
    public OBJ getOBJ()
    {
        return this.ivObj;    
    }
    
    /**
     * Setzt REC-Segment
     * @param pvRec
     */
    public void setREC(REC pvRec)
    {
        this.ivRec = pvRec;   
    }
 
    /**
     * Liefert REC-Segment
     * @return 
     */
    public REC getREC()
    {
        return this.ivRec;  
    }    

    /**
     * Setzt UMS-Segment
     * @param pvUms
     */
    public void setUMS(UMS pvUms)
    {
        this.ivUms = pvUms;   
    }

    /**
     * Liefert UMS-Segment
     * @return 
     */
    public UMS getUMS()
    {
        return ivUms;
    }    
    
    /**
     * Setzt fuer das Datum 01.01.1111 das Bewilligungsdatum ein
     */
    public void pruefeDatum() 
    {
        //System.out.println("Pruefe Datum:");
        
        // KON-Segment
        KON lvHelpKON = null;
        for (int x = 0; x < ivListKON.size(); x++)
        {
            lvHelpKON = ivListKON.get(x);
            if (lvHelpKON.getsKog().equals("11110101"))
            {
                //System.out.println("Neues Datum: " + getKTS().getsDdb());
                lvHelpKON.setsKog(getKTS().getsDdb());
            }
        }
        
        // KONTS-Segment
        KONTS lvHelpKONTS = null;
        for (int x = 0; x < ivListKONTS.size(); x++)
        {
            lvHelpKONTS = ivListKONTS.get(x);
            if (lvHelpKONTS.getsKsg().equals("11110101"))
            {
                lvHelpKONTS.setsKsg(getKTS().getsDdb());
            }
        }
            
        // KTOZB-Segment
        KTOZB lvHelpKTOZB = null;
        for (int x = 0; x < ivListKTOZB.size(); x++)
        {
            lvHelpKTOZB = ivListKTOZB.get(x);
            if (lvHelpKTOZB.getsZbg().equals("11110101"))
            {
                lvHelpKTOZB.setsZbg(getKTS().getsDdb());
            }
        }
    }

    /**
     * Pr�ft auf ZukunftsKONTSTI
     * @return 
     */
    public boolean existsZukunftsKONTSTI()
    {
        KONTS lvHelpKONTSTI = null;

        for (int x = 0; x < ivListKONTS.size(); x++)
        {
            lvHelpKONTSTI = ivListKONTS.get(x);
            if (lvHelpKONTSTI.getAnzahlDatum() > 0)
            {
                if (lvHelpKONTSTI.getsKsart().equals("63"))
                {
                  if (lvHelpKONTSTI.getAnzahlDatum() > this.ivAnzahlBuchungsdatum)
                  {
                      return true;
                  }
                }
            }
        }
        return false;
    }
    
    /**
     * Pr�ft auf ZukunftsKONTSZI
     * @return 
     */
    public boolean existsZukunftsKONTSZI()
    {
        KONTS lvHelpKONTSZI = null;

        for (int x = 0; x < ivListKONTS.size(); x++)
        {
            lvHelpKONTSZI = ivListKONTS.get(0);
            if (lvHelpKONTSZI.getAnzahlDatum() > 0)
            {
                if (lvHelpKONTSZI.getsKsart().equals("56"))
                {
                  if (lvHelpKONTSZI.getAnzahlDatum() > this.ivAnzahlBuchungsdatum)
                  {
                      return true;
                  }
                }
            }
        }
        return false;   
    }
    
    /**
     * Pr�ft auf ein Dummy-Segment
     * @return 
     */
    public boolean existsDummySegment()
    {
        // BAUFI-Segment
        if (ivBaufi != null)
        {
            if (ivBaufi.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                return true;
            }
        }
        
        // INF-Segmente
        // weglassen CT 14.11.2011
        //INF helpINF;
        //for (int i = 0; i < listINF.size(); i++)
        //{
        //    helpINF = listINF.get(i);
        //    if (helpINF.getKopf().getsDwhtdb().equals("DUMMY"))
        //    {
        //        return true;
        //    }
        //}
        
        // KON-Segmente
        KON lvHelpKON;
        for (int i = 0; i < ivListKON.size(); i++)
        {
            lvHelpKON = ivListKON.get(i);
            if (lvHelpKON.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                return true;
            }
        }
        
        // KONTS-Segmente
        KONTS lvHelpKONTS;
        for (int i = 0; i < ivListKONTS.size(); i++)
        {
            lvHelpKONTS = ivListKONTS.get(i);
            if (lvHelpKONTS.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                return true;
            }
        }
        
        // KTOZB-Segmente
        KTOZB lvHelpKTOZB;
        for (int i = 0; i < ivListKTOZB.size(); i++)
        {
            lvHelpKTOZB = ivListKTOZB.get(i);
            if (lvHelpKTOZB.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                ivLogger.error("KTOZB - Dummysegment");
                return true;
            }
        }

        // KTR-Segment
        if (ivKtr != null)
        {
            if (ivKtr.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                return true;
            }
        }
        
        // KTS-Segment
        if (ivKts != null)
        {
            if (ivKts.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                return true;
            }
        }
        
        // OBJ-Segment
        if (ivObj != null)
        {
            if (ivObj.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                return true;
            }
        }

        // REC-Segment
        if (ivRec != null)
        {
            if (ivRec.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                return true;
            }
        }

        // UMS-Segment
        if (ivUms != null)
        {
            if (ivUms.getKopf().getsDwhtdb().equals("DUMMY"))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Es wird gepr�ft, ob alle Pflichtsegmente vorhanden sind
     * @return 
     */
    public boolean existsPflichtsegmente()
    {
        boolean lvOkay = true;
        
        // OBJ-Segment muss vorhanden sein
        if (this.getOBJ() == null)
        {
            lvOkay = false;
            ivLogger.info("Konto " + ivDwhkopf.getsDwhknr() + ": OBJ-Segment nicht vorhanden...");
        }
        
        // KTS-Segment muss vorhanden sein
        if (this.getKTS() == null)
        {
            lvOkay = false;
            ivLogger.info("Konto " + ivDwhkopf.getsDwhknr() + ": KTS-Segment nicht vorhanden...");
        }
        
        // KTR-Segment muss vorhanden sein
        if (this.getKTR() == null)
        {
            lvOkay = false;
            ivLogger.info("Konto " + ivDwhkopf.getsDwhknr() + ": KTR-Segment nicht vorhanden...");
        }
        
        // KON-Segment muss vorhanden sein
        if (this.getKON() == null)
        {
            lvOkay = false;
            ivLogger.info("Konto " + ivDwhkopf.getsDwhknr() + ": KON-Segment nicht vorhanden...");
        }
        
        // KTOZB-Segment muss vorhanden sein
        if (this.getKTOZB() == null)
        {
            lvOkay = false;
            ivLogger.info("Konto " + ivDwhkopf.getsDwhknr() + ": KTOZB-Segment nicht vorhanden...");
        }
        
        // REC-Segment muss vorhanden sein
        if (this.getREC() == null)
        {
            lvOkay = false;
            ivLogger.info("Konto " + ivDwhkopf.getsDwhknr() + ": REC-Segment nicht vorhanden...");
        }
        
        // KONTSTI-Segment muss vorhanden sein
        if (this.getKONTSTI() == null)
        {
            if (!this.existsZukunftsKONTSTI())
            {
              lvOkay = false;
              ivLogger.info("Konto " + ivDwhkopf.getsDwhknr() + ": KONTSTI-Segment nicht vorhanden...");
            }
        }
        
        // KONTSZI-Segment muss vorhanden sein
        if (this.getKONTSZI() == null)
        {
            if (!this.existsZukunftsKONTSZI())
            {
              lvOkay = false;
              ivLogger.info("Konto " + ivDwhkopf.getsDwhknr() + ": KONTSZI-Segment nicht vorhanden...");
            }
        }
        
        return lvOkay;
        // BAUFI/UMS/INF nicht unbedingt
    }
        
}
