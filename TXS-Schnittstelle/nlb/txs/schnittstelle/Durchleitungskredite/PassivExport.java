/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Durchleitungskredite;

import java.io.File;
import java.io.FileOutputStream;

import nlb.txs.schnittstelle.Darlehen.Daten.DarlehenKomplett;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class PassivExport 
{
    /**
     * 
     */
    private FileOutputStream ivFosDarlehenExport;

    /**
     * 
     */
    private Logger LOGGER_DLK;
    
    /**
     * Konstruktor
     * @param pvExportVerzeichnis 
     * @param pvDarlehenExportDatei 
     * @param pvLOGGER_DLK 
     */
    public PassivExport(String pvExportVerzeichnis, String pvDarlehenExportDatei, Logger pvLOGGER_DLK)
    {
        LOGGER_DLK = pvLOGGER_DLK;
        
        // ExportDatei oeffnen
        File lvFileDarlehenExport = new File(pvExportVerzeichnis + "\\" + pvDarlehenExportDatei);
        try
        {
            ivFosDarlehenExport = new FileOutputStream(lvFileDarlehenExport);
        }
        catch (Exception e)
        {
            System.out.println("Konnte DarlehenExport-Datei nicht oeffnen!");
        }
        
        // Headerzeile in DarlehenExport-Datei schreiben
        try
        {
            ivFosDarlehenExport.write(("KONTONUMMER;PASSIVKONTONUMMER;KUNDENNUMMER;KUNDENNAME;PRODUKTSCHLUESSEL;MERKMAL_PASSIV;ZUSATZANGABE1;ZUSATZANGABE2;KUST_OE;VERWALTENDE_OE;DECKUNGSSCHLUESSEL;VERTRAG_BIS;RESTKAPITAL;WAEHRUNG;RESTKAPITAL_EUR;AUSPLATZIERUNGSMERKMAL;BUCHUNGSDATUM;" + StringKonverter.lineSeparator).getBytes());

        }
        catch (Exception exp)
        {
            System.out.println("Konnte Headerzeile nicht in die DarlehenExport-Datei schreiben.");
        }           
    }
    
    /**
     * Schliessen der DarlehenExport-Datei
     */
    public void close()
    {
        // DarlehenExport-Datei schliessen
        try
        {
            ivFosDarlehenExport.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen der DarlehenExport-Datei");
        }
    }
    
    /**
     * Verarbeite das Darlehen
     * @param pvOriginalDarlehen 
     * @param pvBuchungsdatum 
     * @param pvKundenname 
     * @return 
     */
    public boolean export(DarlehenKomplett pvOriginalDarlehen, String pvBuchungsdatum, String pvKundenname)
    { 
        boolean lvVerarbeitet = false;
        if (!(StringKonverter.convertString2Double(pvOriginalDarlehen.getKTS().getsDrk()) == 0.0))
        {
          String lvProduktschluessel = pvOriginalDarlehen.getKTS().getsDpd();
          if (lvProduktschluessel.equals("05201") || lvProduktschluessel.equals("05202") || lvProduktschluessel.equals("05203"))
          {
            LOGGER_DLK.info("Kontonummer: " + pvOriginalDarlehen.getKTR().getKopf().getsDwhknr()); 
            try
            {
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTR().getKopf().getsDwhknr() + ";").getBytes());
              // Objektnummer - Ist nicht Bestandteil
              //ivFosDarlehenExport.write((pvOriginalDarlehen.getKTR().getKopf().getsDwhonr() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTS().getsDpk() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTR().getKopf().getsDwhkdnr() + ";").getBytes());
              ivFosDarlehenExport.write((pvKundenname + ";").getBytes());
              ivFosDarlehenExport.write((lvProduktschluessel + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getREC().getsNkzakpa() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTR().getsDzus1() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTR().getsDzus2() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getOBJ().getsOks() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTS().getsDvs() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTR().getsDd() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTR().getsDdvb().substring(6) + "." + pvOriginalDarlehen.getKTR().getsDdvb().substring(4, 6) + "." + pvOriginalDarlehen.getKTR().getsDdvb().substring(0, 4)+ ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTS().getsDrk() + ";").getBytes());
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTS().getsDwiso() + ";").getBytes());
              if (pvOriginalDarlehen.getKTS().getsDwiso().equals("EUR"))
              {
                ivFosDarlehenExport.write((pvOriginalDarlehen.getKTS().getsDrk() + ";").getBytes());  
              }
              else
              {
                ivFosDarlehenExport.write((";").getBytes());
              }
              ivFosDarlehenExport.write((pvOriginalDarlehen.getKTR().getAusplatzierungsmerkmal() + ";").getBytes());
              ivFosDarlehenExport.write((pvBuchungsdatum.substring(6) + "." + pvBuchungsdatum.substring(4, 6) + "." + pvBuchungsdatum.substring(0, 4)+ ";" + StringKonverter.lineSeparator).getBytes());
              lvVerarbeitet = true;
            }
            catch (Exception exp)
            {
              LOGGER_DLK.error("Konnte Daten nicht in DarlehenExport-Datei schreiben");
            }
          }
        }
        else
        {
            LOGGER_DLK.info("Kontonummer: " + pvOriginalDarlehen.getKTR().getKopf().getsDwhknr() + " - Restkapital: " + pvOriginalDarlehen.getKTS().getsDrk());
        }
        
        return lvVerarbeitet;
    }

}
