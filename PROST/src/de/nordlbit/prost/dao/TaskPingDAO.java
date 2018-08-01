/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.nordlbit.prost.bd.Mandant;

/**
 * @author tepperc
 *
 */
public class TaskPingDAO 
{
    /**
     * Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor
     * @param pvMandant 
     */
    public TaskPingDAO(Mandant pvMandant)
    {
        this.ivMandant = pvMandant;
    }
    
    /**
     * Einfuegen eines Zeitpunkts in die Datenbank
     * @param pvVorgangID 
     * @param pvMandantenID
     */
    public void insertZeitpunkt(String pvVorgangID, String pvMandantenID)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement lvStmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".taskping VALUES (?,?,?)");
          lvStmt.setString(1, pvVorgangID);
          lvStmt.setInt(2, (new Integer(pvMandantenID)).intValue());
          Calendar lvCal = Calendar.getInstance();
          lvStmt.setTimestamp(3, Timestamp.valueOf(printDateAndTime(lvCal)));
          int lvResult = lvStmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          lvStmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keinen Zeitpunkt einfuegen.");
        }  
    }
    
    /**
     * Liefert den letzten Zeitpunkt
     * @param pvVorgangID 
     * @param pvMandantenID 
     * @return 
     */
    public String getLetztenZeitpunkt(String pvVorgangID, String pvMandantenID)
    {
        String lvLetzterZeitpunkt = new String();
        
        int lvMandant = (new Integer(pvMandantenID)).intValue();
        
        try
        {
            DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
            Connection lvCon = DatenbankZugriff.getConnection(); 
            String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

            Statement lvStmt = lvCon.createStatement();

            ResultSet rs = lvStmt.executeQuery("SELECT zeitpunkt FROM " + lvSchemaOwner + ".taskping WHERE vorgang_id = '" + pvVorgangID + "' AND mandant = " + lvMandant + " ORDER BY zeitpunkt ASC");
            while (rs.next()) 
            {
                lvLetzterZeitpunkt = rs.getTimestamp(1).toString();
            }
            rs.close();
            lvStmt.close();
            DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte letzten Zeitpunkt nicht ermitteln.");
        }
        
        return lvLetzterZeitpunkt;
    }   

    /**
     * Liefert eine Liste der Zeitpunkte
     * @param pvVorgangID 
     * @param pvMandantenID 
     * @return 
     */
    public ArrayList<String> getZeitpunkte(String pvVorgangID, String pvMandantenID)
    {
        ArrayList<String> lvResult = new ArrayList<String>();
        
        int lvMandant = (new Integer(pvMandantenID)).intValue();
        
        try
        {
            DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
            Connection lvCon = DatenbankZugriff.getConnection();  
            String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

            Statement lvStmt = lvCon.createStatement();

            ResultSet rs = lvStmt.executeQuery("SELECT zeitpunkt FROM " + lvSchemaOwner + ".taskping WHERE vorgang_id = '" + pvVorgangID + "' AND mandant = " + lvMandant + " ORDER BY zeitpunkt ASC");
            while (rs.next()) 
            {
                lvResult.add(rs.getTimestamp(1).toString());
            }
            rs.close();
            lvStmt.close();
            DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Zeitpunkte ermitteln.");
        }
        
        return lvResult;
    }   

    /**
     * 
     * @param pvCal
     * @return
     */
    private String printDateAndTime(Calendar pvCal)
    { 
        SimpleDateFormat lvSdf = new SimpleDateFormat(); 
        lvSdf.setTimeZone(pvCal.getTimeZone());
        lvSdf.applyPattern("yyyy-MM-dd HH:mm:ss"); 
        return lvSdf.format(pvCal.getTime());                
    }
}
