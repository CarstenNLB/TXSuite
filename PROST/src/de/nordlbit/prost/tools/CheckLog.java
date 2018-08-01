package de.nordlbit.prost.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.MailVersand;
import de.nordlbit.prost.Utilities.StringKonverter;
import de.nordlbit.prost.dao.DatenbankZugriff;

public class CheckLog 
{
	/**
	 * Zeile mit CommandLine 
	 */
	private String ivCommandLine = new String();
	
	/**
	 * Zeile mit StartTime
	 */
	private String ivStartTime = new String();
	
    /**
     * Startroutine
     * @param args
     */
    public static void main(String[] args) 
    {
    	String lvFilenameINI;
        String lvMailDatei = new String();
        String lvMailEmpfaenger = new String();
        String lvLogDatei = new String();

        if (args[args.length - 3].contains(".ini"))
        {
        	lvFilenameINI = args[args.length - 3];
        	lvMailEmpfaenger = args[args.length - 2];
        	lvLogDatei = args[args.length - 1];
        }
        else
        {
        	lvFilenameINI = args[args.length - 4];
        	lvMailEmpfaenger = args[args.length - 3];
        	lvMailDatei = args[args.length - 2];
        	lvLogDatei = args[args.length - 1];
    	}
    	
    	System.out.println("INI-Filename: " + lvFilenameINI);
    	System.out.println("Mail-Empfaenger: " + lvMailEmpfaenger);
    	System.out.println("Mail-Datei: " + lvMailDatei);
    	System.out.println("Log-Datei: " + lvLogDatei);
    	
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
        
        new CheckLog(lvUsername, lvPassword, lvSmtpHost, lvMailEmpfaenger, lvLogDatei);
    }
    
    /**
     * Konstruktor
     * @param pvUsername
     * @param pvPassword
     * @param pvSmtpHost
     * @param pvMailEmpfaenger
     * @param pvLogDatei
     */
    public CheckLog(String pvUsername, String pvPassword, String pvSmtpHost, String pvMailEmpfaenger, String pvLogDatei)
    {
    	MailVersand lvMailVersand = new MailVersand(pvUsername, pvPassword, pvSmtpHost, pvMailEmpfaenger);
        
    	String lvSubject = "TXS - Fataler Fehler beim Import - Verarbeitung angehalten";
    	StringBuilder lvHelpText = new StringBuilder();
    	String lvText = existsFatalError(pvLogDatei);
    	lvHelpText.append(ivCommandLine + StringKonverter.lineSeparator);
    	lvHelpText.append(ivStartTime + StringKonverter.lineSeparator + StringKonverter.lineSeparator);
    	lvHelpText.append(lvText);
    	//System.out.println(lvHelpText.toString());
    	if (!lvText.isEmpty()) // Fataler Fehler vorhanden
    	{
        	lvMailVersand.send(lvSubject, lvHelpText.toString());
        	System.exit(1);
    	}
    	else // Alles okay...
    	{
    		System.out.println("Alles okay...");
    		System.exit(0);
    	}
    }
    
    /**
     * Liefert die Zeilen, in denen der fatale Fehler beschrieben wird.
     * @param pvLogDatei
     * @return Zeilen mit der Beschreibung des fatalen Fehlers
     */
    private String existsFatalError(String pvLogDatei)
    {
    	StringBuilder lvText = new StringBuilder();
    	String lvLine;
    	
        File lvInputDatei = new File(pvLogDatei);
         
        FileInputStream lvFis = null;
         
        try
        {
            lvFis = new FileInputStream(lvInputDatei);
            BufferedReader lvBR = new BufferedReader(new InputStreamReader(lvFis));
    	
            while ((lvLine = lvBR.readLine()) != null) 
            {
            	lvLine = lvLine.trim();
            	// CommandLine
            	if (lvLine.contains("CommandLine"))
            	{
            		ivCommandLine = lvLine;
            	}
            	
            	// StartTime 
            	if (lvLine.contains("StartTime"))
            	{
            		ivStartTime = lvLine;
            	}
            	
            	// Fataler Fehler 
            	if (lvLine.contains("|F|")) 
            	{
            		lvText.append(lvLine + StringKonverter.lineSeparator);
            	}
            }
            lvFis.close();
        }
        catch (Exception exp)
        {
        	System.out.println("Fehler beim Einlesen von '" + pvLogDatei + "'");
        }
        
    	return lvText.toString();
    }
    
}
