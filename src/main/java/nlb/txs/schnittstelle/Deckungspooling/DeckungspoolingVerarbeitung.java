/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import nlb.txs.schnittstelle.Deckungspooling.Kunde.OSPKundenVerarbeitung;
import nlb.txs.schnittstelle.Deckungspooling.OSPInstitut.OSPInstitutDaten;
import nlb.txs.schnittstelle.Deckungspooling.OSPInstitut.OSPInstitutListe;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPSicherheitenVerarbeitung;
import nlb.txs.schnittstelle.Filtern.ListeObjekte;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
@Deprecated
public class DeckungspoolingVerarbeitung 
{
    /**
     * 
     */
    private OSPSicherheitenVerarbeitung ivSicherheitenVerarbeitung;
    
    /**
     * Liste von OSPInstituten
     */
    private OSPInstitutListe lvListeOSPInstitute;
         
    /**
     * Konstruktor
     * @param pvInstitut 
     * @param pvImportVerzeichnis 
     * @param pvExportVerzeichnis 
     * @param pvOSPKundenDatei 
     * @param pvTXSKundenDatei 
     * @param pvOSPSicherheitenDatei 
     * @param pvQuellsystemeDatei 
     * @param pvLogger 
     */
    public DeckungspoolingVerarbeitung(String pvInstitut, String pvImportVerzeichnis, String pvExportVerzeichnis, 
                                       String pvOSPKundenDatei, String pvTXSKundenDatei, String pvOSPSicherheitenDatei,
                                       String pvQuellsystemeDatei, Logger pvLogger) 
    { 
        pvLogger.info("Start Deckungspooling");
        
        // Quellsysteme einlesen
        ListeObjekte lvListeQuellsysteme = new ListeObjekte();
        
        // Oeffnen der Dateien
        File ivFileQuellsysteme = new File(pvImportVerzeichnis + "\\" + pvQuellsystemeDatei);
        FileInputStream lvFis = null;
        try
        {
            lvFis = new FileInputStream(ivFileQuellsysteme);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Quellsysteme-Datei nicht oeffnen!");
            return;
        }
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
          
        String lvZeile = new String();
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen Quellsysteme-Datei
            {
                lvListeQuellsysteme.addObjekt(lvZeile.trim()); 
            }
        }
        catch (Exception e)
        {
          System.out.println("Fehler beim Verarbeiten einer Zeile der Quellsysteme-Datei!");
          e.printStackTrace();
        }
        
        try
        {
          lvIn.close();
        }
        catch (Exception e)
        {
          System.out.println("Konnte Quellsysteme-Datei nicht schliessen!");
        }

        System.out.println(lvListeQuellsysteme.size() + " Quellsysteme eingelesen...");

        // Liste OSPInstitute initialisieren
        lvListeOSPInstitute = new OSPInstitutListe();

        OSPKundenVerarbeitung lvKundenDPP = new OSPKundenVerarbeitung(lvListeOSPInstitute, pvImportVerzeichnis, pvExportVerzeichnis,                
                                                                    pvOSPKundenDatei, pvTXSKundenDatei, lvListeQuellsysteme, pvLogger);
        
        // Sicherheiteninformationen aus OSPlus einlesen
        ivSicherheitenVerarbeitung = new OSPSicherheitenVerarbeitung(lvListeOSPInstitute, pvImportVerzeichnis, pvExportVerzeichnis,
                                                                   pvOSPSicherheitenDatei, pvLogger);                    
        
        if (checkStatistiken())
        {
            System.out.println("Fehler in den Statistikdaten!!!");
        }
        
        pvLogger.info("Ende Deckungspooling");
        
      }

    /**
     * @return the listeOSPInstitute
     */
    public OSPInstitutListe getListeOSPInstitute() 
    {
        return this.lvListeOSPInstitute;
    }

    /**
     * @param pvListeOSPInstitute the listeOSPInstitute to set
     */
    public void setListeOSPInstitute(OSPInstitutListe pvListeOSPInstitute) 
    {
        this.lvListeOSPInstitute = pvListeOSPInstitute;
    }   
    
    /**
     * �berpr�ft die Statistikdaten mit den eingelesen Datenanzahlen
     * @return 
     */
    public boolean checkStatistiken()
    {
        boolean lvOkay = true;
        OSPInstitutDaten lvHelpInst;

        for (int x = 0; x < lvListeOSPInstitute.size(); x++)
        {
            lvHelpInst = lvListeOSPInstitute.get(x);
            lvOkay = lvHelpInst.checkStatistik();
        }
        
        return lvOkay;
    }
    
    /**
     * Liefert die Institutskennung
     * @param pvKontonummer 
     * @return 
     */
    public String getInstitutskennung(String pvKontonummer)
    {
        String lvHelpKennung = new String();
        OSPInstitutDaten lvHelpInst;
        System.out.println("getInstitutskennung - Size: " + lvListeOSPInstitute.size());
        
        for (int x = 0; x < lvListeOSPInstitute.size(); x++)
        {
            lvHelpInst = lvListeOSPInstitute.get(x);
            System.out.println("OSPInstitut " + lvHelpInst.getRegKz() + lvHelpInst.getOSPInst());
            if (!lvHelpInst.getInstitutskennung(pvKontonummer).isEmpty())
            {
              lvHelpKennung = lvHelpInst.getInstitutskennung(pvKontonummer);
            }
        }
     
        return lvHelpKennung;
    }
  
    /**
     * Liefert die Kundennummer
     * @param pvKontonummer 
     * @return 
     */
    public String getKundennummer(String pvKontonummer)
    {
        String lvHelpKd = new String();
        OSPInstitutDaten lvHelpInst;
        //System.out.println("getKundennummer - Size: " + listeOSPInstitute.size());
        
        for (int x = 0; x < lvListeOSPInstitute.size(); x++)
        {
            lvHelpInst = lvListeOSPInstitute.get(x);
            if (!lvHelpInst.getInstitutskennung(pvKontonummer).isEmpty())
            {
              lvHelpKd = lvHelpInst.getKundennummer(pvKontonummer);
            }
        }
     
        return lvHelpKd;
    }

    /**
     * @param pvSicherheitenVerarbeitung the sicherheitenVerarbeitung to set
     */
    public void setSicherheitenVerarbeitung(OSPSicherheitenVerarbeitung pvSicherheitenVerarbeitung) {
        this.ivSicherheitenVerarbeitung = pvSicherheitenVerarbeitung;
    }

    /**
     * @return the sicherheitenVerarbeitung
     */
    public OSPSicherheitenVerarbeitung getSicherheitenVerarbeitung() {
        return ivSicherheitenVerarbeitung;
    }
}
