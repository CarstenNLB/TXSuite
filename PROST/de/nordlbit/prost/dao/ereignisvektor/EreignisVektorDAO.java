/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao.ereignisvektor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;

import de.nordlbit.prost.bd.Ereignis;
import de.nordlbit.prost.bd.EreignisHandler;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.VgEreignis;
import de.nordlbit.prost.dao.DatenbankZugriff;

/**
 * @author tepperc
 *
 */
public class EreignisVektorDAO 
{    
    
    /**
     * Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor
     * @param pvMandant 
     */
    public EreignisVektorDAO(Mandant pvMandant)
    {
        super();
        this.ivMandant = pvMandant;
    }
    
    /**
     * Einfuegen eines Elements in den Ereignis-Vektor
     * @param pvAusloesezeitpunkt 
     * @param pvVorgangID
     * @param pvMandant 
     * @param pvEreignis 
     */
    public void insertEreignisVektor(String pvAusloesezeitpunkt, String pvVorgangID, String pvMandant, String pvEreignis)
     {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".ereignis_vektor VALUES (?, ?, ?, ?, ?)");
          stmt.setInt(1, this.getMaxId() + 1);
          if (pvAusloesezeitpunkt != null)
          {
              stmt.setTimestamp(2, Timestamp.valueOf(pvAusloesezeitpunkt));
          }
          else
          {
              stmt.setTimestamp(2, null);
          }
          stmt.setString(3, pvVorgangID);
          stmt.setInt(4, (new Integer(pvMandant)).intValue());
          stmt.setInt(5, (new Integer(pvEreignis)).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Element nicht in den Ereignis-Vektor einfuegen.");
            exp.printStackTrace();
        }  
    }
    
    /**
     * Veraendern eines Elements in den Ereignis-Vektor
     * @param pvEreignisVektorID 
     * @param pvAusloesezeitpunkt 
     * @param pvVorgangID
     * @param pvMandant 
     * @param pvEreignis 
     */
    public void updateEreignisVektor(String pvEreignisVektorID, String pvAusloesezeitpunkt, String pvVorgangID, String pvMandant, String pvEreignis)
     {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".ereignis_vektor SET ausloesezeitpunkt = ?, vorgang_id = ?, mandant = ?, ereignis = ? WHERE ereignis_vektor_id = ?");
          if (pvAusloesezeitpunkt != null)
          {
              stmt.setTimestamp(1, Timestamp.valueOf(pvAusloesezeitpunkt));
          }
          else
          {
              stmt.setTimestamp(1, null);
          }
          stmt.setString(2, pvVorgangID);
          stmt.setInt(3, (new Integer(pvMandant)).intValue());
          stmt.setInt(4, (new Integer(pvEreignis)).intValue());
          stmt.setInt(5, (new Integer(pvEreignisVektorID)).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("UPDATE-Statement: " + lvResult + " Datensaetze geaendert.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Element nicht veraendern.");
            exp.printStackTrace();
        }  
    }
    
    /**
     * Loeschen eines Elements im Ereignis-Vektor
     * @param pvEreignisVektorID 
     */
    public void deleteEreignisVektor(String pvEreignisVektorID)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".ereignis_vektor WHERE ereignis_vektor_id = " + pvEreignisVektorID);
          System.out.println("DELETE-Statement: " + lvResult + " Datensaetze geloescht.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Element mit ID = " + pvEreignisVektorID + " nicht loeschen.");
        }
    }

    /**
     * Loeschen eines Stop-Ereignisses aus dem Ereignis-Vektor
     * @param pvVorgangID 
     * @param pvMandantID 
     * @param pvEreignisID 
     */
    public void deleteStopEreignisVektor(String pvVorgangID, String pvMandantID, String pvEreignisID)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".ereignis_vektor WHERE vorgang_id = " + pvVorgangID + " AND mandant = " + pvMandantID + " AND ereignis = " + pvEreignisID);
          System.out.println("DELETE-Statement: " + lvResult + " Datensaetze geloescht.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Stop-Ereignis Element fuer Vorgang = " + pvVorgangID + " nicht loeschen.");
        }
    }
    
    /**
     * Finden eines Stop-Ereignisses aus dem Ereignis-Vektor
     * @param pvVorgangID 
     * @param pvMandantID 
     * @param pvEreignisID 
     * @return 
     */
    public boolean findStopEreignisVektor(String pvVorgangID, String pvMandantID, String pvEreignisID)
    {
        boolean lvErgebnis = false;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ausloesezeitpunkt, vorgang_id, mandant, ereignis FROM " + lvSchemaOwner + ".ereignis_vektor WHERE vorgang_id = '" + pvVorgangID + "' AND mandant = " + pvMandantID + " AND ereignis = " + pvEreignisID);
                   
          lvErgebnis = !rs.next();
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Stop-Ereignis im Ereignis-Vektor nicht finden.");
            exp.printStackTrace();
        }

        return lvErgebnis;
    }

    
    /**
     * Finden eines Elements aus dem Ereignis-Vektor anhand der ID
     * @param pvEreignisVektorID 
     * @param pvMandantId 
     * @return 
     */
    public VgEreignis findVgEreignisByID(String pvEreignisVektorID, String pvMandantId)
    {
        VgEreignis lvVgEreignis = null;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ausloesezeitpunkt, vorgang_id, mandant, ereignis FROM " + lvSchemaOwner + ".ereignis_vektor WHERE ereignis_vektor_id = " + pvEreignisVektorID);
                                                   
          if (rs.first()) 
          {
            Mandant lvMandant = Mandant.getMandanten().get(pvMandantId);
            Ereignis lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(4))).toString());
            lvVgEreignis = new VgEreignis(null, lvEreignis);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte VgEreignis nicht finden.");
        }

        return lvVgEreignis;
    } 
    
    /**
     * Finden aller Elemente aus dem Ereignis-Vektor anhand der VorgangID
     * @param pvVorgangID 
     * @return 
     */
    public HashMap<String, VgEreignis> findVgEreignisseByVorgangID(String pvVorgangID)
    {
        HashMap<String, VgEreignis> lvEreignisseHashMap = new HashMap<String, VgEreignis>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ereignis_vektor_id, ausloesezeitpunkt, mandant, ereignis FROM " + lvSchemaOwner + ".ereignis_vektor WHERE vorgang_id = '" + pvVorgangID + "'");
          
          Ereignis lvEreignis;
          VgEreignis lvVgEreignis;
          Mandant lvMandant;
          
          while (rs.next()) 
          {
              lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
              lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(4))).toString());            
              lvVgEreignis = new VgEreignis(pvVorgangID, lvEreignis, lvMandant, rs.getTimestamp(2));
              lvEreignisseHashMap.put((new Integer(rs.getInt(1))).toString(), lvVgEreignis); 
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine VgEreignis im Ereignis-Vektor finden.");
        }

        return lvEreignisseHashMap;
    } 

    
    /**
     * Liefert eine HashMap aller Elemente des Ereignis-Vektors
     * @return 
     */
    public HashMap<String, VgEreignis> getVgEreignisse()
    {
        HashMap<String, VgEreignis> lvEreignisseHashMap = new HashMap<String, VgEreignis>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ereignis_vektor_id, ausloesezeitpunkt, vorgang_id, mandant, ereignis FROM " + lvSchemaOwner + ".ereignis_vektor");
          
          Ereignis lvEreignis;
          VgEreignis lvVgEreignis;
          Mandant lvMandant;
          
          while (rs.next()) 
          {              
            lvMandant = Mandant.getMandanten().get(rs.getString(4));
            lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(5))).toString());
            lvVgEreignis = new VgEreignis(rs.getString(3), lvEreignis, lvMandant, rs.getTimestamp(2));
            lvEreignisseHashMap.put(rs.getString(1), lvVgEreignis); 
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ereignisse aus dem Ereignis-Vektor nicht lesen.");
        }

        return lvEreignisseHashMap;
    }

    /**
     * Liefert die maximale ID
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

          ResultSet rs = stmt.executeQuery("SELECT MAX(ereignis_vektor_id) FROM " + lvSchemaOwner + ".ereignis_vektor");
          
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
            System.out.println("EreignisVektorDAO: Konnte MaxId nicht ermitteln.");
        }  
        
        return lvMaxId;
    }

}
