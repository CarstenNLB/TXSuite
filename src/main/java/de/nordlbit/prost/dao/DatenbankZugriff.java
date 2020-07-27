/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.StringCrypt;

/**
 * @author tepperc
 *
 */
public class DatenbankZugriff 
{
    // Bremer Landesbank
    //private static final String ivUsernameBLB = "prost";
    //private static final String ivPasswordBLB = "gammaBLB0";
    //private static final String ivDatenbankBLB = "DBLBPROD";
    //private static final String ivIPBLB = "14.50.6.72";
    
    // Nord/LB
    //private static final String ivUsernameNLB = "prost";
    //private static final String ivPasswordNLB = "gammaNLB0";
    //private static final String ivPasswordNLB = "gruenNLB0";
    
    //private static final String ivDatenbankNLB = "DNLBPROD";
    //private static final String ivDatenbankNLB = "DNTBTXTE";

    //private static final String ivIPNLB = "14.50.6.130";
    //private static final String ivIPNLB = "14.50.14.54";
    
    //private static final String ivPort = "1521";
    
    // Datenbankinfos
    //private static String svUsername = new String();
    //private static String svPassword = new String();
    //private static String svDatenbank = new String();
    //private static String svIP = new String();
    //private static String svPort = new String();
  
    // Datenbankinfos - DNTBTXTE
    //private static String svUsername = "prost";
    //private static String svPassword = "gruenNLB0";
    //private static String svDatenbank = "DNTBTXTE";
    //private static String svIP = "14.50.14.54";
    //private static String svPort = "1521";

    private static Connection ivConnection;
    
    // Liste der Datenbank-Parameter
    private static HashMap<String, DatenbankParameter> ivListeDatenbankParameter = new HashMap<String, DatenbankParameter>();

    
    /**
     * @return the svUsername
     */
    //public static String getUsername() {
    //    return svUsername;
    //}

    /**
     * @param pvUsername the ivUsername to set
     */
    //public static void setUsername(String pvUsername) {
    //    svUsername = pvUsername;
    //}

    /**
     * @return the svPassword
     */
    //public static String getPassword() {
    //    return svPassword;
    //}

    /**
     * @param pvPassword the ivPassword to set
     */
    //public static void setPassword(String pvPassword) {
    //    svPassword = pvPassword;
    //}

    /**
     * @return the svDatenbank
     */
    //public static String getDatenbank() {
    //    return svDatenbank;
    //}

    /**
     * @param pvDatenbank the ivDatenbank to set
     */
    //public static void setDatenbank(String pvDatenbank) {
    //    svDatenbank = pvDatenbank;
    //}

    /**
     * @return the svIP
     */
    //public static String getIP() {
    //    return svIP;
    //}

    /**
     * @param pvIP the ivIP to set
     */
    //public static void setIP(String pvIP) {
    //    svIP = pvIP;
    //}

    /**
     * @return the svPort
     */
    //public static String getPort() {
    //    return svPort;
    //}

    /**
     * @param pvPort 
     */
    //public static void setPort(String pvPort) {
    //    svPort = pvPort;
    //}
    
    /**
     * Oeffnet eine Datenbankverbindung
     * @param pvInstitut 
     * @return 
     */
    /* public static void openConnection(String pvInstitut)
    {
        //if (institut.equals("004"))
        //{
          try
          {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String lvUrl = "jdbc:oracle:thin:@//" + svIP + ":" + svPort + "/" + svDatenbank;
            ivConnection = DriverManager.getConnection(lvUrl, svUsername, svPassword);
          }
          catch (Exception exp)
          {
            System.out.println("Konnte keine Datenbankverbindung zu " + svIP + ":" + svPort + "/" + svDatenbank + " herstellen.");
          }
        //}
        //else
        //{        
        //    if (institut.equals("009"))
        //    {
        //        try
        //        {
        //            Class.forName("oracle.jdbc.driver.OracleDriver");
        //            String lvUrl = "jdbc:oracle:thin:@//" + ivIPNLB + ":" + ivPort + "/" + ivDatenbankNLB;
        //            ivConnection = DriverManager.getConnection(lvUrl, ivUsernameNLB, ivPasswordNLB);    
        //        }
        //        catch (Exception exp)
        //        {
        //            System.out.println("Konnte Datenbankverbindung NLB nicht oeffnen.");
        //        }            
        //    }
        //    else
        //    {
        //        System.out.println("OpenConnection: Fehlerhaftes Institut " + institut);
        //    }
        //}  
    } */
    
    /**
     * Oeffnet eine Datenbankverbindung
     * @param pvDatenbankParameter 
     * @return 
     */
    /* public static void openConnection(DatenbankParameter pvDatenbankParameter)
    {
        //if (institut.equals("004"))
        //{
          try
          {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String lvUrl = "jdbc:oracle:thin:@//" + pvDatenbankParameter.getIP() + ":" + pvDatenbankParameter.getPort() + "/" + pvDatenbankParameter.getDatenbankname();
            ivConnection = DriverManager.getConnection(lvUrl, pvDatenbankParameter.getUsername(), pvDatenbankParameter.getPassword());
          }
          catch (Exception exp)
          {
            System.out.println("Konnte keine Datenbankverbindung zu " + pvDatenbankParameter.getIP() + ":" + pvDatenbankParameter.getPort() + "/" + pvDatenbankParameter.getDatenbankname() + " herstellen.");
          }
        //}
        //else
        //{        
        //    if (institut.equals("009"))
        //    {
        //        try
        //        {
        //            Class.forName("oracle.jdbc.driver.OracleDriver");
        //            String lvUrl = "jdbc:oracle:thin:@//" + ivIPNLB + ":" + ivPort + "/" + ivDatenbankNLB;
        //            ivConnection = DriverManager.getConnection(lvUrl, ivUsernameNLB, ivPasswordNLB);    
        //        }
        //        catch (Exception exp)
        //        {
        //            System.out.println("Konnte Datenbankverbindung NLB nicht oeffnen.");
        //        }            
        //    }
        //    else
        //    {
        //        System.out.println("OpenConnection: Fehlerhaftes Institut " + institut);
        //    }
        //}  
    } */
    
