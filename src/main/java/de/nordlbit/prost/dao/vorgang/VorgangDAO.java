/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao.vorgang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.Vorgang;
import de.nordlbit.prost.dao.DatenbankZugriff;

/**
 * @author tepperc
 *
 */
public class VorgangDAO 
{
    /**
     * Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor
     * @param pvMandant 
     */
    public VorgangDAO(Mandant pvMandant)
    {
        super();
        this.ivMandant = pvMandant;
    }
 
    /**
     * Einfuegen eines Vorgangs
     * @param pvVorgangID 
     * @param pvMandant 
     * @param pvBuchungstag 
     */
    public void insertVorgang(String pvVorgangID, String pvMandant, String pvBuchungstag)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO "+ lvSchemaOwner + ".vorgang VALUES (?, ?, ?)");
          stmt.setString(1, pvVorgangID); 
          stmt.setInt(2, (new Integer(pvMandant)).intValue());
          stmt.setString(3, pvBuchungstag);
          int lvResult = stmt.executeUpdate();
          System.out.println("VorgangDAO - INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Vorgang nicht einfuegen.");
        }  
    }

    /**
     * Aendern der Daten eines Vorgangs
     * @param pvVorgangID 
     * @param pvMandantID 
     * @param pvBuchungstag 
     */
    public void updateVorgang(String pvVorgangID, String pvMandantID, String pvBuchungstag)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".vorgang SET mandant = ?, buchtungstag = ? WHERE vorgang_id = '?'");
          stmt.setInt(1, (new Integer(pvMandantID)).intValue());
          stmt.setString(2, pvBuchungstag);
          stmt.setString(3, pvVorgangID);
          int lvResult = stmt.executeUpdate();
          System.out.println("VorgangDAO - UPDATE-Statement: " + lvResult + " Datensaetze veraendert.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Vorgang mit ID = " + pvVorgangID + " nicht veraendern.");
        }  
    }
   
    /**
     * Loeschen eines Vorgangs
     * @param pvVorgangID
     */
    public void deleteVorgang(String pvVorgangID)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".vorgang WHERE vorgang_id = '" + pvVorgangID + "'");
          System.out.println("VorgangDAO - DELETE-Statement: " + lvResult + " Datensaetze geloescht.");
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte Vorgang mit ID = " + pvVorgangID + " nicht loeschen.");
        }
    }
    
    /**
     * Finden eines Vorgangs anhand der Vorgang-ID
     * @param pvVorgangID 
     * @return 
     */
    public Vorgang findVorgangByID(String pvVorgangID)
    {
        Vorgang lvVorgang = null;

        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT vorgang_id, mandant, buchungstag FROM " + lvSchemaOwner + ".vorgang WHERE vorgang_id = '" + pvVorgangID + "'");
              
          Mandant lvMandant;
          
          if (rs.next()) 
          {
              lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(2))).toString());
              lvVorgang = new Vorgang(rs.getString(1), rs.getString(3), lvMandant, ivMandant.getInstitutsId());
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Vorgang mit ID = " + pvVorgangID + " nicht finden.");
        }

        return lvVorgang;
    }
        
    /**
     * Finden aller Vorgaenge anhand der MandantenID und des Buchungstags
     * @param pvMandantenID 
     * @param pvBuchungstag 
     * @return 
     */
    public HashMap<String, Vorgang> findVorgaengeByID(String pvMandantenID, String pvBuchungstag)
    {
        HashMap<String, Vorgang> lvVorgaenge = new HashMap<String, Vorgang>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT vorgang_id, mandant, buchungstag FROM " + lvSchemaOwner + ".vorgang WHERE mandant = " + pvMandantenID + " AND buchungstag = '" + pvBuchungstag + "'");
              
          Mandant lvMandant;
          Vorgang lvVorgang;
          
          while (rs.next()) 
          {
              lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(2))).toString());
              lvVorgang = new Vorgang(rs.getString(1), rs.getString(3), lvMandant, ivMandant.getInstitutsId());
              lvVorgaenge.put(rs.getString(1), lvVorgang);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Vorgaenge mit MandantenID = " + pvMandantenID + " und Buchungstag = " + pvBuchungstag + " finden.");
        }

        return lvVorgaenge;
    }
    
    /**
     * Liefert eine HashMap aller Vorgaenge
     * @return 
     */
    public HashMap<String, Vorgang> getVorgaenge()
    {
        HashMap<String, Vorgang> lvVorgangHashMap = new HashMap<String, Vorgang>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT vorgang_id, mandant, buchungstag FROM " + lvSchemaOwner + ".vorgang");

          Mandant lvMandant;
          Vorgang lvVorgang;
          while (rs.next()) 
          {
            lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(2))).toString()); 
            lvVorgang = new Vorgang(rs.getString(1), rs.getString(3), lvMandant, ivMandant.getInstitutsId());
            lvVorgangHashMap.put(rs.getString(1), lvVorgang);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Vorgaenge aus der Datenbank lesen.");
        }

        return lvVorgangHashMap;
    }
    
    /**
     * Liefert die maximale ID
     * @return
     */
    public String getMaxId()
    {
    	String lvMaxId = new String();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT MAX(vorgang_id) FROM " + lvSchemaOwner + ".vorgang");
          
          if (rs.next()) 
          {
              lvMaxId = rs.getString(1);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("VorgangDAO: Konnte MaxId nicht ermitteln.");
        }  
        
        return lvMaxId;
    }

}
