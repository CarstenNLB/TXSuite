package de.nordlbit.prost.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.MailVersand;
import de.nordlbit.prost.Utilities.StringKonverter;
import de.nordlbit.prost.dao.DatenbankZugriff;

public class SendMail 
{
    /**
     * Startroutine
     * 4 Parameter: INI-Filename Mail-Empfaenger Mail-Subject Mail-Dateiname
     * @param args 
     */
    public static void main(String[] args) 
    {
    	if (args.length != 4)
    	{
    		System.out.println("Nicht genuegend Parameter angegeben.");
    		System.out.println("SendMail <INI-Dateiname> <Mail-Empfaenger> <Mail-Subject> <Mail-Dateiname>");
    		System.exit(1);
    	}  	
    	
        String lvFilenameINI = args[args.length - 4];
        String lvMailEmpfaenger = args[args.length - 3];
        String lvMailSubject = args[args.length - 2];
        String lvMailDatei = args[args.length - 1];
    	
    	System.out.println("INI-Filename: " + lvFilenameINI);
    	System.out.println("Mail-Empfaenger: " + lvMailEmpfaenger);
    	System.out.println("Mail-Subject: " + lvMailSubject);
    	System.out.println("Mail-Dateiname: " + lvMailDatei);
    	
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
                
        // Datenbankparameter einlesen
        if (!DatenbankZugriff.readParameter(lvFilenameINI, lvInstitut))
        {
            System.out.println("[Datenbank]: Fehler beim Laden der 'prost.ini'...");
            System.exit(1);
        } 
        
        new SendMail(lvUsername, lvPassword, lvSmtpHost, lvMailEmpfaenger, lvMailSubject, lvMailDatei);
    }
    
    /**
     * Konstruktor mit Parameter
     * @param pvUsername
     * @param pvPassword
     * @param pvSmtpHost
     * @param pvMailEmpfaenger
     * @param pvMailSubject
     * @param pvMailDatei
     */
    public SendMail(String pvUsername, String pvPassword, String pvSmtpHost, String pvMailEmpfaenger, String pvMailSubject, String pvMailDatei)
    {
    	MailVersand lvMailVersand = new MailVersand(pvUsername, pvPassword, pvSmtpHost, pvMailEmpfaenger);
        
    	String lvText = readMailDatei(pvMailDatei);
    	lvMailVersand.send(pvMailSubject, lvText);
        System.exit(0);
    }
    
    /**
     * Liest die Mail-Datei ein
     * @param pvDateiname der Mail-Datei
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
