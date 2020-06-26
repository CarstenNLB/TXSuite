/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.dao.BuchungstagVerwaltung;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.ereignisvektor.EreignisVektorDAO;
import de.nordlbit.prost.dao.mandanten.MandantenDAO;

/**
 * @author tepperc
 *
 */
public class Ultimo 
{
    
    // Ultimos
    private static boolean svMonatsultimo = false;
    private static boolean svQuartalsultimo = false;
    private static boolean svJahresultimo = false;
    
    /**
     * Startroutine
     * @param args
     */
    public static void main(String[] args) 
    {
        // 1. Parameter: INI-Datei
        System.out.println("INI-Datei: " + args[args.length - 8]);
        // 2. Parameter: Vorgang-ID
        System.out.println("Vorgang-ID: " + args[args.length - 7]);
        // 3. Parameter: Mandant-ID
        System.out.println("Mandant-ID: " + args[args.length - 6]);
        // 4. Parameter: Ereignis-ID
        System.out.println("Ereignis-ID: " + args[args.length - 5]);
        // 5. Parameter: AufgabenVektor-ID
        System.out.println("AufgabenVektor-ID: " + args[args.length - 4]);
        // 6. Parameter: Buchungstag
        System.out.println("Buchungstag: " + args[args.length - 3]);
        // 7. Parameter: Ultimokennzeichen
        System.out.println("Ultimokennzeichen: " + args[args.length - 2]);
        // 8. Parameter: Gleich/Ungleich-Kennzeichen
        System.out.println("Gleich/Ungleich: " + args[args.length - 1]);
        
        String lvFilenameINI = args[args.length - 8];
        IniReader lvReader = null;
        String lvInstitut = new String();
        //String lvMandantenname = new String();
        try 
        {
            lvReader = new IniReader(lvFilenameINI);
                        
            lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (lvInstitut.equals("Fehler"))
            {
                System.out.println("[Konfiguration]: Kein Institut in der 'prost.ini'...");
            }
            System.out.println("Institut: " + lvInstitut);
        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Laden der 'prost.ini'...");
            System.exit(0);
        }
                
        //Datenbankparameter einlesen
        if (!DatenbankZugriff.readParameter(lvFilenameINI, lvInstitut))
        {
            System.out.println("[Datenbank]: Fehler beim Laden der 'prost.ini'...");
            System.exit(0);
        }      
        
        MandantenDAO lvMandantenDAO = new MandantenDAO(lvInstitut);
        Mandant.setMandanten(lvMandantenDAO.getMandanten());
        Mandant lvMandant = Mandant.getMandanten().get(lvMandantenDAO.findMandantenID(lvInstitut));
        
        EreignisVektorDAO ivEreignisVektorDAO = new EreignisVektorDAO(lvMandant);     
        AufgabenVektorDAO ivAufgabenVektorDAO = new AufgabenVektorDAO(lvMandant);
        BuchungstagVerwaltung lvBuchungstagVerwaltung = new BuchungstagVerwaltung(lvMandant);
                
        Calendar lvCal = Calendar.getInstance();
        DatenbankZugriff.openConnection(lvInstitut);
        SimpleDateFormat lvSdf = new SimpleDateFormat();
        lvSdf.setTimeZone(lvCal.getTimeZone());
        lvSdf.applyPattern( "yyyy-MM-dd HH:mm:ss" ); 
        
        String lvBuchungstag = args[args.length - 3];
        
        svMonatsultimo = lvBuchungstagVerwaltung.isUltimo(lvBuchungstag, lvMandant.getInstitutsId(), true, false, false);
        svQuartalsultimo = lvBuchungstagVerwaltung.isUltimo(lvBuchungstag, lvMandant.getInstitutsId(), false, true, false); 
        svJahresultimo = lvBuchungstagVerwaltung.isUltimo(lvBuchungstag, lvMandant.getInstitutsId(), false, false, true);        
        
        String lvGleichUngleich = args[args.length - 1];
        String lvUltimo = args[args.length - 2];
        if (lvGleichUngleich.equalsIgnoreCase("GLEICH"))
        {
          if (lvUltimo.equalsIgnoreCase("MONATSULTIMO") && svMonatsultimo) 
          {
            ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), args[args.length - 7], args[args.length - 6], args[args.length - 5]);    
          }
          if (lvUltimo.equalsIgnoreCase("QUARTALSULTIMO") && svQuartalsultimo) 
          {
            ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), args[args.length - 7], args[args.length - 6], args[args.length - 5]);            
          } 
          if (lvUltimo.equalsIgnoreCase("JAHRESULTIMO") && svJahresultimo) 
          {
            ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), args[args.length - 7], args[args.length - 6], args[args.length - 5]);            
          }
          ivAufgabenVektorDAO.setEndeDatum(args[args.length - 4], lvSdf.format(lvCal.getTime()));
        }
        if (lvGleichUngleich.equalsIgnoreCase("UNGLEICH"))
        {
            if (lvUltimo.equalsIgnoreCase("MONATSULTIMO") && !svMonatsultimo) 
            {
              ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), args[args.length - 7], args[args.length - 6], args[args.length - 5]);    
            }
            if (lvUltimo.equalsIgnoreCase("QUARTALSULTIMO") && !svQuartalsultimo) 
            {
              ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), args[args.length - 7], args[args.length - 6], args[args.length - 5]);            
            } 
            if (lvUltimo.equalsIgnoreCase("JAHRESULTIMO") && !svJahresultimo) 
            {
              ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), args[args.length - 7], args[args.length - 6], args[args.length - 5]);            
            }
            ivAufgabenVektorDAO.setEndeDatum(args[args.length - 4], lvSdf.format(lvCal.getTime()));            
        }
        DatenbankZugriff.closeConnection();
        System.exit(0);     
    }    

}
