/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package de.nordlbit.prost.tools.KEVCheckLog;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.MailVersand;
import de.nordlbit.prost.Utilities.StringKonverter;
import de.nordlbit.prost.dao.DatenbankZugriff;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class KEVCheckLog
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
   * Es werden alle Parameter eingelesen.
   * @param args
   */
  public static void main(String[] args)
  {
    String lvFilenameINI;
    String lvMailDatei = new String();
    String lvMailEmpfaenger = new String();
    String lvLogDatei;

    if (args.length == 1)
    {
      lvLogDatei = args[args.length - 1];
      System.out.println("Logdatei: " + lvLogDatei);
      new KEVCheckLog(lvLogDatei);
    }
    else
      {
      lvFilenameINI = args[args.length - 4];
      lvMailEmpfaenger = args[args.length - 3];
      lvMailDatei = args[args.length - 2];
      lvLogDatei = args[args.length - 1];

      System.out.println("INI-Filename: " + lvFilenameINI);
      System.out.println("Mail-Empfaenger: " + lvMailEmpfaenger);
      System.out.println("Mail-Datei: " + lvMailDatei);
      System.out.println("Log-Datei: " + lvLogDatei);

      IniReader lvReader = null;
      String lvInstitut = new String();
      String lvUsername = new String();
      String lvPassword = new String();
      String lvSmtpHost = new String();

      try {
        lvReader = new IniReader(lvFilenameINI);

        lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
        if (lvInstitut.equals("Fehler")) {
          System.out.println("[Konfiguration]: Kein Institut in der 'prost.ini'...");
          System.exit(1);
        }
        System.out.println("Institut: " + lvInstitut);

        lvUsername = lvReader.getPropertyString("Mail", "Username", "Fehler");
        if (lvUsername.equals("Fehler")) {
          System.out.println("[Mail]: Kein Mail-Username in der 'prost.ini' hinterlegt...");
          System.exit(1);
        }
        System.out.println("Mail-Username: " + lvUsername);

        lvPassword = lvReader.getPropertyString("Mail", "Password", "Fehler");
        if (lvPassword.equals("Fehler")) {
          System.out.println("[Mail]: Kein Mail-Password in der 'prost.ini' hinterlegt...");
          System.exit(1);
        }
        System.out.println("Mail-Password: " + lvPassword);

        lvSmtpHost = lvReader.getPropertyString("Mail", "SmtpHost", "Fehler");
        if (lvSmtpHost.equals("Fehler")) {
          System.out.println("[Mail]: Kein SMTP-Host in der 'prost.ini' hinterlegt...");
          System.exit(1);
        }
        System.out.println("SMTP-Host: " + lvSmtpHost);

      } catch (Exception exp) {
        System.out.println("Fehler beim Laden der 'prost.ini'...");
        System.exit(1);
      }

      // Datenbankparameter einlesen
      if (!DatenbankZugriff.readParameter(lvFilenameINI, lvInstitut))
      {
        System.out.println("[Datenbank]: Fehler beim Laden der 'prost.ini'...");
        System.exit(1);
      }

      new KEVCheckLog(lvUsername, lvPassword, lvSmtpHost, lvMailEmpfaenger, lvMailDatei, lvLogDatei);
    }
  }

  /**
   * Konstruktor beim dem lediglich ein 'errorlevel' zurueckgeben wird.
   * Bei '0' alles okay und bei '1' fehlerhaft.
   * @param pvLogDatei Logdatei, die durchsucht wird.
   */
  public KEVCheckLog(String pvLogDatei)
  {

    if (existsQuittung(pvLogDatei))
    {
      System.exit(0);
    }
    else
      {
      System.exit(1);
    }
  }

  /**
   * Konstruktor fuer Check mit Mailversand
   * @param pvUsername Username
   * @param pvPassword Passwort
   * @param pvSmtpHost Adresse des SMTP-Host
   * @param pvMailEmpfaenger Empfaenger der Mail
   * @param pvMailDatei Attachment der Mail
   * @param pvLogDatei Logdatei, die durchsucht wird.
   */
  public KEVCheckLog(String pvUsername, String pvPassword, String pvSmtpHost, String pvMailEmpfaenger, String pvMailDatei, String pvLogDatei)
  {
    MailVersand lvMailVersand = new MailVersand(pvUsername, pvPassword, pvSmtpHost, pvMailEmpfaenger);

    String lvSubject = "KEV - Fehler - Bitte Verarbeitung pruefen!";
    StringBuilder lvHelpText = new StringBuilder();

    String lvHelpSatzfehler = existsSatzfehler(pvLogDatei);
    if (!ivCommandLine.isEmpty())
    {
      lvHelpText.append(ivCommandLine + StringKonverter.lineSeparator);
    }
    if (!ivStartTime.isEmpty())
    {
      lvHelpText.append(ivStartTime + StringKonverter.lineSeparator + StringKonverter.lineSeparator);
    }

    if (!lvHelpSatzfehler.isEmpty()) // Satzfehler vorhanden
    {
      lvHelpText.append(lvHelpSatzfehler);
      lvMailVersand.sendWithAttachment(lvSubject, lvHelpText.toString(), pvMailDatei);
      System.exit(1);
    }
    else // Alles okay...
    {
      System.out.println("Alles okay...");
      System.exit(0);
    }
  }

  /**
   * Existiert eine Quittung
   * @param pvLogDatei Logdatei, die durchsucht wird.
   * @return True -> Quittung; False -> Keine Quittung
   */
  private boolean existsQuittung(String pvLogDatei)
  {
    boolean lvResult = false;
    File lvInputDatei = new File(pvLogDatei);

    if (lvInputDatei.exists())
    {
      String lvLine = "";

      try
      {

        FileInputStream lvFis = new FileInputStream(lvInputDatei);
        BufferedReader lvBR = new BufferedReader(new InputStreamReader(lvFis));

        while ((lvLine = lvBR.readLine()) != null)
        {
          lvLine = lvLine.trim();
          // Zeile mit der Anzahl der herunterladbaren Dateien finden
          if (lvLine.contains("files downloadable for FtPoA"))
          {
            // Zahl aus der Zeile ermitteln
            String lvZahl = lvLine.substring(lvLine.indexOf("ExtraNet-Test: ") + 15);
            lvZahl = lvZahl.substring(0, lvZahl.indexOf(" "));
            // String in Zahl konvertieren
            int lvIntZahl  = StringKonverter.convertString2Int(lvZahl);
            // Ist die Zahl > 0, dann gibt es eine Quittung
            if (lvIntZahl > 0)
            {
              lvResult = true;
            }
          }
        }
      }
      catch (Exception exp)
      {
        System.out.println("Fehler beim Einlesen von '" + pvLogDatei + "'");
      }
    }
    return lvResult;
  }

  /**
   * Liefert die Zeilen, in der ein Satzfehler enthalten ist.
   * @param pvLogDatei
   * @return Zeilen mit Satzfehler
   */
  private String existsSatzfehler(String pvLogDatei)
  {
    StringBuilder lvText = new StringBuilder();

    File lvInputDatei = new File(pvLogDatei);

      String lvLine = "";

      try
      {
        FileInputStream lvFis = new FileInputStream(lvInputDatei);
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

          // Satzfehler
          if (lvLine.contains("Export fehlerhaft wegen Satzfehler"))
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
