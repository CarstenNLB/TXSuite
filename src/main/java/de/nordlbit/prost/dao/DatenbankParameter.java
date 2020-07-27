/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao;

/**
 * @author tepperc
 *
 */
public class DatenbankParameter 
{

    /**
     * Username
     */
    private String ivUsername;
    
    /**
     * Password
     */
    private String ivPassword;
    
    /**
     * Datenbankname
     */
    private String ivDatenbankname;
    
    /**
     * IP
     */
    private String ivIP;
    
    /**
     * Port
     */
    private String ivPort;

    /**
     * Konstruktor
     */
    public DatenbankParameter() 
    {
        this.ivUsername = new String();
        this.ivPassword = new String();
        this.ivDatenbankname = new String();
        this.ivIP = new String();
        this.ivPort = new String();
    }

    /**
     * Liefert den Usernamen
     * @return the username
     */
    public String getUsername() 
    {
        return this.ivUsername;
    }

    /**
     * Setzt den Usernamen
     * @param pvUsername the username to set
     */
    public void setUsername(String pvUsername) 
    {
        this.ivUsername = pvUsername;
    }

    /**
     * Liefert das Passwort
     * @return the password
     */
    public String getPassword() 
    {
        return this.ivPassword;
    }

    /**
     * Setzt das Passwort
     * @param pvPassword the password to set
     */
    public void setPassword(String pvPassword) 
    {
        this.ivPassword = pvPassword;
    }

    /**
     * Liefert den Datenbanknamen
     * @return the datenbankname
     */
    public String getDatenbankname() 
    {
        return this.ivDatenbankname;
    }

    /**
     * Setzt den Datenbanknamen
     * @param pvDatenbankname the datenbankname to set
     */
    public void setDatenbankname(String pvDatenbankname) 
    {
        this.ivDatenbankname = pvDatenbankname;
    }

    /**
     * Liefert die IP
     * @return the iP
     */
    public String getIP() 
    {
        return this.ivIP;
    }

    /**
     * Setzt die IP
     * @param pvIP the iP to set
     */
    public void setIP(String pvIP) 
    {
        this.ivIP = pvIP;
    }

    /**
     * Liefert den Port
     * @return the port
     */
    public String getPort() 
    {
        return this.ivPort;
    }

    /**
     * Setzt den Port
     * @param pvPort the port to set
     */
    public void setPort(String pvPort) 
    {
        this.ivPort = pvPort;
    }
}
