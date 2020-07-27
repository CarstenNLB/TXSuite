/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package de.nordlbit.prost.tools.BeendeAufgabe;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.VgAufgabe;
import de.nordlbit.prost.bd.Vorgang;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;
import de.nordlbit.prost.dao.mandanten.MandantenDAO;
import de.nordlbit.prost.dao.vorgang.VorgangDAO;
import java.util.HashMap;

public class BeendeAufgabe {
  /**
   * Startroutine
   * @param args
   */
  public static void main(String[] args)
  {
    String lvFilenameINI = args[args.length - 1];
    //String lvMailDatei = new String();
    //String lvMailEmpfaenger = new String();

    /*
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
    } */

    System.out.println("INI-Filename: " + lvFilenameINI);
    //System.out.println("Mail-Empfaenger: " + lvMailEmpfaenger);
    //System.out.println("Mail-Datei: " + lvMailDatei);

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

    new BeendeAufgabe(lvInstitut, lvUsername, lvPassword, lvSmtpHost);
  }

  /**
   * Konstruktor mit Parametern
   * @param pvInstitut Institut
   * @param pvUsername Mail-Username
   * @param pvPassword Mail-Passwort
   * @param pvSmtpHost SMTP-Host
   */
  public BeendeAufgabe(String pvInstitut, String pvUsername, String pvPassword, String pvSmtpHost)
  {
    MandantenDAO lvMandantenDAO = new MandantenDAO(pvInstitut);
    Mandant.setMandanten(lvMandantenDAO.getMandanten());
    Mandant lvMandant = Mandant.getMandanten().get(lvMandantenDAO.findMandantenID(pvInstitut));

    //EreignisVektorDAO ivEreignisVektorDAO = new EreignisVektorDAO(lvMandant);
    AufgabenVektorDAO ivAufgabenVektorDAO = new AufgabenVektorDAO(lvMandant);
    VorgangDAO ivVorgangDAO = new VorgangDAO(lvMandant);

    DatenbankZugriff.openConnection(pvInstitut);

    HashMap<String, Vorgang> helpMap = ivVorgangDAO.getVorgaenge();

    for (Vorgang helpVorgang:helpMap.values())
    {
      //System.out.println(helpVorgang.toString());
      HashMap<String, VgAufgabe> helpMapAufgabe = ivAufgabenVektorDAO.findAufgabenByVorgangID(helpVorgang);

      for (VgAufgabe helpAufgabe:helpMapAufgabe.values())
      {
        if (helpAufgabe.getEndeZeitpunkt() == null)
        {
          System.out.println(helpAufgabe.getAufgabe().getBezeichnung() + ": " + helpVorgang.getId() + " " + lvMandant.getId()  + " " + helpAufgabe.getAufgabe().getFertigstellungsEreignis().getId() + " " + helpAufgabe.getAufgabenVektorID());
        }
      }
    }

    DatenbankZugriff.closeConnection();
    System.exit(0);
  }

}