    /**
     * @return the ivListeDatenbankParameter
     */
    public static HashMap<String, DatenbankParameter> getListeDatenbankParameter() 
    {
        return ivListeDatenbankParameter;
    }

    /**
     * @param pvListeDatenbankParameter the ivListeDatenbankParameter to set
     */
    public static void setListeDatenbankParameter(HashMap<String, DatenbankParameter> pvListeDatenbankParameter) 
    {
        ivListeDatenbankParameter = pvListeDatenbankParameter;
    }

    /**
     * Oeffnet eine Datenbankverbindung 
     * @param pvInstitut 
     */
    public static void openConnection(String pvInstitut)
    {
        DatenbankParameter lvDatenbankParameter = ivListeDatenbankParameter.get(pvInstitut);
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String lvUrl = "jdbc:oracle:thin:@//" + lvDatenbankParameter.getIP() + ":" + lvDatenbankParameter.getPort() + "/" + lvDatenbankParameter.getDatenbankname();
            ivConnection = DriverManager.getConnection(lvUrl, lvDatenbankParameter.getUsername(), lvDatenbankParameter.getPassword());
        }
        catch (Exception exp)
        {
            System.err.println("Konnte keine Datenbankverbindung zu " + lvDatenbankParameter.getIP() + ":" + lvDatenbankParameter.getPort() + "/" + lvDatenbankParameter.getDatenbankname() + " herstellen.");
        }
    }
    
    /**
     * Oeffnet die Datenbank
     */
    public void openConnectionSQLite(String pvInstitut)
    {
      DatenbankParameter lvDatenbankParameter = ivListeDatenbankParameter.get(pvInstitut);

      try 
      {
        Class.forName("org.sqlite.JDBC");
        ivConnection = DriverManager.getConnection("jdbc:sqlite:" + lvDatenbankParameter.getDatenbankname());
      } 
      catch (Exception e) 
      {
        System.err.println("Konnte keine Datenbankverbindung zu " + lvDatenbankParameter.getDatenbankname() + " herstellen.");
      }
    }

    
    /**
     * Liefert die aktuelle Datenbankverbindung
     * @return 
     */
    public static Connection getConnection()
    {
        return ivConnection;
    }
    
    /**
     * Schliesst eine Datenbankverbindung
     */
    public static void closeConnection()
    {
        try
        {
          ivConnection.close();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Datenverbindung nicht schliessen.");
        }
    }
    
    /**
     * 
     */
    public static void commit()
    {
        try
        {
          ivConnection.commit();
        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Commit...");
        }
    }
    
    /**
     * Liest die Datenbank-Parameter ein
     * @param pvFilename
     * @param pvInstitut 
     * @return
     */
    public static boolean readParameter(String pvFilename, String pvInstitut)
    {
        DatenbankParameter lvDatenbankParameter = new DatenbankParameter();
        //Datenbankparameter einlesen
        IniReader lvReader = null;
        try 
        {
            lvReader = new IniReader(pvFilename);
            String lvUsername = lvReader.getPropertyString("Datenbank", "Username", "Fehler");
            if (lvUsername.equals("Fehler"))
            {
                System.out.println("Kein Username in der 'prost.ini'...");
                return false;
            }
            lvDatenbankParameter.setUsername(lvUsername);
            System.out.println("Username: " + lvUsername);
                        
            String lvPassword = lvReader.getPropertyString("Datenbank", "Password", "Fehler");
            if (lvPassword.equals("Fehler"))
            {
                System.out.println("Kein Password in der 'prost.ini'...");
                return false;
            }
            lvDatenbankParameter.setPassword(StringCrypt.decodeROT13(lvPassword));
            System.out.println("Password: " + lvPassword);

            String lvDatenbank = lvReader.getPropertyString("Datenbank", "Datenbankname", "Fehler");
            if (lvDatenbank.equals("Fehler"))
            {
                System.out.println("Kein Datenbankname in der 'prost.ini'...");
                return false;
            }
            lvDatenbankParameter.setDatenbankname(lvDatenbank);
            System.out.println("Datenbank: " + lvDatenbank);

            String lvIP =  lvReader.getPropertyString("Datenbank", "IP", "Fehler");
            if (lvIP.equals("Fehler"))
            {
                System.out.println("Keine IP in der 'prost.ini'...");
                return false;
            }
            lvDatenbankParameter.setIP(lvIP);
            System.out.println("IP: " + lvIP);
            
            String lvPort =  lvReader.getPropertyString("Datenbank", "Port", "Fehler");
            if (lvPort.equals("Fehler"))
            {
                System.out.println("Kein Port in der 'prost.ini'...");
                return false;
            }
            lvDatenbankParameter.setPort(lvPort);
            System.out.println("Port: " + lvPort);
        }
        catch (Exception exp)
        {
            //System.out.println("Fehler beim Laden der 'prost.ini'...");
            return false;
        }
        ivListeDatenbankParameter.put(pvInstitut, lvDatenbankParameter);
        return true;
    }
    
}
