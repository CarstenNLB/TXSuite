/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.dao.protokoll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;

import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.ProtokollEintrag;
import de.nordlbit.prost.dao.DatenbankZugriff;

/**
 *
 * @author frankew
 */
public class ProtokollDAO 
{    
    /**
     * Aktuelle Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor
     * @param pvMandant 
     */
    public ProtokollDAO(Mandant pvMandant)
    {
        super();
        this.ivMandant = pvMandant;
    }
    
    /**
     * 
     * @param pvAktion
     * @param pvObjectID
     * @param pvObjectClass
     * @param pvDaten
     * @param pvMandant
     */
    public void insertProtokollEintrag(String pvAktion, String pvObjectID, String pvObjectClass, String pvDaten, String pvMandant)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".protokoll VALUES (?,?,?,?,?,?,?,?)");
          stmt.setInt(1, this.getMaxId() + 1);
          stmt.setString(2, System.getProperty("user.name"));
          stmt.setString(3, pvAktion);
          if(pvObjectID.isEmpty()){
            stmt.setInt(4, 0);
          } else {
            stmt.setInt(4, (new Integer(pvObjectID)).intValue());
          }
          stmt.setString(5, pvObjectClass);
          stmt.setString(6, pvDaten);
          stmt.setInt(7, (new Integer(pvMandant)).intValue());
          GregorianCalendar lvCal = new GregorianCalendar();
          stmt.setTimestamp(8, new Timestamp(lvCal.getTimeInMillis()));
          int lvResult = stmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Protokolleintrag nicht einfuegen.");
        }  
    }
    
    
    public HashMap<String, ProtokollEintrag> findProtokollEintraege(String pvZeitraumVon, String pvZeitraumBis, Mandant pvMandant)
    {
        HashMap<String, ProtokollEintrag> lvProtokollEintragHashMap = new HashMap<String, ProtokollEintrag>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT protokoll_id, benutzer, aktion, objectid, objectclass, daten, aenderungsdatum FROM " + lvSchemaOwner + ".protokoll WHERE aenderungsdatum between " 
                                            + "to_timestamp('"+ pvZeitraumVon + "','YYYY-MM-DD HH24:MI:SS') "    
                                            + "AND " 
                                            + "to_timestamp('"+ pvZeitraumBis + "','YYYY-MM-DD HH24:MI:SS') "  
                                            + " AND mandant = " + pvMandant.getId());

          ProtokollEintrag lvProtokollEintrag;
        
          while (rs.next()) 
          {
            lvProtokollEintrag = new ProtokollEintrag((new Integer(rs.getInt(1))).toString(), rs.getString(2), rs.getString(3), (new Integer(rs.getInt(4))).toString(), rs.getString(5), rs.getString(6), pvMandant, rs.getTimestamp(7));
            lvProtokollEintragHashMap.put((new Integer(rs.getInt(1))).toString(), lvProtokollEintrag);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte keine ProtokollEintraege fuer den Mandanten mit ID = " + pvMandant.getId() + " aus der Datenbank lesen.");
        }

        return lvProtokollEintragHashMap;
    }
    
    /**
     * Liefert die maximale ID
     * @return
     */
    private int getMaxId()
    {
    	int lvMaxId = 0;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT MAX(protokoll_id) FROM " + lvSchemaOwner + ".protokoll");
          
          if (rs.next()) 
          {
              lvMaxId = rs.getInt(1);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("ProtokollDAO: Konnte MaxId nicht ermitteln.");
        }  
        
        return lvMaxId;
    }

    
}
