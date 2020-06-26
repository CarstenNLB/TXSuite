package de.nordlbit.prost.Utilities;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailVersand 
{
	/**
	 * Username
	 */
    private String ivUsername = "";
    
    /**
     * Passwort
     */
    private String ivPassword = "";
    
    /**
     * Sendeadresse immer gleich
     */
    private final String ivSendeAdresse ="prost@nordlb.de (do not reply)";
    
    /**
     * 
     */
    private String ivEmpfaengerAdresse = "txsuite@nordlb.de";
    
    /**
     * Subjekt
     */
    private final String ivSubject = "PROST - Statusbericht";
    
    /**
     * Adresse des SMTP-Server
     */
    private String ivSmtpHost = "172.20.144.53";

    /**
     * Konstruktor mit Parameter
     * @param pvUsername Username
     * @param pvPassword Passwort
     * @param pvSmtpHost Adresse des SMTP-Host
     * @param pvEmpfaengerAdresse Empfaengeradresse
     */
    public MailVersand(String pvUsername, String pvPassword, String pvSmtpHost, String pvEmpfaengerAdresse)
    {
    	ivUsername = pvUsername;
    	ivPassword = pvPassword;
    	ivSmtpHost = pvSmtpHost;
    	ivEmpfaengerAdresse = pvEmpfaengerAdresse;
    }
    
    /**
     * Versendet eine Mail ueber den SMTP-Server
     * @param pvSubject
     * @param pvText
     */
    public void send(String pvSubject, String pvText)
    {
       MailAuthenticator auth = new MailAuthenticator(ivUsername, ivPassword);
       Properties properties = new Properties();
       // Den Properties wird die ServerAdresse hinzugefuegt
       properties.put("mail.smtp.host", ivSmtpHost);
       // !!Wichtig!! Falls der SMTP-Server eine Authentifizierung verlangt,
       // muss an dieser Stelle die Property auf "true" gesetzt werden.
       properties.put("mail.smtp.auth", "false");

       // Hier wird mit den Properties und dem implements Contructor erzeugten
       // MailAuthenticator eine Session erzeugt.
       Session session = Session.getDefaultInstance(properties, auth);
       try 
       {
           // Eine neue Message erzeugen
          Message msg = new MimeMessage(session);
          // Hier werden die Absender- und Empfaengeradressen gesetzt
          msg.setFrom(new InternetAddress(ivSendeAdresse));
          msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ivEmpfaengerAdresse, false));
          // Der Betreff und Body der Message werden gesetzt
          if (pvSubject.isEmpty())
          {
        	  msg.setSubject(ivSubject);
          }
          else
          {
        	  msg.setSubject(pvSubject);
          }
          msg.setText(pvText);
          // Hier lassen sich HEADER-Informationen hinzufuegen
          //msg.setHeader("Test", "Test");
          msg.setSentDate(new Date( ));
          // Zum Schluss wird die Mail natuerlich noch verschickt
          Transport.send(msg);
       }
       catch (Exception e) 
       {
    	   e.printStackTrace( );
       }
    }

  /**
   * Versendet eine Mail mit Anhang ueber den SMTP-Server
   * @param pvSubject Subject der Mail
   * @param pvText Bodytext der Mail
   * @param pvAttachment Attachment der Mail
   */
  public void sendWithAttachment(String pvSubject, String pvText, String pvAttachment)
  {
    MailAuthenticator auth = new MailAuthenticator(ivUsername, ivPassword);
    Properties properties = new Properties();
    // Den Properties wird die ServerAdresse hinzugefuegt
    properties.put("mail.smtp.host", ivSmtpHost);
    // !!Wichtig!! Falls der SMTP-Server eine Authentifizierung verlangt,
    // muss an dieser Stelle die Property auf "true" gesetzt werden.
    properties.put("mail.smtp.auth", "false");

    // Hier wird mit den Properties und dem implements Contructor erzeugten
    // MailAuthenticator eine Session erzeugt.
    Session session = Session.getDefaultInstance(properties, auth);
    try
    {
      // Eine neue MimeMessage erzeugen
      Message msg = new MimeMessage(session);
      // Hier werden die Absender- und Empfaengeradressen gesetzt
      msg.setFrom(new InternetAddress(ivSendeAdresse));
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ivEmpfaengerAdresse, false));
      // Der Betreff und Body der Message werden gesetzt
      if (pvSubject.isEmpty())
      {
        msg.setSubject(ivSubject);
      }
      else
      {
        msg.setSubject(pvSubject);
      }

      // MimeBodyPart1 erstellen
      MimeBodyPart msgBodyPart1 = new MimeBodyPart();
      msgBodyPart1.setText(pvText);

      // MimeBodyPart2 erstellen
      MimeBodyPart msgBodyPart2 = new MimeBodyPart();

      DataSource source = new FileDataSource(pvAttachment);
      msgBodyPart2.setDataHandler(new DataHandler(source));
      msgBodyPart2.setFileName(pvAttachment);

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(msgBodyPart1);
      multipart.addBodyPart(msgBodyPart2);

      msg.setContent(multipart);

      // Hier lassen sich HEADER-Informationen hinzufuegen
      msg.setSentDate(new Date( ));
      // Zum Schluss wird die Mail natuerlich noch verschickt
      Transport.send(msg);
    }
    catch (Exception e)
    {
      e.printStackTrace( );
    }
  }


  /**
     * Eigentlich nicht noetig, da keine Authentifizierung durchgefuehrt wird. 
     * @author Carsten Tepper
     *
     */
    class MailAuthenticator extends Authenticator 
    {
        /**
         * Ein String, der den Usernamen nach der Erzeugung eines
         * Objektes<br>
         * dieser Klasse enthalten wird.
         */
    	private final String user;

    	/**
    	 * Ein String, der das Passwort nach der Erzeugung eines
    	 * Objektes<br>
    	 * dieser Klasse enthalten wird.
         */
    	private final String password;


    	/**
    	 * Der Konstruktor erzeugt ein MailAuthenticator Objekt<br>
    	 * aus den beiden Parametern user und passwort.
    	 * 
    	 * @param user
    	 *            String, der Username fuer den Mailaccount.
    	 * @param password
    	 *            String, das Passwort fuer den Mailaccount.
    	 */
    	public MailAuthenticator(String user, String password) 
    	{
    		this.user = user;
    		this.password = password;
    	}

    	/**
    	 * Diese Methode gibt ein neues PasswortAuthentication
    	 * Objekt zurueck.
    	 * 
    	 * @see javax.mail.Authenticator#getPasswordAuthentication()
         */
    	protected PasswordAuthentication getPasswordAuthentication() 
    	{
    		return new PasswordAuthentication(this.user, this.password);
    	}
    }

}
