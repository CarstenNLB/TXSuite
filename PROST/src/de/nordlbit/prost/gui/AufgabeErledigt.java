package de.nordlbit.prost.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.VgAufgabe;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.mandanten.MandantenDAO;
import de.nordlbit.prost.dao.vorgang.VorgangDAO;

public class AufgabeErledigt 
{
   /** Startroutine
     * @param args
     */
   public static void main(String[] args) 
   {
       String lvFilenameINI = args[args.length - 1];
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
       
       //EreignisVektorDAO ivEreignisVektorDAO = new EreignisVektorDAO(lvMandant);     
       AufgabenVektorDAO ivAufgabenVektorDAO = new AufgabenVektorDAO(lvMandant);
       VorgangDAO ivVorgangDAO = new VorgangDAO(lvMandant);
       
       Calendar lvCal = Calendar.getInstance();
       DatenbankZugriff.openConnection(lvInstitut);
       SimpleDateFormat lvSdf = new SimpleDateFormat();
       lvSdf.setTimeZone(lvCal.getTimeZone());
       lvSdf.applyPattern( "yyyy-MM-dd HH:mm:ss" ); 

       System.out.println("max VorgangID: " + ivVorgangDAO.getMaxId());
       //ArrayList<String> lvListe = ivAufgabenVektorDAO.getAllEndeDatumNULL(lvInstitut, ivVorgangDAO.getMaxId());
       HashMap<String, VgAufgabe> lvListe = ivAufgabenVektorDAO.findAufgabenByVorgangID(ivVorgangDAO.findVorgangByID(ivVorgangDAO.getMaxId()));
       //for (String lvID:lvListe)
       //{
       //	   System.out.println("ID:" + lvID);
    	   
       //}
       //ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), args[args.length - 4], args[args.length - 3], args[args.length - 2]);
       //ivAufgabenVektorDAO.setEndeDatum(args[args.length - 1], lvSdf.format(lvCal.getTime()));
       DatenbankZugriff.closeConnection();
       System.exit(0);     
   }    

}
