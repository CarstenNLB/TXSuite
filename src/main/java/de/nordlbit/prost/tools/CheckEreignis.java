package de.nordlbit.prost.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.mandanten.MandantenDAO;

public class CheckEreignis 
{
    /**
     * Startroutine
     * @param args
     */
    public static void main(String[] args) 
    {
        String lvFilenameINI = args[args.length - 5];
        IniReader lvReader = null;
        String lvInstitut = new String();
        try 
        {
            lvReader = new IniReader(lvFilenameINI);
                        
            lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (lvInstitut.equals("Fehler"))
            {
                System.out.println("[Konfiguration]: Kein Institut in der 'prost.ini'...");
                System.exit(1);
            }
            System.out.println("Institut: " + lvInstitut);            
        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Laden der 'prost.ini'...");
            System.exit(1);
        }
                
        //Datenbankparameter einlesen
        if (!DatenbankZugriff.readParameter(lvFilenameINI, lvInstitut))
        {
            System.out.println("[Datenbank]: Fehler beim Laden der 'prost.ini'...");
            System.exit(1);
        }      
        
        MandantenDAO lvMandantenDAO = new MandantenDAO(lvInstitut);
        Mandant.setMandanten(lvMandantenDAO.getMandanten());
        Mandant lvMandant = Mandant.getMandanten().get(lvMandantenDAO.findMandantenID(lvInstitut));
                
        //EreignisVektorDAO ivEreignisVektorDAO = new EreignisVektorDAO(lvMandant);     
        AufgabenVektorDAO ivAufgabenVektorDAO = new AufgabenVektorDAO(lvMandant);
        
        Calendar lvCal = Calendar.getInstance();
        DatenbankZugriff.openConnection(lvInstitut);
        SimpleDateFormat lvSdf = new SimpleDateFormat();
        lvSdf.setTimeZone(lvCal.getTimeZone());
        lvSdf.applyPattern( "yyyy-MM-dd HH:mm:ss" ); 

        //ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), args[args.length - 4], args[args.length - 3], args[args.length - 2]);
        //ivAufgabenVektorDAO.setEndeDatum(args[args.length - 1], lvSdf.format(lvCal.getTime()));
        if (!ivAufgabenVektorDAO.isEndeDatumNULL(lvInstitut, args[args.length - 1]))
        {
        	System.out.println("Aufgabe erledigt...");
        	System.exit(0);
        }
        else
        {
        	System.out.println("Aufgabe noch offen...");
        	System.exit(1);
        }
        
        DatenbankZugriff.closeConnection();
        //System.exit(0);     
    }    

}
