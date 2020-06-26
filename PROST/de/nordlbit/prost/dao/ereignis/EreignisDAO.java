/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao.ereignis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import de.nordlbit.prost.bd.Ereignis;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.Protokoll;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.protokoll.ProtokollDAO;

/**
 * @author tepperc
 *
 */
public class EreignisDAO 
{    
    /**
     * Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor
     * @param pvMandant 
     */
    public EreignisDAO(Mandant pvMandant)
    {
        super();
        this.ivMandant = pvMandant;
    }
    
    /**
     * Einfuegen eines Ereignisses
     * @param pvEreignis 
     */
    public void insertEreignis(Ereignis pvEreignis)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".ereignis (ereignis_id, bezeichnung, mandant, ende_ereignis, stop_ereignis) VALUES (?, ?, ?, ?, ?)");
          stmt.setInt(1, this.getMaxId() + 1);
          stmt.setString(2, pvEreignis.getName());
          stmt.setInt(3, (new Integer(pvEreignis.getMandant().getId())).intValue());
          stmt.setInt(4, (new Integer(pvEreignis.isEndeEreignis() ? "1" : "0")).intValue());
          stmt.setInt(5, (new Integer(pvEreignis.isStopEreignis() ? "1" : "0")).intValue());
          System.out.println(stmt.toString());
          int lvResult = stmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          stmt.close();
          
          // Protokolleintrag
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.INSERT, pvEreignis.getId(), "Ereignis", pvEreignis.getProtokollEintrag(Protokoll.STRING, Protokoll.INSERT), pvEreignis.getMandant().getId());
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ereignis nicht einfuegen.");
            exp.printStackTrace();
        }  
    }

    /**
     * Aendern der Daten eines Ereignisses
     * @param pvEreignis 
     */
    public void updateEreignis(Ereignis pvEreignis)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".ereignis SET bezeichnung = ?, mandant = ?, ende_ereignis = ?, stop_ereignis = ? WHERE ereignis_id = ?");
          stmt.setString(1, pvEreignis.getName());
          stmt.setString(2, pvEreignis.getMandant().getId());
          stmt.setInt(3, (new Integer(pvEreignis.isEndeEreignis() ? "1" : "0")).intValue());
          stmt.setInt(4, (new Integer(pvEreignis.isStopEreignis() ? "1" : "0")).intValue());
          stmt.setInt(5, (new Integer(pvEreignis.getId())).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("UPDATE-Statement: " + lvResult + " Datensaetze veraendert.");
          stmt.close();
          
                    // Protokolleintrag
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.UPDATE, pvEreignis.getId(), "Ereignis", pvEreignis.getProtokollEintrag(Protokoll.STRING, Protokoll.UPDATE), pvEreignis.getMandant().getId());
          
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ereignis mit ID = " + pvEreignis.getId() + " nicht veraendern.");
        }  
    }
   
    
    /**
     * Loeschen eines Ereignis
     * @param pvEreignis
     */
    public void deleteEreignis(Ereignis pvEreignis)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".ereignis WHERE ereignis_id = " + pvEreignis.getId());
          System.out.println("DELETE-Statement: " + lvResult + " Datensaetze geloescht.");
          stmt.close();
          DatenbankZugriff.closeConnection();
          // Protokolleintrag
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.DELETE, pvEreignis.getId(), "Ereignis", pvEreignis.getProtokollEintrag(Protokoll.STRING, Protokoll.DELETE), pvEreignis.getMandant().getId());
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ereignis mit ID = " + pvEreignis.getId() + " nicht loeschen.");
        }
    }
    
    /**
     * Finden eines Ereignisses anhand der Ereignis-ID
     * @param pvEreignisID 
     * @return 
     */
    public Ereignis findEreignisByID(String pvEreignisID)
    {
        Ereignis lvEreignis = null;

        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ereignis_id, bezeichnung, mandant, ende_ereignis, stop_ereignis FROM " + lvSchemaOwner + ".ereignis WHERE ereignis_id = " + pvEreignisID);
              
          Mandant lvMandant;
          boolean lvEndeEreignis = false;
          boolean lvStopEreignis = false;
          
          while (rs.next()) 
          {
            if (rs.getInt(4) == 0)
            {
                lvEndeEreignis = false;
            }
            else
            {
                lvEndeEreignis = true;
            }
            if (rs.getInt(5) == 0)
            {
                lvStopEreignis = false;
            }
            else
            {
                lvStopEreignis = true;
            }

            if (pvEreignisID.equals((new Integer(rs.getInt(1))).toString()))
            {
                  lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                  lvEreignis = new Ereignis((new Integer(rs.getInt(1))).toString(), rs.getString(2), lvMandant, lvEndeEreignis, lvStopEreignis);
            }
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ereignis mit ID " + pvEreignisID + " nicht finden.");
        }

        return lvEreignis;
    }
    
    /**
     * Liefert alle Ereignisse eines Mandanten
     * @param pvMandantenID 
     * @return 
     */
    public HashMap<String, Ereignis> findEreignisseByMandanten(String pvMandantenID)
    {
        HashMap<String, Ereignis> lvEreignisseHashMap = new HashMap<String, Ereignis>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ereignis_id, bezeichnung, mandant, ende_ereignis, stop_ereignis FROM " + lvSchemaOwner + ".ereignis WHERE mandant = " + pvMandantenID);

          Ereignis lvEreignis;
          Mandant lvMandant;
          boolean lvEndeEreignis = false;
          boolean lvStopEreignis = false;
          
          while (rs.next()) 
          {
            if (rs.getInt(4) == 0)
            {
                lvEndeEreignis = false;
            }
            else
            {
                lvEndeEreignis = true;
            }
            if (rs.getInt(5) == 0)
            {
                lvStopEreignis = false;
            }
            else
            {
                lvStopEreignis = true;
            }
            
            lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
            
            
            
            lvEreignis = new Ereignis((new Integer(rs.getInt(1))).toString(), rs.getString(2), lvMandant, lvEndeEreignis, lvStopEreignis);
            lvEreignisseHashMap.put((new Integer(rs.getInt(1))).toString(), lvEreignis);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Ereignisse fuer den Mandanten mit ID = " + pvMandantenID + " aus der Datenbank lesen.");
        }

        return lvEreignisseHashMap;
    }
    
    /**
     * Finden des Stop-Ereignis
     * @param pvMandantenID 
     * @param pvEreignisID 
     * @return 
     */
    public Ereignis findStopEreignis(String pvMandantenID)
    {
        Ereignis lvEreignis = null;

        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ereignis_id, bezeichnung, mandant, ende_ereignis, stop_ereignis FROM " + lvSchemaOwner + ".ereignis WHERE stop_ereignis = 1 and mandant = " + pvMandantenID);
              
          Mandant lvMandant;
          boolean lvEndeEreignis = false;
          boolean lvStopEreignis = false;
          
          while (rs.next()) 
          {
            if (rs.getInt(4) == 0)
            {
                lvEndeEreignis = false;
            }
            else
            {
                lvEndeEreignis = true;
            }
            if (rs.getInt(5) == 0)
            {
                lvStopEreignis = false;
            }
            else
            {
                lvStopEreignis = true;
            }

            //if (pvEreignisID.equals((new Integer(rs.getInt(1))).toString()))
            //{
                  lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                  lvEreignis = new Ereignis((new Integer(rs.getInt(1))).toString(), rs.getString(2), lvMandant, lvEndeEreignis, lvStopEreignis);
            //}
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Stop-Ereignis nicht finden.");
            exp.printStackTrace();
        }

        return lvEreignis;
    }
    
    /**
     * Liefert eine HashMap aller Ereignisse
     * @return 
     */
    public HashMap<String, Ereignis> getEreignisse()
    {
        HashMap<String, Ereignis> lvEreignisseHashMap = new HashMap<String, Ereignis>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ereignis_id, bezeichnung, mandant, ende_ereignis, stop_ereignis FROM " + lvSchemaOwner + ".ereignis");

          Ereignis lvEreignis;
          Mandant lvMandant;
          
          boolean lvEndeEreignis = false;
          boolean lvStopEreignis = false;
          
          while (rs.next()) 
          {
                if (rs.getInt(4) == 0)
                {
                    lvEndeEreignis = false;
                }
                else
                {
                    lvEndeEreignis = true;
                }
                if (rs.getInt(5) == 0)
                {
                    lvStopEreignis = false;
                }
                else
                {
                    lvStopEreignis = true;
                }

            lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
            lvEreignis = new Ereignis((new Integer(rs.getInt(1))).toString(), rs.getString(2), lvMandant, lvEndeEreignis, lvStopEreignis);
            lvEreignisseHashMap.put((new Integer(rs.getInt(1))).toString(), lvEreignis);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte Ereignisse nicht aus der Datenbank lesen.");
        }

        return lvEreignisseHashMap;
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

          ResultSet rs = stmt.executeQuery("SELECT MAX(ereignis_id) FROM " + lvSchemaOwner + ".ereignis");
          
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
            System.out.println("EreignisDAO: Konnte MaxId nicht ermitteln.");
        }  
        
        return lvMaxId;
    }

}
