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
@Deprecated
public class AktivExport 
{
    /**
     * 
     */
    private FileOutputStream ivFosDarlehenExport;

    /**
     * 
     */
    private FileOutputStream ivFosDarlehenKonsortialExport;
    
    /**
     * 
     */
    private Logger LOGGER_DLK;
    
    /**
     * Konstruktor
     * @param pvExportVerzeichnis 
     * @param pvDarlehenExportDatei 
     * @param pvDarlehenKonsortialExportDatei 
     * @param pvLOGGER_DLK 
     */
    public AktivExport(String pvExportVerzeichnis, String pvDarlehenExportDatei, String pvDarlehenKonsortialExportDatei, Logger pvLOGGER_DLK)
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
        
        // DarlehenKonsortialExportDatei oeffnen
        File lvFileDarlehenKonsortialExport = new File(pvExportVerzeichnis + "\\" + pvDarlehenKonsortialExportDatei);
        try
        {
            ivFosDarlehenKonsortialExport = new FileOutputStream(lvFileDarlehenKonsortialExport);
        }
        catch (Exception e)
        {
            System.out.println("Konnte DarlehenKonsortialExport-Datei nicht oeffnen!");
        }        
        
        // Headerzeile in DarlehenExport-Datei schreiben
        try
        {
            ivFosDarlehenExport.write(("KONTONUMMER;PASSIVKONTONUMMER;KUNDENNUMMER;KUNDENNAME;PRODUKTSCHLUESSEL;MERKMAL_PASSIV;ZUSATZANGABE1;ZUSATZANGABE2;KUST_OE;VERWALTENDE_OE;DECKUNGSSCHLUESSEL;VERTRAG_BIS;RESTKAPITAL;WAEHRUNG;RESTKAPITAL_EUR;AUSPLATZIERUNGSMERKMAL;BUCHUNGSDATUM;" + StringKonverter.lineSeparator).getBytes());
            ivFosDarlehenKonsortialExport.write(("KONTONUMMER;PASSIVKONTONUMMER;KUNDENNUMMER;KUNDENNAME;PRODUKTSCHLUESSEL;MERKMAL_PASSIV;ZUSATZANGABE1;ZUSATZANGABE2;KUST_OE;VERWALTENDE_OE;DECKUNGSSCHLUESSEL;VERTRAG_BIS;RESTKAPITAL;WAEHRUNG;RESTKAPITAL_EUR;AUSPLATZIERUNGSMERKMAL;BUCHUNGSDATUM;" + StringKonverter.lineSeparator).getBytes());
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
        
        // DarlehenKonsortialExport-Datei schliessen
        try
        {
            ivFosDarlehenKonsortialExport.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen der DarlehenKonsortialExport-Datei");
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
          if (lvProduktschluessel.equals("01002") || lvProduktschluessel.equals("01003") || lvProduktschluessel.equals("01004") ||
              lvProduktschluessel.equals("01005") || lvProduktschluessel.equals("01006") || lvProduktschluessel.equals("01007") ||
              lvProduktschluessel.equals("01008"))
          {   
            LOGGER_DLK.info("Kontonummer: " + pvOriginalDarlehen.getKTR().getKopf().getsDwhknr()); 
            if (StringKonverter.convertString2Double(pvOriginalDarlehen.getKTS().getsDrk()) > 0.0)
            {
                LOGGER_DLK.info("Konsortial - Restkapital: " + pvOriginalDarlehen.getKTS().getsDrk() + " Kompensationsschluessel: " + pvOriginalDarlehen.getKTS().getsDkom());
                try
                {
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTR().getKopf().getsDwhknr() + ";").getBytes());
                  // Objektnummer - Ist nicht Bestandteil
                  //ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTR().getKopf().getsDwhonr() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTS().getsDpk() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTR().getKopf().getsDwhkdnr() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvKundenname + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((lvProduktschluessel + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getREC().getsNkzakpa() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTR().getsDzus1() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTR().getsDzus2() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getOBJ().getsOks() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTS().getsDvs() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTR().getsDd() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTR().getsDdvb().substring(6) + "." + pvOriginalDarlehen.getKTR().getsDdvb().substring(4, 6) + "." + pvOriginalDarlehen.getKTR().getsDdvb().substring(0, 4)+ ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTS().getsDrk() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTS().getsDwiso() + ";").getBytes());
                  if (pvOriginalDarlehen.getKTS().getsDwiso().equals("EUR"))
                  {
                    ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTS().getsDrk() + ";").getBytes());  
                  }
                  else
                  {
                    ivFosDarlehenKonsortialExport.write((";").getBytes());
                  }
                  ivFosDarlehenKonsortialExport.write((pvOriginalDarlehen.getKTR().getAusplatzierungsmerkmal() + ";").getBytes());
                  ivFosDarlehenKonsortialExport.write((pvBuchungsdatum.substring(6) + "." + pvBuchungsdatum.substring(4, 6) + "." + pvBuchungsdatum.substring(0, 4)+ ";" + StringKonverter.lineSeparator).getBytes());
                }
                catch (Exception exp)
                {
                  LOGGER_DLK.error("Konnte Daten nicht in DarlehenKonsortialExport-Datei schreiben");
                } 
            }
            else
            {
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
              }
              catch (Exception exp)
              {
                LOGGER_DLK.error("Konnte Daten nicht in DarlehenExport-Datei schreiben");
              }
            }
            lvVerarbeitet = true;
          }
        }
        else
        {
            LOGGER_DLK.info("Kontonummer: " + pvOriginalDarlehen.getKTR().getKopf().getsDwhknr() + " - Restkapital: " + pvOriginalDarlehen.getKTS().getsDrk());
        }
        
        return lvVerarbeitet;
    }

}
