/*******************************************************************************
 * Copyright (c) 2018 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.MailVersand;
import de.nordlbit.prost.Utilities.StringKonverter;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.VgAufgabe;
import de.nordlbit.prost.bd.Vorgang;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.ereignisvektor.EreignisVektorDAO;
import de.nordlbit.prost.dao.mandanten.MandantenDAO;
import de.nordlbit.prost.dao.vorgang.VorgangDAO;

/**
 * @author Carsten Tepper
 *
 */
public class Abschluss 
{
    /**
     * Startroutine
     * @param args
     */
    public static void main(String[] args) 
    {
    	String lvFilenameINI;
        String lvMailDatei = new String();
        String lvMailEmpfaenger = new String();

    	if (args[args.length - 5].contains(".ini"))
    	{
           lvFilenameINI = args[args.length - 5];
    	}
    	else
    	{
    		if (args[args.length - 6].contains(".ini"))
    		{
    			lvFilenameINI = args[args.length - 6];
    			lvMailEmpfaenger = args[args.length - 5];
    		}
    		else
    		{
    			lvFilenameINI = args[args.length - 7];
    			lvMailEmpfaenger = args[args.length - 6];
    			lvMailDatei = args[args.length - 5];
    		}
    	}
    	
    	System.out.println("INI-Filename: " + lvFilenameINI);
    	System.out.println("Mail-Empfaenger: " + lvMailEmpfaenger);
    	System.out.println("Mail-Datei: " + lvMailDatei);
    	
        IniReader lvReader = null;
        String lvInstitut = new String();
        String lvUsername = new String();
        String lvPassword = new String();
        String lvSmtpHost = new String();
        
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
            
            lvUsername = lvReader.getPropertyString("Mail", "Username", "Fehler");
            if (lvUsername.equals("Fehler"))
            {
                System.out.println("[Mail]: Kein Mail-Username in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("Mail-Username: " + lvUsername);

            lvPassword = lvReader.getPropertyString("Mail", "Password", "Fehler");
            if (lvPassword.equals("Fehler"))
            {
                System.out.println("[Mail]: Kein Mail-Password in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("Mail-Password: " + lvPassword);

            lvSmtpHost = lvReader.getPropertyString("Mail", "SmtpHost", "Fehler");
            if (lvSmtpHost.equals("Fehler"))
            {
                System.out.println("[Mail]: Kein SMTP-Host in der 'prost.ini' hinterlegt...");
                System.exit(1);
            }
            System.out.println("SMTP-Host: " + lvSmtpHost);
 
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
        
        String lvVorgangID = args[args.length - 4];
        String lvMandant = args[args.length - 3];
        String lvEreignis = args[args.length - 2];
        String lvAufgabenVektorID = args[args.length - 1];
        
        new Abschluss(lvInstitut, lvUsername, lvPassword, lvSmtpHost, lvMailEmpfaenger, lvMailDatei, lvVorgangID, lvMandant, lvEreignis, lvAufgabenVektorID);
    }
    
    /**
     * Konstruktor mit Parametern
     * @param pvInstitut Institut
     * @param pvUsername Mail-Username
     * @param pvPassword Mail-Passwort
     * @param pvSmtpHost SMTP-Host
     * @param pvMailEmpfaenger Mailempfaengeradresse
     * @param pvMailDatei Mail-Datei
     * @param pvVorgangID ID des Vorgangs
     * @param pvMandant ID des Mandanten
     * @param pvEreignis ID des Ereignisses
     * @param pvAufgabenVektorID ID des AufgabenVektor
     */
    public Abschluss(String pvInstitut, String pvUsername, String pvPassword, String pvSmtpHost, String pvMailEmpfaenger, 
    		         String pvMailDatei, String pvVorgangID, String pvMandant, String pvEreignis, String pvAufgabenVektorID)
    {   
        MandantenDAO lvMandantenDAO = new MandantenDAO(pvInstitut);
        Mandant.setMandanten(lvMandantenDAO.getMandanten());
        Mandant lvMandant = Mandant.getMandanten().get(lvMandantenDAO.findMandantenID(pvInstitut));
        
        EreignisVektorDAO ivEreignisVektorDAO = new EreignisVektorDAO(lvMandant);     
        AufgabenVektorDAO ivAufgabenVektorDAO = new AufgabenVektorDAO(lvMandant);
        VorgangDAO ivVorgangDAO = new VorgangDAO(lvMandant);
        
        Calendar lvCal = Calendar.getInstance();
        DatenbankZugriff.openConnection(pvInstitut);
        SimpleDateFormat lvSdf = new SimpleDateFormat();
        lvSdf.setTimeZone(lvCal.getTimeZone());
        lvSdf.applyPattern( "yyyy-MM-dd HH:mm:ss" ); 

        ivEreignisVektorDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), pvVorgangID, pvMandant, pvEreignis);
        ivAufgabenVektorDAO.setEndeDatum(pvAufgabenVektorID, lvSdf.format(lvCal.getTime()));
       
        if (!pvMailEmpfaenger.isEmpty())
        {
        	MailVersand lvMailVersand = new MailVersand(pvUsername, pvPassword, pvSmtpHost, pvMailEmpfaenger);
         
        	String lvSubject = "TXS - Statusbericht";
        	String lvText;
        	if (pvMailDatei.isEmpty())
        	{
        		lvText = erstelleStatusbericht(ivAufgabenVektorDAO, ivVorgangDAO.findVorgangByID(pvVorgangID));
        	}
        	else
        	{
        		lvText = readMailDatei(pvMailDatei);
        	}
        
        	lvMailVersand.send(lvSubject, lvText);
        }
        
        DatenbankZugriff.closeConnection();
        System.exit(0);     
    }    

    /**
     * Erstellt den Statusbericht
     * @param pvAufgabenVektorDAO
     * @param pvVorgang
     * @return
     */
    public String erstelleStatusbericht(AufgabenVektorDAO pvAufgabenVektorDAO, Vorgang pvVorgang)
    {
    	StringBuilder lvHelpString = new StringBuilder();
    	HashMap<String, VgAufgabe> lvListeVgAufgaben = pvAufgabenVektorDAO.findAufgabenByVorgangID(pvVorgang);
    	for (VgAufgabe lvAufgabe:lvListeVgAufgaben.values())
    	{
        	lvHelpString.append(lvAufgabe.getAufgabe().getBezeichnung() + ": " + lvAufgabe.getAnfangZeitpunkt() + " - " + lvAufgabe.getEndeZeitpunkt() + StringKonverter.lineSeparator);	
    	}
    	
    	return lvHelpString.toString();
    }
    
    /**
     * Liest die Mail-Datei ein
     * @param Dateiname der Mail-Datei
     * @return
     */
    private String readMailDatei(String pvDateiname)
    {
    	// Oeffnen der Dateien
    	FileInputStream lvFis = null;
    	File lvFileMailDatei = new File(pvDateiname);
    	try
    	{
    		lvFis = new FileInputStream(lvFileMailDatei);
    	}
    	catch (Exception e)
    	{
    		System.out.println("Konnte die Mail-Datei nicht oeffnen!");
    		return new String();
    	}

    	BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        StringBuilder lvText = new StringBuilder(); 
        String lvZeile;
    	
    	try
    	{
    		while ((lvZeile = lvIn.readLine()) != null)  // Lesen der Mail-Datei
    		{
    			lvText.append(lvZeile + StringKonverter.lineSeparator);
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println("Fehler beim Einlesen der Mail-Datei!");
    		e.printStackTrace();
    	}
        
    	// Schliessen der Mail-Datei
    	try
    	{
    		lvIn.close();
    	}
    	catch (Exception e)
    	{
    		System.out.println("Konnte die Mail-Datei nicht schliessen!");
    	}  
    	
    	return lvText.toString();
    }
}
